/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
* 
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License version 2.1 as published by the Free Software Foundation.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

*/

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.model.Ordering;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author mpopiolek
 * 
 */
public class AbstractExtendedTableRendererTest extends AbstractAjax4JsfTestCase {

    private ExtendedTableRenderer renderer;
    private UIExtendedDataTable table;

    public AbstractExtendedTableRendererTest(String name) {
        super(name);
        renderer = new ExtendedTableRenderer();
    }

    public void setUp() throws Exception {
        super.setUp();
        application.addComponent(UIExtendedDataTable.COMPONENT_TYPE,
                "org.richfaces.component.DefaultExtendedDataTable");
        table = (UIExtendedDataTable) application
                .createComponent(UIExtendedDataTable.COMPONENT_TYPE);

        table.setFirst(0);
        table.setRows(40);

        facesContext.getViewRoot().getChildren().add(table);
        table.setId("extDT");

        for (int i = 0; i < 4; i++) {
            UIColumn column = (UIColumn) application
                    .createComponent(UIColumn.COMPONENT_TYPE);
            column.setId("columnId" + i);
            column.setRendered(true);
            column.setSortable(true);
            UIOutput text = (UIOutput) createComponent(
                    HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class
                            .getName(), null, null, null);
            column.getFacets().put("header", text);
            column.getFacets().put("footer", text);
            if (i == 0) {
                column.setSortOrder(Ordering.ASCENDING);
            }

            table.getChildren().add(column);
        }

    }

    public void tearDown() throws Exception {
        table = null;
        super.tearDown();
    }

