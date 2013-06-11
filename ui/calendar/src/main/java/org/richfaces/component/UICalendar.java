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

package org.richfaces.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.CurrentDateChangeEvent;
import org.richfaces.event.CurrentDateChangeListener;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;
import org.richfaces.renderkit.CalendarRendererBase;

/**
 * JSF component class
 * 
 */
public abstract class UICalendar extends UIInput implements AjaxComponent {

	/**
	 * firstWeekDay
	 * Gets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in France.
	 */
	private int  _firstWeekDay = 0;		
	/**
	 * Flag indicated what firstWeekDay is set.
	 */
	private boolean _firstWeekDaySet = false;	

	/**
	 * minDaysInFirstWeek
	 * Gets what the minimal days required in the first week of the year 
				are; e.g., if the first week is defined as one that contains the first 
				day of the first month of a year, this method returns 1. If the 
				minimal days required must be a full week, this method returns 7.
	 */
	private int  _minDaysInFirstWeek = 0;		
	/**
	 * Flag indicated what minDaysInFirstWeek is set.
	 */
	private boolean _minDaysInFirstWeekSet = false;	

	public static final String COMPONENT_TYPE = "org.richfaces.Calendar";

	public static final String COMPONENT_FAMILY = "org.richfaces.Calendar";
	
	/**
	 * Default value of "defaultTime" attribute
	 */
	private static String DEFAULT_TIME_VALUE = "hours:12,minutes:0";
	
	/**
	 * Default pattern for defaultTime
	 */
	private static String DEFAULT_TIME_PATTERN = "HH:mm";
	
	/**
	 * Constant "hours"
	 */
	private static String HOURS_VALUE = "hours";
	
	/**
	 * Constant "minutes"
	 */
	private static String MINUTES_VALUE = "minutes";

	public static final String AJAX_MODE = "ajax";

	public static final String CLIENT_MODE = "client";

	private final static Log log = LogFactory.getLog(UICalendar.class);
	
	public abstract Object getLocale();

	public abstract void setLocale(Object locale);

	public abstract TimeZone getTimeZone();

	public abstract void setTimeZone(TimeZone timeZone);

	public abstract Object getPreloadDateRangeBegin();

	public abstract void setPreloadDateRangeBegin(Object date);

	public abstract Object getPreloadDateRangeEnd();

	public abstract void setPreloadDateRangeEnd(Object date);

	public abstract Object getCurrentDate();

	public abstract void setCurrentDate(Object date);

	public abstract CalendarDataModel getDataModel();

	public abstract void setDataModel(CalendarDataModel dataModel);

	public abstract String getDatePattern();

	public abstract void setDatePattern(String pattern);
	
	public abstract Object getDefaultTime();

	public abstract void setDefaultTime(Object defaultTime);
	
	public abstract boolean isResetTimeOnDateSelect();

	public abstract void setResetTimeOnDateSelect(boolean resetTimeOnDateSelect);

	public abstract Object getMonthLabels();

	public abstract void setMonthLabels(Object labels);

	public abstract Object getMonthLabelsShort();

	public abstract void setMonthLabelsShort(Object labels);

	public abstract Object getWeekDayLabels();

	public abstract void setWeekDayLabels(Object labels);

	public abstract Object getWeekDayLabelsShort();

	public abstract void setWeekDayLabelsShort(Object labels);

	public abstract String getJointPoint();

	public abstract void setJointPoint(String jointPoint);

	public abstract String getDirection();

	public abstract void setDirection(String direction);

	public abstract boolean isPopup();

	public abstract void setPopup(boolean popup);

	public abstract boolean isDisabled();

	public abstract void setDisabled(boolean disabled);

	public abstract String getButtonLabel();

	public abstract void setButtonLabel(String buttonLabel);

	public abstract String getToolTipMode();

	public abstract void setToolTipMode(String toolTipMode);

	public abstract String getBoundaryDatesMode();

	public abstract void setBoundaryDatesMode(String boundaryDatesMode);

	public abstract MethodExpression getCurrentDateChangeListener();

	public abstract void setCurrentDateChangeListener(
			MethodExpression scrollerListener);

	public abstract String getMode();

	public abstract void setMode(String mode);

	public abstract int getVerticalOffset();

	public abstract void setVerticalOffset(int verticalOffset);

	public abstract int getHorizontalOffset();

	public abstract void setHorizontalOffset(int horizontalOffset);

	public abstract String getDayStyleClass();

	public abstract void setDayStyleClass(String DayStyleClass);

	public abstract String getIsDayEnabled();

	public abstract void setIsDayEnabled(String isDayEnabled);

