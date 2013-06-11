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
import javax.faces.event.PhaseId;

import org.ajax4jsf.event.EventsQueue;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockViewRoot;
import org.richfaces.component.html.HtmlMenuItem;
import org.richfaces.renderkit.html.images.background.MenuListBackground;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for MenuItem Component.
 */
public class MenuItemComponentTest extends AbstractAjax4JsfTestCase {
    
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
        javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
        javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/form.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/menu.js");
    }

    private UIMenuItem menuItem;

    
    private UIForm form;

    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MenuItemComponentTest(String testName) {
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
        
        menuItem = (UIMenuItem) application
                .createComponent(HtmlMenuItem.COMPONENT_TYPE);
        menuItem.setId("menuItem");
        menuItem.setValue("Menu Item");

        menu.getChildren().add(menuItem);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        menuItem = null;
        form = null;
    }

    /**
     * MenuItem rendering test.
     */
    public void testMenuItemRender() throws Exception {
        menuItem.setSubmitMode(MenuComponent.MODE_NONE);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement div = page.getHtmlElementById(menuItem
                .getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
    }

    /**
     * MenuItem facet test.
     */
    public void testMenuItemIconFacet() throws Exception {
        UIComponent text = createComponent(HtmlOutputText.COMPONENT_TYPE,
                HtmlOutputText.class.getName(), null, null, null);
        menuItem.getFacets().put("icon", text);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuItem
                .getClientId(facesContext)
                + ":icon");
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        assertNotNull(span);
    }

    /**
     * MenuItem facet test.
     */
    public void testMenuItemDisabledIconFacet() throws Exception {
        UIComponent text = createComponent(HtmlOutputText.COMPONENT_TYPE,
                HtmlOutputText.class.getName(), null, null, null);
        menuItem.getFacets().put("iconDisabled", text);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuItem
                .getClientId(facesContext)
                + ":icon");
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        assertNotNull(span);
    }

    /**
     * Test for MenuItem disabled component.
     */
    public void testDisabledMenuItem() throws Exception {
        menuItem.setDisabled(true);
        menuItem.getAttributes().put("iconDisabled", "menuItemIconDisabled");
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement span = page.getHtmlElementById(menuItem
                .getClientId(facesContext)
                + ":anchor");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        String classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-item-label"));
        assertTrue(classAttr.contains("rich-menu-item-label-disabled"));

        span = page.getHtmlElementById(menuItem.getClientId(facesContext)
                + ":icon");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("dr-menu-icon"));
        assertTrue(classAttr.contains("rich-menu-item-icon"));
        assertTrue(classAttr.contains("dr-menu-icon-disabled"));
        assertTrue(classAttr.contains("rich-menu-item-icon-disabled"));

        HtmlElement div = page.getHtmlElementById(menuItem
                .getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        classAttr = div.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-item"));
        assertTrue(classAttr.contains("rich-menu-item-disabled"));
        
        classAttr = div.getAttributeValue("onmouseout");
        assertEquals(0, classAttr.length());
        classAttr = div.getAttributeValue("onmouseover");
        assertEquals(0, classAttr.length());
    }

    /**
     * Test for enabled MenuItem component in "server" mode.
     */
    public void testEnabledMenuItemServerMode() throws Exception {
        assertEquals(false, menuItem.isDisabled());
        menuItem.getAttributes().put("iconClass", "iconClass");
        menuItem.setSubmitMode(MenuComponent.MODE_SERVER);
        menuItem.getAttributes().put("target", "target");
        menuItem.getAttributes().put("selectStyle", "menuItemSelectStyle");
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement anchor = page.getHtmlElementById(menuItem
                .getClientId(facesContext)
                + ":anchor");
        assertNotNull(anchor);
        assertEquals(HTML.SPAN_ELEM, anchor.getNodeName());
        String classAttr = anchor.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-item-label"));

        HtmlElement span = page.getHtmlElementById(menuItem
                .getClientId(facesContext)
                + ":icon");
        assertNotNull(span);
        assertEquals(HTML.SPAN_ELEM, span.getNodeName());
        classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("dr-menu-icon"));
        assertTrue(classAttr.contains("rich-menu-item-icon"));
        
        assertTrue(classAttr.contains((String) menuItem.getAttributes().get("iconClass")));

        HtmlElement div = page.getHtmlElementById(menuItem.getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        classAttr = div.getAttributeValue(HTML.class_ATTRIBUTE);
        assertTrue(classAttr.contains("rich-menu-item"));
        assertTrue(classAttr.contains("rich-menu-item-enabled"));
        
        String onclickAttr = div.getAttributeValue(HTML.onclick_ATTRIBUTE);
        assertNotNull(onclickAttr);
        assertTrue(onclickAttr.length() > 0);
        classAttr = div.getAttributeValue(HTML.onmouseover_ATTRIBUTE);
        assertTrue(classAttr.contains("menuItemSelectStyle"));
    }

    /**
     * Test for enabled MenuItem component in "ajax" mode.
     */
    public void testEnabledMenuItemAJAXMode() throws Exception {
        menuItem.setSubmitMode(MenuComponent.MODE_AJAX);
        menuItem.getAttributes().put("style", "menuItemStyle");
        HtmlPage page = renderView();
        assertNotNull(page);
        
        HtmlElement div = page.getHtmlElementById(menuItem.getClientId(facesContext));
        assertNotNull(div);
        String onclickAttr = div.getAttributeValue(HTML.onclick_ATTRIBUTE);
        assertTrue(onclickAttr.contains("AJAX"));
        String styleAttr = div.getAttributeValue(HTML.style_ATTRIBUTE);
        assertTrue(styleAttr.contains("menuItemStyle"));
    }

    /**
     * Test for enabled MenuItem component in "none" mode.
     */
    public void testEnabledMenuItemNoneMode() throws Exception {
        menuItem.setSubmitMode(MenuComponent.MODE_NONE);
        HtmlPage page = renderView();
        assertNotNull(page);

        HtmlElement div = page.getHtmlElementById(menuItem.getClientId(facesContext));
        assertNotNull(div);
        String classAttr = div.getAttributeValue(HTML.onclick_ATTRIBUTE);
        assertNotNull(classAttr);
        assertTrue(classAttr.length() > 0);
    }

    /**
     * CSS link test.
     */
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List<HtmlElement> links = page.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.LINK_ELEMENT);
        
        assertNotNull(links);
        HtmlElement link = links.get(0);
        assertTrue(link.getAttributeValue(HTML.HREF_ATTR).contains("menucomponents.xcss"));
    }

    /**
     * Scripts link test.
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }

    /**
     * DoDecode method trst. Component in "AJAX" mode. Immediate is true.
     */
    public void testMenuItemDoDecodeAJAXMode() throws Exception {

        menuItem.setImmediate(true);
        menuItem.setSubmitMode(MenuComponent.MODE_AJAX);

        externalContext.getRequestParameterMap().put(
                menuItem.getClientId(facesContext), "Action");
        menuItem.decode(facesContext);

        MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
        EventsQueue events = mockViewRoot.getEventsQueue(PhaseId.APPLY_REQUEST_VALUES);
        assertNotNull(events);
        assertEquals(1, events.size());
    }

    /**
     * DoDecode method trst. Component in "server" mode. Immediate is false.
     */
    public void testMenuItemDoDecodeServerMode() throws Exception {

        menuItem.setImmediate(false);
        menuItem.setSubmitMode(MenuComponent.MODE_SERVER);

        externalContext.getRequestParameterMap().put(
                menuItem.getClientId(facesContext) + ":hidden", "Action");
        menuItem.decode(facesContext);

        MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
        EventsQueue events = mockViewRoot.getEventsQueue(PhaseId.INVOKE_APPLICATION);
        assertNotNull(events);
        assertEquals(1, events.size());
    }

    /**
     * Test accessibility of static image resource.
     */
    public void testMenuItemDefaultIconImage() throws Exception {

        renderView();
        ImageInfo info = getImageResource("org/richfaces/renderkit/html/images/spacer.gif");
		assertNotNull(info);
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
    }

    public void testRenderImages() throws Exception {
        renderView();
        ImageInfo info = getImageResource(MenuListBackground.class.getName());
		assertNotNull(info);
        assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
    }

}
