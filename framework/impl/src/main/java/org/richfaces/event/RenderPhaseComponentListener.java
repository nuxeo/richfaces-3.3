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
package org.richfaces.event;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.richfaces.util.RenderPhaseComponentVisitorUtils;

public class RenderPhaseComponentListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2437433246178955788L;

	public void afterPhase(PhaseEvent event) {
	}
	
	private void processComponents(PhaseEvent event, UIComponent component,
			RenderPhaseComponentVisitor[] handlers, Object[] states) {
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].beforeComponent(component, event, states[i]);
		}
		Collection<UIComponent> facets = component.getFacets().values();
		for (UIComponent facet : facets) {
			processComponents(event, facet, handlers, states);
		}
		Collection<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {
			processComponents(event, child, handlers, states);
		}
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].afterComponent(component, event, states[i]);
		}
	}

	public void beforePhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		RenderPhaseComponentVisitor[] handlers = RenderPhaseComponentVisitorUtils.getVisitors(facesContext);
		UIViewRoot viewRoot = facesContext.getViewRoot();
		if (viewRoot != null && handlers != null) {
			Object[] states = new Object[handlers.length];
			for (int i = 0; i < handlers.length; i++) {
				states[i] = handlers[i].beforeRoot(event);
			}
			processComponents(event, viewRoot, handlers, states);
			for (int i = 0; i < handlers.length; i++) {
				handlers[i].afterRoot(event, states[i]);
			}
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
