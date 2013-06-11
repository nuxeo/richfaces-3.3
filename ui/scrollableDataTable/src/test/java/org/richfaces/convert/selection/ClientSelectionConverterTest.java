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
package org.richfaces.convert.selection;

import javax.faces.convert.Converter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.model.selection.ClientSelection;
import org.richfaces.model.selection.SelectionRange;

/**
 * @author Maksim Kaszynski
 *
 */
public class ClientSelectionConverterTest extends AbstractAjax4JsfTestCase {

	private Converter converter;

	/**
	 * @param name
	 */
	public ClientSelectionConverterTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		converter = application.createConverter(ClientSelection.class);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		converter = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.richfaces.convert.selection.ClientSelectionConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)}.
	 */
	public void testGetAsObject() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.richfaces.convert.selection.ClientSelectionConverter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)}.
	 */
	public void testGetAsString() {
		
		ClientSelection clientSelection = new ClientSelection();
		clientSelection.addRange(new SelectionRange(2,3));
		clientSelection.addRange(new SelectionRange(5,22));
		
		String value = converter.getAsString(facesContext, null, clientSelection);
		
		assertNotNull(value);
		assertTrue(value.length() > 0);
		assertEquals("2,3;5,22;", value);
		
		
	}

}
