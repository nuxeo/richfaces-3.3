/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
* 
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License version 2.1 as published by the Free Software Foundation.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

*/
package org.richfaces.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Data model class for table components.<br> 
 * Usage:
 * <pre>
 * ExtendedTableDataModel&lt;SomeDataType&gt; dataModel = new ExtendedTableDataModel&lt;SomeDataType&gt;(new DataProvider &lt;SomeDataType&gt;());
 * </pre>
 * @author pawelgo
 *
 */
public class ExtendedTableDataModel<T> extends ExtendedDataModel implements Serializable{

	private static final long serialVersionUID = 7374505108088114161L;

	private static final Log log = LogFactory.getLog(ExtendedTableDataModel.class);

	private DataProvider<T> dataProvider;
	private Object rowKey;
	private List<Object> wrappedKeys = null;
	//private boolean detached = false;
	private Map<Object, T> wrappedData = new HashMap<Object, T>();

	public ExtendedTableDataModel(DataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.model.SerializableDataModel#update()
	 */
//	public void update() {
//		;
//	}

	/**
	 * This method never called from framework.
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
	 */
	public Object getRowKey() {
		return rowKey;
	}

	/**
	 * This method normally called by Visitor before request Data Row.
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#setRowKey(java.lang.Object)
	 */
	public void setRowKey(Object key) {
		rowKey = key;
	}

	/** 
	 * This is main part of Visitor pattern. Method called by framework many times during request processing. 
	 * (non-Javadoc)
	 * @see org.ajax4jsf.model.ExtendedDataModel#walk(javax.faces.context.FacesContext, org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range, java.lang.Object)
	 */
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		int rowC = getRowCount();
		int firstRow = ((SequenceRange) range).getFirstRow();
		int numberOfRows = ((SequenceRange) range).getRows();
		if (numberOfRows <= 0) {
			numberOfRows = rowC;
		}
		if (wrappedKeys != null) { // Is this serialized model
			// Here we just ignore current Rage and use whatever data was saved in serialized model. 
			// Such approach uses much more getByPk() operations, instead of just one request by range.
			// Concrete case may be different from that, so you can just load data from data provider by range.
			// We are using wrappedKeys list only to preserve actual order of items.
			for (Object key : wrappedKeys) {
				setRowKey(key);
				visitor.process(context, key, argument);
			}
		} else { // if not serialized, than we request data from data provider
			wrappedKeys = new ArrayList<Object>();
			int endRow = firstRow + numberOfRows;
			if (endRow > rowC){
				endRow = rowC; 
			}
			for (T item : loadData(firstRow, endRow)) {
				Object key = getKey(item);
				wrappedKeys.add(key);
				wrappedData.put(key, item);
				visitor.process(context, key, argument);
			}
		}
	}//walk

	/**
	 * Load range of data items from the source.
	 * Starting from startRow, and up to but excluding endRow
	 * @param startRow
	 * @param endRow
	 * @return list of ordered data
	 */
	protected List<T> loadData(int startRow, int endRow) {
		if (log.isDebugEnabled())
			log.debug("load data from range: " + startRow + " - " + endRow);
		if (startRow < 0){
			startRow = 0;
			throw new IllegalArgumentException("Illegal start index value: " + startRow);
		}
		int rowCount = getRowCount();
		if (endRow > rowCount){
			endRow = rowCount;
			throw new IllegalArgumentException("Illegal end index value: " + endRow);
		}
		//load all from provider and get sublist
		return dataProvider.getItemsByRange(startRow, endRow);
		//return dataProvider.getItemsByRange(0, rowCount).subList(startRow, endRow);
	}//loadData

	/**
	 * This method must return actual data rows count from the Data Provider. It is used by pagination control
	 * to determine total number of data items.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	private Integer rowCount; // better to buffer row count locally

	public int getRowCount() {
		if (rowCount == null) {
			rowCount = new Integer(dataProvider.getRowCount());
		} else {
			return rowCount.intValue();
		}
		return rowCount.intValue();
		//return dataProvider.getRowCount();
	}

	/**
	 *  This is main way to obtain data row. It is intensively used by framework. 
	 * We strongly recommend use of local cache in that method. 
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowData()
	 */
	public T getRowData() {
		if (rowKey == null) {
			return null;
		} else {
			return  getObjectByKey(rowKey);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getKey(T o) {
		return dataProvider.getKey(o);
	}

	public T getObjectByKey(Object key) {
		T t = wrappedData.get(key);
		if (t == null){
			t = dataProvider.getItemByKey(key);
			wrappedData.put(key, t);
		}
		return t;
	}

	private Integer rowIndex;

	/**
	 * Unused rudiment from old JSF staff. (non-Javadoc)
	 * 
	 * @see javax.faces.model.DataModel#getRowIndex()
	 */
	public int getRowIndex() {
		//throw new UnsupportedOperationException();
		return rowIndex.intValue();
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		//throw new UnsupportedOperationException();
		this.rowIndex = rowIndex;
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused rudiment from old JSF staff.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	public void setWrappedData(Object data) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Never called by framework.
	 * (non-Javadoc)
	 * @see javax.faces.model.DataModel#isRowAvailable()
	 */
	public boolean isRowAvailable() {
		return getRowData() != null;
	}

	/**
	 * This method suppose to produce SerializableDataModel that will be
	 * serialized into View State and used on a post-back. In current
	 * implementation we just mark current model as serialized. In more
	 * complicated cases we may need to transform data to actually serialized
	 * form.
	 */
//	public SerializableDataModel getSerializableModel(Range range) {
//		if (wrappedKeys != null) {
//			detached = true;
//			// Some activity to detach persistent data from wrappedData map may be taken here.
//			// In that specific case we are doing nothing.
//			return this;
//		} else {
//			return null;
//		}
//	}
	
	/**
	 * Resets internal cached data. Call this method to reload data from data
	 * provider on first access for data.
	 */
	public void reset(){
		wrappedKeys = null;
		wrappedData.clear();
		rowCount = null;
		rowIndex = -1;
		rowKey = null;
	}

	public DataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

}
