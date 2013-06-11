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

package org.ajax4jsf.gwt.jsf;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.gwt.client.GwtFacesEvent;
import org.ajax4jsf.gwt.client.GwtFacesResult;

/**
 * @author shura
 * 
 */
public interface GwtComponent {

	/**
	 * @return name of GWT module for component
	 */
	public String getModuleName();
	
	/**
	 * Get name of widget in module.
	 * @return widget name for this component ( or null if not important, in case of only one widget in module )
	 */
	public String getWidgetName();

	/**
	 * For base renderer, create Map<String,String> widget parameters.
	 * This pagameters can be get by {@link org.ajax4jsf.gwt.client.ComponentEntryPoint#getWidgetParams(String )}
	 * For more complex cases, developer must create own renderer.
	 * @return
	 */
	public Map getWidgetParameters();

}
