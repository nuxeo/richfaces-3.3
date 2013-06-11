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

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/12 17:46:53 $
 * 
 */
public abstract class AjaxCommandRendererBase extends AjaxComponentRendererBase {

	private static final Log _log = LogFactory
			.getLog(AjaxCommandRendererBase.class);

	protected void doDecode(FacesContext facesContext, UIComponent uiComponent) {

		// super.decode must not be called, because value is handled here
		if (isSubmitted(facesContext, uiComponent)) {
			ActionEvent event;
			event = new ActionEvent(uiComponent);
			uiComponent.queueEvent(event);
			uiComponent.queueEvent(new AjaxEvent(uiComponent));
			// Check areas for processing
			if (uiComponent instanceof AjaxComponent) {
				AjaxComponent ajaxComponent = (AjaxComponent) uiComponent;
				Set<String> toProcess = AjaxRendererUtils.asSet(ajaxComponent
						.getProcess());
				if (null != toProcess) {
					HashSet<String> componentIdsToProcess = new HashSet<String>();
					for (String componentId : toProcess) {
						UIComponent component = getUtils().findComponentFor(uiComponent, componentId);
						if(null != component){
							componentIdsToProcess.add(component.getClientId(facesContext));
						} else {
							componentIdsToProcess.add(componentId);
						}
					}
					AjaxContext.getCurrentInstance(facesContext).setAjaxAreasToProcess(componentIdsToProcess);
				}
			}
		}
	}

	public String getOnClick(FacesContext context, UIComponent component) {
		StringBuffer onClick;
		if (!getUtils().isBooleanAttribute(component, "disabled")) {
			onClick = AjaxRendererUtils.buildOnClick(component, context);
			if (!"reset".equals(component.getAttributes().get("type"))) {
				onClick.append(";return false;");
			}
		} else {
			onClick = new StringBuffer("return false;");
		}
		return onClick.toString();
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		renderChildren(context, component);
	}

	public Object getValue(UIComponent uiComponent) {
		if (uiComponent instanceof ValueHolder) {
			return ((ValueHolder) uiComponent).getValue();
		}
		return uiComponent.getAttributes().get("value");
	}

	public String getType(UIComponent uiComponent) {
		String type;
		if (uiComponent instanceof HtmlCommandButton) {
			type = ((HtmlCommandButton) uiComponent).getType();
		} else {
			type = (String) uiComponent.getAttributes().get("type");
		}
		if (type == null) {
			type = "button";
		}
		return type;
	}

	protected boolean isSubmitted(FacesContext facesContext,
			UIComponent uiComponent) {
		// Componet accept only ajax requests.
		if (!AjaxContext.getCurrentInstance(facesContext).isAjaxRequest()) {
			return false;
		}
		if (getUtils().isBooleanAttribute(uiComponent, "disabled")) {
			return false;
		}
		String clientId = uiComponent.getClientId(facesContext);
		Map<String, String> paramMap = facesContext.getExternalContext()
				.getRequestParameterMap();
		Object value = paramMap.get(clientId);
		boolean submitted = null != value;
		if (submitted && _log.isDebugEnabled()) {
			_log.debug("Decode submit of the Ajax component " + clientId);
		}
		return submitted;
	}

}
