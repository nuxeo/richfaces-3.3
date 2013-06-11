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

package org.ajax4jsf.builder.config;

/**
 * Simple holder for test class name and test superclass name 
 * @author dbiatenia
 *
 */
public class TestClassHolder {

	private String _classname;
	
	private String _superclassname = "org.ajax4jsf.tests.AbstractAjax4JsfTestCase";

	public String getClassname() {
		return _classname;
	}

	public void setClassname(String classname) {
		this._classname = classname;
	}

	public String getSuperclassname() {
		return _superclassname;
	}

	public void setSuperclassname(String superclassname) {
		this._superclassname = superclassname;
	}
	
	/**
	 * Convert full class name to simple.
	 * @return class name without package name. 
	 */
	public String getSimpleClassName() {
		int lastPoint = getClassname().lastIndexOf('.');
		if (lastPoint>0) {
			return getClassname().substring(lastPoint+1);
		}
		return getClassname();
	}
	
	
}
