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

/*
 * Created on 03.07.2006
 */
package org.richfaces.component;

import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.event.AjaxSource;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;

/**
 * @author igels
 * 
 */

public abstract class UIToggleControl extends AjaxActionComponent implements
        AjaxComponent, AjaxSource, ActionSource {
    
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    
    public abstract void setPanelId(String panelId);
    public abstract String getPanelId();
    
    public abstract void setSwitchToState(String switchToState);
    public abstract String getSwitchToState();
    
    public abstract String getFor();
    public abstract void setFor(String f);
    
    public boolean getRendersChildren() {
        return true;
    }
    
    public UITogglePanel getPanel() throws FacesException {
        UIComponent control = this;
        String target = ((UIToggleControl) control).getFor();
        
        if (null != target) {
            
            UIComponent targetComponent = RendererUtils.getInstance()
                    .findComponentFor(control, target);
            if (null != targetComponent) {
                return (UITogglePanel) targetComponent;
            } else {
                throw new FacesException("Parent panel for control (id="
                        + getClientId(getFacesContext())
                        + ") has not been found.");
            }
        } else {
            
            while ((control = control.getParent()) != null) {
                if (control instanceof UITogglePanel) {
                    return (UITogglePanel) control;
                }
            }
            throw new FacesException("Parent panel for control (id="
                    + getClientId(getFacesContext()) + ") has not been found.");
        }
    }
    
    @Override
    protected void setupReRender(FacesContext facesContext) {
        super.setupReRender(facesContext);
        UITogglePanel togglePanel = getPanel();
        AjaxRendererUtils.addRegionByName(facesContext, togglePanel,
                togglePanel.getId());
        
    }
    
    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
