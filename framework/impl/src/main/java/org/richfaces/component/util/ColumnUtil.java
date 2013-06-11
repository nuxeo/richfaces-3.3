/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.component.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.richfaces.component.Column;

/**
 * @author Maksim Kaszynski
 *
 */
public class ColumnUtil {
	
	/**
	 * Extract sort expression from the column
	 * 
	 * @param column
	 * @return logical representation of sort field assigned to column component given
	 * In case of EL-expression, an expression string is returned instead of value - so that EL-expression can be evaluated later;
	 * If there's literal value of dedicated attribute is specified, return it;
	 * If attribute is not set, fall aback to component id
	 */
	
	public static String getColumnSorting(UIComponent column) {
		
		UIComponent component = (UIComponent) column;
		
		ValueExpression binding = component.getValueExpression("sortExpression");
		
		if (binding != null) {
			return binding.getExpressionString();
		}
		
		if (component instanceof Column) {
			Column col = (Column) component;
			String sorting = col.getSortExpression();
			
			if (sorting != null) {
				return sorting;
			}
		}
		
		
		return component.getId();
	}
	
	
	public static boolean isSortable(UIComponent component) {
		if (component instanceof Column) {
			return ((Column) component).isSortable();
		}
		return Boolean.valueOf(String.valueOf(component.getAttributes().get("sortable")));
	}
	
}
