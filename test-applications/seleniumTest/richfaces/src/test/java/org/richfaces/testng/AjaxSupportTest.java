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

import org.ajax4jsf.bean.A4JSupport;
import org.ajax4jsf.bean.A4JSupport.Messages;
import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.richfaces.testng.util.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andrey Markavstov
 * 
 */
public class AjaxSupportTest extends SeleniumTestBase {

    private static final String COMMON_ALISTENER_PREFIX = "AL";

    private static final String DEFAULT_BEHAVIOUR_PAGE = "testDefaultBehaviour.xhtml";

    private static final String VALIDATION_FAILED_PAGE = "testValidationFailed.xhtml";

    private static final String IMMEDIATE_ATTRIBUTE1_PAGE = "testImmediateAttribute1.xhtml";

    private static final String IMMEDIATE_ATTRIBUTE2_PAGE = "testImmediateAttribute2.xhtml";

    private static final String AJAXSINGLE_ATTRIBUTE1_PAGE = "testAjaxSingleAttribute1.xhtml";

    private static final String AJAXSINGLE_ATTRIBUTE2_PAGE = "testAjaxSingleAttribute2.xhtml";

    private static final String AJAXSINGLE_ATTRIBUTE3_PAGE = "testAjaxSingleAttribute3.xhtml";

    private static final String RERENDER_ATTRIBUTE_PAGE = "testRerenderFunctionality.xhtml";

    private static final String ENABLED_ATTRIBUTE_PAGE = "testEnabledAttribute.xhtml";

    private static final String RENDERED_ATTRIBUTE_PAGE = "testRenderedAttribute.xhtml";

    private static final String UPDATING_VALUE = "testUpdatingValue.xhtml";

    private static final String EVENTS_PAGE = "testEvents.xhtml";

    private static final String LIMIT_TO_LIST_ATTRIBUTE_PAGE = "testLimitToList.xhtml";

    private static final String BY_PASS_UPDATES_ATTRIBUTE_PAGE = "testByPassList.xhtml";

    private static final String NAVIGATION_TEST = "pages/ajaxSupport/navigationTest.xhtml";

    private static final String DEFAULT_BEHAVIOUR_ID_PREFIX = "_db";

    private static final String VALIDATION_FAILED_ID_PREFIX = "_vf";

    private static final String IMMEDIATE_ATTRIBUTE1_ID_PREFIX = "_ia1";

    private static final String IMMEDIATE_ATTRIBUTE2_ID_PREFIX = "_ia2";

    private static final String AJAXSINGLE_ATTRIBUTE1_ID_PREFIX = "_asa1";

    private static final String AJAXSINGLE_ATTRIBUTE2_ID_PREFIX = "_asa2";

    private static final String RERENDER_ATTRIBUTE_ID_PREFIX = "_ra";

    private static final String ENABLED_ATTRIBUTE_ID_PREFIX = "_ea";

    private static final String RENDERED_ATTRIBUTE_ID_PREFIX = "_ra";

    private static final String UPDATING_VALUE_ID_PREFIX = "_uv";

    private static final String UPDATING_VALUE_KEY = "v";

    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    private String testUrl;

    private String parentFormId;

    private String linkId;

    private String checkboxId;

    private String commandId;

    private String validManagerId;

    private String dataId1;

    private String dataId2;

    private String inputId;

    /**
     * action and actionListener defined as component attributes and
     * actionListener defined as nested tag are invoked on the server after
     * event of attached to component occurs, navigation occurs
     */
    @Test
    public void testDefaultBehaviour(Template template) {
        setTestUrl(DEFAULT_BEHAVIOUR_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, DEFAULT_BEHAVIOUR_ID_PREFIX, true);
    }

    /**
     * Component re-renders another component (h:outputText)
     */
    @Test
    public void testRerenderFunctionality(Template template) {
        setTestUrl(RERENDER_ATTRIBUTE_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, RERENDER_ATTRIBUTE_ID_PREFIX, true);
    }

