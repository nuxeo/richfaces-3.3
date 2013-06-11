package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.richfaces.model.Ordering;

public class ColumnsBean {
	
	static final String ROW_PREFIX = "Row";
	
	static final String CELL_PREFIX = "Cell";
	
	String status;
	
	boolean rendered = true;
	boolean selfSorted = true;
	
	public class Column {
		String header;
		String footer;
		Ordering ordering;
		Object filterValue;
		
		public Column(String header, String footer) {
			super();
			this.header = header;
			this.footer = footer;
		}
		
		public void listener(ActionEvent event) {
			setStatus(getStatus() + "ActionListener" + columns.indexOf(this));
		}
		
		public boolean filterMethod(Object o) {
			Row [] rows = (Row[])o;
			int i = columns.indexOf(this);
			return rows[i].value == i;
		}

		public String getHeader() {
			return header;
		}
		public void setHeader(String header) {
			this.header = header;
		}

		public Ordering getOrdering() {
			return ordering;
		}

		public void setOrdering(Ordering ordering) {
			this.ordering = ordering;
		}

		public Object getFilterValue() {
			return filterValue;
		}

		public void setFilterValue(Object filterValue) {
			this.filterValue = filterValue;
		}

		public String getFooter() {
			return footer;
		}

		public void setFooter(String footer) {
			this.footer = footer;
		}
		
	
	}
	
	public class Row {
		String input;
		int value;
		
		public Row(int i) {
			this.value = i;
		}

		public String getInput() {
			return input;
		}

		public void setInput(String input) {
			this.input = input;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	}
	
	Comparator<Row[]> comparator = new Comparator<Row[]>(){
		
		public int compare(Row[] o1, Row[] o2) {
			return new Integer(o1[0].value).compareTo(o2[0].value);
		}
		
	};
	
	private int rows = 2;
	
	private int cols = 3;
	
	private int headerCols = 5;
	
	private List<Row []> model;
	
	private List<Column> columns;
	
	private List<Column> headerColumns;
	
	public ColumnsBean() {
		init();
	}
	
	private void init () {
		columns = new ArrayList<Column>();
		for (int i = 0; i < cols; i++) {
			columns.add(new Column("Header" + i, "Footer" + i));
		}
		
		model = new ArrayList<Row[]>();
		for (int i = 0; i < rows; i++) {
			Row[] rows = new Row [cols];
			for (int j=0; j<cols; j++) {
				rows[j] = new Row(i + j);
			}
			model.add(rows);
		}
	}
	
	public void reset() {
		init();
		status = null;
		rendered = true;
		selfSorted = true;
	}
	
	public void prepareRenderedTest () {
		reset();
		rendered = false;
	}
	
	public void prepareExternalSorting () {
		reset();
		selfSorted = false;
	}
	
	public String sortAscending() {
		for (Column c : columns) {
			c.setOrdering(Ordering.ASCENDING);
		}
		return null;
	}
	
	public String sortDescending() {
		for (Column c : columns) {
			c.setOrdering(Ordering.DESCENDING);
		}
		return null;
	}

	public List<Row[]> getModel() {
		return model;
	}

	public void setModel(List<Row[]> model) {
		this.model = model;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public boolean getCheckInputs() {
		Iterator<Row[]> it = model.iterator();
		int row = 0;
		boolean f = true;
		while (it.hasNext()) {
			Row[] r = it.next();
			for (int i = 0; i < cols; i++) {
				String e = ROW_PREFIX + row + CELL_PREFIX + i;
				if (!e.equals(r[i].input)) {
					f = false;
				}
			}
			row++;
		}
		return f;
	}

	public String getStatus() {
		return (status != null) ? status : "";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Comparator<Row[]> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<Row[]> comparator) {
		this.comparator = comparator;
	}

	public List<Column> getHeaderColumns() {
		if (headerColumns == null) {
			headerColumns = new ArrayList<Column>();
			for (int i = 0; i < headerCols; i++) {
				headerColumns.add(new Column("Header" + i, "Footer" + i));
			}
		}
		return headerColumns;
	}

	public boolean isRendered() {
		return rendered;
	}

	public boolean isSelfSorted() {
		return selfSorted;
	}

	
}
