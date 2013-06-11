package org.richfaces.test.staging;

import java.lang.reflect.Method;
import java.util.EventObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This event sent from the staging server introspection method to the registered
 * {@link InvocationListener} instance after all calls to {@link HttpServletRequest} , {@link HttpServletResponse}, {@link HttpSession} and {@link ServletContext} objects. 
 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class InvocationEvent extends EventObject {
	private Object target;
	private Method method;
	private Object[] args;
	private Object result;

	public InvocationEvent(Object target, Method method, Object[] args,
			Object result) {
		super(target);
		this.target = target;
		this.method = method;
		this.args = args;
		this.result = result;
	}

	/**
	 * @return target object instance.
	 */
	public Object getTarget() {
		return target;
	}

	/**
	 * @return {@link Method} that was called.
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @return method arguments.
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @return value returned from the invoked method.
	 */
	public Object getResult() {
		return result;
	}
}