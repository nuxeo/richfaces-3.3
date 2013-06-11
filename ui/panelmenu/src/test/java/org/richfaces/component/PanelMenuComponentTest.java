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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.faces.application.ActionListenerImpl;

/**
 * Unit test for simple Component.
 */
public class PanelMenuComponentTest extends AbstractAjax4JsfTestCase {
	private static Set javaScripts = new HashSet();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
	
	private String[] imageResources = new String[]{
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconChevron",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconChevronDown",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconChevronUp",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconDisc",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconGrid",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconSpacer",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangle",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangleDown",
            "org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangleUp"
    };
	
	static {
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/panelMenu.js");
        javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/form.js");
    }
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PanelMenuComponentTest( String testName )
    {
        super( testName );
    }

    private UIForm form;
    private UIPanelMenu panelMenu;
    private UIPanelMenuGroup group1;
    private UIPanelMenuGroup group2;
    private UIPanelMenuGroup group3;
    private UIPanelMenuItem item1;
    private UIPanelMenuItem item2;
    
    public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent("org.richfaces.panelMenu", "org.richfaces.component.html.HtmlPanelMenu");
    	application.addComponent("org.richfaces.panelMenuGroup", "org.richfaces.component.html.HtmlPanelMenuGroup");
    	application.addComponent("org.richfaces.panelMenuItem", "org.richfaces.component.html.HtmlPanelMenuItem");
    	
    	form = new HtmlForm();
    	form.setId("form");
    	facesContext.getViewRoot().getChildren().add(form);
    	
    	panelMenu = (UIPanelMenu)application.createComponent("org.richfaces.panelMenu");
    	panelMenu.setId("panelMenu");
    	form.getChildren().add(panelMenu);
    	
    	group1 = (UIPanelMenuGroup)application.createComponent("org.richfaces.panelMenuGroup");
    	group1.setId("group1");
    	group1.setDisabled(true);
    	panelMenu.getChildren().add(group1);
    	
    	group2 = (UIPanelMenuGroup)application.createComponent("org.richfaces.panelMenuGroup");
    	group2.setId("group2");
    	panelMenu.getChildren().add(group2);
    	
    	item1 = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item1.setId("item1");
    	group1.getChildren().add(item1);
    	
    	group3 = (UIPanelMenuGroup)application.createComponent("org.richfaces.panelMenuGroup");
    	group3.setId("subgroup");
    	group2.getChildren().add(group3);
    	
    	item2 = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item2.setId("item2");
    	item2.setDisabled(true);
    	group2.getChildren().add(item2);
    	
    	// Items for testing standard icons.
    	UIPanelMenuItem item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("triangleItem");
    	item.setIcon("triangle");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("spacerItem");
    	item.setIcon("spacer");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("triangleDownItem");
    	item.setIcon("triangleDown");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("triangleUpItem");
    	item.setIcon("triangleUp");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("chevronItem");
    	item.setIcon("chevron");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("chevronUpItem");
    	item.setIcon("chevronUp");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("chevronDownItem");
    	item.setIcon("chevronDown");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("discItem");
    	item.setIcon("disc");
    	group2.getChildren().add(item);
    	
