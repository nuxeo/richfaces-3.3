/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/model/Key.java,v $
 *      $Revision: 1.2 $ 
 */

package org.richfaces.demo.datagrid.model;

import java.io.Serializable;

/**
 * @author Maksim Kaszynski
 *
 */
public class Key implements Comparable<Key>, Serializable{
	private int id;
	private String value;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Key o) {
		return id - o.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Key) {
			return ((Key) obj).id == id;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
