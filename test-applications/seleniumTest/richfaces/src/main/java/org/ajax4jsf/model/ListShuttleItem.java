/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.model;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class ListShuttleItem  implements Serializable {

	private static final long serialVersionUID = -7540977992693127759L;

	private Integer numder;
	
	private String name;
	
	public ListShuttleItem() {
		
	}
	
	public ListShuttleItem(Integer i, String name) {
		this.numder = i;
		this.name = name;
	}

	/**
	 * @return the numder
	 */
	public Integer getNumder() {
		return numder;
	}

	/**
	 * @param numder the numder to set
	 */
	public void setNumder(Integer numder) {
		this.numder = numder;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ListShuttleItem)) {
			return false;
		}
		ListShuttleItem o = (ListShuttleItem)obj;
		if (!o.name.equals(name) || !o.numder.equals(numder)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode() + numder;
	}
	
	public String action() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actionResult", name);
		return null;
	}
}
