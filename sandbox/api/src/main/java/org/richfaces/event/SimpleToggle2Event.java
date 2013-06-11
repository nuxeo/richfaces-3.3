/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

/*
 * Created on 11.07.2006
 */
package org.richfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author igels
 */
public class SimpleToggle2Event extends ActionEvent {

    private static final long serialVersionUID = 5582624805941635421L;
    private boolean _opened;

    /**
     * @param source
     * @param opened
     */
    public SimpleToggle2Event(UIComponent source, boolean opened) {
        super(source);
        this._opened = opened;
    }

    /* (non-Javadoc)
      * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
      */
    public boolean isAppropriateListener(FacesListener listener) {
        return listener instanceof ISimpleToggle2Listener;
    }

    /* (non-Javadoc)
      * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
      */
    public void processListener(FacesListener listener) {
        ((ISimpleToggle2Listener) listener).processToggle(this);
    }

    /**
     * @return Returns the _state.
     */
    public boolean isIsOpen() {
        return _opened;
    }

    /**
     *
     * @param opened The state to set
     */
    public void setIsOpen(boolean opened) {
        this._opened = opened;
    }
}