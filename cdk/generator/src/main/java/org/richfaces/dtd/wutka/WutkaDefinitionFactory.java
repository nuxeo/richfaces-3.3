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

package org.richfaces.dtd.wutka;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.dtd.Attribute;
import org.richfaces.dtd.DocumentDefinition;
import org.richfaces.dtd.DocumentDefinitionFactory;
import org.richfaces.dtd.Element;

import com.wutka.dtd.DTD;
import com.wutka.dtd.DTDAttribute;
import com.wutka.dtd.DTDElement;
import com.wutka.dtd.DTDParser;

/**
 * @author Maksim Kaszynski
 *
 */
public class WutkaDefinitionFactory extends DocumentDefinitionFactory{
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	private Map<URL, DocumentDefinition> definitions 
		= new HashMap<URL, DocumentDefinition>();
	
	@Override
	public synchronized DocumentDefinition getDocumentDefinition(URL resource) {
		DocumentDefinition def = null;
		if (definitions.containsKey(resource)) {
			def = definitions.get(resource);
		} else {
			
			try {
				def = initDefinition(resource);
			} catch(Exception e) {
				log.error("An error has occured", e);
			}
			
			if (def != null) {
				definitions.put(resource, def);
			}
		}
		return def;
	}
	
	
	private DocumentDefinition initDefinition(URL resource) throws Exception{

		DTD dtd = new DTDParser(resource).parse();
		
		Element rootElement = fromWutka(dtd.rootElement);
		
		DocumentDefinition definition = new DocumentDefinition(resource, rootElement);
		
		@SuppressWarnings("unchecked")
		Enumeration<DTDElement> elements = dtd.elements.elements();
		
		while(elements.hasMoreElements()) {
			DTDElement element = elements.nextElement();
			
			definition.addElement(fromWutka(element));
		}
		
		return definition;
	}
	
	private Attribute fromWutka(DTDAttribute attr) {
		return new Attribute(attr.name);
	}
	
	private Element fromWutka(DTDElement element) {
		
		if (element == null) {
			return null;
		}
		
		Element e = new Element(element.getName());

		@SuppressWarnings("unchecked")
		Enumeration<DTDAttribute> attrs = 
			element.attributes.elements();
		
		while(attrs.hasMoreElements()) {
			e.addAttribute(fromWutka(attrs.nextElement()));
		}
	
		return e;
	}
}
