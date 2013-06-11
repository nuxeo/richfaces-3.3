package tTree;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.richfaces.component.UITree;
import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import util.componentInfo.ComponentInfo;

public class TTree {
	private String switchType = "server";
	private TreeNode data;
	private UITree tree;
	private boolean immediate = false;
	private boolean ajaxSubmitSelection = false;
	private String anOpened = "null";
	private String anSelected = "null";
	private boolean rendered = true;
	private boolean showConnectingLines = false;
	private boolean toggleOnClick = false;
	private boolean useCustomIcons = false;

	private String reRenderCheck = "... waiting ...";

	public TTree() {
		try {
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

	public void resetReRenderCheck() {
		reRenderCheck = "... waiting ...";
	}

	public Boolean adviseNodeOpened(UITree t) {
		Boolean tAnOpened = null;
		if (anOpened.equals("FALSE"))
			tAnOpened = Boolean.FALSE;
		else if (anOpened.equals("TRUE"))
			tAnOpened = Boolean.TRUE;

		return tAnOpened;
	}

	public Boolean adviseNodeSelected(UITree t) {
		Boolean tAnSelected = null;
		if (anSelected.equals("FALSE"))
			tAnSelected = Boolean.FALSE;
		else if (anSelected.equals("TRUE"))
			tAnSelected = Boolean.TRUE;

		return tAnSelected;
	}

	public void changeExpandListener(NodeExpandedEvent e) {
		reRenderCheck = "!!! I am WORKing !!!";
		System.out.println("!!! changeExpandListener !!!");
	}

	public void nodeSelectListener(NodeSelectedEvent e) {
		reRenderCheck = "!!! I am WORKing !!!";
		System.out.println("!!! nodeSelectListener !!!");
	}

	public String getReRenderCheck() {
		return reRenderCheck;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isAjaxSubmitSelection() {
		return ajaxSubmitSelection;
	}

	public void setAjaxSubmitSelection(boolean ajaxSubmitSelection) {
		this.ajaxSubmitSelection = ajaxSubmitSelection;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public TreeNode getData() {
		return data;
	}

	public void setData(TreeNode data) {
		this.data = data;
	}

	public UITree getTree() {
		return tree;
	}

	public void setTree(UITree tree) {
		this.tree = tree;
	}

	public String getAnOpened() {
		return anOpened;
	}

	public void setAnOpened(String anOpened) {
		this.anOpened = anOpened;
	}

	public String getAnSelected() {
		return anSelected;
	}

	public void setAnSelected(String anSelected) {
		this.anSelected = anSelected;
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

	public boolean isToggleOnClick() {
		return toggleOnClick;
	}

	public void setToggleOnClick(boolean toggleOnClick) {
		this.toggleOnClick = toggleOnClick;
	}

	public boolean isUseCustomIcons() {
		return useCustomIcons;
	}

	public void setUseCustomIcons(boolean useCustomIcons) {
		this.useCustomIcons = useCustomIcons;
	}
}