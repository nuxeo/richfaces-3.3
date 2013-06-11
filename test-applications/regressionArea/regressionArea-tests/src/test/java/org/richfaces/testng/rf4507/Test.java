package org.richfaces.testng.rf4507;

import org.richfaces.SeleniumTestBase;
import org.testng.Assert;

public class Test extends SeleniumTestBase {

	private String getIdSelector(String id) {
		return "//*[@id='" + id + "']";
	}
	
	private void testCalendarSwitch(int buttonPosition, String cellText) {
		renderPage();
		
		AssertTextEquals(getIdSelector("form:calendarDayCell6") + "/div", "4-10");

		selenium.assignId(getIdSelector("form:calendarHeader") + "//td[*[@class='rich-calendar-tool-btn']][" + buttonPosition + "]/div", "testButton");
		fireMouseEvent("testButton", "click", 0, 0, false);
		waitForAjaxCompletion();
		
		AssertTextEquals(getIdSelector( "form:calendarDayCell6") + "/div", cellText);
	}
	
	private void testLink(String linkId, String data) throws Exception {
		renderPage();

		Assert.assertEquals("null", selenium.getEval("window.receivedData"));

		clickAjaxCommandAndWait(getIdSelector(linkId));
		
		Assert.assertEquals(data, selenium.getEval("window.receivedData"));
	}
	
	@org.testng.annotations.Test
	public void testNextMonth() throws Exception {
		testCalendarSwitch(4, "1-11");
	}
	
	@org.testng.annotations.Test
	public void testPreviousMonth() throws Exception {
		testCalendarSwitch(2, "6-9");
	}

	@org.testng.annotations.Test
	public void testFirstLink() throws Exception {
		testLink("form:link1", "firstLink");
	}
	
	@org.testng.annotations.Test
	public void testSecondLink() throws Exception {
		testLink("form:link2", "secondLink");
	}
	
	public String getTestUrl() {
		return "pages/rf4507.xhtml";
	}

}
