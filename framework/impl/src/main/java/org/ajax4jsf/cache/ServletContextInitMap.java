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

package org.ajax4jsf.cache;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 02.05.2007
 * 
 */
public class ServletContextInitMap extends AbstractMap<String, String> {
	private ServletContext servletContext;
	
	public ServletContextInitMap(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
	}

	public Set<Map.Entry<String, String>> entrySet() {
		return new AbstractSet<Map.Entry<String, String>>() {

			public Iterator<Map.Entry<String, String>> iterator() {
				return new Iterator<Map.Entry<String, String>>() {
					
					@SuppressWarnings("unchecked")
					private Enumeration<String> initNames = servletContext.getInitParameterNames();
					
					public boolean hasNext() {
						return initNames.hasMoreElements();
					}

					public Map.Entry<String, String> next() {
						String key = (String) initNames.nextElement();
						String value = servletContext.getInitParameter(key);
					
						return new ServletContextInitMapEntry<String,String>(key, value);
					}

					public void remove() {
						throw new UnsupportedOperationException("This map is read-only");
					}
				
				};
			}

			@SuppressWarnings("unchecked")
			public int size() {
				int result = 0;
				Enumeration<String> initNames = servletContext.getInitParameterNames();
				while (initNames.hasMoreElements()) {
					initNames.nextElement();
					result++;
				}

				return result;
			}
			
			public boolean isEmpty() {
				return !servletContext.getInitParameterNames().hasMoreElements();
			}
		};
	}
	
	public String remove(Object key) {
		throw new UnsupportedOperationException("This map is read-only");
	}

	public String put(String key, String value) {
		throw new UnsupportedOperationException("This map is read-only");
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		throw new UnsupportedOperationException("This map is read-only");
	}

}

class ServletContextInitMapEntry<K, V> implements Map.Entry<K, V> {

	private K key;
	private V value;
	
	public ServletContextInitMapEntry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		throw new UnsupportedOperationException("This map is read-only");
	}
	
}