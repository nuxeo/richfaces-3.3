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

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:44 $
 *
 */
public class TextElement extends ElementBase {
	
	private String text = null;
	
	private boolean escape = false;

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encode(TemplateContext context) throws IOException {
		// TODO Auto-generated method stub
		String textValue = getString(context);
		
		if (isEscape()) {
			context.getWriter().writeText(textValue, null);
		} else {
			context.getWriter().write(textValue.toString());
		}
	}

	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		// Text not contain breakpoints.
		encode(context);
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getString(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public String getString(TemplateContext context) throws FacesException {
		
		String textValue = super.getString(context);
		// If value not set - return element body.
		if (textValue.length()==0 && null != getText()) {
			textValue = getText();
		} 
		return textValue;
	}


	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return Returns the escape.
	 */
	public boolean isEscape() {
		return escape;
	}

	/**
	 * @param escape The escape to set.
	 */
	public void setEscape(boolean escape) {
		this.escape = escape;
	}


	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.VERBATUM_TAG;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getAllowedClasses()
	 */
	protected Class[] getAllowedClasses() {
		// TODO Auto-generated method stub
		return new Class[]{
				MethodCallElement.class,
				ResourceElement.class,
				ClassElement.class				
		};
	}


}
