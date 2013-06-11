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
package org.richfaces.model.selection;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Maksim Kaszynski
 *
 */
public class SimpleSelection implements Selection {

	private static final long serialVersionUID = 1L;

	private Set<Object> keys = new LinkedHashSet<Object>();
	
	private boolean selectAll = false;

	public boolean addKey(Object rowKey) {
		return keys.add(rowKey);
	}
	
	public boolean removeKey(Object rowKey) {
		selectAll = false;
		return keys.remove(rowKey);
	}
	
	public Iterator<Object> getKeys() {
		Iterator<Object> result = Collections.emptyList().iterator();;
		if (!selectAll) {
			result = keys.iterator();
		}
		return result;
	}

	public int size() {
		int result = -1;
		if (!selectAll) {
			result = keys.size();
		}
		return result;
	}
	
	public boolean isSelected(Object rowKey) {
		return selectAll || keys.contains(rowKey);
	}

	public void clear() {
		selectAll = false;
		keys.clear();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		result = prime * result + (selectAll ? 1231 : 1237);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleSelection other = (SimpleSelection) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		if (selectAll != other.selectAll)
			return false;
		return true;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isSelectAll() {
		return selectAll;
	}
	
	
}
