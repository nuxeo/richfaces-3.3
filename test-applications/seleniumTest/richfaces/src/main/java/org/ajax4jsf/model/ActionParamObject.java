/**
 * 
 */
package org.ajax4jsf.model;

/**
 * @author Andrey Markavtsov
 *
 */
public class ActionParamObject {
	String name;
	String date;
	
	public ActionParamObject(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActionParamObject)) {
			return false;
		}
		
		ActionParamObject o = (ActionParamObject)obj;
		return o.name.equals(this.name) && o.date.equals(this.date);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
