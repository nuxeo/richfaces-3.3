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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIComponentControl;
import org.richfaces.component.util.HtmlUtil;


public class ComponentControlRendererBase  extends HeaderResourcesRendererBase {

    /**
     * Constant for "immediate" attach timing option
     */
    private static final String IMMEDIATE = "immediate";

    /**
     * Constant for "onAvailable" attach timing option
     */
    private static final String ON_AVAILABLE = "onavailable";

    /**
     * Constant for "onload" attach timing option
     */
    private static final String ON_LOAD = "onload";

    protected Class<UIComponentControl> getComponentClass() {
        return UIComponentControl.class;
    }

    /**
     * Additional scripts.
     */
    private final InternetResource[] additionalScripts = { new org.ajax4jsf.javascript.PrototypeScript(),
	    new org.ajax4jsf.javascript.AjaxScript(), getResource("/org/richfaces/renderkit/html/scripts/available.js") };

    /**
     * Perform validation of the component control configuration. Throws FacesException in case validation fails.
     * @param clientId - id of the component
     * @param name - component name
     * @param attachTiming - timing options
     * @param forAttr - client ids of target components
     * @param operation - operation performed on target components
     */
    protected void checkValidity(String clientId, String name, String attachTiming, String forAttr, String operation) {
    	if (!ON_LOAD.equals(attachTiming) && !IMMEDIATE.equals(attachTiming) && !ON_AVAILABLE.equals(attachTiming)) {
    	    throw new FacesException("The attachTiming attribute of the controlComponent  (id='" + clientId
    		    + "') has an invalid value:'" + attachTiming + "'. It may have only the following values: '"
    		    + IMMEDIATE + "', '" + ON_LOAD + "', '" + ON_AVAILABLE + "'");
    	}
    
    	if (operation == null || operation.trim().length() == 0) {
    	    throw new FacesException("The operation attribute of the controlComponent (id='" + clientId
    		    + "') must be specified");
    	}
    }

    protected String replaceClientIds(FacesContext context, UIComponent component, String selector) {
        return HtmlUtil.expandIdSelector(HtmlUtil.idsToIdSelector(selector), component, context);
    }

    /**
     * Prepare Java script according to the timing option of the component control
     * and write it to the ResponceWriter
     * @param context - FacesContext
     * @param component - component control
     * @throws IOException - is thrown in case of writing to ResponceWriter exception
     */
    protected void attachEventAccordingToTimingOption(FacesContext context,
            UIComponent component) throws IOException {
        if (!(component instanceof UIComponentControl)) {
            return;
        }
        
        UIComponentControl componentControl = (UIComponentControl) component;
        String attachTo = componentControl.getAttachTo();
        String attachTiming = componentControl.getAttachTiming();
        boolean isImmediate = attachTiming.equals(IMMEDIATE);
        boolean isOnLoad = attachTiming.equals(ON_LOAD);
        boolean isOnAvailable = attachTiming.equals(ON_AVAILABLE);
        
        if (!(isImmediate || isOnLoad || isOnAvailable || attachTo == null || attachTo.length() == 0)) {
            // unknown value of property "attachTiming"
            return;
        }
        
        ResponseWriter writer = context.getResponseWriter();
        ComponentVariables variables = 
            ComponentsVariableResolver.getVariables(this, componentControl);
        
        writer.startElement("script", componentControl);
        getUtils().writeAttribute(writer, "type", "text/javascript");
        writer.writeText("//", null);
        writer.write("<![CDATA[");
        
        String attachEventBodyStart = "\n{\n    Richfaces.componentControl.attachEvent('";
        StringBuilder attachEventBodyEnd = new StringBuilder();
        attachEventBodyEnd.append("', '");
        attachEventBodyEnd.append(convertToString(variables.getVariable("event")));
        attachEventBodyEnd.append("', '");
        attachEventBodyEnd.append(convertToString(variables.getVariable("forAttr")));
        attachEventBodyEnd.append("', '");
        attachEventBodyEnd.append(convertToString(variables.getVariable("operation")));
        attachEventBodyEnd.append("', function() { return {");
        attachEventBodyEnd.append(convertToString(variables.getVariable("params")));
        attachEventBodyEnd.append("}; }, ");
        attachEventBodyEnd.append(convertToString(componentControl.isDisableDefault()));
        attachEventBodyEnd.append(");\n }");
        
        String pattern = "\\s*,\\s*";
        // "attachTo" attribute may contain several ids splitted by ","
        String[] result = attachTo.split(pattern);
        for (int i = 0; i < result.length; i++) {
            if (isOnLoad) {
                writer.write("\n jQuery(document).ready(function()");
            } else if (isOnAvailable) {
                UIComponent target = RendererUtils.getInstance()
                               .findComponentFor(context, component, result[i]);
                String clientId = (target != null) ? target.getClientId(context) : result[i];
                writer.write("\n Richfaces.onAvailable('" + clientId + "', function()");
            } else if (isImmediate) {
            }
            
            writer.write(attachEventBodyStart);
            writer.write(getUtils().escapeJavaScript(replaceClientIds(context, component, result[i])));
            writer.write(attachEventBodyEnd.toString());
            
            if (isOnLoad || isOnAvailable) {
                writer.write(");");
            }
        }
        writer.writeText("//", null);
        writer.write("]]>");
        writer.endElement("script");
    }
	
    /**
     * Gets additional scripts.
     * 
     * @return array of resources
     */
    protected InternetResource[] getScripts() {
        return additionalScripts;
    }

    /**
     * Returns String representation of object. If object is null,
     * returns empty String.
     * @param obj - object
     * @return String representation of object.
     */
    private static String convertToString(Object obj ) {
        return ( obj == null ? "" : obj.toString() );
    }
}
