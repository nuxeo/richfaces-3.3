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

package org.richfaces.samples;

import java.util.Date;

import javax.faces.event.ActionEvent;

/**
 * @author $Autor$
 * 
 */
public class Bean {

    private boolean enabled1 = false;
    private boolean ajaxMode1;
    private Integer value1 = 0;

    private boolean enabled2 = false;
    private boolean ajaxMode2;
    private Integer value2 = 0;

    public String action1() {
	System.out.println("Bean.action()");
	return null;
    }
    
    public String action2() {
	System.out.println("Bean.action()");
	return null;
    }

    public void listener1 (ActionEvent event) {
	System.out.println("Bean.listener1");
    }
    
    public void listener2 (ActionEvent event) {
	System.out.println("Bean.listener2");
    }

    public String start() {
	this.enabled1 = true;
	this.enabled2 = true;
	return null;
    }

    public String getDate1() {
	Date date = new Date();
	return date.toLocaleString();
    }
    
    public String getDate2() {
	Date date = new Date();
	return date.toLocaleString();
    }


    /**
     * @return the value
     */
    public Integer getValue1() {
	// value = value.add(new BigDecimal(0.6));
	return value1;
    }

    /**
     * @param value
     *                the value to set
     */
    public void setValue1(Integer value) {
	this.value1 = value;
    }

    public Integer getIncValue1() {
	return value1++;
    }
    
    public Integer getIncValue2() {
	return value2++;
    }

    /**
     * @return the enabled
     */
    public boolean getEnabled1() {
	return enabled1;
    }

    /**
     * @param enabled
     *                the enabled to set
     */
    public void setEnabled1(boolean enabled) {
	this.enabled1 = enabled;
    }

    public boolean isAjaxMode1() {
	return ajaxMode1;
    }

    public void setAjaxMode1(boolean ajaxMode) {
	this.ajaxMode1 = ajaxMode;
    }

    public String getModeString1() {
	return ajaxMode1 ? "ajax" : "client";
    }
    
    public String getModeString2() {
	return ajaxMode2 ? "ajax" : "client";
    }

    /**
     * @return the enabled2
     */
    public boolean isEnabled2() {
        return enabled2;
    }

    /**
     * @param enabled2 the enabled2 to set
     */
    public void setEnabled2(boolean enabled2) {
        this.enabled2 = enabled2;
    }

    /**
     * @return the ajaxMode2
     */
    public boolean isAjaxMode2() {
        return ajaxMode2;
    }

    /**
     * @param ajaxMode2 the ajaxMode2 to set
     */
    public void setAjaxMode2(boolean ajaxMode2) {
        this.ajaxMode2 = ajaxMode2;
    }

    /**
     * @return the value2
     */
    public Integer getValue2() {
        return value2;
    }

    /**
     * @param value2 the value2 to set
     */
    public void setValue2(Integer value2) {
        this.value2 = value2;
    }

}