package dataDefinitionList;

import java.util.ArrayList;

import org.richfaces.component.html.HtmlDataDefinitionList;

import util.componentInfo.ComponentInfo;
import util.data.Data;

public class DataDefinitionList
{
	private ArrayList<Data> arr;
	private int first;
	private int rows;
	private boolean rendered;
	private String title;
	private String dir;
	private int mSize;
	private HtmlDataDefinitionList htmlDataDefinitionList = null;
	
	public HtmlDataDefinitionList getHtmlDataDefinitionList() {
		return htmlDataDefinitionList;
	}
	
	public void setHtmlDataDefinitionList(HtmlDataDefinitionList htmlDataDefinitionList){
		this.htmlDataDefinitionList = htmlDataDefinitionList;
	}
	
	public void addHtmlDataDefinitionList(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDataDefinitionList);
	}
		
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getMSize() {
		return mSize;
	}

	public void setMSize(int size) {
		mSize = size;
	}

	public void setArr(ArrayList<Data> arr) {
		this.arr = arr;
	}

	public DataDefinitionList(){
		arr = new ArrayList<Data>();
		dir ="LTR";
		rows = 20;
		rows = 0;
		first = 0;
		rendered = true;
		title = "title";
		for(int i = 1; i < Data.cityAfrica.length; i++)
			arr.add(new Data(Data.cityAfrica[i]));
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ArrayList<Data> getArr() {
		return arr;
	}
	
}	