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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.event.AjaxEvent;
import org.richfaces.component.UIPanelMenu;
import org.richfaces.component.UIPanelMenuItem;
import org.richfaces.renderkit.PanelMenuRendererBase;

public class PanelMenuItemRenderer extends PanelMenuRendererBase {
	protected Class getComponentClass() {
		return UIPanelMenuItem.class;
	}

	protected void doEncodeBegin(ResponseWriter writer, FacesContext context,UIComponent component) throws IOException {
		
	}
	protected void doDecode(FacesContext context, UIComponent component) {
		if(isSubmitted(context, component)) {
			UIPanelMenuItem item = (UIPanelMenuItem)component;
			new ActionEvent(component).queue();
            if ("ajax".equals(getItemMode(component))) {
                new AjaxEvent(component).queue();
            }
		}
	}
	
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		
	}
	
	public void insertImage(FacesContext context, UIComponent component, Object data) throws IOException {
		String from = (String)data;
		UIPanelMenu panelMenu = findMenu(component);
		if(panelMenu == null){
			return;
		}
		String align = "";
		UIPanelMenuItem panelMenuItem = (UIPanelMenuItem)component;
		boolean isTopLevel = isTopLevel(panelMenuItem);
		
		if (isTopLevel){
			align = panelMenu.getIconItemTopPosition();
		} else {
			align = panelMenu.getIconItemPosition();
		}
		
		if (null == align || "".equals(align)) {
			align = "left";
		}
		
		if (align.equalsIgnoreCase(from)){
			image(context,component, from);
		} else {
			String iconType = PANEL_MENU_SPACER_ICON_NAME;
			String imageSrc = getIconByType(iconType, isTopLevel, context, component);
			drawIcon(context.getResponseWriter(), iconType, imageSrc, component, from + "Icon" + component.getClientId(context), false);
		}
	}
	
	private void image(FacesContext context, UIComponent component, String from) 
			throws IOException{
		
		UIPanelMenu panelMenu = findMenu(component);
		ResponseWriter writer 	= context.getResponseWriter();
		boolean isTopLevel 		= isTopLevel(component);		
		String iconType			= null;	
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		
		String defaultItemIcon = null;
		String customItemIcon = null;
		
		if(isTopLevel){
			if(item.isDisabled()){
				defaultItemIcon = panelMenu.getIconTopDisabledItem();
			} else {
				defaultItemIcon = panelMenu.getIconTopItem();
			}
			if(defaultItemIcon == null || defaultItemIcon.equals("")){
				if(item.isDisabled()){
					defaultItemIcon = panelMenu.getIconDisabledItem();
				} else {
					defaultItemIcon = panelMenu.getIconItem();
				}
			}
		} else {
			//isTopLevel == false
			if(defaultItemIcon == null || defaultItemIcon.equals("")){
				if(item.isDisabled()){
					defaultItemIcon = panelMenu.getIconDisabledItem();
				} else {
					defaultItemIcon = panelMenu.getIconItem();
				}
			}
		}
		
		customItemIcon = item.isDisabled() ? item.getIconDisabled() : item.getIcon();
		
		if(customItemIcon == null || customItemIcon.equals("")){
			iconType = defaultItemIcon;
		} else iconType = customItemIcon;
		
		boolean drawHidden = false;
		String source = getIconByType(iconType, isTopLevel, context, component);
		if (source != null && source.trim().length() == 0) {
			source = getIconByType(PANEL_MENU_SPACER_ICON_NAME, isTopLevel, context, component);
			drawHidden = true;
		}
		drawIcon(writer, iconType, source, component, from + "Icon" + component.getClientId(context), drawHidden);
	}
	
	
	public String getFullStyleClass(FacesContext context, UIComponent component) {
		StringBuffer classBuffer = new StringBuffer("");
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		UIPanelMenu parentMenu = findMenu(item);
		if (!parentMenu.isDisabled() && !item.isDisabled()) {
			if (calculateLevel(item) == 0) {
				String topItemClass = parentMenu.getTopItemClass();
				if(topItemClass != null && !topItemClass.equals("")){
					classBuffer.append(topItemClass).append(" ");
				}
				classBuffer.append("rich-pmenu-top-item ");
			} else {
				String parentItemClass = parentMenu.getItemClass();
				if(parentItemClass != null && !parentItemClass.equals("")){
					classBuffer.append(parentItemClass).append(" ");
				}	
			}	
			String itemClass = item.getStyleClass();
			if(itemClass != null && !itemClass.equals("")){
				classBuffer.append(itemClass);
			}	
		} else {
			String pmDisabledItemClass = parentMenu.getDisabledItemClass(); 
			if (pmDisabledItemClass != null && !pmDisabledItemClass.equals("")) {
				classBuffer.append(pmDisabledItemClass).append(" ");
			}
			
			String itemDisabledClass = item.getDisabledClass();
			if (itemDisabledClass != null && !itemDisabledClass.equals("")) {
				classBuffer.append(itemDisabledClass);
			}
		}	
		return classBuffer.toString();
	}
	
	public String getFullStyle(FacesContext context, UIComponent component) {
		StringBuffer styleBuffer = new StringBuffer("");
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		UIPanelMenu parentMenu = findMenu(item);
		if (!item.isDisabled()) {
			if (calculateLevel(item) == 0){ 
				String topItemStyle = parentMenu.getTopItemStyle();
				if (topItemStyle != null && !topItemStyle.equals("")) {
					styleBuffer.append(parentMenu.getTopItemStyle()).append(";").append(" ");;
				}	
			} else { 
				String itemStyle = parentMenu.getItemStyle();
				if (itemStyle != null && !itemStyle.equals("")) {
					styleBuffer.append(itemStyle).append(";").append(" ");
				}
			}	
			String style = item.getStyle();
			if (style != null && !style.equals("")) {
				styleBuffer.append(item.getStyle());
			}	
		} else {
			String pmDisabledItemStyle = parentMenu.getDisabledItemStyle();  		
			if (pmDisabledItemStyle != null && !pmDisabledItemStyle.equals("")) {
				styleBuffer.append(pmDisabledItemStyle).append(";").append(" ");; 
			}
			
			String itemDisabledStyle = item.getDisabledStyle();
			if (itemDisabledStyle != null && !itemDisabledStyle.equals("")){
				styleBuffer.append(itemDisabledStyle);
			}	
		}	
		return styleBuffer.toString();
	}
	
	public String getSelectedClass(FacesContext context, UIComponent component) {
		if (isSelected(context, component)) {
			StringBuffer selectedClass = null;
			selectedClass= new StringBuffer();
			selectedClass.append(UIPanelMenu.DEFAULT_SELECTED_CLASS).
				append(" ").
				append(UIPanelMenu.USER_DEFINED_SELECTED_CLASS);
			return selectedClass.toString();
		}
		return  ""; 
	}

	public String getLabelClass(FacesContext context, UIComponent component) {
		StringBuffer resClass = new StringBuffer();
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		UIPanelMenu parentMenu = findMenu(item);
		if (!item.isDisabled() && !parentMenu.isDisabled()) {
			if (isTopLevel(component)) {
				resClass.append("rich-pmenu-item-label rich-pmenu-top-item-label");
			} else {
				resClass.append("rich-pmenu-item-label");
			}
		}
		return resClass.toString();
	}
	
	public String getIconClass(FacesContext context, UIComponent component, String align) {
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		UIPanelMenu parentMenu = findMenu(item);
		String iconClass = "";
		
		if(!item.isDisabled() && !parentMenu.isDisabled()){
			String iconClassAttr = ((UIPanelMenuItem)component).getIconClass();
			
			if(isTopLevel(component)){
				if(align.equals(parentMenu.getIconItemTopPosition())){
					iconClass =  "rich-pmenu-item-icon rich-pmenu-top-item-icon";
				}	
			} 
			
			if(align.equals(parentMenu.getIconItemPosition())){
				if(iconClassAttr != null){
					iconClass = iconClass.equals("") ? ("rich-pmenu-item-icon " + iconClassAttr):(iconClass + " " + iconClassAttr);
				}
			}	
		}
		
		return iconClass;
	}
	
	public boolean isSelected(FacesContext context, UIComponent component){
		UIPanelMenuItem item = (UIPanelMenuItem)component;
		UIPanelMenu parentMenu = findMenu(item);
		return item.getName().equals(parentMenu.getSelectedName());
	}
}
