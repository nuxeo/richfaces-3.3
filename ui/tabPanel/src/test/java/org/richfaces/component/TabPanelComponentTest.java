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
 * Unit test for TabPanel component.
 */
public class TabPanelComponentTest extends AbstractAjax4JsfTestCase {
    private static Set javaScripts = new HashSet();

    static {
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org.ajax4jsf.javascript.ImageCacheScript");
        javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
        javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
        javaScripts.add("scripts/tabPanel.js");
    }

    private UITabPanel tabPanel;
    private UITab tab1;
    private UITab tab2;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TabPanelComponentTest(String testName) {
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
        tabPanel.setId("tabPanel");
        form.getChildren().add(tabPanel);

        tab1 = (UITab) application.createComponent("org.richfaces.Tab");
        tab1.setId("tab1");
        tabPanel.getChildren().add(tab1);

        tab2 = (UITab) application.createComponent("org.richfaces.Tab");
        tab2.setId("tab2");
        tab2.setActive(false);
        tabPanel.getChildren().add(tab2);
    }
    
    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        tabPanel = null;
        tab1 = null;
        tab2 = null;
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

        HtmlElement table = page.getHtmlElementById(tabPanel.getClientId(facesContext));
        assertNotNull(table);
        assertEquals("table", table.getNodeName());

        String classAttr = table.getAttributeValue("class");
        assertTrue(classAttr.contains("rich-tabpanel"));

        HtmlElement cell1 = page.getHtmlElementById(tab1.getClientId(facesContext) + "_cell");
        assertNotNull(cell1);
        assertEquals("td", cell1.getNodeName());

        String classAttr1 = cell1.getAttributeValue("class");
        assertTrue(classAttr1.contains("dr-tbpnl-tbcell-act"));
        assertTrue(classAttr1.contains("rich-tabhdr-cell-active"));

        HtmlElement label1 = page.getHtmlElementById(tab1.getClientId(facesContext) + "_lbl");
        assertNotNull(label1);
        assertEquals("td", label1.getNodeName());

        assertEquals("RichFaces.overTab(this);", label1.getAttributeValue("onmouseover"));
        assertEquals("RichFaces.outTab(this);", label1.getAttributeValue("onmouseout"));

        String classAttrL1 = label1.getAttributeValue("class");
        assertTrue(classAttrL1.contains("dr-tbpnl-tb"));
        assertTrue(classAttrL1.contains("rich-tab-header"));
        assertTrue(classAttrL1.contains("dr-tbpnl-tb-act"));
        assertTrue(classAttrL1.contains("rich-tab-active"));

        HtmlElement cell2 = page.getHtmlElementById(tab2.getClientId(facesContext) + "_cell");
        assertNotNull(cell2);
        assertEquals("td", cell2.getNodeName());

        String classAttr2 = cell2.getAttributeValue("class");
        assertTrue(classAttr2.contains("dr-tbpnl-tbcell-inact"));
        assertTrue(classAttr2.contains("rich-tabhdr-cell-inactive"));

        HtmlElement label2 = page.getHtmlElementById(tab2.getClientId(facesContext) + "_lbl");
        assertNotNull(label2);
        assertEquals("td", label2.getNodeName());

        assertEquals("RichFaces.overTab(this);", label1.getAttributeValue("onmouseover"));
        assertEquals("RichFaces.outTab(this);", label1.getAttributeValue("onmouseout"));

        String classAttrL2 = label2.getAttributeValue("class");
        assertTrue(classAttrL2.contains("dr-tbpnl-tb"));
        assertTrue(classAttrL2.contains("rich-tab-header"));
        assertTrue(classAttrL2.contains("dr-tbpnl-tb-inact"));
        assertTrue(classAttrL2.contains("rich-tab-inactive"));

        HtmlElement tab = page.getHtmlElementById(tab1.getClientId(facesContext));
        assertNotNull(tab);
        assertEquals("td", tab.getNodeName());
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
        assertTrue(link.getAttributeValue("href").contains("css/tabPanel.xcss"));
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List scripts = page.getDocumentElement().getHtmlElementsByTagName("script");
        for (Iterator it = scripts.iterator(); it.hasNext();) {
            HtmlScript item = (HtmlScript) it.next();
            String srcAttr = item.getSrcAttribute();

            if (StringUtils.isNotBlank(srcAttr)) {
                boolean found = false;
                for (Iterator srcIt = javaScripts.iterator(); srcIt.hasNext();)
                {
                    String src = (String) srcIt.next();

                    found = srcAttr.contains(src);
                    if (found) {
                        break;
                    }
                }

                assertTrue(found);
            }
        }
    }

    public void testRenderImages() throws Exception {
        HtmlPage view = renderView();
        assertNotNull(view);
        assertNotNull(getResourceIfPresent("css/tabPanel.xcss"));
        String[] resources = new String[]{
                "org.richfaces.renderkit.images.TabStripeImage"
        };

        for (int i = 0; i < resources.length; i++) {
        ImageInfo info = getImageResource(resources[i]);
        	assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        }
        
        String[] pngResources = new String[]{
                "org.richfaces.renderkit.images.TabGradientA",
                "org.richfaces.renderkit.images.TabGradientB",
        };
        
        for (int i = 0; i < pngResources.length; i++) {
           ImageInfo info = getImageResource(pngResources[i]);
            assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
        }
     }
}
