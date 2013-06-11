package dataOrderedList;

import java.util.ArrayList;

import org.richfaces.component.html.HtmlDataOrderedList;

import util.componentInfo.ComponentInfo;
import util.data.Data;

public class DataOrderedList
{
	private ArrayList<Data> arr;
	private int first;
	private int rows;
	private boolean rendered;
	private String title;
	private String type;
	private String dir;
	private int mSize;
	private ArrayList defaultArr;
	private HtmlDataOrderedList htmlDataOrderedList = null;

	public void addHtmlDataOrderedList(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDataOrderedList);
	}
	
	public HtmlDataOrderedList getHtmlDataOrderedList() {
		return htmlDataOrderedList;
	}
	
	public void setHtmlDataOrderedList(HtmlDataOrderedList htmlDataOrderedList){
		this.htmlDataOrderedList = htmlDataOrderedList;
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

	public DataOrderedList(){
		arr = new ArrayList<Data>();
		defaultArr = new ArrayList<String>();
		dir ="LTR";
		rows = 20;
		rows = 0;
		first = 0;
		rendered = true;
		title = "title";
		type = "1";
		for(int i = 1; i < Data.cityAfrica.length; i++)
			arr.add(new Data(Data.cityAfrica[i]));
		for(int i = 0; i < 5; i++)
			defaultArr.add("text_" + i);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public ArrayList getDefaultArr() {
		return defaultArr;
	}

	public void setDefaultArr(ArrayList defaultArr) {
		this.defaultArr = defaultArr;
	}
	
}	