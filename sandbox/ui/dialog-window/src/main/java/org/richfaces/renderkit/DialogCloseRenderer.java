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

import java.io.IOException;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.richfaces.component.ActionPrefixHolder;
import org.richfaces.component.DialogCloseEvent;
import org.richfaces.component.DialogContext;
import org.richfaces.component.UIDialogAction;
import org.richfaces.component.UIDialogWindow;


/**
 * @author igels
 *
 */
public class DialogCloseRenderer extends DialogBaseActionRenderer {

//	@Override
	protected Class getComponentClass() {
		return UIDialogWindow.class;
	}

//	@Override
	public void doDecode(FacesContext context, UIComponent component) {
		if(!DialogWindowRenderer.isSubmitted(getUtils(), context, component)) {
			return;
		}

		ActionSource actionSource = (ActionSource) component;
        MethodBinding binding = actionSource.getAction();
        if(!(binding instanceof DialogMethodBinding)) {
        	actionSource.setAction(new DialogMethodBinding(binding, (ActionPrefixHolder)component));
        }
        
        DialogCloseEvent dialogEvent = new DialogCloseEvent(component);
        
        if(!UIDialogAction.isServerMode(component)) {
        	super.doAjaxDecode(context, component);
        } else {
	    	Object type = component.getAttributes().get("type");
        	if (null == type || ! "reset".equalsIgnoreCase((String)type) ) {
				ActionEvent event;
				event = new ActionEvent(component);
				component.queueEvent(event);
				//no ajax event
			} 
        }
		dialogEvent.queue();
	}

	public String getRegistration(FacesContext context, UIComponent component) throws IOException {
		/*
		 * If close button was pressed, navigation handler 
		 * calculated id of view that is to be shown after 
		 * dialog window is closed. This id should be passed
		 * as parameter to js function closeDialog() which 
		 * then will invoke parent window with it.
		 */

		String script = 
//			"<script>" +
				"try {" +
					"if(window && window.registerInParent) { " +
						"registerInParent(); " +
						getCloseMethod(context, component) +
//						((parentTargetViewId != null) ? methodName + "('" + id + "','" + parentTargetViewId + "');" : "") +
					"}" +
				"} catch (e) {" +
					"alert('registration script error: ' + e.message);" +
				"}" +
//			"</script>" +
			"";
		return script;
	}
	
	private String getCloseMethod(FacesContext context, UIComponent component) {
		String closeId = component.getClientId(context) + "_close";
		String parentTargetViewId = (String)context.getExternalContext().getRequestMap().get(closeId);
		String closeAll = component.getClientId(context) + "_closeall";
		String ptv = (String)context.getExternalContext().getRequestMap().get(closeAll);
		if(parentTargetViewId == null) return "";
		
		String methodName = (ptv != null) ? "closeAll" : "closeDialog";

		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		String id = session.getId();

		String result = 
			"var args = {};" +
			"args.id = '" + id + "';" +
			"args.targetViewId = '" + parentTargetViewId + "';" +
			
			"" + methodName + "(args);"
		;
		return result;
	}

	protected String getOnDialogLinkParameter(DialogContext dcontext) {
		return "";
	}

}