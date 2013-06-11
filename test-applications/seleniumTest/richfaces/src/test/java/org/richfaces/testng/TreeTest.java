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

import org.ajax4jsf.listener.TreeNodeExpandedListener;
import org.ajax4jsf.listener.TreeNodeSelectedListener;
import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.richfaces.AutoTester.TestSetupEntry;
import org.testng.Assert;
import org.testng.annotations.Test;

public abstract class TreeTest extends SeleniumTestBase {

    private static Map<String, String> params = new HashMap<String, String>();
    private final static String INIT_TOGGLE_ON_CLICK_SERVER_MODE = "#{treeBean.initToggleOnClickTestServerMode}";
    private final static String INIT_TOGGLE_ON_CLICK_AJAX_MODE = "#{treeBean.initToggleOnClickTestAjaxMode}";
    private final static String INIT_TOGGLE_ON_CLICK_CLIENT_MODE = "#{treeBean.initToggleOnClickTestClientMode}";
    private final static String INIT_AJAX_CORE_TEST = "#{treeBean.initAjaxCoreTest}";
    private final static String INIT_AJAX_SUBMIT_SELECTION = "#{treeBean.initAjaxSubmitSelectionTest}";
    private final static String INIT_SERVER_MODE = "#{treeBean.initServerMode}";
    private final static String INIT_AJAX_MODE = "#{treeBean.initAjaxMode}";
    private final static String INIT_CLIENT_MODE = "#{treeBean.initClientMode}";
    private final static String INIT_ADVISOR_TEST = "#{treeBean.initStateAdvisorTest}";
    private final static String INIT_KEYBOARD_NAVIGATION_TEST = "#{treeBean.initKeyboardNavigationTest}";
    private final static String TEAR_DOWN_METHOD = "#{treeBean.tearDown}";
    private final static String INIT_DRAG_AND_DROP_TEST = "#{treeBean.initDragAndDropTest}";
    private final static String DRAG_AND_DROP_TEST_URL = "dragAndDropTest.xhtml";

    static final Map<String, String> styles = new HashMap<String, String>();

    static {
        params.put("parameter1", "value1");
        params.put("parameter2", "value2");
        params.put("parameter3", "value3");

        styles.put("font-size", "13px");
        styles.put("font-weight", "bold");
    }

    @Test
    public void testRenderedAttribute(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component with rendered = false is not present on the page");
        tester.testRendered();
    }

    @Test
    public void testNestedParams(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        writeStatus("Test component encodes nested f:param tags and their values are present as request parameters");
        tester.testRequestParameters(params);
    }

    @Test
    public void testAjaxSingle(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        tester.reset();
        tester.setupControl(TestSetupEntry.ajaxSingle, Boolean.TRUE);
        tester.clickLoad();
        writeStatus("Navigate to the first child");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        writeStatus("Check node expanded events are delivered");
        tester.checkNodeExpandedListener(true);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        tester.setExtrenalValidationFailed();
        tester.startTracing();
        writeStatus("Test ajaxSingle attribute");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(false);
        tester.checkNodeSelectedListener(true);
    }

