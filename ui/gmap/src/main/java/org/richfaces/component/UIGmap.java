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

import java.util.Locale;

import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * JSF component class
 *
 */
public abstract class UIGmap extends UIComponentBase {
	
	public static final String COMPONENT_TYPE = "org.richfaces.Gmap";
	
	private static final String COMPONENT_FAMILY = "org.richfaces.Gmap";

	/**
	 * Returns default <code>Locale</code>.
	 * @return default <code>Locale</code>
	 */
	protected Locale getDefaultLocale() {
	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    if (facesContext != null) {
		UIViewRoot viewRoot = facesContext.getViewRoot();
		if (viewRoot != null) {
		    Locale locale = viewRoot.getLocale();
		    if (locale != null) {
			return locale;
		    }
		}
	    }

	    return Locale.US;
	}
}
