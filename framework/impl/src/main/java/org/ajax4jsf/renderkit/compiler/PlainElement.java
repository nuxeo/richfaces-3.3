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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import org.xml.sax.Attributes;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:49 $
 *
 */
public class PlainElement extends ElementBase {


	private String namespace = "";
	private String tag = null;
	
	private String[][] attrs = null;
	
	private boolean encodeStart = true;

	private boolean encodeEnd = true;
	/**
	 * @param attrs
	 */
	public PlainElement(String namespace, String tag, Attributes attrs) {
		// Set modifable attributes implementation to ability of
		// modifications.
		this.attrs = new String[attrs.getLength()][2];
		this.namespace = namespace;
		this.tag = tag;
		int currentAttribute = 0;
		// remove non-passed attributes, parse special.
		for (int i = 0; i < attrs.getLength(); i++) {
			String qName = attrs.getQName(i); 
			String value = attrs.getValue(i);
			if (qName.equals(HtmlCompiler.NS_PREFIX+"start")) {
				this.encodeStart = "true".equals(value);
			} else if (qName.equals(HtmlCompiler.NS_PREFIX+"end")) {
				this.encodeEnd = "true".equals(value);
			} else {
				this.attrs[currentAttribute][0]=qName;
				this.attrs[currentAttribute++][1]=value;
			}
			
		}
		// squize array if nessesary.
		if (currentAttribute < attrs.getLength()) {
			String[][] newattrs = new String[currentAttribute][2];
			System.arraycopy(this.attrs,0,newattrs,0,currentAttribute);
			this.attrs = newattrs;
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.CompiledXML#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(TemplateContext context) throws IOException {
		ResponseWriter writer = context.getWriter();
		if (this.encodeStart) {
			writer.startElement(getTag(), context.getComponent());
			// write attributes
			for (int i = 0; i < attrs.length; i++) {
				writer.writeAttribute(attrs[i][0], attrs[i][1], null);
			}
		}
	}

	public void encodeEnd(TemplateContext context) throws IOException {
		ResponseWriter writer = context.getWriter();

		if (this.encodeEnd) {
			writer.endElement(getTag());
		}

	}

	/**
	 * @return Returns the tag.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag The tag to set.
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return Returns the attrs.
	 */
	public String[][] getAttrs() {
		return attrs;
	}

	/**
	 * @return Returns the encodeEnd.
	 */
	public boolean isEncodeEnd() {
		return encodeEnd;
	}

	/**
	 * @param encodeEnd The encodeEnd to set.
	 */
	public void setEncodeEnd(boolean encodeEnd) {
		this.encodeEnd = encodeEnd;
	}

	/**
	 * @return Returns the encodeStart.
	 */
	public boolean isEncodeStart() {
		return encodeStart;
	}

	/**
	 * @param encodeStart The encodeStart to set.
	 */
	public void setEncodeStart(boolean encodeStart) {
		this.encodeStart = encodeStart;
	}

	/**
	 * @return Returns the namespace.
	 */
	public String getNamespace() {
		return namespace;
	}

}
