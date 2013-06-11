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

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlMenuGroup;
import org.richfaces.renderkit.html.images.MenuNodeImage;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for MenuGroup Component.
 */
public class MenuGroupComponentTest extends AbstractAjax4JsfTestCase {

	private static class UIMenu extends UIComponentBase implements MenuComponent {
		@Override
		public String getFamily() {
			return null;
		}
		public String getSubmitMode() {
			return null;
		}
		public void setSubmitMode(String submitMode) {
		}
	}

	private static Set<String> javaScripts = new HashSet<String>();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

    static {
        javaScripts.add("PrototypeScript");
        javaScripts.add("AjaxScript");
    }

    private UIMenuGroup menuGroup;

    private UIForm form;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MenuGroupComponentTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);

        UIMenu menu = new UIMenu();
        menu.setId("menu");
        form.getChildren().add(menu);

        menuGroup = (HtmlMenuGroup) application
                .createComponent(HtmlMenuGroup.COMPONENT_TYPE);
        menuGroup.setId("menuGroup");
        menuGroup.setValue("Menu Group");

        menu.getChildren().add(menuGroup);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        menuGroup = null;
        form = null;
    }

    /**
     * Rigourous Test :-)
     */
    public void testMenuGroupRender() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement div = page.getHtmlElementById(menuGroup.getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());

    }

    /**
     * MenuGroup icon test.
     */
    public void testMenuGroupIcon() throws Exception {
        menuGroup.setDisabled(false);
        menuGroup.getFacets().clear();
        menuGroup.getAttributes().put("icon", "menuGroupIcon");
        HtmlPage page = renderView();
        
        assertNotNull(page);
        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        List<HtmlElement> images = span.getHtmlElementsByTagName(HTML.IMG_ELEMENT);
        assertNotNull(images);
        assertEquals(1, images.size());
    }

    /**
     * MenuGroup icon test.
     */
    public void testMenuGroupIconDisabled() throws Exception {
        menuGroup.setDisabled(true);
        menuGroup.getAttributes().put("iconDisabled", "menuGroupIcon");
        HtmlPage page = renderView();

        assertNotNull(page);
        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        assertNotNull(span);
        List<HtmlElement> images = span.getHtmlElementsByTagName(HTML.IMG_ELEMENT);
        assertNotNull(images);
        assertEquals(1, images.size());
    }

    public void testMenuGroupIconFacet() throws Exception {
        menuGroup.setDisabled(false);
        UIComponent facet = createComponent(HtmlOutputText.COMPONENT_TYPE,
                HtmlOutputText.class.getName(), null, null, null);
        menuGroup.getFacets().put("icon", facet);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        assertNotNull(span);
    }

    public void testMenuGroupIconFacetDisabled() throws Exception {
        menuGroup.setDisabled(true);
        UIComponent facet = createComponent(HtmlOutputText.COMPONENT_TYPE,
                HtmlOutputText.class.getName(), null, null, null);
        menuGroup.getFacets().put("iconDisabled", facet);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        assertNotNull(span);
    }

    public void testDisabledMenuItem() throws Exception {
        menuGroup.setDisabled(true);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        String classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);

        HtmlElement div = page.getHtmlElementById(menuGroup.getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        classAttr = div.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-group-disabled"));
        assertTrue(classAttr.contains("rich-menu-group"));

    }

    public void testEnabledMenuItem() throws Exception {
        assertEquals(false, menuGroup.isDisabled());
        menuGroup.getAttributes().put("iconClass", "iconClass");
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement anchor = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":anchor");
        assertNotNull(anchor);
        assertEquals(HTML.SPAN_ELEM, anchor.getNodeName());
        String classAttr = anchor.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-item-label"));

        HtmlElement span = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":icon");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("dr-menu-icon"));
        assertTrue(classAttr.contains("rich-menu-item-icon"));
        assertTrue(classAttr.contains((String) menuGroup.getAttributes().get(
                "iconClass")));

        HtmlElement div = page.getHtmlElementById(menuGroup.getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        classAttr = div.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr
                .contains("rich-menu-group rich-menu-group-enabled"));

        div = page.getHtmlElementById(menuGroup.getClientId(facesContext) + ":folder");
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        classAttr = div.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("dr-menu-node"));
        assertTrue(classAttr.contains("dr-menu-node-icon"));
        assertTrue(classAttr.contains("rich-menu-item-folder"));

    }

    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List<HtmlElement> links = page.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.LINK_ELEMENT);

        assertNotNull(links);
        HtmlElement link = links.get(0);
        assertTrue(link.getAttributeValue(HTML.HREF_ATTR).contains(
                "menucomponents.xcss"));
    }

    /**
     * Scripts link test.
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }

    public void testRenderImages() throws Exception {
        renderView();
        ImageInfo info = getImageResource(MenuNodeImage.class.getName());
		assertNotNull(info);
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
    }
    
}
