package org.richfaces.helloworld.domain.columns;

import java.util.ArrayList;
import java.util.Comparator;

import org.richfaces.model.Ordering;

import org.richfaces.helloworld.domain.util.data.Data;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
@Name("columns")
@Scope(ScopeType.SESSION)
public class Columns implements Comparator{
	private String[] statusIcon = { "/pics/error.gif", "/pics/fatal.gif",
			"/pics/info.gif", "/pics/passed.gif", "/pics/warn.gif" };
	private ArrayList<Data> data1;
	private ArrayList<Data> data2;
	private int length1;
	private int length2;
	private String columns;
	private String begin;
	private String end;
	private String width;
	private String title;
	private int colspan;
	private int rowspan;
	private boolean breakBefore;
	private Ordering[] orderings = new Ordering[100];
	private boolean sortable;
	private boolean dataTableRendered;
	private Ordering sortOrder;
	private String[] filterValue;
	private String filterInput;
	
	public String getFilterInput() {
		return filterInput;
	}

	public void setFilterInput(String filterInput) {
		this.filterInput = filterInput;
	}

	public boolean filterMethod(Object obj) {
		Data d = (Data)obj;
		if(d.getStr0().startsWith(filterInput)) return true;
		return false;
	}
	
	public String[] getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String[] filterValue) {
		this.filterValue = filterValue;
	}
	public Ordering getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Ordering sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Columns() {
		this.columns = "3";
		this.begin = "0";
		this.end = "10";
		this.width = "100";
		this.title = "Title columns";
		this.colspan = 1;
		this.rowspan = 1;
		this.breakBefore = false;
		this.sortable = true;
		this.dataTableRendered = true;
		this.length1 = 5;
		this.length2 = 5;
		this.data1 = new ArrayList<Data>();
		this.data2 = new ArrayList<Data>();
		this.filterValue = new String[length2];
		this.sortOrder = Ordering.ASCENDING;
		this.filterInput = "";
		for (int i = 0; i < length1; i++) {
			data1.add(new Data(i, "data 1 [" + i + "]", "Button " + i, "Link " + i, "select" + (i % 5)));
			data2.add(new Data(i, "data 2 [" + i + "]", "Button " + i, "Link " + i, statusIcon[i % 5]));
		}
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public boolean isBreakBefore() {
		return breakBefore;
	}

	public void setBreakBefore(boolean breakBefore) {
		this.breakBefore = breakBefore;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public void addNewItem1() {
		if (length1 < 0)
			length1 = 0;
		if (data1.size() > length1)
			for (int i = length1; i < data1.size();)
				data1.remove(i);
		else
			for (int i = data1.size() + 1; i <= length1; i++)
				data1.add(new Data(i, "data 1 [" + i + "]", "Button " + i, "Link " + i, "select" + (i % 5)));
	}

	public void addNewItem2() {
		if (length2 < 0)
			length2 = 0;
		if (data2.size() > length2)
			for (int i = length2; i < data2.size();)
				data2.remove(i);
		else
			for (int i = data1.size() + 1; i <= length2; i++)
				data2.add(new Data(i, "data 1 [" + i + "]", "Button " + i, "Link " + i, "select" + (i % 5)));
	}

	public ArrayList<Data> getData2() {
		return data2;
	}

	public void setData2(ArrayList<Data> data2) {
		this.data2 = data2;
	}

	public ArrayList<Data> getData1() {
		return data1;
	}

	public void setData1(ArrayList<Data> data1) {
		this.data1 = data1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLength1() {
		return length1;
	}

	public void setLength1(int length1) {
		this.length1 = length1;
	}

	public int getLength2() {
		return length2;
	}

	public void setLength2(int length2) {
		this.length2 = length2;
	}

	public boolean isDataTableRendered() {
		return dataTableRendered;
	}

	public void setDataTableRendered(boolean dataTableRendered) {
		this.dataTableRendered = dataTableRendered;
	}

	public Ordering[] getOrderings() {
		return orderings;
	}

	public void setOrderings(Ordering[] orderings) {
		this.orderings = orderings;
	}

	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
