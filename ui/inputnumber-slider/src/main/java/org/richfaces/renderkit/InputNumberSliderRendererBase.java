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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.UIInputNumberSlider;
import org.richfaces.component.util.HtmlUtil;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 19.01.2007
 *
 */
public class InputNumberSliderRendererBase extends InputRendererBase {

	protected Class getComponentClass() {
		return UIInputNumberSlider.class;
	}

	protected boolean attributeToBoolean(UIComponent component, String attributeName) {
		return getUtils().isBooleanAttribute(component, attributeName);
	}
	
	public void writeEventHandlerFunction(FacesContext context, UIComponent component, String eventName) throws IOException{
		RendererUtils.writeEventHandlerFunction(context, component, eventName);
	}
	
	public void prepareVariables(FacesContext context, UIInputNumberSlider slider) {
		ComponentVariables variables = ComponentsVariableResolver.getVariables(this, slider);
		
		String inputPosition = (String) slider.getAttributes().get(
				"inputPosition");
		if (!"right".equals(inputPosition) && !"left".equals(inputPosition)
				&& !"top".equals(inputPosition)
				&& !"bottom".equals(inputPosition)) {
			inputPosition = "right";
		}
		if ("bottom".equals(inputPosition)) {
			inputPosition = "left";
		}
		if ("top".equals(inputPosition)) {
			inputPosition = "right";
		}

		variables.setVariable("inputPosition", inputPosition);

        boolean disabled = attributeToBoolean(slider, "disabled");
        boolean showInput = attributeToBoolean(slider, "showInput");
        boolean manualInput = attributeToBoolean(slider, "enableManualInput");

        variables.setVariable("showInput",new Boolean(showInput));
        variables.setVariable("inputReadOnly",new Boolean(!manualInput));
        variables.setVariable("inputDisabled",new Boolean(disabled));

        if (!manualInput || disabled){
            variables.setVariable("color", "color: gray;");
        }
        else{
            variables.setVariable("color", "");
        }

        variables.setVariable("inputSize", slider.getAttributes().get("inputSize"));
        variables.setVariable("style", getStyleValue(slider));
        
        variables.setVariable("orientation", slider.getAttributes().get(
				"orientation"));
		boolean showArrows = attributeToBoolean(slider, "showArrows");
		variables.setVariable("showArrows", new Boolean(showArrows));
		variables.setVariable("delay", slider.getAttributes().get("delay"));
	}
	
	public String getStyleValue(UIInputNumberSlider slider) {
		StringBuffer buf = new StringBuffer();
		String width = slider.getWidth();
		String height = slider.getHeight();
		String style = slider.getStyle();
		if (width != null && width.length() > 0) {
			buf.append("width:" + HtmlUtil.qualifySize(width));
			buf.append(";");
		}

		if (height !=null && height.length() > 0) {
			buf.append("height:" + HtmlUtil.qualifySize(height));
			buf.append(";");
		}
		if (style !=null && style.length() > 0) {
			buf.append(style);
		}
		
		return buf.toString();
	}

}
