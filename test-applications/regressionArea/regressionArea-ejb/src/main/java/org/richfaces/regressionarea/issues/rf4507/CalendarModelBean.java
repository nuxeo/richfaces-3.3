package org.richfaces.regressionarea.issues.rf4507;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

/**
 * This test case for: <a href="https://jira.jboss.org/jira/browse/RF-4507">RF-4507 - Calendar with preload 
 * overrides AJAX data for all preceding components</a>
 * 
 * @author Nick Belaevski
 * 
 */

@Name("rf4507")
@Scope(ScopeType.APPLICATION)
public class CalendarModelBean implements CalendarDataModel {

	public CalendarDataModelItem[] getData(Date[] dateArray) {
		if (dateArray == null) {
			return null;
		}
	
		Calendar calendar = Calendar.getInstance(Locale.US);
		CalendarDataModelItem[] items = new CalendarDataModelItem[dateArray.length];

		for (int i = 0; i < items.length; i++) {
			calendar.setTime(dateArray[i]);
			items[i] = new CalendarDataModelItemImpl(calendar.get(Calendar.DAY_OF_MONTH) + "-" + 
					(calendar.get(Calendar.MONTH) + 1), 
					calendar.get(Calendar.DAY_OF_MONTH));
		}
		
		return items;
	}

	public Object getToolTip(Date date) {
		return null;
	}

}
