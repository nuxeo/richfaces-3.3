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

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.AjaxContextImpl;

import com.sun.faces.context.FacesContextImpl;

public class A4JCommandTestBean {
	
	private String value;
	
	private boolean rendered = true;
	
	private AjaxContext getAjaxContext () {
		FacesContext context = FacesContextImpl.getCurrentInstance();
		if (context != null) {
			return AjaxContextImpl.getCurrentInstance(context); 
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void put2ResponseData(String key, Object value) {
		AjaxContext ajaxContext = getAjaxContext();
		if (ajaxContext != null) {
			Map<String, Object> data = (Map<String, Object>)ajaxContext.getResponseData();
			if (null == data) {
				data = new HashMap<String, Object>();
				ajaxContext.setResponseData(data);
			}
			data.put(key, value);
		}
	}
	
	public String action () {
		put2ResponseData("action", true);
		return null;
	}
	
	public void actionListener (ActionEvent event) {
		put2ResponseData("actionListener", true);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
		put2ResponseData("input", true);
	}
	
	public boolean isRendered() {
		return rendered;
	}
	
	public String reRender(){
		this.setRendered(!rendered);
		return null;
	}

	/**
	 * @param value the value to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public String getDate () {
		return String.valueOf(Math.random());
	}

}
