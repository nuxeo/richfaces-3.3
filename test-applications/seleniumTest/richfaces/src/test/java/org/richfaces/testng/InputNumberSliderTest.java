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

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InputNumberSliderTest extends SeleniumTestBase {

    private static final int BAR_SCALE = 10;

    private final static String RESET_METHOD = "#{sliderBean.reset}";

    private final static String ENABLE_MANUAL_INPUT_TEST = "#{sliderBean.initEnableManualInputAttributeTest}";

    private final static String LOOK_AND_FEEL_TEST_URL = "pages/inputNumberSlider/styleAndClasseStandardHTMLAttributesTest.xhtml";

    //https://jira.jboss.org/jira/browse/RF-3600
    @Test(groups=FAILURES_GROUP)
    public void testInputNumberSlider(Template template) {
        renderPage(template, RESET_METHOD);

        String slider = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);

        String input = slider + "Input";
        String tip = slider + "Tip";
        String track = slider + "Track";

        String ajax = getAutoTester(this).getClientId("ajax");
        String server = getAutoTester(this).getClientId("server");
        String output = getAutoTester(this).getClientId("output");

        writeStatus("Checking initial rendering");
        AssertNotVisible(tip);
        checkSliderVisualState(40);
        AssertTextEquals(output, "40");

        writeStatus("Checking if tip is visible during click");
        selenium.mouseDownAt(track, "800,1");
        AssertVisible(tip);
        selenium.mouseUpAt(track, "800,1");
        AssertNotVisible(tip);

        writeStatus("Checking if value is changed with previous click");
        checkSliderVisualState(80);

        writeStatus("Checking if value is changed with input field");
        selenium.type(input, "22");
        checkSliderVisualState(22);

        writeStatus("Checking if slider is properly re-rendered and submitted with ajax");
        clickAjaxCommandAndWait(ajax);
        checkSliderVisualState(32);
        AssertTextEquals(output, "32");

        writeStatus("Checking if slider is properly submitted");
        clickCommandAndWait(server);
        checkSliderVisualState(42);
        AssertTextEquals(output, "42");

        writeStatus("Checking validation");
        selenium.mouseDownAt(track, "950,1");
        selenium.mouseUpAt(track, "950,1");
        clickAjaxCommandAndWait(ajax);
        checkSliderVisualState(95);
        AssertTextEquals(output, "42");

        selenium.mouseDownAt(track, "300,1");
        selenium.mouseUpAt(track, "300,1");
        clickAjaxCommandAndWait(ajax);
        checkSliderVisualState(40);
        AssertTextEquals(output, "40");
    }

    @Test
    public void testValueChangeEventFiredAndModelUpdatedOnSubmit(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        String submittedValueId = tester.getClientId("submittedValue");

        writeStatus("Check ValueChangeListeners invoked on submit and model binding is updated on value changed");
        Assert.assertEquals(getSliderValue(), "40");
        tester.testSubmit();
        AssertTextEquals(submittedValueId, "50", "Model binding is not updated on value changed");
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
        tester.testSubmitImmediate();
    }

    @Test
    public void testValidatorAndValidatorMessageAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check validator and validatorMessage attributes");
        tester.testValidatorAndValidatorMessageAttributes(true);
    }

    @Test
    public void testDisabledAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check component can be disabled");

        tester.disableComponent(true);
        String inputId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "Input";
        Assert.assertFalse(selenium.isEditable(inputId), "Slider's input has to be read only");
    }

    @Test
    public void testCorrectManualInputMovesSliderHandlerRespectively(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check correct manual input moves slider handler respectively");
        String input = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "Input";

        selenium.type(input, "20");
        checkSliderVisualState(20);

        selenium.type(input, "70");
        checkSliderVisualState(70);
    }

    @Test
    public void testIncorrectManualInputMovesSliderHandlerToTheLeftEdge(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check incorrect manual input moves slider handler to the left edge");
        String input = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "Input";

        selenium.type(input, "-20");
        checkSliderVisualState(0);
        Assert.assertEquals(getSliderValue(), "0", "Slider value has to be dropped to zero");
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        String slider = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        writeStatus("Check component's specific HTML attributes are output to client");

        List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
        events.add(SeleniumEvent.ONKEYDOWN);
        events.add(SeleniumEvent.ONKEYUP);
        events.add(SeleniumEvent.ONKEYPRESS);

        assertEvents(slider + "Input", events);

        writeStatus("Check standart HTML attributes");
        assertEvents(slider, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        String sliderId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        assertStyleAttributeContains(sliderId, "font-size: 17px", "Style attribute was not output to client");
        assertClassAttributeContains(sliderId, "noclass", "Class attribute was not output to client");

        writeStatus("Check inputClass/inputStyle attributes");
        String inputId = sliderId + "Input";
        assertStyleAttributeContains(inputId, "font-size: 16px", "inputStyle attribute was not output to client");
        assertClassAttributeContains(inputId, "input-class", "inputClass attribute was not output to client");

        writeStatus("Check decreaseClass/decreaseStyle/decreaseSelectedClass attributes");
        String decreaseArrowId = sliderId + "ArrowDec";
        assertStyleAttributeContains(decreaseArrowId, "font-size: 14px", "decreaseStyle attribute was not output to client");
        assertClassAttributeContains(decreaseArrowId, "decrease-class", "decreaseClass attribute was not output to client");

        writeStatus("Check increaseClass/increaseStyle/increaseSelectedClass attributes");
        String increaseArrowId = sliderId + "ArrowInc";
        assertStyleAttributeContains(increaseArrowId, "font-size: 15px", "increaseStyle attribute was not output to client");
        assertClassAttributeContains(increaseArrowId, "increase-class", "increaseClass attribute was not output to client");

        writeStatus("Check barClass/barStyle attributes");
        String sliderTrackXpath = "//div[contains(@class, 'rich-inslider-track')]";
        assertStyleAttributeContains(sliderTrackXpath, "font-size: 13px", "barStyle attribute was not output to client");
        assertClassAttributeContains(sliderTrackXpath, "bar-class", "barClass attribute was not output to client");

        writeStatus("Check handleClass/handleSelectedClass attributes");
        String handleId = sliderId + "Handle";
        assertClassAttributeContains(handleId, "handle-class", "handleClass attribute was not output to client");
        selenium.mouseDownAt(handleId, "1,1");
        assertClassAttributeContains(handleId, "handle-selected-class", "handleSelectedClass attribute was not output to client");

        writeStatus("Check tipClass/tipStyle attributes");
        String tipId = sliderId + "Tip";
        assertStyleAttributeContains(tipId, "font-size: 18px", "tipStyle attribute was not output to client");
        assertClassAttributeContains(tipId, "tip-class", "tipClass attribute was not output to client");
    }

    @Test
    public void testClickingEdgesInputBoundaryValues(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check clicking edges input boundary values");

        clickSlider(0);
        Assert.assertEquals(getSliderValue(), "0");

        clickSlider(100);
        Assert.assertEquals(getSliderValue(), "100");
    }

    @Test
    public void testEnableManualInputAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, ENABLE_MANUAL_INPUT_TEST);
        writeStatus("Check setting enableManualInput to false makes input read-only");

        String inputId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "Input";
        Assert.assertFalse(selenium.isEditable(inputId), "Slider's input has to be read only");
    }

    @Test
    public void testAdditionalIncreasingDecreasingControls(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Check clicking an arrow changes the driven value on the amount defined with 'step' attribute");

        String slider = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        String arrowDec = slider + "ArrowDec";
        String arrowInc = slider + "ArrowInc";

        selenium.mouseDownAt(arrowDec, "1,1");
        selenium.mouseUpAt(arrowDec, "1,1");
        Assert.assertEquals(getSliderValue(), "39", "Value would have decreased by one");

        selenium.mouseDownAt(arrowInc, "1,1");
        selenium.mouseUpAt(arrowInc, "1,1");
        Assert.assertEquals(getSliderValue(), "40", "Value would have increased by one");
    }

    private void checkSliderVisualState(int value) {
        String id = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        writeStatus("Checking value in input field");
        AssertValueEquals(id + "Input", Integer.toString(value));
        AssertTextEquals(id + "Tip", Integer.toString(value));

        writeStatus("Checking tip and tracker position");
        String actualHandle = runScript("document.getElementById('" + id + "Handle').style.left");
        String actualTip = runScript("document.getElementById('" + id + "Tip').style.left");

        Assert.assertEquals(actualHandle, actualTip);
        Assert.assertTrue(actualTip.endsWith("px"));

        int actual = new Integer(actualTip.replace("px", "")).intValue();
        int expected = BAR_SCALE * value;
        Assert.assertTrue(Math.abs(actual - expected) < BAR_SCALE,
                "Handle position is not syncronized with slider value!");
    }

    private void clickSlider(int position) {
        String track = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "Track";
        String coords = "" + BAR_SCALE * position + ",1";
        selenium.mouseDownAt(track, coords);
        selenium.mouseUpAt(track, coords);
    }

    private String getSliderValue() {
        return selenium.getValue("name=" + getAutoTester(this).getClientId(AutoTester.COMPONENT_ID));
    }

    private void setSliderValue(String value) {
        selenium.type("name=" + getAutoTester(this).getClientId(AutoTester.COMPONENT_ID), value);        
    }

    @Override
    public void changeValue() {
        setSliderValue("50");
    }

    @Override
    public String getTestUrl() {
        return "pages/inputNumberSlider/inputNumberSliderTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/inputNumberSlider/inputNumberSliderAutoTest.xhtml";
    }

}
