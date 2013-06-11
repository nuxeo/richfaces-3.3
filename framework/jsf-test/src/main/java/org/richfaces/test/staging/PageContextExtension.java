/**
 * 
 */
package org.richfaces.test.staging;

import java.io.IOException;
import java.util.Enumeration;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

final class PageContextExtension extends PageContext {
	private ServletResponse response;
	private Servlet servlet;
	private ServletRequest request;
	private boolean needsSession;
	private int bufferSize;
	private boolean autoFlush;


	@Override
	public void forward(String relativeUrlPath)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Exception getException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPage() {
		return servlet;
	}

	@Override
	public ServletRequest getRequest() {
		return request;
	}

	@Override
	public ServletResponse getResponse() {
		return response;
	}

	@Override
	public ServletConfig getServletConfig() {
		return servlet.getServletConfig();
	}

	@Override
	public ServletContext getServletContext() {
		return servlet.getServletConfig().getServletContext();
	}

	@Override
	public HttpSession getSession() {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return httpRequest.getSession(needsSession);
	}

	@Override
	public void handlePageException(Exception e)
			throws ServletException, IOException {
		throw new ServletException(e);
		
	}

	@Override
	public void handlePageException(Throwable t)
			throws ServletException, IOException {
		throw new ServletException(t);
		
	}

	@Override
	public void include(String relativeUrlPath)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void include(String relativeUrlPath, boolean flush)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(Servlet servlet, ServletRequest request,
			ServletResponse response, String errorPageURL,
			boolean needsSession, int bufferSize, boolean autoFlush)
			throws IOException, IllegalStateException,
			IllegalArgumentException {
		this.response = response;
		this.servlet = servlet;
		this.request = request;
		this.needsSession = needsSession;
		this.bufferSize = bufferSize;
		this.autoFlush = autoFlush;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object findAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String name, int scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAttributesScope(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ELContext getELContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExpressionEvaluator getExpressionEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JspWriter getOut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VariableResolver getVariableResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttribute(String name, int scope) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		// TODO Auto-generated method stub
		
	}
}