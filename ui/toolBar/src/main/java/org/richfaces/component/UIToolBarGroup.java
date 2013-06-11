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

import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

/**
 * @author Maksim Kaszynski
 *
 */
public abstract class UIToolBarGroup extends UIComponentBase {
	public static final String COMPONENT_TYPE = "org.richfaces.component.ToolBarGroup";

	public abstract String getItemSeparator();
	public abstract void setItemSeparator(String itemSeparator);
	
	public abstract String getLocation();
	public abstract void setLocation(String location);

	public UIToolBar getToolBar() {
        UIComponent component = this.getParent();
        if (component == null) {
    		throw new FacesException("The component: " + this.getClientId(getFacesContext()) + 
    				" is not nested within " + UIToolBar.class.getSimpleName());
    	} else if (!(component instanceof UIToolBar)) {
    		throw new FacesException("The component: " + this.getClientId(getFacesContext()) + 
    				" is not a direct child of " + UIToolBar.class.getSimpleName());
    	}
        return (UIToolBar) component;
	}
	
    public List<UIComponent> getRenderedChildren() {
        List<UIComponent> children = this.getChildren();
        List<UIComponent> renderedChildren = new ArrayList<UIComponent>(children.size());
        
        for (UIComponent child : children) {
            if (child.isRendered()) {
                renderedChildren.add(child);
            }
        }
        
        return renderedChildren;
    } 
}
