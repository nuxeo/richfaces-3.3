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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.ajax4jsf.Messages;
import org.richfaces.component.UIColorPicker;
import org.richfaces.component.util.MessageUtil;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class IntegerColorConverter implements Converter {

	private static final Pattern NUMBER = Pattern.compile("(\\d+)");
	
	public static final String CONVERTER_ID = "org.richfaces.IntegerColor";

	private int convertToInteger(String colorValue) {
		int result = 0;

		if (colorValue.charAt(0) == '#') {
			result = Integer.parseInt(colorValue.substring(1), 16);
		} else {
			Matcher matcher = NUMBER.matcher(colorValue);
			for (int i = 1; i <= 3; i++) {
				if (!matcher.find()) {
					throw new IllegalArgumentException(colorValue);
				}
			
				if (i != 1) {
					result <<= 8;
				}
				
				result |= (0xFF & Short.parseShort(matcher.group(1)));
			}
		}
		
		return result;
	}
	
	private String convertToString(int value, String colorMode) {
		if (UIColorPicker.COLOR_MODE_RGB.equals(colorMode)) {
			StringBuilder sb = new StringBuilder("rgb(");
			
			sb.append((value & 0xFF0000) >> 16);
			sb.append(", ");
			sb.append((value & 0x00FF00) >> 8);
			sb.append(", ");
			sb.append(value & 0xFF);

			sb.append(")");
			return sb.toString();
		} else {
			StringBuilder sb = new StringBuilder("#");
			String hexString = Integer.toHexString(value);
			for (int i = 0; i < 6 - hexString.length(); i++) {
				sb.append('0');
			}
			sb.append(hexString);
			return sb.toString();
		}
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		if (value != null && value.length() != 0) {
			try {
				return convertToInteger(value);
			} catch (Exception e) {
				Object label = MessageUtil.getLabel(context, component);
				String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

				throw new ConverterException(new FacesMessage(summary), e);
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		if (value != null) {
			try {
				return convertToString((Integer) value, ((UIColorPicker) component).getColorMode());
			} catch (Exception e) {
				Object label = MessageUtil.getLabel(context, component);
				String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

				throw new ConverterException(new FacesMessage(summary), e);
			}
		} else {
			return "";
		}
	}
}
