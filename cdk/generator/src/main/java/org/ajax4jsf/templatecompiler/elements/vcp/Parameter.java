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

package org.ajax4jsf.templatecompiler.elements.vcp;

/**
 * container class for storing information about f:parameter-tags.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.1 $ $Date: 2006/12/20 18:56:26 $
 */
public class Parameter {
	private String value;

	private Class type;

	/**
	 * 
	 * @param value
	 * @param type
	 */
	public Parameter(final String value) {
		this(value, String.class);
	}

	/**
	 * 
	 * @param value
	 * @param type
	 */
	public Parameter(final String value, final Class type) {
		this.value = value;
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public Class getType() {

		return this.type;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	public String toString() {
		return "\n\tvalue: " + this.value + "\n\ttype: " + this.type;
	}
}
