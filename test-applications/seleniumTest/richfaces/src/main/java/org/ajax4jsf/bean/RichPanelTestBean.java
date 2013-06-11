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

package org.ajax4jsf.bean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class RichPanelTestBean {
    private String value;

    private int value2;

    private Object panelValue;

    private Object panelValue2;

    private String itemAction;

    private boolean rendered;

    private boolean immediate;

    private String content;

    private String switchType = "server";

    private String selectedTab = "tab1";

    private boolean disabled;

    private Map<String, String> inputs = new HashMap<String, String>();

    private String selectedChild;
    
    private String opened;
    
    private String ajaxOpened;
    
    private String clientOpened;
    
    private String serverOpened;
    
    public RichPanelTestBean() {
        value = "";
        value2 = 0;
        rendered = true;
        content = "content";
        immediate = false;
        selectedChild = null;
        opened = "false";
        ajaxOpened = "false";
        clientOpened = "false";
        serverOpened = "false";
    }

    public void reset() {
        value = "";
        value2 = 0;
        rendered = true;
        content = "content";
        selectedTab = "tab1";
        immediate = false;
        disabled = false;
        inputs.clear();
    }

    public void initAjaxCoreTest() {
        selectedTab = "tab1";
    }

    public void reset(ActionEvent event) {
        reset();
    }

    public void resetAndSelectTab() {
        String tab = getSelectedTab();
        reset();
        setSelectedTab(tab);
    }

    /**
     * Gets value of switchType field.
     * 
     * @return value of switchType field
     */
    public String getSwitchType() {
        return switchType;
    }

    /**
     * Set a new value for switchType field.
     * 
     * @param switchType
     *                a new value for switchType field
     */
    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public Object getPanelValue() {
        return panelValue;
    }

    public void setPanelValue(Object panelValue) {
        this.panelValue = panelValue;
    }

    public Object getPanelValue2() {
        return panelValue2;
    }

    public void setPanelValue2(Object panelValue2) {
        this.panelValue2 = panelValue2;
    }

    /**
     * Gets value of content field.
     * 
     * @return value of content field
     */
    public String getContent() {
        return content;
    }

    /**
     * Set a new value for content field.
     * 
     * @param content
     *                a new value for content field
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets value of itemAction field.
     * 
     * @return value of itemAction field
     */
    public String getItemAction() {
        return itemAction;
    }

    /**
     * Set a new value for itemAction field.
     * 
     * @param itemAction
     *                a new value for itemAction field
     */
    public void setItemAction(String itemAction) {
        this.itemAction = itemAction;
    }

    /**
     * Gets value of rendered field.
     * 
     * @return value of rendered field
     */
    public boolean isRendered() {
        return rendered;
    }

    /**
     * Set a new value for rendered field.
     * 
     * @param rendered
     *                a new value for rendered field
     */
    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public void actionListener(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "a", "b"));
        this.value = event.getComponent().getId();
    }

    public void itemActionServer() {
        setItemAction("server");
    }

    public void itemActionAjax() {
        setItemAction("ajax");
    }

    public void itemActionNone() {
        setItemAction("NOT none");
    }

    public String initImmediateTest() {
        reset();
        setImmediate(true);
        return null;
    }

    public String initAjaxSingleTest() {
        reset();
        inputs = new HashMap<String, String>();
        return null;
    }

    public String increment() {
        if (4 == this.value2) {
            this.value2 = 0;
        }
        this.value2++;
        return null;
    }

    public String action1() {
        this.value2 = 1;
        return null;
    }

    public String action2() {
        this.value2 = 2;
        return null;
    }

    public void hide() {
        rendered = false;
    }

    public void reset2Server() {
        cleanValues();
        content = "";
        switchType = "server";
        opened = "false";
    }

    public void reset2Ajax() {
        cleanValues();
        content = "";
        switchType = "ajax";
        opened = "false";
    }

    public void reset2Client() {
        cleanValues();
        content = "";
        switchType = "client";
        opened = "false";
    }

    public void cleanValues() {
        value = "";
        value2 = 0;
        panelValue = null;
        panelValue2 = null;
        itemAction = "";
        rendered = true;
        content = "content";
        immediate = false;
        selectedChild = null;
        ajaxOpened = "false";
        clientOpened = "false";
        serverOpened = "false";
    }

    /**
     * @return the selectedTab
     */
    public String getSelectedTab() {
        return selectedTab;
    }

    /**
     * @param selectedTab
     *                the selectedTab to set
     */
    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    /**
     * @return the inputs
     */
    public Map<String, String> getInputs() {
        return inputs;
    }

    /**
     * @param inputs
     *                the inputs to set
     */
    public void setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }

    /**
     * @return the immediate
     */
    public boolean isImmediate() {
        return immediate;
    }

    /**
     * @param immediate
     *                the immediate to set
     */
    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disabled
     *                the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}

	public String getSelectedChild() {
		return selectedChild;
	}

	public void setOpened(String opened) {
		this.opened = opened;
	}

	public String getOpened() {
		return opened;
	}

	public void setAjaxOpened(String ajaxOpened) {
		this.ajaxOpened = ajaxOpened;
	}

	public String getAjaxOpened() {
		return ajaxOpened;
	}

	public void setClientOpened(String clientOpened) {
		this.clientOpened = clientOpened;
	}

	public String getClientOpened() {
		return clientOpened;
	}

	public void setServerOpened(String serverOpened) {
		this.serverOpened = serverOpened;
	}

	public String getServerOpened() {
		return serverOpened;
	}

}
