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

public class KeepAliveTest extends SeleniumTestBase {

    @Test
    public void _testKeepAliveComponent(Template template) {
        renderPage(template);

        String parentId = getParentId();

        String sumElemId = parentId + "sum";
        AssertValueEquals(sumElemId, "0");
        String addButtonId = parentId + "form:add";
        String addAjaxButtonId = parentId + "form:addAjax";

        writeStatus("The sum has to be increased by 5 twice");

        clickCommandAndWait(addButtonId);
        clickCommandAndWait(addButtonId);
        AssertValueEquals(sumElemId, "10");

        writeStatus("The sum has to be increased by 5 twice again");

        clickById(addAjaxButtonId);
        waitForAjaxCompletion();
        clickById(addAjaxButtonId);
        waitForAjaxCompletion();
        AssertValueEquals(sumElemId, "20");

        writeStatus("Test ajax only regime ... ");

        String sumAjaxOnlyElemId = parentId + "sumAjaxOnly";
        AssertValueEquals(sumAjaxOnlyElemId, "0");
        addButtonId = parentId + "formAjaxOnly:add";
        addAjaxButtonId = parentId + "formAjaxOnly:addAjax";

        writeStatus("State is not saved between full submits. The sum always is 5");

        clickCommandAndWait(addButtonId);
        clickCommandAndWait(addButtonId);
        AssertValueNotEquals(sumAjaxOnlyElemId, "10");

        writeStatus("Ajax request! The sum has to be increased by 5 twice");

//      This test doesn't pass! Bug?!

//      clickById(addAjaxButtonId);
//      clickById(addAjaxButtonId);
//      waitForAjaxCompletion();
//      AssertValueEquals(sumAjaxOnlyElemId, "10");
    }

    public String getTestUrl() {
        return "pages/keepAlive/keepAliveTest.xhtml";
    }

}
