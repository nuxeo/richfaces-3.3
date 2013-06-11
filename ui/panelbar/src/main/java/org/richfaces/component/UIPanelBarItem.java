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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

public abstract class UIPanelBarItem 
		extends UIComponentBase 
		{
	public static final String COMPONENT_TYPE = "org.richfaces.PanelBarItem";
	
	public abstract String getLabel();
	
	public abstract void setLabel(String label);
	
    public abstract Object getName();
    
    public abstract void setName(Object name);

    public UIPanelBar getPanel(){
		UIComponent component = getParent();
    	while (component != null && !(component instanceof UIPanelBar)) {
    		component = component.getParent();
    	}

    	if (component == null) {
    		throw new FacesException("The component: " + this.getClientId(getFacesContext()) + " is not nested within " + UIPanelBar.class.getSimpleName());
    	} else {
        	return (UIPanelBar) component;
    	}
	}

        public boolean getRendersChildren() {
	    	return true;
	    }
	
}
