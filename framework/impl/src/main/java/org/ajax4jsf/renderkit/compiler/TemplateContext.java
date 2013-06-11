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

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererBase;


/**
 * Incapsulate all current parameters for encoding template.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:48 $
 *
 */
public class TemplateContext {
	FacesContext facesContext;
	RendererBase renderer;
	UIComponent component;
	Map parameters = new HashMap();
	/**
	 * @param renderer
	 * @param facesContext
	 * @param component
	 */
	public TemplateContext(RendererBase renderer, FacesContext facesContext, UIComponent component) {
		// TODO Auto-generated constructor stub
		this.facesContext = facesContext;
		this.renderer = renderer;
		this.component = component;
	}
	/**
	 * @return Returns the component.
	 */
	public UIComponent getComponent() {
		return component;
	}
	/**
	 * @return Returns the facesContext.
	 */
	public FacesContext getFacesContext() {
		return facesContext;
	}
	/**
	 * @return Returns the renderer.
	 */
	public RendererBase getRenderer() {
		return renderer;
	}
	
	public void putParameter(Object key,Object value) {
		parameters.put(key,value);
	}
	
	public Object getParameter(Object key) {
		return parameters.get(key);
	}
	public void removeParameter(Object key) {
		parameters.remove(key);
		
	}
	public ResponseWriter getWriter() {
		return facesContext.getResponseWriter();
	}

	/**
	 * Create copy of this context for other child component.
	 * Used in iterations and facets for render template on current child
	 * @param child - component for template
	 * @return - new copy of component, with same parameters.
	 */
	public TemplateContext clone(UIComponent child){
		TemplateContext cloned = new TemplateContext(this.renderer,this.facesContext,child);
		cloned.parameters.putAll(this.parameters);
		return cloned;
	}
	
	/**
	 * Return a mutable Map representing the parameters associated wth this TemplateContext
	 */
	public Map getParameters() {
		return parameters; 
	}
}
