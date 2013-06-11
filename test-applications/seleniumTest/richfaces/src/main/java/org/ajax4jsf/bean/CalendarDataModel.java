/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.Calendar;
import java.util.Date;

import org.richfaces.model.CalendarDataModelItem;

/**
 * @author Andrey Markavtsov
 *
 */
public class CalendarDataModel implements org.richfaces.model.CalendarDataModel {

	/* (non-Javadoc)
	 * @see org.richfaces.model.CalendarDataModel#getData(java.util.Date[])
	 */
	public CalendarDataModelItem[] getData(Date[] dateArray) {
		CalendarDataModelItem[] items = new CalendarDataModelItemImpl[dateArray.length];
		for (int i = 0;i < items.length; i++) {
			items[i] = new CalendarDataModelItemImpl(dateArray[i]);
		}
		return items; 
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.CalendarDataModel#getToolTip(java.util.Date)
	 */
	public Object getToolTip(Date date) {
		return null;
	}

}

class CalendarDataModelItemImpl implements CalendarDataModelItem {
	
	int day;
	Object data;
	Calendar calendar;
	
	public CalendarDataModelItemImpl(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        day = c.get(Calendar.DAY_OF_MONTH);
        this.data = "data" + day;
        this.calendar = c;
	}

	public Object getData() {
		return data;
	}

	public int getDay() {
		return day;
	}

	public String getStyleClass() {
		return  "styleClass" + this.calendar.get(Calendar.MONTH) + day;
	}

	public Object getToolTip() {
		return null;
	}

	public boolean hasToolTip() {
		return false;
	}

	public boolean isEnabled() {
		return day != 13;
	}
	
}
