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
package org.richfaces.component;

import javax.faces.component.UIInput;

public abstract class UIPanelMenu extends UIInput{
	
	public static final String COMPONENT_TYPE = "org.richfaces.panelMenu";

	public static final String DEFAULT_SELECTED_CLASS = "dr-pmenu-selected-item";
	public static final String USER_DEFINED_SELECTED_CLASS = "rich-pmenu-selected-element";

	public abstract String getEvent();
	public abstract void setEvent(String event);
	public abstract String getMode();
	public abstract void setMode(String mode);
	public abstract String getWidth();
	public abstract void setWidth(String width);
	public abstract String getExpandMode();
	public abstract void setExpandMode(String expandMode);
	public abstract boolean isExpandSingle();
	public abstract void setExpandSingle(boolean expandSingle);
	public abstract String getIconItem();
	public abstract void setIconItem(String iconItem);
	public abstract String getIconDisabledItem();
	public abstract void setIconDisabledItem(String iconDisabledItem);
	public abstract String getIconTopItem();
	public abstract void setIconTopItem(String iconTopItem);
	public abstract String getIconTopDisabledItem();
	public abstract void setIconTopDisabledItem(String iconTopDisabledItem);
	public abstract String getIconExpandedGroup();
	public abstract void setIconExpandedGroup(String iconExpandedGroup);
	public abstract String getIconCollapsedGroup();
	public abstract void setIconCollapsedGroup(String iconCollapsedGroup);
	public abstract String getIconDisabledGroup();
	public abstract void setIconDisabledGroup(String iconDisabledGroup);
	public abstract String getIconExpandedTopGroup();
	public abstract void setIconExpandedTopGroup(String iconExpandedTopGroup);
	public abstract String getIconCollapsedTopGroup();
	public abstract void setIconCollapsedTopGroup(String iconCollapsedTopGroup);
	public abstract String getIconTopDisableGroup();
	public abstract void setIconTopDisableGroup(String iconTopDisableGroup);
	public abstract String getIconItemPosition();
	public abstract void setIconItemPosition(String iconItemPosition);
	public abstract String getIconItemTopPosition();
	public abstract void setIconItemTopPosition(String iconItemTopPosition);
	public abstract String getIconGroupPosition();
	public abstract void setIconGroupPosition(String iconGroupPosition);
	public abstract String getIconGroupTopPosition();
	public abstract void setIconGroupTopPosition(String iconGroupTopPosition);
	public abstract String getStyle();
	public abstract void setStyle(String style);
	public abstract String getStyleClass();
	public abstract void setStyleClass(String styleClass);
	public abstract String getGroupStyle();
	public abstract void setGroupStyle(String groupStyle);
	public abstract String getGroupClass();
	public abstract void setGroupClass(String groupClass);
	public abstract String getTopGroupStyle();
	public abstract void setTopGroupStyle(String topGroupStyle);
	public abstract String getTopGroupClass();
	public abstract void setTopGroupClass(String topGroupClass);
	public abstract String getItemStyle();
	public abstract void setItemStyle(String itemStyle);
	public abstract String getItemClass();
	public abstract void setItemClass(String itemClass);
	public abstract String getTopItemStyle();
	public abstract void setTopItemStyle(String topItemStyle);
	public abstract String getTopItemClass();
	public abstract void setTopItemClass(String topItemClass);
	public abstract String getDisabledItemClass();
	public abstract void setDisabledItemClass(String disabledItemClass);
	public abstract String getDisabledItemStyle();
	public abstract void setDisabledItemStyle(String disabledItemStyle);
	public abstract String getDisabledGroupClass();
	public abstract void setDisabledGroupClass(String disabledGroupClass);
	public abstract String getDisabledGroupStyle();
	public abstract void setDisabledGroupStyle(String disabledGroupStyle);
	public abstract String getHoveredItemClass();
	public abstract void setHoveredItemClass(String hoveredItemClass);
	public abstract String getHoveredItemStyle();
	public abstract void setHoveredItemStyle(String hoveredItemStyle);
	public abstract String getHoveredGroupStyle();
	public abstract void setHoveredGroupStyle(String hoveredItemStyle);
	public abstract String getHoveredGroupClass();
	public abstract void setHoveredGroupClass(String hoveredGroupClass);
	public abstract String getOnitemhover();
	public abstract void setOnitemhover(String onitemhover);
	public abstract String getOngroupcollapse();
	public abstract void setOngroupcollapse(String ongroupcollapse);
	public abstract String getOngroupexpand();
	public abstract void setOngroupexpand(String ongroupexpand);
	public abstract boolean isDisabled();
	public abstract void setDisabled(boolean disabled);
	
	public abstract void setOnclick(String string);
	public abstract String getOnclick();

	public abstract void setOndblclick(String string);
	public abstract String getOndblclick();

	public abstract void setOnmouseout(String string);
	public abstract String getOnmouseout();

	public abstract void setOnmousemove(String string);
	public abstract String getOnmousemove();

	public abstract void setOnmouseover(String string);
	public abstract String getOnmouseover();
	
	public abstract void setSelectedChild(String string);
	public abstract String getSelectedChild();
	
	public String getSelectedName(){
		return getValue() != null ? getValue().toString() : getSelectedChild(); 
	}

}
