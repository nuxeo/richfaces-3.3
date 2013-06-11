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

/**
 * @author Andrey Markavstov
 * 
 */
public class AjaxPollTest extends SeleniumTestBase {

    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testAjaxPollComponent(Template template) {
        renderPage(template);
        String parentId = getParentId() + "_form:";
        String pollId = parentId + "poll";
        String inputId = parentId + "_value";

        enablePoll(parentId + "_enabled");
        AssertValueEquals(inputId, "1");

        writeStatus("Polling in progress...");
        pause(1500, pollId);
        AssertValueNotEquals(inputId, "1",
                "Polling does not fire after component was enabled. Or 'ReRender' attribute does not work");

        waiteForCondition("document.getElementById('" + inputId + "').value == 8", 7000);

        pause(1500, pollId);
        writeStatus("Polling should be stopped...");

        AssertValueEquals(inputId, "8", "Polling should be stopped. It continues to poll after component was disabled.");
        AssertTextEquals(parentId + "_text", "Polling",
                "Polling terminated abnormal or invalid count of ajax requests has been submitted");

    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute");
        tester.testImmediate();
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed();
    }

    @Test
    public void testNestedActionListener(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test actionListener defined as nested tag are invoked on the server ");
        tester.testNestedActionListener();
    }

    @Test
    public void testListenersAreNotInvokedInCaseOfExternalValidationFailure(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test listeners aren't invoked in case of external validation failure");
        tester.testExtrenalValidationFailure();
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute");
        tester.testAjaxSingle();
    }

    @Test
    public void testAjaxSingleWithInternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute in case of invalid children state");
        tester.testAjaxSingleWithInternalValidationFailed();
    }

    @Override
    public void sendAjax() {
        delay(2000);
    }

    @Override
    public void setInternalValidationFailed() {
        String childCompId = getAutoTester(this).getClientId("") + "child";
        setValueById(childCompId, "");
    }

    private void enablePoll(String id) {
        writeStatus("Enable polling...");
        try {
            clickById(id);
            waitForAjaxCompletion();
        } catch (Exception e) {
            Assert.fail("Poll component failed after attempt to enable polling. Caused by " + e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.test.base.RichSeleniumTest#getTestUrl()
     */
    public String getTestUrl() {
        return "pages/ajaxPoll/ajaxPollTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxPoll/ajaxPollAutoTest.xhtml";
    }
}
