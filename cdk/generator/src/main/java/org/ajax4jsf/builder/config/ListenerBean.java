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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 03.12.2006
 * 
 */
public class ListenerBean extends ComponentBaseBean {
	private String listenerclass;
	private String componentclass;
	private String eventclass;
	
	private List<ComponentBean> suitableComponents = new ArrayList<ComponentBean>();
	
	public String getComponentclass() {
		return componentclass;
	}
	public void setComponentclass(String componentclass) {
		this.componentclass = componentclass;
	}
	public String getEventclass() {
		return eventclass;
	}
	public void setEventclass(String eventclass) {
		this.eventclass = eventclass;
	}
	public String getListenerclass() {
		return listenerclass;
	}
	public void setListenerclass(String listenerclass) {
		this.listenerclass = listenerclass;
	}

	public void addSuitableComponent(ComponentBean componentBean) {
		suitableComponents.add(componentBean);
	}
	public List<ComponentBean> getSuitableComponents() {
		return suitableComponents;
	}
	
	public String getMethodname() {
		String name = getName();
		
		if (name == null || name.length() == 0) { 
		    return "add" + name; 
        }

		return "add" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
