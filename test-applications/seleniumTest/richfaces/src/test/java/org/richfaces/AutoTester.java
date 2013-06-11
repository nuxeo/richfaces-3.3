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
package org.richfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.autotest.bean.AutoTestBean;
import org.ajax4jsf.template.Template;
import org.testng.Assert;

/**
 * Class provides scope of mehods for automatic test of standard ajax attrs
 * 
 * @author Andrey Markavtsov
 * 
 */
public class AutoTester {

    private static final String AUTOTEST_CONTROLS_FORM_ID = "autoTestControlForm:";

    private static final String AUTOTEST_FORM_ID = "autoTestForm:";

    private static final String INPUT_ID = "_auto_input";
    
    private static final String PROCESS_INPUT_ID = "_auto_process_input";

    public static final String STATUS_ID = "_auto_status";
    
    public static final String VALUE_ID = "_auto_value";

    private static final String TIME_ID = "_auto_time";

    private static final String REQ_PARAMS_ID = "_auto_request_params";

    private static final String MESSAGE_FOR_ID = "_auto_messageId";

    public static final String COMPONENT_ID = "componentId";

    private static final String PARAMS_MAP_VAR_NAME = "requestParamsMap";

    private static final String SIMPLE_SUBMIT = "_auto_simple_submit";

    private static final String AJAX_SUBMIT = "_auto_ajax_submit";

    private static final String AJAX_RESET = "_auto_ajax_reset";

    private SeleniumTestBase base;

    public AutoTester(SeleniumTestBase base) {
        this.base = base;
    }
    
    public void renderPage(Template template, String resetMethodName) {
        base.renderAutoTestPage(template, resetMethodName);
        reset();
        clickLoad();
    }
    
    public void renderPage(String url, Template template, String resetMethodName) {
        base.renderAutoTestPage(url, template, resetMethodName);
    }

    public void testAllAjaxAttributes() {
        testRendered();
        testReRender();
        testActionListener();
        testAjaxSingle();
        testImmediate();
        testBypassUpdate();
        testExtrenalValidationFailure();
        testLimitToList();
    }

    public void testRendered() {
        reset();
        setupControl(TestSetupEntry.rendered, Boolean.FALSE);
        clickLoad();

        base.AssertNotPresent(getClientId(COMPONENT_ID), "Rendered attribute does not work");
    }

    public void testReRender() {
        reset();
        setupControl(TestSetupEntry.reRender, STATUS_ID + "," + TIME_ID);
        clickLoad();

        String text = base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + TIME_ID);

        base.sendAjax();
        base.AssertTextNotEquals(base.getParentId() + AUTOTEST_FORM_ID + TIME_ID, text,
                "ReRender attribute does not work");

