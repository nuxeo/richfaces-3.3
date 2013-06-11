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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.AjaxContext;
import org.richfaces.component.UITree;
import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.event.DropEvent;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.richfaces.model.TreeRowKey;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author $Autor$
 *
 */
public class Bean extends TreeContainer {
	private String switchType = "client";

	private TreeNode data;

	private List<javax.swing.tree.TreeNode> swingTreeNodes;
	
	private TreeNode selectedNode = null;

	private Map selectedNodeChildren = new LinkedHashMap();
	
	private String commandButtonCaption = "Set user icons";	
	private String iconCollapsed = null;
	private String iconExpanded = null;
	private String iconLeaf = null;
	private String icon = null;

	private String styleClass = "treeIcon16";	

	private static final byte MODE_DEFAULT = 1;
	private static final byte MODE_LITERAL = 2;
	private static final byte MODE_EL = 3;
	
	private byte dndValueMode = MODE_DEFAULT;
	
	private boolean renderFacets = false;
	
	private String pathToExpand;

	private boolean dragOn = true;

	private TreeNodeImpl data1;

	private int counter = 0;
	
	private int counter1 = 0;

	private int counter2 = 0;

	private int requestCounter = 0;
	
	private String ajaxNodeSelectionEncodeBehavior;
	private String ajaxChildActivationEncodeBehavior;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCommandButtonCaption() {
		return commandButtonCaption;
	}

	public void setCommandButtonCaption(String commandButtonCaption) {
		this.commandButtonCaption = commandButtonCaption;
	}
	
	public void changeIcons(javax.faces.event.ActionEvent event) {
		if (null == icon) {
			iconCollapsed = "/images/header.png";
			iconExpanded = "/images/item.png";
			iconLeaf = "/images/ajax_process.gif";
			icon = "/images/ajax_stoped.gif";
			commandButtonCaption = "Set default icons";
		} else {
			iconCollapsed = null;
			iconExpanded = null;
			iconLeaf = null;
			icon = null;
			commandButtonCaption = "Set user icons";
		}
	}

	public Bean() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			data = XmlTreeDataBuilder.build(new InputSource(
					getClass().getResourceAsStream("test.xml")));
			TreeNode pomData = XmlTreeDataBuilder.build(new InputSource(
					getClass().getResourceAsStream("pom_sample.xml")));
			Iterator children = pomData.getChildren();
			while (children.hasNext()) {
				Map.Entry entry = (Map.Entry) children.next();
				data.addChild(new Long(1), (TreeNode) entry.getValue());
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		data1 = new TreeNodeImpl();
		data1.setData("Root node");
		for (int i = 0; i < 10; i++)
		{
			TreeNodeImpl child = new TreeNodeImpl() {

				public Object getData() {
					return super.getData() + " " + counter;
				}
			};
			String id = Integer.toString(i);
			child.setData("Node: " + id);
			data1.addChild(id, child);
		}
		initData();
		
		SwingTreeNode treeNode = convertToSwingTreeNode(this.data);
		List<javax.swing.tree.TreeNode> list = new ArrayList<javax.swing.tree.TreeNode>();
		for (int i = 0; i < treeNode.getChildCount(); i++) {
			list.add(treeNode.getChildAt(i));
		}
		
		this.swingTreeNodes = list;
	}

	private SwingTreeNode convertToSwingTreeNode(TreeNode treeNode) {
		SwingTreeNode node = new SwingTreeNode(treeNode.getData());
		if (treeNode.isLeaf()) {
			node.setAllowsChildren(false);
		} else {
			Iterator<Entry<Object, TreeNode>> children = treeNode.getChildren();
			while (children.hasNext()) {
				TreeNode value = children.next().getValue();
				node.addChild(convertToSwingTreeNode(value));
			}
		}
		
		return node;
	}
	
	public TreeNode getData() {
		return data;
	}
	
