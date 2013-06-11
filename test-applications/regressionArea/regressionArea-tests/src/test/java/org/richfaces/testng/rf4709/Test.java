package org.richfaces.testng.rf4709;

import org.richfaces.SeleniumTestBase;

public class Test extends SeleniumTestBase {

	private String getMessagesLocator() {
		return "//*[@id='" + "messages']";
	}
	
	@org.testng.annotations.Test
	public void testTabPanel() throws Exception {
		renderPage();
		
		AssertTextEquals(getMessagesLocator(), "");
		
		selenium.assignId("//*[@id='form:secondTab_lbl']", "testElement");
		fireMouseEvent("testElement", "click", 0, 0, false);
		waitForAjaxCompletion();

		AssertTextEquals(getMessagesLocator(), "tabPanel");
	}
	
	@org.testng.annotations.Test
	public void testTogglePanel() throws Exception {
		renderPage();
	
		AssertTextEquals(getMessagesLocator(), "");
		
		selenium.click("//*[@id='form:firstControl']");
		waitForAjaxCompletion();

		AssertTextEquals(getMessagesLocator(), "togglePanel");
	}

	@org.testng.annotations.Test
	public void testPanelBar() throws Exception {
		renderPage();

		AssertTextEquals(getMessagesLocator(), "");
		selenium.assignId("//*[@id='form:secondPanelBarItem']/div[1]", "testElement");
		fireMouseEvent("testElement", "click", 0, 0, false);
		waitForAjaxCompletion();
		
		AssertTextEquals(getMessagesLocator(), "panelBar");
	}
	
	@Override
	public String getTestUrl() {
		return "pages/rf4709.xhtml";
	}

}
