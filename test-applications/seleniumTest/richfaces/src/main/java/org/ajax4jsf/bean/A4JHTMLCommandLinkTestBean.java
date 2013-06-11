/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import javax.faces.event.ActionEvent;

public class A4JHTMLCommandLinkTestBean {

    private boolean rendered = false;
    private boolean isListenerInvoked = false;
    private boolean isActionInvoked = false;

    private String value;

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public String action() {
        setActionInvoked(true);
        return null;
    }

    public void actionListener (ActionEvent event) {
        setListenerInvoked(true);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isListenerInvoked() {
        return isListenerInvoked;
    }

    public void setListenerInvoked(boolean isListenerInvoked) {
        this.isListenerInvoked = isListenerInvoked;
    }

    public boolean isActionInvoked() {
        return isActionInvoked;
    }

    public void setActionInvoked(boolean isActionInvoked) {
        this.isActionInvoked = isActionInvoked;
    }

}
