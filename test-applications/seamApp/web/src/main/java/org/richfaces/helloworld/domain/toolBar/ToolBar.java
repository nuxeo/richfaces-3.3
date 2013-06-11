package org.richfaces.helloworld.domain.toolBar;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlToolBar;

@Name("toolBar")
@Scope(ScopeType.SESSION)
public class ToolBar {

	private String width;
	private String height;
	private String itemSeparator;//none, line, square, disc and grid
	private String location;//A location of a group on a menu bar. Possible values are left and right
	private HtmlToolBar htmlToolBar = null;
	private String contentStyle;
	private String separatorStyle;
	private String btnLabel="ON";
	private boolean rendered;
	
	
	
	public ToolBar() {
		rendered=true;
		width="75%";
		height="50px";
		itemSeparator="square";
		location="left";
		contentStyle=null;
		separatorStyle=null;
	}

	
	public void doStyles()
	{
		if (getSeparatorStyle() == null) {
			setBtnLabel("OFF");
			setContentStyle("contentStyle");
			setSeparatorStyle("separatorStyle");
			
		} else {
			setBtnLabel("ON");
			setContentStyle(null);
			setSeparatorStyle(null);
		}
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlToolBar);
		return null;
	}
	
	public String getHeight() {
		return height;
	}



	public void setHeight(String height) {
		this.height = height;
	}



	public String getItemSeparator() {
		return itemSeparator;
	}



	public void setItemSeparator(String itemSeparator) {
		this.itemSeparator = itemSeparator;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public boolean isRendered() {
		return rendered;
	}



	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}



	public String getWidth() {
		return width;
	}



	public void setWidth(String width) {
		this.width = width;
	}



	public String getContentStyle() {
		return contentStyle;
	}



	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}



	public String getSeparatorStyle() {
		return separatorStyle;
	}



	public void setSeparatorStyle(String separatorStyle) {
		this.separatorStyle = separatorStyle;
	}


	public String getBtnLabel() {
		return btnLabel;
	}


	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}


	public HtmlToolBar getHtmlToolBar() {
		return htmlToolBar;
	}


	public void setHtmlToolBar(HtmlToolBar htmlToolBar) {
		this.htmlToolBar = htmlToolBar;
	}

}
