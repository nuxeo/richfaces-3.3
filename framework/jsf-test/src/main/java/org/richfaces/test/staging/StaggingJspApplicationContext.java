/**
 * 
 */
package org.richfaces.test.staging;

import javax.el.ELContextListener;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;

import org.richfaces.test.TestException;


/**
 * @author asmirnov
 *
 */
public class StaggingJspApplicationContext implements JspApplicationContext {
	
	private static final String DEFAULT_EXPRESSION_FACTORY="com.sun.el.ExpressionFactoryImpl";
	
	private final ExpressionFactory expressionFactory ;
	private final ServletContext servletContext;
	

	public StaggingJspApplicationContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		String elFactoryClass = servletContext.getInitParameter("com.sun.faces.expressionFactory");
		if(null == elFactoryClass){
			elFactoryClass = servletContext.getInitParameter("com.sun.el.ExpressionFactoryImpl");
		}
		if(null == elFactoryClass){
			elFactoryClass = DEFAULT_EXPRESSION_FACTORY;
		}
		try {
			expressionFactory = Class.forName(elFactoryClass).asSubclass(ExpressionFactory.class).newInstance();
		} catch (Exception e) {
			throw new TestException("Couldn't instantiate EL expression factory",e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspApplicationContext#addELContextListener(javax.el.ELContextListener)
	 */
	public void addELContextListener(ELContextListener listener) {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspApplicationContext#addELResolver(javax.el.ELResolver)
	 */
	public void addELResolver(ELResolver resolver) {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.JspApplicationContext#getExpressionFactory()
	 */
	public ExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

}