	public abstract String getCellHeight();

	public abstract void setCellHeight(String cellHeight);

	public abstract String getCellWidth();

	public abstract void setCellWidth(String cellWidth);

	public abstract boolean isShowWeekDaysBar();

	public abstract void setShowWeekDaysBar(boolean showWeekDaysBar);

	public abstract boolean isShowWeeksBar();

	public abstract void setShowWeeksBar(boolean showWeeksBar);

	public abstract boolean isShowHeader();

	public abstract void setShowHeader(boolean showScrollerBar);

	public abstract boolean isShowFooter();

	public abstract void setShowFooter(boolean showScrollerBar);

	public abstract String getTodayControlMode();

	public abstract void setTodayControlMode(String todayControlMode);

	public abstract boolean isShowApplyButton();

	public abstract void setShowApplyButton(boolean showApplyButton);
	
	public abstract String getTabindex();

	public abstract void setTabindex(String tabindex);
	

	// TODO onclick add users onclick

	// currentDate processing -------------------------------------------------

	public Calendar getCalendar() {
		return Calendar.getInstance(getTimeZone(), getAsLocale(getLocale()));
	}

	public Date getConvertedValue(FacesContext context, String currentDateString)
			throws ConverterException {

		DateTimeConverter datetime = new DateTimeConverter();
		datetime.setPattern("m/y");
		Date newCurrentDate = (Date) datetime.getAsObject(context, this,
				currentDateString);
		return newCurrentDate;
	}

	/**
	 * Returns default value of "defaultTime" attribute
	 * @return default value of "defaultTime" attribute
	 */
	protected Object getDefaultValueOfDefaultTime() {
	    Calendar calendar = getCalendar();
	    calendar.set(Calendar.HOUR_OF_DAY, 12);
	    calendar.set(Calendar.MINUTE, 0);
	    
	    return calendar.getTime();
	}

	/**
	 * Returns default time as a <code>Date</code> value.
	 * Hours and minutes values should be taken from the returned date.
	 * 
	 * @return default time as a <code>Date</code> value
	 */
	public Date getFormattedDefaultTime() {
	    Date result = null;
	    Object defaultTime = getDefaultTime();
	    
	    if (defaultTime instanceof Calendar) {
		result = ((Calendar) defaultTime).getTime();
	    } else if (defaultTime instanceof Date) {
		result = (Date) defaultTime;
	    } else {
		String defaultTimeString = defaultTime.toString();
		String datePattern = getDatePattern();
		
		String timePattern = "\\s*[hHkKma]+[\\W&&\\S]+[hHkKma]+\\s*";
		Pattern pattern = Pattern.compile(timePattern);
		Matcher matcher = pattern.matcher(datePattern);
		
		String subTimePattern = DEFAULT_TIME_PATTERN;
		if(matcher.find()) {
		    subTimePattern = matcher.group().trim();
		}
		
		DateFormat format = new SimpleDateFormat(subTimePattern);

		try {
		    result = format.parse(defaultTimeString);
		} catch (ParseException parseException) {
		    // parse exception
		    result = null;
		}
	    }
	    return result;
	}
	
	/**
	 * Returns hours and minutes from "defaultTime" attribute as a String
	 * with special format: 
	 * hours:"value_hours",minutes:"value_minutes"
	 * 
	 * @return hours and minutes from "defaultTime" attribute
	 */
	public String getPreparedDefaultTime() {
	    Date date = getFormattedDefaultTime();
	    
	    if (date == null) {
		return DEFAULT_TIME_VALUE;
	    }
	    StringBuilder result = new StringBuilder();
	    
	    Calendar calendar = getCalendar();
	    calendar.setTime(date);
	    
	    result.append("{").append(HOURS_VALUE).append(":");
	    result.append(calendar.get(Calendar.HOUR_OF_DAY));
	    result.append(",");
	    result.append(MINUTES_VALUE).append(":");
	    result.append(calendar.get(Calendar.MINUTE)).append("}");
	    
	    return result.toString();
	    
	} 

