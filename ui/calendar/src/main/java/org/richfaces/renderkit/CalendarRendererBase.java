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

package org.richfaces.renderkit;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.event.PhaseId;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.UICalendar;
import org.richfaces.component.util.ComponentUtil;
import org.richfaces.event.CurrentDateChangeEvent;

/**
 * @author Nick Belaevski - mailto:nbelaevski@exadel.com created 08.06.2007
 * 
 */
public class CalendarRendererBase extends TemplateEncoderRendererBase {

	protected static final String MONTH_LABELS_SHORT = "monthLabelsShort";

	protected static final String MONTH_LABELS = "monthLabels";

	protected static final String WEEK_DAY_LABELS_SHORT = "weekDayLabelsShort";

	protected static final String WEEK_DAY_LABELS = "weekDayLabels";

	/**
	 * The constant used to resolve id of hidden input placed on the page
	 * for storing current date in "MM/yyyy" format.
	 * Actual id of hidden input used on the page is #{clientId}InputCurrentDate
	 */
	public static final String CURRENT_DATE_INPUT = "InputCurrentDate";

	public static final String CURRENT_DATE_PRELOAD = "PreloadCurrentDate";

	protected static final String MARKUP_SUFFIX = "Markup";

	public static final String CALENDAR_BUNDLE = "org.richfaces.renderkit.calendar";

	private final static Log log = LogFactory
			.getLog(CalendarRendererBase.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.framework.renderer.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return UICalendar.class;
	}

	public void addPopupToAjaxRendered(FacesContext context,
			UICalendar component) {

		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		Set<String> ajaxRenderedAreas = ajaxContext.getAjaxRenderedAreas();
		String clientId = component.getClientId(context);

		if (ajaxContext.isAjaxRequest() && ajaxRenderedAreas.contains(clientId)) {
			ajaxRenderedAreas.add(clientId + "Popup");

			ajaxRenderedAreas.add(clientId + "IFrame");

			ajaxRenderedAreas.add(clientId + "Script");
		}
	}

	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
	throws ConverterException {
	    
	    if ((context == null) || (component == null)) {
	            throw new NullPointerException();
	    }
	    
	    // skip conversion of already converted date
	    if (submittedValue instanceof Date) {
		return (Date) submittedValue;
	    }

	    // Store submitted value in the local variable as a string
	    String newValue = (String) submittedValue;
	    // if we have no local value, try to get the valueExpression.
	    ValueExpression valueExpression = component.getValueExpression("value");
	    Converter converter = null;
	    
	    UICalendar calendar = (UICalendar) component;
	    converter = calendar.getConverter();
	    
	    if ((converter == null) && (valueExpression != null)) {
		Class<? extends Object> converterType = valueExpression.getType(context.getELContext());
		if((converterType != null) && (converterType != Object.class)) {
		    // if getType returns a type for which we support a default
	            // conversion, acquire an appropriate converter instance.
		    converter = getConverterForClass(converterType, context);
		}
	    }

	    // in case the converter hasn't been set, try to use default DateTimeConverter
	    if (converter == null) {		
		converter = createDefaultConverter();
	    }
	    setupDefaultConverter(converter, calendar);
	    
	    return converter.getAsObject(context, component, newValue);
	}
	
