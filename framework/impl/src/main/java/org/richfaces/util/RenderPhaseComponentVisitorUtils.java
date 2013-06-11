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
package org.richfaces.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.util.ServicesUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.event.RenderPhaseComponentVisitor;

public class RenderPhaseComponentVisitorUtils{
	
	private static final Log log = LogFactory.getLog(RenderPhaseComponentVisitorUtils.class);

	private static final String KEY = RenderPhaseComponentVisitorUtils.class.getName();

	private RenderPhaseComponentVisitorUtils() {}

	public static RenderPhaseComponentVisitor[] getVisitors(FacesContext context) {
		Map<String, Object> applicationMap = context.getExternalContext().getApplicationMap();
		RenderPhaseComponentVisitor[] visitors;
		synchronized(applicationMap) {
			visitors = (RenderPhaseComponentVisitor[])applicationMap.get(KEY);
			if (visitors == null) {
				visitors = init();
				applicationMap.put(KEY, visitors);
			}
		}
		return visitors;
	}

	private static RenderPhaseComponentVisitor[] init() {
		LinkedList<RenderPhaseComponentVisitor> list = new LinkedList<RenderPhaseComponentVisitor>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String resource = "META-INF/services/org.richfaces.component.RenderPhaseComponentVisitor";
		try {
			Enumeration<URL> resources = loader.getResources(resource);
			while (resources.hasMoreElements()) {
				InputStream stream = URLToStreamHelper.urlToStreamSafe(resources.nextElement());
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				try {
					String handlerClassName = null;
					while ((handlerClassName = reader.readLine()) != null) {
						if (handlerClassName.length() > 0) {
							try {
								Class<?> handlerClass = ServicesUtils.loadClass(
										loader, handlerClassName);
								Object handler = handlerClass.newInstance();
								list.add((RenderPhaseComponentVisitor) handler);
							} catch (Exception e) {
								throw new FacesException(
										"Error create instance for class "
												+ handlerClassName, e);
							}
						}
					}
					
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						if (log.isDebugEnabled()) {
							log.debug(e.getLocalizedMessage(), e);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new FacesException("Error load resource "+ resource, e);
		}
		return list.toArray(new RenderPhaseComponentVisitor[list.size()]);
	}
}
