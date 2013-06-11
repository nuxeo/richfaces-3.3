/**
 * 
 */
package org.richfaces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author mikalaj
 *
 */
public class OptionItemConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		int index = value.indexOf(':');
		
		return new OptionItem(value.substring(0, index), Integer.valueOf(value.substring(index + 1)).intValue());
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		OptionItem optionItem = (OptionItem) value;
		return optionItem.getName() + ":" + optionItem.getPrice();
	}

}
