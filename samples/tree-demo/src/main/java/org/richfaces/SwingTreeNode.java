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

package org.richfaces;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * Created 01.11.2007
 * @author Nick Belaevski
 * @since 3.2
 */

public class SwingTreeNode implements TreeNode {

	private TreeNode parent;
	private Vector<SwingTreeNode> children = new Vector<SwingTreeNode>();
	private boolean allowsChildren = true;
	private Object data;
	
	public SwingTreeNode(Object data) {
		super();
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration children() {
		if (getAllowsChildren()) {
			return children.elements();
		} else {
			throw new IllegalStateException();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return allowsChildren;
	}

	public void setAllowsChildren(boolean allowsChildren) {
		this.allowsChildren = allowsChildren;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(int childIndex) {
		if (getAllowsChildren()) {
			return children.get(childIndex);
		} else {
			throw new IllegalStateException();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		return children.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(TreeNode node) {
		if (getAllowsChildren()) {
			return children.indexOf(node);
		} else {
			throw new IllegalStateException();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf() {
		if (getAllowsChildren()) {
			return children.isEmpty();
		} else {
			throw new IllegalStateException();
		}
	}

	public void addChild(SwingTreeNode node) {
		if (getAllowsChildren()) {
			node.setParent(this);
			children.add(node);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public Object getData() {
		return data;
	}
}
