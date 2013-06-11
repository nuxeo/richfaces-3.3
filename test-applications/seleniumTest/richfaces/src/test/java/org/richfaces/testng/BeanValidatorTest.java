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

import org.ajax4jsf.bean.validation.ValidationBean;
import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class BeanValidatorTest extends SeleniumTestBase {

	private static final String RESET_METHOD = "#{validationBean.reset}" ;
	
	private static final String NAME = "name";

    private static final String AGE = "age";

    private static final String EMAIL = "email";

    private static final String ERR_MSG_POSTFIX = "_err_msg";

    private static final String SUBMIT_BTN = "_submit";

    @Test
    public void testBeanValidatorComponent(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check bean validator component with model-based constraints defined using hibernate validator.");

        String parentId = getParentId() + "_form:";

        String tfNameId = parentId + NAME;
        String tfAgeId = parentId + AGE;
        String tfEmailId = parentId + EMAIL;
        String statusId = parentId + "status";

        String tfNameErrMsg = tfNameId + ERR_MSG_POSTFIX;
        String tfAgeErrMsg = tfAgeId + ERR_MSG_POSTFIX;
        String tfEmailErrMsg = tfEmailId + ERR_MSG_POSTFIX;

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfAgeErrMsg);
        assertNotPresent(tfEmailErrMsg);

        writeStatus("Name must be between 3 and 12 at length. Type shorter one");
        selenium.type(tfNameId, "Mi");

        writeStatus("Age must be between 18 and 100. Type less than that");
        selenium.type(tfAgeId, "3");

        writeStatus("Email must be an email. Type not a well-formed email");
        selenium.type(tfEmailId, "notemail");

        submit();

        assertPresent(tfNameErrMsg);
        assertPresent(tfAgeErrMsg);
        assertPresent(tfEmailErrMsg);
        
        AssertTextEquals(statusId, "", "Update should be skipped");

        writeStatus("Correct the inputs and resubmit form. Error messages have to disappear");

        selenium.type(tfNameId, "Mick");
        selenium.type(tfAgeId, "33");
        selenium.type(tfEmailId, "email@ya.com");

        submit();

        assertNotPresent(tfNameErrMsg);
        assertNotPresent(tfAgeErrMsg);
        assertNotPresent(tfEmailErrMsg);
        
        AssertTextEquals(statusId, ValidationBean.STATUS_TEXT, "Update model skipped");

    }

    private void submit() {
        clickAjaxCommandAndWait(getParentId() + "_form:" + SUBMIT_BTN);
    }

    private void assertPresent(String id) {
        AssertTextNotEquals(id, "", "Message [" + id + "] must not be empty on the page");
    }

    private void assertNotPresent(String id) {
        AssertTextEquals(id, "", "Message [" + id + "] must be empty on the page");
    }

    @Override
    public String getTestUrl() {
        return "pages/beanValidator/beanValidatorTest.xhtml";
    }
}
