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
import org.testng.annotations.Test;

public class ToolBarTest extends SeleniumTestBase {

	private final static String TOOL_BAR_GROUP_TEST_URL = "pages/toolBar/toolBarGroupTest.xhtml"; 
	
	static final Map<String, String> TOOL_BAR_STYLES = new  HashMap<String, String>();
	static {
		TOOL_BAR_STYLES.put("font-size", "16px");
	}

    

    /* Tab panel group/item separator constants */
    private final static String LINE_SEPARATOR = "line";

    private final static String DISC_SEPARATOR = "disc";

    private final static String GRID_SEPARATOR = "grid";

    private final static String SQUARE_SEPARATOR = "square";

    private final static String NONE_SEPARATOR = "none";
    
    private final static String RESET_METHOD = "#{toolBarBean.reset}";

    private String formId;
    private String toolBarId;
    private String groupContentPrefix;
    private String renderedControl;
    private String submitControl;
    
    private void initIds(String parentId){
    	formId = parentId + "_form:";
    	toolBarId = formId + "toolBar";
    	groupContentPrefix = formId + "groupContent";
    	renderedControl = formId + "rendered";
    	submitControl = formId + "submit";
    }
    
    @Test
    public void testToolBarComponentLayout(Template template) {
        renderPage(template);

        writeStatus("Check component layout");

        initIds(getParentId());

        AssertPresent(toolBarId, "Tool bar component not present on the page");
        AssertVisible(toolBarId, "Tool bar component not visible on the page");
        assertClassNames(toolBarId, new String[]{"rich-toolbar"}, "Tool bar component not have valid style class", true);
        
        assertColumnsCount(8, toolBarId, "Tool bar should contains 8 elements");
    }
    
    @Test
    public void testSeparators(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId());

		//test group separators
		assertColumnsCount(8, toolBarId, "Tool bar should contains 8 elements");

		clickAjaxCommandAndWait(formId + "gs_" + LINE_SEPARATOR);
		assertColumnsCount(9, toolBarId, "Tool bar should contains 9 elements");
		
		clickAjaxCommandAndWait(formId + "gs_" + DISC_SEPARATOR);
		assertColumnsCount(9, toolBarId, "Tool bar should contains 9 elements");
		
		clickAjaxCommandAndWait(formId + "gs_" + GRID_SEPARATOR);
		assertColumnsCount(9, toolBarId, "Tool bar should contains 9 elements");
		
		clickAjaxCommandAndWait(formId + "gs_" + SQUARE_SEPARATOR);
		assertColumnsCount(9, toolBarId, "Tool bar should contains 9 elements");
		
		clickAjaxCommandAndWait(formId + "gs_" + NONE_SEPARATOR);
		assertColumnsCount(8, toolBarId, "Tool bar should contains 8 elements");

		//test items separators
		
		clickAjaxCommandAndWait(formId + "gis_" + LINE_SEPARATOR);
		assertColumnsCount(12, toolBarId, "Tool bar should contains 12 elements");
		
        clickAjaxCommandAndWait(formId + "gis_" + DISC_SEPARATOR);
        assertColumnsCount(12, toolBarId, "Tool bar should contains 12 elements");
        
        clickAjaxCommandAndWait(formId + "gis_" + GRID_SEPARATOR);
        assertColumnsCount(12, toolBarId, "Tool bar should contains 12 elements");
        
        clickAjaxCommandAndWait(formId + "gis_" + SQUARE_SEPARATOR);
        assertColumnsCount(12, toolBarId, "Tool bar should contains 12 elements");
        
        clickAjaxCommandAndWait(formId + "gis_" + NONE_SEPARATOR);
        assertColumnsCount(8, toolBarId, "Tool bar should contains 8 elements");
	}
    
    @Test
    public void testItemsAlignment(Template template) {
		renderPage(TOOL_BAR_GROUP_TEST_URL, template, RESET_METHOD);
		initIds(getParentId());

		AssertTextEquals(groupContentPrefix + "1", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "2", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "3", "Group1", "Toolbar group should present on page");
		assertAttributeContains(getToolBarTdXpath(toolBarId, 4), "style", "width: 100%", null);
		
		clickAjaxCommandAndWait(formId + "location");
		
		assertAttributeContains(getToolBarTdXpath(toolBarId, 1), "style", "width: 100%", null);
		AssertTextEquals(groupContentPrefix + "1", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "2", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "3", "Group1", "Toolbar group should present on page");
		
	}
    
    @Test
    public void testRendered(Template template){
    	AutoTester tester = getAutoTester(this);
    	tester.renderPage(template, null);
    	
    	tester.testRendered();
    }
    
    @Test
    public void testStylesAndClassesAndHtmlAttributes(Template template){
    	AutoTester tester = getAutoTester(this);
    	tester.renderPage(template, null);
    	
    	tester.testStyleAndClasses(new String [] {"rich-toolbar", "styleClass"}, TOOL_BAR_STYLES);
    	tester.testHTMLEvents();
    }
    
    @Test
    public void testToolBarGroupRendered(Template template){
    	renderPage(TOOL_BAR_GROUP_TEST_URL, template, RESET_METHOD);
    	initIds(getParentId());
    	AssertPresent(groupContentPrefix + "1", "Toolbar group should present on page");
    	AssertPresent(groupContentPrefix + "2", "Toolbar group should present on page");
    	AssertPresent(groupContentPrefix + "3", "Toolbar group should present on page");
    	AssertTextEquals(groupContentPrefix + "1", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "2", "Group1", "Toolbar group should present on page");
		AssertTextEquals(groupContentPrefix + "3", "Group1", "Toolbar group should present on page");
    	clickById(renderedControl);
    	clickCommandAndWait(submitControl);
    	AssertNotPresent(groupContentPrefix + "1", "Toolbar group should not present on page");
    	AssertNotPresent(groupContentPrefix + "2", "Toolbar group should not present on page");
    	AssertNotPresent(groupContentPrefix + "3", "Toolbar group should not present on page");
    	
    }
    
    @Test
    public void testToolBarGroupStylesAndClassesAndHtmlAttributes(Template template){
    	renderPage(TOOL_BAR_GROUP_TEST_URL, template, RESET_METHOD);
    	initIds(getParentId());
    	AssertPresent(groupContentPrefix + "1", "Toolbar group should present on page");
    	AssertPresent(groupContentPrefix + "2", "Toolbar group should present on page");
    	AssertPresent(groupContentPrefix + "3", "Toolbar group should present on page");
    	
    	assertClassNames(getToolBarTdXpath(toolBarId, 1), new String[]{"styleClass"}, "styleClass attribute not work for Tool bar group", false);
    	assertClassNames(getToolBarTdXpath(toolBarId, 2), new String[]{"styleClass"}, "styleClass attribute not work for Tool bar group", false);
    	assertClassNames(getToolBarTdXpath(toolBarId, 3), new String[]{"styleClass"}, "styleClass attribute not work for Tool bar group", false);
    	
    	assertAttributeContains(getToolBarTdXpath(toolBarId, 1), "style", "font-size: 16px", null);
    	assertAttributeContains(getToolBarTdXpath(toolBarId, 2), "style", "font-size: 16px", null);
    	assertAttributeContains(getToolBarTdXpath(toolBarId, 3), "style", "font-size: 16px", null);
    	
    }

    private String getToolBarTdXpath(String toolBarId, int item){
    	return "//*[@id='" + toolBarId + "']/tbody/tr/td[" + item + "]";
    }

    @Override
    public String getTestUrl() {
        return "pages/toolBar/toolBarTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/toolBar/toolBarAutoTest.xhtml";
    }

}
