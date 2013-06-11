package treeSwing;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.ajax4jsf.context.AjaxContext;
import org.richfaces.component.UITree;
import org.richfaces.event.NodeSelectedEvent;

import util.componentInfo.ComponentInfo;

public class TreeSwing {

	MutableTreeNode top;

	UITree tree;

	private String switchType = "ajax";

	private boolean rendered = true;

	private boolean showConnectingLines = false;

	private boolean useCustomIcons = false;

	private boolean disableKeyboardNavigation = false;

	public TreeSwing() {
		top = new DefaultMutableTreeNode("The Java Series");
		createNodes(top);
	}

	public void selectionListener(NodeSelectedEvent e) {
		UITree uiTree = (UITree) e.getComponent();
		Object rowKey = uiTree.getRowKey();
		MutableTreeNode node = (MutableTreeNode) uiTree.getRowData();
		if (node.isLeaf()) {
			uiTree.removeNode(rowKey);
		}

		AjaxContext ac = AjaxContext.getCurrentInstance();
		try {
			ac.addComponentToAjaxRender(uiTree);
		} catch (Exception exc) {
			System.err.print(exc.getMessage());
		}
	}

	private void createNodes(MutableTreeNode top) {
		MutableTreeNode category = null;
		MutableTreeNode book = null;

		category = new DefaultMutableTreeNode("Books for Java Programmers");
		top.insert(category, 0);

		// original Tutorial
		book = new DefaultMutableTreeNode(
				"The Java Tutorial: A Short Course on the Basics");
		category.insert(book, 0);

		// Tutorial Continued
		book = new DefaultMutableTreeNode(
				"The Java Tutorial Continued: The Rest of the JDK");
		category.insert(book, 1);

		// JFC Swing Tutorial
		book = new DefaultMutableTreeNode(
				"The JFC Swing Tutorial: A Guide to Constructing GUIs");
		category.insert(book, 2);

		// ...add more books for programmers...
		category = new DefaultMutableTreeNode("Books for Java Implementers");
		top.insert(category, 1);

		// VM
		book = new DefaultMutableTreeNode(
				"The Java Virtual Machine Specification");
		category.insert(book, 0);

		// Language Spec
		book = new DefaultMutableTreeNode("The Java Language Specification");
		category.insert(book, 1);
	}

	public void addCustomIcons() {
		if (useCustomIcons) {
			tree.setIcon("/pics/1.gif");
			tree.setIconCollapsed("/pics/3.gif");
			tree.setIconExpanded("/pics/4.gif");
			tree.setIconLeaf("/pics/2.gif");
		} else {
			tree.setIcon(null);
			tree.setIconCollapsed(null);
			tree.setIconExpanded(null);
			tree.setIconLeaf(null);
		}
	}
	
	public void add() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(tree);
	}

	public MutableTreeNode getTop() {
		return top;
	}

	public UITree getTree() {
		return tree;
	}

	public void setTree(UITree tree) {
		this.tree = tree;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isShowConnectingLines() {
		return showConnectingLines;
	}

	public void setShowConnectingLines(boolean showConnectingLines) {
		this.showConnectingLines = showConnectingLines;
	}

	public boolean isUseCustomIcons() {
		return useCustomIcons;
	}

	public void setUseCustomIcons(boolean useCustomIcons) {
		this.useCustomIcons = useCustomIcons;
	}

	public boolean isDisableKeyboardNavigation() {
		return disableKeyboardNavigation;
	}

	public void setDisableKeyboardNavigation(boolean disableKeyboardNavigation) {
		this.disableKeyboardNavigation = disableKeyboardNavigation;
	}
}