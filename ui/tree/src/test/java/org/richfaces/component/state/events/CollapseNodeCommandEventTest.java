/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
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

package org.richfaces.component.state.events;

import java.util.ArrayList;

import javax.faces.FacesException;
import javax.faces.event.FacesListener;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UITree;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeRowKey;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 14.04.2007
 * 
 */
public class CollapseNodeCommandEventTest extends AbstractAjax4JsfTestCase {

	private CollapseNodeCommandEvent event;
	private TreeRowKey treeRowKey;
	
	/**
	 * @param name
	 */
	public CollapseNodeCommandEventTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		
		ArrayList keyBase = new ArrayList();
		keyBase.add("string");
		keyBase.add(new Object());
		keyBase.add(new Long(-17));

		this.treeRowKey = new ListRowKey(keyBase);
		this.event = new CollapseNodeCommandEvent(application.createComponent(UITree.COMPONENT_TYPE), treeRowKey);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();

		this.event = null;
		this.treeRowKey = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.state.events.TreeStateCommandEvent#isAppropriateListener(javax.faces.event.FacesListener)}.
	 */
	public final void testIsAppropriateListenerFacesListener() {
		assertTrue((event.isAppropriateListener(new MockTreeStateCommandsListener())));
		assertFalse((event.isAppropriateListener(new FacesListener() {
			
		})));
	}

	/**
	 * Test method for {@link org.richfaces.model.state.events.TreeStateCommandEvent#processListener(javax.faces.event.FacesListener)}.
	 */
	public final void testProcessListenerFacesListener() {
		MockTreeStateCommandsListener listener = new MockTreeStateCommandsListener();
		
		event.processListener(listener);
		assertSame(treeRowKey, listener.getCollapseNode());
		assertFalse(listener.isExpandAll());
		assertFalse(listener.isCollapseAll());
		assertNull(listener.getExpandNode());
	}

	/**
	 * Test method for {@link org.richfaces.model.state.events.TreeStateCommandEvent#processListener(javax.faces.event.FacesListener)}.
	 */
	public final void testProcessListenerExceptionHandle() {
		MockTreeStateCommandsListener listener = new MockExceptionTreeStateCommandsListener();
		
		try {
			event.processListener(listener);

			fail();
		} catch (FacesException e) {
		}
	}
}
