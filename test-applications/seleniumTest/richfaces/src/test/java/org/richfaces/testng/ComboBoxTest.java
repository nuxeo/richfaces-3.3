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

public class ComboBoxTest extends SeleniumTestBase {
	
	private String comboBox;

	private String comboboxField;

	private String comboboxButton;
	
	private String comboboxList;
	
	private String comboboxValue;
	
	private String submit; 

	private String value;

	private String trace; 

	private String message;
	
	private String directInputSuggestions; 
	
	private String filterNewValues;
	
	private String selectFirstOnUpdate; 

	private String enableManualInput;
	
	private void init(Template template) {
        renderPage(null, template, "#{comboBean.init}");
        String attrForm = getParentId() + "attrForm";
        directInputSuggestions = attrForm + ":directInputSuggestions";
        filterNewValues = attrForm + ":filterNewValues";
        selectFirstOnUpdate = attrForm + ":selectFirstOnUpdate";
        enableManualInput = attrForm + ":enableManualInput";
        String mainForm = getParentId() + "_form";
        comboBox = mainForm + ":comboBox";
        comboboxField = comboBox + "comboboxField";
        comboboxButton = comboBox + "comboboxButton";
        comboboxList = comboBox + "list";
        comboboxValue = comboBox + "comboboxValue";
        submit = mainForm + ":submit";
        value = getParentId() + "value";
        trace = getParentId() + "trace";
        message = getParentId() + "message";
	}	
	
    @Test
    public void testComboBoxComponent(Template template) {
        renderPage(template);

        String parentId = getParentId() + "_form:";

        String predefinedCBId = parentId + "predefined";
        String selectItemsCBId = parentId + "selectItems";
        String suggestionValuesCBId = parentId + "suggestionValues";

        writeStatus("check components' default labels");

        getTextById(predefinedCBId + "comboboxField").startsWith("Select a");
        getTextById(selectItemsCBId + "comboboxField").startsWith("Select a");
        getTextById(suggestionValuesCBId + "comboboxField").startsWith("Select a");

        writeStatus("check a combobox with predefined suggestions");

        clickById(predefinedCBId);
        writeStatus("type \"Hunt\"");

        type(predefinedCBId + "comboboxField", "Hunt");

        Assert.assertTrue(isVisibleById(predefinedCBId + "list"), "Component's pop-up must show up on key typing");

        clickById(predefinedCBId + "list");

        Assert.assertFalse(isVisibleById(predefinedCBId + "list"), "Component's pop-up has to be closed");
        AssertValueEquals(predefinedCBId + "comboboxValue", "Hunter");

        writeStatus("check a combobox with dynamic suggestions list");

        clickById(selectItemsCBId);

        writeStatus("verify \"directInputSuggestions\" component's attribute");

        writeStatus("type \"O\"");

        type(selectItemsCBId + "comboboxField", "O");

        Assert.assertTrue(isVisibleById(selectItemsCBId + "list"), "Component's pop-up must show up on key typing");
        AssertValueEquals(selectItemsCBId + "comboboxValue", "Oak");

        writeStatus("check a combobox with a simple String list as suggestions list");

        clickById(suggestionValuesCBId);
        writeStatus("type \"Ma\"");

        type(suggestionValuesCBId + "comboboxField", "Ma");
        Assert.assertTrue(isVisibleById(suggestionValuesCBId + "list"), "Component's pop-up must show up on key typing");

        clickById(suggestionValuesCBId + "list");

        Assert.assertFalse(isVisibleById(suggestionValuesCBId + "list"), "Component's pop-up has to be closed");
        AssertValueEquals(suggestionValuesCBId + "comboboxValue", "Maple");
    }

    /**
     *    Check that comboBox component present on the page.
     *    Styles & events attributes work as should.
     */
    //https://jira.jboss.org/jira/browse/RF-6141
	@Test(groups=FAILURES_GROUP)
	public void testStandardAttributes(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	Map<String, String> styleAttributes = new HashMap<String, String>();
    	styleAttributes.put("width", "100%");
    	styleAttributes.put("color", "yellow");
    	autoTester.testStyleAndClasses(new String[]{"noname"}, styleAttributes);
    	autoTester.testHTMLEvents();
	}
    
