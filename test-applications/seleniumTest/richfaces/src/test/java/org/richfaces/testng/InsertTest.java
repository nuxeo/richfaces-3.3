package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InsertTest  extends SeleniumTestBase {

    /**
     *    component is present on the page and value is output
     */
	@Test
	public void testContent(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	Assert.assertEquals(selenium.getText("id=" + autoTester.getClientId(AutoTester.COMPONENT_ID)), "some text");
	}

    /**
     *    component with rendered = false is not present on the page
     */
	@Test
	public void testRendered(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testRendered();
	}

	@Override
	public String getTestUrl() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAutoTestUrl() {
		return "pages/insert/insertAutoTest.xhtml";
	}
}
