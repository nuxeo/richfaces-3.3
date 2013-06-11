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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * @author asmirnov
 *
 */
public class ValidableBean {
	
	@Min(2)
	@Max(5)
	private int integerProperty;
	
	private String text;
	
	private Object foo;
	
	@NotEmpty
	private List<String> list = new ArrayList<String>();
	
	private String[] array = new String[1];
	
	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * @return the array
	 */
	@NotEmpty
	@Length(min=2,max=5)
	public String[] getArray() {
		return array;
	}

	/**
	 * @param array the array to set
	 */
	public void setArray(String[] array) {
		this.array = array;
	}

	/**
	 * @return the map
	 */
	@Length(min=2,max=5)
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return the integerProperty
	 */
	public int getIntegerProperty() {
		return integerProperty;
	}

	/**
	 * @param integerProperty the integerProperty to set
	 */
	@Min(2)
	@Max(5)
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
	@Length(max=10,min=1,message="text size")
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
	@NotNull
	public void setFoo(Object foo) {
		this.foo = foo;
	}

}
