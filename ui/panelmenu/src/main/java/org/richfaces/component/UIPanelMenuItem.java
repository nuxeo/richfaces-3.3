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

package org.richfaces.component;

import org.ajax4jsf.component.AjaxActionComponent;

public abstract class UIPanelMenuItem extends AjaxActionComponent {
	
	public static final String COMPONENT_TYPE = "org.richfaces.panelMenuItem";

	public abstract String getMode();
	public abstract void setMode(String mode);
	public abstract String getIcon();
	public abstract void setIcon(String icon);
	public abstract String getIconDisabled();
	public abstract void setIconDisabled(String iconDisabled);
	public abstract boolean isDisabled();
	public abstract void setDisabled(boolean disabled);
	public abstract Object getLabel();
	public abstract void setLabel(Object label);
	public abstract String getHoverClass();
	public abstract void setHoverClass(String hoverClass);
	public abstract String getHoverStyle();
	public abstract void setHoverStyle(String hoverStyle);
	public abstract String getDisabledClass();
	public abstract void setDisabledClass(String disabledClass);
	public abstract String getDisabledStyle();
	public abstract void setDisabledStyle(String disabledStyle);
	public abstract String getStyleClass();
	public abstract void setStyleClass(String styleClass);
	public abstract String getStyle();
	public abstract void setStyle(String style);
	public abstract String getIconClass();
	public abstract void setIconClass(String iconClass);
	public abstract String getIconStyle();
	public abstract void setIconStyle(String iconStyle);
	public abstract String getTarget();
	public abstract void setTarget(String target);
	
	public abstract void setName(String string);
	public abstract String getName();
	
}
