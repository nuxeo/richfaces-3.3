package tooltip;

import org.richfaces.component.html.HtmlToolTip;

import util.componentInfo.ComponentInfo;

public class Tooltip {
	
	private boolean followMouse;
	private boolean rendered;
	private boolean disabled;
	private int horizontalOffset;
	private int verticalOffset;
	private int delay;
	private int hideDelay;
	private int showDelay; 
	private String value;
	private String mode;
	private String direction;
	private String style;
	private String layout;
	private String event;
	private HtmlToolTip htmlToolTip = null;
		
	public HtmlToolTip getHtmlToolTip() {
		return htmlToolTip;
	}

	public void setHtmlToolTip(HtmlToolTip htmlToolTip) {
		this.htmlToolTip = htmlToolTip;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlToolTip);
		return null;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Tooltip() {
		event = "onmouseover";
		showDelay = 100;
		hideDelay = 10;
		followMouse = false;
		rendered = true;
		disabled = false;
		value = "tooltip";
		mode = "client";
		direction = "top-right";
		horizontalOffset = 0;
		verticalOffset = 0;
		delay = 0;
		style = "none";
		layout = "inline";
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean isFollowMouse() {
		return followMouse;
	}

	public void setFollowMouse(boolean followMouse) {
		this.followMouse = followMouse;
	}

	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	public void setHorizontalOffset(int horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getVerticalOffset() {
		return verticalOffset;
	}

	public void setVerticalOffset(int verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void bTest1(){
		setDelay(0);
		setDirection("top-right");
		setDisabled(false);
		setFollowMouse(false);
		setHorizontalOffset(0);
		setVerticalOffset(0);
		setLayout("inline");
		setMode("client");
		setValue("Test1");
		setRendered(true);
	}
	
	public void bTest2(){
		setDelay(0);
		setDirection("top-left");
		setDisabled(false);
		setFollowMouse(false);
		setHorizontalOffset(5);
		setVerticalOffset(5);
		setLayout("block");
		setMode("ajax");
		setValue("Test2");		
		setRendered(true);
	}
	
	public void bTest3(){
		setDelay(0);
		setDirection("bottom-right");
		setDisabled(false);
		setFollowMouse(true);
		setHorizontalOffset(5);
		setVerticalOffset(0);
		setLayout("inline");
		setMode("client");
		setValue("Test3");	
		setRendered(true);
	}
	
	public void bTest4(){
		setDelay(0);
		setDirection("bottom-left");
		setDisabled(false);
		setFollowMouse(true);
		setHorizontalOffset(0);
		setVerticalOffset(5);
		setLayout("block");
		setMode("client");
		setValue("Test4");		
		setRendered(true);
	}

	public void bTest5(){
		setDelay(0);
		setDirection("bottom-right");
		setDisabled(false);
		setFollowMouse(true);
		setHorizontalOffset(-5);
		setVerticalOffset(-5);
		setLayout("inline");
		setMode("ajax");
		setValue("Test5");		
		setRendered(true);
	}

	public int getHideDelay() {
		return hideDelay;
	}

	public void setHideDelay(int hideDelay) {
		this.hideDelay = hideDelay;
	}

	public int getShowDelay() {
		return showDelay;
	}

	public void setShowDelay(int showDelay) {
		this.showDelay = showDelay;
	}
}
