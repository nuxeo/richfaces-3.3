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

package org.richfaces;

import java.util.Date;
import java.util.Random;

import javax.faces.event.ActionEvent;

/**
 * @author $Autor$
 *
 */
public class Bean {
	
	private String textValue1 = "Click here to switch panel.";
	private String canon = "First toggle panel (Client switch type)(initialState=canon).";
	private String nikon = "Second toggle panel (Ajax switch type)(initialState=blank).";
	private String olympus = "Third toggle panel (Server switch type)(initialState=blank).";
	private String switchType = "server";
	private String initialState = "olympus";
	private String stateOrder = "canon,nikon,olympus";
	private String beanState = "";
	//private String[] stateOrders = {"blank","canon","nikon","olympus"};
	
	private String first = "";
	private String second = "";
	
	public String getBeanState() {
		return "Last toggle panel options: SwitchType="+getSwitchType()+"; initialState="+getInitialState()+"; stateOrder="+getStateOrder();
	}
	
	public void action(ActionEvent event) {
	    System.out.println(" >>> cfif action");
	}
	
	public void setBeanState(String beanState) {
		this.beanState = beanState;
	}
	public String getCanon() {
		return canon;
	}
	public void setCanon(String canon) {
		this.canon = canon;
	}
	public String getInitialState() {
		return initialState;
	}
	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}
	public String getNikon() {
		return nikon;
	}
	public void setNikon(String nikon) {
		this.nikon = nikon;
	}
	public String getOlympus() {
		return olympus;
	}
	public void setOlympus(String olympus) {
		this.olympus = olympus;
	}
	public String getStateOrder() {
		return stateOrder;
	}
	public void setStateOrder(String stateOrder) {
		this.stateOrder = stateOrder;
	}
	public String getSwitchType() {
		return switchType;
	}
	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}
	public String getTextValue1() {
		return textValue1;
	}
	public void setTextValue1(String textValue1) {
		this.textValue1 = textValue1;
	}
    public String getFirst() {
        return first + " -" + second + "- time=" + new Date();
    }
    public void setFirst(String first) {
        this.first = first;
    }
    public String getSecond() {
        return first + " -" + second + "- time=" + new Date();
    }
    public void setSecond(String second) {
        this.second = second;
    }
}