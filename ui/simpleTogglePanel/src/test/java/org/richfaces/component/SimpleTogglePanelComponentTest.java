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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for SimpleTogglePanel component.
 */
public class SimpleTogglePanelComponentTest extends AbstractAjax4JsfTestCase {

    private static Set<String> javaScripts = new HashSet<String>();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

    static {
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org.ajax4jsf.javascript.ImageCacheScript");
        javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
        javaScripts.add("scripts/simpleTogglePanel.js");
    }

    private UISimpleTogglePanel stp1;
    private UISimpleTogglePanel stp2;
    private UIForm form;
    private UIForm form2;
    private UIOutput openMarker1;
    private UIOutput closeMarker1;
    private UIOutput openMarker2;
    private UIOutput closeMarker2;
    private UICommand command;
    private UIInput input;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleTogglePanelComponentTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        form.setId("form");
        form2 = new HtmlForm();
        form2.setId("form2");
        facesContext.getViewRoot().getChildren().add(form);
        facesContext.getViewRoot().getChildren().add(form2);

        stp1 = (UISimpleTogglePanel)application.createComponent("org.richfaces.SimpleTogglePanel");
        stp1.setId("simpleTogglePanel1");
        stp1.setOpened(true);
        stp1.setSwitchType(UISimpleTogglePanel.SERVER_SWITCH_TYPE);

        openMarker1 = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        openMarker1.setId("openMarker");
        openMarker1.setValue("open");

        closeMarker1 = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        closeMarker1.setId("closeMarker");
        closeMarker1.setValue("close");

        stp1.getFacets().put(openMarker1.getId(), openMarker1);
        stp1.getFacets().put(closeMarker1.getId(), closeMarker1);
        form.getChildren().add(stp1);

        stp2 = (UISimpleTogglePanel)application.createComponent("org.richfaces.SimpleTogglePanel");
        stp2.setId("simpleTogglePanel2");
        stp2.setOpened(false);
        stp2.setSwitchType(UISimpleTogglePanel.SERVER_SWITCH_TYPE);

        openMarker2 = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        openMarker2.setId("openMarker");
        openMarker2.setValue("open");

        closeMarker2 = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        closeMarker2.setId("closeMarker");
        closeMarker2.setValue("close");

        stp2.getFacets().put(openMarker2.getId(), openMarker2);
        stp2.getFacets().put(closeMarker2.getId(), closeMarker2);
        form2.getChildren().add(stp2);

        input = (UIInput)application.createComponent(UIInput.COMPONENT_TYPE);
        input.setValue("");
        input.setId("opened");
        stp1.getChildren().add(input);

        command = new HtmlCommandLink();
        command.setId("command");
        stp1.getChildren().add(command);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        stp1 = null;
        stp2 = null;
        form = null;
        form2 = null;
        openMarker1 = null;
        closeMarker1 = null;
        openMarker2 = null;
        closeMarker2 = null;
        command = null;
        input = null;
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

        HtmlElement div1 = page.getHtmlElementById(stp1.getClientId(facesContext));
        assertNotNull(div1);
        assertEquals("div", div1.getNodeName());

        String classAttr1 = div1.getAttributeValue("class");
        assertTrue(classAttr1.contains("dr-stglpnl"));
        assertTrue(classAttr1.contains("rich-stglpanel"));

        HtmlDivision div2 = (HtmlDivision)page.getHtmlElementById(stp1.getClientId(facesContext) + "_header");
        assertNotNull(div2);
        assertEquals("div", div2.getNodeName());

        String classAttr2 = div2.getAttributeValue("class");
        assertTrue(classAttr2.contains("dr-stglpnl-h"));
        assertTrue(classAttr2.contains("rich-stglpanel-header"));

        HtmlElement div3 = page.getHtmlElementById(stp1.getClientId(facesContext) + "_switch_on");
        assertNotNull(div3);
        assertEquals("div", div3.getNodeName());
        
        HtmlElement div5 = page.getHtmlElementById(stp1.getClientId(facesContext) + "_switch_off");
        assertNotNull(div5);
        assertEquals("div", div5.getNodeName());
        
        HtmlElement div4 = page.getHtmlElementById(stp1.getClientId(facesContext) + "_body");
        assertNotNull(div4);
        assertEquals("div", div4.getNodeName());

        try {
            page.getHtmlElementById(openMarker1.getClientId(facesContext));
            assertTrue(false);
        } catch(Throwable t) { }

        HtmlElement f1 = page.getHtmlElementById(closeMarker1.getClientId(facesContext));
        assertNotNull(f1);

        HtmlElement f2 = page.getHtmlElementById(openMarker2.getClientId(facesContext));
        assertNotNull(f2);

        try {
            page.getHtmlElementById(closeMarker2.getClientId(facesContext));
            assertTrue(false);
        } catch(Throwable t) { }
    }

    /**
     * Test style rendering
     *
     * @throws Exception
     */
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/simpleTogglePanel.xcss"));
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
   }

    /**
     * Test simpleTogglePanel switch
     *
     * @throws Exception
     */
    public void testSwitch() throws Exception {
        assertTrue(stp1.isOpened());

        HtmlPage page = renderView();
        //System.out.println(page.asXml());

        externalContext.addRequestParameterMap(form.getClientId(facesContext), form.getClientId(facesContext));
        externalContext.addRequestParameterMap(stp1.getClientId(facesContext), "false");
        stp1.setSwitchType(UISimpleTogglePanel.CLIENT_SWITCH_TYPE);
        UIViewRoot viewRoot = facesContext.getViewRoot();
        viewRoot.processDecodes(facesContext);
        viewRoot.processValidators(facesContext);
        viewRoot.processUpdates(facesContext);

        assertFalse(stp1.isOpened());
    }
}
