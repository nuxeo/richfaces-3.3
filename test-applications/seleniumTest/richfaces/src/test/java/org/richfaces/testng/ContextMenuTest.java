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

public class ContextMenuTest extends SeleniumTestBase {

	static final String FORMID = "_form:";
	static final String CONTROL_FORMID = "_controls:";
	static final String UNATTACHED_CONTEXTMENU = "contextMenu";
	static final String RESET_METHOD = "#{contextMenuBean.reset}";
	
	String linkShowId;
	String linkHideId;
	String componentControlShowLinkId;
	String componentControlHideLinkId;
	String contextMenuId;
	String autoCreatedId;
	String contextMenuBody;
	
	final static Map<String, String> PARAMS = new HashMap<String, String>();
	static {
		PARAMS.put("parameter1", "value1");
		PARAMS.put("parameter2", "value2");
	}

	void initIds(String parentId, String contextMenuId) {
		parentId = getParentId() + FORMID;
		linkShowId = "showMenu";
		linkHideId = "hideMenu";
		componentControlShowLinkId = parentId + linkShowId;
		componentControlHideLinkId = parentId + linkHideId;
		contextMenuId = parentId + contextMenuId;
		autoCreatedId = contextMenuId + ":_auto_created";
		contextMenuBody = contextMenuId + "_menu";
		this.contextMenuId = contextMenuId;
	}
	
	@Override
	public String getAutoTestUrl() {
		return "pages/contextMenu/contextMenuAutoTest.xhtml";
	}
	
	@Override
	public void sendAjax() {
		String showId = "showMenu";
		String contextMenuId = getAutoTester(this).
				getClientId(AutoTester.COMPONENT_ID, getTemplate());
		String itemId = getAutoTester(this).
				getClientId("item1", getTemplate());
		clickById(showId);
		waitForMenuShow(contextMenuId);
		clickAjaxCommandAndWait(itemId);

	}
	
	@Test
	public void testOncomplete(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testOncomplete();
	}
	
	@Test
	public void testAjaxSingle(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testAjaxSingle();
	}
	
	
	@Test
	public void testExternalValidation(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testExtrenalValidationFailure();
	}
	
	@Test
	public void testImmediate(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testImmediate();
	}
	
	@Test
	public void testImmediateWithExternalValidation(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		autoTester.testImmediateWithExternalValidationFailed();
	}
	
	@Test
	public void testLimitToList(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testLimitToList();
	}	
	
	@Test
	public void testByPassUpdate(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testBypassUpdate();
	}
	
	@Test
	public void testReRender(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testReRender();
	}
	
	@Test
	public void testNestedParams(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testRequestParameters(PARAMS);
	}
	
