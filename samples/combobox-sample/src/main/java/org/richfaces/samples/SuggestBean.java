package org.richfaces.samples;

import java.util.Date;
import javax.faces.model.SelectItem;


public class SuggestBean {
		
	private String property;
	private String property2;
		
	private SelectItem[] comboSelectItems = { new SelectItem("Test", "Test"), new SelectItem("Test2", "Test2"), new SelectItem("Test3", "Test3") };
	
	
	public void updateProperty2() {
		setProperty2((new Date()).toString());
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty2() {
		return property2;
	}

	public void setProperty2(String property2) {
		this.property2 = property2;
	}

	public SelectItem[] getComboSelectItems() {
		return comboSelectItems;
	}

	public void setComboSelectItems(SelectItem[] comboSelectItems) {
		this.comboSelectItems = comboSelectItems;
	}
}
