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
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for data models
 * Subclasses must implement {@link #loadData(int, int, SortOrder)}
 * For certain features (like selection and sorting) to work correctly together,
 * both {@link #getId(Object)} and {@link #getObjectById(Object)}
 * methods need to be overriden
 * 
 * @author Maksim Kaszynski
 *
 */
public abstract class ScrollableTableDataModel<T> extends ExtendedDataModel {
	
	/**
	 * Simple implementation - index-based row key
	 * @author Maksim Kaszynski
	 *
	 */
	public static class SimpleRowKey extends Number implements Serializable{

		private static final long serialVersionUID = 1L;
		
		public static final String PREFIX = "srk";
		private int i;

		public SimpleRowKey(int i) {
			super();
			this.i = i;
		}
		
		public int intValue() {
			return i;
		}
		
		public String toString() {
			return PREFIX + String.valueOf(i);
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final SimpleRowKey other = (SimpleRowKey) obj;
			if (i != other.i)
				return false;
			return true;
		}
		@Override
		public double doubleValue() {
			return i;
		}
		@Override
		public float floatValue() {
			return i;
		}
		@Override
		public long longValue() {
			return i;
		}
		
	}

	
	private static final Log log = LogFactory.getLog(ScrollableTableDataModel.class);
	
	private Object rowKey;

	private Map<Object, T> mapping;
	
	protected SortOrder lastSortOrder;
	
	
	/**
	 * Load range of data items from the source.
	 * Starting from startRow, and up to but excluding endRow
	 * @param startRow
	 * @param endRow
	 * @param sortOrder
	 * @return list of ordered data
	 */
	public abstract List<T> loadData(int startRow, int endRow, SortOrder sortOrder);

	
	/**
	 * Load data range, and iterate over it
	 */
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		
		if (log.isTraceEnabled()) {
			log.trace("Starting walk");
		}
		
		ScrollableTableDataRange sequenceRange = (ScrollableTableDataRange) range;
		
		int startIndex = sequenceRange.getFirst();
		int last = sequenceRange.getLast();
		
		lastSortOrder = sequenceRange.getSortOrder();
		
		List<T> objects = loadData(startIndex, last, lastSortOrder);
		
		mapping = new HashMap<Object, T>();
		
		for (int i = 0; i < objects.size(); i++,startIndex++) {
			T data = objects.get(i);
			Object key = getId(data);
			
			if (key == null) {
				key = new SimpleRowKey(startIndex);
			}
			
			mapping.put(key, data);
			
			visitor.process(context, key, argument);
			
		}

		if (log.isTraceEnabled()) {
			log.trace("Ending walk");
		}
		
	}
	
	
	/**
	 * This method is the reverse of {@link #getId(Object)}
	 * If you override this method, you need to override {@link #getId(Object)} as well
	 * @param id
	 * @return
	 */
	public T getObjectById(Object id) {
		
		if (id instanceof SimpleRowKey) {
			int i = ((SimpleRowKey) id).intValue();
			
			List<T> l = loadData(i, i + 1, lastSortOrder);
			
			return l.get(0);
			
		}
		
		return null;
	}
	
	/**
	 * Implementations may override it to provide domain-specific searches
	 * Id should be serializable
	 * Default implementation returns <code>null</code> anyway
	 * If you override this method, you need to override {@link #getObjectById(Object)} as well
	 * @param o
	 * @return
	 */
	public Object getId(T o) {
		return null;
	}
	
	
	public Object getRowData() {
		
		if (mapping != null && mapping.containsKey(rowKey)) {
			return mapping.get(rowKey);	
		} else {
			return loadAndMap(rowKey);
		}

	}
	
	/**
	 * Row indexes navigation is no longer supported
	 */
	public int getRowIndex() {
		return -1;
	}
	
	/**
	 * Quite simple implementation - data will be cached, so the call will be cheap
	 */
	public boolean isRowAvailable() {
		return getRowData() != null;
	}

	/**
	 * Row indexes navigation is no longer supported
	 */
	public void setRowIndex(int index) {
		//if(index != -1)
			//throw new UnsupportedOperationException("setRowIndex");
		//setRowKey(null);
	}

	public Object getRowKey() {
		return rowKey;
	}

	public void setRowKey(Object key) {
		rowKey = key;
	}

	private Object loadAndMap(Object id) {
		
		if (log.isTraceEnabled()) {
			log.trace("loadAndMap " + id);
		}
		
		T t = getObjectById(id);
		if (t != null) {
			if (mapping == null) {
				mapping = new HashMap<Object, T>();
			}
			mapping.put(id, t);
		}
		return t;
	}
	/*
	 * FIXME: This method is most likely redundant
	public void setSortOrder(SortOrder sortOrder) {
		lastSortOrder = sortOrder;
	}
	*/
	
}
