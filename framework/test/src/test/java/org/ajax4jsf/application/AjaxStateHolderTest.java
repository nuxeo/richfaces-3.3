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

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * @author asmirnov
 *
 */
public class AjaxStateHolderTest extends AbstractAjax4JsfTestCase {

	/**
	 * @param name
	 */
	public AjaxStateHolderTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.ajax4jsf.application.AjaxStateHolder#getInstance(javax.faces.context.FacesContext)}.
	 */
	public void testGetInstance() {
		StateHolder ajaxStateHolder = AjaxStateHolder.getInstance(facesContext);
		assertNotNull(ajaxStateHolder);
		StateHolder ajaxStateHolder2 = AjaxStateHolder.getInstance(facesContext);
		assertSame(ajaxStateHolder, ajaxStateHolder2);
	}

	/**
	 * Test method for {@link org.ajax4jsf.application.AjaxStateHolder#getState(java.lang.String, String)}.
	 */
	public void testGetState() {
		Object state = new Object();
		Object state2 = new Object();
		StateHolder ajaxStateHolder = AjaxStateHolder.getInstance(facesContext);
		assertNull(ajaxStateHolder.getState(facesContext, "foo", "_id1"));
		ajaxStateHolder.saveState(facesContext, "foo", "_id1", new Object[]{state});
		ajaxStateHolder.saveState(facesContext, "foo", "_id2", new Object[]{state2});
		assertNull(ajaxStateHolder.getState(facesContext, "bar", "_id1"));
		assertSame(state2,ajaxStateHolder.getState(facesContext, "foo",null));
		assertSame(state,ajaxStateHolder.getState(facesContext, "foo","_id1"));
		assertSame(state,ajaxStateHolder.getState(facesContext, "foo","_id3"));
		Object state3 = new Object();
		Object state4 = new Object();
		ajaxStateHolder.saveState(facesContext, "bar", "_id1", new Object[]{state3});
		ajaxStateHolder.saveState(facesContext, "bar", "_id2", new Object[]{state4});
		assertSame(state3,ajaxStateHolder.getState(facesContext, "bar","_id1"));
		assertSame(state,ajaxStateHolder.getState(facesContext, "foo","_id3"));
	}


}
