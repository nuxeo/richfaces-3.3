/*
 * Bean.java		Date created: 08.10.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.samples;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.event.ActionEvent;

/**
 * Sample session bean
 * @author Andrey
 *
 */
public class Bean {
	
	List<Column> columns;
	
	List<List<String>> data;
	
	Integer rowsCount;
	
	Boolean name;
	
	Boolean type;
	
	Boolean description;
	
	Integer maxValue;
	
	public Bean() {
		rowsCount = 5;
		name = true;
		type = true;
		description = true;
		maxValue = 15;
		init();
	}
	
	public void apply(ActionEvent event) {
		init();
	}
	
	private void init() {
		// Init columns
		columns = new ArrayList<Column>();
		columns.add(new Column("#"));
		
		if (name) {
			columns.add(new Column("Name"));
		}
		if (type) {
			columns.add(new Column("Type"));
		}
		if (description) {
			columns.add(new Column("Description"));
		}
		
		//Init model
		data = new ArrayList<List<String>>();
		for (int i = 0; i < rowsCount; i++) {
			data.add(getRow(i));
		}
	}
	
	private List<String> getRow(int row) {
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(row));
		if (name) {
			list.add(String.valueOf(Math.random()));
		}
		
		if (type) {
			int i = new Double(Math.random() * 10.0).intValue();
			if (i <= 3) {
				list.add("txt");
			}else if (i <= 6) {
				list.add("jpeg");
			}else if (i <= 10) {
				list.add("xml");
			}
		}
		
		if (description) {
			list.add("");
		}
		
		return list;
	}
	
	public List<Integer[]> getInrementData() {
		List<Integer[]> list = new ArrayList<Integer[]>();
		for (int i = 0; i < 15; i++) {
			list.add(new Integer[] {i});
		}
		return list;
	}
	
	public boolean filterMethod(Object o) {
		Integer [] i  = (Integer [])o;
		if (i[0] > maxValue) {
			return false;
		}
		return true;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public List<List<String>> getData() {
		return data;
	}

	public Boolean getName() {
		return name;
	}

	public void setName(Boolean name) {
		this.name = name;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	public Boolean getDescription() {
		return description;
	}

	public void setDescription(Boolean description) {
		this.description = description;
	}

	public Integer getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(Integer rowsCount) {
		this.rowsCount = rowsCount;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

}
