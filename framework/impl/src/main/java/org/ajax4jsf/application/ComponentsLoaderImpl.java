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
package org.ajax4jsf.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

/**
 * @author asmirnov
 * 
 */
public class ComponentsLoaderImpl implements ComponentsLoader {

	private Map<String, Class<? extends UIComponent>> classes;

	private ClassLoader loader;

	public ComponentsLoaderImpl() {
		classes = new ConcurrentHashMap<String, Class<? extends UIComponent>>(
				64);
		loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ComponentsLoaderImpl.class.getClassLoader();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ajax4jsf.portlet.application.ComponentsLoader#createComponent(java
	 * .lang.String)
	 */
	public UIComponent createComponent(String type) {
		// Classes is a lazy Map, new object will be create on the fly.
		Class<? extends UIComponent> componentClass = classes.get(type);
		if(null == componentClass){
			try {
				componentClass = loader.loadClass(type).asSubclass(UIComponent.class);
				classes.put(type, componentClass);
			} catch (ClassNotFoundException e) {
				throw new FacesException("Can't load class " + type, e);
			}

		}
		try {
			return componentClass.newInstance();
		} catch (InstantiationException e) {
			throw new FacesException(
					"Error on create new instance of the component with class "
							+ type, e);
		} catch (IllegalAccessException e) {
			throw new FacesException(
					"IllegalAccess on attempt to create new instance of the component with class "
							+ type, e);
		}
	}

	ClassLoader getClassLoader() {
		return loader;
	}
}
