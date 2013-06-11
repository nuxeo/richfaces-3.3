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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.bean.DnDBean;
import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.SeleniumException;

public class DnDTest extends SeleniumTestBase {
	
	static final String AJAX_SINGLE_WITH_I_FALIDATION_TEST = "/pages/dnd/dndTestAjaxSingleWithInternalValidation.xhtml";
	static final String RESET_METHOD = "#{dndBean.reset}";
	
	static final String FORMID = "_form:";
	static final String DATAID = "src:";
	
	static final Map<String, String> parameters = new HashMap<String, String>();
	static {
		parameters.put("parameter1", "value1");
		parameters.put("parameter2", "value2");
	}
	
	String statusId;
	String dragValueId;
	String dropValueId;
	String phpDropZoneId;
	String dnetDropZoneId;
	String cfDropZoneId;
	String itemsHolderId;
	String resetButtonId;
	
	void initIds(String parentId) {
		statusId = parentId + FORMID + "status"; 
		dragValueId = parentId + FORMID + "dragValue";
		dropValueId = parentId + FORMID + "dropValue";
		phpDropZoneId =  parentId + FORMID + "PHP";
		dnetDropZoneId =  parentId + FORMID + "DNET";
		cfDropZoneId =  parentId + FORMID + "CF";
		itemsHolderId = parentId + FORMID + DATAID;
		resetButtonId = parentId + FORMID + DATAID + "reset";
	}
	
	@Override
	protected String getFirefoxTemplate() {
		return "c:/FFProfile";
	}
	
	
	
