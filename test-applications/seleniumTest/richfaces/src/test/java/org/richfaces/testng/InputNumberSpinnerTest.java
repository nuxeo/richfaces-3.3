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

public class InputNumberSpinnerTest extends SeleniumTestBase {

    private final static String RF4140 = "/pages/inputNumberSpinner/rf4140.xhtml";

    private final static String RESET_METHOD = "#{spinnerBean.reset}";

    private final static String FORM_ID = "autoTestForm:";

    private final static String CONTROL_FORM_ID = "control:";

    @Test
    public void testComponentIsPresentOnThePageAndShowsModelValue(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Component is present on the page and shows model value");

        String spinnerId = tester.getClientId(AutoTester.COMPONENT_ID);
        AssertPresentAndVisible(spinnerId, "Spinner is not rendered");
        Assert.assertEquals(getSpinnerValue(), "20");
    }

    @Test
    public void testValueChangeEventFiredAndModelUpdatedOnSubmit(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        String submittedValueId = tester.getClientId("submittedValue");

        writeStatus("Check ValueChangeListeners invoked on submit and model binding is updated on value changed");
        Assert.assertEquals(getSpinnerValue(), "20");
        tester.testSubmit();
        AssertTextEquals(submittedValueId, "30", "Model binding is not updated on value changed");
    }

    @Test
    public void testInvalidValueSubmission(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);

        writeStatus("Check component displays submitted value after submission of invalid value");

