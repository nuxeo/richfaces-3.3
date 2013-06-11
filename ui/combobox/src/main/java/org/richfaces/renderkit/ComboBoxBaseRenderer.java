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
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;

import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.util.InputUtils;
import org.ajax4jsf.util.SelectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.UIComboBox;

/**
 * @author Anton Belevich
 * @since 3.2.0
 * ComboBox Base renderer implementation
 *  	
 */
public class ComboBoxBaseRenderer extends HeaderResourcesRendererBase {
	
    private static final String RICH_COMBOBOX_ITEM_CLASSES = "rich-combobox-item rich-combobox-item-normal";
    private static Log logger = LogFactory.getLog(ComboBoxBaseRenderer.class);

    protected Class<? extends UIComponent> getComponentClass() {
    	return UIComboBox.class;
    }

    protected void doDecode(FacesContext context, UIComponent component) {
	UIComboBox comboBox = null;

	if (component instanceof UIComboBox) {
	    comboBox = (UIComboBox) component;
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug("No decoding necessary since the component " + component.getId() + " is not an instance or a sub class of UIComboBox");
	    }
	    return;
	}
	
	String clientId = comboBox.getClientId(context);
	if (clientId == null) {
	    throw new NullPointerException("component client id is NULL");
	}

	if (InputUtils.isDisabled(comboBox) || InputUtils.isReadOnly(comboBox)) {
	    if (logger.isDebugEnabled()) {
	    	logger.debug(("No decoding necessary since the component " + component.getId() + " is disabled"));
	    }
	}

	Map <String, String> request = context.getExternalContext().getRequestParameterMap();
	String newValue = (String) request.get(clientId);
	if (newValue != null) {
	    comboBox.setSubmittedValue(newValue);
	}
    }
    
    public List<Object> encodeItems(FacesContext context, UIComponent component) throws IOException, IllegalArgumentException {
    	List <Object>values = new ArrayList<Object>();
		if (isAcceptableComponent(component)) {
			UIComboBox comboBox = (UIComboBox) component;
			values.addAll(encodeSuggestionValues(context, comboBox));
			List<SelectItem> selectItems = SelectUtils.getSelectItems(context, component);
			for (SelectItem selectItem : selectItems) {
			    String convertedValue = getConvertedStringValue(context, component, selectItem.getValue());
				encodeSuggestion(context, comboBox, convertedValue, RICH_COMBOBOX_ITEM_CLASSES);
				values.add(convertedValue);
			}
		}
		return values;
    }
    
    public List<Object> encodeSuggestionValues(FacesContext context, UIComboBox combobox) throws IOException, IllegalArgumentException {
	
	    List<Object> values = new ArrayList<Object>();
	    Object suggestionValues = combobox.getSuggestionValues();
		if (suggestionValues != null) {
		    if (suggestionValues instanceof Collection) {
				Collection collection = (Collection) suggestionValues;
				for (Object suggestionValue : collection) {
				    String convertedValue = getConvertedStringValue(context, combobox, suggestionValue); 
					encodeSuggestion(context, combobox, convertedValue, RICH_COMBOBOX_ITEM_CLASSES);
					values.add(convertedValue);
				}
		    } else if (suggestionValues.getClass().isArray()) {
				Object[] suggestions = (Object[]) suggestionValues;
				for (Object suggestionValue: suggestions) {
				    String convertedValue = getConvertedStringValue(context, combobox, suggestionValue); 
				    encodeSuggestion(context, combobox, convertedValue, RICH_COMBOBOX_ITEM_CLASSES);
				    values.add(convertedValue); 
				}
		    } else {
		    	throw new IllegalArgumentException("suggestionValues should be Collection or Array");
		    }
		}
		return values;
    }

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
    	return InputUtils.getConvertedValue(context, component, submittedValue);
    }

    protected String getConvertedStringValue(FacesContext context, UIComponent component, Object value) {
    	return InputUtils.getConvertedStringValue(context, component, value);
    }

    protected void encodeSuggestion(FacesContext context, UIComponent component, String value, String classes) throws IOException {
    	ResponseWriter writer = context.getResponseWriter();
    	if(writer != null) {
	    	writer.startElement(HTML.SPAN_ELEM, component);
	    	writer.writeAttribute(HTML.class_ATTRIBUTE, classes, null);
	    	writer.writeText(value, null);
	    	writer.endElement(HTML.SPAN_ELEM);
    	}
    }	
    
    protected boolean isAcceptableComponent(UIComponent component) {
    	return component != null && this.getComponentClass().isAssignableFrom(component.getClass());
    }
    
    public String getItemsTextAsJSArray(FacesContext context, UIComponent component, List items) {
    	return ScriptUtils.toScript(items);
    }

    public String getAsEventHandler(FacesContext context, UIComponent component, String attributeName) {
	JSFunctionDefinition script = getUtils().getAsEventHandler(context, component, attributeName, null);  
	return ScriptUtils.toScript(script);
    }
    
    public String encodeValue(String value){
	return ScriptUtils.toScript(value);     
    }
}
