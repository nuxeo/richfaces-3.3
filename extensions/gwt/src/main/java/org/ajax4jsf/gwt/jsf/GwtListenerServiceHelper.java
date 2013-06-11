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

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.client.rpc.SerializationException;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;
import java.io.Serializable;

/**
 * GwtListener implementation that wraps a generic service bean.
 *
 * @author Rob Jellinghaus
 */
public class GwtListenerServiceHelper implements GwtListener, StateHolder {


    private ValueExpression serviceBeanExpression;


    private boolean _transient = false;


    /**
     * Default constructor; invoked by JSF view restore lifecycle.
     */
    public GwtListenerServiceHelper() {
        super();
    }

    /**
     * Constructor; invoked from GwtListenerHandler when constructing gwt:gwtListener tags.
     * @param serviceBean
     */
    public GwtListenerServiceHelper(ValueExpression serviceBean) {
        super();
        if (null == serviceBean) {
            throw new IllegalArgumentException("Binding expression for GwtListener helper must be not null");
        }
        this.serviceBeanExpression = serviceBean;
    }

    /**
     * Can this service helper handle this request?  Implemented by inspecting the service bean to
     * determine whether it implements the requested interface.
     */
    public boolean canHandleRequest(RPCRequest request) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        Object serviceBean = getServiceBean(context);
        Class requestInterface = request.getMethod().getDeclaringClass();
        boolean interfaceIsSuperclassOf = requestInterface.isAssignableFrom(serviceBean.getClass());
        return interfaceIsSuperclassOf;
    }

    private Object getServiceBean(ELContext context) {
        return serviceBeanExpression.getValue(context);
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.gwt.jsf.GwtListener#processCall(org.ajax4jsf.gwt.jsf.GwtEvent)
      */
    public String processRequest(RPCRequest request) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        Object serviceBean = getServiceBean(context);
        try {
            return RPC.invokeAndEncodeResponse(serviceBean, request.getMethod(), request.getParameters());
        } catch (SerializationException e) {
            throw new RuntimeException("Couldn't serialize method response", e);
        }
    }

    /* (non-Javadoc)
      * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
      */
    public void restoreState(FacesContext context, Object state) {
        GwtListenerServiceHelper.State helperState = (GwtListenerServiceHelper.State) state;
        serviceBeanExpression = (ValueExpression) UIComponentBase.restoreAttachedState(context,helperState.binding);
    }

    /* (non-Javadoc)
      * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
      */
    public Object saveState(FacesContext context) {
        GwtListenerServiceHelper.State helperState = new GwtListenerServiceHelper.State();
        helperState.binding = UIComponentBase.saveAttachedState(context,serviceBeanExpression);
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
     * @author shura
     *
     */
    private static class State implements Serializable {

        private Object binding;

    }

}
