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

package org.richfaces.demo.modifiableModel;

import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.el.ELException;
import javax.el.Expression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.ExtendedFilterField;
import org.richfaces.model.FilterField;
import org.richfaces.model.Modifiable;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class HibernateDataModel extends ExtendedDataModel implements Modifiable {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session session;
	
	protected Session getSession() {
		if (session == null) {
			session = sessionFactory.openSession();
		}
		
		return session;
	}
	
	@PreDestroy
	public void destroy() {
		if (session != null) {
			session.close();
		}
	}
	
	private Long rowKey;
	
	private DataItem dataItem;
	
	private SequenceRange cachedRange;
	
	private List<DataItem> cachedItems;

	private List<FilterField> filterFields;

	private List<SortField2> sortFields;

	private static boolean areEqualRanges(SequenceRange range1, SequenceRange range2) {
		if (range1 == null || range2 == null) {
			return range1 == null && range2 == null;
		} else {
			return range1.getFirstRow() == range2.getFirstRow() && range1.getRows() == range2.getRows();
		}
	}
	
	private Criteria createCriteria() {
		return getSession().createCriteria(DataItem.class);
	}
	
	private void appendFilters(FacesContext context, Criteria criteria) {
		if (filterFields != null) {
			for (FilterField filterField : filterFields) {
				String propertyName = getPropertyName(context, filterField.getExpression());

				String filterValue = ((ExtendedFilterField) filterField).getFilterValue();
				if (filterValue != null && filterValue.length() != 0) {
					criteria.add(Restrictions.like(propertyName, 
							filterValue, 
							MatchMode.ANYWHERE).ignoreCase());
				}
			}
		}
	}
	
	private void appendSorts(FacesContext context, Criteria criteria) {
		if (sortFields != null) {
			for (SortField2 sortField : sortFields) {
				Ordering ordering = sortField.getOrdering();
				
				if (Ordering.ASCENDING.equals(ordering) || Ordering.DESCENDING.equals(ordering)) {
					String propertyName = getPropertyName(context, sortField.getExpression());
					
					Order order = Ordering.ASCENDING.equals(ordering) ? 
							Order.asc(propertyName) : Order.desc(propertyName);
							
					criteria.addOrder(order.ignoreCase());
				}
			}
		}
	}
	
	private String getPropertyName(FacesContext facesContext, Expression expression) {
		try {
			return (String) ((ValueExpression) expression).getValue(facesContext.getELContext());
		} catch (ELException e) {
			throw new FacesException(e.getMessage(), e);
		}
	}
	
	@Override
	public Object getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(Object key) {
		this.rowKey = (Long) key;
		this.dataItem = null;
		
		if (this.rowKey != null) {
			this.dataItem = (DataItem) session.load(DataItem.class, this.rowKey);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void walk(FacesContext facesContext, DataVisitor visitor, Range range,
			Object argument) throws IOException {

		SequenceRange sequenceRange = (SequenceRange) range;
		
		if (this.cachedItems == null || !areEqualRanges(this.cachedRange, sequenceRange)) {
			Criteria criteria = createCriteria();
			appendFilters(facesContext, criteria);
			appendSorts(facesContext, criteria);

			if (sequenceRange != null) {
				int first = sequenceRange.getFirstRow();
				int rows = sequenceRange.getRows();
				
				criteria.setFirstResult(first);
				if (rows > 0) {
					criteria.setMaxResults(rows);
				}
			}
			
			this.cachedRange = sequenceRange;
			this.cachedItems = criteria.list();
		}
		
		//System.out.println(getRowCount());
		
		for (DataItem item: cachedItems) {
			visitor.process(facesContext, item.getId(), argument);
		}
	}

	@Override
	public int getRowCount() {
		Criteria criteria = createCriteria();
		appendFilters(FacesContext.getCurrentInstance(), criteria);
		return (Integer) criteria.list().size();
	}

	@Override
	public Object getRowData() {
		return this.dataItem;
	}

	@Override
	public int getRowIndex() {
		return -1;
	}

	@Override
	public Object getWrappedData() {
		return null;
	}

	@Override
	public boolean isRowAvailable() {
		return (this.dataItem != null);
	}

	@Override
	public void setRowIndex(int rowIndex) {
	}

	@Override
	public void setWrappedData(Object data) {
	}
	
	public void modify(List<FilterField> filterFields, List<SortField2> sortFields) {
		this.filterFields = filterFields;
		this.sortFields = sortFields;

		this.cachedItems = null;
		this.cachedRange = null;
	}

}
