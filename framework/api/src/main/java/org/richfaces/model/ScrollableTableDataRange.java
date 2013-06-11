/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/components/data-view-grid/src/component/com/exadel/jsf/model/ScrollableTableDataRange.java,v $
 *      $Revision: 1.7 $ 
 */

package org.richfaces.model;

import java.io.IOException;
import java.io.Serializable;

import org.ajax4jsf.model.SequenceRange;

/**
 * Iteration range for Scrollable Grid
 * @author Maksim Kaszynski
 * @modified by Anton Belevich
 */
public class ScrollableTableDataRange extends SequenceRange implements Serializable{
	
	private static final long serialVersionUID = -6675002421400464892L;
	
	private SortOrder sortOrder;
	
	public ScrollableTableDataRange() {
		super();
	}
	
	public ScrollableTableDataRange(int first, int last, SortOrder sortOrder) {
		super(first, last > 0 ? last - first : -1);
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the bufferSize
	 */
	public int getLast() {
		return getFirstRow() + getRows();
	}
	/**
	 * @param bufferSize the bufferSize to set
	 */
	public void setLast(int lastRow) {
		setRows(lastRow > 0 ? lastRow - getFirstRow() : -1);
	}
	/**
	 * @return the first
	 */
	public int getFirst() {
		return getFirstRow();
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(int first) {
		setFirstRow(first);
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getFirstRow();
		result = prime * result + getRows();
		result = prime * result
				+ ((sortOrder == null) ? 0 : sortOrder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScrollableTableDataRange other = (ScrollableTableDataRange) obj;
		if (getFirstRow() != other.getFirstRow())
			return false;
		if (getRows() != other.getRows())
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		return true;
	}
	
	 private void writeObject(java.io.ObjectOutputStream out)
     	throws IOException {
		
		 out.defaultWriteObject();
		 
		 out.writeInt(getFirstRow());
		 out.writeInt(getRows());
	 }

	 private void readObject(java.io.ObjectInputStream in)
     	throws IOException, ClassNotFoundException {

		 in.defaultReadObject();
		 
		 setFirstRow(in.readInt());
		 setRows(in.readInt());
	 }

}
