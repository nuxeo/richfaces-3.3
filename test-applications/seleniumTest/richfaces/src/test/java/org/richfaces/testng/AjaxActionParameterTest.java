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

import org.ajax4jsf.bean.A4JActionParam;
import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjaxActionParameterTest extends SeleniumTestBase {
	
	static final String TEST_CONVERTER_ATTRIBUTE = "/pages/actionParam/testConverterAttribute.xhtml";
	
	static final String TEST_CONVERTER_FOR_PROPER_JAVA_TYPE = "/pages/actionParam/testConverterForProperJavaType.xhtml";
	
	
	@Test
	public void testActionListenerInCaseOfExternalValidationFailure(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testActionListener();

	}
	
	@Test
	public void testConverterAttribute(Template template) {
		renderPage(TEST_CONVERTER_ATTRIBUTE, template, null);
		testConverter(getParentId());
	}
	
	@Test
	public void testConverterForProperJavaType(Template template) {
		renderPage(TEST_CONVERTER_FOR_PROPER_JAVA_TYPE, template, null);
		testConverter(getParentId());
	}

    /**
     * Tests ajax action parameter component
     * 
     * @param template
     */
    @Test
    public void testAjaxActionParameterComponent(Template template) {
        renderPage(template);
        String parentId = getParentId();
        String parameter = null;

        String paramID = parentId + "_parameter";
        String ajaxButttonID = parentId + "_form1:ajaxSubmit";
        String htmlButttonID = parentId + "_form2:htmlSubmit";

        // test ajax submit
        clickById(ajaxButttonID);
        waitForAjaxCompletion();
        parameter = getTextById(paramID);
        checkParameter(parameter);

        // test html submit
        clickCommandAndWait(htmlButttonID);
        parameter = getTextById(paramID);
        checkParameter(parameter);
    }

    /**
     * Checks action parameter
     * 
     * @param parameter -
     *                Action Parameter value
     */
    private void checkParameter(String parameter) {
        if ("".equals(parameter)) {
            writeStatus("<a4j:actionParameter> failed. ActionListener skipped.", true);
            Assert.fail("<a4j:actionParameter> failed. ActionListener skipped.");
        } else if (parameter.equals(A4JActionParam.ERRORS.NO_PARAM)) {
            writeStatus("<a4j:actionParameter> failed. Parameter has been not sent with ajax request.", true);
            Assert.fail("<a4j:actionParameter> failed. Parameter has been not sent with ajax request.");
        } else if (parameter.equals(A4JActionParam.ERRORS.NOT_ASSIGNED)) {
            writeStatus("<a4j:actionParameter> failed. Parameter value has been not assigned to bean", true);
            Assert.fail("<a4j:actionParameter> failed. Parameter value has been not assigned to bean");
        } else if (parameter.equals(A4JActionParam.ERRORS.INVALID_VALUE)) {
            writeStatus("<a4j:actionParameter> failed. Parameter value is incorrect", true);
            Assert.fail("<a4j:actionParameter> failed. Parameter value is incorrect");
        } else {
            writeStatus("<a4j:actionParameter> passed successfully");
        }
    }
    
    private void testConverter(String parentId) {
    	String ajaxLinkId = parentId + "form1:ajaxSubmit";
		String paramNameId = parentId + "name";
		String paramValueId = parentId + "value";
		
		String onclick = selenium.getAttribute("//*[@id='"+ajaxLinkId+"']/@onclick");
		if (!onclick.contains("actionParamName,")) {
			Assert.fail("Object has not been converted to string properly But was: " + onclick);
		}
		
		AssertTextEquals(paramNameId, "actionParamName");
		AssertTextEquals(paramValueId, "", "Value should be empty. It should be filled while parameter converting");
		
		clickAjaxCommandAndWait(ajaxLinkId);
		
			
		String text = getTextById(paramValueId);
		if (text == null || text.length() == 0) {
			Assert.fail("Converter has not been performed. Value should not be empty.");
		}
		
		onclick = selenium.getAttribute("//*[@id='"+ajaxLinkId+"']/@onclick");
		if (!onclick.contains("actionParamName," + text)) {
			Assert.fail("Object has not been converted to string properly. It should consist from 'actionParamName,' + current date time. But was: " + onclick);
		}

		
		clickAjaxCommandAndWait(ajaxLinkId);
		
		AssertTextNotEquals(paramValueId, text, "Value should be changed after each submit");
    }
    
    @Override
    public void sendAjax() {
    	String id = getParentId() + "autoTestForm:submit";
    	clickCommandAndWait(id);
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "/pages/actionParam/autoTestActionParam.xhtml";
    }

    @Override
    public String getTestUrl() {
        return "pages/actionParam/actionParam.xhtml";
    }

}
