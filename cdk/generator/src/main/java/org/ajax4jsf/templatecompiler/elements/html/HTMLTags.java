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

package org.ajax4jsf.templatecompiler.elements.html;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.richfaces.dtd.DocumentDefinition;
import org.richfaces.dtd.DocumentDefinitionFactory;
import org.richfaces.dtd.Element;


/**
 * @author yukhovich
 * @author Maksim Kaszynski
 */
public class HTMLTags {
	/**
	 * 
	 */
	
	private static final String HTML_SCHEMA = "META-INF/schema/html/xhtml1-transitional.dtd";
	
	private static final URL HTML_DTD = HTMLTags.class.getClassLoader().getResource(HTML_SCHEMA);
	
	@SuppressWarnings("unchecked")
	public static Set<String> getAttributes(String tagName) {
		Set<String> atrs = Collections.emptySet();
		
		DocumentDefinition dtd = 
			DocumentDefinitionFactory.instance().getDocumentDefinition(HTML_DTD);
		
		if (dtd != null) {
			Element element = dtd.getElement(tagName);

			if (element != null) {
				atrs = element.getAttributes().keySet();
			}
		}
		
		return atrs;
		
	}

	/**
	 * Look for tags.bin, and compare with it
	 * @param args
	 * @throws Exception
	 */
	public static void main(String [] args) throws Exception{
		InputStream stream = HTMLTags.class.getClassLoader().getResourceAsStream("META-INF/templates/templatecompiler/tags.bin");
		
		ObjectInputStream stream2 = new ObjectInputStream(stream);
		
		@SuppressWarnings("unchecked")
		Map<String, List<String>> m = 
			(Map<String, List<String>>) stream2.readObject();
		
		Set<String> newAttrs = new TreeSet<String>();
		
		Set<Entry<String,List<String>>> entrySet = m.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			String element = entry.getKey();
			
			Set<String> attributes = HTMLTags.getAttributes(element);
			List<String> attributeList = entry.getValue();
			
			if (attributeList != null && attributes != null) {
				attributes.removeAll(attributeList);
			}
			newAttrs.addAll(attributes);
			
			System.out.println(element + attributes);
			
		}
		
		System.out.println(newAttrs);
	}

	
}
