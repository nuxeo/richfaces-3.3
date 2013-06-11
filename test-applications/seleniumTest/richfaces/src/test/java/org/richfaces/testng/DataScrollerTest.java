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
import java.util.List;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * DataScroller selenium test class
 * @author Andrey Markavtsov
 *
 */
public class DataScrollerTest extends SeleniumTestBase {
	
	static String TEST__URL = "pages/dataScroller/dataScroller.xhtml";
	
	private static String TEST_PAIRED_SCROLLERS_URL = "pages/dataScroller/doubleDataScroller.xhtml";
	private static String TEST_PAIRED_SCROLLERS_WITH_PAGE_BINDING_URL = "pages/dataScroller/doubleDataScrollerWithPageBinding.xhtml";
	private static String TEST_SCROLLER_SCROLLER_INSIDE_DATATABLE_FACET_URL = "pages/dataScroller/dataScrollerInDataTableFacet.xhtml";
	
	static final String RESET_METHOD_ME = "#{dataScrollerBean.reset}";
	
	String dataScrollerId;
	
	String dataScrollerTableId;
	
	String secondDataScrollerId;
	
	String secondDataScrollerTableId;
	
	String dataScrollerInFacetId;
	
	String dataScrollerTableInFacetId;
	
	String dataTableId;
	
	static final String [] activePageClasses = new String[] {
		"dr-dscr-act","rich-datascr-act"
	};
	
	static final String [] inactivePageClasses = new String[] {
			"dr-dscr-inact","rich-datascr-inact"
	};
		
	static final String [] activeForwardClasses = new String[] {
			"dr-dscr-button","rich-datascr-button"
	};

	static final String [] inactiveForwardClasses = new String [] {
		"dr-dscr-button-dsbld",
		"rich-datascr-button-dsbld",
		"dr-dscr-button",
		"rich-datascr-button"
	};
	
	@SuppressWarnings("serial")
	static final List<String> events = new ArrayList<String>(){
		@Override
		public void clear() {
			
		}
	};
	static {
		events.add("onpagechange");
	}
	
	@Test
	public void testPagesVariables(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		String commandId = getParentId() + "_controls:pageVars";
		clickCommandAndWait(commandId);
		
		String activePageId = getParentId() + "_data:activePage"; 
		String pageCountId = getParentId() + "_data:pagesCount";
		
		AssertTextEquals(activePageId, "Active page: 1", "PageIndexVar does not work.");
		AssertTextEquals(pageCountId, "Count of pages: 10", "PagesVar does not work.");
		
		clickControl(4, dataScrollerTableId);
		
		AssertTextEquals(activePageId, "Active page: 2", "PageIndexVar does not work.");
		AssertTextEquals(pageCountId, "Count of pages: 10", "PagesVar does not work.");

	}
	
	
	@Test
	public void testCancelableOnpagechnage(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		String commandId = getParentId() + "_controls:onpagechange";
		clickCommandAndWait(commandId);
		
		assertClassNames(getPageLinkRefScript(4, dataScrollerTableId), inactivePageClasses, "'2' Link should be inactive", false);
		clickControlNotWait(4);
		assertClassNames(getPageLinkRefScript(4, dataScrollerTableId), inactivePageClasses, "Cancelable onpagechnage does not work. Page should not be switched. .'2' Link should be inactive", false);

	}
	
	@Test
	public void testRendered(Template template){
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testRendered();
		
	}
	
	@Test
	public void testReRendered(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testReRender();
		
	}
    
	@Test
	public void testActionListener(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testActionListener();
	}
	
	@Test
	public void testAjaxSingle(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testAjaxSingle();
	}
    
	@Test
	public void testImmediate(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testImmediate();
	}
	
	@Test
	public void testImmediateWithExternalValidation(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testImmediateWithExternalValidationFailed();
	}
	
