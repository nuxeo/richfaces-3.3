
package sortingAndFiltering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.faces.model.ListDataModel;
import org.richfaces.model.Ordering;
import org.richfaces.component.html.HtmlDataTable;
import util.data.Data;

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
	private boolean rendered;
	private ListDataModel dataModel;
	private HtmlDataTable myDataTable = new HtmlDataTable();
	private Data selectedItem;
	
	private final Comparator<Data> comparator = new Comparator<Data> () {
		public int compare(Data o1, Data o2) {
			if ((o1.getStr1() == null) && (o2.getStr1() == null))
				return 0;
			else if((o1.getStr1() != null) && (o2.getStr1() == null))
				return 1;
			else if((o1.getStr1() == null) && (o2.getStr1() != null))
				return -1;
			else
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
		rendered = true;
		boolean b = true;
		Random r = new Random();
		for(int i = 0; i < 10; i++)
			data.add(new Data(i, r.nextInt(1000), Data.Random(6), r.nextInt(10000) + 98389, Data.Random(r.nextInt(10) + 1), r.nextInt(500000), Data.statusIcon[i % 5], Data.Random(3), false)); //new Data(i, Data.Random(5), Data.statusIcon[i % 5], Data.Random(6), false));
		
		data.get(0).setStr0(null);
		data.get(1).setStr1(null);
		data.get(2).setStr2(null);
		data.get(3).setStr3(null);	
		dataModel = new ListDataModel(data);
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

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public ListDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(ListDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public HtmlDataTable getMyDataTable() {
		return myDataTable;
	}

	public void setMyDataTable(HtmlDataTable myDataTable) {
		this.myDataTable = myDataTable;
	}
	public void select() {
		selectedItem =  (Data)dataModel.getRowData();
	}

	public Data getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Data selectedItem) {
		this.selectedItem = selectedItem;
	}

}
