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


package org.richfaces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */
public class ConvertableDemoBeanConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (value == null || value.length() == 0) {
			return null;
		}
		
		String[] split = value.split(":");
		
		return new ConvertableDemoBean(split[0], Integer.valueOf(split[1]));
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null) {
			return "";
		}

		ConvertableDemoBean convertableDemoBean = (ConvertableDemoBean) value;
		return convertableDemoBean.getName() + ":" + convertableDemoBean.getValue();
	}

}
