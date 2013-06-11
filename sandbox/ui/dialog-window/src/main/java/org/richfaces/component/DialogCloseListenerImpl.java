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

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;

public class DialogCloseListenerImpl implements DialogCloseListener {
	public void processDialogClose(DialogCloseEvent event) {
		UIComponent c = event.getComponent();
		if(!(c instanceof UIDialogClose)) return;
		UIDialogClose component = (UIDialogClose)c;
		/*
		 * If dialog is not going to be closed, nothing 
		 * to be done, new view has already been set by
		 * navigation handler.
		 */
		if(ActionPrefixHolder.PREFIX_NOCLOSE.equals(component.prefix)) return;

		FacesContext context = FacesContext.getCurrentInstance();

		/*
		 * Save id of view that would be shown in the dialog
		 * window if it had not to be closed. Renderer will pass
		 * it to js function closeDialog() 
		 */
		UIViewRoot viewroot = context.getViewRoot();
		String viewId = viewroot.getViewId();
		if(component.isNullPath()) {
			viewId = "null";
		}
		String closeId = component.getClientId(context) + "_close";
        context.getExternalContext().getRequestMap().put(closeId, viewId);
        if(ActionPrefixHolder.PREFIX_CLOSEALL.equals(component.prefix)) {
        	String closeAll = component.getClientId(context) + "_closeall";
            context.getExternalContext().getRequestMap().put(closeAll, viewId);
        }

        /*
         * We need to prevent new view rendering in dialog
         * window. So view set by navigation handler is 
         * to be replaced with old view. 
         */
        UIViewRoot oldRoot = event.getSourceViewRoot();
		String oldViewId = event.getSourceViewId();
		ViewHandler vh = UIDialogAction.getViewHandler(context);
//		UIViewRoot newRoot = vh.createView(context, oldViewId);
//		context.setViewRoot(newRoot);		
		UIViewRoot newRoot = null;
		try { 
			if(oldViewId.equals(oldRoot.getViewId()) && !UIDialogAction.isServerMode(component)) {
				newRoot = oldRoot;
			}
		} catch (Exception e) {
			//ignore
		}
		if(newRoot == null) {
			newRoot = vh.createView(context, oldViewId);
		} else {
			try {
				Object st = oldRoot.saveState(context);
				newRoot.restoreState(context, st);
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.getExternalContext().getRequestMap().put(AjaxContext.AJAX_CONTEXT_KEY, event.getAjaxContext());
		}

		context.setViewRoot(newRoot);
		
		Object nc = null;
		try {		
			nc = newRoot.findComponent(component.getClientId(context));
		} catch (Exception e) {
			//ignore
		}
		if(nc instanceof UIComponent) {
			UIComponent newComponent = (UIComponent)nc;
			try {
				AjaxEvent e = new AjaxEvent(newRoot);
				newRoot.queueEvent(e);
				e = new AjaxEvent(newComponent);
				newComponent.queueEvent(e);
			} catch (Exception exc) {
				System.out.println("Cannot queue event: " + newComponent.getClass().getName());
			}
		}

	}
	
}
