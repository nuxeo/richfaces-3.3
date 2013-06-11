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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ColorPickerBean implements Validator, Converter{

	private String value = "#ff0000"; 
	private String trace;
	
	private Boolean filterNewValues; 
	
    public ColorPickerBean() {
    }

    public void init() {
    	value = "#ff0000";
    	trace = null;
    	
    	filterNewValues = true;
    }
    
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getTrace() {
		return trace;
	}

	public void valueChangeListener(ValueChangeEvent event) {
		trace = "changed";
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if ("#ff00ff".equals(value)) {
			throw new ValidatorException(new FacesMessage("Value mustn't be equal #ff00ff."));
		}
	}
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return value != null ? value.substring(2): null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return value != null ? "c:" + value.toString(): null;
	}

	public void setFilterNewValues(Boolean filterNewValues) {
		this.filterNewValues = filterNewValues;
	}

	public Boolean getFilterNewValues() {
		return filterNewValues;
	}
}
