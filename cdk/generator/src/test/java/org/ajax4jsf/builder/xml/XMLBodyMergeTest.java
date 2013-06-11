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

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class XMLBodyMergeTest extends TestCase {

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

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodyMerge#add(org.w3c.dom.Node)}.
	 */
	public void testAddNode() {
		XMLBodyMerge merge = new XMLBodyMerge("//node()");
		
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodyMerge#add(org.ajax4jsf.builder.xml.XMLBody)}.
	 */
	public void testAddXMLBody() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodyMerge#getLength()}.
	 */
	public void testGetLength() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodyMerge#item(int)}.
	 */
	public void testItem() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.xml.XMLBodyMerge#getContent()}.
	 */
	public void testGetContent() {
		//fail("Not yet implemented");
	}

}
