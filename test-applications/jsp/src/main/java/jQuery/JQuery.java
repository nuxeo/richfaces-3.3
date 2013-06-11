package jQuery;

import org.richfaces.component.html.HtmlJQuery;

import util.componentInfo.ComponentInfo;

public class JQuery {

	private HtmlJQuery htmlJQuery = null;
	
	public JQuery(){
		
	}
	public void add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlJQuery);
	}
	public HtmlJQuery getHtmlJQuery() {
		return htmlJQuery;
	}

	public void setHtmlJQuery(HtmlJQuery htmlJQuery) {
		this.htmlJQuery = htmlJQuery;
	}
}
