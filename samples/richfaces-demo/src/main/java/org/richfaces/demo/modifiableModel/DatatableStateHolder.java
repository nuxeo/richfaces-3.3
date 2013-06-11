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

package org.richfaces.demo.modifiableModel;

import java.util.HashMap;
import java.util.Map;

import org.richfaces.model.SortOrder;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class DatatableStateHolder {

	private Map<String, SortOrder> sortOrders = new HashMap<String, SortOrder>();

	private Map<String, Object> columnFilterValues = new HashMap<String, Object>();

	public Map<String, Object> getColumnFilterValues() {
		return columnFilterValues;
	}

	public void setColumnFilterValues(Map<String, Object> columnFilterValues) {
		this.columnFilterValues = columnFilterValues;
	}

	public Map<String, SortOrder> getSortOrders() {
		return sortOrders;
	}

	public void setSortOrders(Map<String, SortOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}
	
}
