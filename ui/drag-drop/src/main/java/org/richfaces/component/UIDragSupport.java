/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.event.AjaxEvent;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DragListener;

/**
 * JSF component class
 *
 */
public abstract class UIDragSupport extends AjaxActionComponent implements Draggable {
	
	private static final String COMPONENT_TYPE = "org.richfaces.DragSupport";
	
	private static final String COMPONENT_FAMILY = "org.richfaces.DragSupport";

	public void addDragListener(DragListener listener) {
		addFacesListener(listener);
	}

	public void removeDragListener(DragListener listener) {
		removeFacesListener(listener);
	}

	public DragListener[] getDragListeners() {
		return (DragListener[]) getFacesListeners(DragListener.class);
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		if (event instanceof DragEvent) {
			DragEvent dragEvent = (DragEvent) event;
			MethodBinding binding = getDragListener();
			if (binding != null) {
				binding.invoke(getFacesContext(), new Object[] {event});
			}

			new AjaxEvent(this).queue();
			new ActionEvent(this).queue();
		}
	}

	public void queueEvent(FacesEvent event) {
		if (event instanceof DragEvent) {
			if (isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else if (isBypassUpdates()) {
				event.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
			} else {
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}
		}
		super.queueEvent(event);
	}
	
	public String getResolvedDragIndicator(FacesContext facesContext) {
		return null;
	}
	
}
