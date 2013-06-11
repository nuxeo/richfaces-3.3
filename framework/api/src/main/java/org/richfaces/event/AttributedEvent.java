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

package org.richfaces.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;

/**
 * Base class for events carrying component attributes.
 * By using it, renderer-specific attributes can 
 * be applied when event is broadcast
 * @author Maksim Kaszynski
 *
 */
public abstract class AttributedEvent extends FacesEvent implements AttributeHolder{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public AttributedEvent(UIComponent component) {
		super(component);
	}

	public AttributedEvent(UIComponent component, Map<String, Object> attributes) {
		super(component);
		this.attributes.putAll(attributes);
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.event.AttributeHolder#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.event.AttributeHolder#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return attributes.get(name);
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.event.AttributeHolder#applyAttributes(javax.faces.component.UIComponent)
	 */
	public void applyAttributes(UIComponent component) {
		
		Map<String, Object> attrs = component.getAttributes();
		
		for(Iterator<Map.Entry<String, Object>> iterator = attributes.entrySet().iterator(); 
			iterator.hasNext(); ) {
			
			Map.Entry<String, Object> entry = iterator.next();
		
			String key  = entry.getKey();
			Object value = entry.getValue();
			
			if (value == null) {
				attrs.remove(key);
			} else {
				attrs.put(key, value);
			}
		}
	}
	
	/**
	 * copy attributes to event source
	 */
	public void applyAttributes() {
		applyAttributes(getComponent());
	}
 	
}
