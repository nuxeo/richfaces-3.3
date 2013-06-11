package org.richfaces.testng;

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PanelMenuItemTest extends SeleniumTestBase {

    protected final static String LOOK_AND_FEEL_TEST_URL = "styleAndClasseStandardHTMLAttributesTest.xhtml";

    protected static final String FORM_ID = "form:";

    protected static final String CONTROL_FORM_ID = "control:";

    private static Map<String, String> params = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
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
        tester.testRequestParameters(params);
    }

    @Test
    public void testBypassUpdatesAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with bypassUpdates = true skips update model values phase");
        tester.testBypassUpdate();
    }

    @Test
    public void testOncomplete(Template template) {
      AutoTester tester = getAutoTester(this);
      tester.renderPage(template, null);
      writeStatus("Test oncomplete attribute");
      tester.testOncomplete();
    }

    @Test
    public void testLimitToListAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
        tester.testLimitToList();
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute");
        tester.testImmediate();
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed();
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute in case of external validation failure");
        autoTester.testAjaxSingle();
    }

    @Test
    public void testAjaxSingleWithExternalAndProcessedComponentsValidationFailures(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Test ajaxSingle attribute in case of validation failures of both external and processed components");
        autoTester.testAjaxSingleWithProcesExternalValidation(false);
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check standard HTML attributes are output to client");

        String parentId = getParentId();
        String menuItemId = "icon" + parentId + FORM_ID + "componentId";

        disable(false);

        writeStatus("Check standart HTML attributes");
        assertEvents(menuItemId, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check styles and classes are output to client");

        String parentId = getParentId();
        String itemId = parentId + FORM_ID + "componentId";

        writeStatus("Check enabled component at first");
        disable(false);

        String styleElementId = "tablehide" + parentId + FORM_ID + "componentId";
        assertStyleAttribute(styleElementId, "font-size: 16px", "Style attribute was not output to client");
        assertClassNames(styleElementId, new String[] { "noclass" }, "Class attribute was not output to client", true);

        writeStatus("Check hoverClass/hoverStyle attributes");
        selenium.mouseOver(styleElementId);
        //assertStyleAttribute(styleElementId, "font-size: 14px", "hoverStyle attribute was not output to client");
        assertClassNames(styleElementId, new String[] { "hover-class" }, "hoverClass attribute was not output to client", true);
        selenium.mouseOut(styleElementId);

        writeStatus("Check iconClass/iconStyle attributes");
        String iconTdXpath = "//*[@id='" + itemId + "']/table/tbody/tr/td[1]";
        //assertStyleAttribute(iconTdXpath, "font-size: 15px", "iconStyle attribute was not output to client");
        assertClassNames(iconTdXpath, new String[] { "icon-class" }, "iconClass attribute was not output to client", false);

        writeStatus("Check disabled component");
        disable(true);

        writeStatus("Check disabledClass/disabledStyle attributes");
        assertStyleAttribute(styleElementId, "font-size: 13px", "disabledStyle attribute was not output to client");
        assertClassNames(styleElementId, new String[] { "disabled-class" }, "disabledStyle attribute was not output to client", true);
    }

    @Test
    public void testIconsAttributesApply(Template  template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check icons attributes apply: are output to client and images are accessible");
        String itemId = getParentId() + FORM_ID + "componentId";
        String iconImgXpath = "//*[@id='" + itemId + "']/table/tbody/tr/td[1]/img";

        writeStatus("Check iconDisabled attribute");
        disable(true);

        Assert.assertTrue(isPresent(iconImgXpath), "Icon is not rendered");
        testIcon(iconImgXpath, "Disc");

        writeStatus("Check icon attribute");
        disable(false);

        Assert.assertTrue(isPresent(iconImgXpath), "Icon is not rendered");
        testIcon(iconImgXpath, "Chevron");
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait("icon" + getAutoTester(this).getClientId("") + "componentId");
    }

    @Override
    public String getAutoTestUrl() {
        return "panelMenuItemAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return null;
    }

    @Override
    public String getTestBase() {
        return "pages/panelMenuItem/";
    }

    protected boolean disable(boolean isDisabled) {
        String parentId = getParentId();
        String isDisabledId = parentId + CONTROL_FORM_ID + "isDisabled";
        String disablingControlId = parentId + CONTROL_FORM_ID + "disable";
        boolean prevValue = Boolean.parseBoolean(runScript("document.getElementById('" + isDisabledId + "').checked"));
        if (prevValue != isDisabled) {
            runScript("document.getElementById('" + isDisabledId + "').checked=" + isDisabled);
            clickCommandAndWait(disablingControlId);
        }
        return prevValue;
    }

    /**
     * Test an icon.
     *
     * @param location location of image representing icon to be tested
     * @param iconName substring that icon uri has to contain
     */
    protected void testIcon(String location, String iconSubstring) {
        String iconSrc = selenium.getAttribute(location + "/@src");
        if (null == iconSrc || !iconSrc.matches(".*" + iconSubstring + ".*")) {
            Assert.fail("It looks as if the icon is not proper. Uri of icon is being tested must contain ["
                    + iconSubstring + "]");
        }
    }
}
