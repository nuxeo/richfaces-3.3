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

import java.awt.image.renderable.RenderContext;
import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.bean.InplaceInputTestBean;
import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.richfaces.testng.util.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vladimir Molotkov
 *
 */
public abstract class InplacesTest extends SeleniumTestBase {
	
    private static final String REQUIRED_ATTRIBUTES_PAGE = "testRequiredAttributes.xhtml";
    
    private static final String REQUIRED_ATTRIBUTES_ID_PREFIX = "_tra";
    
    private static final String EDITEVENT_ATTRIBUTES_PAGE = "testEditEventAttribute.xhtml";
    
    private static final String EDITEVENT_ATTRIBUTES_ID_PREFIX = "_teea";
    
    private static final String CONTROLS_FACET_PAGE = "testControlsFacet.xhtml";
    
    private static final String CONTROLS_FACET_ID_PREFIX = "_tcf";
    
    private static final String CONTROLS_FACET_BN_OK = "ok";
    
    private static final String CONTROLS_FACET_BN_CANCEL = "cancel";
    
    private static final String SHOW_CONTROLS_ATTRIBUTE1_PAGE = "testShowControlsAttribute1.xhtml";
    
    private static final String SHOW_CONTROLS_ATTRIBUTE1_PAGE_ID_PREFIX = "_tsca1";
    
    private static final String SHOW_CONTROLS_ATTRIBUTE2_PAGE = "testShowControlsAttribute2.xhtml";
    
    private static final String SHOW_CONTROLS_ATTRIBUTE2_PAGE_ID_PREFIX = "_tsca2";
    
    private static final String SHOW_CONTROLS_ATTRIBUTE_BAR = "bar";
    
    private static final String VALIDATOR_PAGE = "testValidator.xhtml";
    
    private static final String VALIDATOR_PAGE_ID_PREFIX = "_tv";
    
    private static final String STANDART_ATTRIBUTES_PAGE = "testStandartAttributes.xhtml";
    
    private static final String STANDART_ATTRIBUTES_ID_PREFIX = "_tsa";
    
    private static final String IMMEDIATE_ATTRIBUTE_PAGE = "testImmediateAttribute.xhtml";
    
    
    private static final String IMMEDIATE_ATTRIBUTE_ID_PREFIX = "_tia";
    
    private static final String VALUE_CHANGE_LISTENER_PAGE = "testValueChangeListener.xhtml";
    
    private static final String VALUE_CHANGE_LISTENER_ID_PREFIX = "_tvcl";
    
    protected static final String JS_API_PAGE = "testJSApi.xhtml";
    
    protected static final String JS_API_ID_PREFIX = "_tja";
    
    private static final String RENDERED_ATTRIBUTE_PAGE = "testRenderedAttribute.xhtml";
    
    private static final String RENDERED_ATTRIBUTE_ID_PREFIX = "_tra";
    
    private static final String BASIC_BEHAVIOUR_PAGE = "testBasicBehaviour.xhtml";
    
    private static final String BASIC_BEHAVIOUR_ID_PREFIX = "_tbb";
    
    protected String testUrl;
    
    protected String formId;
    
    protected String buttonId;
    
    protected String inplaceId;
    
    protected String messageId;
    
    protected String iTempValuePx;
    
    protected String inplaceValuePrefix;
    
    
    /**
     * 'required' and 'requiredMessage' attributes work
     * 
     * @param template - current template
     */
    @Test
    public void testRequiredAttributes(Template template) {
    	setTestUrl(REQUIRED_ATTRIBUTES_PAGE);
    	init(template);
    	
    	selenium.click("id=" + buttonId + REQUIRED_ATTRIBUTES_ID_PREFIX);
    	waitForPageToLoad();
    	checkMessage(messageId + REQUIRED_ATTRIBUTES_ID_PREFIX, "text:requiredMsg", CommonUtils.getSuccessfulTestMessage(inplaceId + REQUIRED_ATTRIBUTES_ID_PREFIX), CommonUtils.getFailedTestMessage(inplaceId + REQUIRED_ATTRIBUTES_ID_PREFIX));
    }
    
