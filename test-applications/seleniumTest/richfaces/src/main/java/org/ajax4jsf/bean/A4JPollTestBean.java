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

public class A4JPollTestBean {

    private Integer i = 1;

    private static final String POLLING = "Polling";

    private boolean enabled = false;

    private String content = "content";

    /**
     * Gets value of content field.
     * @return value of content field
     */
    public String getContent() {
        return content;
    }

    /**
     * Set a new value for content field.
     * @param content a new value for content field
     */
    public void setContent(String content) {
        this.content = content;
    }

    public void start(ActionEvent event) {
        enabled = true;
    }

    public void listener(ActionEvent event) {
        i++;
        if (i == 8) {
            enabled = false;
        }
    }

    public String getText() {
        return POLLING.substring(0, i);
    }

    /**
     * @return the i
     */
    public Integer getI() {
        return i;
    }

    /**
     * @param i
     *                the i to set
     */
    public void setI(Integer i) {
        this.i = i;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *                the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
