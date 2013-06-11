/**
 * 
 */
package org.richfaces.test;

import org.richfaces.test.staging.StagingServer;

/**
 * Internal runtame exception for the {@link StagingServer} engine.
 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class TestException extends RuntimeException {

	/**
	 * Default constructor.
	 */
	public TestException() {
		super();
	}

	/**
	 * @param message
	 */
	public TestException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TestException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

}
