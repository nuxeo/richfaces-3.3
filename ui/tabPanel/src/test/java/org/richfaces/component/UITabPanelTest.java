/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import java.util.Iterator;
import java.util.Map;

/**
 * Unit test for UITabPanel.
 */
public class UITabPanelTest extends AbstractAjax4JsfTestCase {

    private UITabPanel tabPanel;
    private UITab tab1;
    private UITab tab2;
    private UITab tab3;
    private UIComponent form;
    private UIOutput out1;
    private UIOutput out2;
    private UIOutput out3;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UITabPanelTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);

        tabPanel = (UITabPanel) application.createComponent("org.richfaces.TabPanel");
        form.getChildren().add(tabPanel);

        tab1 = (UITab) application.createComponent("org.richfaces.Tab");
        tab1.setId("Tab1_ID");
        tab1.setName("Tab1");
        out1 = (UIOutput) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        out1.setValue("output1");
        tab1.getChildren().add(out1);
        tabPanel.getChildren().add(tab1);

        tab2 = (UITab) application.createComponent("org.richfaces.Tab");
        tab2.setId("Tab2_ID");
        tab2.setName("Tab2");
        out2 = (UIOutput) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        out2.setValue("output2");
        tab2.getChildren().add(out2);
        tabPanel.getChildren().add(tab2);

        tab3 = (UITab) application.createComponent("org.richfaces.Tab");
        tab3.setId("Tab3_ID");
        tab3.setName("Tab3");
        out3 = (UIOutput) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        out3.setValue("output3");
        tab3.getChildren().add(out3);
        tabPanel.getChildren().add(tab3);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        tabPanel = null;
        tab1 = null;
        tab2 = null;
        tab3 = null;
        form = null;
        out1 = null;
        out2 = null;
        out3 = null;
    }

    public void testConvertSwitchValue() throws Exception {
        assertEquals("switchValue",
                tabPanel.convertSwitchValue(tab1, "switchValue"));
        assertEquals("Tab1", tabPanel.convertSwitchValue(tab1, null));

        try {
            assertEquals("Tab1", tabPanel.convertSwitchValue(tabPanel, null));
            assertTrue(false);
        } catch(ClassCastException ex) {
        }
    }

    public void testGetSwitchedFacetsAndChildren1() throws Exception {
        tab1.setSwitchType(UISwitchablePanel.SERVER_METHOD);
        tab2.setSwitchType(UISwitchablePanel.SERVER_METHOD);
        tab3.setSwitchType(UISwitchablePanel.SERVER_METHOD);

        tabPanel.setRenderedValue(tab3.getName());
        Object state = tabPanel.saveState(facesContext);
        tabPanel.restoreState(facesContext, state);
        
        Iterator it = tabPanel.getSwitchedFacetsAndChildren();
        assertEquals(tab3.getId(), ((UITab)it.next()).getId());
        
        assertFalse(it.hasNext());
    }

    public void testGetSwitchedFacetsAndChildren2() throws Exception {
        tab1.setSwitchType(UISwitchablePanel.CLIENT_METHOD);
        tab2.setSwitchType(UISwitchablePanel.CLIENT_METHOD);
        tab3.setSwitchType(UISwitchablePanel.CLIENT_METHOD);

        Iterator it = tabPanel.getSwitchedFacetsAndChildren();
        assertEquals(tab1.getId(), ((UITab)it.next()).getId());
        assertEquals(tab2.getId(), ((UITab)it.next()).getId());
        assertEquals(tab3.getId(), ((UITab)it.next()).getId());

        assertFalse(it.hasNext());
    }
    
    public void testGetSwitchedFacetsAndChildrenFacets() throws Exception {
		Map<String, UIComponent> facets = tabPanel.getFacets();
		
		UIComponent testFacet = new HtmlOutputText();
		facets.put("testFacet", testFacet);
		
        tab1.setSwitchType(UISwitchablePanel.CLIENT_METHOD);
        tab2.setSwitchType(UISwitchablePanel.CLIENT_METHOD);
        tab3.setSwitchType(UISwitchablePanel.CLIENT_METHOD);

        Iterator it = tabPanel.getSwitchedFacetsAndChildren();
        assertEquals(tab1.getId(), ((UITab)it.next()).getId());
        assertEquals(tab2.getId(), ((UITab)it.next()).getId());
        assertEquals(tab3.getId(), ((UITab)it.next()).getId());

        assertSame(testFacet, it.next());

        assertFalse(it.hasNext());
	}
}