	public void updateCurrentDate(FacesContext context, Object currentDate) {

		if (context == null) {
			throw new NullPointerException();
		}
		// RF-1073
		try {
			ValueExpression ve = getValueExpression("currentDate");
			if (ve != null) {
			    	ELContext elContext = context.getELContext(); 
			    	if (ve.getType(elContext).equals(String.class)) {
					DateTimeConverter convert = new DateTimeConverter();
					convert.setLocale(getAsLocale(getLocale()));
					convert.setPattern(getDatePattern());
					ve.setValue(context.getELContext(), convert.getAsString(context, this,
							currentDate));
					return;
				} else if (ve.getType(elContext).equals(Calendar.class)) {
					Calendar c = Calendar.getInstance();
					c.setTime((Date) currentDate);
					ve.setValue(elContext, c);
					return;
				} else {
					ve.setValue(elContext, currentDate);
					return;
				}
			} else {
				setCurrentDate(currentDate);
			}

		} catch (Exception e) {
			setValid(false);
			// XXX nick - kaa - add log.debug(...)
			if (log.isDebugEnabled()) {
				log.debug(" updateCurrentDate method throws exception: "
						+ e.toString(), e);
			}
			e.printStackTrace();
			String messageString = e.toString();
			FacesMessage message = new FacesMessage(messageString);
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(getClientId(context), message);
		}
	}

	public Date getCurrentDateOrDefault() {

		Date date = getAsDate(getCurrentDate());

		if (date != null) {
			return date;
		} else {
			Date value = getAsDate(this.getValue());
			if (value != null) {
				return value;
			} else {
				return java.util.Calendar.getInstance(getTimeZone()).getTime();
			}

		}
	}

	public Date getAsDate(Object date) {

		if (date == null) {
			return null;
		} else {
		    
		    	if (date instanceof Date) {
				return (Date) date;
			} else {
			    	if (date instanceof String) {
					DateTimeConverter converter = new DateTimeConverter();
					converter.setPattern(this.getDatePattern());
					converter.setLocale(getAsLocale(this.getLocale()));
					converter.setTimeZone(this.getTimeZone());
					FacesContext context = FacesContext.getCurrentInstance();
					return (Date) converter.getAsObject(context, this,
							(String) date);
				} else {
					if (date instanceof Calendar) {
						return ((Calendar) date).getTime();
					} else {
					    	
					    	FacesContext context = FacesContext.getCurrentInstance();
					    	Converter converter = getConverter();
					    	
					    	if(converter != null) {
					    	    return getAsDate(converter.getAsString(context, this, date));
					    	}
					    	
						Application application = context.getApplication();
						converter = application.createConverter(date.getClass());
						if (null != converter) {
							return getAsDate(converter.getAsString(context,	this, date));
						} else {
							throw new FacesException("Wrong attibute type or there is no converter for custom attibute type");
						}

					}
				}
			}
		}

	}

	public Object getTooltip(Date date) {

		CalendarDataModel calendarDM = (CalendarDataModel) getDataModel();
		if (calendarDM != null) {
			return calendarDM.getToolTip(date);
		} else {
			return null;
		}

	}

	protected Date getDefaultPreloadBegin(Date date) {
		Calendar calendar = Calendar.getInstance(getTimeZone(), getAsLocale(getLocale()));
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		/*
		 * //force recalculation calendar.getTimeInMillis();
		 * calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		 */
		return calendar.getTime();
	}

	protected Date getDefaultPreloadEnd(Date date) {
		Calendar calendar = Calendar.getInstance(getTimeZone(), getAsLocale(getLocale()));
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		/*
		 * //force recalculation calendar.getTimeInMillis();
		 * calendar.set(Calendar.DAY_OF_WEEK, getLastDayOfWeek(calendar));
		 */
		return calendar.getTime();
	}

