/**
 * 
 */
package org.richfaces;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.richfaces.component.UITabPanel;

/**
 * @author asmirnov
 *
 */
public class TabNavigation {
	
	private UITabPanel tabPanel;

	/**
	 * @return the tabPanel
	 */
	public UITabPanel getTabPanel() {
		return tabPanel;
	}

	/**
	 * @param tabPanel the tabPanel to set
	 */
	public void setTabPanel(UITabPanel tabPanel) {
		this.tabPanel = tabPanel;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();
		if(requestParameterMap.containsKey("page")){
			tabPanel.setValue("page");
		} else if(requestParameterMap.containsKey("java")){
			tabPanel.setValue("java");
		} else if(requestParameterMap.containsKey("usage")){
			tabPanel.setValue("usage");
		}
	}

}
