/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/components/data-view-grid/src/component/com/exadel/jsf/event/sort/SortSource.java,v $
 *      $Revision: 1.1 $ 
 */

package org.richfaces.event.sort;

/**
 * @author Maksim Kaszynski
 *
 */
public interface SortSource {
	public void addSortListener(SortListener listener);
	public void removeSortListener(SortListener listener);
	public SortListener[] getSortListeners();
}
