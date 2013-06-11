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
package org.richfaces.ui.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asmirnov
 *
 */
public class StateImpl extends HashMap<String, Object> implements State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5521648998682443502L;
	
	private Map<String,String>navigations = new HashMap<String, String>();



	/* (non-Javadoc)
	 * @see org.richfaces.ui.model.State#getNavigation(java.lang.String)
	 */
	public String getNavigation(String outcome) {
		// TODO Auto-generated method stub
		return navigations.get(outcome);
	}


	public void setNavigation(String outcome, String navigation) {
		navigations.put(outcome, navigation);
		
	}


}
