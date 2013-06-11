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

public class AjaxRegionTest extends SeleniumTestBase {

    @Test
    public void testAjaxRegionComponent(Template template) {
        renderPage(template);

        String parentId = getParentId() + "_form:";

        writeStatus("check nested regions");

        String externalLink = parentId + "externalLink";
        String internalLink = parentId + "internalLink";

        String internalElemId = parentId + "internal";
        String externalElemId = parentId + "external";

        type(internalElemId, "5");
        type(externalElemId, "5");

        clickAjaxCommandAndWait(internalLink);
        AssertValueEquals(internalElemId, "5");
        AssertValueEquals(externalElemId, "0");

        type(internalElemId, "10");
        type(externalElemId, "10");

        clickAjaxCommandAndWait(externalLink);
        AssertValueEquals(internalElemId, "10");
        AssertValueEquals(externalElemId, "10");

        writeStatus("verify \"selfRendered\" component's attribute");

        String selfRenderedLink = parentId + "selfRenderedLink";
        clickAjaxCommandAndWait(selfRenderedLink);
        AssertNotPresent("transientText");

        writeStatus("verify \"renderRegionOnly\" component's attribute");

        String renderOnlyLink = parentId + "renderOnlyLink";
        String renderLink = parentId + "renderLink";

        String renderOnlyElemId = parentId + "renderOnly";
        String renderElemId = parentId + "render";

        type(renderOnlyElemId, "7");

        clickAjaxCommandAndWait(renderOnlyLink);
        AssertValueEquals(renderOnlyElemId, "7");
        AssertValueEquals(renderElemId, "0");

        type(renderElemId, "11");
        clickAjaxCommandAndWait(renderLink);

        AssertValueEquals(renderOnlyElemId, "11");
        AssertValueEquals(renderElemId, "11");
    }

    // FIXME https://jira.jboss.org/jira/browse/RF-4883
    @Test(groups=FAILURES_GROUP)
    public void testAjaxListenerInvokedOnEachAJAXRequest(Template template) {
        renderPage(template);

        writeStatus("Check ajax listener is invoked on each AJAX request");

        String parentId = getParentId() + "_form:";

        String externalLink = parentId + "externalLink";
        String internalLink = parentId + "internalLink";

        String outerListener = parentId + "outerListener";
        String innerListener = parentId + "innerListener";

        clickAjaxCommandAndWait(internalLink);
        AssertTextEquals(innerListener, "true", "AjaxListener for inner region must have been invoked");
        AssertTextEquals(outerListener, "false", "AjaxListener for outer region mustn't have been invoked");

        clickAjaxCommandAndWait(externalLink);
        AssertTextEquals(innerListener, "true", "AjaxListener for inner region must have been invoked");
        AssertTextEquals(outerListener, "true", "AjaxListener for outer region must have been invoked");
    }

    public String getTestUrl() {
        return "pages/ajaxRegion/ajaxRegionTest.xhtml";
    }

}
