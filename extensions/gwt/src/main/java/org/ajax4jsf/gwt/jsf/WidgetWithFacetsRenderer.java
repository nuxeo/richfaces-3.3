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

package org.ajax4jsf.gwt.jsf;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author shura
 *
 */
public class WidgetWithFacetsRenderer extends WidgetRenderer {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.gwt.jsf.WidgetRenderer#encodeFacets(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	protected void encodeFacets(ResponseWriter writer, FacesContext context, UIComponent component, String element) throws IOException {
		String clientId = component.getClientId(context);
		for (Iterator iter = component.getFacets().entrySet().iterator(); iter.hasNext();) {
			Map.Entry facet = (Map.Entry) iter.next();
			String facetName = (String) facet.getKey();
			UIComponent value = (UIComponent) facet.getValue();
			writer.startElement(element,value);
			writer.writeAttribute("id",clientId+NamingContainer.SEPARATOR_CHAR+facetName,null);
			writer.writeAttribute("style","display:none",null);
			value.encodeAll(context);
			writer.endElement(element);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		if (component.getChildCount()>0) {
			String clientId = component.getClientId(context);
			String element = getElement(component);
			ResponseWriter writer = context.getResponseWriter();
			writer.startElement(element, component);
			writer.writeAttribute("id", clientId
					+ NamingContainer.SEPARATOR_CHAR + "_children", null);
			for (Iterator iter = component.getChildren().iterator(); iter.hasNext();) {
				UIComponent child = (UIComponent) iter.next();
				child.encodeAll(context);
			}
			writer.endElement(element);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}


}
