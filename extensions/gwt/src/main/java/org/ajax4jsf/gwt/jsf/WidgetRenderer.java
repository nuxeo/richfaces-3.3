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
package org.ajax4jsf.gwt.jsf;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

/**
 * @author shura
 * 
 */
public class WidgetRenderer extends Renderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void decode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		super.decode(context, component);
		String clientId = component.getClientId(context);
		Map requestParameterMap = context.getExternalContext()
				.getRequestParameterMap();
		Object parameter;
		String paramName;
		if (component instanceof ActionSource) {
			if (component instanceof UIWidget) {
				paramName = clientId + ((UIWidget) component).getActionParam();
			} else {
				paramName = clientId;
			}
			parameter = requestParameterMap.get(paramName);
			if (null != parameter) {

				// ActionSource action = (ActionSource) component;
				component.queueEvent(new GwtActionEvent(component, parameter));
			}
		}
		if (component instanceof EditableValueHolder) {
			if (component instanceof UIWidget) {
				paramName = clientId + ((UIWidget) component).getInputParam();
			} else {
				paramName = clientId;
			}
			parameter = requestParameterMap.get(paramName);
			if (null != parameter) {
				EditableValueHolder editable = (EditableValueHolder) component;
				editable.setSubmittedValue(parameter);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		super.encodeBegin(context, component);
		Map attributes = component.getAttributes();
		String moduleName;
		String widgetName;
		if (component instanceof GwtComponent) {
			GwtComponent gwt = (GwtComponent) component;
			moduleName = gwt.getModuleName();
			widgetName = gwt.getWidgetName();
		} else {
			moduleName = (String) attributes.get("moduleName");
			widgetName = (String) attributes.get("widgetName");
		}
		ResponseWriter writer = context.getResponseWriter();
		// Write script and metas if not rendered before.
		PageRenderer.writeModule(context, PageRenderer.getScriptBase(context), moduleName);
		PageRenderer.writeParameters(context);
		// Base <span> - widget place
		String element = getElement(component);
		writer.startElement(element, component);
		writer.writeAttribute("id", component.getClientId(context), null);
		StringBuffer htmlClass = new StringBuffer(moduleName);
		// GWT component module and widget name
		if (null != widgetName) {
			htmlClass.append(" ").append(widgetName);
		}
		Object styleClass = attributes.get("styleClass");
		if (null != styleClass) {
			htmlClass.append(" ").append(styleClass);
		}
		if (htmlClass.length() > 0) {
			writer.writeAttribute("class", htmlClass, "styleClass");
		}
		Object style = attributes.get("style");
		if (null != style) {
			writer.writeAttribute("style", style, "style");
		}
		encodeFacets(writer, context, component, element);
	}

	/**
	 * Hoock method for encode all component facets - use in subclass ability to
	 * encode childrens and facets.
	 * 
	 * @param writer
	 * @param context
	 * @param component
	 * @param element
	 * @throws IOException
	 */
	protected void encodeFacets(ResponseWriter writer, FacesContext context,
			UIComponent component, String element) throws IOException {
		// Put content encoding here.
	}

	protected String getElement(UIComponent component) {
		Object layout = component.getAttributes().get("layout");
		return "block".equals(layout) ? "div" : "span";
	}

	/**
	 * Write in html parameters to create widget. For {@link GwtComponent }
	 * write html span element with id={@link UIComponent#getClientId(javax.faces.context.FacesContext)}+":_data"
	 * and for each entry in {@link GwtComponent#getWidgetParameters()} map -
	 * child span element, with title attribute from key , and text context with
	 * value.
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	protected void writeWidgetParameters(FacesContext context,
			UIComponent component) throws IOException {
		// Write widget data.
		Map widgetParameters = null;
		if (component instanceof GwtComponent) {
			GwtComponent gwt = (GwtComponent) component;
			widgetParameters = gwt.getWidgetParameters();
		}
		String valueAsString = getValueAsString(context, component);
		if (null != widgetParameters || null != valueAsString) {
			ResponseWriter writer = context.getResponseWriter();
			writer.startElement("span", component);
			writer.writeAttribute("id", component.getClientId(context)
					+ ":_data", null);
			writer.writeAttribute("style", "display:none;", null);
			if (null != widgetParameters) {
				writeParametersMap(widgetParameters, writer);
			}
			writeParam(writer, "value", valueAsString);
			writer.endElement("span");
		}

	}

	/**
	 * @param widgetParameters
	 * @param writer
	 * @throws IOException
	 */
	protected void writeParametersMap(Map widgetParameters,
			ResponseWriter writer) throws IOException {
		for (Iterator iter = widgetParameters.keySet().iterator(); iter
				.hasNext();) {
			Object key = iter.next();
			Object paramValue = widgetParameters.get(key);
			writeParam(writer, key, paramValue);
		}
	}

	/**
	 * @param writer
	 * @param paramName
	 * @param paramValue
	 * @throws IOException
	 */
	protected void writeParam(ResponseWriter writer, Object paramName,
			Object paramValue) throws IOException {
		if (null != paramValue) {
			// Write parameter
			writer.startElement("span", null);
			writer.writeAttribute("title", paramName, "param_name");
			if (paramValue instanceof Map) {
				Map paramMap = (Map) paramValue;
				writeParametersMap(paramMap, writer);
			} else {
				writer.writeText(paramValue, "param_value");
			}
			writer.endElement("span");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
		String element = getElement(component);
		ResponseWriter writer = context.getResponseWriter();
		// Parameters
		writeWidgetParameters(context, component);
		// write state for append RPC calls parameter.
		context.getApplication().getViewHandler().writeState(context);
		// TODO - make serialization ?
		writer.endElement(element);
	}

	/**
	 * Return converted value for {@link javax.faces.component.ValueHolder} as
	 * String, perform nessesary convertions.
	 * 
	 * @param context
	 * @param component
	 * @return
	 */
	public String getValueAsString(FacesContext context, UIComponent component) {
		// First - get submitted value for input components
		if (component instanceof EditableValueHolder) {
			EditableValueHolder input = (EditableValueHolder) component;
			String submittedValue = (String) input.getSubmittedValue();
			if (null != submittedValue) {
				return submittedValue;
			}
		}
		// If no submitted value presented - convert same for UIInput/UIOutput
		if (component instanceof ValueHolder) {
			return formatValue(context, component, ((ValueHolder) component)
					.getValue());
		} else {
			return null;
		}
	}

	/**
	 * Convert any object value to string. If component instance of
	 * {@link ValueHolder } got {@link Converter} for formatting. If not,
	 * attempt to use converter based on value type.
	 * 
	 * @param context
	 * @param component
	 * @return
	 */
	public String formatValue(FacesContext context, UIComponent component,
			Object value) {
		if (value instanceof String) {
			return (String) value;
		}
		Converter converter = null;
		if (component instanceof ValueHolder) {
			ValueHolder holder = (ValueHolder) component;
			converter = holder.getConverter();
		}
		if (null == converter && null != value) {
			try {
				converter = context.getApplication().createConverter(
						value.getClass());
			} catch (FacesException e) {
				// TODO - log converter exception.
			}
		}
		if (null != value) {
			if (null == converter) {
				return value.toString();
			} else {
				return converter.getAsString(context, component, value);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#getConvertedValue(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public Object getConvertedValue(FacesContext context,
			UIComponent component, Object submittedValue)
			throws ConverterException {
		if (component instanceof EditableValueHolder) {
			EditableValueHolder valueHolder = (EditableValueHolder) component;
			return getConvertedUIInputValue(context, valueHolder,
					(String) submittedValue);
		}
		return super.getConvertedValue(context, component, submittedValue);
	}

	public Object getConvertedUIInputValue(FacesContext facesContext,
			EditableValueHolder component, String submittedValue)
			throws ConverterException {

		Object convertedValue = null;

		/*
		 * if (submittedValue == null) throw new
		 * NullPointerException("submittedValue");
		 */
		if ("".equals(submittedValue)) {
			return null;
		}
		Converter converter = getConverterForProperty(facesContext, component,
				"value");
		if (converter != null) {
			convertedValue = converter.getAsObject(facesContext,
					(UIComponent) component, submittedValue);
		} else {
			convertedValue = submittedValue;
		}

		return convertedValue;

	}

	/**
	 * 
	 * @param facesContext
	 * @param component
	 * @param property
	 * @return converter for specified component attribute
	 */
	public Converter getConverterForProperty(FacesContext facesContext,
			ValueHolder component, String property) {
		Converter converter = component.getConverter();
		if (converter == null) {
			ValueExpression valueBinding = ((UIComponent) component)
					.getValueExpression(property);
			if (valueBinding != null) {
				Class valueType = valueBinding.getType(facesContext
						.getELContext());
				if (String.class.equals(valueType)
						|| Object.class.equals(valueType)) {
					// No converter needed
				} else {
					converter = facesContext.getApplication().createConverter(
							valueType);
					if (converter == null) {
						throw new ConverterException("No converter found for "
								+ valueType.getName());
					}
				}
			}
		}
		return converter;
	}

}
