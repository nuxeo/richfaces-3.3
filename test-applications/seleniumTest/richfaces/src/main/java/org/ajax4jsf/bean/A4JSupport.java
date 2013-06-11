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

public class A4JSupport {
	
	private String data = null;
	
	private boolean checkboxValue1;
	
	private boolean checkboxValue2;
	
	private String status = Messages.IDLE_STATUS;
	
	/** property for testUpdatingValue */
	private String inputValue = A4JSupport.Messages.INPUT_VALUE;
	
	
	
	public static final String BEAN_NAME = "ajaxSupport";
	
	public static class Messages {
		public static final String FOR_LINK = "Link onclick passed";
		
		public static final String FOR_CHECKBOX = "Checkbox onchange passed";
		
		public static final String FOR_COMMAND = "Command onclick passed";
		
		public static final String FOR_SUBMIT = "Default listener";
		
		public static final String NO_DATA = "nodata";
		
		public static final String TEST_PASSED = "test passed";
		
		public static final String VALID_MESSAGE = "field is required";
		
		public static final String INPUT_VALUE = "test";
		
		public static final String IDLE_STATUS = "idle";
		
		public static final String PROCESSING_STATUS = "process";
	}
	
	public void linkListener (ActionEvent event) {
		data = Messages.TEST_PASSED;
		status = Messages.PROCESSING_STATUS;
	}
	
	public void checkBoxListener (ActionEvent event) {
		data = Messages.TEST_PASSED;
		status = Messages.PROCESSING_STATUS;
	}
	
	public void commandListener (ActionEvent event) {
		data = Messages.FOR_COMMAND;
	}

	public void listener (ActionEvent event) {
		data = Messages.FOR_SUBMIT;
	}
	
	public void updatingValueAction() {
		data = inputValue;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return (data != null) ? data : Messages.NO_DATA;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
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
	 * @return the checkboxValue1
	 */
	public boolean isCheckboxValue1() {
		return checkboxValue1;
	}

	/**
	 * @param checkboxValue1 the checkboxValue1 to set
	 */
	public void setCheckboxValue1(boolean checkboxValue1) {
		this.checkboxValue1 = checkboxValue1;
	}

	/**
	 * @return the checkboxValue2
	 */
	public boolean isCheckboxValue2() {
		return checkboxValue2;
	}

	/**
	 * @param checkboxValue2 the checkboxValue2 to set
	 */
	public void setCheckboxValue2(boolean checkboxValue2) {
		this.checkboxValue2 = checkboxValue2;
	}
	
	

}
