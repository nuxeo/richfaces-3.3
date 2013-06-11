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

package org.richfaces.event;

import javax.faces.component.UIComponent;

/**
 * Base interface holding attributes that can lately
 * be applied on {@link UIComponent}
 * @author Maksim Kaszynski
 *
 */
public interface AttributeHolder {

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public abstract void setAttribute(String name, Object value);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public abstract Object getAttribute(String name);

	/**
	 * Copy attributes from event map to component
	 * @param component
	 */
	public abstract void applyAttributes(UIComponent component);

}