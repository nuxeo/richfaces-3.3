/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/components/data-view-grid/src/component/com/exadel/jsf/event/sort/SortEvent.java,v $
 *      $Revision: 1.2 $ 
 */

package org.richfaces.event.sort;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

import org.richfaces.event.ScrollableGridViewEvent;

/**
 * @author Maksim Kaszynski
 * @modified by Anton Belevich
 *
 */
public class SortEvent extends ScrollableGridViewEvent {

	private static final long serialVersionUID = -1453867412542792281L;
	
	private String sortColumn;
	
	private Boolean suggestedOrder = null;
	
	public SortEvent(UIComponent component, String sortColumn, int rows, int first) {
		
		super(component, rows, first);
		this.sortColumn = sortColumn;
		
	}

	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof SortListener);
	}

	public void processListener(FacesListener listener) {
		((SortListener) listener).processSort(this);
	}

	/**
	 * @return the sortField
	 */
	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @see java.util.EventObject#toString()
	 */
	public String toString() {
		return "SortEvent: {sortColumn: " + sortColumn + "}";
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public Boolean getSuggestedOrder() {
		return suggestedOrder;
	}

	public void setProposedOrder(Boolean proposedOrder) {
		this.suggestedOrder = proposedOrder;
	}
}
