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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.ajax4jsf.validators.ValidatorWithAttribute;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderingListTest extends SeleniumTestBase {
	
	private String initMethod = "#{orderingListBean.init}";
	
	private String inputTextId;
	
	private String requiredInputId;
	
	private String orderingListId;
	
	private String orderingListTableId;
	
	private String valueChangedId;
	
	private String tableId;
	
	private String submitId;

    private String firstButton;

    private String firstButtonDisabled;

    private String upButton;

    private String upButtonDisabled;

    private String downButton;

    private String downButtonDisabled;

    private String lastButton;

    private String lastButtonDisabled;

    private String firstRow;

    private String actionResultText;

    private String ajax;

    private String server;

    private String thirdRow;

    private String selectionText;

    private String activeItemText;
    
    private String messagesId;

    private String orderControlsVisibleId;

    private String showButtonLabelsId;

    private String renderedId;

    private String immediateId;
    
    private String attrValidatorId;

    private String tagValidatorId;

	/**
     *  validator defined by component attribute and nested tags work
     */
    @Test
    public void testValidators(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.getText(messagesId).length() == 0, "Message mustn't be rendered.");
        selenium.click(attrValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertEquals(selenium.getText(messagesId), "attrValidator");
        selenium.click(attrValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertTrue(selenium.getText(messagesId).length() == 0, "Message mustn't be rendered.");
        selenium.click(tagValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertEquals(selenium.getText(messagesId), ValidatorWithAttribute.MESSAGE);
     }

    /**
     *  style and classes, standard HTML attributes are output to client
     */
	@Test
	public void testHTMLAttributes(Template template) {
        renderPage(template, initMethod);
        initFields();
		Map<String, String> styleAttributes = new HashMap<String, String>();
		styleAttributes.put("color", "blue");
		styleAttributes.put("text-decoration", "underline");
		
		List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
		events.add(SeleniumEvent.ONCLICK);
		events.add(SeleniumEvent.ONDBLCLICK);
		events.add(SeleniumEvent.ONMOUSEMOVE);
		events.add(SeleniumEvent.ONMOUSEOUT);
		events.add(SeleniumEvent.ONMOUSEOVER);
		
        assertClassNames(orderingListId,new String [] {"noname"}, "Component's rendering invalid", true);
        assertStyleAttributes(orderingListId, styleAttributes);
		
        assertEvents(orderingListId, events);
	}

	/**
     *  respective number of columns created; header dimensions are ok
     */
    @Test
    public void testNumberOfColumnsAndHeaderDimensions(Template template) {
        renderPage(template, initMethod);
        initFields();
        String tdXpath = "id('" + orderingListId +"tbody')/tr[1]/td";
        String thXpath = "id('" + orderingListId +"internal_header_tab')/thead/tr/th";
		Assert.assertEquals(selenium.getXpathCount(tdXpath).intValue(), 3);
        for (int i = 1; i <= 3; i++) {
        	Assert.assertEquals(selenium.getElementWidth("xpath=" + tdXpath + "[" + i + "]").intValue(), selenium.getElementWidth("xpath=" + thXpath + "[" + i + "]").intValue());
		}
   }

	/**
     *  value updated, listener fire using multiple selection with external validation failure after form submission
     */
    @Test
    public void testValidationFailure(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertEquals(selenium.getText(valueChangedId), Boolean.FALSE.toString());
        _checkOrdering(tableId, new String[]{"0", "1", "2", "3"});
        Assert.assertTrue(selenium.getText(selectionText).length() == 0);
        _selectItem(orderingListId + ":0", false, false);
    	selenium.click(downButton);
        _selectItem(orderingListId + ":1", false, false);
       	_selectItem(orderingListId + ":2", false, true);
        _selectItem(orderingListId + ":0", true, false);
        selenium.type(requiredInputId, "");
        clickAjaxCommandAndWait(submitId);
        Assert.assertEquals(selenium.getText(valueChangedId), Boolean.FALSE.toString());
        _checkOrdering(tableId, new String[]{"0", "1", "2", "3"});
        Assert.assertTrue(selenium.getText(selectionText).length() == 0);
    }

    /**
     *  value updated, listener fire using multiple selection
     */
    @Test
    public void testValuesAndListener(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertEquals(selenium.getText(valueChangedId), Boolean.FALSE.toString());
        _checkOrdering(tableId, new String[]{"0", "1", "2", "3"});
        Assert.assertTrue(selenium.getText(selectionText).length() == 0);
        _selectItem(orderingListId + ":0", false, false);
    	selenium.click(downButton);
        _selectItem(orderingListId + ":1", false, false);
       	_selectItem(orderingListId + ":2", false, true);
        _selectItem(orderingListId + ":0", true, false);
        clickAjaxCommandAndWait(submitId);
        Assert.assertEquals(selenium.getText(valueChangedId), Boolean.TRUE.toString());
        _checkOrdering(tableId, new String[]{"1", "0", "2", "3"});
        Assert.assertTrue(selenium.getText(selectionText).split(",").length == 2);
    }

    /**
     *  converter defined by component attribute and configured at application level works
     */
    @Test
    public void testConverter(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.getValue(orderingListId + ":0StateInput").endsWith("0:item0"), "Converter doesn't work.");
    }

    /**
     *  keyboard navigation works for component
     */
	@Test
	public void testKeyboardNavigation(Template template) {
        renderPage(template, initMethod);
        initFields();
        _checkOrdering(orderingListTableId, new String[]{"0", "1", "2", "3"});
        _selectItem(orderingListId + ":0", false, false);
       	_selectItem(orderingListId + ":2", true, false);
    	selenium.click(downButton);
        _checkOrdering(orderingListTableId, new String[]{"1", "0", "3", "2"});
        _selectItem(orderingListId + ":1", false, false);
       	_selectItem(orderingListId + ":3", false, true);
    	selenium.click(downButton);
        _checkOrdering(orderingListTableId, new String[]{"2", "1", "0", "3"});
        _selectItem(orderingListId + ":0", false, false);
        selenium.controlKeyDown();
        selenium.keyDown(orderingListId + "focusKeeper", "A");
        selenium.controlKeyUp();
        clickAjaxCommandAndWait(submitId);
        Assert.assertTrue(selenium.getText(selectionText).split(",").length == 4);
        _selectItem(orderingListId + ":1", false, false);
        selenium.keyDown(orderingListId + "focusKeeper", "\\40");
    	selenium.click(upButton);
        _checkOrdering(orderingListTableId, new String[]{"2", "0", "1", "3"});  
        _selectItem(orderingListId + ":3", false, false);
        selenium.keyDown(orderingListId + "focusKeeper", "\\38");
    	selenium.click(downButton);
        _checkOrdering(orderingListTableId, new String[]{"2", "0", "3", "1"});  
    	_selectItem(orderingListId + ":1", false, false);
        selenium.keyDown(orderingListId + "focusKeeper", "\\33");
        _checkOrdering(orderingListTableId, new String[]{"1", "2", "0", "3"});
        selenium.keyDown(orderingListId + "focusKeeper", "\\34");
        _checkOrdering(orderingListTableId, new String[]{"2", "0", "3", "1"});
    	_selectItem(orderingListId + ":1", false, false);
        selenium.controlKeyDown();
        selenium.keyDown(orderingListId + "focusKeeper", "\\38");
        selenium.controlKeyUp();
        _checkOrdering(orderingListTableId, new String[]{"2", "0", "1", "3"});
    	_selectItem(orderingListId + ":1", false, false);
        selenium.controlKeyDown();
        selenium.keyDown(orderingListId + "focusKeeper", "\\40");
        selenium.controlKeyUp();
        _checkOrdering(orderingListTableId, new String[]{"2", "0", "3", "1"});        
	}

	/**
     *  immediate = true component works respectively
     */
    @Test
    public void testImmediate(Template template) {
        renderPage(template, initMethod);
        initFields();
        selenium.type(inputTextId, "");
        clickAjaxCommandAndWait(getParentId() + "testRequiredAndImmediate:submit");       
        Assert.assertEquals(selenium.getText(messagesId), "Input is required");
        clickAjaxCommandAndWait(immediateId);
        clickAjaxCommandAndWait(getParentId() + "testRequiredAndImmediate:submit");
       Assert.assertFalse("Input is required".equals(selenium.getText(messagesId)));
    }

    /**
     *  component with rendered = false is not present on the page
     */
    @Test
    public void testRendered(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.isElementPresent(orderingListId));
        clickAjaxCommandAndWait(renderedId);
        clickAjaxCommandAndWait(submitId);
        Assert.assertFalse(selenium.isElementPresent(orderingListId));
    }

	/**
     *  component is present on the page with data according to value attribute
     */
    @Test
    public void testValue(Template template) {
        renderPage(template, initMethod);
        initFields();
        int count = selenium.getXpathCount("id('" + orderingListId +"tbody')/tr").intValue();
        for (int i = 0; i < count; i++) {
			Assert.assertEquals(selenium.getText("xpath=id('" + orderingListId +"tbody')/tr[" + (i + 1) + "]/td[1]"), "item" + i);
		}
      }

	/**
     *  check internationalization
     */
    @Test
    public void testInternationalization(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertEquals(selenium.getText(firstButtonDisabled), "Move to top");
     }

    /**
     *  Check control facets
     */
    @Test
    public void testControlFacets(Template template) {
        renderPage(template, initMethod);
        initFields();
        String orderingListId = getParentId() + "testControlFacets";
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":topControlFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":topControlDisabledFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":bottomControlFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":bottomControlDisabledFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":upControlFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":upControlDisabledFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":downControlFacet"));
        Assert.assertTrue(selenium.isElementPresent(orderingListId + ":downControlDisabledFacet"));
     }

    /**
     *  JS API is present and works
     */
    @Test
    public void testJSAPI(Template template) {
        renderPage(template, initMethod);
        initFields();
        selenium.runScript("var listShuttle = ($('" + orderingListId + "')).component;");
        checkJSError();
        Assert.assertTrue("4".equals(selenium.getEval("window.listShuttle.getItems().length")));
        checkJSError();
        Assert.assertTrue("0".equals(selenium.getEval("window.listShuttle.getSelection().length")));
        checkJSError();
        _checkOrdering(orderingListTableId, new String[]{"0", "1", "2", "3"});
        selenium.click(firstRow);
        Assert.assertTrue("1".equals(selenium.getEval("window.listShuttle.getSelection().length")));
        selenium.runScript("listShuttle.down()");
        checkJSError();
        _checkOrdering(orderingListTableId, new String[]{"1", "0", "2", "3"});
        selenium.runScript("listShuttle.bottom()");
        checkJSError();
        _checkOrdering(orderingListTableId, new String[]{"1", "2", "3", "0"});
        selenium.runScript("listShuttle.up()");
        checkJSError();
        _checkOrdering(orderingListTableId, new String[]{"1", "2", "0", "3"});
        selenium.runScript("listShuttle.top()");
        checkJSError();
        _checkOrdering(orderingListTableId, new String[]{"0", "1", "2", "3"});
        
        
   }

    /**
     * Check 'fastOrderControlsVisible' and 'orderControlsVisible' attributes
     */
    @Test
    public void testOrderControlsVisible(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.isElementPresent(firstButtonDisabled));
        Assert.assertTrue(selenium.isElementPresent(upButtonDisabled));
        Assert.assertTrue(selenium.isElementPresent(downButtonDisabled));
        Assert.assertTrue(selenium.isElementPresent(lastButtonDisabled));
        clickAjaxCommandAndWait(orderControlsVisibleId);
        clickAjaxCommandAndWait(submitId);
        Assert.assertFalse(selenium.isElementPresent(firstButtonDisabled));
        Assert.assertFalse(selenium.isElementPresent(upButtonDisabled));
        Assert.assertFalse(selenium.isElementPresent(downButtonDisabled));
        Assert.assertFalse(selenium.isElementPresent(lastButtonDisabled));
    }

    /**
     * Check Check 'showButtonsLabel' attribute
     */
    @Test
    public void testShowButtonsLabel(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.getText(firstButtonDisabled).length()!=0);
        Assert.assertTrue(selenium.getText(upButtonDisabled).length()!=0);
        Assert.assertTrue(selenium.getText(downButtonDisabled).length()!=0);
        Assert.assertTrue(selenium.getText(lastButtonDisabled).length()!=0);
        clickAjaxCommandAndWait(showButtonLabelsId);
        clickAjaxCommandAndWait(submitId);
        Assert.assertFalse(selenium.getText(firstButtonDisabled).length()!=0);
        Assert.assertFalse(selenium.getText(upButtonDisabled).length()!=0);
        Assert.assertFalse(selenium.getText(downButtonDisabled).length()!=0);
        Assert.assertFalse(selenium.getText(lastButtonDisabled).length()!=0);
    }

    /**
     * 'required' and 'requiredMessage' attributes work
     */
    @Test
    public void testRequired(Template template) {
    	renderPage(template, initMethod);
        initFields();
        Assert.assertTrue(selenium.getText(messagesId).length() == 0);
        clickAjaxCommandAndWait(getParentId() + "testRequiredAndImmediate:submit");
        Assert.assertFalse(selenium.getText(messagesId).length() == 0);    	
    }

    /**
     * Check "caption" facet
     */
    @Test
    public void testCaptionFacet(Template template) {
        renderPage(template, initMethod);
        initFields();
        Assert.assertEquals(selenium.getText("xpath=id('" + orderingListId + "table')/tbody/tr[1]"), "Caption Faset");    	
    }

    @Test
    public void testButtons(Template template) {
    	renderPage(template, initMethod);
        initFields();

        writeStatus("Check if all buttons are disabled first time");
        checkButtons(true, true, true, true);

        writeStatus("Click on first row");
        fireMouseEvent(firstRow, "click", 0, 0, false);
        checkButtons(true, true, false, false);

        writeStatus("Click on down button");
        Assert.assertEquals(selenium.getElementIndex("id=" + firstRow), 0);
        clickById(downButton);
        checkButtons(false, false, false, false);
        Assert.assertEquals(selenium.getElementIndex("id=" + firstRow), 1);

        writeStatus("Click on last button");
        clickById(lastButton);
        checkButtons(false, false, true, true);
        Assert.assertEquals(selenium.getElementIndex("id=" + firstRow), 3);

        writeStatus("Click on first button");
        clickById(firstButton);
        checkButtons(true, true, false, false);
        Assert.assertEquals(selenium.getElementIndex("id=" + firstRow), 0);
    }

    @Test
    public void testActions(Template template) {
        renderPage(template, initMethod);
        initFields();
        
        writeStatus("Select two rows");
        
        fireMouseEvent(firstRow, "click", 0, 0, false);
        fireMouseEvent(thirdRow, "click", 0, 0, true);
        checkButtons(true, true, false, false);

        writeStatus("Click on ajax button");
        clickById(ajax);
        waitForAjaxCompletion();
        AssertTextEquals(actionResultText, "item0");
        String selectedItems = getTextById(selectionText); 
        if (!selectedItems.contains("item2")) {
        	Assert.fail("The third item was not selected");
        }
        if (!selectedItems.contains("item0")) {
        	Assert.fail("The first item was not selected");
        }

        //AssertTextEquals(activeItemText, "Item [item0]");

        writeStatus("Select one row");
        fireMouseEvent(firstRow, "click", 0, 0, false);
        writeStatus("Click on server link");
        clickCommandAndWait(server);
        AssertTextEquals(actionResultText, "item1");
        AssertTextEquals(selectionText, "item0");
        //AssertTextEquals(activeItemText, "Item [item0]");
        checkButtons(true, true, false, false);
    }

    @Test
    public void testJSFunctions(Template template) {
        renderPage(template, initMethod);
        initFields();

        writeStatus("Select one row");
        fireMouseEvent(thirdRow, "click", 0, 0, false);

        writeStatus("Check if 'onupclick' event works");
        clickById(upButton);
        Assert.assertEquals(runScript("theLatestEvent"), "up orderchanged");

        writeStatus("Check if 'ondownclick' event works");
        clickById(downButton);
        Assert.assertEquals(runScript("theLatestEvent"), "down orderchanged");

        writeStatus("Check if 'ontopclick' event works");
        clickById(firstButton);
        Assert.assertEquals(runScript("theLatestEvent"), "top orderchanged");

        writeStatus("Check if 'onbottomclick' event works");
        clickById(lastButton);
        Assert.assertEquals(runScript("theLatestEvent"), "bottom orderchanged");
    }

    private void checkButtons(boolean firstDisabled, boolean upDisabled, boolean downDisabled, boolean lastDisabled) {
        if (firstDisabled) {
            Assert.assertTrue(isVisibleById(firstButtonDisabled));
            Assert.assertFalse(isVisibleById(firstButton));
        } else {
            Assert.assertFalse(isVisibleById(firstButtonDisabled));
            Assert.assertTrue(isVisibleById(firstButton));
        }

        if (upDisabled) {
            Assert.assertTrue(isVisibleById(upButtonDisabled));
            Assert.assertFalse(isVisibleById(upButton));
        } else {
            Assert.assertFalse(isVisibleById(upButtonDisabled));
            Assert.assertTrue(isVisibleById(upButton));
        }

        if (downDisabled) {
            Assert.assertTrue(isVisibleById(downButtonDisabled));
            Assert.assertFalse(isVisibleById(downButton));
        } else {
            Assert.assertFalse(isVisibleById(downButtonDisabled));
            Assert.assertTrue(isVisibleById(downButton));
        }

        if (lastDisabled) {
            Assert.assertTrue(isVisibleById(lastButtonDisabled));
            Assert.assertFalse(isVisibleById(lastButton));
        } else {
            Assert.assertFalse(isVisibleById(lastButtonDisabled));
            Assert.assertTrue(isVisibleById(lastButton));
        }
    }

    private void _checkOrdering(String tableId, String[] ordering) {
    	for (int i = 0; i < ordering.length; i++) {
			Assert.assertEquals(selenium.
    				getText("xpath=id('" + tableId + "')/tbody/tr[" + (i + 1) + "]/td[1]"),
    				"item" + ordering[i]);
		}
    	
    }

    private void _selectItem(String itemId, boolean ctrl, boolean shift) {
        writeStatus("Select item id:  " + itemId);
        try {
	        if (ctrl) {
				selenium.controlKeyDown();
			}
	        if (shift) {
				selenium.shiftKeyDown();
			}
			selenium.click(itemId);
	        if (ctrl) {
	        	selenium.controlKeyUp();
	        }
	        if (shift) {
	        	selenium.shiftKeyUp();
	        }
        } catch (Exception e) {
            writeStatus("Selection item id: " + itemId + " failed.");
            Assert.fail("No item was found. Item id: " + itemId + e);
        }
    }

    private void initFields() {
        String formId = getParentId() + "_form:";
        String attrFormId = getParentId() + "attrFormId";
        inputTextId = getParentId() + "testRequiredAndImmediate:inputTextId";
        requiredInputId = formId + "requiredInputId";
		orderingListId = formId + "orderingList";
		orderingListTableId = orderingListId + "internal_tab";
		valueChangedId = formId + "valueChangedId";
		tableId = formId + "tableId";
		submitId = formId + "submitId";
        firstButton = orderingListId + "first";
        firstButtonDisabled = orderingListId + "disfirst";

        upButton = orderingListId + "up";
        upButtonDisabled = orderingListId + "disup";

        downButton = orderingListId + "down";
        downButtonDisabled = orderingListId + "disdown";

        lastButton = orderingListId + "last";
        lastButtonDisabled = orderingListId + "dislast";

        firstRow = orderingListId + ":0";
        thirdRow = orderingListId + ":2";

        actionResultText = formId + "actionResult";
        selectionText = formId + "selection";
        activeItemText = formId + "activeItem";
        ajax = firstRow + ":_ajax";
        server = orderingListId + ":1:_server";
        messagesId = formId + "messages";
        orderControlsVisibleId = attrFormId + ":orderControlsVisibleId";
        showButtonLabelsId = attrFormId + ":showButtonLabelsId";
        renderedId = attrFormId + ":renderedId";
        immediateId = attrFormId + ":immediateId";
        attrValidatorId = attrFormId + ":attrValidatorId";
        tagValidatorId = attrFormId + ":tagValidatorId";
    }

    public String getTestUrl() {
        return "pages/orderingList/orderingListTest.xhtml";
    }

}
