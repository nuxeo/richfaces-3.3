/**
 * 
 */
package org.richfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
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

/**
 * @author dmorozov
 * 
 */
public class TreeDndBean extends TreeContainer {
    private static final String DATA_PATH1 = "org/richfaces/simpleTreeData.properties";

    private TreeNode<String> treeNodeLeft;

    private TreeNode<String> treeNodeRight;

    private String leftSelectedNodeTitle;

    private String rightSelectedNodeTitle;

    private UITree leftTree;

    private UITree rightTree;

    /**
     * Helper tree model creation method from properties object
     * 
     * @param path node path
     * @param node parent node
     * @param properties properties object
     */
    private void addNodes(String path, TreeNode<String> node, Properties properties) {
	boolean end = false;
	int counter = 1;
	while (!end) {
	    String key = path != null ? path + '.' + counter : String.valueOf(counter);
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

    /**
     * Init sample tree model
     * 
     * @return tree model
     */
    private TreeNode<String> initPaneTree() {
	TreeNode<String> rootNode = null;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesContext.getExternalContext();

	InputStream dataStream = this.getClass().getClassLoader().getResourceAsStream(DATA_PATH1);

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

    /**
     * Left tree demo selection handler.
     * @param event node selection event
     */
    public void processLSelection(NodeSelectedEvent event) {
	UITree tree = (UITree) event.getComponent();
	leftSelectedNodeTitle = (String) tree.getRowData();
	System.out.println("OnSelect LEFT tree: " + leftSelectedNodeTitle);
    }

    /**
     * Right tree demo selection handler.
     * @param event node selection event
     */
    public void processRSelection(NodeSelectedEvent event) {
	UITree tree = (UITree) event.getComponent();
	if (tree != null) {
		rightSelectedNodeTitle = (String) tree.getRowData();
		System.out.println("OnSelect RIGHT tree: " + rightSelectedNodeTitle);
	}
    }

    /**
     * Left tree value binding
     * @return the treeNodeLeft
     */
    public TreeNode<String> getTreeNodeLeft() {
	if (treeNodeLeft == null) {
	    treeNodeLeft = initPaneTree();
	}
	return treeNodeLeft;
    }

    /**
     * Left tree value binding
     * @param treeNodeLeft
     *                the treeNodeLeft to set
     */
    public void setTreeNodeLeft(TreeNode<String> treeNodeLeft) {
	this.treeNodeLeft = treeNodeLeft;
    }

    /**
     * @return the leftSelectedNodeTitle
     */
    public String getLeftSelectedNodeTitle() {
	return leftSelectedNodeTitle;
    }

    /**
     * @param leftSelectedNodeTitle
     *                the leftSelectedNodeTitle to set
     */
    public void setLeftSelectedNodeTitle(String leftSelectedNodeTitle) {
	this.leftSelectedNodeTitle = leftSelectedNodeTitle;
    }

    /**
     * @return the rightSelectedNodeTitle
     */
    public String getRightSelectedNodeTitle() {
	return rightSelectedNodeTitle;
    }

    /**
     * @param rightSelectedNodeTitle
     *                the rightSelectedNodeTitle to set
     */
    public void setRightSelectedNodeTitle(String rightSelectedNodeTitle) {
	this.rightSelectedNodeTitle = rightSelectedNodeTitle;
    }

    /**
     * Right tree value binding
     * @return the treeNodeRight
     */
    public TreeNode<String> getTreeNodeRight() {
	if (treeNodeRight == null) {
	    treeNodeRight = initPaneTree();
	}
	return treeNodeRight;
    }

    /**
     * Right tree value binding
     * @param treeNodeRight
     *                the treeNodeRight to set
     */
    public void setTreeNodeRight(TreeNode<String> treeNodeRight) {
	this.treeNodeRight = treeNodeRight;
    }

    /**
     * Left tree binding
     * @return the leftTree
     */
    public UITree getLeftTree() {
	return leftTree;
    }

    /**
     * Left tree binding
     * @param leftTree
     *                the leftTree to set
     */
    public void setLeftTree(UITree leftTree) {
	this.leftTree = leftTree;
    }

    /**
     * Right tree binding
     * @return the rightTree
     */
    public UITree getRightTree() {
	return rightTree;
    }

    /**
     * Right tree binding
     * @param rightTree
     *                the rightTree to set
     */
    public void setRightTree(UITree rightTree) {
	this.rightTree = rightTree;
    }

    /**
     * Expand event handler
     * @param event expand event
     */
    public void onExpand(NodeExpandedEvent event) {
	UITree tree = getTree(event);
	System.out.println("Tree ('"+tree.getId()+"') node " + (tree.isExpanded() ? "expanded" : "collapsed") + " " + tree.getRowKey());
    }

    /**
     * Drag event handler
     * @param dragEvent event handler
     */
    public void onDrag(DragEvent dragEvent) {
	    System.out.println("onDrag occured.");
	System.out.println("DragValue: " + dragEvent.getDragValue());
	System.out.println("DropValue: " + dragEvent.getDropValue());
    }
    
    /**
     * Sample unique subnode identifier generation
     * 
     * @param parentNode parent node
     * @return unique subnode identifier
     */
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
    
    /**
     * Drop event handler
     * @param dropEvent Drop event
     */
    public void onDrop(DropEvent dropEvent) {
	    System.out.println("onDrop occured.");
	System.out.println("DragValue: " + dropEvent.getDragValue());
	System.out.println("DropValue: " + dropEvent.getDropValue());

	// resolve drag source attributes
	UITreeNode srcNode = (dropEvent.getDraggableSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getDraggableSource() : null;
	UITree srcTree = srcNode != null ? srcNode.getUITree() : null;
	TreeRowKey dragNodeKey = (dropEvent.getDragValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDragValue() : null;
	TreeNode draggedNode = dragNodeKey != null ? srcTree.getTreeNode(dragNodeKey) : null;

	// resolve drag destination attributes
	UITreeNode destNode = (dropEvent.getSource() instanceof UITreeNode) ? (UITreeNode) dropEvent.getSource() : null;
	UITree destTree = destNode != null ? destNode.getUITree() : getTree(dropEvent);
	TreeRowKey dropNodeKey = (dropEvent.getDropValue() instanceof TreeRowKey) ? (TreeRowKey) dropEvent.getDropValue() : null;
	TreeNode droppedInNode = dropNodeKey != null ? destTree.getTreeNode(dropNodeKey) : null;
	
	// Note: check if we dropped node on to itself here
	if (droppedInNode != null && droppedInNode.equals(draggedNode)) {
	    System.out.println("Warning: Can't drop on itself!");
	    return;
	}

	FacesContext context = FacesContext.getCurrentInstance();
	
	if (dropNodeKey != null) {
	    // add destination node for rerender
	    destTree.addRequestKey(dropNodeKey);
	    
		Object state = null;
		if (dragNodeKey != null) { // Drag from this or other tree
		    TreeNode parentNode = draggedNode.getParent();
		    // 1. remove node from tree
		    state = srcTree.removeNode(dragNodeKey);
		    // 2. add parent for rerender
		    Object rowKey = srcTree.getTreeNodeRowKey(parentNode);
		    srcTree.addRequestKey(rowKey);		    
		} else if (dropEvent.getDragValue() != null) { // Drag from some drag source
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
	
	if (leftTree.getRowKey() != null) {
        	leftSelectedNodeTitle = (String) leftTree.getRowData();
        	UIComponent selectL = context.getViewRoot().findComponent("DnDTreeForm:selectedNodeL");
        	if (selectL != null) {
                	try {
                		ac.addComponentToAjaxRender(selectL);
                	} catch (Exception e) {
                		System.err.print(e.getMessage());
                	}
        	}
	}
	if (rightTree.getRowKey() != null) {
        	rightSelectedNodeTitle = (String) rightTree.getRowData();
        	UIComponent selectR = context.getViewRoot().findComponent("DnDTreeForm:selectedNodeR");
        	if (selectR != null) {
        		try {
        			ac.addComponentToAjaxRender(selectR);
        		} catch (Exception e) {
        			System.err.print(e.getMessage());
        		}
        	}
	}

	// Add source tree to reRender
	try {
		ac.addComponentToAjaxRender(srcTree);
	} catch (Exception e) {
		System.err.print(e.getMessage());
	}
	
	System.out.println("+++++");
    }
}
