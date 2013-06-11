package separator;

import org.richfaces.component.html.HtmlSeparator;

import util.componentInfo.ComponentInfo;

public class Separator {

	
	
	private String width;
	private String title;
	private String height;
	private String lineType;
	private String align;
	private String btnLabel="ON";
	private String style;
	private HtmlSeparator htmlSeparator = null;	
	private boolean rendered; 
	
	public void addHtmlSeparator(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlSeparator);
	}
		
	public String getBtnLabel() {
		return btnLabel;
	}



	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}



	public String getStyle() {
		return style;
	}



	public void setStyle(String style) {
		this.style = style;
	}



	public Separator() {
		width="300px";
		height="10px";
		title="title goes here";
		rendered=true;
		lineType="beveled";//beveled (default), dotted, dashed, double and solid
		align="left"; //left|center|right
		style=null;
	}
	
	public void doStyles() {
		if (getStyle() == null) {
			setBtnLabel("OFF");
			setStyle("style");
		} else {
			setStyle(null);
			setBtnLabel("ON");
		}
	
	}


	public String getAlign() {
		return align;
	}



	public void setAlign(String align) {
		this.align = align;
	}



	public String getHeight() {
		return height;
	}



	public void setHeight(String height) {
		this.height = height;
	}



	public String getLineType() {
		return lineType;
	}



	public void setLineType(String lineType) {
		this.lineType = lineType;
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



	public String getWidth() {
		return width;
	}



	public void setWidth(String width) {
		this.width = width;
	}

	public void bTest1(){
		setAlign("left");
		setHeight("300px");
		setWidth("10px");
		setLineType("beveled");
		setTitle("Test1");
	}

	public void bTest2(){
		setAlign("center");
		setHeight("50px");
		setWidth("600px");
		setLineType("dotted");
		setTitle("Test2");		
	}

	public void bTest3(){
		setAlign("right");
		setHeight("150px");
		setWidth("200px");
		setLineType("dashed");
		setTitle("Test3");		
	}

	public void bTest4(){
		setAlign("left");
		setHeight("20px");
		setWidth("300px");
		setLineType("double");
		setTitle("Test4");		
	}

	public void bTest5(){
		setAlign("left");
		setHeight("100px");
		setWidth("100px");
		setLineType("solid");
		setTitle("Test5");		
	}

	public HtmlSeparator getHtmlSeparator() {
		return htmlSeparator;
	}

	public void setHtmlSeparator(HtmlSeparator htmlSeparator) {
		this.htmlSeparator = htmlSeparator;
	}
}
