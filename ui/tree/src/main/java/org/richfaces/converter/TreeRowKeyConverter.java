package org.richfaces.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.ajax4jsf.Messages;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeRowKey;

public class TreeRowKeyConverter extends BaseTreeConverter {

	public static final String CONVERTER_ID = "org.richfaces.TreeRowKeyConverter";
	
	protected Object convertStringToKey(FacesContext context, UIComponent component, String value) {
		return value;
	}
	
	protected String convertKeyToString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		if (value == null || value.length() == 0) {
			return null;
		}
		
		try {
			List<String> split = splitKeyString(value);
			List<Object> result = new ArrayList<Object>(split.size());
			
			Iterator<String> iterator = split.iterator();
			while (iterator.hasNext()) {
				String stringKey = iterator.next();
				Object convertedKey = convertStringToKey(context, component, stringKey);
				result.add(convertedKey);
			}
			
			return new ListRowKey<Object>(result);
		} catch (Exception e) {
			Object label = MessageUtil.getLabel(context, component);
			String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

			throw new ConverterException(new FacesMessage(summary), e);
		}
		
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (context == null) {
			throw new NullPointerException("context");
		}
		
		if (component == null) {
			throw new NullPointerException("component");
		}
		
		if (value == null) {
			return null;
		}
		
		try {
			TreeRowKey<?> key = (TreeRowKey<?>) value;
			Iterator<?> iterator = key.iterator();
			StringBuilder sb = new StringBuilder();
			
			while (iterator.hasNext()) {
				Object next = iterator.next();
				String convertedKey = convertKeyToString(context, component, next);
				
				appendToKeyString(sb, convertedKey);
			}
			
			return sb.toString();
		} catch (Exception e) {
			Object label = MessageUtil.getLabel(context, component);
			String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

			throw new ConverterException(new FacesMessage(summary), e);
		}
		
	}

}
