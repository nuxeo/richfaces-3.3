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
package org.richfaces.model.impl.expressive;

import java.text.Collator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.el.Expression;

import org.richfaces.model.Ordering;
import org.richfaces.model.SortField;
import org.richfaces.model.SortField2;

/**
 * Comparator for {@link JavaBeanWrapper} objects.
 * Compares them using {@link SortField} sequence.
 * 
 * @author Maksim Kaszynski
 *
 */
public final class WrappedBeanComparator2 implements Comparator<Object> {
	
	private static final Comparator<String> defaultStringComparator = new Comparator<String>() {

		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
		
	};

	private final List<SortField2> sortFields;

	private Locale currentLocale;
	
	private Comparator<? super String> stringComparator;

	public WrappedBeanComparator2(List<SortField2> sortFields) {
		this(sortFields, null);
	}

	public WrappedBeanComparator2(List<SortField2> sortFields, Locale locale) {
		super();
		this.sortFields = sortFields;
		this.currentLocale = locale;
	}

	private Comparator<? super String> createStringComparator() {
		Comparator<? super String> comparator = null;
		if (currentLocale != null) {
			comparator = Collator.getInstance(currentLocale);
		} else {
			comparator = defaultStringComparator;
		}
		
		return comparator;
	}
	
	public int compare(Object o1, Object o2) {
		return compare((JavaBeanWrapper) o1, (JavaBeanWrapper) o2);
	}
	
	@SuppressWarnings("unchecked")
	private int compare(JavaBeanWrapper w1, JavaBeanWrapper w2) {
		int result = 0;
		
		for (Iterator<SortField2> iterator = sortFields.iterator(); iterator.hasNext() && result == 0;) {
			SortField2 field = iterator.next();
			Expression expression = field.getExpression();
			String prop = expression.getExpressionString();
			Ordering ordering = field.getOrdering();
			if (ordering != null) {
				Object p1 = w1.getProperty(prop);
				Object p2 = w2.getProperty(prop);
				if (p1 == p2 && p1 instanceof Comparator) {
					result = ((Comparator<Object>)p1).compare(w1.getWrappedObject(), w2.getWrappedObject());
				} else if (p1 instanceof String && p2 instanceof String) {
					if (stringComparator == null) {
						stringComparator = createStringComparator();
					}
					result = stringComparator.compare(((String)p1).trim(), ((String)p2).trim());
				} else if (p1 instanceof Comparable && p2 instanceof Comparable) {
					result = ((Comparable<Object>) p1).compareTo(p2);
				} else if (p1 == null && p2 != null) {
					result = 1;
				} else if (p2 == null && p1 != null) {
					result = -1;
				}
				
				if (ordering.equals(Ordering.DESCENDING)) {
					result = -result;
				}
			}
			
		}
		
		return result;
	}
}