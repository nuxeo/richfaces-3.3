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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.RendererBase;
import org.xml.sax.SAXException;


public class MethodParameterElement extends ElementBase {

	/**
	 * 
	 */
	public MethodParameterElement() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(RendererBase renderer, FacesContext context, UIComponent component) throws IOException {
		// DO Nothing !
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		// DO Nothing !
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		// DO Nothing !
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#addChild(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void addChild(PreparedTemplate child) throws SAXException {
		// TODO Auto-generated method stub
		super.addChild(child);
		if (child instanceof ResourceElement) {
			final ResourceElement res = (ResourceElement) child;
			this.valueGetter = new ValueGetter(){

				/* (non-Javadoc)
				 * @see org.ajax4jsf.renderkit.compiler.ElementBase.ValueGetter#getValue(org.ajax4jsf.renderkit.compiler.TemplateContext)
				 */
				Object getValue(TemplateContext context) {
					return context.getRenderer().getResource(res.getValue(context).toString());
				}
				
			};
		}
	}

	public String getTag() {
		// TODO Auto-generated method stub
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.CALL_PARAM_TAG;
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getAllowedClasses()
	 */
	protected Class[] getAllowedClasses() {
		// TODO Auto-generated method stub
		return new Class[]{
				ResourceElement.class
		};
	}

	
}
