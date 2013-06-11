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
 * ajaxInclude component sselenium test
 * @author Alexandr Levkovsky
 *
 */
public class AjaxIncludeTest extends SeleniumTestBase {


	private String layout;
	
	private String keepTransient;
	
	private String mainForm;
	
	private String include;
	
	private String text;
	
	private String transientState;
	
	private String submit;
	
	private String messages;
	
	private String setTransient;
	
	private void init(Template template) {
        renderPage(null, template, "#{a4jIncludeBean.init}");
        String attrForm = getParentId() + "attrForm";
        layout = attrForm + ":layout";
        keepTransient = attrForm + ":keepTransient";
        mainForm = getParentId() + "mainForm";
        include = mainForm + ":include";
        text = include + ":text";
        transientState = include + ":transientState";
        submit = include + ":submit";
        messages = include + ":messages";
        setTransient = mainForm + ":setTransient";
	}

	/**
	 *  component is rendered together with page contents defined as viewId
	 */
	@Test
	public void testPresent(Template template) {
		init(template);
		Assert.assertTrue(selenium.isElementPresent(include));
		Assert.assertTrue(selenium.isElementPresent(text));
	}
	
	/**
	 *  components in included page pass through all JSF lifecycle,
	 *  navigation works after include is re-rendered and viewId expression is updated
	 */
	@Test
	public void testLifecycleAndNavigation(Template template) {
		init(template);
		Assert.assertTrue(selenium.getText(messages).length() == 0);
		selenium.type(text, "");
		clickAjaxCommandAndWait(submit);
		Assert.assertFalse(selenium.getText(messages).length() == 0);
		selenium.type(text, "q");
		clickAjaxCommandAndWait(submit);
		Assert.assertTrue(selenium.getText(messages).length() == 0);
		selenium.type(text, "next");
		clickAjaxCommandAndWait(submit);
		Assert.assertEquals(selenium.getText(text), "next");
	}
	
	/**
	 *  transient components are kept when keepTransient = true
	 */
	//https://jira.jboss.org/jira/browse/RF-6475
	@Test(groups=FAILURES_GROUP)
	public void testKeepTransient(Template template) {
		init(template);
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
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/span/input@id"), text);
		selenium.type(layout, "block");
		waitForAjaxCompletion();
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/div/input@id"), text);
		selenium.type(layout, "none");
		waitForAjaxCompletion();
		Assert.assertEquals(selenium.getAttribute("xpath=id('" + mainForm + "')/span/input@id"), text);
	}
	
	@Override
	public String getAutoTestUrl() {
        return "pages/ajaxInclude/ajaxIncludeAutoTest.xhtml";
	}
	
	@Override
    public String getTestUrl() {
        return "pages/ajaxInclude/ajaxIncludeTest.xhtml";
    }

}