    /**
     * Check 'editEvent' attribute
     * 
     * @param template - current template
     */
    @Test
    public void testEditEventAttribute(Template template) {
    	setTestUrl(EDITEVENT_ATTRIBUTES_PAGE);
    	init(template);
    	
    	String iid = inplaceId + EDITEVENT_ATTRIBUTES_ID_PREFIX;
    	selenium.doubleClick(iid);
    	Assert.assertTrue(isVisible(iid + iTempValuePx));
    }

    /**
     * Check 'controls' facet
     * 
     * @param template - current template
     */
    @Test
    public void testControlsFacet(Template template) {
    	setTestUrl(CONTROLS_FACET_PAGE);
    	init(template);
    	
    	check((isPresent(CONTROLS_FACET_BN_OK + CONTROLS_FACET_ID_PREFIX) || isPresent(CONTROLS_FACET_BN_CANCEL + CONTROLS_FACET_ID_PREFIX)),
    		  CommonUtils.getSuccessfulTestMessage(inplaceId + CONTROLS_FACET_ID_PREFIX),
    		  CommonUtils.getFailedTestMessage(inplaceId + CONTROLS_FACET_ID_PREFIX));
    }
    
    /**
     * Verify component behaviour with showControls="false" attribute 
     * 
     * @param template - current template
     */
    @Test
    public void testShowControlsAttribute1(Template template) {
    	setTestUrl(SHOW_CONTROLS_ATTRIBUTE1_PAGE);
    	init(template);
    	
    	String iid = inplaceId + SHOW_CONTROLS_ATTRIBUTE1_PAGE_ID_PREFIX;
    	
    	clickById(iid);
    	Assert.assertEquals(isVisible(iid + SHOW_CONTROLS_ATTRIBUTE_BAR), false, "showControls='false' but controls are visible");
    }
    
    /**
     * The same as previous but with showControls="true" attribute
     * 
     * @param template - current template
     */
    @Test
    public void testShowControlsAttribute2(Template template) {
    	setTestUrl(SHOW_CONTROLS_ATTRIBUTE2_PAGE);
    	init(template);
    	
    	String iid = inplaceId + SHOW_CONTROLS_ATTRIBUTE2_PAGE_ID_PREFIX;
    	
    	clickById(iid);
    	
    	Assert.assertEquals(isVisible(iid + SHOW_CONTROLS_ATTRIBUTE_BAR), true, "showControls='true' but controls are not visible");
    }
    
    /**
     * Validator defined by component attribute and nested tags work 
     * 
     * @param template - current template
     */
    @Test
    public void testValidator1(Template template) {
    	setTestUrl(VALIDATOR_PAGE);
    	init(template);
    	
    	String iid = inplaceId + VALIDATOR_PAGE_ID_PREFIX;
    	
    	clickById(buttonId + VALIDATOR_PAGE_ID_PREFIX + "1");
    	waitForAjaxCompletion();
    	check("".equals(getTextById(messageId + VALIDATOR_PAGE_ID_PREFIX + "1")), 
    					   CommonUtils.getSuccessfulTestMessage(iid + "1"), 
    					   CommonUtils.getFailedTestMessage(iid + "1"));
    	
    	clickById(buttonId + VALIDATOR_PAGE_ID_PREFIX + "2");
    	waitForAjaxCompletion();
    	check("".equals(getTextById(messageId + VALIDATOR_PAGE_ID_PREFIX + "2")), 
    					   CommonUtils.getSuccessfulTestMessage(iid + "2"), 
    					   CommonUtils.getFailedTestMessage(iid + "2"));
    }
    
