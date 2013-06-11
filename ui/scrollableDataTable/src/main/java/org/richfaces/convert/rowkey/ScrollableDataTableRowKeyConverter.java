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
package org.richfaces.convert.rowkey;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.richfaces.model.ScrollableTableDataModel.SimpleRowKey;

/**
 * Converter for {@link SimpleRowKey} 
 * @author Maksim Kaszynski
 *
 */
public class ScrollableDataTableRowKeyConverter implements Converter {

	
	public static final String CONVERTER_ID = "org.richfaces.convert.rowkey.ScrollableDataTableRowKeyConverter";
	
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			String prefix = SimpleRowKey.PREFIX;
			if (value != null && value.startsWith(prefix)) {
				int i = Integer.parseInt(value.substring(prefix.length()));
				return new SimpleRowKey(i);
			}
			return new Integer(value);
		} catch(Exception e) {
			throw new ConverterException("Unable to convert value " + value + "to " + SimpleRowKey.class, e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value instanceof SimpleRowKey || value instanceof Integer) {
			return value.toString();
		}
		throw new ConverterException("Value " + value + " is not supported by this converter");
	}

}
