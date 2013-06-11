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
package org.richfaces.renderkit;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.event.AjaxEvent;
import org.richfaces.component.DialogContext;
import org.richfaces.component.DialogContextManager;
import org.richfaces.component.DialogOpenEvent;
import org.richfaces.component.UIDialogAction;
import org.richfaces.component.UIDialogWindow;


public class DialogWindowOutputLinkRenderer extends DialogWindowRenderer {

//	@Override
	public void doDecode(FacesContext context, UIComponent component) {
		if(!isSubmitted(getUtils(), context, component)) {
			return;
		}
		String dialogId = component.getClientId(context);
		String action = (String)context.getExternalContext().getRequestParameterMap().get("action");
		DialogContext dcontext = DialogContextManager.getInstance(context).getContext(dialogId);
        DialogOpenEvent dialogEvent = new DialogOpenEvent(component);
        
		if(!"close".equals(action) && !"closeall".equals(action)) {
			DialogContext parentContext = DialogContextManager.getInstance(context).getActiveContext();
			DialogContextManager.getInstance(context).setActiveRequest(dialogId);
			if(parentContext != null && parentContext != dcontext) parentContext.addChildContext(dcontext);
			dcontext.setLocked(false);
			ViewHandler vh = UIDialogAction.getViewHandler(context);
			String targetActionURL = (String)((UIDialogWindow)component).getValue();
			targetActionURL = vh.getActionURL(context, targetActionURL);
	        dcontext.setDialogPath(targetActionURL);
 			component.queueEvent( new AjaxEvent(component));
 			context.renderResponse();
		} else {
			String targetViewId = (String)context.getExternalContext().getRequestParameterMap().get("targetViewId");
			if(targetViewId != null) {
				dialogEvent.setSourceViewId(targetViewId);
			}
			DialogContext parentContext = dcontext.getParentContext();
			dcontext.deactivate();
	        if(parentContext != null) { 
				DialogContextManager.getInstance(context).setActiveRequest(parentContext.getDialogId());
	        } else if("closeall".equals(action)) {
		    	DialogContextManager.getInstance(context).setActiveRequest(null);
			} else {
		    	DialogContextManager.getInstance(context).setActiveRequest(null);
			}
 			dialogEvent.setClose(true);
 			dialogEvent.queue();
 			component.queueEvent( new AjaxEvent(component));
		}
		
	}

}
