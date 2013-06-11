/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.ajax4jsf.gwt.server;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.http.HttpServletRequest;

import org.ajax4jsf.gwt.client.ComponentEntryPoint;
import org.ajax4jsf.gwt.client.GwtFacesEvent;
import org.ajax4jsf.gwt.client.GwtFacesService;
import org.ajax4jsf.gwt.jsf.GwtCallListener;
import org.ajax4jsf.gwt.jsf.GwtEvent;
import org.ajax4jsf.gwt.jsf.GwtListener;
import org.ajax4jsf.gwt.jsf.GwtSource;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.client.rpc.SerializationException;

/**
 * @author shura
 * 
 */
public class GwtFacesServlet extends RemoteServiceServlet {

    protected Lifecycle lifecycle;

    private static final Class[] CALL_PARAMS = {GwtFacesEvent.class};


    /**
     * Handle the GWT RPC call by looking up the client ID specified in the request, and passing a callback
     * via JSF to the proper JSF component.
     */
    public String processCall (String requestPayload)
        throws SerializationException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String clientId;
        String responsePayload = null;
        RPCRequest rpcRequest;
        rpcRequest = RPC.decodeRequest(requestPayload);

        if (null != facesContext) {
            clientId = (String)facesContext.getExternalContext().getRequestParameterMap().get("clientId");

            if (null != clientId) {

                // Invoke event on target component
                GwtFacesCallback callback = new GwtFacesCallback(rpcRequest);
                try {
                    facesContext.getViewRoot().invokeOnComponent(facesContext,
                            clientId, callback);
                } catch (Exception e) {
                    // Hmm, for now, let's make this be a server-only exception.
                    throw new UnexpectedException("Error send event to component", e);
                }
                responsePayload = callback.getResponsePayload();
            } else if(null != lifecycle){
                // Invoke event on registered listeners.
                PhaseListener[] listeners = lifecycle.getPhaseListeners();
                for (int i = 0; i < listeners.length; i++) {
                    PhaseListener listener = listeners[i];
                    if (listener instanceof GwtCallListener) {
                        GwtCallListener gwtListener = (GwtCallListener) listener;
                        responsePayload = gwtListener.processRequest(rpcRequest);
                    }
                }
            }
        } else {
            // Non-faces environment, attempt to load stub class for hosted mode
            HttpServletRequest threadLocalRequest = getThreadLocalRequest();
            String moduleName = threadLocalRequest.getParameter(ComponentEntryPoint.GWT_MODULE_NAME_PARAMETER);
            if(null == moduleName){
                // Hackish here, not sure this will work properly.....
                throw new SecurityException("Module name not set in request");
            }
            // Find latest package delimiter. GWT not allow to use default package - since
            // always present.
            int i = moduleName.lastIndexOf('.');
            // Module class name
            String className = "Mock" + moduleName.substring(i+1);
            // Module package name
            String packageName = moduleName.substring(0,i)+".server.";
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            className = packageName+className;
            Class clazz = null;
            try {
                clazz = classLoader.loadClass(className);

                // assume it's looking for GwtFacesService.
                // TODO: figure out how to tell what it's actually looking for!
                // (probably just trust the call!?!)
                GwtFacesService service = (GwtFacesService) clazz.newInstance();
                // OK so it warns about varargs redundancy here, but what does it want????
                responsePayload = RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters());
            } catch (ClassNotFoundException e) {
                throw new SecurityException("Could not find requested mock class " + className);
            } catch (Exception e) {
                throw new SecurityException("Could not construct mock service class " + clazz);
            }
        }
        return responsePayload;
    }

    /**
     * This callback object is passed to ViewRoot.invokeOnComponent() to invoke the RPCCall
     * on the proper JSF component.
     */
    private static class GwtFacesCallback implements ContextCallback {
        private String responsePayload = null;

        private RPCRequest request;

        /**
         * Construct a callback for the specified call.
         */
        public GwtFacesCallback(RPCRequest request) {
            super();
            this.request = request;
        }

        /**
         * Called by JSF to invoke the call on the specified target.
         * @param context
         * @param target
         */
        public void invokeContextCallback(FacesContext context,
                                          UIComponent target) {
            if (target instanceof GwtSource) {
                GwtEvent gwtEvent = new GwtEvent(target){

                    public String getResponsePayload() {
                        // TODO Auto-generated method stub
                        return responsePayload;
                    }

                    public void setCallbackParameter(String callbackParam) {
                        responsePayload = callbackParam;

                    }

                    public RPCRequest getRPCRequest () {
                        // TODO Auto-generated method stub
                        return request;
                    }

                };
                target.broadcast(gwtEvent);
            }
            if (target instanceof GwtListener) {
                GwtListener listener = (GwtListener) target;
                responsePayload = listener.processRequest(request);
            }

        }

        /**
         * @return Returns the gwtFacesResult.
         */
        public String getResponsePayload() {
            return responsePayload;
        }

    }
}
