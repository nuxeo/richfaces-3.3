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

import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.resource.InternetResource;


/**
 * @author Maksim Kaszynski
 *
 */
public abstract class PrototypeBasedRendererBase extends RendererBase implements HeaderResourceProducer {

	protected static final String[] EMPTY_ARRAY = {};
	private InternetResource prototypeScript = getResource(PrototypeScript.class.getName());
	
	public LinkedHashSet<String> getHeaderScripts(FacesContext context, UIComponent component) {
		LinkedHashSet<String> scripts = new LinkedHashSet<String>() ; // Collections.singleton(ajaxScript.getUri(context, null));
		scripts.add(prototypeScript.getUri(context, null));
		String[] additionalScripts = getAdditionalScripts();
		for (int i = 0; i < additionalScripts.length; i++) {
			String resource = additionalScripts[i];
			scripts.add(getResource(resource).getUri(context, null));
		}
		return scripts;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.HeaderResourceProducer#getHeaderStyles(javax.faces.context.FacesContext)
	 */
	public LinkedHashSet<String> getHeaderStyles(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String[] getAdditionalScripts() {
		return EMPTY_ARRAY;
	}
	
}
