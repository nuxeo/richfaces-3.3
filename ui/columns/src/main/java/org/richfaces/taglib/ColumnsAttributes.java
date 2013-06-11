/**
 * 
 */
package org.richfaces.taglib;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Markavtsov
 */
public class ColumnsAttributes {
	
	/** Filter attributes */
	public static final List<String> FILTER_ATTRIBUTES =  new ArrayList<String>();
	
	static {
		FILTER_ATTRIBUTES.add("filterBy");
		FILTER_ATTRIBUTES.add("filterExpression");
		FILTER_ATTRIBUTES.add("filterValue");
		FILTER_ATTRIBUTES.add("filterEvent");
	}

	/** Sort attributes */
	public static final List<String> SORT_ATTRIBUTES = new ArrayList<String>();
	
	static {
		SORT_ATTRIBUTES.add("sortExpression");
	}
}
