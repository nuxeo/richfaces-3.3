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

public class TooltipTest extends SeleniumTestBase {

    private final static String RESET_METHOD = "#{tooltipBean.reset}";

    private final static String INIT_CLIENT_TEST = "#{tooltipBean.initClientTest}";

    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute");
        tester.testImmediate();
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed();
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute");
        tester.testAjaxSingle();
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check that listeners are not invoked in case of external validation failure");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testCoreAjaxMode(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Component retrieves data from server; 'defaultContent' facet is handled; listeners fire");
        String componentId = autoTester.getClientId(AutoTester.COMPONENT_ID);
        String contentId = autoTester.getClientId(AutoTester.COMPONENT_ID + "content");
        String defaultContentId = autoTester.getClientId(AutoTester.COMPONENT_ID + "defaultContent");

        triggerTooltip();
        writeStatus("Check 'defaultContent' feature");
        //It seems event handlers registered slow down tooltip appearance a little bit ... wait 0.5 second
        waiteForCondition("document.getElementById('" + componentId + "').style.display != 'none'", 500);
        AssertTextEquals(defaultContentId, "Wait...");
        waitForAjaxCompletion();

        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 0");
        assertTooltipPositioning();

        writeStatus("Check tooltip content (ajax mode) is rendered on server every time");
        triggerTooltipAndWait();
        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 1");
        assertTooltipPositioning();
    }

    @Test
    public void testCoreClientMode(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, INIT_CLIENT_TEST);
        writeStatus("Check component is activated by defined event(onclick); its positioning and dimensions are ok");

        String componentId = autoTester.getClientId(AutoTester.COMPONENT_ID);
        String contentId = autoTester.getClientId(AutoTester.COMPONENT_ID + "content");

        triggerTooltip();
        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 0");
        assertTooltipPositioning();

        writeStatus("Check tooltip content (client mode) is rendered on server only once");

        triggerTooltip();
        AssertVisible(componentId);
        assertTooltipPositioning();
        AssertTextEquals(contentId, "Tooltip hit count 0");
    }

    private void assertTooltipPositioning() {
        writeStatus("Check bottom-right positionning");
        String componentId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        String tooltipAreaId = getAutoTester(this).getClientId("tooltipArea");

        String tooltipLeft = WINDOW_JS_RESOLVER + "$('" + componentId + "').cumulativeOffset().left";
        String tooltipTop = WINDOW_JS_RESOLVER + "$('" + componentId + "').cumulativeOffset().top";

        // we click at 1,1 of tooltipArea
        String eventLeft = WINDOW_JS_RESOLVER + "$('" + tooltipAreaId + "').cumulativeOffset().left + 1";
        String eventTop = WINDOW_JS_RESOLVER + "$('" + tooltipAreaId + "').cumulativeOffset().top + 1";

        String leftExpr = String.format("Math.abs((%1$s) - (%2$s)) <= 2", eventLeft, tooltipLeft);
        String topExpr = String.format("Math.abs((%1$s) - (%2$s)) <= 2", eventTop, tooltipTop);

        if ("false".equalsIgnoreCase(selenium.getEval(leftExpr))) {
            Assert.fail(selenium.getEval(eventLeft) + " == " + selenium.getEval(tooltipLeft));
        }

        if ("false".equalsIgnoreCase(selenium.getEval(topExpr))) {
            Assert.fail(selenium.getEval(eventTop) + " == " + selenium.getEval(tooltipTop));
        }
    }

    @Test
    public void testJSAPI(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check JS API is present and works");
        String componentId = autoTester.getClientId(AutoTester.COMPONENT_ID);
        String contentId = autoTester.getClientId(AutoTester.COMPONENT_ID + "content");

        String event = "createMouseEvent('" + componentId + "',click, 1, 1, false)";

        writeStatus("Check show() function");
        AssertNotVisible(componentId);
        invokeFromComponent(componentId, "show", event);
        waitForAjaxCompletion();
        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 0");

        writeStatus("Check hide() function");
        invokeFromComponent(componentId, "hide", event);
        AssertNotVisible(componentId);

        writeStatus("Check disable() function");
        writeStatus("Check disabled tooltip cannot be shown neither by js event nor by js api");
        invokeFromComponent(componentId, "disable", event);
        triggerTooltip();
        AssertNotVisible(componentId);
        invokeFromComponent(componentId, "show", event);
        AssertNotVisible(componentId);

        writeStatus("Check enable() function");
        invokeFromComponent(componentId, "enable", event);
        triggerTooltipAndWait();
        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 1");
        invokeFromComponent(componentId, "show", event);
        waitForAjaxCompletion();
        AssertVisible(componentId);
        AssertTextEquals(contentId, "Tooltip hit count 2");
    }

    @Test
    public void testDisabledAttribute(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check disabled attribute");

        autoTester.disableComponent(true);
        writeStatus("Check tooltip is not shown up, server is not hit, listeners are not invoked");
        triggerTooltip();
        String componentId = autoTester.getClientId(AutoTester.COMPONENT_ID);
        AssertNotVisible(componentId);
        autoTester.checkActionListener(false);
    }

    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check style and classes, standard HTML attributes are output to client");

        String componentId = autoTester.getClientId(AutoTester.COMPONENT_ID);
        String tooltipAreaId = getAutoTester(this).getClientId("tooltipArea");

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        assertStyleAttributeContains(componentId, "font-size: 13px", "Style attribute was not output to client");
        assertClassAttributeContains(componentId, "noclass", "Class attribute was not output to client");

        writeStatus("Test component html events");
        assertEvents(componentId, SeleniumEvent.STANDARD_HTML_EVENTS);

        triggerTooltipAndWait();
        assertEvent("onshow");
        selenium.mouseOut(tooltipAreaId);
        assertEvent("onhide");
    }

    @Override
    public void sendAjax() {
        triggerTooltipAndWait();
    }

    private void triggerTooltip() {
        selenium.clickAt(getAutoTester(this).getClientId("tooltipArea"), "1,1");
    }

    private void triggerTooltipAndWait() {
        selenium.clickAt(getAutoTester(this).getClientId("tooltipArea"), "1,1");
        waitForAjaxCompletion();        
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/tooltip/tooltipAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        throw new UnsupportedOperationException();
    }
}
