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

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import com.google.gwt.user.server.rpc.RPCRequest;

/**
 * Abstract superclass of GWT events that route GWT calls through JSF.
 * @author shura
 */
public abstract class GwtEvent extends FacesEvent {

    /**
     * @param source
     */
    public GwtEvent(UIComponent source) {
        super(source);
    }

    /**
     * Is the listener appropriate?
     * @param listener
     */
    public boolean isAppropriateListener (FacesListener listener) {
        if (listener instanceof GwtListener) {
            GwtListener gwtListener = (GwtListener) listener;
            return gwtListener.canHandleRequest(getRPCRequest());
        }
        return false;
    }

    /* (non-Javadoc)
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
    public void processListener(FacesListener listener) {
        GwtListener gwtListener = (GwtListener) listener;
        setCallbackParameter(gwtListener.processRequest(this.getRPCRequest()));
    }

    /**
     * Get the GWT serialized response payload.
     * @return the response payload
     */
    public abstract String getResponsePayload();

    /**
     * Set the response payload.  Called by the handler when it invokes the GWT call on the
     * proper component.
     * @param responsePayload the payload to set
     */
    public abstract void setCallbackParameter(String responsePayload);

    /**
     * Get the call that's being routed.
     */
    public abstract RPCRequest getRPCRequest();

}
