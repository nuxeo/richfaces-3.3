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

package org.richfaces.component.state;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataComponentState;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.richfaces.component.UITree;
import org.richfaces.component.state.events.TreeStateCommandsListener;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeRange;
import org.richfaces.model.TreeRowKey;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com created 23.11.2006
 * 
 */
public class TreeState implements DataComponentState, TreeStateCommandsListener, StateHolder, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9083705369340888171L;

	enum NodeState {
		EXPANDED, COLLAPSED;
	}
	
	private class ExpansionVisitor implements DataVisitor {
		private UITree tree;

		public ExpansionVisitor(UITree tree) {
			super();
			
			this.tree = tree;
		}

		public void process(FacesContext context, Object rowKey, Object argument)
		throws IOException {
			tree.setRowKey(context, rowKey);
			if (!tree.isLeaf()) {
				addQueuedState((TreeRowKey) rowKey, NodeState.EXPANDED);
			}
		}
	};

	private boolean stopInCollapsed = false;

	private TreeRowKey selectedNode = null;

	private Set<TreeRowKey> expandedNodes = new HashSet<TreeRowKey>();

	private Map<TreeRowKey, NodeState> queuedNodeStates = new HashMap<TreeRowKey, NodeState>();

	public TreeState() {
		super();
	}
	
	public TreeState(boolean stopInCollapsed) {
		super();
		this.stopInCollapsed = stopInCollapsed;
	}

	public boolean isExpanded(TreeRowKey rowKey) {
		if (rowKey == null) {
			return true;
		}

		return expandedNodes.contains(rowKey) || 
			NodeState.EXPANDED.equals(queuedNodeStates.get(rowKey));
	}

	public boolean isSelected(TreeRowKey rowKey) {
		return (rowKey == null && selectedNode == null) || (selectedNode != null && selectedNode.equals(rowKey));
	}

	public TreeRowKey getSelectedNode() {
		return selectedNode;
	}
	
	public void setSelected(TreeRowKey rowKey) {
		selectedNode = rowKey;
	}

	private boolean _transient;

	public Range getRange() {
		if (stopInCollapsed) {
			return new TreeRange() {

				public boolean processChildren(TreeRowKey rowKey) {
					if (rowKey == null) {
						return true;
					}

					return expandedNodes.contains(rowKey);
				}

				public boolean processNode(TreeRowKey rowKey) {
					return true;
				}
			};
		} else {
			return TreeRange.RANGE_UNCONSTRAINED;
		}
	}

	public boolean isTransient() {
		return _transient;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] _state = (Object[]) state;
		expandedNodes = (Set) _state[0];
		queuedNodeStates = (Map) _state[1];
		_transient = ((Boolean) _state[2]).booleanValue();
		stopInCollapsed = ((Boolean) _state[3]).booleanValue();
		selectedNode = (TreeRowKey) _state[4];
	}

	public Object saveState(FacesContext context) {
		Object[] state = new Object[5];
		state[0] = expandedNodes;
		state[1] = queuedNodeStates;
		state[2] = Boolean.valueOf(_transient);
		state[3] = Boolean.valueOf(stopInCollapsed);
		state[4] = selectedNode;
		return state;
	}

	public void setTransient(boolean newTransientValue) {
		this._transient = newTransientValue;
	}

	public boolean isStopInCollapsed() {
		return stopInCollapsed;
	}

	public void setStopInCollapsed(boolean stopInCollapsed) {
		this.stopInCollapsed = stopInCollapsed;
	}

	public void expandAll(UITree tree) throws IOException {
		queuedNodeStates = new HashMap<TreeRowKey, NodeState>();
		
		tree.walk(FacesContext.getCurrentInstance(), 
				new ExpansionVisitor(tree), TreeRange.RANGE_UNCONSTRAINED, null, null);
	}

	public void collapseAll(UITree tree) throws IOException {
		queuedNodeStates = new HashMap<TreeRowKey, NodeState>();
		
		for (TreeRowKey key : expandedNodes) {
			addQueuedState(key, NodeState.COLLAPSED);
		}
	}

	public void addQueuedState(TreeRowKey rowKey, NodeState state) {
		if (NodeState.EXPANDED.equals(state)) {
			if (!expandedNodes.contains(rowKey)) {
				queuedNodeStates.put(rowKey, NodeState.EXPANDED);
			}
		} else {
			queuedNodeStates.put(rowKey, NodeState.COLLAPSED);
		}
	}

	public void collapseNode(UITree tree, TreeRowKey rowKey) throws IOException {
		addQueuedState(rowKey, NodeState.COLLAPSED);
	}
	
	public void expandNode(UITree tree, TreeRowKey rowKey) throws IOException {
		Object oldRowKey = tree.getRowKey();
		
		tree.setRowKey(rowKey);
		
		if (tree.isRowAvailable()) {
			if (!tree.isLeaf()) {
				TreeRowKey key = rowKey;
				
				while (key != null && key.depth() != 0) {
					addQueuedState(key, NodeState.EXPANDED);
					key = (TreeRowKey) tree.getParentRowKey(key);
				};
				
			} else {
				//TODO debug log
			}
		} else {
			FacesMessage message = new FacesMessage("Row key: " + rowKey
					+ " isn't available!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext context = FacesContext.getCurrentInstance();
			
			context.addMessage(tree.getBaseClientId(context), message);
		}
		
		tree.setRowKey(oldRowKey);
	}
	
	private void transferState(TreeRowKey key, NodeState state) {
		if (state != null) {
			if (NodeState.EXPANDED.equals(state)) {
				expandedNodes.add(key);
			} else {
				expandedNodes.remove(key);
			}
		}
	}
	
	public void transferQueuedNodes(TreeRowKey rootKey) {
		Iterator<Entry<TreeRowKey, NodeState>> itr = queuedNodeStates.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<TreeRowKey, NodeState> entry = itr.next();
			TreeRowKey rowKey = entry.getKey();
			
			if (rootKey == null || rootKey.depth() == 0 || rootKey.isSubKey(rowKey)) {
				transferState(rowKey, entry.getValue());
			
				itr.remove();
			}
		}

		if (queuedNodeStates.isEmpty()) {
			queuedNodeStates = new HashMap<TreeRowKey, NodeState>();
		}
	}
	
	public void makeExpanded(TreeRowKey key) {
		queuedNodeStates.remove(key);
		expandedNodes.add(key);
	}

	public void makeCollapsed(TreeRowKey key) {
		queuedNodeStates.remove(key);
		expandedNodes.remove(key);
	}
	
	public TreeState getSubState(TreeRowKey rowKey) {
		TreeState subTreeState = new TreeState(this.stopInCollapsed);
		if (getSelectedNode() != null && rowKey.equals(getSelectedNode())) {
			subTreeState.setSelected(rowKey);
		}
		// FIXME: whether it is needed?
		subTreeState._transient = _transient;
		
		int subKeyDepth = rowKey.depth() - 1;

		for (Iterator<TreeRowKey> iter = this.expandedNodes.iterator(); iter.hasNext(); ) {
			TreeRowKey nextKey = iter.next();
			if (nextKey != null && rowKey.isSubKey(nextKey)) {
				subTreeState.expandedNodes.add(nextKey.getSubKey(subKeyDepth));
			}
		}
		
		for (Iterator<Entry<TreeRowKey, NodeState>> iter = this.queuedNodeStates.entrySet().iterator();
			iter.hasNext(); ) {
		
			Entry<TreeRowKey, NodeState> entry = iter.next();

			TreeRowKey nextKey = entry.getKey();
			
			if (nextKey != null && rowKey.isSubKey(nextKey)) {
				subTreeState.queuedNodeStates.put(nextKey.getSubKey(subKeyDepth), 
					entry.getValue());
			}
		}
		
		return subTreeState;
	}
	
	public void clearSubState(TreeRowKey rowKey) {
		if (rowKey.equals(getSelectedNode())) {
			setSelected(null);
		}

		if (rowKey.getPath().equals("null")) { // root node
			this.expandedNodes.clear();
			this.queuedNodeStates = new HashMap<TreeRowKey, NodeState>();
		} else {
			// collect nodes to clean up
			for (Iterator<TreeRowKey> itr = this.expandedNodes.iterator(); itr.hasNext(); ) {
				TreeRowKey nextKey = itr.next();
				if (nextKey != null && rowKey.isSubKey(nextKey)) {
					itr.remove();
				}
			}
			
			for (Iterator<Entry<TreeRowKey, NodeState>> itr = 
				this.queuedNodeStates.entrySet().iterator(); itr.hasNext(); ) {

				TreeRowKey nextKey = itr.next().getKey();
				if (nextKey != null && rowKey.isSubKey(nextKey)) {
					itr.remove();
				}
			}
		}
	}
	
	public void mergeSubState(TreeRowKey rowKey, TreeState subState) {
		Iterator<TreeRowKey> iter = subState.expandedNodes.iterator();
		while (iter != null && iter.hasNext()) {
			TreeRowKey key = iter.next().getSubKey(1);
			if (key.depth() > 0) {
			    expandedNodes.add(new ListRowKey((ListRowKey)rowKey, (ListRowKey)key));
			} else if (!expandedNodes.contains(rowKey)) {
			    expandedNodes.add(rowKey);
			}
		}

		Iterator<Entry<TreeRowKey, NodeState>> sItr = subState.queuedNodeStates.entrySet().iterator();
		while (sItr.hasNext()) {
			Entry<TreeRowKey, NodeState> entry = sItr.next();
			TreeRowKey key = entry.getKey().getSubKey(1);
			
			TreeRowKey newKey;
			
			if (key.depth() > 0) {
				newKey = new ListRowKey((ListRowKey)rowKey, (ListRowKey)key);
			} else {
				newKey = rowKey;
			}

			addQueuedState(newKey, entry.getValue());
		}
	}
}
