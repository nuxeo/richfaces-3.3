/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.component.html;

import javax.el.MethodExpression;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

import org.richfaces.component.Column;
import org.richfaces.model.Ordering;

/**
 * @author Maksim Kaszynski
 * 
 */
public class MockColumns extends UIComponentBase implements Column {

	private MockColumns(){
		
	}
	
	private String sortExpression;
	private Ordering sortOrder;
	private MethodExpression methodExpression;
	private boolean sortable;
	private boolean breakbefore;

	public String getSortExpression() {
		return sortExpression;
	}

	public void setSortExpression(String sortExpression) {
		this.sortExpression = sortExpression;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isBreakBefore() {
		return breakbefore;
	}

	public void setBreakBefore(boolean breakbefore) {
		this.breakbefore = breakbefore;
	}

	public String getFamily() {
		return UIColumn.COMPONENT_FAMILY;
	}

	public static UIComponent newColumn(String id) {
		MockColumns column = new MockColumns();
		column.setId(id);
		return column;
	}

	public Ordering getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Ordering sortOrder) {
		this.sortOrder = sortOrder;
	}

	public MethodExpression getFilterMethod() {
		return methodExpression;
	}

	public void setFilterMethod(MethodExpression methodExpression) {
		this.methodExpression = methodExpression;
		
	}

	public String getFilterValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFilterValue(String filterValue) {
		// TODO Auto-generated method stub
		
	}

	public boolean isSelfSorted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setSelfSorted(boolean selfSorted) {
		// TODO Auto-generated method stub
		
	}
}
