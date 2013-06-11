/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIListShuttle.SubmittedValue;
import org.richfaces.model.ListShuttleRowKey;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class ListShuttleComponentTest extends AbstractAjax4JsfTestCase {
	private UIForm form = null;
	private UIListShuttle listShuttle = null;
	private UIListShuttle listShuttle2 = null;
	private ListShuttleBean sourceBean = null;
	private ListShuttleBean targetBean = null;
	private UICommand command = null;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ListShuttleComponentTest( String testName ) {
        super( testName );
    }
    
    public void setUp() throws Exception {
    	super.setUp();
    	
        application.addComponent(UIListShuttle.COMPONENT_TYPE, "org.richfaces.component.html.HtmlListShuttle");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        command = new HtmlCommandLink();
        command.setId("command");
        command.setValue("test command");
        form.getChildren().add(command);
        
        sourceBean = new ListShuttleBean(false);
        targetBean = new ListShuttleBean();
        
        externalContext.getRequestMap().put("sourceBean", sourceBean);
        externalContext.getRequestMap().put("targetBean", targetBean);
        
        listShuttle = (UIListShuttle)application.createComponent(UIListShuttle.COMPONENT_TYPE);
        listShuttle.setId("listShuttle");
        listShuttle.setVar("item");
        listShuttle.setValueBinding("sourceValue", application.createValueBinding("#{sourceBean.value}"));
        listShuttle.setValueBinding("targetValue", application.createValueBinding("#{targetBean.value}"));
        
        form.getChildren().add(listShuttle);

        listShuttle2 = (UIListShuttle)application.createComponent(UIListShuttle.COMPONENT_TYPE);
    }
    
    public void tearDown() throws Exception {
    	form = null;
    	listShuttle = null;
    	listShuttle2 = null;
    	sourceBean = null;
    	targetBean = null;
    	command = null;
    	
    	super.tearDown();
    }


    /**
     * Tests if component accepts request parameters and stores them in submittedValue().
     * If component is immediate, validation (possibly with conversion) should occur on that phase.
     *
     * @throws Exception
     */
    public void testDecode() throws Exception {
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "0:1");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), ":");
        listShuttle.processDecodes(facesContext);
        Object submittedValue = listShuttle.getSubmittedValue();
        assertNotNull(submittedValue);
        
        assertTrue(submittedValue instanceof UIListShuttle.SubmittedValue);
        assertFalse(((UIListShuttle.SubmittedValue)submittedValue).isNull());
    }
    
    public void testDecodeImmediate() throws Exception {
    	 listShuttle.setImmediate(true);
         listShuttle.addValidator(new Validator() {

 			public void validate(FacesContext arg0, UIComponent arg1,
 					Object arg2) throws ValidatorException {
 				FacesMessage mess = new FacesMessage("Fake test message.");
 				throw new ValidatorException(mess);
 				
 			}
         });
    	
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), ":");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "0:1");
        listShuttle.processDecodes(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
    }
    
    /**
     * Tests if component handles value bindings correctly
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
         externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "sa1:2");
         externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), ":");
         externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "0:1");
         listShuttle.processDecodes(facesContext);
         listShuttle.processValidators(facesContext);
         listShuttle.processUpdates(facesContext);

         assertNotNull(targetBean.getValue());
         assertEquals(1, targetBean.getValue().size());
         assertEquals("1", targetBean.getValue().get(0));
    }
    
    /**
     * Tests if component handles validation correctly
     *
     * @throws Exception
     */
    public void testValidate() throws Exception {
    	listShuttle.addValidator(new Validator() {

			public void validate(FacesContext arg0, UIComponent arg1,
					Object arg2) throws ValidatorException {
				FacesMessage mess = new FacesMessage("Fake test message.");
				throw new ValidatorException(mess);
				
			}
        	
        });
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), ":");
        externalContext.addRequestParameterMap(listShuttle.getClientId(facesContext), "0:1");
        listShuttle.processDecodes(facesContext);
        listShuttle.processValidators(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
        
        Object submittedValue = listShuttle.getSubmittedValue();
        assertNotNull(submittedValue);
        assertTrue(submittedValue instanceof UIListShuttle.SubmittedValue);
        assertFalse(((UIListShuttle.SubmittedValue)submittedValue).isNull());
    }
    
    public void testSaveRestore() throws Exception {
    	List value = new ArrayList();
    	value.add("1");
    	listShuttle.setValueBinding("sourceValue", null);
    	listShuttle.setSourceValue(value);
    	assertEquals(value, listShuttle.getSourceValue());
    	assertNull(listShuttle.getValueBinding("value"));
    	
    	Object state = listShuttle.saveState(facesContext);
    	
    	listShuttle2.restoreState(facesContext, state);
    	Object value2 = listShuttle2.getSourceValue();
    	assertNotNull(value2);
    	assertEquals(value, value2);
    }
    
    public void testSourceTargetDecode() throws Exception {
		String shuttleId = listShuttle.getClientId(facesContext);
    	
    	externalContext.addRequestParameterMap(shuttleId, "0:a");
    	externalContext.addRequestParameterMap(shuttleId, "t5:b");
    	externalContext.addRequestParameterMap(shuttleId, ":");
    	externalContext.addRequestParameterMap(shuttleId, "t1:c");
    	externalContext.addRequestParameterMap(shuttleId, "2:d");
    	externalContext.addRequestParameterMap(shuttleId, "t10:e");

    	listShuttle.decode(facesContext);
    	
    	SubmittedValue value = (SubmittedValue) listShuttle.getSubmittedValue();
    	assertNotNull(value);
    	
    	Map map = value.getMap();
    	
    	Iterator<Map.Entry<ListShuttleRowKey, Object>> itr = map.entrySet().iterator();
    	Entry<ListShuttleRowKey, Object> entry;
    	ListShuttleRowKey rowKey;
    	
    	entry = itr.next();
    	rowKey = entry.getKey();
    	assertEquals("a", entry.getValue());
    	assertTrue(rowKey.isFacadeSource());
    	assertTrue(rowKey.isSource());
    	assertEquals(Integer.valueOf(0), rowKey.getRowKey());
    	
    	entry = itr.next();
    	rowKey = entry.getKey();
    	assertEquals("b", entry.getValue());
    	assertTrue(rowKey.isFacadeSource());
    	assertFalse(rowKey.isSource());
    	assertEquals(Integer.valueOf(5), rowKey.getRowKey());

    	entry = itr.next();
    	rowKey = entry.getKey();
    	assertEquals("c", entry.getValue());
    	assertFalse(rowKey.isFacadeSource());
    	assertFalse(rowKey.isSource());
    	assertEquals(Integer.valueOf(1), rowKey.getRowKey());
    	
    	entry = itr.next();
    	rowKey = entry.getKey();
    	assertEquals("d", entry.getValue());
    	assertFalse(rowKey.isFacadeSource());
    	assertTrue(rowKey.isSource());
    	assertEquals(Integer.valueOf(2), rowKey.getRowKey());
    	
    	entry = itr.next();
    	rowKey = entry.getKey();
    	assertEquals("e", entry.getValue());
    	assertFalse(rowKey.isFacadeSource());
    	assertFalse(rowKey.isSource());
    	assertEquals(Integer.valueOf(10), rowKey.getRowKey());
    }

    protected class ListShuttleBean {
    	private List value = null;
    	
    	public ListShuttleBean() {
    		this(true);
    	}
    	
    	public ListShuttleBean(boolean empty) {
			value = new ArrayList();
			if (!empty) {
				value.add("1");
				value.add("2");
			}
		}

		public List getValue() {
			return value;
		}

		public void setValue(List value) {
			this.value = value;
		}	
    }
}
