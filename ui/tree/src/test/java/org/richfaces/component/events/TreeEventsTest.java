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

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.ajax4jsf.event.EventsQueue;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockMethodBinding;
import org.ajax4jsf.tests.MockViewRoot;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.event.AjaxSelectedEvent;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 08.04.2007
 * 
 */
public class TreeEventsTest extends AbstractAjax4JsfTestCase {

	private static final String NODE = "node";
	private UITree tree;
	private UITreeNode treeNode;
	
	public TreeEventsTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		UIViewRoot viewRoot = facesContext.getViewRoot();

		tree = (UITree) application.createComponent(UITree.COMPONENT_TYPE);
		tree.setNodeFace(NODE);
		viewRoot.getChildren().add(tree);
		
		treeNode = (UITreeNode) application.createComponent(UITreeNode.COMPONENT_TYPE);
		treeNode.setType(NODE);
		
		tree.getChildren().add(treeNode);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	
		this.tree = null;
		this.treeNode = null;
	}

	public void testPassThroughGenericEvent() {
		//just to be sure generic events pass through ok
		TreeEvents.invokeListenerBindings(treeNode, new FacesEvent(treeNode) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2196096358111753037L;

			public boolean isAppropriateListener(FacesListener listener) {
				return false;
			}

			public void processListener(FacesListener listener) {
				throw new AbortProcessingException();
			}
			
		}, facesContext);
	}
		
	public void testAjaxSelectedEvent() {
		MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
		tree.setAjaxSubmitSelection(true);
		treeNode.setAjaxSubmitSelection("inherit");
		
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setNodeSelectListener(binding);
		
		EventsQueue events = mockViewRoot.getAjaxEventsQueue();
		assertNotNull(events);
		assertEquals(0, events.size());

		AjaxSelectedEvent event = new AjaxSelectedEvent(treeNode, null);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);
		
		assertEquals(1, events.size());

		Object[][] args = binding.getInvocationArgs();
		assertEquals(1, args.length);
		assertEquals(1, args[0].length);
		assertSame(event, args[0][0]);
	}

	public void testAjaxSelectedEventListenersOff() {
		MockViewRoot mockViewRoot = (MockViewRoot) facesContext.getViewRoot();
		tree.setAjaxSubmitSelection(false);
		treeNode.setAjaxSubmitSelection("inherit");
		
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setNodeSelectListener(binding);
		
		EventsQueue events = mockViewRoot.getAjaxEventsQueue();
		assertNotNull(events);
		assertEquals(0, events.size());
		assertEquals(0, binding.getInvocationArgs().length);

		AjaxSelectedEvent event = new AjaxSelectedEvent(treeNode, null);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);
		
		assertNotNull(events);
		assertEquals(0, events.size());
		assertEquals(0, binding.getInvocationArgs().length);
	}
	
	
	
	public void testNodeExpansionEvent() {
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setChangeExpandListener(binding);
	
		NodeExpandedEvent event = new NodeExpandedEvent(treeNode);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);

		Object[][] args = binding.getInvocationArgs();
		assertEquals(1, args.length);
		assertEquals(1, args[0].length);
		assertSame(event, args[0][0]);
	}

	public void testNodeSelectionEvent() {
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setNodeSelectListener(binding);
	
		NodeSelectedEvent event = new NodeSelectedEvent(treeNode, null);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);

		Object[][] args = binding.getInvocationArgs();
		assertEquals(1, args.length);
		assertEquals(1, args[0].length);
		assertSame(event, args[0][0]);
	}

	public void testDropEvent() {
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setDropListener(binding);
	
		DropEvent event = new DropEvent(treeNode);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);

		Object[][] args = binding.getInvocationArgs();
		assertEquals(1, args.length);
		assertEquals(1, args[0].length);
		assertSame(event, args[0][0]);
	}
	
	public void testDragEvent() {
		MockMethodBinding binding = new MockMethodBinding();
		treeNode.setDragListener(binding);
	
		DragEvent event = new DragEvent(treeNode);
		TreeEvents.invokeListenerBindings(treeNode, event, facesContext);

		Object[][] args = binding.getInvocationArgs();
		assertEquals(1, args.length);
		assertEquals(1, args[0].length);
		assertSame(event, args[0][0]);
	}
}

