package org.richfaces.test.staging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Base abstract class for an all file references in the 'virtual' staging server directory.
 * @author asmirnov
 *
 */
public abstract class AbstractServerResource implements ServerResource {

	private static final Logger log = ServerLogger.RESOURCE.getLogger();

	public AbstractServerResource() {
	}

	/** This implementation creates stream from the resource URL. I also tries to disable
	 * url connection cache, to prevent jar file locking in the windows environment.
	 * @see org.richfaces.test.staging.ServerResource#getAsStream()
	 */
	public InputStream getAsStream() throws IOException {
		URL url = getURL();
		if (url != null) {
			URLConnection connection = url.openConnection();
			try {
				connection.setUseCaches(false);
			} catch (IllegalArgumentException e) {
				log.info("RESOURCE_NOT_CACHEABLE");
			}
			return connection.getInputStream();
		} else {
			return null;
		}
	}

	/**
	 * File-like resources do not allows resources additions.
	 * @throws UnsupportedOperationException
	 * @see org.richfaces.test.staging.ServerResource#addResource(org.richfaces.test.staging.ServerResourcePath, org.richfaces.test.staging.ServerResource)
	 */
	public void addResource(ServerResourcePath path, ServerResource resource) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#getResource(org.richfaces.test.staging.ServerResourcePath)
	 */
	public ServerResource getResource(ServerResourcePath path) {
		if(null == path){
			throw new NullPointerException();
		}
		// If path points to the resource itself, returns this instance.
		if(path.isFile()){
			return this;
		}
		return null;
	}

	/** File-like resources does not contain any other, so this method always returns null.
	 * @see org.richfaces.test.staging.ServerResource#getPaths()
	 */
	public Set<String> getPaths() {
		return null;//Collections.emptySet();
	}

}