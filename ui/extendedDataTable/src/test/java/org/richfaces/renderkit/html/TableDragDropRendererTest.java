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

package org.richfaces.renderkit.html;

import java.util.Iterator;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColumn;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author mpopiolek
 * 
 */
public class TableDragDropRendererTest extends AbstractAjax4JsfTestCase {

    private TableDragDropRenderer renderer;

    private UIColumn column1;

    public TableDragDropRendererTest(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        column1 = (UIColumn) application
                .createComponent(UIColumn.COMPONENT_TYPE);
        column1.setId("columnId1");
        facesContext.getViewRoot().getChildren().add(column1);

        renderer = TableDragDropRenderer.getInstance(facesContext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        renderer = null;
        column1 = null;
    }

    public void testEncodeChildScripts() {

    }

    public void testRenderDragSupport() {
        try {
            setupResponseWriter();
            renderer.renderDragSupport(column1, "dragSourceId", "indicatorId",
                    "dragLabel");
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement script = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("script")) {
                    script = node;
                }
            }

            assertNotNull(script);

            String className = script.getAttributeValue("id");

            assertNotNull(className);

            assertEquals("columnId1:dnd_drag_script", className);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testRenderDropSupport() {

        try {
            setupResponseWriter();
            renderer.renderDropSupport(column1, "dropTargetId", true);
            HtmlPage page = processResponseWriter();

            Iterator elementIterator = page.getAllHtmlChildElements();

            HtmlElement script = null;

            while (elementIterator.hasNext()) {
                HtmlElement node = (HtmlElement) elementIterator.next();
                if (node.getNodeName().equalsIgnoreCase("script")) {
                    script = node;
                }
            }

            assertNotNull(script);

            String className = script.getAttributeValue("id");

            assertNotNull(className);

            assertEquals("columnId1:dnd_drop_script_left", className);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
