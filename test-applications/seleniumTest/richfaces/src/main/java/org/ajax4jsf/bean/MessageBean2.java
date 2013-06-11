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

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class MessageBean2 implements Validator{
	
	private String string;
	
	private String secondInputString;
	
	private String thirdInputString;
	
	private Boolean rendered;

	private Boolean showDetail;

	private Boolean showSummary;

	private Boolean ajaxRendered;
	
	public MessageBean2() {
		init();
	}
	
	public void init() {
		string = "something";
		secondInputString = "something2";
		thirdInputString = "something3";
		rendered = true;
		showDetail = true;
		showSummary = false;
		ajaxRendered = false;
	}
	
	public String addGlobalMessage() {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "globalMessageSummary",
						"globalMessageDetail"));
		return null;
	}
	
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		Severity severity = null;
		if ("fatal".equals(value)) {
			severity = FacesMessage.SEVERITY_FATAL;			
		} else if ("error".equals(value)) {
			severity = FacesMessage.SEVERITY_ERROR;			
		} else if ("warn".equals(value)) {
			severity = FacesMessage.SEVERITY_WARN;			
		} else if ("info".equals(value)) {
			severity = FacesMessage.SEVERITY_INFO;			
		}
		if (severity != null) {
			throw new ValidatorException(new FacesMessage(severity,
					"messageSummary", "messageDetail"));
		}
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}

	public Boolean getRendered() {
		return rendered;
	}

	public void setShowDetail(Boolean showDetail) {
		this.showDetail = showDetail;
	}

	public Boolean getShowDetail() {
		return showDetail;
	}

	public void setShowSummary(Boolean showSummary) {
		this.showSummary = showSummary;
	}

	public Boolean getShowSummary() {
		return showSummary;
	}

	public void setAjaxRendered(Boolean ajaxRendered) {
		this.ajaxRendered = ajaxRendered;
	}

	public Boolean getAjaxRendered() {
		return ajaxRendered;
	}

	/**
	 * @return the secondInputString
	 */
	public String getSecondInputString() {
		return secondInputString;
	}

	/**
	 * @param secondInputString the secondInputString to set
	 */
	public void setSecondInputString(String secondInputString) {
		this.secondInputString = secondInputString;
	}

	/**
	 * @return the thirdInputString
	 */
	public String getThirdInputString() {
		return thirdInputString;
	}

	/**
	 * @param thirdInputString the thirdInputString to set
	 */
	public void setThirdInputString(String thirdInputString) {
		this.thirdInputString = thirdInputString;
	}

	
}
