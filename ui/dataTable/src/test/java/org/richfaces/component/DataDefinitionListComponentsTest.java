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
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;
import javax.faces.model.ListDataModel;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for DataDefinitionList component.
 */
public class DataDefinitionListComponentsTest extends AbstractAjax4JsfTestCase {

    private UIDataDefinitionList dataList;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public DataDefinitionListComponentsTest(String testName) {
        super(testName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        dataList = (UIDataDefinitionList) application
                .createComponent("org.richfaces.DataDefinitionList");
        dataList.setId("dataDefinitionList");

        List list = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            list.add(new Date((long) Math.random()));
        }
        dataList.setValue(new ListDataModel(list));

        UIOutput element1 = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        element1.setValueBinding("value", new ValueBinding() {
            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return String.class;
            }

            public Object getValue(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return Long.toString(((Date) dataList.getValue()).getTime());
            }

            public boolean isReadOnly(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return false;
            }

            public void setValue(FacesContext context, Object value)
                    throws EvaluationException, PropertyNotFoundException {
            }
        });
        dataList.getChildren().add(element1);

        UIOutput element2 = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputLink.class.getName(),
                null, null, null);
        element2.setValueBinding("value", new ValueBinding() {
            public Class getType(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return String.class;
            }

            public Object getValue(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return Long.toString(((Date) dataList.getValue()).getTime());
            }

            public boolean isReadOnly(FacesContext context)
                    throws EvaluationException, PropertyNotFoundException {
                return false;
            }

            public void setValue(FacesContext context, Object value)
                    throws EvaluationException, PropertyNotFoundException {
            }
        });
        dataList.getChildren().add(element2);

        facesContext.getViewRoot().getChildren().add(dataList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        dataList = null;
    }

    /**
     * Test DataDefinitionList component rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataDefinitionList() throws Exception {

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement dl = page.getHtmlElementById(dataList
                .getClientId(facesContext));
        assertNotNull(dl);
        assertEquals("dl", dl.getNodeName());
        String classAttr = dl.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-deflist rich-deflist"));

        List dds = dl.getHtmlElementsByTagName("dd");
        assertTrue(dds.size() > 0);
        assertEquals(dds.size(), 5);
        HtmlElement dd = (HtmlElement) dds.get(0);
        assertNotNull(dd);
        classAttr = dd.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-definition rich-definition"));
    }

    /**
     * Test DataDefinitionList component facet rendering.
     * 
     * @throws Exception
     */
    public void testDataDefinitionListFacet() throws Exception {

        UIOutput facet = (UIOutput) createComponent(
                HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(),
                null, null, null);
        facet.setValue("Term facet");
        dataList.getFacets().put("term", facet);

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement dl = page.getHtmlElementById(dataList
                .getClientId(facesContext));
        assertNotNull(dl);
        assertEquals("dl", dl.getNodeName());
        String classAttr = dl.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-deflist rich-deflist"));

        List dts = dl.getHtmlElementsByTagName("dt");
        assertTrue(dts.size() > 0);
        assertEquals(dts.size(), 5);
        HtmlElement dt = (HtmlElement) dts.get(0);
        assertNotNull(dt);
        classAttr = dt.getAttributeValue("class");
        assertTrue(classAttr
                .contains("dr-definition-term rich-definition-term"));

        List dds = dl.getHtmlElementsByTagName("dd");
        assertTrue(dds.size() > 0);
        assertEquals(dds.size(), 5);
        HtmlElement dd = (HtmlElement) dds.get(0);
        assertNotNull(dd);
        classAttr = dd.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-definition rich-definition"));

        Iterator fixedChildren = dataList.fixedChildren();
        assertNotNull(fixedChildren);
    }
}
