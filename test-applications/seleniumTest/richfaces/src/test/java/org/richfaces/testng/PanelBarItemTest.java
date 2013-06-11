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

public class PanelBarItemTest extends SeleniumTestBase {

    private static final String PANEL_BAR_ITEM_ID = "panelBarItemId";

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

        writeStatus("Check onenter/onleave events are fired ");

        String parentId = getParentId() + "_form:";

        List<String> eventsExpected = new ArrayList<String>();
        assertEvents(eventsExpected);

        writeStatus("Leave panel. Onleave event must be triggered");
        clickById(parentId + "label2");

        eventsExpected.add("onleave");
        assertEvents(eventsExpected);

        writeStatus("Enter panel once more. Onenter event must be triggered");
        clickById(parentId + "label1");

        eventsExpected.add("onenter");
        assertEvents(eventsExpected);
    }

    @Test
    public void testStyleAndClassesAreOutputToClient(Template template) {
        renderPage(template);

        writeStatus("Check style and classes are output to client");

        String compDivId = getParentId() + "_form:" + PANEL_BAR_ITEM_ID;
        String contentElemXpath = "//div[@id='" + compDivId + "']/div[3]/table/tbody/tr/td";
        assertClassAttributeContains(contentElemXpath, "content-class", "contentClass attribute was not output to client");
        assertStyleAttributeContains(contentElemXpath, "font-size: 14px", "contentStyle attribute was not output to client");

        String headerElementXpath = "//div[@id='" + compDivId + "']/div[1]";
        assertClassAttributeContains(headerElementXpath, "header-class", "headerClass attribute was not output to client");
        assertStyleAttributeContains(headerElementXpath, "font-size: 13px", "headerStyle attribute was not output to client");

        String activeHeaderElementXpath = "//div[@id='" + compDivId + "']/div[2]";
        assertClassAttributeContains(activeHeaderElementXpath, "header-class-active", "headerClassActive attribute was not output to client");
        assertStyleAttributeContains(activeHeaderElementXpath, "font-size: 12px", "headerStyleActive attribute was not output to client");
    }

    public String getTestUrl() {
        return "pages/panelBarItem/panelBarItemTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/panelBarItem/panelBarItemAutoTest.xhtml";
    }
}
