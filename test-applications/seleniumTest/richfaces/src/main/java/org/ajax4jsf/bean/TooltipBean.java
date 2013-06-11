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

public class TooltipBean {

    private String mode = "ajax";

    private int hitCount = 0;

    /**
     * Gets value of mode field.
     * @return value of mode field
     */
    public String getMode() {
        return mode;
    }

    /**
     * Set a new value for mode field.
     * @param mode a new value for mode field
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Gets value of hitCount field.
     * @return value of hitCount field
     */
    public int getHitCount() {
        return hitCount++;
    }

    /**
     * Set a new value for hitCount field.
     * @param hitCount a new value for hitCount field
     */
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public void initClientTest() {
        reset();
        mode = "client";
    }

    public void reset() {
        hitCount = 0;
        mode = "ajax";
    }
}
