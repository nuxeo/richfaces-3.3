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

package org.richfaces;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.event.ValueChangeEvent;

import org.richfaces.event.CurrentDateChangeEvent;

/**
 * 20/07/2007
 * 
 * @author Alexej Kushunin
 * @mailto: akushunin@exadel.com
 * 
 */
public class CalendarBean {

	private static final String[] WEEK_DAY_LABELS = new String[] { "Sun *",
			"Mon +", "Tue +", "Wed +", "Thu +", "Fri +", "Sat *" };
	private Locale locale;

	private boolean popup;
	private boolean readonly;
	private boolean showInput;
	private boolean enableManualInput;
	private boolean disabled;
	private boolean showApplyButton;
	private String pattern;
	private Date currentDate;
	private Date selectedDate;
	private String jointPoint;
	private String direction;
	private String boundary;
	private String todayControlMode;
	private boolean showHeader;
	private boolean showFooter;
	private boolean resetTimeOnDateSelect;

	private int counter;
	
	
	
	
	private boolean useCustomDayLabels;

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

	public void selectPattern(ValueChangeEvent event) {
		String tPatern = (String) event.getNewValue();
		if (tPatern != null) {
			pattern = tPatern;
		}
	}

	public CalendarBean() {

		locale = Locale.US;
		popup = true;
		pattern = "MMM d, yyyy";
		jointPoint = "bottom-left";
		direction = "bottom-right";
		readonly = true;
		enableManualInput=false;
		showInput=true;
		boundary = "inactive";
		disabled = false;
		todayControlMode = "select";
		resetTimeOnDateSelect = false;
		
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

	public void selectLocale(ValueChangeEvent event) {

		String tLocale = (String) event.getNewValue();
		if (tLocale != null) {
			String lang = tLocale.substring(0, 2);
			String country = tLocale.substring(3);
			locale = new Locale(lang, country, "");
		}
	}

	public boolean isUseCustomDayLabels() {
		return useCustomDayLabels;
	}

	public void setUseCustomDayLabels(boolean useCustomDayLabels) {
		this.useCustomDayLabels = useCustomDayLabels;
	}

	public Object getWeekDayLabelsShort() {
		if (isUseCustomDayLabels()) {
			return WEEK_DAY_LABELS;
		} else {
			return null;
		}
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

	public void selectJointPoint(ValueChangeEvent event) {
		jointPoint = (String) event.getNewValue();
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void selectDirection(ValueChangeEvent event) {
		direction = (String) event.getNewValue();
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}
	public void dcl(CurrentDateChangeEvent event){
		System.out.println(event.getCurrentDateString());
		System.out.println("ajvhckndskncs");
	}
	public void ddd(ValueChangeEvent event){
		System.out.println(event.getOldValue());
		System.out.println(event.getNewValue());
		
	}

	public int getCounter() {
		return counter++;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isShowApplyButton() {
		return showApplyButton;
	}

	public void setShowApplyButton(boolean showApplyButton) {
		this.showApplyButton = showApplyButton;
	}

	public String getTodayControlMode() {
		return todayControlMode;
	}

	public void setTodayControlMode(String todayControlMode) {
		this.todayControlMode = todayControlMode;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean isShowFooter() {
		return showFooter;
	}

	public void setShowFooter(boolean showFooter) {
		this.showFooter = showFooter;
	}

	public boolean isResetTimeOnDateSelect() {
		return resetTimeOnDateSelect;
	}

	public void setResetTimeOnDateSelect(boolean resetTimeOnDateSelect) {
		this.resetTimeOnDateSelect = resetTimeOnDateSelect;
	}
}