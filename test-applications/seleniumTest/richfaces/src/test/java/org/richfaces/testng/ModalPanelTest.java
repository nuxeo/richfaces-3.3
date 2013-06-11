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
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * rich:modalPannel component selenium test
 * @author Alexandr Levkovsky
 *
 */
public class ModalPanelTest extends SeleniumTestBase {
    
	static final String TRIM_OVERLAYED_TEST = "pages/modalPanel/testTrimOverlayedElements.xhtml";
    private final static String PANEL_ID="test_panel";
    private final static String FORM_ID="form:";
    private final static String FORM2_ID="_f:";
    private final static String RESET_ID="reset";
    private final static String CHANGE_RENDERED_ID="change_rendered";
    private final static String CHANGE_ATTRIBUTES_ID="change_attributes";
    private final static String CHANGE_AUTOSIZED_ID = "set_autosized";
    private final static String CHANGE_VISUAL_STATE_ID = "keep_visual"; 
    private final static String HIDE_ID="hide";
    private final static String SHOW_ID="show";
    private final static String PANEL_C_DIV_ID = PANEL_ID + "CDiv";
    private final static String PANEL_CONTENT_DIV_ID = PANEL_ID + "ContentDiv";
    private final static String PANEL_CONTAINER_DIV_ID = PANEL_ID + "Container";
    private final static String PANEL_HEADER_ID = PANEL_ID + "Header";
    
    private final static String RESET_METHOD_NAME = "#{modalPanelBean.reset}";
    
    
    /**
     * @see org.richfaces.SeleniumTestBase#getTestUrl()
     */
    @Override
    public String getTestUrl() {
	return "pages/modalPanel/modalPanelTest.xhtml";
    }
    
    @Test
    public void testTrimOverlayedElementsAttribute(Template template) {
    	renderPage(TRIM_OVERLAYED_TEST, template, RESET_METHOD_NAME);
    	
    	String booleanId = getParentId() + FORM_ID + "trimOverlayed";
    	String submitId = getParentId() +FORM_ID + "submit";
    	String showButtonId = "show";
    	
    	clickById(showButtonId);
    	
    	Map<String, String> styles = new HashMap<String, String>();
    	styles.put("position", "relative");
    	styles.put("z-index", "0");
    	
    	assertStyleAttributes(getParentId() + PANEL_CONTENT_DIV_ID, styles);
    
    	selenium.click(booleanId);
    	//selenium.check(booleanId);
    	clickCommandAndWait(submitId);
    	clickById(showButtonId);
    	
    	styles.clear();
    	styles.put("position", "static");
    
    	assertStyleAttributes(getParentId() + PANEL_CONTENT_DIV_ID, styles);
    }

    @Test
    public void testModalPanel(Template template) throws Exception {
    renderPage(template, RESET_METHOD_NAME);
	writeStatus("Testing modal panel");

	String panelId = getParentId() + PANEL_CONTAINER_DIV_ID;

	AssertPresent(panelId);
	AssertNotVisible(panelId);

	clickShow();

	AssertPresent(panelId);
	AssertVisible(panelId);

	clickHide();

	AssertPresent(panelId);
	AssertNotVisible(panelId);

    }

    @Test
    public void testRenderedAttribute(Template template) throws Exception {
    renderPage(template, RESET_METHOD_NAME);
	writeStatus("Testing rendered attribute");

	String panelId = getParentId() +  PANEL_CONTAINER_DIV_ID;

	AssertPresent(panelId);

	clickChangeRendered();

	AssertNotPresent(panelId);
	
    }

    @Test
    public void testShowWhenRenderedAttribute(Template template) throws Exception {
    renderPage(template, RESET_METHOD_NAME);
	writeStatus("Testing showWhenRendered attribute");

	String panelId = getParentId() + PANEL_CONTAINER_DIV_ID;

	AssertPresent(panelId);
	AssertNotVisible(panelId);

	clickChangeAttributes();

	AssertPresent(panelId);
	AssertVisible(panelId);
    }
    
    @Test
    public void testNotResizeableAndNotMoveable(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	writeStatus("Testing not resizeable panel");

    	String panelId = getParentId() + PANEL_CONTAINER_DIV_ID;
    	String resizerNId = getParentId() + PANEL_ID + "ResizerN";
    	String resizerSELId = getParentId() + PANEL_ID + "ResizerSEL";
    	String resizerSWUId = getParentId() + PANEL_ID + "ResizerSWU";
    	String headerId = getParentId() + PANEL_HEADER_ID;
    	String cIdvId = getParentId() +  PANEL_C_DIV_ID;

    	clickChangeAttributes();

    	AssertPresent(panelId);
    	AssertVisible(panelId);
    	
    	AssertNotPresent(resizerSELId, "Resizeable = false does not work");
    	AssertNotPresent(resizerSWUId, "Resizeable = false does not work");
    	AssertNotPresent(resizerNId, "Resizeable = false does not work");
    	
    	
    	Integer left = (Integer)getLeftById(cIdvId);
    	Integer top = (Integer)getTopById(cIdvId);
    	
    	selenium.dragAndDrop(headerId, "+10,+10");
    	
    	Assert.assertTrue(((Integer)getLeftById(cIdvId)).equals(left), "Moveable = false attribute does not work");
    	Assert.assertTrue(((Integer)getTopById(cIdvId)).equals(top), "Moveable = false attribute does not work");
         	
    	
    }
    
