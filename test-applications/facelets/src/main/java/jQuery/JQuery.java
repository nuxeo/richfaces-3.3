package jQuery;

import org.richfaces.component.html.HtmlJQuery;

import util.componentInfo.ComponentInfo;

public class JQuery {

	private HtmlJQuery htmlJQuery = null;

	public HtmlJQuery getHtmlJQuery() {
		return htmlJQuery;
	}

	public void setHtmlJQuery(HtmlJQuery htmlJQuery) {
		this.htmlJQuery = htmlJQuery;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlJQuery);
		return null;
	}
}
