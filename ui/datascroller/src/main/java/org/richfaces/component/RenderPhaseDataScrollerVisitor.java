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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.Expression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.ajax4jsf.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.RenderPhaseComponentVisitor;


/**
 * Created 08.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class RenderPhaseDataScrollerVisitor implements RenderPhaseComponentVisitor {

	private class State {
		
		private ComponentConnections connections;
		private LinkedList<UIComponent> components;
		
		private State(LinkedList<UIComponent> components,
				ComponentConnections connections) {
			this.components = components;
			this.connections = connections;
		}
	}
	
	private static final class ComponentConnections {
		private final Map<UIData, List<UIDatascroller>> map = 
			new HashMap<UIData, List<UIDatascroller>>();
		
		public void addConnection(UIData one, UIDatascroller multi) {
			List<UIDatascroller> list = map.get(one);
			if (list == null) {
				list = new ArrayList<UIDatascroller>(1);
				map.put(one, list);
			}
			
			list.add(multi);
		}
		
		public Iterator<Entry<UIData, List<UIDatascroller>>> iterator() {
			return map.entrySet().iterator();
		}
		
		@Override
		public String toString() {
			return this.getClass().getSimpleName() + ": " + map;
		}
	}
	
	private static final Log log = LogFactory.getLog(RenderPhaseDataScrollerVisitor.class);

	public Object beforeRoot(PhaseEvent event) {
		return new State(new LinkedList<UIComponent>(), new ComponentConnections());
	}

	public void beforeComponent(UIComponent component, PhaseEvent event, Object object) {
		State state = (State) object;
		state.components.addLast(component);

		if (component instanceof UIDatascroller && isRendered(state.components)) {
			UIDatascroller datascroller = (UIDatascroller) component;
			UIData dataTable = datascroller.getDataTable();

			state.connections.addConnection(dataTable, datascroller);
		}
	}

	public void afterComponent(UIComponent component, PhaseEvent event, Object object) {
		State state = (State) object;
		state.components.removeLast();
	}

	public void afterRoot(PhaseEvent event, Object object) {
		State state = (State) object;
		FacesContext facesContext = event.getFacesContext();
		Iterator<Entry<UIData, List<UIDatascroller>>> entries = 
			state.connections.iterator();

		while (entries.hasNext()) {
			Entry<UIData, List<UIDatascroller>> entry = entries.next();
			List<UIDatascroller> scrollers = entry.getValue();
			if (!scrollers.isEmpty()) {
				List<Object> values = new ArrayList<Object>(scrollers.size());
				
				UIData data = entry.getKey();
				UIDatascroller activeComponent = null;
				
				Map<String, Object> attributes = data.getAttributes();
				Object pageValue = attributes.get(UIDatascroller.SCROLLER_STATE_ATTRIBUTE);

				if (pageValue == null) {
					boolean valid = true;

					for (UIDatascroller datascroller : scrollers) {
						Object nextPageValue = null;

						if (datascroller.isLocalPageSet()) {
							nextPageValue = datascroller.getPage();
							attributes.put(UIDatascroller.SCROLLER_STATE_ATTRIBUTE, nextPageValue);
							datascroller.resetLocalPage();
						} else {
							nextPageValue = datascroller.getValueExpression("page");
						}

						if (!values.isEmpty() && !same(values.get(values.size() - 1), nextPageValue)) {
							valid = false;
						}
						
						values.add(nextPageValue);

						if (nextPageValue != null) {
							activeComponent = datascroller;
							pageValue = nextPageValue;
						} 
					}

					if (!valid) {
						StringBuilder builder = new StringBuilder("\n[");
						Iterator<UIDatascroller> scrollerItr = scrollers.iterator();
						Iterator<Object> valueItr = values.iterator();
						
						while (scrollerItr.hasNext()) {
							UIDatascroller next = scrollerItr.next();
							builder.append(MessageUtil.getLabel(facesContext, next));
							builder.append(": ");
							
							Object value = valueItr.next();
							if (value instanceof Expression) {
								builder.append(((Expression) value).getExpressionString());
							} else {
								builder.append(value);
							}
							
							builder.append(scrollerItr.hasNext() ? ",\n" : "]");
						}
						
						String formattedMessage = Messages.getMessage(Messages.DATASCROLLER_PAGES_DIFFERENT, 
								new Object[] {MessageUtil.getLabel(facesContext, data), builder});

						
						log.error(formattedMessage);
					}
					
				}

				if (activeComponent == null) {
					activeComponent = scrollers.get(scrollers.size() - 1);
				}

				if (pageValue != null) {
					activeComponent.setupFirstRowValue();
				}
			}
		}
	}

	private static boolean isRendered(List<UIComponent> components) {
		boolean rendered;
		
		for (UIComponent component : components) {
			rendered = false;

			try {
				rendered = component.isRendered();
			} catch (Exception e) {
				//ignore that
				if (log.isDebugEnabled()) {
					log.debug(e.getLocalizedMessage(), e);
				}
			}
			
			if (!rendered) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean same(Object o1, Object o2) {
		if (o1 instanceof ValueExpression && o2 instanceof ValueExpression) {
			ValueExpression ve1 = (ValueExpression) o1;
			ValueExpression ve2 = (ValueExpression) o2;
		
			if (same(ve1.getExpressionString(), ve2.getExpressionString()) && same(ve1.getExpectedType(), ve2.getExpectedType())) {
				return true;
			}
		}
		
		return (o1 != null && o1.equals(o2)) || (o1 == null && o2 == null);
	}
}