    public void testEncodeOneRow() {
        try {
            setupResponseWriter();
            ExtendedTableHolder holder = new ExtendedTableHolder(table);

            table.setRowKey(10);
            renderer.encodeOneRow(facesContext, holder);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement capt = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("div")) {
                    capt = node;

                    assertNotNull(capt);

                    String className = capt.getAttributeValue("class");
                    assertNotNull(className);
                    assertEquals("extdt-cell-div", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeColumns() {
        try {
            setupResponseWriter();
            renderer.encodeColumns(facesContext, table);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("col")) {
                    element = node;
                    assertNotNull(element);
                    String className = element.getAttributeValue("width");
                    if (null != className && className.length()>0) {
                        assertEquals("100", className);
                    }
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeHeader() {
        try {
            UIOutput text = (UIOutput) createComponent(
                    HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class
                            .getName(), null, null, null);
            table.getFacets().put("header", text);
            setupResponseWriter();
            renderer.encodeHeader(facesContext, table);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("thead")) {
                    element = node;
                    assertNotNull(element);

                    String id = element.getAttributeValue("id");
                    assertNotNull(id);
                    assertEquals("extDT:header", id);
                    
                    assertEquals(element.getHtmlElementsByTagName("tr").size(), 3);
                    assertNotNull(element.getHtmlElementById("extDT:fakeIeRow"));
                    assertNotNull(element.getHtmlElementById("extDT:headerRow"));
                    
                } 
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testIsColumnFacetPresent() {
        assertFalse(renderer.isColumnFacetPresent(table, "header"));
        UIOutput text = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        table.getSortedColumns().next().getFacets().put("header", text);
        assertTrue(renderer.isColumnFacetPresent(table, "header"));
    }

    public void testIsColumnRendered() {
        table.setRendered(false);
        assertFalse(renderer.isColumnRendered(table));
        table.setRendered(true);
        assertTrue(renderer.isColumnRendered(table));
    }

    public void testEncodeHeaderFacets() {
        try {
            UIOutput text = (UIOutput) createComponent(
                    HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class
                            .getName(), null, null, null);
            // header
            table.getFacets().put("header", text);
            setupResponseWriter();
            renderer
                    .encodeHeaderFacets(
                            facesContext,
                            writer,
                            table,
                            table.getSortedColumns(),
                            "extdt-dr-menucell extdt-subheadercell rich-extdt-subheadercell",
                            (String) table.getAttributes().get("headerClass"),
                            "header", "th", 4);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("th")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("scope");
                    assertNotNull(className);
                    assertEquals("col", className);
                }
            }
            // footer
            table.getFacets().put("footer", text);
            setupResponseWriter();
            renderer
                    .encodeHeaderFacets(
                            facesContext,
                            writer,
                            table,
                            table.getSortedColumns(),
                            "extdt-dr-menucell extdt-subheadercell rich-extdt-subheadercell",
                            (String) table.getAttributes().get("headerClass"),
                            "header", "td", 4);
            page = processResponseWriter();

            elementIterator = page.getAllHtmlChildElements();

            element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("td")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("scope");
                    assertNotNull(className);
                    assertEquals("col", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeFooter() {
        try {
            UIOutput text = (UIOutput) createComponent(
                    HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class
                            .getName(), null, null, null);

            table.getFacets().put("footer", text);
            setupResponseWriter();
            renderer.encodeFooter(facesContext, table);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("tfoot")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("id");
                    assertNotNull(className);
                    assertEquals("extDT:footer", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testRowGroupChanged() {

    }

    public void testEncodeRowStartFacesContextStringUIDataTableTableHolderResponseWriter() {
        try {
            setupResponseWriter();
            table.setRowKey(10);

            ExtendedTableHolder holder = new ExtendedTableHolder(table);
            renderer.encodeRowStart(facesContext, "extdt-row rich-extdt-row",
                    "rowClass", table, holder, writer);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("test")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("id");
                    assertNotNull(className);
                    assertEquals("j_id0:10:n:0", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeRowId() {
        try {
            setupResponseWriter();
            table.setRowKey(10);

            writer.startElement("test", table);
            renderer.encodeRowId(facesContext, writer, table, 0);
            writer.endElement("test");

            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("test")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("id");
                    assertNotNull(className);
                    assertEquals("extDT:n:0", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testGetColumnsCount() {
        assertEquals(4, renderer.getColumnsCount(table));
    }

    public void testCalculateRowColumns() {
        assertEquals(4, renderer.calculateRowColumns(table.columns()));
    }

    public void testEncodeScriptIfNecessary() {
        try {
            setupResponseWriter();
            renderer.encodeScriptIfNecessary(facesContext, table);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement element = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();

                if (node.getNodeName().equalsIgnoreCase("script")) {
                    element = node;
                    assertNotNull(element);

                    String className = element.getAttributeValue("type");
                    assertNotNull(className);
                    assertEquals("text/javascript", className);
                }
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // JSFunction or JSFunctionDefinition return:

    // public void testGetSortFunctionDefFacesContextUIDataTable() {
    // }
    //
    // public void testGetSortFunctionDefFacesContextUIDataTableBoolean() {
    // }
    //
    // public void testGetSortFunction() {
    // }
    //
    // public void testGetOnGroupToggleFunctionDef() {
    // }
    //
    // public void testGetGroupFunction() {
    // }
    //
    // public void testGetOnResizeFunctionDef() {
    // }
    //
    // public void testGetChangeColumnVisibilityFunction() {
    // }
    //
    // public void testGetPreSendAjaxRequestFunction() {
    // }
    //
    // public void testBuildAjaxFunction() {
    // }

    public void testDoDecodeFacesContextUIComponent() {
         facesContext.getExternalContext().getRequestParameterMap().put(
         table.getClientId(facesContext), "fsp");
         facesContext.getExternalContext().getRequestParameterMap().put(
         "fsp", "extDT:columnId0");
         renderer.doDecode(facesContext, table);
    }

    // public void testEncodeEndFacesContextUIComponent() {
    //
    // }

    public void testAddInplaceInput() {
//        UIColumn column = (UIColumn) application
//                .createComponent(UIColumn.COMPONENT_TYPE);
//        column.setId("columnId");
    	UIColumn column = (UIColumn)RendererUtils.getInstance().findComponentFor(table, "columnId0");
        column.setFilterValue("test");
        // filter event == null
        try {
            setupResponseWriter();
            renderer.addInplaceInput(facesContext, column);
            UIInput input = (UIInput) column.getFacets()
                    .get("filterValueInput");
            //assertEquals("buffer", input.getAttributes().get("onchange"));
            assertTrue(((String)input.getAttributes().get("onchange")).length()>0);
            assertEquals("test", input.getValue());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        // with set filter event
        column.getAttributes().put("filterEvent", "testEvent");
        try {
            setupResponseWriter();
            renderer.addInplaceInput(facesContext, column);
            UIInput input = (UIInput) column.getFacets()
                    .get("filterValueInput");
            assertTrue(((String)input.getAttributes().get("testEvent")).length()>0);
            //assertEquals("buffer", input.getAttributes().get("testEvent"));
            assertEquals("test", input.getValue());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        column.setFilterValue(null);
    }

    public void testGetJavaScriptVarName() {
        assertEquals("window.ExtendedDataTable.DataTable_extDT", renderer
                .getJavaScriptVarName(facesContext, table));
    }

    public void testGetOnAjaxCompleteFunction() {
        assertNull(renderer.getOnAjaxCompleteFunction(facesContext, table));
    }

}
