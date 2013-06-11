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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Unit test for simple Component.
 */
public class ColorPickerTest extends AbstractAjax4JsfTestCase {
    private UIForm form;
    private UIColorPicker colorPicker;
    private UIColorPicker colorPicker2;
    
    private ColorPickerBean targetBean = null;
    
    /**
     * Create the test case
     * 
     * @param testName - name of the test case
     */
    public ColorPickerTest(String testName) {
        super(testName);
    }
    
    public void setUp() throws Exception {
        super.setUp();
        
        application.addComponent(UIColorPicker.COMPONENT_TYPE,
                "org.richfaces.component.html.HtmlColorPicker");
        
        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        colorPicker = (UIColorPicker) application.createComponent(UIColorPicker.COMPONENT_TYPE);
        form.getChildren().add(colorPicker);

        targetBean = new ColorPickerBean();
        externalContext.getRequestMap().put("targetBean", targetBean);
        colorPicker.setValueBinding("value", application.createValueBinding("#{targetBean.value}"));
    
        colorPicker2 = (UIColorPicker) application.createComponent(UIColorPicker.COMPONENT_TYPE);
    }
    
    public void tearDown() throws Exception {
        form = null;
        colorPicker = null;
        colorPicker2 = null;

        targetBean = null;
        
        super.tearDown();
    }
    
    /**
     * Tests if component accepts request parameters and stores them in
     * submittedValue(). If component is immediate, validation (possibly with
     * conversion) should occur on that phase.
     * 
     * @throws Exception
     */
    public void testDecode() throws Exception {
        final String value = "value";
        
        externalContext.addRequestParameterMap(colorPicker.getClientId(facesContext), value);
        colorPicker.processDecodes(facesContext);
        
        Object submittedValue = colorPicker.getSubmittedValue();
        assertEquals(value, submittedValue);
    }
    
    public void testDecodeImmediate() throws Exception {
         colorPicker.setImmediate(true);
         colorPicker.addValidator(new Validator() {

            public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
                FacesMessage mess = new FacesMessage("Fake test message.");
                throw new ValidatorException(mess);
                
            }
         });
        
        externalContext.addRequestParameterMap(colorPicker.getClientId(facesContext), "value");
        colorPicker.processDecodes(facesContext);
        
        assertTrue(facesContext.getMessages().hasNext());
    }
    
    /**
     * Tests if component handles value bindings correctly
     * 
     * @throws Exception
     */
    public void testUpdate() throws Exception {
        externalContext.addRequestParameterMap(colorPicker.getClientId(facesContext), "value");

        colorPicker.processDecodes(facesContext);
        colorPicker.processValidators(facesContext);
        colorPicker.processUpdates(facesContext);
        
        assertEquals("value", targetBean.getValue());
    }
    
    /**
     * Tests if component handles validation correctly
     * 
     * @throws Exception
     */
    public void testValidate() throws Exception {
        colorPicker.addValidator(new Validator() {
            
            public void validate(FacesContext arg0, UIComponent arg1,
                    Object arg2) throws ValidatorException {
                FacesMessage mess = new FacesMessage("Fake test message.");
                throw new ValidatorException(mess);
                
            }
            
        });
        externalContext.addRequestParameterMap(colorPicker.getClientId(facesContext), "value");

        colorPicker.processDecodes(facesContext);
        colorPicker.processValidators(facesContext);

        assertTrue(facesContext.getMessages().hasNext());
        assertNotNull(colorPicker.getSubmittedValue());
    }
    
    public void testSaveRestore() throws Exception {
        final String value = "value";

        colorPicker.setValueBinding("value", null);
        assertNull(colorPicker.getValueBinding("value"));

        colorPicker.setValue(value);
        assertEquals(value, colorPicker.getValue());
        
        Object state = colorPicker.saveState(facesContext);
        
        colorPicker2.restoreState(facesContext, state);
        assertEquals(value, colorPicker2.getValue());
    }
    
    protected class ColorPickerBean {
        private String value = null;
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
    }
}
