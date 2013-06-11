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
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GraphValidatorTest extends SeleniumTestBase {

    private static final String NAME = "name";

    private static final String AGE = "age";

    private static final String EMAIL = "email";

    private static final String ERR_MSG_POSTFIX = "_err_msg";

    private static final String ALL_MSGS = "all_messages";

    private static final String SAVE_BTN = "_save";
    
    private static final String RESET_DATA = "#{validationBean.reset}";

    @Test
    public void testGraphValidatorComponentWithComponentSubtree(Template template) {
        renderPage(template,RESET_DATA);

        writeStatus("Check that components subtree confined by the component is validated "
                + "with Hibernate Validator properly");
        String parentId = getParentId() + "_form:";

        String tfNameId = parentId + NAME;
        String tfEmailId = parentId + EMAIL;
        String tfAgeId = parentId + AGE;

        String tfNameErrMsg = tfNameId + ERR_MSG_POSTFIX;
        String tfEmailErrMsg = tfEmailId + ERR_MSG_POSTFIX;
        String tfAgeErrMsg = tfAgeId + ERR_MSG_POSTFIX;

        String saveBtn = parentId + SAVE_BTN;

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfEmailErrMsg);
        assertNotPresent(tfAgeErrMsg);

        writeStatus("Name must be between 3 and 12 at length. Type shorter one");
        selenium.type(tfNameId, "Mi");

        writeStatus("Email must be an email. Type not a well-formed email");
        selenium.type(tfEmailId, "notemail");

        writeStatus("Age must be between 18 and 100. Type less than that");
        selenium.type(tfAgeId, "3");

        writeStatus("Try to save an input");
        clickAjaxCommandAndWait(saveBtn);

        assertPresent(tfNameErrMsg);
        assertPresent(tfAgeErrMsg);
        assertPresent(tfEmailErrMsg);

        writeStatus("Correct the input and resubmit form. Error messages have to disappear");

        selenium.type(tfNameId, "Mick");
        selenium.type(tfAgeId, "33");
        selenium.type(tfEmailId, "email@ya.com");

        writeStatus("Try to save an input once more");
        clickAjaxCommandAndWait(saveBtn);

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfAgeErrMsg);
        assertNotPresent(tfEmailErrMsg);

    }

    @Test
    public void testGraphValidatorComponentWithValueBoundToBean(Template template) {
        renderPage(template, RESET_DATA);

        writeStatus("Check that component validates a bean bound to component value property. "
                + "After model is updated the bean must be validated again.");

        String parentId = getParentId() + "_form1:";

        String firstCompErrMsg = parentId + "table:0:time" + ERR_MSG_POSTFIX;
        String compErrMsg3 = parentId + "table:2:time" + ERR_MSG_POSTFIX;

        String saveBtn = parentId + SAVE_BTN;
        String allMessages = parentId + ALL_MSGS;

        writeStatus("Try to save an input. Model validation cannot be passed");
        clickAjaxCommandAndWait(saveBtn);

        AssertTextEquals(allMessages, "Invalid values: Please fill at least one entry");

        writeStatus("Check bean properties are validated at validation phase. Type time of sport activity greater than allowed");
        spinnerManualInput("13", 0);

        clickAjaxCommandAndWait(saveBtn);
        assertPresent(firstCompErrMsg);

        writeStatus("Correct the input and save data again");
        spinnerManualInput("9", 0);
        clickAjaxCommandAndWait(saveBtn);
        assertNotPresent(firstCompErrMsg);
        AssertTextEquals(allMessages, "Changes Stored Successfully");
        
        spinnerManualInput("17", 0);
        spinnerManualInput("10", 1);
        spinnerManualInput("17", 2);
        spinnerManualInput("10", 3);
        clickAjaxCommandAndWait(saveBtn);
        assertPresent(firstCompErrMsg);
        assertPresent(compErrMsg3);
        
        spinnerManualInput("11", 0);
        spinnerManualInput("10", 1);
        spinnerManualInput("10", 2);
        spinnerManualInput("10", 3);
        clickAjaxCommandAndWait(saveBtn);
        String messageText = getTextById(allMessages);
        if (!messageText.contains("Invalid values: Only 24h in a day!") || !messageText.contains("Are you sure you have power for this??!!")) {
        	Assert.fail("All properties validation phase has been skipped. Expected: Invalid values: Only 24h in a day!Invalid values: Are you sure you have power for this??!!. But was: " + messageText);
        }
        
        spinnerManualInput("9", 0);
        clickAjaxCommandAndWait(saveBtn);
        AssertTextEquals(allMessages, "Invalid values: Only 24h in a day!", "All properties validation phase has been skipped ");
      
    }

    private void assertPresent(String id) {
        AssertTextNotEquals(id, "", "Message [" + id + "] must not be empty on the page");
    }

    private void assertNotPresent(String id) {
        AssertTextEquals(id, "", "Message [" + id + "] must be empty on the page");
    }

    private void spinnerManualInput(String value, int n) {
        selenium.type("xpath=//table[@id='" + getParentId() + "_form1:table:"+n+":time']/tbody/tr/td/input", value);
    }

    @Override
    public String getTestUrl() {
        return "pages/graphValidator/graphValidatorTest.xhtml";
    }
}