        String oncomplete = base.runScript("window._ajaxOncomplete");
        Assert.assertEquals(oncomplete,"true", "Oncomplete attribute does not work.");
    }

    public void testActionListener() {
        reset();
        clickLoad();

        base.sendAjax();

        checkActionListener(true);
        checkUpdateModel(true);
    }
    
    public void testActionAndNavigation() {
        reset();
        clickLoad();

        base.sendAjax();
        
        checkAction(true);
        checkNavigation(true);
    }

    public void testExtrenalValidationFailure() {
        reset();
        clickLoad();

        setExtrenalValidationFailed();
        base.sendAjax();

        checkActionListener(false);
        checkUpdateModel(false);
    }
    
    public void testAjaxSingleWithProcesExternalValidation(boolean checkListener) {
    	reset();
    	setupControl(TestSetupEntry.ajaxSingle, Boolean.TRUE);
    	setupControl(TestSetupEntry.processExternalValidation, Boolean.TRUE);
    	clickLoad();
    	
    	setExtrenalValidationFailed();
    	base.sendAjax();
    	
    	if (checkListener) {
    	    checkActionListener(false);
        }
        checkUpdateModel(false);
    	
    }
    
    public void testAjaxSingle() {
        testAjaxSingle(true);
    }
    
    public void testAjaxSingle(boolean checkListener) {
        reset();
        setupControl(TestSetupEntry.ajaxSingle, Boolean.TRUE);
        clickLoad();

        setExtrenalValidationFailed();
        base.sendAjax();

        if (checkListener) {
            checkActionListener(true);
        }
        
        checkUpdateModel(false);
    }


    /**
     * Important! Override
     * {@link SeleniumTestBase#setInternalValidationFailed()} method if you use
     * this method
     */
    public void testAjaxSingleWithInternalValidationFailed() {
        reset();
        setupControl(TestSetupEntry.ajaxSingle, Boolean.TRUE);
        setInternalValidation(true);
        clickLoad();

       // setInternalValidation(true);
        base.setInternalValidationFailed();
        base.sendAjax();

        checkActionListener(false);
        checkUpdateModel(false);
    }

    public void testImmediate() {
    	testImmediate(true);
    }
    
    public void testImmediate(boolean checkListener) {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        base.sendAjax();
        
        if (checkListener) {
        	checkActionListener(true);
        }
        checkUpdateModel(false);

    }

    public void testImmediateWithExternalValidationFailed() {
    	testImmediateWithExternalValidationFailed(true);
    }
    
    public void testImmediateWithExternalValidationFailed(boolean checkListener) {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        setExtrenalValidationFailed();
        base.sendAjax();

        if (checkListener) {
        	checkActionListener(true);
        }
        checkUpdateModel(false);

    }

    public void testBypassUpdate() {
        testBypassUpdate(true);
    }

    public void testBypassUpdate(boolean checkListener) {
        reset();
        setupControl(TestSetupEntry.bypassUpdate, Boolean.TRUE);
        clickLoad();

        base.sendAjax();

        if (checkListener) {
            checkActionListener(true);
        }
        checkUpdateModel(false);
    }

    public void testLimitToList() {
        reset();
        setupControl(TestSetupEntry.limitToList, Boolean.TRUE);
        clickLoad();

        checkComponentReRendered();

        reset();
        setupControl(TestSetupEntry.limitToList, Boolean.TRUE);
        setupControl(TestSetupEntry.reRender, TIME_ID);
        clickLoad();

        String text = base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + TIME_ID);
        String status = getStatus();

        checkComponentReRendered();

        base.AssertTextNotEquals(base.getParentId() + AUTOTEST_FORM_ID + TIME_ID, text, ""
                + "LimitToList = true does not work. Component in reRender list was not rerendered");
        base.AssertTextEquals(base.getParentId() + AUTOTEST_FORM_ID + STATUS_ID, status, "Component inside <a4j:outputPanel ajaxRendered='true'> was rendered but should not in case of limitToList=true.");
        

    }


    public void testRequestParameters(Map<String, String> params) {
        reset();
        clickLoad();

        base.sendAjax();

        for (String name : params.keySet()) {
            String value = base.runScript(PARAMS_MAP_VAR_NAME + "." + name);
            if (value == null) {
                Assert.fail("Parameter [" + name + "] is not present in ajax request");
            } else if (!value.equals(params.get(name))) {
                Assert.fail("Parameter [" + name + "] value is invalid. Expected [" + params.get(name) + "]. But was ["
                        + value + "]");
            }
        }
    }

    public void testNestedActionListener() {
        reset();
        clickLoad();

        base.sendAjax();

        checkNestedActionListener(true);
    }

    public void testOncomplete() {
        reset();
        clickLoad();

        base.sendAjax();

        String oncomplete = base.runScript("window._ajaxOncomplete");
        Assert.assertEquals("true", oncomplete, "Oncomplete attribute does not work.");

    }

    public void testStyleAndClasses(String[] classNames, Map<String, String> styleAttr) {
        reset();
        clickLoad();

        String componentId = getClientId(COMPONENT_ID);

        base.assertClassNames(componentId, classNames, "Component's rendering invalid", true);
        base.assertStyleAttributes(componentId, styleAttr);
    }

//
// ActionSource test methods
//

    public void testAS() {
        reset();
        clickLoad();

        base.sendAction();

        checkActionListener(true);
        checkNestedActionListener(true);
        checkNavigation(true);
        checkUpdateModel(true);
    }

    public void testASImmediate() {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        base.sendAction();

        checkActionListener(true);
        checkNestedActionListener(true);
        checkNavigation(true);
        checkUpdateModel(false);
    }

    public void testASImmediateWithExternalValidationFailed() {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        setExtrenalValidationFailed();
        base.sendAction();

        checkActionListener(true);
        checkNestedActionListener(true);
        checkNavigation(true);
        checkUpdateModel(false);
    }

    public void testASExtrenalValidationFailure() {
        reset();
        clickLoad();

        setExtrenalValidationFailed();
        base.sendAction();

        checkActionListener(false);
        checkNestedActionListener(false);
        checkNavigation(false);
        checkUpdateModel(false);
    }

    public void testASRequestParameters(Map<String, String> params) {
        reset();
        clickLoad();

        base.sendAction();

        String paramString = base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + REQ_PARAMS_ID);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!paramString.contains(entry.toString())) {
                Assert.fail("Request parameter [" + entry.toString() + "] is expected");
            }
        }
    }

