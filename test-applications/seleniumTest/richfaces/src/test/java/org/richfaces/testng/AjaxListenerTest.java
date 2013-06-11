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

public class AjaxListenerTest extends SeleniumTestBase {

    @Test
    public void testAjaxListenerComponent(Template template) {
        renderPage(template);

        String parentId = getParentId() + "_form:";

        String inputElemId = parentId + "input";
        String outputElemId = parentId + "output";
        String ajaxSubmitElemId = outputElemId;

        writeStatus("Try to submit form with invalid input. In contrast to <f:actionListener> and "
                + "<f:valueChangeListener> ajax listener must be invoked anyway");

        clickAjaxCommandAndWait(ajaxSubmitElemId);
        AssertTextEquals(outputElemId, "Validation failed, but ajax listener is invoked anyway",
                "Ajax listener must be invoked even through validation failed");

        writeStatus("Correct input and try to submit again");
        type(inputElemId, "5");

        clickAjaxCommandAndWait(ajaxSubmitElemId);
        AssertTextEquals(outputElemId, "Ajax listener has been invoked successfully", "Ajax listener has not been invoked");

    }

    @Override
    public String getTestUrl() {
        return "pages/ajaxListener/ajaxListenerTest.xhtml";
    }

}
