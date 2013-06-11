/**
 * 
 */
package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andrey Markavtsov
 *
 */
public class ColumnGroupTest extends SeleniumTestBase {
	
	static final String RESET_METHOD = "#{columnGroupBean.reset}";
	static final String RESET_METHOD_FOR_RENDERED_TEST = "#{columnGroupBean.prepareRenderedTest}";
	
	
	@Test
	public void testRenderedAttribute(Template template) {
		renderPage(template, RESET_METHOD_FOR_RENDERED_TEST);
		String tableId = getParentId() + "table";
		
		int headerContentCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/*").intValue();
		
		if (headerContentCount != 0) {
			Assert.fail("Rendered attribute does not work. Table header should not be output to client.");
		}
	}
	
	@Test
	public void testStylesAndClasses(Template template) {
		renderPage(template, RESET_METHOD);
		String tableId = getParentId() + "table";
		
		// Check rowClass attribute
		String rowClass = selenium.getAttribute(getTableXpath(tableId) + "/thead/tr/@class");
		if (!rowClass.contains("rowClass")) {
			Assert.fail("RowClasses attribute does not work. 'rowClass' css class should be rendered for header row");
		}
		
		// Check style attribute
		assertStyleAttributeContains(getTableXpath(tableId) + "/thead/tr", "color: green", "Style attribute does not work ");
		
		// Check title attribute 
		String title = selenium.getAttribute(getTableXpath(tableId) + "/thead/tr/@title");
		Assert.assertEquals(title, "title", "Title was not output to client");
		
		// Check columnClasses attribute
		String columnClass = selenium.getAttribute(getTableXpath(tableId) + "/thead/tr/th[1]/@class");
		if (!columnClass.contains("columnClass")) {
			Assert.fail("ColumnClasses attribute does not work. 'columnClass' css class should be rendered for header cell");
		}
		
	}
	
	
	@Test
	public void testOutput(Template template) {
		renderPage(template, RESET_METHOD);
		
		String tableId = getParentId() + "table";
		
		// Check breakBefore attribute
		int headerRowsCount =  selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr").intValue();
		int firstRowColsCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr[1]/th").intValue();
		int secondRowColsCount = selenium.getXpathCount("//table[@id='"+tableId+"']/thead/tr[2]/th").intValue();
		
		if (headerRowsCount != 2 || firstRowColsCount != 4 || secondRowColsCount != 1) {
			Assert.fail("ColumnGroup component works unexpectly. Table header should have two rows. The first row should have 4 columns. The second - 1 column.");
		}
	}
	
	String getTableXpath(String clientId) {
		return "//table[@id='"+clientId+"']";
	}

	/* (non-Javadoc)
	 * @see org.richfaces.SeleniumTestBase#getTestUrl()
	 */
	@Override
	public String getTestUrl() {
		return "pages/columnGroup/columnGroupTest.xhtml";
	}

}
