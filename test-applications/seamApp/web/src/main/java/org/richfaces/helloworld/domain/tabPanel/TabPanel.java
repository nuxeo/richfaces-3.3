package org.richfaces.helloworld.domain.tabPanel;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlTabPanel;

@Name("tabPanel")
@Scope(ScopeType.SESSION)
public class TabPanel {
	
	private String width;
	private String height;
	private String title;
	private String switchType; //"client", "server"(default), "ajax"
	private String headerAlignment; //"left", "center" and "right". 
	private String headerSpacing;
	private String selectedTab;
	private String labelWidth;
	private String label;
	private String activeTabStyle;
	private String	disabledTabStyle;
	private String	inactiveTabStyle;
	private String	contentStyle;
	private String BtnLabel="ON";
	private boolean immediate;
	private HtmlTabPanel htmlTabPanel = null;	
	private boolean rendered;
	private boolean disabledTab;
	
	public TabPanel() {
		width="75%";
		height="200px";
		title="title goes here...";
		switchType="server";
		headerAlignment="center";
		headerSpacing="20px";
		label="Tab Label";
		labelWidth="150px";
		
		activeTabStyle=null;
		disabledTabStyle=null;
		inactiveTabStyle=null;
		contentStyle=null;
		
		immediate = false;
		rendered=true;
		disabledTab=false;
		
	}
	
	public void doStyles()
	{
		if (getContentStyle() == null) {
			setBtnLabel("Off");
			setActiveTabStyle("activeTabStyle");
			setContentStyle("contentStyle");
			setDisabledTabStyle("disabledTabStyle");
			setInactiveTabStyle("inactiveTabStyle");
		} else {
			setBtnLabel("ON");
			setActiveTabStyle(null);
			setContentStyle(null);
			setDisabledTabStyle(null);
			setInactiveTabStyle(null);
		}
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlTabPanel);
		return null;
	}
	
	public String getHeaderAlignment() {
		return headerAlignment;
	}

	public void setHeaderAlignment(String headerAlignment) {
		this.headerAlignment = headerAlignment;
	}

	public String getHeaderSpacing() {
		return headerSpacing;
	}

	public void setHeaderSpacing(String headerSpacing) {
		this.headerSpacing = headerSpacing;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isDisabledTab() {
		return disabledTab;
	}

	public void setDisabledTab(boolean disabledTab) {
		this.disabledTab = disabledTab;
	}

	public String getActiveTabStyle() {
		return activeTabStyle;
	}

	public void setActiveTabStyle(String activeTabStyle) {
		this.activeTabStyle = activeTabStyle;
	}

	public String getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}

	public String getDisabledTabStyle() {
		return disabledTabStyle;
	}

	public void setDisabledTabStyle(String disabledTabStyl) {
		this.disabledTabStyle = disabledTabStyl;
	}

	public String getInactiveTabStyle() {
		return inactiveTabStyle;
	}

	public void setInactiveTabStyle(String inactiveTabStyle) {
		this.inactiveTabStyle = inactiveTabStyle;
	}

	public String getBtnLabel() {
		return BtnLabel;
	}

	public void setBtnLabel(String btnLabel) {
		BtnLabel = btnLabel;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}
	
	public void bTest1() {
		setDisabledTab(false);
		setHeaderAlignment("left");
		setHeaderSpacing("10px");
		setHeight("250px");
		setImmediate(false);
		setLabel("Test 1");
		setLabelWidth("10px");
		setWidth("300px");
		setSelectedTab("2");
		setSwitchType("server");
		setTitle("Title test 1");
	}

	public void bTest2() {
		setDisabledTab(true);
		setHeaderAlignment("right");
		setHeaderSpacing("40px");
		setHeight("400px");
		setImmediate(false);
		setLabel("Test 2");
		setLabelWidth("40px");
		setWidth("40%");
		setSelectedTab("1");
		setSwitchType("ajax");
		setTitle("Title test2");
	}

	public void bTest3() {
		setDisabledTab(false);
		setHeaderAlignment("center");
		setHeaderSpacing("20px");
		setHeight("20%");
		setImmediate(false);
		setLabel("Test 3");
		setLabelWidth("400px");
		setWidth("600px");
		setSelectedTab("3");
		setSwitchType("client");
		setTitle("Title test 3");
	}

	public void bTest4() {
		setDisabledTab(true);
		setHeaderAlignment("left");
		setHeaderSpacing("300px");
		setHeight("500px");
		setImmediate(false);
		setLabel("Teset 4");
		setLabelWidth("500px");
		setWidth("500px");
		setSelectedTab("2");
		setSwitchType("server");
		setTitle("Title test 4");
	}

	public void bTest5() {
		setDisabledTab(false);
		setHeaderAlignment("left");
		setHeaderSpacing("20px");
		setHeight("40%");
		setImmediate(false);
		setLabel("Test 5");
		setLabelWidth("40px");
		setWidth("10%");
		setSelectedTab("3");
		setSwitchType("client");
		setTitle("Title test 5");
	}

	public HtmlTabPanel getHtmlTabPanel() {
		return htmlTabPanel;
	}

	public void setHtmlTabPanel(HtmlTabPanel htmlTabPanel) {
		this.htmlTabPanel = htmlTabPanel;
	}

}
