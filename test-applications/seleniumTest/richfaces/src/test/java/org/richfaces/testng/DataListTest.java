package org.richfaces.testng;


public class DataListTest extends AbstractDataListTest {
	@Override
	public String getTestUrl() {
		return "pages/dataList/dataList.xhtml";
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/dataList/dataListAutoTest.xhtml";
	}
}
