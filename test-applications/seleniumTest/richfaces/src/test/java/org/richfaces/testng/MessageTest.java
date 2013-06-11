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

import org.ajax4jsf.template.Template;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MessageTest extends AbstractMessageTest {

	@Override
	protected void init(Template template) {
		super.init(template);
    	label = "xpath=id('" + message + "')/span[2]";
	}
	
    /**
     * ajaxRendered message output first message for attached component by ajax request
     */
    @Test(groups=FAILURES_GROUP)
    public void testAjaxRendered(Template template) {
    	init(template);
    	
    	//FIXME https://jira.jboss.org/jira/browse/RF-5145
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":passedMarker"));
    	selenium.type(inputText, "fatal");
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":passedMarker"));
    	clickAjaxCommandAndWait(ajaxRendered);
    	clickAjaxCommandAndWait(mainForm + ":submitWithoutReRender");
    	Assert.assertTrue(selenium.isElementPresent(mainForm + ":fatalMarker"));
    }
    
    /**
     * check markers rendering
     */
    @Test
    public void testMarkers(Template template) {
    	init(template);
    	assertMarkers(true);
    }
    
    /**
     * showDetail and showSummary attributes work
     */
    @Test
    public void testShowDetailAndShowSummary(Template template) {
    	init(template);
    	assertShowDetailAndShowSummary(true);
    }
    
	@Override
	public String getTestUrl() {
		return "pages/message/message.xhtml";
	}

}
