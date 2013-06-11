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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSLiteral;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.richfaces.component.UIDatascroller;
import org.richfaces.event.DataScrollerEvent;


public class DataScrollerRenderer extends HeaderResourcesRendererBase {

    protected Class getComponentClass() {
        return UIDatascroller.class;
    }
    
    public void doDecode(FacesContext context, UIComponent component) {
    	Map paramMap = getParamMap(context);
    	String clientId = component.getClientId(context);
    	String param = (String) paramMap.get(clientId);
    	if (param != null) {
    		UIDatascroller scroller = (UIDatascroller) component;
    		int newPage = scroller.getPageForFacet(param);
    		int page = scroller.getPage();
    		if (newPage != 0 && newPage != page) {
    			DataScrollerEvent event = new DataScrollerEvent(scroller,
    					String.valueOf(page), param, newPage);
    			event.queue();

    		}
    		
            if (AjaxRendererUtils.isAjaxRequest(context)) {
                AjaxContext.getCurrentInstance(context)
                    .addAreasToProcessFromComponent(context, component);
            }
            
    		new AjaxEvent(component).queue();
    	}
    }

    public ControlsState getControlsState(FacesContext context,
            UIDatascroller datascroller, int pageIndex, int pageCount) {
        int minPageIdx = 1;
        int maxPageIdx = pageCount;
        int fastStep = datascroller.getFastStep();
        if (fastStep <= 1) {
            fastStep = 1;
        }

        boolean useFirst = true;
        boolean useLast = true;

        boolean useBackFast = true;
        boolean useForwFast = true;

        ControlsState controlsState = new ControlsState();

        if (pageIndex <= minPageIdx) {
            useFirst = false;
        }

        if (pageIndex >= maxPageIdx) {
            useLast = false;
        }

        if (pageIndex - fastStep < minPageIdx) {
            useBackFast = false;
        }

        if (pageIndex + fastStep > maxPageIdx) {
            useForwFast = false;
        }

        boolean isAuto;
        String boundaryControls = datascroller.getBoundaryControls();
        String stepControls = datascroller.getStepControls();
        String fastControls = datascroller.getFastControls();

        if ((isAuto = "auto".equals(boundaryControls)) || "show".equals(
                boundaryControls)) {
            if (isAuto) {
                controlsState.setFirstRendered(useFirst);
                controlsState.setLastRendered(useLast);
            }

            controlsState.setFirstEnabled(useFirst);
            controlsState.setLastEnabled(useLast);
        } else {
            controlsState.setFirstRendered(false);
            controlsState.setLastRendered(false);
        }

        if ((isAuto = "auto".equals(stepControls)) || "show".equals(
                stepControls)) {
            if (isAuto) {
                controlsState.setPreviousRendered(useFirst);
                controlsState.setNextRendered(useLast);
            }

            controlsState.setPreviousEnabled(useFirst);
            controlsState.setNextEnabled(useLast);
        } else {
            controlsState.setPreviousRendered(false);
            controlsState.setNextRendered(false);
        }

        if ((isAuto = "auto".equals(fastControls)) || "show".equals(
                fastControls)) {
            if (isAuto) {
                controlsState.setFastForwardRendered(useForwFast);
                controlsState.setFastRewindRendered(useBackFast);
            }

            controlsState.setFastForwardEnabled(useForwFast);
            controlsState.setFastRewindEnabled(useBackFast);
        } else {
            controlsState.setFastForwardRendered(false);
            controlsState.setFastRewindRendered(false);
        }

        UIComponent controlsSeparatorFacet = datascroller.getFacet("controlsSeparator");
        if (controlsSeparatorFacet != null && controlsSeparatorFacet.isRendered()) {
        	controlsState.setControlsSeparatorRendered(true);
        }
        
        return controlsState;
    }

