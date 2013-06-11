package org.richfaces.test.staging;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Implementations of the this interface represent any executable request object ( filter or servlet ) in the virtual server.
 * Filter implementation also hold a reference to the next server object in the chain.
 * @author asmirnov
 *
 */
public interface RequestChain {

	/**
	 * Is this object applicable to process given path ?
	 * @param path request path relative to web application context.
	 * @return true if this object was configured to process given path.
	 */
	public abstract boolean isApplicable(String path);

	/**
	 * Execute request with current object. Filter objects also delegate doFilter calls to the next object in the chain.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void execute(ServletRequest request,
			ServletResponse response) throws ServletException, IOException;

	/**
	 * Destroy containing web server objects.
	 */
	public abstract void destroy();

	/**
	 * Init containing objects ( filters or servlet ).
	 * @param servletContext
	 * @throws ServletException
	 */
	public abstract void init(ServletContext servletContext) throws ServletException;

	/**
	 * Calculate path info for a request path.
	 * @param path request path relative to the web application context.
	 * @return part of the path after servlet path or null for a suffix-based mapping.
	 */
	public abstract String getPathInfo(String path);

	/**
	 * Calculate servlet path for a given request.
	 * @param path request path relative to the web application context.
	 * @return prefix part from servlet mapping or {@code path} for a suffix-based mapping.
	 */
	public abstract String getServletPath(String path);

}