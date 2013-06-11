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

/**
 * @author Andrey Markavstov
 * 
 */
public class ListShuttleTest extends SeleniumTestBase {

	private String initMethod = "#{listShuttle.init}";
	
    String parentId;

    String inputTextId;

    String lsId;

    String sourceTableId;

    String targetTableId;

    String sourceSelectionTableId;

    String targetSelectionTableId;

    String availebleListId;

    String targetListId;

    String copyAllId;

    String copyId;

    String removeId;

    String removeAllId;

    String firstId;

    String upId;

    String downId;

    String lastId;

    String submitId;

    String resetId;

    String hideId;

    String msgId;
    
    String attrFormId;
    
    String sourceRequiredId;

    String targetRequiredId;

    String showButtonLabelsId;

    String switchByClickId;

    String renderedId;

    String immediateId;

    String attrValidatorId;
    
    String tagValidatorId;
    
    String renderControlFacet;
    
    private void init(Template template) {
        renderPage(template, initMethod);
        parentId = getParentId() + "_form:";
        inputTextId = parentId + "inputTextId";
        lsId = parentId + "ls";
        sourceTableId = parentId + "sourceTableId";
        targetTableId = parentId + "targetTableId";
        sourceSelectionTableId = parentId + "sourceSelectionTableId";
        targetSelectionTableId = parentId + "targetSelectionTableId";
        availebleListId = parentId + "lstbody";
        targetListId = parentId + "lstlTbody";
        copyAllId = parentId + "lscopyAll";
        copyId = parentId + "lscopy";
        removeId = parentId + "lsremove";
        removeAllId = parentId + "lsremoveAll";
        firstId = parentId + "lsfirst";
        upId = parentId + "lsup";
        downId = parentId + "lsdown";
        lastId = parentId + "lslast";
        submitId = parentId + "submit";
        resetId = parentId + "reset";
        hideId = parentId + "hide";
        msgId = getParentId() + "msgId";
        attrFormId = getParentId() + "attrFormId";
        sourceRequiredId = attrFormId + ":sourceRequiredId";
        targetRequiredId = attrFormId + ":targetRequiredId";
        showButtonLabelsId = attrFormId + ":showButtonLabelsId";
        switchByClickId = attrFormId + ":switchByClickId";
        renderedId = attrFormId + ":renderedId";
        immediateId = attrFormId + ":immediateId";
        attrValidatorId = attrFormId + ":attrValidatorId";
        tagValidatorId = attrFormId + ":tagValidatorId";
        renderControlFacet = attrFormId + ":renderControlFacet";
    }

	/**
     *  Respective number of columns, header dimensions
     */
    @Test
    public void testNumberOfColumnsAndHeaderDimensions(Template template) {
        init(template);
    	_selectItem(parentId + "ls:0", true, false);
    	clickById(copyId);    	
        clickAjaxCommandAndWait(submitId);
        String tdXpath = "id('" + lsId +"tbody')/tr[1]/td";
        String thXpath = "id('" + lsId +"internal_header_tab')/thead/tr/th";
		Assert.assertEquals(selenium.getXpathCount(tdXpath).intValue(), 2);
        for (int i = 1; i <= 2; i++) {
        	Assert.assertEquals(selenium.getElementWidth("xpath=" + tdXpath + "[" + i + "]").intValue(), selenium.getElementWidth("xpath=" + thXpath + "[" + i + "]").intValue());
		}
        tdXpath = "id('" + lsId +"tlTbody')/tr[1]/td";
        thXpath = "id('" + lsId +"tlInternal_header_tab')/thead/tr/th";
		Assert.assertEquals(selenium.getXpathCount(thXpath).intValue(), 2);
        for (int i = 1; i <= 2; i++) {
        	Assert.assertEquals(selenium.getElementWidth("xpath=" + tdXpath + "[" + i + "]").intValue(), selenium.getElementWidth("xpath=" + thXpath + "[" + i + "]").intValue());
		}
   }

