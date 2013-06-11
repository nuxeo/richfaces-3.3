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

import javax.faces.component.ActionSource2;
import javax.faces.context.FacesContext;
import javax.faces.convert.BooleanConverter;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxInputComponent;

public abstract class UIPanelMenuGroup extends AjaxInputComponent implements ActionSource2{
	
	public static final String COMPONENT_TYPE = "org.richfaces.panelMenuGroup";
	
	public abstract String getStyleClass();
	public abstract void setStyleClass(String styleClass);

	public abstract String getExpandMode();
	public abstract void setExpandMode(String exapandMode);
	public abstract String getIconExpanded();
	public abstract void setIconExpanded(String expanded);
	
	public abstract String getIconCollapsed();
	public abstract void setIconCollapsed(String iconCollapsed);
	public abstract String getIconDisabled();
	public abstract void setIconDisabled(String iconDisabled);
	public abstract boolean isDisabled();
	public abstract void setDisabled(boolean disabled);
	public abstract String getTarget();
	public abstract void setTarget(String target);
	public abstract String getHoverClass();
	public abstract void setHoverClass(String hoverClass);
	public abstract String getHoverStyle();
	public abstract void setHoverStyle(String hoverStyle);
	public abstract String getDisabledClass();
	public abstract void setDisabledClass(String disabledClass);
	public abstract String getDisabledStyle();
	public abstract void setDisabledStyle(String disabledStyle);
	public abstract String getStyle();
	public abstract void setStyle(String style);
	public abstract String getIconClass();
	public abstract void setIconClass(String iconClass);
	public abstract String getIconStyle();
	public abstract void setIconStyle(String iconStyle);
	public abstract String getOncollapse();
	public abstract void setOncollapse(String ongroupcollapse);
	public abstract String getOnexpand();
	public abstract void setOnexpand(String ongroupexpand);
	public abstract String getLabel();
	public abstract void setLabel(String label);
	public abstract void setName(String string);
	public abstract String getName();
	
	public boolean isExpanded(){
		
		Object value = getValue();
		if(value == null){
			return false;
		}
		if(value instanceof String){
			value = Boolean.valueOf((String) value);
		}
		
		return ((Boolean)value).booleanValue();
	}
	
	public void setExpanded(boolean expanded){
		setValue(expanded);
	}
	
	public UIPanelMenuGroup(){
		setConverter(new BooleanConverter());
	}
	
	public void addActionListener(ActionListener listener) {
		addFacesListener(listener);
	}

	public ActionListener[] getActionListeners() {
        ActionListener al[] = (ActionListener [])
	    getFacesListeners(ActionListener.class);
        return (al);

	}

	public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
	}

	public void queueEvent(FacesEvent event) {
		if(event instanceof ActionEvent && this == event.getSource()){
			if (isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}
		}
		super.queueEvent(event);
	}
	
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		if(event instanceof ActionEvent){
            //workaround for https://issues.apache.org/jira/browse/MYFACES-1871
			FacesListener[] listeners = getFacesListeners(FacesListener.class);
			for (FacesListener listener : listeners) {
				if (event.isAppropriateListener(listener)) {
					event.processListener(listener);
				}
			}
            
			FacesContext context = getFacesContext();
            // Notify the specified action listener method (if any)
            MethodBinding mb = getActionListener();
            if (mb != null) {
                mb.invoke(context, new Object[] { event });
            }

            // Invoke the default ActionListener
            ActionListener listener =
              context.getApplication().getActionListener();
            if (listener != null) {
                listener.processAction((ActionEvent) event);
            }
		} else {
			super.broadcast(event);
		}
	}
	

}
