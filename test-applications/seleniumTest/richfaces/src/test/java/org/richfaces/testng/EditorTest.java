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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexandr Levkovsky
 *
 */
public class EditorTest extends SeleniumTestBase {

	private static String TEST_URL = "pages/editor/editor.xhtml";
	private static String AUTO_TEST_URL = "pages/editor/editorAutoTest.xhtml";
	private static String THEME_TEST_URL = "pages/editor/testThemeAttribute.xhtml";
	private static String SKIN_TEST_URL = "pages/editor/testSkinAttribute.xhtml";
	private static String REQUIRED_TEST_URL = "pages/editor/testRequiredAttribute.xhtml";
	private static String WIDTH_HEIGHT_TEST_URL = "pages/editor/testWidthAndHeightAttribute.xhtml";
	private static String PLUGINS_TEST_URL = "pages/editor/testPluginsAttribute.xhtml";
	private static String CONFIGURATION_AND_FPARAMS_TEST_URL = "pages/editor/testConfigurationAttribute.xhtml";
	private static String VALIDATOR_TEST_URL = "pages/editor/testValidatorAttribute.xhtml";
	private static String CONVERTER_TEST_URL = "pages/editor/testConverterAttribute.xhtml";
	private static String AJAX_TEST_URL = "pages/editor/tesAjaxaAndBindingRerender.xhtml";

	private static String FORM_ID = "formId:";
	private static String EDITOR_DIV_ID = FORM_ID + "editorId";
	
	private static String EDITOR_TEXAREA_DIV_ID = FORM_ID + "editorIdTextArea";
	private static String EDITOR_TABLE_ID = EDITOR_TEXAREA_DIV_ID + "_tbl";
	private static String EDITOR_IFRAME_ID = EDITOR_TEXAREA_DIV_ID + "_ifr";
	private static String EDITOR_HMESSAGE_ID = FORM_ID + "editorMessageId";

	private static String XPATH_ADVANCED_THEME_STYLES = "//link[contains(@href,'themes/advanced')]";
	private static String XPATH_SIMPLE_THEME_STYLES = "//link[contains(@href,'themes/simple')]";

	private static String XPATH_O2K7_SKIN_STYLES = "//link[contains(@href,'skins/o2k7')]";
	private static String XPATH_RICHFACES_SKIN_STYLES = "//link[contains(@href,'skins/richfaces')]";

	private static String ACTION_BUTTON_ID = FORM_ID + "submitId";
	private static String EDITOR_VALUE_OUTPUT_ID = FORM_ID + "editorValueId";
	private static String EDITOR_VALUE_OUTPUT_ID2 = FORM_ID + "actualValueId";

	private static String REQUIRED_HMESSAGE = "Value is required";

	private static String CONFIGURATION_NAME = "editorFullConfiguration";
	private static String FPARAM_NAME = "paramName";
	private static String FPARAM_VALUE = "paramValue";

	private static List<String> PLUGINS = new ArrayList<String>();
	static {
		PLUGINS.add("Spellchecker");
		PLUGINS.add("PageBreak");
		PLUGINS.add("Style");
		PLUGINS.add("Layer");
		PLUGINS.add("Tables");
		PLUGINS.add("Save");
		PLUGINS.add("Advanced HR");
		PLUGINS.add("Advanced image");
		PLUGINS.add("Advanced link");
		PLUGINS.add("Emotions");
		PLUGINS.add("IESpell (IE Only)");
		PLUGINS.add("InlinePopups");
		PLUGINS.add("Insert date/time");
		PLUGINS.add("Preview");
		PLUGINS.add("Media");
		PLUGINS.add("Search/Replace");
		PLUGINS.add("Print");
		PLUGINS.add("Contextmenu");
		PLUGINS.add("Paste text/word");
		PLUGINS.add("Directionality");
		PLUGINS.add("Fullscreen");
		PLUGINS.add("Non editable elements");
		PLUGINS.add("Visual characters");
		PLUGINS.add("Nonbreaking space");
		PLUGINS.add("XHTML Xtras Plugin");
		PLUGINS.add("Template plugin");
	};

	static Map<String, String> STYLE_ATTR;
	static String[] CLASSES = new String[] { "myClass" };