    /**
     * Validator defined by component attribute and nested tags work 
     * 
     * @param template - current template
     */
    @Test
    public void testValidator2(Template template) {
    	setTestUrl(VALIDATOR_PAGE);
    	init(template);
    	
    	String iid = inplaceId + VALIDATOR_PAGE_ID_PREFIX; 
    	
    	setValue(iid + "1", "Pine");
    	setValue(iid + "2", "Pine");
    	
    	clickById(buttonId + VALIDATOR_PAGE_ID_PREFIX + "1");
    	waitForAjaxCompletion();
    	check("Value isn't correct!".equals(getTextById(messageId + VALIDATOR_PAGE_ID_PREFIX + "1")), 
    					   CommonUtils.getSuccessfulTestMessage(iid + "1"), 
    					   CommonUtils.getFailedTestMessage(iid + "1"));

    	setValue(iid + "1", "Pine");
    	setValue(iid + "2", "Pine");

    	clickById(buttonId + VALIDATOR_PAGE_ID_PREFIX + "2");
    	waitForAjaxCompletion();
    	check("Value isn't correct!".equals(getTextById(messageId + VALIDATOR_PAGE_ID_PREFIX + "2")), 
    					   CommonUtils.getSuccessfulTestMessage(iid + "2"), 
    					   CommonUtils.getFailedTestMessage(iid + "2"));
    }
    
    /**
     * Immediate = true component works respectively 
     *   
     * @param template - current template
     */
    @Test
    public void testImmediateAttribute(Template template) {
    	setTestUrl(IMMEDIATE_ATTRIBUTE_PAGE);
    	init(template);
    	
    	String iid = inplaceId + IMMEDIATE_ATTRIBUTE_ID_PREFIX; 
    	
    	setValueById(iid + inplaceValuePrefix, "Aspen");
    	clickById(buttonId + IMMEDIATE_ATTRIBUTE_ID_PREFIX);
    	waitForAjaxCompletion();
    	check("Value isn't correct!".equals(getTextById(messageId + IMMEDIATE_ATTRIBUTE_ID_PREFIX)), 
    					   CommonUtils.getSuccessfulTestMessage(iid), 
    					   CommonUtils.getFailedTestMessage(iid));
    }
    
    /**
     * valueChangeListener should fire on submit 
     * and model binding should be updated on value changed  
     *   
     * @param template - current template
     */
    @Test
    public void testValueChangeListener(Template template) {
    	setTestUrl(VALUE_CHANGE_LISTENER_PAGE);
    	init(template);
    	
    	String iid = inplaceId + VALUE_CHANGE_LISTENER_ID_PREFIX; 
    	
    	clickById(iid);
    	setValue(iid, "Aspen");
    	clickById(buttonId + VALUE_CHANGE_LISTENER_ID_PREFIX);
    	waitForAjaxCompletion();
    	check(("Aspen" + InplaceInputTestBean.Messages.VALUECHANGELISTENER_CALLED).equals(getTextById(messageId + VALUE_CHANGE_LISTENER_ID_PREFIX)), 
    					   CommonUtils.getSuccessfulTestMessage(iid), 
    					   CommonUtils.getFailedTestMessage(iid));
    }
    
    
    /**
     * style and classes, standard HTML attributes are output to client 
     * 
     * @param template - current template
     */
    @Test
    public void testStandartAttributes(Template template) {
    	setTestUrl(STANDART_ATTRIBUTES_PAGE);
    	init(template);
    	
    	String iid = inplaceId + STANDART_ATTRIBUTES_ID_PREFIX;
		
		List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
		events.add(SeleniumEvent.ONMOUSEMOVE);
		events.add(SeleniumEvent.ONMOUSEOUT);
		events.add(SeleniumEvent.ONMOUSEOVER);
		events.add(SeleniumEvent.ONCLICK);
		events.add(SeleniumEvent.ONDBLCLICK);
		events.add(SeleniumEvent.ONKEYDOWN);
		events.add(SeleniumEvent.ONKEYPRESS);
		events.add(SeleniumEvent.ONKEYUP);
		events.add(SeleniumEvent.ONMOUSEUP);
		
        assertClassNames(iid,new String [] {"classForInplaceinput"}, "Component's rendering invalid", true);
        String color = getStyleAttributeString(iid, "color");
    	check(("rgb(255, 0, 0)".equals(color)) || ("red".equals(color)), 
    		   CommonUtils.getSuccessfulTestMessage(iid), 
    		   CommonUtils.getFailedTestMessage(iid));
		
        assertEvents(iid, events);
    }
    
