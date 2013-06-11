package org.richfaces.regressionarea.issues.rf2644;

public class Item {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void action() {
		System.out.println("Item.action() " + name);
	}
}
