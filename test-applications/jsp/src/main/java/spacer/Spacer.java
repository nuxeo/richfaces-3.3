package spacer;

import org.richfaces.component.html.HtmlSpacer;

import util.componentInfo.ComponentInfo;

public class Spacer {

	private String width;
	private String height;
	private String style;
	private String btn;
	private String title;
	private boolean rendered;
	private HtmlSpacer htmlSpacer = null;
	
	public void addHtmlSpacer(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlSpacer);
	}
	
	public HtmlSpacer getHtmlSpacer() {
		return htmlSpacer;
	}

	public void setHtmlSpacer(HtmlSpacer htmlSpacer) {
		this.htmlSpacer = htmlSpacer;
	}

	public Spacer() {
		title = "title text";
		height = "50px";
		width = "300px";
		rendered = true;
		style = null;
		btn = "Switch on styleClass";
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String doStyle() {
		if (getStyle() == null) {
			setBtn("Switch off styleClass");
			setStyle("spacer");
		} else {
			setStyle(null);
			setBtn("Switch on styleClass");
		}
		// if("spacer".equals(getStyle())) setStyle(null);
		return null;
	}

	public String getBtn() {
		return btn;
	}

	public void setBtn(String btn) {
		this.btn = btn;
	}
	
	public void bTest1(){
		setHeight("100");
		setWidth("10");
	}

	public void bTest2(){
		setHeight("10");
		setWidth("100");
	}
	
	public void bTest3(){
		setHeight("5");
		setWidth("5");
	}
	
	public void bTest4(){
		setHeight("100");
		setWidth("100");
	}
	
	public void bTest5(){
		setHeight("400");
		setWidth("200");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
