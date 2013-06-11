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

public class DataFilterSliderTest extends SeleniumTestBase {

    private static final String DATA_FLT_SLIDER_ID = "componentId";

    private static final String DATA_FLT_SLIDER_INPUT = DATA_FLT_SLIDER_ID + "slider_val";

    private static final String DATA_FLT_SLIDER_TRACK = DATA_FLT_SLIDER_ID + "slider-track";

    private static final String DATA_FLT_SLIDER_HANDLE = DATA_FLT_SLIDER_ID + "slider-handle";

    private static final String DATA_FLT_SLIDER_RANGE = DATA_FLT_SLIDER_ID + "slider-range";

    private static final String DATA_FLT_SLIDER_TRAILER = DATA_FLT_SLIDER_ID + "slider-trailer";

    private static final String TABLE = "planetList";

    private static final int PLANETS_ALL = 11;

    private static final int PLANETS_SMALLER_3 = 10;

    private static final int PLANETS_SMALLER_2 = 9;

    private static final int PLANETS_SMALLER_1 = 6;

    private static final int PLANETS_SMALLER_0 = 0;

    private static final String RESET_METHOD = "#{dataFilterSliderBean.reset}";

    private static final String INIT_INCREMENT_TEST = "#{dataFilterSliderBean.initIncrementTest}";

    private static final String INIT_STORE_RESULTS_TEST = "#{dataFilterSliderBean.initStoreResultsTest}";

    private static final String FORM_ID = "autoTestForm:";

    @Test
    public void testDataFilterSliderComponent(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + FORM_ID;
        String tableId = parentId + TABLE;
        String submittedValueId = parentId + "submittedValue";

        writeStatus("Check component core functionality");

        writeStatus("In the beginning all planets have to be displayed");
        assertColumnsCount(2, tableId);
        assertRowsCount(PLANETS_ALL, tableId);

        writeStatus("Set 1g! Only 6 planets have to meet this condition");
        clickSlider(1);
        assertRowsCount(PLANETS_SMALLER_1, tableId);
        AssertTextEquals(submittedValueId, "1");
        checkDataFilterSliderEventFired(20, 1);
        
        writeStatus("Set 2g! Only 9 planets have to meet this condition");
        clickSlider(2);
        assertRowsCount(PLANETS_SMALLER_2, tableId);
        checkDataFilterSliderEventFired(1, 2);

        writeStatus("Set 5g! Only 10 planets have to meet this condition");
        clickSlider(5);
        assertRowsCount(PLANETS_SMALLER_3, tableId);
        checkDataFilterSliderEventFired(2, 5);
    }

    @Test
    public void testDataFilterSliderComponentManualInput(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + FORM_ID;
        String tableId = parentId + TABLE;
        String inputId = parentId + DATA_FLT_SLIDER_INPUT;

        writeStatus("Check component manual input");

        writeStatus("In the beginning all planets have to be displayed");
        assertColumnsCount(2, tableId);
        assertRowsCount(PLANETS_ALL, tableId);

        writeStatus("Type 1g! Only 6 planets have to meet this condition");
        selenium.type(inputId, "1");
        waitForAjaxCompletion();
        assertRowsCount(PLANETS_SMALLER_1, tableId);

        writeStatus("Type 2g! Only 9 planets have to meet this condition");
        selenium.type(inputId, "2");
        waitForAjaxCompletion();
        assertRowsCount(PLANETS_SMALLER_2, tableId);

        writeStatus("Type 5g! Only 10 planets have to meet this condition");
        selenium.type(inputId, "5");
        waitForAjaxCompletion();
        assertRowsCount(PLANETS_SMALLER_3, tableId);
    }

    @Test
    public void testIncrementAttribute(Template template) {
        renderPage(template, INIT_INCREMENT_TEST);

        String parentId = getParentId() + FORM_ID;
        String tableId = parentId + TABLE;
        String inputId = parentId + DATA_FLT_SLIDER_INPUT;
        String submittedValueId = parentId + "submittedValue";

        writeStatus("Check increment attribute");

        writeStatus("Increment set to 5 therefore only multiple of 5 accepted.");
        writeStatus("Any other will be adjusted by component to the nearest value (7 to 5, 13 to 15 and so on) Check it!");

        selenium.type(inputId, "7");
        waitForAjaxCompletion();
        AssertTextEquals(submittedValueId, "5");
        assertRowsCount(PLANETS_SMALLER_3, tableId);

        selenium.type(inputId, "13");
        waitForAjaxCompletion();
        AssertTextEquals(submittedValueId, "15");
        assertRowsCount(PLANETS_SMALLER_3, tableId);

        clickSlider(2);
        AssertTextEquals(submittedValueId, "0");
        assertRowsCount(PLANETS_SMALLER_0, tableId);

        clickSlider(18);
        AssertTextEquals(submittedValueId, "20");
        assertRowsCount(PLANETS_SMALLER_3, tableId);
    }

    @Test
    public void testStartEndRangeAttributes(Template template) {
        renderPage(template, RESET_METHOD);
        
        String parentId = getParentId() + FORM_ID;
        String tableId = parentId + TABLE;
        String inputId = parentId + DATA_FLT_SLIDER_INPUT;
        String submittedValueId = parentId + "submittedValue";

        writeStatus("Check 'startRange' and 'endRange' attributes.");

        writeStatus("disposition: startRange [0] endRange[40]");
        writeStatus("Any value is out of this range will be adjusted by component: < 0 -> 0 and > 40 -> 40. Check it!");

        selenium.type(inputId, "45");
        waitForAjaxCompletion();
        AssertTextEquals(submittedValueId, "40");
        assertRowsCount(PLANETS_ALL, tableId);

        selenium.type(inputId, "-5");
        waitForAjaxCompletion();
        AssertTextEquals(submittedValueId, "0");
        assertRowsCount(PLANETS_SMALLER_0, tableId);
    }

