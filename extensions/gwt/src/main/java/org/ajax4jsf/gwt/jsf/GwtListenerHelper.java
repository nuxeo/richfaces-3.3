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
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.google.gwt.user.server.rpc.RPCRequest;

/**
 * @author shura
 *
 */
public class GwtListenerHelper implements GwtListener, StateHolder {


    private ValueExpression _binding;


    private boolean _transient = false;


    /**
     *
     */
    public GwtListenerHelper() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param binding
     */
    public GwtListenerHelper(ValueExpression binding) {
        super();
        if (null == binding) {
            throw new IllegalArgumentException("Binding expression for GwtListener helper must be not null");
        }
        _binding = binding;
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.gwt.jsf.GwtListener#isAcceptEvent(org.ajax4jsf.gwt.client.GwtFacesEvent)
      */
    public boolean canHandleRequest(RPCRequest request) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        GwtListener handler = getHandler(context);
        return handler.canHandleRequest(request);
    }

    private GwtListener getHandler(ELContext context) {
        return (GwtListener) _binding.getValue(context);
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.gwt.jsf.GwtListener#processCall(org.ajax4jsf.gwt.jsf.GwtEvent)
      */
    public String processRequest(RPCRequest request) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        GwtListener handler = getHandler(context);
        return handler.processRequest(request);
    }

    /* (non-Javadoc)
      * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
      */
    public void restoreState(FacesContext context, Object state) {
        State helperState = (State) state;
        _binding = (ValueExpression) UIComponentBase.restoreAttachedState(context,helperState.binding);
    }

    /* (non-Javadoc)
      * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
      */
    public Object saveState(FacesContext context) {
        State helperState = new State();
        helperState.binding = UIComponentBase.saveAttachedState(context,_binding);
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
