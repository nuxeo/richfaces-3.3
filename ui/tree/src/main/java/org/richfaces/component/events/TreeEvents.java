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

package org.richfaces.component.events;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.richfaces.event.AjaxExpandedEvent;
import org.richfaces.event.AjaxSelectedEvent;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.event.TreeAjaxEvent;
import org.richfaces.event.TreeAjaxEventType;
import org.richfaces.event.TreeListenerEventsProducer;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com created 29.11.2006
 * Utility class to invoke event listener bindings of {@link TreeListenerEventsProducer} instances
 */
public class TreeEvents {
	/**
	 * Invoke listener bindings 
	 * @param eventsProducer bindings source
	 * @param event event to pass as argument
	 * @param context faces context
	 * @throws AbortProcessingException
	 */
	public static void invokeListenerBindings(TreeListenerEventsProducer eventsProducer,
			FacesEvent event, FacesContext context)
			throws AbortProcessingException {
		MethodBinding binding = null;

		UIComponent component = event.getComponent();
		if (event instanceof NodeExpandedEvent) {
			binding = eventsProducer.getChangeExpandListener();
			if (event instanceof AjaxExpandedEvent) {
				new TreeAjaxEvent(component, TreeAjaxEventType.EXPANSION).queue();
			}
		} else if (event instanceof AjaxSelectedEvent) {
			if (eventsProducer.hasAjaxSubmitSelection()) {
				binding = eventsProducer.getNodeSelectListener();
				new TreeAjaxEvent(component, TreeAjaxEventType.SELECTION).queue();
			}
		} else if (event instanceof NodeSelectedEvent) {
			binding = eventsProducer.getNodeSelectListener();
		} else if (event instanceof DropEvent) {
			binding = eventsProducer.getDropListener();
		} else if (event instanceof DragEvent) {
			binding = eventsProducer.getDragListener();
		}
		
		if (binding != null) {
			binding.invoke(context, new Object[] { event });
		}
	}
}
