/**
 * License Agreement.
 *
 * Ajax4jsf 1.1 - Natural Ajax for Java Server Faces (JSF)
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


// 
// Imports
//
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.renderkit.AjaxComponentRendererBase;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.richfaces.event.ValidationEvent;



/**
 * Renderer for component class org.richfaces.renderkit.html.BeanValidatorRenderer
 */
public class BeanValidatorRenderer extends AjaxComponentRendererBase {

	
	public static final String RENDERER_TYPE = "org.richfaces.BeanValidatorRenderer";
	public BeanValidatorRenderer () {
		super();
	}

	protected void doDecode(FacesContext facesContext, UIComponent uiComponent) {

		// super.decode must not be called, because value is handled here
		if (isSubmitted(facesContext, uiComponent)) {
			uiComponent.queueEvent(new ValidationEvent(uiComponent));
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

	protected boolean isSubmitted(FacesContext facesContext,
			UIComponent uiComponent) {
		// Componet accept only ajax requests.
		if (!AjaxContext.getCurrentInstance(facesContext).isAjaxRequest()) {
			return false;
		}
		String clientId = uiComponent.getClientId(facesContext);
		Map<String, String> paramMap = facesContext.getExternalContext()
				.getRequestParameterMap();
		String value = paramMap.get(clientId);
		boolean submitted = null != value;
		return submitted;
	}
	/**
	 * Get base component class, targetted for this renderer. Used for check arguments in decode/encode.
	 * @return
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return org.richfaces.component.UIBeanValidator.class;
	}		
	

}
