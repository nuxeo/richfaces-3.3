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
package org.richfaces.ui.application;

import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.richfaces.ui.model.States;

/**
 * @author asmirnov
 *
 */
public class StateNavigationHandler extends NavigationHandler {
	
	private NavigationHandler parent;

	/**
	 * @param parent
	 */
	public StateNavigationHandler(NavigationHandler parent) {
		super();
		this.parent = parent;
	}

	/**
	 * @param context
	 * @param fromAction
	 * @param outcome
	 * @see javax.faces.application.NavigationHandler#handleNavigation(javax.faces.context.FacesContext, java.lang.String, java.lang.String)
	 */
	public void handleNavigation(FacesContext context, String fromAction,
			String outcome) {
		if(null != outcome){
			Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
			for (Object bean : requestMap.values()) {
				if (bean instanceof States) {
					States state = (States) bean;
					String navigation = state.getNavigation(outcome);
					if(null != navigation){
						state.setCurrentState(navigation);
					}
				}
			}
		}
		parent.handleNavigation(context, fromAction, outcome);
	}

}
