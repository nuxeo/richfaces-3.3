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

public class TogglePanelTest extends SeleniumTestBase {

    private static final String RESET_METHOD = "#{panelBean.cleanValues}";
    
    private final static String CHILD_PROCESSING_TEST_URL = "pages/togglePanel/togglePanelChildProcessingTest.xhtml";
    																		   

    @Test
    public void testTogglePanelComponentAjaxMode(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + "mainForm:";

        String inputId = parentId + "value";
        String outputId = parentId + "value2";

        String controlNext = parentId + "ajax_next";
        String controlOne = parentId + "ajax_one";
        String controlTwo = parentId + "ajax_two";

        String oneFacet = parentId + "ajax_state_one";
        String twoFacet = parentId + "ajax_state_two";

        writeStatus("Check ajax mode");

        writeStatus("Click on ajax controlNext");
        clickAjaxCommandAndWait(controlNext);
        AssertValueEquals(inputId, "ajax_next");
        AssertTextEquals(outputId, "1");
        AssertPresentAndVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotPresent(oneFacet, "Both of 'one' and 'two' states were rendered for ajax toggle panel.");

        writeStatus("Click on ajax controlOne");
        clickAjaxCommandAndWait(controlOne);
        AssertValueEquals(inputId, "ajax_one");
        AssertTextEquals(outputId, "2");
        AssertPresentAndVisible(oneFacet, "State 'two' has to give place to state 'one'");
        AssertNotPresent(twoFacet, "Both of 'one' and 'two' states were rendered for ajax toggle panel.");

        writeStatus("Click on ajax controlTwo");
        clickAjaxCommandAndWait(controlTwo);
        AssertValueEquals(inputId, "ajax_two");
        AssertTextEquals(outputId, "1");
        AssertPresentAndVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotPresent(oneFacet, "Both of 'one' and 'two' states were rendered for ajax toggle panel.");
    }

    @Test
    public void testTogglePanelComponentServerMode(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + "mainForm:";

        String inputId = parentId + "value";
        String outputId = parentId + "value2";

        String controlNext = parentId + "server_next";
        String controlOne = parentId + "server_one";
        String controlTwo = parentId + "server_two";

        String oneFacet = parentId + "server_state_one";
        String twoFacet = parentId + "server_state_two";

        writeStatus("Check server mode");
        writeStatus("Click on controlNext");
        clickCommandAndWait(controlNext);
        AssertValueEquals(inputId, "server_next");
        AssertTextEquals(outputId, "1");
        AssertPresentAndVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotPresent(oneFacet, "Both of 'one' and 'two' states were rendered for server toggle panel.");

        writeStatus("Click on server controlOne");
        clickCommandAndWait(controlOne);
        AssertValueEquals(inputId, "server_one");
        AssertTextEquals(outputId, "2");
        AssertPresentAndVisible(oneFacet, "State 'two' has to give place to state 'one'");
        AssertNotPresent(twoFacet, "Both of 'one' and 'two' states were rendered for server toggle panel.");

        writeStatus("Click on server controlTwo");
        clickCommandAndWait(controlTwo);
        AssertValueEquals(inputId, "server_two");
        AssertTextEquals(outputId, "3");
        AssertPresentAndVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotPresent(oneFacet, "Both of 'one' and 'two' states were rendered for server toggle panel.");
    }

    @Test
    public void testTogglePanelComponentClientMode(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId() + "mainForm:";

        String controlNext = parentId + "client_next";
        String controlOne = parentId + "client_one";
        String controlTwo = parentId + "client_two";

        String oneFacet = parentId + "client_state_one";
        String twoFacet = parentId + "client_state_two";

        writeStatus("Check client mode");
        writeStatus("Click on client controlNext");
        clickById(controlNext);
        AssertVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotVisible(oneFacet, "Both of 'one' and 'two' states were rendered for client toggle panel.");

        writeStatus("Click on client controlOne");
        clickById(controlOne);
        AssertVisible(oneFacet, "State 'two' has to give place to state 'one'");
        AssertNotVisible(twoFacet, "Both of 'one' and 'two' states were rendered for client toggle panel.");

        writeStatus("Click on client controlTwo");
        clickById(controlTwo);
        AssertVisible(twoFacet, "State 'one' has to give place to state 'two'");
        AssertNotVisible(oneFacet, "Both of 'one' and 'two' states were rendered for client toggle panel.");
    }
    
