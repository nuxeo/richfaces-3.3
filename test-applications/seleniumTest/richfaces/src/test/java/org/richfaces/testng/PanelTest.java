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
import org.testng.annotations.Test;

/**
 * @author Andrey Markavtsov
 *
 */
public class PanelTest extends SeleniumTestBase {
	
	String panelId;
	
	String panelBodyId;
	
	String panelHeaderId;
	
	static final String FORM_ID = "_form:";
	static final String PANEL_ID = "panel"; 
	
	
	static final String [] panelClasses = new String [] {
		"dr-pnl",
		"rich-panel",
		"myClass"
	};
	
	static final Map<String, String> styles = new HashMap<String, String>();
	static {
		styles.put("color", "green");
		styles.put("font-weight", "bold");
	}
	
	private void initIds(String parentId) {
		panelId = parentId + FORM_ID + PANEL_ID;
		panelBodyId = panelId + "_body";
		panelHeaderId = panelId + "_header";
	}
	
	@Test
	public void testOutputToClient(Template template) {
		renderPage(template);
		initIds(getParentId());
		
		AssertPresent(panelId, "Panel was not output to client");
		AssertPresent(panelBodyId, "Panel was not output to client");
	}
	
	@Test
	public void testHTMLAndStyles(Template template) {
		renderPage(template);
		initIds(getParentId());
		
		assertClassNames(panelId, panelClasses, "Panel CSS classes was not rendered to client", true);
		assertStyleAttributes(panelId, styles);
		
		assertClassNames(panelBodyId, new String [] {"bodyClass"}, "Body panel css classes was not rendered to client", true);
	
	}
	
	@Test
	public void testContentAndFacets(Template template) {
		renderPage(template);
		initIds(getParentId());
		
		AssertPresent(panelBodyId);
		AssertPresent(panelHeaderId);
		
		AssertTextEquals(panelHeaderId, "Header", "Header facet was not rendered to client");
		AssertTextEquals(panelBodyId, "Panel Content", "Panel does not render children components");
		
	}
	
	@Test
	public void testRendered(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, null);
		
		tester.testRendered();
	}

	/* (non-Javadoc)
	 * @see org.richfaces.SeleniumTestBase#getTestUrl()
	 */
	@Override
	public String getTestUrl() {
		return "pages/panel/panelTest.xhtml";
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/panel/panelAutoTest.xhtml";
	}

}
