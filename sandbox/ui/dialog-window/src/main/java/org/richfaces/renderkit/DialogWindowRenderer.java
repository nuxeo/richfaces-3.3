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
import java.util.Map;

import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.ViewIdHolder;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.renderkit.AjaxCommandRendererBase;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.ActionPrefixHolder;
import org.richfaces.component.DialogContext;
import org.richfaces.component.DialogContextManager;
import org.richfaces.component.DialogOpenEvent;
import org.richfaces.component.DialogWindowClosedEvent;
import org.richfaces.component.UIDialogAction;
import org.richfaces.component.UIDialogWindow;


/**
 * @author igels
 *
 */
public class DialogWindowRenderer extends AjaxCommandRendererBase {

	private InternetResource[] _scripts = {new PrototypeScript(), getResource("script/dialog-window.js") };	

//	@Override
	protected Class getComponentClass() {
		return UIDialogWindow.class;
	}

//	@Override
	protected InternetResource[] getAdditionalScripts() {
		return _scripts;
	}

	/**
	 * Action is invoked in two cases:
	 * 1) Button has been pressed - standard JSF invocation.
	 * 2) Child dialog has just been closed - it simulates 
	 * action of this component by AJAX request
	 */
//	@Override
	public void doDecode(FacesContext context, UIComponent component) {
		if(!isSubmitted(getUtils(), context, component)) {
			return;
		}

//		((UIDialogAction)component).provideForceRerender();
		
		String dialogId = component.getClientId(context);

		/*
		 * Parameter action=close is set by js function closeDialog()
		 * for AJAX request simulating this action.
		 */
		String action = (String)context.getExternalContext().getRequestParameterMap().get("action");
		
		DialogContext dcontext = DialogContextManager.getInstance(context).getContext(dialogId);

		String posReferenceId = (String)context.getExternalContext().getRequestParameterMap().get("posReferenceId");
		((UIDialogWindow)component).setPosExternalReferenceId(posReferenceId);

		ActionSource actionSource = (ActionSource) component;

		/*
		 * Specific navigation for open/close dialogs 
		 * includes prefixes to outcomes. We need to 
		 * intercept computed outcome to extract the prefix
		 * and provide JSF with outcome stripped of it. 
		 */
        MethodBinding binding = actionSource.getAction();
        if(!(binding instanceof DialogMethodBinding)) {
        	actionSource.setAction(new DialogMethodBinding(binding, (ActionPrefixHolder)component));
        }
        
        DialogOpenEvent dialogEvent = new DialogOpenEvent(component);
        
		if(!"close".equals(action) && !"closeall".equals(action)) {
			
			/*
			 * Button has been pressed. 
			 * Child dialog is to be open.
			 * We need to send a standard ActionEvent to 
			 * let JSF to pass all the lifecycle up to 
			 * the end of invoke application phase where 
			 * we will interfere and change the navigation logic. 
			 */
			DialogContext parentContext = DialogContextManager.getInstance(context).getActiveContext();
			DialogContextManager.getInstance(context).setActiveRequest(dialogId);
			if(parentContext != null && parentContext != dcontext) parentContext.addChildContext(dcontext);
			dcontext.setLocked(false);

			Object type = component.getAttributes().get("type");
	     	if (null == type || ! "reset".equalsIgnoreCase((String)type) ) {
///	     		String mode = ((UIDialogAction)component).getMode();
     			ActionEvent event;
     			event = new ActionEvent(component);
     			component.queueEvent(event);
     			if(component instanceof ViewIdHolder) {
    	 			component.queueEvent( new AjaxEvent(component));
//    	 			((UIDialogWindow)component).initViewId();
//     				AjaxContext.getCurrentInstance(context).setViewIdHolder((ViewIdHolder)component);
//     				return;
     			}

	     	} else {
	 			component.queueEvent( new AjaxEvent(component));
	 		}
		} else {
			/*
			 * Child dialog has just been closed. 
			 * It simulates action of this component 
			 * by AJAX request from js function closeDialog() 
			 * Parameter 'targetViewId' is id of view
			 * that would be shown in dialog window if it had not 
			 * to be closed. Now it is saved in event 
			 * to be substituted for view root of this window.
			 */
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

	        component.queueEvent(new DialogWindowClosedEvent(component));
	        
 			component.queueEvent( new AjaxEvent(component));
 			dialogEvent.setClose(true);
		}
		
		/*
		 * Queue event that will be processed during 
		 * invoke application phase after navigation handler
		 * has calculated and set new view id. 
		 * See UIDialogWindow.processDialogOpen for details 
		 * of processing this event to change navigation logic.
		 */
		dialogEvent.queue();
	}
	
