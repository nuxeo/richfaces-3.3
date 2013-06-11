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

import java.io.IOException;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResource;

/**
 * Interface for renderers, for wich nessesary insert script or style resource in header.
 * This is the second revision of {@link HeaderResourceProducer} interface that returns 
 * arrays of {@link InternetResource} instead of {@link Set}
 * @author nbelaevski@exadel.com
 *
 */
public interface HeaderResourceProducer2 {
	
	public void encodeToHead(FacesContext context, UIComponent component, ProducerContext pc) throws IOException;
}
