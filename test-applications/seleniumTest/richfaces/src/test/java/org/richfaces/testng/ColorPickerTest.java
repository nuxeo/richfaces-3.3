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

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ColorPickerTest extends SeleniumTestBase {
	
	private String colorPicker;
	private String colorPickerButton;
	private String colorPickerList;
	private String xPathInput;
	private String xPathColorArea;
	private String hexInputId;
	
	private String submit; 
//	private String value;
//	private String trace; 
//	private String message;
	
	private void init(Template template) {
        renderPage(null, template, "#{comboBean.init}");
        String mainForm = getParentId() + "autoTestForm";
        
        colorPicker = mainForm + ":componentId";
        colorPickerButton = colorPicker;
        colorPickerList = colorPicker + "-colorPicker-popup";
        hexInputId = colorPicker + "-colorPicker-hex";
        xPathInput = "xpath=id('" + colorPickerButton + "')/input";

        submit = mainForm + ":submit";
        
//        value = getParentId() + "value";
//        trace = getParentId() + "trace";
//        message = getParentId() + "message";
	}	
	
//    @Test
//    public void testColorPickerComponent(Template template) {
//        init(template);
//
//        // Test setted value
//        testValueSettedOnPage(template);
//        
//        // Select color on RGB 
//        testSetValueOnRGB(template);
//
//        // open colorPicker and close it by Cancel button
//        testSetValueOnHSB(template);
//
//        // open colorPicker and close it by Cancel button
//        testInputValueByColorArea(template);
//        
//        // test cancel button
//        testCancelButton(template);
//        
//        // test cancel button
//        testValue(template);
//    }

	@Test
    public void testCancelButton(Template template) {
        init(template);
        
        openColorPicker();

        final String oldValue = selenium.getValue(xPathInput);
        
        setValueOnRGB("255", "0", "255");
        
        Assert.assertTrue(isVisible(hexInputId), "Hex Input must be presented");
        Assert.assertEquals("#" + selenium.getValue(hexInputId), "#ff00ff"); 
        
        final String xPathButtonCancel = "xpath=id('" + colorPickerList + "')//button[@name='cancel']";
        Assert.assertTrue(isVisible(xPathButtonCancel), "Button Cancel must be presented");
        selenium.click(xPathButtonCancel);
        
        Assert.assertFalse(isVisible(colorPickerList), "Component's pop-up must show up on click");

        Assert.assertTrue(isVisible(xPathInput), "Button Ok must be presented");
        Assert.assertEquals(selenium.getValue(xPathInput), oldValue); // value from page
    }

    
    public void testInputValueByColorArea(Template template) {
        init(template);
        
        openColorPicker();

        xPathColorArea = "xpath=id('" + colorPickerList + "')//div[@class='rich-colorPicker-color']/div";
        selenium.clickAt(xPathColorArea, "0,0"); // don't work
        
        testInputedValue("#ffffff");
    }

    @Test
    public void testValueSettedOnPage(Template template) { // don't work in tables
        init(template);
        
        Assert.assertTrue(isVisible(colorPickerButton), "Component must be visible");
        Assert.assertFalse(isVisible(colorPickerList), "Component's pop-up must show up on click");

        Assert.assertTrue(isVisible(xPathInput), "Input must be presented");
        Assert.assertEquals(selenium.getValue(xPathInput), "#ff0000"); // value from page
    }

    @Test
    public void testSetValueOnHSB(Template template) {
        init(template);
        
        openColorPicker();

        selenium.type(colorPicker + "-colorPicker-hsb-h", "120");
        selenium.type(colorPicker + "-colorPicker-hsb-s", "100");
        selenium.type(colorPicker + "-colorPicker-hsb-b", "100");
        
        testInputedValue("#00ff00");
    }

    @Test
    public void testSetValueOnRGB(Template template) {
        init(template);
        
        openColorPicker();

        setValueOnRGB("0", "0", "255");
        
        testInputedValue("#0000ff");
    }

    private void setValueOnRGB(String r, String g, String b) {
        selenium.type(colorPicker + "-colorPicker-rgb-r", r);
        selenium.type(colorPicker + "-colorPicker-rgb-g", g);
        selenium.type(colorPicker + "-colorPicker-rgb-b", b);
    }

    private void testInputedValue(final String expectedValue) {
        Assert.assertTrue(isVisible(hexInputId), "Hex Input must be presented");
        Assert.assertEquals("#" + selenium.getValue(hexInputId), expectedValue); 
        
        final String xPathButtonOk = "xpath=id('" + colorPickerList + "')//button[@name='submit']";
        Assert.assertTrue(isVisible(xPathButtonOk), "Button Cancel must be presented");
        selenium.click(xPathButtonOk);
        
        Assert.assertFalse(isVisible(colorPickerList), "Component's pop-up must show up on click");

        Assert.assertTrue(isVisible(xPathInput), "Button Ok must be presented");
        Assert.assertEquals(selenium.getValue(xPathInput), expectedValue); // value from page
    }

    /**
     *    Check that valueChangeListener triggered
     *    when user select new value from popup.
     *    Selected value should be correctly applied. 
     */
	@Test
	public void testValue(Template template) {
		init(template);

        openColorPicker();
        setValueOnRGB("0", "255", "255");
        testInputedValue("#00ffff");

		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getValue(xPathInput), "#00ffff");
	}

    private void openColorPicker() {
        clickById(colorPickerButton);
        Assert.assertTrue(isVisible(colorPickerList), "Component's pop-up must show up on click");
    }
    
//    /**
//     *    Check component validation.
//     *    Specific validator defined in "validator" attribute
//     *    should be triggered either as the ones defined using nested validator tags
//     */
//	@Test
//	public void testValidators(Template template) {
//		init(template);
//		
//		Assert.assertEquals(selenium.getText(message), "");
//		
//		AutoTester
//        openColorPicker();
//        setValueOnRGB("0", "255", "255");
//        testInputedValue("#00ffff");
//        clickAjaxCommandAndWait(submit);
//		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 11.");
//		
//        openColorPicker();
//        setValueOnRGB("255", "255", "255");
//        testInputedValue("#ffffff");
//		clickAjaxCommandAndWait(submit);
//		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 44.");
//	}
//    
//    /**
//     *    The same as for RF-6064 but for the case of validation failure.
//     *    Submitted value should be output back unchanged after submit
//     */
//	@Test
//	public void testValidationFailure(Template template) {
//		init(template);
//		Assert.assertEquals(selenium.getText(value), "");
//		Assert.assertEquals(selenium.getText(message), "");
//		selenium.click(colorPickerButton);
//		selenium.mouseMove("xpath=id('" + colorPickerList + "')/span[4]");
//		selenium.click("xpath=id('" + colorPickerList + "')/span[4]");
//		clickAjaxCommandAndWait(submit);
//		Assert.assertEquals(selenium.getText(value), "");
//		Assert.assertEquals(selenium.getText(message), "Value mustn't be equal 44.");
//	}
//    
//    /**
//     *    Check "immediate", "required" & "requiredMessage" attributes
//     */
//	@Test
//	public void testImmediateAndRequired(Template template) {
//    	AutoTester autoTester = getAutoTester(this);
//    	autoTester.renderPage(template, null);
//    	autoTester.testImmediate(false);
//    	autoTester.testRequiredAndRequiredMessageAttributes();
//	}
    
	@Override
	public void setValueEmpty() {
		selenium.type(getParentId() + "autoTestForm:componentIdcolorPickerValue", "");
	}
	
    public String getTestUrl() {
        return "pages/colorPicker/colorPickerTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/colorPicker/colorPickerAutoTest.xhtml";
    }
}