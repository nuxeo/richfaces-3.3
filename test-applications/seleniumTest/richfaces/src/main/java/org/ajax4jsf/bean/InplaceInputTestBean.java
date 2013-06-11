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
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

public class InplaceInputTestBean {

	public final static class Messages {
		
		public final static String ACTION_CALLED = "action called";
		
		public final static String VALUECHANGELISTENER_CALLED = "listener called";
		
		public final static String II_VALUE = "test";
	}
	
    private String text ="";
    
    private String newText = "";
    
    private String iiValue = "test";
    
    /**
     * Gets value of text field.
     * @return value of text field
     */
    public String getText() {
        return text;
    }

    /**
     * Set a new value for text field.
     * @param text a new value for text field
     */
    public void setText(String text) {
        this.text = text;
    }

    public void action() {
    	setText(InplaceInputTestBean.Messages.ACTION_CALLED);
    }
    
    public void valueChangeListener(ValueChangeEvent ve) {
    	setNewText(Messages.VALUECHANGELISTENER_CALLED);
    }
    
    public void validator(FacesContext arg0, UIComponent arg1, Object arg2) {
    	if (!"test".equals(arg2)) {
			throw new ValidatorException(new FacesMessage("Value isn't correct!"));
		}
    }

	/**
	 * @return the newText
	 */
	public String getNewText() {
		return newText;
	}

	/**
	 * @param newText the newText to set
	 */
	public void setNewText(String newText) {
		this.newText = newText;
	}

	/**
	 * @return the iiValue
	 */
	public String getIiValue() {
		return iiValue;
	}

	/**
	 * @param iiValue the iiValue to set
	 */
	public void setIiValue(String iiValue) {
		this.iiValue = iiValue;
	}
	
	public void reset() {
		this.text ="";
		this.newText = "";
		this.iiValue = "test";
	}
	
}
