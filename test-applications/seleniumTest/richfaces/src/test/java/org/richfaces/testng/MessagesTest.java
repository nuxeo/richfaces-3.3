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

import org.ajax4jsf.template.Template;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessagesTest extends AbstractMessageTest {

	private static String TEST_MORE_THAN_ONE_MESSAGE = "pages/message/testMoreThanOneMessage.xhtml";
	private static String TEST_MESSAGES_WITH_FOR_ATTRIBUTE = "pages/message/testMessagesWithForAttribute.xhtml";
	private static String TEST_MESSAGES_GLOBAL_ONLY_ATTRIBUTE = "pages/message/testMessagesWithGlobalOnlyAttribute.xhtml";
		
	@Override
	protected void init(Template template) {
		super.init(template);
    	label = "xpath=id('" + message + "')/dt[1]/span[2]";
	}
	
	@Override
	public String getTestUrl() {
		return "pages/message/messages.xhtml";
	}
	
	@Test
    public void testComponentOutputAllMessagesByAjax(Template template) {
    	init(template, TEST_MORE_THAN_ONE_MESSAGE);
    	
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    	
    	selenium.type(inputText, "fatal");
    	selenium.type(inputText + "2", "");
    	selenium.type(inputText + "3", "");
    	
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	AssertPresent(mainForm + ":fatalMarker");
    	AssertPresent(mainForm + ":errorMarker");
    	Assert.assertEquals("messageSummary", selenium.getText("xpath=id('" + message + "')/dt[1]/span[2]"));
    	Assert.assertEquals("Required message 1", selenium.getText("xpath=id('" + message + "')/dt[2]/span[2]"));
    	Assert.assertEquals("Required message 2", selenium.getText("xpath=id('" + message + "')/dt[3]/span[2]"));
    	
    	selenium.type(inputText, "passed");
    	selenium.type(inputText + "2", "passed");
    	selenium.type(inputText + "3", "passed");
  
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    }
	
	@Test
    public void testMessagesForAttribute(Template template) {
    	init(template, TEST_MESSAGES_WITH_FOR_ATTRIBUTE);
    	
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    	
    	selenium.type(inputText, "fatal");
    	selenium.type(inputText + "2", "");
    	selenium.type(inputText + "3", "");
    	
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	
    	//should present error message only for 2-nd input
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertPresent(mainForm + ":errorMarker");	
    	Assert.assertEquals("Required message 1", selenium.getText("xpath=id('" + message + "')/dt[1]/span[2]"));
    	
    	selenium.type(inputText + "2", "passed");
  
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    }
	
	@Test
    public void testMessagesGlobalOnlyAttribute(Template template) {
    	init(template, TEST_MESSAGES_GLOBAL_ONLY_ATTRIBUTE);
    	
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    	AssertNotPresent(mainForm + ":warnMarker");
    	AssertNotPresent(mainForm + ":infoMarker");
    	
    	clickAjaxCommandAndWait(mainForm + ":invalidate");
    	
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    	AssertNotPresent(mainForm + ":warnMarker");
    	AssertPresent(mainForm + ":infoMarker");	
    	Assert.assertEquals("globalMessageSummary", selenium.getText("xpath=id('" + message + "')/dt[1]/span[2]"));
    	
    	selenium.type(inputText, "fatal");
    	selenium.type(inputText + "2", "");
    	selenium.type(inputText + "3", "");
    	
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	
    	AssertNotPresent(mainForm + ":fatalMarker");
    	AssertNotPresent(mainForm + ":errorMarker");
    	AssertNotPresent(mainForm + ":warnMarker");
    	AssertNotPresent(mainForm + ":infoMarker");
    }
	
	/**
     * check markers rendering
     */
    @Test
    public void testMarkers(Template template) {
    	init(template);
    	assertMarkers(false);
    }
    
    /**
     * showDetail and showSummary attributes work
     */
    @Test
    public void testShowDetailAndShowSummary(Template template) {
    	init(template);
    	assertShowDetailAndShowSummary(false);
    }
}
