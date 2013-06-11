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
import org.richfaces.renderkit.DialogWindowUtils;


/**
 * 
 * @author glory
 *
 */

public class DialogOpenListenerImpl implements DialogOpenListener {
	DialogOpenEvent lastProcessedEvent = null;
	/**
	 * This method is invoked during invoke application phase
	 * after navigation handler calculated and set new view id 
	 * (as result of processing an ActionEvent).
	 * Now it is the best time two interfere into its logic 
	 * and 'redistribute' views in parent and child windows.
	 */
	public void processDialogOpen(DialogOpenEvent event) {
		if(lastProcessedEvent == event) return;
		lastProcessedEvent = event; 
		UIComponent c = event.getComponent();
		if(!(c instanceof UIDialogWindow)) return;
		UIDialogWindow component = (UIDialogWindow)c;
		
		/*
		 * If dialog is not needed, nothing else is to be done.
		 * 
		 */
		if(ActionPrefixHolder.PREFIX_NORMAL.equals(component.prefix)) {
			if(!event.isClose()) {
				rejectOpen();
			}
			return;
		}
		if(component.isNullPath()) {
			if(!event.isClose()) {
				rejectOpen();
				return;
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot viewroot = context.getViewRoot();
		ViewHandler vh = UIDialogAction.getViewHandler(context);
		UIViewRoot oldRoot = event.getSourceViewRoot();

		if(!event.isClose()) {
			/*
			 * Child dialog is about to open.
			 * The view id caculated by the navigation handler
			 * is to be saved and passed to child dialog window.
			 */
			String viewId = viewroot.getViewId();
			String targetActionURL = vh.getActionURL(context, viewId);
			targetActionURL += component.getParameterRequest();
	        DialogContext dcontext = DialogContextManager.getInstance(context).getContext(component.getClientId(context));
	        dcontext.setDialogPath(targetActionURL);
		} else {
			/*
			 * Child dialog has just been closed. 
			 * Nothing to be saved for future use.
			 */
		}

        /*
         * We need to prevent rendering new view in parent
         * window. Here we have two cases:
         * 1) Child dialog is about to open. In this case 
         * we need to restore in (this) parent window old view.         * 
         * 2) Child dialog has just been closed. In this case 
         * event keeps view id that should be shown in  
         * So view set by navigation handler is (this) parent 
         * window.
         */
		String oldViewId = event.getSourceViewId();
		UIViewRoot newRoot = null;
		boolean b1 = oldViewId == null || oldViewId.equals("null");
		boolean b2 = b1 || oldViewId.equals(oldRoot.getViewId());
		boolean b3 = (
				(!UIDialogAction.isServerMode(component) &&
					DialogWindowUtils.findAncestorForm(context, component) != null)
				|| component.isClose());
		boolean b = (b1 || (b2 && b3));
		try { 
			if(b) {
				newRoot = oldRoot;
			}
		} catch (Exception e) {
			//ignore
		}
		if(newRoot == null) {
			newRoot = vh.createView(context, oldViewId);
		} else {
			if(!b) try {
//				System.out.println("Restore state");
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
			if(b) {
				nc = component;
			} else {
				nc = newRoot.findComponent(component.getClientId(context));
			}
		} catch (Exception e) {
			//ignore
		}
		if(nc instanceof UIComponent) {
			UIComponent newComponent = (UIComponent)nc;
			AjaxEvent e = new AjaxEvent(newRoot);
			try {
//				newRoot.queueEvent(e);
				e = new AjaxEvent(newComponent);
				newComponent.queueEvent(e);
			} catch (Exception exc) {
				System.out.println("Cannot queue event: " + newComponent.getClass().getName());
			}
		}

	}
	
	private void rejectOpen() {
		FacesContext context = FacesContext.getCurrentInstance();
		DialogContext dcontext = DialogContextManager.getInstance(context).getActiveContext();
		if(dcontext == null) return;
		DialogContext parentContext = dcontext.getParentContext();
		dcontext.deactivate();
        if(parentContext != null) { 
			DialogContextManager.getInstance(context).setActiveRequest(parentContext.getDialogId());
		} else {
	    	DialogContextManager.getInstance(context).setActiveRequest(null);
		}
		
		
	}
	
}
