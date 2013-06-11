package org.richfaces.helloworld.domain.panelMenu;

import org.richfaces.component.html.HtmlPanelMenu;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.richfaces.helloworld.domain.util.icon.Icon;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("panelMenu")
@Scope(ScopeType.SESSION)
public class PanelMenu {

	private Icon icon;
	private String width;
	private String mode;
	private String align;
	private boolean rendered;
	private String iconItemPosition;
	private String iconItemTopPosition;
	private String iconGroupPosition;
	private String iconGroupTopPosition;
	private String tabIndex;
	private String expandMode;
	private String inputText;
	private boolean disabled;
	private boolean expandSingle;
	private HtmlPanelMenu htmlPanelMenu = null;
	
	public HtmlPanelMenu getHtmlPanelMenu() {
		return htmlPanelMenu;
	}

	public void setHtmlPanelMenu(HtmlPanelMenu htmlPanelMenu) {
		this.htmlPanelMenu = htmlPanelMenu;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlPanelMenu);
		return null;
	}
	
	public PanelMenu() {
		width = "500px";
		mode = "none";
		expandMode = "none";
		align = "";
		rendered = true;
		disabled = false;
		tabIndex = "1";
		iconGroupPosition = "left";
		iconGroupTopPosition = "left";
		iconItemPosition = "left";
		iconGroupTopPosition = "left";
		icon = new Icon();
		icon.setCollapsed(icon.iconCollapse);
		icon.setExpanded(icon.iconExpand);
		icon.setItem(icon.iconItem);
		icon.setIcon(icon.iconFileManager);
		icon.setCollapsedGroup(icon.iconAjaxProcess);
		icon.setDisabledGroup(icon.iconAjaxStoped);
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isExpandSingle() {
		return expandSingle;
	}
	public void setExpandSingle(boolean expandSingle) {
		this.expandSingle = expandSingle;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public String getIconGroupPosition() {
		return iconGroupPosition;
	}
	public void setIconGroupPosition(String iconGroupPosition) {
		this.iconGroupPosition = iconGroupPosition;
	}
	public String getIconGroupTopPosition() {
		return iconGroupTopPosition;
	}
	public void setIconGroupTopPosition(String iconGroupTopPosition) {
		this.iconGroupTopPosition = iconGroupTopPosition;
	}
	public String getIconItemPosition() {
		return iconItemPosition;
	}
	public void setIconItemPosition(String iconItemPosition) {
		this.iconItemPosition = iconItemPosition;
	}
	public String getIconItemTopPosition() {
		return iconItemTopPosition;
	}
	public void setIconItemTopPosition(String iconItemTopPosition) {
		this.iconItemTopPosition = iconItemTopPosition;
	}

	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getExpandMode() {
		return expandMode;
	}

	public void setExpandMode(String expandMode) {
		this.expandMode = expandMode;
	}

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public void bTest1(){
		icon.setCollapsed(icon.iconCollapse);
		icon.setExpanded(icon.iconExpand);
		icon.setItem(icon.iconItem);
		icon.setIcon(icon.iconFileManager);
		icon.setCollapsedGroup(icon.iconAjaxProcess);
		icon.setDisabledGroup(icon.iconAjaxStoped);
		
		setWidth("400px");
		setAlign("right");
		setDisabled(false);
		setExpandMode("ajax");
		setExpandSingle(false);
		setIconGroupPosition("left");
		setIconGroupTopPosition("right");
		setIconItemPosition("left");
		setIconItemTopPosition("right");
		setMode("ajax");
		setTabIndex("3");
	}

	public void bTest2(){
		icon.setCollapsed(icon.iconExpand);
		icon.setExpanded(icon.iconCollapse);
		icon.setItem(icon.iconHeader);
		icon.setIcon(icon.iconFileManagerReject);
		icon.setCollapsedGroup(icon.iconAjaxStoped);
		icon.setDisabledGroup(icon.iconAjaxProcess);
		
		setWidth("250px");
		setAlign("bottom");
		setDisabled(false);
		setExpandMode("server");
		setExpandSingle(false);
		setIconGroupPosition("right");
		setIconGroupTopPosition("left");
		setIconItemPosition("right");
		setIconItemTopPosition("left");
		setMode("server");
		setTabIndex("2");		
	}

	public void bTest3(){
		icon.setCollapsed(icon.iconCollapse);
		icon.setExpanded(icon.iconExpand);
		icon.setItem(icon.iconItem);
		icon.setIcon(icon.iconFileManager);
		icon.setCollapsedGroup(icon.iconAjaxProcess);
		icon.setDisabledGroup(icon.iconAjaxStoped);
		
		setWidth("400px");
		setAlign("middle");
		setDisabled(false);
		setExpandMode("server");
		setExpandSingle(false);
		setIconGroupPosition("right");
		setIconGroupTopPosition("right");
		setIconItemPosition("right");
		setIconItemTopPosition("left");
		setMode("none");
		setTabIndex("2");		
		
	}

	public void bTest4(){
		icon.setCollapsed(icon.iconExpand);
		icon.setExpanded(icon.iconCollapse);
		icon.setItem(icon.iconHeader);
		icon.setIcon(icon.iconFileManagerReject);
		icon.setCollapsedGroup(icon.iconAjaxStoped);
		icon.setDisabledGroup(icon.iconAjaxProcess);
		
		setWidth("250px");
		setAlign("top");
		setDisabled(true);
		setExpandMode("none");
		setExpandSingle(false);
		setIconGroupPosition("left");
		setIconGroupTopPosition("left");
		setIconItemPosition("left");
		setIconItemTopPosition("left");
		setMode("ajax");
		setTabIndex("2");		
		
	}

	public void bTest5(){
		icon.setCollapsed(icon.iconCollapse);
		icon.setExpanded(icon.iconExpand);
		icon.setItem(icon.iconItem);
		icon.setIcon(icon.iconFileManager);
		icon.setCollapsedGroup(icon.iconAjaxProcess);
		icon.setDisabledGroup(icon.iconAjaxStoped);
		
		setWidth("250px");
		setAlign("right");
		setDisabled(false);
		setExpandMode("none");
		setExpandSingle(true);
		setIconGroupPosition("right");
		setIconGroupTopPosition("right");
		setIconItemPosition("right");
		setIconItemTopPosition("right");
		setMode("none");
		setTabIndex("2");		

	}
}