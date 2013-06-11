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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.renderkit.AjaxCommandRendererBase;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.DialogContext;
import org.richfaces.component.DialogContextManager;
import org.richfaces.component.UIDialogAction;


/**
 * 
 * @author glory
 *
 */
public abstract class DialogBaseActionRenderer extends AjaxCommandRendererBase {
	private InternetResource[] _scripts = {new PrototypeScript(), getResource("script/dialog-window.js") };	

	protected InternetResource[] getAdditionalScripts() {
		return _scripts;
	}
	
	protected void doAjaxDecode(FacesContext context, UIComponent component) {
		super.doDecode(context, component);
	}

//	@Override
	public void doDecode(FacesContext context, UIComponent component) {
		if(!DialogWindowRenderer.isSubmitted(getUtils(), context, component)) {
			return;
		}

        if(!UIDialogAction.isServerMode(component)) {
        	super.doDecode(context, component);
        } else {
	    	Object type = component.getAttributes().get("type");
        	if (null == type || ! "reset".equalsIgnoreCase((String)type) ) {
				ActionEvent event;
				event = new ActionEvent(component);
				component.queueEvent(event);
				//no ajax event
			} 
        }
	}

	public String getOnClick(FacesContext context, UIComponent component) {
		if (getUtils().isBooleanAttribute(component, "disabled")) {
			return "return false;";
		}
		DialogContext dcontext = DialogContextManager.getInstance(context).getActiveContext();

		StringBuffer superOnClick = null;
		if(UIDialogAction.isServerMode(component)) {
			String formId = DialogWindowUtils.findAncestorForm(context, component);

			JSFunction f = new JSFunction("DialogContext.submitDialogAction");
			f.addParameter(formId);
			f.addParameter(component.getClientId(context));
			f.addParameter(AjaxRendererUtils.buildEventOptions(context, component));
			superOnClick = new StringBuffer();
			f.appendScript(superOnClick);
			superOnClick.append(';');
			if (!"reset".equals(component.getAttributes().get("type"))) {
				superOnClick.append("return false;");
			}
		} else {
			superOnClick = new StringBuffer(super.getOnClick(context, component));
		}
        String script = "" +
		    "try {\n" +
		    	(dcontext != null ? "if(window) { onDialogLink(" + getOnDialogLinkParameter(dcontext) + "); }\n" : "") +
			    superOnClick.toString() + "\n" +
		    "} catch (e) {\n" +
		        "alert('Error in onclick ' + e.message);\n" +
		    "}\n" +
		    "return false;\n" +
		"";

        return script;
	}
	
	protected String getOnDialogLinkParameter(DialogContext dcontext) {
		return "'" + dcontext.getDialogPath() + "'";
	}

}
