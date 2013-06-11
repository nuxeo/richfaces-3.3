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
import org.richfaces.model.StackingTreeModelKey;
import org.richfaces.model.TreeRowKey;

public class TreeAdaptorRowKeyConverter extends BaseTreeConverter {

	public static final String CONVERTER_ID = "org.richfaces.TreeAdaptorRowKeyConverter";
	
	protected Object convertStringToModelKey(FacesContext context, UIComponent component, 
			String modelId, String value) {
		
		return value;
	}
	
	protected String convertModelKeyToString(FacesContext context, UIComponent component, 
			String modelId, Object value) {
		
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
			List<StackingTreeModelKey<?>> keys = new ArrayList<StackingTreeModelKey<?>>();
			Iterator<String> iterator = splitKeyString(value).iterator();
			while (iterator.hasNext()) {
				String modelId = iterator.next();
				String modelKey = iterator.next();
			
				Object convertedModelKey = convertStringToModelKey(context, component, modelId, modelKey);
				keys.add(new StackingTreeModelKey<Object>(modelId, convertedModelKey));
			}
			
			
			return new ListRowKey<StackingTreeModelKey<?>>(keys);
		} catch (Exception e) {
			Object label = MessageUtil.getLabel(context, component);
			String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

			throw new ConverterException(new FacesMessage(summary), e);
		}
	}
	
	@SuppressWarnings("unchecked")
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
			StringBuilder sb = new StringBuilder();
			TreeRowKey<StackingTreeModelKey<?>> treeRowKey = (TreeRowKey<StackingTreeModelKey<?>>) value;
			Iterator<StackingTreeModelKey<?>> iterator = treeRowKey.iterator();
			while (iterator.hasNext()) {
				StackingTreeModelKey<?> key = iterator.next();
				String modelId = key.getModelId();
				appendToKeyString(sb, modelId);
				Object modelKey = key.getModelKey();
				String convertedModelKey = convertModelKeyToString(context, component, modelId, modelKey);
				appendToKeyString(sb, convertedModelKey);
			}
			
			return sb.toString();
		} catch (Exception e) {
			Object label = MessageUtil.getLabel(context, component);
			String summary = Messages.getMessage(Messages.COMPONENT_CONVERSION_ERROR, label, value);

			throw new ConverterException(new FacesMessage(summary), e);
		}
	}
}
