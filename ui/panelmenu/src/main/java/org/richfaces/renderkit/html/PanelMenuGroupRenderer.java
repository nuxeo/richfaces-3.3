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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.event.AjaxEvent;
import org.richfaces.component.UIPanelMenu;
import org.richfaces.component.UIPanelMenuGroup;
import org.richfaces.renderkit.PanelMenuRendererBase;

public class PanelMenuGroupRenderer extends PanelMenuRendererBase {

	protected Class getComponentClass() {
		return UIPanelMenuGroup.class;
	}
	
	protected void doDecode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId(context);
		Map requestMap =context.getExternalContext().getRequestParameterMap();
		UIPanelMenuGroup group = ((UIPanelMenuGroup)component);
		
		if(requestMap.containsKey("panelMenuState"+clientId)){
			Object property = requestMap.get("panelMenuState"+clientId);
			if (property.equals("opened")) {
				group.setSubmittedValue("true");
			} else if (property.equals("closed")) {
				group.setSubmittedValue("false");
			}
			
		}
		if(isSubmitted(context, component)){
            new ActionEvent(component).queue();
            if ("ajax".equals(getItemMode(component))) {
                new AjaxEvent(component).queue();
            }
		}
	}
	
	public void insertImage(FacesContext context, UIComponent component, Object data)
			throws IOException {
		
		String from = (String)data;
		String align = "";
		
		UIPanelMenu panelMenu = findMenu(component);
		boolean isTopLevel = isTopLevel(component);

		if (isTopLevel){
			align = panelMenu.getIconGroupTopPosition();
		} else {
			align = panelMenu.getIconGroupPosition();
		}
		
		if (align.equalsIgnoreCase(from)){
			image(context,component, from + "Icon" + component.getClientId(context));
		} else {
			String iconType = PANEL_MENU_SPACER_ICON_NAME;
			String imageSrc = getIconByType(iconType, isTopLevel, context, component);
			drawIcon(context.getResponseWriter(), iconType, imageSrc, component, from + "Icon" + component.getClientId(context), false);
		}
	}
	
	private void image(FacesContext context, UIComponent component, String id)throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		UIPanelMenu panelMenu = findMenu(component);
		if(panelMenu == null){
			return;
		}
		UIPanelMenuGroup panelMenuGroup = (UIPanelMenuGroup)component;
		boolean isTopLevel = isTopLevel(panelMenuGroup);

		boolean isOpened = isOpened(context,component);

		
		String defaultIconNodeClosed = null;
		
		if(isTopLevel){
			if(panelMenuGroup.isDisabled()){
				defaultIconNodeClosed = panelMenu.getIconTopDisableGroup();
				if(defaultIconNodeClosed == null || defaultIconNodeClosed.equals("")){
					defaultIconNodeClosed = panelMenu.getIconDisabledGroup();
				}
			} else {
				defaultIconNodeClosed = panelMenu.getIconCollapsedTopGroup();
				if(defaultIconNodeClosed == null || defaultIconNodeClosed.equals("")){
					defaultIconNodeClosed = panelMenu.getIconCollapsedGroup();
				}
			}
		} else {
			if(panelMenuGroup.isDisabled()){
				defaultIconNodeClosed = panelMenu.getIconDisabledGroup();
			} else {
				defaultIconNodeClosed = panelMenu.getIconCollapsedGroup();
			}
		}
		
		String defaultIconNodeOpened = null;
		
		if(isTopLevel){
			defaultIconNodeOpened = panelMenu.getIconExpandedTopGroup();
			if(defaultIconNodeOpened == null || defaultIconNodeOpened.equals("")){
				defaultIconNodeOpened = panelMenu.getIconExpandedGroup();
			}
		} else {
			defaultIconNodeOpened = panelMenu.getIconExpandedGroup();
		}

		String iconExpanded = "";
		String iconCollapsed = "";

		iconExpanded = panelMenuGroup.isDisabled() ? panelMenuGroup.getIconDisabled() : panelMenuGroup.getIconExpanded();
		iconCollapsed = panelMenuGroup.isDisabled() ? panelMenuGroup.getIconDisabled() : panelMenuGroup.getIconCollapsed();
		
		String icon = null;
		if(isOpened){
			if(iconExpanded != null && !iconExpanded.equals("")){
				if(iconExpanded.equals("none")){
					return;
				} else {
					icon = iconExpanded; 
				}
			} else {
				icon = defaultIconNodeOpened;
			}
		} else {
			if(iconCollapsed!= null && !iconCollapsed.equals("")){
				if(iconCollapsed.equals("none")){
					return;
				} else {
					icon = iconCollapsed; 
				}
			} else {
				icon = defaultIconNodeClosed;
			}
		}
		
		if ("".equals(icon))
			icon = "custom";
		String source = getIconByType(icon, isTopLevel, context, component);
		boolean drawHidden = false;
		if (source != null && source.trim().length() == 0) {
			source = getIconByType(PANEL_MENU_SPACER_ICON_NAME, isTopLevel, context, component);
			drawHidden = true;
		}
		drawIcon(writer, icon, source, component, id, drawHidden);
	}

	public String getFullStyleClass(FacesContext context, UIComponent component) {
		StringBuffer classBuffer = new StringBuffer("");
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		UIPanelMenu parentMenu = findMenu(group);
		if (!parentMenu.isDisabled() && !group.isDisabled()) {
			if (calculateLevel(group) == 0) 
				classBuffer.append(parentMenu.getTopGroupClass() + " ");
			else 
				classBuffer.append(parentMenu.getGroupClass() + " ");
			classBuffer.append(group.getStyleClass());
		} else
			classBuffer.append(parentMenu.getDisabledGroupClass() + " ")
				.append(group.getDisabledClass());
		return classBuffer.toString();
	}
	
	public String getFullStyle(FacesContext context, UIComponent component) {
		StringBuffer styleBuffer = new StringBuffer("");
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		UIPanelMenu parentMenu = findMenu(group);
		if (!group.isDisabled()) {
			if (calculateLevel(group) == 0) 
				styleBuffer.append(parentMenu.getTopGroupStyle() + "; ");
			else 
				styleBuffer.append(parentMenu.getGroupStyle() + "; ");
			styleBuffer.append(group.getStyle());
		} else
			styleBuffer.append(parentMenu.getDisabledGroupStyle() + "; ")
				.append(group.getDisabledStyle());
		return styleBuffer.toString();
	}

	public void insertLabel(FacesContext context, UIComponent component) throws IOException {
		Object label = component.getAttributes().get("label");
		if (label!=null){
			context.getResponseWriter().writeText(label, null);
		}
	}

	public Object getConvertedValue(FacesContext context,
			UIComponent component, Object submittedValue)
			throws ConverterException {
		
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		if(group.getConverter() != null){
			return group.getConverter().getAsObject(context, component, (String)submittedValue);
		} else {
			return submittedValue;
		}
		
	}
	
	public boolean isOpened(FacesContext context, UIComponent component)throws IOException {
		
		boolean value = false;
		if(component instanceof UIPanelMenuGroup){
			UIPanelMenuGroup group = (UIPanelMenuGroup)component;
			value = group.isExpanded();
			
			boolean isParentDisabled = isParentDisabled(component);
			boolean disabled = !group.isDisabled() && !isParentDisabled ;
			
			if( value && disabled){
				value = true;
			} else {
				//check expanded attributes in children groups, if exists
				boolean isChildrenExpanded = isChildrenExpanded(component);
				value = isChildrenExpanded  && disabled; // ? "opened" : "closed";
			}
			
		}
		
		return value;
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
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		UIPanelMenu parentMenu = findMenu(group);
		StringBuffer resClass = new StringBuffer(); 
		if(!group.isDisabled() && !parentMenu.isDisabled()){
			if(isTopLevel(component)){
				resClass.append("rich-pmenu-group-self-label rich-pmenu-top-group-self-label");
			} else resClass.append("rich-pmenu-group-self-label");
		}
		
		return resClass.toString();
	}
	
	public String getIconClass(FacesContext context, UIComponent component, String align) {
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		UIPanelMenu parentMenu = findMenu(group);
		String iconClass = "";
		
		if(!group.isDisabled() && !parentMenu.isDisabled()){
			String iconClassAttr = ((UIPanelMenuGroup)component).getIconClass();
			if(isTopLevel(component)){
				if(align.equals(parentMenu.getIconGroupTopPosition())){
					iconClass = "rich-pmenu-group-self-icon rich-pmenu-top-group-self-icon";
				}	
			} 
			
			if(align.equals(parentMenu.getIconGroupPosition())){
				if(iconClassAttr != null){
					iconClass = iconClass.equals("") ? ("rich-pmenu-group-self-icon " + iconClassAttr): (iconClass + " " + iconClassAttr);
				}	
			}	
		}
		
		return iconClass;
	}
	
	public String getDivClass(FacesContext context, UIComponent component) {
		String result = "";
		if (isTopLevel(component))
			result = "dr-pmenu-top-group-div rich-pmenu-top-group-div";
		return result;
	}
	
	public String getTableClass(FacesContext context, UIComponent component) {
		String result;
		if (isTopLevel(component))
			result = "dr-pmenu-top-group rich-pmenu-top-group ";
		else
			result = "dr-pmenu-group";
		return result;
	}
	
	public boolean isSelected(FacesContext context, UIComponent component){
		UIPanelMenuGroup group = (UIPanelMenuGroup)component;
		UIPanelMenu parentMenu = findMenu(group);
		return group.getName().equals(parentMenu.getSelectedName());
	}

}
