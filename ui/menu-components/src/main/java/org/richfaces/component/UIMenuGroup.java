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

import javax.faces.component.UIOutput;


/**
 * JSF component class
 */
public abstract class UIMenuGroup extends UIOutput {
    public static final String COMPONENT_TYPE = "org.richfaces.MenuGroup";

    public abstract boolean isDisabled();

    public abstract void setDisabled(boolean disabled);

    public abstract String getIcon();
    public abstract void setIcon(String icon);

    public abstract String getIconDisabled();
    public abstract void setIconDisabled(String icon);

    public abstract String getIconFolder();
    public abstract void setIconFolder(String icon);

    public abstract String getIconFolderDisabled();
    public abstract void setIconFolderDisabled(String icon);

	public abstract String getStyleClass();
	public abstract void setStyleClass(String styleClass);
	
	public abstract String getStyle();
	public abstract void setStyle(String style);
}
