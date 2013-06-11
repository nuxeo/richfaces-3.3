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

package org.richfaces.renderkit;

import java.util.Map;

import org.ajax4jsf.component.UIDataAdaptor;

/**
 * Private class for keep reference to table and intermediate iteration values ( current row styles, events etc )
 * @author shura
 *
 */
public class TableHolder {
	private UIDataAdaptor table;
	private int rowCounter;
	private int gridRowCounter;
	private String[] rowClasses;
	private String[] columnsClasses;

	/**
	 * @param table
	 */
	public TableHolder(UIDataAdaptor table) {
		this.table = table;
		this.rowCounter = 0;
		this.gridRowCounter = 0;
		Map<String, Object> attributes = table.getAttributes();
		String classes = (String) attributes.get("rowClasses");
		if(null != classes){
			rowClasses=classes.split(",");
		}
		classes = (String) attributes.get("columnClasses");
		if(null != classes){
			columnsClasses=classes.split(",");
		}
	}

	/**
	 * @return the table
	 */
	public UIDataAdaptor getTable() {
		return this.table;
	}

	/**
	 * @return the rowCounter
	 */
	public int getRowCounter() {
		return this.rowCounter;
	}
	
	/**
	 * Get current rendered row number, and increment to next value.
	 * @return the rowCounter
	 */
	public int nextRow() {
		return ++rowCounter;
	}
	
	public String getRowClass() {
		int row = rowCounter;
		return getRowClass(row);
	}

	public String getRowClass(int row) {
		String rowClass = null;
		if(null != rowClasses){
			rowClass = rowClasses[row%rowClasses.length];
		}
		return rowClass;
	}
	
	public String getColumnClass(int columnNumber) {
		String columnClass = null;
		if(null != columnsClasses){
			columnClass = columnsClasses[columnNumber%columnsClasses.length];
		}
		return columnClass;
	}

	/**
	 * @return the gridRowCounter
	 */
	public int getGridRowCounter() {
		return this.gridRowCounter;
	}

	/**
	 * @param gridRowCounter the gridRowCounter to set
	 */
	public void setGridRowCounter(int gridRowCounter) {
		this.gridRowCounter = gridRowCounter;
	}
}
