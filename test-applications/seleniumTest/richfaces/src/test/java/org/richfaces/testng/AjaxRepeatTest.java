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

import java.util.Arrays;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * a4j:repeat component selenium test
 * 
 * @author Alexandr Levkovsky
 * 
 */
public class AjaxRepeatTest extends SeleniumTestBase {

	private String rows;

	private String rendered;
	
	private String panelGroup;

	private String repeat;

	private String reRender;

	private String outputText;
	
	private String dataTable;

	private void init(Template template) {
        renderPage(null, template, "#{a4jRepeatBean.init}");
        String attrForm = getParentId() + "attrForm";
        rows = attrForm + ":rows";
        rendered = attrForm + ":rendered";
        String mainForm = getParentId() + "mainForm";
        panelGroup = mainForm + ":panelGroup";
        repeat = mainForm + ":repeat";
        reRender = mainForm + ":reRender";
        outputText = getParentId() + "outputText";
        dataTable = getParentId() + "dataTable";
	}

	/**
	 *  items from collection defined as value attribute are output to the client;
	 *  number of items is limited using elements attribute and not
	 */
	@Test
	public void testStructure(Template template) {
	    init(template);
		chekStructure();
		selenium.type(rows, "3");
		waitForAjaxCompletion();
		Assert.assertEquals(selenium.getXpathCount("id('"+ panelGroup + "')/div"), 3);
		chekStructure();
	}
	
    /**
     *  nested input and command components work correctly
     *  for the cases of validation failure or not
     */
	@Test
	public void testComponentsProcessingWithValidation(Template template) {
        init(template);
		String liLocator = "xpath=id('"+ panelGroup + "')/div[";
		String inputLocator = liLocator + "6]/input[";
		Assert.assertEquals(selenium.getText(outputText), "");
		selenium.click(inputLocator + "2]");
		waitForPageToLoad();
		Assert.assertTrue(selenium.getText(outputText).endsWith("5:submit"));
		selenium.click(inputLocator + "3]");
        waitForAjaxCompletion();
		Assert.assertTrue(selenium.getText(outputText).endsWith("5:ajaxSubmit"));
		selenium.click(inputLocator + "4]");
        waitForAjaxCompletion();
		Assert.assertTrue(selenium.getText(outputText).endsWith("5:ajaxSingleSubmit"));
		selenium.type(inputLocator + "1]", "abc5");
		selenium.click(inputLocator + "3]");
        waitForAjaxCompletion();
		chekStructure();        
		selenium.type(inputLocator + "1]", "fail");
		selenium.type(liLocator + "3]/input[1]", "fail");
		selenium.click(inputLocator + "3]");
        waitForAjaxCompletion();
		chekStructure(3, 6);        
	}

    /**
     *  rows defined by ajaxKeys attribute are re-rendered correctly
     */
	@Test
	public void testAjaxKeys(Template template) {
        init(template);
		chekStructure();
		clickAjaxCommandAndWait(reRender);
		chekStructure(3, 7);        
	}

    /**
     *  components using rowKeyVar and stateVar variables are correctly output to the client
     */
	@Test
	public void testVars(Template template) {
        init(template);
		Assert.assertEquals(selenium.getText(repeat + ":c_2:first"), "0", "Attribute 'stateVar' works wrong.");
		Assert.assertEquals(selenium.getText(repeat + ":c_2:rows"), "0", "Attribute 'stateVar' works wrong.");
		int rows = selenium.getXpathCount("id('"+ repeat + "')/div").intValue();		
		for (int i = 0; i < rows; i++) {
			Assert.assertEquals(selenium.getText(repeat + ":c_" + i + ":rowKeyVar"), Integer.toString(i));			
		}
	}

    /**
     *  componentState attribute stores nested components state across requests
     */
	@Test
	public void testComponentState(Template template) {
        init(template);
		Assert.assertEquals(selenium.getText(repeat + ":c_2:firstState"), "0", "Attribute 'componentState' works wrong.");
		Assert.assertEquals(selenium.getText(repeat + ":c_2:rowsState"), "0", "Attribute 'componentState' works wrong.");
	}

    /**
     *  rowKeyConverter outputs correct client ids
     */
	@Test
	public void testRowKeyConverter(Template template) {
        init(template);
		Assert.assertTrue(selenium.getAttribute("xpath=id('"+ panelGroup + "')/div[3]/input[1]@id").indexOf(":c_2") != -1, "Attribute 'rowKeyConverter' works wrong.");
	}

	private void chekStructure(int ... notEqualRows) {
		String dataTableRowLocator = "id('"+ dataTable + "')/div";
		int count = selenium.getXpathCount(dataTableRowLocator).intValue();
		for (int i = 1; i <= count; i++) {
			if (Arrays.binarySearch(notEqualRows, i) < 0) {
				Assert.assertEquals(selenium.getValue("xpath=id('"+ repeat + "')/div[" + i + "]/input"),
						selenium.getText("xpath=" + dataTableRowLocator + "[" + i + "]"));
			} else {
				Assert.assertFalse(selenium.
						getValue("xpath=id('"+ repeat + "')/div[" + i + "]/input").
						equals(selenium.getText("xpath=" + dataTableRowLocator + "[" + i + "]")));
			}
		}
	}

    /**
     *    component with rendered = false is not present on the page
     */
	@Test
	public void testRendered(Template template) {
        init(template);
		Assert.assertFalse(selenium.getXpathCount("id('"+ panelGroup + "')/div").intValue() == 0);
		clickAjaxCommandAndWait(rendered);
		Assert.assertTrue(selenium.getXpathCount("id('"+ panelGroup + "')/div").intValue() == 0);
	}

	@Override
    public String getTestUrl() {
	return "pages/ajaxRepeat/ajaxRepeatTest.xhtml";
    }
}
