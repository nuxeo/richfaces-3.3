/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class IndexBean {

    private List<ComponentItem> list = new ArrayList<ComponentItem>();

    public class ComponentItem {
        String name;

        String link;

        public ComponentItem(String name, String link) {
            this.name = name;
            this.link = link;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the link
         */
        public String getLink() {
            return link;
        }

    };

    private void sortList() {
        String[] names = new String[list.size()];
        Map<String, ComponentItem> map = new HashMap<String, ComponentItem>();
        for (int i = 0; i < list.size(); i++) {
            names[i] = list.get(i).name;
            map.put(list.get(i).name, list.get(i));
        }
        Arrays.sort(names);
        list = new ArrayList<ComponentItem>();
        for (String name : names) {
            list.add(map.get(name));
        }
    }

    public IndexBean() {
        list.add(new ComponentItem("<a4j:commandButton>", "ajaxCommandButton/ajaxButtonTest.xhtml"));
        list.add(new ComponentItem("<a4j:commandLink>", "ajaxCommandLink/ajaxLinkTest.xhtml"));
        list.add(new ComponentItem("<a4j:htmlCommandLink>", "ajaxHTMLCommandLink/ajaxHTMLLinkTest.xhtml"));
        list.add(new ComponentItem("<a4j:poll>", "ajaxPoll/ajaxPollTest.xhtml"));
        list.add(new ComponentItem("<a4j:region>", "ajaxRegion/ajaxRegionTest.xhtml"));
        list.add(new ComponentItem("<rich:calendar>", "calendar/calendarTest.xhtml"));
        list.add(new ComponentItem("<rich:combobox>", "comboBox/comboBoxTest.xhtml"));
        list.add(new ComponentItem("<rich:contextMenu>", "contextMenu/contextMenu.xhtml"));
        list.add(new ComponentItem("<rich:dataTable>", "dataTable/dataTableTest.xhtml"));
        list.add(new ComponentItem("<rich:dropDownMenu>", "dropDownMenu/dropDownMenuTest.xhtml"));
        list.add(new ComponentItem("<rich:inplaceSelect>", "inplaceSelect/inplaceSelectTest.xhtml"));
        list.add(new ComponentItem("<rich:inputNumberSpinner>", "inputNumberSpinner/inputNumberSpinnerTest.xhtml"));
        list.add(new ComponentItem("<a4j:keepAlive>", "keepAlive/keepAliveTest.xhtml"));
        list.add(new ComponentItem("<a4j:loadScript>", "loadScript/loadScriptTest.xhtml"));
        list.add(new ComponentItem("<a4j:loadStyle>", "loadStyle/loadStyleTest.xhtml"));
        list.add(new ComponentItem("<rich:orderingList>", "orderingList/orderingListTest.xhtml"));
        list.add(new ComponentItem("<rich:panelBar>", "panelBar/panelBarTest.xhtml"));
        list.add(new ComponentItem("<rich:panelMenu>", "panelMenu/panelMenuTest.xhtml"));
        list.add(new ComponentItem("<rich:pickList>", "pickList/pickListTest.xhtml"));
        list.add(new ComponentItem("<rich:progressBar>", "progressBar/progressBarTest.xhtml"));
        list.add(new ComponentItem("<rich:simpleTogglePanel>", "simpleTogglePanel/simpleTogglePanel.xhtml"));
        list.add(new ComponentItem("<rich:tabPanel>", "tabPanel/tabPanelTest.xhtml"));
        list.add(new ComponentItem("<rich:togglePanel>", "togglePanel/togglePanelTest.xhtml"));
        list.add(new ComponentItem("<a4j:actionParam>", "actionParam/actionParam.xhtml"));
        list.add(new ComponentItem("<a4j:form>", "ajaxForm/ajaxFormTest.xhtml"));
        list.add(new ComponentItem("<a4j:ajaxListener>", "ajaxListener/ajaxListenerTest.xhtml"));
        list.add(new ComponentItem("<a4j:outputPanel>", "ajaxOutputPanel/ajaxOutputPanelTest.xhtml"));
        list.add(new ComponentItem("<a4j:support>", "ajaxSupport/ajaxSupport.xhtml"));
        list.add(new ComponentItem("<a4j:jsFunction>", "jsFunction/jsFunctionTest.xhtml"));
        list.add(new ComponentItem("<rich:spacer>", "spacer/spacerTest.xhtml"));
        list.add(new ComponentItem("<a4j:loadBundle>", "loadBundle/loadBundle.xhtml"));
        list.add(new ComponentItem("<rich:toolTip>", "toolTip/toolTip.xhtml"));
        list.add(new ComponentItem("<rich:listShuttle>", "listShuttle/listShuttleTest.xhtml"));
        list.add(new ComponentItem("<a4j:include>", "ajaxInclude/ajaxIncludeTest.xhtml"));
        list.add(new ComponentItem("<a4j:log>", "ajaxLog/ajaxLogTest.xhtml"));
        list.add(new ComponentItem("<a4j:mediaOutput>", "ajaxMediaOutput/ajaxMediaOutputTest.xhtml"));
        list.add(new ComponentItem("<a4j:push>", "ajaxPush/ajaxPushTest.xhtml"));
        list.add(new ComponentItem("<a4j:repeat>", "ajaxRepeat/ajaxRepeatTest.xhtml"));
        list.add(new ComponentItem("<a4j:status>", "ajaxStatus/ajaxStatusTest.xhtml"));
        list.add(new ComponentItem("<rich:columns>", "columns/columnsTest.xhtml"));
        list.add(new ComponentItem("<rich:dataFilterSlider>", "dataFilterSlider/dataFilterSliderTest.xhtml"));
        list.add(new ComponentItem("<rich:inplaceInput>", "inplaceInput/inplaceInputTest.xhtml"));
        list.add(new ComponentItem("<rich:inputNumberSlider>", "inputNumberSlider/inputNumberSliderTest.xhtml"));
        list.add(new ComponentItem("<rich:modalPanel>", "modalPanel/modalPanelTest.xhtml"));
        list.add(new ComponentItem("<rich:toolBar>", "toolBar/toolBarTest.xhtml"));
        list.add(new ComponentItem("<rich:datascroller>", "dataScroller/dataScroller.xhtml"));
        list.add(new ComponentItem("<rich:dragSupport>", "dnd/dndTest.xhtml"));
        list.add(new ComponentItem("<rich:dropSupport>", "dnd/dndTest.xhtml"));
        list.add(new ComponentItem("<rich:effect>", "effect/effectTest.xhtml"));
        list.add(new ComponentItem("<rich:graphValidator>", "graphValidator/graphValidatorTest.xhtml"));
        list.add(new ComponentItem("<rich:panel>", "panel/panelTest.xhtml"));
        list.add(new ComponentItem("<rich:colorPicker>", "colorPicker/colorPickerTest.xhtml"));
        
        sortList();

    }

    /**
     * @return the list
     */
    public List<ComponentItem> getList() {
        return list;
    }

    public List<String> getLinks() {
    	List<String> links = null; 
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	ExternalContext externalContext = facesContext.getExternalContext();
    	String pathString = "/pages/";
    	String link = externalContext.getRequestParameterMap().get("link");
    	if (link != null) {
    		pathString += link;
    	}
    	Set<String> paths = externalContext.getResourcePaths(pathString);
    	if (paths != null) {
    		links = new ArrayList<String>(paths.size());
    		for (String path : paths) {
    			String[] names = path.split("/");
    			String name = names[names.length - 1];
    			if (name.endsWith(".xhtml")) {
    				links.add(link + "/" + name);
    			} else if (name.matches("[a-zA-Z0-9]+")) {
    				links.add(name);
    			}
			}
        	Collections.sort(links);
    	}
        return links;
    }
}
