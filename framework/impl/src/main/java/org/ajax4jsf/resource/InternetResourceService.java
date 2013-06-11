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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.Messages;
import org.ajax4jsf.cache.Cache;
import org.ajax4jsf.cache.CacheConfigurationLoader;
import org.ajax4jsf.cache.CacheException;
import org.ajax4jsf.cache.CacheFactory;
import org.ajax4jsf.cache.CacheLoader;
import org.ajax4jsf.cache.CacheManager;
import org.ajax4jsf.cache.ServletContextInitMap;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.webapp.BaseFilter;
import org.ajax4jsf.webapp.CacheContent;
import org.ajax4jsf.webapp.WebXml;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InternetResourceService implements CacheLoader,
		CacheConfigurationLoader {
	private static final Log log = LogFactory
			.getLog(InternetResourceService.class);

	static final String ENABLE_CACHING_PARAMETER = "enable-cache";

	private static final String RESOURCE_LIFECYCLE_PARAMETER = "org.ajax4jsf.RESOURCE_LIFECYCLE";

	private FilterConfig filterConfig;

	private boolean cacheEnabled = true;

	private Cache cache = null;

	// private ServletCacheAdministrator cacheAdmin;

	private FacesContextFactory contextFactory;

	// private RenderKitFactory renderKitFactory;
	private String lifecycleClass;

	private ResourceLifecycle lifecycle;

	private InternetResourceBuilder resourceBuilder;

	private WebXml webXml;

	public InternetResourceService() {
	}

	public void setCacheEnabled(boolean b) {
		cacheEnabled = b;
	}

	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
		ServletContext servletContext = config.getServletContext();
		if ("false".equalsIgnoreCase(config
				.getInitParameter(ENABLE_CACHING_PARAMETER))) {
			setCacheEnabled(false);
			// this.cacheEnabled = false;
			// this.cacheAdmin = null;
		} else {
			// this.cacheAdmin = ServletCacheAdministrator.getInstance(
			// servletContext, cacheProperties);
			try {
				CacheManager cacheManager = CacheManager.getInstance();
				Map<String, String> env = new ServletContextInitMap(servletContext);
				CacheFactory cacheFactory = cacheManager.getCacheFactory(env);
				this.cache = cacheFactory.createCache(env, this, this);
			} catch (CacheException e) {
				throw new FacesException(e.getMessage(), e);
			}
		}
		// Create Resource-specific Faces Lifecycle instance.
		lifecycleClass = servletContext
				.getInitParameter(RESOURCE_LIFECYCLE_PARAMETER);
		if (lifecycleClass != null) {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			try {
				Class<?> clazz = classLoader.loadClass(lifecycleClass);
				lifecycle = (ResourceLifecycle) clazz.newInstance();
			} catch (Exception e) {
				throw new FacesException(
						"Error create instance of resource Lifecycle "
								+ lifecycleClass, e);
			}
		} else {
			lifecycle = new ResourceLifecycle();
		}
		webXml = new WebXml();
		webXml.init(servletContext, filterConfig.getFilterName());
		if (log.isDebugEnabled()) {
			log.debug("Resources service initialized");
		}
	}

	public boolean serviceResource(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		String resourceKey = webXml.getFacesResourceKey(httpServletRequest);
		if (null != resourceKey) {
			serviceResource(resourceKey, httpServletRequest,
					httpServletResponse);
			return true;
		}
		return false;
	}

	public void serviceResource(String resourceKey, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InternetResource resource;// getInternetResource(request);
		try {
			resource = getResourceBuilder().getResourceForKey(resourceKey);
		} catch (ResourceNotFoundException e) {
			throw new ServletException(e);
		}
		Object resourceDataForKey = getResourceBuilder().getResourceDataForKey(
				resourceKey);

		ResourceContext resourceContext = getResourceContext(resource, request,
				response);
		resourceContext.setResourceData(resourceDataForKey);
		try {

			if (resource.isCacheable(resourceContext) && this.cacheEnabled) {
				// Test for client request modification time.
				try {
					long ifModifiedSince = request
							.getDateHeader("If-Modified-Since");
					if (ifModifiedSince >= 0) {
						// Test for modification. 1000 ms due to round
						// modification
						// time to seconds.
						long lastModified = resource.getLastModified(
								resourceContext).getTime() - 1000;
						if (lastModified <= ifModifiedSince) {
							response.setStatus(304);
							return;
						}
					}
				} catch (IllegalArgumentException e) {
					log
							.warn(
									Messages
											.getMessage(Messages.PARSING_IF_MODIFIED_SINCE_WARNING),
									e);
				}
				String cacheKey = resourceKey;
				CachedResourceContext cachedResourceContext = new CachedResourceContext(
						resourceContext);

				CacheContext cacheLoaderContext = new CacheContext(
						cachedResourceContext, resource);

				try {
					CacheContent content = (CacheContent) cache.get(cacheKey,
							cacheLoaderContext);
					if (log.isDebugEnabled()) {
						log
								.debug(Messages.getMessage(
										Messages.GET_CONTENT_FROM_CACHE_INFO,
										cacheKey));
					}
					content.sendHeaders(response);
					// Correct expires date for resource.
					long expired = resource.getExpired(resourceContext);
					if (expired < 0 ) {
						expired = InternetResource.DEFAULT_EXPIRE;
					} 
						response.setDateHeader("Expires", System
								.currentTimeMillis()
								+ expired);
//						response.addHeader("Cache-control", "max-age="
//								+ (expired / 1000L));
					if (!request.getMethod().equals("HEAD")) {
						content.send(response);
					}
					content.flush(response);
				} catch (CacheException e) {
					log.error(
							Messages.getMessage(Messages.SEND_RESOURCE_ERROR),
							e);
					throw new ServletException(Messages.getMessage(
							Messages.SEND_RESOURCE_ERROR_2, e.getMessage()), e);
				}
			} else {
				getLifecycle().send(resourceContext, resource);
				// sendResource(resource, request, response,
				// resourceDataForKey);
			}

		} finally {
			resourceContext.release();
		}
	}

	/**
	 * @param resource
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws FacesException
	 */
	protected ResourceContext getResourceContext(InternetResource resource,
			HttpServletRequest request, HttpServletResponse response)
			throws FacesException {
		FacesContext facesContext = null;
		ResourceContext resourceContext;
		if (resource.requireFacesContext()) {
			facesContext = getFacesContext(request, response);
			resourceContext = new FacesResourceContext(facesContext);
		} else {
			resourceContext = new ServletResourceContext(getServletContext(),
					request, response);
		}
		return resourceContext;
	}

	/**
	 * Get properties file from classpath
	 * 
	 * @param name
	 * @return
	 */
	protected Properties getProperties(String name) {
		Properties properties = new Properties();
		InputStream props = URLToStreamHelper.urlToStreamSafe(BaseFilter.class.getResource(name));
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

	/**
	 * @return Returns the servletContext.
	 */
	protected ServletContext getServletContext() {
		return filterConfig.getServletContext();
	}

	/**
	 * @return the lifecycle
	 * @throws ServletException
	 */
	protected ResourceLifecycle getLifecycle() throws FacesException {
		return lifecycle;
	}

	/**
	 * @return the contextFactory
	 */
	protected synchronized FacesContextFactory getContextFactory() {
		if (contextFactory == null) {
			contextFactory = (FacesContextFactory) FactoryFinder
					.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		}

		return contextFactory;
	}

	protected FacesContext getFacesContext(ServletRequest request,
			ServletResponse response) throws FacesException {
		return getContextFactory().getFacesContext(getServletContext(),
				request, response, getLifecycle());
	}

	/**
	 * @return the resourceBuilder
	 */
	protected InternetResourceBuilder getResourceBuilder() {
		if (resourceBuilder == null) {
			// Create resource builder for this filter.
			resourceBuilder = InternetResourceBuilder.getInstance();
		}
		return resourceBuilder;
	}

	public Object load(Object key, Object context) throws CacheException {
		CacheContext cacheKey = (CacheContext) context;
		CachedResourceContext resourceContext = cacheKey.getResourceContext();
		try {
			getLifecycle().send(resourceContext, cacheKey.getResource());
		} catch (IOException e) {
			throw new CacheException(e.getMessage(), e);
		}
		// TODO - set refresh interval ?
		// cache.put(cacheKey, resourceContext.getContent());
		return resourceContext.getContent();
	}

	public Properties loadProperties(String name) {
		return getProperties(name);
	}

}
