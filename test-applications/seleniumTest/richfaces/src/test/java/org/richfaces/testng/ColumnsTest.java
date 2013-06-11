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
import org.richfaces.AutoTester;
import org.richfaces.SeleniumEvent;
import org.richfaces.SeleniumTestBase;
import org.richfaces.model.Ordering;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ColumnsTest extends SeleniumTestBase {
	
	static final String RESET_METHOD = "#{columnsBean.reset}";
	static final String RESET_METHOD_FOR_RENDERED_TEST = "#{columnsBean.prepareRenderedTest}";
	static final String RESET_METHOD_FOR_EXTERNAL_SORT_TEST = "#{columnsBean.prepareExternalSorting}";
	
	static final String SORTING_TEST_URL = "pages/columns/testColumnsSorting.xhtml";
	static final String FILTERING_TEST_URL = "pages/columns/testColumnsFiltering.xhtml";
	static final String VALUE_ATTRIBUTE_TEST_URL = "pages/columns/testValueAttribute.xhtml";  
	static final String ROSPAN_COLSPAN_BREAKBEFORE_ATTRIBUTES_TEST_URL = "pages/columns/testColspanRowspanBreakbefore.xhtml";
	
	enum FEATURE {
		SORTING,
		FILTERING
	}
	
	static final String [] COLUMNS_CLASSSES = new String [] {"dr-table-cell", "rich-table-cell", "columnClass"};
	
	@Test
	public void testExternalSorting(Template template) {
		renderAutoTestPage(SORTING_TEST_URL, template, RESET_METHOD_FOR_EXTERNAL_SORT_TEST);
		
		final int cols = 3;
		
		String tableId =  getParentId() + "_form:table2";
		String testAscId = getParentId() + "_form:sortAsc";
		String testDescId = getParentId() + "_form:sortDesc";
		
		clickAjaxCommandAndWait(testAscId);
		
		// Test external sorting ASC
		for (int i = 0; i < cols; i++) {
			checkColumnsData(tableId, i, new String [] {String.valueOf(i), String.valueOf(i+1)}, FEATURE.SORTING, "Sorting does not work as expected if selfSorted = false.");
			checkSortOrderAttribute(tableId, i, Ordering.ASCENDING);
		}
		
		// Test external sorting DESC
		clickAjaxCommandAndWait(testDescId);
		for (int i = 0; i < cols; i++) {
			checkColumnsData(tableId, i, new String [] {String.valueOf(i+1), String.valueOf(i)}, FEATURE.SORTING, "Sorting does not work as expected if selfSorted = false.");
			checkSortOrderAttribute(tableId, i, Ordering.DESCENDING);
		}
		
	}
	
	@Test
	public void testRenderedAttribute (Template template) {
		renderPage(template, RESET_METHOD_FOR_RENDERED_TEST);
		
		String tableId =  getParentId() + "_form:table";
		
		int headerCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/*").intValue();
		int footerCount = selenium.getXpathCount("//table[@id='"+tableId+"']/tfoot/*").intValue();
		int dataCount = selenium.getXpathCount("//table[@id='"+tableId+"']/tbody/tr/td/*").intValue();
		
		if (headerCount != 0 || footerCount != 0 || dataCount != 0) {
			Assert.fail("Rendered attribute does not work. No data and no facets should be output to client.");
		}
	}
	
	@Test
	public void testRowspanColspanBreakBeforeAttributes(Template template) {
		renderPage(ROSPAN_COLSPAN_BREAKBEFORE_ATTRIBUTES_TEST_URL, template, RESET_METHOD);
		
		String tableId = getParentId() + "_form:table";
		
		// Check rowspan attribute
		String col1Rowspan = selenium.getAttribute("//table[@id='"+tableId+"']/thead/tr/th[1]/@rowspan");
		String col3Rowspan = selenium.getAttribute("//table[@id='"+tableId+"']/thead/tr/th[4]/@rowspan");
		
		if (!col1Rowspan.equals("2") || !col3Rowspan.equals("2")) {
			Assert.fail("Rowspan attributes was not output to client. The first and third columns should have rowspan=2. But was: " + col1Rowspan + " and " + col3Rowspan);
		}
		
		// Check breakBefore attribute
		int headerRowsCount =  selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr").intValue();
		int firstRowColsCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr[1]/th").intValue();
		int secondRowColsCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr[2]/th").intValue();
		
		if (headerRowsCount != 2 || firstRowColsCount != 4 || secondRowColsCount != 1) {
			Assert.fail("BreakBefore attribute was not applied as expected. Table header should have two rows. The first row should have 4 columns. The second - 1 column.");
		}

		
		// Check colspan attribute
		String col4Colspan = selenium.getAttribute("//table[@id='"+tableId+"']/thead/tr[2]/th/@colspan");
		
		if (!col4Colspan.equals("2")) {
			Assert.fail("Colspan attributes was not output to client. The 4th column should have colspan=2. But was: " + col4Colspan);
		}
		
	}
	

	@Test
	public void testHeaderFooterFacets(Template template) {
		renderPage(template, RESET_METHOD);
		
		String tableId = getParentId() + "_form:table";
		
		final int cols = 3;
		for (int i = 0; i < cols; i++) {
			AssertTextEquals("//table[@id='"+tableId+"']/thead/tr/th["+(i+1)+"]", "Header" + i, "Header for column " + i + " was not output to client"); 
			AssertTextEquals("//table[@id='"+tableId+"']/tfoot/tr/td["+(i+1)+"]", "Footer" + i, "Footer for column " + i + " was not output to client");
		}
	}
	
	
	@Test
	public void testStylesClassesAndStandardHTMLAttributes(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		String tableId =  tester.getClientId("table");
		String columnPath = "//table[@id='"+tableId+"']/tbody/tr/td[1]";
		
		assertEvents(columnPath, SeleniumEvent.STANDARD_HTML_EVENTS);
		assertAttributeContains(columnPath, "class", "columnClass", "StyleClass attribute was not output to client");
		assertClassNames(columnPath, COLUMNS_CLASSSES, "Columns have unexpected css classes", false);
		assertStyleAttributeContains(columnPath, "color: green", "Style attribute was not output to client");
		assertAttributeContains(columnPath, "title", "title", "Title attribute was not output to client");
	}
	
	@Test
	public void testValueAttribute(Template template) {
		renderPage(VALUE_ATTRIBUTE_TEST_URL, template, RESET_METHOD);
		
		final int columnsCount = 3;
		
		String tableId = getParentId() + "_form:table";
		assertColumnsCount(columnsCount, tableId, "Strign value attribute was not applied properly. Table should have 3 columns");
		
		for (int i = 0; i < columnsCount; i++) {
			AssertTextEquals("//table[@id='"+tableId+"']/thead/tr/th["+(i+1)+"]", "Header" + i, "Var was not initialized properly");
			checkColumnsData(tableId, i, new String[] {String.valueOf(i), String.valueOf(i+1)}, null, "");
		}
		
	}

	
	@Test
	public void testSortingFeature(Template template) {
		renderPage(SORTING_TEST_URL, template, RESET_METHOD);
		
		final int columnsCount = 3;
		
		// Test sorting by sortBy attribute
		testOrdering(getParentId() + "_form:table", columnsCount);

		// Test sorting by comparator attribute
		testOrdering(getParentId() + "_form:table2", columnsCount);
		
	}
	
	@Test
	public void testFilteringFeature(Template template) {
		renderPage(FILTERING_TEST_URL, template, RESET_METHOD);
		
		// Check filterBy attribute  ----------->>>>>>
		
		String tableId = getParentId() + "_form:table";
		String filterInputPath = "//table[@id='"+tableId+"']/thead/tr/th[1]/*/input";
		final int columnsCount = 3;
		
  		    // Filter by the first column with '0' filter value 
			selenium.type(filterInputPath, "0");
			waitForAjaxCompletion();
			
			for (int i = 0; i < columnsCount; i++) {
				checkColumnsData(tableId, i, new String [] {String.valueOf(i)}, FEATURE.FILTERING, "Filterby does snot work.");
			}
			AssertTextEquals("//table[@id='"+tableId+"']/tbody/tr/td[1]", "0", "Filter value attribute does not work. Binding value was not changed.");
		
		    // Filter by the third column with '3' filter value
			selenium.type(filterInputPath, "");
			waitForAjaxCompletion();
			
			filterInputPath = "//table[@id='"+tableId+"']/thead/tr/th[3]/*/input";
			selenium.type(filterInputPath, "3");
			waitForAjaxCompletion();
			
			for (int i = 0; i < columnsCount; i++) {
				checkColumnsData(tableId, i, new String [] {String.valueOf(i + 1)}, FEATURE.FILTERING, "Filterby does not work.");
			}
			AssertTextEquals("//table[@id='"+tableId+"']/tbody/tr/td[3]", "3", "Filter value attribute does not work. Binding value was not changed.");
	
		//<<<----------------
		
		// Test filter method attribute
			
		tableId = getParentId() + "_form:table2";
			for (int i = 0; i < columnsCount; i++) {
				checkColumnsData(tableId, i, new String [] {String.valueOf(i)}, FEATURE.FILTERING, "FilterMethod does not work.");
			}
			
	   // Test filter expression attribute 
	   tableId = getParentId() + "_form:table3";
			for (int i = 0; i < columnsCount; i++) {
				checkColumnsData(tableId, i, new String [] {String.valueOf(i + 1)}, FEATURE.FILTERING, "FilterExpression does not work.");
			}
			
	}
	
     
    @Test
    public void testOutputAndNestedInputsAndCommands(Template template) {
    	renderPage(template, RESET_METHOD);
    	
    	int rows = 2;
    	int cols = 3;
    	
    	String parentId = getParentId() + "_form:";
    	String tableId = parentId + "table";
    	String submitId = parentId + "submit";
    	assertRowsCount(rows, tableId);
    	assertColumnsCount(cols, tableId, "Columns count is invalid.");
    	
    	// Check headers
    	for (int i=0;i<cols;i++) {
    		String header = selenium.getText("//*[@id='"+tableId+"']/thead/tr[1]/th["+(i+1)+"]");
    		if (!("Header" + i).equals(header)) {
    			Assert.fail("Header for columns ["+i+"] was not output to client");
    		}

    	}
    	
    	for (int i=0; i<rows; i++) {
    		for (int j=0;j<cols;j++) {
    			String inputPath = "//*[@id='"+tableId+"']/tbody/tr["+(i+1)+"]/td["+(j+1)+"]/input[@type='text']";
    			String submitPath = "//*[@id='"+tableId+"']/tbody/tr["+(i+1)+"]/td["+(j+1)+"]/input[@type='submit']";
    			if (selenium.getXpathCount(inputPath).intValue() != 1) {
    				Assert.fail("Input for row["+i+"] cell["+j+"] was not output to client");
    			}
    			if (selenium.getXpathCount(submitPath).intValue() != 1) {
    				Assert.fail("Submit for row["+i+"] cell["+j+"] was not output to client");
    			}
    			String submitValue = selenium.getValue(submitPath);
    			String expectedValue = ("Submit"+j);
    			if (!expectedValue.equals(submitValue)) {
    				Assert.fail("Submit value for row["+i+"] cell["+j+"] is incorrect. Should be: " +expectedValue + "But was: " + submitValue);
    			}
    		}
    	}
    	
    	setInputs(tableId, rows, cols);
    	clickCommandAndWait(submitId);
    	
    	String checkInputs = getTextById(parentId + "checkInputs");
    	if (!Boolean.TRUE.toString().equals(checkInputs)) {
    		Assert.fail("Set inputs values were not applied to data modal as expected. Nested input dont work");
    	}
    	
    	// Click submit button in the first row
    	String submitPath = "//*[@id='"+tableId+"']/tbody/tr[1]/td[1]/input[@type='submit']";
    	selenium.click(submitPath);
    	selenium.waitForPageToLoad("10000");
    	
    	String status = getTextById(parentId + "status");
    	if (!"ActionListener0".equals(status)) {
    		Assert.fail("Nested command dont work.");
    	}
    	
    	// Click submit button in the first row
    	submitPath = "//*[@id='"+tableId+"']/tbody/tr[2]/td[2]/input[@type='submit']";
    	selenium.click(submitPath);
    	selenium.waitForPageToLoad("10000");
    	
    	status = getTextById(parentId + "status");
    	if (!"ActionListener0ActionListener1".equals(status)) {
    		Assert.fail("Nested command dont work.");
    	}
    }
 
    
    private void setInputs(String tableId, int rows, int cols) {
    	for (int i=0;i<rows; i++) {
    		for (int j=0; j<cols; j++) {
    			String v = "Row"+i+"Cell"+j;
    			String path = "//*[@id='"+tableId+"']/tbody/tr["+(i+1)+"]/td["+(j+1)+"]/input[@type='text']";
    			selenium.type(path, v);
    		}
    	}
    }
    
    private void checkColumnsData (String tableId, int col, String [] expected, FEATURE feature, String message) {
    	int l = expected.length;
    	String [] values = new String [l];
    	for (int i = 0; i < l; i++) {
    		values[i] = selenium.getText("//table[@id='"+tableId+"']/tbody/tr["+(i+1)+"]/td["+(col+1)+"]");
    	}
    	
    	for (int i = 0; i < l; i++) {
    		if (!expected[i].equals(values[i])) {
    			Assert.fail(message + "Column number [" + col + "] was "+(feature != null ? ((feature == FEATURE.SORTING) ? "sorted" : "filtered") : "")+" unexpectedly. Column's value should be: " + Arrays.toString(expected) + ". But was: " + Arrays.toString(values));
    		}
    	}
    }
    
    private void clickSort(String tableId, int col) {
    	selenium.click("//table[@id='"+tableId+"']/thead/tr[1]/th["+(col+1)+"]");
    	waitForAjaxCompletion();
    }
    
    private void checkSortOrderAttribute(String tableId, int col, Ordering ordering) {
    	String header = selenium.getText("//table[@id='"+tableId+"']/thead/tr[1]/th["+(col+1)+"]");
    	if (!ordering.equals(Ordering.valueOf(header))) {
    		Assert.fail("SortOrder attribute was not changed for columns number ["+col+"]. Expected : " + ordering + ". But was : " + header);
    	}
    }
    
    private void testOrdering(String tableId, int cols) {
    	final String message = "SortBy does not work.";
    	for (int i=0; i<cols; i++) {
    		clickSort(tableId, i);
    		
    		checkColumnsData(tableId, 0, new String [] {"0", "1"}, FEATURE.SORTING, message);
    		checkColumnsData(tableId, 1, new String [] {"1", "2"}, FEATURE.SORTING, message);
    		checkColumnsData(tableId, 2, new String [] {"2", "3"}, FEATURE.SORTING, message);
    		checkSortOrderAttribute(tableId, i, Ordering.ASCENDING);
    		
    		clickSort(tableId, i);
    		
    		checkColumnsData(tableId, 0, new String [] {"1", "0"}, FEATURE.SORTING, message);
    		checkColumnsData(tableId, 1, new String [] {"2", "1"}, FEATURE.SORTING, message);
    		checkColumnsData(tableId, 2, new String [] {"3", "2"}, FEATURE.SORTING, message);
    		checkSortOrderAttribute(tableId, i, Ordering.DESCENDING);
   		
    	}
    }
    
 
    @Override
    public String getTestUrl() {
        return "pages/columns/columnsTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/columns/columnsAutoTest.xhtml";
    }

}
