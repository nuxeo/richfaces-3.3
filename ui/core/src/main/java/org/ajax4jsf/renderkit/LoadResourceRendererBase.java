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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.UIResource;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceRenderer;
import org.ajax4jsf.resource.URIInternetResource;

/**
 * @author shura
 *
 */
public class LoadResourceRendererBase extends RendererBase implements UserResourceRenderer2 {
	
	private static final String SCRIPT_COMPONENT_FAMILY="org.ajax4jsf.LoadScript";
	private static final String STYLE_COMPONENT_FAMILY="org.ajax4jsf.LoadStyle";

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return UIResource.class;
	}
	
	public void encodeToHead(FacesContext context, UIComponent component) throws IOException {
		UIResource resource = (UIResource) component;
		Object src = resource.getSrc();
		if(null == src) {
			throw new FacesException("Source for resource is null for component "+resource.getClientId(context));
		}

		String family = resource.getFamily();
		ResourceRenderer renderer = null;
		InternetResource internetResource = null;
		Map<String, Object> attributes = Collections.emptyMap();
		
		if (SCRIPT_COMPONENT_FAMILY.equals(family)) {
			renderer = InternetResourceBuilder.getInstance().getScriptRenderer();
		} else if (STYLE_COMPONENT_FAMILY.equals(family)) {
			renderer = InternetResourceBuilder.getInstance().getStyleRenderer();
			String media = (String) resource.getAttributes().get(HTML.media_ATTRIBUTE);
			if (media != null && media.length() != 0) {
				attributes = new HashMap<String, Object>();
				attributes.put(HTML.media_ATTRIBUTE, media);
			}
		}
		
		if (src instanceof InternetResource) {
			internetResource = (InternetResource) src;
		} else {
			if(resource.isRendered()){
				String uri = context.getApplication().getViewHandler().
					getResourceURL(context, src.toString());
				uri = context.getExternalContext().encodeResourceURL(uri);
				internetResource = new URIInternetResource(uri);
			}
		}

		if (internetResource != null) {
			renderer.encode(internetResource, context, null, attributes);
		}
	}

}
