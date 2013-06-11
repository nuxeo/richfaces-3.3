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
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

/**
 * @author pawelgo
 *
 */
public class ExtendedTableDataModifiableModel<T> extends ModifiableModel {
	
	//private static final Log log = LogFactory.getLog(ExtendedTableDataModifiableModel.class);
	
	private ExtendedTableDataModel<T> orgModel;
	private boolean sortNeeded = true;
	private boolean filterNeeded = true;
	
	@SuppressWarnings("unchecked")
	public ExtendedTableDataModifiableModel(DataProvider<T> dataProvider, String var) {
		super(new ExtendedTableDataModel<T>(dataProvider), var);
		this.orgModel = (ExtendedTableDataModel<T>)this.originalModel;
	}
	
	public ExtendedTableDataModifiableModel(DataProvider<T> dataProvider) {
		this(dataProvider, null);
	}
	
	@SuppressWarnings("unchecked")
	public ExtendedTableDataModifiableModel(ExtendedTableDataModel<T> dataModel, String var) {
		super(dataModel, var);
		this.orgModel = dataModel;
	}
	
	@Override
	public Object getRowKey() {
		return originalModel.getRowKey();
	}

	@Override
	public void setRowKey(Object key) {
		originalModel.setRowKey(key);
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
			visitor.process(context, rowKeys.get(currentRow), argument);
		}
	}
	
	/**
	 * Resets internal cached data. Call this method to reload data from data
	 * provider on first access for data.
	 */
	public void reset(){
		orgModel.reset();
		rowKeys = null;
		sortNeeded = true;
		filterNeeded = true;
	}
	
	public Object getKey(T o) {
		return orgModel.getKey(o);
	}

	public T getObjectByKey(Object key) {
		return orgModel.getObjectByKey(key);
	}
	
	@Override
	public void modify(List<FilterField> filterFields, List<SortField2> sortFields) {
		if (sortNeeded || filterNeeded){
			if (var == null){
				throw new IllegalStateException("\"var\" model attribute can not be null.");
			}
			super.modify(filterFields, sortFields);
			sortNeeded = false;
			filterNeeded = false;
		}
	}
	
	public void resetSort(){
		sortNeeded = true;
	}
	
	public void resetFilter(){
		filterNeeded = true;
	}
	
	public void setVar(String var){
		this.var = var;
	}
}