    private Converter getConverterForClass(Class <? extends Object> converterClass, FacesContext context) {
        if (converterClass == null) {
            return null;
        }

        try {            
            Application application = context.getApplication();
            return (application.createConverter(converterClass));
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage(), e);
			return null;
        }
    }
    
    /**
     * Returns hours and minutes from "defaultTime" attribute as a String with
     * special format: hours:"value_hours",minutes:"value_minutes"
     * 
     * @param calendar - UICalendar
     * 
     * @return hours and minutes from "defaultTime" attribute
     */
    public String getPreparedDefaultTime(UICalendar calendar) {
    	return calendar.getPreparedDefaultTime();
    } 

	/**
	 * Overloads getFormattedValue to take a advantage of a previously
	 * obtained converter.
	 * @param context the FacesContext for the current request
	 * @param component UIComponent of interest
	 * @param currentValue the current value of <code>component</code>
	 * @param converter the component's converter
	 * @return the currentValue after any associated Converter has been
	 *  applied
	 *
	 * @throws ConverterException if the value cannot be converted
	 */
	protected String getFormattedValue(FacesContext context, UIComponent component, Object currentValue,
		Converter converter) throws ConverterException {

	    // formatting is supported only for components that support
	    // converting value attributes.
	    if (!(component instanceof UICalendar)) {
		if (currentValue != null) {
		    return currentValue.toString();
		}
		return null;
	    }
	    UICalendar calendar = (UICalendar) component;
	    
	    // if value is null and no converter attribute is specified, then
	    // return a zero length String.
	    if(currentValue == null) {
		return "";
	    }
	    
	    if (converter == null) {
		// If there is a converter attribute, use it to to ask application
		// instance for a converter with this identifier.
		converter = calendar.getConverter();
	    }
	    
	    if (converter == null) {
		// Do not look for "by-type" converters for Strings
		if (currentValue instanceof String) {
		    return (String) currentValue;
		}

		// if converter attribute set, try to acquire a converter
		// using its class type.
		Class<? extends Object> converterType = currentValue.getClass();
		converter = getConverterForClass(converterType, context);

		// if there is no default converter available for this identifier,
		// assume the model type to be String.
		if (converter == null) {
		    // in case the converter hasn't been set, try to use default DateTimeConverter
		    converter = createDefaultConverter();
		}
	    }
	    setupDefaultConverter(converter, calendar);
	    
	    return converter.getAsString(context, calendar, currentValue);
	}


	/**
	 * @param context the FacesContext for the current request
	 * @param component UIComponent of interest
	 * @param currentValue the current value of <code>component</code>
	 *
	 * @return the currentValue after any associated Converter has been
	 *  applied
	 *
	 * @throws ConverterException if the value cannot be converted
	 */
	protected String getFormattedValue(FacesContext context, UIComponent component, Object currentValue)
				throws ConverterException {
	    return getFormattedValue(context, component, currentValue, null);
	}
	
	/**
	 * Creates default <code>DateTimeConverter</code> for the calendar
	 * @param calendar - calendar component
	 * 
	 * @return created converter
	 */
	protected static Converter createDefaultConverter() {
	    return new DateTimeConverter();
	}

	/**
	 * Setup the default converter provided by JSF API
	 * (<code>DateTimeConverter</code>) with the component settings 
	 * @param converter
	 * @param calendar
	 * @return
	 */
	protected static Converter setupDefaultConverter(Converter converter, UICalendar calendar) {
	    // skip id converter is null
	    if(converter == null) {
		return null;
	    }
	    
	    if(converter instanceof DateTimeConverter) {
		DateTimeConverter defaultConverter = (DateTimeConverter) converter;
		defaultConverter.setPattern(calendar.getDatePattern());
		defaultConverter.setLocale(calendar.getAsLocale(calendar.getLocale()));
		defaultConverter.setTimeZone(calendar.getTimeZone());
	    }
	    
	    return converter;
	}

	protected void doDecode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		super.doDecode(context, component);

		String clientId = component.getClientId(context);

		Map<String, String> requestParameterMap = context.getExternalContext()
				.getRequestParameterMap();

		String currentDateString = (String) requestParameterMap.get(clientId + CURRENT_DATE_INPUT);

		if (currentDateString != null) {
			CurrentDateChangeEvent ev = new CurrentDateChangeEvent(component,
					currentDateString);
			ev.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
			ev.queue();
		}

		if (requestParameterMap.get(clientId + CURRENT_DATE_PRELOAD) != null) {
			// TODO nick - nick - queue this event when ValueChangeEvent is
			// queued?
			new AjaxEvent(component).queue();

			AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
			if (ajaxContext.isAjaxRequest(context)) {
				ajaxContext.addAreasToProcessFromComponent(context, component);
			}
		}
		
		String selectedDateString = (String) requestParameterMap.get(clientId
				+ "InputDate");
		if (selectedDateString != null) {
		    	((UICalendar) component).setSubmittedValue(selectedDateString);
		}
	}

	public void encodeChildren(FacesContext context, UIComponent calendar)
			throws IOException {

	}

	public void writeClass(FacesContext context, UIComponent component)
			throws IOException {

		UICalendar calendar = (UICalendar) component;
		String styleClass = (String) calendar.getAttributes().get("styleClass");
		if (styleClass != null && styleClass.length() != 0) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeText(",\n className: '" + styleClass + "'", null);
		}
	}

	public void writeDayStyleClass(FacesContext context, UIComponent component)
			throws IOException {

		UICalendar calendar = (UICalendar) component;
		String dayStyleClass = (String) calendar.getAttributes().get(
				"dayStyleClass");
		if (dayStyleClass != null && dayStyleClass.length() != 0) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeText(",\n dayStyleClass: " + dayStyleClass, null);
		}

	}

	public void writeIsDayEnabled(FacesContext context, UIComponent component)
			throws IOException {
		UICalendar calendar = (UICalendar) component;
		String isDayEnabled = (String) calendar.getAttributes().get(
				"isDayEnabled");
		if (isDayEnabled != null && isDayEnabled.length() != 0) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeText(",\n isDayEnabled: " + isDayEnabled, null);
		}
	}

	public void writeMarkupScriptBody(FacesContext context,
			UIComponent component, boolean children) throws IOException {
		writeScriptBody(context, component, children);
	}

	public void writeOptionalFacetMarkupScriptBody(FacesContext context,
			UIComponent component, String facetName) throws IOException {

		UIComponent facet = component.getFacet(facetName);
		if (facet != null && facet.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeText(",\n " + facetName + MARKUP_SUFFIX + ": ", null);
			writeMarkupScriptBody(context, facet, false);
		}
	}

	public void dayCellClass(FacesContext context, UIComponent component)
			throws IOException {
		// if cellWidth/Height is set send dayCellClass to script
		String cellwidth = (String) component.getAttributes().get("cellWidth");
		String cellheight = (String) component.getAttributes()
				.get("cellHeight");
		ResponseWriter writer = context.getResponseWriter();
		String clientId = component.getClientId(context);
		String divStyle = "";
		if (cellwidth != null && cellwidth.length() != 0) {
			if (cellwidth.contains("px") || cellwidth.contains("%")) {
				divStyle = divStyle + "width:" + cellwidth + ";";
			} else {
				divStyle = divStyle + "width:" + cellwidth + "px;";
			}
		}
		if (cellheight != null && cellheight.length() != 0) {
			if (cellheight.contains("px") || cellheight.contains("%")) {
				divStyle = divStyle + "height:" + cellheight.toString() + ";";
			} else {
				divStyle = divStyle + "height:" + cellheight.toString() + "px;";
			}
		}

		if (divStyle.length() != 0) {
			writer.startElement("style", component);
			getUtils().writeAttribute(writer, "type", "text/css");
			writer.writeText("." + clientId.replace(':', '_') + "DayCell{"
					+ divStyle + "}", null);
			writer.endElement("style");
		}
	}

	public void writeDayCellClass(FacesContext context, UIComponent component)
			throws IOException {

		String cellwidth = (String) component.getAttributes().get("cellWidth");
		String cellheight = (String) component.getAttributes()
				.get("cellHeight");
		ResponseWriter writer = context.getResponseWriter();
		if (cellwidth != null && cellwidth.length() != 0 || cellheight != null
				&& cellheight.length() != 0) {
			String clientId = component.getClientId(context);
			writer.writeText(",\n dayCellClass: '" + clientId.replace(':', '_')
					+ "DayCell'", null);
		}
	}

	public void writeFacetMarkupScriptBody(FacesContext context,
			UIComponent component, String facetName) throws IOException {

		UIComponent facet = component.getFacet(facetName);
		if (facet != null && facet.isRendered()) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeText(",\n " + facetName + MARKUP_SUFFIX + ": ", null);
			writeMarkupScriptBody(context, facet, false);
		}
	}

	public void writePreloadBody(FacesContext context, UICalendar calendar)
			throws IOException {
		Object preload = calendar.getPreload();
		if (preload != null) {
			ResponseWriter writer = context.getResponseWriter();
			writer.write(ScriptUtils.toScript(preload));
		}
	}

	public void writeSubmitFunction(FacesContext context, UICalendar calendar)
			throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(calendar,
				context, AjaxRendererUtils.AJAX_FUNCTION_NAME);
		ajaxFunction.addParameter(JSReference.NULL);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(calendar.getClientId(context) + CURRENT_DATE_PRELOAD, Boolean.TRUE);
		
		Map<String, Object> options = AjaxRendererUtils.buildEventOptions(context, calendar, params);
		options.put("calendar", JSReference.THIS);

		String oncomplete = AjaxRendererUtils.getAjaxOncomplete(calendar);
		JSFunctionDefinition oncompleteDefinition = new JSFunctionDefinition();
		oncompleteDefinition.addParameter("request");
		oncompleteDefinition.addParameter("event");
		oncompleteDefinition.addParameter("data");
		oncompleteDefinition.addToBody("this.calendar.load(data, true);");
		if (oncomplete != null) {
			oncompleteDefinition.addToBody(oncomplete);
		}

		options.put("oncomplete", oncompleteDefinition);
		JSReference requestValue = new JSReference("requestValue");
		ajaxFunction.addParameter(options);
		JSFunctionDefinition definition = new JSFunctionDefinition();
		definition.addParameter(requestValue);
		definition.addToBody(ajaxFunction);
		writer.write(definition.toScript());
	}

	public void writeEventHandlerFunction(FacesContext context,
			UIComponent component, String eventName) throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		Object script = component.getAttributes().get(eventName);
		if (script != null && !script.equals("")) {
			JSFunctionDefinition onEventDefinition = new JSFunctionDefinition();
			onEventDefinition.addParameter("event");
			onEventDefinition.addToBody(script);
			writer.writeText(",\n" + eventName + ": "
					+ onEventDefinition.toScript(), null);
		}
	}

	public String getInputValue(FacesContext context, UIComponent component) {
		UICalendar calendar = (UICalendar) component;
		// Fix for myFaces 1.1.x RF-997
		String returnValue = null;
		Object value = calendar.getSubmittedValue();
		if (value != null) {
		    try {
			returnValue =  getFormattedValue(context, calendar, value); 
		    } catch (Exception e) {
			if (log.isDebugEnabled()) {
			    log.debug(" InputValue: " + e.toString(), e);
			}
			returnValue = (String)value;
		    }
        	} else {
        	    returnValue =  getFormattedValue(context, calendar, calendar.getValue());  
        	}
		
		return returnValue;
	}

	public void writeSymbols(FacesContext facesContext, UICalendar calendar)
			throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		Map<String, String[]> symbolsMap = getSymbolsMap(facesContext, calendar);
		Iterator<Map.Entry<String, String[]>> entryIterator = symbolsMap.entrySet().iterator();
		writer.writeText(", \n", null);
		while (entryIterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) entryIterator.next();

			writer.writeText(ScriptUtils.toScript(entry.getKey()), null);
			writer.writeText(": ", null);
			writer.writeText(ScriptUtils.toScript(entry.getValue()), null);

			if (entryIterator.hasNext()) {
				writer.writeText(", \n", null);
			}
		}
	}

	private static String[] shiftDates(int minimum, int maximum, String[] labels) {
		if (minimum == 0 && (maximum - minimum == labels.length - 1)) {
			return labels;
		}

		String[] shiftedLabels = new String[maximum - minimum + 1];
		System.arraycopy(labels, minimum, shiftedLabels, 0, maximum - minimum
				+ 1);

		return shiftedLabels;
	}

	protected Map<String, String[]> getSymbolsMap(FacesContext facesContext, UICalendar calendar) {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Locale locale = calendar.getAsLocale(calendar.getLocale());
		Calendar cal = calendar.getCalendar();
		int maximum = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
		int minimum = cal.getActualMinimum(Calendar.DAY_OF_WEEK);

		int monthMax = cal.getActualMaximum(Calendar.MONTH);
		int monthMin = cal.getActualMinimum(Calendar.MONTH);

		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[] weekDayLabels = ComponentUtil.asArray(calendar
				.getWeekDayLabels());
		if (weekDayLabels == null) {
			weekDayLabels = symbols.getWeekdays();
			weekDayLabels = shiftDates(minimum, maximum, weekDayLabels);
		}

		String[] weekDayLabelsShort = ComponentUtil.asArray(calendar
				.getWeekDayLabelsShort());
		if (weekDayLabelsShort == null) {
			weekDayLabelsShort = symbols.getShortWeekdays();
			weekDayLabelsShort = shiftDates(minimum, maximum,
					weekDayLabelsShort);
		}

		String[] monthLabels = ComponentUtil.asArray(calendar.getMonthLabels());
		if (monthLabels == null) {
			monthLabels = symbols.getMonths();
			monthLabels = shiftDates(monthMin, monthMax, monthLabels);
		}

		String[] monthLabelsShort = ComponentUtil.asArray(calendar
				.getMonthLabelsShort());
		if (monthLabelsShort == null) {
			monthLabelsShort = symbols.getShortMonths();
			monthLabelsShort = shiftDates(monthMin, monthMax, monthLabelsShort);
		}

		map.put(WEEK_DAY_LABELS, weekDayLabels);
		map.put(WEEK_DAY_LABELS_SHORT, weekDayLabelsShort);
		map.put(MONTH_LABELS, monthLabels);
		map.put(MONTH_LABELS_SHORT, monthLabelsShort);

		return map;
	}

	public String getFirstWeekDay(FacesContext context, UICalendar calendar)
			throws IOException {
		return String.valueOf(calendar.getFirstWeekDay());
	}

	public String getMinDaysInFirstWeek(FacesContext context,
			UICalendar calendar) throws IOException {
		return String.valueOf(calendar.getMinDaysInFirstWeek());
	}

	public String getCurrentDateAsString(FacesContext context,
			UICalendar calendar, Date date) throws IOException {

		Format formatter = new SimpleDateFormat("MM/yyyy");
		return formatter.format(date);
	}

	public String getCurrentDate(FacesContext context, UICalendar calendar,
			Date date) throws IOException {
		return ScriptUtils.toScript(formatDate(date));
	}

	public String getSelectedDate(FacesContext context, UICalendar calendar)
			throws IOException {
	     	Object returnValue = null;
	     	
	     	if(calendar.isValid()) {
	     	    Date date;
	     	    Object value = calendar.getValue();
	     	    date = calendar.getAsDate(value);
	     	    if(date != null) {
	     		returnValue = formatSelectedDate(calendar.getTimeZone(), date);  
	     	    }
	     	}
	     	return ScriptUtils.toScript(returnValue);    
	}

	public static Object formatDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		JSFunction result = new JSFunction("new Date");
		result.addParameter(Integer.valueOf(calendar.get(Calendar.YEAR)));
		result.addParameter(Integer.valueOf(calendar.get(Calendar.MONTH)));
		result.addParameter(Integer.valueOf(calendar.get(Calendar.DATE)));

		return result;
	}

	public static Object formatSelectedDate(TimeZone timeZone, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(timeZone);
		calendar.setTime(date);
		JSFunction result = new JSFunction("new Date");
		result.addParameter(Integer.valueOf(calendar.get(Calendar.YEAR)));
		result.addParameter(Integer.valueOf(calendar.get(Calendar.MONTH)));
		result.addParameter(Integer.valueOf(calendar.get(Calendar.DATE)));
		result
				.addParameter(Integer.valueOf(calendar
						.get(Calendar.HOUR_OF_DAY)));
		result.addParameter(Integer.valueOf(calendar.get(Calendar.MINUTE)));
		result.addParameter(new Integer(0));
		return result;
	}

	/**
	 * Write labels used in the Calendar component, taken from message bundles.
	 * Try to use bundle1 at first. If the 1st bundle is null or it doesn't
	 * contain requested message key, use the bundle2.
	 * @param bundle1 - 1st bundle to be used as a source for messages
	 * @param bundle2 - 2nd bundle to be used as a source for messages
	 * @param name - name of the requested label
	 * @param writer - response writer
	 * @throws IOException
	 */
	public void writeStringsFromBundle(ResourceBundle bundle1, ResourceBundle bundle2, String name,
		ResponseWriter writer) throws IOException {
	    String label = null;
	    String bundleKey = "RICH_CALENDAR_" + name.toUpperCase() + "_LABEL";
	    
	    if (bundle1 != null) {
		try {
		    label = bundle1.getString(bundleKey);
		} catch (MissingResourceException mre) {
		    // Current key was not found, ignore this exception;
		}
	    }
	    // Current key wasn't found in application bundle, use CALENDAR_BUNDLE,
	    // if it is not null
	    if((label == null) && (bundle2 != null)) {
		try {
		    label = bundle2.getString(bundleKey);
		} catch (MissingResourceException mre) {
		    // Current key was not found, ignore this exception;
		}
	    }
	    
	    writeStringFoundInBundle(name, label, writer);
	}
	
	public void writeStringFoundInBundle(String name, String value, ResponseWriter writer) throws IOException {
		if(null!=value){
			if (!("close").equals(name.toLowerCase())) {
				writer.writeText(name.toLowerCase() + ":'" + value + "', ",null);
			} else {
				writer.writeText("close:'" + value + "'", null);					
			}
		}else{
			if (!("close").equals(name.toLowerCase())) {
				writer.writeText(name.toLowerCase() + ":'" + name + "', ",null);
			} else {
				writer.writeText("close:'x'", null);					
			}
		}
		
	}	

	public void writeLabels(FacesContext context, UICalendar calendar)
			throws IOException {

		ResourceBundle bundle1 = null;
		ResourceBundle bundle2 = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String messageBundle = context.getApplication().getMessageBundle();
		Object locale = calendar.getLocale();
		if (null != messageBundle) {
			bundle1 = ResourceBundle.getBundle(messageBundle, calendar.getAsLocale(locale), loader);
		} 
		try {
			bundle2 = ResourceBundle.getBundle(CALENDAR_BUNDLE, calendar.getAsLocale(locale), loader);

		} catch (MissingResourceException e) {
				//No external bundle was found, ignore this exception.				
		}
		
		ResponseWriter writer = context.getResponseWriter();		
		writer.writeText(",\n labels:{", null);
		if (null != bundle1 || null != bundle2) {
			writeStringsFromBundle(bundle1, bundle2, "Apply", writer);
			writeStringsFromBundle(bundle1, bundle2, "Today", writer);
			writeStringsFromBundle(bundle1, bundle2, "Clean", writer);
			writeStringsFromBundle(bundle1, bundle2, "Cancel", writer);
			writeStringsFromBundle(bundle1, bundle2, "OK", writer);
			writeStringsFromBundle(bundle1, bundle2, "Close", writer);
		}else{
			// No bundles were found, use default labels.
			writer.writeText("apply:'Apply', today:'Today', clean:'Clean', ok:'OK', cancel:'Cancel', close:'x'", null);			
		}
		writer.writeText("}", null);

	}
}