    /**
     * @see #testDefaultBehaviour(Template template) the same for the case of
     *      external validation failure - listeners and navigation do not work
     */
    @Test
    public void testValidationFailed(Template template) {
        setTestUrl(VALIDATION_FAILED_PAGE);
        init(template);
        breakExternalValidation();
        checkBasicFunctionality(template, VALIDATION_FAILED_ID_PREFIX, false);
    }

    /**
     * @see #testDefaultBehaviour(Template template) the same for immediate =
     *      true component
     */
    @Test
    public void testImmediateAttribute1(Template template) {
        setTestUrl(IMMEDIATE_ATTRIBUTE1_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, IMMEDIATE_ATTRIBUTE1_ID_PREFIX, true);
    }

    /**
     * @see #testDefaultBehaviour(Template template) the same for immediate =
     *      true component for the case of external validation failure
     */
    @Test
    public void testImmediateAttribute2(Template template) {
        setTestUrl(IMMEDIATE_ATTRIBUTE2_PAGE);
        init(template);
        breakExternalValidation();
        checkBasicFunctionality(template, IMMEDIATE_ATTRIBUTE2_ID_PREFIX, true);
    }

    /**
     * @see #testDefaultBehaviour() the same for ajaxSingle = true component
     */
    @Test
    public void testAjaxSingleAttribute1(Template template) {
        setTestUrl(AJAXSINGLE_ATTRIBUTE1_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, AJAXSINGLE_ATTRIBUTE1_ID_PREFIX, true);
    }

    /**
     * @see #testDefaultBehaviour() the same for ajaxSingle = true component for
     *      the case of external validation failure
     */
    @Test
    public void testAjaxSingleAttribute2(Template template) {
        setTestUrl(AJAXSINGLE_ATTRIBUTE2_PAGE);
        init(template);
        breakExternalValidation();
        checkBasicFunctionality(template, AJAXSINGLE_ATTRIBUTE2_ID_PREFIX, true);
    }

    /**
     * component with bypassUpdates = true skips update model values phase
     */
    @Test
    public void testByPassAttribute(Template template) {
        setTestUrl(BY_PASS_UPDATES_ATTRIBUTE_PAGE);
        init(template);
        passExternalValidation();
        processingElement(checkboxId, dataId2, false);
        AssertTextEquals(dataId2 + "_ch", "false", "By pass updates attribute not work");
        processingElement(checkboxId + COMMON_ALISTENER_PREFIX, dataId2 + COMMON_ALISTENER_PREFIX, false);
        AssertTextEquals(dataId2 + COMMON_ALISTENER_PREFIX + "_ch", "false", "By pass updates attribute not work");

    }

    /**
     * component with limitToList = true skips ajaxRendered areas update
     */
    @Test
    public void testLimitToListAttribute1(Template template) {
        setTestUrl(LIMIT_TO_LIST_ATTRIBUTE_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, "", false);
        AssertTextEquals(parentFormId + "ajax_status", Messages.IDLE_STATUS, "Limit to list attribute not work");
    }

    /**
     * component with rendered = false is not present on the page
     */
    @Test
    public void testRenderedAttribute(Template template) {
        setTestUrl(RENDERED_ATTRIBUTE_PAGE);
        init(template);

        checkOnclickAttr(linkId + RENDERED_ATTRIBUTE_ID_PREFIX);
        checkOnclickAttr(linkId + COMMON_ALISTENER_PREFIX + RENDERED_ATTRIBUTE_ID_PREFIX);
        checkOnclickAttr(checkboxId + RENDERED_ATTRIBUTE_ID_PREFIX);
        checkOnclickAttr(checkboxId + COMMON_ALISTENER_PREFIX + RENDERED_ATTRIBUTE_ID_PREFIX);
    }

