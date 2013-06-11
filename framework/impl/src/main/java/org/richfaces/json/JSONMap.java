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

package org.richfaces.json;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 15.12.2006
 * 
 */
public class JSONMap extends AbstractMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2898468948832273123L;

	private JSONObject jsonObject;

	public JSONMap() throws JSONException {
		super();

		this.jsonObject = new JSONObject();
	}

	public JSONMap(String jsonString) throws JSONException {
		super();

		this.jsonObject = new JSONObject(jsonString);
	}

	public JSONMap(JSONObject object) {
		super();
		
		this.jsonObject = object;
	}

	public Set entrySet() {
		return new AbstractSet() {

			public Iterator iterator() {
				return new Iterator() {

					private Iterator keys = jsonObject.keys();
					private String currentName;

					public boolean hasNext() {
						return keys.hasNext();
					}

					public Object next() {
						currentName = (String) keys.next();

						return new Entry() {

							private String key = currentName;

							public Object getKey() {
								return key;
							}

							public Object getValue() {
								try {
									return JSONAccessor.getValue(jsonObject, this.key);
								} catch (JSONException e) {
									throw new RuntimeException(e.getMessage(), e);
								}
							}

							public Object setValue(Object value) {
								throw new UnsupportedOperationException();
							}

						};
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}

			public int size() {
				return jsonObject.length();
			}

			public boolean add(Object o) {
				Entry entry = (Entry) o;
				return JSONAccessor.putValue(jsonObject, (String) entry.getKey(), entry.getValue());
			}
		};
	}

	public Object put(Object key, Object value) {
		String keyString = key.toString();
		try {
			Object previousValue = JSONAccessor.getValue(jsonObject, keyString);
			JSONAccessor.putValue(jsonObject, keyString, value);

			return previousValue;
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public String getString() throws JSONException {
		return jsonObject.toString(0);
	}

	public String getString(int indentFactor) throws JSONException {
		return jsonObject.toString(indentFactor);
	}
	
//	public static void main(String[] args) throws Exception {
//		String json = "{ test1: 'test string', test2: [15, 45, 28], test3: { innerMap1: { prop1: '1', prop2: true }, innerProp1: [12, 2] } }";
//		JSONMap map = new JSONMap(json);
//		System.out.println(map.get("test1"));
//		System.out.println("next >>>>>>>>");
//		System.out.println(map.get("test2"));
//		System.out.println("next >>>>>>>>");
//		System.out.println(map.get("test3"));
//	}
}


