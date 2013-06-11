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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.bean.InplaceInputTestBean.Messages;

public class InplaceSelectTestBean {

    private List<SelectItem> treeItems = new ArrayList<SelectItem>();

    private List<String> treeNames = Arrays.asList("Pine", "Birch", "Aspen", "Spruce", "Oak", "Maple", "Ash", "Lime", "test", "999");

    private String tree = "";
    
    private String isValue = "test";
    
    private String isNumberValue = "999";
    
    private String newValue;

    /**
     * Gets value of tree field.
     * 
     * @return value of tree field
     */
    public String getTree() {
        return tree;
    }

    /**
     * Set a new value for tree field.
     * 
     * @param tree
     *                a new value for tree field
     */
    public void setTree(String tree) {
        this.tree = tree;
    }

    public InplaceSelectTestBean() {
        for (String treeName : treeNames) {
            treeItems.add(new SelectItem(treeName, treeName));
        }
    }

    /**
     * Gets value of treeItems field.
     * 
     * @return value of treeItems field
     */
    public List<SelectItem> getTreeItems() {
        return treeItems;
    }
    
    public void validator(FacesContext arg0, UIComponent arg1, Object arg2) {
    	if (!"test".equals(arg2)) {
			throw new ValidatorException(new FacesMessage("Value isn't correct!"));
		}
    }
    
    public void valueChangeListener(ValueChangeEvent ve) {
    	setNewValue(Messages.VALUECHANGELISTENER_CALLED);
    }
    
    public void action() {
    	setIsValue(InplaceInputTestBean.Messages.ACTION_CALLED);
    }

	/**
	 * @return the isValue
	 */
	public String getIsValue() {
		return isValue;
	}

	/**
	 * @param isValue the isValue to set
	 */
	public void setIsValue(String isValue) {
		this.isValue = isValue;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the isNumberValue
	 */
	public String getIsNumberValue() {
		return isNumberValue;
	}

	/**
	 * @param isNumberValue the isNumberValue to set
	 */
	public void setIsNumberValue(String isNumberValue) {
		this.isNumberValue = isNumberValue;
	}
    
	public void reset() {
	
		this.treeItems = new ArrayList<SelectItem>();

		this.treeNames = Arrays.asList("Pine", "Birch", "Aspen", "Spruce", "Oak", "Maple", "Ash", "Lime", "test", "999");

		this.tree = "";
		    
		this.isValue = "test";
		    
		this.isNumberValue = "999";
		    
		this.newValue = null;
		
		for (String treeName : treeNames) {
			treeItems.add(new SelectItem(treeName, treeName));
		}
	}
}
