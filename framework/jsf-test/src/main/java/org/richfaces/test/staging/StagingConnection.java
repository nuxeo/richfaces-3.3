/**
 * 
 */
package org.richfaces.test.staging;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.richfaces.test.TestException;

/**
 * This class represent single connection ( request ) to the virtual server. These instance should not be created directly, but by the {@link StagingServer#getConnection(URL)}.
 * method only.
 * Since instance have been created, additional request parameters and headers can be set.
 * @author asmirnov
 * 
 */
public class StagingConnection {

	private static final Logger log = ServerLogger.SERVER.getLogger();

	private final StagingServer server;

	final URL url;

	private ConnectionRequest request;

	private ConnectionResponse response;

	private final RequestChain servlet;

	private HttpMethod method = HttpMethod.GET;

	private static final Cookie[] COOKIE = new Cookie[] {};

	private List<Cookie> cookies = new ArrayList<Cookie>();

	private Map<String, String[]> requestParameters = new HashMap<String, String[]>();

	private final String pathInfo;

	private final String servletPath;

	private boolean finished = false;

	private boolean started = false;

	private String queryString;

	private HttpServletRequest requestProxy;

	private HttpServletResponse responseProxy;

	/**
	 * Create connection instance.
	 * @param localServer virtual server instance.
	 * @param url request URL.
	 */
	StagingConnection(StagingServer localServer, URL url) {
		this.server = localServer;
		this.url = url;
		// TODO - context path support.
		String path = url.getPath();
		servlet = localServer.getServlet(path);
		if (null == servlet) {
			throw new IllegalArgumentException();
		}
		this.pathInfo = servlet.getPathInfo(path);
		this.servletPath = servlet.getServletPath(path);
		this.request = new ConnectionRequest();
		this.response = new ConnectionResponse();
		this.request.setAttribute("javax.servlet.include.path_info",
				this.pathInfo);
		this.request.setAttribute("javax.servlet.include.servlet_path",
				this.servletPath);
		setQueryString(url.getQuery());
		if (null != getQueryString()) {
			parseFormParameters(queryString);
		}
		
		// Create proxy objects.
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if(null == loader){
			loader = this.getClass().getClassLoader();
		}
		requestProxy = (HttpServletRequest) Proxy.newProxyInstance(loader, new Class[]{HttpServletRequest.class}, server.getInvocationHandler(request));
		responseProxy = (HttpServletResponse) Proxy.newProxyInstance(loader, new Class[]{HttpServletResponse.class}, server.getInvocationHandler(response));
	}

	/**
	 * Parse 'application/x-www-form-urlencoded' string with parameters name/value pairs,
	 * as it expected after a form submit.
	 * @param queryString URL query string or POST content.
	 */
	public void parseFormParameters(String queryString) {
		String[] queryParams = queryString.split("&");
		for (int i = 0; i < queryParams.length; i++) {
			try {
				String par = queryParams[i];
				int eqIndex = par.indexOf('=');
				if (eqIndex >= 0) {
					// decode url-decoded values.
					String name = URLDecoder.decode(par.substring(0, eqIndex),
							request.getCharacterEncoding());
					String value = URLDecoder.decode(
							par.substring(eqIndex + 1), request
									.getCharacterEncoding());
					addRequestParameter(name, value);
				} else {
					addRequestParameter(URLDecoder.decode(par, request
							.getCharacterEncoding()), null);
				}
			} catch (UnsupportedEncodingException e) {
				throw new TestException(e);
			}
		}
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}

//	private void checkStarted() {
//		if (!isFinished()) {
//			throw new TestException("request have not been started");
//		}
//	}

//	private void checkNotStarted() {
//		if (isStarted()) {
//			throw new TestException("request was started, no parameters changes allowed");
//		}
//	}

	/**
	 * Execute this connection request on the associated servlet or filter chain.
	 * @throws TestException if any errors were during execution.
	 */
	public void execute() {
		if (isStarted() || isFinished()) {
			throw new TestException(
					"request have already been executed");
		}
		start();
		try {
			this.servlet.execute(request, response);
		} catch (ServletException e) {
			throw new TestException("Error execute request ",e);
		} catch (IOException e) {
			throw new TestException("IO Error during request execution",e);
		} finally {
			finish();
		}
	}

