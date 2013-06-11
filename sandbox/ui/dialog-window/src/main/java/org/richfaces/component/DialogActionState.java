/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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
package org.richfaces.component;

import java.io.Serializable;

/**
 * 
 * @author glory
 *
 */

public class DialogActionState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String SERVER_MODE = "server";
	public static final String AJAX_MODE = "ajax";

    private boolean immediate = false;
    private boolean immediateSet = false;
    private String mode = AJAX_MODE;
    
    public DialogActionState() {}

    public boolean isImmediate() {
    	return immediate;
    }

    public boolean isImmediateSet() {
    	return immediateSet;
    }

    public void setImmediate(boolean immediate) {
    	if (immediate != this.immediate) {
    		this.immediate = immediate;
    	}
    	this.immediateSet = true;
    }

	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public boolean isServerMode() {
		return SERVER_MODE.equals(mode);
	}

}
