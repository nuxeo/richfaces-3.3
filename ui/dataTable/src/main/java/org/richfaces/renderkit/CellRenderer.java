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

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererBase;

/**
 * @author shur
 *	modified by Alexej Kushunin
 *
 */
public class CellRenderer extends RendererBase {
	
	public String styleClass(FacesContext context , UIComponent component){
		StringBuffer styleClass = new StringBuffer();
		// Construct predefined classes
		Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
		Object parentPredefined = requestMap.get(AbstractRowsRenderer.SKIN_CELL_CLASS_KEY);
		if (null != parentPredefined) {
			styleClass.append(parentPredefined).append(" ");			
		} else {
			styleClass.append("dr-table-cell rich-table-cell ");
		}
		// Append class from parent component.
		Object parent = requestMap.get(AbstractRowsRenderer.CELL_CLASS_KEY);
		if (null != parent) {
			styleClass.append(parent).append(" ");
		}
		Object custom = component.getAttributes().get("styleClass");
		if (null != custom) {
			styleClass.append(custom);
		}
		return styleClass.toString();
	}
	
	protected void doEncodeBegin(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {
		
		super.doEncodeBegin(writer, context, component);
		java.lang.String clientId = component.getClientId(context);
		boolean isHeader = (styleClass(context, component)).contains("header");
        if(isHeader)
        {
        	writer.startElement("th", component);
        	
        }else{
        	
        	writer.startElement("td", component);
        }
        
        
		getUtils().writeAttribute(writer, "class", styleClass(context,component) );
		getUtils().writeAttribute(writer, "id", clientId );
		getUtils().encodeAttributesFromArray(context,component,new String[] {
			    "abbr" ,
				    "align" ,
				    "axis" ,
				    "bgcolor" ,
				    "char" ,
				    "charoff" ,
				    "colspan" ,
				    "dir" ,
				    "headers" ,
				    "height" ,
				    "lang" ,
				    "nowrap" ,
				    "onclick" ,
				    "ondblclick" ,
				    "onkeydown" ,
				    "onkeypress" ,
				    "onkeyup" ,
				    "onmousedown" ,
				    "onmousemove" ,
				    "onmouseout" ,
				    "onmouseover" ,
				    "onmouseup" ,
				    "rowspan" ,
				    "scope" ,
				    "style" ,
				    "title" ,
				    "valign" ,
				    "width" ,
				    "xml:lang" });
		
	}
	
	
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context,	UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.doEncodeEnd(writer, context, component);
		boolean isHeader = (styleClass(context, component)).contains("header");
        if(isHeader)
        {
        	writer.endElement("th");
        	
        }else{
        	
        	writer.endElement("td");
        }
	}
	
	protected Class<? extends UIComponent> getComponentClass() {
		
		return UIColumn.class;
	}

}
