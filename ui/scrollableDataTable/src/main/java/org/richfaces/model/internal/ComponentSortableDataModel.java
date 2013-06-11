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

package org.richfaces.model.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.richfaces.model.ScrollableTableDataModel;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
import org.richfaces.model.impl.expressive.ObjectWrapperFactory;
import org.richfaces.model.impl.expressive.WrappedBeanComparator;

/**
 * @author Maksim Kaszynski
 *
 */
public class ComponentSortableDataModel extends ScrollableTableDataModel<Object> {

	
	/**
	 * Wrap every list element with reflective sorting object, perform sorting, and then unwrap.
	 * @param context
	 * @param collection
	 * @param sortOrder
	 * @return
	 */
	protected List prepareCollection(FacesContext context, List collection, SortOrder sortOrder) {
		
		ObjectWrapperFactory factory = new ObjectWrapperFactory(context, var, sortOrder);
		final SortField [] fields = sortOrder.getFields();

		
		factory.wrapList(collection);

		Collections.sort(collection, new WrappedBeanComparator(fields));
		
		factory.unwrapList(collection);
		
		
		return collection;
	}
	
	
	
	private List wrappedList;
	DataModel model;
	private String var;
	
	public ComponentSortableDataModel(String var, Object value, SortOrder sortOrder) {
		this.var = var;
		lastSortOrder = sortOrder;
		setWrappedData(value);
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.model.ScrollableTableDataModel#loadData(int, int, org.richfaces.model.SortOrder)
	 */
	public List loadData(int startRow, int endRow, SortOrder sortOrder) {
		List list = null;
		int rc = getRowCount();
		if (startRow < 0) {
			startRow = 0;
		}
		
		if (endRow > rc) {
			endRow = rc;
		}
		if (sortOrder == null) {
			if(model != null) {
				list = new ArrayList(rc);
				for (int i = startRow; i < endRow; i++) {
					model.setRowIndex(i);
					list.add(model.getRowData());
				}
			} else {
				list = wrappedList.subList(startRow, endRow);
			}
		} else {
			if(model != null) {
				list = new ArrayList(rc);
				for (int i = 0; i < rc; i++) {
					model.setRowIndex(i);
					list.add(model.getRowData());
				}
			} else {
				list = new ArrayList(wrappedList);
			}
			list = prepareCollection(FacesContext.getCurrentInstance(), list, sortOrder).subList(startRow, endRow);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	public int getRowCount() {
		if (model != null) {
			return model.getRowCount();
		} else {
			return wrappedList.size();
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		if (model != null) {
			return model;
		} else {
			return wrappedList;
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	public void setWrappedData(Object value) {
		if (value == null) {
			
			wrappedList = new ArrayList();
			
		} else if (value instanceof Object[]) {

			Object [] array = (Object[]) value;
			
			wrappedList = new ArrayList(array.length);
			
			for (int i = 0; i < array.length; i++) {
				wrappedList.add(array[i]);
			}
		
		} else if (value instanceof Collection) {
			
			wrappedList = new ArrayList((Collection) value);
			
		} else if (value instanceof DataModel) {
			model = (DataModel)value;
		} else {
			wrappedList = new ArrayList(1);
			wrappedList.add(value);
		}
	}

}
