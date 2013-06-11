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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIToolBar;
import org.richfaces.component.UIToolBarGroup;
import org.richfaces.component.util.ViewUtil;
import org.richfaces.renderkit.html.images.DotSeparatorImage;
import org.richfaces.renderkit.html.images.GridSeparatorImage;
import org.richfaces.renderkit.html.images.LineSeparatorImage;
import org.richfaces.renderkit.html.images.SquareSeparatorImage;


public class ToolBarRendererBase extends HeaderResourcesRendererBase {
	private final static String[] ON_ITEM_EVENTS = new String[] {
		"onitemkeydown",
		"onitemkeypress",
		"onitemkeyup",
		"onitemclick",
		"onitemdblclick",
		"onitemmousedown",
		"onitemmousemove",
		"onitemmouseover",
		"onitemmouseout",
		"onitemmouseup"
	};
	
	public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException {
		UIToolBar toolBar = (UIToolBar) component;
		List<UIComponent> children = toolBar.getChildren();
		String contentClass = (String) toolBar.getAttributes().get("contentClass");
		String contentStyle = (String) toolBar.getAttributes().get("contentStyle");

		if (null == contentClass) {
			contentClass = "";
		}
		if (null == contentStyle) {
			contentStyle = "";
		}
		
		if (children != null) {
			List<UIComponent> childrenToTheLeft = new LinkedList<UIComponent>();
			List<UIComponent> childrenToTheRight = new LinkedList<UIComponent>();
			for (Iterator<UIComponent> iter = children.iterator(); iter.hasNext();) {
				UIComponent child = iter.next();
				if(child.isRendered()){
					if (child instanceof UIToolBarGroup) {
						UIToolBarGroup group = (UIToolBarGroup) child;
						String location = group.getLocation();
						if(location != null && location.equals("right")){
							childrenToTheRight.add(child);
						} else {
							childrenToTheLeft.add(child);
						}
					} else {
						childrenToTheLeft.add(child);
					}
				}
			}
			
			ResponseWriter writer = facesContext.getResponseWriter();
			for (Iterator<UIComponent> it = childrenToTheLeft.iterator(); it.hasNext();) {				
				UIComponent child = it.next();
				
				if (! (child instanceof UIToolBarGroup)) {
					writer.startElement(HTML.td_ELEM, component);					
					writer.writeAttribute(HTML.class_ATTRIBUTE, "dr-toolbar-int rich-toolbar-item " + contentClass, null);
					getUtils().writeAttribute(writer, HTML.style_ATTRIBUTE, contentStyle);
					encodeEventsAttributes(facesContext, toolBar, writer);
				}
				renderChild(facesContext, child);
				if (! (child instanceof UIToolBarGroup)) {
					writer.endElement(HTML.td_ELEM);					
				}
				if (it.hasNext()) {
					insertSeparatorIfNeed(facesContext, toolBar, writer);
				}
			}				
			
			writer.startElement(HTML.td_ELEM, component);
			writer.writeAttribute(HTML.style_ATTRIBUTE, "width:100%", null);
			writer.endElement(HTML.td_ELEM);
			
			for (Iterator<UIComponent> it = childrenToTheRight.iterator(); it.hasNext();) {
				UIComponent child = it.next();					
				renderChild(facesContext, child);
				if (it.hasNext()) {
					insertSeparatorIfNeed(facesContext, toolBar, writer);
				}
			}
		}
	}
	
	/**
	 * Inserts separator between toolbar items. Uses facet "itemSeparator" if it is set
	 * and default separator implementation if facet is not set.
	 * @param context - faces context
	 * @param component - component
	 * @param writer - response writer
	 * @throws IOException - in case of IOException during writing to the ResponseWriter
	 */
	protected void insertSeparatorIfNeed(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
	    UIComponent separatorFacet = component.getFacet("itemSeparator");
	    boolean isSeparatorFacetRendered = (separatorFacet != null) ? separatorFacet.isRendered() : false;
	    if (isSeparatorFacetRendered) {
		writer.startElement(HTML.td_ELEM, component);
		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-toolbar-separator", null);
		renderChild(context, separatorFacet);
		writer.endElement(HTML.td_ELEM);
	    }
	    else {
		insertDefaultSeparatorIfNeed(context, component, writer);
	    }
	}
	
	/**
	 * Inserts default separator. Possible values are:
	 * "square", "disc", "grid", "line" - for separators provided by component inplementation;
	 * "none" - for no separators between toolbar items;
	 * URI string value - for custom images specified by the page author.
	 * 
	 * @param context - faces context
	 * @param component - component
	 * @param writer - response writer
	 * @throws IOException - in case of IOException during writing to the ResponseWriter
	 */
	protected void insertDefaultSeparatorIfNeed(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
		
		String itemSeparator = (String)component.getAttributes().get("itemSeparator");
		if (itemSeparator != null && itemSeparator.length()!=0 && ! itemSeparator.equalsIgnoreCase("none")){
			String uri = null;			
			if (itemSeparator.equalsIgnoreCase("square")) {
				uri = getResource(SquareSeparatorImage.class.getName()).getUri(context, component);
			} else if (itemSeparator.equalsIgnoreCase("disc")) {
				uri = getResource(DotSeparatorImage.class.getName()).getUri(context, component);
			} else if (itemSeparator.equalsIgnoreCase("grid")) {
				uri = getResource(GridSeparatorImage.class.getName()).getUri(context, component);				
			} else if (itemSeparator.equalsIgnoreCase("line")) {
				uri = getResource(LineSeparatorImage.class.getName()).getUri(context, component);
			} else {
			    	// let the user specify URI to custom separator image in "itemSeparator" attribute
			    	uri = ViewUtil.getResourceURL(itemSeparator);
			}
			writer.startElement(HTML.td_ELEM, component);
			writer.writeAttribute(HTML.align_ATTRIBUTE, "center", null);
			getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE, component.getAttributes().get("separatorClass"));			
			writer.startElement(HTML.IMG_ELEMENT, component);			
			getUtils().writeAttribute(writer, HTML.src_ATTRIBUTE, uri);
			getUtils().writeAttribute(writer, HTML.alt_ATTRIBUTE, "");
			writer.endElement(HTML.IMG_ELEMENT);
			writer.endElement(HTML.td_ELEM);			
		}
	}

	protected Class<? extends javax.faces.component.UIComponent> getComponentClass() {
		return UIToolBar.class;
	}

	public boolean getRendersChildren() {
		return true;
	}
	
	protected void encodeEventsAttributes(FacesContext facesContext, UIComponent component, ResponseWriter writer) 
			throws IOException {
	    	
		Map<String, Object> attributes = component.getAttributes();
		for (int i = 0; i < ON_ITEM_EVENTS.length; i++) {
		    	String eventName = ON_ITEM_EVENTS[i];
		    	Object value = null;
		    	value = attributes.get(eventName);
		    	String attrvalue = "";
			if ((null != value && !"".equals(value))) {
				attrvalue = attrvalue + value;
			}
			getUtils().writeAttribute(writer, eventName.replace("item",""), attrvalue);
		}
	}

	public UIToolBar getParentToolBar(UIComponent component) {
		if (component instanceof UIToolBar) {
			return (UIToolBar) component;
		} else if (component instanceof UIToolBarGroup) {
        	return ((UIToolBarGroup)component).getToolBar();
        } else {
        	UIComponent parent = component.getParent();
        	if (!(parent instanceof UIToolBar)) {
        		return null;
        	} else {
        		return (UIToolBar) parent;
        	}
        }
	}

}
