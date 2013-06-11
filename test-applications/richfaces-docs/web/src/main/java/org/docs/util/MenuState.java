package org.docs.util;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;


public class MenuState {
	
	
	private Map <String, Boolean> menu;
	 
	   private String selectedMenuItem;
	 
	   public String getSelectedMenuItem() {
		return selectedMenuItem;
	   }
	   public Map<String, Boolean> getMenu() {
		return menu;
	   }	
	   public void setMenu(Map<String, Boolean> menu) {
		this.menu = menu;
	   }
	   public void setSelectedMenuItem(String selectedMenuItem) {
		this.selectedMenuItem = selectedMenuItem;
	   }
	   public MenuState() {
	   }
	   @PostConstruct
	   public void init () {
		menu = new HashMap <String, Boolean>();
		menu.put("group1", false);
		menu.put("group2", false);
		menu.put("group3", false);
		menu.put("group4", false);
	   }

	

}
