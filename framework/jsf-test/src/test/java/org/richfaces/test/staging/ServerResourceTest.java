/**
 * 
 */
package org.richfaces.test.staging;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.junit.Test;
import org.richfaces.test.staging.ClasspathServerResource;
import org.richfaces.test.staging.ServerResource;
import org.richfaces.test.staging.ServerResourcePath;
import org.richfaces.test.staging.ServerResourcesDirectory;

/**
 * @author asmirnov
 * 
 */
public class ServerResourceTest {

	private class MockResource implements ServerResource {

		public void addResource(ServerResourcePath path, ServerResource resource) {
		}

		public InputStream getAsStream() throws IOException {
			return null;
		}

		public Set<String> getPaths() {
			return null;
		}

		public ServerResource getResource(ServerResourcePath path) {
			if (null != path && path.isFile()) {
				return this;
			}
			return null;
		}

		public URL getURL() {
			return null;
		}

	}

	/**
	 * Test method for
	 * {@link org.richfaces.test.staging.ServerResourcesDirectory#addResource(org.richfaces.test.staging.ServerResourcePath, org.richfaces.test.staging.ServerResource)}
	 * .
	 */
	@Test
	public void testAddResource() {
		ServerResourcesDirectory root = new ServerResourcesDirectory();
		MockResource webXml = new MockResource();
		root.addResource(ServerResourcePath.WEB_XML, webXml);
		assertEquals(1, root.getPaths().size());
		MockResource facesConfig = new MockResource();
		root.addResource(ServerResourcePath.FACES_CONFIG, facesConfig);
		assertEquals(1, root.getPaths().size());
		ServerResource webInf = root.getResource(ServerResourcePath.WEB_INF);
		assertNotNull(webInf);
		assertEquals(2, webInf.getPaths().size());
		assertSame(webXml, webInf
				.getResource(new ServerResourcePath("/web.xml")));
		assertSame(facesConfig, webInf.getResource(new ServerResourcePath(
				"/faces-config.xml")));
	}

	/**
	 * Test method for
	 * {@link org.richfaces.test.staging.ServerResourcesDirectory#getResource(org.richfaces.test.staging.ServerResourcePath)}
	 * .
	 */
	@Test
	public void testGetResource() {
		ServerResourcesDirectory root = new ServerResourcesDirectory();
		MockResource webXml = new MockResource();
		root.addResource(ServerResourcePath.WEB_XML, webXml);
		ServerResource webInf = root.getResource(ServerResourcePath.WEB_INF);
		assertNotNull(webInf);
		assertNull(root.getResource(new ServerResourcePath("/foo")));
		assertNull(root.getResource(new ServerResourcePath("/foo/baz")));
		assertEquals(1, root.getPaths().size());
		assertNull(root.getResource(new ServerResourcePath(
				"/WEB-INF/web.xml/foo")));
		assertSame(webXml, webInf.getResource(new ServerResourcePath(
				"/web.xml")));
	}

	/**
	 * Test method for
	 * {@link org.richfaces.test.staging.ServerResourcesDirectory#getResource(org.richfaces.test.staging.ServerResourcePath)}
	 * .
	 */
	@Test
	public void testGetResourceRoot() {
		ServerResourcesDirectory root = new ServerResourcesDirectory();
		MockResource indexXhtml = new MockResource();
		root.addResource(new ServerResourcePath("/index.xhtml"), indexXhtml);
		ServerResource index = root.getResource(new ServerResourcePath("/index.xhtml"));
		assertNotNull(index);
		assertNull(root.getResource(new ServerResourcePath("/foo")));
		assertNull(root.getResource(new ServerResourcePath("/foo/baz")));
		assertEquals(1, root.getPaths().size());
	}
	
	/**
	 * Test method for
	 * {@link org.richfaces.test.staging.ServerResourcesDirectory#getAsStream()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetAsStream() throws IOException {
		ClasspathServerResource resource = new ClasspathServerResource(
				"org/richfaces/test/resource.txt");
		InputStream inputStream = resource.getAsStream();
		assertNotNull(inputStream);
		try {
			byte[] buff = new byte[20];
			assertEquals(3, inputStream.read(buff));

		} finally {
			inputStream.close();			
		}
	}


	/**
	 * Test method for
	 * {@link org.richfaces.test.staging.ServerResourcesDirectory#getURL()}.
	 */
	@Test
	public void testGetURL() {
		ClasspathServerResource resource = new ClasspathServerResource(
		"org/richfaces/test/resource.txt");
		assertNotNull(resource.getURL());
	}

}
