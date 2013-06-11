package org.rf.datatable;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

public class UpdateBean {
	HtmlInputText priceRef;

	public HtmlInputText getPriceRef() {
		return priceRef;
	}

	public void setPriceRef(HtmlInputText priceRef) {
		this.priceRef = priceRef;
	}
	
	public String change(){
		priceRef.processValidators(FacesContext.getCurrentInstance());
		priceRef.processUpdates(FacesContext.getCurrentInstance());
		return null;
	}
}
