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
package org.richfaces.rendekit;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIInplaceInput;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class InplaceInputRendererTest extends AbstractAjax4JsfTestCase{
	
	private UIInplaceInput iinput;

	
	public InplaceInputRendererTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
	    iinput = (UIInplaceInput)application.createComponent("org.richfaces.InplaceInput");
	    iinput.setValue("New York");
	    facesContext.getViewRoot().getChildren().add(iinput);
	}
	
	public void testRender() throws Exception{
		
		HtmlPage page = renderView();
		assertNotNull(page);
		HtmlElement elem = page.getHtmlElementById(iinput.getClientId(facesContext));
		assertNotNull(elem);
		assertEquals(elem.getTagName(), "span");
	}
}
