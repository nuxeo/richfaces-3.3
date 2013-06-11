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

import java.io.IOException;

import org.richfaces.component.UITree;
import org.richfaces.model.TreeRowKey;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 14.04.2007
 * 
 */
class MockTreeStateCommandsListener implements TreeStateCommandsListener {

	private boolean collapseAll;
	private boolean expandAll;
	private TreeRowKey collapseNode;
	private TreeRowKey expandNode;

	public void collapseAll(UITree tree) throws IOException {
		this.collapseAll = true;
	}

	public void collapseNode(UITree tree, TreeRowKey rowKey) throws IOException {
		this.collapseNode = rowKey;
	}

	public void expandAll(UITree tree) throws IOException {
		this.expandAll = true;
	}

	public void expandNode(UITree tree, TreeRowKey rowKey) throws IOException {
		this.expandNode = rowKey;
	}

	public boolean isCollapseAll() {
		return collapseAll;
	}

	public boolean isExpandAll() {
		return expandAll;
	}

	public TreeRowKey getCollapseNode() {
		return collapseNode;
	}

	public TreeRowKey getExpandNode() {
		return expandNode;
	}

}

class MockExceptionTreeStateCommandsListener extends MockTreeStateCommandsListener {

	public void collapseAll(UITree tree) throws IOException {
		throw new IOException();
	}

	public void collapseNode(UITree tree, TreeRowKey rowKey) throws IOException {
		throw new IOException();
	}

	public void expandAll(UITree tree) throws IOException {
		throw new IOException();
	}

	public void expandNode(UITree tree, TreeRowKey rowKey) throws IOException {
		throw new IOException();
	}
	
}
