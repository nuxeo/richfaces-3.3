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

package org.richfaces.event;

import javax.faces.component.UIComponent;


/**
 * @author Maksim Kaszynski
 *
 */
public abstract class ScrollableGridViewEvent extends AttributedEvent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7417387605074667926L;
	/**
	 * number of rows to update
	 */
	protected int rows;
	/**
	 * start position of update
	 */
	protected int first;


	public ScrollableGridViewEvent(UIComponent component, int rows, int first) {
		super(component);
		this.rows = rows;
		this.first = first;
	}

	public int getFirst() {
		return first;
	}

	public int getRows() {
		return rows;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}