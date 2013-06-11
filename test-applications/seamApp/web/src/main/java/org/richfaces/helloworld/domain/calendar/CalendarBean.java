/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces.helloworld.domain.calendar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.faces.event.ValueChangeEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.UICalendar;
import org.richfaces.event.CurrentDateChangeEvent;

@Name("calendarBean")
@Scope(ScopeType.SESSION)
public class CalendarBean {

	private static final String [] WEEK_DAY_SHORT = new String[] { "<i>Sun</i>",
		"Mon +", "Tue +", "Wed +", "Thu +", "Fri +", "Sat*" };
	private static final String [] WEEK_DAY = new String[] { "*Saturday", "+Monday+",
	"+Tuesday+", "+Wednesday+", "+Thursday+", "+Friday+", "*Sunday*"};
	private static final String [] MOUNT_LABELS = new String[] { "<i>January</i>",
		"February+", "March+", "April+", "May +", "June +", "July +", "August +",
		"September +", "October +", "November +", "December +" };
	private static final String [] MOUNT_LABELS_SHORT = new String[] { "+Jan",
		"+Feb+", "+Mar+", "+Apr+", "+May+", "+Jun+", "+Jul+", "+Aug+",
		"+Sep+", "+Oct+", "+Nov+", "+Dec+" };
	private Locale locale;
	private TimeZone tZone;
	private boolean popup;
	private boolean readonly;
	private boolean showInput;
	private boolean enableManualInput;
	private boolean ajaxSingle;
	private boolean disabled;
	private boolean bypassUpdates;
	private boolean rendered;
	private boolean required;
	private Date currentDate;
	private Date selectedDate;
	private String headerFacet;
	private String mode;
	private String preloadDateRangeBegin;
	private String preloadDateRangeEnd;
	private String weekDay;
	private String month;
	private String pattern;
	private String jointPoint;
	private String direction;
	private String boundary;
	private String icon;
	private String toolTipMode;
	private String label;
	private String timeZone;
	private String horizontalOffset;
	private String verticalOffset;
	private int zindex;
	private int counter;
	private String cellHeight;
	private String cellWidth;
	private boolean immediate;
	private boolean isDayEnabled;
	private boolean showApplyButton;
	private boolean showScrollerBar;
	private boolean showWeekDaysBar;
	private boolean showWeeksBar;
	private String todayControlMode; // scroll, select, hidden;
	private UICalendar htmlCalendar = null;
	private int text1,text2,text3,text4;

