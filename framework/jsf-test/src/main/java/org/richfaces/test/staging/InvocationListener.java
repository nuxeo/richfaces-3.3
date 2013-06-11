/**
 * 
 */
package org.richfaces.test.staging;

import java.util.EventListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Listener interface to inspect all calls to {@link HttpServletRequest} , {@link HttpServletResponse}, {@link HttpSession} and {@link ServletContext} objects.
 * @author asmirnov
 *
 */
public interface InvocationListener extends EventListener{

	/**
	 * This metod called after successful invocation on the target object.
	 * @param invocationEvent
	 */
	public void afterInvoke(InvocationEvent invocationEvent);
	
	/**
	 * This method called after any {@link Throwable} thrown during method invocation.
	 * @param invocationErrorEvent
	 */
	public void processException(InvocationErrorEvent invocationErrorEvent);
	
}
