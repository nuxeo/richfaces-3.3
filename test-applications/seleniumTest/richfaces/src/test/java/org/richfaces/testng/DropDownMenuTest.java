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

public class DropDownMenuTest extends SeleniumTestBase {
	
	static final String RESET_METHOD = "#{ddmenuBean.reset}";
	
	static final String INIT_IMMEDIATE_METHOD = "#{ddmenuBean.initImmediateTest}";
	
	static final String IMMEDIATE_WITH_EXTERNAL_VALIDATION_URL = "/pages/dropDownMenu/testImmediateWithExternalValidation.xhtml";
	
	static final Map<String, String> params = new HashMap<String, String>();
	
	static {
		params.put("parameter1", "value1");
	}
	
	
	@Test
	public void testCommonHiperLink(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		String submit = tester.getClientId("submit1");
		selenium.click(submit);
		waitForPageToLoad();
		
		tester.checkActionListener(true);
	
	}
	
	@Test
	public void testClassStylesAndHtmlAttributes(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		String ddMenuId = tester.getClientId(AutoTester.COMPONENT_ID);
		String ddMenuGroup = tester.getClientId("group1");
		String ddMenuItem = tester.getClientId("item1");
		String ddMenuIdPath = "//div[@id='" + ddMenuId + "']/div[1]";
		
		assertClassNames(ddMenuId, new String [] {"dr-menu-label", "rich-ddmenu-label", "dr-menu-label-unselect", "rich-ddmenu-label-unselect"}, "Drop down menu has unexpected css classes", true);
		assertClassNames(ddMenuId, new String[] {"dropDownMenuClass"}, "Class attribute does not work for DropDown menu component", true);
		assertClassNames(ddMenuGroup, new String [] {"rich-menu-group-enabled", "rich-menu-group"}, "Menu group has unexpected css classes", true);
		assertClassNames(ddMenuGroup, new String[] {"menuGroupClass"}, "Class attribute does not work for Menu group component", true);
		assertClassNames(ddMenuItem, new String [] {"rich-menu-item-enabled", "rich-menu-item"}, "Menu item has unexpected css classes", true);
		assertClassNames(ddMenuItem, new String[] {"menuItemClass"}, "Class attribute does not work for Menu item component", true);
		
		assertStyleAttribute(ddMenuId, "color: red", "Style attribute for Drop down menu does not work");
		assertStyleAttribute(ddMenuGroup, "color: green", "Style attribute for Menu droup component does not work");
		assertStyleAttribute(ddMenuItem, "color: black", "Style attribute for Menu item component does not work");
		
		selenium.mouseOver(ddMenuIdPath);
		waiteForCondition(getElementById(ddMenuId + "_menu") + ".style.display != 'none'", 5000);
		selenium.mouseOver(ddMenuGroup);
		waiteForCondition(getElementById(ddMenuGroup + "_menu") + ".style.display != 'none'", 5000);
		selenium.mouseOver(ddMenuId);
		
		assertClassNames(ddMenuGroup, new String[] {"menuGroupSelectClass"}, "selectClass attribute does not work for Menu group component", true);
		assertEvent("ddmenuOnmouseover", "Onmouseover attribute does not work for DropDown menu component");
		assertEvent("ddmenuOnexpand", "Onexpand attribute does not work for DropDown menu component");
		assertEvent("ddmenuOngroupactivate", "Ongroupactivate attribute does not work for DropDown menu component");
		assertEvent("ddmenuGroupOnmouseover", "Onmouseover attribute does not work for Menu group component");
		assertEvent("ddmenuGroupOnopen", "Onopen attribute does not work for Menu group component");
		
		selenium.mouseMove(ddMenuId);
		selenium.mouseOut(ddMenuId);
		assertEvent("ddmenuOnmousemove", "onmousemove does not work for Drop down menu");
		assertEvent("ddmenuOnmouseout", "onmouseout does not work for Drop down menu");

		selenium.mouseMove(ddMenuGroup);
		selenium.mouseOut(ddMenuGroup);
		assertEvent("ddmenuGroupOnmousemove", "onmousemove does not work for Group menu");
		assertEvent("ddmenuGroupOnmouseout", "onmouseout does not work for Group menu");

		selenium.mouseMove(ddMenuItem);
		selenium.mouseOut(ddMenuItem);
		selenium.mouseDown(ddMenuItem);
		selenium.mouseUp(ddMenuItem);
		selenium.click(ddMenuItem);
		assertEvent("onmousedown", "onmousedown attribute does not work for Menu item component");
		assertEvent("onmousemove", "onmousemove attribute does not work for Menu item component");
		assertEvent("onmouseout", "onmouseout attribute does not work for Menu item component");
		assertEvent("onmouseup", "onmouseup attribute does not work for Menu item component");
		assertEvent("onclick", "onclick attribute does not work for Menu item component");
	
		assertEvent("ddmenuOnitemselect", "Onitemselect attribute does not work for DropDown menu component");
		
		selenium.click("//body");
		waiteForCondition(getElementById(ddMenuId + "_menu") + ".style.display == 'none'", 5000);
		assertEvent("ddmenuOncollapse", "oncollapse attribute does not work for DrownDownMenu component");
		assertEvent("ddmenuGroupOnclose", "onclose attribute does not work for Menu group component");
		
	}
	
	@Test
	public void testOnComplete(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testOncomplete();
	}
	
	@Test
	public void testReRender(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testReRender();
	}
	
	@Test
	public void testLimitToList(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testLimitToList();
	}
	
	@Test
	public void testByPassUpdates(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testBypassUpdate();
	}
	
