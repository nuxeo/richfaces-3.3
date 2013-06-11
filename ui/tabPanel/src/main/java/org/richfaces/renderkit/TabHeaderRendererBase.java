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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UISwitchablePanel;
import org.richfaces.component.UITab;
import org.richfaces.component.UITabPanel;
import org.richfaces.component.util.HtmlUtil;


/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 *         created 12.01.2007
 */
public class TabHeaderRendererBase extends org.ajax4jsf.renderkit.HeaderResourcesRendererBase {

    private static final String LABEL_SUFFIX = "_lbl";

    protected Class<? extends UIComponent> getComponentClass() {
        return UITab.class;
    }
    
    // find and encode UIParameter's components
    //TODO generify
    //TODO move the code to utils
    public List encodeParams(FacesContext context, UITab component) throws IOException {
    	
    	UITab menuItem = component;
    	List params = new ArrayList();
    	//TODO use StringBuilder
    	StringBuffer buff = new StringBuffer();
    	
    	//TODO use getChildCount() > 0
    	List children = menuItem.getChildren();
    	for (Iterator iterator = children.iterator(); iterator.hasNext();) {
    		UIComponent child = (UIComponent) iterator.next();
	
    		if(child instanceof UIParameter){
					
    			UIParameter param = (UIParameter)child;
				String name = param.getName();
				
				if (name != null) {
					
					Object value = param.getValue();
					buff.append("_params[");
					buff.append(ScriptUtils.toScript(name));
					buff.append("] = ");
					buff.append(ScriptUtils.toScript(value));
					buff.append(";");
					
					//TODO ???
					params.add(buff.toString());

					buff.setLength(0);
				}
			}
    	}
    	
    	return params;
  	}	
    
    public void encodeTabLabel(FacesContext context, UITab tab) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        boolean disabled = tab.isDisabled();
        UITabPanel pane = tab.getPane();
        String method = tab.getSwitchTypeOrDefault();
        boolean ajax = UISwitchablePanel.AJAX_METHOD.equals(method);
        boolean clientSide = UISwitchablePanel.CLIENT_METHOD.equals(method);
        String label = tab.getLabel();

        if (label == null) {
            label = "";
        }

        String clientId = tab.getClientId(context);

        //String style = "position:relative; top:1px;" + (String) tab.getAttributes().get("style");
        
        //TODO use CSS classes
        String defShift = tab.isActive() ? "position:relative; top:1px;" : "position:relative;";
        String componentStyle = (String) tab.getAttributes().get("style");
        String style = defShift + (componentStyle != null ? componentStyle : "");

        //TODO format code block properly
        if (!disabled) {
            if (clientSide) {
        	//TODO use StringBuilder
                writer.writeAttribute(HTML.onclick_ATTRIBUTE, "if (RichFaces.onTabChange(event, '"+pane.getClientId(context)+"','"+
                	clientId+"')) RichFaces.switchTab('" + 
                	pane.getClientId(context) + "','" + clientId + "','" + 
                	getUtils().formatValue(context, pane, tab.getName()) + "');", "switchScript");
            } else {
                String activeCheck = "if (RichFaces.isTabActive('" + clientId + LABEL_SUFFIX + "')) return false;";
                String eventCheck = " if (!RichFaces.onTabChange(event, '"+pane.getClientId(context)+"','"+clientId+"')) return false;";
                
                if (ajax) {
                    JSFunction function = AjaxRendererUtils.buildAjaxFunction(tab,
                            context);
                    Map eventOptions = AjaxRendererUtils.buildEventOptions(context,
                            tab);
                    function.addParameter(eventOptions);

                    StringBuffer buffer = new StringBuffer();
                    function.appendScript(buffer);
                    //TODO remove this.onclick = null
                    buffer.append("; return false; this.onclick = null;");
                    String script = buffer.toString();
                    writer.writeAttribute(HTML.onclick_ATTRIBUTE, activeCheck + eventCheck +  script, null);
                } else /* TODO if server */ {
                    StringBuffer script = new StringBuffer("var _formName = A4J.findForm(this).id; var _paramName = '" + clientId + "_server_submit'; var _params = new Object(); _params[_paramName] = _paramName; ");
                    List params = encodeParams(context, tab);
                    
                    for (Iterator iterator = params.iterator(); iterator.hasNext();) {
						script.append(iterator.next());
					}
                    
                    script.append("_JSFFormSubmit('");
                    script.append(clientId);
                    script.append("', _formName, null, _params);");
                    //TODO remove this.onclick = null
                    script.append("this.onclick = null; _clearJSFFormParameters(_formName, null, [_paramName]);");

                    writer.writeAttribute(HTML.onclick_ATTRIBUTE, activeCheck + eventCheck + script.toString()
                            /* "RichFaces.submitTab(this,'"+clientId + "_inp" +"','"+pane.getClientId(context)+"');"*/, null);
                }
            }
        }