    @Test
    public void testStoreResultsAttribute(Template template) {
        renderPage(template, INIT_STORE_RESULTS_TEST);

        String parentId = getParentId() + FORM_ID;
        String tableId = parentId + TABLE;
        String submittedValueId = parentId + "submittedValue";

        writeStatus("Check 'storeResults' attributes");

        writeStatus("Check that if storeResults set to false dataFilterSlider has no effect on result dataTable");

        writeStatus("All planets are shown at every turn");

        clickSlider(1);
        assertRowsCount(PLANETS_ALL, tableId);
        AssertTextEquals(submittedValueId, "1");
        checkDataFilterSliderEventFired(20, 1);

        clickSlider(2);
        assertRowsCount(PLANETS_ALL, tableId);
        checkDataFilterSliderEventFired(1, 2);

        clickSlider(5);
        assertRowsCount(PLANETS_ALL, tableId);
        checkDataFilterSliderEventFired(2, 5);
    }

    //https://jira.jboss.org/jira/browse/RF-6273
    @Test(groups=FAILURES_GROUP)
    public void testClientErrorMessageAttribute(Template template) {
        renderPage(template, null);

        writeStatus("Check 'clientErrorMessage' attributes");

        Assert.fail("Bug https://jira.jboss.org/jira/browse/RF-6273");
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
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute in case of external validation failure");
        autoTester.testAjaxSingle();
    }

    //https://jira.jboss.org/jira/browse/RF-6144
    @Test(groups=FAILURES_GROUP)
    public void testAjaxSingleWithExternalAndProcessedComponentsValidationFailures(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute in case of validation failures of both external and processed components");
        autoTester.testAjaxSingleWithProcesExternalValidation(true);
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, RESET_METHOD);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testOncomplete(Template template) {
      AutoTester tester = getAutoTester(this);
      tester.renderPage(template, RESET_METHOD);
      writeStatus("Test oncomplete attribute");
      tester.testOncomplete();
    }

    //https://jira.jboss.org/jira/browse/RF-6144
    @Test(groups=FAILURES_GROUP)
    public void testLimitToListAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
        tester.testLimitToList();
    }

    @Test
    public void testBypassUpdatesAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with bypassUpdates = true skips update model values phase");
        tester.testBypassUpdate();
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(template, null);

        writeStatus("Check component's specific HTML attributes are output to client");

        List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
        events.add(SeleniumEvent.ONKEYDOWN);
        events.add(SeleniumEvent.ONKEYUP);
        events.add(SeleniumEvent.ONKEYPRESS);

        assertEvents(getParentId() + FORM_ID + DATA_FLT_SLIDER_INPUT, events);

        writeStatus("Check standart HTML attributes");
        //String sliderContainerXpath = "//div[contains(@class, 'slider-container')]";
        //assertEvents(sliderContainerXpath, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        renderPage(template, null);

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        String sliderContainerXpath = "//div[contains(@class, 'slider-container')]";
        assertStyleAttributeContains(sliderContainerXpath, "font-size: 16px", "Style attribute was not output to client");
        assertClassAttributeContains(sliderContainerXpath, "noclass", "Class attribute was not output to client");

        writeStatus("Check trackStyleClass attribute");
        String sliderTrackId = getParentId() + FORM_ID + DATA_FLT_SLIDER_TRACK;
        assertClassAttributeContains(sliderTrackId, "track-style-class", "trackStyleClass attribute was not output to client");

        writeStatus("Check handleStyleClass attribute");
        String sliderHandleId = getParentId() + FORM_ID + DATA_FLT_SLIDER_HANDLE;
        assertClassAttributeContains(sliderHandleId, "handle-style-class", "handleStyleClass attribute was not output to client");

        writeStatus("Check rangeStyleClass attribute");
        String sliderRangeId = getParentId() + FORM_ID + DATA_FLT_SLIDER_RANGE;
        assertClassAttributeContains(sliderRangeId, "range-style-class", "rangeStyleClass attribute was not output to client");

        writeStatus("Check trailerStyleClass attribute");
        String sliderTrailerId = getParentId() + FORM_ID + DATA_FLT_SLIDER_TRAILER;
        assertClassAttributeContains(sliderTrailerId, "trailer-style-class", "trailerStyleClass attribute was not output to client");
    }

    private void clickSlider(int position) {
        String trackId = getParentId() + FORM_ID + DATA_FLT_SLIDER_TRACK;
        int w = selenium.getElementWidth(trackId).intValue();
        double step = w / 40.;
        int pos = (int) ((position + 1) * step);
        selenium.mouseDownAt(trackId, pos + ",0");
        selenium.mouseUpAt(trackId, pos + ",0");
        waitForAjaxCompletion();
        delay(500);
    }

    private void checkDataFilterSliderEventFired(int oldValue, int newValue) {
        String eventSnapshotExpected = "DataFilterSliderEvent[" + oldValue + "," + newValue + "]";
        String eventSnapshotActual = selenium.getText(getParentId() + FORM_ID + "eventSnapshot");
        Assert.assertTrue((null != eventSnapshotActual) && eventSnapshotActual.equals(eventSnapshotExpected), eventSnapshotExpected + " is expected!");
    }

    @Override
    public void sendAjax() {
        clickSlider(3);
    }

    @Override
    public String getTestUrl() {
        return "pages/dataFilterSlider/dataFilterSliderTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/dataFilterSlider/dataFilterSliderAutoTest.xhtml";
    }

}
