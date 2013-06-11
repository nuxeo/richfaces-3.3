/**
 * 
 */
package org.richfaces.test.staging;

import java.util.regex.Pattern;

import javax.servlet.ServletContext;

/**
 * Class to represent web server resources directory path.
 * @author asmirnov
 * 
 */
public class ServerResourcePath {
	
	private static final Pattern SLASH = Pattern.compile("/");

	public static final ServerResourcePath WEB_INF=new ServerResourcePath("/WEB-INF/");

	public static final ServerResourcePath META_INF=new ServerResourcePath("/META-INF/");
	public static final ServerResourcePath WEB_XML=new ServerResourcePath("/WEB-INF/web.xml");
	public static final ServerResourcePath FACES_CONFIG=new ServerResourcePath("/WEB-INF/faces-config.xml");


	private final String[] pathElements;

	/**
	 * Private constructor for next sub - path.
	 * @param pathElements
	 */
	private ServerResourcePath(String[] pathElements) {
		this.pathElements = pathElements;
	}

	/**
	 * Create path from string representation. Path have to started with training slash, as required for
	 * {@link ServletContext#getResource(String)} 
	 * @param path 
	 */
	public ServerResourcePath(String path) {
		if (null == path) {
			throw new NullPointerException();
		}
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException();
		}
		String[] split = SLASH.split(path);
		if(split.length >1 && path.endsWith("/")){
			pathElements = new String[split.length+1];
			System.arraycopy(split, 0, pathElements, 0, split.length);
		} else {
			pathElements = split;
		}
	}

	/**
	 * Method to detect last element in the path.
	 * @return true for a last element in the path.
	 */
	public boolean isFile() {
		return pathElements.length <= 1 || null == pathElements[1];
	}

	/**
	 * Name of the next element ( directory or file ) name. 
	 * For the "/foo/bar/baz" it should be "foo/" ,  /bar/baz : "bar/" , "/" : null.
	 * @return name of the next element or null if it is last element in the chain ( file ).
	 */
	public String getNextElementName() {
		if (pathElements.length > 1) {
			String name = pathElements[1];
			if(pathElements.length>2){
				name+='/';
			}
			return name;
		} else {
			return null;
		}
	}

	/**
	 * Create next path of the path chain.
	 * Path /foo/bar/baz should be converted to /bar/baz , /bar/baz -> /baz -> / ( empty path )
	 * @return next subdirectory path or null.
	 */
	public ServerResourcePath getNextPath() {
		if (pathElements.length > 1 && null != pathElements[1]) {
		String[] nextElenemts = new String[pathElements.length - 1];
		System.arraycopy(pathElements, 1, nextElenemts, 0, nextElenemts.length);
		return new ServerResourcePath(nextElenemts);
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (pathElements.length > 1) {
		for (int i = 1; i < pathElements.length; i++) {
			String element = pathElements[i];
			str.append("/");
			if(null != element){
				str.append(element);
			}
		}
		} else {
			str.append("/");
		}
		return str.toString();
	}
}
