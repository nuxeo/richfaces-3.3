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
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxStatusTest extends SeleniumTestBase {

    private static final String STATUS_1_ID = "region1:status";

    private static final String STATUS_2_ID = "region2:status";

    private static final String STATUS_3_ID = "region3:status";

    private static final String BTN_1_ID = "button1";

    private static final String BTN_2_ID = "button2";

    @Test
    public void testAjaxStatusComponent(Template template) {
        renderPage(template);

        String parentId = getParentId() + "_form:";
        clickById(parentId + BTN_1_ID);

        writeStatus("Ajax request is in progress");

        String reg1Status = parentId + STATUS_1_ID;
        AssertVisible(reg1Status + ".start");
        AssertNotVisible(reg1Status + ".stop");
        String start = getTextById(reg1Status + ".start");
        Assert.assertTrue(start.startsWith("In progress..."));
        pause(3000, reg1Status);

        writeStatus("Ajax request completed");

        AssertNotVisible(reg1Status + ".start");
        AssertVisible(reg1Status + ".stop");
        String stop = getTextById(reg1Status + ".stop");
        Assert.assertTrue(stop.startsWith("Complete"));

        clickById(parentId + BTN_2_ID);

        writeStatus("Ajax request is in progress");

        String reg2Status = parentId + STATUS_2_ID;
        AssertVisible(reg2Status + ".start");
        AssertNotVisible(reg2Status + ".stop");
        start = getTextById(reg2Status + ".start");
        Assert.assertTrue(start.startsWith("In progress..."));
        pause(3000, reg2Status);

        writeStatus("Ajax request completed");

        AssertNotVisible(reg2Status + ".start");
        AssertVisible(reg2Status + ".stop");
        stop = getTextById(reg1Status + ".stop");
        Assert.assertTrue(stop.startsWith("Complete"));

    }

    @Test
    public void testStatusIndicatedOnlyForAjaxContainerPointedByForAttribute(Template template) {
        renderPage(template);

        writeStatus("Check status is indicated only for ajax container pointed by 'for' attribute");

        String parentId = getParentId() + "_form:";
        String reg1Status = parentId + STATUS_1_ID;
        String reg2Status = parentId + STATUS_2_ID;
        String stop = "";

        clickById(parentId + BTN_1_ID);

        AssertNotVisible(reg2Status + ".start");
        AssertVisible(reg2Status + ".stop");
        stop = getTextById(reg2Status + ".stop");
        Assert.assertTrue(stop.startsWith("Complete"),
                "Status components have to be untouched for irrelevant ajax containers");
        pause(3000, reg1Status);

        clickById(parentId + BTN_2_ID);

        AssertNotVisible(reg1Status + ".start");
        AssertVisible(reg1Status + ".stop");
        stop = getTextById(reg1Status + ".stop");
        Assert.assertTrue(stop.startsWith("Complete"),
                "Status components have to be untouched for irrelevant ajax containers");
        pause(3000, reg2Status);

    }

    @Test
    public void testRenderedComponentAttribute(Template template) {
        renderPage(template);

        writeStatus("Check the component with rendered = false is not present on the page");
        AssertNotPresent(getParentId() + "_form:" + STATUS_3_ID);
    }

    @Test
    public void testProperStylesAreAppliedToStartAndStopStates(Template template) {
        renderPage(template);

        writeStatus("Check proper style and style classes are applied to 'start' and 'stop' states");

        String statusElemId = getParentId() + "_form:" + STATUS_1_ID;
        String startFacetId = statusElemId + ".start";
        String stopFacetId = statusElemId + ".stop";

        assertStyleAttribute(startFacetId, "color: red", "Style attribute was not output to client for start facet");
        assertClassNames(startFacetId, new String[] {"start-class"}, "Class attribute was not output to client for start facet", true);

        assertStyleAttribute(stopFacetId, "color: green", "Style attribute was not output to client for stop facet");
        assertClassNames(stopFacetId, new String[] {"stop-class"}, "Class attribute was not output to client for stop facet", true);
    }

    @Test
    public void testOnstartOnstopEventsFired(Template template) {
        renderPage(template);

        writeStatus("Check onstart/onstop events are fired ");

        List<String> eventsExpected = new ArrayList<String>();
        assertEvents(eventsExpected);

        clickById(getParentId() + "_form:" + BTN_1_ID);

        eventsExpected.add("onstart");
        assertEvents(eventsExpected);

        delay(3000);

        eventsExpected.add("onstop");
        assertEvents(eventsExpected);
    }

    //FIXME https://jira.jboss.org/jira/browse/RF-4597
    @Test(groups=FAILURES_GROUP)
    public void testStyleAndClassesStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(template);

        writeStatus("Check style and classes, standard HTML attributes are output to client");

        String statusElemId = getParentId() + "_form:" + STATUS_1_ID;

        assertStyleAttribute(statusElemId, "font-size: 13px", "Style attribute was not output to client");
        assertClassNames(statusElemId, new String[] { "noclass" }, "Class attribute was not output to client", true);
    }

    public String getTestUrl() {
        return "pages/ajaxStatus/ajaxStatusTest.xhtml";
    }
}
