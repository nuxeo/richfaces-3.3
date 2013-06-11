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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.util.SelectUtils;
import org.richfaces.component.UIDataFltrSlider;
import org.richfaces.event.DataFilterSliderEvent;

/**
 * @author Wesley Hales
 */
public class DataFilterSliderRendererBase extends org.ajax4jsf.renderkit.HeaderResourcesRendererBase {

    protected Class getComponentClass() {
        return UIDataFltrSlider.class;
    }

    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
        return SelectUtils.getConvertedUIInputValue(context, (UIInput) component, (String) submittedValue);
    }

    public Map getRequestMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
    
    public String  getEventHandlerFunction(UIDataFltrSlider slider,String eventName){
    	
    	String returnScript = null;     	
    	Object script = slider.getAttributes().get(eventName);
    	if(script != null && !script.equals("")){
    		JSFunctionDefinition jsFunctionDefinition  = new JSFunctionDefinition();
    		jsFunctionDefinition.addParameter("event");
    		jsFunctionDefinition.addToBody(script);
    		returnScript = jsFunctionDefinition.toScript();
    	}	
    	
    	return returnScript; 
    }

    public String renderSliderJs1(FacesContext context, UIDataFltrSlider slider) throws IOException {
	AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
    	StringBuffer script = new StringBuffer();
    	String clientId = slider.getClientId(context);
    	script.append("var dataFilterSlider = ");
        script.append("new ");
    	
       	StringBuffer options = new StringBuffer();
    	options.append("{");
    	options.append("range: $R(" + slider.getStartRange() + "," + slider.getEndRange() + ")");
    	options.append(",values: [" + slider.getSliderRange() + "]");
    	options.append(",startSpan: '"+ clientId +"slider-trailer'");
    	options.append(",sliderInputId: '" + clientId + "slider_val'");
    	options.append(",isAjax: " + ajaxContext.isAjaxRequest());
    	options.append(",sliderValue:$('"+ clientId +"slider_val').value");
    	
    	String onslide = getEventHandlerFunction(slider, "onslide");
    	if(onslide != null){
        	options.append(", onslide: " + onslide);
    	}
    	
    	String onchange = getEventHandlerFunction(slider, "onchange");
    	if(onchange != null){
    		options.append(", onchange: " + onchange);
    	}

    	if (slider.isSubmitOnSlide()) {
	    options.append(",onSlideSubmit: function(event, v) { updateSlider1(event, v);}");
	}
        options.append("}");

        JSReference reference = new JSReference(options.toString());
        
    	JSFunction function = new JSFunction("Richfaces.DFSControl.Slider");
    	function.addParameter(clientId + "slider-handle");
    	function.addParameter(clientId + "slider-track");
    	function.addParameter(reference);
    	function.appendScript(script);
    	script.append(";");
    	
    	return script.toString();
    }	
    

    public String renderSliderJs2(FacesContext context, UIDataFltrSlider slider) throws IOException {

	String clientId = slider.getClientId(context);
	
	String formClientId = _findFormClientId(context, slider);

        String sliderClientId = slider.getClientId(context);

        String id = slider.getId();

    	JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(slider,context);
    	ajaxFunction.addParameter(AjaxRendererUtils.buildEventOptions(
				context, slider));
    	StringBuffer script= new StringBuffer();
        ajaxFunction.appendScript(script);
        script.append("; return false");
        String sliderHandlerScript = "function updateSlider1(event, value) {\n" +
                                                "$('"+ clientId +"slider_val').value = value;\n" +
                                                script.toString() +
                                                "}\n";

        return sliderHandlerScript;
    }

    /**
     * Finds the parent UIForm component client identifier.
     *
     * @param context   the Faces context
     * @param component the Faces component
     * @return the parent UIForm client identifier, if present, otherwise null
     */
    private String _findFormClientId(
            FacesContext context,
            UIComponent component) {
        while (component != null &&
                !(component instanceof UIForm)) {
            component = component.getParent();
        }

        return (component != null) ? component.getClientId(context) : null;
    }

    protected void doDecode(FacesContext context, UIComponent component) {

        ExternalContext external = context.getExternalContext();
        Map requestParams = external.getRequestParameterMap();
        String clientId = component.getClientId(context);

        UIDataFltrSlider dataFilterSlider = (UIDataFltrSlider) component;
        Integer oldSliderVal = dataFilterSlider.getHandleValue();
        //Here we get new slider val and compare with old value
        Integer newSliderVal = dataFilterSlider.getStartRange();

        if (requestParams.get(clientId + "slider_val") != null
                	&& !"".equals(requestParams.get(clientId + "slider_val"))) {
        	try {
        		newSliderVal = Integer.valueOf(requestParams.get(clientId + "slider_val").toString());
        	} catch (NumberFormatException nfe) {
        		newSliderVal = oldSliderVal;
        	}
        }

        if (newSliderVal.equals(oldSliderVal)) {
            //you could use this to reset the table, but it gets called when the dataScroller is clicked also
            //The only way I know to reset the table is to find the component and use helper to reset in an actionListener
            //ajaxSlider.resetDataTable();
            //ajaxSlider.setHandleValue(ajaxSlider.getStartRange());
        } else {
            dataFilterSlider.setHandleValue(newSliderVal);

            new DataFilterSliderEvent(dataFilterSlider, oldSliderVal, newSliderVal).queue();
            new ActionEvent(dataFilterSlider).queue();

            if (dataFilterSlider.isStoreResults()) {
                dataFilterSlider.filterDataTable(newSliderVal.intValue());
            }

        }
    }

    public void encodeChildren(FacesContext context,
                               UIComponent component) throws IOException {

        component.encodeBegin(context);
        if (component.getChildCount() > 0) {

            if (component.getRendersChildren()) {
                component.encodeChildren(context);
            }
            component.encodeEnd(context);

        }
    }

    protected boolean attributeToBoolean(UIComponent component, String attributeName) {
        Object object = component.getAttributes().get(attributeName);
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }

        if (object instanceof String) {
            return new Boolean((String) object).booleanValue();
        }

        return object != null && object.equals(Boolean.TRUE);
    }  

}
