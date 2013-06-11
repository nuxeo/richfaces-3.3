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

package org.ajax4jsf.gwt.jsf;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * @author shura
 *
 */
public class GwtActionEvent extends ActionEvent {

	private Object _value;
	/**
	 * @param component
	 */
	public GwtActionEvent(UIComponent component, Object value) {
		super(component);
		_value = value;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return _value;
	}

}
