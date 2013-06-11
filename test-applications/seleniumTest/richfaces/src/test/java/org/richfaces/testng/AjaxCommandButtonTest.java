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


public class AjaxCommandButtonTest extends SeleniumTestBase {
	
	static final String [] CSS_CLASSES = new String [] {"commandStyle"};
	static final Map<String, String> CSS_STYLES = new HashMap<String, String>();
	
	static {
		CSS_STYLES.put("font-weight", "bold");
		CSS_STYLES.put("color", "green");
	}
	

	@Test
	public void testStylesClassesAndEvents(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testStyleAndClasses(CSS_CLASSES, CSS_STYLES);
		
		String componentId = tester.getClientId(AutoTester.COMPONENT_ID, template);
		assertAttributeContains(componentId, "title", "Command Button title", "");	
		assertAttributeContains(componentId, "alt", "Command Button alt", "");
		assertAttributeContains(componentId, "tabIndex", "1", "");
	
		tester.testHTMLEvents();
	
	}
	
	@Test
	public void testLimit2ListAttribute(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testLimitToList();
		
	}
	
	@Test
	public void testRenderedAttribute(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testRendered();
		
	}
	
	@Test
	public void testBypassUpdates(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testBypassUpdate();
		
	}

    @Test
    public void testAjaxCommandButtonComponent(Template template) {
        renderPage(template);

    	String parentId = getParentId() + "_form:";

    	String buttonId = parentId + "b1";
    	
    	String rerenderId = parentId + "_rerender";    	
    	String rerenderStr = getTextById(rerenderId);
    	boolean ajaxSingle = false;
    	boolean immediate = false;
    	writeStatus("Click button 1");
    	setValidation(true);
    	clickById(buttonId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(buttonId, true, ajaxSingle, immediate, true, true, true, true);
    	checkRerendering(rerenderStr, rerenderId);
    	
    	buttonId = parentId + "b2";
    	ajaxSingle = false;
    	immediate = false;
    	writeStatus("Click button 2");
    	setValidation(false);
    	clickById(buttonId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(buttonId, false, ajaxSingle, immediate, false, false, false, false);
    	
    	buttonId = parentId + "b3";
    	ajaxSingle = false;
    	immediate = true;
    	writeStatus("Click button 3");
    	setValidation(true);
    	clickById(buttonId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(buttonId, true, ajaxSingle, immediate, true, true, true, false);
    	
    	buttonId = parentId + "b4";
    	ajaxSingle = true;
    	immediate = true;
    	writeStatus("Click button 4");
    	setValidation(false);
    	clickById(buttonId);
    	waitForAjaxCompletion();
    	waitForOnCompleteHndler();
    	checkButton(buttonId, true, ajaxSingle, immediate, true, true, true, false);
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
    	writeStatus("Checking button...");
    	StringBuffer buffer = new StringBuffer("checkButton('");
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
    		Assert.fail("<a4j:commandButton> [ajaxSingle="+ajaxSingle+" ; immediate="+immediate+"] test failure caused by " + result);
    	}
    }

    public String getTestUrl() {
        return "pages/ajaxCommandButton/ajaxButtonTest.xhtml";
    }
    
    @Override
    public void sendAjax() {
    	String commandId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
    	clickAjaxCommandAndWait(commandId);
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/ajaxCommandButton/autoTestCommandButton.xhtml";
    }
    

}
