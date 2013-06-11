/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/model/Status.java,v $
 *      $Revision: 1.1 $ 
 */

package org.richfaces.demo.datagrid.model;

/**
 * @author Maksim Kaszynski
 *
 */
public class Status {
	private String name;
	private int id;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Status) {
			Status status = (Status) obj;
			return status.id == id;
		}
		return false;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id;
	}
	
}
