/**
 * 
 */
package org.richfaces.test.staging;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;


/**
 * @author asmirnov
 *
 */
abstract class StagingServletContext implements ServletContext {
	
	private static final Logger log = ServerLogger.SERVER.getLogger();

	public static final String CONTEXT_PATH = "";

	private static final String APPLICATION_NAME = "stub";
	private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	private final Map<String,String> initParameters = new HashMap<String, String>();

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getAttributeNames()
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		return Collections.enumeration(attributes.keySet());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getContext(java.lang.String)
	 */
	public ServletContext getContext(String uripath) {
		// stub server has only one context.
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getContextPath()
	 */
	public String getContextPath() {
		// Test always run in the root context.
		return CONTEXT_PATH;
	}

	public void addInitParameters(Map<String,String>parameters) {
		initParameters.putAll(parameters);
	}
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return initParameters.get(name);
	}

	
	/**
	 * Put new init parameter to the Map.
	 * @param name
	 * @param value
	 */
	public void setInitParameter(String name, String value) {
		initParameters.put(name, value);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getInitParameterNames()
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return Collections.enumeration(initParameters.keySet());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getMajorVersion()
	 */
	public int getMajorVersion() {
		return 2;
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getMinorVersion()
	 */
	public int getMinorVersion() {
		return 5;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
	 */
	public RequestDispatcher getNamedDispatcher(String name) {
		// TODO create stub dispatcher.
		log.info("unimplemented response method getNamedDispatcher");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
	 */
	public String getRealPath(String path) {
		// we always use 'virtual' configuration.
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
	 */
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO implement stub dispatcher.
		log.info("unimplemented response method getRequestDispatcher");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getResource(java.lang.String)
	 */
	public URL getResource(String path) throws MalformedURLException {
		URL url = null;
		ServerResource resource = getServerResource(path);
		if(null != resource){
			url = resource.getURL();
		}
		return url;
	}

	/**
	 * @param path
	 * @return
	 */
	protected abstract ServerResource getServerResource(String path);

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String path) {
		ServerResource resource = getServerResource(path);
		if(null != resource){
			try {
				return resource.getAsStream();
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Set getResourcePaths(String path) {
		HashSet result=null;
		ServerResource resource = getServerResource(path);
		if(null == resource && !path.endsWith("/")){
			path+="/";
			resource = getServerResource(path);
		}
		if(null != resource){
			Set<String> paths = resource.getPaths();
			if(null != paths && paths.size()>0){
				result = new HashSet(paths.size());
				for (String resourcePath : paths) {
					result.add(path+resourcePath);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getServerInfo()
	 */
	public String getServerInfo() {
		return "Stub test server";
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getServlet(java.lang.String)
	 */
	public Servlet getServlet(String name) throws ServletException {
		// always return null.
		log.info("unimplemented response method getServlet");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getServletContextName()
	 */
	public String getServletContextName() {
		// Stub server has no declared name.
		return APPLICATION_NAME;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getServletNames()
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServletNames() {
		log.info("unimplemented response method getServletNames");
		return Collections.enumeration(Collections.EMPTY_LIST);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#getServlets()
	 */
	@SuppressWarnings("unchecked")
	public Enumeration getServlets() {
		log.info("unimplemented response method getServlets");
		return Collections.enumeration(Collections.EMPTY_LIST);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#log(java.lang.String)
	 */
	public void log(String msg) {
		log.finest(msg);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
	 */
	public void log(Exception exception, String msg) {
		log.log(Level.FINEST, msg, exception);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
	 */
	public void log(String message, Throwable throwable) {
		log.log(Level.FINEST, message, throwable);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		// TODO - inform listeners
		Object removed = attributes.remove(name);
		if(null != removed){
			valueUnbound(new ServletContextAttributeEvent(this,name,removed));
		}
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String name, Object object) {
		// TODO - inform listeners
		if (null == object) {
			removeAttribute(name);
		} else {
			Object oldValue = attributes.put(name, object);
			ServletContextAttributeEvent event = new ServletContextAttributeEvent(this,name,object);
			if(null != oldValue){
				valueReplaced(event);
			} else {
				valueBound(event);
			}
		}
	}

	protected abstract void valueBound(ServletContextAttributeEvent event);

	protected abstract void valueReplaced(ServletContextAttributeEvent event);

	protected abstract void valueUnbound(
			ServletContextAttributeEvent servletContextAttributeEvent);

}
