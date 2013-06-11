package ddMenu;

import java.awt.Event;

import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlDropDownMenu;

import util.componentInfo.ComponentInfo;

public class DDMenu {

	private int hideDelay;
	private int showDelay;
	private int verticalOffset;
	private int horizontalOffset;
	private String event;
	private String direction = "";
	private String groupDirection;
	private String jointPoint = "";
	private String popupWidth = "";
	private String icon = null;
	private String iconFolder = null;
	private String selectMenu;
	private String mode;
	private String action;
	private String actionListener;
	private boolean rendered;
	private boolean disabled;
	private boolean disabledDDM;
	private boolean check;
	private HtmlDropDownMenu htmlDDMenu = null;

	public HtmlDropDownMenu getHtmlDDMenu() {
		return htmlDDMenu;
	}

	public void setHtmlDDMenu(HtmlDropDownMenu htmlDDMenu) {
		this.htmlDDMenu = htmlDDMenu;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDDMenu);
		return null;
	}

	public DDMenu() {
		selectMenu = "accord";
		hideDelay = 0;
		showDelay = 0;
		verticalOffset = 0;
		horizontalOffset = 0;
		event = "onmouseover";
		direction = "";
		groupDirection = "auto";
		jointPoint = "";
		popupWidth = "";
		mode = "none";
		icon = null;
		iconFolder = null;
		rendered = true;
		disabled = false;
		disabledDDM = false;
		check = false;
		action = "---";
		actionListener = "---";
	}

	public void act() {
		action = "action work!";
	}
	
	public void actListener(ActionEvent e) {
		actionListener = "actionListener work!";
	}

	public String getActionListener() {
		return actionListener;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconFolder() {
		return iconFolder;
	}

	public void setIconFolder(String iconFolder) {
		this.iconFolder = iconFolder;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getGroupDirection() {
		return groupDirection;
	}

	public void setGroupDirection(String groupDirection) {
		this.groupDirection = groupDirection;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getHideDelay() {
		return hideDelay;
	}

	public void setHideDelay(int hideDelay) {
		this.hideDelay = hideDelay;
	}

	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	public void setHorizontalOffset(int horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public String getJointPoint() {
		return jointPoint;
	}

	public void setJointPoint(String jointPoint) {
		this.jointPoint = jointPoint;
	}

	public String getPopupWidth() {
		return popupWidth;
	}

	public void setPopupWidth(String popupWidth) {
		this.popupWidth = popupWidth;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public int getShowDelay() {
		return showDelay;
	}

	public void setShowDelay(int showDelay) {
		this.showDelay = showDelay;
	}

	public int getVerticalOffset() {
		return verticalOffset;
	}

	public void setVerticalOffset(int verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public void changeIcons() {
		if (icon != null) {
			icon = null;
			iconFolder = null;
		} else {
			icon = "/pics/header.png";
			iconFolder = "/pics/ajax_process.gif";

		}
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getSelectMenu() {
		return selectMenu;
	}

	public void setSelectMenu(String selectMenu) {
		this.selectMenu = selectMenu;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void bTest1(){
		setDirection("top-right");
		setEvent("onmouseover");
		setGroupDirection("top-up");
		setHideDelay(5);
		setHorizontalOffset(1);
		setVerticalOffset(1);
		setJointPoint("top-left");
		setMode("none");
		setPopupWidth("300px");
		setShowDelay(1);
		setDisabled(false);
	}

	public void bTest2(){
		setDirection("bottom-right");
		setEvent("onclick");
		setGroupDirection("bottom-left");
		setHideDelay(5);
		setShowDelay(5);
		setHorizontalOffset(20);
		setVerticalOffset(20);
		setJointPoint("bottom-right");
		setMode("ajax");
		setPopupWidth("200px");
		setDisabled(false);
	}
	
	public void bTest3(){
		setDirection("bottom-left");
		setEvent("onmouseover");
		setGroupDirection("top-up");
		setHideDelay(10);
		setShowDelay(10);
		setHorizontalOffset(5);
		setVerticalOffset(5);
		setJointPoint("top-left");
		setMode("server");
		setPopupWidth("50px");
		setDisabled(false);
	}

	public void bTest4(){
		setDirection("top-left");
		setEvent("onmouseover");
		setGroupDirection("auto");
		setHideDelay(0);
		setShowDelay(0);
		setHorizontalOffset(0);
		setVerticalOffset(0);
		setJointPoint("auto");
		setMode("ajax");
		setPopupWidth("auto");
		setDisabled(true);
	}
	
	public void bTest5(){
		setDirection("auto");
		setEvent("onmouseover");
		setGroupDirection("auto");
		setHideDelay(1);
		setShowDelay(5);
		setHorizontalOffset(2);
		setVerticalOffset(2);
		setJointPoint("auto");
		setMode("ajax");
		setPopupWidth("auto");
		setDisabled(true);
	}

	public boolean isDisabledDDM() {
		return disabledDDM;
	}

	public void setDisabledDDM(boolean disabledDDM) {
		this.disabledDDM = disabledDDM;
	}

	public String getAction() {
		return action;
	}
}
