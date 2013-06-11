package org.richfaces.regressionarea.issues.rf4507;

import org.richfaces.model.CalendarDataModelItem;

/**
 * @author mikalaj
 *
 */
public class CalendarDataModelItemImpl implements CalendarDataModelItem {

	private Object data;
	
	private int day;
	
	public CalendarDataModelItemImpl(Object data, int day) {
		super();
		this.data = data;
		this.day = day;
	}

	public CalendarDataModelItemImpl() {
		super();
	}
	
	public Object getData() {
		return data;
	}

	public int getDay() {
		return day;
	}

	public String getStyleClass() {
		return null;
	}

	public Object getToolTip() {
		return null;
	}

	public boolean hasToolTip() {
		return false;
	}

	public boolean isEnabled() {
		return true;
	}

}
