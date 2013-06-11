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

package org.richfaces.renderkit;

import org.richfaces.component.UIColumn;
import org.richfaces.component.UIExtendedDataTable;

/**
 * Extended table holder. It keeps additional information like last row key, last row data
 * and information about grouping.
 * @author pawelgo
 *
 */
public class ExtendedTableHolder extends TableHolder {
	
	private Object lastData = null;
	private Object lastKey = null;
	//private int curRowId = -1;
	private int groupRowCounter = -1;
	
	private boolean groupingOn = false;
	private boolean firstRow = true;
	private UIColumn groupingColumn = null;
	private String groupingColumnLabel = null;
	
	/**
	 * 
	 * @param table
	 */
	public ExtendedTableHolder(UIExtendedDataTable table) {
		super(table);
		lastData = null;
		lastKey = null;
		groupRowCounter = 0;
		groupingOn = table.isGroupingOn();
		groupingColumnLabel = "";
		if (groupingOn){
			groupingColumn = table.getGroupByColumn();
			if (groupingColumn != null){
				groupingColumnLabel = (String)groupingColumn.getAttributes().get("label");;
			}
		}
	}
	
	public boolean isFirstRow() {
        return firstRow;
    }

    public void setFirstRow(boolean firstRow) {
        this.firstRow = firstRow;
    }



    public UIExtendedDataTable getTable() {
		return (UIExtendedDataTable)super.getTable();
	}

	public Object getLastData() {
		return lastData;
	}

	public void setLastData(Object lastData) {
		this.lastData = lastData;
	}

	public Object getLastKey() {
		return lastKey;
	}

	public void setLastKey(Object lastKey) {
		this.lastKey = lastKey;
	}
	
	public int getGroupRowCounter() {
		return groupRowCounter;
	}

	/**
	 * Get current rendered row number, and increment to next value.
	 * @return the rowCounter
	 */
	public int nextGroupRow() {
		return groupRowCounter++;
	}
	
	public String getGroupingColumnLabel() {
		return groupingColumnLabel;
	}

	public boolean isGroupingOn() {
		return groupingOn;
	}

	public UIColumn getGroupingColumn() {
		return groupingColumn;
	}

}