        String width = tab.getLabelWidth();
        
        //TODO move to class
        style += ";height : 100%; ";
        if (width != null) {
            //TODO use qualifySize
            style += " width: " + getUtils().encodePctOrPx(width) + ";";
        }

        if (style != null) {
            writer.writeAttribute(HTML.style_ATTRIBUTE, style, "tabStyle");
        }
    }

    //TODO review
    public boolean getRendersChildren() {
        return true;
    }

    public void encodeCellClasses(FacesContext context, UITab tab) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String labelClass;
        if (tab.isDisabled()) {
            labelClass = TabPanelRendererBase.DISABLED_CELL_CLASSES;
        } else {
            if (tab.isActive()) {
                labelClass = TabPanelRendererBase.ACTIVE_CELL_CLASSES;
            } else {
                labelClass = TabPanelRendererBase.INACTIVE_CELL_CLASSES;
            }
        }

        writer.writeAttribute(HTML.class_ATTRIBUTE, labelClass, null);
    }

    public void writeLabel(FacesContext context, UITab tab) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        String labelClass = "";
        if (tab.isDisabled()) {
            labelClass = TabPanelRendererBase.getDisabledTabClass(tab);
        } else {
            if (tab.isActive()) {
                labelClass = TabPanelRendererBase.getActiveTabClass(tab);
            } else {
                labelClass = TabPanelRendererBase.getInactiveTabClass(tab);
            }

            writer.writeAttribute(HTML.onmouseover_ATTRIBUTE, ONMOUSEOVER, "tabOnMouseOver");
            writer.writeAttribute(HTML.onmouseout_ATTRIBUTE, ONMOUSEOUT, "tabOnMouseOut");
        }
        writer.writeAttribute(HTML.class_ATTRIBUTE, labelClass, "tabClass");
        String title = tab.getTitle();
        if (title != null && title.length() != 0) {
            writer.writeAttribute(HTML.title_ATTRIBUTE, title, null);
        }
        
        //TODO make "label" constant
        UIComponent facet = tab.getFacet("label");

        if (facet != null && facet.isRendered()) {
            renderChild(context, facet);
        } else {
            String label = tab.getLabel();

            if (label == null || label.length() == 0) {
        	//TODO to constant
        	label = "&#160;";
        	writer.write(label);
            }else{
        	writer.writeText(label,null);
            }

        }
        
    }

    protected String encodeTabLabelWidth(FacesContext context, UITab tab) {
        String labelWidth = tab.getLabelWidth();
        if (labelWidth == null || labelWidth.trim().length() == 0) {
            return "";
        }

        return "width: " + HtmlUtil.qualifySize(labelWidth) + ";";
    }

    public String encodeHeaderSpacing(FacesContext context, UITab tab) throws IOException {
        UITabPanel pane = tab.getPane();
        String headerSpacing = pane.getHeaderSpacing();
        return "width: " + HtmlUtil.qualifySize(headerSpacing) + "; ";
    }

    private static final String ONMOUSEOVER = "RichFaces.overTab(this);";
    private static final String ONMOUSEOUT = "RichFaces.outTab(this);";
}