//
// EditableValueHolder test methods
//

    public void testSubmit() {
        reset();
        clickLoad();

        changeValue();
        clickSubmit();

        checkValueChangeListener(true);
        checkUpdateModel(true);
    }

    public void testSubmitWithExternalValidationFailed() {
        reset();
        clickLoad();

        setExtrenalValidationFailed();
        changeValue();
        clickSubmit();

        checkValueChangeListener(true);
        checkUpdateModel(false);
    }

    public void testSubmitImmediate() {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        changeValue();
        clickSubmit();

        checkValueChangeListener(true);
        checkUpdateModel(false);

    }

    public void testSubmitImmediateWithExternalValidationFailed() {
        reset();
        setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        clickLoad();

        setExtrenalValidationFailed();
        changeValue();
        clickSubmit();

        checkValueChangeListener(true);
        checkUpdateModel(false);
    }

    public void testHTMLEvents() {
        reset();
        clickLoad();
        String componentId = getClientId(COMPONENT_ID);
        base.assertEvents(componentId, SeleniumEvent.STANDARD_HTML_EVENTS);
    }

    private void checkComponentReRendered() {
        if (base.getReRendersId() == null) {
        	base.sendAjax();
            return;
        }

        List<String> htmlBefore = new ArrayList<String>();
        List<String> htmlAfter = new ArrayList<String>();

        for (String id : base.getReRendersId()) {
            htmlBefore.add(base.getHTMLById(getClientId(id)));
        }

        base.sendAjax();

        for (String id : base.getReRendersId()) {
            htmlAfter.add(base.getHTMLById(getClientId(id)));
        }

        int i = 0;
        for (String html : htmlBefore) {
            Assert.assertEquals(html, htmlAfter.get(i),
                    "LimitToList does not work. Component should not be reRendered if limitToList=true");

            i++;
        }
    }

    public String getClientId(String id) {
        return (base.getParentId() != null ? base.getParentId() : "") + AUTOTEST_FORM_ID + id;
    }

    public String getClientId(String id, Template template) {
        return template.getPrefix() + AUTOTEST_FORM_ID + id;
    }

    public void reset() {
        if (base.getParentId() == null) {
            Assert.fail("Page has not been rendered before test. Invoke 'renderPage' method before !");
        }

        for (TestSetupEntry attr : TestSetupEntry.list) {
            setupControl(attr, attr.defaultValue);
        }
    }

    public void startTracing() {
        String resetId = base.getParentId() + AUTOTEST_CONTROLS_FORM_ID + AJAX_RESET;
        base.clickAjaxCommandAndWait(resetId);
    }

    public void clickLoad() {
        String commandButtonId = base.getParentId() + AUTOTEST_CONTROLS_FORM_ID + "_auto_load";
        base.clickCommandAndWait(commandButtonId);
    }

    public void clickAjaxSubmit() {
        String ajaxSubmitId = base.getParentId() + AUTOTEST_FORM_ID + AJAX_SUBMIT;
        base.clickAjaxCommandAndWait(ajaxSubmitId);
    }

    public void clickSubmit() {
        String submitId = base.getParentId() + AUTOTEST_FORM_ID + SIMPLE_SUBMIT;
        base.clickCommandAndWait(submitId);
    }

    public void setExtrenalValidationFailed() {
        base.setValueById(base.getParentId() + AUTOTEST_FORM_ID + INPUT_ID, "");
    }

