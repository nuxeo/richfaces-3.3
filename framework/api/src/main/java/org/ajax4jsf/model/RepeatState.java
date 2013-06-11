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

package org.ajax4jsf.model;

import java.io.Serializable;

/**
 * @author shura
 *
 */
public class RepeatState implements DataComponentState,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5552520123654180445L;

	private int _rows = -1;
	
	private int _first = 0;
	

	/**
	 * @return the first
	 */
	public int getFirst() {
		return _first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(int first) {
		this._first = first;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return _rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this._rows = rows;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.DataComponentState#getRange()
	 */
	public Range getRange() {
		// TODO Auto-generated method stub
		return new SequenceRange(getFirst(),getRows());
	}


}
