/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.PropertyNotFoundException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlForm;
import javax.faces.el.MethodNotFoundException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.HtmlTestUtils;
import org.richfaces.event.DataScrollerEvent;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for Datascroller component.
 */
public class DatascrollerComponentTest extends AbstractAjax4JsfTestCase {
	private static Set<String> javaScripts = new HashSet<String>();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

	static {
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	}

	private UIDatascroller scroller;
	private UIComponent form;
	private UIData data;
	private UIOutput first;
	private UIOutput first_disabled;
	private UIOutput fastrewind;
	private UIOutput fastrewind_disabled;
	private UIOutput previous;
	private UIOutput previous_disabled;
	private UIOutput fastforward;
	private UIOutput fastforward_disabled;
	private UIOutput next;
	private UIOutput next_disabled;
	private UIOutput last;
	private UIOutput last_disabled;
	private UICommand command = null;

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public DatascrollerComponentTest(String testName) {
		super(testName);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();

		form = new HtmlForm();
		form.setId("form");        
		facesContext.getViewRoot().getChildren().add(form);
		ArrayList dat = new ArrayList();
		for (int i=0;i<20;i++){
			dat.add(new Integer(i));
		}        
		data = (UIData) application.createComponent(HtmlDataTable.COMPONENT_TYPE);
		data.setValue(dat);
		data.setId("data");
		data.setRows(5);        
		form.getChildren().add(data);

		scroller = (UIDatascroller) application.createComponent("org.richfaces.Datascroller");
		scroller.setId("dataScroller");
		scroller.setFor(data.getId());

		first = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		first.setId("first");
		first.setValue("first");
		first_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		first_disabled.setId("first_disabled");
		first_disabled.setValue("first_disabled");
		
		fastrewind = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		fastrewind.setId("fastrewind");
		fastrewind.setValue("fastrewind");

		fastrewind_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		fastrewind_disabled.setId("fastrewind_disabled");
		fastrewind_disabled.setValue("fastrewind_disabled");
		
		previous = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		previous.setId("previous");
		previous.setValue("previous");

		previous_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		previous_disabled.setId("previous_disabled");
		previous_disabled.setValue("previous_disabled");
		
		fastforward = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		fastforward.setId("fastforward");
		fastforward.setValue("fastforward");

		fastforward_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		fastforward_disabled.setId("fastforward_disabled");
		fastforward_disabled.setValue("fastforward_disabled");
		
		next = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		next.setId("next");
		next.setValue("next");

		next_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		next_disabled.setId("next_disabled");
		next_disabled.setValue("next_disabled");
		
		last = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		last.setId("last");
		last.setValue("last");

		last_disabled = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
		last_disabled.setId("last_disabled");
		last_disabled.setValue("last_disabled");
		
		scroller.getFacets().put(first.getId(), first);		
		scroller.getFacets().put(fastrewind.getId(), fastrewind);
		scroller.getFacets().put(previous.getId(), previous);
		scroller.getFacets().put(fastforward.getId(), fastforward);
		scroller.getFacets().put(next.getId(), next);
		scroller.getFacets().put(last.getId(), last);

		scroller.getFacets().put(first_disabled.getId(), first_disabled);		
		scroller.getFacets().put(fastrewind_disabled.getId(), fastrewind_disabled);
		scroller.getFacets().put(previous_disabled.getId(), previous_disabled);
		scroller.getFacets().put(fastforward_disabled.getId(), fastforward_disabled);
		scroller.getFacets().put(next_disabled.getId(), next_disabled);
		scroller.getFacets().put(last_disabled.getId(), last_disabled);
		
		
		form.getChildren().add(scroller);

		command = new HtmlCommandLink();
		command.setId("command");
		form.getChildren().add(command);

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		scroller = null;
		form = null;
		data = null;
		first = null;
		fastrewind = null;
		previous = null;
		fastforward = null;
		next = null;
		last = null;
	}

	/**
	 * Test component rendering
	 *
	 * @throws Exception
	 */
	public void testRender() throws Exception {
		HtmlPage page = renderView();        
		assertNotNull(page);
		//System.out.println(page.asXml());

		HtmlElement div = page.getHtmlElementById(scroller.getClientId(facesContext));
		assertNotNull(div);
		assertEquals("div", div.getNodeName());

		HtmlElement f1_d = page.getHtmlElementById(first_disabled.getClientId(facesContext));
		assertNotNull(f1_d);		
		HtmlElement f2 = page.getHtmlElementById(last.getClientId(facesContext));
		assertNotNull(f2);
		HtmlElement f3 = page.getHtmlElementById(fastforward.getClientId(facesContext));
		assertNotNull(f3);
		HtmlElement f4_d = page.getHtmlElementById(fastrewind_disabled.getClientId(facesContext));
		assertNotNull(f4_d);		
		HtmlElement f5 = page.getHtmlElementById(next.getClientId(facesContext));
		assertNotNull(f5);
		HtmlElement f6_d = page.getHtmlElementById(previous_disabled.getClientId(facesContext));
		assertNotNull(f6_d);

		String classAttr = div.getAttributeValue("class");
		assertTrue(classAttr.contains("dr-dscr"));
		assertTrue(classAttr.contains("rich-datascr"));


		scroller.processDecodes(facesContext);
		scroller.processValidators(facesContext);
		scroller.processUpdates(facesContext);
		
		scroller.setFor("xxx");
		
		try {
			page = renderView();
			assertTrue(false);
		}catch(FacesException ex) {
		}catch(IllegalArgumentException ex){
		}
		
		scroller.setFor("form");
		
		try {
			page = renderView();
			assertTrue(false);
		}catch(FacesException ex) {
		}catch(IllegalArgumentException ex){
		}
		
		scroller.setFor(data.getId());
		scroller.processDecodes(facesContext);
		scroller.processValidators(facesContext);
		scroller.processUpdates(facesContext);
	}