	/**
	 * Finish request to the this connection, inform server listeners about request status.
	 */
	public void finish() {
		server.requestFinished(request);
		finished = true;
	}

	/**
	 * Start request to the this connection, inform server listeners about request status.
	 * No request parameters changes allowed after connection start.
	 */
	public void start() {
		log.fine("start " + getRequestMethod() + " request processing for file "
				+ url.getFile());
		log.fine("request parameters: " + requestParameters);
		server.requestStarted(request);
		started = true;
	}

	/**
	 * Get request HTTP methos ( GET, POST etc ).
	 * @return the method
	 */
	public HttpMethod getRequestMethod() {
		return method;
	}

	/**
	 * Set request HTTP methos ( GET, POST etc ).
	 * @param method
	 *            the method to set
	 */
	public void setRequestMethod(HttpMethod method) {
//		checkNotStarted();
		this.method = method;
	}

	/**
	 * Get request url.
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Append additional request parameter.
	 * @param name
	 * @param value
	 */
	public void addRequestParameter(String name, String value) {
//		checkNotStarted();
		String[] values = requestParameters.get(name);
		if (null == values) {
			values = new String[1];
		} else {
			String[] newValues = new String[values.length + 1];
			System.arraycopy(values, 0, newValues, 0, values.length);
			values = newValues;
		}
		values[values.length - 1] = value;
		requestParameters.put(name, values);
	}

	/**
	 * Get content of the response as String. 
	 * @return content of the response writer or String created from the ServletOutputStream with current response encoding.
	 * @throws TestException
	 * 	          if has an unsupported encoding.
	 */
	public String getContentAsString() {
//		checkStarted();
		String content = response.getWriterContent();
		if (null == content) {
			byte[] streamContent = response.getStreamContent();
			if (null != streamContent) {
				String encoding = response.getCharacterEncoding();
				if (null != encoding) {
					try {
						content = new String(streamContent, encoding);
					} catch (UnsupportedEncodingException e) {
						throw new TestException(e);
					}
				} else {
					content = new String(streamContent);
				}
			}
		}
		return content;
	}

