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

package org.richfaces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class LongColorConverter extends IntegerColorConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		Integer integer = (Integer) super.getAsObject(context, component, value);
		if (integer != null) {
			return Long.valueOf(integer.intValue());
		} else {
			return null;
		}
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		Integer intValue = null;
		if (value != null) {
			intValue = 0xFFFFFF & ((Long) value).intValue();
		}
		
		return super.getAsString(context, component, intValue);
	}
}
