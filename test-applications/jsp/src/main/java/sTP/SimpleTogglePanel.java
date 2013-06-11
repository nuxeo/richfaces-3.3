package sTP;

import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlSimpleTogglePanel;

import util.componentInfo.ComponentInfo;

public class SimpleTogglePanel {

	private String switchType; // "client", "server"(default), "ajax"
	private String width;
	private String height;
	private String position =  "right";
	private boolean focus;
	private boolean rendered;
	private String action;
	private String actionListener;
	private HtmlSimpleTogglePanel htmlSimpleTogglePanel = null;

	public void addHtmlSimpleTogglePanel(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlSimpleTogglePanel);
	}

	public HtmlSimpleTogglePanel getHtmlSimpleTogglePanel() {
		return htmlSimpleTogglePanel;
	}

	public void setHtmlSimpleTogglePanel(HtmlSimpleTogglePanel htmlSimpleTogglePanel) {
		this.htmlSimpleTogglePanel = htmlSimpleTogglePanel;
	}

	public SimpleTogglePanel() {
		width = "75%";
		height = "100%";
		switchType = "server";
		focus = true;
		rendered = true;
		action = "---";
		actionListener = "---";
	}

	public String act() {
		action = "action work!";
		return null;
	}
	
	public void actListener(ActionEvent e){
		actionListener = "actionListener work!"; 
	}
	
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	
	public void bTest1(){
		setHeight("80px");
		setWidth("300px");
		setSwitchType("ajax");
	}

	public void bTest2(){
		setHeight("10%");
		setWidth("100%");
		setSwitchType("client");		
	}
	
	public void bTest3(){
		setHeight("80px");
		setWidth("100%");
		setSwitchType("server");		
	}
	
	public void bTest4(){
		setHeight("10%");
		setWidth("200px");
		setSwitchType("client");		
	}
	
	public void bTest5(){
		setHeight("400px");
		setWidth("250px");
		setSwitchType("client");		
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAction() {
		return action;
	}

	public String getActionListener() {
		return actionListener;
	}
}
