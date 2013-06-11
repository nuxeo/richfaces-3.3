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

package org.ajax4jsf.bean;

import java.util.Date;

import javax.faces.event.ActionEvent;

public class ContextMenuTestBean {

	static final String attachTo = "attachToLink"; 
	
	String status; 
	private String value;
	private int value2;
	private String submitMode = "none";
	private Boolean attached = false;
	private String attacheTo = "";
	private Boolean disableDefault = true;

	public ContextMenuTestBean() {
		value = "";
		value2 = 0;
	}

	public void actionListener(ActionEvent event) {
		status = "ActionListener";
	}

	public String testServerSubmitMode() {
		submitMode = "server";
		status = null;
		return null;
	}

	public String testAjaxSubmitMode() {
		submitMode = "ajax";
		status = null;
		return null;
	}
	
	public String testAttached() {
		attached = true;
		attacheTo = null;
		return null;
	}
	
	public String testAttachTo() {
		attacheTo = attachTo;
		attached = true;
		return null;
	}
	
	public String testDisableDefault() {
		disableDefault = false;	
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String action() {
		if (4 == value2) {
			value2 = 1;
		} else {
			value2++;
		}
		return null;
	}

	public String getTime() {
		return String.valueOf(new Date().getTime());
	}

	public void reset() {
		submitMode = "none";
		attached = false;
		attacheTo = "";
		status = null;
		disableDefault = true;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public String getMessage3() {
		return "Menu3";
	}

	public String getMessage4() {
		return "Menu4";
	}

	/**
	 * @return the submitMode
	 */
	public String getSubmitMode() {
		return submitMode;
	}

	/**
	 * @param submitMode
	 *            the submitMode to set
	 */
	public void setSubmitMode(String submitMode) {
		this.submitMode = submitMode;
	}

	/**
	 * @return the attached
	 */
	public Boolean getAttached() {
		return attached;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(Boolean attached) {
		this.attached = attached;
	}

	/**
	 * @return the attacheTo
	 */
	public String getAttacheTo() {
		return attacheTo;
	}

	/**
	 * @param attacheTo the attacheTo to set
	 */
	public void setAttacheTo(String attacheTo) {
		this.attacheTo = attacheTo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the disableDefault
	 */
	public Boolean getDisableDefault() {
		return disableDefault;
	}

	/**
	 * @param disableDefault the disableDefault to set
	 */
	public void setDisableDefault(Boolean disableDefault) {
		this.disableDefault = disableDefault;
	}

}
