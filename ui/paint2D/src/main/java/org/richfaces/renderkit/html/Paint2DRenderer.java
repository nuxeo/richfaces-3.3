/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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
import java.util.Collections;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIPaint2D;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 10.01.2007
 * 
 */
public class Paint2DRenderer extends RendererBase {

	protected Class getComponentClass() {
		return UIPaint2D.class;
	}

	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		String resourceClassName = ((UIPaint2D) component).isCacheable() ? Paint2DCachedResource.class.getName() : Paint2DResource.class.getName();
		InternetResource image = getResource(resourceClassName);
		image.encodeBegin(context, component, Collections.EMPTY_MAP);
		
		// fix for bug CH-1445
		String style = (String) component.getAttributes().get("style");
		if (null != style) writer.writeAttribute("style", style, null);
		
		String styleClass = (String) component.getAttributes().get("styleClass");
		String richPaint2DClass = "rich-paint2D";
		if (null != styleClass) {
			writer.writeAttribute("class", richPaint2DClass + " " + styleClass, null);
		} else {
			writer.writeAttribute("class", richPaint2DClass, null);
		}
	
		String hspace = (String) component.getAttributes().get("hspace");
		if (null != hspace) writer.writeAttribute("hspace", hspace, null);
		String vspace = (String) component.getAttributes().get("vspace");
		if (null != vspace) writer.writeAttribute("vspace", vspace, null);
		
		getUtils().encodeCustomId(context, component);
		getUtils().encodePassThru(context, component);
		image.encodeEnd(context, component);		
	}

}
