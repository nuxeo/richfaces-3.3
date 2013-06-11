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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maksim Kaszynski
 *
 */
public class DocumentDefinition {
	private Map<String, Element> elements = new HashMap<String, Element>();
	
	private URL url;
	
	private Element rootElement;
	
	public DocumentDefinition(URL url, Element rootElement) {
		super();
		this.url = url;
		this.rootElement = rootElement;
	}

	public void addElement(Element e) {
		elements.put(e.getName(), e);
	}
	
	public Element getElement(String name) {
		return elements.get(name);
	}

	public URL getUrl() {
		return url;
	}

	public Element getRootElement() {
		return rootElement;
	}

	
}
