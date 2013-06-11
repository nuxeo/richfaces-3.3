package org.richfaces.demo.tab;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class TabsBean {

	private boolean[] tabRendered = { true, true, true };
	private boolean tabsRendered = true;


	public boolean[] getTabRendered() {
		return tabRendered;
	}

	public void setTabRendered(boolean[] tabRendered) {
		this.tabRendered = tabRendered;
	}

	public boolean isTabsRendered() {
		return tabsRendered;
	}

	public void setTabsRendered(boolean tabsRendered) {
		this.tabsRendered = tabsRendered;
	}
	//TODO Need to resolve duplicate call
	public void deleteTab(ActionEvent event) {
		int tabIndex = Integer.parseInt(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("tabToDelete"));
		tabRendered[tabIndex] = false;
		for (int i = 0; i < tabRendered.length; i++) {
			tabsRendered = tabsRendered||tabRendered[i];
		}
	}
	
	public void resetTabs() {
		for (int i = 0; i < tabRendered.length; i++) {
			tabRendered[i]=true;
		}
	}

}
