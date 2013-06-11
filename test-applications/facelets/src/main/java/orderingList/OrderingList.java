package orderingList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.html.HtmlDataTable;

import org.richfaces.component.html.HtmlOrderingList;

import util.componentInfo.ComponentInfo;
import util.data.Data;

public class OrderingList{
	private ArrayList<String> info;
	private ArrayList<Data> list;
	private String captionLabel;
	private Collection<Data> selection;
	private String controlsType;
	private String controlsVerticalAlign;
	private String controlsHorizontalAlign;
	private String bottomControlLabel;
	private String upControlLabel;
	private String topControlLabel;
	private String downControlLabel;
	private String listHeight;
	private String listWidth;
	private int lenght; 
	private boolean orderControlsVisible;
	private boolean fastOrderControlsVisible;
	private boolean rendered;
	private boolean showButtonLabels;
	private boolean facet;
	private boolean showAllData;
	private boolean showSelect;
	private HtmlOrderingList htmlOL = null;
	
	public HtmlOrderingList getHtmlOL() {
		return htmlOL;
	}

	public void setHtmlOL(HtmlOrderingList htmlOL) {
		this.htmlOL = htmlOL;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlOL);
		return null;
	}

	public boolean isFacet() {
		return facet;
	}

	public void setFacet(boolean facet) {
		this.facet = facet;
	}

	public Collection<Data> getSelection() {
		return selection;
	}

	public void setSelection(Collection<Data> selection) {
		System.out.println("Selection.out: " + selection);
		info.clear();		
		addSelection(selection);
		this.selection = selection;
	}

	public ArrayList<Data> getList() {
		return list;
	}

	public void setList(ArrayList<Data> list) {
		this.list = list;
	}

	public void addNewItem() {
		if(lenght < 0) lenght = 0;
		if(list.size() > lenght)
			for(int i = lenght; i < list.size(); )
				list.remove(i);
		else
			for(int i = list.size() + 1; i <= lenght; i++)
				list.add(new Data(i, "Button " + i, "Link " + i, "select" +(i % 5), Data.statusIcon[i % 5]));
	}	
	
	public OrderingList() {
		this.info = new ArrayList<String>();
		this.captionLabel = "captionLabel";
		this.lenght = 10;
		this.listHeight = "300"; 
		this.listWidth = "800";
		this.controlsType = "button";
		this.controlsHorizontalAlign = "right";
		this.controlsVerticalAlign = "center";
		this.bottomControlLabel = "bottom label";
		this.upControlLabel = "up label";
		this.downControlLabel = "down label";
		this.topControlLabel = "top label";
		this.orderControlsVisible = true;
		this.fastOrderControlsVisible = true;
		this.rendered = true;
		this.showButtonLabels = true;
		this.showAllData = true;
		this.showSelect = true;
		this.list = new ArrayList<Data>();
		addNewItem();
	}

	public String getListHeight() {
		return listHeight;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public String getControlsType() {
		return controlsType;
	}

	public void setControlsType(String controlsType) {
		this.controlsType = controlsType;
	}

	public String getBottomControlLabel() {
		return bottomControlLabel;
	}

	public void setBottomControlLabel(String bottomControlLabel) {
		this.bottomControlLabel = bottomControlLabel;
	}

	public String getCaptionLabel() {
		return captionLabel;
	}

	public void setCaptionLabel(String captionLabel) {
		this.captionLabel = captionLabel;
	}

	public String getUpControlLabel() {
		return upControlLabel;
	}

	public void setUpControlLabel(String upControlLabel) {
		this.upControlLabel = upControlLabel;
	}

	public String getTopControlLabel() {
		return topControlLabel;
	}

	public void setTopControlLabel(String topControlLabel) {
		this.topControlLabel = topControlLabel;
	}

	public String getControlsVerticalAlign() {
		return controlsVerticalAlign;
	}

	public void setControlsVerticalAlign(String controlsVerticalAlign) {
		this.controlsVerticalAlign = controlsVerticalAlign;
	}

	public String getControlsHorizontalAlign() {
		return controlsHorizontalAlign;
	}

	public void setControlsHorizontalAlign(String controlsHorizontalAlign) {
		this.controlsHorizontalAlign = controlsHorizontalAlign;
	}

	public String getDownControlLabel() {
		return downControlLabel;
	}

	public void setDownControlLabel(String downControlLabel) {
		this.downControlLabel = downControlLabel;
	}

	public boolean isOrderControlsVisible() {
		return orderControlsVisible;
	}

	public void setOrderControlsVisible(boolean orderControlsVisible) {
		this.orderControlsVisible = orderControlsVisible;
	}

	public boolean isFastOrderControlsVisible() {
		return fastOrderControlsVisible;
	}

	public void setFastOrderControlsVisible(boolean fastOrderControlsVisible) {
		this.fastOrderControlsVisible = fastOrderControlsVisible;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isShowButtonLabels() {
		return showButtonLabels;
	}

	public void setShowButtonLabels(boolean showButtonLabels) {
		this.showButtonLabels = showButtonLabels;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	public void cbAction() {
		info.clear();
		info.add("commandButton submit();");
		addSelection(getSelection());
	}
	
	public void clAction() {
		info.clear();
		info.add("commandLink submit();");
		addSelection(getSelection());
	}

	public void bTest1(){
		setCaptionLabel("Caption Test1");
		setControlsHorizontalAlign("0");
		setControlsVerticalAlign("0");
		setControlsType("button");
		setLenght(40);
		setListHeight("300");
		setListWidth("400");
		setFastOrderControlsVisible(true);
		setOrderControlsVisible(true);
		setRendered(true);
		setShowButtonLabels(true);
		setTopControlLabel("t1 top label");
		setBottomControlLabel("t1 bottom label");
		setUpControlLabel("t1 up label");
		setDownControlLabel("t1 down label");
		addNewItem();
	}
	
	public void bTest2(){
		setCaptionLabel("Caption Test2");
		setControlsHorizontalAlign("30");
		setControlsVerticalAlign("50");
		setControlsType("button");
		setLenght(40);
		setListHeight("600");
		setListWidth("400");
		setFastOrderControlsVisible(false);
		setOrderControlsVisible(true);
		setRendered(true);
		setShowButtonLabels(true);
		setTopControlLabel("t2");
		setBottomControlLabel("t2");
		setUpControlLabel("t2");
		setDownControlLabel("t2");
		addNewItem();
	}

	public void bTest3(){
		setCaptionLabel("Caption Test3");
		setControlsHorizontalAlign("50");
		setControlsVerticalAlign("10");
		setControlsType("button");
		setLenght(40);
		setListHeight("500");
		setListWidth("500");
		setFastOrderControlsVisible(true);
		setOrderControlsVisible(false);
		setRendered(true);
		setShowButtonLabels(true);
		setTopControlLabel("top");
		setBottomControlLabel("bottom");
		setUpControlLabel("up");
		setDownControlLabel("down");
		addNewItem();
	}

	public void bTest4(){
		setCaptionLabel("Caption Test4");
		setControlsHorizontalAlign("0");
		setControlsVerticalAlign("0");
		setControlsType("button");
		setLenght(10000);
		setListHeight("500");
		setListWidth("600");
		setFastOrderControlsVisible(true);
		setOrderControlsVisible(true);
		setRendered(true);
		setShowButtonLabels(false);
		setTopControlLabel("");
		setBottomControlLabel("");
		setUpControlLabel("");
		setDownControlLabel("");
		addNewItem();
	}

	public void bTest5(){
		setCaptionLabel("Caption Test5");
		setControlsHorizontalAlign("20");
		setControlsVerticalAlign("20");
		setControlsType("none");
		setLenght(100);
		setListHeight("400");
		setListWidth("500");
		setFastOrderControlsVisible(true);
		setOrderControlsVisible(true);
		setRendered(true);
		setShowButtonLabels(true);
		setTopControlLabel("top");
		setBottomControlLabel("bottom");
		setUpControlLabel("up");
		setDownControlLabel("down");
		addNewItem();
	}

	public ArrayList<String> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = info;
	}
	
	private void addSelection(Collection<Data> selection) {
		if(selection == null) return;
		Iterator<Data> inter = selection.iterator();
		Data data = new Data();
		while (inter.hasNext()) {
			data = inter.next();
			info.add(data.getInt0() + "; " + data.getStr0() + "; " + data.getStr0() + "submit(); " + data.getStr1() + "; "  + data.getStr1() + "submit(); " + data.getStr2() + "; " + data.getStr3());
		}
	}

	public boolean isShowAllData() {
		return showAllData;
	}

	public void setShowAllData(boolean showAllData) {
		this.showAllData = showAllData;
	}

	public boolean isShowSelect() {
		return showSelect;
	}

	public void setShowSelect(boolean showSelect) {
		this.showSelect = showSelect;
	}
}
