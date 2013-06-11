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

import java.util.Comparator;

import org.richfaces.model.SortField;

/**
 * Comparator for {@link JavaBeanWrapper} objects.
 * Compares them using {@link SortField} sequence.
 * 
 * @author Maksim Kaszynski
 *
 */
public final class WrappedBeanComparator implements Comparator<Object> {
	/**
	 * 
	 */
	private final SortField[] fields;

	/**
	 * @param fields
	 */
	public WrappedBeanComparator(SortField[] fields) {
		this.fields = fields;
	}
	
	public int compare(Object o1, Object o2) {
		return compare((JavaBeanWrapper) o1, (JavaBeanWrapper) o2);
	}
	
	@SuppressWarnings("unchecked")
	private int compare(JavaBeanWrapper w1, JavaBeanWrapper w2) {
		
		int result = 0;
		
		for (int i = 0; i < fields.length && result == 0; i++) {
			
			String prop = fields[i].getName();
			Boolean asc = fields[i].getAscending();
			
			Object p1 = w1.getProperty(prop);
			Object p2 = w2.getProperty(prop);
			
			if (p1 instanceof Comparable && p2 instanceof Comparable) {
				result = ((Comparable<Object>) p1).compareTo(p2);
			} else if (p1 == null && p2 != null) {
				result = 1;
			} else if (p2 == null && p1 != null) {
				result = -1;
			}
			
			if (asc != null && !asc.booleanValue()) {
				result = -result;
			}
			
		}
		
		
		return result;
	}
}