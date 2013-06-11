/*
 * Column.java		Date created: 08.10.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.samples;

import org.richfaces.model.Ordering;

/**
 * Column sample model
 * @author Andrey
 *
 */
public class Column {
	
	String name;
	
	Ordering ordering;
	
	String filterValue;
	
	public Column(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

}
