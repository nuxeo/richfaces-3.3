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
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxFormTest extends SeleniumTestBase {

    private final static String FORM_ID = "a4j_form";

    private static final String STANDART_FORM = "staticForm";

    private final static String INNER_INPUT_ID = "inner_name";

    private final static String OUTER_INPUT_ID = "outer_name";

    private final static String TEST_FIELD_ID = "test_field_name";

    private final static String BUTTON_ID = "submit";

    private final static String PREV_TEXT = "before submit";

    private final static String NEXT_TEXT = "after submit";

    private final static String CHECK_ID_RENDERED = "rendered";

    private final static String LINK_ID = "link";

    private final static String RESET_METHOD = "#{formBean.reset}";

    @Test
    public void testAttrAjaxSubmit(Template template) throws Exception {
        renderPage(template, RESET_METHOD);

        // clickOnCheckbox(CHECK_ID_AJAXSUBMIT, BUTTON_ID);
        test(BUTTON_ID, INNER_INPUT_ID, STANDART_FORM.concat(":").concat(OUTER_INPUT_ID), TEST_FIELD_ID, true, null,
                null, null, "testAttrAjaxSubmit");
    }

    @Test
    public void testAttrPrependId(Template template) {
        renderPage(template, RESET_METHOD);

        if (!PREV_TEXT.equals(getValueById(getFullComponentId(INNER_INPUT_ID, "1")))) {
            assertFail(null, null, true, null, "testAttrPrependId");
        }
    }

    /*
     * @Test public void testAttrIgnoreDupResponses() { //TODO: will be
     * implemented in future }
     */

    /*
     * @Test public void testAttrTimeout() { //TODO: will be implemented in
     * future }
     */

    @Test
    public void testProcessingLinks(Template template) {
        renderPage(template, RESET_METHOD);

        clickOnCheckbox(CHECK_ID_RENDERED, BUTTON_ID, "2");
        clickById(getParentId() + LINK_ID.concat("2"));
        waitForAjaxCompletion();
        if (!NEXT_TEXT.equals(getTextById(getParentId() + TEST_FIELD_ID.concat("2")))) {
            assertFail(null, null, null, null, "testProcessingLinks");
        }
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
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

    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template){
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        
        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        String formId = getParentId() + AutoTester.COMPONENT_ID;
        assertStyleAttributeContains(formId, "font-size: 13px", "Style attribute was not output to client");
        assertClassAttributeContains(formId, "noclass", "Class attribute was not output to client");

        writeStatus("Check component's specific HTML attributes are output to client");
        clickAjaxCommandAndWait(getParentId() + AutoTester.COMPONENT_ID + ":submit");
        assertEvent("onsubmit");
    }

    @Test
    public void testMainFeature(Template template) {
        renderPage(template, RESET_METHOD);
        writeStatus("Check that a4j:form with a4j:htmlCommandLink fix the problem of h:commandLink component");
        writeStatus("that cannot be re-rendered without re-rendering the whole form it belongs to.");

        String parentId = getParentId() + "mainFeatureForm:";
        String updateNonAjaxLinkId = parentId + "update_non_ajax_link";
        String nonAjaxSubmit = parentId + "non_ajax_submit";
        String resultId = parentId + "result";

        writeStatus("First of all check a jsf non-ajax link works in general");
        AssertTextEquals(resultId, "before submit");
        clickCommandAndWait(nonAjaxSubmit);
        AssertTextEquals(resultId, "after submit");

        writeStatus("Rerender non-ajax link.");
        clickAjaxCommandAndWait(updateNonAjaxLinkId);

        writeStatus("Check that it still works");
        AssertTextEquals(resultId, "before submit");
        clickCommandAndWait(nonAjaxSubmit);
        AssertTextEquals(resultId, "after submit");
    }

    private String getFullComponentId(String componentId, String index) {
        return getParentId() + FORM_ID + index + ":" + componentId + index;
    }

    private boolean isAjaxSubmitSuccessfull(String innerInputId, String outerInputId) {
        String innerText = getTextById(getParentId() + innerInputId);
        String outerText = getValueById(getParentId() + outerInputId);
        return (NEXT_TEXT.equals(innerText) && (PREV_TEXT.equals(outerText)));
    }

    private void submitForm(String buttonId) {
        writeStatus("Submit form");
        clickAjaxCommandAndWait(getParentId() + buttonId);
    }

    private void test(String buttonId, String innerInputId, String outerInputId, String testInputId,
            Boolean ajaxSubmit, Boolean ignoreDupResponses, Integer timeout, Boolean prependId, String testName) {
        setValueById(getParentId() + innerInputId, NEXT_TEXT);
        submitForm(buttonId);
        if (!isAjaxSubmitSuccessfull(testInputId, outerInputId)) {
            assertFail(ajaxSubmit, ignoreDupResponses, prependId, timeout, testName);
        }
    }

    private void assertFail(Boolean ajaxSubmit, Boolean ignoreDupResponses, Boolean prependId, Integer timeout,
            String testName) {
        Assert.fail("<a4j:form> [ajaxSubmit=" + ajaxSubmit + " ; ignoreDupResponses=" + ignoreDupResponses
                + " ; timeout=" + timeout + " ; prependId=" + prependId + "] test '" + testName + "' failure.");
    }

    private void clickOnCheckbox(String checkId, String bottonId, String index) {
        clickById(getParentId() + checkId + index);
        clickById(getParentId() + bottonId + index);
        waitForAjaxCompletion();
    }

    private void resetTestData() {
        clickAjaxCommandAndWait(getParentId() + "_form:reset");
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait(getParentId() + AutoTester.COMPONENT_ID + ":submit");
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxForm/ajaxFormAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return "pages/ajaxForm/ajaxFormTest.xhtml";
    }
}
