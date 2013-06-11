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

package org.ajax4jsf.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class EntryPointTestCase extends GWTTestCase {

	/*
	 * Test method for 'org.ajax4jsf.gwt.client.ComponentEntryPoint.getProperty(String)'
	 */
	public void testGetProperty() {

	}

	/*
	 * Test method for 'org.ajax4jsf.gwt.client.ComponentEntryPoint.isHaveClass(Element, String)'
	 */
	public void testIsHaveClass() {
		Element element = DOM.createElement("span");
		DOM.setAttribute(element,"className","foo bar baz");
		assertTrue(ComponentEntryPoint.isHaveClass(element,"foo"));
		assertTrue(ComponentEntryPoint.isHaveClass(element,"bar"));
		assertFalse(ComponentEntryPoint.isHaveClass(element,"xxx"));
	}

	/*
	 * Test method for 'org.ajax4jsf.gwt.client.ComponentEntryPoint.createFacesService(String)'
	 */
	public void testCreateFacesService() {

	}

	public String getModuleName() {
		// TODO Auto-generated method stub
		return "org.ajax4jsf.gwt.TestCase";
	}

}
