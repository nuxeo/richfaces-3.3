package org.docs.util;

import javax.faces.event.ActionEvent;



public class MenuBean {
 
   private MenuState menuState;
 
   public MenuState getMenuState() {
	return menuState;
   }
   public void setMenuState(MenuState menuState) {
	this.menuState = menuState;
   }
   public MenuBean() {}
 
   public void select (ActionEvent event) {
	menuState.setSelectedMenuItem(event.getComponent().getId());
   }
}