    /**
     *    Check that valueChangeListener triggered
     *    when user select new value from popup.
     *    Selected value should be correctly applied. 
     */
	@Test
	public void testValueAndListener(Template template) {
		init(template);
		Assert.assertEquals(selenium.getText(value), "");
		Assert.assertEquals(selenium.getText(trace), "");
		selenium.click(comboboxButton);
		selenium.mouseMove("xpath=id('" + comboboxList + "')/span[2]");
		selenium.click("xpath=id('" + comboboxList + "')/span[2]");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(value), "22");
		Assert.assertEquals(selenium.getText(trace), "changed");
	}
    
    /**
     *    Check component validation.
     *    Specific validator defined in "validator" attribute
     *    should be triggered either as the ones defined using nested validator tags
     */
	@Test
	public void testValidators(Template template) {
		init(template);
		Assert.assertEquals(selenium.getText(message), "");
		selenium.click(comboboxButton);
		selenium.mouseMove("xpath=id('" + comboboxList + "')/span[1]");
		selenium.click("xpath=id('" + comboboxList + "')/span[1]");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 11.");
		selenium.click(comboboxButton);
		selenium.mouseMove("xpath=id('" + comboboxList + "')/span[4]");
		selenium.click("xpath=id('" + comboboxList + "')/span[4]");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 44.");
	}
    
    /**
     *    The same as for RF-6064 but for the case of validation failure.
     *    Submitted value should be output back unchanged after submit
     */
	@Test
	public void testValidationFailure(Template template) {
		init(template);
		Assert.assertEquals(selenium.getText(value), "");
		Assert.assertEquals(selenium.getText(message), "");
		selenium.click(comboboxButton);
		selenium.mouseMove("xpath=id('" + comboboxList + "')/span[4]");
		selenium.click("xpath=id('" + comboboxList + "')/span[4]");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(value), "");
		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 44.");
	}
    
    /**
     *    Check "immediate", "required" & "requiredMessage" attributes
     */
	@Test
	public void testImmediateAndRequired(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testImmediate(false);
    	autoTester.testRequiredAndRequiredMessageAttributes();
	}
    
    /**
     *    Check "converter" attribute
     */
	@Test
	public void testConverter(Template template) {
		init(template);
		selenium.click(comboboxButton);
		Assert.assertEquals(selenium.getText("xpath=id('" + comboboxList + "')/span[3]"), "c:33");
	}
    
    /**
     *    Check components selection, filtering & presentation values
     *    by testing "directInputSuggestions", "filterNewValues",
     *    "selectFirstOnUpdate" attributes.
     */
	@Test
	public void testSelectionFilteringAndPresentation(Template template) {
		init(template);
		type(comboboxField, "c");
		Assert.assertEquals(selenium.getValue(comboboxField), "c");
		selenium.click(comboboxButton);
		clickAjaxCommandAndWait(directInputSuggestions);
		type(comboboxField, "c");
		Assert.assertEquals(selenium.getValue(comboboxField), "c:11");
		selenium.click(comboboxButton);
		clickAjaxCommandAndWait(directInputSuggestions);
		type(comboboxField, "c:2");
		Assert.assertEquals(selenium.getXpathCount("id('" + comboboxList + "')/span").intValue(), 1);
		selenium.click(comboboxButton);
		clickAjaxCommandAndWait(filterNewValues);
		type(comboboxField, "c:2");
		Assert.assertEquals(selenium.getXpathCount("id('" + comboboxList + "')/span").intValue(), 4);
		selenium.click(comboboxButton);
		clickAjaxCommandAndWait(filterNewValues);
		selenium.click(comboboxButton);
		Assert.assertTrue(selenium.getAttribute("xpath=id('" + comboboxList + "')/span[1]@class").indexOf("selected") != -1);
		selenium.click(comboboxButton);
		clickAjaxCommandAndWait(selectFirstOnUpdate);
		selenium.click(comboboxButton);
		Assert.assertFalse(selenium.getAttribute("xpath=id('" + comboboxList + "')/span[1]@class").indexOf("selected") != -1);
	}
    
    
    /**
     *    Check combobox JS API: showList(), hideList(), enable(), disable().
     */
	@Test
	public void testJSAPI(Template template) {
		init(template);
		selenium.runScript("var comboBox = document.getElementById('" + comboBox + "').component;");
		selenium.runScript("comboBox.showList();");
		Assert.assertTrue(selenium.isVisible(comboboxList));
		selenium.runScript("comboBox.hideList();");
		Assert.assertFalse(selenium.isVisible(comboboxList));
		selenium.runScript("comboBox.disable();");
		Assert.assertFalse(selenium.isEditable(comboboxField));
		selenium.runScript("comboBox.enable();");
		Assert.assertTrue(selenium.isEditable(comboboxField));
	}
	
    /**
     *    check default label.
     */
	@Test
	public void testDefaultLabel(Template template) {
		init(template);
		Assert.assertEquals(selenium.getValue(comboboxField), "Select ...");
	}
	
    /**
     *    component with rendered = false is not present on the page 
     */
	@Test
	public void testRendered(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testRendered();
	}
	
    /**
     *    Check with enableManualInput = false attribute.   	  
     */
	@Test
	public void testDisableManualInput(Template template) {
		init(template);
		clickAjaxCommandAndWait(enableManualInput);
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + comboboxField + "')@readonly"), "true");
	}
	
	@Override
	public void setValueEmpty() {
		selenium.type(getParentId() + "autoTestForm:componentIdcomboboxValue", "");
	}
	
    public String getTestUrl() {
        return "pages/comboBox/comboBoxTest.xhtml";
    }

    
    @Override
    public String getAutoTestUrl() {
    	return "pages/comboBox/comboBoxAutoTest.xhtml";
    }
}