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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.RendererBase;
import static org.richfaces.component.util.HtmlUtil.concatClasses;
import static org.richfaces.component.util.HtmlUtil.concatStyles;



public class MenuItemRendererDelegate extends RendererBase {
	
	protected void initializeStyles(FacesContext context, UIComponent menuItem, 
			boolean disabled, ComponentVariables variables) {
		UIComponent parentMenu = getParentMenu(context, menuItem);
		
		String itemClass = null;
		String itemStyle = null;
		String disabledItemClass = null;
		String disabledItemStyle = null;
		String selectItemClass = null;
	
		if (parentMenu != null) {
			itemClass = (String) parentMenu.getAttributes().get("itemClass");
			itemStyle = (String) parentMenu.getAttributes().get("itemStyle");
			disabledItemClass = (String) parentMenu.getAttributes().get("disabledItemClass");
			disabledItemStyle = (String) parentMenu.getAttributes().get("disabledItemStyle");
			selectItemClass = (String) parentMenu.getAttributes().get("selectItemClass");
			
		}
		String selectClass = (String) menuItem.getAttributes().get("selectClass");
		String styleClass = (String) menuItem.getAttributes().get("styleClass");
		String style = (String) menuItem.getAttributes().get("style");
		String labelClass = (String) menuItem.getAttributes().get("labelClass");
		String disabledLabelClass = (String) menuItem.getAttributes().get("labelClass");
		String selectedLabelClass = (String) menuItem.getAttributes().get("labelClass");
		
		if (disabled) {
			variables.setVariable("menuItemClass", concatClasses("rich-menu-item rich-menu-item-disabled", styleClass, itemClass, disabledItemClass));
			variables.setVariable("menuItemStyle", concatStyles(itemStyle, disabledItemStyle, style));
			variables.setVariable("menuItemLabelClass", concatClasses("rich-menu-item-label rich-menu-item-label-disabled", labelClass, disabledLabelClass));
			variables.setVariable("menuGroupClass", concatClasses("rich-menu-group rich-menu-group-disabled", itemClass, disabledItemClass, styleClass));
			variables.setVariable("menuItemMouseMove", "");
			variables.setVariable("menuItemItemIconClass", "rich-menu-item-icon-disabled");
			variables.setVariable("menuItemItemLabelClass", concatClasses("rich-menu-item-label-disabled", labelClass));
			variables.setVariable("menuItemItemFolderClass", "rich-menu-item-folder-disabled");
		} else {
			variables.setVariable("menuItemCustomClass", concatClasses(styleClass, itemClass));
			variables.setVariable("menuItemClass", concatClasses("rich-menu-item rich-menu-item-enabled", styleClass, itemClass));
			variables.setVariable("menuItemStyle", concatStyles(itemStyle, style));
			variables.setVariable("menuItemHoverClass", concatClasses(styleClass, selectClass, selectItemClass));
			variables.setVariable("menuItemLabelClass", concatClasses("rich-menu-item-label", labelClass));
			variables.setVariable("selectLabelClass", selectedLabelClass);
			variables.setVariable("menuGroupClass", concatClasses("rich-menu-group rich-menu-group-enabled", itemClass, styleClass));
			variables.setVariable("menuGroupCustomClass", concatClasses(itemClass, styleClass));
			variables.setVariable("menuItemMouseMove", menuItem.getAttributes().get("onmousemove"));
			variables.setVariable("menuGroupItemIconClass", "rich-menu-item-icon-enabled rich-menu-group-icon");
			variables.setVariable("menuGroupItemLabelClass", concatClasses("rich-menu-item-label rich-menu-group-label", labelClass));
			variables.setVariable("menuGroupItemFolderClass", "rich-menu-item-folder rich-menu-group-folder");
			variables.setVariable("onmouseoutInlineStyles", collectInlineStyles(context, menuItem, false));
            variables.setVariable("onmouseoverInlineStyles", collectInlineStyles(context, menuItem, true));
            
            variables.setVariable("menuGroupHoverClass", concatClasses(itemClass, selectItemClass, styleClass));
		}
	}
	
	protected String collectInlineStyles(FacesContext context, UIComponent menuItem, boolean isOnmouseover) {
		String style = (String) menuItem.getAttributes().get("style");
		String selectStyle = (String) menuItem.getAttributes().get("selectStyle");

		UIComponent parentMenu = getParentMenu(context, menuItem);
		String selectItemStyle = null;
		String itemStyle = null;
		if (parentMenu != null) {
			selectItemStyle = (String) parentMenu.getAttributes().get("selectItemStyle");
			itemStyle = (String) parentMenu.getAttributes().get("itemStyle");
		}

		if (isOnmouseover) {
			return concatStyles(style, itemStyle, selectItemStyle, selectStyle);
		} else {
			return concatStyles(style, itemStyle);
		}
	}
	
	@Deprecated
	protected String processInlineStyles(FacesContext context, UIComponent menuItem, boolean isOnmouseover) {
		return ("$('" + menuItem.getClientId(context) + "').style.cssText='" + collectInlineStyles(context, menuItem, isOnmouseover) + "';");
	}
	
	protected UIComponent getParentMenu(FacesContext context, UIComponent menuItem) {
		UIComponent parent = menuItem.getParent();
		while (null != parent) {
			if (parent instanceof org.richfaces.component.MenuComponent) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
//		throw new FacesException( "Parent menu for menu group (id=" 
//   			 + menuItem.getClientId(context) + ") has not been found.");
	}
	
	protected Class getComponentClass() {
		return null;
	}

}
