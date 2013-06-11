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

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.event.AjaxSource;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

/**
 * Base class for panel tab.
 *
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.12 $ $Date: 2007/02/21 20:35:05 $
 */
//TODO remove excess interfaces
public abstract class UITab extends AjaxActionComponent implements AjaxComponent, AjaxSource, ActionSource {

    public static final String COMPONENT_TYPE = "org.richfaces.Tab";

    private transient boolean _active = false;

    /**
     * @param context
     * @param active
     * @throws IOException
     */
    public void encodeTab(FacesContext context, boolean active) throws IOException {
        if (context == null) {
            throw new NullPointerException("No FacesContext");
        }

        if (!isRendered()) {
            return;
        }

        Renderer renderer = getRenderer(context);
        if (null != renderer && renderer instanceof TabEncoder) {
            ((TabEncoder) renderer).encodeTab(context, this, active);
        }
    }

    /**
     * Get enclosed {@link UITabPanel} tab panel for this component.
     *
     * @return {@link UITabPanel}
     */
    public UITabPanel getPane() throws FacesException {
        UIComponent component = this.getParent();
    	while (component != null && !(component instanceof UITabPanel)) {
    		component = component.getParent();
    	}

    	if (component == null) {
    		throw new FacesException("The component: " + this.getClientId(getFacesContext()) + " is not nested within " + UITabPanel.class.getSimpleName());
    	} else {
            return (UITabPanel) component;
    	}
    }

    /* (non-Javadoc)
      * @see org.ajax4jsf.framework.ajax.AjaxActionComponent#setupReRender()
      */
    //TODO remove
    protected void setupReRender() {
        super.setupReRender();
        AjaxRendererUtils.addRegionByName(getFacesContext(), this, this.getId());
    }

    /**
     * @return Returns the active.
     */
    public boolean isActive() {
        return _active;
    }

    /**
     * @param active The active to set. This method should never be called from user code.
     */
    public void setActive(boolean active) {
        _active = active;
    }

    /**
     * Get Model Value for this Tab.
     *
     * @return value from local variable or value bindings
     */
    public abstract Object getValue();

    /**
     * Set Model Value for this Tab.
     *
     * @param newvalue
     */
    public abstract void setValue(Object newvalue);

    public abstract Object getName();

    public abstract void setName(Object newvalue);

    public abstract boolean isDisabled();

    public abstract void setDisabled(boolean disabled);

    public abstract String getLabel();

    public abstract void setLabel(String newvalue);

    public abstract String getLabelWidth();

    public abstract void setLabelWidth(String newvalue);

    public abstract String getTitle();

    public abstract void setTitle(String newvalue);

    /*	public abstract String getDescription();
     public abstract void setDescription(String newvalue);*/

    public String getSwitchTypeOrDefault() {
    	String switchType = getSwitchType();
    	if (switchType == null) {
    		switchType = getPane().getSwitchType();
    	}
    	
    	if (switchType != null) {
    		return switchType;
    	}
    	
        return UISwitchablePanel.DEFAULT_METHOD;
    }
    
    public abstract String getSwitchType();
    public abstract void setSwitchType(String newvalue);

    //TODO - nick - ?
//	public void processDecodes(FacesContext context) {
//
//		UITabPanel pane = getPane();
//
//		if(getName().equals(pane.getSelectedTab().toString())) {
//			super.processDecodes(context);
//		} else {
//			super.decode(context);
//		}
//	}
}