    /**
     * onsubmit event fires on component activation then oncomplete with proper
     * request data
     */
    @Test
    public void testOnSubmitAndOnCOmpleteEvents(Template template) {
        setTestUrl(EVENTS_PAGE);
        init(template);
        passExternalValidation();
        processingElement(linkId, dataId1, false);
        assertEvents();
        processingElement(linkId + COMMON_ALISTENER_PREFIX, dataId1 + COMMON_ALISTENER_PREFIX, false);
        assertEvents();
        processingElement(checkboxId, dataId2, false);
        assertEvents();
        processingElement(checkboxId + COMMON_ALISTENER_PREFIX, dataId2 + COMMON_ALISTENER_PREFIX, false);
        assertEvents();

    }

    private void assertEvents() {
        List<String> eventsExpected = new ArrayList<String>();

        eventsExpected.add("onsubmit");
        eventsExpected.add("oncomplete");
        assertEvents(eventsExpected);
    }

    /**
     * component with disabled = true do not fire ajax requests
     */
    @Test
    public void testEnabledAttribute(Template template) {
        setTestUrl(ENABLED_ATTRIBUTE_PAGE);
        init(template);
        passExternalValidation();
        checkBasicFunctionality(template, ENABLED_ATTRIBUTE_ID_PREFIX, false);
    }

    /**
     * 
     * @param template
     */
    @Test
    public void testUpdatingValue(Template template) {
        setTestUrl(UPDATING_VALUE);
        init(template);

        String iid = inputId + UPDATING_VALUE_ID_PREFIX;
        setValueById(iid, UPDATING_VALUE_KEY);
        selenium.fireEvent("id=" + iid, "keyup");
        waitForAjaxCompletion();
        AssertTextEquals(dataId1 + UPDATING_VALUE_ID_PREFIX, UPDATING_VALUE_KEY, CommonUtils.getFailedTestMessage(iid));
        writeStatus(CommonUtils.getSuccessfulTestMessage(iid));
    }

    /**
     * @see #testAjaxSupportComponent() the same for ajaxSingle = true component
     *      for the case of external validation failure + process for validation
     *      failed field - listeners and navigation do not work
     */
    @Test
    public void testAjaxSingleAttribute3(Template template) {
        setTestUrl(AJAXSINGLE_ATTRIBUTE3_PAGE);
        init(template);
        breakExternalValidation();
        checkBasicFunctionality(template, "", false);
        AssertNotPresent(getParentId() + "successNavigationTextId", "Navigation shouldn't work");
    }

//
// Auto-test methods
//

    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testNavigation(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(NAVIGATION_TEST, template, null);
        writeStatus("if all is ok listeners defined both as attribute and as nested tag are invoked");
        writeStatus("navigation occurs");
        tester.testActionAndNavigation();
        tester.checkActionListener(true);
        tester.checkNestedActionListener(true);
    }

    @Test
    public void testNavigationWithExternalValidationFailure(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(NAVIGATION_TEST, template, null);
        writeStatus("in case of extrenal validation failure listeners and navigation do not work");
        tester.reset();
        tester.clickLoad();

        tester.setExtrenalValidationFailed();
        sendAjax();

        tester.checkAction(false);
        tester.checkNavigation(false);
        tester.checkActionListener(false);
        tester.checkNestedActionListener(false);
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
    public void testAjaxSingle(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute in case of external validation failure");
        autoTester.testAjaxSingle();
    }

    @Test
    public void testAjaxSingleWithExternalAndProcessedComponentsValidationFailures(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute in case of validation failures of both external and processed components");
        autoTester.testAjaxSingleWithProcesExternalValidation(true);
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testOncomplete(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test oncomplete attribute");
        tester.testOncomplete();
    }

    @Test
    public void testLimitToListAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
        tester.testLimitToList();
    }

    @Test
    public void testBypassUpdatesAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with bypassUpdates = true skips update model values phase");
        tester.testBypassUpdate();
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait(getAutoTester(this).getClientId("link"));
    }

