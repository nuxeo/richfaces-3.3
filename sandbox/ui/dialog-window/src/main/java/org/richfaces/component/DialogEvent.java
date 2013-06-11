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

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;

public abstract class DialogEvent extends FacesEvent {
	UIViewRoot root;
	/**
	 * View id of page that sends this event. 
	 * It is to be keeped to override view that will 
	 * be set by navigation handler, because that other 
	 * view is needed for the parent window.
	 */
	String sourceViewId;
	AjaxContext ajaxContext;

	public DialogEvent(UIComponent component) {
		super(component);
		setPhaseId(PhaseId.INVOKE_APPLICATION);
		if(component instanceof ActionSource && ((ActionSource)component).isImmediate()) {
			setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
		} else if(component instanceof AjaxComponent && ((AjaxComponent)component).isBypassUpdates()) {
			setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		root = context.getViewRoot();
		sourceViewId = context.getViewRoot().getViewId();
		ajaxContext = AjaxContext.getCurrentInstance(context);
	}

	public String getSourceViewId() {
		return sourceViewId;
	}
	
	public UIViewRoot getSourceViewRoot() {
		return root;
	}
	
	public AjaxContext getAjaxContext() {
		return ajaxContext;
	}
	
}
