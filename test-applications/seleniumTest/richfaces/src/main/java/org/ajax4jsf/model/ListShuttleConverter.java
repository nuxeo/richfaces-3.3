/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ListShuttleConverter implements javax.faces.convert.Converter{

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		int index = value.indexOf(':');
		return new ListShuttleItem(Integer.valueOf(value.substring(0, index)), value.substring(index + 1));
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		ListShuttleItem optionItem = (ListShuttleItem) value;
		return optionItem.getNumder() + ":" + optionItem.getName();

	}

	
}