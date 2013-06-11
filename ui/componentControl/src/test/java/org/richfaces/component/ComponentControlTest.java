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

import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for simple Component.
 */
public class ComponentControlTest extends AbstractAjax4JsfTestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ComponentControlTest (String testName) {
        super( testName );
    }
    
    private UIForm form = null;
    private UIComponentControl componentControl = null;
    private UIInput input = null;
    private UIOutput output = null;
    public UIParameter param = null;

    public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent(UIComponentControl.COMPONENT_TYPE, "org.richfaces.component.html.HtmlComponentControl");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        input = new UIInput();
        input.setId("input");
        input.setValue("value");
        form.getChildren().add(input);
        
        componentControl =  (UIComponentControl) application.createComponent(UIComponentControl.COMPONENT_TYPE);
        componentControl.setEvent("onclick");
        componentControl.setOperation("testOperation");
        componentControl.setName("testName");
        componentControl.setId("componentControl");
        componentControl.setParams("x:'y'");
        componentControl.setFor("button");
        input.getChildren().add(componentControl);

        output = new UIOutput();
        output.setId("output");
        output.setValue("test");
        form.getChildren().add(output);
        
        param = new UIParameter();
        param.setName("name");
        param.setValue("value");
        componentControl.getChildren().add(param);
    }
    
    public void tearDown() throws Exception {
    	super.tearDown();
    	
    	param = null;
    	input = null;
    	componentControl = null;
    	output = null;
    	form = null; 
    }

    public void testEventString() throws Exception {
    	HtmlPage page = renderView();
    	assertNotNull(page);
    	
    	HtmlInput htmlInput = (HtmlInput)page.getHtmlElementById(input.getClientId(facesContext));
    	assertNotNull(htmlInput);
    	
    	String eventString = htmlInput.getAttributeValue("onclick");
    	assertNotNull(eventString);
    	assertTrue(eventString.contains("Richfaces.componentControl.performOperation"));
    	
    	eventStringParams(eventString);
    }

    public void testAttachStringEmpty() throws Exception {
    	HtmlPage page = renderView();
        assertNotNull(page);
        
        List<?> scripts = page.getByXPath("//script");
        
        String eventString = null;
        for (Object obj : scripts) {
            HtmlElement element = (HtmlElement) obj;
            if (element.asXml().contains("Richfaces.componentControl.attachEvent")) {
                eventString = element.asXml(); 
                break; 
            }
        }
        
        assertNull(eventString);
    }
    
    public void testAttachString() throws Exception {
        this.componentControl.setAttachTo("#output");
    	
    	HtmlPage page = renderView();
        assertNotNull(page);
        
        List<?> scripts = page.getByXPath("//script");
        assertFalse(scripts.isEmpty());
        
        String eventString = null;
        for (Object obj : scripts) {
            HtmlScript element = (HtmlScript) obj;
            if (element.asXml().contains("Richfaces.componentControl.attachEvent")) {
                eventString = element.asXml(); 
                break; 
            }
        }
        
        assertNotNull(eventString);
    }

    private void eventStringParams(String eventString) {
        String [] params = eventString.split(",");
    	assertEquals(6, params.length);
    	
    	assertTrue(params[0].trim().endsWith("event"));
    	assertEquals("'#button'", params[1].trim());
    	assertEquals("'testOperation'", params[2].trim());
    	assertTrue(params[3].trim().startsWith("{"));
    	assertTrue(params[4].trim().endsWith("}"));
    	assertTrue(params[5].trim().endsWith("false)"));
    }
    
    public void testParametersMap() throws Exception {
    	String paramMap = componentControl.getEncodedParametersMap();
    	assertNotNull(paramMap);
    	
    	String [] arr = paramMap.split(",");
    	assertEquals(2, arr.length);
    	
    	String [] arr1 = arr[0].split(":");
    	assertEquals(2, arr1.length);
    	String [] arr2 = arr[1].split(":");
    	assertEquals(2, arr2.length);
    	if ("x".equals(arr1[0].trim())) {
    		assertEquals("'y'", arr1[1].trim());
    		assertEquals("'name'", arr2[0].trim());
    		assertEquals("'value'", arr2[1].trim());
    	} else {
    		assertEquals("'name'", arr1[0].trim());
    		assertEquals("'value'", arr1[1].trim());
    		assertEquals("x", arr2[0].trim());
    		assertEquals("'y'", arr2[1].trim());
    	}
    	
    	param.setName(null);
    	try {
			renderView();
			assertTrue("Parameter name is null, but exception isn't thrown!", false);
		} catch (IllegalArgumentException e) {
			
		}
    }
    
    public void testCheckValidity() throws Exception {
    	HtmlPage page = renderView();
    	assertNotNull(page);
    	
    	componentControl.setAttachTiming("wrong value");
    	try {
			renderView();
			assertTrue("attachTiming attribute has wrong value, but exception isn't thrown!", false);
		} catch (FacesException e) {
			
		}
    	
		componentControl.setAttachTiming("onload");
		componentControl.setOperation("");
		try {
			renderView();
			assertTrue("operation is empty, but exception isn't thrown!", false);
		} catch (FacesException e) {
			
		}
    }
}
