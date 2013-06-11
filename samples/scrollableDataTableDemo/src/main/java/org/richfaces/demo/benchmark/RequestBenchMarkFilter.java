/**
 * 
 */
package org.richfaces.demo.benchmark;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Maksim Kaszynski
 *
 */
public class RequestBenchMarkFilter implements Filter {

	private Log log = LogFactory.getLog(this.getClass());
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		long startTime = System.currentTimeMillis();
		
		log.info("Request started " + ((HttpServletRequest) request).getRequestURL());
		
		chain.doFilter(request, response);
		
		long endTime = System.currentTimeMillis();
		
		log.info("Request ended " + ((HttpServletRequest) request).getRequestURL());
		
		log.info(requestType() + 
				((HttpServletRequest) request).getRequestURL() + 
				" took " + 
				(endTime - startTime) + 
				"ms");

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	protected String requestType() {
		return "Request with filters ";
	}

}
