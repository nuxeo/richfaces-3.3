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
package org.richfaces.renderkit.html;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIColumn;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.javascript.AjaxScript;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;
import org.richfaces.component.UIScrollableDataTable;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;
/**
 * @author Anton Belevich
 *
 */
public class ScrollableDataTableRendererTest extends AbstractAjax4JsfTestCase{

	UIScrollableDataTable grid;
	int columns = 7;
	private static Set javaScripts = new HashSet();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
	
	static {
		javaScripts.add(AjaxScript.class.getName());
		javaScripts.add(PrototypeScript.class.getName());
		
		javaScripts.add("org/richfaces/renderkit/html/scripts/common-scrollable-data-table.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/controls-scrollable-data-table.js");
	}
	
	public ScrollableDataTableRendererTest(String arg0) {
		super(arg0);
	}
	
	public void setUp() throws Exception {
		
		super.setUp();

		// create grid 
		grid = (UIScrollableDataTable)application.createComponent("org.richfaces.component.ScrollableDataTable");
		grid.setId("grid");
		grid.getAttributes().put("frozenColCount", new Integer(3));
	
		// add columns
		for (int i = 0; i < columns; i++) {
			
			UIColumn column = (UIColumn) createComponent(
					"org.richfaces.Column",
					"org.richfaces.component.Column",
						null, null, null);
			
			
			UIOutput outputText = (UIOutput) createComponent(
	                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
	                null, null, null);
			
			column.getFacets().put("header", outputText);
			column.getFacets().put("footer", outputText);
			column.getChildren().add(outputText);
			
			grid.getChildren().add(column);
		}

		grid.setFirst(0);
		grid.setRows(40);
		
		
		
		facesContext.getViewRoot().getChildren().add(grid);
		
	}
	 
	public void tearDown() throws Exception {
		super.tearDown();
		grid = null;
	} 
	
	public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertNotNull(links);
        HtmlElement link = (HtmlElement)links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/scrollable-data-table.xcss"));
    }
	
	public void testRenderScripts() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
	}

	static final Set tagNames = new HashSet();
	static final String [] names = {"input","div","script", "table", "tbody", "tr", "td", "thead", "tfoot", "th"};
	static {
		Collections.addAll(tagNames, names);
	}
	
	public void testRenderingFrozenNormalColumns()throws Exception{
		
		HtmlPage page = renderView();
		assertNotNull(page);
		
		
		HtmlElement div = page.getHtmlElementById(grid.getClientId(facesContext));
		assertNotNull(div);
		assertEquals("table", div.getNodeName());
		
		String classAttr = div.getAttributeValue("class");
	    assertTrue(classAttr.contains("dr-sdt"));
	    Iterator childIter= div.getChildElementsIterator();
		
	    String id = grid.getId();
		
	    HtmlElement input = page.getHtmlElementById(id+"_hc");
	    assertNotNull(input);
	    input = null;
	    input = page.getHtmlElementById(id + "_state_input");
	    assertNotNull(input);
	    input = null;
	    input = page.getHtmlElementById(id + "_options_input");
	    assertNotNull(input);
	    input = null;
	    input = page.getHtmlElementById(id + "_rows_input");
	    assertNotNull(input);
	    input = null;
	    input = page.getHtmlElementById(id + "_submit_input");
	    assertNotNull(input);
	    input = null;
	    
		for (; childIter.hasNext();) {
			
			HtmlElement elem = (HtmlElement) childIter.next();
			assertNotNull(elem);
			
			boolean res = tagNames.contains(elem.getNodeName().toLowerCase());
			assertTrue(res);
			
			if(elem.getNodeName().equals("div")){
			
				String elemClassAttr = elem.getAttributeValue("class");
				res = false;
				if(elemClassAttr.contains("dr-sdt-inlinebox")){
					res = true;
				}else if(elemClassAttr.contains("dr-sdt-hsplit")){
					res = true;
				}
				assertTrue(res);
				
				if(!elemClassAttr.contains("dr-sdt-hsplit")){
					
					boolean templates = elem.getId().equals(grid.getId()+ "_GridBodyTemplate") || elem.getId().equals(grid.getId()+ "_GridFooterTemplate") || elem.getId().equals(grid.getId()+ "_GridHeaderTemplate");
					assertTrue(templates);
								
					Iterator divIter = elem.getChildElementsIterator();
					HtmlElement inDiv = (HtmlElement) divIter.next();
					assertNotNull(inDiv);
					boolean test = false;
					if(inDiv.getNodeName().equals("div")){
						test = true;
					}else if(inDiv.getNodeName().equals("iframe")){
						test = true;
					}
					
					assertTrue(test);
				
					Iterator spanIter  = inDiv.getChildElementsIterator();

					for (;spanIter.hasNext();) {
				
						HtmlElement element = (HtmlElement) spanIter.next();
						assertNotNull(element);
						res = false;
						if(element.getNodeName().equals("span")){
							res = true;
							
						}else if(element.getNodeName().equals("div")){
							 String divId = element.getAttributeValue("id");
							 assertEquals(id + ":sb", divId);
							 res = true;
						} else if (element.getNodeName().equals("br")) {
							res = true;
						}
						
						if(!res) {
							System.out
									.println("ScrollableDataTableRendererTest.testRenderingFrozenNormalColumns()");
						}
						
						assertTrue(res);
						elemClassAttr = element.getAttributeValue("class");
						assertNotNull(elemClassAttr);
					
						if(element.getId().contains("FrozenBox")){
							assertTrue(elemClassAttr.contains("dr-sdt-tmplbox dr-sdt-fb"));
						}else if(element.getId().contains("NormalBox")){
							assertTrue(elemClassAttr.contains("dr-sdt-tmplbox dr-sdt-nb"));
						}
					
				
						for (int i = 0; i < columns; i++) {
						
							HtmlElement hcell = page.getHtmlElementById(id + ":hc_" + i);
							assertNotNull(hcell);
							elemClassAttr = hcell.getAttributeValue("class");
							assertTrue(elemClassAttr.contains("dr-sdt-hc rich-sdt-header-cell"));
						
							for (int j = 0; j < grid.getRows(); j++) {
								HtmlElement bcell = page.getHtmlElementById(id + ":c_" + j + "_" + i);
								assertNotNull(bcell);
								elemClassAttr = bcell.getAttributeValue("class");
								assertTrue(elemClassAttr.contains("dr-sdt-bc rich-sdt-column-cell"));
							
							}
						}
					}
				}
			}
		}
	}
}