	@Test
	public void testBypassUpdate(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);
		autoTester.testBypassUpdate();
	}
    
	@Test
	public void testExtrenalValidationFailure(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);	
		autoTester.testExtrenalValidationFailure();
	}
	
	@Test
	public void testAjaxSingleWithExtrenalValidationFailure(Template template) {
		AutoTester autoTester = getAutoTester(this);
		dataScrollerTableId = autoTester.getClientId(AutoTester.COMPONENT_ID + "_table", template);
		autoTester.renderPage(template, RESET_METHOD_ME);	
		autoTester.testAjaxSingleWithProcesExternalValidation(true);
	}
	
	@Test
	public void testLimitToList(Template template) {
		renderPage(template,RESET_METHOD_ME);
		String parentId = getParentId();
		String formId = parentId + "_data:";
		
		// init id's
		String limitCheckBox = formId + "limit_checkbox";
		String singleCheckBox = formId + "single_checkbox";
		String limitApplyButton = formId + "limit_apply";
		String limitResetButton = formId + "limit_reset_button";
		String limitContentInput1 = formId + "limit_content_input1";
		String limitContentListener1 = formId + "limit_content_listener1";
		String limitContentInput2 = formId + "limit_content_input2";
		String limitContentListener2 = formId + "limit_content_listener2";
		
		dataScrollerTableId = formId + "scroller_table";
		
		// reset listeners status
		AssertRendered(limitResetButton);
		clickAjaxCommandAndWait(limitResetButton);
		
		//ajaxSingle='false'
		clickById(singleCheckBox);
		clickAjaxCommandAndWait(limitApplyButton);
		
		// check listeners default status
		AssertRendered(limitContentListener1);
		AssertTextEquals(limitContentListener1, "reset");
				
		AssertRendered(limitContentListener2);
		AssertTextEquals(limitContentListener2, "reset");
				
		// change value
		AssertRendered(limitContentInput1);
		type(limitContentInput1, "content11");
		
		AssertRendered(limitContentInput2);
		type(limitContentInput2, "content22");
		
		clickControl(8,dataScrollerTableId);
		
		AssertTextEquals(limitContentListener1, "invoked");
		AssertTextEquals(limitContentListener2, "invoked");
		
		//reset listeners status again 
		clickAjaxCommandAndWait(limitResetButton);
		
		//set limitToList='true' 
		clickById(limitCheckBox);
				
		// change value
		AssertRendered(limitContentInput1);
		type(limitContentInput1, "content11");
		
		AssertRendered(limitContentInput2);
		type(limitContentInput2, "content22");
		
		clickControl(9,dataScrollerTableId);
		
		AssertTextEquals(limitContentListener1, "invoked");
		AssertTextEquals(limitContentListener2, "reset");
			
	}
		
	
	@Override
	public void sendAjax() {
		clickControl(4, dataScrollerTableId);
		
	}
	
	@Override
	public String[] getReRendersId() {
		return new String [] {AutoTester.COMPONENT_ID, "tbl"};
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/dataScroller/dataScrollerAjax.xhtml";
	}
	
	
	
		
	@Test
	public void testDataScrollerRendering(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testRendering();
		testHTMLEvent(dataScrollerId);
	}
	
	@Test
	public void testDataScrollerPagination(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testScrollers(false, dataScrollerId, dataScrollerTableId, null, null);						
	}
	
	@Test
	public void testMaxPagesAttr(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testPageCount(16, dataScrollerTableId);
		
		String buttonId = getParentId() + "_controls:maxP";
		clickCommandAndWait(buttonId);
		
		testPageCount(11, dataScrollerTableId);
		
		String text = selenium.getTable("id=" + dataScrollerTableId + ".0.5");
		Assert.assertEquals(text, "3", "'3' link should be in the middle");
		
		clickControl(7, dataScrollerTableId);
		testPageCount(11, dataScrollerTableId);
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.5");
		Assert.assertEquals(text, "5", "'5' link should be in the middle");
		testData(1, "Page 5");
		
		
		clickControl(10, dataScrollerTableId);
		testPageCount(11, dataScrollerTableId);
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.7");
		Assert.assertEquals(text, "10");
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.3");
		Assert.assertEquals(text, "6");
		testData(1, "Page 10");
		
		clickControl(3, dataScrollerTableId);
		testPageCount(11, dataScrollerTableId);
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.5");
		Assert.assertEquals(text, "6");
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.3");
		Assert.assertEquals(text, "4");
		testData(1, "Page 6");
		
		clickControl(1, dataScrollerTableId);
		clickControl(1, dataScrollerTableId);
		testPageCount(11, dataScrollerTableId);
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.5");
		Assert.assertEquals(text, "4");
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.3");
		Assert.assertEquals(text, "2");
		testData(1, "Page 4");
				
	
	}
	
	
	@Test
	public void testChangedData(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		String tableRowsId = getParentId() + "_controls:tableRow";
		setValueById(tableRowsId, "2");
		
		String applyId = getParentId() + "_controls:apply";
		clickCommandAndWait(applyId);
		
		testPageCount(11, dataScrollerTableId);
		assertRowsCount(2, dataTableId);
		
		clickControl(7, dataScrollerTableId);
		String text = selenium.getTable("id=" + dataScrollerTableId + ".0.7");
		Assert.assertEquals(text, "5", "5th page should be displayed");
		assertRowsCount(2, dataTableId);
		
		clickControl(3, dataScrollerTableId);
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.3");
		Assert.assertEquals(text, "1", "1st page should be displayed");
		assertRowsCount(2, dataTableId);
		
		
	}
	
	@Test
	public void testRenderIfSinglePageAttribute(Template template) {
		renderPage(template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		AssertPresent(dataScrollerId);

		String tableRowsId = getParentId() + "_controls:tableRow";
		setValueById(tableRowsId, "10");

		String applyId = getParentId() + "_controls:apply";
		clickCommandAndWait(applyId);

		AssertVisible(dataScrollerId);

		String commandId = getParentId() + "_controls:changeRenderIfSinglePage";
		clickCommandAndWait(commandId);

		AssertNotVisible(dataScrollerId);

		clickCommandAndWait(commandId);

		AssertVisible(dataScrollerId);
	}
	
	@Test
	public void testPairedDataScrollers(Template template) {
		renderPage(TEST_PAIRED_SCROLLERS_URL, template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testScrollers(true, dataScrollerId, dataScrollerTableId, secondDataScrollerId, secondDataScrollerTableId);		
	}
	
	@Test
	public void testPairedDataScrollersWithSharedBinding(Template template) {
		renderPage(TEST_PAIRED_SCROLLERS_WITH_PAGE_BINDING_URL, template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testScrollers(true, dataScrollerId, dataScrollerTableId, secondDataScrollerId, secondDataScrollerTableId);	
		
		// change page attribute through shared binding
		String commandId = getParentId() + "_controls:changePage";
		clickCommandAndWait(commandId);
		testData(1, "Page 5");
		assertClassNames(getPageLinkRefScript(7, dataScrollerTableId), activePageClasses, "'5' link should current", false);	
		assertClassNames(getPageLinkRefScript(7, secondDataScrollerTableId), activePageClasses, "'5' link should current", false);

		
	}
	
	@Test
	public void testDataScrollerInsideDataTableFacet(Template template) {
		renderPage(TEST_SCROLLER_SCROLLER_INSIDE_DATATABLE_FACET_URL, template, RESET_METHOD_ME);
		initIDs(getParentId(), template);
		
		testScrollers(false, dataScrollerInFacetId, dataScrollerTableInFacetId, null, null);	
	}
	
	private void testScrollers(boolean paired, String dataScrollerId, String dataScrollerTableId, String secondDataScrollerId, String secondDataScrollerTableId){
		AssertPresent(dataScrollerId);
		if(paired){
			AssertPresent(secondDataScrollerId);
		}

		testPageCount(16, dataScrollerTableId);
		
		clickControl(5, dataScrollerTableId);
		testData(1, "Page 3");
		
		assertClassNames(getPageLinkRefScript(5, dataScrollerTableId), activePageClasses, "'3' link should current", false);
		assertClassNames(getPageLinkRefScript(3, dataScrollerTableId), inactivePageClasses, "'1' link should be inactive", false);
		assertClassNames(getPageLinkRefScript(0, dataScrollerTableId), activeForwardClasses, "'««' control should be accessible", false);
		assertClassNames(getPageLinkRefScript(1, dataScrollerTableId), activeForwardClasses, "'«' control should be accessible", false);
		
		if(paired){
			assertClassNames(getPageLinkRefScript(5, secondDataScrollerTableId), activePageClasses, "'3' link should current", false);
			assertClassNames(getPageLinkRefScript(3, secondDataScrollerTableId), inactivePageClasses, "'1' link should be inactive", false);
			assertClassNames(getPageLinkRefScript(0, secondDataScrollerTableId), activeForwardClasses, "'««' control should be accessible", false);
			assertClassNames(getPageLinkRefScript(1, secondDataScrollerTableId), activeForwardClasses, "'«' control should be accessible", false);
		}
		
		if(paired){
			clickControl(10, secondDataScrollerTableId);
		}else{
			clickControl(10, dataScrollerTableId);
		}
		testData(1, "Page 8");
		assertClassNames(getPageLinkRefScript(10, dataScrollerTableId), activePageClasses, "'8' link should current", false);
		if(paired){
			assertClassNames(getPageLinkRefScript(10, secondDataScrollerTableId), activePageClasses, "'8' link should current", false);
		}
		
		clickControl(15, dataScrollerTableId);
		testData(1, "Page 10");
		assertClassNames(getPageLinkRefScript(15, dataScrollerTableId), inactiveForwardClasses, "'»»' control should be inactive", false);
		assertClassNames(getPageLinkRefScript(14, dataScrollerTableId), inactiveForwardClasses, "'»' control should be inactive", false);
		if(paired){
			assertClassNames(getPageLinkRefScript(15, secondDataScrollerTableId), inactiveForwardClasses, "'»»' control should be inactive", false);
			assertClassNames(getPageLinkRefScript(14, secondDataScrollerTableId), inactiveForwardClasses, "'»' control should be inactive", false);
		}
		
		if(paired){
			clickControl(1, secondDataScrollerTableId);
		}else{
			clickControl(1, dataScrollerTableId);
		}
		testData(1, "Page 9");
		assertClassNames(getPageLinkRefScript(0, dataScrollerTableId), activeForwardClasses, "'»»' control should be active", false);
		assertClassNames(getPageLinkRefScript(1, dataScrollerTableId), activeForwardClasses, "'»' control should be active", false);
		assertClassNames(getPageLinkRefScript(11, dataScrollerTableId), activePageClasses, "'9' link should be current", false);
		if(paired){
			assertClassNames(getPageLinkRefScript(0, secondDataScrollerTableId), activeForwardClasses, "'»»' control should be active", false);
			assertClassNames(getPageLinkRefScript(1, secondDataScrollerTableId), activeForwardClasses, "'»' control should be active", false);
			assertClassNames(getPageLinkRefScript(11, secondDataScrollerTableId), activePageClasses, "'9' link should be current", false);
		}
		
		clickControl(0, dataScrollerTableId);
		testData(1, "Page 1");
		assertClassNames(getPageLinkRefScript(3, dataScrollerTableId), activePageClasses, "'1' link should be current", false);
		assertClassNames(getPageLinkRefScript(4, dataScrollerTableId), inactivePageClasses, "'2' link should be inactive", false);
		if(paired){
			assertClassNames(getPageLinkRefScript(3, secondDataScrollerTableId), activePageClasses, "'1' link should be current", false);
			assertClassNames(getPageLinkRefScript(4, secondDataScrollerTableId), inactivePageClasses, "'2' link should be inactive", false);
		}

		if(paired){
			clickControl(14, secondDataScrollerTableId);
		}else{
			clickControl(14, dataScrollerTableId);
		}
		testData(1, "Page 2");
		assertClassNames(getPageLinkRefScript(3, dataScrollerTableId), inactivePageClasses, "'1' link should be inactive", false);
		assertClassNames(getPageLinkRefScript(4, dataScrollerTableId), activePageClasses, "'2' link should be current", false);
		if(paired){
			assertClassNames(getPageLinkRefScript(3, secondDataScrollerTableId), inactivePageClasses, "'1' link should be inactive", false);
			assertClassNames(getPageLinkRefScript(4, secondDataScrollerTableId), activePageClasses, "'2' link should be current", false);
		}

	}

	private void initIDs(String parentId, Template template) {
		dataScrollerId = parentId + "_data:scroller";
		dataScrollerTableId = parentId + "_data:scroller_table";
		
		secondDataScrollerId = parentId + "_data:scroller2";
		secondDataScrollerTableId = parentId + "_data:scroller2_table";
		
		dataScrollerInFacetId = parentId + "_data:tbl:scroller";
		dataScrollerTableInFacetId = parentId + "_data:tbl:scroller_table";
		
		dataTableId =  parentId + "_data:tbl";
	}
	
	private void testHTMLEvent(String id) {
		selenium.click(id);	
		selenium.mouseDown(id);
		selenium.mouseMove(id);
		selenium.mouseOut(id);
		selenium.mouseOver(id);
		selenium.mouseUp(id);
		
		assertEvent("onclick");
		assertEvent("onmousedown");
		assertEvent("onmousemove");
		assertEvent("onmouseout");
		assertEvent("onmouseover");
		assertEvent("onmouseup");
	}
	
	private void testRendering() {
		assertClassNames(dataScrollerId,new String [] 
		                                            {"dr-dscr",
		                                             "rich-datascr"},
		                 "DataScroller rendering failed: ", true);
		
		assertClassNames(dataScrollerTableId,new String [] 
		                                            {"dr-dscr-t",
		                                             "rich-dtascroller-table"},
		                 "DataScroller rendering failed: ", true);

		testPageCount(16, dataScrollerTableId);
		testControls();
		testData(1,"Page 1");
		checkCustomFacets();
	}
	

	private void testPageCount(int n, String dataScrollerTableId) {
		assertColumnsCount(n, dataScrollerTableId, "DataScroller inner table contains invalid columns count");
	}
	
	private void testControls() {
		// Check '««' link 
		String text = selenium.getTable("id=" + dataScrollerTableId + ".0.0");
		Assert.assertEquals("««", text, "DataScroller does not contain '««' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(0, dataScrollerTableId),inactiveForwardClasses,
		                 "DataScroller rendering failed: ", false);
		
		// Check '«' link
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.1");
		Assert.assertEquals("«", text, "DataScroller does not contain '«' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(1, dataScrollerTableId),inactiveForwardClasses,
		                 "DataScroller rendering failed: ", false);
		
		// Check '1' link
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.3");
		Assert.assertEquals("1", text, "DataScroller does not contain '1' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(3, dataScrollerTableId),activePageClasses,
		 		                 "DataScroller rendering failed: ", false);
		
		// Check '5' link
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.7");
		Assert.assertEquals("5", text, "DataScroller does not contain '5' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(7, dataScrollerTableId),inactivePageClasses,
		 		                 "DataScroller rendering failed: ", false);
		
		// Check '»' link
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.14");
		Assert.assertEquals("»", text, "DataScroller does not contain '»' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(14, dataScrollerTableId),activeForwardClasses,
		 		                 "DataScroller rendering failed: ", false);
		
		// Check '»»' link
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.15");
		Assert.assertEquals("»»", text, "DataScroller does not contain '»»' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(15, dataScrollerTableId),activeForwardClasses,
		 		                 "DataScroller rendering failed: ", false);

	}
	
	private void checkCustomFacets() {
		String text = selenium.getTable("id=" + dataScrollerTableId + ".0.2");
		Assert.assertEquals("", text, "'Previous' facet should be rendered yet");
		assertClassNames(getPageLinkRefScript(2, dataScrollerTableId),inactiveForwardClasses,
		 		                 "DataScroller rendering failed: ", false);
		
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.13");
		Assert.assertEquals("Next", text, "'Next' facet didnot rendered. DataScroller does not contain 'Next' link or its position is invalid");
		assertClassNames(getPageLinkRefScript(13, dataScrollerTableId),activeForwardClasses,
		 		                 "DataScroller rendering failed: ", false);
		clickControl(4, dataScrollerTableId);
		
		text = selenium.getTable("id=" + dataScrollerTableId + ".0.2");
		Assert.assertEquals("Previous", text, "'Previous' facet didnot be rendered.");
		assertClassNames(getPageLinkRefScript(2, dataScrollerTableId),activeForwardClasses,
		 		                 "DataScroller rendering failed: ", false);
			
		
	}
	
	private void testData(int rowCount, String data) {
		assertRowsCount(rowCount, dataTableId);
		
		for (int i=1; i <= rowCount; i++) {
			String text = selenium.getTable("id=" + dataTableId + "."+rowCount+".1");
			Assert.assertEquals(data, text,"Data Table content is invalid");
		}
	}
	
	private String getPageLinkRefScript(int n, String dataScrollerTableId) {
		return "//*[@id='" + dataScrollerTableId + "']/tbody/tr/td[" + (n + 1) + "]";
	}
	
	private void clickControl(int n, String dataScrollerTableId) {
		selenium.click(getPageLinkRefScript(n, dataScrollerTableId));
		waitForAjaxCompletion();
		assertEvent("onpagechange");
	}
		
	private void clickControlNotWait(int n) {
		selenium.click(getPageLinkRefScript(n, dataScrollerTableId));	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.SeleniumTestBase#getTestUrl()
	 */
	@Override
	public String getTestUrl() {
		return TEST__URL;
	}
	
	@Override
	public Object [][] getTestDependedTemplates() {
		return new Object [][] { { Template.SIMPLE }, { Template.MODAL_PANEL } };
	}
	
	@Override
	public boolean useDefaultTemplates() {
		return false;
	}
}

