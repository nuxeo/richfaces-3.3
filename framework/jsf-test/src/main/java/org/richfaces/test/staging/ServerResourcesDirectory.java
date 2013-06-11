/**
 * 
 */
package org.richfaces.test.staging;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Directory-like resource for a virtual web application content.
 * @author asmirnov
 *
 */
public class ServerResourcesDirectory implements ServerResource {
	
	

	/**
	 * Directory content.
	 */
	private final Map<String,ServerResource> children = new HashMap<String,ServerResource>();

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#addResource(org.richfaces.test.staging.ServerResource)
	 */
	public void addResource(ServerResourcePath path,ServerResource resource) {
		
		if(null == path || path.isFile()){
			throw new IllegalArgumentException();
		} else if(path.getNextPath().isFile()) {
			// This is a last part in the path - append resource to the directory content.
			children.put(path.getNextElementName(), resource);
		} else {
			// Create a next directory entry.
			ServerResource nextDirectory = children.get(path.getNextElementName());
			if(null == nextDirectory){
				nextDirectory = new ServerResourcesDirectory();
				children.put(path.getNextElementName(), nextDirectory);
			}
			nextDirectory.addResource(path.getNextPath(), resource);
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#getResource(org.richfaces.test.staging.ServerResourcePath)
	 */
	public ServerResource getResource(ServerResourcePath path) {
		if(null == path){
			throw new NullPointerException();
		}
		ServerResource resource = null; //children.get(path.getName());
		if(path.isFile()){
			// Path points to the resource itself.
			resource=this;
		} else {
			resource = children.get(path.getNextElementName());
			if(!path.getNextPath().isFile() && null!=resource){
				// Get next resource in the tree, if exists.
				resource = resource.getResource(path.getNextPath());
			}			
		}
		return resource;
	}
	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#getAsStream()
	 */
	public InputStream getAsStream() {
		// can't read directory.
		return null;
	}


	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#getPaths()
	 */
	public Set<String> getPaths() {
		return children.keySet();
	}

	/* (non-Javadoc)
	 * @see org.richfaces.test.staging.ServerResource#getURL()
	 */
	public URL getURL() {
		// Directory don't have url.
		return null;
	}


	
}
