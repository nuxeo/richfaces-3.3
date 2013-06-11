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
import java.util.LinkedHashSet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResource;

/**
 * Base renderer for components used JavaScripts and Styles in header. In real
 * class override getScripts or/and getStyles methods
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:58:51 $
 * 
 */
public abstract class HeaderResourcesRendererBase extends RendererBase
		implements HeaderResourceProducer2, HeaderResourceProducer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.HeaderResourceProducer#getHeaderScripts(javax.faces.context.FacesContext)
	 */
	public LinkedHashSet<String> getHeaderScripts(FacesContext context,
			UIComponent component) {
		return getUrisSet(context, getScripts(), component);
	}

	/**
	 * Hoock method to return array of scripts resources to store in head.
	 * 
	 * @return
	 */
	protected InternetResource[] getScripts() {
		// return nothing by default.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.HeaderResourceProducer#getHeaderStyles(javax.faces.context.FacesContext)
	 */
	public LinkedHashSet<String> getHeaderStyles(FacesContext context,
			UIComponent component) {
		return getUrisSet(context, getStyles(), component);
	}

	/**
	 * Hoock method to return array of styles resources to store in head
	 * 
	 * @return
	 */
	protected InternetResource[] getStyles() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param context
	 * @param resources
	 * @param component
	 *            TODO
	 * @return
	 */
	private LinkedHashSet<String> getUrisSet(FacesContext context,
			InternetResource[] resources, UIComponent component) {
		if (null != resources) {
			LinkedHashSet<String> uris = new LinkedHashSet<String>(); // Collections.singleton(ajaxScript.getUri(context,
			// null));
			for (int i = 0; i < resources.length; i++) {
				InternetResource resource = resources[i];
				uris.add(resource.getUri(context, component));
			}
			return uris;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#preEncodeBegin(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
//	protected void preEncodeBegin(FacesContext context, UIComponent component)
//			throws IOException {
//		if ((null == context.getExternalContext().getRequestMap().get(
//				BaseFilter.RESPONSE_WRAPPER_ATTRIBUTE))
//		/* && (!AjaxRendererUtils.isAjaxRequest(context)) */) {
//			// Filter not used - encode scripts and CSS before component.
//			ExternalContext externalContext = context.getExternalContext();
//			String scriptStrategy = externalContext
//					.getInitParameter(InternetResourceBuilder.LOAD_SCRIPT_STRATEGY_PARAM);
//			if (null == scriptStrategy || !InternetResourceBuilder.LOAD_NONE.equals(scriptStrategy)) {
//					encodeResourcesArray(context, component, getScripts());
//			}
//			String styleStrategy = externalContext
//					.getInitParameter(InternetResourceBuilder.LOAD_STYLE_STRATEGY_PARAM);
//			if (null != styleStrategy || !InternetResourceBuilder.LOAD_NONE.equals(styleStrategy)) {
//					encodeResourcesArray(context, component, getStyles());
//			}
//		}
//	}

	/**
	 * @param context
	 * @param component
	 * @param resources
	 * @throws IOException
	 */
	protected void encodeResourcesArray(FacesContext context,
			UIComponent component, InternetResource[] resources)
			throws IOException {
		if (resources != null) {
			for (int i = 0; i < resources.length; i++) {
				resources[i].encode(context, component);
			}
		}
	}

	public void encodeToHead(FacesContext context, UIComponent component, ProducerContext pc) throws IOException {

		if (pc.isProcessScripts()) {
			encodeResourcesArray(context, component, getScripts());
		}

		if (pc.isProcessStyles()) {
			encodeResourcesArray(context, component, getStyles());
		}
	}
}
