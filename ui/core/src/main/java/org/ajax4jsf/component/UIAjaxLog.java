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

package org.ajax4jsf.component;

import javax.faces.component.UIComponentBase;

/**
 * @author shura
 *
 */
public abstract class UIAjaxLog extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.ajax4jsf.Log";
	
	public abstract String getHotkey();
	
	public abstract void setHotkey(String newvalue);

	public abstract String getName();
	public abstract void setName(String newvalue);

	public abstract String getWidth();
	public abstract void setWidth(String newvalue);

	public abstract String getHeight();
	public abstract void setHeight(String newvalue);
	
	public abstract String getLevel();
	public abstract void setLevel(String newvalue);
	
	public abstract boolean isPopup();
	public abstract void setPopup(boolean popup);
}