    /**
     *  Check control facets
     */
    @Test
    public void testControlFacets(Template template) {
        init(template);
        clickAjaxCommandAndWait(renderControlFacet);
        _selectItem(parentId + "ls:1");
        _checkControlFacet("copyAll", true);
        _checkControlFacet("copy", true);
        _checkControlFacet("removeAll", false);
        _checkControlFacet("remove", false);
        _checkControlFacet("top", false);
        _checkControlFacet("bottom", false);
        _checkControlFacet("up", false);
        _checkControlFacet("down", false);
        selenium.click(lsId + ":copyAllControlFacet");
        _selectItem(parentId + "ls:1");
        _checkControlFacet("copyAll", false);
        _checkControlFacet("copy", false);
        _checkControlFacet("removeAll", true);
        _checkControlFacet("remove", true);
        _checkControlFacet("top", true);
        _checkControlFacet("bottom", true);
        _checkControlFacet("up", true);
        _checkControlFacet("down", true);
     }

    private void _checkControlFacet(String facet, boolean enabled) {
        Assert.assertEquals(selenium.isVisible(lsId + ":" + facet + "ControlFacet"), enabled);
        Assert.assertEquals(selenium.isVisible(lsId + ":" + facet + "ControlDisabledFacet"), !enabled);
	}
    
    /**
     *  check internationalization
     */
    @Test
    public void testInternationalization(Template template) {
        init(template);
        Assert.assertEquals(selenium.getText(copyAllId), "Copy All Items");
     }

