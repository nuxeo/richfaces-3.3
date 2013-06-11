package org.richfaces;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class ListShuttleDemoRequestBean {

	private UIComponent eventsBouncer;
	
	public UIComponent getEventsBouncer() {
		if (eventsBouncer == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIComponent output = facesContext.getApplication().createComponent(UIOutput.COMPONENT_TYPE);
			UIOutput o = new UIEventsBouncer();
			
			o.getAttributes().put("escape", Boolean.FALSE);
			output.getChildren().add(o);

			eventsBouncer = output;
		}
		
		return eventsBouncer;
	}
	
	public void setEventsBouncer(UIComponent component) {
		this.eventsBouncer = component;
	}
}