	static {
		STYLE_ATTR = new HashMap<String, String>();
		STYLE_ATTR.put("width", "100%");
		STYLE_ATTR.put("color", "blue");
	}

	static final String RESET_METHOD_ME = "#{editorBean.reset}";

	@Test
	public void testRenderedAttribute(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testRendered();
	}

	@Test
	public void testAutoStyleAndClassesAttributes(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testStyleAndClasses(CLASSES, STYLE_ATTR);
	}

	@Test
	public void testRequiredAndRequiredMessageAttributes(Template template) {
		renderPage(REQUIRED_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();
		String messageId = getParentId() + EDITOR_HMESSAGE_ID;

		AssertNotPresent(messageId);
		//setValueInEditor("");
		clickTestButton();

		AssertPresent(messageId);
		String message = getTextById(messageId);
		Assert.assertEquals(message, REQUIRED_HMESSAGE,
				"Required message not correct");
		//String checked = selenium.getAttribute(XPATH_CHECKBOX_VALUE);

		setValueInEditor("<span> Some value </span>");

		clickTestButton();

		AssertNotPresent(messageId);
		//checked = selenium.getAttribute(XPATH_CHECKBOX_VALUE);

	}

	@Test
	public void testValidatorAttribute(Template template) {
		renderPage(VALIDATOR_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();
		String messageId = getParentId() + EDITOR_HMESSAGE_ID;

		AssertNotPresent(messageId);
		clickTestButton();

		AssertPresent(messageId);
		String message = getTextById(messageId);
		Assert.assertEquals(message, "Incorrect input",
				"Validator message not correct");
	}
	
	@Override
	public void reRenderForm() {
		// we should skip reRender parent container on render page (tinyMCE JS script becomes broken after this)
	}
	
	@Test
	public void testConvertorAttribute(Template template) {
		renderPage(CONVERTER_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();
		String messageId = getParentId() + EDITOR_HMESSAGE_ID;

		AssertNotPresent(messageId);

		clickTestButton();

		AssertNotPresent(messageId);
		String value = getTextById(getParentId() + EDITOR_VALUE_OUTPUT_ID);
		Assert.assertEquals(value, "Converter Value", "Value is not valid");

		clickTestButton();

		AssertPresent(messageId);
		String message = getTextById(messageId);
		Assert.assertEquals(message, "Error while convert to Object",
				"Converter message not correct");
	}

	@Test
	public void testAjaxRerenderingAndBinding(Template template) {
		renderPage(AJAX_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();
		String valueOutputId = getParentId() + EDITOR_VALUE_OUTPUT_ID;
		String actualOutputId = getParentId() + EDITOR_VALUE_OUTPUT_ID2;

		AssertTextEquals(valueOutputId, "Some value....",
				"Editor Value is invalid");

		clickAjaxCommandAndWait(getParentId() + ACTION_BUTTON_ID);
		checkJSError();

		AssertTextEquals(valueOutputId, "Some value....",
				"This output should have old Value");
		AssertTextEquals(actualOutputId, "Ajax value",
				"This output should have new rerendered Value");
	}

	@Test
	public void testThemeAttribute(Template template) {
		renderPage(THEME_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();

		int advancedThemeStylesCount = selenium.getXpathCount(
				XPATH_ADVANCED_THEME_STYLES).intValue();

		if (advancedThemeStylesCount < 1) {
			Assert.fail("Advanced theme styles in not loaded");
		}

		clickTestButton();
		int simpleThemeStylesCount = selenium.getXpathCount(
				XPATH_SIMPLE_THEME_STYLES).intValue();

		if (simpleThemeStylesCount < 1) {
			Assert.fail("Simple theme styles in not loaded");
		}

	}

	@Test
	public void testSkinAttribute(Template template) {
		renderPage(SKIN_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();

		int richfacesSkinStylesCount = selenium.getXpathCount(
				XPATH_RICHFACES_SKIN_STYLES).intValue();

		if (richfacesSkinStylesCount < 1) {
			Assert.fail("richfaces skins styles in not loaded");
		}

		clickTestButton();

		int o2k7SkinStylesCount = selenium
				.getXpathCount(XPATH_O2K7_SKIN_STYLES).intValue();

		if (o2k7SkinStylesCount < 1) {
			Assert.fail("o2k7 skins styles in not loaded");
		}
	}

	@Test
	public void testWidthAndHeightAttributes(Template template) {
		renderPage(WIDTH_HEIGHT_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();

		String editorTableId = getParentId() + EDITOR_TABLE_ID;
		String width = getStyleAttributeString(editorTableId, "width");
		String height = getStyleAttributeString(editorTableId, "height");
		Assert.assertEquals(width, "600px", "width is invalid");
		Assert.assertEquals(height, "600px", "height is invalid");

		clickTestButton();

		width = getStyleAttributeString(editorTableId, "width");
		height = getStyleAttributeString(editorTableId, "height");
		Assert.assertEquals(width, "650px", "width is invalid");
		Assert.assertEquals(height, "650px", "height is invalid");
	}

	@Test
	public void testPlugingAttribute(Template template) {
		renderPage(PLUGINS_TEST_URL, template, RESET_METHOD_ME);
		assertEditorPresent();

		clickById(getParentId() + ACTION_BUTTON_ID);
		String text = getTextById("pluginsId");
		if (text == null || text.length() == 0) {
			Assert.fail("can't get loaded plugins");
		}
		String[] plugins = text.split(",");
		if (plugins.length != PLUGINS.size()) {
			Assert.fail("count of loaded plugins is not equlas required");
		}
		for (String plugin : plugins) {
			if (!PLUGINS.contains(plugin)) {
				Assert.fail("Invalid plugin was loaded: " + plugin);
			}
		}

	}

	@Test
	public void testConfigurationAttribute(Template template) {
		renderPage(CONFIGURATION_AND_FPARAMS_TEST_URL, template,
				RESET_METHOD_ME);
		assertEditorPresent();

		Properties properties = loadProperties(CONFIGURATION_NAME);

		clickById(getParentId() + ACTION_BUTTON_ID);
		String text = getTextById("parametersId");

		if (text == null || text.length() == 0) {
			Assert.fail("can't get loaded parameters");
		}
		String[] parameters = text.split(";;");
		List<String> paramsList = Arrays.asList(parameters);

		for (Object key : properties.keySet()) {
			String keyString = key.toString();
			String valueString = properties.get(key).toString();
			if (valueString.indexOf("\"") != -1) {
				valueString = valueString.replaceAll("\"", "");
			}
			String param = keyString + ':' + valueString;
			if (!paramsList.contains(param)) {
				Assert.fail("Configuration parameters not contains " + param);
			}
		}

	}

	@Test
	public void testFPramsPassingToEditor(Template template) {
		renderPage(CONFIGURATION_AND_FPARAMS_TEST_URL, template,
				RESET_METHOD_ME);
		assertEditorPresent();

		clickById(getParentId() + ACTION_BUTTON_ID);
		String text = getTextById("parametersId");

		if (text == null || text.length() == 0) {
			Assert.fail("can't get loaded parameters");
		}
		String[] parameters = text.split(";;");
		List<String> paramsList = Arrays.asList(parameters);

		String param = FPARAM_NAME + ':' + FPARAM_VALUE;
		if (!paramsList.contains(param)) {
			Assert.fail("f:param not passed to configuration");
		}

	}

	private void assertEditorPresent() {
		AssertPresent(getParentId() + EDITOR_DIV_ID);
		AssertPresent(getParentId() + EDITOR_TEXAREA_DIV_ID);
		AssertPresent(getParentId() + EDITOR_IFRAME_ID);
	}

	private void clickTestButton() {
		clickCommandAndWait(getParentId() + ACTION_BUTTON_ID);
	}

	private void setValueInEditor(String value) {
		String script = "tinyMCE.activeEditor.setContent(" + value + ");";
		runScript(script, true);
	}

	@Override
	public String getAutoTestUrl() {
		return AUTO_TEST_URL;
	}

	@Override
	public String getTestUrl() {
		return TEST_URL;
	}

	/**
	 * Method to load properties from property file
	 * 
	 * @param name - properties file name
	 * @return Properties
	 */
	private Properties loadProperties(String name) {
		Properties properties = new Properties();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(name + ".properties");
		if (is == null) {
			Assert.fail("Can't load propertioes file for test");
		}
		try {
			properties.load(is);
		} catch (IOException e) {
			Assert.fail("Can't load propertioes file for test");
		}
		return properties;
	}
}