    @Test
	public void testRendered(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testRendered();
	}
    
    @Test
    public void testStandardAttributes(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	Map <String, String> styleAttributes = new HashMap<String, String>();
    	styleAttributes.put("width", "200px");
    	styleAttributes.put("background-color", "blue");
    	styleAttributes.put("color", "yellow");
    	autoTester.testStyleAndClasses(new String[]{"className"}, styleAttributes);
    	autoTester.testHTMLEvents();
    }
    
    @Test
    public void testWithExternalValidationFailure(Template template) {
        AutoTester autoTester = getAutoTester(this);
        autoTester.renderPage(template, null);
        writeStatus("Check component in case of external validation failure: listeners are not invoked, model is not updated");
        autoTester.testExtrenalValidationFailure();
    }
    
    @Test
	public void testImmediate(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testImmediate(false);
	}
    
    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, RESET_METHOD);
        writeStatus("Test immediate attribute with external validation failed");
        tester.testImmediateWithExternalValidationFailed(false);
    }
    
    @Test
    public void testChildProcessingAjax(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Ajax}");
        
        writeStatus("Check children processing for ajax mode");
        
        String parentId = getParentId() + "mainForm:";
        String resultId = parentId + "_result";
        String child = parentId + "_child";
        String ajaxButton = parentId + "_ajax_submit";
        String serverButton = parentId + "_server_submit";
        String toggleControl = parentId + "_ajax_switch";
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "1", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickAjaxCommandAndWait(toggleControl);
        AssertTextEquals(resultId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        type(child, "valid");
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "3", "Panel is server-aware and open! Invalid child components must be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "4", "Panel is server-aware and open! Invalid child components must be processed");
    }
  
    @Test
    public void testChildProcessingServer(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Server}");
        
        writeStatus("Check children processing for server mode");
        
        String parentId = getParentId() + "mainForm:";
        String resultId = parentId + "_result";
        String child = parentId + "_child";
        String ajaxButton = parentId + "_ajax_submit";
        String serverButton = parentId + "_server_submit";
        String toggleControl = parentId + "_ajax_switch";
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "1", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickCommandAndWait(toggleControl);
        AssertTextEquals(resultId, "2", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        type(child, "valid");
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "3", "Panel is server-aware and open! Invalid child components must be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "4", "Panel is server-aware and open! Invalid child components must be processed");
	
    }
    
    @Test
    public void testChildProcessingClient(Template template) {
        renderPage(CHILD_PROCESSING_TEST_URL, template, "#{panelBean.reset2Client}");
        
        writeStatus("Check children processing for client mode");
        
        String parentId = getParentId() + "mainForm:";
        String resultId = parentId + "_result";
        String child = parentId + "_child";
        String ajaxButton = parentId + "_ajax_submit";
        String serverButton = parentId + "_server_submit";
        String toggleControl = parentId + "_ajax_switch";
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "0", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "0", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        clickById(toggleControl);
        AssertTextEquals(resultId, "0", "Panel is server-aware and closed! Invalid child components must not be processed");
        
        type(child, "valid");
        
        clickAjaxCommandAndWait(ajaxButton);
        AssertTextEquals(resultId, "1", "Panel is server-aware and open! Invalid child components must be processed");
        
        clickCommandAndWait(serverButton);
        AssertTextEquals(resultId, "2", "Panel is server-aware and open! Invalid child components must be processed");
	
    }
    
    @Override
    public String getTestUrl() {
        return "pages/togglePanel/togglePanelTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
       	return "pages/togglePanel/togglePanelAutoTest.xhtml";
    }
   
}
