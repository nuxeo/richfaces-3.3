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
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public abstract class AbstractMessageTest extends SeleniumTestBase {
	
	protected String mainForm;

	protected String inputText;
    
    protected String message;
    
    private String submit;
	
    private String rendered;
	
	private String showDetail;

	private String showSummary;

	protected String ajaxRendered;

	protected String label;

	protected void init(Template template) {
		init(template, null);
    }
	
	protected void init(Template template, String url) {
        renderPage(url, template, "#{messageBean.init}");
        mainForm = getParentId() + "mainForm";
        inputText = mainForm + ":inputText";
        message = mainForm + ":message";
        submit = mainForm + ":submit";
        String attrForm = getParentId() + "attrForm";
        rendered = attrForm + ":rendered"; 
        showDetail = attrForm + ":showDetail"; 
        showSummary = attrForm + ":showSummary"; 
        ajaxRendered = attrForm + ":ajaxRendered"; 
    }

    /**
     *  style and classes, standard HTML attributes are output to client
     */
	@Test
	public void testHTMLAttributes(Template template) {
        init(template);
		Map<String, String> styleAttributes = new HashMap<String, String>();
		styleAttributes.put("color", "blue");
		styleAttributes.put("text-decoration", "underline");			
        assertClassNames(message,new String [] {"noname"}, "Component's rendering invalid", true);
        assertStyleAttributes(message, styleAttributes);
	}

    public void assertShowDetailAndShowSummary(boolean shouldPresemtIfEmpty) {
    	if(shouldPresemtIfEmpty){
    		Assert.assertTrue(selenium.getText(label).length() == 0);
    	}
    	selenium.type(inputText, "fatal");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue("messageDetail".equals(selenium.getText(label)));
    	clickAjaxCommandAndWait(showSummary);
    	selenium.type(inputText, "fatal");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue("messageSummarymessageDetail".equals(selenium.getText(label)));
    	clickAjaxCommandAndWait(showDetail);
    	selenium.type(inputText, "fatal");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue("messageSummary".equals(selenium.getText(label)));
    	
    }

    /**
     * level selects message of respective level
     */
    @Test(groups=FAILURES_GROUP)
    public void testLevel(Template template) {
    	//TODO
    	Assert.fail("This test should be completed after resolving bug RF-5107.");
    }
    
    
    
    public void assertMarkers(boolean isPassedMarkerShouldPresent) {
    	if(isPassedMarkerShouldPresent){
    		Assert.assertTrue(selenium.isElementPresent(mainForm + ":passedMarker"));
    	}
    	selenium.type(inputText, "fatal");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":fatalMarker"));
    	selenium.type(inputText, "error");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":errorMarker"));
    	selenium.type(inputText, "warn");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":warnMarker"));
    	selenium.type(inputText, "info");
    	clickAjaxCommandAndWait(submit);
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":infoMarker"));
    }
    
    /**
     *   	 component with rendered = false is not present on the page
     */
    @Test
    public void testRendered(Template template) {
    	init(template);
        init(template); 
        Assert.assertTrue(selenium.isElementPresent(message), "Message must be rendered.");

        selenium.click(rendered);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submit);       
        
        Assert.assertFalse(selenium.isElementPresent(message), "Message mustn't be rendered.");
    }
    	
}
