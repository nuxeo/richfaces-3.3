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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.util.HtmlDimensions;
import org.richfaces.component.UISwitchablePanel;
import org.richfaces.component.UITab;
import org.richfaces.component.UITabPanel;
import org.richfaces.component.util.HtmlUtil;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.SwitchablePanelSwitchEvent;


/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 *         created 12.01.2007
 */
public class TabPanelRendererBase extends org.ajax4jsf.renderkit.HeaderResourcesRendererBase {

    public final static String ACTIVE_CELL_CLASSES = "dr-tbpnl-tbcell-act rich-tabhdr-cell-active";
    public final static String INACTIVE_CELL_CLASSES = "dr-tbpnl-tbcell-inact rich-tabhdr-cell-inactive";
    public final static String DISABLED_CELL_CLASSES = "dr-tbpnl-tbcell-dsbld rich-tabhdr-cell-disabled";
    private final String TABS_WITH_SAME_NAMES_ERROR = "tabs with the same name not allowed";  

    protected Class getComponentClass() {
        return UITabPanel.class;
    }

    protected void doDecode(FacesContext context, UIComponent component) {
        super.doDecode(context, component);

        UITabPanel panel = (UITabPanel) component;

        String clientId = component.getClientId(context);
        Map requestParameterMap = context.getExternalContext().getRequestParameterMap();

        UITab eventTab = null;

        for (Iterator tabsIterator = panel.getRenderedTabs();
             tabsIterator.hasNext() && eventTab == null;) {

            UITab tab = (UITab) tabsIterator.next();
            if (tab.isDisabled()) {
                continue;
            }

            String tabClientId = tab.getClientId(context);
            if (null != requestParameterMap.get(tabClientId) ||
                    null != requestParameterMap.get(tabClientId + "_server_submit"))
            {

                eventTab = tab;
            }
        }

        if (eventTab != null) {
            new SwitchablePanelSwitchEvent(panel, null, eventTab).queue();
            new ActionEvent(eventTab).queue();

            if (UISwitchablePanel.AJAX_METHOD.equals(eventTab.getSwitchTypeOrDefault()))
            {
                new AjaxEvent(eventTab).queue();
            }
        } else {
            String newValue = (String) requestParameterMap.get(clientId);
            
            if (null != newValue) {
            		new SwitchablePanelSwitchEvent(panel, newValue, null).queue();
            }
        }
    }

  
    private static final TabInfoCollector collector = new TabInfoCollector() {
        private final JSReference JSR_ACTIVE_CLASS = new JSReference("activeClass");
        private final JSReference JSR_ID = new JSReference("id");
        private final JSReference JSR_INACTIVE_CLASS = new JSReference("inactiveClass");

        private final JSReference JSR_CELL_ACTIVE_CLASS = new JSReference("cellActiveClass");
        private final JSReference JSR_CELL_INACTIVE_CLASS = new JSReference("cellInactiveClass");

        private final JSReference JSR_NAME = new JSReference("name");
        private final JSReference JSR_ONTABLEAVE = new JSReference("ontableave");
        private final JSReference JSR_ONTABENTER = new JSReference("ontabenter");
        
        public Object collectTabInfo(FacesContext context, UITab tab) {
            Map info = new HashMap();
            info.put(JSR_ID, tab.getClientId(context));
            info.put(JSR_ACTIVE_CLASS, TabPanelRendererBase.getActiveTabClass(tab));
            info.put(JSR_INACTIVE_CLASS, TabPanelRendererBase.getInactiveTabClass(tab));

            info.put(JSR_CELL_ACTIVE_CLASS, ACTIVE_CELL_CLASSES);
            info.put(JSR_CELL_INACTIVE_CLASS, INACTIVE_CELL_CLASSES);

            info.put(JSR_NAME, tab.getName());
            info.put(JSR_ONTABLEAVE, tab.getAttributes().get("ontableave"));
            info.put(JSR_ONTABENTER, tab.getAttributes().get("ontabenter"));

            return info;
        }
    };

    private static final TabPanelInfoCollector panelInfoCollector = new TabPanelInfoCollector(){
    	
        private final JSReference JSR_ONTABCHANGE = new JSReference("ontabchange");
        private final JSReference JSR_ID = new JSReference("id");
           	
		public Object collectTabPanelInfo(FacesContext context, UITabPanel tabPanel) {
        	Map info = new HashMap();
            info.put(JSR_ONTABCHANGE, tabPanel.getAttributes().get("ontabchange"));
            info.put(JSR_ID, tabPanel.getClientId(context));
            
			return info;
		}
    	
    };
    
