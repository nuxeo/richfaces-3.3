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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.renderkit.MacroDefinitionJSContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Maksim Kaszynski
 *
 */
public class ContextMenuContentHandler extends MacroDefinitionJSContentHandler {

	
	private static final Set INTERPOLATED_ATTRIBUTES = new HashSet();
	
	static {
		String[] attrs = {
				HTML.onblur_ATTRIBUTE,
				HTML.onchange_ATTRIBUTE,
				HTML.onclick_ATTRIBUTE,
				HTML.onfocus_ATTRIBUTE,
				HTML.onkeydown_ATTRIBUTE,
				HTML.onkeypress_ATTRIBUTE,
				HTML.onkeyup_ATTRIBUTE,
				HTML.onmousedown_ATTRIBUTE,
				HTML.onmousemove_ATTRIBUTE,
				HTML.onmouseout_ATTRIBUTE,
				HTML.onmouseover_ATTRIBUTE,
				HTML.onmouseup_ATTRIBUTE,
				HTML.onselect_ATTRIBUTE
			};
		
		for (int i = 0; i < attrs.length; i++) {
			INTERPOLATED_ATTRIBUTES.add(attrs[i]);
		}
	}

	private Lifo elementStack = new Lifo();
	
	
	/**
	 * @param writer
	 * @param prolog
	 * @param epilog
	 */
	public ContextMenuContentHandler(Writer writer, String prolog, String epilog) {
		super(writer, prolog, epilog);
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		elementStack.push(localName);
		super.startElement(uri, localName, name, attributes);
	}
	
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		elementStack.pop();
		super.endElement(uri, localName, name);
	}
	
	protected List parseExpressiion(String expressionString)
			throws SAXException {
		
		if (HTML.SCRIPT_ELEM.equals(elementStack.peek())) {
			return Collections.singletonList(expressionString);
		} else {
			return super.parseExpressiion(expressionString);
		}
	}
	

	protected void encodeAttributeValue(Attributes attributes, int idx)
			throws SAXException, IOException {
		
		if (INTERPOLATED_ATTRIBUTES.contains(attributes.getQName(idx))) {
			outputWriter.write("function (context) { return Richfaces.interpolate(\"");
			
			String value = attributes.getValue(idx);
			outputWriter.write(value);
			outputWriter.write("\", context);}");
		} else {
			super.encodeAttributeValue(attributes, idx);
		}
		
	}
}
