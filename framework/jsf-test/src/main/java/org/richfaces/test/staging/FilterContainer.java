package org.richfaces.test.staging;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Holds a filter instance and reference to the next executable server object (
 * filter or servlet ) in the chain.
 * 
 * @author asmirnov
 * 
 */
public class FilterContainer implements RequestChain {

	private static final Logger log = ServerLogger.SERVER.getLogger();

	/**
	 * Servlet filter instance.
	 */
	private final Filter filter;

	/**
	 * Next object in the chain.
	 */
	private final RequestChain next;

	/**
	 * Filter name.
	 */
	private String name = "Default";

	/**
	 * Filter initialization parameters.
	 */
	private final Map<String, String> initParameters;

	/**
	 * Initialization flag to avoid double calls.
	 */
	private boolean initialized = false;

	/**
	 * @param filter
	 *            instance of the web application filter.
	 * @param next
	 *            next executable object in the filter chain.
	 * @throws NullPointerException if any of parameter is null.
	 */
	public FilterContainer(Filter filter, RequestChain next) {
		if(null == filter || null == next){
			throw new NullPointerException();
		}
		this.filter = filter;
		this.next = next;
		this.initParameters = new HashMap<String, String>();
	}

	/**
	 * @return filter name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            new filter name.
	 */
	public void setName(String name) {
		if (initialized) {
			throw new IllegalStateException(
					"Filter have already been initialized, name can't be changed");
		}
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.richfaces.test.staging.RequestChain#execute(javax.servlet.ServletRequest
	 * , javax.servlet.ServletResponse)
	 */
	public void execute(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		if (!initialized) {
			throw new IllegalStateException(
					"Filter "+getName()+" have not been initialized, could'n execute request");
		}
		log.finest("Request '"+request+"' executes by the '"+getName()+"' filter");
		FilterChain chain = new FilterChain() {
			// Execute next object in the chain.
			public void doFilter(ServletRequest request,
					ServletResponse response) throws IOException,
					ServletException {
				next.execute(request, response);

			}

		};
		filter.doFilter(request, response, chain);

	}

	/**
	 * Append filter initialization parameter. Name and value are same as
	 * defined in the web.xml
	 * 
	 * <code>
	 * &lt;init-param&gt;
	 *    &lt;param-name&gt;foo&lt;/param-name&gt;
	 *    &lt;param-value&gt;bar&lt;/param-value&gt;
	 *   &lt;/init-param&gt;
	 * </code>
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            its value
	 */
	public void addInitParameter(String name, String value) {
		if (initialized) {
			throw new IllegalStateException(
					"Filter have already been initialized, init parameters can't be changed");
		}
		initParameters.put(name, value);
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.RequestChain#isApplicable(java.lang.String)
	 */
	public boolean isApplicable(String path) {
		// Delegate to the next object. Filter has a same mapping as target servlet.
		return next.isApplicable(path);
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.RequestChain#destroy()
	 */
	public void destroy() {
		if (initialized) {
			next.destroy();
			filter.destroy();
			initialized = false;
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.RequestChain#init(org.richfaces.test.staging.StagingServletContext)
	 */
	public void init(final ServletContext context)
			throws ServletException {
		if (!initialized) {
			filter.init(new FilterConfig() {

				public String getFilterName() {
					return name;
				}

				public String getInitParameter(String name) {
					return initParameters.get(name);
				}

				@SuppressWarnings("unchecked")
				public Enumeration getInitParameterNames() {
					return Collections.enumeration(initParameters.keySet());
				}

				public ServletContext getServletContext() {
					return context;
				}

			});
			next.init(context);
			initialized = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.RequestChain#getPathInfo(java.lang.String)
	 */
	public String getPathInfo(String path) {
		// Delegate to the next object. Filter has a same mapping as target servlet.
		return next.getPathInfo(path);
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.RequestChain#getServletPath(java.lang.String)
	 */
	public String getServletPath(String path) {
		// Delegate to the next object. Filter has a same mapping as target servlet.
		return next.getServletPath(path);
	}

}
