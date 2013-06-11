/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.dtd;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maksim Kaszynski
 *
 */
public class Element extends Node{

	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	public Element(String name) {
		super(name);
	}
	
	public void addAttribute(Attribute attribute) {
		attributes.put(attribute.getName(), attribute);
	}
	
	public Map<String, Attribute> getAttributes() {
		return attributes;
	}


}
