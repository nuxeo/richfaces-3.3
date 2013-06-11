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
package org.richfaces.component;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

public class InplaceInputComponentTest extends AbstractAjax4JsfTestCase {
	
	UIForm form;
	UIInplaceInput iinput;
	
	private static Set <String>javaScripts = new HashSet<String>();

	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("scripts/inplaceinput.js");
		javaScripts.add("scripts/inplaceinputstyles.js");
		javaScripts.add("scripts/utils.js");
		javaScripts.add("scripts/comboboxUtils.js");
		
	}
	
	public InplaceInputComponentTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
	    iinput = (UIInplaceInput)application.createComponent("org.richfaces.InplaceInput");
	    iinput.setValue("New York");
	    form.getChildren().add(iinput);
	}
	
	public void testRenderer() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
	}
	
	public void testComboBoxStyles() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List <HtmlLink> links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");
		if(links.size()==0){
				fail();
		}
		
		for (int i = 0; i < links.size(); i++) {
			HtmlElement link = (HtmlElement) links.get(i);
			assertTrue(link.getAttributeValue("href").contains("css/inplaceinput.xcss"));
		}
	}
	
	public void testComboBoxScripts() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List <HtmlScript> scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName("script");

		for (Iterator <HtmlScript> it = scripts.iterator(); it.hasNext();) {
			
			HtmlScript item =  it.next();
			String srcAttr = item.getSrcAttribute();
			
			if (item.getFirstDomChild() != null) {
				DomNode script = item.getFirstDomChild();
				assertTrue(script.getNodeValue().contains("Richfaces.InplaceInput"));
			}
	
			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator <String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();
					found = srcAttr.contains(src);
					if (found) {
						break;
					}
				}
				assertTrue(found);
			}
		}
	}
		
	public void tearDown() throws Exception {
		super.tearDown();
	}	
}
