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

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.component.UIAjaxCommandLink;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

/**
 * @author igels
 */
abstract public class UIDialogClose extends UIDialogAction implements ActionPrefixHolder {
	public static final String COMPONENT_TYPE = UIAjaxCommandLink.COMPONENT_TYPE;
	public static final String COMPONENT_FAMILY = "javax.faces.Command";

    String prefix = null;
    boolean isNullPath = false;
    DialogCloseListenerImpl listener = new DialogCloseListenerImpl();
    
    public UIDialogClose() {}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	protected void setupReRender() {
		FacesContext context = getFacesContext();
		if(context.getMessages().hasNext()) {
			super.setupReRender();
		}
		AjaxRendererUtils.addRegionByName(context,this,this.getId());
	}
	
	public void setPrefix(String s) {
		prefix = s;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public void setNullPath() {
		isNullPath = true;
	}

	public boolean isNullPath() {
		return isNullPath;
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
        if (event.isAppropriateListener(listener)) {
            event.processListener(listener);
        }
	}

}
