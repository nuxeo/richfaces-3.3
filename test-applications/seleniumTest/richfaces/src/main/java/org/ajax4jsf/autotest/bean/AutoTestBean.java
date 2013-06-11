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
package org.ajax4jsf.autotest.bean;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.ajax4jsf.javascript.ScriptUtils;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;

/**
 * Bean for auto test
 * 
 * @author Andrey Markavtsov
 * 
 */
public class AutoTestBean {

    public static final String INPUT_TEXT = "Text";

    public static final String STATUS_ID = "_auto_status";
    
    public static final String UPDATE_MODEL_STATUS = "UpdateModel";
    
    public static final String PROCESS_INPUT_UPDATE_MODEL = "ProcessInputUpdateModel";
    
    public static final String PROCESS_INPUT_CHANGE_LISTENER = "ProcessInputChangeListener";

    public static final String ACTION_LISTENER_STATUS = "ActionListener";
    
    public static final String ACTION_STATUS = "Action1";

    public static final String NESTED_ACTION_LISTENER_STATUS = "NestedListener";

    public static final String VALUE_CHANGE_LISTENER_STATUS = "ValueChangeListener";

    public static final String NODE_SELECTED_LISTENER_STATUS = "NodeSelectedListener";

    public static final String NODE_EXPANDED_LISTENER_STATUS = "NodeExpandedListener";
    
    public static final String CONVERTER_ID = "autoTestConverter";

    public static final String VALIDATOR_ID = "autoTestValidator";

    public static final String VALIDATOR_DEFAULT_ID = "autoTestDefaultValidator";

    public static final String ONCOMPLETE = "window._ajaxOncomplete = true;";
    
    public static final String VALIDATOR_MESSAGE = "Validator message";

    public static final String REQUIRED_MESSAGE = "Required message";

    public static final String AUTOTEST_BEAN_NAME = "autoTestBean";
    
    public static final String NAVIGATION_ACTION = "autoTestNavigation";

    // private String input = INPUT_TEXT;

    private String reRender = STATUS_ID;

    private Boolean limitToList = false;

    private Boolean ajaxSingle = false;

    private Boolean immediate = false;

    private Boolean bypassUpdate = false;

    private Boolean rendered = true;

    private String status = null;

    private String oncomplete = ONCOMPLETE;

    private String validatorId = VALIDATOR_DEFAULT_ID;
    
    private Object value;
   
    private boolean converterSet;
    
//    private String processInput;
    
    private boolean processSet = false;
    
    private boolean processExternalValidationSet = false;

    private boolean required;

    private boolean disabled;

    public class AutoTestConverter implements Converter {
    	
    	public static final String AS_OBJECT_STRING = "AUTO_TEST_CONVERTER_AS_OBJECT";

    	/* (non-Javadoc)
    	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
    	 */
    	public Object getAsObject(FacesContext context, UIComponent component,
    			String v) {
    		 value = AS_OBJECT_STRING;
    		 return v; 
    	}

    	/* (non-Javadoc)
    	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
    	 */
    	public String getAsString(FacesContext context, UIComponent component,
    			Object value) {
    		return null;
    	}

    };
    
    AutoTestConverter converter = new AutoTestConverter();

   
    /**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public void actionListener(ActionEvent event) {
        setStatus(getStatus() + ACTION_LISTENER_STATUS);
    }
    
    public void actionListener(DropEvent event) {
        setStatus(getStatus() + ACTION_LISTENER_STATUS);
    }
    
    public String action () {
    	setStatus(getStatus() + ACTION_STATUS);
    	return NAVIGATION_ACTION;
    }
    
    public void processInputChangeListener(ValueChangeEvent event) {
    	if (processSet) {
            setStatus(getStatus() + PROCESS_INPUT_CHANGE_LISTENER);
        }
    }
  
    public void valueChangeListener(ValueChangeEvent event) {
        setStatus(getStatus() + VALUE_CHANGE_LISTENER_STATUS);
    }

    public void processSelection(NodeSelectedEvent event) {
        setStatus(getStatus() + NODE_SELECTED_LISTENER_STATUS);
    }

    public void processExpansion(NodeExpandedEvent nodeExpandedEvent) {
        setStatus(getStatus() + NODE_EXPANDED_LISTENER_STATUS);
    }

    /**
     * Puts a message into the status that can be checked by <code>AutoTester#checkMessage</code> method hereafter
     * @param msg message
     */
    public void addMessage(String msg) {
        setStatus(getStatus() + msg);
    }

    public void validate(FacesContext context, UIComponent component, Object o) {
    	if (VALIDATOR_ID.equals(this.validatorId)) {
    		AutoTestValidator validator = new AutoTestValidator();
    		validator.validate(context, component, o);
    	}
    }

    public String load() {
        status = null;
        return null;
    }

    public String reset() {
        status = null;
        value = null;
        return null;
    }

