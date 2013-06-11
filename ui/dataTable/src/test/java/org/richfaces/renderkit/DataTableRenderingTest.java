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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.ListDataModel;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIColumnGroup;
import org.richfaces.component.UIDataTable;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DataTableRenderingTest extends AbstractAjax4JsfTestCase {

	private UIDataTable dataTable;

    private UIColumn column1;

    private UIColumn column2;

    private UIForm form = null;
    
    private UIColumnGroup columnGroup;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
	public DataTableRenderingTest(String name) {
		super(name);
	}
	
	/*
     * <form>
     *     <dataTable id="dataTable" value="List<Date>">
     *         <columnGroup>
     *             <column>
     *                 <outputText column="" />
     *             <column>
     *             <column>
     *                 <outputLink value="" />
     *             <column>
     *         </columnGroup>
     *         <column>
     *             <output value="value" />
     *         </column>
     *     </dataTable>
     * </form>
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        
        form = new HtmlForm();
        form.setId("form");
        form.getChildren().add(getDataTable());

        facesContext.getViewRoot().getChildren().add(form);
    }

    /*
     * <dataTable id="dataTable" value="List<Date>">
     *     <columnGroup>
     *        ...
     *     </columnGroup>
     *     <column>
     *        <output value="value" />
     *     </column>
     * </dataTable>
     * 
     * */
    private UIDataTable getDataTable() {
        dataTable = (UIDataTable) application.createComponent("org.richfaces.DataTable");
        dataTable.setId("dataTable");

        List<Date> list = new ArrayList<Date>();
        for (int i = 1; i <= 5; i++) {
            list.add(new Date((long) Math.random()));
        }
        dataTable.setValue(new ListDataModel(list));

        dataTable.getChildren().add(getColumnGroup());
        dataTable.getChildren().add(getColumn3());
        
        return dataTable;
    }

    private javax.faces.component.UIColumn getColumn3() {
        javax.faces.component.UIColumn column3 = new javax.faces.component.UIColumn();
        column3.setId("column3");
        UIOutput cellElement3 = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        cellElement3.setValue("value");
        column3.getChildren().add(cellElement3);
        return column3;
    }

    /*
     * <columnGroup>
     *     <column>
     *         <outputText column="" />
     *     <column>
     *     <column>
     *         <outputLink value="" />
     *     <column>
     * </columnGroup>
     * 
     * */
    private UIColumnGroup getColumnGroup() {
        columnGroup = (UIColumnGroup) application.createComponent("org.richfaces.ColumnGroup");
        columnGroup.setId("columnGroup");
        
        column1 = (UIColumn) application.createComponent("org.richfaces.Column");
        column1.setId("column1");
        UIOutput cellElement1 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        cellElement1.setValueExpression("column", new ColumnValueExpression());
        column1.getChildren().add(cellElement1);
        columnGroup.getChildren().add(column1);

        column2 = (UIColumn) application.createComponent("org.richfaces.Column");
        column2.setId("column2");
        UIOutput cellElement2 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputLink.class.getName(),null, null, null);
        cellElement2.setValueExpression("value", new ColumnValueExpression());
        column2.getChildren().add(cellElement2);
        columnGroup.getChildren().add(column2);
        
        return columnGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();

        column1 = null;
        column2 = null;
        columnGroup = null;
        dataTable = null;
    }
    
    /**
     * Test DataTable component rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataTable() throws Exception {
        // setUp
        final Map<String, Object> attributes = dataTable.getAttributes();
        attributes.put("columnsWidth", "400px,200px");
        attributes.put("onRowMouseOver", "onRowMouseOver");
        
        UIColumn column3 = (UIColumn) application.createComponent("org.richfaces.Column");
        column3.setId("local_column3");
        dataTable.getChildren().add(column3);
        UIOutput text = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        text.setValue("Column");
        column3.getChildren().add(text);
        column3.setBreakBefore(true);

        UIColumn column4 = (UIColumn) application.createComponent("org.richfaces.Column");
        column4.setId("local_column4");
        dataTable.getChildren().add(column4);
        UIOutput text2 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        text.setValue("Column2");
        column4.getChildren().add(text2);
        column4.setRendered(false);

        // test
        HtmlPage page = renderView();
//        System.out.println(page.asXml());
        
        assertNotNull(page);

        HtmlElement table = page.getHtmlElementById(dataTable.getClientId(facesContext));
        assertNotNull(table);
        assertEquals("table", table.getNodeName());
        String classAttr = table.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table rich-table"));

        List<?> elements = table.getHtmlElementsByTagName("col");
        assertEquals(2, elements.size());
        classAttr = ((HtmlElement) elements.get(0)).getAttributeValue("width");
        assertTrue(classAttr.contains("400px"));
        classAttr = ((HtmlElement) elements.get(1)).getAttributeValue("width");
        assertTrue(classAttr.contains("200px"));

        List<?> bodies = table.getHtmlElementsByTagName("tbody");
        assertEquals(1, bodies.size());
        List<?> trs = ((HtmlElement) bodies.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        HtmlElement tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-firstrow rich-table-firstrow"));
        classAttr = tr.getAttributeValue("onmouseover");
        assertTrue(classAttr.contains("onRowMouseOver"));
        for (int i = 1; i < trs.size(); i++) {
            tr = (HtmlElement) trs.get(i);
            assertNotNull(tr);

            classAttr = tr.getAttributeValue("onmouseover");
            assertTrue("Row i = " + i + " must have onmouseover attribute",
                    classAttr.contains("onRowMouseOver"));

            classAttr = tr.getAttributeValue("class");
            assertFalse("Row i = " + i + " have 'dr-table-firstrow' style",
                    classAttr.contains("dr-table-firstrow"));
            assertFalse("Row i = " + i + " have 'rich-table-firstrow' style", 
                    classAttr.contains("rich-table-firstrow"));
        }

        Iterator<?> tds = tr.getChildIterator();
        assertTrue(tds.hasNext());
        HtmlElement td = (HtmlElement) tds.next();
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-cell rich-table-cell"));
    }

    /**
     * Test DataTable component facets rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataTableFacets() throws Exception {
        
        UIColumnGroup header1 = (UIColumnGroup) application.createComponent("org.richfaces.ColumnGroup");
        header1.getAttributes().put("columnClasses", "cola, colb");
        dataTable.getFacets().put("header", header1);

        UIColumn headerColumn1 = (UIColumn) application.createComponent("org.richfaces.Column");
        UIOutput headerText1 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        headerText1.setValue("Header Column1");
        headerColumn1.getChildren().add(headerText1);
        header1.getChildren().add(headerColumn1);

        UIOutput column1header = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        column1header.setValue("Column1 Header");
        headerColumn1.getFacets().put("header", column1header);

        UIColumn headerColumn2 = (UIColumn) application.createComponent("org.richfaces.Column");
        UIOutput headerText2 = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        headerText2.setValue("Header Column2");
        headerColumn2.getChildren().add(headerText2);
        header1.getChildren().add(headerColumn2);

        UIOutput column2header = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        column2header.setValue("Column2 Header");
        headerColumn2.getFacets().put("header", column2header);

        UIOutput caption = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        dataTable.getFacets().put("caption", caption);
        caption.setValue("Caption");

        UIOutput footer = (UIOutput) createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),null, null, null);
        dataTable.getFacets().put("footer", footer);
        footer.setValue("Footer");

        
        // test
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement table = page.getHtmlElementById(dataTable
                .getClientId(facesContext));
        assertNotNull(table);

        List<?> captions = table.getHtmlElementsByTagName("caption");
        assertNotNull(captions);
        assertEquals(1, captions.size());
        String classAttr = ((HtmlElement) captions.get(0))
                .getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-caption rich-table-caption"));

        List<?> headers = table.getHtmlElementsByTagName("thead");
        assertNotNull(headers);
        assertEquals(1, headers.size());
        List<?> trs = ((HtmlElement) headers.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        HtmlElement tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-header rich-table-header"));

        Iterator<?> tds = tr.getChildIterator();
        assertNotNull(tds);
        assertTrue(tds.hasNext());
        HtmlElement td = (HtmlElement) tds.next();
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-headercell rich-table-headercell"));
        assertTrue(classAttr.contains("cola"));

        List<?> footers = table.getHtmlElementsByTagName("tfoot");
        assertNotNull(footers);
        assertEquals(1, footers.size());
        trs = ((HtmlElement) footers.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-footer rich-table-footer "));
  
        tds = tr.getChildIterator();
        assertTrue(tds.hasNext());
        td = (HtmlElement) tds.next();
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-footercell rich-table-footercell "));

        Iterator<?> fixedChildren = dataTable.fixedChildren();
        assertNotNull(fixedChildren);
        assertTrue(fixedChildren.hasNext());
    }

    /**
     * Test DataTable component rows and columns rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataTableRowsAndColumns() throws Exception {

        UIOutput caption = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        dataTable.getFacets().put("caption", caption);
        caption.setValue("Caption");
        dataTable.getAttributes().put("captionClass", "captionClass");
        dataTable.getAttributes().put("captionStyle", "captionStyle");

        dataTable.getAttributes().put("rowClasses", "row1,row2");
        dataTable.getAttributes().put("columnClasses", "column1,column2");

        column1.getAttributes().put("styleClass", "column1StyleClass");
        column2.getAttributes().put("styleClass", "");

        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement table = page.getHtmlElementById(dataTable
                .getClientId(facesContext));
        assertNotNull(table);

        List<?> captions = table.getHtmlElementsByTagName("caption");
        assertNotNull(captions);
        assertEquals(1, captions.size());
        String classAttr = ((HtmlElement) captions.get(0)).getAttributeValue("class");
        assertTrue(classAttr.contains("captionClass"));
        classAttr = ((HtmlElement) captions.get(0)).getAttributeValue("style");
        assertTrue(classAttr.contains("captionStyle"));

        List<?> bodies = table.getHtmlElementsByTagName("tbody");
        assertTrue(bodies.size() > 0);
        List<?> trs = ((HtmlElement) bodies.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        HtmlElement tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("row1"));

        List<?> tds = tr.getHtmlElementsByTagName("td");
        assertTrue(tds.size() > 0);
        HtmlElement td = (HtmlElement) tds.get(0);
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr.contains("column1"));
        assertTrue(classAttr.contains("column1StyleClass"));
    }
    
    protected class ColumnValueExpression extends ValueExpression {

		private static final long serialVersionUID = -4572752019634445014L;

		public Class<?> getExpectedType() {
			return null;
		}

		public Class<?> getType(ELContext context) {
			return String.class;
		}

		public Object getValue(ELContext context) {
			return Long.toString(((Date) dataTable.getValue()).getTime());
		}

		public boolean isReadOnly(ELContext context) {
			return false;
		}

		public void setValue(ELContext context, Object value) {
			
		}

		public boolean equals(Object obj) {
			return false;
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
    	
    }

}
