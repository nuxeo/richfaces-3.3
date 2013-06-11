/**
 * 
 */
package org.ajax4jsf;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author asmirnov
 *
 */
@Name("menuAction") @Scope(ScopeType.SESSION)
public class MenuAction {
	
	private int _selectedTab=0;

	/**
	 * @return the selectedTab
	 */
	public int getSelectedTab() {
		return _selectedTab;
	}

	/**
	 * @param selectedTab the selectedTab to set
	 */
	public void setSelectedTab(int selectedTab) {
		_selectedTab = selectedTab;
	}
	
	

}
