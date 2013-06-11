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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.component.util.ColumnUtil;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
import org.richfaces.model.selection.SimpleSelection;

/**
 * Implementation of sortListener for multi-column sorting
 * @author Maksim Kaszynski
 *
 */
public class MultiColumnSortListener extends AbstractSortListener {

	public static final MultiColumnSortListener INSTANCE = new MultiColumnSortListener();
	
	private MultiColumnSortListener() {
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
		
		Boolean suggested = e.getSuggestedOrder();
		
		SortField[] fields = sortOrder.getFields();
		
		if (fields == null) {
			//If no sorting was applied at all, set sorting to current
			fields = new SortField[] {new SortField(name, nextSortOrder(null, suggested))};
		} else {
			
			List<SortField> newFields = new LinkedList<SortField>(Arrays.asList(fields));
			SortField newField = null;
			
			for (Iterator<SortField> iterator = newFields.iterator(); iterator.hasNext() && newField == null; ) {
				SortField sortField = (SortField) iterator.next();
				if (name != null && name.equals(sortField.getName())) {
					
					Boolean asc = sortField.getAscending();
					
					newField = new SortField(name, nextSortOrder(asc, suggested));
					iterator.remove();
					
				}				
			}
			
			if (newField == null) {
				
				newField = new SortField(name, nextSortOrder(null, suggested));
			}
			
			newFields.add(newField);
			fields = (SortField[]) newFields.toArray(new SortField[newFields.size()]);
		}
		
		sortOrder.setFields(fields);
	}
	

}
