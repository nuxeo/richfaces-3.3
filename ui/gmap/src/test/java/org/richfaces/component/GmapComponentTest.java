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
public class GmapComponentTest extends AbstractAjax4JsfTestCase {
	private UIForm form = null;
    private UIComponent gmap = null;
    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = false;
    
    private final static String TEST_STYLE = "width:500px; height:400px";

    static {
	    javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	    javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
	    javaScripts.add("script/gmap.js");
	    javaScripts.add("http://maps.google.com");
    }
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GmapComponentTest( String testName )
    {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
    	form = new HtmlForm();
    	facesContext.getViewRoot().getChildren().add(form);
    	
    	gmap = application.createComponent(UIGmap.COMPONENT_TYPE);
    	gmap.setId("gmap");
    	
    	gmap.getAttributes().put("style", TEST_STYLE);
    	gmap.getAttributes().put("showGLargeMapControl", "true");
    	gmap.getAttributes().put("showGMapTypeControl", "true");
    	gmap.getAttributes().put("gmapVar", "testGmap");
    	
    	form.getChildren().add(gmap);
    }
    
    public void tearDown() throws Exception {
       	super.tearDown();
       	gmap = null;
       	form = null;
    }
    
    public void testRendcerComponent() throws Exception {
    	HtmlPage renderedView = renderView();
    	
    	HtmlElement htmlGmap = renderedView.getHtmlElementById(gmap.getClientId(facesContext));
    	
    	assertNotNull(htmlGmap);
    	assertTrue("div".equals(htmlGmap.getTagName()));
    	assertEquals(htmlGmap.getAttributeValue("style"), TEST_STYLE);

    	assertTrue(htmlGmap.getAttributeValue("class").contains("dr-gmap"));
    	assertTrue(htmlGmap.getAttributeValue("class").contains("rich-gmap"));

    }
    
    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/gmap.xcss"));
    }
    
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertNotNull(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue());
    }
}
