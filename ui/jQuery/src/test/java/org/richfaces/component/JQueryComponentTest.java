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

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for simple Component.
 */
public class JQueryComponentTest extends AbstractAjax4JsfTestCase {
	private UIForm form = null;
    private UIJQuery jq = null;
    private static Set javaScripts = new HashSet();
    

    static {
	    javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
	    javaScripts.add("script/query.js");
    }
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JQueryComponentTest( String testName )
    {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
    	form = new HtmlForm();
    	facesContext.getViewRoot().getChildren().add(form);
    	
    	jq = (UIJQuery) application.createComponent(UIJQuery.COMPONENT_TYPE);
    	jq.setId("jq");
    	jq.getAttributes().put("query", "form");
    	form.getChildren().add(jq);
    }
    
    public void tearDown() throws Exception {
       	super.tearDown();
       	jq = null;
       	form = null;
    }
    
    public void testRendcerComponent() throws Exception {
    	HtmlPage renderedView = renderView();
    	List scripts = renderedView.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.SCRIPT_ELEM);
    	assertNotNull(scripts);
    	assertFalse(scripts.isEmpty());
    	//HtmlElement htmlGmap = renderedView.getHtmlElementById(jq.getClientId(facesContext));
    	

    }
    
    public void testRenderStyle() throws Exception {
    }
    
    public void testRenderScript() throws Exception {
    }
}
