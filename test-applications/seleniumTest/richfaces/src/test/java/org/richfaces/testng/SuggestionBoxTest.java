package org.richfaces.testng;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class SuggestionBoxTest extends SeleniumTestBase {

    private static Map<String, String> params = new HashMap<String, String>();

    private final static String RESET_METHOD = "#{suggestionBean.reset}";

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
    }

    @Test
    public void testOncomplete(Template template) {
      AutoTester tester = getAutoTester(this);
      tester.renderPage(template, RESET_METHOD);
      writeStatus("Test oncomplete attribute");
      tester.testOncomplete();
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testLimitToListAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
        tester.testLimitToList();
    }

    //https://jira.jboss.org/jira/browse/RF-6606
    @Test(groups=FAILURES_GROUP)
    public void testBypassUpdatesAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with bypassUpdates = true skips update model values phase");
        tester.testBypassUpdate(false);
    }

    //https://jira.jboss.org/jira/browse/RF-6605
    @Test(groups=FAILURES_GROUP)
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute");
        tester.testImmediate(false);
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed(false);
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute");
        tester.testAjaxSingle(false);
    }

    @Test
    public void testAjaxSingleWithInternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute in case of invalid children state");
        tester.testAjaxSingleWithInternalValidationFailed();
    }

    @Test
    public void testAjaxSingleWithProcessExternalValidation(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        tester.testAjaxSingleWithProcesExternalValidation(false);
    }

    @Test
    public void testKeyboardNavigation(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check keyboard navigation works for component");
        String componentId = getAutoTester(this).getClientId("suggestion");

        type(componentId, "stan");
        waitForAjaxCompletion();
        selenium.keyDown(componentId, "\\40");//down x 4 times
        selenium.keyDown(componentId, "\\40");
        selenium.keyDown(componentId, "\\40");
        selenium.keyDown(componentId, "\\40");
        selenium.keyDown(componentId, "\\13");//enter
        AssertValueEquals(componentId, "Uzbekistan");

        type(componentId, "stan");
        waitForAjaxCompletion();
        selenium.keyDown(componentId, "\\40");//down x 2 times
        selenium.keyDown(componentId, "\\40");
        selenium.keyDown(componentId, "\\38");//up
        selenium.keyDown(componentId, "\\13");//enter
        AssertValueEquals(componentId, "Kyrgyzstan");
    }

    @Test
    public void testJSAPI(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check JS API is present and works");

        writeStatus("Check callSuggestion(ignoreMinChars) function");
        String componentId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        String suggestInputId = getAutoTester(this).getClientId("suggestion");
        type(suggestInputId, "Be");
        invokeFromComponent(componentId, "callSuggestion", true);
        waitForAjaxCompletion();
        selenium.keyDown(suggestInputId, "\\13");//enter
        AssertValueEquals(suggestInputId, "Belarus");

        writeStatus("Check getSelectedItems() function");
        type(suggestInputId, "Aze");//Azerbaijan
        waitForAjaxCompletion();
        selenium.keyDown(suggestInputId, "\\13");//enter
        String capital = runScript(String.format("document.getElementById('%1$s').component.getSelectedItems()[0].capital", componentId));
        Assert.assertEquals("Baku", capital);
    }

    @Test
    public void testNothingLabelAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check nothingLabel attribute is shown up as sure as no suggestions available; item selection is not available");
        String componentId = tester.getClientId("suggestion");
        String suggestPopupId = tester.getClientId(AutoTester.COMPONENT_ID + ":suggest");

        writeStatus("Misspell a little");
        type(componentId, "Azir");
        waitForAjaxCompletion();
        AssertVisible(suggestPopupId, "Suggestion popup is not come up");
        Assert.assertEquals(1, selenium.getXpathCount("//table[@id='" + suggestPopupId + "']/tbody/tr"));
        String nothingLabelItemXpath = "//table[@id='" + suggestPopupId + "']/tbody/tr[1]";
        AssertTextEquals(nothingLabelItemXpath, "No countries found");

        writeStatus("Check item is not selectable");
        selenium.click(nothingLabelItemXpath);
        AssertValueEquals(componentId, "Azir");
    }

    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        String inputId = tester.getClientId("suggestion");
        String componentId = tester.getClientId(AutoTester.COMPONENT_ID);
        String spId = tester.getClientId(AutoTester.COMPONENT_ID + ":suggest");

        type(inputId, "men");
        waitForAjaxCompletion();

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        assertStyleAttributeContains(componentId, "font-size: 13px", "Style attribute was not output to client");
        assertClassAttributeContains(componentId, "noclass", "Class attribute was not output to client");

        writeStatus("Check popupClass/popupStyle attributes");
        //assertStyleAttributeContains(componentId, "font-size: 14px", "popupStyle attribute was not output to client");
        assertClassAttributeContains(componentId, "popup-class", "popupClass attribute was not output to client");

        writeStatus("Check height/width attributes");
        int w = selenium.getElementWidth(componentId).intValue();
        int h = selenium.getElementHeight(componentId).intValue();

        if (250 != h || 300 != w) {
            Assert.fail("height/width are not applied: Expected [h=250,w=300] was [h=" + h + ",w=" + w + "]");
        }

        writeStatus("Check entryClass attribute");
        assertClassAttributeContains("//table[@id='" + spId + "']/tbody/tr[1]", "entry-class", "entryClass attribute was not output to client");
        assertClassAttributeContains("//table[@id='" + spId + "']/tbody/tr[2]", "entry-class", "entryClass attribute was not output to client");

        writeStatus("Check style alternating rows feature - rowClasses attribute");
        assertClassAttributeContains("//table[@id='" + spId + "']/tbody/tr[1]", "odd-class", "rowClasses attribute is not applied");
        assertClassAttributeDoesNotContain("//table[@id='" + spId + "']/tbody/tr[1]", "even-class", "rowClasses attribute is not applied");
        assertClassAttributeContains("//table[@id='" + spId + "']/tbody/tr[2]", "even-class", "rowClasses attribute is not applied");
        assertClassAttributeDoesNotContain("//table[@id='" + spId + "']/tbody/tr[2]", "odd-class", "rowClasses attribute is not applied");

        writeStatus("Check selectedClass attribute");
        selenium.keyDown(inputId, "\\40");// select second row
        assertClassAttributeDoesNotContain("//table[@id='" + spId + "']/tbody/tr[1]", "selected-class", "selectedClass attribute was output to client");
        assertClassAttributeContains("//table[@id='" + spId + "']/tbody/tr[2]", "selected-class", "selectedClass attribute was not output to client");
    }

    @Test
    public void testTokensAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check tokens attribute allows to recall suggestion several times for different words");
        writeStatus("In this case three tokens are given: ,[]");

        String inputId = tester.getClientId("suggestion");
        type(inputId, "Bel");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        AssertValueEquals(inputId, "Belarus");
        assertSelectedCapitals("Minsk");

        typeOn(inputId, ",Arm");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        AssertValueEquals(inputId, "Belarus,Armenia");
        assertSelectedCapitals("Minsk", "Yerevan");

        typeOn(inputId, ",Rus");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        AssertValueEquals(inputId, "Belarus,Armenia,Russia");
        assertSelectedCapitals("Minsk" , "Yerevan" , "Moscow");

