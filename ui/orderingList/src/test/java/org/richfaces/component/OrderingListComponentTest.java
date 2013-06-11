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
import java.util.List;

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
import org.richfaces.component.html.HtmlOrderingList;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Siarhej Chalipau
 *
 */
public class OrderingListComponentTest extends AbstractAjax4JsfTestCase {
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OrderingListComponentTest( String testName )
    {
        super( testName );
    }
    
    private UIForm form = null;
    private UIOrderingList orderingList = null;
    private List valueList = null;
    private UICommand command = null;
    private UIOrderingList orderingList2 = null;

    public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent("org.richfaces.OrderingList", "org.richfaces.component.html.HtmlOrderingList");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        command = new HtmlCommandLink();
        command.setId("command");
        form.getChildren().add(command);
        
        valueList = new ArrayList();
        valueList.add("1");
        valueList.add("2");
    	
    	orderingList = (UIOrderingList)application.createComponent("org.richfaces.OrderingList");
    	orderingList.setControlsType("link");
    	orderingList.setVar("item");
    	orderingList.setValueBinding("value", new ValueBinding() {

			public Class getType(FacesContext arg0) throws EvaluationException,
					PropertyNotFoundException {
				return List.class;
			}

			public Object getValue(FacesContext arg0)
					throws EvaluationException, PropertyNotFoundException {
				return valueList;
			}

			public boolean isReadOnly(FacesContext arg0)
					throws EvaluationException, PropertyNotFoundException {
				return false;
			}

			public void setValue(FacesContext arg0, Object arg1)
					throws EvaluationException, PropertyNotFoundException {
				assertTrue(arg1 instanceof List);
				valueList = (List)arg1;
			}
    		
    	});
    	
    	form.getChildren().add(orderingList);
    	
    	orderingList2 = (UIOrderingList)application.createComponent("org.richfaces.OrderingList");
    }
    
    public void tearDown() throws Exception {
    	valueList = null;
    	orderingList = null;
    	orderingList2 = null;
    	command = null;
    	form = null;
    	
    	super.tearDown();
    }
    
    /**
     * Tests if component accepts request parameters and stores them in submittedValue().
     * If component is immediate, validation (possibly with conversion) should occur on that phase.
     *
     * @throws Exception
     */
    public void testDecode() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        
        HtmlAnchor anchor = (HtmlAnchor)page.getDocumentElement().getHtmlElementById(command.getClientId(facesContext));
        anchor.click();
        externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "0:1");
        orderingList.processDecodes(facesContext);
        Object submittedValue = orderingList.getSubmittedValue();
        assertNotNull(submittedValue);
        assertTrue(submittedValue instanceof UIOrderingList.SubmittedValue);
        UIOrderingList.SubmittedValue sValue = (UIOrderingList.SubmittedValue) submittedValue;
        assertFalse(sValue.isNull());
        
        orderingList.setImmediate(true);
        orderingList.addValidator(new Validator() {

			public void validate(FacesContext arg0, UIComponent arg1,
					Object arg2) throws ValidatorException {
				FacesMessage mess = new FacesMessage("Fake test message.");
				throw new ValidatorException(mess);
				
			}
        	
        });
        
        page = renderView();
        anchor = (HtmlAnchor)page.getDocumentElement().getHtmlElementById(command.getClientId(facesContext));
        anchor.click();
        externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "0:1");
        orderingList.processDecodes(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
    }
    
    /**
     * Tests if component handles value bindings correctly
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
    	 externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "sa1:2");
         externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "0:1");
         orderingList.processDecodes(facesContext);
         orderingList.processValidators(facesContext);
         orderingList.processUpdates(facesContext);
         
         assertNotNull(valueList);
         assertEquals(2, valueList.size());
         assertEquals("2", valueList.get(0));
         assertEquals("1", valueList.get(1));
    }
    
    /**
     * Tests if component handles validation correctly
     *
     * @throws Exception
     */
    public void testValidate() throws Exception {
    	orderingList.addValidator(new Validator() {

			public void validate(FacesContext arg0, UIComponent arg1,
					Object arg2) throws ValidatorException {
				FacesMessage mess = new FacesMessage("Fake test message.");
				throw new ValidatorException(mess);
				
			}
        	
        });
    	
    	externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "sa1:2");
        externalContext.addRequestParameterMap(orderingList.getClientId(facesContext), "0:1");
        orderingList.processDecodes(facesContext);
        orderingList.processValidators(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
        
        Object submittedValue = orderingList.getSubmittedValue();
        assertNotNull(submittedValue);
        assertTrue(submittedValue instanceof UIOrderingList.SubmittedValue);
        assertFalse(((UIOrderingList.SubmittedValue)submittedValue).isNull());
    }
    
    public void testSaveRestore() throws Exception {
    	List value = new ArrayList();
    	value.add("1");
    	orderingList.setValueBinding("value", null);
    	orderingList.setValue(value);
    	assertEquals(value, orderingList.getValue());
    	assertNull(orderingList.getValueBinding("value"));
    	
    	Object state = orderingList.saveState(facesContext);
    	
    	orderingList2.restoreState(facesContext, state);
    	Object value2 = orderingList2.getValue();
    	assertNotNull(value2);
    	assertEquals(value, value2);
    }
    
}
