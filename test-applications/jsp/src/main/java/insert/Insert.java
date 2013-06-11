package insert;

import org.richfaces.ui.component.html.HtmlInsert;

import util.componentInfo.ComponentInfo;

public class Insert {
	private String highlight;
	private String src;   
	private boolean rendered;
	private HtmlInsert htmlInsert = null;
	
	public void addHtmlInsert(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlInsert);
	}
	
	public HtmlInsert getHtmlInsert() {
		return htmlInsert;
	}

	public void setHtmlInsert(HtmlInsert htmlInsert) {
		this.htmlInsert = htmlInsert;
	}

	public Insert() {
		src = "/Insert/src/test.html";
		rendered = true;
		highlight = "HTML";
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
