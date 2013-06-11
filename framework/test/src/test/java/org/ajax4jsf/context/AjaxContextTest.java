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

package org.ajax4jsf.context;

import java.net.URL;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

public class AjaxContextTest extends AbstractAjax4JsfTestCase {

	public AjaxContextTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurrentInstance() {
		AjaxContext ajaxContext2 = AjaxContext.getCurrentInstance();
		assertSame(ajaxContext, ajaxContext2);
	}

	public void testGetCurrentInstanceFacesContext() {
		this.ajaxContext = null;
		request.removeAttribute(AjaxContext.AJAX_CONTEXT_KEY);
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader loader = new ClassLoader(contextClassLoader){
			@Override
			public URL getResource(String name) {
				if(AjaxContext.SERVICE_RESOURCE.equals(name)){
					return super.getResource("META-INF/ajaxContext.txt");
				} else {
					return super.getResource(name);
				}
			}
		};
		Thread.currentThread().setContextClassLoader(loader);
		AjaxContext ajaxContext2 = AjaxContext.getCurrentInstance(facesContext);
		assertSame(MockAjaxContext.class,ajaxContext2.getClass());
	}

}
