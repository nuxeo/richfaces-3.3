/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.richfaces.event.SwitchablePanelSwitchEvent;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 23.01.2007
 * 
 */
public abstract class UISwitchablePanel extends UIInput {

	/**
	 * value for tab change method for - client-side tabs.
	 */
	public static final String CLIENT_METHOD = "client";
	/**
	 * value for tab change method - server-side tabs
	 */
	public static final String SERVER_METHOD = "server";
	/**
	 * value for tab change method - ajax tabs
	 */
	public static final String AJAX_METHOD = "ajax";

	/**
	 * default tab change method - server.
	 */
	public static final String DEFAULT_METHOD = SERVER_METHOD;

	private String switchType;
	
	public String getSwitchType() {
		if (this.switchType != null) {
			return switchType;
		}

		ValueBinding switchTypeBinding = getValueBinding("switchType");
		if (switchTypeBinding != null) {
			return (String) switchTypeBinding.getValue(FacesContext.getCurrentInstance());
		}
		
		return DEFAULT_METHOD;
	}
	
	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public Object convertSwitchValue(UIComponent component, Object object) {
		return object;
	}
	
	public void queueEvent(FacesEvent event) {
		
		if (event instanceof SwitchablePanelSwitchEvent && this.equals(event.getComponent())) {
			if (isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
			}
		}

		super.queueEvent(event);
	}
	
	public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
		
		if(facesEvent instanceof ActionEvent){
			//TODO invoke action listener or remove it
		    	if (isImmediate()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.renderResponse();
			}
		} //TODO else here
		
		if (facesEvent instanceof SwitchablePanelSwitchEvent) {
			if (isRendered()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				
				SwitchablePanelSwitchEvent switchEvent = (SwitchablePanelSwitchEvent) facesEvent;
				Object newValue = convertSwitchValue(switchEvent.getEventSource(),
						switchEvent.getValue());
				
				Object oldValue = getValue();
				if ((oldValue == null && newValue != null) ||
					(oldValue != null && !oldValue.equals(newValue))) {
	
					queueEvent(new ValueChangeEvent(this, oldValue, newValue));
				}
				
				//TODO UIInput should update the model, not the switchable panel itself
				ValueExpression valueBinding = getValueExpression("value");
				if (valueBinding != null) {
					valueBinding.setValue(facesContext.getELContext(), newValue);
					setValue(null);
					setLocalValueSet(false);
				} else {
					setValue(newValue);
				}
				
				if (AjaxRendererUtils.isAjaxRequest(facesContext) 
				        && this.getSwitchType().equals(AJAX_METHOD)) {
					
				    AjaxRendererUtils.addRegionByName(facesContext, this, this.getId());
					
				}
			}
		} else /* component should throw IllegalArgumentException for unknown events - RF-30 */ {
			super.broadcast(facesEvent);
		}
	}
	
	public void updateModel(FacesContext context) {
		//no processing here
	}

	protected Iterator<UIComponent> getSwitchedFacetsAndChildren() {
		return getFacetsAndChildren();
	}
	
	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("FacesContext is null!");
		}

		if (!isRendered()) {
			return ;
		}
		
        // Process all facets and children of this component
        Iterator<UIComponent> kids = getSwitchedFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = kids.next();
            kid.processDecodes(context);
        }

        try {
            decode(context);
    
            if (isImmediate()) {
            	validate(context);
            }
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
	}

	public void processUpdates(FacesContext context) {
	    if (context == null) {
            throw new NullPointerException("FacesContext is null!");
        }
        
        if (!isRendered()) {
            return;
        }
        
        Iterator<UIComponent> kids = getSwitchedFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = kids.next();
            kid.processUpdates(context);
        }
        
        try {
            updateModel(context);
            
            if (!isValid()) {
                context.renderResponse();
            }
            
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
	}

	public void processValidators(FacesContext context) {
		if (context == null) {
			throw new NullPointerException("FacesContext is null!");
		}

		if (!isRendered()) {
			return ;
		}

        Iterator<UIComponent> kids = getSwitchedFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.processValidators(context);
        }

        try {
            if (!isImmediate()) {
                validate(context);

                if (!isValid()) {
                    context.renderResponse();
                }
            }
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
	}
	
	public Object saveState(FacesContext context) {
		Object[] states = new Object[2];
		states[0] = super.saveState(context);
		states[1] = switchType;

		return states;
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		
		this.switchType = (String) states[1];
	}
}
