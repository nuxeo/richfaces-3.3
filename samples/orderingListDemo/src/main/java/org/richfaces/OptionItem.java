package org.richfaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class OptionItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3639047543048763052L;
	private String name;
	private int price;

	public OptionItem() {
	}
	
	public OptionItem(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public String action() {
		String actionResult = "OptionItem.action(): " + name + ", " + price;
		System.out.println(actionResult);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actionResult", actionResult);
		
		return null;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + " [" + name + "] by " + price;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionItem other = (OptionItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		return true;
	}

}
