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

package org.ajax4jsf.renderkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.UIAjaxFunction;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;

/**
 * @author shura
 * 
 */
public class AjaxFunctionRendererBase extends AjaxCommandRendererBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return null;
	}

	public String getFunction(FacesContext context, UIAjaxFunction component) {
		String functionName = component.getName();
		if (functionName == null) {
			throw new FacesException("Value of 'name' attribute of a4j:jsFunction component is null!");
		}
		
		StringBuffer script = new StringBuffer(functionName).append("=");
		JSFunctionDefinition func = new JSFunctionDefinition();
		//func.setName(component.getName());
		// Create AJAX Submit function.
		JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(
				component, context,AjaxRendererUtils.AJAX_FUNCTION_NAME);
		Map<String, Object> options = AjaxRendererUtils.buildEventOptions(context, component);
		Map<String, Object> parameters = (Map<String, Object>) options.get("parameters");
		if (null == parameters) {
			parameters = new HashMap<String, Object>();
			options.put("parameters", parameters);
		}
		ajaxFunction.addParameter(JSReference.NULL);
		ajaxFunction.addParameter(options);
		// Fill parameters.
		for (Iterator<UIComponent> it = component.getChildren().iterator(); it.hasNext();) {
			UIComponent child = it.next();
			if (child instanceof UIParameter) {
				UIParameter parameter = ((UIParameter) child);
				String name = parameter.getName();
				func.addParameter(name);
				// Put parameter name to AJAX.Submit parameter, with default value.
				JSReference reference = new JSReference(name);
				if (null != parameter.getValue()) {
					reference = new JSReference(name + "||"
							+ ScriptUtils.toScript(parameters.get(name)));

				}
				// Replace parameter value to reference.
				parameters.put(name, reference);
			}
		}
		func.addToBody(ajaxFunction.toScript());
		func.appendScript(script);
		return script.toString();
	}
}
