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

/*
 * Created on 04.07.2006
 */
package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxSupport;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.UISimpleTogglePanel;
import org.richfaces.event.SimpleToggleEvent;
import org.richfaces.event.SimpleTogglePanelSwitchEvent;

//public class SimpleTogglePanelRenderer extends AjaxCommandLinkRenderer {

public class SimpleTogglePanelRenderer extends org.ajax4jsf.renderkit.HeaderResourcesRendererBase {

	final static String NONE = "none";
    final static String EMPTY = "";
    
    protected Class<? extends UIComponent> getComponentClass() {
        return UISimpleTogglePanel.class;
    }

    public boolean getRendersChildren() {
        return true;
    }

	public void writeEventHandlerFunction(FacesContext context,
			UIComponent component, String eventName) throws IOException {
		RendererUtils.writeEventHandlerFunction(context, component, eventName);
	}
	
	        
    public void doDecode(FacesContext context, UIComponent component) {

		super.doDecode(context, component);
		ExternalContext exCtx = context.getExternalContext();

		Map <String, String> rqMap = exCtx.getRequestParameterMap();
		Object clnId = rqMap.get(component.getClientId(context));
		UISimpleTogglePanel panel = (UISimpleTogglePanel) component;

		if (clnId != null) {
			boolean currentState = panel.isOpened();
			boolean submittedState = false;

			// enqueue event here for this component or for component with Id
			// taken fro forId attribute
			String switchType = panel.getSwitchType();

			if (UISimpleTogglePanel.CLIENT_SWITCH_TYPE.equals(switchType)) {
				submittedState = Boolean.parseBoolean((String) clnId);
			} else {
				submittedState = !currentState;
			}

			if (currentState != submittedState) {
				SimpleToggleEvent event = new SimpleToggleEvent(panel, submittedState);
				event.queue();

				SimpleTogglePanelSwitchEvent stateEvent = new SimpleTogglePanelSwitchEvent(panel, submittedState);
				stateEvent.queue();
			}

			// in case of "ajax" request and "ajax" switch mode of toggle panel
			// set the regions to be rendered (reRendered) after operating this
			// "ajax" request
			if (AjaxRendererUtils.isAjaxRequest(context)
			        && panel.getSwitchType().equals(UISimpleTogglePanel.AJAX_SWITCH_TYPE)) {
				
			    // add toggle panel itself to rendered list of components
				AjaxRendererUtils.addRegionByName(context, panel, panel.getId());
				
				// add regions specified in the "reRender" attribute of toggle
				// panel to rendered list of components
				AjaxRendererUtils.addRegionsFromComponent(panel, context);

				AjaxContext.getCurrentInstance(context)
				    .addAreasToProcessFromComponent(context, panel);
			}

		}
	}

    public String getdivdisplay(FacesContext context, UIComponent component) {
    	UISimpleTogglePanel simpleTogglePanel = (UISimpleTogglePanel) component;
    	return simpleTogglePanel.isOpened() ? EMPTY : NONE;
// String Switch = Boolean.toString(((UISimpleTogglePanel)
// component).isOpened());
//        if (Switch == null || Switch.equals(Boolean.toString(UISimpleTogglePanel.EXPANDED)))
//        {
//            //xxxx by nick - denis - do not set "block" explicitly - that can break some elements, set "" for display. See Element.show() in prototype.js
//            return "";
//        }
//        return "none";
    }

    public String getOnClick(FacesContext context, UIComponent component) {
        UISimpleTogglePanel tgComp = (UISimpleTogglePanel) component;

        String switchType = tgComp.getSwitchType();
        StringBuffer onClick = new StringBuffer();
    	JSReference eventRef = new JSReference("event");
    	String panelId = tgComp.getClientId(context);

	if (UISimpleTogglePanel.CLIENT_SWITCH_TYPE.equals(switchType)) {
            // Client
            JSFunction function = new JSFunction("SimpleTogglePanelManager.toggleOnClient");
            function.addParameter(eventRef);
            function.addParameter(panelId);
            function.appendScript(onClick);
            onClick.append(";");
            
        } else if (UISimpleTogglePanel.AJAX_SWITCH_TYPE.equals(switchType)) {
//			Ajax
                 	
        	JSFunction function = new JSFunction("SimpleTogglePanelManager.toggleOnAjax");
        	function.addParameter(eventRef);
        	function.addParameter(panelId);
        	function.appendScript(onClick);
        	onClick.append(";");
        
        	JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(tgComp, context);
        	ajaxFunction.addParameter(AjaxRendererUtils.buildEventOptions(context, tgComp));
           	ajaxFunction.appendScript(onClick);
           	
           	if (tgComp instanceof AjaxSupport) {
    			AjaxSupport support = (AjaxSupport) tgComp;
    			if (support.isDisableDefault()) {
    				onClick.append("; return false;");
    			}
    		}

        } else {
            // Server
        	JSFunction function = new JSFunction("SimpleTogglePanelManager.toggleOnServer");	
            function.addParameter(eventRef);
            function.addParameter(panelId);
            function.appendScript(onClick);
            onClick.append(";");        
            //.append(tgComp.getSwitch()==null?"'0'":"'" + tgComp.getSwitch() + "'")
            //.append("")
        }
        return onClick.toString();
    }

    protected String getValueAsString(FacesContext context, UISimpleTogglePanel Panel) {
        return getUtils().getValueAsString(context, Panel);
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        UISimpleTogglePanel comp = (UISimpleTogglePanel) component;
        if (!(((comp.getSwitchType() == null) || (comp.getSwitchType().equals(UISimpleTogglePanel.CLIENT_SWITCH_TYPE) != true)) && (!comp.isOpened())))
        {
            super.encodeChildren(context, component);
        }
    }
    
    public String getSwitchOnStatus(FacesContext context, UIComponent component) {
    	UISimpleTogglePanel simpleTogglePanel = (UISimpleTogglePanel) component;
    	return simpleTogglePanel.isOpened() ? EMPTY : NONE;
//        String sw = Boolean.toString(((UISimpleTogglePanel) component).isOpened());
//        if (sw == null || sw.equals(Boolean.toString(UISimpleTogglePanel.EXPANDED)))
//            return EMPTY; 
//        else return NONE;
    }

    public String getSwitchOffStatus(FacesContext context, UIComponent component) {
    	UISimpleTogglePanel simpleTogglePanel = (UISimpleTogglePanel) component;
    	return simpleTogglePanel.isOpened() ?  NONE : EMPTY ;
   	
//    	String sw = Boolean.toString(((UISimpleTogglePanel) component).isOpened());
//        if (sw == null || sw.equals(Boolean.toString(UISimpleTogglePanel.EXPANDED)))
//            return NONE; 
//        else return EMPTY;
        
    }

}
