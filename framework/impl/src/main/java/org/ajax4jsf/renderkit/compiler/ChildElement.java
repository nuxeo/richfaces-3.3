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
import javax.faces.component.UIComponent;

import org.ajax4jsf.Messages;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:43 $
 *
 */
public class ChildElement extends ElementBase {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		throw new FacesException(Messages.getMessage(Messages.BREAKPOINTS_UNSUPPORTED_ERROR_3));
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		UIComponent child = (UIComponent) context.getParameter(ChildrensElement.CURRENT_CHILD);
		if(null == child){
			child = (UIComponent) getValue(context);
		}
		if(null !=child && child.isRendered()){
			context.getRenderer().renderChild(context.getFacesContext(),child);
		}
	}

	public String getTag() {
		return HtmlCompiler.NS_UTIL_PREFIX+HtmlCompiler.CHILD_TAG;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#getAllowedClasses()
	 */
	protected Class[] getAllowedClasses() {
		// child elements not allowed
		return new Class[]{};
	}

}