    	item = (UIPanelMenuItem)application.createComponent("org.richfaces.panelMenuItem");
    	item.setId("gridItem");
    	item.setIcon("grid");
    	group2.getChildren().add(item);
    	
    }
    
    public void tearDown() throws Exception {
    	super.tearDown();
    	
    	item1 = null;
    	item2 = null;
    	group1 = null;
    	group2 = null;
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
        
        HtmlElement div = page.getHtmlElementById(panelMenu.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
        
        HtmlElement firstGroupDiv = page.getHtmlElementById(group1.getClientId(facesContext));
        assertNotNull(firstGroupDiv);
        assertEquals("div", firstGroupDiv.getNodeName());
        
        String styleClass = firstGroupDiv.getAttributeValue("class");
        assertTrue(styleClass.contains("dr-pmenu-top-group-div"));
        
        HtmlElement firstGroupHide = page.getHtmlElementById("tablehide" + group1.getClientId(facesContext));
        assertNotNull(firstGroupHide);
        assertEquals("table", firstGroupHide.getNodeName());
        
        styleClass = firstGroupHide.getAttributeValue("class");
        assertTrue(styleClass.contains("dr-pmenu-top-group"));
        assertTrue(styleClass.contains("rich-pmenu-group"));
        assertTrue(styleClass.contains("rich-pmenu-disabled-element"));
        assertTrue(styleClass.contains("dr-pmenu-disabled-element"));
        
        HtmlElement subGroupDiv = page.getHtmlElementById(group3.getClientId(facesContext));
        assertNotNull(subGroupDiv);
        assertEquals("div", subGroupDiv.getNodeName());
        
        HtmlElement subGroupHide = page.getHtmlElementById("tablehide" + group3.getClientId(facesContext));
        assertNotNull(subGroupHide);
        assertEquals("table", subGroupHide.getNodeName());
        
        styleClass = subGroupHide.getAttributeValue("class");
        assertTrue(styleClass.contains("dr-pmenu-group"));
        assertTrue(styleClass.contains("rich-pmenu-group"));
        
        HtmlElement leftIcon = page.getHtmlElementById("leftIcon" + group1.getClientId(facesContext));
        assertNotNull(leftIcon);
        assertEquals("img", leftIcon.getNodeName());
        
        HtmlElement rightIcon = page.getHtmlElementById("rightIcon" + group1.getClientId(facesContext));
        assertNotNull(rightIcon);
        assertEquals("img", rightIcon.getNodeName());
        
        HtmlElement firstItemHide = page.getHtmlElementById("tablehide" + item1.getClientId(facesContext));
        assertNotNull(firstItemHide);
        assertEquals("table", firstItemHide.getNodeName());
        
        styleClass = firstItemHide.getAttributeValue("class");
        assertTrue(styleClass.contains("dr-pmenu-item"));
        assertTrue(styleClass.contains("rich-pmenu-item"));
        
        leftIcon = page.getHtmlElementById("leftIcon" + item1.getClientId(facesContext));
        assertNotNull(leftIcon);
        assertEquals("img", leftIcon.getNodeName());
        
        rightIcon = page.getHtmlElementById("rightIcon" + item1.getClientId(facesContext));
        assertNotNull(rightIcon);
        assertEquals("img", rightIcon.getNodeName());
        
        HtmlElement secondItemHide = page.getHtmlElementById("tablehide" + item2.getClientId(facesContext));
        assertNotNull(secondItemHide);
        assertEquals("table", secondItemHide.getNodeName());
        
        styleClass = secondItemHide.getAttributeValue("class");
        assertTrue(styleClass.contains("rich-pmenu-disabled-element"));
    }
    
    /**
     * Test style rendering
     *
     * @throws Exception
     */
    public void testRenderStyle() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        List links = view.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/panelMenu.xcss"));
        assertNotNull(getResourceIfPresent("org/richfaces/renderkit/html/css/panelMenu.xcss"));
    }
    
    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
    	HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }
    
    public void testRenderImages() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);

        for (int i = 0; i < imageResources.length; i++) {
        	ImageInfo info = getImageResource(imageResources[i]);
    		assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        }
    }
    
    public void testAddRemoveListener() throws Exception {
    	ActionListener listener = new ActionListenerImpl();
    	group2.addActionListener(listener);
    	HtmlPage view = renderView();
        assertNotNull(view);

        assertNotNull(group2.getActionListeners());
        assertTrue(group2.getActionListeners().length > 0);
        assertEquals(listener, group2.getActionListeners()[0]);
        group2.removeActionListener(listener);
        assertTrue(group2.getActionListeners().length == 0);
    }
    
    public void testBroadcast() throws Exception {
    	group3.addActionListener(testListener);
    	HtmlPage view = renderView();
        assertNotNull(view);
        group3.broadcast(new ActionEvent(group3));
        assertTrue(facesContext.getMessages(group3.getClientId(facesContext)).hasNext());
    }
    
    public void testQueueEvent() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        group3.queueEvent(new ActionEvent(group3));
    }
    
    ActionListener testListener = new ActionListener(){

		public void processAction(ActionEvent event)
				throws AbortProcessingException {
			facesContext.addMessage(group3.getClientId(facesContext), new FacesMessage("Method invoked!"));
		}
    	
    };
}
