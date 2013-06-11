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
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PanelMenuTest extends SeleniumTestBase {

    private final static String RESET_METHOD_NAME = "#{panelBean.cleanValues}";

    private final static String[] ITEM_HIGHLIGHTS = {"dr-pmenu-selected-item", "rich-pmenu-selected-element"};

    private final static String COSMETIC_TEST_URL = "pages/panelMenu/menuPanelCosmeticTest.xhtml";

    @Test
    public void testPanelMenuClientAPI(Template template) {
        renderPage(template, RESET_METHOD_NAME);

        String form1 = getParentId() + "_form1:";
        String form2 = getParentId() + "_form2:";

        String group = form1 + "pGroup3";
        String sigleGroup = form2 + "pGroup3_single";

        String ajaxItem = "tablehide" + form1 + "pItem1";
        String serverItem = "tablehide" + form1 + "pItem2";
        String clientItem = "tablehide" + form1 + "pItem3";

        writeStatus("Test expand JS API method for ajax multiple panel menu.");
        selenium.runScript("$('" + group + "').component.expand()");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertTrue(isVisibleById(clientItem));

        writeStatus("Test collapse JS API method for ajax multiple panel menu.");
        selenium.runScript("$('" + group + "').component.collapse()");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        ajaxItem = "tablehide" + form2 + "pItem1_single";
        serverItem = "tablehide" + form2 + "pItem2_single";
        clientItem = "tablehide" + form2 + "pItem3_single";

        writeStatus("Test expand JS API method for ajax single panel menu.");
        selenium.runScript("$('" + sigleGroup + "').component.expand()");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertTrue(isVisibleById(clientItem));

        writeStatus("Test collapse JS API method for ajax single panel menu.");
        selenium.runScript("$('" + sigleGroup + "').component.collapse()");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));
    }

    @Test
    public void testPanelMenuComponent(Template template) {
        renderPage(template, RESET_METHOD_NAME);

        String parentId = getParentId() + "_form1:";

        String value1Id = getParentId() + "_value";
        String value2Id = getParentId() + "_value2";

        String ajaxGroup = "tablehide" + parentId + "pGroup1";
        String serverGroup = "tablehide" + parentId + "pGroup2";
        String clientGroup = "tablehide" + parentId + "pGroup3";
        String disabledGroup = "tablehide" + parentId + "pGroup4";

        String ajaxItem = "tablehide" + parentId + "pItem1";
        String serverItem = "tablehide" + parentId + "pItem2";
        String clientItem = "tablehide" + parentId + "pItem3";
        String disabledItem = "tablehide" + parentId + "pItem4";

        writeStatus("Click on ajax group");
        clickById(ajaxGroup);
        waitForAjaxCompletion();
        AssertTextEquals(value1Id, "pGroup1");
        AssertTextEquals(value2Id, "1");
        Assert.assertTrue(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on server group");
        clickCommandAndWait(serverGroup);
        AssertTextEquals(value1Id, "pGroup2");
        AssertTextEquals(value2Id, "2");
        Assert.assertTrue(isVisibleById(ajaxItem));
        Assert.assertTrue(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on ajax group");
        clickById(ajaxGroup);
        waitForAjaxCompletion();
        AssertTextEquals(value1Id, "pGroup1");
        AssertTextEquals(value2Id, "3");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertTrue(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on server group");
        clickCommandAndWait(serverGroup);

        AssertTextEquals(value1Id, "pGroup2");
        AssertTextEquals(value2Id, "4");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on client group");
        clickById(clientGroup);
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertTrue(isVisibleById(clientItem));

        writeStatus("Click on client group");
        clickById(clientGroup);
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on disabled client group");
        clickById(disabledGroup);
        Assert.assertFalse(isVisibleById(disabledItem));
    }

    @Test
    public void testPanelMenuComponentSingleMode(Template template) {
        renderPage(template, RESET_METHOD_NAME);

        String parentId = getParentId() + "_form2:";

        String value1Id = getParentId() + "_value";
        String value2Id = getParentId() + "_value2";

        String ajaxGroup = "tablehide" + parentId + "pGroup1_single";
        String serverGroup = "tablehide" + parentId + "pGroup2_single";
        String clientGroup = "tablehide" + parentId + "pGroup3_single";
        String disabledGroup = "tablehide" + parentId + "pGroup4_single";

        String ajaxItem = "tablehide" + parentId + "pItem1_single";
        String serverItem = "tablehide" + parentId + "pItem2_single";
        String clientItem = "tablehide" + parentId + "pItem3_single";
        String disabledItem = "tablehide" + parentId + "pItem4_single";

        writeStatus("Click on ajax group");
        clickById(ajaxGroup);
        waitForAjaxCompletion();
        AssertTextEquals(value1Id, "pGroup1_single");
        AssertTextEquals(value2Id, "1");
        Assert.assertTrue(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on server group");
        clickCommandAndWait(serverGroup);
        AssertTextEquals(value1Id, "pGroup2_single");
        AssertTextEquals(value2Id, "2");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertTrue(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on ajax group");
        clickById(ajaxGroup);
        waitForAjaxCompletion();
        AssertTextEquals(value1Id, "pGroup1_single");
        AssertTextEquals(value2Id, "3");
        Assert.assertTrue(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on server group");
        clickCommandAndWait(serverGroup);
        AssertTextEquals(value1Id, "pGroup2_single");
        AssertTextEquals(value2Id, "4");
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertTrue(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on client group");
        clickById(clientGroup);
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertTrue(isVisibleById(clientItem));

        writeStatus("Click on client group");
        clickById(clientGroup);
        Assert.assertFalse(isVisibleById(ajaxItem));
        Assert.assertFalse(isVisibleById(serverItem));
        Assert.assertFalse(isVisibleById(clientItem));

        writeStatus("Click on disabled client group");
        clickById(disabledGroup);
        Assert.assertFalse(isVisibleById(disabledItem));
    }

    @Test
    public void testPanelMenuItemActionAndSubmitModeInheritance(Template template) {
        renderPage(template, RESET_METHOD_NAME);

        String parentId = getParentId() + "_form";
        String itemAction = getParentId() + "_itemAction";

        writeStatus("Test panel menu with ajax submission mode.");
        writeStatus("Click on ajax group");

        clickAjaxCommandAndWait("tablehide" + parentId + "1:pGroup1");

        writeStatus("Click on expanded server action item");
        clickCommandAndWait("tablehide" + parentId + "1:pItem1");
        AssertTextEquals(itemAction, "server", "Server mode action is not triggered");

        writeStatus("Click on server group");

        clickCommandAndWait("tablehide" + parentId + "1:pGroup2");

        writeStatus("Click on expanded ajax action item");
        clickAjaxCommandAndWait("tablehide" + parentId + "1:pItem2");
        AssertTextEquals(itemAction, "ajax", "Ajax mode action is not triggered");

        writeStatus("Click on client group");

        clickById("tablehide" + parentId + "1:pGroup3");

        writeStatus("Click on expanded none action item");
        clickById("tablehide" + parentId + "1:pItem3");
        AssertTextNotEquals(itemAction, "NOT none", "Nothing is going to happen");

        writeStatus("Test panel menu with default(server) submission mode.");
        writeStatus("Click on ajax group");

        clickAjaxCommandAndWait("tablehide" + parentId + "2:pGroup1_single");

        writeStatus("Click on expanded server action item");
        clickCommandAndWait("tablehide" + parentId + "2:pItem1_single");
        AssertTextEquals(itemAction, "server", "Server mode action is not triggered");

        writeStatus("Click on server group");

        clickCommandAndWait("tablehide" + parentId + "2:pGroup2_single");

        writeStatus("Click on expanded ajax action item");
        clickAjaxCommandAndWait("tablehide" + parentId + "2:pItem2_single");
        AssertTextEquals(itemAction, "ajax", "Ajax mode action is not triggered");

        writeStatus("Click on client group");

        clickById("tablehide" + parentId + "2:pGroup3_single");

        writeStatus("Click on expanded none action item");
        clickById("tablehide" + parentId + "2:pItem3_single");
        AssertTextNotEquals(itemAction, "NOT none", "Nothing is going to happen");
    }

    @Test
    public void testIconsAttributesAreApplied(Template template) {
        renderPage(COSMETIC_TEST_URL, template, RESET_METHOD_NAME);

        writeStatus("Check icons attributes applied: are output to client and images are accessible");

        String parentId = getParentId() + "_form3:";

        testIcon("pGroup1_selected", "Triangle");
        testIcon("pGroup2_selected", "Triangle");
        testIcon("pGroup3_selected", "Triangle");

        writeStatus("Expose group 2");        
        clickById("tablehide" + parentId + "pGroup2_selected");

        testIcon("pGroup2_selected", "Spacer");
        testIcon("pItem2_selected", "Chevron");
    }

    @Test
    public void testRenderedComponentAttribute(Template template) {
        renderPage(COSMETIC_TEST_URL, template, RESET_METHOD_NAME);

        writeStatus("Check the component with rendered = false is not present on the page");

        AssertPresent(getParentId() + "_form3:pMenu_selected");
        clickCommandAndWait(getParentId() + "_form3:_hide");
        AssertNotPresent(getParentId() + "_form3:pMenu_selected");
    }

    @Test
    public void testStyleAndClassesStandardHTMLAttributesAreOutputToClient(Template template) {
        renderPage(COSMETIC_TEST_URL, template, RESET_METHOD_NAME);

        writeStatus("Check style and classes, standard HTML attributes are output to client");

        String menuId = getParentId() + "_form3:pMenu_selected";
        assertStyleAttribute(menuId, "font-size: 13px", "Style attribute was not output to client");
        assertClassNames(menuId, new String[] { "noclass" }, "Class attribute was not output to client", true);

        String menuTopGroupId = "tablehide" + getParentId() + "_form3:pGroup1_selected";
        assertStyleAttribute(menuTopGroupId, "font-size: 14px", "topGroupStyle attribute was not output to client");
        assertClassNames(menuTopGroupId, new String[] { "topGroupClass" }, "topGroupClass attribute was not output to client", true);

        String menuGroupId = "tablehide" + getParentId() + "_form3:pGroup11_selected";
        assertStyleAttribute(menuGroupId, "font-size: 15px", "groupStyle attribute was not output to client");
        assertClassNames(menuGroupId, new String[] { "groupClass" }, "groupClass attribute was not output to client", true);

        String menuItemId = "tablehide" + getParentId() + "_form3:pItem1_selected";
        assertStyleAttribute(menuItemId, "font-size: 16px", "itemStyle attribute was not output to client");
        assertClassNames(menuItemId, new String[] { "itemClass" }, "itemClass attribute was not output to client", true);
    }

    @Test
    public void testSelectedChildAttributeIsReadOnRenderingAndUpdatedOnFormSubmit(Template template) {
        renderPage(COSMETIC_TEST_URL, template, RESET_METHOD_NAME);

        writeStatus("Check selectedChild attribute is read on rendering and updated on form submit");

        String parentId = "row_" + getParentId() + "_form3:";
        assertClassNames(parentId + "pGroup2_selected", ITEM_HIGHLIGHTS, "Group 2 must be highlighted", true);

        writeStatus("Click group 1");
        String group1 = parentId + "pGroup1_selected";
        clickById(group1);
        assertClassNames(group1, ITEM_HIGHLIGHTS, "Group 1 must be highlighted", true);

        writeStatus("Click group 1.1");
        String group11 = parentId + "pGroup11_selected";
        clickById(group11);
        assertClassNames(group11, ITEM_HIGHLIGHTS, "Group 1.1 must be highlighted", true);

        writeStatus("Click item 1.1 Submit is being initiated");
        String item11 = parentId + "pItem11_selected";
        clickCommandAndWait(item11);
        assertClassNames(item11, ITEM_HIGHLIGHTS, "Item 1.1 must be highlighted", true);
    }

    /**
     * Test an icon.
     *
     * @param iconId id of image representing icon to be tested
     * @param iconName type of icon e. g. Chevron, Triangle
     */
    private void testIcon(String iconId, String iconName) {
        String parentId = getParentId() + "_form3:";

        String iconSrc = selenium.getAttribute("//img[@id='leftIcon" + parentId + iconId + "']/@src");

        if (null == iconSrc || !iconSrc.matches(".*" + iconName + ".*")) {
            Assert.fail("It looks as if the icon is not proper. " + iconName + " icon is expected");
        }
    }

    public String getTestUrl() {
        return "pages/panelMenu/panelMenuTest.xhtml";
    }
}
