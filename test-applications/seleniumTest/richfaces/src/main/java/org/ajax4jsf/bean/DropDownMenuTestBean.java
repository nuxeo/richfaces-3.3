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

public class DropDownMenuTestBean {

    private String actionName = "";
    
    private boolean immediate;

    /**
     * Gets value of actionName field.
     * @return value of actionName field
     */
    public String getActionName() {
        return actionName;
    }
    
    public void reset() {
    	actionName = "";
    	immediate = false;
    }
    
    public void initImmediateTest() {
    	reset();
    	immediate = true;
    }

    /**
     * Set a new value for actionName field.
     * @param actionName a new value for actionName field
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String doNew() {
        setActionName("New");
        return null;
    }

    public String doOpen() {
        setActionName("Open");
        return null;
    }

    public String doSave() {
        setActionName("Save");
        return null;
    }

    public String doSaveAll() {
        setActionName("Save All");
        return null;
    }

    public String doClose() {
        setActionName("Close");
        return null;
    }

    public String doExit() {
        setActionName("Exit");
        return null;
    }

	/**
	 * @return the immediate
	 */
	public boolean getImmediate() {
		return immediate;
	}

	/**
	 * @param immediate the immediate to set
	 */
	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	/**
	 * @return the input
	 */
	public String getInput() {
		return "Text";
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		
	}
    
    

}
