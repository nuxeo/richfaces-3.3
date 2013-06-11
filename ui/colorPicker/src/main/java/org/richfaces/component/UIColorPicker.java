/**
 *
 */

package org.richfaces.component;

import java.awt.Color;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.richfaces.convert.AWTColorConverter;
import org.richfaces.convert.IntegerColorConverter;
import org.richfaces.convert.LongColorConverter;

/**
 * JSF component class
 *
 */
public abstract class UIColorPicker extends UIInput {
	
	public static final String COMPONENT_TYPE = "org.richfaces.ColorPicker";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.ColorPicker";

	public static final String COLOR_MODE_RGB = "rgb";
	
	public static final String COLOR_MODE_HEX = "hex";

	public abstract String getColorMode();
	public abstract void setColorMode(String colorMode);
	
	@Override
	public Converter getConverter() {
		Converter converter = super.getConverter();
		if (converter == null) {
			ValueExpression valueExpression = this.getValueExpression("value");
			if (valueExpression != null) {
				FacesContext context = getFacesContext();
				
			    Class<?> converterType = valueExpression.getType(context.getELContext());
			    Application application = context.getApplication();
				if (converterType != null && !String.class.equals(converterType) && 
			    		!Object.class.equals(converterType) && !Integer.class.equals(converterType) && 
			    		!Long.class.equals(converterType) /* standard integer or long converter won't suite our needs */) {

			    	converter = application.createConverter(converterType);
			    }
			    
			    if (converter == null) {
			    	if (Long.class.equals(converterType)) {
			    		converter = application.createConverter(LongColorConverter.CONVERTER_ID);
			    	} else if (Integer.class.equals(converterType)) {
			    		converter = application.createConverter(IntegerColorConverter.CONVERTER_ID);
			    	} else if (Color.class.equals(converterType)) {
			    		converter = application.createConverter(AWTColorConverter.CONVERTER_ID);
			    	}
			    }
			}
		}
		
		return converter;
	}
	
}
