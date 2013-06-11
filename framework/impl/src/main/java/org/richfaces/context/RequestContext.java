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

package org.richfaces.context;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public final class RequestContext {

	private static final String REQUEST_ATTRIBUTE_NAME = RequestContext.class.getName();
	
	private Map<String, Object> attributesMap = null;
	
	private RequestContext() {
		super();
	}
	
	public static RequestContext getInstance() {
		return getInstance(FacesContext.getCurrentInstance());
	}

	public static RequestContext getInstance(FacesContext context) {
		ExternalContext externalContext = context.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		
		RequestContext requestContext = (RequestContext) requestMap.get(REQUEST_ATTRIBUTE_NAME);
		if (requestContext == null) {
			requestContext = new RequestContext();

			requestMap.put(REQUEST_ATTRIBUTE_NAME, requestContext);
		}
		
		return requestContext;
	}

	public Object getAttribute(String name) {
		Object result = null;
		
		if (attributesMap != null) {
			result = attributesMap.get(name);
		}
		
		return result;
	}
	
	public void setAttribute(String name, Object value) {
		if (value != null) {
			if (attributesMap == null) {
				attributesMap = new HashMap<String, Object>();
			}

			attributesMap.put(name, value);
		} else {
			if (attributesMap != null) {
				attributesMap.remove(name);
			}
		}
	}
	
}
