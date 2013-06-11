package org.richfaces.testng;

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VirtualEarthTest  extends SeleniumTestBase {

    /**
     *    component is present on the page together with map images and there are no JS errors
     */
	@Test(groups=FAILURES_GROUP)
	public void testImages(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	Assert.assertTrue(selenium.getXpathCount("id('" + autoTester.getClientId(AutoTester.COMPONENT_ID) + "')//img").intValue() != 0);
    	runScript("map.ZoomIn()");
	}

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
    	autoTester.testStyleAndClasses(new String[]{"noname"}, styleAttributes);
    	autoTester.testHTMLEvents();
	}

	@Override
	public String getTestUrl() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAutoTestUrl() {
		return "pages/virtualEarth/virtualEarthAutoTest.xhtml";
	}
}
