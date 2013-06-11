/**
 * 
 */
package org.richfaces;

import java.io.Serializable;


/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 16.11.2007
 *
 */
public class ListShuttleOptionItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8460586062149160189L;
	private String name;
	private int price;

	public ListShuttleOptionItem() {
	}
	
	public ListShuttleOptionItem(String name, int price) {
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

	public String toString() {
		return this.getClass().getSimpleName() + " [" + name + "] by " + price;
	}

	public void action() {
		System.out.println("ListShuttleOptionItem.action() " + this.toString());
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
		ListShuttleOptionItem other = (ListShuttleOptionItem) obj;
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
