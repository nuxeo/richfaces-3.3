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
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.component.UIOutput;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIExtendedDataTable;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author mpopiolek
 * 
 */
public class AbstractExtendedRowsRendererTest extends AbstractAjax4JsfTestCase {

    private ExtendedRowsRenderer rr;
    private UIExtendedDataTable table;

    public AbstractExtendedRowsRendererTest(String name) {
        super(name);
        rr = new ExtendedRowsRenderer();
    }

    public void setUp() throws Exception {
        super.setUp();
        application.addComponent(UIExtendedDataTable.COMPONENT_TYPE,
                "org.richfaces.component.DefaultExtendedDataTable");
        table = (UIExtendedDataTable) application
                .createComponent(UIExtendedDataTable.COMPONENT_TYPE);
        facesContext.getViewRoot().getChildren().add(table);
        table.setId("extDT");
    }

    public void tearDown() throws Exception {
        table = null;
        super.tearDown();
    }

    public void testGetRendersChildren() {
        assertTrue(rr.getRendersChildren());
    }

    public void testProcess() {
        Object rowKey = 100;
        ExtendedTableHolder eth = new ExtendedTableHolder(table);
        int count = eth.getRowCounter();
        Object argument = (Object) eth;
        try {
            rr.process(facesContext, rowKey, argument);
            eth = (ExtendedTableHolder) argument;
            assertEquals(count + 1, eth.getRowCounter());
            assertEquals(100, eth.getLastKey());
            assertEquals(100, eth.getTable().getRowKey());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        eth = (ExtendedTableHolder) argument;
        assertEquals(count + 1, ((ExtendedTableHolder) argument)
                .getRowCounter());

    }

    public void testEncodeRowsFacesContextUIComponentExtendedTableHolder() {
        ExtendedTableHolder eth = new ExtendedTableHolder(table);
        try {
            rr.encodeRows(facesContext, table, eth);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeCaption() {
        UIOutput caption = new UIOutput();
        caption.setValue("test");
        table.getFacets().put("caption", caption);
        try {
            setupResponseWriter();
            rr.encodeCaption(facesContext, table);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement capt = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("caption")) {
                    capt = node;
                }
            }

            assertNotNull(capt);

            String className = capt.getAttributeValue("class");
            assertNotNull(className);
            assertEquals("extdt-caption rich-extdt-caption", className);

            className = capt.getAttributeValue("id");
            assertNotNull(className);
            assertEquals("extDT:caption", className);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeStyleClass() {
        ResponseWriterMock writer = new ResponseWriterMock();
        try {
            rr.encodeStyleClass(writer, null, "predefined", "parent", "custom");
            ArrayList<String> attr = writer.attr;
            assertTrue(attr.contains("class predefined parent custom"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeStyle() {
        ResponseWriterMock writer = new ResponseWriterMock();
        try {
            rr.encodeStyle(writer, null, "predefined", "parent", "custom");
            ArrayList<String> attr = writer.attr;
            assertTrue(attr.contains("style predefined parent custom"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    public void testEncodeTableHeaderFacet() {
        UIOutput footer = new UIOutput();
        footer.setValue("test");
        table.getFacets().put("footer", footer);
        try {
            setupResponseWriter();
            rr.encodeTableHeaderFacet(facesContext, 10, writer, footer,
                    "skinFirstRowClass", "skinRowClass", "skinCellClass",
                    "footerClass", "element", "facetName");
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement n = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("element")) {
                    n = node;
                }
            }

            assertNotNull(n);

            String className = n.getAttributeValue("colspan");
            assertNotNull(className);
            assertEquals("10", className);

            className = n.getAttributeValue("scope");
            assertNotNull(className);
            assertEquals("colgroup", className);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
