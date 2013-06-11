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
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Ajax Output Panel selenium test
 * 
 * @author Alexandr Levkovsky
 * 
 */
public class AjaxOutputPanelTest extends SeleniumTestBase {

	private String layout;
	
	private String ajaxRendered;
	
	private String keepTransient;
	
	private String reset;
	
	private String mainForm;
	
	private String outputPanel;
	
	private String outputText;
	
	private String transientState;
	
	private String submit;
	
	private String setTransient;
	
	private void init(Template template) {
        renderPage(null, template, "#{a4jOutputPanelBean.init}");
        String attrForm = getParentId() + "attrForm";
        layout = attrForm + ":layout";
        ajaxRendered = attrForm + ":ajaxRendered";
        keepTransient = attrForm + ":keepTransient";
        reset = attrForm + ":reset";
        mainForm = getParentId() + "mainForm";
        outputPanel = mainForm + ":outputPanel";
        outputText = mainForm + ":outputText";
        transientState = mainForm + ":transientState";
        submit = mainForm + ":submit";
        setTransient = mainForm + ":setTransient";
	}

	/**
	 *  output panel is present in browser together with nested elements
	 */
	@Test
	public void testPresent(Template template) {
		init(template);
		Assert.assertTrue(selenium.isElementPresent(outputPanel));
		Assert.assertTrue(selenium.isElementPresent(outputText));
	}
	
	/**
	 *  component with ajaxRendered = true is updated on each request
	 */
	@Test
	public void testAjaxRendered(Template template) {
		init(template);
		Assert.assertEquals(selenium.getText(outputText), "text");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(outputText), "text");
		clickAjaxCommandAndWait(reset);
		clickAjaxCommandAndWait(ajaxRendered);
		Assert.assertEquals(selenium.getText(outputText), "text");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(outputText), "changed");
	}
	
	/**
	 *  transient components are kept when keepTransient = true
	 */
	@Test
	public void testKeepTransient(Template template) {
		init(template);
		clickAjaxCommandAndWait(ajaxRendered);
		Assert.assertEquals(selenium.getText(transientState), "false");		
		clickAjaxCommandAndWait(setTransient);
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(transientState), "false");	
		clickAjaxCommandAndWait(keepTransient);
		Assert.assertEquals(selenium.getText(transientState), "false");		
		clickAjaxCommandAndWait(setTransient);
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(transientState), "true");		
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

	@Test
	public void testLayoutAttribute(Template template) {
		init(template);
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/span/span@id"), outputText);
		selenium.type(layout, "block");
		waitForAjaxCompletion();
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/div/span@id"), outputText);
		selenium.type(layout, "none");
		waitForAjaxCompletion();
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/span@id"), outputText);
	}
	
	@Override
	public String getAutoTestUrl() {
        return "pages/ajaxOutputPanel/ajaxOutputPanelAutoTest.xhtml";
	}
	
	@Override
    public String getTestUrl() {
        return "pages/ajaxOutputPanel/ajaxOutputPanelTest.xhtml";
    }

}
