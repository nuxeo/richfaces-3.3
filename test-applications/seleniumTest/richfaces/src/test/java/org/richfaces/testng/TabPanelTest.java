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

import org.ajax4jsf.autotest.bean.AutoTestBean;
import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

public class TabPanelTest extends SeleniumTestBase {

    private static final String FORM_ID = "_form:";
    
    private static final String CONTROLS_FORM_ID = "control:";

    private static final String RESET_METHOD = "#{panelBean.reset}";

    private final static String INIT_AJAX_CORE_TEST = "#{panelBean.initAjaxCoreTest}";
    
    private final static String INIT_IMMEDIATE_TEST = "#{panelBean.initImmediateTest}";
    
    private final static String INIT_AJAX_SINGLE_TEST = "#{panelBean.initAjaxSingleTest}";

    private final static String INIT_IMMEDIATE_TEST_URL = "pages/tabPanel/immediateTabPanelTest.xhtml";
    
    private final static String INIT_AJAX_SINGLE_TEST_URL = "pages/tabPanel/tabPanelAjaxSingleTest.xhtml";

    private final static String TAB_FACET_TEST_URL = "pages/tabPanel/tabFacetTest.xhtml";
    
    private final static String DISABLED_TAB_URL = "pages/tabPanel/testDisabledTab.xhtml";

    private final static String INVISIBLE_TAB_TEST_URL = "pages/tabPanel/invisibleTabChildrenProcessingTest.xhtml";

    private final static String IMMEDIATE_TAB_URL = "pages/tabPanel/immediateTabTest.xhtml";
    
    private final static String SERVER_AWARE_MODE_URL = "pages/tabPanel/testTabServerMode.xhtml";
    
    private final static String SERVER_AWARE_MODE_WITH_EXTERAL_VALIDATIO_URL = "pages/tabPanel/testTabServerModeWithExternalValidation.xhtml";

    private static Map<String, String> params = new HashMap<String, String>();
    
