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
package org.richfaces.validator;


/**
 * @author asmirnov
 *
 */
public class UnValidableBean {
	
	private int integerProperty;
	
	private String text;
	
	private Object foo;

	/**
	 * @return the integerProperty
	 */
	public int getIntegerProperty() {
		return integerProperty;
	}

	/**
	 * @param integerProperty the integerProperty to set
	 */
	public void setIntegerProperty(int integerProperty) {
		this.integerProperty = integerProperty;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the foo
	 */
	public Object getFoo() {
		return foo;
	}

	/**
	 * @param foo the foo to set
	 */
	public void setFoo(Object foo) {
		this.foo = foo;
	}

}
