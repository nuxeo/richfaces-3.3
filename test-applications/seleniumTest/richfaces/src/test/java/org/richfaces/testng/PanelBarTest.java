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
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class PanelBarTest extends SeleniumTestBase {

    private static final String PANEL_BAR_ID = "panelBarId";

    private static final String PANEL_BAR_ITEM_ID = "item1";

    @Test
    public void testPanelBarComponent(Template template) {
        renderPage(template);
        String parentId = getParentId() + "_form:";

        String label2 = parentId + "label2";
        String label3 = parentId + "label3";

        String output1 = parentId + "output1";
        String output2 = parentId + "output2";
        String output3 = parentId + "output3";

        String ajaxButton = parentId + "button_ajax";
        String simpleButton = parentId + "button_simple";

        writeStatus("Click on label two. It should be opened.");
        clickById(label2);
        AssertNotVisible(output1);
        AssertVisible(output2);
        AssertNotVisible(output3);

        writeStatus("Click on simple commandButton. Opened states should not change after page reloaded.");
        clickCommandAndWait(simpleButton);
        AssertNotVisible(output1);
        AssertVisible(output2);
        AssertNotVisible(output3);

        writeStatus("Click on label three. It should be opened.");
        clickById(label3);
        AssertNotVisible(output1);
        AssertNotVisible(output2);
        AssertVisible(output3);

        writeStatus("Click on ajax commandButton. Opened states should not change.");
        clickById(ajaxButton);
        waitForAjaxCompletion();
        AssertNotVisible(output1);
        AssertNotVisible(output2);
        AssertVisible(output3);
    }

    @Test
    public void testSubmit(Template template ) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component's form submission");
        tester.testSubmit();
    }

    @Test
    public void testSubmitWithExternalValidationFailed(Template template ) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component's form submission with an invalid element in it");
        tester.reset();
        tester.clickLoad();

        tester.setExtrenalValidationFailed();
        changeValue();
        tester.clickSubmit();

        tester.checkValueChangeListener(false);
        tester.checkUpdateModel(false);
    }

    @Test
    public void testSubmitImmediate(Template template ) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test submission of component with immediate = true");
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
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testJSEventFiring(Template template) {
        renderPage(template);

        writeStatus("Check onitemchange event is fired ");

        String parentId = getParentId() + "_form:";

        List<String> eventsExpected = new ArrayList<String>();
        assertEvents(eventsExpected);

        writeStatus("Leave panel. Onitemchange event must be triggered");
        clickById(parentId + "label2");

        eventsExpected.add("onitemchange");
        assertEvents(eventsExpected);

        writeStatus("Enter panel once more. Onitemchange event must be triggered again");
        clickById(parentId + "label1");

        eventsExpected.add("onitemchange");
        assertEvents(eventsExpected);
    }

    @Test
    public void testStyleAndClassesAreOutputToClient(Template template) {
        renderPage(template);

        writeStatus("Check style and classes are output to client");

        String itemDivId = getParentId() + "_form:" + PANEL_BAR_ITEM_ID;

        String contentElemXpath = "//div[@id='" + itemDivId + "']/div[3]/table/tbody/tr/td";
        assertClassAttributeContains(contentElemXpath, "content-class", "contentClass attribute was not output to client");
        assertStyleAttributeContains(contentElemXpath, "font-size: 14px", "contentStyle attribute was not output to client");

        String headerElementXpath = "//div[@id='" + itemDivId + "']/div[1]";
        assertClassAttributeContains(headerElementXpath, "header-class", "headerClass attribute was not output to client");
        assertStyleAttributeContains(headerElementXpath, "font-size: 13px", "headerStyle attribute was not output to client");

        String activeHeaderElementXpath = "//div[@id='" + itemDivId + "']/div[2]";
        assertClassAttributeContains(activeHeaderElementXpath, "header-class-active", "headerClassActive attribute was not output to client");
        assertStyleAttributeContains(activeHeaderElementXpath, "font-size: 12px", "headerStyleActive attribute was not output to client");

        String compDivId = getParentId() + "_form:" + PANEL_BAR_ID;

        assertClassAttributeContains(compDivId, "noclass", "Class attribute was not output to client");
        assertStyleAttributeContains(compDivId, "font-size: 11px", "Style attribute was not output to client");

        assertStyleAttributeContains(compDivId, "width: 300px", "width attribute was not output to client");
        assertStyleAttributeContains(compDivId, "height: 200px", "height attribute was not output to client");
    }

    public String getTestUrl() {
        return "pages/panelBar/panelBarTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/panelBar/panelBarAutoTest.xhtml";
    }

    @Override
    public void changeValue() {
        String item2 = getAutoTester(this).getClientId("") + "label2";
        clickById(item2);
    }

}
