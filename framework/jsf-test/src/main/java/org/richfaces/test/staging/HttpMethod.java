/**
 * 
 */
package org.richfaces.test.staging;

/**
 * @author asmirnov
 *
 */
public enum HttpMethod {
	
	GET("GET"),
	POST("POST"),
	HEAD("HEAD"),
	PUT("PUT"),
	OPTIONS("OPTIONS"),
	TRACE("TRACE"),
	DELETE("DELETE");
	
	/**
	 * @param name
	 */
	private HttpMethod(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String toString() {
		return name;
	}
}
