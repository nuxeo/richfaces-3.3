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

public class AjaxPushTest extends SeleniumTestBase {

    private final static String STOP_RESET = "#{ajaxPushBean.stop}";
    
    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testAjaxPushComponent(Template template) throws Exception {
        renderPage(template, STOP_RESET);

        String parentId = getParentId() + "_form:";

        String startBtnID = parentId + "startButton";
        String stopBtnID = parentId + "stopButton";

        writeStatus("Start push component. Test bean has to start sending events to registered PushEventListener");

        clickAjaxCommandAndWait(startBtnID);

        writeStatus("check whether push events are being fired");

        int eventBefore = getEventsCount();

		delay(1500);

        int eventAfter = getEventsCount();

        if (eventBefore >= eventAfter) {
            Assert.fail("PushEvents are not fired");
        }

        writeStatus("stop pushing");
        clickAjaxCommandAndWait(stopBtnID);
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
      
        tester.reset();
        tester.clickLoad();

        String parentId = getParentId() + "autoTestForm:";
    	String startBtn = parentId + "startButton";
    	String stopBtn = parentId + "stopButton";
    	
    	clickAjaxCommandAndWait(startBtn);
	    
    	delay(1500);
	    
        for (String name : params.keySet()) {
            String value = runScript("requestParamsMap" + "." + name);
            if (value == null) {
                Assert.fail("Parameter [" + name + "] is not present in ajax request");
            } else if (!value.equals(params.get(name))) {
                Assert.fail("Parameter [" + name + "] value is invalid. Expected [" + params.get(name) + "]. But was ["
                        + value + "]");
            }
        }
	    clickAjaxCommandAndWait(stopBtn);

    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test ajaxSingle attribute");
        tester.testAjaxSingle();
    }

    @Test
    public void testAjaxSingleWithInternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test ajaxSingle attribute in case of invalid children state");
        tester.testAjaxSingleWithInternalValidationFailed();
    }
    
    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test immediate attribute");
        tester.testImmediate();
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed();
    }

    @Test
    public void testListenersAreNotInvokedInCaseOfExternalValidationFailure(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, STOP_RESET);
        writeStatus("Test listeners aren't invoked in case of external validation failure");
        tester.testExtrenalValidationFailure();
    }

    @Override
    public void sendAjax() {
    	String parentId = getParentId() + "autoTestForm:";
    	String startBtn = parentId + "startButton";
    	String stopBtn = parentId + "stopButton";
    	clickAjaxCommandAndWait(startBtn);
	    delay(5000);
	    clickAjaxCommandAndWait(stopBtn);
    }

    @Override
    public void setInternalValidationFailed() {
        String childCompId = getAutoTester(this).getClientId("") + "child";
        setValueById(childCompId, "");
    }

    private int getEventsCount() throws Exception {
        String events = getTextById(getParentId() + "_form:events");
        try {
            return Integer.parseInt(events);
        } catch (Exception e) {
            Assert.fail("Quantity of sent events is not numeric  :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public String getTestUrl() {
        return "pages/ajaxPush/ajaxPushTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxPush/ajaxPushAutoTest.xhtml";
    }

}
