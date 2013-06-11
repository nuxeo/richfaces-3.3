/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces;
/**
 * @author $Autor$
 *
 */
public class Bean {
	private int value = 20;
	private boolean disabled = false;
	private String commandButtonCaption = "Disable input number spinner";	
	
	public boolean isDisabled() {
		return disabled;
	}	
	public void setDisabled(boolean value) {
		this.disabled = disabled;
	}
	public void swichDisabled(javax.faces.event.ActionEvent event) {
		if (disabled) {
			disabled = false;
			commandButtonCaption = "Disable input number spinner";
		} else {
			disabled = true;
			commandButtonCaption = "Enable input number spinner";
		}			
	}
	public String getCommandButtonCaption() {
		return commandButtonCaption;
	}
	public void setCommandButtonCaption(String commandButtonCaption) {
		this.commandButtonCaption = commandButtonCaption;
	}
	public int getValue() {
		return value;
	}	
	public void setValue(int value) {
		this.value = value;
	}
}