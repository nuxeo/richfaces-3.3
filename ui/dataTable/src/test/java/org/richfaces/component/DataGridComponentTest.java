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
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for DataGrid component.
 */
public class DataGridComponentTest extends AbstractAjax4JsfTestCase {

    private UIDataGrid dataGrid;
    private UIComponent form;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public DataGridComponentTest(String testName) {
        super(testName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

    	form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
        dataGrid = (UIDataGrid) application
                .createComponent("org.richfaces.DataGrid");
        dataGrid.setId("dataGrid");

        dataGrid.setColumns(5);

        List list = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            list.add(new Date((long) Math.random()));
        }
        dataGrid.setValue(list);

        form.getChildren().add(dataGrid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        dataGrid = null;
    }

    /**
     * Test DataGrid component rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataGrid() throws Exception {

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement table = page.getHtmlElementById(dataGrid
                .getClientId(facesContext));
        assertNotNull(table);
    }

    /**
     * Test DataGrid component facets rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataGridFacets() throws Exception {

        dataGrid.getAttributes().put("rowClasses", "row1,row2");
        dataGrid.getAttributes().put("columnClasses", "A,B,Y,B,C");

        UIOutput header = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        dataGrid.getFacets().put("header", header);
        header.setValue("Header");

        UIOutput footer = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        dataGrid.getFacets().put("footer", footer);
        footer.setValue("Footer");
       
        UIOutput caption = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        caption.setValue("Caption");
        caption.setId("caption");
        dataGrid.getFacets().put("caption", caption);
 //     System.out.println(dataGrid.getFacets().toString());
      
        

        HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());

        HtmlElement table = page.getHtmlElementById(dataGrid
                .getClientId(facesContext));
        assertNotNull(table);
        assertEquals("table", table.getNodeName());
        String classAttr = table.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table rich-table"));

        List captions = table.getHtmlElementsByTagName("caption");
        assertNotNull(captions);
        assertEquals(1, captions.size());
        classAttr = ((HtmlElement) captions.get(0)).getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-caption rich-table-caption"));

        List headers = table.getHtmlElementsByTagName("thead");
        assertNotNull(headers);
        assertEquals(1, headers.size());
        List trs = ((HtmlElement) headers.get(0))
                .getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        HtmlElement tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-header rich-table-header"));

        List tds = tr.getHtmlElementsByTagName("th");
        assertTrue(tds.size() > 0);
        HtmlElement td = (HtmlElement) tds.get(0);
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr
                .contains("dr-table-headercell rich-table-headercell"));

        List footers = table.getHtmlElementsByTagName("tfoot");
        assertNotNull(footers);
        assertEquals(1, footers.size());
        trs = ((HtmlElement) footers.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-footer rich-table-footer"));

        tds = tr.getHtmlElementsByTagName("td");
        assertTrue(tds.size() > 0);
        td = (HtmlElement) tds.get(0);
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr
                .contains("dr-table-footercell rich-table-footercell"));

        List bodies = table.getHtmlElementsByTagName("tbody");
        assertEquals(1, bodies.size());
        trs = ((HtmlElement) bodies.get(0)).getHtmlElementsByTagName("tr");
        assertTrue(trs.size() > 0);
        tr = (HtmlElement) trs.get(0);
        assertNotNull(tr);
        classAttr = tr.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-row rich-table-row"));
        assertTrue(classAttr.contains("row1"));

        tds = tr.getHtmlElementsByTagName("td");
        assertTrue(tds.size() > 0);
        td = (HtmlElement) tds.get(0);
        assertNotNull(td);
        classAttr = td.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-table-cell rich-table-cell"));
        assertTrue(classAttr.contains("A"));
    }

    /**
     * Test UIDataGrid component class.
     * 
     * @throws Exception
     */
    public void testUIDataGrid() throws Exception {

        dataGrid.setColumns(1);

        dataGrid.setElements(5);
        assertEquals(5, dataGrid.getElements());

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement table = page.getHtmlElementById(dataGrid
                .getClientId(facesContext));
        assertNotNull(table);
        List bodies = table.getHtmlElementsByTagName("tbody");
        assertEquals(1, bodies.size());
        List trs = ((HtmlElement) bodies.get(0)).getHtmlElementsByTagName("tr");
        assertEquals(5, trs.size());
    }

    /**
     * Test UIDataGrid component class.
     * 
     * @throws Exception
     */
    public void testDataGridWithSubTable() throws Exception {

        UIColumn column1 = (UIColumn) application
                .createComponent("org.richfaces.Column");
        dataGrid.getChildren().add(column1);
        HtmlOutputText text1 = (HtmlOutputText) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        text1.setValue("Column");
        column1.getChildren().add(text1);

        UIColumn column2 = (UIColumn) application
                .createComponent("org.richfaces.Column");
        dataGrid.getChildren().add(column2);
        HtmlOutputText text2 = (HtmlOutputText) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        text2.setValue("Column2");
        column2.getChildren().add(text2);

        dataGrid.getAttributes().put("columnClasses", "sub1,");

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement table = page.getHtmlElementById(dataGrid
                .getClientId(facesContext));
        assertNotNull(table);
        List bodies = table.getHtmlElementsByTagName("tbody");
        assertEquals(1, bodies.size());
        List trs = ((HtmlElement) bodies.get(0)).getHtmlElementsByTagName("tr");
        assertEquals(2, trs.size());
    }

}