	@Test
	public void testDisableDefault(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), "contextMenu2");
		
		String menuScript = getContextMenuScript(contextMenuId);
		Assert.assertTrue(menuScript.indexOf("Richfaces.disableDefaultHandler") != -1, "DisableDefaultMenu = true does not work");
		
		String controlId = getParentId() + "_controls:testDisableDefault";
		clickCommandAndWait(controlId);
		
		menuScript = getContextMenuScript(contextMenuId);
		Assert.assertTrue(menuScript.indexOf("Richfaces.disableDefaultHandler") == -1, "DisableDefaultMenu = false does not work");
		
	}
	
	
	@Test
	public void testRendered(Template template) {
		AutoTester autoTester = getAutoTester(this);
		autoTester.renderPage(template, RESET_METHOD);
		
		autoTester.testRendered();
	}
		
	@Test
	public void testActionListener(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);
		String statusId = getParentId() + FORMID + "status";
		
		clickById(linkShowId);
		waitForMenuShow(contextMenuId);
		String itemId = getParentId() + FORMID + "item2";
		clickById(itemId);
		pause(200, contextMenuId);
		AssertTextEquals(statusId, "", "No action listener should be passed for submitMode = none");
		
		String controlId = getParentId() + CONTROL_FORMID + "testServerMode";
		clickCommandAndWait(controlId);
		clickById(linkShowId);
		waitForMenuShow(contextMenuId);
		clickCommandAndWait(itemId);
		AssertTextEquals(statusId, "ActionListener", "Action listener skipped for SubmitMode = server ");
		
		controlId = getParentId() + CONTROL_FORMID + "testAjaxMode";
		clickCommandAndWait(controlId);
		clickById(linkShowId);
		waitForMenuShow(contextMenuId);
		clickAjaxCommandAndWait(itemId);
		AssertTextEquals(statusId, "ActionListener", "Action listener skipped for SubmitMode = ajax ");
		
	}

	@Test
	public void testAttachedAndAttachedTo(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);

		String controlId = getParentId() + CONTROL_FORMID + "testAttached";
		clickCommandAndWait(controlId);

		String attachedLink = getParentId() + FORMID + "parentLink";
		clickById(attachedLink);
		waitForMenuShow(contextMenuId, "Attached = true does not work");
		AssertPresent(contextMenuBody, "Context menu has not been shown");
		

		controlId = getParentId() + CONTROL_FORMID + "testAttachTo";
		clickCommandAndWait(controlId);

		String attachedTo = "attachToLink";
		clickById(attachedTo);
		waitForMenuShow(contextMenuId, "AttachTo does not work");
		AssertPresent(contextMenuBody, "Context menu has not been shown");
	}

	@Test
	public void testSubmitMode(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);

		String controlId = getParentId() + CONTROL_FORMID + "testServerMode";
		String timeId = getParentId() + FORMID + "time";
		String time = getTextById(timeId);
		String itemId = getParentId() + FORMID + "item1";

		writeStatus("Test server submit mode");
		/** test server submit mode */
		clickCommandAndWait(controlId);
		clickById(linkShowId);
		waitForMenuShow(contextMenuId);
		clickCommandAndWait(itemId);
		AssertTextNotEquals(timeId, time, "Server submit mode does not work");

		writeStatus("Test server submit mode");
		/** test ajax submit mode */
		controlId = getParentId() + CONTROL_FORMID + "testAjaxMode";
		clickCommandAndWait(controlId);
		time = getTextById(timeId);
		clickById(linkShowId);
		waitForMenuShow(contextMenuId);
		clickAjaxCommandAndWait(itemId);
		AssertTextNotEquals(timeId, time, "Ajax submit mode does not work");

	}

	@Test
	public void testContextMenuRendering(Template template) {
		renderPage(template, RESET_METHOD);

		String parentId = getParentId();
		String menuId = parentId + FORMID + "contextMenu2";
		String menuBody = menuId + "_menu";
		String autoCreatedId = menuId + ":_auto_created";

		String showId = "showContext2";
		String hideId = "hideContext2";

		// show menu
		clickById(showId);

		waitForMenuShow(menuId);

		AssertPresent(menuBody, "Context menu has not been displayed");
		AssertPresent(autoCreatedId, "Context menu has not been displayed");

		String menuGroupId = parentId + FORMID + "menuGroup";
		String itemId = parentId + FORMID + "g5";
		AssertPresent(menuGroupId, "Context menu group has not been displayed");
		AssertPresent(itemId, "Menu item has not been displayed");

		int separatorCount = selenium
				.getXpathCount(
						"//div[@id='"
								+ menuBody
								+ "']/div/div[@class='dr-menu-separator rich-menu-separator']")
				.intValue();
		Assert.assertTrue(separatorCount == 1, "Separators count is invalid");

		menuBody = parentId + FORMID + "menuGroup_menu";

		AssertPresent(menuBody);
		itemId = parentId + FORMID + "g1";
		AssertTextEquals(itemId, "Item1", "Item label is invalid");
		AssertPresent(itemId, "Menu item has not been displayed");
		itemId = parentId + FORMID + "g2";
		AssertTextEquals(itemId, "Item2", "Item label is invalid");
		AssertPresent(itemId, "Menu item has not been displayed");
		itemId = parentId + FORMID + "g3";
		AssertPresent(itemId, "Menu separator div does not present");
		itemId = parentId + FORMID + "g4";
		AssertTextEquals(itemId, "Item3", "Item label is invalid");
		AssertPresent(itemId, "Menu item has not been displayed");

		separatorCount = selenium
				.getXpathCount(
						"//div[@id='"
								+ menuBody
								+ "']/div/div[@class='dr-menu-separator rich-menu-separator']")
				.intValue();
		Assert.assertTrue(separatorCount == 1, "Separators count is invalid");

		clickById(hideId);
		clickById(showId);

		menuId = parentId + FORMID + "menuGroup";
		fireMouseEvent(menuId, "mouseover", 10, 10, false);
		waitForMenuShow(menuId);

		String top = runScript(getElementById(menuBody) + ".style.top");
		String left = runScript(getElementById(menuBody) + ".style.left");

		Assert.assertTrue(top != null && top.length() > 0);
		Assert.assertTrue(left != null && left.length() > 0);

		AssertTextEquals(menuId, "Group1", "Group label is invalid");

	}

	private void waitForMenuShow(String id) {
		String bodyId = id + "_menu";
		if (!selenium.isElementPresent(bodyId)) {
			waiteForCondition(getElementById(bodyId), 5000);
		}
		waiteForCondition(getElementById(bodyId) + ".style.display != 'none'",
				5000);
	}

	private void waitForMenuShow(String id, String message) {
		try {
			waitForMenuShow(id);
		} catch (Exception e) {
			Assert.fail(message + e.getMessage());
		}
	}
	
	private String getContextMenuScript(String id) {
		return getHTMLById(id);
	
	}

	@Test
	public void testContextMenuStandAlone(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);

		String menuItemId = getParentId() + FORMID + "item2";

		AssertNotPresent(menuItemId, "Context menu should be hidden");
		AssertNotPresent(autoCreatedId, "Context menu should be hidden");

		clickById(linkShowId);

		AssertPresent(menuItemId, "Context menu has not been shown");
		AssertPresent(autoCreatedId, "Context menu has not been shown");
		AssertTextEquals(menuItemId, "Menu2",
				"Context menu displays incorrect.");

		clickById(linkHideId);

		AssertNotVisible(contextMenuBody, "Context menu has not been hidden");
	}

	@Test
	public void testComponentControlManagement(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);

		String menuItemId = getParentId() + FORMID + "item2";

		AssertNotPresent(menuItemId, "Context menu should be hidden");
		AssertNotPresent(autoCreatedId, "Context menu should be hidden");

		clickById(componentControlShowLinkId);

		AssertPresent(menuItemId, "Context menu has not been shown");
		AssertPresent(autoCreatedId, "Context menu has not been shown");
		AssertTextEquals(menuItemId, "Menu2",
				"Context menu displays incorrect.");

		clickById(componentControlHideLinkId);

		AssertNotVisible(contextMenuBody, "Context menu has not been hidden");
	}

	@Test
	public void testMacrodefinitions(Template template) {
		renderPage(template, RESET_METHOD);
		initIds(getParentId(), UNATTACHED_CONTEXTMENU);
		String menuItemId = getParentId() + FORMID + "item1";

		clickById(linkShowId);

		AssertTextEquals(menuItemId, "Menu1",
				"Macrosubstitution does not work.");

		clickById(componentControlShowLinkId);
		menuItemId = getParentId() + FORMID + "item3";
		AssertTextEquals(menuItemId, "Menu3",
				"Macrosubstitution does not work.");
		menuItemId = getParentId() + FORMID + "item4";
		AssertTextEquals(menuItemId, "Menu4",
				"Macrosubstitution does not work.");

	}

	public String getTestUrl() {
		return "pages/contextMenu/contextMenu.xhtml";
	}

}
