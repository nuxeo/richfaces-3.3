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
package org.richfaces.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModelListener;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SerializableDataModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation stores last loaded data, so no additional requests to db will be performed
 * Acts as a proxy between the component and original data model
 * @author Maksim Kaszynski
 *
 */
public class DataModelCache extends ScrollableTableDataModel {

	private static Log log = LogFactory.getLog(DataModelCache.class);
	
	private static class DataRange {
		private int startRow;
		private int endRow;
		private SortOrder sortOrder;
		private List loadedData;
		
		public DataRange(int startRow, int endRow, SortOrder sortOrder,
				List loadedData) {
			super();
			this.startRow = startRow;
			this.endRow = endRow;
			this.sortOrder = sortOrder;
			this.loadedData = loadedData;
		}

		public boolean match(int s, int e, SortOrder sortOrder) {
			return s == startRow 
				&& e == endRow 
				&& sortOrdersMatch(sortOrder, this.sortOrder);
		}
		
		private boolean sortOrdersMatch(SortOrder sortOrder1, SortOrder sortOrder2) {
			boolean result = sortOrder1 == sortOrder2;
			
			if (sortOrder1 != null && sortOrder2 != null) {
				result = sortOrder1.equals(sortOrder2);
			}
			
			return result;
		}
		
	}
	
	private Map secondaryMapping = new HashMap();
	
	private DataRange dataRange;
	
	private ScrollableTableDataModel scrollableTableDataModel;

	private int rowCount = Integer.MIN_VALUE;
	
	public DataModelCache(ScrollableTableDataModel scrollableTableDataModel) {
		super();
		this.scrollableTableDataModel = scrollableTableDataModel;
		
		if (log.isTraceEnabled()) {
			log.trace("initializing with " + scrollableTableDataModel);
		}
	}

	public void addDataModelListener(DataModelListener listener) {
		scrollableTableDataModel.addDataModelListener(listener);
	}

	public DataModelListener[] getDataModelListeners() {
		return scrollableTableDataModel.getDataModelListeners();
	}

	public Object getObjectById(Object id) {
		
		if (log.isDebugEnabled()) {
			log.debug("Trying to get object by id"  + id);
		}
		//First try to find data in inner cache
		//If not found - get it from original model
		Object cached = secondaryMapping.get(id);
		
		if (cached == null) {
			
			if (log.isDebugEnabled()) {
				log.debug("Cache miss " + id + " falling back to original model");
			}
			
			cached = scrollableTableDataModel.getObjectById(id);
			secondaryMapping.put(id, cached);

		}
		
		if (log.isDebugEnabled()) {
			log.debug("At last found element " + cached);
		}
		
		return cached;
	}

	public int getRowCount() {
		if (rowCount == Integer.MIN_VALUE) {
			rowCount = scrollableTableDataModel.getRowCount();
		}
		return rowCount;
	}

	public Object getRowData() {
		Object secondaryMapped = secondaryMapping.get(getRowKey());
		
		if (secondaryMapped == null) {
			secondaryMapped = super.getRowData();
		}
		return secondaryMapped;
	}

	public int getRowIndex() {
		return scrollableTableDataModel.getRowIndex();
	}

	public Object getRowKey() {
		return scrollableTableDataModel.getRowKey();
	}

	public SerializableDataModel getSerializableModel(Range range) {
		return scrollableTableDataModel.getSerializableModel(range);
	}

	public Object getWrappedData() {
		return scrollableTableDataModel.getWrappedData();
	}

	public boolean isRowAvailable() {
		return secondaryMapping.containsKey(getRowKey()) || super.isRowAvailable();
	}

	public List loadData(int startRow, int endRow, SortOrder sortOrder) {
		if (dataRange == null || !dataRange.match(startRow, endRow, sortOrder)) {
			List data = scrollableTableDataModel.loadData(startRow, endRow, sortOrder);
			dataRange = new DataRange(startRow, endRow, sortOrder, data);
		}
		return dataRange.loadedData;
	}

	public void removeDataModelListener(DataModelListener listener) {
		scrollableTableDataModel.removeDataModelListener(listener);
	}

	public void setRowIndex(int arg0) {
		scrollableTableDataModel.setRowIndex(arg0);
	}

	public void setRowKey(Object key) {
		scrollableTableDataModel.setRowKey(key);
		super.setRowKey(key);
	}

	public void setWrappedData(Object arg0) {
		scrollableTableDataModel.setWrappedData(arg0);
	}
	
	public Object getId(Object o) {
		return scrollableTableDataModel.getId(o);
	}
	
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		// TODO Auto-generated method stub
		super.walk(context, visitor, range, argument);
	}
	/*
	 * FIXME: see superclass
	@Override
	public void setSortOrder(SortOrder sortOrder) {
		scrollableTableDataModel.setSortOrder(sortOrder);
	}
	*/
}
