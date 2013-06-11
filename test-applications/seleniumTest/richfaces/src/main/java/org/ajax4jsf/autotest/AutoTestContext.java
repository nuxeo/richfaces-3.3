/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.autotest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoTestContext {
	
	String component;
	
	List<String> events = new ArrayList<String>();
	
	Map<String, String> attributes = new HashMap<String, String>();
	
	String rendered;
	
	public AutoTestContext(String component) {
		this.component = component;
	}
	
	public void addEvent(String ev) {
		events.add(ev);
	}
	
	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * @return the events
	 */
	public List<String> getEvents() {
		return events;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @return the rendered
	 */
	public String getRendered() {
		return rendered;
	}

}
