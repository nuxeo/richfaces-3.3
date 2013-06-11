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
package org.richfaces.model.impl.expressive;

import java.util.List;
import java.util.Locale;

import org.richfaces.model.ExtendedFilterField;
import org.richfaces.model.FilterField;

/**
 * @author Maksim Kaszynski
 *
 */
public class WrappedBeanFilter implements org.richfaces.model.filter.Filter<JavaBeanWrapper>{
	
	private final List<FilterField> filterFields;
	private Locale currentLocale;
	
	public WrappedBeanFilter(List<FilterField> filterFields) {
		this(filterFields, null);
	}

	public WrappedBeanFilter(List<FilterField> filterFields, Locale locale) {
		this.filterFields = filterFields;
		this.currentLocale = (locale != null ? locale : Locale.getDefault());
	}
	
	public boolean accept(JavaBeanWrapper wrapper) {
		for (FilterField filterField : filterFields) {
			if (filterField instanceof ExtendedFilterField) {
				Object property = wrapper.getProperty(filterField.getExpression().getExpressionString());
				String filterValue = ((ExtendedFilterField)filterField).getFilterValue();
				if(filterValue != null) {
					filterValue = filterValue.trim().toUpperCase(currentLocale);
					if(filterValue.length() > 0) {
						if(property == null || !property.toString().trim().toUpperCase(currentLocale).startsWith(filterValue)) {
							return false;
						}	
					}				
				}				
			} else {
				Object property = wrapper.getProperty(filterField.getExpression().getExpressionString());
				if(!((Boolean)property).booleanValue()) {
					return false;
				}
			}
		}
		return true;
	}

}
