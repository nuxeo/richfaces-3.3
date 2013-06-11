/**
 * 
 */
package org.richfaces.model;


/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 29.07.2007
 *
 */
public abstract class Entry {
	private String name;
	private Entry parent;
	
	public String getPath() {
		StringBuffer result = new StringBuffer();
		
		Entry parent = getParent();
		if (parent != null) {
			result.append(parent.getPath());
			result.append(" -> ");
		}

		result.append(this.toString());
		
		return result.toString();
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "[" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entry getParent() {
		return parent;
	}

	public void setParent(Entry parent) {
		this.parent = parent;
	}
	
	public void click() {
		System.out.println("Entry.click() " + getPath());
	}
	
	public abstract void addEntry(Entry entry);

	public abstract void removeEntry(Entry entry);
}
