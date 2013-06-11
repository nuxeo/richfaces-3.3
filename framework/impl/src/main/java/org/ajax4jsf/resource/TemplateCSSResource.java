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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;


/**
 * Class for create and send resources from template ( at most, used for CSS files )
 * @author shura (latest modification by $Author: nick_belaevski $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/01/11 16:52:14 $
 *
 * @deprecated
 */
public class TemplateCSSResource implements InternetResource {
	 
	private String templateName;
	private InternetResource _resource;
	private static TemplateCSSRenderer templateCSSRenderer = new TemplateCSSRenderer();

	public TemplateCSSResource(){
		
	}
	/**
	 * @param templateName
	 */
	public TemplateCSSResource(String templateName) {
		super();
		setPath(templateName);
	}
	/**
	 * @param templateName
	 * @throws FacesException
	 */
	public void setPath(String templateName) throws FacesException {
		// remove leaded / - as need for ClassLoader.getResource call.
		this.templateName = templateName.startsWith("/")?templateName.substring(1):templateName;
		// Self-register resource in system.
		InternetResourceBuilder resourceBuilder = InternetResourceBuilder.getInstance();
		_resource = resourceBuilder.createResource(null,this.templateName);
		_resource.setRenderer(templateCSSRenderer);
		try {
			// Attempt to compile template at init time. Due to client-side caching,
			// without registering resources requests can be not call rendering template.
			templateCSSRenderer.getTemplate(_resource, null);
		} catch (Exception e) {
			// Do nothing - template will be compiled at rendering time.
			// TODO log error.
		}
	}

	/**
	 * @param context
	 * @param data
	 * @param attributes
	 * @throws IOException
	 * @see org.ajax4jsf.resource.InternetResource#encode(FacesContext, java.lang.Object, java.util.Map)
	 */
	public void encode(FacesContext context, Object data, Map<String, Object> attributes) throws IOException {
		_resource.encode(context, data, attributes);
	}

	/**
	 * @param context
	 * @param data
	 * @throws IOException
	 * @see org.ajax4jsf.resource.InternetResource#encode(FacesContext, java.lang.Object)
	 */
	public void encode(FacesContext context, Object data) throws IOException {
		_resource.encode(context, data);
	}

	/**
	 * @param context
	 * @param component
	 * @param attrs
	 * @throws IOException
	 * @see org.ajax4jsf.resource.InternetResource#encodeBegin(FacesContext, java.lang.Object, java.util.Map)
	 */
	public void encodeBegin(FacesContext context, Object component, Map<String, Object> attrs) throws IOException {
		_resource.encodeBegin(context, component, attrs);
	}

	/**
	 * @param context
	 * @param component
	 * @throws IOException
	 * @see org.ajax4jsf.resource.InternetResource#encodeEnd(FacesContext, java.lang.Object)
	 */
	public void encodeEnd(FacesContext context, Object component) throws IOException {
		_resource.encodeEnd(context, component);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getContentLength(ResourceContext)
	 */
	public int getContentLength(ResourceContext resourceContext) {
		return _resource.getContentLength(resourceContext);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getContentType(ResourceContext)
	 */
	public String getContentType(ResourceContext resourceContext) {
		return _resource.getContentType(resourceContext);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getExpired(ResourceContext)
	 */
	public long getExpired(ResourceContext resourceContext) {
		return _resource.getExpired(resourceContext);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getKey()
	 */
	public String getKey() {
		return _resource.getKey();
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getLastModified(ResourceContext)
	 */
	public Date getLastModified(ResourceContext resourceContext) {
		return _resource.getLastModified(resourceContext);
	}

	/**
	 * @param key
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getProperty(java.lang.Object)
	 */
	public Object getProperty(Object key) {
		return _resource.getProperty(key);
	}

	/**
	 * @param context
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getResourceAsStream(org.ajax4jsf.resource.ResourceContext)
	 */
	public InputStream getResourceAsStream(ResourceContext context) {
		return _resource.getResourceAsStream(context);
	}

	/**
	 * @param context
	 * @param data
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getUri(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public String getUri(FacesContext context, Object data) {
		return _resource.getUri(context, data);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#isCacheable(ResourceContext)
	 */
	public boolean isCacheable(ResourceContext resourceContext) {
		return _resource.isCacheable(resourceContext);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#isSessionAware()
	 */
	public boolean isSessionAware() {
		return _resource.isSessionAware();
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#requireFacesContext()
	 */
	public boolean requireFacesContext() {
		return _resource.requireFacesContext();
	}

	/**
	 * @param context
	 * @throws IOException
	 * @see org.ajax4jsf.resource.InternetResource#send(org.ajax4jsf.resource.ResourceContext)
	 */
	public void send(ResourceContext context) throws IOException {
		_resource.send(context);
	}

	/**
	 * @param response
	 * @see org.ajax4jsf.resource.InternetResource#sendHeaders(org.ajax4jsf.resource.ResourceContext)
	 */
	public void sendHeaders(ResourceContext response) {
		_resource.sendHeaders(response);
	}

	/**
	 * @param key
	 * @see org.ajax4jsf.resource.InternetResource#setKey(java.lang.String)
	 */
	public void setKey(String key) {
		_resource.setKey(key);
	}

	/**
	 * @param key
	 * @param value
	 * @see org.ajax4jsf.resource.InternetResource#setProperty(java.lang.Object, java.lang.Object)
	 */
	public void setProperty(Object key, Object value) {
		_resource.setProperty(key, value);
	}

	/**
	 * @param renderer
	 * @see org.ajax4jsf.resource.InternetResource#setRenderer(org.ajax4jsf.resource.ResourceRenderer)
	 */
	public void setRenderer(ResourceRenderer renderer) {
		_resource.setRenderer(renderer);
	}

	/**
	 * @return
	 * @see org.ajax4jsf.resource.InternetResource#getRenderer(ResourceContext)
	 */
	public ResourceRenderer getRenderer(ResourceContext resourceContext) {
		return _resource.getRenderer(null);
	}

	/**
	 * @param cacheable
	 * @see org.ajax4jsf.resource.InternetResource#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		_resource.setCacheable(cacheable);
	}

	/**
	 * @param expired
	 * @see org.ajax4jsf.resource.InternetResource#setExpired(long)
	 */
	public void setExpired(long expired) {
		_resource.setExpired(expired);
	}

	/**
	 * @param lastModified
	 * @see org.ajax4jsf.resource.InternetResource#setLastModified(java.util.Date)
	 */
	public void setLastModified(Date lastModified) {
		_resource.setLastModified(lastModified);
	}

	/**
	 * @param sessionAware
	 * @see org.ajax4jsf.resource.InternetResource#setSessionAware(boolean)
	 */
	public void setSessionAware(boolean sessionAware) {
		_resource.setSessionAware(sessionAware);
	}
}
