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

import javax.faces.context.FacesContext;

import org.ajax4jsf.component.UIAjaxCommandLink;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.event.AjaxListener;

public class AjaxListenerTestBean implements AjaxListener{

    private long number = 0l;

    /**
     * Gets value of number field.
     * @return value of number field
     */
    public long getNumber() {
        return number;
    }

    /**
     * Set a new value for number field.
     * @param number a new value for number field
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * @see AjaxListener#processAjax(AjaxEvent)
     */
    public void processAjax(AjaxEvent event) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        UIAjaxCommandLink link = (UIAjaxCommandLink) event.getComponent();
        if(ctx.getMessages().hasNext()) {
            link.setValue("Validation failed, but ajax listener is invoked anyway");
        } else {
            link.setValue("Ajax listener has been invoked successfully");
        }
    }

}
