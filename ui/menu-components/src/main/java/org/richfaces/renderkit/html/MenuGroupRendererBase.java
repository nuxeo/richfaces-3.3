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

import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.util.ViewUtil;



public class MenuGroupRendererBase extends HeaderResourcesRendererBase {
	
	private MenuItemRendererDelegate delegate;
	
	public MenuGroupRendererBase() {
		delegate = new MenuItemRendererDelegate();
	}

	protected Class getComponentClass() {
		return UIMenuGroup.class;
	}

	public boolean getRendersChildren() {
		return true;
	}

	private String getSpacerUri(FacesContext context, UIMenuGroup menuGroup) {
		return getResource("images/spacer.gif").getUri(
				context, menuGroup);
	}
	
	public void initializeStyleClasses(FacesContext context, UIMenuGroup menuGroup) {
		ComponentVariables variables =
			ComponentsVariableResolver.getVariables(this, menuGroup);
		delegate.initializeStyles(context, menuGroup, menuGroup.isDisabled(), variables);
	}
	
	public void initializeResources(FacesContext context, UIMenuGroup menuGroup)
	throws IOException {
		ComponentVariables variables =
			ComponentsVariableResolver.getVariables(this, menuGroup);

		String icon = ViewUtil.getResourceURL(menuGroup.getIcon());
		if (icon == null || icon.length() == 0) {
			icon = getSpacerUri(context, menuGroup);
		}

		variables.setVariable("icon", icon);
		
		String iconDisabled = ViewUtil.getResourceURL(menuGroup.getIconDisabled());
		if (iconDisabled == null || iconDisabled.length() == 0) {
			iconDisabled = getSpacerUri(context, menuGroup);
		}

		variables.setVariable("iconDisabled", iconDisabled);
		
		String actualIcon = menuGroup.isDisabled() ? iconDisabled : icon;
		variables.setVariable("actualIcon", actualIcon);

		String iconFolder = ViewUtil.getResourceURL(menuGroup.getIconFolder());
		if (iconFolder == null || iconFolder.length() == 0) {
			iconFolder = getSpacerUri(context, menuGroup);
		}

		variables.setVariable("iconFolder", iconFolder);

		String iconFolderDisabled = ViewUtil.getResourceURL(menuGroup.getIconFolderDisabled());
		if (iconFolderDisabled == null || iconFolderDisabled.length() == 0) {
			iconFolderDisabled = getSpacerUri(context, menuGroup);
		}

		variables.setVariable("iconFolderDisabled", iconFolderDisabled);
		
		String actualIconFolder = menuGroup.isDisabled() ? iconFolderDisabled : iconFolder;
		variables.setVariable("actualIconFolder", actualIconFolder);
	}
	
	protected UIComponent getParentMenu(FacesContext context, UIMenuGroup menuGroup) {
		return delegate.getParentMenu(context, menuGroup);
	}
	
    protected String collectInlineStyles(FacesContext context, UIMenuItem menuItem, boolean isOnmouseover) {
    	return delegate.collectInlineStyles(context, menuItem, isOnmouseover);
    }
    
	@Deprecated
	protected String processInlineStyles(FacesContext context,
			UIMenuGroup menuGroup, boolean isOnmouseover) {

		return delegate.processInlineStyles(context, menuGroup, isOnmouseover);
	}
}
