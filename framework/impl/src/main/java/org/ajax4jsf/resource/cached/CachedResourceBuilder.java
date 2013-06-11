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

package org.ajax4jsf.resource.cached;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.resource.ResourceNotFoundException;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is intended to generate predictable URIs for all resources handled by RichFaces. 
 * It creates mapping between resource key/data value and generated random string of known format 
 * for all resource requests. By default {@link UUID#toString()} is used. Mapping is maintained by LRU map
 * having default capacity of {@value #DEFAULT_CAPACITY} so be aware that stale entries can be removed and 
 * application users will get errors then. 
 * 
 * How to use: add to application classpath META-INF/services/org.ajax4jsf.resource.InternetResourceBuilder
 * file with the following content <code>org.ajax4jsf.resource.cached.CachedResourceBuilder</code>
 * 
 * Limitations:
 * 
 * <ol>
 * 	<li>Doesn't work in clustered environments</li>
 * 	<li>All resource URIs become invalid after server restart that can cause cache issues</li>
 * 	<li>
 * 		Diagnostic of resource loading errors becomes somewhat harder. Variant of code where random key 
 * 		is appended to resource name doesn't satisfy the requirement of no path depth &gt; 8 as requested 
 * 		by users (see <a href="https://jira.jboss.org/jira/browse/RF-3586">RF-3586</a> for more info)
 * 	</li>
 * </ol>
 * 
 * @author Alexander Smirnov
 * @author Nick Belaevski
 */
public class CachedResourceBuilder extends ResourceBuilderImpl {

	private static final Log log = LogFactory.getLog(CachedResourceBuilder.class);

	protected static final int DEFAULT_CAPACITY = 10000;

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private DualLRUMap cache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#decrypt(byte[])
	 */
	protected byte[] decrypt(byte[] data) {
		// dummy - data not send via internet.
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#encrypt(byte[])
	 */
	protected byte[] encrypt(byte[] data) {
		// dummy - data not send via internet.
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#getResourceDataForKey(java.lang.String)
	 */
	public Object getResourceDataForKey(String key) {
		ResourceBean bean = null;
		try {
			readWriteLock.readLock().lock();
			bean = (ResourceBean) cache.get(key);
		} finally {
			readWriteLock.readLock().unlock();
		}

		if (null == bean) {
			throw new ResourceNotFoundException("Resource for key " + key
					+ "not present in cache");
		}

		return bean.getData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#getResourceForKey(java.lang.String)
	 */
	public InternetResource getResourceForKey(String key)
	throws ResourceNotFoundException {
		ResourceBean bean = null;
		try {
			readWriteLock.readLock().lock();
			bean = (ResourceBean) cache.get(key);
		} finally {
			readWriteLock.readLock().unlock();
		}

		if (null == bean) {
			throw new ResourceNotFoundException("Resource for key " + key
					+ "not present in cache");
		}
		
		return super.getResourceForKey(bean.getKey());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#getUri(org.ajax4jsf.resource.InternetResource,
	 *      javax.faces.context.FacesContext, java.lang.Object)
	 */
	public String getUri(InternetResource resource, FacesContext facesContext,
			Object data) {
		ResourceBean bean;
		if (null == data) {
			bean = new ResourceBean(resource.getKey());
		} else {
			if (data instanceof byte[]) {
				// Special case for simple bytes array data.
				bean = new ResourceBytesDataBean(resource.getKey(),
						(byte[]) data);
			} else {
				bean = new ResourceDataBean(resource.getKey(), data);
			}
		}

		String key = null;
		
		try {
			readWriteLock.readLock().lock();
			
			key = (String) cache.getKey(bean);

			if (key != null) {
				// Refresh LRU
				cache.get(key);
			}
		} finally {
			readWriteLock.readLock().unlock();
		}

		if (key == null) {
			try {
				readWriteLock.writeLock().lock();

				key = (String) cache.getKey(bean);
				if (null == key) {
					key = createNextKey();
					while (cache.containsKey(key)) {
						key = createNextKey();
					}

					cache.put(key, bean);
				} else {
					// Refresh LRU
					cache.get(key);
				}
				
			} finally {
				readWriteLock.writeLock().unlock();
			}
		}
		
		boolean isGlobal = !resource.isSessionAware();

		String resourceURL = getFacesResourceURL(facesContext, key, isGlobal);
		if (!isGlobal) {
			resourceURL = facesContext.getExternalContext().encodeResourceURL(
					resourceURL);
		}
		if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.BUILD_RESOURCE_URI_INFO,
					resource.getKey(), resourceURL));
		}
		return resourceURL;// context.getExternalContext().encodeResourceURL(resourceURL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceBuilderImpl#init(javax.servlet.ServletContext,
	 *      java.lang.String)
	 */
	public void init() throws FacesException {
		super.init();
		
		Properties properties = getProperties("cache.properties");
		int capacity = getCapacity(properties);
		if (capacity <= 0) {
			capacity = DEFAULT_CAPACITY;
			log.info("Using default capacity: " + DEFAULT_CAPACITY);
		}
		
		cache = new DualLRUMap(capacity);
	}

	/**
	 * Get properties file from classpath
	 * 
	 * @param name
	 * @return
	 */
	protected Properties getProperties(String name) {
		Properties properties = new Properties();
		InputStream props = URLToStreamHelper.urlToStreamSafe(CachedResourceBuilder.class
				.getResource(name));
		if (null != props) {
			try {
				properties.load(props);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.warn(Messages.getMessage(Messages.READING_PROPERTIES_ERROR,
						name), e);
			} finally {
				try {
					props.close();
				} catch (IOException e) {
					// Can be ignored
				}
			}
		}
		return properties;

	}
	
	protected String createNextKey() {
		return UUID.randomUUID().toString();
	}

	protected int getCapacity(Properties properties) {
		// Create cache manager.
		int capacity = 0;
		String capacityString = properties.getProperty("cache.capacity");
		if (null != capacityString) {
			try {
				capacity = Integer.parseInt(capacityString);
			} catch (NumberFormatException e) {
				log.warn("Error parsing value of parameters cache capacity", e);
			}
		}
		
		return capacity;
	}
}
