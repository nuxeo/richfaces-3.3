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

import static org.ajax4jsf.bean.PickListTestBean.ITEMS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PickListTest extends SeleniumTestBase {

    private static final String PICK_LIST = "pickList";
    private static final String COPY_BTN = "copy";
    private static final String COPY_ALL_BTN = "copyAll";
    private static final String REMOVE_BTN = "remove";
    private static final String REMOVE_ALL_BTN = "removeAll";

    private final static String RESET_METHOD = "#{pickListBean.reset}";

    private final static String INIT_SWITCH_BY_CLICK_TEST = "#{pickListBean.initSwitchByClickTest}";

    private final static String I18N_TEST_URL = "pages/pickList/testI18N.xhtml";

    private final static String LAYOUT_TEST_URL = "pages/pickList/layoutTest.xhtml";

    private final static String FACETS_TEST_URL = "pages/pickList/testFacets.xhtml";

    @Test
    public void testPickListComponent(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + "_form:";
        String pickListId = parentId + PICK_LIST;

        String copyElemId = pickListId + COPY_BTN;
        String copyAllElemId = pickListId + COPY_ALL_BTN;
        String removeElemId = pickListId + REMOVE_BTN;
        String removeAllElemId = pickListId + REMOVE_ALL_BTN;

        writeStatus("Check move controls customized labels");

        AssertTextEquals(copyElemId,      "MOVE");
        AssertTextEquals(copyAllElemId,   "MOVE ALL");
        AssertTextEquals(removeElemId,    "TAKE AWAY");
        AssertTextEquals(removeAllElemId, "TAKE ALL AWAY");

        String destListId = parentId + PICK_LIST + "tlTbody";
        String srcListId = parentId + PICK_LIST + "tbody";

        writeStatus("Check initial disposition");

        Assert.assertEquals(getNumberOfChildren(srcListId),  6);
        Assert.assertEquals(getNumberOfChildren(destListId), 2);

        writeStatus("Verify initial selected items.");

        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[1]"), "ZHURIK");
        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[2]"), "MELESHKO");

        writeStatus("Check buttons state");

        assertButtonEnabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonEnabled(REMOVE_ALL_BTN);

        writeStatus("Try to move the first item and check state thereafter");

        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");
        clickById(copyElemId);

        Assert.assertEquals(getNumberOfChildren(srcListId), 5);
        Assert.assertEquals(getNumberOfChildren(destListId), 3);
        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[3]"), "LEONTIEV");

        writeStatus("Check multiple selection");
        selenium.controlKeyDown();
        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");
        selenium.click("//tbody[@id='" + srcListId + "']/tr[2]");
        selenium.controlKeyUp();

        clickById(copyElemId);

        Assert.assertEquals(getNumberOfChildren(srcListId), 3);
        Assert.assertEquals(getNumberOfChildren(destListId), 5);

        writeStatus("Test 'MOVE ALL' button");

        clickById(copyAllElemId);

        Assert.assertEquals(getNumberOfChildren(srcListId), 0);
        Assert.assertEquals(getNumberOfChildren(destListId), 8);

        assertButtonDisabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonEnabled(REMOVE_ALL_BTN);

        writeStatus("Test 'REMOVE ALL' button");

        clickById(removeAllElemId);

        Assert.assertEquals(getNumberOfChildren(srcListId), 8);
        Assert.assertEquals(getNumberOfChildren(destListId), 0);

        assertButtonEnabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonDisabled(REMOVE_ALL_BTN);
    }

    @Test
    public void testJSAPI(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check JavaScript API. It is not declared, but it is");

        String parentId = getParentId() + "_form:";
        String pickListId = parentId + PICK_LIST;

        String destListId = parentId + PICK_LIST + "tlTbody";
        String srcListId = parentId + PICK_LIST + "tbody";

        Assert.assertEquals(getNumberOfChildren(srcListId),  6);
        Assert.assertEquals(getNumberOfChildren(destListId), 2);

        assertButtonEnabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonEnabled(REMOVE_ALL_BTN);

        writeStatus("Check copy js function");
        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");
        invokeFromComponent(pickListId, "copy", null);
        Assert.assertEquals(getNumberOfChildren(srcListId), 5);
        Assert.assertEquals(getNumberOfChildren(destListId), 3);

        writeStatus("Check remove js function");
        selenium.click("//tbody[@id='" + destListId + "']/tr[1]");
        invokeFromComponent(pickListId, "remove", null);
        Assert.assertEquals(getNumberOfChildren(srcListId), 6);
        Assert.assertEquals(getNumberOfChildren(destListId), 2);

        writeStatus("Check copyAll js function");
        invokeFromComponent(pickListId, "copyAll", null);
        Assert.assertEquals(getNumberOfChildren(srcListId), 0);
        Assert.assertEquals(getNumberOfChildren(destListId), 8);
        assertButtonDisabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonEnabled(REMOVE_ALL_BTN);

        writeStatus("Check removeAll js function");
        invokeFromComponent(pickListId, "removeAll", null);
        Assert.assertEquals(getNumberOfChildren(srcListId), 8);
        Assert.assertEquals(getNumberOfChildren(destListId), 0);
        assertButtonEnabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonDisabled(REMOVE_ALL_BTN);
    }

    @Test
    public void testSwitchByClickAttribute(Template template) {
        renderPage(template, INIT_SWITCH_BY_CLICK_TEST);

        writeStatus("Check 'switchByClick' attribute");

        String parentId = getParentId() + "_form:";
        String destListId = parentId + PICK_LIST + "tlTbody";
        String srcListId = parentId + PICK_LIST + "tbody";

        assertButtonEnabled(COPY_ALL_BTN);
        assertButtonDisabled(COPY_BTN);
        assertButtonDisabled(REMOVE_BTN);
        assertButtonEnabled(REMOVE_ALL_BTN);

        writeStatus("Try to move an item by click and check state thereafter");

        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");

        Assert.assertEquals(getNumberOfChildren(srcListId), 5);
        Assert.assertEquals(getNumberOfChildren(destListId), 3);
        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[3]"), "LEONTIEV");      
    }

    @Test
    public void testKeyboardNavigation(Template template) {
        renderPage(template, RESET_METHOD);

        writeStatus("Check keyboard navigation");

        String pickListId = getParentId() + "_form:" + PICK_LIST;
        String focusKeeper = pickListId + "focusKeeper";
        String destListId = pickListId + "tlTbody";
        String srcListId = pickListId + "tbody";
        String copyElemId = pickListId + COPY_BTN;
        String removeAllElemId = pickListId + REMOVE_ALL_BTN;

        writeStatus("Check Up arrow");
        selenium.click("//tbody[@id='" + srcListId + "']/tr[6]");
        selenium.keyDown(focusKeeper, "\\38");//up
        clickById(copyElemId);
        Assert.assertEquals(getNumberOfChildren(srcListId), 5);
        Assert.assertEquals(getNumberOfChildren(destListId), 3);
        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[3]"), "KOSTITSYN");

        writeStatus("Check Down arrow");
        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");
        selenium.keyDown(focusKeeper, "\\40");//down
        clickById(copyElemId);
        Assert.assertEquals(getNumberOfChildren(srcListId), 4);
        Assert.assertEquals(getNumberOfChildren(destListId), 4);
        Assert.assertEquals(selenium.getText("xpath=//tbody[@id='" + destListId + "']/tr[4]"), "KOVAL");

        writeStatus("Check SHIFT + click");
        selenium.click("//tbody[@id='" + srcListId + "']/tr[1]");
        selenium.shiftKeyDown();
        selenium.click("//tbody[@id='" + srcListId + "']/tr[4]");
        selenium.shiftKeyUp();
        clickById(copyElemId);
        Assert.assertEquals(getNumberOfChildren(srcListId), 0);
        Assert.assertEquals(getNumberOfChildren(destListId), 8);

        clickById(removeAllElemId);

        writeStatus("Check CTRL + A shortcut");

        selenium.controlKeyDown();
        selenium.keyDown(focusKeeper, "A");
        selenium.controlKeyUp();

        clickById(copyElemId);

        Assert.assertEquals(getNumberOfChildren(srcListId), 0);
        Assert.assertEquals(getNumberOfChildren(destListId), 8);
    }

    @Test
    public void testValueChangeEventFiredAndModelUpdatedOnSubmit(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        String resultId = tester.getClientId("result");

        writeStatus("Check ValueChangeListeners invoked on submit and model binding is updated on value changed");

        List<String> expected = Arrays.asList("ZHURIK", "MELESHKO");
        AssertTextEquals(resultId, expected.toString());

        tester.testSubmit();

        AssertTextEquals(resultId, ITEMS.toString(), "Model binding is not updated");
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testConverterAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        tester.testConverterAttribute();
    }

    @Test
    public void testValidatorAndValidatorMessageAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        tester.testValidatorAndValidatorMessageAttributes(false);
    }

    @Test
    public void testRequiredAndRequiredMessageAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);

        writeStatus("Check required & requiredMessage attributes");
        tester.testRequiredAndRequiredMessageAttributes();
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute");
        tester.testSubmitImmediate();
    }

    @Test
    public void testSubmitImmediateWithExternalValidationFailed(Template template ) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test submission of component with immediate = true and external validation failed");
        tester.testSubmitImmediateWithExternalValidationFailed();
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: valueChangeListeners are invoked, model is not updated");
        autoTester.testSubmitWithExternalValidationFailed();
    }

    @Test
    public void testI18N(Template template) {
        renderPage(I18N_TEST_URL, template, RESET_METHOD);

        writeStatus("Check internationalization");

        String pickListId = getParentId() + "_form:" + PICK_LIST;

        String copyElemId = pickListId + COPY_BTN;
        String copyAllElemId = pickListId + COPY_ALL_BTN;
        String removeElemId = pickListId + REMOVE_BTN;
        String removeAllElemId = pickListId + REMOVE_ALL_BTN;

        writeStatus("This test page forces German locale. Check all labels are in German");

        AssertTextEquals(copyElemId, "kopieren");
        AssertTextEquals(copyAllElemId, "kopieren alles");
        AssertTextEquals(removeElemId, "l\u00f6schen");
        AssertTextEquals(removeAllElemId, "l\u00f6schen alles");
    }

    @Test
    public void testStandardHTMLAttributesAreOutputToClient(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(LAYOUT_TEST_URL, template, RESET_METHOD);

        String pickList = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        writeStatus("Check component's specific HTML attributes are output to client");

        List<String> eventsExpected = new ArrayList<String>();
        eventsExpected.add("onlistchange");
        eventsExpected.add("onlistchanged");
        eventsExpected.add("onclick");

        changeValue();
        assertEvents(eventsExpected);

        writeStatus("Check standart HTML attributes");
        assertEvents(pickList, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    @Test
    public void testStylesAndStyleClassesAreOutputToClient(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(LAYOUT_TEST_URL, template, RESET_METHOD);

        writeStatus("Check styles and classes are output to client");

        writeStatus("Check styleClass/style attributes");
        String pickListId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        assertStyleAttributeContains(pickListId, "font-size: 13px", "Style attribute was not output to client");
        assertClassAttributeContains(pickListId, "noclass", "Class attribute was not output to client");

        writeStatus("Check listClass attributes");
        String srcListXpath = "//*[@id='" + pickListId + "']/tbody/tr/td[1]/div";
        assertClassAttributeContains(srcListXpath, "list-class", "listClass attribute was not output to client for source (left) list)");
        String destListXpath = "//*[@id='" + pickListId + "']/tbody/tr/td[3]/div";
        assertClassAttributeContains(destListXpath, "list-class", "listClass attribute was not output to client for destination (right) list)");

        writeStatus("Check controlClass attributes");
        String controlsXpath = "//*[@id='" + pickListId + "']/tbody/tr/td[2]/div";
        assertClassAttributeContains(controlsXpath, "control-class", "controlClass attribute was not output to client");

        // declared disabledStyle, disabledStyleClass, enabledStyle,
        // enabledStyleClass attributes are not output
    }

    @Test
    public void testShowButtonsLabelAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(LAYOUT_TEST_URL, template, RESET_METHOD);

        writeStatus("Check 'showButtonsLabel' attribute");

        String pickListId = tester.getClientId(AutoTester.COMPONENT_ID);

        String copyElemId = pickListId + COPY_BTN;
        String copyAllElemId = pickListId + COPY_ALL_BTN;
        String removeElemId = pickListId + REMOVE_BTN;
        String removeAllElemId = pickListId + REMOVE_ALL_BTN;

        writeStatus("Check labels are gone");

        AssertTextEquals(copyElemId, "");
        AssertTextEquals(copyAllElemId, "");
        AssertTextEquals(removeElemId, "");
        AssertTextEquals(removeAllElemId, "");
    }

    @Test
    public void testFacets(Template template) {
        renderPage(FACETS_TEST_URL, template, RESET_METHOD);

        writeStatus("Check facets");

        assertFacetEnabled("copyAllControl", true);
        assertFacetEnabled("copyControl", false);
        assertFacetEnabled("removeControl", false);
        assertFacetEnabled("removeAllControl", true);

        writeStatus("Copy all");
        clickById("_copyAllControl");

        assertFacetEnabled("copyAllControl", false);
        assertFacetEnabled("copyControl", false);
        assertFacetEnabled("removeControl", false);
        assertFacetEnabled("removeAllControl", true);

        writeStatus("Remove all");
        clickById("_removeAllControl");

        assertFacetEnabled("copyAllControl", true);
        assertFacetEnabled("copyControl", false);
        assertFacetEnabled("removeControl", false);
        assertFacetEnabled("removeAllControl", false);

        // AssertPresentAndVisible("_caption", "Caption facet is not rendered");
    }

    private void assertButtonEnabled(String btnId) {
        String id = getParentId() + "_form:" + PICK_LIST;
        AssertVisible(id + btnId);
        AssertNotVisible(id + "dis" + btnId);
    }

    private void assertButtonDisabled(String btnId) {
        String id = getParentId() + "_form:" + PICK_LIST;
        AssertNotVisible(id + btnId);
        AssertVisible(id + "dis" + btnId);
    }

    private void assertFacetEnabled(String name, boolean enabled) {
        String patternXpath = "//*[@id='" + getParentId() + "_form:componentId']/tbody/tr/td[2]/div//div[@id='%1$s']";

        String eFacet = name;
        String dFacet = name + "Disabled";

        if(enabled) {
            Assert.assertTrue(isVisible(String.format(patternXpath, "_" + eFacet)), eFacet + " facet is not rendered in control panel!");
            Assert.assertFalse(isVisible(String.format(patternXpath, "_" + dFacet)), dFacet + " facet has not to be rendered in control panel at this moment!");
        } else {
            Assert.assertTrue(isVisible(String.format(patternXpath, "_" + dFacet)), dFacet + " facet is not rendered in control panel!");
            Assert.assertFalse(isVisible(String.format(patternXpath, "_" + eFacet)), eFacet + " facet has not to be rendered in control panel at this moment!");
        }
    }

    /**
     * Returns number of children for DOM element with given id.
     * @param elemId DOM element id
     * @return number of children
     */
    private int getNumberOfChildren(String elemId) {
        return selenium.getXpathCount("//*[@id='" + elemId + "']/*").intValue();
    }

    @Override
    public void changeValue() {
        clickById(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + COPY_ALL_BTN);
    }

    @Override
    public void setValueEmpty() {
        clickById(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + REMOVE_ALL_BTN);
    }

    @Override
    public String getTestUrl() {
        return "pages/pickList/pickListTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/pickList/pickListAutoTest.xhtml";
    }

}
