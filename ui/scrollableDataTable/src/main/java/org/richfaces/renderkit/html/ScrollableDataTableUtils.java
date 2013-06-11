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

package org.richfaces.renderkit.html;

import javax.faces.component.UIComponent;

import org.richfaces.component.UIScrollableDataTable;

/**
 * @author Maksim Kaszynski
 *
 */
public class ScrollableDataTableUtils {
	
	public static final String FROZEN_COL_COUNT_ATTR = "frozenColCount";
	public static final String CLIENT_ROW_KEY = "clientIndex";
	
	public static int getFrozenColumnsCount(UIScrollableDataTable grid) {
		return getIntOr0(grid, FROZEN_COL_COUNT_ATTR);
	}
	
	public static int getClientRowIndex(UIScrollableDataTable grid) {
		return getIntOr0(grid, CLIENT_ROW_KEY);
	}
	
	public static int getIntOr0(UIComponent grid, String attribute) {
		Object value = grid.getAttributes().get(attribute);
		int i = 0;
		if (value instanceof Number) {
			i = ((Number) value).intValue();
		}
		
		return i;
	}
	
}
