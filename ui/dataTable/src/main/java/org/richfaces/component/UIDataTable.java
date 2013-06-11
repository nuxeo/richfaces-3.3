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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.SequenceDataAdaptor;
import org.ajax4jsf.context.ContextInitParameters;
import org.ajax4jsf.model.ExtendedDataModel;
import org.richfaces.model.FilterField;
import org.richfaces.model.LocaleAware;
import org.richfaces.model.Modifiable;
import org.richfaces.model.ModifiableModel;
import org.richfaces.model.SortField2;


/**
 * JSF component class
 * 
 */
public abstract class UIDataTable extends SequenceDataAdaptor {

	Collection<Object> sortPriority = new ArrayList<Object>();
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#dataChildren()
	 */
	public Iterator<UIComponent> dataChildren() {
		return new DataIterator(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#fixedChildren()
	 */
	public Iterator<UIComponent> fixedChildren() {
		return new FixedChildrenIterator(this);
	}

	public Iterator<UIComponent> columns() {
		return new ColumnsIterator(this);
	}
	
	public static final String COMPONENT_TYPE = "org.richfaces.DataTable";

	public static final String COMPONENT_FAMILY = "org.richfaces.DataTable";
		
	@Override
	protected ExtendedDataModel createDataModel() {
		List<FilterField> filterFields = new LinkedList<FilterField>();
		Map<String, SortField2> sortFieldsMap = new LinkedHashMap<String, SortField2>();
		List<UIComponent> list = getChildren();
		for (Iterator<UIComponent> iterator = list.iterator(); iterator.hasNext();) {
			UIComponent component = iterator.next();
			if (component instanceof org.richfaces.component.UIColumn) {
				org.richfaces.component.UIColumn column = (org.richfaces.component.UIColumn) component;
				FilterField filterField = column.getFilterField();
				if (filterField != null) {
					filterFields.add(filterField);
				}
				SortField2 sortField = column.getSortField();
				if (sortField != null) {
					sortFieldsMap.put(component.getId(), sortField);
				}
			}
			
		}
		List<SortField2> sortFields = new LinkedList<SortField2>();
		Collection<Object> sortPriority = getSortPriority();
		if (sortPriority != null) {
			for (Object object : sortPriority) {
				if (object instanceof String) {
					String id = (String) object;
					SortField2 sortField = sortFieldsMap.get(id);
					if (sortField != null) {
						sortFields.add(sortField);
						sortFieldsMap.remove(id);
					}
				}
			}
		}
		sortFields.addAll(sortFieldsMap.values());
		ExtendedDataModel dataModel = super.createDataModel();
		if ((filterFields != null && !filterFields.isEmpty())
				|| (sortFields != null && !sortFields.isEmpty())) {
			Modifiable modifiable = null;
			if (dataModel instanceof Modifiable) {
				modifiable = (Modifiable) dataModel;
			} else {
				ModifiableModel modifiableModel = new ModifiableModel(dataModel, getVar());
				dataModel = modifiableModel;
				modifiable = modifiableModel;
			}
			
			if (dataModel instanceof LocaleAware) {
				FacesContext facesContext = getFacesContext();
				if (ContextInitParameters.isDatatableUsesViewLocale(facesContext)) {
					UIViewRoot viewRoot = facesContext.getViewRoot();
					((LocaleAware) dataModel).setLocale(viewRoot.getLocale());
				}
			}
			
			modifiable.modify(filterFields, sortFields);
		}
		return dataModel;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getSortPriority(){
		ValueExpression ve = getValueExpression("sortPriority");
		if (ve != null) {
		    Collection<Object> value = null;
		    try {
				value = (Collection<Object>) ve.getValue(getFacesContext().getELContext());
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		    return value;
		} else {
			return sortPriority;
		}
	}

	public void setSortPriority(Collection<Object> sortPriority){
		ELContext context = getFacesContext().getELContext();
		ValueExpression ve = getValueExpression("sortPriority");
		if (ve != null && !ve.isReadOnly(context)) {
		    try {
				ve.setValue(context, sortPriority);
		    } catch (ELException e) {
				throw new FacesException(e);
		    }
		} else {
		    this.sortPriority = sortPriority;
		}
	}

	public abstract String getSortMode();
	public abstract void setSortMode(String sortMode);
	
	@Override
	public Object saveState(FacesContext context) {
		Object [] state = new Object[2];
		state[0] = super.saveState(context);
		state[1] = sortPriority;
		return state;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void restoreState(FacesContext context, Object state) {
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		sortPriority = (Collection<Object>)states[1];
	}
}
