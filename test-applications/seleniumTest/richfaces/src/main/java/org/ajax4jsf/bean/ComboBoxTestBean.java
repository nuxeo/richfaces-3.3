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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ComboBoxTestBean implements Validator, Converter{

	private String value; 
	private String trace; 
	private Boolean directInputSuggestions; 
	private Boolean filterNewValues; 
	private Boolean selectFirstOnUpdate; 
	private Boolean enableManualInput;
	
    private List<SelectItem> treeItems;
    private List<String> treeNames;

    public ComboBoxTestBean() {
        treeNames = Arrays.asList("Pine", "Birch", "Aspen", "Spruce", "Oak", "Maple", "Ash", "Lime");
        treeItems = new ArrayList<SelectItem>();
        for (String treeName : treeNames) {
            treeItems.add(new SelectItem(treeName, treeName));
        }
    }

    public void init() {
    	value = null;
    	trace = null;
    	directInputSuggestions = false;
    	filterNewValues = true;
    	selectFirstOnUpdate = true;
    	enableManualInput = true;
    }
    
    /**
     * Gets value of treeItems field.
     * @return value of treeItems field
     */
    public List<SelectItem> getTreeItems() {
        return treeItems;
    }

    /**
     * Gets value of treeNames field.
     * @return value of treeNames field
     */
    public List<String> getTreeNames() {
        return treeNames;
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
		if ("11".equals(value)) {
			throw new ValidatorException(new FacesMessage("Value mustn't be equal 11."));
		}
	}
	
	public void validate2(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if ("44".equals(value)) {
			throw new ValidatorException(new FacesMessage("Value mustn't be equal 44."));
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

	public void setDirectInputSuggestions(Boolean directInputSuggestions) {
		this.directInputSuggestions = directInputSuggestions;
	}

	public Boolean getDirectInputSuggestions() {
		return directInputSuggestions;
	}

	public void setFilterNewValues(Boolean filterNewValues) {
		this.filterNewValues = filterNewValues;
	}

	public Boolean getFilterNewValues() {
		return filterNewValues;
	}

	public void setSelectFirstOnUpdate(Boolean selectFirstOnUpdate) {
		this.selectFirstOnUpdate = selectFirstOnUpdate;
	}

	public Boolean getSelectFirstOnUpdate() {
		return selectFirstOnUpdate;
	}

	public void setEnableManualInput(Boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public Boolean getEnableManualInput() {
		return enableManualInput;
	}
}
