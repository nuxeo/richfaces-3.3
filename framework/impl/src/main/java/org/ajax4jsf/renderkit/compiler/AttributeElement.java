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

import javax.faces.FacesException;

import org.ajax4jsf.Messages;
import org.xml.sax.SAXException;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:44 $
 *
 */
public class AttributeElement extends ElementBase {

	private String name;
	private boolean mandatory = true;
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(TemplateContext context) throws IOException {
		String attributeValue = getString(context);
		if(attributeValue.length()>0 || isMandatory()){
			context.getWriter().writeAttribute(getName(),attributeValue,null);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		throw new FacesException(Messages.getMessage(Messages.BREAKPOINTS_UNSUPPORTED_INSIDE_ATTRIBUTE_ERROR, getName(), context.getComponent().getId()));
	}


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
	/**
	 * @return Returns the notNull.
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	/**
	 * Alias ( due to typo error ) for Mandatory property.
	 * @param notNull The notNull to set.
	 */
	public void setMantadory(boolean notNull) {
		this.mandatory = notNull;
	}

	/**
	 * @param notNull The notNull to set.
	 */
	public void setMandatory(boolean notNull) {
		this.mandatory = notNull;
	}

	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.ATTRIBUTE_TAG;
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {		
		super.setParent(parent);
		if (getName()==null) {
			throw new SAXException(Messages.getMessage(Messages.NO_NAME_ATTRIBUTE_ERROR, getTag()));
		}
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getAllowedClasses()
	 */
	protected Class[] getAllowedClasses() {
		// TODO Auto-generated method stub
		return new Class[]{
				MethodCallElement.class,
				TextElement.class,
				ResourceElement.class,
				ClassElement.class				
		};
	}
	
}
