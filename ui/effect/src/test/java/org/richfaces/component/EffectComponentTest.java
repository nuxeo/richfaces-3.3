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
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for simple Component.
 */
public class EffectComponentTest extends AbstractAjax4JsfTestCase {
	private UIForm form = null;
    private UIEffect effect = null;
    private UIComponent panel = null;
    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
    


    static {
	    javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	    javaScripts.add("scripts/scriptaculous/effects.js");
	    javaScripts.add("script/processEffect.js");
	    javaScripts.add("scripts/scriptaculous/scriptaculous.js");
    }
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EffectComponentTest( String testName )
    {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
    	form = new HtmlForm();
    	facesContext.getViewRoot().getChildren().add(form);
    	panel = application.createComponent(UIPanel.COMPONENT_TYPE);
    	panel.setId("panel");
    	effect = (UIEffect)application.createComponent(UIEffect.COMPONENT_TYPE);
    	effect.setId("effect");
    	effect.setFor("panel");
    	effect.setEvent("onclick");
    	effect.setType("Appear");
    	effect.setParams("delay:3.0,duration:0.5");
    	
    	
    	panel.getChildren().add(effect);
    	form.getChildren().add(effect);
    }
    
    public void tearDown() throws Exception {
       	super.tearDown();
       	effect = null;
       	form = null;
    }
    
    public void testRenderComponent() throws Exception {
    	HtmlPage page = renderView();
    	assertNotNull(page);
    	assertNotNull(effect.getEvent());
    	assertNotNull(effect.getEventString());
    }
    
    public void testRenderStyle() throws Exception {
     }
    
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertNotNull(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue());
    }

}
