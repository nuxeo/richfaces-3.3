/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.bean.tree.rich.AudioLibrary;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.event.DropEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeRowKey;
import org.xml.sax.SAXException;

public class TreeTestBean {

    private TreeNode<String> rootNode = null;

    private String nodeTitle;

    private TreeNode<Object> richRootNode = null;

    public synchronized TreeNode<Object> getRichTreeNode() {
        if (null == richRootNode) {
            Digester digester = DigesterLoader.createDigester(getClass().getResource("/rich-digester-rules.xml"));
            AudioLibrary library = new AudioLibrary();
            digester.push(library);
            try {
                digester.parse(getClass().getResourceAsStream("/audio-library.xml"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            richRootNode = library;
        }

        return richRootNode;
    }

    private List<javax.swing.tree.TreeNode> swingNodes = null;

    public synchronized List<javax.swing.tree.TreeNode> getSwingTreeNodes() {
        if (null == swingNodes) {
            Digester digester = DigesterLoader.createDigester(getClass().getResource("/swing-digester-rules.xml"));
            List<javax.swing.tree.TreeNode> nodes = new ArrayList<javax.swing.tree.TreeNode>();
            digester.push(nodes);
            try {
                digester.parse(getClass().getResourceAsStream("/audio-library.xml"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            swingNodes = nodes;
        }

        return swingNodes;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public void dropListener(DropEvent dropEvent) {
        //we have only one tree: destination tree coincides with source one
        UITree tree = ((UITreeNode) dropEvent.getSource()).getUITree();

        TreeRowKey<Object> dropNodeKey = (TreeRowKey<Object>) dropEvent.getDropValue();
        TreeRowKey<Object> draggedNodeKey = (TreeRowKey<Object>) dropEvent.getDragValue();

        //mark destination node as wanting rerendering
        tree.addRequestKey(dropNodeKey);
        Object state = null;
        if(null != draggedNodeKey) {
            Object newKey;
            Object draggedNode = tree.getTreeNode(draggedNodeKey);
            if (draggedNode == null) {
            	draggedNode = tree.getRowData(draggedNodeKey);
            	newKey = null;
            } else {
                newKey = System.currentTimeMillis();
            }
            
            // 1. mark its parent as wanting rerendering
            tree.addRequestKey(draggedNodeKey.getParentKey());
            // 2. remove node from tree
            state = tree.removeNode(draggedNodeKey);

            // 3. add the node at the new place
            tree.addNode(FacesContext.getCurrentInstance(), dropNodeKey, draggedNode, newKey, state);
        }
    }

    private String switchType = "ajax";

    /**
     * Gets value of switchType field.
     * @return value of switchType field
     */
    public String getSwitchType() {
        return switchType;
    }

    /**
     * Set a new value for switchType field.
     * @param switchType a new value for switchType field
     */
    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    private boolean toggleOnClick = false;

    /**
     * Gets value of toggleOnClick field.
     * @return value of toggleOnClick field
     */
    public boolean isToggleOnClick() {
        return toggleOnClick;
    }

    /**
     * Set a new value for toggleOnClick field.
     * @param toggleOnClick a new value for toggleOnClick field
     */
    public void setToggleOnClick(boolean toggleOnClick) {
        this.toggleOnClick = toggleOnClick;
    }

    private boolean ajaxSubmitSelection = false;

    /**
     * Gets value of ajaxSubmitSelection field.
     * @return value of ajaxSubmitSelection field
     */
    public boolean isAjaxSubmitSelection() {
        return ajaxSubmitSelection;
    }

    /**
     * Set a new value for ajaxSubmitSelection field.
     * @param ajaxSubmitSelection a new value for ajaxSubmitSelection field
     */
    public void setAjaxSubmitSelection(boolean ajaxSubmitSelection) {
        this.ajaxSubmitSelection = ajaxSubmitSelection;
    }

    public void initToggleOnClickTestServerMode() {
        setToggleOnClick(true);
        setSwitchType("server");
        setAjaxSubmitSelection(false);
    }

    public void initToggleOnClickTestAjaxMode() {
        setToggleOnClick(true);
        setSwitchType("ajax");
        setAjaxSubmitSelection(false);
    }

    public void initToggleOnClickTestClientMode() {
        setToggleOnClick(true);
        setSwitchType("client");
        setAjaxSubmitSelection(false);
    }

    public void initAjaxSubmitSelectionTest() {
        setSwitchType("client");
        setAjaxSubmitSelection(true);
        setToggleOnClick(false);
    }

    public void initAjaxCoreTest() {
        setSwitchType("ajax");
        setToggleOnClick(false);
        setAjaxSubmitSelection(true);
    }

    public void initServerMode() {
        setSwitchType("server");
        setToggleOnClick(false);
        setAjaxSubmitSelection(false);
    }

    public void initAjaxMode() {
        setSwitchType("ajax");
        setToggleOnClick(false);
        setAjaxSubmitSelection(false);
    }

    public void initClientMode() {
        setSwitchType("client");
        setToggleOnClick(false);
        setAjaxSubmitSelection(false);
    }

    public void initDragAndDropTest() {
        if(advisor == null) {
            setAdvisor(new TestAdvisor());
        }
    }

    public void initKeyboardNavigationTest() {
        setSwitchType("ajax");
        setToggleOnClick(false);
        setAjaxSubmitSelection(false);
    }

    private TreeStateAdvisor advisor;

    /**
     * Gets value of advisor field.
     * @return value of advisor field
     */
    public TreeStateAdvisor getAdvisor() {
        return advisor;
    }

    /**
     * Set a new value for advisor field.
     * @param advisor a new value for advisor field
     */
    public void setAdvisor(TreeStateAdvisor advisor) {
        this.advisor = advisor;
    }

    public void initStateAdvisorTest() {
        if(advisor == null) {
            setAdvisor(new TestAdvisor());
        }
    }

    static class TestAdvisor implements TreeStateAdvisor {

        public Boolean adviseNodeOpened(UITree tree) {
            return true;
        }

        public Boolean adviseNodeSelected(UITree tree) {
            return null;
        }
    }

    public void tearDown() {
        setAdvisor(null);
    }

}
