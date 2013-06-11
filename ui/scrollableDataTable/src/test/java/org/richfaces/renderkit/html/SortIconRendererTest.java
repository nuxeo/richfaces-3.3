/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Maksim Kaszynski
 * 
 */
public class SortIconRendererTest extends AbstractAjax4JsfTestCase {

	private SortIconRenderer renderer;
	
	/**
	 * @param name
	 */
	public SortIconRendererTest(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		renderer = new SortIconRenderer() {

			protected Class getComponentClass() {
				return UIComponent.class;
			}
		};
		
		
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		renderer = null;
	}

	/**
	 * Test method for
	 * {@link org.richfaces.renderkit.html.SortIconRenderer#renderAscIcon(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}.
	 */
	public final void testRenderAscIcon() {
		UIComponent component = new UIColumn();
		try {
			setupResponseWriter();
			renderer.renderAscIcon(facesContext, component);
			HtmlPage page = processResponseWriter();
			
			Iterator elementIterator = page.getAllHtmlChildElements();
			
			HtmlElement div = null;
			
			while(elementIterator.hasNext()) {
				HtmlElement node = (HtmlElement) elementIterator.next();
				if (node.getNodeName().equalsIgnoreCase("div")) {
					div = node;
				}
			}
			
			assertNotNull(div);
			
			String className = div.getAttributeValue("class");
			
			assertNotNull(className);
			
			assertEquals("dr-sdt-sort-asc", className);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}

	
	public void testRenderFacet() {
		UIComponent component = new UIColumn();
		UIOutput output = new UIOutput() {
			public String getRendererType() {
				return null;
			}
			public void encodeEnd(FacesContext context) throws IOException {
				ResponseWriter writer = context.getResponseWriter();
				writer.startElement("div", this);
				writer.writeAttribute("id", "test_div_0", "id");
				writer.endElement("div");
			}
		};
		
		component.getFacets().put("ascIcon", output);
		
		
		try {
			setupResponseWriter();
			renderer.renderAscIcon(facesContext, component);
			HtmlPage htmlPage = processResponseWriter();
			
			HtmlElement element = htmlPage.getHtmlElementById("test_div_0");
			assertNotNull(element);
			assertEquals("div", element.getNodeName());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		
	}
	
	/**
	 * Test method for
	 * {@link org.richfaces.renderkit.html.SortIconRenderer#renderDescIcon(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}.
	 */
	public final void testRenderDescIcon() {
		UIComponent component = new UIColumn();
		try {
			setupResponseWriter();
			renderer.renderDescIcon(facesContext, component);
			HtmlPage page = processResponseWriter();
			
			Iterator elementIterator = page.getAllHtmlChildElements();
			
			HtmlElement div = null;
			
			while(elementIterator.hasNext()) {
				HtmlElement node = (HtmlElement) elementIterator.next();
				if (node.getNodeName().equalsIgnoreCase("div")) {
					div = node;
				}
			}
			
			assertNotNull(div);
			
			String className = div.getAttributeValue("class");
			
			assertNotNull(className);
			
			assertEquals("dr-sdt-sort-desc", className);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
