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

package org.richfaces.component;

import java.util.Iterator;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import junit.framework.TestCase;

/**
 * @author hans
 *
 */
public class TreeNodeTest extends TestCase {

	private final static Object NODE_ID = new Integer(0);
	
	protected TreeNode node = null;
	protected static final int TEST_CHILDREN_COUNT = 7;
	
	/**
	 * @param arg0
	 */
	public TreeNodeTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		node = new TreeNodeImpl();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		node = null;
	}
	
	public void testChildrenNew() {
		assertFalse(node.getChildren().hasNext());
	}
	
	public void testChildrenCount() {
		for (int i = 0; i < TEST_CHILDREN_COUNT; i++)
			node.addChild(new Integer(i), new TreeNodeImpl());
		Iterator it = node.getChildren();
		int count = 0;
		for (; it.hasNext(); it.next())
			count++;
		assertEquals(count, TEST_CHILDREN_COUNT);
	}
	
	public void testIsLeafNew() {
		assertTrue(node.isLeaf());
	}
	
	public void testIsLeaf() {
		node.addChild(NODE_ID, new TreeNodeImpl());
		assertFalse(node.isLeaf());
	}
	
	public void testParent() {
		TreeNode child = new TreeNodeImpl();
		node.addChild(NODE_ID, child);
		assertEquals(child.getParent(), node);
	}
	
	public void testGetChild() {
		TreeNode firstNode = new TreeNodeImpl();
		firstNode.setData("First Node");
		TreeNode secondNode = new TreeNodeImpl();
		secondNode.setData("Second Node");
		node.addChild(NODE_ID, firstNode);
		node.addChild("second", secondNode);
		node.addChild("third", new TreeNodeImpl());
		assertTrue(node.getChild("second").getData().equals("Second Node"));
	}

	public void testRemove() {
		assertTrue(node.isLeaf());
		node.addChild(NODE_ID, new TreeNodeImpl());
		assertFalse(node.isLeaf());
		node.removeChild(NODE_ID);
		assertTrue(node.isLeaf());
	}

}