	public List<javax.swing.tree.TreeNode> getSwingTreeNodes() {
		return swingTreeNodes;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public void up() {
		if(selectedNode.getParent()!=null) {
			selectedNode = selectedNode.getParent();		
		}			
		initData();
	}

	public void onSelect(NodeSelectedEvent event) {
		System.out.println("Node selected: " + getTree(event).getRowKey());
		UITree tree = getTree(event);
//		Set keys = tree.getAjaxKeys();
//		if (keys == null) {
//			keys = new HashSet();
//			tree.setAjaxKeys(keys);
//		}

		//keys.add(tree.getRowKey());
		//keys.add(new ListRowKey());
		
		if (getTree(event).getTreeNode()!=null) {
			selectedNode = getTree(event).getTreeNode();
			initData();
		}
	}

	public boolean getReRenderValue() {
		return false;
	}
	
	public void setReRenderValue(boolean value) {
		if (value) {
			tree.addRequestKey(tree.getRowKey());
			FacesContext context = FacesContext.getCurrentInstance();
			AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
			ajaxContext.getAjaxAreasToRender().add(tree.getClientId(context));
		}
	}
	
	public void onSelectInc(NodeSelectedEvent event) {
		counter ++;
		
		UITree tree = getTree(event);
		tree.addRequestKey(tree.getRowKey());

		AjaxContext ajaxCtx = AjaxContext.getCurrentInstance();
		FacesContext fctx = FacesContext.getCurrentInstance();
		tree.setRowKey(null);
		// Force more than one node to update here:
		for (int i = 0; i < 5; i++)
		{
			ListRowKey dirtyKey = new ListRowKey(null, Integer.toString(i));
			tree.addRequestKey(dirtyKey);
			tree.setRowKey(dirtyKey);
			ajaxCtx.addComponentToAjaxRender(tree.getParent(), tree.getClientId(fctx));
		}
	}
	
	private void initData() {
		selectedNodeChildren.clear();
		if (selectedNode != null) {
			Iterator iter = selectedNode.getChildren();
			int i = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				selectedNodeChildren.put(((TreeNode)entry.getValue()).getData(), Integer.toString(i++));			
			}
		}
	}

	public void onExpand(NodeExpandedEvent event) {
		UITree tree = getTree(event);
		System.out.println("Node "
				+ (tree.isExpanded() ? "expanded" : "collapsed") + " "
				+ tree.getRowKey());
	}

	private String expandPath;

	private UITree tree;

