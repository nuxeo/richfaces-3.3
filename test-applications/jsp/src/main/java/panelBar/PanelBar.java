package panelBar;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlPanelBar;

import util.componentInfo.ComponentInfo;

public class PanelBar {

	private String width;
	private HtmlPanelBar panelBar; 
	private String height;
	private String contentStyle;
	private String style=null;
	private String headerStyle=null;
	private String btnLabel="ON";

	private String[] label = {"label_0","label_1", "label_2", "label_3"};
	               
	private boolean rendered;

	public void addHtmlPanelBar(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(panelBar);
	}
	
	public PanelBar() {

		height = "300px";
		width = "500px";
		rendered = true;
		//contentStyle=null;
		//style=null;
		//headerStyle=null;
	}
	
	public void actionListener(ActionEvent e) {
		//Application application = FacesContext.getCurrentInstance().getApplication();
		String pId = panelBar.getId();
		if(pId != null){
			System.out.println("Binding test passed. PanelBar ID = " + pId);
		}
	}
	
	public void doStyles()
	{
		if (getContentStyle() == null) {
			setBtnLabel("OFF");
			setContentStyle("contentStyle");
			setHeaderStyle("activeTabStyle");
			setStyle("style");			
		} else {
			setBtnLabel("ON");
			setContentStyle(null);
			setHeaderStyle(null);
			setStyle(null);	
		}
	}
	
	public String getHeight() {
		return height;
	}

	public String getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String[] getLabel() {
		return label;
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

	public void setLabel(String[] label) {

		this.label = label;

	}

	public void makeLabels(ValueChangeEvent event) {
		String tlabel = event.getNewValue().toString();
		if (tlabel.equalsIgnoreCase("")) {
			for (int i = 0; i < 4; i++) {
				label[i] = "label_" + i;
			}
		} else {
			for (int i = 0; i < 4; i++) {
				label[i] = tlabel + "_" + i;
			}
		}

	}

	public String getBtnLabel() {
		return btnLabel;
	}

	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}

	public void bTest1(){
		setWidth("500px");
		setHeight("300px");
		setLabel(new String [] {"Test1", "Test 1", "Test_1", "Test-1"});
	}
	
	public void bTest2(){
		setWidth("500px");
		setHeight("600px");
		setLabel(new String [] {"Test2", "Test 2", "Test_2", "Test-2"});
	}
	
	public void bTest3(){
		setWidth("50%");
		setHeight("300px");
		setLabel(new String [] {"Test3", "Test 3", "Test_3", "Test-3"});
	}
	
	public void bTest4(){
		setWidth("400px");
		setHeight("10%");
		setLabel(new String [] {"Test4", "Test 4", "Test_4", "Test-4"});
	}
	
	public void bTest5(){
		setWidth("400px");
		setHeight("400px");
		setLabel(new String [] {"Test5", "Test 5", "Test_5", "Test-5"});
	}

	public HtmlPanelBar getPanelBar() {
		return panelBar;
	}

	public void setPanelBar(HtmlPanelBar panelBar) {
		this.panelBar = panelBar;
	}
}
