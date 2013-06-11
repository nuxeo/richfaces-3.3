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

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for simple Component.
 */
public class VirtualEarthComponentTest extends AbstractAjax4JsfTestCase {
	private UIForm form = null;
    private UIComponent virtualEarth = null;
    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = false;
    


    static {
	    javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	    javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
	    javaScripts.add("script/virtualEarth.js");
	    javaScripts.add("http://dev.virtualearth.net/mapcontrol/mapcontrol.ashx");
    }
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public VirtualEarthComponentTest( String testName )
    {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
    	form = new HtmlForm();
    	facesContext.getViewRoot().getChildren().add(form);
    	
    	virtualEarth = application.createComponent(UIVirtualEarth.COMPONENT_TYPE);
    	virtualEarth.setId("virtualEarth");
    	
    	virtualEarth.getAttributes().put("var", "testVirtualEarth");
    	
    	form.getChildren().add(virtualEarth);
    }
    
    public void tearDown() throws Exception {
       	super.tearDown();
       	virtualEarth = null;
       	form = null;
    }
    
    public void testRenderComponent() throws Exception {
    	HtmlPage renderedView = renderView();
    	
    	HtmlElement htmlVirtualEarth = renderedView.getHtmlElementById(virtualEarth.getClientId(facesContext));
    	
    	assertNotNull(htmlVirtualEarth);
    	assertTrue("div".equals(htmlVirtualEarth.getTagName()));

    	assertTrue(htmlVirtualEarth.getAttributeValue("class").contains("dr-ve"));
    	assertTrue(htmlVirtualEarth.getAttributeValue("class").contains("rich-virtualEarth"));

    }
    
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/virtualEarth.xcss"));
    }
    
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertNotNull(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue());
    }

}
