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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ajax4jsf.builder.config.ParsingException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class XMLBodySerializerTest extends TestCase {

	private XMLBodySerializer serializer;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		serializer = new XMLBodySerializer();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		serializer = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodySerializer#serialize(org.w3c.dom.NodeList, org.w3c.dom.Document)}.
	 */
	public void testSerialize() throws ParsingException{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<!DOCTYPE faces-config PUBLIC \"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN\"\n"
			+ "                              \"http://java.sun.com/dtd/web-facesconfig_1_1.dtd\">\n"
			+ "<faces-config>\n" + "	<component><test>blabla</test></component><component><test>blabla</test></component><component><test>blabla2</test></component>\n"
			+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);

		NodeList singleElementList = body.getByXpath("/faces-config");
		assertEquals(1, singleElementList.getLength());
		Node node = singleElementList.item(0);
		assertNotNull(node);
		assertEquals("faces-config", node.getNodeName());
		String actual = serializer.serialize(singleElementList, node.getOwnerDocument()).replaceAll("\\s", "");
		String expected = "<faces-config><component><test>blabla</test></component><component><test>blabla</test></component><component><test>blabla2</test></component></faces-config>";
		assertEquals(expected, actual);
		
		NodeList children = node.getChildNodes();
		actual = serializer.serialize(children, node.getOwnerDocument()).replaceAll("\\s", "");
		expected = "<component><test>blabla</test></component><component><test>blabla</test></component><component><test>blabla2</test></component>";
		assertEquals(expected, actual);
		
	
	}

}
