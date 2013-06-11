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
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.richfaces.event.SwitchablePanelSwitchEvent;

/**
 * JSF component class
 *
 */

public abstract class UIPanelBar extends UISwitchablePanel 
//		extends UIComponentBase 
		{
	
	public static final String COMPONENT_TYPE = "org.richfaces.PanelBar";

	public abstract String getHeight();

	public abstract void setHeight(String height);

	public abstract String getWidth();

	public abstract void setWidth(String width);
    
    public abstract Object getSelectedPanel();

    public abstract void setSelectedPanel(Object item);
	
	public boolean getRendersChildren() {
	    	return true;
	    }
	public String getSwitchType() {
		return CLIENT_METHOD;
	}

    public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
        if (facesEvent instanceof SwitchablePanelSwitchEvent) {
            if (isRendered()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                
                Object oldValue = getValue();
                super.broadcast(facesEvent);
                SwitchablePanelSwitchEvent switchEvent = (SwitchablePanelSwitchEvent) facesEvent;
                Object newValue = convertSwitchValue(switchEvent.getEventSource(),
                        switchEvent.getValue());
                if (oldValue == null && newValue != null ||
                        oldValue != null && !oldValue.equals(newValue)) {

                    Object newName = null;
                    List items = getChildren();
                    for(Iterator it = items.iterator();it.hasNext();) {
                        UIComponent comp = (UIComponent)it.next();
                        if (comp instanceof UIPanelBarItem) {
                            UIPanelBarItem item = (UIPanelBarItem) comp;
                            if (item.getClientId(facesContext).equals(newValue)) {
                                newName = item.getName();
                                break;
                            }
                        } 
                    }
                    
                    if (newName != null) {
                        ValueBinding valueBinding = getValueBinding("selectedPanel");
                        if (valueBinding != null) {
                            valueBinding.setValue(facesContext, newName);
                            setSelectedPanel(null);
                        } else {
                            setSelectedPanel(newName);
                        }
                    }
                }
            }
        } else { 
            super.broadcast(facesEvent);
        }
    }

}