        //invalid value cannot be submitted due to smart client validation
        //try a hack
        String invalidValue = "invalid";
        Assert.assertEquals(getSpinnerValue(), "20");
        selenium.type("name=" + getParentId() + FORM_ID + "componentId", invalidValue);
        setValueByName(getParentId() + FORM_ID + "componentId", invalidValue);
        tester.clickSubmit();
        tester.checkUpdateModel(false);
        tester.checkValueChangeListener(false);
        Assert.assertEquals(getSpinnerValue(), invalidValue);
        AssertTextEquals(tester.getClientId("submittedValue"), "20", "Model has not to be updated in case of invalid submission");
    }

    @Test
    public void testRequiredAndRequiredMessageAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);

        writeStatus("Check required & requiredMessage attributes");
        tester.testRequiredAndRequiredMessageAttributes();
    }

    @Test
    public void testCycleMode(Template template) {
        renderPage(template, RESET_METHOD);
        writeStatus("Check cycled attribute");

        Assert.assertEquals(getSpinnerValue(), "20");

        writeStatus("check upper bound and cycling");

        clickUp(); //+10
        clickUp(); //+10
        clickUp(); //+10
        clickUp(); //+10
        clickUp(); //+10
        Assert.assertEquals(getSpinnerValue(), "10");

        writeStatus("check lower bound and cycling");

        clickDown(); //-10
        Assert.assertEquals(getSpinnerValue(), "0");
        clickDown(); //-10
        Assert.assertEquals(getSpinnerValue(), "50");
        clickDown(); //-10
        Assert.assertEquals(getSpinnerValue(), "40");

    }

    @Test
    public void testSpinnerIsInvariantWithRespectToPairOfSequentialOppositeOperations(Template template) {
        renderPage(template, RESET_METHOD);
        writeStatus("check whether the spinner is invariant with respect to pair of sequential opposite operations");

        //start value = 20
        String before = getSpinnerValue();
        clickUp();
        clickDown();
        String after = getSpinnerValue();
        Assert.assertEquals(before, after);
        Assert.assertEquals(after, "20");
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
    public void testEnableManualInputAttribute(Template template) {
        renderPage(template, RESET_METHOD);
        writeStatus("Check enableManualInput = false makes input read-only");
        String spinnerId = getParentId() + FORM_ID + "componentId";

        writeStatus("Check if enableManualInput == true component's value can be edited manually");
        Assert.assertEquals(getSpinnerValue(), "20");
        selenium.type("name=" + spinnerId, "30");
        Assert.assertEquals(getSpinnerValue(), "30");

        writeStatus("Via buttons as before too:");
        clickUp();
        Assert.assertEquals(getSpinnerValue(), "40");

        writeStatus("Otherwise not, only via buttons");
        enableManualInput(false);
        Assert.assertEquals(getSpinnerValue(), "20");
        Assert.assertFalse(selenium.isEditable("name=" + spinnerId), "Spinner has to be read only");
        writeStatus("Via buttons? you are welcome:");
        clickUp();
        Assert.assertEquals(getSpinnerValue(), "30");
    }

    @Test
    public void testDisabledAttribute(Template template) {
        renderPage(template, RESET_METHOD);
        writeStatus("Check component can be disabled");
        String spinnerId = getParentId() + FORM_ID + "componentId";

        writeStatus("Check while component is not disabled its value can be edited at liberty");
        Assert.assertEquals(getSpinnerValue(), "20");
        writeStatus("Via buttons:");
        clickUp();
        Assert.assertEquals(getSpinnerValue(), "30");
        writeStatus("By hand");
        selenium.type("name=" + spinnerId, "40");
        Assert.assertEquals(getSpinnerValue(), "40");

        writeStatus("Ruffle! Disable the spinner and check there is nothing can be edited");
        disable(true);

        Assert.assertEquals(getSpinnerValue(), "20");
        writeStatus("neither via buttons:");
        clickUp();
        Assert.assertEquals(getSpinnerValue(), "20");
        writeStatus("Nor by hand");
        Assert.assertFalse(selenium.isEditable("name=" + spinnerId), "Spinner has to be read only");
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check standard HTML attributes are output to client");

        String spinnerId = getParentId() + FORM_ID + "componentId";
        String spinnerInputXpath ="//*[@name='" + spinnerId + "']";

        writeStatus("Check standart HTML attributes");
        List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
        events.add(SeleniumEvent.ONMOUSEMOVE);
        events.add(SeleniumEvent.ONMOUSEOUT);
        events.add(SeleniumEvent.ONMOUSEOVER);
        events.add(SeleniumEvent.ONMOUSEUP);
        events.add(SeleniumEvent.ONMOUSEDOWN);
        events.add(SeleniumEvent.ONDBLCLICK);
        events.add(SeleniumEvent.ONKEYUP);
        events.add(SeleniumEvent.ONKEYPRESS);

        assertEvents(spinnerInputXpath, events);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        renderPage(template);

        writeStatus("Check styles and classes are output to client");

        String spinnerId = getParentId() + FORM_ID + "componentId";
        assertClassNames(spinnerId, new String[] { "noclass" }, "Class attribute was not output to client", true);
        assertStyleAttribute(spinnerId, "font-size: 10px", "Style attribute was not output to client");

        String spinnerInputXpath ="//*[@name='" + spinnerId + "']";

        writeStatus("Check inputSize attribute");
        Assert.assertEquals(selenium.getAttribute(spinnerInputXpath + "@size"), "20");

        writeStatus("Check inputClass/inputStyle attributes");
        assertClassNames(spinnerInputXpath, new String[] { "input-class" }, "inputClass attribute was not output to client", false);
        //assertStyleAttributeContains(spinnerInputXpath, "color: blue", "inputStyle attribute was not output to client");
    }

    private void clickUp() {
        String id = getParentId() + FORM_ID + "componentIdButtons";
        selenium.fireEvent("xpath=//table[@id='" + id + "']/tbody/tr[1]/td", "mousedown");
        selenium.fireEvent("xpath=//table[@id='" + id + "']/tbody/tr[1]/td", "mouseup");
    }

    private void clickDown() {
        String id = getParentId() + FORM_ID + "componentIdButtons";
        selenium.fireEvent("xpath=//table[@id='" + id + "']/tbody/tr[2]/td", "mousedown");
        selenium.fireEvent("xpath=//table[@id='" + id + "']/tbody/tr[2]/td", "mouseup");
    }

    private String getSpinnerValue() {
        return selenium.getValue("name=" + getParentId() + FORM_ID + "componentId");
    }

    private void disable(boolean disabled) {
        runScript("$('" + getParentId() + CONTROL_FORM_ID + "disabled" + "').checked=" + disabled);
        clickCommandAndWait(getParentId() + CONTROL_FORM_ID + "apply");
    }

    private void enableManualInput(boolean enabled) {
        runScript("$('" + getParentId() + CONTROL_FORM_ID + "enableManualInput" + "').checked=" + enabled);
        clickCommandAndWait(getParentId() + CONTROL_FORM_ID + "apply");
    }

    @Override
    public void changeValue() {
        clickUp();
    }

    @Override
    public void setValueEmpty() {
        selenium.type("name=" + getParentId() + FORM_ID + "componentId", " ");
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/inputNumberSpinner/inputNumberSpinnerAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return "pages/inputNumberSpinner/inputNumberSpinnerTest.xhtml";
    }

}
