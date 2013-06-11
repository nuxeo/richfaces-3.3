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

package org.richfaces.utils;

import javax.faces.FacesException;

import org.ajax4jsf.renderkit.RendererBase;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 * 
 */
public class TemplateLoaderTest extends TestCase {

	/**
	 * @param name
	 */
	public TemplateLoaderTest(String name) {
		super(name);
	}

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
	 * {@link org.richfaces.utils.TemplateLoader#loadTemplate(java.lang.String)}.
	 */
	public final void testLoadTemplate() {
		boolean exception = false;
		try {
			TemplateLoader.loadTemplate("xxx.xxx.xx.TestClass");
		} catch (FacesException e) {
			exception = true;
			assertTrue(e.getCause() instanceof ClassNotFoundException);
		}

		assertTrue(exception);

		exception = false;

		try {
			TemplateLoader.loadTemplate(RendererBase.class.getName());
		} catch (FacesException e) {
			exception = true;
			assertTrue(e.getCause() instanceof InstantiationException);
		}

		assertTrue(exception);

		RendererBase template = TemplateLoader
				.loadTemplate("org.richfaces.renderkit.html.ScrollableDataTableFooterCellRenderer");

		assertNotNull(template);

	}

}
