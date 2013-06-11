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

package org.ajax4jsf.renderkit;

import java.util.LinkedHashSet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Interface for renderers, for wich nessesary insert script or style resource in header.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:50 $
 *
 */
public interface HeaderResourceProducer {
	
	/**
	 * Return set of strings with URI's of nessesary scripts.
	 * Use linked set to preserve insertion order
	 * @param context - current faces context.
	 * @param component TODO
	 * @return - set of URI's or null
	 */
	public LinkedHashSet<String> getHeaderScripts(FacesContext context, UIComponent component);

	/**
	 * Return set of strings with URI's of nessesary CSS styles.
	 * Use linked set to preserve insertion order.
	 * @param context - current faces context.
	 * @param component TODO
	 * @return - set of URI's or null
	 */
	public LinkedHashSet<String> getHeaderStyles(FacesContext context, UIComponent component);

}
