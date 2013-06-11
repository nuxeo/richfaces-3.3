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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.UIModalPanel;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 13.02.2007
 * 
 */
public class ModalPanelRendererBase extends InputRendererBase {

	//TODO nick - set sizeA to actual min value
	private static final int sizeA = 10;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	private static final String STATE_OPTION_SUFFIX = "StateOption_";
	
	protected static final String[] RESIZERS = new String[] {
		"N", "E", "S", "W", "NWU", "NEU", "NEL", 
		"SEU", "SEL", "SWL",
		"SWU",	"NWL"
	};
	
	@SuppressWarnings("unchecked")
	protected void doDecode(FacesContext context, UIComponent component) {
		super.doDecode(context, component);
		
		UIModalPanel panel = (UIModalPanel)component;
		ExternalContext exCtx = context.getExternalContext();
		Map<String, String> rqMap = exCtx.getRequestParameterMap();
		Object panelOpenState = rqMap.get(panel.getClientId(context) + "OpenedState");
		
		if (panel.isKeepVisualState()) {
	        if (null != panelOpenState) {
	            	// Bug https://jira.jboss.org/jira/browse/RF-2466
	        	// Incorrect old: 
	            	//	panel.setShowWhenRendered(Boolean.parseBoolean((String) clnId));
	            	// ShowWhenRendered can be settled separately with modal panel "showWhenRendered" attribute
	            	// so we should combine ShowWhenRendered || KeepVisualState && (OpenedState==TRUE) against rewriting
	        	boolean showWhenRendered = panel.isShowWhenRendered() || Boolean.parseBoolean((String) panelOpenState);
	        	panel.setShowWhenRendered(showWhenRendered);
	        	
	        	Map<String, Object> visualOptions = (Map<String, Object>) panel.getVisualOptions();
	        	Iterator<Entry<String, String>> it = rqMap.entrySet().iterator();
	        	while ( it.hasNext()) {
	        		Map.Entry<String, String> entry = it.next();
	        		int suffixPos = entry.getKey().toString().indexOf(STATE_OPTION_SUFFIX); 
	        		if (-1 != suffixPos) {
	        			String key = entry.getKey().toString().substring(suffixPos + STATE_OPTION_SUFFIX.length());
	        			visualOptions.put(key, entry.getValue());
	        		}
	        	}
	        }
		} 
	}
		
	protected Class getComponentClass() {
		return UIModalPanel.class;
	}

	private static final Set<String> ALLOWED_ATTACHMENT_OPTIONS = new HashSet<String>();
	static {
		ALLOWED_ATTACHMENT_OPTIONS.add("body");
		ALLOWED_ATTACHMENT_OPTIONS.add("parent");
		ALLOWED_ATTACHMENT_OPTIONS.add("form");
	}
	
	//TODO nick - add messages
	public void checkOptions(FacesContext context, UIModalPanel panel) {
		if (panel.isAutosized() && panel.isResizeable()) {
			throw new IllegalArgumentException("Autosized modal panel can't be resizeable.");
		}
		
		String domElementAttachment = panel.getDomElementAttachment();
		if (domElementAttachment != null && domElementAttachment.trim().length() != 0) {
			if (!ALLOWED_ATTACHMENT_OPTIONS.contains(domElementAttachment)) {
				throw new IllegalArgumentException("Value '" + domElementAttachment + "' of domElementAttachment attribute is illegal. " +
						"Allowed values are: " + ALLOWED_ATTACHMENT_OPTIONS);
			}
		}
		
		if (panel.getMinHeight() != -1) {
			if (panel.getMinHeight() < sizeA) {
				throw new IllegalArgumentException();
			}
			
//			if (panel.getHeight() < panel.getMinHeight()) {
//			    panel.setHeight(panel.getMinHeight());
//			}
		}

		if (panel.getMinWidth() != -1) {
			if (panel.getMinWidth() < sizeA) {
				throw new IllegalArgumentException();
			}

//			if (panel.getWidth() < panel.getMinWidth()) {
//			    panel.setWidth(panel.getMinWidth());
//			}
		} 
	}

    public void initializeResources(FacesContext context, UIModalPanel panel)
    throws IOException {
        ComponentVariables variables =
            ComponentsVariableResolver.getVariables(this, panel);

        String onshow = ScriptUtils.toScript(panel.getAttributes().get("onshow"));
        variables.setVariable("onshow", onshow);
        String onhide = ScriptUtils.toScript(panel.getAttributes().get("onhide"));
        variables.setVariable("onhide", onhide);
        String onbeforeshow = ScriptUtils.toScript(panel.getAttributes().get("onbeforeshow"));
        variables.setVariable("onbeforeshow", onbeforeshow);
        String onbeforehide = ScriptUtils.toScript(panel.getAttributes().get("onbeforehide"));
        variables.setVariable("onbeforehide", onbeforehide);
        String onmove = ScriptUtils.toScript(panel.getAttributes().get("onmove"));
        variables.setVariable("onmove", onmove);
    }
    
    
//	protected String buildOptions(FacesContext context, UIModalPanel panel) {
//		return getOptions(context, panel, getUtils());
//	}
	
	public boolean getRendersChildren() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public String getShowScript(FacesContext context, UIModalPanel panel) {
		StringBuffer result = new StringBuffer();
		
		// Bug https://jira.jboss.org/jira/browse/RF-2466
		// We are already processed KeepVisualState and current open state in
		// doDecode, so no need to check panel.isKeepVisualState() here.
		if (/*panel.isKeepVisualState() || */panel.isShowWhenRendered()) {
			result.append("Richfaces.showModalPanel('" + panel.getClientId(context) + "', {");
			
			Iterator<Map.Entry<String, Object>> it = ((Map<String, Object>) panel.getVisualOptions()).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				
				result.append(entry.getKey() + ": '" + entry.getValue() + "'");
				if (it.hasNext()) {
					result.append(", ");
				}
			}
			
			result.append("});");
		}
		return result.toString();
	}
	
	public void writeEventHandlerFunction(FacesContext context, UIComponent component, String eventName) throws IOException{
		RendererUtils.writeEventHandlerFunction(context, component, eventName);
	}
	
	public void writeVisualOptions(FacesContext context, UIModalPanel panel) 
			throws IOException {
		StringBuffer result = new StringBuffer();
		ResponseWriter writer = context.getResponseWriter();
		
		Iterator<Map.Entry<String, Object>> it = ((Map<String, Object>) panel.getVisualOptions()).entrySet().iterator();
		if (it.hasNext()) {
			result.append(",\n");
		}
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			
			result.append(entry.getKey() + ": '" + entry.getValue() + "'");
			if (it.hasNext()) {
				result.append(",\n");
			}
		}
		
		writer.writeText(result, null);
	}
}
