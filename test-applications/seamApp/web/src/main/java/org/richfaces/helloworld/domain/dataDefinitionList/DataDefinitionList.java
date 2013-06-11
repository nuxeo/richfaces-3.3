package org.richfaces.helloworld.domain.dataDefinitionList;

import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.richfaces.helloworld.domain.util.data.Data;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlDataDefinitionList;

@Name("dataDefinitionList")
@Scope(ScopeType.SESSION)
public class DataDefinitionList
{
	private ArrayList<Data> arr;
	private int first;
	private int rows;
	private boolean rendered;
	private String title;
	private String dir;
	private int mSize;
	private HtmlDataDefinitionList myDefinitionList = null;
	private String bindLabel;
	private String columnClasses;
	
	public String getColumnClasses() {
		return columnClasses;
	}

	public void setColumnClasses(String columnClasses) {
		this.columnClasses = columnClasses;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myDefinitionList);
		return null;		
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
		bindLabel = "not ready";
		columnClasses = "test,style";
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

	public HtmlDataDefinitionList getMyDefinitionList() {
		return myDefinitionList;
	}

	public void setMyDefinitionList(HtmlDataDefinitionList myDefinitionList) {
		this.myDefinitionList = myDefinitionList;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myDefinitionList.getBaseClientId(context);
	}
	
}	