package dataTable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.richfaces.component.html.HtmlDataTable;

import util.componentInfo.ComponentInfo;

public class DataTable {
	
	private static final String [] mNames={"Jan","Feb","Mar","Apr","May", "Jun", "Jul"};
	private static final String [] mDay={"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private static final String [] mTtown = {"Adrian", "Ailey", "Alamo", "Alapaha", "Albany", "Allenhurst", "Alma"}; 
	private List mounths = new ArrayList(); 
	private List numbers = new ArrayList();
	private String align; 
	private String border;
	private String width;
	private String columns;
	private String columnsWidth;
	private String cellpadding;
	private String cellspacing;
	private String rows;
	private boolean r2rendered;
	private boolean rendered;
	private HtmlDataTable htmlDataTable = null;
	
	public void addHtmlDataTable(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDataTable);
	}
	
	public HtmlDataTable getHtmlDataTable() {
		return htmlDataTable;
	}

	public void setHtmlDataTable(HtmlDataTable htmlDataTable) {
		this.htmlDataTable = htmlDataTable;
	}

	public DataTable() {
		columns = "2";
		cellpadding = "0";
		cellspacing = "0";
		rows = "0";
		align = "center";
		border = "1";
		width = "400px";
		columnsWidth = "200px";
		rendered = true;
		r2rendered = true;
		
		Properties properties = System.getProperties();
		Enumeration keys = properties.keys();
		for(int i=0;i<7;i++){
			Data bean = new Data();
			//int l = (int)(Math.random()*8)+1;
			int l = 1;
			bean.setTotal(0);
			bean.setMounth(DataTable.mNames[i]);
			bean.setDay(DataTable.mDay[i]);
			bean.setTown(DataTable.mTtown[i]);
			mounths.add(bean);
			for(int j=0;j<l;j++){
				ChildBean child = new ChildBean();
				child.setName((String) keys.nextElement());
				int qty = (int)(Math.random()*10);
				bean.setTotal(bean.getTotal()+qty);
				child.setQty(qty);
				bean.getDetail().add(child);
			}
		}
		for(int i=0;i<1;i++){
			numbers.add(new Integer(i));
		}
	}

	public boolean isC1rendered() {
		return r2rendered;
	}
	
	public void setC1rendered(boolean c1rendered) {
		this.r2rendered = c1rendered;
	}
	
	public String toggleColumn() {
		this.r2rendered = !this.r2rendered;
		return null;
	}
	/**
	 * @return the numbers
	 */
	public List getNumbers() {
		return this.numbers;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/**
	 * @param numbers the numbers to set
	 */
	public void setNumbers(List numbers) {
		this.numbers = numbers;
	}

	/**
	 * @return the mounths
	 */
	public List getMounths() {
		return this.mounths;
	}

	/**
	 * @param mounths the mounths to set
	 */
	public void setMounths(List mounths) {
		this.mounths = mounths;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getColumnsWidth() {
		return columnsWidth;
	}

	public void setColumnsWidth(String columnsWidth) {
		this.columnsWidth = columnsWidth;
	}

	public boolean isR2rendered() {
		return r2rendered;
	}

	public void setR2rendered(boolean r2rendered) {
		this.r2rendered = r2rendered;
	}
	
	public void bTest1() {
		setAlign("center");
		setBorder("4px");
		setC1rendered(false);
		setColumnsWidth("300px");
		setWidth("500px");
	}

	public void bTest2() {
		setAlign("left");
		setBorder("0px");
		setC1rendered(true);
		setColumnsWidth("500px");
		setWidth("300px");
	}

	public void bTest3() {
		setAlign("right");
		setBorder("5px");
		setC1rendered(true);
		setColumnsWidth("100px");
		setWidth("200px");
	}

	public void bTest4() {
		setAlign("center");
		setBorder("4px");
		setC1rendered(false);
		setColumnsWidth("500px");
		setWidth("500px");
	}

	public void bTest5() {
		setAlign("center");
		setBorder("4px");
		setC1rendered(false);
		setColumnsWidth("400px");
		setWidth("800px");
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getCellpadding() {
		return cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public String getCellspacing() {
		return cellspacing;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
}