//    private void setInternalValidationFailed() {
//        base.setInternalValidationFailed();
//    }
    
    private void setProcessInputValue() {
        base.setValueById(base.getParentId() + AUTOTEST_FORM_ID + PROCESS_INPUT_ID, String.valueOf(new Date().getTime()));
    }

    private void changeValue() {
        base.changeValue();
    }

    private void setValueEmpty() {
        base.setValueEmpty();
    }

    public void setInternalValidation(boolean passed) {
    	setupControl(TestSetupEntry.validatorId, (passed) ? AutoTestBean.VALIDATOR_DEFAULT_ID : AutoTestBean.VALIDATOR_ID);
    }

    public String getValidatorMessage() {
    	String id = base.getParentId() + AUTOTEST_FORM_ID + MESSAGE_FOR_ID;
    	if (base.isPresent(id)) {
    		return base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + MESSAGE_FOR_ID);
    	}
    	return "";
    }

    public void setupControl(TestSetupEntry attr, Object o) {
        final String idPrefix = "_auto_";
        String controlId = base.getParentId() + AUTOTEST_CONTROLS_FORM_ID + idPrefix + attr.name;
        if (attr.type.equals(String.class)) {
            base.runScript("document.getElementById('" + controlId + "').value = '" + o.toString() + "'");
        } else if (attr.type.equals(Boolean.class)) {
            base.runScript("document.getElementById('" + controlId + "').checked = " + o.toString());
        }
    }
    
    public void testProcessAttribute() {
    	reset();
    	setupControl(TestSetupEntry.process, true);
    	setupControl(TestSetupEntry.ajaxSingle, true);
    	clickLoad();
    	
    	setProcessInputValue();
    	base.sendAjax();
    	
    	try{
    		checkProcessInputChangeListener(true);
    		checkUpdateModelForProcessInput(true);
    	}catch (AssertionError error) {
			Assert.fail("Process attribute does not work. " + error);
		}
    	
    }
    
    public void testConverterAttribute() {
    	setupControl(TestSetupEntry.converter, true);
    	clickLoad();
    	
    	changeValue();
    	
    	clickSubmit();
    	String value = getComponentValue();
    	if (!AutoTestBean.AutoTestConverter.AS_OBJECT_STRING.equals(value)) {
    		Assert.fail("Converter attribute does not work: getAsObject method failed of converter was not triggered. Expected component value: ["+AutoTestBean.AutoTestConverter.AS_OBJECT_STRING+"]. But was: ["+value+"]");
    	}
    }

    public void testValidatorAndValidatorMessageAttributes(boolean checkListener) {
        reset();
        setInternalValidation(true);
        clickLoad();

        changeValue();
        clickSubmit();

        String validatorMessage = getValidatorMessage();
        if (!"".equals(validatorMessage)) {
            Assert.fail("Validator attribute does not work. Validation failed, but should not.");
        }

        if (checkListener) {
            checkValueChangeListener(true);
        }

        setInternalValidation(false);
        clickLoad();

        changeValue();
        clickSubmit();

        validatorMessage = getValidatorMessage();
        if ("".equals(validatorMessage)) {
            Assert.fail("Validator attribute does not work. Validation passed, should be failed.");
        } else if (!AutoTestBean.VALIDATOR_MESSAGE.equals(validatorMessage)) {
            Assert.fail("ValidatorMessage attribute does not work. ValidationMessage expected: ["
                    + AutoTestBean.VALIDATOR_MESSAGE + "].But was [" + validatorMessage + "]");
        }

        if (checkListener) {
            checkValueChangeListener(false);
        }
    }

    public void testRequiredAndRequiredMessageAttributes() {
        reset();
        setupControl(TestSetupEntry.required, true);
        clickLoad();
        setValueEmpty();
        clickSubmit();

        String errorMessage = getValidatorMessage();

        if (null == errorMessage || !errorMessage.contains(AutoTestBean.REQUIRED_MESSAGE)) {
            Assert.fail("requiredMessage attribute does not work. RequiredMessage expected: ["
                    + AutoTestBean.REQUIRED_MESSAGE + "].But was [" + errorMessage + "]");
        }

        try {
            checkUpdateModel(false);
            checkValueChangeListener(false);
        } catch (AssertionError ae) {
            Assert.fail("required attribute does not work. Cause: " + ae.getMessage());
        }
    }

    public void checkActionListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.ACTION_LISTENER_STATUS) == -1) {
            Assert.fail("ActionListener has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.ACTION_LISTENER_STATUS) != -1) {
            Assert.fail(status);
        }
    }
    
    public void checkAction(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.ACTION_STATUS) == -1) {
            Assert.fail("Action has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.ACTION_STATUS) != -1) {
            Assert.fail(status);
        }
    }
    
    public void checkNavigation(boolean passed) {
        boolean isNavigated = base.isPresent("_auto_test_navigation")
                && "Navigation successfully".equals(base.getTextById("_auto_test_navigation"));
        if (passed && !isNavigated) {
            Assert.fail("Navigation has not been occurred");
        } else if (!passed && isNavigated) {
            Assert.fail("Navigation would not have been occurred");
        }
    }

    public void checkProcessInputChangeListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.PROCESS_INPUT_CHANGE_LISTENER) == -1) {
            Assert.fail("ValueChangeListener for component specified in process attribute has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.PROCESS_INPUT_CHANGE_LISTENER) != -1) {
            Assert.fail(status);
        }
    }

    public void checkNestedActionListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.NESTED_ACTION_LISTENER_STATUS) == -1) {
            Assert.fail("Nested actionListener has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.NESTED_ACTION_LISTENER_STATUS) != -1) {
            Assert.fail("Nested actionListener should be skipped");
        }
    }

    public void checkValueChangeListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.VALUE_CHANGE_LISTENER_STATUS) == -1) {
            Assert.fail("ValueChangeListener has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.VALUE_CHANGE_LISTENER_STATUS) != -1) {
            Assert.fail(status);
        }
    }

    public void checkNodeSelectedListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.NODE_SELECTED_LISTENER_STATUS) == -1) {
            Assert.fail("NodeSelectedListener has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.NODE_SELECTED_LISTENER_STATUS) != -1) {
            Assert.fail(status);
        }
    }

    public void checkNodeExpandedListener(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.NODE_EXPANDED_LISTENER_STATUS) == -1) {
            Assert.fail("NodeExpandedListener has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.NODE_EXPANDED_LISTENER_STATUS) != -1) {
            Assert.fail(status);
        }
    }

    public void checkUpdateModel(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.UPDATE_MODEL_STATUS) == -1) {
            Assert.fail("Update Model phase has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.UPDATE_MODEL_STATUS) != -1) {
            Assert.fail("Update Model phase should be skipped");
        }
    }
    
    public void checkUpdateModelForProcessInput(boolean passed) {
        String status = getStatus();
        if (passed && status != null && status.indexOf(AutoTestBean.PROCESS_INPUT_UPDATE_MODEL) == -1) {
            Assert.fail("Update Model phase for component defined in process attribute has been skipped");
        } else if (!passed && status != null && status.indexOf(AutoTestBean.PROCESS_INPUT_UPDATE_MODEL) != -1) {
            Assert.fail("Update Model phase for component defined in process attribute should be skipped");
        }
    }

    public boolean checkMessage(String msg, boolean contain) {
        String status = getStatus();
        if (contain && status != null && status.indexOf(msg) == -1) {
            return false;
        } else if (!contain && status != null && status.indexOf(msg) != -1) {
            return false;
        }
        return true;
    }

    public void disableComponent(boolean disabled) {
        reset();
        if (disabled) {
            setupControl(TestSetupEntry.disabled, Boolean.TRUE);
        }
        clickLoad();
    }

    private String getStatus() {
        return base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + STATUS_ID);
    }
    
    private String getComponentValue() {
        return base.getTextById(base.getParentId() + AUTOTEST_FORM_ID + VALUE_ID);
    }

    public static class TestSetupEntry {

        String name;

        Class<?> type;

        Object defaultValue;

        public TestSetupEntry(String name, Class<?> type, Object defaultValue) {
            super();
            this.name = name;
            this.type = type;
            this.defaultValue = defaultValue;
        }

        public static final TestSetupEntry reRender = new TestSetupEntry("reRender", String.class, AutoTester.STATUS_ID);

        public static final TestSetupEntry validatorId = new TestSetupEntry("validatorId", String.class,
                AutoTestBean.VALIDATOR_DEFAULT_ID);

        public static final TestSetupEntry oncomplete = new TestSetupEntry("oncomplete", String.class,
                AutoTestBean.ONCOMPLETE);

        public static final TestSetupEntry rendered = new TestSetupEntry("rendered", Boolean.class, Boolean.TRUE);

        public static final TestSetupEntry ajaxSingle = new TestSetupEntry("ajaxSingle", Boolean.class, Boolean.FALSE);

        public static final TestSetupEntry immediate = new TestSetupEntry("immediate", Boolean.class, Boolean.FALSE);

        public static final TestSetupEntry bypassUpdate = new TestSetupEntry("bypassUpdate", Boolean.class, Boolean.FALSE);

        public static final TestSetupEntry limitToList = new TestSetupEntry("limitToList", Boolean.class, Boolean.FALSE);
        
        public static final TestSetupEntry converter = new TestSetupEntry("converter", Boolean.class, Boolean.FALSE);
        
        public static final TestSetupEntry process = new TestSetupEntry("process", Boolean.class, Boolean.FALSE);
        
        public static final TestSetupEntry processExternalValidation = new TestSetupEntry("processExtValid", Boolean.class, Boolean.FALSE);

        public static final TestSetupEntry required = new TestSetupEntry("required", Boolean.class, Boolean.FALSE);

        public static final TestSetupEntry disabled = new TestSetupEntry("disabled", Boolean.class, Boolean.FALSE);

        public static final List<TestSetupEntry> list = new ArrayList<TestSetupEntry>();
        static {
            list.add(reRender);
            list.add(validatorId);
            list.add(rendered);
            list.add(ajaxSingle);
            list.add(immediate);
            list.add(bypassUpdate);
            list.add(limitToList);
            list.add(oncomplete);
            list.add(converter);
            list.add(process);
            list.add(processExternalValidation);
            list.add(required);
            list.add(disabled);
        }

    }
}