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
package org.richfaces.component.html;

import javax.faces.context.FacesContext;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import junit.framework.TestCase;

public class HtmlPanelTest extends AbstractAjax4JsfTestCase {

	public HtmlPanelTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSaveStateFacesContext() {
		HtmlPanel panel = new HtmlPanel();
		panel.setBodyClass("panelBodyClass");
		panel.setHeaderClass("headerClass");
		Object state = panel.saveState(facesContext);
		HtmlPanel newPanel = new HtmlPanel();
		newPanel.restoreState(facesContext, state);
		assertEquals("panelBodyClass", newPanel.getBodyClass());
		assertEquals("headerClass", newPanel.getHeaderClass());
	}

}
