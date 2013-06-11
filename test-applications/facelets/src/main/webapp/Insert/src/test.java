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

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.compiler.TemplateContext;
import org.richfaces.skin.Skin;
import org.ajax4jsf.util.style.CSSFormat;
import org.richfaces.component.UIPanelMenu;
import org.richfaces.component.UIPanelMenuGroup;
import org.richfaces.component.UIPanelMenuItem;
import org.richfaces.renderkit.html.PanelMenuGroupRenderer;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconChevron;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconChevronDown;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconChevronUp;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconDisc;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconGrid;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconSpacer;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangle;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangleDown;
import org.richfaces.renderkit.html.iconimages.PanelMenuIconTriangleUp;
import org.ajax4jsf.renderkit.RendererUtils.HTML;

/**
 * @author hans
 *
 */
public abstract class PanelMenuRendererBase extends HeaderResourcesRendererBase {
	
	public final static String PANEL_MENU_SPACER_ICON_NAME = "spacer";
	
	public void insertSpacerImages(FacesContext context , UIComponent component) throws IOException {
		ResponseWriter writer 	= context.getResponseWriter();
		int level = calculateLevel(component);
		//StringBuffer buffer = new StringBuffer();
		String src = getIconByType("custom",false,context,component);
		int w = 16; //width(context);
		
		for (int i=0;i<level;i++){
			writer.startElement("img", component);
			writer.writeAttribute("src", src, null);
			writer.writeAttribute("alt", "", null);
			writer.writeAttribute("hspace", "0", null);
			writer.writeAttribute("vspace", "0", null);
			writer.writeAttribute("height", String.valueOf(w), null);
			writer.writeAttribute("width", String.valueOf(w), null);
			writer.endElement("img");
		}
	}
	
	protected int calculateLevel (UIComponent component){
		int level = 0;
		UIComponent parent = component.getParent();
		while(parent != null){
			if(parent instanceof UIPanelMenu){
				return level;
			} else if(parent instanceof UIPanelMenuGroup){
				level++;
			}
			parent = parent.getParent();
		}
		return level;
	}
	
	/**
	 * If icon type is a pre-defined value, return corresponding image, otherwise
	 * otherwise  
	 * @param iconType
	 * @param context
	 * @param component
	 * @return URI of requested image
	 */
	protected String getIconByType(String iconType, boolean isTopLevel, FacesContext context, 
			UIComponent component){
		
		String source = "";
		String color = "";
		Skin skin = getSkin(context);
				
		if (isTopLevel){
				color = (String) skin.getParameter(context,"panelmenu.headerBulletColor");
		} else {
			color = (String) skin.getParameter(context,"panelmenu.itemBulletColor");
		}
		if(iconType != null && !iconType.equals("none")){
			if (iconType.equals("custom") || "".equals(iconType)){
				source = getResource(PanelMenuIconSpacer.class.getName()).getUri(context, color);
			} else if (iconType.equals("spacer")){
				source = getResource(PanelMenuIconSpacer.class.getName()).getUri(context, color);
			} else if (iconType.equals("triangle")) {
				source = getResource(PanelMenuIconTriangle.class.getName()).getUri(context, color);
			} else if (iconType.equals("triangleDown")) {
				source = getResource(PanelMenuIconTriangleDown.class.getName()).getUri(context, color);
			} else if (iconType.equals("triangleUp")) {
				source = getResource(PanelMenuIconTriangleUp.class.getName()).getUri(context, color);
			} else if (iconType.equals("chevron")) {
				source = getResource(PanelMenuIconChevron.class.getName()).getUri(context, color);
			} else if (iconType.equals("chevronUp")) {
				source = getResource(PanelMenuIconChevronUp.class.getName()).getUri(context, color);
			} else if (iconType.equals("chevronDown")) {
				source = getResource(PanelMenuIconChevronDown.class.getName()).getUri(context, color);
//			} else if (iconType.equals("square")) {
//				source = getResource(PanelMenuIconSquare.class.getName()).getUri(context, color);
			} else if (iconType.equals("disc")) {
				source = getResource(PanelMenuIconDisc.class.getName()).getUri(context, color);
			} else if (iconType.equals("grid")) {
				source = getResource(PanelMenuIconGrid.class.getName()).getUri(context, color);
			} else {
				//TODO by nick - dima - TemplateContext is deprecated and shouldn't be used
				source = (String)getUtils().encodeResourceURL(new  TemplateContext(this,context,component),iconType);
			}
		}
		return source;
	}
	
