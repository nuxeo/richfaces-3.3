package org.richfaces.helloworld.domain.sortingAndFiltering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.model.Ordering;
import org.richfaces.helloworld.domain.util.data.Data;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

@Name("sortingAndFiltering")
@Scope(ScopeType.SESSION)
public class SortingAndFiltering {
	private ArrayList<Data> data;
	private String sortMode;
	private boolean selfSorted;
	private boolean sortable;
	private String sortExpression;
	private Ordering sortOrder;
	private String currentSortOrder;
	private String filterInput;
	private String filterValue;
	private HtmlDataTable myDataTable = null;
	
	private EntityManager mEntityManager;
	
	private final Comparator<Data> comparator = new Comparator<Data> () {
		public int compare(Data o1, Data o2) {
			return o1.getStr1().length() - o2.getStr1().length();
		}
	};
	
	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilterInput() {
		return filterInput;
	}

	public void setFilterInput(String filterInput) {
		this.filterInput = filterInput;
	}	
	public SortingAndFiltering() {
		filterValue = "";
		filterInput = "";
		data = new ArrayList<Data>();
		sortMode = "single";
		sortOrder = Ordering.ASCENDING;
		currentSortOrder = "ASCENDING";
		boolean b = true;
		Random r = new Random();
		for(int i = 0; i < 10; i++)
			data.add(new Data(i, r.nextInt(1000), "dataScroller1", r.nextInt(10000) + 98389, Data.Random(r.nextInt(10) + 1), r.nextInt(500000), Data.statusIcon[i % 5], Data.Random(3), false)); //new Data(i, Data.Random(5), Data.statusIcon[i % 5], Data.Random(6), false));
		data.add(new Data(11, r.nextInt(1000), "dataTable_scroll1", r.nextInt(10000) + 98389, Data.Random(r.nextInt(10) + 1), r.nextInt(500000), Data.statusIcon[11 % 5], Data.Random(3), false)); //new Data(i, Data.Random(5), Data.statusIcon[i % 5], Data.Random(6), false));
	}	
	
	
	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}

	public boolean isSelfSorted() {
		return selfSorted;
	}

	public void setSelfSorted(boolean selfSorted) {
		this.selfSorted = selfSorted;
	}

	public String getSortExpression() {
		return sortExpression;
	}

	public void setSortExpression(String sortExpression) {
		this.sortExpression = sortExpression;
	}	
	public ArrayList<Data> getData() {
		return data;
	}
	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public Ordering getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Ordering sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getCurrentSortOrder() {
		return currentSortOrder;
	}

	public void setCurrentSortOrder(String currentSortOrder) {
		if("DESCENDING".equals(currentSortOrder)) this.sortOrder = Ordering.DESCENDING;
		else if("UNSORTED".equals(currentSortOrder)) this.sortOrder = Ordering.UNSORTED;
		else if("ASCENDING".equals(currentSortOrder)) this.sortOrder = Ordering.ASCENDING;
		this.currentSortOrder = currentSortOrder;
	}

	public boolean filterMethod(Object obj) {
	        String value = ((Data) obj).getStr0();
                if (null == value) {
                    return (null == filterInput);
                }
                return value.startsWith(filterInput);
	}

	public Comparator<Data> getComparator() {
		return comparator;
	}
	public String navAction(Data data12){		
		
		System.out.println("action: " + data12.getStr1());
		return data12.getStr1();		
	}

	public HtmlDataTable getMyDataTable() {
		return myDataTable;
	}

	public void setMyDataTable(HtmlDataTable myDataTable) {
		this.myDataTable = myDataTable;
	}

}
