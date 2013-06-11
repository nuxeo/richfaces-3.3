/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.ajax4jsf.bean;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.convert.DateTimeConverter;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UICalendar;
import org.richfaces.event.CurrentDateChangeEvent;

public class CalendarTestBean {

    public static final String valueChangeListener = "valueChangeListener";

    public static final String currentDateChangeListener = "currentDateChangeListener";

    public static final String REQUIRED_MESSAGE = "Date cannot be empty";

    public static final String DATE_PATTERN = "MM/dd/yyyy HH:mm";

    public static final Locale LOCALE = Locale.US;

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Minsk");

    public static Date DEFAULT_DATE;

    private static DateTimeConverter DEFAULT_CONVERTER;

    public static DateFormat DATE_FORMAT;

    // constructor for static fields
    static {
        setupDefaultConverter();
        setupDefaultDate();
    }

    private String datePattern;

    private Locale locale;

    private TimeZone timeZone;

    private Date selectedDate;

    private String selectedDateString;

    private CalendarDataModel model;

    private String mode = UICalendar.AJAX_MODE;

    private String status;
    
    private Date preloadDateRangeStart;
    
    private Date preloadDateRangeEnd;
    
    private boolean NULLModel = false;
    

    public CalendarTestBean() {
        selectedDate = DEFAULT_DATE;
        defaultTime = new Date();
        resetSelectedDateString();

        datePattern = DATE_PATTERN;
        locale = LOCALE;
        timeZone = TIME_ZONE;
        Calendar c = getLocale()!=null ? Calendar.getInstance(getLocale()) : Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, 1); 
        preloadDateRangeStart = c.getTime(); // Set preloadBegin by the first day of the last month
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 3, 0);
        preloadDateRangeEnd = c.getTime(); // Set preloadEnd by the last day of the next month
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
    	this.selectedDate = selectedDate;
        resetSelectedDateString();
    }

    public void resetSelectedDateString() {
        if (getSelectedDate() != null) {
            setSelectedDateString(DATE_FORMAT.format(getSelectedDate()));
        } else {
            setSelectedDateString("");
        }
    }

    public String getSelectedDateString() {
        return selectedDateString;
    }

    public void setSelectedDateString(String selectedDateString) {
        this.selectedDateString = selectedDateString;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public Locale getLocale() {
        return locale;
    }

    public TimeZone getTimeZone() {
    	return timeZone;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    public static Calendar getCalendar() {
        return Calendar.getInstance(CalendarTestBean.TIME_ZONE, CalendarTestBean.LOCALE);
    }

    /**
     * Setup the default date
     */
    private static void setupDefaultDate() {
        DEFAULT_DATE = getDayInMay(10);
    }

    public static Date getDayInMay(int dayOfMonth) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.YEAR, 2008);
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Setup the default converter
     */
    private static void setupDefaultConverter() {
        DEFAULT_CONVERTER = new DateTimeConverter();

        DEFAULT_CONVERTER.setPattern(DATE_PATTERN);
        DEFAULT_CONVERTER.setLocale(LOCALE);
        DEFAULT_CONVERTER.setTimeZone(TIME_ZONE);

        try {
            Method method = null;
            Method[] declaredMethods = DateTimeConverter.class.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].getName().equals("getDateFormat")) {
                    method = declaredMethods[i];
                    break;
                }
            }
            if (method != null) {

                method.setAccessible(true);
                DATE_FORMAT = (DateFormat) method.invoke(DEFAULT_CONVERTER, DEFAULT_CONVERTER.getLocale());
                DATE_FORMAT.setTimeZone(DEFAULT_CONVERTER.getTimeZone());
            }
        } catch (Exception e) {
            // skip exception
        }
    }

    public void valueChangeListener(ValueChangeEvent event) {
        status = getStatus() + valueChangeListener;
    }

    public void currectDateChangeListener(CurrentDateChangeEvent event) {
        status = getStatus() + currentDateChangeListener;
        
    }

    public String testClientMode() {
        mode = UICalendar.CLIENT_MODE;
        return null;
    }
    
    public String testLocale () {
    	locale = new Locale("ru","RU","");
    	return null;
    }
    
    public String testNullModel () {
    	NULLModel = true;
    	return null;
    }


    public void reset() {
    	mode = UICalendar.AJAX_MODE;
    	status = "";
    	selectedDate = new Date();
    	isPopup = false;
    	currentDate = new Date();
    	locale = Locale.US;
    	required = false;
    	enableManualInput = false;
    	NULLModel = false;
    	firstWeekDay = 0;
    	disabled = false;
    	datePattern = DATE_PATTERN;
    }
    
    public void reset2() {
        selectedDate = DEFAULT_DATE;
        currentDate = getDayInMay(5);
        defaultTime = new Date();
        resetSelectedDateString();

        datePattern = DATE_PATTERN;
        locale = LOCALE;
        timeZone = TIME_ZONE;
        Calendar c = getLocale()!=null ? Calendar.getInstance(getLocale()) : Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, 1); 
        preloadDateRangeStart = c.getTime(); // Set preloadBegin by the first day of the last month
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 3, 0);
        preloadDateRangeEnd = c.getTime(); // Set preloadEnd by the last day of the next month
        isPopup = true;
    }
    
    public void reset3() {
    	mode = UICalendar.CLIENT_MODE;
    	status = "";
    	selectedDate = new Date();
    	isPopup = false;
    	currentDate = new Date();
    	locale = Locale.US;
    	required = false;
    	enableManualInput = false;
    	NULLModel = true;
    	firstWeekDay = 0;
    	disabled = false;
    	datePattern = DATE_PATTERN;
    }
        
    public String resetAction() {
        reset();
        return null;
    }
    
    public String resetAction2() {
        reset2();
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public CalendarDataModel getModel() {
    	if (!NULLModel) {
    		return model;
    	}
    	return null;
    }

    public void setModel(CalendarDataModel model) {
        this.model = model;
    }

    private Date currentDate = null;

    /**
     * Gets value of currentDate field.
     * 
     * @return value of currentDate field
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * Set a new value for currentDate field.
     * 
     * @param currentDate
     *                a new value for currentDate field
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    private boolean isPopup;

    /**
     * Gets value of isPopup field.
     * @return value of isPopup field
     */
    public boolean isPopup() {
        return isPopup;
    }

    /**
     * Set a new value for isPopup field.
     * @param isPopup a new value for isPopup field
     */
    public void setPopup(boolean isPopup) {
        this.isPopup = isPopup;
    }

    /**
     * @return the preloadDateRangeStart
     */
    public Date getPreloadDateRangeStart() {
        return preloadDateRangeStart;
    }

    /**
     * @param preloadDateRangeStart
     *                the preloadDateRangeStart to set
     */
    public void setPreloadDateRangeStart(Date preloadDateRangeStart) {
        this.preloadDateRangeStart = preloadDateRangeStart;
    }

    /**
     * @return the preloadDateRangeEnd
     */
    public Date getPreloadDateRangeEnd() {
        return preloadDateRangeEnd;
    }

    /**
     * @param preloadDateRangeEnd
     *                the preloadDateRangeEnd to set
     */
    public void setPreloadDateRangeEnd(Date preloadDateRangeEnd) {
        this.preloadDateRangeEnd = preloadDateRangeEnd;
    }

    private boolean showApplyButton;

    private boolean showHeader;

    private boolean showFooter;

    private boolean showInput;

    private boolean showWeekDaysBar;

    private boolean showWeeksBar;

    /**
     * Gets value of showApplyButton field.
     * @return value of showApplyButton field
     */
    public boolean isShowApplyButton() {
        return showApplyButton;
    }

    /**
     * Set a new value for showApplyButton field.
     * @param showApplyButton a new value for showApplyButton field
     */
    public void setShowApplyButton(boolean showApplyButton) {
        this.showApplyButton = showApplyButton;
    }

    /**
     * Gets value of showHeader field.
     * @return value of showHeader field
     */
    public boolean isShowHeader() {
        return showHeader;
    }

    /**
     * Set a new value for showHeader field.
     * @param showHeader a new value for showHeader field
     */
    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    /**
     * Gets value of showFooter field.
     * @return value of showFooter field
     */
    public boolean isShowFooter() {
        return showFooter;
    }

    /**
     * Set a new value for showFooter field.
     * @param showFooter a new value for showFooter field
     */
    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    /**
     * Gets value of showInput field.
     * @return value of showInput field
     */
    public boolean isShowInput() {
        return showInput;
    }

    /**
     * Set a new value for showInput field.
     * @param showInput a new value for showInput field
     */
    public void setShowInput(boolean showInput) {
        this.showInput = showInput;
    }

    /**
     * Gets value of showWeekDaysBar field.
     * @return value of showWeekDaysBar field
     */
    public boolean isShowWeekDaysBar() {
        return showWeekDaysBar;
    }

    /**
     * Set a new value for showWeekDaysBar field.
     * @param showWeekDaysBar a new value for showWeekDaysBar field
     */
    public void setShowWeekDaysBar(boolean showWeekDaysBar) {
        this.showWeekDaysBar = showWeekDaysBar;
    }

    /**
     * Gets value of showWeeksBar field.
     * @return value of showWeeksBar field
     */
    public boolean isShowWeeksBar() {
        return showWeeksBar;
    }

    /**
     * Set a new value for showWeeksBar field.
     * @param showWeeksBar a new value for showWeeksBar field
     */
    public void setShowWeeksBar(boolean showWeeksBar) {
        this.showWeeksBar = showWeeksBar;
    }

    public void initShowAttributesTest() {
        showApplyButton = true;
        showHeader = true;
        showFooter = true;
        showInput = true;
        showWeekDaysBar = true;
        showWeeksBar = true;
        firstWeekDay = 0;
        disabled = false;
        enableManualInput = false;
    }

    private String todayControlMode = "select";

    /**
     * Gets value of todayControlMode field.
     * @return value of todayControlMode field
     */
    public String getTodayControlMode() {
        return todayControlMode;
    }

    /**
     * Set a new value for todayControlMode field.
     * @param todayControlMode a new value for todayControlMode field
     */
    public void setTodayControlMode(String todayControlMode) {
        this.todayControlMode = todayControlMode;
    }

    private boolean required;

    /**
     * Gets value of required field.
     * @return value of required field
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Set a new value for required field.
     * @param required a new value for required field
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    private String requiredMessage = REQUIRED_MESSAGE;

    /**
     * Gets value of requiredMessage field.
     * @return value of requiredMessage field
     */
    public String getRequiredMessage() {
        return requiredMessage;
    }

    /**
     * Set a new value for requiredMessage field.
     * @param requiredMessage a new value for requiredMessage field
     */
    public void setRequiredMessage(String requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

    public void initRequiredTest() {
    	reset();
        required = true;
        selectedDate = null;
        isPopup = false;
    }

    private boolean enableManualInput;

    /**
     * Gets value of enableManualInput field.
     * @return value of enableManualInput field
     */
    public boolean isEnableManualInput() {
        return enableManualInput;
    }

    /**
     * Set a new value for enableManualInput field.
     * @param enableManualInput a new value for enableManualInput field
     */
    public void setEnableManualInput(boolean enableManualInput) {
        this.enableManualInput = enableManualInput;
    }

    private int firstWeekDay = 0;

    /**
     * Gets value of firstWeekDay field.
     * @return value of firstWeekDay field
     */
    public int getFirstWeekDay() {
        return firstWeekDay;
    }

    /**
     * Set a new value for firstWeekDay field.
     * @param firstWeekDay a new value for firstWeekDay field
     */
    public void setFirstWeekDay(int firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
    }

    private boolean disabled;

    /**
     * Gets value of disabled field.
     * @return value of disabled field
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Set a new value for disabled field.
     * @param disabled a new value for disabled field
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    private Date defaultTime;

    /**
     * Gets value of defaultTime field.
     * @return value of defaultTime field
     */
    public Date getDefaultTime() {
        return defaultTime;
    }

    /**
     * Set a new value for defaultTime field.
     * @param defaultTime a new value for defaultTime field
     */
    public void setDefaultTime(Date defaultTime) {
        this.defaultTime = defaultTime;
    }

    private boolean resetTimeOnDateSelect;

    /**
     * Gets value of resetTimeOnDateSelect field.
     * @return value of resetTimeOnDateSelect field
     */
    public boolean isResetTimeOnDateSelect() {
        return resetTimeOnDateSelect;
    }

    /**
     * Set a new value for resetTimeOnDateSelect field.
     * @param resetTimeOnDateSelect a new value for resetTimeOnDateSelect field
     */
    public void setResetTimeOnDateSelect(boolean resetTimeOnDateSelect) {
        this.resetTimeOnDateSelect = resetTimeOnDateSelect;
    }

    private String jointPoint = "bottom-left";

    /**
     * Gets value of jointPoint field.
     * @return value of jointPoint field
     */
    public String getJointPoint() {
        return jointPoint;
    }

    /**
     * Set a new value for jointPoint field.
     * @param jointPoint a new value for jointPoint field
     */
    public void setJointPoint(String jointPoint) {
        this.jointPoint = jointPoint;
    }

    private String direction = "bottom-right";

    /**
     * Gets value of direction field.
     * @return value of direction field
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Set a new value for direction field.
     * @param direction a new value for direction field
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    private String boundaryDatesMode = "inactive";

    /**
     * Gets value of boundaryDatesMode field.
     * @return value of boundaryDatesMode field
     */
    public String getBoundaryDatesMode() {
        return boundaryDatesMode;
    }

    /**
     * Set a new value for boundaryDatesMode field.
     * @param boundaryDatesMode a new value for boundaryDatesMode field
     */
    public void setBoundaryDatesMode(String boundaryDatesMode) {
        this.boundaryDatesMode = boundaryDatesMode;
    }

}
