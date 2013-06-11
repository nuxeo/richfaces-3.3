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
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.richfaces.model.Ordering;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataTableTest extends SeleniumTestBase {
	
	private String dataTableId;
	
	private String dataTableBodyId;
	
	private String commandId;
	
	private static final String FORM_ID = "_form:";
	
	private static final String CONTROLS_FORM = "_controls:";
	
	private static final String RESET_METHOD = "#{dataTableBean.reset}";
	
	private static final String [] dataTableClassNames = new String [] {
		"dr-table",
		"rich-table" 
	};
	
	private static final Map<String, String> styleAttributes = new HashMap<String, String>();
	static {
		styleAttributes.put("width", "100%");
		styleAttributes.put("color", "yellow");
	}
	
	private void init(String parentId) {
		dataTableId = parentId + FORM_ID + "table";
		dataTableBodyId = dataTableId + ":tb";
		commandId = parentId + FORM_ID + "submit";
	}
	

    public String getTestUrl() {
        return "pages/dataTable/dataTableTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/dataTable/dataTableAutoTest.xhtml";
    }

    @Test
    public void testSortByAttribute(Template template) {
        renderPage(template, RESET_METHOD);
        init(getParentId());
        
        testSorting(2, new String [] {"1", "2", "3", "4", "5", "6"});
        testSorting(3, new String [] {"2", "3", "4", "5", "6", "7"});
        testSortIcon(2, Ordering.UNSORTED);
        
        clickSort(2);
        
        testSorting(2, new String [] {"1", "2", "3", "4", "5", "6"});
        testSorting(3, new String [] {"2", "3", "4", "5", "6", "7"});
        testSortIcon(2, Ordering.ASCENDING);
        
        clickSort(2);
        
        testSorting(2, new String [] {"6", "5", "4", "3", "2", "1"});
        testSorting(3, new String [] {"7", "6", "5", "4", "3", "2"});
        testSortIcon(2, Ordering.DESCENDING);
    
    }
    
    @Test
    public void testSortComparator(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
        
        testSorting(2, new String [] {"1", "2", "3", "4", "5", "6"});
        testSorting(3, new String [] {"2", "3", "4", "5", "6", "7"});
        testSortIcon(3, Ordering.UNSORTED);
        
        clickSort(3);
        
        testSorting(2, new String [] {"1", "2", "3", "4", "5", "6"});
        testSorting(3, new String [] {"2", "3", "4", "5", "6", "7"});
        testSortIcon(3, Ordering.ASCENDING);
        
        clickSort(3);
        
        testSorting(2, new String [] {"6", "5", "4", "3", "2", "1"});
        testSorting(3, new String [] {"7", "6", "5", "4", "3", "2"});
        testSortIcon(3, Ordering.DESCENDING);
    }
    
    @Test
    public void testExternalSorting(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
    	
    	String commandId = getParentId() + CONTROLS_FORM + "testACS";
    	clickCommandAndWait(commandId);
    	
    	testSorting(1, new String [] {"0", "1", "2", "3", "4", "5"});
    	testSortIcon(1, Ordering.ASCENDING);
    	
    	commandId = getParentId() + CONTROLS_FORM + "testDESC";
    	clickCommandAndWait(commandId);
    	
    	testSorting(1, new String [] {"5", "4", "3", "2", "1", "0"});
    	testSortIcon(1, Ordering.DESCENDING);
    	
    	
    }
    
    @Test
    public void testFilterBy(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
        
        String filterId = dataTableId + ":col4fsp";
        setValueById(filterId, "5");
        clickAjaxCommandAndWait(filterId);         
        
        testRowsCount(1);
        testSorting(4, new String [] { "5" });
        
        setValueById(filterId, "3");
        clickAjaxCommandAndWait(filterId);         
        
        testRowsCount(1);
        testSorting(4, new String [] { "3" });
    }
    
    @Test
    public void testFilterMethod(Template template) {
     	renderPage(template, RESET_METHOD);
        init(getParentId());
    	
    	String commandId = getParentId() + CONTROLS_FORM + "testFilterMethod";
    	clickCommandAndWait(commandId);
    	
    	testRowsCount(2);
    	testSorting(5, new String [] { "4", "5" });
    	testSorting(3, new String [] { "2", "3" });
    	
    }
    
    @Test
    public void testNestedInput(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
    	
    	String inputId = dataTableId + ":0:input";
    	setValueById(inputId, "One");
    	
    	inputId = dataTableId + ":1:input";
    	setValueById(inputId, "Two");
    	
      	inputId = dataTableId + ":2:input";
    	setValueById(inputId, "Three");
    	
    	clickCommandAndWait(commandId);
    	
    	AssertValueEquals(dataTableId + ":0:input", "One", "Nested input does not work.");
    	AssertValueEquals(dataTableId + ":1:input", "Two", "Nested input does not work.");
    	AssertValueEquals(dataTableId + ":2:input", "Three", "Nested input does not work.");
   	
    }
    
    @Test
    public void testFacetsAndColumnsHeaders(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
        
        String title = getHeaderFacet(1);
        Assert.assertEquals(title, "Data Table header 1", "DataTable header missed");
        title = getHeaderFacet(2);
        Assert.assertEquals(title, "Data Table header 2", "DataTable header missed");
        title = getHeaderFacet(3);
        Assert.assertEquals(title, "Data Table header 3", "DataTable header missed");
   	  	title = getFooterFacet();
        Assert.assertEquals(title, "Data Table footer", "Data Table footer missed");
        
       
        for (int i = 1; i < 6; i++) {
        	String headerId = dataTableId + ":col" + i + "header:sortDiv";
        	AssertTextEquals(headerId, "Column" + i, "Column ["+i+"] header incorrect");
        }
        
        testColspanRowspanBreakBefore();
    }
    
    @Test
    public void testAjaxKeysAttribute(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
        
      	String [] oldValues = new String[6];
      	for (int i = 0; i < 6; i++) {	
      		String id = dataTableId + ":" +i + ":date";
      		oldValues[i] = getTextById(id);
      	}      
           
        pause(1500, dataTableId);
        String linkId = dataTableId + ":0:link";
        clickAjaxCommandAndWait(linkId);
        
        String [] newValues = new String[6];
      	for (int i = 0; i < 6; i++) {	
      		String id = dataTableId + ":" +i + ":date";
      		newValues[i] = getTextById(id);
      	}     
      	
      	
      	//equal
      	
      	List<Integer> notEquals = new ArrayList<Integer>();
      	
      	for (int i = 0; i < 6; i++) {
      		if (!oldValues[i].equals(newValues[i])) {
      			notEquals.add(i);
      		}
      	}
      	
      	if (notEquals.size() != 3 || !notEquals.get(0).equals(0)
      							  || !notEquals.get(1).equals(2)
      							  || !notEquals.get(2).equals(4)) {
      							  Assert.fail("Ajax keys attribute does not work. [0,2,4] rows should be reRendered only. But was: " + toString(notEquals));
      	}
        
    }
    
    
    
    @Test
    public void testVars(Template template) {
    	renderPage(template, RESET_METHOD);
        init(getParentId());
        
        for (int i = 0; i < 6; i++) {
        	String rowNId = dataTableId + ":" + i + ":rowN";
        	String stateId = dataTableId + ":" + i + ":state";
        	AssertTextEquals(rowNId, String.valueOf(i), "Row var attribute does not work");
        	Assert.assertNotNull(getTextById(stateId), "State request var does not present"); 
        }
        
        String commandId = getParentId() + CONTROLS_FORM + "testComponentState";
        try {
        	clickCommandAndWait(commandId);
        }catch (Exception e) {
			Assert.fail("Component state does not work. State object does not references to HTMLDataTable");
		}
        
    }
    
    
    @Test
    public void testHTMLAndRendered (Template template){
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testStyleAndClasses(dataTableClassNames, styleAttributes);
    	autoTester.testRendered();    	
    }
    
    @Test
    public void testRowKeyConverter (Template template) {
    	renderPage(template, RESET_METHOD);
    	init(getParentId());
    	
    	String commandId = getParentId() + CONTROLS_FORM + "testRowKeyConverter";
    	clickCommandAndWait(commandId);
    	
    	String message = "RowKeyConverter generates incorrect ids";
    	AssertPresent(dataTableId + ":00:col1", message);
    	AssertPresent(dataTableId + ":00:col2", message);
    	AssertPresent(dataTableId + ":00:col3", message);
    	AssertPresent(dataTableId + ":00:col4", message);
    	AssertPresent(dataTableId + ":00:col5", message);
    	AssertPresent(dataTableId + ":00:v1", message);
    	AssertPresent(dataTableId + ":00:v2", message);
    	AssertPresent(dataTableId + ":00:v3", message);
    	AssertPresent(dataTableId + ":00:v4", message);
    	AssertPresent(dataTableId + ":00:v5", message);
    	
    	AssertPresent(dataTableId + ":11:v2", message);
    	AssertPresent(dataTableId + ":22:v3", message);
    	AssertPresent(dataTableId + ":33:v4", message);
    	AssertPresent(dataTableId + ":44:input", message);
    	AssertPresent(dataTableId + ":55:link", message);    	
    	
    }
    
    private void testColspanRowspanBreakBefore() {
    	String id = dataTableId + ":header1";
    	String colspan = getColspan(id);
    	String rowspan = null;
    	Assert.assertEquals(colspan, "3", "Colspan does not work");
    	
    	id = dataTableId + ":header2";
    	colspan = getColspan(id);
    	rowspan = getRowspan(id);
    	Assert.assertEquals(colspan, "2", "Colspan does not work");
    	Assert.assertEquals(rowspan, "2", "Rowspan does not work");
    	
    	id = dataTableId + ":header3";
    	colspan = getColspan(id);
    	Assert.assertEquals(colspan, "3", "Colspan does not work");
    	
    	String parentTagName = runScript(getElementById(id) + ".parentNode.parentNode.tagName;");
    	Assert.assertEquals(parentTagName.toLowerCase(), "tr", "Breakbefore does not work");
    	
    	
    }
   
       
    private void testRowsCount(int rows) {
    	assertRowsCount(rows, dataTableId);
    }
    
    private void testSorting(int columns, String [] values) {
    	columns--;
    	int rowCount = values.length;
    	assertRowsCount(rowCount, dataTableId);
    	
    	String [] actualValues = new String[rowCount];
    	for (int i = 0; i < rowCount; i++) {
    		actualValues[i] = getCellText(i, columns);
    	}
    	
    	for (int i = 0; i < rowCount; i++) {
    		if (!values[i].equals(actualValues[i])) {
    			Assert.fail("Columns was sorted incorectly. Expected: " + toString(values) + " But was: " + toString(actualValues));
    		}
    	}
    }
    
    private void clickSort(int column) {
    	String commandId = dataTableId + ":col" + column + "header";
    	clickAjaxCommandAndWait(commandId);
    }
    
    private String getCellText(int row, int cell) {
    	StringBuffer b = new StringBuffer(getElementById(dataTableBodyId));
    	b.append(".rows[").append(row).append("].cells[").append(cell).append("].firstChild.innerHTML");
    	return runScript(b.toString());    	
    }
    
    private void testSortIcon(int column, Ordering ordering) {
		String id = dataTableId + ":col" + column + "header:sortDiv";
		StringBuffer b = new StringBuffer(getElementById(id));
		b.append(".firstChild.childNodes[1].src");
		String src = runScript(b.toString());
		if (ordering == Ordering.UNSORTED) {
			Assert.assertTrue(src.indexOf("DataTableIconSortNone") != -1,
					"Column [" + column + "] sort image is invalid");
		} else if (ordering == Ordering.ASCENDING) {
			Assert.assertTrue(src.indexOf("DataTableIconSortAsc") != -1,
					"Column [" + column + "] sort image is invalid");
		} else if (ordering == Ordering.DESCENDING) {
			Assert.assertTrue(src.indexOf("DataTableIconSortDesc") != -1,
					"Column [" + column + "] sort image is invalid");
		}
	}
	
	private String toString(String[] ss) {
		StringBuffer b = new StringBuffer();
		for (String s : ss) {
			b.append(s).append(",");
		}
		return b.toString();
	}
	
	private String toString(List<?> ss) {
		StringBuffer b = new StringBuffer();
		for (Object s : ss) {
			b.append(s).append(",");
		}
		return b.toString();
	}

	private String getHeaderFacet(int i) {
		String id = dataTableId + ":header" + i;
		return getTextById(id);
	}
	
	private String getFooterFacet() {
		String id = dataTableId + ":footer";
		return getTextById(id);
	}
	

	private String getColspan(String headerID) {
		return runScript(getElementById(headerID) + ".parentNode.colSpan;");
	}
	
	private String getRowspan(String headerID) {
		return runScript(getElementById(headerID) + ".parentNode.rowSpan;");
	}
    
    
 
}
