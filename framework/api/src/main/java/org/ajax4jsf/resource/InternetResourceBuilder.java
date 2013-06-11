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

package org.ajax4jsf.resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class InternetResourceBuilder {

    private static final Log log = LogFactory.getLog(InternetResourceBuilder.class);
	public static final String LOAD_NONE = "NONE";
	public static final String LOAD_ALL = "ALL";

	public static final String SKINNING_SCRIPT = "/org/richfaces/renderkit/html/scripts/skinning.js";

	public static final String COMMON_FRAMEWORK_SCRIPT = "/org/ajax4jsf/framework.pack.js";
	public static final String COMMON_UI_SCRIPT = "/org/richfaces/ui.pack.js";
	public static final String COMMON_STYLE_PREFIX = "/org/richfaces/skin";
	public static final String COMMON_STYLE_EXTENSION = ".xcss";
	public static final String COMMON_STYLE = COMMON_STYLE_PREFIX + COMMON_STYLE_EXTENSION;
	public static final String LOAD_STYLE_STRATEGY_PARAM = "org.richfaces.LoadStyleStrategy";
	public static final String LOAD_SCRIPT_STRATEGY_PARAM = "org.richfaces.LoadScriptStrategy";

	public static final String STD_CONTROLS_SKINNING_PARAM = "org.richfaces.CONTROL_SKINNING";
	public static final String STD_CONTROLS_SKINNING_CLASSES_PARAM = "org.richfaces.CONTROL_SKINNING_CLASSES";
	public static final String ENABLE = "enable";

	public static final String CONTROL_SKINNING_LEVEL = "org.richfaces.CONTROL_SKINNING_LEVEL";
	public static final String BASIC = "basic";
	public static final String EXTENDED = "extended";

	/**
         * Get application start time for check resources modification time.
         * 
         * @return application start time in msec's
         */
    public abstract long getStartTime();

    /**
         * @param cacheable
         * @param session
         * @param mime
         * @return
         * @throws FacesException
         */
    public abstract InternetResource createUserResource(boolean cacheable,
	    boolean session, String mime) throws FacesException;

    /**
         * @param key
         * @param resource
         */
    public abstract void addResource(String key, InternetResource resource);

    /**
         * @param path
         * @return
         * @throws ResourceNotFoundException
         */
    public abstract InternetResource getResource(String path)
	    throws ResourceNotFoundException;

    /**
         * @param key
         * @return
         */
    public abstract Object getResourceDataForKey(String key);

    /**
         * @param key
         * @return
         * @throws ResourceNotFoundException
         */
    public abstract InternetResource getResourceForKey(String key)
	    throws ResourceNotFoundException;

    /**
         * @param resource
         * @param context
         * @param storeData
         * @return
         */
    public abstract String getUri(InternetResource resource,
	    FacesContext context, Object storeData);

    /**
         * @param base
         * @param path
         * @return
         * @throws FacesException
         */
    public abstract InternetResource createResource(Object base, String path)
	    throws FacesException;

    /**
         * @throws ServletException
         */
    public abstract void init()
	    throws FacesException;

    /**
         * static instance variable.
         */
    private static Map<ClassLoader, InternetResourceBuilder> instances = 
    	Collections.synchronizedMap(new HashMap<ClassLoader, InternetResourceBuilder> ());

    /**
         * Get ( or create if nessesary ) instance of builder for current
         * loader. check content of file
         * META-INF/services/org.ajax4jsf.resource.InternetResourceBuilder
         * for name of class to instantiate, othrthise create
         * {@link ResourceBuilderImpl} instance.
         * 
         * @return current builder instance.
         */
    public static InternetResourceBuilder getInstance() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InternetResourceBuilder instance = (InternetResourceBuilder) instances
				.get(loader);
		if (null == instance) {
			try {
				// Default service implementation
				String serviceClassName = "org.ajax4jsf.resource.ResourceBuilderImpl";
				String resource = "META-INF/services/"
						+ InternetResourceBuilder.class.getName();
				InputStream in = URLToStreamHelper.urlToStreamSafe(
						loader.getResource(resource));
				if (null != in) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					serviceClassName = reader.readLine();
					reader.close();
					in.close();
				}
				Class<?> builderClass = loader.loadClass(serviceClassName);
				instance = (InternetResourceBuilder) builderClass.newInstance();
				if (log.isDebugEnabled()) {
					log.debug("Create instance of InternetBuilder from class "
							+ serviceClassName);
				}
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.error(
							"Can't create instance of InternetBuilder service",
							e);
					throw new FacesException(
							"Error on create instance of InternetBuilder service",
							e);
				}
				// TODO - detect default instance.
				// instance = new ResourceBuilderImpl();
			}
			instances.put(loader, instance);
		}
		if (log.isDebugEnabled()) {
			log.debug("Return instance of internet resource builder "
					+ instance.toString());
		}
		return instance;
	}

    /**
         * Package-wide method for reset instance in Junit tests.
         * 
         * @param instance
         */
    public static void setInstance(InternetResourceBuilder instance) {
	ClassLoader loader = Thread.currentThread().getContextClassLoader();
	instances.put(loader, instance);
    }

    public InternetResourceBuilder() {
	super();
    }

    public abstract ResourceRenderer getStyleRenderer();

    public abstract ResourceRenderer getScriptRenderer();
}