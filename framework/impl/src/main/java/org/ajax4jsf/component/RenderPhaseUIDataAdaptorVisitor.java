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
package org.ajax4jsf.component;

import javax.faces.component.UIComponent;
import javax.faces.event.PhaseEvent;

import org.richfaces.event.RenderPhaseComponentVisitor;

public class RenderPhaseUIDataAdaptorVisitor implements
		RenderPhaseComponentVisitor {

	/* (non-Javadoc)
	 * @see org.richfaces.event.ComponentPhaseEventHandler#beforePhaseBegin(javax.faces.event.PhaseEvent)
	 */
	public Object beforeRoot(PhaseEvent event) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.event.ComponentPhaseEventHandler#componentBegin(javax.faces.component.UIComponent, javax.faces.event.PhaseEvent, java.lang.Object)
	 */
	public void beforeComponent(UIComponent component, PhaseEvent event,
			Object state) {
		if (component instanceof UIDataAdaptor) {
			((UIDataAdaptor) component).beforeRenderResponse(event.getFacesContext());
			
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.event.ComponentPhaseEventHandler#bcomponentEnd(javax.faces.component.UIComponent, javax.faces.event.PhaseEvent, java.lang.Object)
	 */
	public void afterComponent(UIComponent component, PhaseEvent event,
			Object state) {}
	
	/* (non-Javadoc)
	 * @see org.richfaces.event.ComponentPhaseEventHandler#beforePhaseEnd(javax.faces.event.PhaseEvent, java.lang.Object)
	 */
	public void afterRoot(PhaseEvent event, Object state) {}
}
