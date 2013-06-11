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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;

import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:43 $
 *
 */
public class ResourceElement extends ElementBase {

	private Map attrs = new HashMap();
	
	private Map<String, Object> createParameterMap(TemplateContext context) {
	    //usefullness of lazy evaluation here is doubtful for me - nick 
	    Map<String, Object> result = new HashMap<String, Object>();
	    
	    for (Object object : getChildren()) {
		if (object instanceof AttributeElement) {
		    AttributeElement attribute = (AttributeElement) object;
		    
		    result.put(attribute.getName(), attribute.getValue(context));
		}
	    }
	    
	    return result;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		return attrs.put(key, value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		return attrs.get(key);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeBegin(TemplateContext context) throws IOException {
		InternetResource resource = findResource(context);
		if(null !=resource){
		    Object dataParameter = context.getComponent();
		    if (dataParameter == null) {
			dataParameter = createParameterMap(context);
		    }

		    resource.encodeBegin(context.getFacesContext(), dataParameter, attrs);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#encode(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(TemplateContext context) throws IOException {
		InternetResource resource = findResource(context);
		if(null !=resource){
		    Object dataParameter = context.getComponent();
		    if (dataParameter == null) {
			dataParameter = createParameterMap(context);
		    }

		    resource.encodeEnd(context.getFacesContext(), dataParameter);
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.RootElement#getString(javax.faces.render.Renderer, javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public String getString(TemplateContext context) throws FacesException {
		InternetResource resource = findResource(context);
		if(null !=resource){
		    Object dataParameter = context.getComponent();
		    if (dataParameter == null) {
			dataParameter = createParameterMap(context);
		    }

		    return resource.getUri(context.getFacesContext(), dataParameter);
		}
		return "";
	}

	private InternetResource findResource(TemplateContext context) {
		InternetResource resource;
		String path = (String) getValue(context);
		RendererBase renderer = context.getRenderer();
		if (renderer != null)  {
			resource = renderer.getResource(path);
		} else {
			resource = InternetResourceBuilder.getInstance().createResource(null, path);
		}
		
		return resource;
	}
	
	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.RESOURCE_TAG;
	}
	
}
