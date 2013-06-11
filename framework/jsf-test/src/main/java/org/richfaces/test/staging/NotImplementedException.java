/**
 * 
 */
package org.richfaces.test.staging;

/**
 * This exception indicates feature not yet implemented in the stub server.
 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class NotImplementedException extends RuntimeException {

	/**
	 * 
	 */
	public NotImplementedException() {
		super("This feature not yet implemented");
	}

	/**
	 * @param message
	 */
	public NotImplementedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NotImplementedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotImplementedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
