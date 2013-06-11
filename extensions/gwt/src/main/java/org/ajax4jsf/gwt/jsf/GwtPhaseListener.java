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
package org.ajax4jsf.gwt.jsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ajax4jsf.gwt.client.ComponentEntryPoint;
import org.ajax4jsf.gwt.server.GwtFacesServlet;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handle GWT requests during the RESTORE_VIEW phase.  Requests coming into the
 * FacesServlet that begin with "gwt/" will cause straight classpath resource retrieval
 * ending with responseComplete (in the beforePhase method).  Requests coming in to a
 * GWT service endpoint URL will get delegated to the appropriate GWT service servlet
 * (in the afterPhase method).
 *
 * @author shura
 */
public class GwtPhaseListener implements PhaseListener {
	
	private static final Logger _log = Logger.getLogger(GwtPhaseListener.class.getName());
	
	public static final String GWT_RESOURCE_PREFIX = "gwt/";

	private static final class WebModuleConfigHandler extends DefaultHandler {
		private  String _servletClassName = null;

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String, java.lang.String)
		 */
		public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
			// Dummy resolver - disable all entity references
			return new InputSource(new StringReader(""));
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if ("servlet".equals(qName)) {
				_servletClassName = attributes.getValue("class");
			}
		}

		/**
		 * @return Returns the servletClassName.
		 */
		String getServletClassName() {
			return _servletClassName;
		}
	}

	private static final int BUFFER_SIZE = 1024;

	static protected final long DEFAULT_EXPIRE = 1000L * 60L * 60L * 12L;// 12
																			// Hours

	/**
	 * Map contain gwt service servlet instances for different modules.
	 */
	private Map serviceServlets = Collections.synchronizedMap(new HashMap());

	private Servlet _defaultServiceServlet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {
		// Check for request type - in case of google RPC call, forward to
		// service servlet.
		// headerMap must contain x-gwtcallingmodule='module name'
		// content-type=text/plain; charset=utf-8
		FacesContext facesContext = event.getFacesContext();
		ExternalContext externalContext = facesContext.getExternalContext();
		String requestContentType = externalContext.getRequestContentType();
		Map requestParameterMap = externalContext.getRequestParameterMap();
		if (null != requestContentType
				&& requestContentType.contains("text/plain")) {
			// GWT module name. Due to differences in browsers and containers, compare with ignore case.
			String module = (String) requestParameterMap.get(ComponentEntryPoint.GWT_MODULE_NAME_PARAMETER);
			Servlet servletForModule;
			if(null != module){
					servletForModule = getServletForModule(facesContext, module);
			} else {
					servletForModule = getDefaultServiceServlet(facesContext);
			}
					try {
						servletForModule.service((ServletRequest) externalContext
								.getRequest(), (ServletResponse) externalContext
								.getResponse());
					} catch (ServletException e) {
						_log.severe("Servlet exception in GWT service method "+e.getMessage());
						throw new FacesException(
								"Servlet exception in GWT service method", e);
					} catch (IOException e) {
						_log.severe("IO exception in GWT service method "+e.getMessage());
						throw new FacesException("IO exception in GWT service method",
								e);
					}
					facesContext.responseComplete();
		}
		// Map requestParameterMap = externalContext.getRequestParameterMap();
	}

	/**
	 * Get GWT RPC service servlet instance for module
	 * 
	 * @param context
	 * @param moduleName
	 */
	private Servlet getServletForModule(FacesContext context, String moduleName) {
		// First - check for existing instances
		if (serviceServlets.containsKey(moduleName)) {
			if (_log.isLoggable(Level.FINE)) {
				_log.fine("Found existing servlet for module "+moduleName);
			}
			return (Servlet) serviceServlets.get(moduleName);
		}
		// Second -attempt to read module config and, if in contain servlet
		// definition -
		// create instance and store in Map
		String servletClass = getServletClassForModule(moduleName);
		if(null != servletClass){
			Servlet servlet = createServlet(context,servletClass);
			serviceServlets.put(moduleName,servlet);
			if (_log.isLoggable(Level.FINE)) {
				_log.fine("Create new servlet for module "+moduleName+" with class "+servletClass);
			}
			return servlet;
		}
		// last - return default service servlet
		Servlet defaultServiceServlet = getDefaultServiceServlet(context);
		serviceServlets.put(moduleName,defaultServiceServlet);
		if (_log.isLoggable(Level.FINE)) {
			_log.fine("Use default servlet for module "+moduleName);
		}
		return defaultServiceServlet;
	}

	private String getServletClassForModule(String moduleName) {
		ClassLoader classLoader = Thread.currentThread()
		.getContextClassLoader();
		String servletClassName = null;
		// for module foo.bar.Module config file will be placed at foo/bar/Module.gwt.xml
		String gwtModuleConfigPath = moduleName.replace('.','/')+".gwt.xml";
		InputStream stream = classLoader.getResourceAsStream(gwtModuleConfigPath);
		if(null != stream){
			// We have module confir to read - parce it for servlen name.
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			try {
				SAXParser parser = parserFactory.newSAXParser();
				WebModuleConfigHandler handler = new WebModuleConfigHandler();
				parser.parse(stream,handler);
				servletClassName = handler.getServletClassName();
			} catch (Exception e) {
				if (_log.isLoggable(Level.WARNING)) {
					_log.warning("Exception in parsing web module config "+e.getMessage());
				}
			}
		}
		return servletClassName;
	}

	/**
	 * Create and initialize instance for servlet from class
	 * 
	 * @param context
	 * @param className
	 *            name of servlet class.
	 */
	private Servlet createServlet(FacesContext context, String className) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			Class clazz = classLoader.loadClass(className);
			Servlet servlet = (Servlet) clazz.newInstance();
			final ServletContext servletContext = (ServletContext) context.getExternalContext()
			.getContext();
			servlet.init(new ServletConfig() {

				public String getServletName() {
					// TODO Auto-generated method stub
					return "gwtFacesService";
				}

				public ServletContext getServletContext() {
					// TODO Auto-generated method stub
					return servletContext;
				}

				public String getInitParameter(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}

				public Enumeration getInitParameterNames() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			return servlet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FacesException("Error to create gwt service servlet", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		// check for script name.
		FacesContext facesContext = event.getFacesContext();
		ExternalContext externalContext = facesContext.getExternalContext();
		String mime = "text/html";
		String resourceName = null;
		// assume that mapping have /faces/* prefix
		String pathInfo = externalContext
				.getRequestPathInfo();
		
		if (null != pathInfo   ) {
			if(pathInfo.charAt(0) == '/'){
				pathInfo = pathInfo.substring(1);
			}
			if (pathInfo.startsWith(GWT_RESOURCE_PREFIX) ) {
				resourceName = pathInfo;
				if (pathInfo.endsWith(".js")) {
					mime = "text/javascript";
				} else if (pathInfo.endsWith(".xml")) {
					// xml with module cache entries
					mime = "text/xml";
				} else if (pathInfo.endsWith(".css")) {
					// xml with module cache entries
					mime = "text/css";
				} else if (pathInfo.endsWith(".gif")) {
					mime = "image/gif";
				} else if (pathInfo.endsWith(".jpg")
						|| pathInfo.endsWith(".jpeg")) {
					mime = "image/jpeg";
				} else {
					// normal request - do nothing
					//				return;
				}
				// we have resource - send it.
				try {
					HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
					ClassLoader loader = Thread.currentThread()
							.getContextClassLoader();
					InputStream in = loader.getResourceAsStream("META-INF/"
							+ resourceName);
					
					if (null != in) {
						OutputStream out = response.getOutputStream();
						response.setContentType(mime);
						// Set cachimg/nocaching headers.
						
						if (resourceName.endsWith(".nocache.html")) {
							response.setHeader("Cache-control", "no-cache");
							response.setHeader("Cache-control", "no-store");
							response.setHeader("Pragma", "no-cache");
							response.setIntHeader("Expires", 0);
						} else if (resourceName.contains(".cache.")){
							// TODO - send notmodified if request contain If-Modified-Since
							// ?
							HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
							long ifModifiedSince = request
								.getDateHeader("If-Modified-Since");
								if (ifModifiedSince >= 0) {
									response.setStatus(304);// Not modified
									in.close();
									out.close();
									facesContext.responseComplete();
									return ;
								} else {
									response.setDateHeader("Expires", System
											.currentTimeMillis()
											+ DEFAULT_EXPIRE);
								}
						}
						// Set output compression
						if(mime.contains("text") || mime.contains("xml")){
						String encoding = (String) externalContext.getRequestHeaderMap().get("Accept-Encoding");
						if(null != encoding && encoding.contains("gzip")){
							// TODO - use cache ?
							response.setHeader("Content-Encoding","gzip");
							out = new GZIPOutputStream(out,BUFFER_SIZE);
						}
						}
						byte[] buffer = new byte[BUFFER_SIZE];
						int buffersCount = -1;
						int length;
						for (length = in.read(buffer); length > 0; length = in
								.read(buffer)) {
							out.write(buffer, 0, length);
							buffersCount++;
						}
						// For cacheable resources, store size.
						// if(isCacheable()){
						// setContentLength(buffersCount*BUFFER_SIZE+length);
						// }
						in.close();
						out.flush();
						out.close();
					} else {
						response.sendError(404);
					}
				} catch (IOException e) {
					throw new FacesException("Error then send resource", e);
				} finally {
					facesContext.responseComplete();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

	/**
	 * @return Returns the defaultServiceServlet.
	 */
	public Servlet getDefaultServiceServlet(FacesContext context) {
		if (_defaultServiceServlet == null) {
			_defaultServiceServlet = createServlet(context,
					GwtFacesServlet.class.getName());
		}
		return _defaultServiceServlet;
	}

}