	@Test
	public void testExternalValidationFailure(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testExtrenalValidationFailure();
	}
	
	
	@Test
	public void testImmediateWithInternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testImmediateWithExternalValidationFailed();
	}
	
	@Test
	public void testImmediate(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testImmediate();
	}
	
	
	
	@Test
	public void testAjaxSingleWithInternalValidationFailed(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(AJAX_SINGLE_WITH_I_FALIDATION_TEST, template, RESET_METHOD);
		
		tester.testAjaxSingleWithInternalValidationFailed();
	}
	

		
	
	@Test
	public void testAjaxSingleTrue(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testAjaxSingle();
	}
	
	@Test
	public void testOncomplete(Template template) {
		AutoTester tester = getAutoTester(this);
		tester.renderPage(template, RESET_METHOD);
		
		tester.testOncomplete();
	}
	
	@Test 
	public void testRendered(Template template) {
		 renderPage(template, RESET_METHOD);
	     initIds(getParentId());	
	     
	     String controlsId = getParentId() + "_controls:testRendered";
	     clickCommandAndWait(controlsId);
	     
	     _DragAndDrop(0, phpDropZoneId);
	     AssertTextEquals(phpDropZoneId + "_body", "", "Drop zone text should be empty");
	     assertDropValue("");
	     
	     assertListeners();
	     assertEvents(Collections.EMPTY_LIST);
	}
	

   @Test
    public void testDragValue(Template template) {
        renderPage(template, RESET_METHOD);
        initIds(getParentId());	
        
        DragAndDrop(0, phpDropZoneId);
        assertDragValue("Flexible Ajax", phpDropZoneId);
        
        DragAndDrop(1, dnetDropZoneId);
        assertDragValue("AJAXEngine", dnetDropZoneId);
        
        DragAndDrop(0, cfDropZoneId);
        assertDragValue("ajaxCFC", cfDropZoneId);
    }
    
    
   @Test
    public void testDropValue(Template template) {
        renderPage(template, RESET_METHOD);
        initIds(getParentId());	
        
        DragAndDrop(0, phpDropZoneId);
        assertDropValue("PHP");
        testDropData();
        
        DragAndDrop(1, dnetDropZoneId);
        assertDropValue("DNET");
        testDropData();
        
        DragAndDrop(0, cfDropZoneId);
        assertDropValue("CF");
        testDropData();
    }
    
   @Test
    public void testListeners(Template template) {
    	renderPage(template, RESET_METHOD);
        initIds(getParentId());	
        
        DragAndDrop(0, phpDropZoneId);
        assertListeners(DnDBean.RICHDRAGLISTENER, DnDBean.DRAGLISTENER, DnDBean.DROPLISTENER);
        
        clickAjaxCommandAndWait(resetButtonId);
        
        DragAndDrop(1, cfDropZoneId);
        assertListeners(DnDBean.RICHDRAGLISTENER, DnDBean.DRAGLISTENER, DnDBean.RICHDROPLISTENER);

        
    }
   
    @Test
   	public void testEvents(Template template) {
   		renderPage(template, RESET_METHOD);
        initIds(getParentId());	
        
        DragAndDrop(0, phpDropZoneId);
        List<String> eventsExpected = new ArrayList<String>();
        
        eventsExpected.add("ondragstart");
        eventsExpected.add("ondragenter");
        eventsExpected.add("ondragend");                                  
        eventsExpected.add("ondrop");
        eventsExpected.add("ondropend");
        
        assertEvents(eventsExpected);
   	}
   	
   	@Test
   	public void testListenersIfTypeNotAccepted(Template template) {
   		renderPage(template, RESET_METHOD);
        initIds(getParentId());	
        
        boolean exception = false;
        
        try {
        	DragAndDrop(0, cfDropZoneId);
        }catch (SeleniumException e) {
        	exception = true;
		}
        
        if (!exception) {
        	Assert.fail("Drop on not accepted zone should not force ajax request");
        }
        
        assertListeners();
   	}
   	
   	@Test
   	public void testEventsIfTypeNotAccepted(Template template) {
   	 renderPage(template, RESET_METHOD);
     initIds(getParentId());	
        
   	 _DragAndDrop(0, cfDropZoneId);
     List<String> eventsExpected = new ArrayList<String>();
     
     eventsExpected.add("ondragstart");
     eventsExpected.add("ondragenter");
     eventsExpected.add("ondragend");                                  
     eventsExpected.add("ondropend");
     
     assertEvents(eventsExpected);
   	}
   	
   	@Test
   	public void testNestedParameters(Template template) {
   		AutoTester tester = getAutoTester(this);
   		tester.renderPage(template, RESET_METHOD);
   		
   		tester.testRequestParameters(parameters);
   	}
   	
   	@Test
   	public void testByPassUpdate(Template template) {
   		AutoTester tester = getAutoTester(this);
   		tester.renderPage(template, RESET_METHOD);
   		
   		tester.testBypassUpdate();
   	}
   	
	@Test
   	public void testReRender(Template template) {
   		AutoTester tester = getAutoTester(this);
   		tester.renderPage(template, RESET_METHOD);
   		
   		tester.testReRender();
   	}
	
	@Test
   	public void testLimit2List(Template template) {
   		AutoTester tester = getAutoTester(this);
   		tester.renderPage(template, RESET_METHOD);
   		
   		tester.testLimitToList();
   	}
    
    void DragAndDrop(int itemNumber, String dropZoneId) {
    	_DragAndDrop(itemNumber, dropZoneId);
    	waitForAjaxCompletion();
    }
    
    void _DragAndDrop(int itemNumber, String dropZoneId) {
    	selenium.dragAndDropToObject("id=" + itemsHolderId + itemNumber + ":item", "id=" + dropZoneId + "_body");
    }
     
    void testDropData () {
    	String dragData = runScript("window._dropData");
    	if (dragData == null || !dragData.equals("dropData")) {
    		Assert.fail("Data attribute for dropSupport does not work.");
    	}
    	runScript("window._dropData = null;");
    }
   
    void assertDragValue(String dragLabel, String dropZoneId) {
    	String text = getTextById(dragValueId);
    	if (text == null || text.indexOf("org.ajax4jsf.bean.DnDBean$Framework") == -1) {
    		Assert.fail("Drag value ["+text+"] is incorrect. It's not refferenced to org.ajax4jsf.bean.DnDBean$Framework class instance");
    	}
    	String dropZoneText = getTextById(dropZoneId);
    	if (dropZoneText == null || dropZoneText.indexOf(dragLabel) == -1) {
    		Assert.fail("Drag label is incorrect. Drop zone text ["+dropZoneText+"] does not contain label of dragged element");
    	}
    }
    
    void assertDropValue(String expectedValue) {
    	AssertTextEquals(dropValueId, expectedValue, "Drop Value is incorrect");
    }
    
    void assertListeners(String ... listener ) {
    	String status  = getStatusValue();
    	String s = status;
    	String sum = "";
    	for (String l : listener) {
    		if (status.indexOf(l) == -1) {
    			Assert.fail(l + " has been skipped");
    		}else {
    			s = s.replace(l, "");
    		}
    		sum += l;
    	}
    	if (s.length() >  0) {
    		Assert.fail("The following listener were called but shouldn't: " + s);
    	}
    	if (!status.equals(sum)) {
    		Assert.fail("Order of listeners call is incorrect. Should be: " + sum + ". But was : " + status);
    	}
    }
    
    String getStatusValue() {
    	return getTextById(statusId);
    }

    @Override
    public String getTestUrl() {
        return "pages/dnd/dndTest.xhtml";
    }
    
    @Override
    public String getAutoTestUrl() {
       	return "pages/dnd/dndAutoTest.xhtml";
    }
    
    @Override
    public void sendAjax() {
    	String dragId = getParentId() + "autoTestForm:drag";
    	String dropId = getParentId() + "autoTestForm:PHP";
    	selenium.dragAndDropToObject(dragId, dropId);
    	waitForAjaxCompletion();
    }
}