    private void checkOnclickAttr(String elementId) {
        checkEmptyAttr(getElementOnclickAttr(elementId), CommonUtils.getFailedTestMessage(elementId), CommonUtils
                .getSuccessfulTestMessage(elementId));
    }

    private void checkEmptyAttr(String locator, String errMessage, String okMessage) {
        if (null == locator) {
            writeStatus(okMessage);
        } else {
            writeStatus(errMessage, true);
            Assert.fail(errMessage);
        }
    }

    private void checkBasicFunctionality(Template template, String casePrefix, boolean isDataAvailable) {
        processingElement(linkId + casePrefix, dataId1 + casePrefix, isDataAvailable);
        processingElement(linkId + COMMON_ALISTENER_PREFIX + casePrefix,
                dataId1 + COMMON_ALISTENER_PREFIX + casePrefix, isDataAvailable);

        processingElement(checkboxId + casePrefix, dataId2 + casePrefix, isDataAvailable);
        processingElement(checkboxId + COMMON_ALISTENER_PREFIX + casePrefix, dataId2 + COMMON_ALISTENER_PREFIX
                + casePrefix, isDataAvailable);
    }

    private void processingElement(String linkId, String dataId, boolean isValidationFailed) {
        clickById(linkId);
        waitForAjaxCompletion();
        String data = getTextById(dataId);
        checkMessage(data, A4JSupport.Messages.TEST_PASSED, CommonUtils.getFailedTestMessage(linkId), CommonUtils
                .getSuccessfulTestMessage(linkId), isValidationFailed);
    }

    private void breakExternalValidation() {
        setValueById(validManagerId, "");
    }

    private void passExternalValidation() {
        setValueById(validManagerId, "1");
    }

    /*
     * private void checkRequestDelayAttribute(String data, String casePrefix) {
     * String comId = commandId + casePrefix; clickById(comId); pause(1000,
     * comId);
     * 
     * // if (!data.equals(getTextById(dataId))) { //
     * writeStatus("<a4j:support failed. 'requestDelay' attribute does not work."
     * ); //
     * Assert.fail("<a4j:support failed. 'requestDelay' attribute does not work."
     * ); // } waitForAjaxCompletion(); data = getTextById(dataId);
     * checkMessage(data, A4JSupport.Messages.FOR_COMMAND,
     * CommonUtils.getFailedTestMessage(comId),
     * CommonUtils.getSuccessfulTestMessage(comId));
     * 
     * if (A4JSupport.Messages.FOR_SUBMIT.equals(data)) {
     * writeStatus("<a4j:support failed. 'disableDefault' attribute does not work"
     * );
     * Assert.fail("<a4j:support failed. 'disableDefault' attribute does not work."
     * ); } }
     */

    private void checkMessage(String data, String message, String errorMessage, String okMessage,
            boolean isDataAvailable) {
        if (!isDataAvailable) {
            if (!A4JSupport.Messages.NO_DATA.equals(data)) {
                writeStatus(errorMessage, true);
                Assert.fail(errorMessage);
            } else {
                writeStatus(okMessage);
            }
        } else {
            if (message.equals(data)) {
                writeStatus(okMessage);
            } else {
                writeStatus(errorMessage, true);
                Assert.fail(errorMessage);
            }
        }
    }

    private void init(Template template) {
        renderPage(template);
        initIds();
    }

    private void initIds() {
        parentFormId = getParentId() + "_form:";
        linkId = parentFormId + "link";
        checkboxId = parentFormId + "checkbox";
        commandId = parentFormId + "command";
        validManagerId = parentFormId + "validManager";
        dataId1 = parentFormId + "data1";
        dataId2 = parentFormId + "data2";
        inputId = parentFormId + "input";
    }

    @Override
    public String getTestUrl() {
        return testUrl;
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxSupport/ajaxSupportAutoTest.xhtml";
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = "pages/ajaxSupport/" + testUrl;
    }

}
