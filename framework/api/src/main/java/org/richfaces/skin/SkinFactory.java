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

package org.richfaces.skin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base factory class ( implement Singleton design pattern ). Produce self
 * instance to build current skin configuration. At present, realised as lazy
 * creation factory. TODO - select point to initialize.
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:43 $
 * 
 */
public abstract class SkinFactory {

	/**
	 * static instance variable.
	 */
	private static Map<ClassLoader, SkinFactory> instances = 
		Collections.synchronizedMap(new HashMap<ClassLoader, SkinFactory>());

	private static final Log log = LogFactory.getLog(SkinFactory.class);


	public static void reset() {
		instances = Collections.synchronizedMap(new HashMap<ClassLoader, SkinFactory>());
	}

	/**
	 * Initialize skin factory. TODO - make call from init() method of any
	 * servlet or custom faces element method ??? If exist resource
	 * META-INF/services/org.richfaces.skin.SkinFactory , create
	 * instance of class by name from first line of this file. If such class
	 * have constructor with {@link SkinFactory} parameter, instantiate it with
	 * instance of default factory ( as usual in JSF ). If any error occurs in
	 * instantiate custom factory, return default.
	 */
	public static final SkinFactory getInstance() {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    SkinFactory instance = (SkinFactory) instances.get(loader);
			if (instance == null) {
				// Pluggable factories.
				InputStream input = null; // loader.getResourceAsStream(SERVICE_RESOURCE);
				input = URLToStreamHelper.urlToStreamSafe(
						loader.getResource(SERVICE_RESOURCE));
				// have services file.
				if (input != null) {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(input));
						String factoryClassName = reader.readLine();
						if (log.isDebugEnabled()) {
							log.debug(Messages.getMessage(
									Messages.SET_SKIN_FACTORY_INFO,
									factoryClassName));
						}
						instance = instantiateSkinFactory(factoryClassName, loader);						
					} catch (Exception e) {
						log
								.warn(
										Messages
												.getMessage(Messages.CREATING_SKIN_FACTORY_ERROR),
										e);
					} finally {
						try {
							input.close();
						} catch (IOException e) {
							// can be ignored
						}

					}
				}
				if (instance == null) {				    
				    // instantiate default implementation of SkinFactory - org.richfaces.skin.SkinFactoryImpl,
				    // placed in the richfaces-impl.jar
				    instance = instantiateSkinFactory(DEFAULT_SKIN_FACTORY_IMPL_CLASS, loader);				    
				}
			instances.put(loader, instance);	
			}

		return instance;
	}
	
	/**
	 * Create new instance of class with given name with the help of given <code>ClassLoader</code>.
	 * Instantiated class should extend <code>SkinFactory</code> base class.
	 * @param factoryClassName - class name of SkinFactory
	 * @param classLoader - class loader
	 * @return - instantiated <code>SkinFactory</code>
	 * @throws FacesException - FacesException is thrown when instantiation fails;
	 *                          causing exception is wrapped into <code>FacesException</code>  
	 */
	private static SkinFactory instantiateSkinFactory(String factoryClassName, ClassLoader classLoader) throws FacesException {
	    SkinFactory instance = null;
	    try {
		Class<?> clazz = Class.forName(factoryClassName, false, classLoader);
		try {
		    // try construct factory chain.
		    Constructor<?> factoryConstructor = clazz.getConstructor(new Class[] { SkinFactory.class });
		    instance = (SkinFactory) factoryConstructor.newInstance(new Object[] { instance });
		} catch (NoSuchMethodException e) {
		    // no chain constructor - attempt default.
		    instance = (SkinFactory) clazz.newInstance();
		}
	    } catch (InvocationTargetException ite) {
		log.error(Messages.getMessage(Messages.CREATING_SKIN_FACTORY_ERROR), ite);
		throw new FacesException("Exception when creating instance of [" + SkinFactory.class.getName() + "]", ite);
	    } catch (InstantiationException ie) {
		log.error(Messages.getMessage(Messages.CREATING_SKIN_FACTORY_ERROR), ie);
		throw new FacesException("Exception when creating instance of [" + SkinFactory.class.getName() + "]", ie);
	    } catch (IllegalAccessException iae) {
		log.error(Messages.getMessage(Messages.CREATING_SKIN_FACTORY_ERROR), iae);
		throw new FacesException("Exception when creating instance of [" + SkinFactory.class.getName() + "]", iae);
	    } catch (ClassNotFoundException cnfe) {
		log.error(Messages.getMessage(Messages.CREATING_SKIN_FACTORY_ERROR), cnfe);
		throw new FacesException("Exception when creating instance of [" + SkinFactory.class.getName() + "]", cnfe);
	    }

	    return instance;
	}

	/**
	 * Get default {@link Skin} implementation.
	 * 
	 * @param context
	 * @return
	 */
	public abstract Skin getDefaultSkin(FacesContext context);
	
	/**
	 * Get current {@link Skin} implementation.
	 * @param context
	 * @return
	 */
	public abstract Skin getSkin(FacesContext context);
	
//	public abstract SkinConfiguration getSkinConfiguration(FacesContext context);
	
	
	
	/**
	 * Resource Uri for file with name of class for application-wide SkinFactory same as SPI definitions for common Java SAX, Jsf etc. factories
	 */
	public static final String SERVICE_RESOURCE = "META-INF/services/" + SkinFactory.class.getName();

	/**
	 * Name of web application init parameter for current skin . Can be simple
	 * String for non-modified name, or EL-expression for calculate current
	 * skin. If EL evaluated to <code>String</code> - used as skin name, if to
	 * instance of {@link Skin } - used this instance. by default -
	 * "org.exadel.chameleon.SKIN"
	 */
	public static final String SKIN_PARAMETER = "org.richfaces.SKIN";

	public static final String BASE_SKIN_PARAMETER = "org.richfaces.BASE_SKIN";
	
	/**
	 * Full class name of default implementation of the SkinFactory class 
	 */
	public static final String DEFAULT_SKIN_FACTORY_IMPL_CLASS = "org.richfaces.skin.SkinFactoryImpl";

	/**
	 * Get base {@link Skin} implementation
	 * @param facesContext
	 * @return
	 */
	public abstract Skin getBaseSkin(FacesContext facesContext);
	
	/**
	 * @param facesContext
	 * @param name
	 * @return
	 */
	public abstract Theme getTheme(FacesContext facesContext, String name);

}