	public String getOnClick(FacesContext context, UIComponent component) {
		if (getUtils().isBooleanAttribute(component,"disabled")) {
			return "return false;";
		}
		String dialogPath = DialogWindowUtils.getDialogPath(context, component);
		if(dialogPath != null) return "return false;";
		
		DialogWindowUtils.saveParameters(component, context);

		StringBuffer sb = new StringBuffer();
		sb.append(DialogWindowUtils.getRunCondition());

		if(UIDialogAction.isServerMode(component)
				//|| DialogWindowUtils.findAncestorForm(context, component) == null
				) {
			String formId = DialogWindowUtils._getFormId(context, component);

			JSFunction f = new JSFunction("DialogContext.submitDialogAction");
			f.addParameter(formId);
			f.addParameter(component.getClientId(context));
			f.addParameter(AjaxRendererUtils.buildEventOptions(context, component));
			f.appendScript(sb);
			sb.append(';');

			if (!"reset".equals(component.getAttributes().get("type"))) {
				sb.append("return false;");
			}
//		} else if(DialogWindowUtils.findAncestorForm(context, component) == null) {
//			JSFunction f = new JSFunction("A4J.AJAX.Submit");
//			f.addParameter("_viewRoot");
//			f.addParameter("");
//			f.addParameter(new JSReference("event"));
//			f.addParameter(AjaxRendererUtils.buildEventOptions(context, component));
//			f.appendScript(sb);
//			sb.append(';');
//
//			if (!"reset".equals(component.getAttributes().get("type"))) {
//				sb.append("return false;");
//			}
//			return sb.toString();
		} else {
			sb = DialogWindowUtils.buildOnClick(component, context);
			sb.insert(0, DialogWindowUtils.getRunCondition());
			if (!"reset".equals(component.getAttributes().get("type"))) {
				sb.append(";return false;");
			}
		}
		return sb.toString();
	}
	
	public String getDialogBox(FacesContext context, UIComponent component) throws IOException {
        DialogContext dcontext = DialogContextManager.getInstance(context).getContext(component.getClientId(context));
		String dialogPath = DialogWindowUtils.getDialogPath(context, component);
		if(dialogPath == null) return "";
		String dialogId = component.getClientId(context);
		// Added by Hans, Wed Mar  7 14:55:24 EET 2007, CH-1541
		StringBuffer options = new StringBuffer(DialogWindowUtils.getOptions(context, component, getUtils()));
		options.deleteCharAt(options.length() - 1);
		String spacerImage = getResource("/org/richfaces/renderkit/html/images/spacer.gif")
			.getUri(context, null);
		DialogWindowUtils.append(options, "spacerImage", spacerImage);
		options.append('}');
		// by Hans
		String iframe = 
//		"<script type=\"text/javascript\">" +
			"try {" +
				"if(window && window.DialogContext) {" +
				options.toString() +
				    "new DialogContext(\'" + dialogId + "\', \'" + dialogPath + "\', " + getParentDialogId(dcontext) + ", options);" +
				"}" +
			"} catch (e) {" +
				"alert('Error in dialog window script: ' + e.message);" +
			"}" +
//		"</script>" +		
		"";
		return iframe;
	}

	public String getLinkID(FacesContext context, UIComponent component) throws IOException {
		return component.getClientId(context) + "_a";
	}

	// repeated from AjaxCommandRendererBase
	public static boolean isSubmitted(RendererUtils utils, FacesContext facesContext, UIComponent uiComponent) {
		// Componet accept only ajax requests. 
		if (!UIDialogWindow.isServerMode(uiComponent) && !AjaxRendererUtils.isAjaxRequest(facesContext)
				&& DialogWindowUtils.findAncestorForm(facesContext, uiComponent) != null) {
			return false;
		}
		if(utils.isBooleanAttribute(uiComponent, "disabled")) {
			return false;
		}
	    String clientId = uiComponent.getClientId(facesContext);
	    Map paramMap = facesContext.getExternalContext().getRequestParameterMap();
	    Object  value = paramMap.get( clientId );
		return null != value;
	}
	
	public String getParentDialogId(DialogContext dcontext) {
		DialogContext parentContext = dcontext.getParentContext();
		return (parentContext == null) ? null : "'" + parentContext.getDialogId() + "'";
	}

	public void insertFormBegin(FacesContext context, UIComponent component) {
		String formId = DialogWindowUtils.findAncestorForm(context, component);
		if (formId == null) {
			ResponseWriter out = context.getResponseWriter();
			try {
				out.startElement("form", component);
				out.writeAttribute("method", "post", null);
				out.writeAttribute("id", DialogWindowUtils._getInternalFormId(context, component), null);
			} catch (Exception e) {
			}
		}
	}

	public void insertFormEnd(FacesContext context, UIComponent component) {
		String formId = DialogWindowUtils.findAncestorForm(context, component);
		if (formId == null) {
			ResponseWriter out = context.getResponseWriter();
			try {
				out.endElement("form");
			} catch (Exception e) {
			}
		}
	}

}