	public void testNotRender() throws Exception {
		data.setRows(25);
		scroller.setRenderIfSinglePage(false);

		HtmlPage page = renderView();
		
		assertNotNull(page);
		System.out.println(page.asXml());
		try {
			page.getHtmlElementById(scroller.getClientId(facesContext)+"_table");
			assertTrue(false);
		} catch(Exception ex) {
		}

	}

	public void testNotFor() throws Exception {
		scroller.setFor(null);        
		try {
			HtmlPage page = renderView();
			assertTrue(false);
		} catch(Exception ex) {
		}

	}


	/**
	 * Test style rendering
	 *
	 * @throws Exception
	 */
	public void testRenderStyle() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");

		assertEquals(1, links.size());
		HtmlElement link = (HtmlElement) links.get(0);
		assertTrue(link.getAttributeValue("href").contains("css/datascroller.xcss"));
	}

	/**
	 * Test script rendering
	 *
	 * @throws Exception
	 */
	public void testRenderScript() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
	}

	/**
	 * Test binary search
	 *
	 * @throws Exception
	 */
	public void testBinarySearch() throws Exception {
		class MockData extends UIData {
			private int rowIndex;
			private int minRow;
			private int maxRow;

			public int getMaxRow() {
				return maxRow;
			}

			public void setMaxRow(int maxRow) {
				this.maxRow = maxRow;
			}

			public int getMinRow() {
				return minRow;
			}

			public void setMinRow(int minRow) {
				this.minRow = minRow;
			}

			public boolean isRowAvailable() {
				return rowIndex >= getMinRow() && rowIndex < getMaxRow();
			}

			public void setRowIndex(int rowIndex) {
				this.rowIndex = rowIndex;
			}
		}

		MockData data;

		data = new MockData();
		data.setMaxRow(1);
		assertEquals(1, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(2);
		assertEquals(2, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(3);
		assertEquals(3, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(4);
		assertEquals(4, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(5);
		assertEquals(5, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(6);
		assertEquals(6, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(7);
		assertEquals(7, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(8);
		assertEquals(8, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(9);
		assertEquals(9, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(10);
		assertEquals(10, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(11);
		assertEquals(11, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(12);
		assertEquals(12, UIDatascroller.BinarySearch.search(data));

		data = new MockData();
		data.setMaxRow(13);
		assertEquals(13, UIDatascroller.BinarySearch.search(data));
	}

	public void testSetPage() throws Exception {
		assertEquals(4,scroller.getPageCount());
		scroller.setPage("2");
		scroller.setFastStep(2);
		scroller.setupFirstRowValue();
		assertEquals(5, scroller.getFirstRow(data));
		
		scroller.setPage("next");
		scroller.setupFirstRowValue();
		assertEquals(10,scroller.getFirstRow(data));
		
		scroller.setPage("previous");
		scroller.setupFirstRowValue();
		assertEquals(5,scroller.getFirstRow(data));
		
		scroller.setPage("fastforward");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));
		
		scroller.setPage("fastrewind");
		scroller.setupFirstRowValue();
		assertEquals(5,scroller.getFirstRow(data));
		
		scroller.setPage("first");
		scroller.setupFirstRowValue();
		assertEquals(0,scroller.getFirstRow(data));
		
		scroller.setPage("previous");
		scroller.setupFirstRowValue();
		assertEquals(0,scroller.getFirstRow(data));
		
		scroller.setPage("fastrewind");
		scroller.setupFirstRowValue();
		assertEquals(0,scroller.getFirstRow(data));      
		
		scroller.setPage("last");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));  
		
		scroller.setPage("next");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));
		
		scroller.setPage("fastforward");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));        
		
		scroller.setPage("5");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));
		
		scroller.setPage("0");
		scroller.setupFirstRowValue();
		assertEquals(15,scroller.getFirstRow(data));
	}

	public void testListener() throws Exception{
		HtmlPage renderedView = renderView();

		HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
		htmlLink.click();
		
		MethodExpression binding = new MethodExpression(){
			
			public Object invoke(ELContext context, Object[] params) throws PropertyNotFoundException, MethodNotFoundException,
            ELException  {
				facesContext.addMessage(scroller.getClientId(facesContext), new FacesMessage("Method invoked!"));
				return "invoked"; 
			}
			
			public MethodInfo getMethodInfo(ELContext context) {
			
				return null;
			}

			public boolean equals(Object obj) {
				 return (obj instanceof MethodExpression && obj.hashCode() == this.hashCode());
			}

			
			public String getExpressionString() {
				return null;
			}

			public int hashCode() {
				 return 0;
			}

			public boolean isLiteralText() {
				return false;
			}
			
		};
		
//		MethodBinding binding = new MethodBinding(){    
//			public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
//				facesContext.addMessage(scroller.getClientId(facesContext), new FacesMessage("Method invoked!"));
//				return "invoked"; 
//			}
//			public Class getType(FacesContext context) throws MethodNotFoundException {
//				return String.class;
//			}
//		};

		DataScrollerEvent event = new DataScrollerEvent( ((UIComponent) scroller), "1", "2", 2);
		this.scroller.setScrollerListener(binding);
		this.scroller.broadcast(event);

		assertTrue(facesContext.getMessages().hasNext());
	} 


}
