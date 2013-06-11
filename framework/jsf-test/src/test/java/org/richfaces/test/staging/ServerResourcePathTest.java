/**
 * 
 */
package org.richfaces.test.staging;

import static org.junit.Assert.*;

import org.junit.Test;
import org.richfaces.test.staging.ServerResourcePath;

/**
 * @author asmirnov
 *
 */
public class ServerResourcePathTest {

	/**
	 * Test method for {@link org.richfaces.test.staging.ServerResourcePath#ServerResourcePath(java.lang.String)}.
	 */
	@Test
	public void testRootPath() {
		ServerResourcePath path = new ServerResourcePath("/");
		assertNull(path.getNextPath());
		assertNull(path.getNextElementName());
		assertTrue(path.isFile());
		assertEquals("/", path.toString());
	}

	/**
	 * Test method for {@link org.richfaces.test.staging.ServerResourcePath#ServerResourcePath(java.lang.String)}.
	 */
	@Test
	public void testWebInfPath() {
		ServerResourcePath path = ServerResourcePath.WEB_INF;
		assertNotNull(path.getNextPath());
		assertNotNull(path.getNextElementName());
		assertFalse(path.isFile());
		assertEquals("WEB-INF/", path.getNextElementName());
		assertEquals("/WEB-INF/", path.toString());
		path = path.getNextPath();
		assertNotNull(path);
		assertTrue(path.isFile());
		path = path.getNextPath();
		assertNull(path);
	}

	/**
	 * Test method for {@link org.richfaces.test.staging.ServerResourcePath#ServerResourcePath(java.lang.String)}.
	 */
	@Test
	public void testWebInfTrainingSlashPath() {
		ServerResourcePath path = new ServerResourcePath("/WEB-INF/");
		assertNotNull(path.getNextPath());
		assertNotNull(path.getNextElementName());
		assertFalse(path.isFile());
		assertEquals("WEB-INF/", path.getNextElementName());
		assertEquals("/WEB-INF/", path.toString());
		path = path.getNextPath();
		assertNotNull(path);
		assertTrue(path.isFile());
		path = path.getNextPath();
		assertNull(path);
	}

	
	/**
	 * Test method for {@link org.richfaces.test.staging.ServerResourcePath#ServerResourcePath(java.lang.String)}.
	 */
	@Test
	public void testWebXmlPath() {
		ServerResourcePath path = ServerResourcePath.WEB_XML;
		assertFalse(path.isFile());
		assertEquals("WEB-INF/", path.getNextElementName());
		assertEquals("/WEB-INF/web.xml", path.toString());
		path = path.getNextPath();
		assertNotNull(path.getNextElementName());
		assertFalse(path.isFile());
		assertEquals("web.xml", path.getNextElementName());
		assertEquals("/web.xml", path.toString());
		path = path.getNextPath();
		assertNotNull(path);
		assertTrue(path.isFile());
		path = path.getNextPath();
		assertNull(path);
	}


}
