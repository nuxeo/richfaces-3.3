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

import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for ToolBar component.
 */
public class ToolBarComponentTest extends AbstractAjax4JsfTestCase {

    private UIToolBar toolBar;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ToolBarComponentTest(String testName) {
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

        toolBar = (UIToolBar) application.createComponent("org.richfaces.ToolBar");
        toolBar.setId("toolBar");
        toolBar.setItemSeparator("square");
        form.getChildren().add(toolBar);

        UICommand button = (UICommand) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBar.getChildren().add(button);

        button = (UICommand) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBar.getChildren().add(button);

        UIToolBarGroup toolBarGroup = (UIToolBarGroup) application.createComponent("org.richfaces.ToolBarGroup");
        toolBarGroup.setItemSeparator("disc");
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        toolBar.getChildren().add(toolBarGroup);

        toolBarGroup = (UIToolBarGroup) application.createComponent("org.richfaces.ToolBarGroup");
        toolBarGroup.setItemSeparator("grid");
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        toolBar.getChildren().add(toolBarGroup);

        toolBarGroup = (UIToolBarGroup) application.createComponent("org.richfaces.ToolBarGroup");
        toolBarGroup.setItemSeparator("line");
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        button = (HtmlCommandButton) application.createComponent(HtmlCommandButton.COMPONENT_TYPE);
        button.setValue("Change");
        toolBarGroup.getChildren().add(button);
        toolBar.getChildren().add(toolBarGroup);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        toolBar = null;
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

        HtmlElement table = page.getHtmlElementById(form.getId() + ":" + toolBar.getId());
        assertNotNull(table);
        assertEquals("table", table.getNodeName());

        String classAttr = table.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-toolbar-ext"));
        assertTrue(classAttr.contains("rich-toolbar"));
    }

    /**
     * Test style rendering
     *
     * @throws Exception
     */
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        Assert.assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/toolBar.xcss"));
    }

    public void testRenderImages() throws Exception {
        renderView();
        assertNotNull(getResourceIfPresent("css/toolBar.xcss"));
        String[] resources = new String[]{
                "org.richfaces.renderkit.html.images.DotSeparatorImage",
                "org.richfaces.renderkit.html.images.GridSeparatorImage",
                "org.richfaces.renderkit.html.images.LineSeparatorImage",
                "org.richfaces.renderkit.html.images.SquareSeparatorImage"
        };

        for (int i = 0; i < resources.length; i++) {
        	ImageInfo info = getImageResource(resources[i]);
    		assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        }
    }
}
