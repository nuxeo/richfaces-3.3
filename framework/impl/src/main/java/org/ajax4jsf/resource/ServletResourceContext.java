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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.resource.ResourceContext;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:01 $
 *
 */
public class ServletResourceContext extends ResourceContext {
	
	private ServletContext context;
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	/**
	 * @param request
	 * @param response
	 */
	public ServletResourceContext(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		this.context = context;
		this.request = request;
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#setHeader(java.lang.String, java.lang.String)
	 */
	public void setHeader(String name, String value) {
		response.setHeader(name,value);

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#setIntHeader(java.lang.String, int)
	 */
	public void setIntHeader(String name, int value) {
		response.setIntHeader(name,value);

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#setDateHeader(java.lang.String, long)
	 */
	public void setDateHeader(String name, long value) {
		response.setDateHeader(name,value);

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return response.getOutputStream();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#getQueryString()
	 */
	public String getQueryString() {
		return request.getQueryString();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#getPathInfo()
	 */
	public String getPathInfo() {
		return request.getPathInfo();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#getSessionAttribute(java.lang.String)
	 */
	public Object getSessionAttribute(String name) {
		return request.getSession(false).getAttribute(name);
	}

	/**
	 * @param name
	 * @return
	 * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
	 */
	public Object getContextAttribute(String name) {
		return context.getAttribute(name);
	}

	/**
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 * @see javax.servlet.ServletContext#getResource(java.lang.String)
	 */
	public URL getResource(String path) throws MalformedURLException {
		return context.getResource(path);
	}

	/**
	 * @param path
	 * @return
	 * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
    public Set getResourcePaths(String path) {
		return context.getResourcePaths(path);
	}

	public InputStream getResourceAsStream(String path) {
		// TODO Auto-generated method stub
		return context.getResourceAsStream(path);
	}

	public String getRequestParameter(String data_parameter) {
		// TODO Auto-generated method stub
		return request.getParameter(data_parameter);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.ResourceContext#getWriter()
	 */
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return response.getWriter();
	}

	public void setContentType(String contentType) {
		response.setContentType(contentType);		
	}

	public void setContentLength(int contentLength) {
		response.setContentLength(contentLength);
	}
	
	public String getInitParameter(String name) {
		// TODO Auto-generated method stub
		return context.getInitParameter(name);
	}

	public String getServletPath() {
		// TODO Auto-generated method stub
		return request.getServletPath();
	}

}
