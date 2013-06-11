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
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProgressBarTest extends SeleniumTestBase {

	private static Map<String, String> params = new HashMap<String, String>();

	private final static String RESET_METHOD = "#{progressBarBean.reset}";
	static {
		params.put("parameter1", "value1");
		params.put("parameter2", "value2");
		params.put("parameter3", "value3");
	}

	private final static String RERENDER_AFTER_COMPLETE_TEST_URL = "pages/progressBar/progressBarRerenderOnCompleteTest.xhtml";
	private final static String MACROSUBSTITUTION_TEST_URL = "pages/progressBar/progressBarMacrosubstitutionTest.xhtml";

	@Test
	public void testProgressBarComponent(Template template) {

		renderPage(template, RESET_METHOD);

		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar1";
		int value = getProgressBarValue(progressBarId);
		Assert.assertTrue(value < 0, "Progress value should be negative");
		String text = getTextById(progressBarId);
		writeStatus("Check if process not started");
		Assert.assertTrue(text.startsWith("Process not started"),
				"Initial facet does not appear");

		writeStatus("Enable polling");
		enableProgressBar(progressBarId, true);

		while (progressBarId.indexOf("progressBar1") != -1 && !selenium.isElementPresent(progressBarId + ":remain")) {
			waitForAjaxCompletion();
		}

		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		writeStatus("Check label");
		text = getTextById(progressBarId + ":remain");
		Assert.assertTrue(text.endsWith("%"), "Percent label doesnot appear");

		writeStatus("Check value");
		value = getProgressBarValue(progressBarId);
		Assert.assertTrue(value > 0, "Progress value should be positive");

		writeStatus("Enable polling");
		enableProgressBar(progressBarId, true);

		pause(5000, progressBarId);
		writeStatus("Check value");
		Assert.assertTrue(
				(getProgressBarValue(progressBarId).intValue() > value),
				"Progress has been terminated abnormal");

		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		value = getProgressBarValue(progressBarId);
		pause(1500, progressBarId);
		writeStatus("Check value");
		Assert.assertTrue(getProgressBarValue(progressBarId) == value,
				"Progress didnt stop after progressBar disabling");

		clickById(parentId + "_complete");
		waitForAjaxCompletion();
		writeStatus("Check if process completed");
		text = getTextById(progressBarId);
		Assert.assertTrue(text.startsWith("Process completed"),
				"Comlete facet does not appear");

		// - Test client mode

		writeStatus("Check value");
		progressBarId = parentId + "progressBar2";
		value = getProgressBarValue(progressBarId);
		Assert.assertTrue(value == -5, "Progress value should be -5 ");

		text = getTextById(progressBarId);
		writeStatus("Check if process not started");
		Assert.assertTrue(text.startsWith("Process not started"),
				"Initial facet does not appear");

		setProgressBarValue(progressBarId, 20);
		value = getProgressBarValue(progressBarId);
		writeStatus("Check value");
		Assert.assertTrue(value == 20, "Progress value should be 20 ");

		setProgressBarValue(progressBarId, 60);
		value = getProgressBarValue(progressBarId);
		writeStatus("Check value");
		Assert.assertTrue(value == 60, "Progress value should be 60");

	}

	@Test
	public void testActionListenersWithExternalValidationFailure(
			Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test action listener attribute");
		tester.testExtrenalValidationFailure();
	}

	@Test
	public void testRenderedAttribute(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test component with rendered = false is not present on the page");
		tester.testRendered();
	}

	@Test
	public void testNestedParams(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
		tester.testRequestParameters(params);
	}

	@Test
	public void testImmediate(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test immediate attribute");
		tester.testImmediate(false);
	}

	@Test
	public void testImmediateWithExternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test immediate attribute with external validation failed");
		tester.testImmediateWithExternalValidationFailed(false);
	}

	@Test
	public void testListenersAreNotInvokedInCaseOfExternalValidationFailure(
			Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test listeners aren't invoked in case of external validation failure");
		tester.testExtrenalValidationFailure();
	}

	@Test
	public void testAjaxSingle(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test ajaxSingle attribute");
		tester.testAjaxSingle(false);
	}

	@Test
	public void testAjaxSingleWithInternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test ajaxSingle attribute in case of invalid children state");
		tester.testAjaxSingleWithInternalValidationFailed();
	}

	@Test
	public void testAjaxSingleWithExternalProcessValidationFailed(
			Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test ajaxSingle attribute in case of external validation failure");
		tester.testAjaxSingleWithProcesExternalValidation(false);
	}

	@Test
	public void testReRender(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		writeStatus("Test reRender attribute");
		tester.testReRender();
	}

	@Test
	public void testReRenderAfterComplete(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(RERENDER_AFTER_COMPLETE_TEST_URL, template,
				RESET_METHOD);
		writeStatus("Test reRenderAfterComplete attribute");
		tester.testReRender();
	}

	@Test
	public void testJSAPIAjaxMode(Template template) {
		renderPage(template, RESET_METHOD);
		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar1";
		testJSAPI(progressBarId);

	}

	@Test
	public void testJSAPIClientMode(Template template) {
		renderPage(template, RESET_METHOD);
		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar2";
		testJSAPI(progressBarId);
	}

	private void testJSAPI(String progressBarId) {

		int value = getProgressBarValue(progressBarId);
		Assert.assertTrue(value < 0, "Progress value should be negative");

		// enable()
		enableProgressBar(progressBarId, true);
		while (progressBarId.indexOf("progressBar1") != -1 && !selenium.isElementPresent(progressBarId + ":remain")) {
			waitForAjaxCompletion();
		}

		// disable()
		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		// setValue(value)
		writeStatus("Check value");
		setProgressBarValue(progressBarId, 20);
		value = getProgressBarValue(progressBarId);

		Assert.assertTrue(value == 20, "Progress value should be 20 ");

		// setLabel(label)
		writeStatus("Check label");
		String text = getTextById(progressBarId + ":remain");
		Assert.assertTrue(text.endsWith("%"), "Percent label doesnot appear");

		setProgressBarLabel(progressBarId, "myLabel");

		text = getTextById(progressBarId + ":remain");
		Assert.assertTrue(text.contains("myLabel"), "New label doesnot appear");

	}

	@Test
	public void testFacets(Template template) {

		renderPage(template, RESET_METHOD);

		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar1";
		int value = getProgressBarValue(progressBarId);
		Assert.assertTrue(value < 0, "Progress value should be negative");
		String text = getTextById(progressBarId);
		writeStatus("Check if process not started");
		Assert.assertTrue(text.startsWith("Process not started"),
				"Initial facet does not appear");

		writeStatus("Enable polling");
		enableProgressBar(progressBarId, true);

		while (!selenium.isElementPresent(progressBarId + ":remain")) {
			waitForAjaxCompletion();
		}

		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		text = getTextById(progressBarId);
		Assert.assertFalse(text.contains("Process not started"),
				"Initial facet does not dissapear");
		Assert.assertFalse(text.contains("Process completed"),
				"Initial facet does not dissapear");

		clickById(parentId + "_complete");
		waitForAjaxCompletion();
		writeStatus("Check if process completed");
		text = getTextById(progressBarId);
		Assert.assertTrue(text.startsWith("Process completed"),
				"Comlete facet does not appear");

	}

	@Test
	public void testMacrosubstitutionAjaxMode(Template template) {
		renderPage(MACROSUBSTITUTION_TEST_URL, template, RESET_METHOD);
		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar1";
		testMacrosubstitution(progressBarId, 2);

	}

	@Test
	public void testMacrosubstitutionClientMode(Template template) {
		renderPage(MACROSUBSTITUTION_TEST_URL, template, RESET_METHOD);
		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar2";
		testMacrosubstitution(progressBarId, 1);
	}

	private void testMacrosubstitution(String progressBarId, int value) {

		writeStatus("Enable polling");
		enableProgressBar(progressBarId, true);
		while (progressBarId.indexOf("progressBar1") != -1 && !selenium.isElementPresent(progressBarId + ":remain")) {
			waitForAjaxCompletion();
		}

		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		setProgressBarValue(progressBarId, 20);

		// check macrosubstitutions

		writeStatus("Check macrosubstitutions");
		String text = getTextById(progressBarId);
		Assert.assertTrue(text.contains("Min value is 0"),
				"{minValue} macrosubstitution doesn't work");
		Assert.assertTrue(text.contains("current value is 20"),
				"{value} macrosubstitution doesn't work");
		Assert.assertTrue(text.contains("max value is 100"),
				"{maxValue} macrosubstitution doesn't work");

	}

	@Test
	public void testStylesAndClasses(Template template) {

		renderPage(MACROSUBSTITUTION_TEST_URL, template, RESET_METHOD);
		String parentId = getParentId() + "_form:";
		String progressBarId = parentId + "progressBar1";

		assertClassNames(progressBarId, new String[] { "initialClass" },
				"initialClass attribute not work for Progress Bar", true);
		
		writeStatus("Enable polling");
		enableProgressBar(progressBarId, true);
		while (!selenium.isElementPresent(progressBarId + ":remain")) {
			waitForAjaxCompletion();
		}

		writeStatus("Disable polling");
		enableProgressBar(progressBarId, false);

		assertClassNames(progressBarId, new String[] { "testClass" },
				"styleClass attribute not work for Progress Bar", true);
		assertClassNames(progressBarId + ":remain",
				new String[] { "remainClass" },
				"remainClass attribute not work for Progress Bar", true);
		assertClassNames(progressBarId + ":complete",
				new String[] { "completeClass" },
				"completeClass attribute not work for Progress Bar", true);

		assertAttributeContains(progressBarId, "style", "font-size: 10px",
				"style attribute not work for Progress Bar");
	}
	
	@Test
    public void testHTMLEvents(Template template){
    	AutoTester tester = getAutoTester(this);
    	tester.renderPage(template, null);   	
    	tester.testHTMLEvents();
    }

	private void enableProgressBar(String id, boolean enable) {
		invokeFromComponent(id, (enable ? "enable" : "disable"), null);
	}

	private void setProgressBarValue(String id, Object value) {
		invokeFromComponent(id, "setValue", value);
	}

	private void setProgressBarLabel(String id, Object value) {
		invokeFromComponent(id, "setLabel", value);
	}

	private Integer getProgressBarValue(String id) {
		String value = invokeFromComponent(id, "getValue", null); // runScript(script.toString());
		Integer v = Integer.parseInt(value);
		return v;
	}

	@Override
	public void sendAjax() {
		delay(2000);
	}

	@Override
	public void setInternalValidationFailed() {
		String childCompId = getAutoTester(this).getClientId("") + "child";
		setValueById(childCompId, "");
	}

	public String getTestUrl() {
		return "pages/progressBar/progressBarTest.xhtml";
	}

	@Override
	public String getAutoTestUrl() {
		return "pages/progressBar/progressBarAutoTest.xhtml";
	}

}
