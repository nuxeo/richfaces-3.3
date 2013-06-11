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
 * Unit test for DataList component.
 */
public class DataListComponentsTest extends AbstractAjax4JsfTestCase {

    private UIDataList dataList;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public DataListComponentsTest(String testName) {
        super(testName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        dataList = (UIDataList) application
                .createComponent("org.richfaces.DataList");
        dataList.setId("dataList");

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
     * Test DataList component rendering.
     * 
     * @throws Exception
     */
    public void testRenderDataList() throws Exception {

        // TYPE=disc|circle|square
        dataList.getAttributes().put("style", "list-style:circle");

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement ul = page.getHtmlElementById(dataList
                .getClientId(facesContext));
        assertNotNull(ul);
        assertEquals("ul", ul.getNodeName());
        String classAttr = ul.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-list rich-datalist"));
        classAttr = ul.getAttributeValue("type");
        

        List lis = ul.getHtmlElementsByTagName("li");
        assertTrue(lis.size() > 0);
        assertEquals(lis.size(), 5);
        HtmlElement li = (HtmlElement) lis.get(0);
        assertNotNull(li);
        classAttr = li.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-list-item rich-list-item"));
    }

}
