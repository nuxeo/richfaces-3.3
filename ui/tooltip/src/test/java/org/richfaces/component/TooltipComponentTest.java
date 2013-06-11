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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * @author Alexej Kushunin - mailto:akushunini@exadel.com created 09.08.2007
 */

public class TooltipComponentTest extends AbstractAjax4JsfTestCase {

	private UIComponent form;

	private UIToolTip tooltip1;
	private UIToolTip tooltip2;
	private static Set<String> javaScripts = new HashSet<String>();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

	static {

		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/jquery/jquery.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/tooltip.js");
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	}

	public TooltipComponentTest(String testName) {
		super(testName);
	}

	public void setUp() throws Exception {

		super.setUp();

		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
		tooltip1 = (UIToolTip) application.createComponent(UIToolTip.COMPONENT_TYPE);
		tooltip2 = (UIToolTip) application.createComponent(UIToolTip.COMPONENT_TYPE);
		tooltip1.setId("tooltip1");
		tooltip1.setLayout("block");
		form.getChildren().add(tooltip1);
		tooltip2.setId("tooltip2");
		tooltip2.setLayout("inline");
		form.getChildren().add(tooltip2);

	}

	public void testRender() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
	}

	public void testToolTipStyles() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List<?> links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");
		if(links.size()==0){fail();}
		for (int i = 0; i < links.size(); i++) {
			HtmlElement link = (HtmlElement) links.get(i);
			assertTrue(link.getAttributeValue("href").contains("css/tooltip.xcss"));
		}
	}

	public void testToolTipScrits() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);

		List<?> scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName("script");
		for (Iterator<?> it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			String srcAttr = item.getSrcAttribute();

			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator<String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();
					found = srcAttr.contains(src);
					if (found) {
						break;
					}
				}
				assertTrue(found);
			}
		}
		
		assertEquals(javaScripts.size(), getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue());
	}
	public void testBroadcast() throws Exception{
		FacesEvent fe = new AjaxEvent(tooltip1); 
		try{
		tooltip1.broadcast(fe);
		}catch(Exception e){fail();}
	}
	
	public void testComponent() {
		assertTrue(true);
	}

	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		form = null;
		tooltip1 = null;
		tooltip2 = null;
		super.tearDown();
	}
}
