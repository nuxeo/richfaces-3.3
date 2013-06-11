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
package org.ajax4jsf.gwt.jsf;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.ajax4jsf.gwt.client.GwtFacesEvent;
import org.ajax4jsf.gwt.client.GwtFacesResult;
import org.ajax4jsf.gwt.client.GwtFacesService;
import org.ajax4jsf.gwt.client.GwtFacesException;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.UnexpectedException;
import com.google.gwt.user.client.rpc.SerializationException;

/**
 * @author shura
 *
 */
public class GwtListenerMethodHelper implements GwtListener, StateHolder, GwtFacesService {

    private MethodExpression _method;


    private Class _eventType ;

    private boolean _transient = false;


    /**
     *
     */
    public GwtListenerMethodHelper() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param method
     * @param listenerType
     */
    public GwtListenerMethodHelper(MethodExpression method, Class listenerType) {
        super();
        if (null == method) {
            throw new IllegalArgumentException("Method expression for GwtListener helper must be not null");
        }
        _method = method;
        _eventType = listenerType;
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.gwt.jsf.GwtListener#isAcceptEvent(org.ajax4jsf.gwt.client.GwtFacesEvent)
      */
    public boolean canHandleRequest(RPCRequest request) {
        // Verify that it's a GWTFacesService call.
        Class gwtFacesServiceClass = GwtFacesService.class;
        Class requestClass = request.getMethod().getDeclaringClass();
        // why is this not true here?????
        if (!gwtFacesServiceClass.equals(requestClass)) {
            return false;
        }

        if (!"sendEvent".equals(request.getMethod().getName())) {
            return false;
        }

        if (request.getParameters().length != 1) {
            return false;
        }

        Object event = request.getParameters()[0];

        if (null != _eventType) {
            return _eventType.isInstance(event);
        } else {
            return true;
        }
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.gwt.jsf.GwtListener#processCall(org.ajax4jsf.gwt.jsf.GwtEvent)
      */
    public String processRequest(RPCRequest request) {
        // Invoke the call on this helper itself!
        try {
            return RPC.invokeAndEncodeResponse(this, request.getMethod(), request.getParameters());
        } catch (SerializationException e) {
            throw new RuntimeException("Could not deserialize request", e);
        }
    }


    /**
     * (non-Javadoc)
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state) {
        State helperState = (State) state;
        _method = (MethodExpression) UIComponentBase.restoreAttachedState(context,helperState.method);
        _eventType = helperState.eventType;
    }

    /* (non-Javadoc)
      * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
      */
    public Object saveState(FacesContext context) {
        State helperState = new State();
        helperState.method = UIComponentBase.saveAttachedState(context,_method);
        helperState.eventType = this._eventType;
        return helperState;
    }

    /**
     * @return Returns the transient.
     */
    public boolean isTransient() {
        return _transient;
    }

    /**
     * @param transient1 The transient to set.
     */
    public void setTransient(boolean transient1) {
        _transient = transient1;
    }

    /**
     * Route the event through to the method.
     * @param event
     * @return
     * @throws GwtFacesException
     */
    public GwtFacesResult sendEvent(GwtFacesEvent event) throws GwtFacesException {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        return (GwtFacesResult)_method.invoke(context, new Object[]{event});
    }

    /**
     * @author shura
     *
     */
    private static class State implements Serializable {

        private Object method;


        private Class  eventType ;

    }

    /**
     * @param eventType The eventType to set.
     */
    public void setEventType(Class eventType) {
        _eventType = eventType;
    }

}