//        writeStatus("@ is not supported token");
//        typeOn(inputId, "@Kaz");
//        waitForAjaxCompletion();
//        selenium.keyDown(inputId, "\\13");//enter
//        AssertValueEquals(inputId, "Belarus@Kaz");
    }

    @Test
    public void testSuggestionObjectFeature(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check suggestion object feature works");
        String inputId = tester.getClientId("suggestion");
        typeOn(inputId, "Bel");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        assertSelectedCapitals("Minsk");
        typeOn(inputId, ",Kaz");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        assertSelectedCapitals("Minsk", "Astana");
        typeOn(inputId, ",Arm");
        waitForAjaxCompletion();
        selenium.keyDown(inputId, "\\13");//enter
        assertSelectedCapitals("Minsk", "Astana", "Yerevan");
    }

    @Test
    public void testCore(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);

        writeStatus("Check component is activated by user-defined events");
        writeStatus("Check list of options is fetched from server and shown");
        writeStatus("arbitrary option can be selected");

        String suggestInputId = tester.getClientId("suggestion");
        String suggestPopupId = tester.getClientId(AutoTester.COMPONENT_ID + ":suggest");

        writeStatus("Check component is activated");
        type(suggestInputId, "stan");
        waitForAjaxCompletion();
        AssertVisible(suggestPopupId, "Suggestion popup is not come up");

        writeStatus("Check list of options is fetched from server and shown");
        //5 + nothingLabel row
        Assert.assertEquals(6, selenium.getXpathCount("//table[@id='" + suggestPopupId + "']/tbody/tr"));

        writeStatus("Check arbitrary option can be selected");
        writeStatus("Try to select second item: Kyrgyzstan (Bishkek)");
        selenium.click("//table[@id='" + suggestPopupId + "']/tbody/tr[2]");
        AssertValueEquals(suggestInputId, "Kyrgyzstan");
        assertSelectedCapitals("Bishkek");
    }

    @Test
    public void testMultiColumnPossibility(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check multi-column display is possible using var attribute");

        String suggestInputId = tester.getClientId("suggestion");
        String suggestPopupId = tester.getClientId(AutoTester.COMPONENT_ID + ":suggest");
        String firstOptionXpath = "//table[@id='" + suggestPopupId + "']/tbody/tr[1]/td";
        type(suggestInputId, "Bel");
        waitForAjaxCompletion();
        writeStatus("Check option's columns are rendered properly");
        Assert.assertFalse(isVisible(firstOptionXpath + "[1]"));
        AssertTextEquals(firstOptionXpath + "[2]", "Belarus");
        AssertTextEquals(firstOptionXpath + "[3]", "Minsk");
        AssertTextEquals(firstOptionXpath + "[4]", "207600.0");
        AssertTextEquals(firstOptionXpath + "[5]", "9689800");
    }

    private void assertSelectedCapitals(String... capitals) {
        String liXpath = "//*[@id='suggestionObject']/ul/li";
        Assert.assertEquals(capitals.length, selenium.getXpathCount(liXpath).intValue());
        for (int i = 0; i < capitals.length; i++) {
            AssertTextEquals(liXpath + "[" + (i + 1) + "]", capitals[i]);
        }
    }

    @Override
    public void sendAjax() {
        type(getAutoTester(this).getClientId("suggestion"), "Bel");
        waitForAjaxCompletion();
        selenium.click("//table[@id='" + getAutoTester(this).getClientId(AutoTester.COMPONENT_ID + ":suggest']/tbody/tr[1]"));
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/suggestionBox/suggestionBoxAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        throw new UnsupportedOperationException();
    }

}
