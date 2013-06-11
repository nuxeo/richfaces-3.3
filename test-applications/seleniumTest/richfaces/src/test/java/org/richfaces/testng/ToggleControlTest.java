/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 

package org.richfaces.testng;


import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ToggleControlTest extends SeleniumTestBase {
	
    private static final String RESET_METHOD = "#{panelBean.cleanValues}";
    
    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }
    
    @Test
	public void testRendered(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Test component render attribute");
    	autoTester.testRendered();
	}
    
    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
    }
    
	@Test
    public void testStandardAttributes(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	Map <String, String> styleAttributes = new HashMap<String, String>();
    	styleAttributes.put("width", "200px");
    	styleAttributes.put("background-color", "blue");
    	styleAttributes.put("color", "yellow");
       	autoTester.testStyleAndClasses(new String[]{"className"}, styleAttributes);
        writeStatus("Test component html events");
    	autoTester.testHTMLEvents();
    }
	
	@Test
	public void testImmediate(Template template) {
	   	AutoTester autoTester = getAutoTester(this);
	   	autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Test component immediate attribute");
	   	autoTester.testImmediate(false);
	}
	
	    
	@Test
	public void testImmediateWithExternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		writeStatus("Test immediate attribute with external validation failed");
		tester.testImmediateWithExternalValidationFailed();
	}
	
	@Test
	public void testAjaxSingle(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		writeStatus("Test ajaxSingle attribute");
		tester.testAjaxSingle();
	}
	
	@Test
	public void testAjaxSingleWithProcessExternalValidation(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);

		writeStatus("Test ajaxSingle attribute with external validation failed");
		tester.testAjaxSingleWithProcesExternalValidation(true);
	}
	
	@Test
	public void testAjaxSingleWithInternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		writeStatus("Test ajaxSingle attribute with internal validation failed");
		tester.testAjaxSingleWithInternalValidationFailed();
	}
	
	@Test
	public void testBypassUpdatesAttribute(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		writeStatus("Test component with bypassUpdates = true skips update model values phase");
		tester.testBypassUpdate();
	}
	
	@Test
	public void testLimitToListAttribute(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
		tester.testLimitToList();
	}
	
	@Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }
	
	@Test
    public void testNestedActionListener(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test nested listener");
        tester.testNestedActionListener();
    }
	
	@Test
	public void testComponentLayout(Template template) {
		renderPage(template, RESET_METHOD);
        
		String parentId = getParentId() + "mainForm:";
        String controlId = parentId + "ajax_next";
		
        String controlHtml = getHTMLById(controlId);
		if(!"Ajax next".equals(controlHtml)){
			Assert.fail("toggle control should be output as link with text defined as value attribute");
		}
		
		int i = selenium.getXpathCount("//a[@id='"+controlId+"']").intValue();
		if (i != 1) {
			Assert.fail("toggle control should be rendered as <a> tag");
		}
	}
	
	@Override
	public void sendAjax() {
		clickAjaxCommandAndWait(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID));
	}
	
	@Override
	public void setInternalValidationFailed() {
		String childCompId = getAutoTester(this).getClientId("") + "child";
		setValueById(childCompId, "");
	}
	
	@Override
	public String getTestUrl() {
        return "pages/togglePanel/togglePanelTest.xhtml";
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/toggleControl/toggleControlAutoTest.xhtml";
	}

}
