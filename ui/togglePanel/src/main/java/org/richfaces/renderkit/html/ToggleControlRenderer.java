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
import javax.faces.component.UIForm;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.richfaces.component.UIToggleControl;
import org.richfaces.component.UITogglePanel;
import org.richfaces.event.SwitchablePanelSwitchEvent;

/**
 * @author igels
 *
 */
public class ToggleControlRenderer extends HeaderResourcesRendererBase {

	//xxx by nick - denis - please move scripts to template
	//private InternetResource[] _scripts = {new PrototypeScript(), getResource("scripts/togglePanel.js") };	

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		return UIToggleControl.class;
	}

	public void doDecode(FacesContext context, UIComponent component) {
		super.doDecode(context, component);
		ExternalContext exCtx = context.getExternalContext();
		Map<String, String> rqMap = exCtx.getRequestParameterMap();
		Object clnId = rqMap.get(component.getClientId(context));
		if (clnId != null) {
			// enqueue event here for this component or for component with Id 
			// taken fro forId attribute
			UIToggleControl control = (UIToggleControl)component;

			UITogglePanel panel = control.getPanel();

        	new SwitchablePanelSwitchEvent(panel, null, control).queue();
			
		    if (UITogglePanel.AJAX_METHOD.equals(panel.getSwitchType())) {
		    	 new AjaxEvent(component).queue();
		    	 
                // add regions specified in the "reRender" attribute of toggle
                // panel to rendered list of components
                AjaxRendererUtils.addRegionsFromComponent(control, context);
                
                AjaxContext.getCurrentInstance(context)
                    .addAreasToProcessFromComponent(context, control);
		    }
		    
		    ActionEvent actionEvent = new ActionEvent(component);
	    	component.queueEvent(actionEvent);
		}
	}

	public String getOnClick(FacesContext context, UIComponent component) {
		UIToggleControl tgComp = (UIToggleControl)component;
		UITogglePanel panel = tgComp.getPanel();
		//UITogglePanel panel = tgComp.getPanel(context); 
		//denis
		String switchType = panel.getSwitchType();
		StringBuffer onClick = new StringBuffer();
		String userOnClick = (String)component.getAttributes().get("onclick");
		if(userOnClick!=null) {
			onClick.append(userOnClick);
			if(!userOnClick.trim().endsWith(";")) {
				onClick.append("; ");
			}
		}

		if(UITogglePanel.CLIENT_METHOD.equals(switchType)) {
			// Client
			String panelId = panel.getClientId(context);
			String switchToDivId = tgComp.getSwitchToState();
			onClick.append("TogglePanelManager.toggleOnClient('")
			.append(panelId).append("',")
			.append(switchToDivId==null?"null":"'" + switchToDivId + "'")
			.append(");");
		} else if(UITogglePanel.AJAX_METHOD.equals(switchType)) {
			// Ajax
			if ( !getUtils().isBooleanAttribute(component,"disabled")) {
				return AjaxRendererUtils.buildOnClick(component, context).toString();
			} else {
				return "return false;";
			}
		} else {
			// Server
			//xxx by nick - denis - use org.ajax4jsf.renderkit.RendererUtils#getNestingForm(FacesContext, UIComponent)
			UIForm Form= getUtils().getNestingForm(context, component);
			String formId=null;			
			if (Form!=null){
				 formId = Form.getClientId(context);
			}						
			if(formId==null) {
				throw new RuntimeException("toogleControl (id=\"" + component.getClientId(context) + "\") did not find parent form.");
			}

			onClick.append("TogglePanelManager.toggleOnServer('")
				.append(formId).append("','")
				.append(component.getClientId(context)).append("',")
				.append(tgComp.getSwitchToState()==null?"''":"'" + tgComp.getSwitchToState() + "'")
				.append(");");
		}
		return onClick.toString();
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
 		    super.encodeChildren(context, component);
	}
}