    public void renderPager(FacesContext context, UIComponent component, int pageIndex, int count)
            throws IOException {
        ResponseWriter out = context.getResponseWriter();
        UIDatascroller scroller = (UIDatascroller) component;
        int currentPage = pageIndex;

        int maxPages = scroller.getMaxPages();
        if (maxPages <= 1) {
            maxPages = 1;
        }
        
        int pageCount = count;
        if (pageCount <= 1) {
            return;
        }

        int delta = maxPages / 2;

        int pages;
        int start;
        if (pageCount > maxPages && currentPage > delta) {
            pages = maxPages;
            start = currentPage - pages / 2 - 1;
            if (start + pages > pageCount) {
                start = pageCount - pages;
            }
        } else {
            pages = pageCount < maxPages ? pageCount : maxPages;
            start = 0;
        }

        for (int i = start, size = start + pages; i < size; i++) {

            boolean isCurrentPage = (i + 1 == currentPage);
            String styleClass;
            String style;
			if (isCurrentPage) {
			    styleClass = scroller.getSelectedStyleClass();
			    style = scroller.getSelectedStyle();
			} else {
				styleClass = scroller.getInactiveStyleClass();
			    style = scroller.getInactiveStyle();
			}
			if (styleClass==null){
			    styleClass="";
			}

            out.startElement("td", component);
            
            if (isCurrentPage) {
                out.writeAttribute("class", "dr-dscr-act rich-datascr-act "+
                		styleClass, null);
            } else {
                out.writeAttribute("class", "dr-dscr-inact rich-datascr-inact "+
                		styleClass, null);
                out.writeAttribute("onclick", getOnClick(Integer.toString(i + 1)), null);
            }
            if (null != style) out.writeAttribute("style", style, null);
            out.writeText(Integer.toString(i + 1), null);
            //renderChild(context, link);
            out.endElement("td");
        }

    }

    public Object getOnClick(String string) {
	return "Event.fire(this, 'rich:datascroller:onscroll', {'page': '" + string + "'});";
    }

    public void renderPages(FacesContext context, UIComponent component, int pageIndex, int count)
            throws IOException {
        UIDatascroller scroller = (UIDatascroller) component;
        int currentPage = pageIndex;

        int pageCount = count;
        if (pageCount <= 1) {
            pageCount = 1;
        }
        String varName = (String) scroller.getAttributes().get("pageIndexVar");
        if (varName != null && varName.length() > 0) {
            context.getExternalContext()
                    .getRequestMap().put(varName, new Integer(currentPage));
        }
        varName = (String) scroller.getAttributes().get("pagesVar");
        if (varName != null && varName.length() > 0) {
            context.getExternalContext()
                    .getRequestMap().put(varName, new Integer(pageCount));
        }
    }

    private Map getParamMap(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap();
    }

    //get UIParameter's Map
    protected Map getParameters(FacesContext context, UIComponent component){
    	Map parameters = new HashMap(); 
    	
    	if(component instanceof UIDatascroller){
    		UIDatascroller datascroller = (UIDatascroller)component;
    		List children = datascroller.getChildren();
    		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
				UIComponent child = (UIComponent) iterator.next();
				if(child instanceof UIParameter) {
					UIParameter param = (UIParameter)child;
					String name = param.getName();
					if (name != null) {
						parameters.put(name, param.getValue());
					}
				}
			}
    	}
    	    	
    	return parameters;
    }
    
    public String getSubmitFunction(FacesContext context, UIComponent component) {
	JSFunctionDefinition definition = new JSFunctionDefinition("event");
        
	JSFunction function = AjaxRendererUtils.buildAjaxFunction(component,
                context);
        Map eventOptions = AjaxRendererUtils.buildEventOptions(context,
                component);
        Map parameters = (Map) eventOptions.get("parameters");
        
        Map params = getParameters(context,component);
        if(!params.isEmpty()){
        	parameters.putAll(params);
        }
        
        parameters.put(component.getClientId(context), new JSLiteral("event.memo.page"));
        
        function.addParameter(eventOptions);
        StringBuffer buffer = new StringBuffer();
        function.appendScript(buffer);
        buffer.append("; return false;");

	String onPageChange = (String) component.getAttributes().get("onpagechange");
	if (onPageChange != null && onPageChange.length() != 0) {
	    JSFunctionDefinition onPageChangeDef = new JSFunctionDefinition("event");
	    onPageChangeDef.addToBody(onPageChange);
	    onPageChangeDef.addToBody("; return true;");

	    definition.addToBody("if (");
	    definition.addToBody(onPageChangeDef.toScript());
	    definition.addToBody("(event)) {");
	    definition.addToBody(buffer.toString());
	    definition.addToBody("}");
	} else {
	    definition.addToBody(buffer.toString());
	}
        
        return definition.toScript();
    }


    /** Creates HtmlAjaxCommandLink sets its id, value and reRender and UIParameter */
    public boolean getRendersChildren() {
        return true;
    }
}
