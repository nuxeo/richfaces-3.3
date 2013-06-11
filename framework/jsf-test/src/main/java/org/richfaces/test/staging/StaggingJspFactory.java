/**
 * 
 */
package org.richfaces.test.staging;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * Stagging implementation of the {@link JspFactory} to simulate JSP processing in the stagging server.
 * TODO - implement JSP functionality.
 * @author asmirnov
 * 
 */
public class StaggingJspFactory extends JspFactory {

	private static final JspEngineInfo engineInfo = new JspEngineInfo() {

		@Override
		public String getSpecificationVersion() {
			return "2.1";
		}

	};

	private final JspApplicationContext context;

	public StaggingJspFactory(ServletContext servletContext) {
		this.context = new StaggingJspApplicationContext(servletContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.JspFactory#getEngineInfo()
	 */
	@Override
	public JspEngineInfo getEngineInfo() {
		// TODO Auto-generated method stub
		return engineInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.jsp.JspFactory#getJspApplicationContext(javax.servlet.
	 * ServletContext)
	 */
	@Override
	public JspApplicationContext getJspApplicationContext(ServletContext context) {
		return this.context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.JspFactory#getPageContext(javax.servlet.Servlet,
	 * javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * java.lang.String, boolean, int, boolean)
	 */
	@Override
	public PageContext getPageContext(final Servlet servlet, final ServletRequest request,
			final ServletResponse response, String errorPageURL,
			final boolean needsSession, int buffer, boolean autoflush) {
		PageContextExtension pageContextExtension = new PageContextExtension();
		try {
			pageContextExtension.initialize(servlet, request, response, errorPageURL, needsSession, buffer, autoflush);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return pageContextExtension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.jsp.JspFactory#releasePageContext(javax.servlet.jsp.PageContext
	 * )
	 */
	@Override
	public void releasePageContext(PageContext pc) {
		pc.release();

	}

}
