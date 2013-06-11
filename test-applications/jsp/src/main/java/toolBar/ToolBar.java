package toolBar;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlToolBar;

import util.componentInfo.ComponentInfo;

public class ToolBar {

	private String width;
	private String height;
	private String itemSeparator;//none, line, square, disc and grid
	private String location;//A location of a group on a menu bar. Possible values are left and right
	private HtmlToolBar htmlToolBar = null;
	private String contentClass;
	private String separatorClass;
	private String btnLabel="ON";
	private boolean rendered;
	private String bindLabel="not checked";
	
	public void addHtmlToolBar(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlToolBar);
	}
	
	public HtmlToolBar getHtmlToolBar() {
		return htmlToolBar;
	}


	public void setHtmlToolBar(HtmlToolBar htmlToolBar) {
		this.htmlToolBar = htmlToolBar;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = htmlToolBar.getClientId(context);
	}	
	
	public ToolBar() {
		rendered=true;
		width="75%";
		height="50px";
		itemSeparator="line";
		location="left";
		contentClass=null;
		separatorClass=null;
	}
	
	public void doStyles()
	{
		if (getSeparatorClass() == null) {
			setBtnLabel("OFF");
			setContentClass("contentClass");
			setSeparatorClass("separatorClass");
			
		} else {
			setBtnLabel("ON");
			setContentClass(null);
			setSeparatorClass(null);
		}
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



	public String getContentClass() {
		return contentClass;
	}



	public void setContentClass(String contentClass) {
		this.contentClass = contentClass;
	}



	public String getSeparatorClass() {
		return separatorClass;
	}



	public void setSeparatorClass(String separatorClass) {
		this.separatorClass = separatorClass;
	}


	public String getBtnLabel() {
		return btnLabel;
	}


	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

}