     /**
     * @param context
     * @param component
     * @return
     * @throws IOException
     */
    public static String encodeStyles(FacesContext context, UIComponent component) throws IOException {
        String widthAttrValue = (String) component.getAttributes().get(HTML.width_ATTRIBUTE);
        String heightAttrValue = (String) component.getAttributes().get(HTML.height_ATTRIBUTE);
        String styleAttrValue = (String) component.getAttributes().get(HTML.style_ATTRIBUTE);
        String style = styleAttrValue != null ? styleAttrValue : "";

        if (!parameterPresent(styleAttrValue, HTML.width_ATTRIBUTE)) {
            String width = (widthAttrValue != null && widthAttrValue.length() > 0) ? HtmlUtil.qualifySize(widthAttrValue) : "";
            style = addParameter(style, HTML.width_ATTRIBUTE + ":" + width);
        }

        if (!parameterPresent(styleAttrValue, HTML.height_ATTRIBUTE)) {
            String height = (heightAttrValue != null && heightAttrValue.length() > 0) ? HtmlUtil.qualifySize(heightAttrValue) : "";
            if (height.length() > 0) {
                style = addParameter(style, HTML.height_ATTRIBUTE + ":" + height);
            }
        }

        return style;
    }

    /**
     * @param style
     * @param name
     * @return
     */
    protected static boolean parameterPresent(String style, String name) {
        if (style != null && style.length() > 0) {
            String[] styles = style.split(";");
            for (int i = 0; i < styles.length; i++) {
                String[] pair = styles[i].split(":");
                if (pair[0].trim().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String addParameter(String style, String element) {
        String s = style.trim();
        return style + (s.length() == 0 || s.endsWith(";") ? "" : ";") + element;
    }

    protected String getValueAsString(FacesContext context, UITabPanel tabPanel) {
    	return getUtils().getValueAsString(context, tabPanel);
    }

    private Object checkValue(Object value) {
    	if (value instanceof String) {
			String s = (String) value;
			
			if (s.length() == 0) {
				return null;
			} else {
				return s;
			}
		}

    	return value;
    }
    
    protected static class TabsIteratorHelper {
    	private Iterator tabs;
    	
    	private UITab namedTab = null;
    	private UITab firstApplicableTab = null;
    	private boolean fallback;
    	
		public TabsIteratorHelper(Iterator tabs, Object name) {
			super();
			this.tabs = tabs;
			
			if (name != null) {
				//findTabByName
				while (tabs.hasNext()) {
					UITab tab = nextTab();
					if (name.equals(tab.getName())) {
						this.namedTab = tab;
						break;
					}
				}
			}
			
			if (namedTab == null) {
				if (name != null) {
	            	//tried but failed
					fallback = true;
				}
				
				this.namedTab = findAnyTab();
			} else if (namedTab.isDisabled()) {
	        	UITab tab = findAnyTab();
	        	if (this.namedTab != tab && tab != null && !tab.isDisabled()) {
	            	fallback = true;
	            	this.namedTab = tab;
	        	} else {
	        		//use disabled tab found by name
	        	}
			}
		}
		
		private UITab nextTab() {
			UITab tab = (UITab) tabs.next();
			
			if (firstApplicableTab == null) {
				firstApplicableTab = tab;
			} else if (firstApplicableTab.isDisabled() && !tab.isDisabled()) {
				//more appropriate
				firstApplicableTab = tab;
			}
			
			return tab;
		}
		
		public UITab getTab() {
			return this.namedTab;
		}
		
		public boolean isFallback() {
			return fallback;
		}
		
		private UITab findAnyTab() {
			while ((firstApplicableTab == null || firstApplicableTab.isDisabled()) && tabs.hasNext()) {
				nextTab();
			}

			return firstApplicableTab;
		}
    }
    
    
    protected void preEncodeBegin(FacesContext context, UIComponent component) throws IOException {
    	super.preEncodeBegin(context, component);
        if(component instanceof UITabPanel){
        	UITabPanel tabPanel = (UITabPanel)component;
            ComponentVariables componentVariables = ComponentsVariableResolver.getVariables(this, tabPanel);
        	componentVariables.setVariable("tabPanel", getTabPanelInfoCollector().collectTabPanelInfo(context, (UITabPanel)component));
    	}
    }
    
    public void encodeTabs(FacesContext context, UITabPanel tabPanel) throws IOException {
        ComponentVariables componentVariables = ComponentsVariableResolver.getVariables(this, tabPanel);

        tabPanel.setRenderedValue(null);
        
        Object checkedValue = checkValue(tabPanel.getValue());

        UITabPanel pane = tabPanel;
        UITab activeTab = null;

        TabsIteratorHelper helper = new TabsIteratorHelper(pane.getRenderedTabs(), checkedValue);
        activeTab = helper.getTab();

        if (activeTab == null) {
        	Object label = MessageUtil.getLabel(context, tabPanel);
        	String message = label + ": tab panel has no enabled or rendered tabs!";
        	context.getExternalContext().log(message);
        	return ;
        }

        if (helper.isFallback()) {
        	Object label = MessageUtil.getLabel(context, tabPanel);
        	String message = label + ": tab panel [@selectedTab=" + checkedValue + 
        			"] has no enabled or rendered tab with such name. Tab: " + activeTab.getName() + 
        			" will be used instead!";
        	context.getExternalContext().log(message);
        	tabPanel.setRenderedValue(activeTab.getName());
    	} else if (checkedValue == null) {
        	tabPanel.setRenderedValue(activeTab.getName());
    	} else {
    		tabPanel.setRenderedValue(null);
    	}
        
        helper = null;

        ArrayList tabs = new ArrayList();
        boolean clientSide = UISwitchablePanel.CLIENT_METHOD.equals(pane.getSwitchType());

        TabInfoCollector tabInfoCollector = getTabInfoCollector();
        
        Set<Object> tabNamesSet = new HashSet<Object>();
        
        for (Iterator iter = pane.getRenderedTabs(); iter.hasNext();) {
            UITab tab = (UITab) iter.next();
            boolean active = activeTab == tab;
            tab.setActive(active);

            if (!clientSide) {
                clientSide = UISwitchablePanel.CLIENT_METHOD.equals(tab.getSwitchTypeOrDefault());
            }
            
            if (!tabNamesSet.add(tab.getName())) {
        		throw new FacesException(TABS_WITH_SAME_NAMES_ERROR);
            }
                        
            tab.encodeTab(context, active);

            if (!tab.isDisabled()) {
                tabs.add(tabInfoCollector.collectTabInfo(context, tab));
            }
        }
        
        // Store flag for exist client-side tabs.
        componentVariables.setVariable("clientSide", new Boolean(clientSide));
        componentVariables.setVariable("tabs", tabs);
    }

    public static String getActiveTabCellClass(UITab tab) {
        return TabClassBuilder.activeTabClassBuilder.buildTabClass(tab);
    }

    public static String getDisabledTabCellClass(UITab tab) {
        return TabClassBuilder.disabledTabClassBuilder.buildTabClass(tab);
    }

    public static String getInactiveTabCellClass(UITab tab) {
        return TabClassBuilder.inactiveTabClassBuilder.buildTabClass(tab);
    }


    public static String getActiveTabClass(UITab tab) {
        return TabClassBuilder.activeTabClassBuilder.buildTabClass(tab);
    }

    public static String getDisabledTabClass(UITab tab) {
        return TabClassBuilder.disabledTabClassBuilder.buildTabClass(tab);
    }

    public static String getInactiveTabClass(UITab tab) {
        return TabClassBuilder.inactiveTabClassBuilder.buildTabClass(tab);
    }

    /**
     * Encode JavaScript function for switch tabs.
     *
     * @param context
     * @throws IOException
     */
    public void encodeTabsScript(FacesContext context, UITabPanel pane) throws IOException {
        ComponentVariables variables = ComponentsVariableResolver.getVariables(this, pane);
        ArrayList tabs = (ArrayList) variables.getVariable("tabs");
        // TODO - create tab control function.
        getUtils().writeScript(context, pane, "RichFaces.panelTabs['" + pane.getClientId(context) + "']=" + ScriptUtils.toScript(tabs) + ";");
    }
    
    public void encodeTabPanelScript(FacesContext context, UITabPanel pane) throws IOException {
        ComponentVariables variables = ComponentsVariableResolver.getVariables(this, pane);
        Object tabPanel = variables.getVariable("tabPanel");
        getUtils().writeScript(context, pane, "RichFaces.tabPanel['" + pane.getClientId(context) + "']=" + ScriptUtils.toScript(tabPanel) + ";");
    }

    protected TabInfoCollector getTabInfoCollector() {
        return collector;
    }
    
    protected TabPanelInfoCollector getTabPanelInfoCollector(){
    	return panelInfoCollector;
    }
    
    public String encodeHeaderSpacing(FacesContext context, UITabPanel pane) throws IOException {
        String headerSpacing = pane.getHeaderSpacing();
        Double decoded = HtmlDimensions.decode(headerSpacing);
        return "width: " + HtmlUtil.qualifySize(headerSpacing) + "; ";
    }
    
}
