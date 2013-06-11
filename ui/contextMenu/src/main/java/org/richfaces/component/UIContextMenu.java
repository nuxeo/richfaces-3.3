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

import javax.faces.component.UIComponentBase;

/**
 * @author Maksim Kaszynski
 *
 */
public abstract class UIContextMenu extends UIComponentBase implements MenuComponent {
    public static final String COMPONENT_TYPE = "org.richfaces.ContextMenu";
    
    public static final String ON_CONTEXT_MENU = "onContextMenu";
    public static final String DISABLE_DEFAULT_MENU = "disableDefaultMenu";
    
    public abstract boolean isAttached();
    public abstract void setAttached(boolean b);

    public abstract String getAttachTo();
    public abstract void setAttachTo(String attachTo);
    
    public abstract String getAttachTiming();
    public abstract void setAttachTiming(String attachTo);

    public abstract String getEvent();
    public abstract void setEvent(String event);
    
    public abstract void setShowDelay(Integer showDelay);
    public abstract Integer getShowDelay();
    
    public abstract boolean isDisableDefaultMenu();
    public abstract void setDisableDefaultMenu(boolean disableDefault);
    
}