	/**
	 * Get content of the response as byte array.
	 * @return content of the ServletOutputStream or convert String, collected by response writer, with current response encoding.
	 * @throws TestException
	 * 	          if response has unsupported encoding.
	 */
	public byte[] getResponseBody() {
//		checkStarted();
		byte[] content = response.getStreamContent();
		if (null == content) {
			String writerContent = response.getWriterContent();
			if (null != writerContent) {
				try {
					content = writerContent.getBytes(response
							.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					content = writerContent.getBytes();
				}
			} else {
				content = new byte[0];
			}
		}
		return content;
	}

	/**
	 * List of the {@link Cookie} used by the request or response ( There are same cookies for both request and response ). 
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * request object for the this connection.
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return requestProxy;
	}

	/**
	 * response object for the this connection.
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return responseProxy;
	}

	/**
	 * @return encoding used to write response.
	 */
	public String getResponseCharacterEncoding() {
//		checkStarted();
		return response.getCharacterEncoding();
	}

	/**
	 * @return content type ( eg 'text/html' ) of the response.
	 */
	public String getResponseContentType() {
//		checkStarted();
		return response.getContentType();
	}

	/**
	 * @return HTTP status code of the response.
	 */
	public int getResponseStatus() {
//		checkStarted();
		return response.getStatus();
	}

	/**
	 * @return HTTP error message.
	 */
	public String getErrorMessage() {
//		checkStarted();
		return response.getErrorMessage();
	}

	/**
	 * Set request Query string. This method does not parse query string, {@link #parseFormParameters(String)} should be used.
	 * @param queryString
	 *            the queryString to set
	 */
	public void setQueryString(String queryString) {
//		checkNotStarted();
		this.queryString = queryString;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Get HTTP response headers.
	 * @return headers name-values map.
	 */
	public Map<String, String[]> getResponseHeaders() {
//		checkStarted();
		return response.getHeaders();
	}

	/**
	 * Set charset for the request body.
	 * @param charset
	 * @throws UnsupportedEncodingException
	 */
	public void setRequestCharacterEncoding(String charset) throws UnsupportedEncodingException {
//		checkNotStarted();
		request.setCharacterEncoding(charset);		
	}

	/**
	 * Set HTTP POST/PUT methods uploading content.
	 * @param body
	 */
	public void setRequestBody(String body) {
//		checkNotStarted();
		request.setRequestBody(body);		
	}

	/**
	 * Set HTTP request content type ( eg 'application/x-www-form-urlencoded' or 'text/xml' ).
	 * @param contentType
	 */
	public void setRequestContentType(String contentType) {
//		checkNotStarted();
		request.setContentType(contentType);
		
	}

	/**
	 * Append additional HTTP request headers.
	 * @param headers
	 */
	public void addRequestHeaders(Map<String, String> headers) {
//		checkNotStarted();
		request.addHeaders(headers);		
	}

	private class ConnectionRequest extends StagingHttpRequest {
	
		public Cookie[] getCookies() {
			return cookies.toArray(COOKIE);
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getMethod()
		 */
		public String getMethod() {
			return method.toString();
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getServletPath()
		 */
		public String getServletPath() {
			return servletPath;
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getPathInfo()
		 */
		public String getPathInfo() {
			return pathInfo;
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getQueryString()
		 */
		public String getQueryString() {
			return queryString;
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getRequestURI()
		 */
		public String getRequestURI() {
			return url.getPath();
		}
		
		/* (non-Javadoc)
		 * @see org.richfaces.test.staging.StagingHttpRequest#getServerName()
		 */
		@Override
		public String getServerName() {
			return url.getHost();
		}
		
	
		@Override
		public int getLocalPort() {
			int port = url.getPort();
			if(port < 0){
				port = super.getLocalPort();
			}
			return port;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
		 */
		public String getParameter(String name) {
			String[] values = requestParameters.get(name);
			if (null != values && values.length > 0) {
				return values[0];
			}
			return null;
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.ServletRequest#getParameterMap()
		 */
		@SuppressWarnings("unchecked")
		public Map getParameterMap() {
			return Collections.unmodifiableMap(requestParameters);
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.ServletRequest#getParameterNames()
		 */
		@SuppressWarnings("unchecked")
		public Enumeration getParameterNames() {
			return Collections.enumeration(requestParameters.keySet());
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.servlet.ServletRequest#getParameterValues(java.lang.String)
		 */
		public String[] getParameterValues(String name) {
			return requestParameters.get(name);
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getSession()
		 */
		public HttpSession getSession() {
			return server.getSession();
		}
	
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
		 */
		public HttpSession getSession(boolean create) {
			return server.getSession(create);
		}
	
		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			RequestDispatcher dispatcher = null;
			if (!path.startsWith("/")) {
				try {
					URL absoluteUrl = new URL(url, path);
					path = absoluteUrl.getFile();
				} catch (MalformedURLException e) {
					return null;
				}
			}
			final RequestChain dispatchedServlet = server.getServlet(path);
			if (null != dispatchedServlet) {
				dispatcher = new RequestDispatcher() {
	
					public void forward(ServletRequest request,
							ServletResponse response) throws ServletException,
							IOException {
						response.reset();
						dispatchedServlet.execute(request, response);
					}
	
					public void include(ServletRequest request,
							ServletResponse response) throws ServletException,
							IOException {
						dispatchedServlet.execute(request, response);
					}
	
				};
			}
			return dispatcher;
		}
	
		@Override
		protected void attributeAdded(String name, Object o) {
			server.requestAttributeAdded(this, name, o);
	
		}
	
		@Override
		protected void attributeRemoved(String name, Object removed) {
			server.requestAttributeRemoved(this, name, removed);
	
		}
	
		@Override
		protected void attributeReplaced(String name, Object o) {
			server.requestAttributeReplaced(this, name, o);
	
		}
	
	}

	private class ConnectionResponse extends StagingHttpResponse {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http
		 * .Cookie )
		 */
		public void addCookie(Cookie cookie) {
			cookies.add(cookie);
	
		}
	
	}

	public int getResponseContentLength() {
		return response.getContentLength();		
	}

}
