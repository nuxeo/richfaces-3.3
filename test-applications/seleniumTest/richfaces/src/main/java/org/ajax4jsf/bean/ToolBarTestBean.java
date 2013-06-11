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

public class ToolBarTestBean {

    private String groupSeparator = "";

    private String groupItemSeparator = "";
    
    private String location = "left";
    
    private boolean toolBarGroupRendered = true;
    
    public void reset(){
    	toolBarGroupRendered = true;
    	groupSeparator = "";
    	groupItemSeparator = "";
    	location = "left";
    }
    

    /**
     * Gets value of groupSeparator field.
     * 
     * @return value of groupSeparator field
     */
    public String getGroupSeparator() {
        return groupSeparator;
    }

    /**
     * Set a new value for groupSeparator field.
     * 
     * @param groupSeparator
     *                a new value for groupSeparator field
     */
    public void setGroupSeparator(String groupSeparator) {
        this.groupSeparator = groupSeparator;
    }

    /**
     * Gets value of groupItemSeparator field.
     * 
     * @return value of groupItemSeparator field
     */
    public String getGroupItemSeparator() {
        return groupItemSeparator;
    }

    /**
     * Set a new value for groupItemSeparator field.
     * 
     * @param groupItemSeparator
     *                a new value for groupItemSeparator field
     */
    public void setGroupItemSeparator(String groupItemSeparator) {
        this.groupItemSeparator = groupItemSeparator;
    }


	/**
	 * @return the toolBarGroupRendered
	 */
	public boolean isToolBarGroupRendered() {
		return toolBarGroupRendered;
	}


	/**
	 * @param toolBarGroupRendered the toolBarGroupRendered to set
	 */
	public void setToolBarGroupRendered(boolean toolBarGroupRendered) {
		this.toolBarGroupRendered = toolBarGroupRendered;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
