/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.config;

/**
 * @author shura
 *
 */
public class EventBean extends JsfBean {

	private String _listenerInterface;
	
	private String _sourceInterface;

	
	/**
	 * @return the listenerInterface
	 */
	public String getListenerInterface() {
		return this._listenerInterface;
	}

	/**
	 * @param listenerInterface the listenerInterface to set
	 */
	public void setListenerInterface(String listenerInterface) {
		this._listenerInterface = listenerInterface;
	}

	/**
	 * @return the sourceInterface
	 */
	public String getSourceInterface() {
		return this._sourceInterface;
	}

	/**
	 * @param sourceInterface the sourceInterface to set
	 */
	public void setSourceInterface(String sourceInterface) {
		this._sourceInterface = sourceInterface;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		// TODO Auto-generated method stub
		return getClassname().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return getClassname().equals(obj);
	}

	/**
	 * 
	 */
	public void checkProperties() {
		// TODO Auto-generated method stub
		
	}
}
