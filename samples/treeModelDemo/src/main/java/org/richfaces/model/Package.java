/**
 * 
 */
package org.richfaces.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 25.07.2007
 *
 */
public class Package extends Entry {
	private Map classes = new LinkedHashMap(); 

	public Map getClasses() {
		return classes;
	}

	public void setClasses(Map classes) {
		this.classes = classes;
	}
	
	public void addClass(Class clazz) {
		this.classes.put(clazz.getName(), clazz);
		clazz.setParent(this);
	}

	@Override
	public void addEntry(Entry entry) {
		addClass((Class) entry);
	}

	@Override
	public void removeEntry(Entry entry) {
		classes.remove(entry.getName());
	}
}
