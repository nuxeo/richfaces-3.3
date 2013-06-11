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
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.RendererBase;
import org.xml.sax.SAXException;


/**
 * Public interface for compiled XML rendered template.
 * {@link javax.faces.render.Renderer} must simple call xml.encode(this,context,component); 
 * for produce {@link javax.faces.context.ResponseWriter} events.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:49 $
 *
 */
public interface PreparedTemplate {
	
	/**
	 * Encode this XML component. May be simple send element and attributes,
	 * call specified Renderer or RendererUtils method etc. Can encode children
	 * elements ( if exist ).
	 * @param renderer
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void encode(RendererBase renderer, FacesContext context, UIComponent component) throws IOException;
	
	public void encode(TemplateContext context) throws IOException;

	public void encode(TemplateContext context,String breakPoint) throws IOException;
	/**
	 * @return List of childrens of this component.
	 */
	public List getChildren();
	
	/**
	 * Append child element to current.
	 * @param child
	 * @throws SAXException 
	 */
	public void addChild(PreparedTemplate child) throws SAXException;
	
	/**
	 * Set parent element in compiled tree for template.
	 * @param parent
	 * @throws SAXException 
	 */
	public void setParent(PreparedTemplate parent) throws SAXException;
	
	/**
	 * @return tag name for element.
	 */
	public String getTag();

	/**
	 * @return Returns the value. If is EL expression valueBinding - evaluate in.
	 */
	public Object getValue(TemplateContext context);

}
