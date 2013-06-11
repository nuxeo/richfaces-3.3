package org.richfaces.helloworld.domain.insert;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.ui.component.html.HtmlInsert;

@Name("insert")
@Scope(ScopeType.SESSION)
public class Insert {
	private String highlight;
	private String src;   
	private boolean rendered;
	private HtmlInsert htmlInsert = null;
	
	public HtmlInsert getHtmlInsert() {
		return htmlInsert;
	}

	public void setHtmlInsert(HtmlInsert htmlInsert) {
		this.htmlInsert = htmlInsert;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlInsert);
		return null;
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
