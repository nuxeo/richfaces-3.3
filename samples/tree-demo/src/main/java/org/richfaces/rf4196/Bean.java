package org.richfaces.rf4196;

import org.richfaces.component.UITree;

public class Bean {
	private UITree tree;
	
	public UITree getTree() {
		return tree;
	}
	
	public void setTree(UITree tree) {
		this.tree = tree;
	}
	
	public void rerenderNode() {
		tree.addNodeRequestKey(tree.getRowKey());
	}

	public void rerenderSubtree() {
		tree.addRequestKey(tree.getRowKey());
	}
	
	public void rerenderRoot() {
		tree.addRequestKey(null);
	}
	
}
