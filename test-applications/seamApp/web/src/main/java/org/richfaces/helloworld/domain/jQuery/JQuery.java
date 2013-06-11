package org.richfaces.helloworld.domain.jQuery;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlJQuery;

@Name("jQuery")
@Scope(ScopeType.SESSION)
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
