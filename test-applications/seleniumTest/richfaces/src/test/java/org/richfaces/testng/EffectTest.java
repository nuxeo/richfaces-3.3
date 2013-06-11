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

import java.util.Date;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Andrey
 *
 */
public class EffectTest extends SeleniumTestBase {
	
	static final String RESET_METHOD = "#{effectBean.reset}"; 
	
	@Test
	public void testRendered(Template template) {
		renderPage(template, RESET_METHOD);
		String parentId = getParentId();
		
		String controlId = parentId + "_controls:testRendered";
		clickCommandAndWait(controlId);
		
		String button2Id = parentId + "button2"; 
		String hideLink2 = "hideLink2"; 
		
		AssertVisible(button2Id);
		clickById(hideLink2);
		AssertVisible(button2Id, "Button should not be hidden in case on effect rendered = false");
	}
	
	@Test
	public void testNamedEffect(Template template) {
		renderPage(template, RESET_METHOD);
		
		String testLink = "testLink";
		AssertVisible(testLink);
		
		clickById(testLink);
		pause(200, testLink);
		assertStyleAttribute(testLink, "opacity", "Named effect does not work");
		pause(2500, testLink);
		
		if (isIE()) {
			String style = getStyleAttributeString(testLink, "filter");
			if (style == null || (!style.contains("opacity=9.99") && !style.contains("opacity=10"))) {
				Assert.fail("Opacity effect has not been completed. Style expected [filter = {opacity: 9.99}]. But was: " + style);
			}
		}else {
			String style = getStyleAttributeString(testLink, "opacity");
			if (style == null || !style.equals("0.1")) {
				Assert.fail("Opacity effect has not been completed. Style expected [opacity: 0.1]. But was: " + style);
			}
		}
	}
	
	@Test
	public void testComponentHideShow(Template template) {
		renderPage(template, RESET_METHOD);
		String parentId = getParentId();
		
		String button2Id = parentId + "button2";
		String button3Id = parentId + "button3";
		
		String hideLink2 = "hideLink2";
		String showLink2 = "showLink2";
		
		AssertVisible(button2Id);
		clickById(hideLink2);
		AssertNotVisible(button2Id, "Command Button has not been hidden by Fade effect");
		clickById(showLink2);
		AssertVisible(button2Id, "Command Button has not been appeared by Fade effect");
		
		AssertVisible(button3Id);
		clickById(button3Id);
		AssertNotVisible(button3Id, "Command Button has not been hidden by Fade effect attached to parent");
		
		pause(1500, button3Id);
		AssertVisible(button3Id, "Command Button has not been appeared after 1 second duration by Fade effect attached to parent");

	}
	
	
	@Test
	public void testFadeEffect(Template template) {
		renderPage(template, RESET_METHOD);
		String parentId = getParentId();
		
		String button1Id = "button1";
		String button2Id = parentId + "button2";
		
		String hideLink1 = "hideLink1";
		String hideLink2 = "hideLink2";
		
		AssertVisible(button1Id);
		AssertVisible(button2Id);
		
		clickById(hideLink1);

		//AssertVisible(button1Id, "Duration param of Fade effect does not work ");
		//assertStyleAttribute(button1Id, "opacity", "Element has no opacity style");
		
		pause(3000, hideLink1);
		
		AssertNotVisible(button1Id, "Input button has not been hidden by Fade effect or targetId has not been resolved");
		
		clickById(hideLink2);
		AssertNotVisible(button2Id, "Command Button has not been hidden by Fade effect or targetId has not been resolved");
			
	}

	/* (non-Javadoc)
	 * @see org.richfaces.SeleniumTestBase#getTestUrl()
	 */
	@Override
	public String getTestUrl() {
		return "pages/effect/effectTest.xhtml";
	}

}