    /**
     * Verify JS API of the component 
     * 
     * @param template - current template
     */
    @Test
    public void testJSApi(Template template) {
    	setTestUrl(JS_API_PAGE);
    	init(template);
  	
    	String iid = inplaceId + JS_API_ID_PREFIX;
        Assert.assertTrue("test".equals(getValue(iid)));
        
        setValue(iid, "999");
        Assert.assertTrue("999".equals(getValue(iid)));
        
        setValue(iid, "Aspen");
    	Assert.assertEquals("Aspen", getValue(iid), CommonUtils.getFailedTestMessage(iid));

    	edit(iid);
    	type(iid + iTempValuePx, "Test");
    	cancel(iid);
    	
    	Assert.assertEquals("Aspen", getValue(iid), CommonUtils.getFailedTestMessage(iid));
    }
    
//    protected void testJSApiSetValue() {
//    	selenium.getEval("window.inplace.setValue(\"\", {value: \"JSApi\"})");
//        Assert.assertTrue("JSApi".equals(selenium.getEval("window.inplace.getValue()")));
//    }
    
    /**
     * Component is present on the page and shows model value 
     * 
     * @param template - current temlate
     */
    @Test
    public void testBasicBehaviour(Template template) {
    	setTestUrl(BASIC_BEHAVIOUR_PAGE);
    	init(template);
    	
    	AssertValueEquals(inplaceId + BASIC_BEHAVIOUR_ID_PREFIX + inplaceValuePrefix, "test");
    }
    
    /**
     * Component with rendered = false is not present on the page 
     * 
     * @param template - current template
     */
    @Test
    public void testRenderedAttribute(Template template) {
    	setTestUrl(RENDERED_ATTRIBUTE_PAGE);
    	init(template);
    	
    	AssertNotRendered(inplaceId + RENDERED_ATTRIBUTE_ID_PREFIX);
    }
    
    protected void typeAndCheck(String iid, String word, String expectedWord) {
    	typeWord(iid, word);
    	checkMessage(iid, expectedWord, 
			     CommonUtils.getSuccessfulTestMessage(iid), 
			     CommonUtils.getFailedTestMessage(iid));
    }
    
    protected void typeWord(String iid, String word) {
    	clickById(iid);
    	type(iid + "tempValue", word);
    	selenium.fireEvent(iid + "tempValue", "blur");
    }
    
    protected void checkMessage(String elementId, String expectedValue, String okMsg, String errMsg) {
    	String currentValue = getTextById(elementId);
    	check(currentValue.indexOf(expectedValue) != -1, okMsg, errMsg); //firefox returns '// + currentVlaue'
    }
    
    private void check(boolean expression, String okMsg, String errMsg) {
    	if (expression) {
	        writeStatus(okMsg);
    	} else {
    		writeStatus(errMsg, true);
	        Assert.fail(errMsg);
    	}
    }
    
    protected void init(Template template) {
    	renderPage(template);
    	initIds();
    }
    
    public void initIds() {
    	formId = getParentId() + "_form:";
    	buttonId = formId + "bn";
    	messageId = "text";
    }
        
    @Override
    public String getTestUrl() {
        return testUrl;
    }
    
    public abstract void setTestUrl(String testUrl);
    
    protected void setValue(String id, String newValue) {
		selenium.runScript("$('" + id + "').component.setValue('" +  newValue + "')");
	}
	
	protected String getValue(String id) {
		return runScript("$('" + id + "').component.getValue()");
	}
	
	protected void edit(String id) {
		runScript("$('" + id + "').component.edit()");
	}
	
	protected void cancel(String id) {
		runScript("$('" + id + "').component.cancel()");
	}
    
}
