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
package org.ajax4jsf.application;

import java.net.URL;
import java.net.URLClassLoader;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

import junit.framework.TestCase;

/**
 * @author asmirnov
 *
 */
public class ComponentLoaderTest extends TestCase {

	private ComponentsLoaderImpl loader;
	private ClassLoader contextClassLoader;
	private URLClassLoader classLoader;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		contextClassLoader = Thread.currentThread().getContextClassLoader();
        classLoader = new URLClassLoader(new URL[0],
                this.getClass().getClassLoader());
		Thread.currentThread().setContextClassLoader(classLoader);
		loader = new ComponentsLoaderImpl();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		loader = null;
		Thread.currentThread().setContextClassLoader(contextClassLoader);
		contextClassLoader = null;
	}

	/**
	 * Test method for {@link org.ajax4jsf.application.ComponentsLoaderImpl#createComponent(java.lang.String)}.
	 */
	public void testCreateComponent() {
		UIComponent input = loader.createComponent(UIInput.class.getName());
		assertNotNull(input);
		assertEquals(UIInput.class, input.getClass());
	}


	public void testGetLoader() throws Exception {
		assertSame(classLoader,loader.getClassLoader());
	}
}