    @Test
    public void testJSApi(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	String panelId = getParentId() + PANEL_CONTAINER_DIV_ID;
	
    	AssertNotVisible(panelId);
    	switchPanel(true);
    	
    	AssertVisible(panelId, "Modal panel has not opened by JS API");
    	
    	switchPanel(false);
    	
    	AssertNotVisible(panelId, "Modal panel has not closed by JS API");
    }
    
    @Test
    public void testLayoutAttributes(Template template) throws Exception {
    renderPage(template, RESET_METHOD_NAME);
	writeStatus("Testing layout attribute");

	clickShow();

	String panelContainerId = getParentId() + PANEL_CONTAINER_DIV_ID;
	String panelContentId = getParentId() + PANEL_CONTENT_DIV_ID;
	String panelCDivId = getParentId() + PANEL_C_DIV_ID;

	AssertPresent(panelContainerId);
	AssertPresent(panelContentId);
	AssertPresent(panelCDivId);

	// test zindex attribute - should be 12
	assertStyleAttribute(panelContainerId, "z-index: 12");

	if (isFF()) {
	    // test top attribute - should be 101
	    assertStyleAttribute(panelCDivId, "top: 101");

	    // test left attribute - should be 102
	    assertStyleAttribute(panelCDivId, "left: 102");
	}

	// test width attribute - should be 103
	assertStyleAttribute(panelContentId, "width: 103");

	// test height attribute - should be 104
	assertStyleAttribute(panelContentId, "height: 104");
	
	assertClassNames(panelContainerId, new String [] {"noclass"}, "Class attribute was not output to client ", true);
	assertClassNames(panelContainerId, new String [] {"rich-modalpanel"}, "", true);
    }
    
    @Test
    public void testDragByHeader(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	
    	clickShow();

    	String headerId = getParentId() + PANEL_HEADER_ID;
    	String cIdvId = getParentId() +  PANEL_C_DIV_ID;
    	
    	Integer left = (Integer)getLeftById(cIdvId);
    	Integer top = (Integer)getTopById(cIdvId);
    	
    	selenium.dragAndDrop(headerId, "+10,+10");
    	
    	Assert.assertTrue((Integer)getLeftById(cIdvId) - left == 10, "Modal Panel drag failured");
    	Assert.assertTrue((Integer)getTopById(cIdvId) - top == 10, "Modal Panel drag failured");
    	
    	selenium.dragAndDrop(headerId, "-10,-10");
    	
    	Assert.assertTrue(((Integer)getLeftById(cIdvId)).equals(left), "Modal Panel drag failured");
    	Assert.assertTrue(((Integer)getTopById(cIdvId)).equals(top), "Modal Panel drag failured");
   	
    }
    
    @Test
    public void testMinWidthAndMinHeight(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	
    	clickShow();
    	
    	String resizerId = getParentId() + PANEL_ID + "ResizerSEL";
    	String contentId = getParentId() + PANEL_CONTENT_DIV_ID;
    	
    	selenium.dragAndDrop(resizerId, "-100,-100");
    	
    	String width = getWidth(contentId);
    	String height = getHeight(contentId);
    	
    	Assert.assertTrue(width.equals("70px"), "Modal Panel width ["+width+"] does not equal to min width");
    	Assert.assertTrue(height.equals("70px"), "Modal Panel height ["+height+"] does not equal to min height");
    	
    }
    
    @Test
    public void testNestedInputAndCommand(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	
    	final String VALUE = "69";
    	
    	clickShow();
    	
    	String inputId = getParentId() + FORM2_ID + "input";
    	String commandId = getParentId() + FORM2_ID + "submit";
    	
    	AssertPresent(inputId);
    	AssertPresent(commandId);
    	
    	setValueById(inputId, VALUE);
    	
    	clickCommandAndWait(commandId);
    	clickShow();
    	
    	AssertPresent(inputId);
    	AssertPresent(commandId);
    	AssertValueEquals(inputId, VALUE, "Input value does not equal to correct ");
    	
    }
    
