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

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class SimpleTogglePanelTest extends SeleniumTestBase {

    private static Map<String, String> params = new HashMap<String, String>();

    private final static String CHILD_PROCESSING_TEST_URL = "pages/simpleTogglePanel/simpleTogglePanelChildProcessingTest.xhtml";

    private final static String RESET_METHOD = "#{panelBean.cleanValues}";

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    @Test
    public void testSimpleTogglePanelComponent(Template template) {
        renderPage(template, RESET_METHOD);
        String parentId = getParentId() + "_form:";
        String inputId = parentId + "_value";
        String outputId = parentId + "_value2";

        String ajaxHeader = parentId + "panel1_header";
        String serverHeader = parentId + "panel2_header";
        String clientHeader = parentId + "panel3_header";

        String ajaxBody = parentId + "panel1_body";
        String serverBody = parentId + "panel2_body";
        String clientBody = parentId + "panel3_body";

        String ajaxPanelContent = parentId + "content1";
        String serverPanelContent = parentId + "content2";
        String clientPanelContent = parentId + "content3";

        writeStatus("Click on client simple toggle panel. It should be opened.");
        clickById(clientHeader);
        AssertVisible(clientBody);
        AssertPresent(clientPanelContent, "Content of client panel should always be present");
        writeStatus("Click on client simple toggle panel again. It should be closed.");
        clickById(clientHeader);
        AssertNotVisible(clientBody);
        AssertPresent(clientPanelContent, "Content of client panel should always be present");

        writeStatus("Click on ajax simple toggle panel. It should be opened.");
        clickById(ajaxHeader);
        waitForAjaxCompletion();
        AssertValueEquals(inputId, "panel1");
        AssertTextEquals(outputId, "1");
        AssertVisible(ajaxBody);
        AssertPresent(ajaxPanelContent, "Content of open ajax panel should be present");

        writeStatus("Click on ajax simple toggle panel again. It should be closed.");
        clickById(ajaxHeader);
        waitForAjaxCompletion();
        AssertValueEquals(inputId, "panel1");
        AssertTextEquals(outputId, "2");
        AssertNotVisible(ajaxBody);
        AssertNotPresent(ajaxPanelContent, "Content of closed ajax panel should not be present at all");

        writeStatus("Click on server simple toggle panel. It should be opened.");
        clickCommandAndWait(serverHeader);
        AssertValueEquals(inputId, "panel2");
        AssertTextEquals(outputId, "3");
        AssertVisible(serverBody);
        AssertPresent(serverPanelContent, "Content of open server panel should be present");

        writeStatus("Click on server simple toggle panel. It should be closed.");
        clickCommandAndWait(serverHeader);
        AssertValueEquals(inputId, "panel2");
        AssertTextEquals(outputId, "4");
        AssertNotVisible(serverBody);
        AssertNotPresent(serverPanelContent, "Content of closed server panel should not be present at all");
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
    }

    @Test
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
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testStyleAndClassesAreOutputToClient(Template template) {
        renderPage(template);

        writeStatus("Check style and classes are output to client");

        String compDivId = getParentId() + "_form:panel1";

        String headerDivId = compDivId + "_header";
        String bodyDivId = compDivId + "_body";

        assertStyleAttribute(compDivId, "font-size: 13px", "Style attribute was not output to client");
        assertClassNames(compDivId, new String[] { "noclass" }, "Class attribute was not output to client", true);

        assertClassNames(headerDivId, new String[] { "header-class" }, "headerClass attribute was not output to client", true);
        assertClassNames(bodyDivId, new String[] { "body-class" }, "bodyClass attribute was not output to client", true);
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test ajaxSingle attribute");
        tester.testAjaxSingle();
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
        tester.testAjaxSingleWithProcesExternalValidation(true);
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
    public void testOncomplete(Template template) {
      AutoTester tester = getAutoTester(this);
      tester.renderPage(template, RESET_METHOD);
      writeStatus("Test oncomplete attribute");
      tester.testOncomplete();
    }

    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }

    @Test
    public void testChildProcessingServerCase(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Server}");
        writeStatus("Check children components processing for server mode");

        String parentId = getParentId() + "_form:";

        String panelId = parentId + "panel_header";
        String luckId = parentId + "_luck";
        String childId = parentId + "_child";
        String ajaxSubmit = parentId + "_ajaxSubmit";
        String fullSubmit = parentId + "_fullSubmit";

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "1", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickCommandAndWait(panelId);
        AssertTextEquals(luckId, "3", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "3", "Panel is server-aware and open! Invalid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "3", "Panel is server-aware and open! Invalid child components must be processed");

        type(childId, "valid");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "4", "Panel is server-aware and open! Valid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "1", "Panel is server-aware and open! Valid child components must be processed");

        clickCommandAndWait(panelId);
        AssertTextEquals(luckId, "2", "Panel is server-aware and open! Valid child components must be processed");
    }

    @Test
    public void testChildProcessingAjaxCase(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Ajax}");
        writeStatus("Check children components processing for ajax mode");

        String parentId = getParentId() + "_form:";

        String panelId = parentId + "panel_header";
        String luckId = parentId + "_luck";
        String childId = parentId + "_child";
        String ajaxSubmit = parentId + "_ajaxSubmit";
        String fullSubmit = parentId + "_fullSubmit";

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "1", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickAjaxCommandAndWait(panelId);
        AssertTextEquals(luckId, "3", "Panel is server-aware and closed! Invalid child components must not be processed");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "3", "Panel is server-aware and open! Invalid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "3", "Panel is server-aware and open! Invalid child components must be processed");

        type(childId, "valid");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "4", "Panel is server-aware and open! Valid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "1", "Panel is server-aware and open! Valid child components must be processed");

        clickAjaxCommandAndWait(panelId);
        AssertTextEquals(luckId, "2", "Panel is server-aware and open! Valid child components must be processed");
    }

    @Test
    public void testChildProcessingClientCase(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Client}");
        writeStatus("Check children components processing for client mode");

        String parentId = getParentId() + "_form:";

        String panelId = parentId + "panel_header";
        String luckId = parentId + "_luck";
        String childId = parentId + "_child";
        String ajaxSubmit = parentId + "_ajaxSubmit";
        String fullSubmit = parentId + "_fullSubmit";

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "0", "Panel is not server-aware! Invalid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "0", "Panel is not server-aware! Invalid child components must be processed");

        clickById(panelId);
        AssertTextEquals(luckId, "0", "Client switch! Nothing is going to happen");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "0", "Panel is not server-aware! Invalid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "0", "Panel is not server-aware! Invalid child components must be processed");

        selenium.type(childId, "valid");

        clickAjaxCommandAndWait(ajaxSubmit);
        AssertTextEquals(luckId, "1", "Panel is not server-aware! Valid child components must be processed");

        clickCommandAndWait(fullSubmit);
        AssertTextEquals(luckId, "2", "Panel is not server-aware! Valid child components must be processed");

        clickById(panelId);
        AssertTextEquals(luckId, "2", "Client switch! Nothing is going to happen");
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait(getAutoTester(this).getClientId(AutoTester.COMPONENT_ID) + "_header");
    }

    @Override
    public void setInternalValidationFailed() {
        String childCompId = getAutoTester(this).getClientId("") + "child";
        setValueById(childCompId, "");
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/simpleTogglePanel/simpleTogglePanelAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return "pages/simpleTogglePanel/simpleTogglePanelTest.xhtml";
    }

}
