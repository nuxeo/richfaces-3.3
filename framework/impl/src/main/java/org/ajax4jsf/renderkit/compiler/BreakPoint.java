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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.renderkit.RendererBase;
import org.xml.sax.SAXException;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:45 $
 *
 */
public class BreakPoint implements PreparedTemplate {
	
	private List childrens = new ArrayList();

	private PreparedTemplate _parent;
	private String name;

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#encode(org.ajax4jsf.renderkit.RendererBase, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(RendererBase renderer, FacesContext context,
			UIComponent component) throws IOException {
		throw new BreakException(getName());

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		throw new BreakException(getName());

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint)
			throws IOException {
		if (!getName().equals(breakPoint)) {
			throw new BreakException(getName());
		} else if(getChildren().size()>0){
			// encode all childrens of this breakpoint.
			for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
				PreparedTemplate element = (PreparedTemplate) iter.next();
				element.encode(context);
			}
			// after childrens, break encoding.
			throw new BreakException(getName());
		}
		// if no childrens - continue encoding.
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#getChildren()
	 */
	public List getChildren() {
		// breakpoint - empty element
		return childrens;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#addChild(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void addChild(PreparedTemplate child) throws SAXException {
		child.setParent(this);
		childrens.add(child);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {
		if (getName()==null) {
			throw new SAXException(Messages.getMessage(Messages.NO_NAME_ATTRIBUTE_ERROR, getTag()));
		}
		this._parent = parent;
		if (parent instanceof ElementBase) {
			((ElementBase) parent).addBreakPoint(getName());
		}
	}

	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.BREAK_TAG;
	}

	public Object getValue(TemplateContext context) {
		// TODO Auto-generated method stub
		return null;
	}


}
