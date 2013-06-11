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

package org.richfaces.renderkit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.ListDataModel;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIDataTable;
import org.richfaces.model.Ordering;
import org.richfaces.renderkit.html.iconimages.DataTableIconSortAsc;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

public class SortableHeaderRenderingTest extends AbstractAjax4JsfTestCase {
	private static final int ROWS_COUNT = 10;
	
	private static Set<String> javaScripts = new HashSet<String>();
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("org/richfaces/renderkit/html/scripts/data-table.js");
		javaScripts.add("scripts/inplaceinput.js");
		javaScripts.add("scripts/utils.js");
	}

    ListDataModel model;
    
	private UIDataTable dataTable;

    private UIColumn column1;

    private UIColumn column2;

    private UIForm form = null;
    
    private Comparator<Date> comparator;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
	public SortableHeaderRenderingTest(String name) {
		super(name);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		comparator = new SortComparator();
		
		form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        dataTable = (UIDataTable) application
                .createComponent("org.richfaces.DataTable");
        dataTable.setId("dataTable");
        
        List<Date> list = new ArrayList<Date>();
        for (int i = 0; i < ROWS_COUNT; i++) {
            list.add(new Date((long) Math.random()));
        }
        model = new ListDataModel(list);
		dataTable.setValue(model);
        dataTable.setVar("var");

        column1 = (UIColumn) application.createComponent("org.richfaces.Column");
		UIOutput cellElement1 = (UIOutput) createComponent(
		        HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
		cellElement1.setValueExpression("value", new ColumnOneExpression());
		
		column1.getChildren().add(cellElement1);
		column1.setId("column1");
		column1.setSortOrder(Ordering.ASCENDING);
		column1.setValueExpression("comparator", new ComparatorExpression());
		
		UIOutput facet1 = new HtmlOutputText();
		facet1.setValue("sort");
		column1.getFacets().put("header", facet1);
		
        dataTable.getChildren().add(column1);
        
        column2 = (UIColumn) application.createComponent("org.richfaces.Column");
        column2.setId("column2");
		UIOutput cellElement2 = (UIOutput) createComponent(
		        HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
		cellElement2.setValueExpression("value", new ColumnTwoExpression());
		column2.setFilterValue("filterValue");
		column2.setValueExpression("filterBy", new ColumnTwoExpression());
		
		
		column2.getChildren().add(cellElement2);
        dataTable.getChildren().add(column2);
        
        form.getChildren().add(dataTable);
	}
	
	@Override
	public void tearDown() throws Exception {
		model = null;
		form = null;
		column1 = null;
		column2 = null;
		dataTable = null;
		
		super.tearDown();
	}
	
	/**
     * Test sortable header rendering.
     * 
     * @throws Exception
     */
    public void testRenderSortableHeader() throws Exception {
    	HtmlPage page = renderView();
        assertNotNull(page);
        
        List<HtmlElement> headers = page.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.th_ELEM);
        assertNotNull(headers);
        assertEquals(2, headers.size());
        
        HtmlElement th = headers.get(0);
        assertNotNull(th);
        String onclick = th.getAttributeValue(HTML.onclick_ATTRIBUTE);
        assertNotNull(onclick);
        assertTrue(onclick.startsWith("A4J.AJAX.Submit"));
        
        HtmlElement div = (HtmlElement) th.getFirstDomChild();
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getTagName());
        assertNull(div.getNextDomSibling());
        
        HtmlElement span = (HtmlElement) div.getFirstDomChild();
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getTagName());
        assertNull(span.getNextDomSibling());
        
        String clazz = span.getAttributeValue(HTML.class_ATTRIBUTE);
        assertNotNull(clazz);
        //assertTrue(clazz.contains("dr-table-header-sort-up"));
        assertTrue(clazz.contains("dr-table-sortable-header"));
        
        DomText text = (DomText) span.getFirstDomChild();
        assertNotNull(text);
        
        HtmlElement img = (HtmlElement) text.getNextDomSibling();
        assertNotNull(img);
        assertEquals(HTML.IMG_ELEMENT, img.getTagName());
        assertNull(img.getNextDomSibling());
        
        String src = img.getAttributeValue(HTML.src_ATTRIBUTE);
        assertNotNull(src);
        assertTrue(src.contains(DataTableIconSortAsc.class.getName()));
    }
    
    /**
     * Test filtered header rendering.
     * 
     * @throws Exception
     */
    public void testRenderFilteredHeader() throws Exception {
    	 HtmlPage page = renderView();
         assertNotNull(page);
         
         List<HtmlElement> headers = page.getDocumentElement().getHtmlElementsByTagName(HTML.th_ELEM);
         assertNotNull(headers);
         assertEquals(2, headers.size());
         
         HtmlElement th = headers.get(1);
         assertNotNull(th);
         
         HtmlElement div = (HtmlElement) th.getFirstChild();
         assertNotNull(div);
         assertEquals(HTML.DIV_ELEM, div.getTagName());
         assertNull(div.getFirstChild());
         
         div = (HtmlElement) div.getNextSibling();
         assertNotNull(div);
         assertEquals(HTML.DIV_ELEM, div.getTagName());
         assertNull(div.getNextSibling());
         
         List<HtmlElement> spans= div.getHtmlElementsByTagName(HTML.SPAN_ELEM);
         assertNotNull(spans);
         if (1 == spans.size()) { // inplace input is used
	         HtmlElement span = spans.get(0);
	         String clazz = span.getAttributeValue(HTML.class_ATTRIBUTE);
	         assertNotNull(clazz);
	         assertTrue(clazz.contains("rich-inplace-view"));
         } else { // simple inputText
        	 HtmlElement input = (HtmlElement) div.getFirstChild();
        	 assertNotNull(input);
        	 assertEquals(HTML.INPUT_ELEM, input.getTagName());
         }
    }
    
    /**
     * Test filtered data rendering
     * 
     * @throws Exception
     */
    public void testRenderFilteredData() throws Exception {
    	HtmlPage page = renderView();
        assertNotNull(page);
        
        HtmlElement table = page.getHtmlElementById(dataTable.getClientId(facesContext));
        assertNotNull(table);
        
        HtmlElement tbody = table.getHtmlElementById(dataTable.getClientId(facesContext)+ ":tb");
        assertNotNull(tbody);
        assertNull(tbody.getFirstChild());
        
        tearDown();
        setUp();
        
        column2.setFilterValue(null);
        column2.setValueExpression("filterBy", null);
        
        page = renderView();
        assertNotNull(page);
        
        table = page.getHtmlElementById(dataTable.getClientId(facesContext));
        assertNotNull(table);
        
        tbody = table.getHtmlElementById(dataTable.getClientId(facesContext)+ ":tb");
        assertNotNull(tbody);
        assertNotNull(tbody.getFirstChild());
        
        int count = 0;
        Iterator<HtmlElement> it = tbody.getChildElementsIterator();
        while (it.hasNext()) {
        	HtmlElement tr = it.next();
        	count++;
        }
        assertEquals(ROWS_COUNT, count);
    }
    
    public void testScript() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List<HtmlScript> scripts = page.getDocumentElement().getHtmlElementsByTagName(
				"script");
		int foundCount = 0;
		for (Iterator<HtmlScript> it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			String srcAttr = item.getSrcAttribute();

			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator<String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();

					found = srcAttr.contains(src);
					if (found) {
						foundCount++;
						break;
					}
				}

				assertTrue(found);
			}
		}
	}
	
	protected class ColumnOneExpression extends ValueExpression {

		private static final long serialVersionUID = -60617505361080421L;

		@Override
		public Class<?> getExpectedType() {
			return String.class;
		}

		@Override
		public Class<?> getType(ELContext context) {
			return String.class;
		}

		@Override
		public Object getValue(ELContext context) {
			return ((Date)model.getRowData()).getTime();
		}

		@Override
		public boolean isReadOnly(ELContext context) {
			return false;
		}

		@Override
		public void setValue(ELContext context, Object value) {
			
		}

		@Override
		public boolean equals(Object obj) {
			return false;
		}

		@Override
		public String getExpressionString() {
			return null;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public boolean isLiteralText() {
			return false;
		}
	}
	
	protected class ColumnTwoExpression extends ColumnOneExpression {

		private static final long serialVersionUID = -865017340246458449L;
		
		@Override
		public Object getValue(ELContext context) {
			return ((Date)model.getRowData()).getTimezoneOffset();
		}
	}
	
	protected class ComparatorExpression extends ColumnOneExpression {

		private static final long serialVersionUID = -865017340246458449L;
		
		@Override
		public Object getValue(ELContext context) {
			return comparator;
		}
	}
	
	protected class SortComparator implements Comparator<Date> {

		public int compare(Date o1, Date o2) {
			return o1.compareTo(o2);
		}
		
	}

}