    // public String testRendered() {
    // reset();
    // rendered = false;
    // return null;
    // }
    //	
    // public String testReRender() {
    // reset();
    // reRender = TIME_ID + "," + STATUS_ID ;
    // return null;
    // }
    //	
    // public String testListeners() {
    // reset();
    // return null;
    // }
    //	
    // public String testFalidation() {
    // reset();
    // return null;
    // }
    //	
    // public String testAjaxSingle() {
    // reset();
    // ajaxSingle = true;
    // return null;
    // }
    //	
    // public String testImmdediate() {
    // reset();
    // immediate = true;
    // return null;
    // }
    //	
    // public String testBypassUpdate() {
    // reset();
    // bypassUpdate = true;
    // return null;
    // }
    //	
    // public String testLimitToList1() {
    // reset();
    // limitToList = true;
    // return null;
    // }
    //	
    // public String testLimitToList2() {
    // reset();
    // limitToList = true;
    // reRender = TIME_ID + "," + STATUS_ID;
    // return null;
    // }
    //	
    // public String testValidator() {
    // reset();
    // validatorId = VALIDATOR_ID;
    // return null;
    // }
    //	
    // public String testValidatorAndAjaxSingle() {
    // return null;
    // }

    public Object getRequestParamsMap() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            ExternalContext ext = context.getExternalContext();
            if (ext != null) {
                return ScriptUtils.toScript(context.getExternalContext().getRequestParameterMap());
            }
        }
        return "";
    }

    public String getText() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * @return the input
     */
    public String getInput() {
        return INPUT_TEXT;
    }

    /**
     * @param input
     *                the input to set
     */
    public void setInput(String input) {
        if (input != null && input.equals(INPUT_TEXT)) {
            setStatus(getStatus() + UPDATE_MODEL_STATUS);
        }
        // this.input = input;
    }

    /**
     * @return the reRender
     */
    public String getReRender() {
        return reRender;
    }

    /**
     * @param reRender
     *                the reRender to set
     */
    public void setReRender(String reRender) {
        this.reRender = reRender;
    }

    /**
     * @return the limitToList
     */
    public Boolean getLimitToList() {
        return limitToList;
    }

    /**
     * @param limitToList
     *                the limitToList to set
     */
    public void setLimitToList(Boolean limitToList) {
        this.limitToList = limitToList;
    }

    /**
     * @return the ajaxSingle
     */
    public Boolean getAjaxSingle() {
        return ajaxSingle;
    }

    /**
     * @param ajaxSingle
     *                the ajaxSingle to set
     */
    public void setAjaxSingle(Boolean ajaxSingle) {
        this.ajaxSingle = ajaxSingle;
    }

    /**
     * @return the immediate
     */
    public Boolean getImmediate() {
        return immediate;
    }

    /**
     * @param immediate
     *                the immediate to set
     */
    public void setImmediate(Boolean immediate) {
        this.immediate = immediate;
    }

    /**
     * @return the bypassUpdate
     */
    public Boolean getBypassUpdate() {
        return bypassUpdate;
    }

    /**
     * @param bypassUpdate
     *                the bypassUpdate to set
     */
    public void setBypassUpdate(Boolean bypassUpdate) {
        this.bypassUpdate = bypassUpdate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        if (status == null) {
            status = "";
        }
        return status;
    }

    /**
     * @param status
     *                the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the rendered
     */
    public Boolean getRendered() {
        return rendered;
    }

    /**
     * @param rendered the rendered to set
     */
    public void setRendered(Boolean rendered) {
        this.rendered = rendered;
    }

    /**
     * @return the oncomplete
     */
    public String getOncomplete() {
        return oncomplete;
    }

    /**
     * @param oncomplete the oncomplete to set
     */
    public void setOncomplete(String oncomplete) {
        this.oncomplete = oncomplete;
    }

    /**
     * @return the validatorId
     */
    public String getValidatorId() {
        return validatorId;
    }

    /**
     * @param validatorId the validatorId to set
     */
    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

    /**
     * @return the requiredMessage
     */
    public String getRequiredMessage() {
        return REQUIRED_MESSAGE;
    }

    /**
     * @return the validatorMessage
     */
    public String getValidatorMessage() {
        return VALIDATOR_MESSAGE;
    }

    /**
     * @return the converter
     */
    public Converter getConverter() {
        if (converterSet) {
            return converter;
        }
        return null;
    }

    /**
     * @return the converterSet
     */
    public boolean isConverterSet() {
        return converterSet;
    }

    /**
     * @param converterSet
     *                the converterSet to set
     */
    public void setConverterSet(boolean converterSet) {
        this.converterSet = converterSet;
    }

    /**
     * @return the processSet
     */
    public boolean isProcessSet() {
        return processSet;
    }

    /**
     * @param processSet
     *                the processSet to set
     */
    public void setProcessSet(boolean processSet) {
        this.processSet = processSet;
    }

    /**
     * @return the processInput
     */
    public String getProcessInput() {
        return null;
    }

    /**
     * @param processInput
     *                the processInput to set
     */
    public void setProcessInput(String processInput) {
        if (processSet) {
            setStatus(getStatus() + PROCESS_INPUT_UPDATE_MODEL);
        }
    }

    public String getProcess() {
        return (processSet) ? "_auto_process_input" : (processExternalValidationSet) ?  "_auto_input" : null;
    }

    /**
     * Gets value of required field.
     * @return value of required field
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Set a new value for required field.
     * @param required a new value for required field
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Gets value of disabled field.
     * @return value of disabled field
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Set a new value for disabled field.
     * @param disabled a new value for disabled field
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isProcessExternalValidationSet() {
        return processExternalValidationSet;
    }

    public void setProcessExternalValidationSet(boolean processExternalValidationSet) {
        this.processExternalValidationSet = processExternalValidationSet;
    }
    
    

}