	/**
     *  validator defined by component attribute and nested tags work
     */
    @Test
    public void testValidators(Template template) {
        init(template);
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
        selenium.click(attrValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertEquals(selenium.getText(msgId), "attrValidator");
        selenium.click(attrValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
        selenium.click(tagValidatorId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);             
        Assert.assertEquals(selenium.getText(msgId), ValidatorWithAttribute.MESSAGE);
     }

	/**
     *  sourceValue/targetValue not updated, listeners not call using multiple selection with external validation failure after form submission
     */
    @Test
    public void testValidationFailure(Template template) {
        init(template);
        Assert.assertEquals(getValueById(inputTextId), "something", "Value of inputText(id='inputTextId') must equal initial value('something').");
    	_assertTableRowsCount(sourceTableId, 5);
    	_assertTableRowsCount(targetTableId, 0);
    	_assertTableRowsCount(sourceSelectionTableId, 0);
    	_assertTableRowsCount(targetSelectionTableId, 0);

    	_selectItem(parentId + "ls:0", true, false);
    	_selectItem(parentId + "ls:2", true, false);
    	
    	clickById(copyId);
    	
    	_selectItem(parentId + "ls:1", true, false);
    	_selectItem(parentId + "ls:3", true, false);
    	_selectItem(parentId + "ls:0", true, false);
    	
    	selenium.type("id=" + inputTextId, "");
        clickAjaxCommandAndWait(submitId);
    	
        Assert.assertEquals(getValueById(inputTextId), "", "Value of inputText(id='inputTextId') must equal changed value('').");
    	_assertTableRowsCount(sourceTableId, 5);
    	_assertTableRowsCount(targetTableId, 0);
    	_assertTableRowsCount(sourceSelectionTableId, 0);
    	_assertTableRowsCount(targetSelectionTableId, 0);
    }

    /**
     *  keyboard navigation works for component
     */
	@Test
	public void testKeyboardNavigation(Template template) {
        init(template);
    	_selectItem(parentId + "ls:0", true, false);
    	_selectItem(parentId + "ls:1", true, false);
    	_selectItem(parentId + "ls:2", true, false);
    	_selectItem(parentId + "ls:4", true, false);
    	_selectItem(parentId + "ls:1", true, false);
    	clickById(copyId);
    	_assertTableRowsCount(targetListId, 3);
        clickById(removeAllId);

    	_selectItem(parentId + "ls:1", false, false);
    	_selectItem(parentId + "ls:0", false, true);
    	clickById(copyId);
    	_assertTableRowsCount(targetListId, 3);
        clickById(removeAllId);
        
    	_selectItem(parentId + "ls:0", false, false);
        selenium.controlKeyDown();
        selenium.keyDown("id=" + lsId + "focusKeeper", "A");
        selenium.controlKeyUp();
    	clickById(copyId);
    	_assertTableRowsCount(targetListId, 5);
        clickById(removeAllId);
	}
	
    /**
     *  style and classes, standard HTML attributes are output to client
     */
	@Test
	public void testHTMLAttributes(Template template) {
        init(template);
        
		Map<String, String> styleAttributes = new HashMap<String, String>();
		styleAttributes.put("color", "blue");
		styleAttributes.put("text-decoration", "underline");
		
		List<SeleniumEvent> events = new ArrayList<SeleniumEvent>();
		events.add(SeleniumEvent.ONMOUSEMOVE);
		events.add(SeleniumEvent.ONMOUSEOUT);
		events.add(SeleniumEvent.ONMOUSEOVER);
		
        assertClassNames(lsId,new String [] {"noname"}, "Component's rendering invalid", true);
        assertStyleAttributes(lsId, styleAttributes);
		
        assertEvents(lsId, events);
	}

	/**
     *  sourceValue/targetValue updated, listener fire with multiple selection
     */
    @Test
    public void testValuesAndListener(Template template) {
        init(template);
        Assert.assertEquals(getValueById(inputTextId), "something", "Value of inputText(id='inputTextId') must equal initial value('something').");
    	_assertTableRowsCount(sourceTableId, 5);
    	_assertTableRowsCount(targetTableId, 0);
    	_assertTableRowsCount(sourceSelectionTableId, 0);
    	_assertTableRowsCount(targetSelectionTableId, 0);

    	_selectItem(parentId + "ls:0", true, false);
    	_selectItem(parentId + "ls:2", true, false);
    	
    	clickById(copyId);
    	
    	_selectItem(parentId + "ls:1", true, false);
    	_selectItem(parentId + "ls:3", true, false);
    	_selectItem(parentId + "ls:0", true, false);
    	
        clickAjaxCommandAndWait(submitId);
    	
        Assert.assertEquals(getValueById(inputTextId), "value was changed", "Value of inputText(id='inputTextId') must equal changed value('value was changed').");
    	_assertTableRowsCount(sourceTableId, 3);
    	_assertTableRowsCount(targetTableId, 2);
    	_assertTableRowsCount(sourceSelectionTableId, 2);
    	_assertTableRowsCount(targetSelectionTableId, 1);
    }
    
    /**
     *  immediate = true component works respectively
     */
    @Test
    public void testImmediate(Template template) {
        init(template); 
        selenium.click(targetRequiredId);
        waitForAjaxCompletion();
        setValueById(inputTextId, "");
        clickAjaxCommandAndWait(submitId);       
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
        selenium.click(immediateId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);
        Assert.assertTrue(selenium.isElementPresent(msgId), "Message must be rendered. Target list is empty.");
    }
    
    /**
     *  converter defined by component attribute and configured at application level works
     */
    @Test
    public void testConverter(Template template) {
        init(template); 
    	AssertValueEquals(lsId + ":0StateInput", "0:1:Item1", "Converter doesn't work.");
    }
    
    /**
     *  component with rendered = false is not present on the page
     */
    @Test
    public void testRendered(Template template) {
        init(template); 
        Assert.assertTrue(selenium.isElementPresent(lsId), "ListShuttle must be rendered.");

        selenium.click(renderedId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);       
        
        Assert.assertFalse(selenium.isElementPresent(lsId), "ListShuttle mustn't be rendered.");
    }
    
    /**
     *  JS API is present and works
     */
    @Test
    public void testJSAPI(Template template) {
        init(template); 
        selenium.runScript("var listShuttle = ($('" + lsId + "')).component;");
        checkJSError();
        // Check count
        _assertTableRowsCount(availebleListId, 5);
        _assertTableRowsCount(targetListId, 0);
        // Copy all and check count
        try {
            selenium.runScript("listShuttle.copyAll();");
            checkJSError();
            _assertTableRowsCount(availebleListId, 0);
            _assertTableRowsCount(targetListId, 5);
        } catch (Exception e) {
            writeStatus("Test failed. Copy all does not work. Cause: " + e, true);
            Assert.fail("Test failed. Copy all does not work. Cause: " + e);
        }

        // Remove all and check count
        try {
            selenium.runScript("listShuttle.removeAll();");
            checkJSError();
            _assertTableRowsCount(availebleListId, 5);
            _assertTableRowsCount(targetListId, 0);
        } catch (Exception e) {
            writeStatus("Test failed. Remove all does not work. Cause: " + e, true);
            Assert.fail("Test failed. Remove all does not work. Cause: " + e);
        }

        try {
            // Copy 1st & 2nd item
            _selectItem(parentId + "ls:0");
            selenium.runScript("listShuttle.copy();");
            checkJSError();
            _selectItem(parentId + "ls:1");
            selenium.runScript("listShuttle.copy();");
            checkJSError();
            _selectItem(parentId + "ls:2");
            selenium.runScript("listShuttle.copy();");
            checkJSError();
            // Check count
            _assertTableRowsCount(availebleListId, 2);
            _assertTableRowsCount(targetListId, 3);
        } catch (Exception e) {
            writeStatus("Test failed. Copy does not work. Cause: " + e, true);
            Assert.fail("Test failed. Copy does not work. cause: " + e);
        }

        // Check posting to server
        _checkDataPost2Server(2, 3);

        selenium.runScript("var listShuttle = ($('" + lsId + "')).component;");
        // Check ordering
        _checkOrdering(targetListId, "1Item1", "2Item2", "3Item3", "");

        // Move the first to to the last
         _selectItem(parentId + "ls:t0");
        selenium.runScript("listShuttle.down();");
        checkJSError();
         _checkOrdering(targetListId, "2Item2", "1Item1", "3Item3", "Test failed. Down control does not work");

         _selectItem(parentId + "ls:t0");
         selenium.runScript("listShuttle.bottom();");
         checkJSError();
        _checkOrdering(targetListId, "2Item2", "3Item3", "1Item1", "Test failed. Last control does not work");

        _selectItem(parentId + "ls:t2");
        selenium.runScript("listShuttle.top();");
        checkJSError();
        _checkOrdering(targetListId, "3Item3", "2Item2", "1Item1", "Test failed. First control does not work");

        _selectItem(parentId + "ls:t0");
        selenium.runScript("listShuttle.up();");
        checkJSError();
        _checkOrdering(targetListId, "3Item3", "1Item1", "2Item2", "Test failed. Up control does not work");

        _selectItem(parentId + "ls:t1");
        selenium.runScript("listShuttle.remove();");
        checkJSError();
        _checkOrdering(targetListId, "3Item3", "1Item1", null, "Test failed. Remove control does not work");

        _assertTableRowsCount(targetListId, 2);
    }

    /**
     *  Check 'switchByClick' attribute
     */
    @Test
    public void testSwitchByClick(Template template) {
        init(template); 
        _selectItem(parentId + "ls:0");
        Assert.assertEquals(getTextById(targetListId), "", "Target list must be empty.");
        
        selenium.click(switchByClickId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);       
        
        _selectItem(parentId + "ls:0");
        Assert.assertEquals(getTextById(targetListId), "1Item1", "Target list mustn't be empty.");
    }

    /**
     *  Check 'showButtonsLabel' attribute
     */
    @Test
    public void testShowButtonsLabel(Template template) {
        init(template);
        Assert.assertEquals(getTextById(copyAllId), "Copy All Items", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(copyId), "Copy", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(removeId), "Remove", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(removeAllId), "Remove All", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(firstId), "Move to top", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(upId), "Up", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(downId), "Down", "The text on the button must be visible.");
        Assert.assertEquals(getTextById(lastId), "Last", "The text on the button must be visible.");

        selenium.click(showButtonLabelsId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);       
        
        Assert.assertEquals(getTextById(copyAllId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(copyId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(removeId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(removeAllId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(firstId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(upId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(downId), "", "The text on the button mustn't be visible.");
        Assert.assertEquals(getTextById(lastId), "", "The text on the button mustn't be visible.");
    }
    
   /**
     * 'sourceRequired' and 'targetRequired' attributes work
     */
    @Test
    public void testSourceRequiredAndTargetRequired(Template template) {
        init(template);
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
        
        selenium.click(targetRequiredId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);        
        Assert.assertTrue(selenium.isElementPresent(msgId), "Message must be rendered. Target list is empty.");
        clickById(copyAllId);
        clickAjaxCommandAndWait(submitId);        
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
        
        selenium.click(targetRequiredId);
        waitForAjaxCompletion();
        
        selenium.click(sourceRequiredId);
        waitForAjaxCompletion();
        clickAjaxCommandAndWait(submitId);        
        Assert.assertTrue(selenium.isElementPresent(msgId), "Message must be rendered. Source list is empty.");
        clickById(removeAllId);
        clickAjaxCommandAndWait(submitId);        
        Assert.assertFalse(selenium.isElementPresent(msgId), "Message mustn't be rendered.");
    }

    /**
     * Check "sourceCaption" and "targetCaption" facets
     */
    @Test
    public void testSourceCaptionAndTargetCaption(Template template) {
        init(template);
        Assert.assertTrue(selenium.isElementPresent(parentId + "ls:sourceCaptionId"), "Facet 'sourceCaption' must be rendered.");    	
        Assert.assertTrue(selenium.isElementPresent(parentId + "ls:targetCaptionId"), "Facet 'targetCaption' must be rendered.");    	
    }
    
    @Test
    public void testListShuttleComponent(Template template) {
        init(template);
 
        _checkVisibility(true);

        // Check count
        _assertTableRowsCount(availebleListId, 5);
        _assertTableRowsCount(targetListId, 0);

        // Copy all and check count
        try {
            clickById(copyAllId);
            _assertTableRowsCount(availebleListId, 0);
            _assertTableRowsCount(targetListId, 5);
        } catch (Exception e) {
            writeStatus("Test failed. Copy all does not work. Cause: " + e, true);
            Assert.fail("Test failed. Copy all does not work. Cause: " + e);
        }

        // Remove all and check count
        try {
            clickById(removeAllId);
            _assertTableRowsCount(availebleListId, 5);
            _assertTableRowsCount(targetListId, 0);
        } catch (Exception e) {
            writeStatus("Test failed. Remove all does not work. Cause: " + e, true);
            Assert.fail("Test failed. Remove all does not work. Cause: " + e);
        }

        try {
            // Copy 1st & 2nd item
            _doItemAction(parentId + "ls:0", copyId, "Copy");
            _doItemAction(parentId + "ls:1", copyId, "Copy");
            _doItemAction(parentId + "ls:2", copyId, "Copy");

            // Check count
            _assertTableRowsCount(availebleListId, 2);
            _assertTableRowsCount(targetListId, 3);
        } catch (Exception e) {
            writeStatus("Test failed. Copy does not work. Cause: " + e, true);
            Assert.fail("Test failed. Copy does not work. cause: " + e);
        }

        // Check posting to server
        _checkDataPost2Server(2, 3);

        // Check ordering
        _checkOrdering(targetListId, "1Item1", "2Item2", "3Item3", "");

        // Move the first to to the last
        _doItemAction(parentId + "ls:t0", downId, "Down");
        _checkOrdering(targetListId, "2Item2", "1Item1", "3Item3", "Test failed. Down control does not work");

        _doItemAction(parentId + "ls:t0", lastId, "Last");
        _checkOrdering(targetListId, "2Item2", "3Item3", "1Item1", "Test failed. Last control does not work");

        _doItemAction(parentId + "ls:t2", firstId, "first");
        _checkOrdering(targetListId, "3Item3", "2Item2", "1Item1", "Test failed. First control does not work");

        _doItemAction(parentId + "ls:t0", upId, "Up");
        _checkOrdering(targetListId, "3Item3", "1Item1", "2Item2", "Test failed. Up control does not work");

        _doItemAction(parentId + "ls:t1", removeId, "Remove");
        _checkOrdering(targetListId, "3Item3", "1Item1", null, "Test failed. Remove control does not work");

        _assertTableRowsCount(targetListId, 2);

        hideControls();
        _checkVisibility(false);

    }

    private void hideControls() {
        clickAjaxCommandAndWait(hideId);
    }

    private void _checkVisibility(boolean isVisible) {
        if (isVisible) {
            AssertVisible(copyAllId);
            AssertVisible(parentId + "lsdisremoveAll");
            AssertVisible(parentId + "lsdisremove");
            AssertVisible(parentId + "lsdisremove");

            AssertVisible(parentId + "lsdisfirst");
            AssertVisible(parentId + "lsdisup");
            AssertVisible(parentId + "lsdisdown");
            AssertVisible(parentId + "lsdislast");
        } else {
            AssertNotRendered(copyAllId);
            AssertNotRendered(removeAllId);
            AssertNotRendered(copyId);
            AssertNotRendered(removeId);
            AssertNotRendered(firstId);
            AssertNotRendered(lastId);
            AssertNotRendered(downId);
            AssertNotRendered(upId);
        }
    }

    private void _checkOrdering(String listId, String first, String second, String fird, String errorMessage) {
        try {
            if (first != null)
                _checkItemText(first, listId, 0);
            if (second != null) {
                _checkItemText(second, listId, 1);
            }
            if (fird != null) {
                _checkItemText(fird, listId, 2);
            }
        } catch (Exception e) {
            writeStatus(errorMessage, true);
            Assert.fail(errorMessage + e.getMessage());
        }
    }

    private void _assertTableRowsCount(String tbId, int rows) {
        writeStatus("Check items count for list id : " + tbId);
        StringBuffer b = new StringBuffer("$('");
        b.append(tbId);
        b.append("').rows.length");
        String l = runScript(b.toString());
        int r;
        try {
            r = Integer.parseInt(l);
            if (r != rows) {
                writeStatus("Incorrect list item count in table id: " + tbId, true);
                Assert.fail("Incorrect list item count in table id: " + tbId);
            }
        } catch (Exception e) {
            writeStatus("List shuttle test failed. Cause: " + e.getMessage(), true);
            Assert.fail("List shuttle test failed. Cause: " + e);
        }
    }

    private void _doItemAction(String itemId, String actionId, String actionName) {
        writeStatus(actionName + " the item id: " + itemId);
        _selectItem(itemId);
        clickById(actionId);
    }

    private void _checkDataPost2Server(int availableCount, int targetCount) {
        writeStatus("Rerender form. List items should be changed.");
        clickAjaxCommandAndWait(submitId);

        try {
            _assertTableRowsCount(availebleListId, availableCount);
            _assertTableRowsCount(targetListId, targetCount);
        } catch (Exception e) {
            writeStatus("Data was post to server incorrectly. List data before rerendering does not match after", true);
            Assert
                    .fail("Data was post to server incorrectly. List data before rerendering does not match after. Cause: "
                            + e);
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
			selenium.click("id=" + itemId);
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

    private void _selectItem(String itemId) {
    	_selectItem(itemId, false, false);
    }

    private void _checkItemText(String text, String listId, int i) {
        StringBuffer b = new StringBuffer("$('");
        b.append(listId);
        b.append("').rows[");
        b.append(i);
        b.append("].id");

        String id = null;
        try {
            id = runScript(b.toString());
            AssertTextEquals(id, text, "Invalid item's text");
        } catch (Exception e) {
            writeStatus("Invalid item text. Item id: " + id);
            Assert.fail("Invalid item text. Item id: " + id);
        }
    }

    /*
     * (non-Javadoc)
     *  
     * @see org.richfaces.SeleniumTestBase#getTestUrl()
     */
    @Override
    public String getTestUrl() {
        return "pages/listShuttle/listShuttleTest.xhtml";
    }
}