	public String expand() {
		if (expandPath != null && expandPath.length() != 0) {
			try {
				tree.queueNodeExpand(new ListRowKey(null, expandPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public String getExpandPath() {
		return expandPath;
	}

	public void setExpandPath(String expandPath) {
		this.expandPath = expandPath;
	}

	public UIComponent getTree() {
		return tree;
	}

	public void setTree(UIComponent tree) {
		this.tree = (UITree) tree;
	}

	public String collapseAll() throws IOException {
		this.tree.queueCollapseAll();
		return null;
	}

	public String expandAll() {
		try {
			this.tree.queueExpandAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Map getSelectedNodeChildren() {
		return selectedNodeChildren;
	}

	public void setSelectedNodeChildren(Map selectedNodeChildren) {
		this.selectedNodeChildren = selectedNodeChildren;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void processDrop(DropEvent dropEvent) {
		System.out.println("Should be printed twice per event!!!");
		System.out.println(dropEvent);
		System.out.println("DragValue: " + dropEvent.getDragValue());
		System.out.println("DropValue: " + dropEvent.getDropValue());
		if (dropEvent.getDragValue() instanceof TreeRowKey) {
			System.out.println("Drag row data: " + getTree(dropEvent).getRowData(dropEvent.getDragValue()));
		}
		
		if (dropEvent.getDropValue() instanceof TreeRowKey) {
			System.out.println("Drop row data: " + getTree(dropEvent).getRowData(dropEvent.getDropValue()));
		}
		
		System.out.println("+++++");
	}

	public String getIconCollapsed() {
		return iconCollapsed;
	}

	public void setIconCollapsed(String iconCollapsed) {
		this.iconCollapsed = iconCollapsed;
	}

	public String getIconExpanded() {
		return iconExpanded;
	}

	public void setIconExpanded(String iconExpanded) {
		this.iconExpanded = iconExpanded;
	}

	public String getIconLeaf() {
		return iconLeaf;
	}

	public void setIconLeaf(String iconLeaf) {
		this.iconLeaf = iconLeaf;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	
	public String getPathToExpand() {
		return pathToExpand;
	}

	public void setPathToExpand(String pathToExpand) {
		this.pathToExpand = pathToExpand;
	}
	
	public String expandNode() {
		String pathToExpand = getPathToExpand();
		if (pathToExpand != null && pathToExpand.trim().length() != 0) {
			try {
				((UITree) getTree()).queueNodeExpand(new ListRowKey(null, pathToExpand));
			} catch (IOException e) {
				throw new FacesException(e);
			}
		}
		
		return null;
	}

	public boolean isDragOn() {
		return dragOn;
	}

	public void setDragOn(boolean dragOn) {
		this.dragOn = dragOn;
	}
	
	public TreeNodeImpl getData1() {
		return data1;
	}
	
	public void setData1(TreeNodeImpl data1) {
		this.data1 = data1;
	}

	public boolean isRenderFacets() {
		return renderFacets;
	}

	public void setRenderFacets(boolean renderFacets) {
		this.renderFacets = renderFacets;
	}

	public byte getDndValueMode() {
		return dndValueMode;
	}

	public void setDndValueMode(byte dndValueMode) {
		this.dndValueMode = dndValueMode;
		switch (dndValueMode) {
		case MODE_DEFAULT:
			this.tree.setValueBinding("dragValue", null);
			this.tree.setValueBinding("dropValue", null);
			this.tree.setDragValue(null);
			this.tree.setDropValue(null);
			break;

		case MODE_LITERAL:
			this.tree.setValueBinding("dragValue", null);
			this.tree.setValueBinding("dropValue", null);
			this.tree.setDragValue("dragValue");
			this.tree.setDropValue("dropValue");
			break;

		case MODE_EL:
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			this.tree.setValueBinding("dragValue", application.createValueBinding("#{data.name}"));
			this.tree.setValueBinding("dropValue", application.createValueBinding("#{data.name}"));
			this.tree.setDragValue(null);
			this.tree.setDropValue(null);
			break;
		
		default:
			break;
		}
	}
	
	public int getCounter1() {
		return counter1;
	}
	
	public int getCounter2() {
		return counter2++;
	}
	
	public void incCounter1() {
		counter1++;
	}

	public void action(ActionEvent event) {
	    System.out.println("Bean.action() " + 
		    event.getComponent().getClientId(FacesContext.getCurrentInstance()));
	}
	
	private static final String REQUEST_COUNTER_ATTRIBUTE = Bean.class.getName() + ":requestCounter";
	
	public int getRequestCounter() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		
		if (requestMap.get(REQUEST_COUNTER_ATTRIBUTE) == null) {
			requestMap.put(REQUEST_COUNTER_ATTRIBUTE, Boolean.TRUE);
			
			requestCounter++;
		}
		
		return requestCounter;
	}

	public String getAjaxChildActivationEncodeBehavior() {
		return ajaxChildActivationEncodeBehavior;
	}
	
	public String getAjaxNodeSelectionEncodeBehavior() {
		return ajaxNodeSelectionEncodeBehavior;
	}
	
	public void setAjaxChildActivationEncodeBehavior(
			String ajaxChildActivationEncodeBehavior) {
		this.ajaxChildActivationEncodeBehavior = ajaxChildActivationEncodeBehavior;
	}
	
	public void setAjaxNodeSelectionEncodeBehavior(
			String ajaxNodeSelectionEncodeBehavior) {
		this.ajaxNodeSelectionEncodeBehavior = ajaxNodeSelectionEncodeBehavior;
	}
}
