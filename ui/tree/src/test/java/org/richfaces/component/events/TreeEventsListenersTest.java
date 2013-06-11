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

package org.richfaces.component.events;

import javax.faces.component.UIComponentBase;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.event.NodeSelectedListener;

import junit.framework.TestCase;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 09.04.2007
 * 
 */
public class TreeEventsListenersTest extends TestCase {
	private UIComponentBase source;

	protected void setUp() throws Exception {
		super.setUp();

		source = new UIComponentBase() {

			public String getFamily() {
				return null;
			}};
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();

		this.source = null;
	}
	
	public void testNodeExpandedListener() {
		NodeExpandedEvent event = new NodeExpandedEvent(source);
		
		ExpandListener listener = new ExpandListener();
		
		assertTrue(event.isAppropriateListener(listener));
		assertFalse(event.isAppropriateListener(new FacesListener(){
			
		}));

		event.processListener(listener);
		
		assertSame(event, listener.getEvent());
	}

	public void testNodeSelectedListener() {
		NodeSelectedEvent event = new NodeSelectedEvent(source, null);
		
		SelectListener listener = new SelectListener();
		
		assertTrue(event.isAppropriateListener(listener));
		assertFalse(event.isAppropriateListener(new FacesListener(){
			
		}));

		event.processListener(listener);
		
		assertSame(event, listener.getEvent());
	}
}

class ExpandListener implements NodeExpandedListener {

	private FacesEvent event;
	
	public void processExpansion(NodeExpandedEvent nodeExpandedEvent)
			throws AbortProcessingException {

		event = nodeExpandedEvent;
	}

	public FacesEvent getEvent() {
		return event;
	}
}

class SelectListener implements NodeSelectedListener {

	private FacesEvent event;

	public void processSelection(NodeSelectedEvent nodeSelectedEvent)
			throws AbortProcessingException {

		event = nodeSelectedEvent;
	}
	
	public FacesEvent getEvent() {
		return event;
	}
}
