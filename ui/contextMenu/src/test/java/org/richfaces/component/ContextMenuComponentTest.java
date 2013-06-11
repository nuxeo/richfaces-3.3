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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for context menu component.
 */
public class ContextMenuComponentTest extends AbstractAjax4JsfTestCase {
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
	private static Set javaScripts = new HashSet();
	private static final String PARAM_SEQUENCE = "{&apos;name&apos;:&apos;value&apos;}";
	
	private UIContextMenu menu = null;
	private HtmlForm form = null;
	private UIOutput output = null;
	private UIMenuItem menuItem = null;
	private UIParameter param = null;
	private UIMenuGroup menuGroup = null;
	
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/json/json-dom.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/context-menu.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/menu.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/available.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/jquery/jquery.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/jquery.utils.js");
	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ContextMenuComponentTest( String testName ) {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent(UIContextMenu.COMPONENT_TYPE, "org.richfaces.component.html.ContextMenu");
    	application.addComponent(UIMenuItem.COMPONENT_TYPE, "org.richfaces.component.html.HtmlMenuItem");
    	application.addComponent(UIMenuGroup.COMPONENT_TYPE, "org.richfaces.component.html.HtmlMenuGroup");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        output = new UIOutput();
        output.setId("output");
        output.setValue("value");
        
        menu = (UIContextMenu)application.createComponent(UIContextMenu.COMPONENT_TYPE);
        menu.setId("contextMenu");
        
        param = new UIParameter();
        param.setId("param");
        param.setName("name");
        param.setValue("value");
        menu.getChildren().add(param);
        
        menuGroup = (UIMenuGroup)application.createComponent(UIMenuGroup.COMPONENT_TYPE);
        
        output.getChildren().add(menu);
        
        menuItem = (UIMenuItem)application.createComponent(UIMenuItem.COMPONENT_TYPE);
        menuItem.setId("menuItem");
        menuItem.setValue("value");
        menuGroup.getChildren().add(menuItem);
        
        form.getChildren().add(output);
    }
    
    public void tearDown() throws Exception {
    	param = null;
    	menuItem = null;
    	menuGroup =  null;
    	menu = null;
    	form = null;
    	output = null;
    	
    	super.tearDown();
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
    
    /**
     * Test common rendering
     * 
     * @throws Exception
     */
    public void testRender() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        HtmlElement div = view.getHtmlElementById(menu.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
        
        HtmlElement script = (HtmlElement) div.getFirstChild();
        assertNotNull(script);
        assertEquals("script", script.getNodeName());
        
        DomNode scriptBody = (DomNode) script.getFirstChild();
        assertNotNull(scriptBody);
        assertTrue(scriptBody instanceof DomText);
        String scriptText = scriptBody.asText();
        assertNotNull(scriptText);
        scriptText = scriptText.replaceAll("\\s", "");
        
        assertTrue(scriptText.startsWith("varcontextMenu=newRichfaces.ContextMenu"));
        assertTrue(scriptText.contains(PARAM_SEQUENCE));
        
        assertNull(script.getNextSibling());
        
        menu.setEvent(null);
        menu.setValueBinding("event", new ValueBinding() {

			public Class getType(FacesContext arg0) throws EvaluationException,
					PropertyNotFoundException {
				return String.class;
			}

			public Object getValue(FacesContext arg0)
					throws EvaluationException, PropertyNotFoundException {
				return null;
			}

			public boolean isReadOnly(FacesContext arg0)
					throws EvaluationException, PropertyNotFoundException {
				return true;
			}

			public void setValue(FacesContext arg0, Object arg1)
					throws EvaluationException, PropertyNotFoundException {
			}
        	
        });
        try {
			renderView();
			assertTrue("Attribute 'event' is not set, but exception isn't thrown!", false);
		} catch (FacesException e) {
			
		}
    }
    
}