	protected Locale getDefaultLocale() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			UIViewRoot viewRoot = facesContext.getViewRoot();
			if (viewRoot != null) {
				Locale locale = viewRoot.getLocale();
				if (locale != null) {
					return locale;
				}
			}
		}

		return Locale.US;
	}

	protected TimeZone getDefaultTimeZone() {
		return TimeZone.getDefault();
	}

	public Date convertCurrentDate(String currentDateString) {

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.DATE, 1);
		int idx = currentDateString.indexOf('/');
		if (idx != -1) {
			calendar.set(Calendar.MONTH, Integer.parseInt(currentDateString
					.substring(0, idx)) - 1);
			calendar.set(Calendar.YEAR, Integer.parseInt(currentDateString
					.substring(idx + 1)));

			return calendar.getTime();
		} else {
			return null;
		}

	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		// TODO Auto-generated method stub
		if (event instanceof AjaxEvent) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			AjaxContext ajaxContext = AjaxContext
					.getCurrentInstance(facesContext);
			ajaxContext.addRegionsFromComponent(this);
			if (getPreload() != null) {
				ajaxContext.setResponseData(getPreload());
			}
		} else {
			if (event instanceof CurrentDateChangeEvent) {
				FacesContext facesContext = getFacesContext();
				CurrentDateChangeEvent dateChangeEvent = (CurrentDateChangeEvent) event;
				String currentDateString = dateChangeEvent
						.getCurrentDateString();

				// if currentDateString is not null then event cames from
				// apply request phase
				try {
					// XXX nick - kaa - we should use datePattern
					// attribute-based converter only for selectedDate
					// current date string always has predefined format: m/y
					// review
					// org.richfaces.renderkit.CalendarRendererBase.convertCurrentDate(String)
					// method
					// for more

					// XX nick - kaa - throw exception and review resulting
					// message :)

					Date currentDate = getAsDate(getCurrentDate());
					Date submittedCurrentDate = convertCurrentDate(currentDateString);
					dateChangeEvent.setCurrentDate(submittedCurrentDate);

					if (!submittedCurrentDate.equals(currentDate)) {
						updateCurrentDate(facesContext, submittedCurrentDate);
						MethodExpression methodExpression = getCurrentDateChangeListener();
						if (methodExpression != null) {
						    methodExpression.invoke(facesContext.getELContext(), new Object[] { dateChangeEvent });
						}
					}
					
				} catch (Exception e) {
					// XXX nick - kaa - add log.debug(...)
					// XXX nick - kaa - we should stop processing on exc.
					// setValid(false) and then call
					// FacesContext.renderResponse(...)
					// update model phase shouldn't start
					if (log.isDebugEnabled()) {
						log.debug(
								" currentDate convertion fails with following exception: "
										+ e.toString(), e);
					}
					setValid(false);
					String messageString = e.toString();
					e.printStackTrace();
					FacesMessage message = new FacesMessage(messageString);
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					facesContext.addMessage(getClientId(facesContext),
							message);
					facesContext.renderResponse();
				}
			} else {
				super.broadcast(event);
			}

		}

	}

	public Object getPreload() {
		Date[] preloadDateRange = getPreloadDateRange();
		if (preloadDateRange != null && preloadDateRange.length != 0) {
			CalendarDataModel calendarDataModel = (CalendarDataModel) getDataModel();
			if (calendarDataModel != null) {
				CalendarDataModelItem[] calendarDataModelItems = calendarDataModel
						.getData(preloadDateRange);

				HashMap <String, Object> args = new HashMap<String,Object>();
				args.put("startDate", CalendarRendererBase.formatDate(preloadDateRange[0]));
				args.put("days", calendarDataModelItems);
				return args;
			}
		}

		return null;
	}

	public Date[] getPreloadDateRange() {
		Date dateRangeBegin = getAsDate(this.getPreloadDateRangeBegin());
		Date dateRangeEnd = getAsDate(this.getPreloadDateRangeEnd());

		if (dateRangeBegin == null && dateRangeEnd == null) {
			return null;
		} else {
			if (dateRangeBegin.after(dateRangeEnd)) {
				// XXX add message
				FacesMessage message = new FacesMessage(
						"preloadDateRangeBegin is greater than preloadDateRangeEnd");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(getClientId(context), message);
				throw new IllegalArgumentException();
			}

			List <Date> dates = new ArrayList<Date>();

			Calendar calendar = Calendar.getInstance(this.getTimeZone(), getAsLocale(this.getLocale()));
			Calendar calendar2 = (Calendar) calendar.clone();
			calendar.setTime(dateRangeBegin);
			calendar2.setTime(dateRangeEnd);

			do {
				dates.add(calendar.getTime());
				calendar.add(Calendar.DATE, 1);
			} while (!calendar.after(calendar2));

			return (Date[]) dates.toArray(new Date[dates.size()]);
		}
	}

	public void addCurrentDateChangeListener(CurrentDateChangeListener listener) {
		addFacesListener(listener);
	}

	public CurrentDateChangeListener[] getCurrentDateChangeListeners() {
		return (CurrentDateChangeListener[]) getFacesListeners(CurrentDateChangeListener.class);
	}

	public void removeCurrentDateChangeListener(
			CurrentDateChangeListener listener) {
		removeFacesListener(listener);
	}
	
	 /**
	  *Parse Locale from String.
	  *String must be represented as Locale.toString(); xx_XX_XXXX
     */
	
	public Locale parseLocale(String localeStr){
		
		int length = localeStr.length();
		if(null==localeStr||length<2){
			return Locale.getDefault();
		}
		
		//Lookup index of first '_' in string locale representation.
		int index1 = localeStr.indexOf("_");
		//Get first charters (if exist) from string
		String language = null; 
		if(index1!=-1){
			language = localeStr.substring(0, index1);
		}else{
			return new Locale(localeStr);
		}
		//Lookup index of second '_' in string locale representation.
		int index2 = localeStr.indexOf("_", index1+1);
		String country = null;
		if(index2!=-1){
			country = localeStr.substring(index1+1, index2);
			String variant = localeStr.substring(index2+1);
			return new Locale(language, country, variant);
		}else{
			country = localeStr.substring(index1+1);
			return new Locale(language, country);
		}		
	}
	
	public Locale getAsLocale(Object locale) {

		if (locale instanceof Locale) {

			return (Locale) locale;

		} else if (locale instanceof String) {

			return parseLocale((String) locale);

		} else {

			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			Converter converter = application
					.createConverter(locale.getClass());
			if (null != converter) {
				return parseLocale(converter.getAsString(context, this, locale));
			} else {
				throw new FacesException(
						"Wrong locale attibute type or there is no converter for custom attibute type");
			}
		}
	}

	protected int getDefaultFirstWeekDay() {
		Calendar cal = getCalendar();
		return cal.getFirstDayOfWeek() - cal.getActualMinimum(Calendar.DAY_OF_WEEK);
	}
	
	protected int getDefaultMinDaysInFirstWeek() {
		return getCalendar().getMinimalDaysInFirstWeek();
	}
	/**
	 * Gets what the minimal days required in the first week of the year 
			are; e.g., if the first week is defined as one that contains the first 
			day of the first month of a year, this method returns 1. If the 
			minimal days required must be a full week, this method returns 7.
	 * Setter for minDaysInFirstWeek
	 * @param minDaysInFirstWeek - new value
	 */
	public void setMinDaysInFirstWeek( int  __minDaysInFirstWeek ){
		this._minDaysInFirstWeek = __minDaysInFirstWeek;
		this._minDaysInFirstWeekSet = true;
	}


	/**
	 * Gets what the minimal days required in the first week of the year 
			are; e.g., if the first week is defined as one that contains the first 
			day of the first month of a year, this method returns 1. If the 
			minimal days required must be a full week, this method returns 7.
	 * Getter for minDaysInFirstWeek
	 * @return minDaysInFirstWeek value from local variable or value bindings
	 */
	public int getMinDaysInFirstWeek(  ){
		if(this._minDaysInFirstWeekSet){
			return this._minDaysInFirstWeek;
		}
		ValueExpression ve = getValueExpression("minDaysInFirstWeek");
		if (ve != null) {
		    	Integer value = (Integer) ve.getValue(getFacesContext().getELContext());
			if (null == value) {
				return getDefaultMinDaysInFirstWeek();
			}
			return (value.intValue());
		} else {
			return getDefaultMinDaysInFirstWeek();
		}
	}
	/**
	 * Gets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in France.
	 * Setter for firstWeekDay
	 * @param firstWeekDay - new value
	 */
	public void setFirstWeekDay( int  __firstWeekDay ){
		this._firstWeekDay = __firstWeekDay;
		this._firstWeekDaySet = true;
	}


	/**
	 * Gets what the first day of the week is; e.g., SUNDAY in the U.S., MONDAY in France.
	 * Getter for firstWeekDay
	 * @return firstWeekDay value from local variable or value bindings
	 */
	public int getFirstWeekDay(  ){
		int result;
		if (this._firstWeekDaySet) {
			result = this._firstWeekDay;
		}else{
			ValueExpression ve = getValueExpression("firstWeekDay");
			if (ve != null) {
			    			    
				Integer value = (Integer) ve.getValue(getFacesContext().getELContext());
				result = (value.intValue());
			} else {
				result = getDefaultFirstWeekDay();
			}
		}
		if (result < 0 || result > 6) {
			getFacesContext().getExternalContext()
				.log(result + " value of firstWeekDay attribute is not a legal one for component: "
								+ MessageUtil.getLabel(getFacesContext(), this)
								+ ". Default value was applied.");
			result = getDefaultFirstWeekDay();
		}
		return result;
	}
	
	public Object saveState(FacesContext context) {
		return new Object [] {
			super.saveState(context),
			
			new Integer(_firstWeekDay),
			new Boolean(_firstWeekDaySet),

			new Integer(_minDaysInFirstWeek),
			new Boolean(_minDaysInFirstWeekSet)
		};
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] states = (Object[]) state;
		super.restoreState(context, states[0]);
		
		_firstWeekDay = ((Integer)states[1]).intValue();
		_firstWeekDaySet = ((Boolean)states[2]).booleanValue();	

		_minDaysInFirstWeek = ((Integer)states[3]).intValue();
		_minDaysInFirstWeekSet = ((Boolean)states[4]).booleanValue();	
	}
}
