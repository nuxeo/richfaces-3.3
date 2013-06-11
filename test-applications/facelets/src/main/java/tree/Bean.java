package tree;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.context.AjaxContext;
import org.richfaces.component.UITree;
import org.richfaces.component.UITreeNode;
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

import util.componentInfo.*;

public class Bean {
	private String switchType = "client";
	private TreeNode data;
	private TreeNode selectedNode = null;
	private Map selectedNodeChildren = new LinkedHashMap();
	private String commandButtonCaption = "Set user icons";
	private String iconCollapsed = null;
	private String iconExpanded = null;
	private String iconLeaf = null;
	private String icon = null;
	private boolean renderFacets = false;
	private String pathToExpand;
	private boolean dragOn = true;
	private TreeNodeImpl data1;
	private int counter = 0;
	private String expandPath;
	private UITree tree;

	public Bean() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			data = XmlTreeDataBuilder.build(new InputSource(getClass()
					.getResourceAsStream("test.xml")));
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
		for (int i = 0; i < 10; i++) {
			TreeNodeImpl child = new TreeNodeImpl() {

				public Object getData() {
					return super.getData() + " " + counter;
				}
			};
			String id = Integer.toString(i);
			child.setData("Node: " + id);
			data1.addChild(id, child);
		}
		// initData();
	}

	private void initData() {
		selectedNodeChildren.clear();
		if (selectedNode != null) {
			Iterator iter = selectedNode.getChildren();
			int i = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				selectedNodeChildren.put(((TreeNode) entry.getValue())
						.getData(), Integer.toString(i++));
			}
		}
	}

	/*
	 * FIXME
	public String add() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(tree);
		return null;
	}
	*/

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
			iconCollapsed = "/pics/header.png";
			iconExpanded = "/pics/item.png";
			iconLeaf = "/pics/ajax_process.gif";
			icon = "/pics/ajax_stoped.gif";
			commandButtonCaption = "Set defoult icons";
		} else {
			iconCollapsed = null;
			iconExpanded = null;
			iconLeaf = null;
			icon = null;
			commandButtonCaption = "Set user icons";
		}
	}

	public TreeNode getData() {
		return data;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	private UITree getTree(FacesEvent event) {
		UIComponent component = event.getComponent();
		if (component instanceof UITree) {
			return ((UITree) component);
		}

		if (component instanceof UITreeNode) {
			return ((UITree) component.getParent());
		}

		return null;
	}

	public void up() {
		if (selectedNode.getParent() != null) {
			selectedNode = selectedNode.getParent();
		}
		initData();
	}

	public void onSelect(NodeSelectedEvent event) {
		System.out.println("Node selected: " + getTree(event).getRowKey());
		if (getTree(event).getTreeNode() != null) {
			selectedNode = getTree(event).getTreeNode();
			data.removeChild(selectedNode.getChildren());
			initData();
		}
	}

	public void onSelectInc(NodeSelectedEvent event) {
		counter++;

		UITree tree = getTree(event);
		TreeRowKey key = (TreeRowKey) tree.getRowKey();
		Set keys = tree.getAjaxKeys();
		if (keys == null) {
			keys = new HashSet();
			tree.setAjaxKeys(keys);
		}

		AjaxContext ajaxCtx = AjaxContext.getCurrentInstance();
		FacesContext fctx = FacesContext.getCurrentInstance();
		tree.setRowKey(null);
		// Force more than one node to update here:
		for (int i = 0; i < 5; i++) {
			ListRowKey dirtyKey = new ListRowKey(null, Integer.toString(i));
			keys.add(dirtyKey);
			tree.setRowKey(dirtyKey);
			ajaxCtx.addComponentToAjaxRender(tree.getParent(), tree
					.getClientId(fctx));
		}
		tree.setRowKey(key);
	}

	public void onExpand(NodeExpandedEvent event) {
		UITree tree = getTree(event);
		System.out.println("Node "
				+ (tree.isExpanded() ? "expanded" : "collapsed") + " "
				+ tree.getRowKey());
	}

	public String expand() {
		if (expandPath != null && expandPath.length() != 0) {
			try {
				tree.queueNodeExpand(new ListRowKey(null, expandPath));
			} catch (IOException e) {

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
		System.out.println("Drag row data: "
				+ getTree(dropEvent).getRowData(dropEvent.getDragValue()));
		System.out.println("Drop row data: "
				+ getTree(dropEvent).getRowData(dropEvent.getDropValue()));
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
				((UITree) getTree()).queueNodeExpand(new ListRowKey(null,
						pathToExpand));
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

}
