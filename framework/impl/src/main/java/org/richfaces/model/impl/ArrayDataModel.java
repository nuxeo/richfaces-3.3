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
package org.richfaces.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.richfaces.model.SortOrder;

/**
 * @author Maksim Kaszynski
 *
 */
public class ArrayDataModel extends SimpleGridDataModel {

	private Object [] data;

	public ArrayDataModel() {
		this(null);
	}
	
	public ArrayDataModel(Object [] data) {
		super();
		setWrappedData(data);
	}

	

	/* (non-Javadoc)
	 * @see org.richfaces.model.ScrollableTableDataModel#loadData(int, int, org.richfaces.model.SortOrder)
	 */
	public List loadData(int startRow, int endRow, SortOrder sortOrder) {
		
		if (data != null && getRowCount() > 0) {
			Object [] sortedList = data;
			
			if (sortOrder != null) {

				sortedList = new Object[data.length];
				
				System.arraycopy(data, 0, sortedList, 0, data.length);
				
				Comparator comparator = createComparator(sortOrder);
				
				if (comparator == null) {
					
					Arrays.sort(sortedList);
				
				} else {
					
					Arrays.sort(sortedList, comparator);
				
				}
				
			}
			
			Object [] subArray = new Object[endRow - startRow];
			
			System.arraycopy(sortedList, startRow, subArray, 0, subArray.length);
			
			return Arrays.asList(subArray);
		}
		
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	public int getRowCount() {
		return  data == null ? 0 : data.length;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	public void setWrappedData(Object data) {
		this.data = (Object[]) data;
	}
}
