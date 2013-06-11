/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;

import junit.framework.TestCase;

import org.ajax4jsf.builder.config.ParsingException;

/**
 * @author shura
 * 
 */
public class XMLBodyTest extends TestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link org.ajax4jsf.builder.xml.XMLBody#loadXML(java.io.InputStream)}.
	 * 
	 * @throws ParsingException
	 */
	public void testLoadXML() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
				+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
				+ "<faces-config>\n"
				+ "	<component>\n"
				+ "		<component-type>org.ajax4jsf.ajax.Test</component-type>\n"
				+ "		<component-class>org.ajax4jsf.ajax.html.Test</component-class>\n"
				+ "\n" + "		<component-extension>\n"
				+ "			<component-family>org.ajax4jsf.Test</component-family>\n"
				+ "			<renderer-type>org.ajax4jsf.Test</renderer-type>\n"
				+ "		</component-extension>\n" + "	</component>\n"
				+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
	}

	/**
	 * Test method for
	 * {@link org.ajax4jsf.builder.xml.XMLBody#isRootName(java.lang.String)}.
	 * 
	 * @throws ParsingException
	 */
	public void testIsRootName() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
				+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
				+ "<faces-config>\n" + "	<component>\n" + "	</component>\n"
				+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		assertTrue(body.isRootName("faces-config"));
		assertEquals("faces-config", body.getDoctype());
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBody#getDoctype()}.
	 * 
	 * @throws ParsingException
	 */
	public void testGetDoctype() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
				+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
				+ "<faces-config>\n" + "	<component>\n" + "	</component>\n"
				+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		assertEquals(
				"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN",
				body.getPiblicId());
	}

	/**
	 * Test method for
	 * {@link org.ajax4jsf.builder.xml.XMLBody#getRootNameSpace()}.
	 * 
	 * @throws ParsingException
	 */
	public void testGetRootNameSpace() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<project  xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
				+ "  <modelVersion>4.0.0</modelVersion>\n" + "</project>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		assertNull( body.getRootTypeName());
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBody#getContent()}.
	 * 
	 * @throws ParsingException
	 */
	public void testGetContent() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<project  xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">"
				+ "<modelVersion>4.0.0</modelVersion></project>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		assertEquals("<modelVersion>4.0.0</modelVersion>", body.getContent().trim());
	}

	public void testGetContentXpath() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
				+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
				+ "<faces-config>\n" + "	<component>blabla</component>\n"
				+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		try {
			assertEquals(
					"<component>blabla</component>",
					body.getContent("/faces-config/component").trim());
		} catch (ParsingException e) {
			e.printStackTrace();
			assertTrue(e.getMessage(),false);
		}
	}

	public void testGetContentUnique() throws ParsingException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
				+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
				+ "<faces-config>\n" + "	<component><test>blabla</test></component><component><test>blabla</test></component><component><test>blabla2</test></component>\n"
				+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		try {
			String expected = "<component><test>blabla</test></component><component><test>blabla2</test></component>";
			String actual = body.getContentUnique("/faces-config/component", "test/text()", new HashSet<String>()).replaceAll("\\s", "");
			
			assertEquals(
					expected,
					actual);
		} catch (ParsingException e) {
			e.printStackTrace();
			assertTrue(e.getMessage(),false);
		}
	}
	
	public void testGetContentWithNS() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<f:faces-config xmlns=\"http://foo.bar\" xmlns:f=\"http://foo.baz\">" 
			+ "<f:component><test f:foo=\"xxx\">blabla</test></f:component>"
			+ "</f:faces-config>";
	InputStream in = new ByteArrayInputStream(xml.getBytes());
	XMLBody body = new XMLBody();
	body.loadXML(in,true);
	try {
		assertEquals(
				"<f:component xmlns:f=\"http://foo.baz\"><test f:foo=\"xxx\" xmlns=\"http://foo.bar\">blabla</test></f:component>",
				body.getContent().replace("\r", "\n").replace("\n", ""));
	} catch (ParsingException e) {
		e.printStackTrace();
		assertTrue(e.getMessage(),false);
	}
		
	}
}
