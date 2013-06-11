/**
 * 
 */
package org.richfaces.test.staging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

/**
 * @author asmirnov
 *
 */
public interface ServerResource {
	

	/**
	 * Get URL for a test server resource content. Directories should return null or "stub"
	 * @return
	 */
	public URL getURL();
	
	/**
	 * Create input stream to read content. Directories returns {@code null}
	 * @return
	 * @throws IOException
	 */
	public InputStream getAsStream() throws IOException;
	
	/**
	 * @return strings representing directory content. File-like resources return null.
	 */
	public Set<String> getPaths();
	
	/**
	 * Append resource to the current directory-like structure.
	 * @param path
	 * @param resource
	 */
	public void addResource(ServerResourcePath path, ServerResource resource);
	
	/**
	 * Get resource from the current directory or its subdirectories.
	 * @param path
	 * @return
	 */
	public ServerResource getResource(ServerResourcePath path);

}
