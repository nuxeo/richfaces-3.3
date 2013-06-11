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

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.model.ActionParamObject;
import org.ajax4jsf.model.ActionParamObject2;

import com.sun.faces.context.FacesContextImpl;

public class A4JActionParam {
	
	private static final String PARAMETER_NAME = "param";
	
	private static final String PARAMETER_VALUE = "Action Parameter";
	
	public static class ERRORS {
		public static final String NO_PARAM = "1"; 
		
		public static final String INVALID_VALUE = "2";
		
		public static final String NOT_ASSIGNED = "3";
	}
	
	private String parameterName;
	
	private String parameterValue;
	
	private String errorMessage = null;
	
	private ActionParamObject object = new ActionParamObject("actionParamName");
	
	private ActionParamObject2 object2 = new ActionParamObject2("actionParamName");
	
	public void listener(ActionEvent event) {
		FacesContext context = FacesContextImpl.getCurrentInstance();
		Map<String, String> p = context.getExternalContext().getRequestParameterMap();
		if (p.get(PARAMETER_NAME) == null) {
			errorMessage = ERRORS.NO_PARAM;
		} else if (!PARAMETER_VALUE.equals(p.get(PARAMETER_NAME))) {
			errorMessage = ERRORS.INVALID_VALUE;
		} else if (parameterValue == null) {
			errorMessage = ERRORS.NOT_ASSIGNED;
		} else {
			parameterName = PARAMETER_NAME;
		}
		
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return (errorMessage == null && (parameterName != null || parameterValue!=null)) ? (parameterName + "='" + parameterValue + "'") : "";
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		;
	}

	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public ActionParamObject getObject() {
		return object;
	}

	public void setObject(ActionParamObject object) {
		this.object = object;
	}

	/**
	 * @return the object2
	 */
	public ActionParamObject2 getObject2() {
		return object2;
	}

	/**
	 * @param object2 the object2 to set
	 */
	public void setObject2(ActionParamObject2 object2) {
		this.object2 = object2;
	}
	
	

}
