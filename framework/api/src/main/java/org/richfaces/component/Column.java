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

package org.richfaces.component;

import javax.el.MethodExpression;

import org.richfaces.model.Ordering;

/**
 * Marker interface for all components used as column in UIDataTable
 * @author shura
 *
 */
public interface Column {
	
	
	/**
	 * Out this column on new row
	 * @parameter
	 * @return the acceptClass
	 */
	public abstract boolean isBreakBefore();

	/**
	 * @param newBreakBefore the value  to set
	 */
	public abstract void setBreakBefore(boolean newBreakBefore);
	
	
	/**
	 * The column allows data sorting
	 * @return
	 */
	public abstract boolean isSortable();
	
	public abstract void setSortable(boolean sortable);

	/**
	 * expression used for column sorting. 
	 * literal value is treated as property of data object
	 * EL-expression is evaluated on every data row
	 * @param sortExpression
	 */
	public abstract void setSortExpression(String sortExpression);
	public abstract String getSortExpression();
	
	/**
	 * SortOrder is an enumeration of the possible sort orderings.
	 * 
	 * @param sortOrder
	 */
	public abstract void setSortOrder(Ordering sortOrder);
	public abstract Ordering getSortOrder();
	
	public abstract void setFilterMethod(MethodExpression methodExpression);
	public abstract MethodExpression getFilterMethod();

	public abstract void setFilterValue(String filterValue);
	public abstract String getFilterValue();
	
	public abstract boolean isSelfSorted();
	public abstract void setSelfSorted(boolean selfSorted);
}