    @Test
    public void testAutosized(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	
    	String contentId = getParentId() + PANEL_CONTENT_DIV_ID;
    	String panelId = getParentId() + PANEL_CONTAINER_DIV_ID;
    	String resizerNId = getParentId() + PANEL_ID + "ResizerN";
    	String resizerSELId = getParentId() + PANEL_ID + "ResizerSEL";
    	String resizerSWUId = getParentId() + PANEL_ID + "ResizerSWU";
    	String cIdvId = getParentId() +  PANEL_C_DIV_ID;
    	
   	
    	clickShow();
    	
    	String width = getWidth(contentId);
    	String height = getHeight(contentId);
    	
    	clickSetAutosized();
    	
    	AssertVisible(panelId);   	
    	AssertNotPresent(resizerSELId, "Autosized panel should not be resizeable");
    	AssertNotPresent(resizerSWUId, "Autosized panel should not be resizeable");
    	AssertNotPresent(resizerNId, "Autosized panel should not be resizeable");
    	
    	Assert.assertFalse(getWidth(contentId).equals(width), "Panel was not autosized");
    	Assert.assertFalse(getHeight(contentId).equals(height), "Panel was not autosized");
    	
    	//check moveable
    	writeStatus("Check moveability of autosized panel");
    	String headerId = getParentId() + PANEL_HEADER_ID;
    	
    	Integer left = (Integer)getLeftById(cIdvId);
    	Integer top = (Integer)getTopById(cIdvId);
    	
    	selenium.dragAndDrop(headerId, "+10,+10");
    	
    	Assert.assertFalse(((Integer)getLeftById(cIdvId)).equals(left), "Autosized panel should be moved");
    	Assert.assertFalse(((Integer)getTopById(cIdvId)).equals(top), "Autosized panel should be moved");
    	
    }
    
    @Test
    public void testKeepVisualState(Template template) {
    	renderPage(template, RESET_METHOD_NAME);
    	String message = "KeepVisualState attribute does not work";
    	clickChangeVisualState();
    	
    	
    	String headerId = getParentId() + PANEL_HEADER_ID;
    	String cIdvId = getParentId() +  PANEL_C_DIV_ID;
    	String resizerId = getParentId() + PANEL_ID + "ResizerSEL";
    	String contentId = getParentId() + PANEL_CONTENT_DIV_ID;
        	
    	selenium.dragAndDrop(headerId, "+10,+10");
    	selenium.dragAndDrop(resizerId, "+10,+10");
    	
    	Integer left = (Integer)getLeftById(cIdvId);
    	Integer top = (Integer)getTopById(cIdvId);
    	
    	String width = getWidth(contentId);
    	String height = getHeight(contentId);
    	
    	String commandId = getParentId() + FORM2_ID + "submit";
    	clickCommandAndWait(commandId);
    	
    	Assert.assertTrue(((Integer)getLeftById(cIdvId)).equals(left), message);
    	Assert.assertTrue(((Integer)getTopById(cIdvId)).equals(top), message);
    	Assert.assertTrue(getWidth(contentId).equals(width), message);
    	Assert.assertTrue(getHeight(contentId).equals(height), message);

    	
    	
    }
    

    private String getWidth(String id) {
    	String w = runScript("$('"+id+"').style.width", false);
    	return w;
    }
    
    private String getHeight(String id) {
    	String h = runScript("$('"+id+"').style.height", false);
    	return h;
    }

    private void clickReset() {
	String buttonId = getParentId() + FORM_ID + RESET_ID;
	writeStatus("Click reset button");
	clickCommandAndWait(buttonId);
    }
    
    private void clickSetAutosized() {
    	String buttonId = getParentId() + FORM_ID + CHANGE_AUTOSIZED_ID;
    	writeStatus("Click set autosized button");
    	clickCommandAndWait(buttonId);
        }

    private void clickChangeRendered() {
	String buttonId = getParentId() + FORM_ID + CHANGE_RENDERED_ID;
	writeStatus("Click change rendered button");
	clickCommandAndWait(buttonId);
    }

    private void clickChangeAttributes() {
	String buttonId = getParentId() + FORM_ID + CHANGE_ATTRIBUTES_ID;
	writeStatus("Click change attributes button");
	clickCommandAndWait(buttonId);
    }
    
    private void clickChangeVisualState() {
    	String buttonId = getParentId() + FORM_ID + CHANGE_VISUAL_STATE_ID;
    	writeStatus("Click change visual state button");
    	clickCommandAndWait(buttonId);
    }

    private void clickShow() {
	String buttonId = getParentId() + FORM_ID + SHOW_ID;
	writeStatus("Click show button");
	clickById(buttonId);
    }

    private void clickHide() {
	String buttonId = getParentId() + FORM2_ID + HIDE_ID;
	writeStatus("Click hide button");
	clickById(buttonId);
    }
    
    private void switchPanel(boolean show) {
    	String panelId = getParentId() + PANEL_ID;
    	if (show) {
    		invokeFromComponent(panelId, "show", null);
    	}else {
    		invokeFromComponent(panelId, "hide", null);
    	}
    	
    }
    
}
