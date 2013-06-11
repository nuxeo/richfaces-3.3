package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.richfaces.AutoTester.TestSetupEntry;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxPageTest extends SeleniumTestBase {

    private final static String TIMESTAMP_ID = "form:timestamp";

    private final static String STATUS_ID = "form:status";

    private final static String INPUT_ID = "form:input";

    private final static String SUBMIT_ID = "form:submit";

    @Test
    public void testRenderer(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        selenium.selectFrame("page");
        try {
            String contentType = selenium.getAttribute("//meta@content");
            Assert.assertEquals(contentType, "application/xhtml+xml");
        } finally {
            selenium.selectFrame("relative=parent");
        }
        writeStatus("Check facets");
        selenium.selectFrame("page");
        try {
            String bodyText = selenium.getText("//body");
            if (!bodyText.contains("Page Content here")) {
                Assert.fail("Page Content is not output at all");
            }
            if (bodyText.contains("Head Content here")) {
                Assert.fail("Head facet is output to wrong place (must to head but really to body)");
            }
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Test
    public void testAjaxListener(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Check ajaxListener fires on each AJAX request");
        selenium.selectFrame("page");
        try {
            clickAjaxCommandAndWait(SUBMIT_ID);
            AssertTextEquals(STATUS_ID, "AjaxEvent", "ajaxListeners are not triggered");
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Test
    public void testSelfRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Check selfRendered attribute. Each ajax request should cause component to be rerendered");
        selenium.selectFrame("page");
        try {
            String was = selenium.getText(TIMESTAMP_ID);
            clickAjaxCommandAndWait(SUBMIT_ID);
            AssertTextNotEquals(TIMESTAMP_ID, was, "selfRendered attribute does not work");
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        tester.reset();
        tester.setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        tester.clickLoad();
        writeStatus("Check that AjaxEvents are delivered in Apply Request Values instead of Invoke Application phase");
        writeStatus("Move input to invalid state");
        selenium.selectFrame("page");
        try {
            type(INPUT_ID, "A");
            clickAjaxCommandAndWait(SUBMIT_ID);
            AssertTextEquals(STATUS_ID, "AjaxEvent", "immediate attribute does not work");
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        selenium.selectFrame("page");
        try {
            assertStyleAttributeContains("//body", "font-size: 13px", "Style attribute was not output to client");
            assertClassAttributeContains("//body", "noclass", "Class attribute was not output to client");
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        tester.reset();
        tester.setupControl(TestSetupEntry.rendered, Boolean.FALSE);
        tester.clickLoad();

        selenium.selectFrame("page");
        try {
            AssertTextEquals("//body", "", "Rendered attribute does not work");
        } finally {
            selenium.selectFrame("relative=parent");
        }
    }

    @Override
    public String getAutoTestUrl(){
        return "pages/ajaxPage/ajaxPageAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return null;
    }

}
