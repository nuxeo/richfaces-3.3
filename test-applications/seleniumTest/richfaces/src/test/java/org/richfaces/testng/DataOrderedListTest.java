package org.richfaces.testng;


public class DataOrderedListTest extends AbstractDataListTest {
	@Override
	public String getTestUrl() {
		return "pages/dataList/dataOrderedList.xhtml";
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/dataList/dataOrderedListAutoTest.xhtml";
	}
}
