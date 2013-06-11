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

package org.ajax4jsf.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Class to fill and handle widget parameters.
 * @author shura
 * 
 */
public class Parameters {

	private Map _params;


	public Parameters(String id) {
		// Parse parameters.
		String dataId = id + ":_data";
		Element dataElement = DOM.getElementById(dataId);
		_params = new HashMap();
		if (null != dataElement) {
			int childCount = DOM.getChildCount(dataElement);
			fillParamsMap(dataElement, _params, childCount);
		}
	}

	/**
	 * Fill parameters map by data from given element. If data element don't
	 * have child elements, got content as text, else - put new map with content
	 * of recursive same method.
	 * 
	 * @param dataElement
	 * @param params
	 * @param childCount
	 */
	private void fillParamsMap(Element dataElement, Map params, int childCount) {
		for (int it = 0; it < childCount; it++) {
			Element data = DOM.getChild(dataElement, it);
			String key = DOM.getAttribute(data, "title");
			if (null != key) {
				int dataChildCount = DOM.getChildCount(data);
				if (dataChildCount > 0) {
					// Put data from child elements as new map
					Map innerData = new HashMap(dataChildCount);
					fillParamsMap(data, innerData, dataChildCount);
					params.put(key, innerData);
				} else {
					// Simple text value
					String innerText = DOM.getInnerText(data);
					params.put(key, innerText);
				}
			}
		}
	}

	public String get(String key, String defaultValue) {
		String value = (String) _params.get(key);
		if (null == value) {
			value = defaultValue;
		}
		return value;
	}

	public Object get(String key){
		return _params.get(key);
	}
}
