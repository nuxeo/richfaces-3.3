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

package org.ajax4jsf.renderkit.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIPush;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.renderkit.AjaxCommandRendererBase;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;

/**
 * @author shura
 *
 */
public class AjaxPushRenderer extends AjaxCommandRendererBase {
	
	public static final String PUSH_INTERVAL_PARAMETER = "A4J.AJAX.Push.INTERVAL";

	public static final String PUSH_WAIT_PARAMETER = "A4J.AJAX.Push.WAIT";

	public static final String PUSH_URL_PARAMETER = "A4J.AJAX.Push.URL";

	public static final int DEFAULT_PUSH_INTERVAL = 1000;

	public static final int DEFAULT_PUSH_WAIT = Integer.MIN_VALUE;
	
	private static final String AJAX_PUSH_FUNCTION = "A4J.AJAX.Push";

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeEnd(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		UIPush push = (UIPush) component;
		writer.startElement(HTML.SPAN_ELEM, component);
		writer.writeAttribute(HTML.style_ATTRIBUTE, "display:none;", null);
		getUtils().encodeId(context, component);
		getUtils().encodeBeginFormIfNessesary(context, component);
			// pushing script.
				writer.startElement(HTML.SCRIPT_ELEM, component);
				writer.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
				StringBuffer script = new StringBuffer("\n");
				if(push.isEnabled()){
				JSFunction function = AjaxRendererUtils.buildAjaxFunction(component, context, AJAX_PUSH_FUNCTION);
				// Set dummy form id, if nessesary.
				Map<String, Object> options = AjaxRendererUtils.buildEventOptions(context, component);
				options.put("dummyForm", component.getClientId(context)+RendererUtils.DUMMY_FORM_ID);
				int interval = push.getInterval();
				if(interval == Integer.MIN_VALUE){
				    String intervalInitParameter = context.getExternalContext().getInitParameter(PUSH_INTERVAL_PARAMETER);
				    if(null != intervalInitParameter){
					interval = Integer.parseInt(intervalInitParameter);
				    } else {
					interval = DEFAULT_PUSH_INTERVAL;
				    }
				}
				options.put("pushinterval", new Integer(interval));
				options.put("pushId", push.getListenerId(context));
				String pushUrl = context.getExternalContext().getInitParameter(PUSH_URL_PARAMETER);
				if(null != pushUrl){
				    options.put("pushUrl", pushUrl);
				}
				//				options.put("timeout", interval);
				function.addParameter(options);
				function.appendScript(script);
				} else {
					script.append("A4J.AJAX.StopPush('").append(push.getListenerId(context)).append("')");
				}
				script.append(";\n");
				writer.writeText(script.toString(),null);
				writer.endElement(HTML.SCRIPT_ELEM);
		getUtils().encodeEndFormIfNessesary(context, component);
		writer.endElement(HTML.SPAN_ELEM);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		// only push component is allowed.
		return UIPush.class;
	}
	
	protected boolean isSubmitted(FacesContext facesContext, UIComponent uiComponent) {
		boolean submitted = super.isSubmitted(facesContext, uiComponent);
		UIPush push = (UIPush) uiComponent;
		push.setSubmitted(submitted);
		return submitted;
	}

}
