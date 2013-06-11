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
package org.richfaces.event.sort;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.component.util.ColumnUtil;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
import org.richfaces.model.selection.SimpleSelection;

/**
 * @author Maksim Kaszynski
 *
 */
public class SingleColumnSortListener extends AbstractSortListener {

	public static final SingleColumnSortListener INSTANCE = new SingleColumnSortListener();
	
	private SingleColumnSortListener() {
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.event.sort.SortListener#processSort(org.richfaces.event.sort.SortEvent)
	 */
	public void processSort(SortEvent e) {
		UIScrollableDataTable grid = (UIScrollableDataTable) e.getComponent();
		
		//fix https://jira.jboss.org/jira/browse/RF-4368
		SimpleSelection selection = (SimpleSelection) grid.getSelection();
		if (selection != null) {
			selection.clear();
			grid.setActiveRowKey(null);
		}
		
		String sortColumn = e.getSortColumn();

		UIComponent column = grid.findComponent(sortColumn);
		
		String name = ColumnUtil.getColumnSorting(column);
		
		SortOrder sortOrder = grid.getSortOrder();
		if (sortOrder == null) {
			sortOrder = new SortOrder();
			grid.setSortOrder(sortOrder);
			
			if (grid.getValueExpression("sortOrder") != null) {
				grid.getValueExpression("sortOrder").setValue(FacesContext.getCurrentInstance().getELContext(), sortOrder);
			}
			
		}
		
		SortField[] fields = sortOrder.getFields();
		
		SortField newField = new SortField(name, nextSortOrder(null, e.getSuggestedOrder()));
		
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				SortField sortField = fields[i];
				if (name != null && name.equals(sortField.getName())) {
					
					Boolean asc = nextSortOrder(sortField.getAscending(), e.getSuggestedOrder()) ;
					
					newField = new SortField(name, asc);
					break;
					
				}
			}
		}
		
		sortOrder.setFields(new SortField[] {newField});
	}
	

}