    @Test
    public void testAjaxSingleWithInternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        tester.reset();
        tester.setupControl(TestSetupEntry.ajaxSingle, Boolean.TRUE);
        tester.clickLoad();
        writeStatus("Navigate to the first child");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        writeStatus("Check node expanded events are delivered");
        tester.checkNodeExpandedListener(true);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        writeStatus("Set a new invalid value for the child");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "");
        tester.startTracing();
        writeStatus("Test ajaxSingle attribute in case of invalid component state");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(false);
        tester.checkNodeSelectedListener(false);
    }

    @Test
    public void testImmediate(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        tester.reset();
        tester.setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        tester.clickLoad();
        writeStatus("Navigate to the first child");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        writeStatus("Check node expanded events are delivered");
        tester.checkNodeExpandedListener(true);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        writeStatus("Set a new value for the child");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        tester.startTracing();
        writeStatus("Test immediate attribute");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(true);
        tester.checkNodeSelectedListener(true);
    }

    @Test
    public void testImmediateWithExternalValidationFailed(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        tester.reset();
        tester.setupControl(TestSetupEntry.immediate, Boolean.TRUE);
        tester.clickLoad();
        writeStatus("Navigate to the first child");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        writeStatus("Check node expanded events are delivered");
        tester.checkNodeExpandedListener(true);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        writeStatus("set a new value for the child");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        writeStatus("Set external validation to fail");
        tester.setExtrenalValidationFailed();
        tester.startTracing();
        writeStatus("Test immediate attribute with external validation failed");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(false);
        tester.checkNodeSelectedListener(true);
    }

    @Test
    public void testToggleOnClickTrueWithServerMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_TOGGLE_ON_CLICK_SERVER_MODE);
        tester.reset();
        tester.clickLoad();
        writeStatus("Try to toggle a node by click with server switch type");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickTreeNode("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        waitForPageToLoad();
        writeStatus("Check the node is expanded and proper listeners are invoked");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has not been expanded");
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);
        tester.checkUpdateModel(true);
        tester.startTracing();
        writeStatus("Test the same in case of external validation failure");
        writeStatus("Set external validation to fail");
        tester.setExtrenalValidationFailed();
        clickTreeNode("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        waitForPageToLoad();
        writeStatus("Check the node is not collapsed and no listeners are invoked due to external validation error");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has been collapsed");
        tester.checkNodeExpandedListener(false);
        tester.checkNodeSelectedListener(false);
        tester.checkUpdateModel(false);
    }

    @Test
    public void testToggleOnClickTrueWithAjaxMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_TOGGLE_ON_CLICK_AJAX_MODE);
        tester.reset();
        tester.clickLoad();
        writeStatus("Try to toggle a node by click with ajax swith type");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        writeStatus("Check the node is expanded and proper listeners are invoked");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has not been expanded");
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);
        tester.checkUpdateModel(true);
        tester.startTracing();
        writeStatus("Test the same in case of external validation failure");
        writeStatus("Set external validation to fail");
        tester.setExtrenalValidationFailed();
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        writeStatus("Check the node is not collapsed and no listeners are invoked thanks to external validation error");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has been collapsed");
        tester.checkNodeExpandedListener(false);
        tester.checkNodeSelectedListener(false);
        tester.checkUpdateModel(false);
    }

    @Test
    public void testToggleOnClickTrueWithClientMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_TOGGLE_ON_CLICK_CLIENT_MODE);
        tester.reset();
        tester.clickLoad();
        writeStatus("Try to toggle a node by click with client swith type");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickTreeNode("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        writeStatus("Check the node is expanded and no listeners are invoked");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has not been expanded");
        tester.checkNodeExpandedListener(false);
        tester.checkNodeSelectedListener(false);
        tester.checkUpdateModel(false);
        tester.startTracing();
        writeStatus("Test the same in case of external validation failure");
        writeStatus("Set external validation to fail");
        tester.setExtrenalValidationFailed();
        clickTreeNode("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        writeStatus("Check the node is collapsed and no listeners are invoked anyway");
        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]"), "Node has not been collapsed");
        tester.checkNodeExpandedListener(false);
        tester.checkNodeSelectedListener(false);
        tester.checkUpdateModel(false);
    }

    @Test
    public void testAjaxSubmitSelection(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_SUBMIT_SELECTION);
        tester.reset();
        tester.clickLoad();
        writeStatus("test ajaxSubmitSelection attribute");
        tester.startTracing();
        writeStatus("the form has to be submitted and proper listeners are invoked at every node click");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(true);
        tester.checkNodeSelectedListener(true);
        tester.startTracing();
        selenium.click("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);
        tester.startTracing();
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]");
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);
        tester.startTracing();
        writeStatus("Set external validation to fail and click on node. No listeners must be invoked");
        tester.setExtrenalValidationFailed();
        clickTreeNodeAndWait("//*[@id='"+ compId + ":childs']/div/div/table[2]/tbody/tr/td[3]");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        tester.checkNodeSelectedListener(false);
    }

    @Test
    public void testServerMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_SERVER_MODE);
        tester.reset();
        tester.clickLoad();
        tester.startTracing();
        writeStatus("Check server mode: a component is submitted, proper listeners are invoked");
        writeStatus("Navigate to the first child. Node must be exposed, model - updated, listeners - invoked");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        selenium.click("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        waitForPageToLoad();
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.startTracing();
        writeStatus("Navigate to the second child. Node must be exposed, model - updated, listeners - invoked");
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        waitForPageToLoad();
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.startTracing();
        writeStatus("Set tree to invalid state and try to collapse node. Model is not updated, no listeners are invoked");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "");
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        waitForPageToLoad();
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        tester.startTracing();
        writeStatus("The same with external validation failure");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        tester.setExtrenalValidationFailed();
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        waitForPageToLoad();
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
    }

    @Test
    public void testAjaxMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_MODE);
        tester.reset();
        tester.clickLoad();
        tester.startTracing();
        writeStatus("Check ajax mode: a component is submitted, proper listeners are invoked");
        writeStatus("Navigate to the first child. Node must be exposed, model - updated, listeners - invoked");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.startTracing();
        writeStatus("Navigate to the second child. Node must be exposed, model - updated, listeners - invoked");
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(true);
        tester.checkNodeExpandedListener(true);
        tester.startTracing();
        writeStatus("Set tree to invalid state and try to collapse node. Model is not updated, no listeners are invoked");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "");
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        tester.startTracing();
        writeStatus("The same with external validation failure");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        tester.setExtrenalValidationFailed();
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
    }

    @Test
    public void testClientMode(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_CLIENT_MODE);
        tester.reset();
        tester.clickLoad();
        tester.startTracing();
        writeStatus("Check client mode: all the time node is exposed, model stays untouched, no listeners are invoked");
        writeStatus("Navigate to the first child.");
        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        selenium.click("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        writeStatus("Navigate to the second child.");
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        writeStatus("Set tree to invalid state and try to collapse node. Now all is allowed");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "");
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        Assert.assertFalse(isVisible("//*[@id='"+ compId + ":childs']/div/div"), "Node has not been collapsed");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
        writeStatus("The same with external validation failure");
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        type("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]/input", "New");
        tester.setExtrenalValidationFailed();
        selenium.click("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        Assert.assertFalse(isVisible("//*[@id='"+ compId + ":childs']/div/div"), "Node has not been collapsed");
        tester.checkUpdateModel(false);
        tester.checkNodeExpandedListener(false);
    }

    @Test
    public void testNodePresentationCanBeCustomizedUsingRenderedAndNodeFaceAttributes(Template template) {
        renderPage(template, TEAR_DOWN_METHOD);
        writeStatus("Check node presentation can be customized using rendered and nodeFace attributes");

        String compId = getParentId() + "_form:tree";
        writeStatus("Bretney is not my favourite singer (I don't even know how it spells). Check it");
        testIcon("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[2]/img", "singer");
        AssertTextEquals("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]", "Britney Spears");

        writeStatus("Christina is my favourite one. Test it");
        testIcon("//*[@id='"+ compId + ":childs']/table[2]/tbody/tr/td[2]/img", "favorite");
        AssertTextEquals("//*[@id='"+ compId + ":childs']/table[2]/tbody/tr/td[3]", "Christina Aguilera [Oh, Lord! She's my favorite]");
    }

    @Test
    public void testIconsAttributesAreApplied(Template template) {
        renderPage(template, TEAR_DOWN_METHOD);

        writeStatus("Check icons attributes applied: are output to client and images are accessible");

        String compId = getParentId() + "_form:tree";

        testIcon("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[2]/img", "singer");
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a");
        testIcon("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[2]/img", "disc");
        clickAjaxCommandAndWait("//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td/div/a");
        testIcon("//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[2]/img", "song");
    }

    @Test
    public void testTreeStateAdvisor(Template template) {
        renderPage(template, INIT_ADVISOR_TEST);
        writeStatus("Check tree state advisors work properly");
        writeStatus("All nodes have to be expanded thanks to advisor set. Test it");

        String compId = getParentId() + "_form:tree";

        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div[1]"), "It looks as if there are unexpanded nodes here");
        Assert.assertTrue(isVisible("//*[@id='"+ compId + ":childs']/div[1]"), "It looks as if there are unexpanded nodes here");

        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div[1]/div"), "It looks as if there are unexpanded nodes here");
        Assert.assertTrue(isVisible("//*[@id='"+ compId + ":childs']/div[1]/div"), "It looks as if there are unexpanded nodes here");

        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div[2]"), "It looks as if there are unexpanded nodes here");
        Assert.assertTrue(isVisible("//*[@id='"+ compId + ":childs']/div[2]"), "It looks as if there are unexpanded nodes here");

        Assert.assertTrue(isPresent("//*[@id='"+ compId + ":childs']/div[2]/div"), "It looks as if there are unexpanded nodes here");
        Assert.assertTrue(isVisible("//*[@id='"+ compId + ":childs']/div[2]/div"), "It looks as if there are unexpanded nodes here");
    }

    @Test
    public void testDragAndDrop(Template template) {
        renderPage(DRAG_AND_DROP_TEST_URL, template, INIT_DRAG_AND_DROP_TEST);
        writeStatus("Check drag-and-drop ability");

        String compId = getParentId() + "_form:tree";
        String britneyAlbumXpath = "//*[@id='"+ compId + ":childs']/div[1]/table/tbody/tr/td[3]";
        String britney1stSongXpath = "//*[@id='"+ compId + ":childs']/div[1]/div/table/tbody/tr/td[3]";

        String aguileraAlbumXpath = "//*[@id='"+ compId + ":childs']/div[2]/table/tbody/tr/td[3]";
        String aguilera1stSongXpath = "//*[@id='"+ compId + ":childs']/div[2]/div/table/tbody/tr/td[3]";

        int britneySons = getNodesCount("//*[@id='"+ compId + ":childs']/div[1]/div");
        int aguileraSongs = getNodesCount("//*[@id='"+ compId + ":childs']/div[2]/div");

        writeStatus("Try to have brithey's song dragged");
        selenium.dragAndDropToObject(britney1stSongXpath, aguileraAlbumXpath);
        waitForAjaxCompletion();
        assertChildNodesCount(--britneySons, "//*[@id='"+ compId + ":childs']/div[1]/div", "Song is not dragged");
        assertChildNodesCount(++aguileraSongs, "//*[@id='"+ compId + ":childs']/div[2]/div", "Song is not dropped");

        writeStatus("Now try to drop song back");
        selenium.dragAndDropToObject(aguilera1stSongXpath, britneyAlbumXpath);
        waitForAjaxCompletion();
        assertChildNodesCount(++britneySons, "//*[@id='"+ compId + ":childs']/div[1]/div", "Song is not dragged back");
        assertChildNodesCount(--aguileraSongs, "//*[@id='"+ compId + ":childs']/div[2]/div", "Song is not dropped back");
    }

    @Test
    public void testStyleAndClassesAreOutputToClient(Template template) {
        renderPage(template, TEAR_DOWN_METHOD);

        writeStatus("Check style and classes are output to client");
        String compId = getParentId() + "_form:tree";

        assertClassNames(compId, new String[] { "noclass" }, "Class attribute was not output to client", true);
        assertStyleAttributes(compId, styles);

        writeStatus("Check css class of highlighted tree node");
        String aNodeXpath = "//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]";
        selenium.mouseOver(aNodeXpath);
        assertClassAttributeContains(aNodeXpath, "highlighted-class", "highlightedClass attribute was not output to client");
    }

    @Test
    public void testKeyboardNavigation(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_KEYBOARD_NAVIGATION_TEST);
        tester.reset();
        tester.clickLoad();
        writeStatus("Check keyboard navigation for component");

        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        String firstNodeXpath = "//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td[3]";
        String secondNodeXpath = "//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]";
        String thirdNodeXpath = "//*[@id='"+ compId + ":childs']/div/div/table[1]/tbody/tr/td[3]";

        writeStatus("Select first node and press right arrow. Check the node is expanded and proper events are delivered");
        clickTreeNode(firstNodeXpath);
        selenium.keyDown(firstNodeXpath, "\\39");//right
        waitForAjaxCompletion();
        Assert.assertTrue(isPresent(secondNodeXpath), "Node has not been expanded");
        tester.checkNodeExpandedListener(true);

        tester.startTracing();
        //it looks as if a focus is gone as the result of start tracing try to restore one
        clickTreeNode(firstNodeXpath);

        writeStatus("Move down to the second node and press right arrow. Check the same");
        selenium.keyDown(firstNodeXpath, "\\40");//down
        selenium.keyDown(secondNodeXpath, "\\39");
        waitForAjaxCompletion();
        Assert.assertTrue(isPresent(thirdNodeXpath), "Node has not been expanded");
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);

        tester.startTracing();
        //it looks as if a focus is gone as the result of start tracing try to restore one
        clickTreeNode(secondNodeXpath);

        writeStatus("Press left. Check the node is collapsed and proper events are delivered");
        selenium.keyDown(secondNodeXpath, "\\37");//left
        waitForAjaxCompletion();
        Assert.assertFalse(isPresent(thirdNodeXpath), "Node has not been collapsed");
        tester.checkNodeExpandedListener(true);

        tester.startTracing();
        //it looks as if a focus is gone as the result of start tracing try to restore one
        clickTreeNode(secondNodeXpath);

        writeStatus("Move up to the first node and press left arrow. Check the same");
        selenium.keyDown(secondNodeXpath, "\\38");//up
        selenium.keyDown(firstNodeXpath, "\\37");
        waitForAjaxCompletion();
        Assert.assertFalse(isPresent(secondNodeXpath), "Node has not been collapsed");
        tester.checkNodeExpandedListener(true);
        tester.checkNodeSelectedListener(true);

        //l37
        //u38
        //r39
        //d40
    }

    @Test
    public void testNodeSelectedAndNodeExpandedListeners(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, INIT_AJAX_CORE_TEST);
        tester.reset();
        tester.clickLoad();
        writeStatus("Check NodeExpandedListener and NodeSelectedListener");

        String compId = getAutoTester(this).getClientId(AutoTester.COMPONENT_ID);
        String firstNodeHandlerXpath = "//*[@id='"+ compId + ":childs']/table[1]/tbody/tr/td/div/a";
        String secondNodeXpath = "//*[@id='"+ compId + ":childs']/div/table[1]/tbody/tr/td[3]";

        writeStatus("Expand the first node. NodeExpandedListener must be triggered. NodeSelectedListener should remain untouched. Check it");
        clickAjaxCommandAndWait(firstNodeHandlerXpath);
        Assert.assertTrue(tester.checkMessage(TreeNodeExpandedListener.NE_LISTENER, true), "NodeExpandedListener has to be triggered");
        Assert.assertTrue(tester.checkMessage(TreeNodeSelectedListener.NS_LISTENER, false), "NodeSelectedListener has not to be triggered");

        writeStatus("Select the second node. Now vice versa: NodeSelectedListener must be triggered. NodeExpandedListener should remain untouched. Check it");
        tester.startTracing();
        clickTreeNodeAndWait(secondNodeXpath);
        Assert.assertTrue(tester.checkMessage(TreeNodeExpandedListener.NE_LISTENER, false), "NodeExpandedListener has not to be triggered");
        Assert.assertTrue(tester.checkMessage(TreeNodeSelectedListener.NS_LISTENER, true), "NodeSelectedListener has to be triggered");
    }

    @Override
    public void sendAjax() {
        AutoTester tester = getAutoTester(this);
        String compId = tester.getClientId(AutoTester.COMPONENT_ID);
        String xpath = "//*[@id='" + compId + ":childs']/table[1]/tbody/tr/td[3]";
        clickTreeNodeAndWait(xpath);
    }

    @Override
    public String getTestUrl() {
        return "treeTest.xhtml";
    }

    @Override
    public String getAutoTestUrl() {
        return "treeAutoTest.xhtml";
    }

    private void clickTreeNode(String locator){
        //workaround: selenium triggerMouseEvent method (only in IE) ignores mouse event button property unless
        //it is supplied with some specific parameters (e.g. event coordinates) while it is highly expected by tree node handlers
        selenium.mouseDownAt(locator, "1,1");
        selenium.clickAt(locator, "1,1");
    }

    private void clickTreeNodeAndWait(String locator){
        clickTreeNode(locator);
        waitForAjaxCompletion();
    }

    /**
     * Test an icon.
     *
     * @param location location of image representing icon to be tested
     * @param iconName substring that icon uri has to contain
     */
    private void testIcon(String location, String iconSubstring) {
        String iconSrc = selenium.getAttribute(location + "/@src");
        if (null == iconSrc || !iconSrc.matches(".*" + iconSubstring + ".*")) {
            Assert.fail("It looks as if the icon is not proper. Uri of icon is being tested must contain [" + iconSubstring + "]");
        }
    }

    /**
     * Asserts that given div <code>containingDiv</code> contains <code>childrenCount</code> nodes
     * @param nodesCount expected nodes count
     * @param xpath xpath location of nodes containing div
     * @param errorMsg error msg
     */
    private void assertChildNodesCount(int nodesCount, String xpath, String errorMsg) {
        int count = getNodesCount(xpath);
        Assert.assertEquals(count, nodesCount, errorMsg);
    }

    private int getNodesCount(String xpath) {
        return selenium.getXpathCount(xpath + "/table").intValue();
    }

}
