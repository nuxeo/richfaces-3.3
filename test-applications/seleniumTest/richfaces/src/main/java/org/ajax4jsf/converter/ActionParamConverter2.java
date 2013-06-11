/**
 * 
 */
package org.ajax4jsf.converter;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ajax4jsf.model.ActionParamObject2;

/**
 * @author Andrey
 *
 */
public class ActionParamConverter2 implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		String [] s = value.split(",");
		ActionParamObject2 object = null;
		if (s != null && s.length == 2) {
			object = new ActionParamObject2(s[0]);
			object.setDate(String.valueOf(new Date().getTime()));
		}
		
		return object;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		ActionParamObject2 o = (ActionParamObject2)value;
		return o.getName() + "," + o.getDate();
	}

}