	@Test
	public void testRendered(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testRendered();
	}
	
	@Test
	public void testDropDownMenuOutput(Template template) {
		renderPage(template, RESET_METHOD);
		
		String parentId = getParentId() + "_form:";
		String file = parentId + "file";
        String filePath = "//div[@id='" + file + "']/div[1]";
        String open = parentId + "open:anchor";
        String saveAs = parentId + "saveAs:anchor";
        String save = parentId + "save:anchor";
        String saveAll = parentId + "saveAll:anchor";
        String close = parentId + "close:anchor";
        String exit = parentId + "exit:anchor";
        String menu = file + "_menu";
        String separator = parentId + "menuSeparator11";

        selenium.mouseOver(filePath);
        pause(1000, menu);
        Number width = selenium.getElementWidth(menu);
        System.out.println(width);
        if (width.equals(0)) {
        	Assert.fail("Drop down menu has null width");
        }
        if  (selenium.getElementPositionTop(menu).intValue() <= 0) {
        	Assert.fail("Drop down menu should have top position more than 0");
        }
        AssertVisible(open);
        AssertTextEquals(open, "Open", "Open menu Item was not rendered");
        AssertVisible(saveAs);
        AssertTextEquals(saveAs, "Save As...", "Save As... menu Item was not rendered");
		AssertVisible(close);
        AssertTextEquals(close, "Close", "Open menu Item was not rendered");
		AssertVisible(exit);
        AssertTextEquals(exit, "Exit", "Exit menu Item was not rendered");
        AssertVisible(parentId + "open:icon", "Icon for menu item was not rendered");
        
        
		
        selenium.mouseOver(saveAs);
        pause(1000, menu);
        AssertVisible(save);
        AssertVisible(saveAll);
        AssertTextEquals(save, "Save", "Save menu Item was not rendered");
        AssertTextEquals(saveAll, "Save All", "Save All menu Item was not rendered");
        AssertVisible(parentId + "saveAs:folder", "Save as group was not rendered");
        
        assertClassNames(separator, new String [] {"rich-menu-separator"}, "Separator has invalid css class names", true);
        AssertVisible(separator, "Separator was not output");
		
	}

    @Test
    public void testDropDownMenuComponent(Template template) {
        renderPage(template, RESET_METHOD);

        testDropDownComponent();
    }
    
   @Test
    public void testWithExternalvalidationFailure(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testExtrenalValidationFailure();
    }
    
    @Test
    public void testImmediate(Template template) {
    	renderPage(template, INIT_IMMEDIATE_METHOD);
    	
    	testDropDownComponent();
    }
    
    @Test
    public void testImmediateWithExternalValidation(Template template) {
    	renderPage(IMMEDIATE_WITH_EXTERNAL_VALIDATION_URL, template, INIT_IMMEDIATE_METHOD);
    	
    	String parentId = getParentId() + "form:";
    	String messageId = parentId + "ii_m";
    	String submitId = parentId + "new";
    	
    	clickCommandAndWait(submitId);
    	AssertNotPresent(messageId, "Validation message should be displayed for external component in case of dropdown menu immediate=true");
    }
    
    @Test
    public void testAjaxSingle(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testAjaxSingle();
    }
    
    @Test
    public void testAjaxSingleWithExternalValidationFailed(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testAjaxSingleWithInternalValidationFailed();
    }
    
    @Test
    public void testAjaxSingleWithProcessExternalValidationFailed(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testAjaxSingleWithProcesExternalValidation(true);
    }
    
    @Test
    public void testNestedParams(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, RESET_METHOD);
    	
    	autoTester.testRequestParameters(params);
    }


    public String getTestUrl() {
        return "pages/dropDownMenu/dropDownMenuTest.xhtml";
    }
    
    @Override
    public void setInternalValidationFailed() {
    	String inputId = getAutoTester(this).getClientId("inputRequired");
    	setValueById(inputId, "");
    }
    
    @Override
    public String getAutoTestUrl() {
    	return "pages/dropDownMenu/dropDownMenuAutoTest.xhtml";
    }
    
    @Override
    public void sendAjax() {
    	String ajaxCommandId = getAutoTester(this).getClientId("new");
    	clickAjaxCommandAndWait(ajaxCommandId);
    }
    
    
    private void testDropDownComponent() {
    	  String parentId = getParentId() + "_form:";
          String file = parentId + "file";
          String open = parentId + "open:anchor";
          String saveAs = parentId + "saveAs:anchor";
          String save = parentId + "save:anchor";
          String saveAll = parentId + "saveAll:anchor";
          String close = parentId + "close:anchor";
          String exit = parentId + "exit:anchor";
          String operation = parentId + "operation";

          writeStatus("Check menu item in ajax mode");

          selenium.mouseOver(file);
          selenium.mouseOver(saveAs);
          clickAjaxCommandAndWait(saveAll);

          AssertTextEquals(operation, "Save All");

          selenium.mouseOver(file);
          selenium.mouseOver(saveAs);
          clickAjaxCommandAndWait(save);

          AssertTextEquals(operation, "Save");

          writeStatus("Check the drop down menu closed");

          AssertNotVisible(save);
          AssertNotVisible(saveAs);

          writeStatus("Check menu item in server mode");

          selenium.mouseOver(file);
          clickCommandAndWait(close);

          AssertTextEquals(operation, "Close");

          writeStatus("Check menu item in 'none' mode");

          selenium.mouseOver(file);
          selenium.mouseOver(exit);
          clickById(exit);

          AssertTextEquals(operation, "Close");
          AssertNotVisible(exit);
    }

}
