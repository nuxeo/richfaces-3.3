/**
 * 
 */
package org.ajax4jsf;

import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;

/**
 * @author asmirnov
 *
 */
public class TreeBacker {
    
    UIComponent tree;
    
    TreeModel treeModel;

    /**
     * @return the tree
     */
    public UIComponent getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(UIComponent tree) {
        this.tree = tree;
    }

    /**
     * @return the treeModel
     */
    public TreeModel getTreeModel() {
        if (treeModel == null) {            
	    TreeNode rootNode = new TestTreeModel();
	    for(int i=0;i<5;i++){
		rootNode.getChildren().add(new TestTreeModel());
	    }
	    treeModel = new TreeModelBase(rootNode );
	}

	return treeModel;
    }

    /**
     * @param treeModel the treeModel to set
     */
    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }

    /**
     * 
     */
    public TreeBacker() {
	// TODO Auto-generated constructor stub
    }

}
