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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;

/**
 * @author Maksim Kaszynski
 *
 */
public abstract class SortIconRenderer extends RendererBase {
	
	
	public void renderAscIcon(FacesContext context, UIComponent component) throws IOException{
		renderFacetOrIcon(context, component, "ascIcon", "dr-sdt-sort-asc");
	}

	public void renderDescIcon(FacesContext context, UIComponent component) throws IOException{
		renderFacetOrIcon(context, component, "descIcon", "dr-sdt-sort-desc");
	}
	
	private void renderFacetOrIcon(FacesContext context, UIComponent component, String facetName, String clazz) throws IOException{
		UIComponent facet = component.getFacet(facetName);
		if (facet != null) {
			renderChild(context, facet);
		} else {
			renderIcon(context.getResponseWriter(), clazz);
		}
	}
	
	private void renderIcon(ResponseWriter writer, String clazz) throws IOException {
		writer.startElement(HTML.DIV_ELEM, null);
		if (clazz != null) {
			writer.writeAttribute(HTML.class_ATTRIBUTE, clazz, null);
		}
		writer.endElement(HTML.DIV_ELEM);
	}
}
