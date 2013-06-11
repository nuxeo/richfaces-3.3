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

import org.ajax4jsf.event.AjaxEvent;

public class A4JRegionTestBean {

    private Integer internal = 0;

    private Integer external = 0;

    private Integer value = 0;

    private boolean isInternalListenerInvoked;

    private boolean isExternalListenerInvoked;

    /**
     * Gets value of internal field.
     * 
     * @return value of internal field
     */
    public Integer getInternal() {
        return internal;
    }

    /**
     * Set a new value for internal field.
     * 
     * @param internal
     *                a new value for internal field
     */
    public void setInternal(Integer internal) {
        this.internal = internal;
    }

    /**
     * Gets value of external field.
     * 
     * @return value of external field
     */
    public Integer getExternal() {
        return external;
    }

    /**
     * Set a new value for external field.
     * 
     * @param external
     *                a new value for external field
     */
    public void setExternal(Integer external) {
        this.external = external;
    }

    /**
     * Gets value of value field.
     * 
     * @return value of value field
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Set a new value for value field.
     * 
     * @param value
     *                a new value for value field
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Gets value of isInternalListenerInvoked field.
     * @return value of isInternalListenerInvoked field
     */
    public boolean isInternalListenerInvoked() {
        return isInternalListenerInvoked;
    }

    /**
     * Set a new value for isInternalListenerInvoked field.
     * @param isInternalListenerInvoked a new value for isInternalListenerInvoked field
     */
    public void setInternalListenerInvoked(boolean isInternalListenerInvoked) {
        this.isInternalListenerInvoked = isInternalListenerInvoked;
    }

    /**
     * Gets value of isExternalListenerInvoked field.
     * @return value of isExternalListenerInvoked field
     */
    public boolean isExternalListenerInvoked() {
        return isExternalListenerInvoked;
    }

    /**
     * Set a new value for isExternalListenerInvoked field.
     * @param isExternalListenerInvoked a new value for isExternalListenerInvoked field
     */
    public void setExternalListenerInvoked(boolean isExternalListenerInvoked) {
        this.isExternalListenerInvoked = isExternalListenerInvoked;
    }

    public void processOuterAjax(AjaxEvent event) {
        setExternalListenerInvoked(true);
    }

    public void processInnerAjax(AjaxEvent event) {
        setInternalListenerInvoked(true);
    }
}
