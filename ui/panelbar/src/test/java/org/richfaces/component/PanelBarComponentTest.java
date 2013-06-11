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

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.event.EventsQueue;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockViewRoot;
import org.richfaces.component.html.HtmlPanelBar;
import org.richfaces.component.html.HtmlPanelBarItem;
import org.richfaces.event.SwitchablePanelSwitchEvent;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for PanelBar components.
 */
public class PanelBarComponentTest extends AbstractAjax4JsfTestCase {

    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

    static {
        javaScripts.add("browser_info.js");
        javaScripts.add("panelbar.js");
        javaScripts.add("AjaxScript");
        javaScripts.add("PrototypeScript");
        javaScripts.add("ImageCacheScript");
    }

    private UIPanelBar panelBar;

    private UIPanelBarItem panelBarItem1;

    private UIPanelBarItem panelBarItem2;

    private UIForm form;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public PanelBarComponentTest(String testName) {
        super(testName);
    }

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);

        panelBar = (UIPanelBar) application
                .createComponent(HtmlPanelBar.COMPONENT_TYPE);
        panelBar.setId("panelBar");

        panelBarItem1 = (UIPanelBarItem) application
                .createComponent(HtmlPanelBarItem.COMPONENT_TYPE);
        panelBarItem1.setId("panelBarItem1");
        panelBarItem1.setLabel("Panel Bar Item 1");
        panelBarItem2 = (UIPanelBarItem) application
                .createComponent(HtmlPanelBarItem.COMPONENT_TYPE);
        panelBarItem2.setId("panelBarItem2");
        panelBarItem2.setLabel("Panel Bar Item 2");

        panelBar.getChildren().add(panelBarItem1);
        panelBar.getChildren().add(panelBarItem2);
        form.getChildren().add(panelBar);
    }

    public void tearDown() throws Exception {
        super.tearDown();

        panelBarItem1 = null;
        panelBarItem2 = null;
        panelBar = null;
        form = null;
    }

    /**
     * PanelBar components renderer test.
     */
    public void testPanelBarRender() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById(panelBar
                .getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());

        div = page.getHtmlElementById(panelBarItem1.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());

        div = page.getHtmlElementById(panelBarItem2.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());

        HtmlElement input = page.getHtmlElementById(panelBar
                .getClientId(facesContext)
                + "_panelBarInput");
        assertNotNull(input);
        assertEquals("input", input.getNodeName());
        assertEquals(input.getAttributeValue("type"), "hidden");
    }

    /**
     * PanelBar attributes test.
     */
    public void testPanelBarAttributes() throws Exception {
        panelBar.setValue("panelBarItem1");
        panelBar.getAttributes().put("styleClass", "panelBarStyleClass");
        panelBar.getAttributes().put("headerClassActive",
                "panelBarHeaderClassActive");
        panelBar.getAttributes().put("style", "panelBarHeaderStyle");
        panelBar.setWidth("500");
        panelBar.setHeight("700");

        panelBarItem1.getAttributes().put("headerClassActive",
                "panelBarItem1HeaderClassActive");
        panelBarItem1.getAttributes().put("headerClass",
                "panelBarItem1HeaderClass");

        HtmlPage page = renderView();
        assertNotNull(page);
        // System.out.println(page.asXml());

        HtmlElement div = page.getHtmlElementById(panelBar
                .getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
        String classAttr = div.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-pnlbar rich-panelbar dr-pnlbar-b"));
        assertTrue(classAttr.contains("panelBarStyleClass"));
        classAttr = div.getAttributeValue("style");
        assertTrue(classAttr.contains("width: 500px;"));
        assertTrue(classAttr.contains("height: 700px;"));

        HtmlElement input = page.getHtmlElementById(panelBar
                .getClientId(facesContext)
                + "_panelBarInput");
        assertNotNull(input);
        assertEquals("input", input.getNodeName());
        classAttr = input.getAttributeValue("value");
        assertTrue(classAttr.contains(panelBarItem1.getId()));

        div = page.getHtmlElementById(panelBarItem1.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
        classAttr = div.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-pnlbar rich-panelbar dr-pnlbar-int rich-panelbar-interior"));
        assertTrue(classAttr.contains("panelBarStyleClass"));
        classAttr = div.getAttributeValue("style");
        assertTrue(classAttr.contains("panelBarHeaderStyle"));
        List children = div.getHtmlElementsByTagName("div");
        assertEquals(3, children.size());

    }

    /**
     * CSS link test.
     */
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        // Assert.assertEquals(1, links.size());
        assertNotNull(links);
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains(
                "css/panelbar.xcss"));
    }

    /**
     * Scripts link test.
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        //System.out.println(page.asXml());

        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }

    /**
     * doDecode method test.
     */
    public void testPanelBarDoDecodeImmediate() throws Exception {

    	panelBar.setImmediate(true);
        assertNotNull(panelBar.getSwitchType());
        panelBar.setSwitchType(UISwitchablePanel.AJAX_METHOD);

        externalContext.getRequestParameterMap().put(
                panelBar.getClientId(facesContext), "Swich");
        panelBar.decode(facesContext);

        MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
        EventsQueue events = mockViewRoot.getEventsQueue(PhaseId.APPLY_REQUEST_VALUES);
        assertNotNull(events);
        assertEquals(1, events.size());

        FacesEvent event = (FacesEvent) events.remove();
        assertTrue(event instanceof SwitchablePanelSwitchEvent);
        SwitchablePanelSwitchEvent switchEvent = (SwitchablePanelSwitchEvent) event;
        assertEquals(switchEvent.getValue(), "Swich");
    }
       
    public void testPanelBarSwitch() throws Exception {
    	
    	assertNotNull(panelBar.getSwitchType());
        panelBar.setSwitchType(UISwitchablePanel.SERVER_METHOD);

        externalContext.getRequestParameterMap().put(panelBar.getClientId(facesContext), "form:panelBarItem2");
        
        panelBar.decode(facesContext);
      
        MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
        mockViewRoot.processUpdates(facesContext);
        
        assertEquals(panelBar.getValue(), "form:panelBarItem2");
    }	
    
    /**
     * doDecode method test. No events must be generated.
     */
    public void testPanelBarDoDecodeNoEvent() throws Exception {

        externalContext.getRequestParameterMap().put("ABYBC", "Swich");
        panelBar.decode(facesContext);

        MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
        EventsQueue events = mockViewRoot.getEventsQueue(PhaseId.INVOKE_APPLICATION);
        assertNotNull(events);
        assertEquals(0, events.size());
    }
}
