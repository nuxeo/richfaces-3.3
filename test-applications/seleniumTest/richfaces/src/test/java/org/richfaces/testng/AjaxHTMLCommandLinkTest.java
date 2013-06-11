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
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxHTMLCommandLinkTest extends SeleniumTestBase {

    private final static String IS_ACTION_INVOKED = "_form:_action";

    private final static String IS_LISTENER_INVOKED = "_form:_listener";

    private final static String SUBMITTED_VALUE = "_form:_submittedValue";

    private final static String LOOK_AND_FEEL_TEST_URL = "pages/ajaxHTMLCommandLink/styleAndClasseStandardHTMLAttributesTest.xhtml";

    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testAjaxHTMLCommandLinkComponent(Template template) {
        renderPage(template);

        String parentId = getParentId() + "_form:";
        String linkId = null;

        linkId = parentId + "l2";
        writeStatus("Click link 2");
        clickCommandAndWait(linkId);
        assertValueSubmitted(false);
        assertActionInvoked(false);
        assertListenerInvoked(false);

        linkId = parentId + "l3";
        writeStatus("Click link 3");
        clickCommandAndWait(linkId);
        assertValueSubmitted(false);
        assertActionInvoked(true);
        assertListenerInvoked(true);

        linkId = parentId + "l4";
        writeStatus("Click link 4");
        clickCommandAndWait(linkId);
        assertValueSubmitted(false);
        assertActionInvoked(true);
        assertListenerInvoked(true);

        linkId = parentId + "l1";
        writeStatus("Click link 1");
        clickCommandAndWait(linkId);
        assertValueSubmitted(true);
        assertActionInvoked(true);
        assertListenerInvoked(true);
    }

    @Test
    public void testComponentDesignMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Check action and actionListener defined as component attributes and actionListener defined as nested tag are invoked");
        writeStatus(" on the server after button is pressed, navigation occurs");
        tester.testAS();
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute");
        tester.testASImmediate();
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testASImmediateWithExternalValidationFailed();
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: listeners and navigation does not work");
        autoTester.testASExtrenalValidationFailure();
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
        tester.testASRequestParameters(params);
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        renderAutoTestPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check standart HTML attributes");
        assertEvents(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID), SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        renderAutoTestPage(LOOK_AND_FEEL_TEST_URL, template, null);
        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        String componentId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        assertStyleAttributeContains(componentId, "font-size: 16px", "Style attribute was not output to client");
        assertClassAttributeContains(componentId, "noclass", "Class attribute was not output to client");
    }

    @Test
    public void testComponentIsRenderedAsLinkWithTextDefinedAsValueAttribute(Template template) {
        renderAutoTestPage(LOOK_AND_FEEL_TEST_URL, template, null);
        writeStatus("Check component is rendered as link with text defined as value attribute");

        String componentId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        isRenderedAs(componentId, "a");
        AssertTextEquals(componentId, "HtmlCommandLink",
                "Component should be rendered as link with text defined as value attribute");
    }

    @Override
    public void sendAction() {
        clickCommandAndWait(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID));
    }

    private void assertValueSubmitted(boolean submitted) {
        if (submitted) {
            writeStatus("Check that a new value is submitted");
            AssertTextEquals(getParentId() + SUBMITTED_VALUE, "text", "A new value is not submitted");
        } else {
            writeStatus("Check that a new value is not submitted");
            AssertTextEquals(getParentId() + SUBMITTED_VALUE, "", "A new value is submitted");
        }
    }

    private void assertActionInvoked(boolean invoked) {
        if (invoked) {
            writeStatus("Check that action is invoked");
            AssertTextEquals(getParentId() + IS_ACTION_INVOKED, "true", "An action is not invoked");
        } else {
            writeStatus("Check that action is not invoked");
            AssertTextEquals(getParentId() + IS_ACTION_INVOKED, "false", "An action is invoked");
        }
    }

    private void assertListenerInvoked(boolean invoked) {
        if (invoked) {
            writeStatus("Check that listener is invoked");
            AssertTextEquals(getParentId() + IS_LISTENER_INVOKED, "true", "A listener is not invoked");
        } else {
            writeStatus("Check that listener is not invoked");
            AssertTextEquals(getParentId() + IS_LISTENER_INVOKED, "false", "A listener is invoked");
        }
    }

    private void isRenderedAs(String id, String tagName) {
        if (selenium.getXpathCount("//" + tagName + "[@id='" + id + "']").intValue() != 1) {
            Assert.fail("Dom element with id [" + id + "] is not rendered as <" + tagName + ">");
        }
    }

    @Override
    public String getTestUrl() {
        return "pages/ajaxHTMLCommandLink/ajaxHTMLLinkTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxHTMLCommandLink/ajaxHTMLLinkAutoTest.xhtml";
    }

}
