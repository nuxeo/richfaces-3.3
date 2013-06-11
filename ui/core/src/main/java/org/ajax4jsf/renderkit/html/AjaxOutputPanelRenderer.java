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

package org.ajax4jsf.renderkit.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIAjaxOutputPanel;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/02/01 15:31:27 $
 *
 */
public class AjaxOutputPanelRenderer extends RendererBase {
	
	private final String[] STYLE_ATTRIBUTES = new String[]{"style","class"};

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// 
		UIAjaxOutputPanel panel = (UIAjaxOutputPanel) component;
		if ("none".equals(panel.getLayout())) {
			if (component.getChildCount() > 0) {
				AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
				boolean ajaxRequest = ajaxContext.isAjaxRequest();
				Set<String> ajaxRenderedAreas = ajaxContext.getAjaxRenderedAreas();
				for (UIComponent child : component.getChildren()) {
					String childId = child.getClientId(context);
					if (child.isRendered()) {
						renderChild(context, child);						
					} else {
						// Render "dummy" span.
						ResponseWriter out = context.getResponseWriter();
						out.startElement(HTML.SPAN_ELEM,child);
						out.writeAttribute(HTML.id_ATTRIBUTE,childId,HTML.id_ATTRIBUTE);
						out.writeAttribute(HTML.style_ATTRIBUTE,"display: none;","style");
						out.endElement(HTML.SPAN_ELEM);
					}
					// register child as rendered
					if(ajaxRequest && null != ajaxRenderedAreas) {
						ajaxRenderedAreas.add(childId);
					}
				}
			}
			
		} else {
			renderChildren(context,component);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return UIAjaxOutputPanel.class;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeBegin(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeBegin(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		UIAjaxOutputPanel panel = (UIAjaxOutputPanel) component;
		if (!"none".equals(panel.getLayout())) {
			writer.startElement(getTag(panel), panel);
			getUtils().encodeId(context, component);
			getUtils().encodePassThru(context, component);
			getUtils().encodeAttributesFromArray(context,component,STYLE_ATTRIBUTES);
		}
	}

	/**
	 * @param panel
	 * @return
	 */
	private String getTag(UIAjaxOutputPanel panel) {
		return "block".equals(panel.getLayout())?HTML.DIV_ELEM:HTML.SPAN_ELEM;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeEnd(javax.faces.context.ResponseWriter, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		UIAjaxOutputPanel panel = (UIAjaxOutputPanel) component;
		if (!"none".equals(panel.getLayout())) {
			writer.endElement(getTag(panel));
		}
		if (panel.isKeepTransient()) {
			markNoTransient(component);
		}
	}

	/**
	 * Set "transient" flag to false for component and all its children ( recursive ).
	 * @param component
	 */
	private void markNoTransient(UIComponent component) {
		for (Iterator<UIComponent> iter = component.getFacetsAndChildren(); iter.hasNext();) {
			UIComponent element = iter.next();
			markNoTransient(element);
			element.setTransient(false);
		}
		
	}

	
}
