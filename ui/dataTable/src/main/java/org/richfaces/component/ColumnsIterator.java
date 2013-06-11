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
package org.richfaces.component;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;

/**
 * Iterator for all children table columns.
 * @author asmirnov
 *
 */
class ColumnsIterator implements Iterator<UIComponent>{
	

	private UIComponent next;
	
	private boolean initialized = false;
	
	protected Iterator<UIComponent> childrenIterator;
	
	public ColumnsIterator(UIComponent dataTable) {
		this.childrenIterator = dataTable.getChildren().iterator();
	}

	public boolean hasNext() {
		if(!initialized){
			next = nextColumn();
			initialized = true;
		}
		return null != next;
	}

	public UIComponent next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		UIComponent result = next;
		next = nextColumn();
		return result;
	}

	public void remove() {
		throw new UnsupportedOperationException("Iterator is read-only");			
	}
	
	protected UIComponent nextColumn(){
		UIComponent nextColumn = null;
		while (childrenIterator.hasNext()) {
			UIComponent child = childrenIterator.next();
			if(child instanceof UIColumn || child instanceof Column){
				nextColumn = child;
				break;
			}
		}
		return nextColumn;
	}
	
}
