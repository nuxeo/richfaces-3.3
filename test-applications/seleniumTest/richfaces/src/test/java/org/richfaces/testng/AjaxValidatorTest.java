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
import org.testng.annotations.Test;

public class AjaxValidatorTest extends SeleniumTestBase {

    private static final String NAME = "name";

    private static final String AGE = "age";

    private static final String EMAIL = "email";

    private static final String ERR_MSG_POSTFIX = "_err_msg";

    private static final String RESET_METHOD = "#{validationBean.init}";

    @Test
    public void testAjaxValidatorComponentWithStandartValidators(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check ajax validator component with standart validators. "
                + "Validation has to be occured by javascript event 'onblur'");
        String parentId = getParentId() + "_form:";

        String tfNameId = parentId + NAME;
        String tfAgeId = parentId + AGE;

        String tfNameErrMsg = tfNameId + ERR_MSG_POSTFIX;
        String tfAgeErrMsg = tfAgeId + ERR_MSG_POSTFIX;

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfAgeErrMsg);

        writeStatus("Name must be between 3 and 12 at length. Type shorter one");
        selenium.type(tfNameId, "Mi");
        selenium.fireEvent(tfNameId, "blur");
        waitForAjaxCompletion();
        assertPresent(tfNameErrMsg);

        writeStatus("Age must be greater than or equal to 18. Type less than that");
        selenium.type(tfAgeId, "3");
        waitForAjaxCompletion();
        assertPresent(tfAgeErrMsg);

        writeStatus("Correct the inputs and leave. Error messages have to disappear");

        selenium.type(tfNameId, "Mick");
        selenium.fireEvent(tfNameId, "blur");
        waitForAjaxCompletion();
        assertNotPresent(tfNameErrMsg);

        selenium.type(tfAgeId, "33");
        waitForAjaxCompletion();
        assertNotPresent(tfAgeErrMsg);

    }

    @Test
    public void testRendered(Template template) {
        renderPage(template, RESET_METHOD);

        String rendered = getParentId() + "attrForm" + ":rendered";
        String tfNameId = getParentId() + "_form:" + NAME;
        String tfNameErrMsg = tfNameId + ERR_MSG_POSTFIX;
        selenium.type(tfNameId, "Mi");
        selenium.fireEvent(tfNameId, "blur");
        waitForAjaxCompletion();
        assertPresent(tfNameErrMsg);
		clickAjaxCommandAndWait(rendered);
        selenium.type(tfNameId, "Mi");
        selenium.fireEvent(tfNameId, "blur");
        assertNotPresent(tfNameErrMsg);
   }

    @Test
    public void testAjaxValidatorComponentWithHibernateValidator(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check ajax validator component with hibernate validator. "
                + "Validation has to be occured by javascript event 'onblur'");
        String parentId = getParentId() + "_form1:";

        String tfNameId = parentId + NAME;
        String tfAgeId = parentId + AGE;
        String tfEmailId = parentId + EMAIL;

        String tfNameErrMsg = tfNameId + ERR_MSG_POSTFIX;
        String tfAgeErrMsg = tfAgeId + ERR_MSG_POSTFIX;
        String tfEmailErrMsg = tfEmailId + ERR_MSG_POSTFIX;

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfAgeErrMsg);
        assertNotPresent(tfEmailErrMsg);

        writeStatus("Name must be between 3 and 12 at length. Type shorter one");
        selenium.type(tfNameId, "Mi");
        selenium.fireEvent(tfNameId, "blur");
        waitForAjaxCompletion();
        assertPresent(tfNameErrMsg);

        writeStatus("Age must be between 18 and 100. Type less than that");
        selenium.type(tfAgeId, "3");
        selenium.fireEvent(tfAgeId, "blur");
        waitForAjaxCompletion();
        assertPresent(tfAgeErrMsg);

        writeStatus("Email must be an email. Type not a well-formed email");
        selenium.type(tfEmailId, "notemail");
        selenium.fireEvent(tfEmailId, "blur");
        waitForAjaxCompletion();
        assertPresent(tfEmailErrMsg);

        writeStatus("Correct the inputs and leave. Error messages have to disappear");

        selenium.type(tfNameId, "Mick");
        selenium.fireEvent(tfNameId, "blur");
        waitForAjaxCompletion();
        assertNotPresent(tfNameErrMsg);

        selenium.type(tfAgeId, "33");
        selenium.fireEvent(tfAgeId, "blur");
        waitForAjaxCompletion();
        assertNotPresent(tfAgeErrMsg);

        selenium.type(tfEmailId, "email@ya.com");
        selenium.fireEvent(tfEmailId, "blur");
        waitForAjaxCompletion();
        assertNotPresent(tfEmailErrMsg);

    }

    private void assertPresent(String id) {
        AssertTextNotEquals(id, "", "Message [" + id + "] must not be empty on the page");
    }

    private void assertNotPresent(String id) {
        AssertTextEquals(id, "", "Message [" + id + "] must be empty on the page");
    }

    @Override
    public String getTestUrl() {
        return "pages/ajaxValidator/ajaxValidatorTest.xhtml";
    }
}
