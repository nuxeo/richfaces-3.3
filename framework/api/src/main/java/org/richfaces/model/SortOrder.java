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
package org.richfaces.model;

import java.io.Serializable;
import java.util.Arrays;
/**
 * Class representing grid sort order
 * Sort Order is the combination of {@link SortField}
 * Order of occurrence of sort fields must be maintained
 * to guarantee stable sorting 
 * @author Maksim Kaszynski
 */
public class SortOrder implements Serializable {

	private static final long serialVersionUID = 2423450561570551363L;

	private static int hashCode(Object[] array) {
		final int prime = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (int index = 0; index < array.length; index++) {
			result = prime * result
					+ (array[index] == null ? 0 : array[index].hashCode());
		}
		return result;
	}

	private SortField [] fields;

	public SortOrder() {
		
	}
	
	public SortOrder(SortField[] fields) {
		super();
		this.fields = fields;
	}



	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SortOrder other = (SortOrder) obj;
		if (!Arrays.equals(fields, other.fields))
			return false;
		return true;
	}

	public SortField[] getFields() {
		return fields;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SortOrder.hashCode(fields);
		return result;
	}

	public void setFields(SortField[] fields) {
		this.fields = fields;
	}

	
	
}