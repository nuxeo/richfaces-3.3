package org.richfaces.helloworld.domain.componentControl;

import org.richfaces.component.html.HtmlComponentControl;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("componentControl")
@Scope(ScopeType.SESSION)
public class ComponentControl {
	private boolean calendarPopup;
	private HtmlComponentControl htmlComponentControl = null;
	
	public HtmlComponentControl getHtmlComponentControl() {
		return htmlComponentControl;
	}

	public void setHtmlComponentControl(HtmlComponentControl htmlComponentControl) {
		this.htmlComponentControl = htmlComponentControl;
	}
	
	public String addHtmlComponentControl(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlComponentControl);
		return null;		
	}
	public ComponentControl() {
		calendarPopup = false;
	}

	public boolean isCalendarPopup() {
		return calendarPopup;
	}

	public void setCalendarPopup(boolean calendarPopup) {
		this.calendarPopup = calendarPopup;
	}
}
