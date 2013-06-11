/**
 * 
 */
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Markavtsov
 *
 */
public class ColumnGroupBean {
	
	boolean rendered = true;
	
	List<String> rows = new ArrayList<String>();
	
	public ColumnGroupBean() {
		for (int i=0; i<2; i++) {
			rows.add("Text" + i);
		}
	}
	
	public void reset() {
		rendered = true;
	}
	
	public void prepareRenderedTest() {
		reset();
		rendered = false;
	}

	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}

	public boolean isRendered() {
		return rendered;
	}
	
}