    private static final String [] DISABLED_TAB_CLASSES = new String [] { "dr-tbpnl-tb", "rich-tab-header", "dr-tbpnl-tb-dsbl", "rich-tab-disabled"};

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");
    }

    static final Map<String, String> TAB_PANEL_STYLES = new HashMap<String, String>();
    static {
        TAB_PANEL_STYLES.put("font-size", "16px");
    }

    @Test
    public void testRichTabPanelComponent(Template template) {
        renderPage(template, RESET_METHOD);
        String parentId = getParentId() + FORM_ID;
        String linkId = parentId + "tab2_lbl";
        String tabId1 = parentId + "tab1";
        String tabId2 = parentId + "tab2";
        String tabId4 = parentId + "tab4";

        writeStatus("Click on tab2");
        clickById(linkId);
        waitForAjaxCompletion();
        AssertTextEquals(tabId2, "Tab two");
        AssertVisible(tabId2);
        AssertNotVisible(tabId4);

        writeStatus("Click on tab1");
        linkId = parentId + "tab1_lbl";
        clickCommandAndWait(linkId);
        AssertTextEquals(tabId1, "Tab one");
        AssertVisible(tabId1);
        AssertNotVisible(tabId4);

        writeStatus("Click on tab3");
        linkId = parentId + "tab3_lbl";
        clickById(linkId);
        AssertVisible(tabId1);

        writeStatus("Click on tab4");
        linkId = parentId + "tab4_lbl";
        clickById(linkId);
        AssertVisible(tabId4);
        AssertNotVisible(tabId1);
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testReRenderAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component re-renders another components");
        tester.testReRender();
    }

    @Test
    public void testLimitToListAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component with limitToList = true skips ajaxRendered areas update");
        tester.testLimitToList();
    }

    @Test
    public void testOncomplete(Template template) {
      AutoTester tester = getAutoTester(this);
      tester.renderPage(template, INIT_AJAX_CORE_TEST);
      writeStatus("Test oncomplete attribute");
      tester.testOncomplete();
    }

    @Test
    public void testBypassUpdatesAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component with bypassUpdates = true skips update model values phase");
        tester.testBypassUpdate();
    }

    @Test(groups=FAILURES_GROUP)
    public void testValidatorAndValidatorMessageAttributes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Check validator and validatorMessage attributes");

        Assert.fail("https://jira.jboss.org/jira/browse/RF-5779");
        
        tester.setInternalValidation(false);
        tester.clickLoad();

        clickAjaxCommandAndWait(getAutoTester(this).getClientId("") + "tab2_lbl");

        String validatorMessage = tester.getValidatorMessage();
        if ("".equals(validatorMessage)) {
                Assert.fail("Validator attribute does not work. Validation passed, should be failed.");
        }else if (!AutoTestBean.VALIDATOR_MESSAGE.equals(validatorMessage)) {
                Assert.fail("ValidatorMessage attribute does not work. ValidationMessage expected: [" + AutoTestBean.VALIDATOR_MESSAGE+"].But was [" + validatorMessage + "]");
        }

        tester.checkValueChangeListener(false);
    }

    @Test
    public void testSubmissionModesAndListeners(Template template) {
        renderPage(template, RESET_METHOD);

        String parentId = getParentId();

        testListener(parentId);
        testSubmissionModes(parentId);
    }

    @Test
    public void testTabFacetsAreEncodedDecodedCorrectly(Template template) {
        renderPage(TAB_FACET_TEST_URL, template, null);
        writeStatus("Check tab facets are encoded/decoded correctly");

        String parentId = getParentId();

        AssertTextEquals(parentId + "tab1_lbl", "Facet: tab1", "Facet for the 1st tab is not rendered");
        AssertTextEquals(parentId + "tab2_lbl", "Facet: tab2", "Facet for the 2nd tab is not rendered");
        AssertTextEquals(parentId + "tab3_lbl", "Facet: tab3", "Facet for the 3rd tab is not rendered");
        AssertTextEquals(parentId + "tab4_lbl", "Facet: tab4", "Facet for the 4th tab is not rendered");
    }

    @Test
    public void testImmediatePanel(Template template) {
        renderPage(INIT_IMMEDIATE_TEST_URL, template, INIT_IMMEDIATE_TEST);
        String parentId = getParentId();

        String tabId1 = parentId + FORM_ID + "tab1";
        String tabId2 = parentId + FORM_ID + "tab2";
        String tabId4 = parentId + FORM_ID + "tab4";
        String messages = parentId + FORM_ID + "messages";

        // immediate = true and external validation failure

        AssertPresent(tabId1);
        AssertNotPresent(messages);

        AssertNotPresent(tabId2);
        AssertNotPresent(tabId4);

        // switch to second tab
        clickCommandAndWait(tabId2 + "_lbl");

        // second tab should to open
        AssertNotPresent(messages);
        AssertNotPresent(tabId1);
        AssertPresent(tabId2);
        AssertNotPresent(tabId4);

        // immediate = false and external validation failure
        reset(parentId);

        AssertPresent(tabId1);
        AssertNotPresent(messages);

        AssertNotPresent(tabId2);
        AssertNotPresent(tabId4);

        // switch to second tab
        clickCommandAndWait(tabId2 + "_lbl");

        // second tab should not to open - validation message should appear
        AssertPresent(messages);
        AssertPresent(tabId1);
        AssertNotPresent(tabId2);
        AssertNotPresent(tabId4);
    }
    
    @Test(groups=FAILURES_GROUP)
    public void testAjaxSingleTab(Template template) {
        renderPage(INIT_AJAX_SINGLE_TEST_URL, template, INIT_AJAX_SINGLE_TEST);
        String parentId = getParentId();
        
        //FIXME https://jira.jboss.org/jira/browse/RF-5759
        Assert.fail("https://jira.jboss.org/jira/browse/RF-5759");
        String tabId1 = parentId + FORM_ID + "tab1";
        String tabId2 = parentId + FORM_ID + "tab2";
        String tabId4 = parentId + FORM_ID + "tab4";

        setValueById(tabId1 + "_input", "text");
        setValueById(tabId4 + "_input", "text");
        
        // switch to second tab
        clickCommandAndWait(tabId2 + "_lbl");
        AssertNotPresent(tabId1);
        AssertPresent(tabId2);
    }
    
    @Test(groups=FAILURES_GROUP)
    public void testAjaxSingleTabWithEXternalValidationFailure(Template template) {
        renderPage(INIT_AJAX_SINGLE_TEST_URL, template, INIT_AJAX_SINGLE_TEST);
        String parentId = getParentId();

        //FIXME https://jira.jboss.org/jira/browse/RF-5759
        Assert.fail("https://jira.jboss.org/jira/browse/RF-5759");
        String tabId1 = parentId + FORM_ID + "tab1";
        String tabId2 = parentId + FORM_ID + "tab2";

        setValueById(tabId1 + "_input", "text");
        
        // switch to second tab
        clickCommandAndWait(tabId2 + "_lbl");
        AssertNotPresent(tabId1);
        AssertPresent(tabId2);
    }
    
    @Test(groups=FAILURES_GROUP)
    public void testAjaxSingleTabWithEXternalValidationFailureAndProcess(Template template) {
        renderPage(INIT_AJAX_SINGLE_TEST_URL, template, INIT_AJAX_SINGLE_TEST);
        

        //FIXME https://jira.jboss.org/jira/browse/RF-5759
        Assert.fail("https://jira.jboss.org/jira/browse/RF-5759");
    }
    
    @Test
    public void testDisabledTabs(Template template) {
    	renderPage(DISABLED_TAB_URL, template, RESET_METHOD);
    	
    	   	
    	String parentId = getParentId();
    	String selectedTabInputId = parentId + CONTROLS_FORM_ID + "selected";
    	String applyButtonId = parentId + CONTROLS_FORM_ID + "apply";
    	String submitButtonId = parentId + FORM_ID + "submit";
    	String tabId1 = parentId + FORM_ID + "tab1_lbl";
    	String tabId2 = parentId + FORM_ID + "tab2_lbl";
    	String tab1_shifted = parentId + FORM_ID + "tab1_shifted";
    	String tab2_shifted = parentId + FORM_ID + "tab2_shifted";
    	String _inputsId = parentId + FORM_ID + "_inputs";
    	String tabInputId = parentId + FORM_ID + "tab2_input";
    	
   	
    	// Check css classes for disabled tabs 
    	assertClassNames(tabId1, DISABLED_TAB_CLASSES, "Tab1 has not been disabled", true);
    	String onclick = getElementOnclickAttr(tab1_shifted);
    	if (onclick != null) {
    		Assert.fail("Onclick should not be rendered for disabled tab. But was: " + onclick);
    	}
    	
    	assertClassNames(tabId2, DISABLED_TAB_CLASSES, "Tab2 has not been disabled", true);
    	onclick = getElementOnclickAttr(tab2_shifted);
    	if (onclick != null) {
    		Assert.fail("Onclick should not be rendered for disabled tab. But was: " + onclick);
    	}
    	
    	// Check that tab panel does not switch to disabled tab
    	boolean tabSwitched = true;
    	
    	try {
    		selenium.click(tabId2);
    		waitForAjaxCompletion(3000);
    	}catch (SeleniumException e) {
			tabSwitched = false;
		}
    	
    	if (tabSwitched) {
    		Assert.fail("Tab panel was switched to dislabled tab2");
    	}
    	
    	// Check that disabled tabs can be switched on server side
    	AssertValueEquals(selectedTabInputId, "tab1", "First tab should be selected now");
    	AssertTextEquals(parentId + FORM_ID + "tab1", "Tab one", "Content of disabled tab has not been rendered");
    	setValueById(selectedTabInputId, "tab2");
    	clickCommandAndWait(applyButtonId);
    	
    	AssertValueEquals(selectedTabInputId, "tab2", "The second tab should be selected now");
    	AssertTextEquals(parentId + FORM_ID + "tab2", "Tab two", "Content of disabled tab has not been rendered");
      	
    	
    	// Check that children inputs does not update the model in case of disabled tab
    	setValueById(tabInputId, "Some text");
    	clickCommandAndWait(submitButtonId);
    	AssertValueEquals(tabInputId, "", "Text for the inputs should not be applied to model in case of disabled tab");
    	AssertTextEquals(_inputsId, "{}", "Text for the inputs should not be applied to model in case of disabled tab");
    	
    }
    
    @Test
    public void testImmediateTab(Template template) {
    	AutoTester tester = getAutoTester(this);
    	tester.renderPage(IMMEDIATE_TAB_URL, template, RESET_METHOD);
    	
    	tester.testImmediate();
    	AssertVisible(tester.getClientId("tab2", template), "Tab was not swtiched");
    	
    }
    
    @Test
    public void testImmediateTabWithExternalValidationFailure(Template template) {
    	AutoTester tester = getAutoTester(this);
    	tester.renderPage(IMMEDIATE_TAB_URL, template, RESET_METHOD);
    	
    	tester.testImmediateWithExternalValidationFailed();
    	AssertVisible(tester.getClientId("tab2", template), "Tab was not swtiched");
    	
    }
    
    @Test
    public void testServerAwareMode(Template template) {
    	renderPage(SERVER_AWARE_MODE_URL, template, RESET_METHOD);
    	
    	String parentId = getParentId() + FORM_ID;
    	String tab1 = parentId + "tab1";
    	String tab2 = parentId + "tab2";
    	String messageTab1 = tab1 + "_message";
    	String messageTab2 = tab2 + "_message";
    	String inputsId = parentId + "inputs";
    	String submitId = parentId + "submit";
    	
    	clickCommandAndWait(submitId);
    	
    	assertMessageNotEquals(messageTab1, "", "Validation was not processed for rendered tab");
    	assertMessageEquals(messageTab2, "", "Validation was processed for closed tab");
    	
    	setValueById(tab1 + "_input", "Some text");
    	clickCommandAndWait(tab2 + "_lbl");
    	assertMessageEquals(messageTab1, "", "Validation for the first tab should be passed");
    	assertMessageEquals(messageTab2, "", "Validation was processed for closed tab");
    	AssertVisible(tab2, "TabPanel was not swtiched to the second tab");
    	AssertTextEquals(inputsId, "{tab1=Some text}", "Update model for the first tab skipped but should not");
    	
    	
    }
    
    @Test
    public void testServerAwareModeWithExternalValidation(Template template) {
    	renderPage(SERVER_AWARE_MODE_WITH_EXTERAL_VALIDATIO_URL, template, RESET_METHOD);
    	
    	String parentId = getParentId() + FORM_ID;
    	String tab1 = parentId + "tab1";
    	String tab2 = parentId + "tab2";
    	String message = parentId + "message";
    	String input = parentId + "external_input";
    	
    	AssertVisible(tab1, "TabPanel was not swtiched to the second tab");   	   	
    	assertMessageEquals(message, "", "Validation was processed for rendered tab");
    	
    	clickCommandAndWait(tab2 + "_lbl");
    	
    	assertMessageNotEquals(message, "", "Validation was not processed for rendered tab");   	
    	AssertVisible(tab1, "TabPanel was not swtiched to the second tab");
    	
    	setValueById(input, "Some text"); 	
    	clickCommandAndWait(tab2 + "_lbl");
    	
    	assertMessageEquals(message, "", "Validation should be passed");   	
    	AssertVisible(tab2, "TabPanel was not swtiched to the second tab");
    	
    }
    
    @Test
    public void testStylesAndClassesStandardAttributes(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	String panelId = autoTester.getClientId(AutoTester.COMPONENT_ID);
    	String tab1Id = autoTester.getClientId("tab1");
    	String tab2Id = autoTester.getClientId("tab2");
    	
    	autoTester.testStyleAndClasses(new String [] {"rich-tabpanel", "styleClass"}, TAB_PANEL_STYLES);
    	String title = selenium.getAttribute("//*[@id='" + panelId + "']/@title");
    	if (!"panelTitle".equals(title)) {
    		Assert.fail("Title attribute for tabPanel was not output to client");
    	}
    	
    	autoTester.testHTMLEvents();
    	
       	assertClassNames("//*[@id='" + panelId + "']/tbody/tr/td", new String [] { "dr-bottom-line", "rich-tab-bottom-line", "headerClass"}, "Header css classes are incoorect or headerClass attribute was not output to client", false);
    	
    	assertClassNames("//*[@id='" + tab1Id + "_lbl']", new String [] {"dr-tbpnl-tb", "rich-tab-header", "dr-tbpnl-tb-act", "rich-tab-active", "tabClass", "activeTabClass"}, "Active Tab has incorrect css classes.", false);
    	assertClassNames("//*[@id='" + tab2Id + "_lbl']", new String [] {"dr-tbpnl-tb", "rich-tab-header", "dr-tbpnl-tb-inact", "rich-tab-inactive", "tabClass", "inactiveTabClass"}, "Inactive Tab has incorrect css classes.", false);
    	
    	clickAjaxCommandAndWait(tab2Id + "_lbl");
    	
    	String tab2ContentPath = "//*[@id='" + tab2Id + "']/table/tbody/tr/td";
    	assertStyleAttributeContains(tab2ContentPath, "font-weight: bold", "Style attribute was not output for tab");
    	assertClassNames(tab2ContentPath, new String [] {"dr-tbpnl-cntnt", "rich-tabpanel-content", "contentClass", "styleClass"}, "Tab css classes are incorrect", false);
    	assertEvents(tab2ContentPath, SeleniumEvent.STANDARD_HTML_EVENTS);
    	
    	title = selenium.getAttribute(tab2ContentPath + "/@title");
    	if (!"title".equals(title)) {
    		Assert.fail("Title attribute for tab was not output to client");
    	}
    }
    
    @Test
    public void testRenderedAttribute(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testRendered();
    }

    @Test
    public void testChildrenComponentsAreNotProcessedForServerAwareModesAndInvisibleTabs(Template template) {
        renderPage(INVISIBLE_TAB_TEST_URL, template, RESET_METHOD);
        writeStatus("Check children components aren't processed for server-aware modes and invisible tabs");
        String parentId = getParentId();
        String selectedTabInputId = parentId + CONTROLS_FORM_ID + "selected";
        String resetApplyButtonId = parentId + CONTROLS_FORM_ID + "reset_apply";
        String submitButtonId = parentId + FORM_ID + "submit";
        String _inputsId = parentId + FORM_ID + "_inputs";

        writeStatus("Check all invisible server-aware tabs are not processed. The others are processed.");

        clickCommandAndWait(submitButtonId);
        String _inputs = selenium.getText(_inputsId);

        Assert.assertTrue(_inputs.contains("1"), "Visible server-aware tabs (tab1) must be processed");
        Assert.assertFalse(_inputs.contains("2"), "invisible server-aware tabs (tab2) must not be processed");
        Assert.assertTrue(_inputs.contains("3"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");
        Assert.assertTrue(_inputs.contains("4"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");

        setValueById(selectedTabInputId, "tab2");
        clickCommandAndWait(resetApplyButtonId);
        clickCommandAndWait(submitButtonId);
        _inputs = selenium.getText(_inputsId);

        Assert.assertFalse(_inputs.contains("1"), "invisible server-aware tabs (tab1) must not be processed");
        Assert.assertTrue(_inputs.contains("2"), "Visible server-aware tabs (tab2) must be processed");
        Assert.assertTrue(_inputs.contains("3"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");
        Assert.assertTrue(_inputs.contains("4"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");

        setValueById(selectedTabInputId, "tab3");
        clickCommandAndWait(resetApplyButtonId);
        clickCommandAndWait(submitButtonId);
        _inputs = selenium.getText(_inputsId);

        Assert.assertFalse(_inputs.contains("1"), "invisible server-aware tabs (tab1) must not be processed");
        Assert.assertFalse(_inputs.contains("2"), "invisible server-aware tabs (tab2) must not be processed");
        Assert.assertTrue(_inputs.contains("3"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");
        Assert.assertTrue(_inputs.contains("4"), "Non-server-aware tabs (tab3,tab4) have to be processed anyway");
    }

    private void testListener(String parentId) {

        String linkId = parentId + FORM_ID + "tab2_lbl";
        String inputId = parentId + FORM_ID + "_value";

        writeStatus("Click on tab2");
        clickById(linkId);
        waitForAjaxCompletion();
        AssertTextEquals(inputId, "tab2", "Listener for the second tab [ajax mode] has not been called");

        writeStatus("Click on tab1");
        linkId = parentId + FORM_ID + "tab1_lbl";
        clickCommandAndWait(linkId);
        AssertTextEquals(inputId, "tab1", "Listener for the first tab [server mode] has not been called");
    }

    private void testSubmissionModes(String parentId) {
        String tabId1 = parentId + FORM_ID + "tab1";
        String tabId2 = parentId + FORM_ID + "tab2";
        String tabId4 = parentId + FORM_ID + "tab4";

        reset(parentId);

        // Set input for the first tab
        setValueById(tabId1 + "_input", "text1");
        clickAjaxCommandAndWait(tabId2 + "_lbl");
        checkDecodes(parentId, "text1", null, null);

        // Set input for the second and 4th tabs
        setValueById(tabId2 + "_input", "text2");
        setValueById(tabId4 + "_input", "text4");
        clickCommandAndWait(tabId1 + "_lbl");
        checkDecodes(parentId, "text1", "text2", "text4");

        // Reset model. Swtich to tyhe first tab
        reset(parentId);

        // Switch to 4th tab
        clickById(tabId4 + "_lbl");
        setValueById(tabId4 + "_input", "text");
        submit(parentId);
        AssertVisible(tabId4, "Tab4 (client) should be kept as active after form submition");
        checkDecodes(parentId, null, null, "text");

        // Switch to 2nd tab
        clickById(tabId2 + "_lbl");
        waitForAjaxCompletion();
        setValueById(tabId4 + "_input", "text4");
        setValueById(tabId2 + "_input", "text2");
        ajaxSubmit(parentId);
        checkDecodes(parentId, null, "text2", "text4");

    }

    private void checkDecodes(String parentId, String input1, String input2, String input4) {
        String inputsId = parentId + FORM_ID + "_inputs";
        String inputs = getTextById(inputsId);

        if (input1 != null && !inputs.contains("tab1=" + input1)) {
            Assert.fail("Decode or update model for the first tab processed incorrect. The model should contain ['"
                    + input1 + "'] value submitted from the tab");
        }
        if (input2 != null && !inputs.contains("tab2=" + input2)) {
            Assert.fail("Decode or update model for the second tab processed incorrect. The model should contain ['"
                    + input2 + "'] value submitted from the tab");
        }
        if (input4 != null && !inputs.contains("tab4=" + input4)) {
            Assert.fail("Decode or update model for the 4th tab processed incorrect. The model should contain ['"
                    + input4 + "'] value submitted from the tab");
        }
    }

    private void submit(String parentId) {
        String commandId = parentId + FORM_ID + "submit";
        clickCommandAndWait(commandId);
    }

    private void ajaxSubmit(String parentId) {
        String commandId = parentId + FORM_ID + "ajaxSubmit";
        clickAjaxCommandAndWait(commandId);
    }

    private void reset(String parentId) {
        String commandId = parentId + "controls:reset";
        clickCommandAndWait(commandId);
    }
    
    private String getMessage(String id) {
    	if (isPresent(id)) {
    		return getTextById(id);
    	}
    	return "";
    }
    
    private void assertMessageEquals(String id, String value, String message) {
    	String m = getMessage(id);
    	if (!value.equals(m)) {
    		Assert.fail(message);
    	}
    }
    
    private void assertMessageNotEquals(String id, String value, String message) {
    	String m = getMessage(id);
    	if (value.equals(m)) {
    		Assert.fail(message);
    	}
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait(getAutoTester(this).getClientId("") + "tab2_lbl");
    }

    @Override
    public String getAutoTestUrl() {
        return "pages/tabPanel/tabPanelAutoTest.xhtml";
    }

    @Override
    public String getTestUrl() {
        return "pages/tabPanel/tabPanelTest.xhtml";
    }

}
