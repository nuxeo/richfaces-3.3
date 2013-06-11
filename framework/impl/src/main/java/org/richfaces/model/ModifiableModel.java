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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModelListener;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.ajax4jsf.model.SerializableDataModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.model.impl.expressive.JavaBeanWrapper;
import org.richfaces.model.impl.expressive.ObjectWrapperFactory;
import org.richfaces.model.impl.expressive.WrappedBeanComparator2;
import org.richfaces.model.impl.expressive.WrappedBeanFilter;

/**
 * @author Konstantin Mishin
 *
 */
public class ModifiableModel extends ExtendedDataModel implements Modifiable, LocaleAware {
	
	protected class RowKeyWrapperFactory extends ObjectWrapperFactory {
		
		private class ExtendedJavaBeanWrapper extends JavaBeanWrapper {
			
			private Object key;

			public ExtendedJavaBeanWrapper(Object key, Object o, Map<String, Object> props) {
				super(o, props);
				this.key = key;
			}
			
			public Object getKey() {
				return key;
			}
		}

		public RowKeyWrapperFactory(FacesContext context, String var,
				List<? extends Field> sortOrder) {
			super(context, var, sortOrder);
		}

		@Override
		public JavaBeanWrapper wrapObject(Object key) {
			originalModel.setRowKey(key);
			JavaBeanWrapper wrapObject = super.wrapObject(originalModel.getRowData());
			return new ExtendedJavaBeanWrapper(key, wrapObject.getWrappedObject(), wrapObject.getProperties());
		}

		@Override
		public Object unwrapObject(Object wrapper) {
			return ((ExtendedJavaBeanWrapper) wrapper).getKey();
		}
	}

	private static final Log log = LogFactory.getLog(ModifiableModel.class);
	
	protected List<Object> rowKeys;

	protected ExtendedDataModel originalModel;
	
	protected String var;
	
	protected Locale locale = null;

	public ModifiableModel(ExtendedDataModel originalModel, String var) {
		this.originalModel = originalModel;
		this.var = var;
	}
	
	@Override
	public void addDataModelListener(DataModelListener listener) {
		originalModel.addDataModelListener(listener);
	}

	@Override
	public DataModelListener[] getDataModelListeners() {
		return originalModel.getDataModelListeners();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public Object getRowKey() {
		Object originalModelKey = originalModel.getRowKey();
		int idx = rowKeys.indexOf(originalModelKey);

		if (originalModelKey != null || idx >= 0) {
			return idx;
		} else {
			return null;
		}
	}

	@Override
	public void setRowKey(Object key) {
		Object originalKey = null;
		if(key != null){
			int i = (Integer)key;
			if (i >= 0 &&  i < rowKeys.size()) {
				originalKey = rowKeys.get(i);
			}
		}
		originalModel.setRowKey(originalKey);
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		final SequenceRange seqRange = (SequenceRange) range;
		int rows = seqRange.getRows();
		int rowCount = getRowCount();
		int currentRow = seqRange.getFirstRow();
		if(rows > 0){
			rows += currentRow;
			rows = Math.min(rows, rowCount);
		} else {
			rows = rowCount;
		}
		for (; currentRow < rows; currentRow++) {
			visitor.process(context, currentRow, argument);
		}
	}

	@Override
	public int getRowCount() {
		if (rowKeys == null) {
			return -1;
		} else {
			return rowKeys.size();
		}
	}

	@Override
	public Object getRowData() {
		return originalModel.getRowData();
	}

	@Override
	public int getRowIndex() {
		return rowKeys.indexOf(originalModel.getRowKey());
	}

	@Override
	public Object getWrappedData() {
		return originalModel.getWrappedData();
	}

	@Override
	public boolean isRowAvailable() {
		return originalModel.isRowAvailable();
	}

	@Override
	public void setRowIndex(int rowIndex) {
		Object originalKey = null;
		if (rowIndex >= 0 &&  rowIndex < rowKeys.size()) {
			originalKey = rowKeys.get(rowIndex);
		}
		originalModel.setRowKey(originalKey);
	}

	@Override
	public void setWrappedData(Object data) {
		originalModel.setWrappedData(data);
	}
	
	@Override
	public SerializableDataModel getSerializableModel(Range range) {
		return originalModel.getSerializableModel(range);
	}

	@Override
	public void removeDataModelListener(DataModelListener listener) {
		originalModel.removeDataModelListener(listener);
	}


	public void modify(List<FilterField> filterFields, List<SortField2> sortFields) {
		int rowCount = originalModel.getRowCount();
		
		if (rowCount > 0) {
			rowKeys = new ArrayList<Object>(rowCount);
		} else {
			rowKeys = new ArrayList<Object>();
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {
		
			originalModel.walk(context, new DataVisitor() {
				public void process(FacesContext context, Object rowKey,
						Object argument) throws IOException {
					originalModel.setRowKey(rowKey);
					if (originalModel.isRowAvailable()) {
						rowKeys.add(rowKey);
					}
				}
			}, new SequenceRange(0, -1),
			null);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
				
		filter(filterFields);
		sort(sortFields);

	}

	protected List<Object> filter(List<FilterField> filterFields) {
		if (filterFields != null && !filterFields.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			List <Object> filteredCollection = new ArrayList<Object>();
			ObjectWrapperFactory wrapperFactory = new RowKeyWrapperFactory(context, var, filterFields);
			
			WrappedBeanFilter wrappedBeanFilter = new WrappedBeanFilter(filterFields, locale);
			wrapperFactory.wrapList(rowKeys);
			for (Object object : rowKeys) {
				if(wrappedBeanFilter.accept((JavaBeanWrapper)object)) {
					filteredCollection.add(object);
				}
			}
			rowKeys = filteredCollection;
			wrapperFactory.unwrapList(rowKeys);
		}
		return rowKeys;
	}

	protected void sort(List<SortField2> sortFields) {
		if (sortFields != null && !sortFields.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			ObjectWrapperFactory wrapperFactory = new RowKeyWrapperFactory(
					context, var, sortFields);
			
			WrappedBeanComparator2 wrappedBeanComparator = new WrappedBeanComparator2(
				sortFields, locale);
			wrapperFactory.wrapList(rowKeys);
			Collections.sort(rowKeys, wrappedBeanComparator);
			wrapperFactory.unwrapList(rowKeys);
		}
	}
}
