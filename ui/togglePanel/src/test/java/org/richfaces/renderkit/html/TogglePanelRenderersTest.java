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

package org.richfaces.renderkit.html;

import java.io.StringWriter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;
import org.richfaces.component.UIToggleControl;
import org.richfaces.component.UITogglePanel;

/**
 * Unit test for TogglePanel renderers.
 */
public class TogglePanelRenderersTest extends AbstractAjax4JsfTestCase {

    private UITogglePanel togglePanel;

    private UIToggleControl toggleControl;

    private UIComponent form;

    private UIOutput a;

    private UIOutput b;

    private static ToggleControlRenderer controlRenderer = new ToggleControlRenderer();

    private static TogglePanelRenderer panelRenderer = new TogglePanelRenderer();

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public TogglePanelRenderersTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();

        a = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        a.setId("a_output");
        a.setValue("a");

        b = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        b.setId("b_output");
        b.setValue("b");

        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);

        togglePanel = (UITogglePanel) application
                .createComponent("org.richfaces.TogglePanel");
        togglePanel.setId("TogglePanel");
        togglePanel.getFacets().put("a", a);
        togglePanel.getFacets().put("b", b);
        togglePanel.setStateOrder("a,b");
        togglePanel.setInitialState("a");

        form.getChildren().add(togglePanel);

        toggleControl = (UIToggleControl) application
                .createComponent("org.richfaces.ToggleControl");
        toggleControl.setId("ToggleControl");
        toggleControl.setFor(togglePanel.getId());
        form.getChildren().add(toggleControl);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        togglePanel = null;
        toggleControl = null;
        form = null;
    }

    /**
     * Test for some ToggleControl renderer methods.
     */
    public void testToggleControlRendererMethods() throws Exception {

        assertEquals(controlRenderer.getComponentClass(), UIToggleControl.class);

        toggleControl.getAttributes().put("onclick", "toggleControlOnClick");
        String result = controlRenderer.getOnClick(facesContext, toggleControl);
        assertTrue(result.contains("toggleControlOnClick;"));
        assertTrue(result.contains("TogglePanelManager.toggleOnServer"));

        togglePanel.setSwitchType(UITogglePanel.AJAX_METHOD);
        toggleControl.getAttributes().put("onclick", "toggleControlOnClick;");
        result = controlRenderer.getOnClick(facesContext, toggleControl);
        assertTrue(result.contains("toggleControlOnClick;"));
        assertTrue(result.contains("A4J.AJAX.Submit"));
        toggleControl.getAttributes().put("disabled", "true");
        result = controlRenderer.getOnClick(facesContext, toggleControl);
        assertEquals("return false;", result);
        toggleControl.getAttributes().put("disabled", "false");

        togglePanel.setSwitchType(UITogglePanel.CLIENT_METHOD);
        toggleControl.setSwitchToState("b");
        result = controlRenderer.getOnClick(facesContext, toggleControl);
        assertTrue(result.contains("toggleControlOnClick;"));
        assertTrue(result.contains("TogglePanelManager.toggleOnClient"));

        toggleControl.setSwitchToState(null);
        String result2 = controlRenderer
                .getOnClick(facesContext, toggleControl);
        assertTrue(!result.equalsIgnoreCase(result2));
    }

    /**
     * Test for some TogglePanel renderer methods..
     */
    public void testTogglePanelRendererMethods() throws Exception {

        assertEquals(panelRenderer.getComponentClass(), UITogglePanel.class);

        facesContext.setResponseWriter(new MockResponseWriter(new StringWriter(), "text/html", "UTF-8"));
        togglePanel.setStateOrder(null);
        panelRenderer.handleFacets(facesContext, togglePanel);
    }
}
