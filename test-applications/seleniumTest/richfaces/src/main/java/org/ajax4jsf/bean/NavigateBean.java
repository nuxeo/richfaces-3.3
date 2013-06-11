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
package org.ajax4jsf.bean;

import java.io.Serializable;

import org.ajax4jsf.component.UIInclude;

public class NavigateBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String viewId="/pages/hello.jsp";
	
	transient private UIInclude include;
	
	public String navigateOne() {
		
		return "sucessOne";
	}
	
	public String navigateTwo() {
		
		return "sucessTwo";
	}
	
	public String navigateHello() {
		
		return "sucessHello";
	}

	/**
	 * @return the viewId
	 */
	public String getViewId() {
		return viewId;
	}

	/**
	 * @param viewId the viewId to set
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	
	public String reset(){
		setViewId("/pages/hello.jsp");
		if(null != include){
			include.setViewId("/pages/hello.jsp");
		}
		return null;
	}

	/**
	 * @return the include
	 */
	public UIInclude getInclude() {
		return include;
	}

	/**
	 * @param include the include to set
	 */
	public void setInclude(UIInclude include) {
		this.include = include;
	}
	
}
