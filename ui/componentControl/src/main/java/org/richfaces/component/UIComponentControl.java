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

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.component.AjaxSupport;
import org.ajax4jsf.component.EventValueExpression;
import org.ajax4jsf.component.JavaScriptParameter;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.util.HtmlUtil;

/**
 * JSF component class
 * 
 */
public abstract class UIComponentControl extends UIComponentBase 
            implements AjaxSupport {
    
    public static final String COMPONENT_TYPE = "org.richfaces.ComponentControl";
    
    public static final String COMPONENT_FAMILY = "org.richfaces.ComponentControl";
    
    private static final Log log = LogFactory.getLog(UIComponentControl.class);
    
    private boolean disableDefault = false;
    private boolean disableDefaultSet = false;
    
    /**
     * @return JavaScript eventString. Rebuild on every call, since can be in
     *         loop ( as in dataTable ) with different parameters.
     */
    public String getEventString() {
        String targetId = HtmlUtil.idsToIdSelector(getFor());
        
        targetId = HtmlUtil.expandIdSelector(targetId, this, FacesContext
                .getCurrentInstance());
        
        JSFunction invocation = new JSFunction("Richfaces.componentControl.performOperation");
        invocation.addParameter(new JSReference("event"));
        invocation.addParameter(targetId);
        invocation.addParameter(getOperation());
        invocation.addParameter(new JSReference("{" + getEncodedParametersMap() + "}"));
        invocation.addParameter(Boolean.valueOf(isDisableDefault()));
        
        return invocation.toScript();
    }
    
    public String getEncodedParametersMap() {
        StringBuilder result = new StringBuilder();
        
        boolean shouldClose = false;
        
        String params = this.getParams();
        if (params != null && params.trim().length() != 0) {
            result.append(params);
            shouldClose = true;
        }
        
        for (UIComponent child : this.getChildren()) {
            if (!(child instanceof UIParameter)) {
                continue;
            }
            
            String name = ((UIParameter) child).getName();
            Object value = ((UIParameter) child).getValue();
            if (null == name) {
                FacesContext context = FacesContext.getCurrentInstance();
                throw new IllegalArgumentException(Messages.getMessage(
                        Messages.UNNAMED_PARAMETER_ERROR, this
                                .getClientId(context)));
            }
            
            boolean escape = true;
            if (child instanceof JavaScriptParameter) {
                JavaScriptParameter actionParam = (JavaScriptParameter) child;
                escape = !actionParam.isNoEscape();
            }
            
            if (shouldClose) {
                result.append(", ");
            }
            
            ScriptUtils.addEncodedString(result, name);
            result.append(": ");
            result.append(ScriptUtils.toScript(escape ? value
                    : new JSReference(value.toString())));
            
            shouldClose = true;
        }
        
        return result.toString();
    }
    
    public abstract String getEvent();
    
    public abstract void setEvent(String event);
    
    public abstract String getFor();
    
    public abstract void setFor(String value);
    
    public abstract String getParams();
    
    public abstract void setParams(String value);
    
    public abstract String getOperation();
    
    public abstract void setOperation(String value);
    
    public abstract String getAttachTo();
    
    public abstract void setAttachTo(String value);
    
    public boolean isDisableDefault() {
    	if (this.disableDefaultSet) {
    	    return (this.disableDefault);
    	}
    	
    	ValueExpression ve = getValueExpression("disableDefault");
    	if (ve != null) {
    	    Boolean value = null;
    	    
    	    try {
    			value = (Boolean) ve.getValue(getFacesContext().getELContext());
    	    } catch (ELException e) {
    			throw new FacesException(e);
    	    }
    	    
    	    if (null != value) {
        	    return value.booleanValue();
    	    }
    	}

    	String event = getEvent();
	    return ("contextmenu".equalsIgnoreCase(event) || "oncontextmenu".equalsIgnoreCase(event));
    }

    public void setDisableDefault(boolean disableDefault) {
    	this.disableDefaultSet = true;
    	this.disableDefault = disableDefault;
    }
    
    protected String replaceClientIds(FacesContext context,
            UIComponent component, String selector) {
        return HtmlUtil.expandIdSelector(selector, component, context);
    }
    
    /**
     * After nornal setting <code>parent</code> property in case of created
     * component set Ajax properties for parent.
     * 
     * @see javax.faces.component.UIComponentBase#setParent(javax.faces.component.UIComponent)
     */
    public void setParent(UIComponent parent) {
        super.setParent(parent);
        if (parent != null && parent.getFamily() != null) {
            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage(Messages.CALLED_SET_PARENT,
                        parent.getClass().getName()));
            }
            
            // TODO If this comopnent configured, set properties for parent
            // component.
            // NEW created component have parent, restored view - null in My
            // faces.
            // and SUN RI not call at restore saved view.
            // In other case - set in restoreState method.
            // if (parent.getParent() != null)
            if (log.isDebugEnabled()) {
                log.debug(Messages
                        .getMessage(Messages.DETECT_NEW_COMPONENT));
            }
            setParentProperties(parent);
        }
    }
    
    public void setParentProperties(UIComponent parent) {
        String event = getEvent();
        if (event == null || event.length() == 0) {
            return;
        }
        
        String attachTo = getAttachTo();
        if (attachTo == null || attachTo.length() == 0) {
            parent.setValueExpression(event, new EventValueExpression(this));
        } else {
            ValueExpression vb = parent.getValueExpression(event);
            if (vb instanceof EventValueExpression) {

                // TODO check if that's EventValueBinding for us
                parent.setValueExpression(event, null);
            }
        }
    }
    
    public abstract void setName(String name);
    
    public abstract String getName();
    
    public abstract void setAttachTiming(String attachTiming);
    
    public abstract String getAttachTiming();
    
    @Override
    public Object saveState(FacesContext context) {
    	Object[] state = new Object[3];
    	state[0] = super.saveState(context);
    	state[1] = this.disableDefault ? Boolean.TRUE : Boolean.FALSE;
    	state[2] = this.disableDefaultSet ? Boolean.TRUE : Boolean.FALSE;

    	return state;
    }
    
    @Override
    public void restoreState(FacesContext context, Object stateObject) {
    	Object[] state = (Object[]) stateObject;
    	super.restoreState(context, state[0]);
    	this.disableDefault = ((Boolean) state[1]).booleanValue();
    	this.disableDefaultSet = ((Boolean) state[2]).booleanValue();
    }
}
