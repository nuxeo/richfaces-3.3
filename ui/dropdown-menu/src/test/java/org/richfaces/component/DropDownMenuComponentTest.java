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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for Datascroller component.
 */
public class DropDownMenuComponentTest extends AbstractAjax4JsfTestCase {
    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
    

    static {
    	javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");        
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("org.ajax4jsf.util.command.CommandScript");
        javaScripts.add("scripts/menu.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
        javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/form.js");
    }

    private UIDropDownMenu dropDownMenu;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DropDownMenuComponentTest(String testName) {
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
        dropDownMenu = (UIDropDownMenu)application.createComponent("org.richfaces.DropDownMenu");
        dropDownMenu.setId("DropDownMenu");
        dropDownMenu.getAttributes().put("style", "width:300px");
        dropDownMenu.getAttributes().put("styleClass", "myclass");
        form.getChildren().add(dropDownMenu);

        UIMenuItem item = (UIMenuItem)application.createComponent("org.richfaces.MenuItem");
        item.setId("item11");
        item.setValue("Item 11");
        dropDownMenu.getChildren().add(item);

        UIMenuGroup group = (UIMenuGroup)application.createComponent("org.richfaces.MenuGroup");
        group.setId("group1");
        group.setValue("Group 1");
        dropDownMenu.getChildren().add(group);

        item = (UIMenuItem)application.createComponent("org.richfaces.MenuItem");
        item.setId("item12");
        item.setValue("Item 12");
        dropDownMenu.getChildren().add(item);

        UIMenuSeparator separator = (UIMenuSeparator)application.createComponent("org.richfaces.MenuSeparator");
        separator.setId("separator1");
        dropDownMenu.getChildren().add(separator);

        item = (UIMenuItem)application.createComponent("org.richfaces.MenuItem");
        item.setId("item13");
        item.setValue("Item 13");
        dropDownMenu.getChildren().add(item);

        //---------------------------------------------------------------------
        item = (UIMenuItem)application.createComponent("org.richfaces.MenuItem");
        item.setId("item21");
        item.setValue("Item 21");
        group.getChildren().add(item);

        separator = (UIMenuSeparator)application.createComponent("org.richfaces.MenuSeparator");
        separator.setId("separator2");
        group.getChildren().add(separator);

        item = (UIMenuItem)application.createComponent("org.richfaces.MenuItem");
        item.setId("item22");
        item.setValue("Item 22");
        group.getChildren().add(item);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        dropDownMenu = null;
        form = null;
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
        HtmlElement div = page.getHtmlElementById(dropDownMenu.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
        String classAttr = div.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-menu-label"));
        assertTrue(classAttr.contains("dr-menu-label-unselect"));
        assertTrue(classAttr.contains("rich-ddmenu-label"));
        assertTrue(classAttr.contains("rich-ddmenu-label-unselect"));
        assertTrue(classAttr.contains("myclass"));
        String style = div.getAttributeValue("style");
        assertTrue(style.contains("width:300px"));
    }

    /**
     * Test component not rendering
     *
     * @throws Exception
     */
    public void testNotRender() throws Exception {
        dropDownMenu.setRendered(false);
        HtmlPage page = renderView();
        assertNotNull(page);
        try {
            page.getHtmlElementById(dropDownMenu.getClientId(facesContext));
            assertTrue(false);
        } catch(Exception ex) {
        }        
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
        assertEquals(2, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/dropdownmenu.xcss"));
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertNotNull(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue());
    }

    
    public void testRenderImages() throws Exception {
        renderView();
        assertNotNull(getResourceIfPresent("css/dropdownmenu.xcss"));
        String[] resources = new String[] {
                "org.richfaces.renderkit.html.images.background.MenuListBackground"                                 
        };

        for (int i = 0; i < resources.length; i++) {
        	ImageInfo info = getImageResource(resources[i]);
    		assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
        }
    }
}
