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
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:48 $
 *
 */
public class FacetElement extends ElementBase {

	public static final String CURRENT_FACET = "child_to_render";
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		String facetName = getValue(context).toString();
		if (null == facetName) {
			throw new FacesException(Messages.getMessage(Messages.NO_FACET_NAME_ATTRIBUTE, getTag()));
		}
		UIComponent stored = (UIComponent) context
			.getParameter(CURRENT_FACET);
		// if inside childrens loop - render facet from current iteration component.
		// TODO - use cloned context
		UIComponent facet = null == stored?context.getComponent().getFacet(facetName):stored.getFacet(facetName);		
		if (null != facet && facet.isRendered()) {
			if (getChildren().size()>0) {
				// Store facet to render in <u:child> element.
				// TODO - use cloned context.
				context.putParameter(CURRENT_FACET, facet);
				// In fact, render all childrens.
				super.encode(context);
				if (null != stored) {
					context.putParameter(CURRENT_FACET, stored);
				} else {
					context.removeParameter(CURRENT_FACET);
				}
			} else {
				// empty element - render facet as-is.
				context.getRenderer().renderChild(context.getFacesContext(),facet);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		// TODO Auto-generated method stub
		throw new FacesException(Messages.getMessage(Messages.BREAKPOINTS_UNSUPPORTED_ERROR, getValue(context)));
	}
	
	public void setName(Object name) {
		setValue(name);
	}

	public String getTag() {
		return HtmlCompiler.NS_UTIL_PREFIX+HtmlCompiler.FACET_TAG;
	}
	
	
}
