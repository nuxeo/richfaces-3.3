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

import javax.faces.component.UIComponentBase;


/**
 * JSF component class
 */
public abstract class UIDropDownMenu extends UIComponentBase implements MenuComponent {

    public static final String COMPONENT_TYPE = "org.richfaces.DropDownMenu";

    public abstract void setEvent(String Event);

    public abstract String getEvent();

    public abstract void setSubmitMode(String submitMode);

    public abstract String getSubmitMode();
    
    public abstract boolean isDisabled();
    
    public abstract void setDisabled(boolean disabled);

}
