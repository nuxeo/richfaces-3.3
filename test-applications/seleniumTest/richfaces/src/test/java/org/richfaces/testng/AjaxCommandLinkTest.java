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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AjaxCommandLinkTest extends SeleniumTestBase {
	
	static final String TEST_NAVIGATION_URL = "/pages/ajaxCommandLink/testListenerAndNavigation.xhtml";
	
	private static Map<String, String> parameter = new HashMap<String, String>();
	private static Map<String, String> styleAttributes = new HashMap<String, String>();
	static {
		parameter.put("parameter1", "value1");
		parameter.put("parameter2", "value2");
		parameter.put("parameter3", "value3");
		
		styleAttributes.put("color", "blue");
		styleAttributes.put("text-decoration", "underline");
		
	}
	
	private void testEvents (AutoTester tester, Template template) {
		String id = tester.getClientId(AutoTester.COMPONENT_ID, template);
		List<String> events = new ArrayList<String>(); 
		
		events.add("onfocus");
		events.add("onclick");
		clickById(id);
		assertEvents(events);
		
		selenium.mouseOver(id);
		selenium.mouseDown(id);
		selenium.mouseMove(id);
		selenium.mouseUp(id);
		selenium.mouseOut(id);
		assertEvent("onmouseover");
		assertEvent("onmousedown");
		assertEvent("onmousemove");
		assertEvent("onmouseup");
		assertEvent("onmouseout");
		
		selenium.keyDown(id, "a");
		selenium.keyPress(id, "a");
		selenium.keyUp(id, "a");
		assertEvent("onkeydown");
		assertEvent("onkeypress");
		assertEvent("onkeyup");
		
		
	}
	
	@Test
	public void testWithExternalValidation(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testExtrenalValidationFailure();
	}
	
	
	@Test
	public void testAjaxSingleWithProcessExternalValidation(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testAjaxSingleWithProcesExternalValidation(true);
	}
	
	@Test
	public void testAjaxSingle(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testAjaxSingle();
	}
		
	
	@Test
	public void testOutput(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		String componentId = tester.getClientId(AutoTester.COMPONENT_ID);
		if (!"Link".equals(getHTMLById(componentId))) {
			Assert.fail("Command link should be output as link with text defined as value attribute");
		}
		
		int i = selenium.getXpathCount("//a[@id='"+componentId+"']").intValue();
		if (i != 1) {
			Assert.fail("Command link should be rendered as <a> tag");
		}
		
	}
	
	@Test
	public void testNavigationForAjaxSingle(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testAjaxSingle();
		tester.checkNavigation(true);
		tester.checkAction(true);
	}
	
	@Test
	public void testNavigationForImmediate(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testImmediate();
        tester.checkAction(true);
        tester.checkNavigation(true);
	}
	
	@Test
	public void testNavigation(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(TEST_NAVIGATION_URL, template, null);
		
		tester.testActionAndNavigation();
		tester.checkActionListener(true);
		tester.checkNestedActionListener(true);
	
	}
	
	@Test
	public void testHTMLAttributes(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testStyleAndClasses(new String [] {"noname"}, styleAttributes);
		//tester.testHTMLEvents();
		testEvents(tester, template);
		

	}
	
	@Test
	public void testReRender(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		tester.testReRender();
	}
	
	@Test
	public void testBypassUpdates(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		tester.testBypassUpdate();
	}
	
	@Test
	public void testLimitToList(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		tester.testLimitToList();
	}
	
	@Test
	public void testRendered(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		tester.testRendered();
	}
	
	@Test
	public void testNestedParams (Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testRequestParameters(parameter);
	}

    @Test
    public void testAjaxCommandLinkComponent(Template template) {
        renderPage(template, null);

    	String parentId = getParentId() + "_form:";
    	
    	String rerenderId = parentId + "_rerender";    	
    	String rerenderStr = getTextById(rerenderId);

    	String linkId = parentId + "l1";
    	boolean ajaxSingle = false;
    	boolean immediate = false;
    	writeStatus("Click link 1");
    	setValidation(true);
    	clickById(linkId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(linkId, true, ajaxSingle, immediate, true, true, true, true);
    	checkRerendering(rerenderStr, rerenderId);
    	
    	linkId = parentId + "l2";
    	ajaxSingle = false;
    	immediate = false;
    	writeStatus("Click link 2");
    	setValidation(false);
    	clickById(linkId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(linkId, false, ajaxSingle, immediate, false, false, false, false);
    	
    	linkId = parentId + "l3";
    	ajaxSingle = false;
    	immediate = true;
    	writeStatus("Click link 3");
    	setValidation(true);
    	clickById(linkId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(linkId, true, ajaxSingle, immediate, true, true, true, false);
    	
    	linkId = parentId + "l4";
    	ajaxSingle = true;
    	immediate = true;
    	writeStatus("Click link 4");
    	setValidation(false);
    	clickById(linkId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(linkId, true, ajaxSingle, immediate, true, true, true, false);
    	
      
    }
    
    private void setValidation(boolean success) {
    	StringBuffer buffer = new StringBuffer("setValidation(");
    	buffer.append(success);
    	buffer.append(");");
    	runScript(buffer.toString());
    }
    
    private void waitForOnCompleteHndler () {
    	waiteForCondition("_onCompleteHandler == true;", 3000);
    	runScript("_onCompleteHandler = false;");
    }
    
    private void checkRerendering(String oldRerender, String rerenderId) {
    	String newRerender = getTextById(rerenderId);
    	if (oldRerender.equals(newRerender)) {
    		Assert.fail("<a4j:commandButton> failed. Rerender does not work.");
    	}
    }
    
    private void checkButton(String id, boolean testData, boolean ajaxSingle, boolean immediate, boolean testAction, boolean testActionListener, boolean testFListener, boolean testInput) {
    	writeStatus("Checking link...");
    	StringBuffer buffer = new StringBuffer("checkLink('");
    	buffer.append(id);
    	buffer.append("',");
    	buffer.append(testData);
    	buffer.append(",");
    	buffer.append(testAction);
    	buffer.append(",");
    	buffer.append(testActionListener);
    	buffer.append(",");
    	buffer.append(testFListener);
    	buffer.append(",");
    	buffer.append(testInput);
    	buffer.append(");");
    	String result = runScript(buffer.toString());
    	if (result != null && result.length() > 0) {
    		Assert.fail("<a4j:commandLink> [ajaxSingle="+ajaxSingle+" ; immediate="+immediate+"] test failure caused by " + result);
    	}
    	
    }

    public String getTestUrl() {
        return "pages/ajaxCommandLink/ajaxLinkTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/ajaxCommandLink/ajaxCommandLinkAutoTest.xhtml";
    }
    
    @Override
    public void sendAjax() {
    	clickAjaxCommandAndWait(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID));
    }
 
}
