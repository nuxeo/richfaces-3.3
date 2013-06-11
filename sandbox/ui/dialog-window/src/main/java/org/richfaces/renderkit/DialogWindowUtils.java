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

import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.component.AjaxSupport;
import org.ajax4jsf.component.JavaScriptParameter;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.DialogContext;
import org.richfaces.component.DialogContextManager;
import org.richfaces.component.UIDialogAction;
import org.richfaces.component.UIDialogWindow;


public class DialogWindowUtils {
	
	/**
	 * Options:
	 * 		width				public
	 * 		height				public
	 * 		posx				public
	 * 		posy				public
	 * 		resizable			public
	 * 		_float				public
	 * 		maxWidth			public
	 * 		maxHeight			public
	 * 		hideParentDialog	public
	 * 		headerHeight		public
	 * 		posFunction			public
	 * 		posReferenceId		public
	 * 
	 * @param context
	 * @param component
	 * @param utils
	 * @return
	 */

	public static String getOptions(FacesContext context, UIComponent component, RendererUtils utils) {
		StringBuffer options = new StringBuffer("{");
		options.append("var options= {};");
		UIComponent nestingContainer = (UIComponent) AjaxRendererUtils.findAjaxContainer(context, component);
		UIComponent nestingForm = AjaxRendererUtils.getNestingForm(component);
		String formId = nestingForm != null ? nestingForm.getClientId(context) : "";
		append(options, "container", nestingContainer.getClientId(context));
		append(options, "form", formId);
		
		Object w = component.getAttributes().get("width");
		if(w == null || w.toString().trim().length() == 0 || "auto".equals(w)) {
			w = "-1";
		} else {
			w = strip(w);
		}
		append(options, "width", w);

		Object h = component.getAttributes().get("height");
		if(h == null || h.toString().trim().length() == 0 || "auto".equals(h)) {
			h = "-1";
		} else {
			h = strip(h);
		}
		append(options, "height", h);

		Object x = component.getAttributes().get("posx");
		if(x == null) x = component.getAttributes().get("align");
		if(x == null || x.toString().trim().length() == 0) {
			x = "50%";
		} else {
			x = strip(x);
		}
		if(x.equals("left")) x = "0%";
		else if(x.equals("center") || x.equals("middle")) x = "50%";
		else if(x.equals("right")) x = "100%";
		append(options, "posx", x);

		Object y = component.getAttributes().get("posy");
		if(y == null) y = component.getAttributes().get("valign");
		if(y == null || y.toString().trim().length() == 0) {
			y = "50%";
		} else {
			y = strip(y);
		}
		if(y.equals("top")) y = "0%";
		else if(y.equals("middle") || y.equals("center")) y = "50%";
		else if(y.equals("bottom")) y = "100%";
		append(options, "posy", y);
		
		w = component.getAttributes().get("maxWidth");
		if(w == null || w.toString().trim().length() == 0) {
			w = "-1";
		} else {
			w = strip(w);
		}
		append(options, "maxWidth", w);
		
		h = component.getAttributes().get("maxHeight");
		if(h == null || h.toString().trim().length() == 0) {
			h = "-1";
		} else {
			h = strip(h);
		}
		append(options, "maxHeight", h);

		if(!UIDialogAction.isServerMode(component) 
			&& DialogWindowRenderer.isSubmitted(utils, context, component)
			&& findAncestorForm(context, component) != null) {
			append(options, "ajax", "true");
		}

		Object c = component.getAttributes().get("resizable");
		if(c == null) c = Boolean.TRUE;
		append(options, "resizable", c);
		
		c = component.getAttributes().get("float");
		if(c == null) c = Boolean.TRUE;
		append(options, "_float", c);		

		c = component.getAttributes().get("headerHeight");
		if(c == null) c = "16px";
		append(options, "headerHeight", c);
		
		c = component.getAttributes().get("posFunction");
		if(c != null) {
			append(options, "posFunction", c);
		}

		Object posReferenceId = ((UIDialogWindow)component).getExternalPosReferenceId();
		if(posReferenceId == null) posReferenceId = ((UIDialogWindow)component).getPosReferenceId();
		if(posReferenceId != null) {
			append(options, "posReferenceId", posReferenceId);
		}
		
		c = component.getAttributes().get("hideParentDialog");
		if(c == null) c = Boolean.FALSE;
		append(options, "hideParentDialog", c);		

		options.append('}');
		return options.toString();
	}
	
