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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField2;
import org.richfaces.model.selection.Selection;

/**
 * @author mpopiolek
 * 
 */
public class DefaultExtendedDataTable extends UIExtendedDataTable {

    @Override
    public Object getActiveRowKey() {
        return "activeRowKey";
    }

    @Override
    public void setActiveRowKey(Object activeRowKey) {
    }

    @Override
    public String getSortMode() {
        return null;
    }

    @Override
    public void setSortMode(String sortMode) {
    }

    public Selection getSelection() {
        return null;
    }

    public void setSelection(Selection selection) {
    }

    public List<SortField2> getSortFields() {
        List<SortField2> list = new ArrayList<SortField2>();
        return list;
    }

    public void setSortFields(List<SortField2> sortFields) {
    }

    public List<FilterField> getFilterFields() {
        List<FilterField> list = new ArrayList<FilterField>();
        return list;
    }

    public void setFilterFields(List<FilterField> filterFields) {
    }

	@Override
	public String getGroupingColumn() {
		return "";
	}

	@Override
	public void setGroupingColumn(String groupingColumn) {
	}

	@Override
	public String getTableState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTableState(String tableState) {
		// TODO Auto-generated method stub
		
	}

}
