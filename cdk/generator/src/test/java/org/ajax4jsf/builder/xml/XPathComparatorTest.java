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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class XPathComparatorTest extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCompare() throws Exception{
		String xml = "<faces-config>" + 
			"<component><test>blabla1</test></component>" +
			"<component><test>blabla</test></component>" +
			"<renderer><foo>blabla2</foo></renderer>" +
			"<component><test>blabla2</test></component>"
			+ "</faces-config>";
		InputStream in = new ByteArrayInputStream(xml.getBytes());
		XMLBody body = new XMLBody();
		body.loadXML(in);
		NodeList list = body.getByXpath("//component|//renderer");
		assertEquals(4, list.getLength());
		Node node0 = list.item(0);
		Node node1 = list.item(1);
		Node node2 = list.item(2);
		Node node3 = list.item(3);
		
		XPathComparator dummyComparator = new XPathComparator();
		assertEquals(0, dummyComparator.compare(node0, node1));
		assertEquals(0, dummyComparator.compare(node1, node2));
		assertEquals(0, dummyComparator.compare(node0, node2));
		assertEquals(0, dummyComparator.compare(node0, node3));
		assertEquals(0, dummyComparator.compare(node2, node3));
		assertEquals(0, dummyComparator.compare(node1, node0));
		assertEquals(0, dummyComparator.compare(node2, node1));
		assertEquals(0, dummyComparator.compare(node2, node0));
		
		XPathComparator simpleComparator = new XPathComparator("local-name()");
		assertEquals(0, simpleComparator.compare(node0, node1));
		assertEquals(0, simpleComparator.compare(node0, node3));
		assertTrue(simpleComparator.compare(node0, node2) < 0);
		assertTrue(simpleComparator.compare(node2, node1) > 0);
		
		XPathComparator advancedComparator = new XPathComparator("local-name()", "test/text()", "foo/text()");
		assertTrue(advancedComparator.compare(node0, node2) < 0);
		assertTrue(advancedComparator.compare(node2, node1) > 0);
		assertTrue(advancedComparator.compare(node0, node1) > 0);
		assertTrue(advancedComparator.compare(node1, node0) < 0);
		
		
	}
}