	protected UIPanelMenu findMenu (UIComponent component){
		if (component instanceof UIPanelMenu) return (UIPanelMenu)component;
		UIComponent parent = component;
		while(parent != null){
			if(parent instanceof UIPanelMenu){
				return (UIPanelMenu) parent;
			}
			parent = parent.getParent();
		}
		return (UIPanelMenu) parent;
	}
	
	public String getHideStyle(FacesContext context, UIComponent component) {
		if (!(component.getParent() instanceof UIPanelMenu)) {
			CSSFormat format = new CSSFormat();
			format.add("display", "none");
			if(component.getParent() instanceof UIPanelMenuGroup) {
				UIPanelMenuGroup parent = (UIPanelMenuGroup)component.getParent();
				PanelMenuGroupRenderer renderer = (PanelMenuGroupRenderer) context.getRenderKit().getRenderer(parent.getFamily(), parent.getRendererType());
				try {
					if ( renderer.isOpened(context, parent) ){
						return "";
					} else 
						return format.toString();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else return format.toString();
		}
		return "";
	}
	
	public void insertLabel(FacesContext context, UIComponent component) throws IOException {
		Object value = component.getAttributes().get("label");
		if (value!=null){
			context.getResponseWriter().write(value.toString());
		}
	}
	
	protected boolean isChildrenExpanded(UIComponent component){
		if (component.getChildren() != null){
			Iterator itr = component.getChildren().iterator();
			while(itr.hasNext()){
				UIComponent child = (UIComponent)itr.next();
				if(child instanceof UIPanelMenuGroup){
					if(  ((UIPanelMenuGroup)child).isExpanded() ){
						return true;
					} else {
						return isChildrenExpanded(child); 
					}
				}
			}
		}
		return false;
	}
	
	protected boolean isParentDisabled(UIComponent component){
		boolean returnValue = false;
		UIComponent parent = component.getParent();
		if(parent instanceof UIPanelMenuGroup){
			UIPanelMenuGroup parentGroup = (UIPanelMenuGroup)parent;
			if(parentGroup.isDisabled()){
				returnValue = true;
			} else {
				returnValue = isParentDisabled(parentGroup);
			}
		}
		return returnValue;
	}
	
	protected boolean isSubmitted(FacesContext context, UIComponent component){
        boolean submitted = false;
		String clientId = component.getClientId(context);
		Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
		
		Object value = requestParameterMap.get("panelMenuAction"+clientId);
		if (clientId!=null&&value!=null){
			if (value.equals(clientId)) {
				submitted = true;
			}
		}
		return submitted;
	}
	
	protected String getItemMode(UIComponent component) {
		String parentExpandMode = findMenu(component).getExpandMode();
		String parentMode = findMenu(component).getMode();
		if (null == parentMode || "".equals(parentMode))
			parentMode = "server";
		if (null == parentExpandMode || "".equals(parentExpandMode))
			parentExpandMode = "none";
		String mode = "none";
		if (component instanceof UIPanelMenuGroup) {
			UIPanelMenuGroup group = (UIPanelMenuGroup) component;
			if (null != group.getExpandMode() && ! "".equals(group.getExpandMode()))
				mode = group.getExpandMode();
			else
				mode = parentExpandMode;
		} else if (component instanceof UIPanelMenuItem) {
			UIPanelMenuItem item = (UIPanelMenuItem) component;
			if (null != item.getMode() && ! "".equals(item.getMode()))
				mode = item.getMode();
			else
				mode = parentMode;
		}
		return mode;
	}
	
	/**
	 * 
	 * @param component
	 * @return
	 */
	public boolean isTopLevel(UIComponent component){
		UIComponent parent = component.getParent();
		while( !(parent instanceof UIPanelMenu) && !(parent instanceof UIPanelMenuGroup)) {
			parent = parent.getParent();
		}	
		if(parent instanceof UIPanelMenu){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param writer
	 * @param iconType
	 * @param imageSrc
	 * @param component
	 * @throws IOException
	 */
	public void drawIcon(ResponseWriter writer, String iconType, String imageSrc, UIComponent component, String id) throws IOException{
		if (iconType != null && !iconType.equals("") && !iconType.equals("none")){
			int h = 16; //width(context);
			writer.startElement("img", component);
			writer.writeAttribute("src", imageSrc, null);
			writer.writeAttribute("alt", "", null);
			writer.writeAttribute("vspace", "0", null);
			writer.writeAttribute("hspace", "0", null);
			writer.writeAttribute("width", String.valueOf(h), null);
			writer.writeAttribute("height", String.valueOf(h), null);
			writer.writeAttribute("id", id, null);
			writer.endElement("img");
		}	
		
	}

}
