/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;

/**
 * JSF component class
 *
 */
public abstract class UITogglePanel extends UISwitchablePanel {
	
    /**
     * <p>The standard component family for this component.</p>
     */
	public static final String COMPONENT_FAMILY = "org.richfaces.TogglePanel";
    public static final String COMPONENT_TYPE = "org.richfaces.TogglePanel";
    
    public Object getValue() {
    	return super.getValue();
    }
    
    public void setValue(Object value) {
    	super.setValue(value);
    }
    
    public abstract void setInitialState(String  initialState);
    public abstract String getInitialState();
    public abstract void setStateOrder(String  stateOrder);
    public abstract String getStateOrder();
    public abstract String getSwitchType();
    public abstract void setSwitchType(String  switchType);

	public Object convertSwitchValue(UIComponent toggleControlComponent, Object object) {
		if (toggleControlComponent instanceof UIToggleControl) {
			UIToggleControl toggleControl = (UIToggleControl) toggleControlComponent;
			
			String stateName = toggleControl.getSwitchToState();

			List<String> stateOrderList = getStateOrderList();
			
			if (stateName != null) {
				return stateName;
			} else {
				//switch to next state
				Object value = getValue();
				int currentIdx = 0;
				if (value != null) {
					currentIdx = stateOrderList.indexOf(value);
					if (currentIdx + 1 == stateOrderList.size()) {
						return stateOrderList.get(0);
					} else {
						return stateOrderList.get(++currentIdx);
					}					
				} else {
					return stateOrderList.get(0);
				}
			}
		} else {
			return super.convertSwitchValue(toggleControlComponent, object);
		}
	}

	public List<String> getStateOrderList() {
		List<String> list = new ArrayList<String>();
		String order = getStateOrder();
		if (order != null) {
			StringTokenizer st = new StringTokenizer(order, ";,", false);
			while(st.hasMoreTokens()) {
				String name = st.nextToken().trim();
				list.add(name);
			}
		}
		return list;
	}
}
