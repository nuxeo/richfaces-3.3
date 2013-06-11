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

import java.util.Date;

import javax.faces.event.ActionEvent;

public class ProgressBarTestBean {
	
	private boolean enabled = false;
	
	private Long value = -5L;
	private Long secondValue = -1L;
		
	public String getDate() {
		return String.valueOf(new Date().getTime());
	}
	
	public String getCompleteDate() {
		return String.valueOf(new Date().getTime());
	}
	
	public void reset() {
		this.value = -5L;
		this.secondValue = -1L;
	}
	
	public void complete(ActionEvent event) {
		this.value = 120L;
	}
	
	public void reset(ActionEvent event) {
		this.value = -5L;
		this.secondValue = -1L;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the value
	 */
	public Long getValue() {
		value++;
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Long value) {
		this.value = value;
	}

	/**
	 * @return the secondValue
	 */
	public Long getSecondValue() {
		secondValue++;
		return secondValue;
	}

	/**
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(Long secondValue) {
		this.secondValue = secondValue;
	}
	
	

}
