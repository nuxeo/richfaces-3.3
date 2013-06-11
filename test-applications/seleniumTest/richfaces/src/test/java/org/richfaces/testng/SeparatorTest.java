package org.richfaces.testng;

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeparatorTest  extends SeleniumTestBase {

    /**
     *    component with rendered = false is not present on the page,
     *    style and classes, standard HTML attributes are output to client
     */
	@Test
	public void testStandardAttributes(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testRendered();
    	Map<String, String> styleAttributes = new HashMap<String, String>();
    	styleAttributes.put("width", "100%");
    	styleAttributes.put("color", "yellow");
    	autoTester.testHTMLEvents();
    	Assert.assertTrue(selenium.getAttribute("xpath=id('"
    			+ autoTester.getClientId(AutoTester.COMPONENT_ID) + "')/div@class")
    			.indexOf("noname") != -1);
    	Assert.assertTrue(selenium.getAttribute("xpath=id('"
    			+ autoTester.getClientId(AutoTester.COMPONENT_ID) + "')/div@style").toLowerCase()
    			.indexOf("color: yellow") != -1);
	}

	@Override
	public String getTestUrl() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAutoTestUrl() {
		return "pages/separator/separatorAutoTest.xhtml";
	}
}
