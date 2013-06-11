package org.richfaces.helloworld.domain.util.icon;

public class Icon {

	public final String iconAjaxProcess = "/pics/ajax_process.gif";
	public final String iconAjaxStoped = "/pics/ajax_stoped.gif";
	public final String iconCollapse = "/pics/collapse.gif";
	public final String iconExpand = "/pics/expand.gif";
	public final String iconFileManagerReject = "/pics/file-manager-reject.png";
	public final String iconFileManager = "/pics/file-manager.png";
	public final String iconHeader = "/pics/header.png";
	public final String iconItem = "/pics/item.png";
	public final String none = "none";

	private String icon;
	private String item;
	private String disabled;
	private String disabledItem;
	private String topItem;
	private String topDisabledItem;
	private String expandedGroup;
	private String collapsedGroup;
	private String disabledGroup;
	private String expandedTopGroup;
	private String collapsedTopGroup;
	private String topDisableGroup;
	private String expanded;
	private String collapsed;
 
	public Icon() {
		icon = "none";
		item = "none";
		disabled = "none";
		disabledItem = "none";
		topItem = "none";
		topDisabledItem = "none";
		expandedGroup = "none";
		collapsedGroup = "none";
		disabledGroup = "none";
		expandedTopGroup = "none";
		collapsedTopGroup = "none";
		topDisableGroup = "none";
		expanded = "none";
		collapsed = "none";
	}

	public final String getIconAjaxProcess() {
		return iconAjaxProcess;
	}

	public final String getIconAjaxStoped() {
		return iconAjaxStoped;
	}

	public final String getIconCollapse() {
		return iconCollapse;
	}

	public final String getIconExpand() {
		return iconExpand;
	}

	public final String getIconFileManager() {
		return iconFileManager;
	}

	public final String getIconFileManagerReject() {
		return iconFileManagerReject;
	}

	public final String getIconHeader() {
		return iconHeader;
	}

	public final String getIconItem() {
		return iconItem;
	}

	public String getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(String collapsed) {
		this.collapsed = collapsed;
	}

	public String getCollapsedGroup() {
		return collapsedGroup;
	}

	public void setCollapsedGroup(String collapsedGroup) {
		this.collapsedGroup = collapsedGroup;
	}

	public String getCollapsedTopGroup() {
		return collapsedTopGroup;
	}

	public void setCollapsedTopGroup(String collapsedTopGroup) {
		this.collapsedTopGroup = collapsedTopGroup;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getDisabledGroup() {
		return disabledGroup;
	}

	public void setDisabledGroup(String disabledGroup) {
		this.disabledGroup = disabledGroup;
	}

	public String getDisabledItem() {
		return disabledItem;
	}

	public void setDisabledItem(String disabledItem) {
		this.disabledItem = disabledItem;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getExpandedGroup() {
		return expandedGroup;
	}

	public void setExpandedGroup(String expandedGroup) {
		this.expandedGroup = expandedGroup;
	}

	public String getExpandedTopGroup() {
		return expandedTopGroup;
	}

	public void setExpandedTopGroup(String expandedTopGroup) {
		this.expandedTopGroup = expandedTopGroup;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getTopDisabledItem() {
		return topDisabledItem;
	}

	public void setTopDisabledItem(String topDisabledItem) {
		this.topDisabledItem = topDisabledItem;
	}

	public String getTopDisableGroup() {
		return topDisableGroup;
	}

	public void setTopDisableGroup(String topDisableGroup) {
		this.topDisableGroup = topDisableGroup;
	}

	public String getTopItem() {
		return topItem;
	}

	public void setTopItem(String topItem) {
		this.topItem = topItem;
	}

	public final String getNone() {
		return none;
	}
}
