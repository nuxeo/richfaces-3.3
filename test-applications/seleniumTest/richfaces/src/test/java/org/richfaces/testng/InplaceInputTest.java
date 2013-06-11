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
import org.richfaces.testng.util.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InplaceInputTest extends InplacesTest {

    private static final String INPLACE_INPUT_SIMPLE = "inplaceInput";

    private static final String INPLACE_INPUT_DECORATED = "inplaceInputDecorated";

    private static final String INPLACE_INPUT_DECORATED_OK = "inplaceInputDecoratedok";

    private static final String INPLACE_INPUT_DECORATED_CANCEL = "inplaceInputDecoratedcancel";

    private static final String INPLACE_INPUT_FIELD_POSTFIX = "tempValue";

    private final static String EVENT_TEST_RESULT_PASSED_TEXT = "Passed";
    
    private static final String INPLACE_INPUT_PAGE = "inplaceInputTest.xhtml";
    
    private static final String DEFAULTLABEL_AND_SPACES_PAGE = "testDefaultLabelAndSpaces.xhtml";
    
    private static final String DEFAULTLABEL_AND_SPACES_PAGE_ID_PREFIX = "_dfas";
    
    private static final String PROCESSING_INVALID_VALUE_PAGE = "testProcessingInvalidValue.xhtml";
    
    private static final String PROCESSING_INVALID_VALUE_ID_PREFIX = "_tpiv";
    
    private final static String INPLACE_INPUT_RESET_METHOD = "#{inplaceInputBean.reset}";

    
    @Test
    public void testInplaceInputComponentLayout(Template template) {
    	setTestUrl(INPLACE_INPUT_PAGE);
        renderPage(template, INPLACE_INPUT_RESET_METHOD);
        writeStatus("Check component layout");

        String inplaceInputS = getParentId() + "_form:" + INPLACE_INPUT_SIMPLE;
        String inplaceInputD = getParentId() + "_form:" + INPLACE_INPUT_DECORATED;

        Assert.assertTrue(isPresent(inplaceInputS));
        int count = selenium.getXpathCount("//*[@id='" + inplaceInputS + "' and (name()='span' or name()='SPAN')]").intValue();
        Assert.assertTrue(count == 1, "InplaceInput[" + inplaceInputS + "] has layout=inline(default) and should be rendered as 'span' element");

        Assert.assertTrue(isPresent(inplaceInputD));
        count = selenium.getXpathCount("//*[@id='" + inplaceInputD + "' and (name()='DIV' or name()='div')]").intValue();
        Assert.assertTrue(count == 1, "InplaceInput [" + inplaceInputD + "] has layout=block and should be rendered as 'div' element");
        
    }

    @Test
    public void testInplaceInputClientAPI(Template template) {
    	setTestUrl(INPLACE_INPUT_PAGE);
        renderPage(template,INPLACE_INPUT_RESET_METHOD);
        writeStatus("Check component client API");
    }

    @Test
    public void testInplaceInputEvents(Template template) {
    	setTestUrl(INPLACE_INPUT_PAGE);
        renderPage(template, INPLACE_INPUT_RESET_METHOD);

        writeStatus("Check component event triggering");

        String inplaceInputS = getParentId() + "_form:" + INPLACE_INPUT_SIMPLE;
        String inplaceInputD = getParentId() + "_form:" + INPLACE_INPUT_DECORATED;
        String inplaceInputDOk = getParentId() + "_form:" + INPLACE_INPUT_DECORATED_OK;

        writeStatus("Click first component being tested");

        clickById(inplaceInputS);
        AssertTextEquals(inplaceInputS + "_edit", EVENT_TEST_RESULT_PASSED_TEXT, "oneditactivated event is not fired");

        writeStatus("Stop editing first component being tested");

        String inplaceInputSInput = inplaceInputS + INPLACE_INPUT_FIELD_POSTFIX;
        selenium.fireEvent(inplaceInputSInput, "blur");

        AssertTextEquals(inplaceInputS + "_view", EVENT_TEST_RESULT_PASSED_TEXT, "onviewactivated event is not fired");

        writeStatus("Double-click second component being tested");

        selenium.doubleClick(inplaceInputD);
        AssertTextEquals(inplaceInputD + "_edit", EVENT_TEST_RESULT_PASSED_TEXT, "oneditactivated event is not fired");
      
        type(inplaceInputSInput, "Hello");

        writeStatus("Stop editing second component being tested");

        selenium.mouseDown(inplaceInputDOk);

        AssertTextEquals(inplaceInputD + "_view", EVENT_TEST_RESULT_PASSED_TEXT, "onviewactivated event is not fired");
    }

    @Test
    public void testInplaceInputKeyAttributes(Template template) {
    	setTestUrl(INPLACE_INPUT_PAGE);
        renderPage(template, INPLACE_INPUT_RESET_METHOD);

        String inplaceInputS = getParentId() + "_form:" + INPLACE_INPUT_SIMPLE;
        String inplaceInputD = getParentId() + "_form:" + INPLACE_INPUT_DECORATED;

        String inplaceInputSInput = inplaceInputS + INPLACE_INPUT_FIELD_POSTFIX;
        String inplaceInputDInput = inplaceInputD + INPLACE_INPUT_FIELD_POSTFIX;

        writeStatus("Check component's key attributes");

        writeStatus("test 'editEvent' attribute");

        writeStatus("The first component must change your state by single clicking only");
        clickById(inplaceInputS);
        AssertVisible(inplaceInputSInput);

        writeStatus("The second component must change your state by double clicking only");
        selenium.doubleClick(inplaceInputD);
        AssertVisible(inplaceInputDInput);
    }

    @Test
    public void testInplaceInputComponentCore(Template template) {
    	setTestUrl(INPLACE_INPUT_PAGE);
        renderPage(template, INPLACE_INPUT_RESET_METHOD);

        String inplaceInputD = getParentId() + "_form:" + INPLACE_INPUT_DECORATED;
        String inplaceInputDInput = inplaceInputD + INPLACE_INPUT_FIELD_POSTFIX;
        String inplaceInputDOk = getParentId() + "_form:" + INPLACE_INPUT_DECORATED_OK;
        String inplaceInputDCancel = getParentId() + "_form:" + INPLACE_INPUT_DECORATED_CANCEL;

        writeStatus("Check component's core functionality");

        writeStatus("Double-click the second component and type an initial text");
        selenium.doubleClick(inplaceInputD);
        type(inplaceInputDInput, "Sun");

        writeStatus("Stop editing with ok. The input has to be saved");
        selenium.mouseDown(inplaceInputDOk);
        String sun = invokeFromComponent(inplaceInputD, "getValue", null);
        Assert.assertEquals(sun, "Sun", "An inputted text has not been saved with ok");
        AssertTextEquals(inplaceInputD, "Sun", "An inputted text has not been saved with ok");

        writeStatus("Double-click the second component again and type a new text");
        selenium.doubleClick(inplaceInputD);
        type(inplaceInputDInput, "Moon");

        writeStatus("Stop editing with cancel. The input has not to be saved");
        selenium.mouseDown(inplaceInputDCancel);

        sun = invokeFromComponent(inplaceInputD, "getValue", null);
        Assert.assertEquals(sun, "Sun", "An inputted text has not to be saved with cancel");
        AssertTextEquals(inplaceInputD, "Sun");

    }

    /**
     * Input some spaces in inplaceInput; verify that defaultLabel is 
     * displayed after selecting; component does not disappear from the page
     * 
     * @param template - current template
     */
    @Test
    public void testDefaultLabelAndSpaces(Template template) {
    	setTestUrl(DEFAULTLABEL_AND_SPACES_PAGE);
    	init(template);
    	
    	String iid = inplaceId + DEFAULTLABEL_AND_SPACES_PAGE_ID_PREFIX;
    	
    	checkMessage(iid, "defaultLabel", 
    			     CommonUtils.getSuccessfulTestMessage(iid), 
    			     CommonUtils.getFailedTestMessage(iid));
    	
    	typeAndCheck(iid, "   ", "defaultLabel");
    }
    
/**
   * Component displays submitted value after 
   * submission of invalid value
   * 
   * @param template - current template
   */
  @Test
  public void testProcessingInvalidValue(Template template) {
  	setTestUrl(PROCESSING_INVALID_VALUE_PAGE);
  	init(template);
  	
  	String iid = inplaceId + PROCESSING_INVALID_VALUE_ID_PREFIX;
  	String newValue = "Aspen";
  	setValue(iid, newValue);
  	clickAjaxCommandAndWait(buttonId + PROCESSING_INVALID_VALUE_ID_PREFIX);
  	String value = getTextById(iid);
  	Assert.assertTrue(value.endsWith(newValue));
  }
    
	@Override
	public void setTestUrl(String testUrl) {
		this.testUrl = "pages/inplaceInput/" + testUrl;
	}
	
	public void initIds() {
		super.initIds();
		inplaceId = formId + "ii";
		inplaceValuePrefix = "value";
		iTempValuePx = "tempValue";
	}
	
	@Override
	protected void renderPage(Template template) {
		renderPage(template,INPLACE_INPUT_RESET_METHOD);
	}
	
	@Override
	public void AssertTextEquals(String locator, String value, String message) {
		String _v = selenium.getText(locator);
		Assert.assertEquals(_v.endsWith(value), true, message);
	}
	
	@Override
	public void AssertTextEquals(String locator, String value) {
		AssertTextEquals(locator, value, null);
	}
}