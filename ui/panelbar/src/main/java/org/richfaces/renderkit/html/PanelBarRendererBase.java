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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.AjaxScript;
import org.ajax4jsf.javascript.ImageCacheScript;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIPanelBar;
import org.richfaces.component.UIPanelBarItem;
import org.richfaces.event.SwitchablePanelSwitchEvent;
import org.richfaces.renderkit.ScriptOptions;

public abstract class PanelBarRendererBase extends HeaderResourcesRendererBase {

	public static final String PANEL_BAR_RESOURCES = "PANEL_BAR_RESOURCES";
	public static final String EXPANDED_ATTR="expanded";

	private final InternetResource[] scripts = {new AjaxScript(),new PrototypeScript(),new ImageCacheScript(), getResource("/org/richfaces/renderkit/html/scripts/browser_info.js"), getResource("/org/richfaces/renderkit/html/scripts/utils.js"), getResource("scripts/panelbar.js") };
	
	protected InternetResource[] getScripts() {
		return scripts;
	}
	
	protected Class getComponentClass() {
		return UIPanelBar.class;
	}
	
	public boolean getRendersChildren() {
		return true;
	}

	public String expanded(FacesContext context, UIComponent component) throws IOException {
		UIPanelBar panelbar = (UIPanelBar) component;
		String value = (String)panelbar.getValue();
        ValueBinding valueBinding = panelbar.getValueBinding("selectedPanel");
		if (value != null && valueBinding == null) {
			return value;
		} else {
            Object selected = panelbar.getSelectedPanel();
            if (selected != null) {
                List items = panelbar.getChildren();
                for(Iterator it = items.iterator();it.hasNext();) {
                    UIComponent comp = (UIComponent)it.next();
                    if (comp instanceof UIPanelBarItem) {
                        UIPanelBarItem item = (UIPanelBarItem) comp;
                        if (item.getName().equals(selected)) {
                            return item.getClientId(context);
                        }
                    } 
                }
            }
        }
		return "";
	}
	
	public String width(FacesContext context, UIComponent component) throws IOException {
		String width = (String) component.getAttributes().get("width");
		if (width == null || width.length() == 0) {
			width = "100%";
		}
		return "width: "+getUtils().encodePctOrPx(width)+";";
	}
	
	public String height(FacesContext context, UIComponent component) throws IOException {
		String height = (String) component.getAttributes().get("height");
		if (height == null || height.length() == 0 || height.equals("100%")) {
			height = "100%";
		}
		return "height: "+getUtils().encodePctOrPx(height)+";";
	}


	public void decode(FacesContext context, UIComponent component) {
		super.decode(context,component);

		Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
		String value = (String)requestParameterMap.get(component.getClientId(context));
		if (value != null) {
			new SwitchablePanelSwitchEvent(component, value, null).queue();
		}
		
/*		Object property=context.getExternalContext().getRequestParameterMap().get(component.getClientId(context));
		if (property!=null){
			component.getAttributes().put(EXPANDED_ATTR,property);
		}*/
	}
	
	protected List getScriptPanelBarItems(FacesContext context,UIPanelBar panelBar){
		List items = new ArrayList();
		JSReference id_ref = new JSReference("id");
		
		List children = panelBar.getChildren();
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			UIComponent child = (UIComponent) iterator.next();

			if(child instanceof UIPanelBarItem && child.isRendered()){
			
				Map item = new HashMap();
				UIPanelBarItem panelBarItem = (UIPanelBarItem) child;
				item.put(id_ref, panelBarItem.getClientId(context));
		
				Object enterScript = panelBarItem.getAttributes().get("onenter");
				Object leaveScript = panelBarItem.getAttributes().get("onleave");
				
				if (enterScript != null && !enterScript.equals("")) {
					JSFunctionDefinition onenter = new JSFunctionDefinition();
					onenter.addParameter("event");
					onenter.addToBody(enterScript);
					item.put("onenter", onenter);
				} else {
					item.put("onenter", "");
				}
				
				if (leaveScript != null && !leaveScript.equals("")) {
					JSFunctionDefinition onleave = new JSFunctionDefinition();
					onleave.addParameter("event");
					onleave.addToBody(leaveScript);
					item.put("onleave", onleave);
				} else {
					item.put("onleave", "");
				}
				
				items.add(item);
			}
		}
				
		return items;
	}
	
	public void encodeScript(FacesContext context, UIComponent component) throws IOException{
		
		if(component instanceof UIPanelBar){
			UIPanelBar panelBar = (UIPanelBar)component;
			
			List items = getScriptPanelBarItems(context, panelBar);
				
			ScriptOptions options = new ScriptOptions(component);
			
			AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
			
			Object changeScript = panelBar.getAttributes().get("onitemchange");
			
			if (changeScript != null && !changeScript.equals("")) {
				JSFunctionDefinition function =  new JSFunctionDefinition();
				function.addParameter("event");
				function.addToBody(changeScript);
				options.addOption("onitemchange",function);
			} else {
				options.addOption("onitemchange","");
			}
					
			options.addOption("onclick", panelBar.getAttributes().get("onclick"));
			options.addOption("mouseover", panelBar.getAttributes().get("onmouseover"));
			options.addOption("mouseout", panelBar.getAttributes().get("onmouseout"));
			options.addOption("mousemove", panelBar.getAttributes().get("onmousemove"));
			options.addOption("items", items);
			options.addOption("ajax", ajaxContext.isAjaxRequest());
			
					
			StringBuffer script = new StringBuffer();
			JSFunction function = new JSFunction("new Richfaces.PanelBar");
			function.addParameter(panelBar.getClientId(context));
			function.addParameter(options);
			function.appendScript(script);
			
			ResponseWriter writer = context.getResponseWriter();
			writer.startElement(HTML.SCRIPT_ELEM, panelBar);
			writer.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
			String outerScript = script.append(";").toString(); 
			writer.write(outerScript);
			writer.endElement(HTML.SCRIPT_ELEM);
		}
	}
}
