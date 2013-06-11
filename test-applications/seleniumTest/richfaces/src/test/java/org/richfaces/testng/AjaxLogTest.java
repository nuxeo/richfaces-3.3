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
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.richfaces.AutoTester.TestSetupEntry;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxLogTest extends SeleniumTestBase {

    private final static String RESET_METHOD = "#{logBean.reset}";

    private final static String INIT_POPUP_MODE = "#{logBean.initPopupMode}";

    @Test
    public void testLogComponentWithPopupTrue(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_POPUP_MODE);
        String inputElemId = tester.getClientId("input");

        writeStatus("Check log component opens popup window by hotkey, ajax request changes component content");
        selenium.controlKeyDown();
        selenium.shiftKeyDown();
        selenium.keyDown(inputElemId, "\\76");
        selenium.shiftKeyUp();
        selenium.controlKeyUp();

        selenium.windowFocus();

        //writeStatus("Type smth. All typing is being logged");
        
        //http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/Selenium%20FAQ#section-Selenium+FAQ-WhyDoYouUseTheTypeKeysCommandAndThenFollowThatCommandUpWithTypeWithTheExactSameTextInAdditionINoticedSomeTyposWhenYouWroteTheValuesForTheTypeKeysCommands
        selenium.typeKeys(inputElemId, "taping");

        try {
            selenium.selectWindow("logWindow");
            selenium.windowFocus();
            // at least dozen messages have to be logged
            String body = selenium.getBodyText();
            if (!body.contains("debug[")) {
                Assert.fail("There is no debug messages here");
            }

            // check clear button
            selenium.click("//button");

            body = selenium.getBodyText();
            if (body.contains("debug[")) {
                Assert.fail("log must be empty");
            }
        } finally {
            selenium.selectWindow(null);
        }
    }

    @Test
    public void testLogComponentWithPopupFalse(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check log component is embedded, ajax request changes component content");

        String inputElemId = tester.getClientId("input");
        String componentId = "logConsole";

        writeStatus("Type smth. All typing is being logged");

        http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/Selenium%20FAQ#section-Selenium+FAQ-WhyDoYouUseTheTypeKeysCommandAndThenFollowThatCommandUpWithTypeWithTheExactSameTextInAdditionINoticedSomeTyposWhenYouWroteTheValuesForTheTypeKeysCommands 
        selenium.typeKeys(inputElemId, "taping");
        waitForAjaxCompletion();

        writeStatus("test that log element is present");
        AssertRendered(componentId);

        writeStatus("at least dozen messages have to be logged");
        int logCount = selenium.getXpathCount("//div[@id='" + componentId + "']/div").intValue();
        if (logCount < 12) {
            Assert.fail("There are suspiciously few log messages here");
        }
    }

    @Test
    public void testClearLogButton(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check clear button clears log");

        String componentId = "logConsole";
        String inputElemId = tester.getClientId("input");

        writeStatus("Type smth. All typing is being logged");

        http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/Selenium%20FAQ#section-Selenium+FAQ-WhyDoYouUseTheTypeKeysCommandAndThenFollowThatCommandUpWithTypeWithTheExactSameTextInAdditionINoticedSomeTyposWhenYouWroteTheValuesForTheTypeKeysCommands
        selenium.typeKeys(inputElemId, "taping");
        waitForAjaxCompletion();

        writeStatus("test that log element is present");
        AssertRendered(componentId);

        writeStatus("test that clear button is present and has proper label");
        String buttonXpath = "//div[@id='" + componentId + "']/button";

        if (selenium.isElementPresent(buttonXpath)) {
            String clearBtnLabel = selenium.getText(buttonXpath);
            if (null == clearBtnLabel || !clearBtnLabel.trim().equalsIgnoreCase("Clear")) {
                Assert.fail("Clear button has obscure label");
            }
        } else {
            Assert.fail("Clear button is not rendered");
        }

        writeStatus("At least dozen messages have to be logged. Check it!");
        String logDivXpath = "//div[@id='" + componentId + "']/div";
        int logCount = selenium.getXpathCount(logDivXpath).intValue();
        if (logCount < 12) {
            Assert.fail("There are suspiciously few log messages here");
        }

        writeStatus("check clear button");
        selenium.click(buttonXpath);

        logCount = selenium.getXpathCount(logDivXpath).intValue();
        if (logCount > 0) {
            Assert.fail("log must be empty");
        }
    }

    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        String componentId = "logConsole";

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        assertStyleAttributeContains(componentId, "font-size: 13px", "Style attribute was not output to client");
        assertClassAttributeContains(componentId, "noclass", "Class attribute was not output to client");

        int w = selenium.getElementWidth(componentId).intValue();
        int h = selenium.getElementHeight(componentId).intValue();

        if (800 != w || 300 != h) {
            Assert.fail("Style does not affect ajax log component");
        }

        writeStatus("Test component html events");
        assertEvents(componentId, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.reset();
        tester.setupControl(TestSetupEntry.rendered, Boolean.FALSE);
        tester.clickLoad();

        String componentId = "logConsole";
        AssertNotPresent(componentId, "Rendered attribute does not work");
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/ajaxLog/ajaxLogAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        throw new UnsupportedOperationException();
    }

}
