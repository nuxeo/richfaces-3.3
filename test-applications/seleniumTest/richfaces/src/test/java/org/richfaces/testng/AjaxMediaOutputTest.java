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

import junit.framework.Assert;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

/**
 * ajaxMediaOutput component selenium test
 * @author Alexandr Levkovsky
 *
 */
public class AjaxMediaOutputTest extends SeleniumTestBase {

    private final static String FORM_ID = "form:";
    private final static String IMAGE_ID = FORM_ID + "media_data";
    private final static String IMAGE_EVENT_ID = FORM_ID + "media_data_event_";
    private final static String EVENT_RESULT_ID = "_result";
    private final static String EVENT_TEST_RESULT_FAILED_TEXT = "No";
    private final static String EVENT_TEST_RESULT_PASSED_TEXT = "Passed";
    /**
     * @see org.richfaces.SeleniumTestBase#getTestUrl()
     */
    @Override
    public String getTestUrl() {
	return "pages/ajaxMediaOutput/ajaxMediaOutputTest.xhtml";
    }

    @Test
    public void testAjaxMediaOutput(Template template) throws Exception {
	renderPage(template);
	writeStatus("Testing media output");
	
	String imageId = getParentId() + IMAGE_ID;
	
	AssertPresent(imageId);
	
	String tagName = runScript("getElementType('" + imageId +"');");
	Assert.assertEquals(tagName.toLowerCase(), "img");
    }
    
    @Test
    public void testOnclickEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 1;
	super.testOnclickEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }

    @Test
    public void testOnDblclickEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 2;
	super.testOnDblclickEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }
    
    @Test
    public void testOnmousedownEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 7;
	super.testOnmousedownEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }
    
    @Test
    public void testOnmousemoveEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 8;
	super.testOnmousemoveEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }
    
    @Test
    public void testOnmouseoutEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 9;
	super.testOnmouseoutEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }
    
    @Test
    public void testOnmouseoverEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 10;
	super.testOnmouseoverEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }

    @Test
    public void testOnmouseupEvent(Template template) throws Exception {
	renderPage(template);
	String imageId = getParentId() + IMAGE_EVENT_ID + 11;
	super.testOnmouseupEvent(imageId, imageId + EVENT_RESULT_ID, EVENT_TEST_RESULT_FAILED_TEXT, EVENT_TEST_RESULT_PASSED_TEXT);
	runScript("resetEventTestResult('" + imageId + EVENT_RESULT_ID + "');");
    }

}
