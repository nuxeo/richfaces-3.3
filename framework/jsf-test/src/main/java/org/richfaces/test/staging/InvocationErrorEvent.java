package org.richfaces.test.staging;

import java.lang.reflect.Method;
import java.util.EventObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This event sent from the virtual server introspection method to the registered
 * {@link InvocationListener} instance after any exception thrown from calls to {@link HttpServletRequest} , {@link HttpServletResponse}, {@link HttpSession} and {@link ServletContext} objects. 

 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class InvocationErrorEvent extends EventObject {
	private Object target;
	private Method method;
	private Object[] args;
	private Throwable e;

	public InvocationErrorEvent(Object target, Method method, Object[] args,
			Throwable e) {
		super(target);
		this.target = target;
		this.method = method;
		this.args = args;
		this.e = e;
	}

	public Object getTarget() {
		return target;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	/**
	 * @return thrown exception.
	 */
	public Throwable getE() {
		return e;
	}
}