	public CalendarBean() {
		horizontalOffset = "0";
		verticalOffset = "0";
		isDayEnabled = true;
		immediate = false;
		cellHeight = "15";
		cellWidth = "15";
		showApplyButton = false;
		showWeeksBar = false;
		showWeekDaysBar = false;
		showScrollerBar = false;
		todayControlMode = "select";
		mode = "client";
		label = "Button label";
		locale = new Locale("us","US","");
		popup = true;
		pattern = "MMM d, yyyy";
		jointPoint = "bottom-left";
		direction = "bottom-right";
		readonly = true;
		enableManualInput = false;
		showInput = true;
		boundary = "inactive";
		icon = null;
		disabled = false;
		bypassUpdates = false;
		rendered = true;
		zindex = 2;
		toolTipMode = "none";
		required = false;
		weekDay = "long";
		month = "none";
		timeZone = "Eastern European Time";
		preloadDateRangeBegin = "10.08.2007"; //d.m.y
		preloadDateRangeEnd = "11.10.2007";
	}
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlCalendar);
		return null;
	}
	
	public String getHorizontalOffset() {
		return horizontalOffset;
	}

	public void setHorizontalOffset(String horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public String getVerticalOffset() {
		return verticalOffset;
	}

	public void setVerticalOffset(String verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public boolean getImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean getIsDayEnabled() {
		return isDayEnabled;
	}

	public void setIsDayEnabled(boolean isDayEnabled) {
		this.isDayEnabled = isDayEnabled;
	}

	public String getTodayControlMode() {
		return todayControlMode;
	}

	public void setTodayControlMode(String todayControlMode) {
		this.todayControlMode = todayControlMode;
	}

	public String getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(String cellHeight) {
		this.cellHeight = cellHeight;
	}

	public String getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(String cellWidth) {
		this.cellWidth = cellWidth;
	}

	public boolean isShowApplyButton() {
		return showApplyButton;
	}

	public void setShowApplyButton(boolean showApplyButton) {
		this.showApplyButton = showApplyButton;
	}

	public boolean isShowScrollerBar() {
		return showScrollerBar;
	}

	public void setShowScrollerBar(boolean showScrollerBar) {
		this.showScrollerBar = showScrollerBar;
	}

	public boolean isShowWeekDaysBar() {
		return showWeekDaysBar;
	}

	public void setShowWeekDaysBar(boolean showWeekDaysBar) {
		this.showWeekDaysBar = showWeekDaysBar;
	}

	public boolean isShowWeeksBar() {
		return showWeeksBar;
	}

	public void setShowWeeksBar(boolean showWeeksBar) {
		this.showWeeksBar = showWeeksBar;
	}

	public boolean getRenderedAjax() {
		if(mode.equalsIgnoreCase("ajax") && rendered) return true; 
		else return false;
	}
	
	public boolean getRenderedClient() {
		if(mode.equalsIgnoreCase("client") && rendered) return true; 
		else return false;		
	}
	
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void changeIcons() {
		if (icon != null) {
			icon = null;
			label = "Button label";
		} else {
			icon = "/pics/item.png";
			label = null;
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {

		this.pattern = pattern;
	}
	
	public void selectLocale(ValueChangeEvent event) {
		String tLocale = (String) event.getNewValue();
		if (tLocale != null) {
			String lang = tLocale.substring(0, 2);
			String country = tLocale.substring(3);
			locale = new Locale(lang, country, "");
		}
	}
	
	public Date getPrDateRangeBegin() {
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(preloadDateRangeBegin,".");
		ArrayList<Integer> date = new ArrayList<Integer>();
		while(st.hasMoreTokens()) {
			date.add(Integer.parseInt(st.nextToken()));
		}
		cal.set(date.get(2), date.get(1) - 1, date.get(0), 12, 0, 0);
		return cal.getTime();
	}

	public Date getPrDateRangeEnd() {
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(preloadDateRangeEnd, ".");
		ArrayList<Integer> date = new ArrayList<Integer>();
		while(st.hasMoreTokens()) {
			date.add(Integer.parseInt(st.nextToken()));
		}
		cal.set(date.get(2), date.get(1) - 1, date.get(0), 12, 0, 0);
		return cal.getTime();
	}

	public boolean isShowInput() {
		return showInput;
	}

	public void setShowInput(boolean showInput) {
		this.showInput = showInput;
	}

	public boolean isEnableManualInput() {
		return enableManualInput;
	}

	public void setEnableManualInput(boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public Object getWeekDayLabels() {
		if(weekDay.equals("long"))
			return CalendarBean.WEEK_DAY;
	else return null;
	}

	public Object getWeekDayLabelsShort() {
		if(weekDay.equals("short"))
			return CalendarBean.WEEK_DAY_SHORT;
		else return null;
	}

	public Object getMonthLabels() {
		if(month.equals("long"))
			return CalendarBean.MOUNT_LABELS;
		else return null;
	}
	
	public Object getMonthLabelsShort() {
		if(month.equals("short"))
			return CalendarBean.MOUNT_LABELS_SHORT;
		else return null;
		
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCurrentDateAsText() {
		Date currentDate = getCurrentDate();
		if (currentDate != null) {
			return DateFormat.getDateInstance(DateFormat.FULL).format(
					currentDate);
		}

		return null;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getJointPoint() {
		return jointPoint;
	}

	public void setJointPoint(String jointPoint) {
		this.jointPoint = jointPoint;
	}


	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public void dcl(CurrentDateChangeEvent event) {
		System.out.println(event.getCurrentDateString());
	}

	public void ddd(ValueChangeEvent event) {
		System.out.println("=============inside valueChangeListener==============");
		System.out.println(event.getOldValue());
		System.out.println(event.getNewValue());

	}

	public int getCounter() {
		return counter++;
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isBypassUpdates() {
		return bypassUpdates;
	}

	public void setBypassUpdates(boolean bypassUpdates) {
		this.bypassUpdates = bypassUpdates;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getToolTipMode() {
		return toolTipMode;
	}

	public void setToolTipMode(String toolTipMode) {
		this.toolTipMode = toolTipMode;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public TimeZone getTmZone() {
		TimeZone tZone = TimeZone.getDefault();
		tZone.setID(timeZone);
		return tZone;
	}

	public String getPreloadDateRangeBegin() {
		return preloadDateRangeBegin;
	}

	public void setPreloadDateRangeBegin(String preloadDateRangeBegin) {
		this.preloadDateRangeBegin = preloadDateRangeBegin;
	}

	public String getPreloadDateRangeEnd() {
		return preloadDateRangeEnd;
	}

	public void setPreloadDateRangeEnd(String preloadDateRangeEnd) {
		this.preloadDateRangeEnd = preloadDateRangeEnd;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	public void bTest1() {	
		setCurrentDate(new Date());
//		setTimeZone();
		setSelectedDate(new Date());
		changeIcons();
		setHeaderFacet("Test 1");
		setLocale(new Locale("de","DE",""));
		setDirection("bottom-left");
		setEnableManualInput(false);
		setDisabled(false);
		setJointPoint("top-right");
		setMonth("long");
		setPattern("dd-MM-yyyy");
		setPopup(true);
		setPreloadDateRangeBegin("10.09.2007");
		setPreloadDateRangeEnd("11.01.2008");
		setReadonly(true);
		setRequired(true);
		setShowInput(false);
		setToolTipMode("single");
		setWeekDay("long");
		setZindex(3);
	}

	public void bTest2() {	
//		setCurrentDate(new Date());
//		setTimeZone();
//		setSelectedDate();
		changeIcons();
		setHeaderFacet("Test 2");
		setLocale(new Locale("fr","FR",""));
		setDirection("top-right");
		setEnableManualInput(true);
		setDisabled(false);
		setJointPoint("bottom-left");
		setMonth("none");
		setPattern("dd.MM.yyyy");
		setPopup(true);
		setPreloadDateRangeBegin("10.09.2007");
		setPreloadDateRangeEnd("12.01.2008");
		setReadonly(true);
		setRequired(true);
		setShowInput(false);
		setToolTipMode("single");
		setWeekDay("none");
		setZindex(3);
	}
	
	public void bTest3() {	
//		setCurrentDate(new Date());
//		setTimeZone();
//		setSelectedDate();
		changeIcons();
		setHeaderFacet("Test 3");
		setLocale(new Locale("ru","RU",""));
		setDirection("bottom-left");
		setEnableManualInput(false);
		setDisabled(false);
		setJointPoint("top-right");
		setMonth("long");
		setPattern("dd-MM-yyyy");
		setPopup(false);
		setPreloadDateRangeBegin("10.09.2007");
		setPreloadDateRangeEnd("11.01.2008");
		setReadonly(false);
		setRequired(false);
		setShowInput(false);
		setToolTipMode("single");
		setWeekDay("long");
		setZindex(3);
	}
	
	public void bTest4() {	
//		setCurrentDate(new Date());
//		setTimeZone();
//		setSelectedDate();
		changeIcons();
		setHeaderFacet("Test 4");
		setLocale(new Locale("de","DE",""));
		setDirection("bottom-left");
		setEnableManualInput(false);
		setDisabled(true);
		setJointPoint("top-right");
		setMonth("long");
		setPattern("dd-MM-yyyy");
		setPopup(false);
		setPreloadDateRangeBegin("10.09.2007");
		setPreloadDateRangeEnd("11.01.2008");
		setReadonly(false);
		setRequired(false);
		setShowInput(false);
		setToolTipMode("single");
		setWeekDay("none");
		setZindex(3);
	}
	
	public void bTest5() {	
//		setCurrentDate(new Date());
//		setTimeZone();
//		setSelectedDate();
		changeIcons();
		setHeaderFacet("Test 5");
		setLocale(new Locale("de","DE",""));
		setDirection("bottom-right");
		setEnableManualInput(false);
		setDisabled(false);
		setJointPoint("bottom-left");
		setMonth("none");
		setPattern("d/M/yy");
		setPopup(true);
		setPreloadDateRangeBegin("09.09.2007");
		setPreloadDateRangeEnd("10.01.2008");
		setReadonly(false);
		setRequired(true);
		setShowInput(true);
		setToolTipMode("none");
		setWeekDay("short");
		setZindex(1);
	}

	public String getHeaderFacet() {
		return headerFacet;
	}

	public void setHeaderFacet(String headerFacet) {
		this.headerFacet = headerFacet;
	}


	public UICalendar getHtmlCalendar() {
		return htmlCalendar;
	}


	public void setHtmlCalendar(UICalendar htmlCalendar) {
		this.htmlCalendar = htmlCalendar;
	}
	public int getText1() {
		return ++text1;
	}
	public void setText1(int text1) {
		this.text1 = text1;
	}
	public int getText2() {
		return ++text2;
	}
	public void setText2(int text2) {
		this.text2 = text2;
	}
	public int getText3() {
		return ++text3;
	}
	public void setText3(int text3) {
		this.text3 = text3;
	}
	public int getText4() {
		return ++text4;
	}
	public void setText4(int text4) {
		this.text4 = text4;
	}
}