	static String strip(Object value) {
		String v = value.toString().trim();
		if(v.endsWith("px")) return v.substring(0, v.length() - 2);
		return v;
	}
	
	public static void append(StringBuffer options, String name, Object value) {
		options.append("options.").append(name).append("='").append(value.toString()).append("';");		
	}

	public static String findAncestorForm(FacesContext context,UIComponent component) {
		while (component != null && !(component instanceof UIForm)) {
			component = component.getParent();
		}
		return component != null ? component.getClientId(context) : null;
	}

	public static String _getFormId(FacesContext context, UIComponent component) {
		String formId = findAncestorForm(context, component);
		if (formId == null)
			return _getInternalFormId(context, component);
		else
			return formId;
	}

	public static String _getInternalFormId(FacesContext context, UIComponent component) {
		return "dialogForm_" + component.getClientId(context);
	}
	
	/**
	 * This is modified version of AjaxRendererUtils.buildOnClick
	 * We need to insert into options id of reference component 
	 * transferred by DW.open(dialogId, referenceId)
	 * @param uiComponent
	 * @param facesContext
	 * @return
	 */
	
	public static StringBuffer buildOnClick(UIComponent uiComponent, FacesContext facesContext) {
		String eventName = HTML.onclick_ATTRIBUTE;
		StringBuffer onEvent = new StringBuffer(); 
		if(null != eventName) {
			String commandOnEvent = (String) uiComponent.getAttributes().get(eventName);
			if (commandOnEvent != null) {
				onEvent.append(commandOnEvent);
				onEvent.append(';');
			}
		}
		Map options = AjaxRendererUtils.buildEventOptions(facesContext, uiComponent);
		onEvent.append("var options=").append(ScriptUtils.toScript(options)).append(";");
		onEvent.append("if(this.posReferenceId) { options.parameters.posReferenceId=this.posReferenceId;this.posReferenceId=null; }");

		JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(uiComponent, facesContext);
		ajaxFunction.addParameter(new JSReference("options"));

		// appendAjaxSubmitParameters(facesContext, uiComponent, onEvent);
		onEvent.append("try {");
		ajaxFunction.appendScript(onEvent);
		onEvent.append("} catch (e) {alert('Error in onclick: ' + e.message);}");
		
		if (uiComponent instanceof AjaxSupport) {
			AjaxSupport support = (AjaxSupport) uiComponent;
			if (support.isDisableDefault()) {
				onEvent.append("; return false;");
			}
		}
		return onEvent;

	}
	
	public static String getRunCondition() {
		return "try {if(window.hasModalDialog) return false;this.onclick='';this.click=null; window.hasModalDialog=true;} catch (e) {'error in runCondition: ' + e.message};";
	}
	
	public static String getDialogPath(FacesContext context, UIComponent component) {
        DialogContext dcontext = DialogContextManager.getInstance(context).getContext(component.getClientId(context));
		return dcontext.getDialogPath();
	}

	public static void saveParameters(UIComponent uiComponent, FacesContext facesContext) {
		if(!(uiComponent instanceof UIDialogWindow)) return;
		String dialogId = uiComponent.getClientId(facesContext);
		DialogContext context = DialogContextManager.getInstance(facesContext).getContext(dialogId);
		for (Iterator it = uiComponent.getChildren().iterator(); it.hasNext();) {
			UIComponent child = (UIComponent) it.next();
			if (child instanceof UIParameter) {
				String name = ((UIParameter) child).getName();
				Object value = ((UIParameter) child).getValue();
				if (null == name) {
					throw new IllegalArgumentException(Messages.getMessage(
							Messages.UNNAMED_PARAMETER_ERROR, uiComponent
									.getClientId(facesContext)));
				}
				boolean escape = true;
				if (child instanceof JavaScriptParameter) {
					JavaScriptParameter actionParam = (JavaScriptParameter) child;
					escape = !actionParam.isNoEscape();
				}
				if (escape) {
					context.setParameter(name, value);
				} else {
//					parameters.put(name, new JSReference(value.toString()));
				}
			}
		}
		
	}

	
}
