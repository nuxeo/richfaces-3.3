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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.JavaScriptParameter;
import org.ajax4jsf.javascript.JSBind;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIContextMenu;
import org.richfaces.component.util.HtmlUtil;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.renderkit.TemplateEncoderRendererBase;
import org.xml.sax.ContentHandler;

/**
 * @author Maksim Kaszynski
 *
 */
public class ContextMenuRendererBase extends TemplateEncoderRendererBase {

    /**
     * Constant for "immediate" attach timing option
     */
    private static final String IMMEDIATE = "immediate";

    /**
     * Constant for "onAvailable" attach timing option
     */
    private static final String ON_AVAILABLE = "onavailable";

    /**
     * Constant for "onload" attach timing option
     */
    private static final String ON_LOAD = "onload";
    
    private final ContextMenuRendererDelegate delegate = 
		new ContextMenuRendererDelegate();

	private final InternetResource[] ownScripts  = {
		getResource("/org/richfaces/renderkit/html/scripts/json/json-dom.js"),
		getResource("/org/richfaces/renderkit/html/scripts/utils.js"),
		getResource("/org/richfaces/renderkit/html/scripts/context-menu.js"),
		new org.ajax4jsf.javascript.PrototypeScript(),
		new org.ajax4jsf.javascript.AjaxScript(),
		getResource("/org/richfaces/renderkit/html/scripts/available.js"),
		getResource("/org/richfaces/renderkit/html/scripts/jquery/jquery.js"),
		getResource("/org/richfaces/renderkit/html/scripts/jquery.utils.js")

	};
	
	private final InternetResource[] scripts;
	
	public ContextMenuRendererBase() {
		InternetResource[] delegateScripts = delegate.getScripts();
		scripts = new InternetResource[delegateScripts.length + ownScripts.length];
		
		System.arraycopy(delegateScripts, 0, scripts, 0, delegateScripts.length);
		System.arraycopy(ownScripts, 0, scripts, delegateScripts.length, ownScripts.length);
	}
	
	protected InternetResource[] getScripts() {
		return scripts;
	}
	
	protected InternetResource[] getStyles() {
		return delegate.getStyles();
	}
	
	protected Class getComponentClass() {
		return delegate.getComponentClass();
	}


