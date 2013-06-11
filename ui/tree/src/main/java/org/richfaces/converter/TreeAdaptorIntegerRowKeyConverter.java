package org.richfaces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class TreeAdaptorIntegerRowKeyConverter extends TreeAdaptorRowKeyConverter {

	public static final String CONVERTER_ID = "org.richfaces.TreeAdaptorIntegerRowKeyConverter";
	
	@Override
	protected Object convertStringToModelKey(FacesContext context,
			UIComponent component, String modelId, String value) {

		return Integer.parseInt(value);
	}
}
