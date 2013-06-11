package org.richfaces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CustomConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Bean bean = (Bean)context.getExternalContext().getSessionMap().get("pickBean");
		return bean.getStore().get(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			return ((Animal)value).getName();
		} else {
			return "";
		}
	}

}
