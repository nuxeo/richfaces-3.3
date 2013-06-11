package tTree;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.context.AjaxContext;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.richfaces.model.TreeRowKey;

public class TTreeDND {
	private static final String DATA_PATH = "org/richfaces/simpleTreeData.properties";

	private TreeNode<String> treeNodeLeft;
	private UITree leftTree;
	private String leftSelectedNodeTitle;
	private String rightSelectedNodeTitle;
	private TreeNode<String> treeNodeRight;
	private UITree rightTree;

	private void addNodes(String path, TreeNode<String> node,
			Properties properties) {
		boolean end = false;
		int counter = 1;
		while (!end) {
			String key = path != null ? path + '.' + counter : String
					.valueOf(counter);
			String value = properties.getProperty(key);
			if (value != null) {
				TreeNodeImpl<String> nodeImpl = new TreeNodeImpl<String>();
				nodeImpl.setData(value);
				node.addChild(new Integer(counter), nodeImpl);
				addNodes(key, nodeImpl, properties);
				counter++;
			} else {
				end = true;
			}
		}
	}

	private TreeNode<String> initPaneTree() {
		TreeNode<String> rootNode = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		InputStream dataStream = this.getClass().getClassLoader()
				.getResourceAsStream(DATA_PATH);

		try {
			Properties properties = new Properties();
			properties.load(dataStream);
			rootNode = new TreeNodeImpl<String>();
			addNodes(null, rootNode, properties);
		} catch (IOException e) {

			throw new FacesException(e.getMessage(), e);

		} finally {
			if (dataStream != null) {
				try {
					dataStream.close();
				} catch (IOException e) {
					externalContext.log(e.getMessage(), e);
				}
			}
		}
		return rootNode;
	}

	private Object getNewId(TreeNode parentNode) {
		Map<Object, TreeNode> childs = new HashMap<Object, TreeNode>();
		Iterator<Map.Entry<Object, TreeNode>> iter = parentNode.getChildren();
		while (iter != null && iter.hasNext()) {
			Map.Entry<Object, TreeNode> entry = iter.next();
			childs.put(entry.getKey(), entry.getValue());
		}

		Integer index = 1;
		while (childs.containsKey(index)) {
			index++;
		}
		return index;
	}

	public void onDrop(DropEvent dropEvent) {
		System.out.println("onDrop occured.");
		System.out.println("DragValue: " + dropEvent.getDragValue());
		System.out.println("DropValue: " + dropEvent.getDropValue());

		// resolve drag source attributes
		UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent
				.getDraggableSource()
				: null;
		UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
		TreeRowKey dragNodeKey = (dropEvent.getDragValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent
				.getDragValue()
				: null;

		// resolve drag destination attributes
		UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent
				.getSource()
				: null;
		UITree destTree = destNode != null ? destNode.getUITree()
				: (UITree) dropEvent.getComponent();
		TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent
				.getDropValue()
				: null;

		FacesContext context = FacesContext.getCurrentInstance();

		if (dropNodeKey != null) {
			// add destination node for rerender
			destTree.addRequestKey(dropNodeKey);

			Object state = null;
			TreeNode draggedNode = null;
			if (dragNodeKey != null) { // Drag from this or other tree
				draggedNode = srcTree.getModelTreeNode(dragNodeKey);

				TreeNode parentNode = draggedNode.getParent();
				// 1. remove node from tree
				state = srcTree.removeNode(dragNodeKey);
				// 2. add parent for rerender
				Object rowKey = srcTree.getTreeNodeRowKey(parentNode);
				srcTree.addRequestKey(rowKey);
			} else if (dropEvent.getDragValue() != null) { // Drag from some
				// drag source
				draggedNode = new TreeNodeImpl<String>();
				draggedNode.setData(dropEvent.getDragValue().toString());
			}

			// generate new node id
			Object id = getNewId(destTree.getTreeNode(dropNodeKey));
			destTree.addNode(dropNodeKey, draggedNode, id, state);
		}

		AjaxContext ac = AjaxContext.getCurrentInstance();
		// Add destination tree to reRender
		try {
			ac.addComponentToAjaxRender(destTree);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		// Add source tree to reRender
		try {
			ac.addComponentToAjaxRender(srcTree);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		System.out.println("+++++");
	}

	public void onExpand(NodeExpandedEvent event) {
		UITree tree = (UITree) event.getComponent();
		System.out.println("Tree ('" + tree.getId() + "') node "
				+ (tree.isExpanded() ? "expanded" : "collapsed") + " "
				+ tree.getRowKey());
	}

	public void onDrag(DragEvent dragEvent) {
		System.out.println("onDrag occured.");
		System.out.println("DragValue: " + dragEvent.getDragValue());
		System.out.println("DropValue: " + dragEvent.getDropValue());
	}

	public void processLSelection(NodeSelectedEvent event) {
		UITree tree = (UITree) event.getComponent();
		if (tree != null) {
			leftSelectedNodeTitle = (String) tree.getRowData();
		}
	}

	public void processRSelection(NodeSelectedEvent event) {
		UITree tree = (UITree) event.getComponent();
		if (tree != null) {
			rightSelectedNodeTitle = (String) tree.getRowData();
		}
	}

	public TreeNode<String> getTreeNodeLeft() {
		if (treeNodeLeft == null) {
			treeNodeLeft = initPaneTree();
		}
		return treeNodeLeft;
	}

	public void setTreeNodeLeft(TreeNode<String> treeNodeLeft) {
		this.treeNodeLeft = treeNodeLeft;
	}

	public UITree getLeftTree() {
		return leftTree;
	}

	public void setLeftTree(UITree leftTree) {
		this.leftTree = leftTree;
	}

	public String getRightSelectedNodeTitle() {
		return rightSelectedNodeTitle;
	}

	public void setRightSelectedNodeTitle(String rightSelectedNodeTitle) {
		this.rightSelectedNodeTitle = rightSelectedNodeTitle;
	}

	public String getLeftSelectedNodeTitle() {
		return leftSelectedNodeTitle;
	}

	public void setLeftSelectedNodeTitle(String leftSelectedNodeTitle) {
		this.leftSelectedNodeTitle = leftSelectedNodeTitle;
	}

	public UITree getRightTree() {
		return rightTree;
	}

	public void setRightTree(UITree rightTree) {
		this.rightTree = rightTree;
	}

	public TreeNode<String> getTreeNodeRight() {
		if (treeNodeRight == null) {
			treeNodeRight = initPaneTree();
		}
		return treeNodeRight;
	}

	public void setTreeNodeRight(TreeNode<String> treeNodeRight) {
		this.treeNodeRight = treeNodeRight;
	}
}
