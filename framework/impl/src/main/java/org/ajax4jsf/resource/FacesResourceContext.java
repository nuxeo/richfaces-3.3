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
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.resource.ResourceContext;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: nick_belaevski $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/01/11 16:52:14 $
 * 
 */
public class FacesResourceContext extends ResourceContext {

    FacesContext facesContext;

    ExternalContext externalContext;

    HttpServletResponse response;

    /**
         * @param facesContext
         */
    public FacesResourceContext(FacesContext facesContext) {

	this.facesContext = facesContext;
	this.externalContext = facesContext.getExternalContext();
	Object facesResponse = externalContext.getResponse();
	if (facesResponse instanceof HttpServletResponse) {
	    this.response = (HttpServletResponse) facesResponse;
	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#setHeader(java.lang.String,
         *      java.lang.String)
         */
    public void setHeader(String name, String value) {
	if (null != response) {
	    response.setHeader(name, value);

	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#setIntHeader(java.lang.String,
         *      int)
         */
    public void setIntHeader(String name, int value) {
	if (null != response) {
	    response.setIntHeader(name, value);

	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#setDateHeader(java.lang.String,
         *      long)
         */
    public void setDateHeader(String name, long value) {
	if (null != response) {
	    response.setDateHeader(name, value);

	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#getOutputStream()
         */
    public OutputStream getOutputStream() throws IOException {
	if (null != response) {
	    return response.getOutputStream();

	} else {
	    // May be non-servlet (Portlet?) environment. Try to use reflection
		OutputStream stream = null;
		Object response = externalContext.getResponse();
		try {
		    Method gW = response.getClass()
			    .getMethod("getOutputStream", new Class[0]);
		    stream = (OutputStream) gW.invoke(response, new Object[0]);
		} catch (Exception e) {
		    throw new FacesException(e);
		}
		return stream;
	    
	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#getWriter()
         */
    public PrintWriter getWriter() throws IOException {
	if (null != response) {
	    return response.getWriter();

	} else {
	    // May be non-servlet (Portlet?) environment. Try to use reflection
		PrintWriter writer = null;
		Object response = externalContext.getResponse();
		try {
		    Method gW = response.getClass()
			    .getMethod("getWriter", new Class[0]);
		    writer = (PrintWriter) gW.invoke(response, new Object[0]);
		} catch (Exception e) {
		    throw new FacesException(e);
		}
		return writer;
	    
	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#getQueryString()
         */
    public String getQueryString() {
	return ((HttpServletRequest) externalContext.getRequest())
		.getQueryString();
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#getPathInfo()
         */
    public String getPathInfo() {
	return externalContext.getRequestPathInfo();
    }

    /*
         * (non-Javadoc)
         * 
         * @see org.ajax4jsf.resource.ResourceContext#getSessionAttribute(java.lang.String)
         */
    public Object getSessionAttribute(String name) {
	return externalContext.getSessionMap().get(name);
    }

    @Override
    public Object getContextAttribute(String name) {
    	return externalContext.getApplicationMap().get(name);
    }

    
    public InputStream getResourceAsStream(String path) {

	return externalContext.getResourceAsStream(path);
    }

    /**
	 * @param path
	 * @return
	 * @throws MalformedURLException
	 * @see javax.faces.context.ExternalContext#getResource(java.lang.String)
	 */
	public URL getResource(String path) throws MalformedURLException {
		return externalContext.getResource(path);
	}

	/**
	 * @param path
	 * @return
	 * @see javax.faces.context.ExternalContext#getResourcePaths(java.lang.String)
	 */
	public Set<String> getResourcePaths(String path) {
		return externalContext.getResourcePaths(path);
	}

	public String getRequestParameter(String data_parameter) {

	return (String) externalContext.getRequestParameterMap().get(
		data_parameter);
    }

    public void setContentType(String contentType) {
	response.setContentType(contentType);

    }
    

    public void setContentLength(int contentLength) {
    	response.setContentLength(contentLength);
    }

    public String getInitParameter(String name) {
	// TODO Auto-generated method stub
	return externalContext.getInitParameter(name);
    }

    public String getServletPath() {
	// TODO Auto-generated method stub
	return externalContext.getRequestServletPath();
    }

    public void release() {
	super.release();
	externalContext = null;
	response = null;
	facesContext.release();
    }

    // added by nick 11.01.2007 - getters for contexts added
    public FacesContext getFacesContext() {
	return facesContext;
    }

    public ExternalContext getExternalContext() {
	return externalContext;
    }
    // by nick
}
