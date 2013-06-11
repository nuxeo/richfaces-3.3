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

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.MenuComponent;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.html.HtmlMenuItem;

/**
 * Unit test for MenuItem renderer.
 */
public class MenuItemRendererBaseTest extends AbstractAjax4JsfTestCase {

    private UIMenuItem menuItem;

    private MenuComponent menu;

    private static MenuItemRendererBase renderer = new MenuItemRendererBase();

    /**
     * Simple implementation of MenuComponent interfase.
     */
    private class MenuComponentImpl extends UIComponentBase implements
            MenuComponent {

        public String getFamily() {
            // TODO Auto-generated method stub
            return null;
        }

        public String getSubmitMode() {
            return MenuComponent.MODE_SERVER;
        }

		public void setSubmitMode(String submitMode) {
			throw new UnsupportedOperationException();
		}

    }

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MenuItemRendererBaseTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();

        menu = new MenuComponentImpl();

        menuItem = (UIMenuItem) application
                .createComponent(HtmlMenuItem.COMPONENT_TYPE);
        menuItem.setId("menuItem");
        menuItem.setValue("Menu Item");

        ((UIComponent) menu).getChildren().add(menuItem);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        menuItem = null;
        menu = null;
    }

    /**
     * Test for resolveSubmitMode method.
     */
    public void testResolveSubmitModeMethod() throws Exception {
        menuItem.setSubmitMode(null);
        assertEquals(menuItem.getParent(), menu);
        String resultMode = renderer.resolveSubmitMode(menuItem);
        assertEquals(MenuComponent.MODE_SERVER, resultMode);

        menuItem.setSubmitMode(MenuComponent.MODE_NONE);
        resultMode = renderer.resolveSubmitMode(menuItem);
        assertEquals(resultMode, MenuComponent.MODE_NONE);
    }

    /**
     * Test for processInlineStyles method.
     */
    public void testProcessInlineStylesMethod() throws Exception {
        String style = "some style";
        String selectStyle = "some select style";
        String result = renderer.processInlineStyles(facesContext, menuItem,true);
        
        assertNotNull(result);
        assertTrue(result.startsWith("$('menuItem').style.cssText="));

        menuItem.getAttributes().put("selectStyle", selectStyle);
        result = renderer.processInlineStyles(facesContext, menuItem, false);
        assertTrue(!result.contains(selectStyle));
        assertTrue(!result.contains(style));

        result = renderer.processInlineStyles(facesContext, menuItem, true);
        assertTrue(result.contains(selectStyle));
        assertTrue(!result.contains(style));

        menuItem.getAttributes().put("style", style);
        result = renderer.processInlineStyles(facesContext, menuItem, true);
        assertTrue(result.contains(selectStyle));
        assertTrue(result.contains(style));
    }

    /**
     * Test for getComponentClass methods.
     */
    public void testGetComponentClassMethod() throws Exception {

        assertEquals(renderer.getComponentClass(), UIMenuItem.class);
        MenuGroupRendererBase menuGroupRenderer = new MenuGroupRendererBase();
        assertEquals(menuGroupRenderer.getComponentClass(), UIMenuGroup.class);
    }

    /**
     * Test for getStringAttributeOrEmptyString method.
     */
    public void testGetStringAttributeOrEmptyStringMethod() throws Exception {
        menuItem.getAttributes().put("existsAttribute", "attributeValue");
        assertTrue(renderer.getStringAttributeOrEmptyString(menuItem,
                "existsAttribute").equalsIgnoreCase("attributeValue"));
        assertTrue(renderer.getStringAttributeOrEmptyString(menuItem,
                "notExistsAttribute").equalsIgnoreCase(""));

    }

}
