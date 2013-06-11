/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.model.impl.expressive;

import java.util.Collections;
import java.util.Map;

import org.richfaces.model.impl.expressive.JavaBeanWrapper;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class JavaBeanWrapperTest extends TestCase {

	
	private JavaBeanWrapper wrapper;
	final Boolean test = Boolean.TRUE;
	final Map props = Collections.singletonMap("true", test);
	/**
	 * @param name
	 */
	public JavaBeanWrapperTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		wrapper = new JavaBeanWrapper(test, props);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		wrapper = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.JavaBeanWrapper#getProperty(java.lang.String)}.
	 */
	public void testGetProperty() {
		assertEquals(test, wrapper.getProperty("true"));
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.JavaBeanWrapper#getWrappedObject()}.
	 */
	public void testGetWrappedObject() {
		assertEquals(test, wrapper.getWrappedObject());
	}

}
