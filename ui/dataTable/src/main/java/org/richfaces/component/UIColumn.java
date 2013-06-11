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

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxSupport;
import org.richfaces.model.ExtendedFilterField;
import org.richfaces.model.FilterField;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;



/**
 * JSF component class
 * 
 */
public abstract class UIColumn extends javax.faces.component.UIColumn implements Column {
	
	public static final String COMPONENT_TYPE = "org.richfaces.Column";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.Column";
	
	private Ordering sortOrder = Ordering.UNSORTED;
	private String filterValue;
	
	public abstract String getSortIcon();
	public abstract void setSortIcon(String sortIcon);
	
	public abstract String getSortIconAscending();
	public abstract void setSortIconAscending(String sortIcon);
	
	public abstract String getSortIconDescending();
	public abstract void setSortIconDescending(String sortIcon);
	
	public abstract boolean isVisible();
	public abstract void setVisible(boolean visible);

	//This code block adds support for visible attribute of the column component
	@Override
	public boolean isRendered() {
		return isRendered(true);
	}
	
	/**
	 * Check if component is rendered.
	 * Consider "rendered" attribute and optionally depend on parameter consider "visible" attribute.
	 * @author PGolawski
	 * @param considerVisible consider "visible" attribute
	 * @return attribute "rendered" value 
	 */
	public boolean isRendered(boolean considerVisible) {
		boolean rendered = super.isRendered();
		if (considerVisible) {
			rendered = (rendered && isVisible());
		}
		return rendered;
	}
	//
	
	public FilterField getFilterField(){
		FilterField filterField = null;
		MethodExpression filterMethod = getFilterMethod();
		ValueExpression filterExpression = getValueExpression("filterExpression");
		ValueExpression filterBy = getValueExpression("filterBy");
		String filterValue = getFilterValue();
		if (filterMethod != null) {
			filterField = new FilterField(filterMethod);
		} else if (filterExpression != null) {
			filterField = new FilterField(filterExpression);
		} else if (filterBy != null) {
			filterField = new ExtendedFilterField(filterBy, filterValue);
		}
		return filterField;
	}

	public SortField2 getSortField(){
		SortField2 sortField2 = null;
		if (!Ordering.UNSORTED.equals(getSortOrder())) {
			ValueExpression comparator = getValueExpression("comparator");
			ValueExpression sortBy = getValueExpression("sortBy");
			if (comparator != null) {
				sortField2 = new SortField2(comparator, getSortOrder());
			} else if (sortBy != null) {
				sortField2 = new SortField2(sortBy, getSortOrder());
			}
		}
		return sortField2;
	}
	
	public void toggleSortOrder(){
		if(Ordering.ASCENDING.equals(getSortOrder())) {
			setSortOrder(Ordering.DESCENDING);
		} else {
			setSortOrder(Ordering.ASCENDING);
		}		
	}
	
	public Ordering getSortOrder(){
		ValueExpression ve = getValueExpression("sortOrder");
		if (ve != null) {
		    Ordering value = null;
		    try {
				value = (Ordering) ve.getValue(getFacesContext().getELContext());
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		    return value;
		} else {
		    return sortOrder;
		}
	}

	public void setSortOrder(Ordering sortOrder){
		ELContext context = getFacesContext().getELContext();
		ValueExpression ve = getValueExpression("sortOrder");
		if (ve != null && !ve.isReadOnly(context)) {
		    try {
				ve.setValue(context, sortOrder);
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		} else {
		    this.sortOrder = sortOrder;
		}
	}
	
	public String getFilterValue(){
		ValueExpression ve = getValueExpression("filterValue");
		if (ve != null) {
		    String value = null;
		    try {
				value = (String) ve.getValue(getFacesContext().getELContext());
		    } catch (ELException e) {
				throw new FacesException(e);
		    } 
		    return value;
		} else {
			return filterValue;
		}
	}

	public void setFilterValue(String filterValue){
		ELContext context = getFacesContext().getELContext();
		ValueExpression ve = getValueExpression("filterValue");
		if (ve != null && !ve.isReadOnly(context)) {
		    try {
				ve.setValue(context, filterValue);
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		} else {
		    this.filterValue = filterValue;
		}
	}
	
	@Override
	public Object saveState(FacesContext context) {
		Object [] state = new Object[3];
		state[0] = super.saveState(context);
		state[1] = filterValue;
		state[2] = sortOrder;
		return state;
	}
	
	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		filterValue = (String)states[1];
		sortOrder = (Ordering)states[2];
	}
	
	@Override
	public void processDecodes(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }
		
		for (UIComponent component : getChildren()) {
			component.processDecodes(context);
		}

		for (UIComponent component : getFacets().values()) {
			if (isAjaxComponent(component)) {
				component.processDecodes(context);
			}
		}
		decode(context);
	}

	@Override
	public void processValidators(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }
		
		for (UIComponent component : getChildren()) {
			component.processValidators(context);
		}

		for (UIComponent component : getFacets().values()) {
			if (isAjaxComponent(component)) {
				component.processValidators(context);
			}
		}
	}
	
	@Override
	public void processUpdates(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }
		
		for (UIComponent component : getChildren()) {
			component.processUpdates(context);
		}

		for (UIComponent component : getFacets().values()) {
			if (isAjaxComponent(component)) {
				component.processUpdates(context);
			}
		}
	}
	private boolean isAjaxComponent(UIComponent component) {
		return component instanceof AjaxSupport;
	}
}