	protected void doEncodeBegin(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		ensureParentPresent(component);
		checkAttachTimingValidity(context, component);
		writer.startElement(HTML.DIV_ELEM, component);
		writer.writeAttribute(HTML.id_ATTRIBUTE, component.getClientId(context), "id");
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	private void ensureParentPresent(UIComponent component) {
		UIComponent parent = component.getParent();
		UIContextMenu menu = (UIContextMenu) component;
		
		if (parent != null) {
			if (!HtmlUtil.shouldWriteId(parent) && menu.isAttached() && isEmpty(menu.getAttachTo())) {
				throw new FacesException(
						"Context menu cannot be attached to the component with id = "
						+ parent.getId()
						+ ", because a client identifier of the component won't be rendered onto the page. Please, set the identifier.");
			}
		} else {
			throw new FacesException(
					"Parent component is null for ContextMenu "
							+ component.getId());
		}
	}
	
	/**
	 * Perform validation of the contextMenu configuration.
	 * 
	 * @param component - menu component
	 */
	protected void checkAttachTimingValidity(FacesContext context, UIComponent component) {
		UIContextMenu menu = (UIContextMenu) component;
		String attachTiming = menu.getAttachTiming();
	    if (!ON_LOAD.equals(attachTiming) && !IMMEDIATE.equals(attachTiming) && !ON_AVAILABLE.equals(attachTiming)) {
	    	context.getExternalContext().log(attachTiming + " value of attachTiming attribute is not a legal one for component: "
							+ MessageUtil.getLabel(context, component)
							+ ". Default value was applied.");
	    	menu.setAttachTiming(ON_AVAILABLE);
	    }
	}
	
	public void renderChildren(FacesContext context, UIComponent component)
			throws IOException {
		delegate.encodeChildren(context, component);
	}
	

	public void encodeChildren(FacesContext context, UIComponent component)
	throws IOException {
		UIContextMenu menu = (UIContextMenu) component;
		ResponseWriter writer = context.getResponseWriter();
		
		String event = menu.getEvent();
		
		if (event == null) {
			throw new FacesException("Attribute 'event' is not set for component " + component.getClientId(context));
		}
		
		writer.startElement("script", component);
		getUtils().writeAttribute(writer, "type", "text/javascript" );
		writer.writeText("var contextMenu = new Richfaces.ContextMenu('", null);
		writer.writeText(component.getClientId(context), null);
		writer.writeText("', ", null);
		writer.writeText(menu.getShowDelay() + ", ", null);
		writeScriptBody(context, component, true);
		writer.writeText(")", null);
		writer.writeText(";", null);
		writer.writeText(getClientAttachmentOptions(context, menu), null);
		
		if (menu.isDisableDefaultMenu()) {
			writer.writeText("Richfaces.disableDefaultHandler('", null);
			writer.writeText(event, null);
			writer.writeText("');", null);
		}
		
		writer.endElement("script");
	}

	private String getClientAttachmentOptions(FacesContext context, UIContextMenu contextMenu) {
	    // if contextMenu is not attached (it can be called by JS API)
	    if(!contextMenu.isAttached()) {
		return "";
	    }

	    String attachTo = contextMenu.getAttachTo();
	    String attachTiming = contextMenu.getAttachTiming();

	    boolean isImmediate = attachTiming.equals(IMMEDIATE);
	    boolean isOnLoad = attachTiming.equals(ON_LOAD);
	    boolean isOnAvailable = attachTiming.equals(ON_AVAILABLE);

	    if (!(isImmediate || isOnLoad || isOnAvailable)) {
		// unknown value of property "attachTiming"
		return "";
	    }

	    String pattern = "\\s*,\\s*";
	    // "attachTo" attribute may contain several ids split by ","
	    List<String> attachToIds = new ArrayList<String>();
	    String[] splitAttachTo = attachTo.split(pattern);
	    for (String tempId : splitAttachTo) {
		if(tempId.length() > 0) {
		    attachToIds.add(tempId);
		}
	    }

	    String baseJSFucntionName = "contextMenu.attachToElementById";
	    
	    // if attribute "attachTo" is not defined,
	    // attach contextMenu to the parent component
	    if(attachToIds.size() == 0) {
		UIComponent parentComponent = contextMenu.getParent();
		String clientId = parentComponent.getClientId(context);
		if (clientId != null) {
		    attachToIds.add(clientId);
		    baseJSFucntionName = "contextMenu.attachToParent";
		}
	    }

	    StringBuilder attachContextMenuBuffer = new StringBuilder();

	    // collect parameters
	    Map<String, Object> params = new LinkedHashMap<String, Object>();
	    for (UIComponent kid : contextMenu.getChildren()) {
		if (kid instanceof UIParameter) {
		    UIParameter parameter = (UIParameter) kid;
		    String name = parameter.getName();
		    Object value = parameter.getValue();

		    if ((parameter instanceof JavaScriptParameter) && ((JavaScriptParameter) parameter).isNoEscape()) {
			value = new JSReference(String.valueOf(value));
		    }

		    params.put(name, value);
		}
	    }

	    for (String attachToId : attachToIds) {
		JSFunction attachContextMenuFunction = new JSFunction(baseJSFucntionName);
		UIComponent target = RendererUtils.getInstance().findComponentFor(context, contextMenu, attachToId);
		String clientId = (target != null) ? target.getClientId(context) : attachToId;			

		attachContextMenuFunction.addParameter(clientId);
		attachContextMenuFunction.addParameter(contextMenu.getEvent());
		attachContextMenuFunction.addParameter(params);

		if (isImmediate) {
		    attachContextMenuBuffer.append(attachContextMenuFunction.toScript());
		} else {
			if (isOnAvailable) {
				JSFunction availableFunction = new JSFunction("Richfaces.onAvailable");
			    availableFunction.addParameter(clientId);
				availableFunction.addParameter(new JSBind(attachContextMenuFunction, "contextMenu"));
			    attachContextMenuBuffer.append(availableFunction.toScript());
			    
			} else if (isOnLoad) {
			    JSFunction onloadFunction = new JSFunction("jQuery(document).ready");
			    onloadFunction.addParameter(new JSBind(attachContextMenuFunction, "contextMenu"));

			    attachContextMenuBuffer.append(onloadFunction.toScript());
			}
		}

		attachContextMenuBuffer.append(";");
	    }

	    return attachContextMenuBuffer.toString();
	}
	
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		writer.endElement(HTML.DIV_ELEM);
	}
	
	protected ContentHandler createContentHandler(Writer writer) {
		return new ContextMenuContentHandler(writer, "Richfaces.evalMacro(\"", "\", context)");
	}
}
