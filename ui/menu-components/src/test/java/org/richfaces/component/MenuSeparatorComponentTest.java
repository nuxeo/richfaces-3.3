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

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlMenuSeparator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for MenuSeparator Component.
 */
public class MenuSeparatorComponentTest 
    extends AbstractAjax4JsfTestCase
{
	
/*	private static Set javaScripts = new HashSet();
	
	static {		
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript()");
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript()");
        javaScripts.add("org.ajax4jsf.util.command.CommandScript()");
	}
*/	
	private UIMenuSeparator menuSeparator;
	private UIForm form;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MenuSeparatorComponentTest(String testName)
    {
        super(testName);
    }
    
    public void setUp() throws Exception {
    	super.setUp();
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);

        menuSeparator = (UIMenuSeparator) application.createComponent(HtmlMenuSeparator.COMPONENT_TYPE);
        menuSeparator.setId("menuSeparator");    	
    	
		form.getChildren().add(menuSeparator);
    }

    public void tearDown() throws Exception {
    	super.tearDown();
    	menuSeparator = null;
    	form = null;
    }
    
    /**
     * MenuSeparator rendering test.
     */
    public void testMenuSeparatorRender() throws Exception
    {
    	HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById(menuSeparator.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
    }
    
    /**
     * MenuSeparator attributes test.
     */
    public void testMenuSeparatorAttributes() throws Exception
    {
    	HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById(menuSeparator.getClientId(facesContext));
        String classAttr = div.getAttributeValue("class");
        assertTrue(classAttr.contains("rich-menu-separator")); 
        classAttr = div.getAttributeValue("id");
        assertEquals(menuSeparator.getClientId(facesContext), classAttr);
    }

}
