package contextMenu;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UIContextMenu;

import rich.RichBean;
import util.componentInfo.ComponentInfo;

public class ContextMenu {
	private String info;
	private String inputText;
    private String submitMode;
	private String event;
    private String popupWidth;
    private String selectOneMenu;
    private int hideDelay;
    private int showDelay;
    private boolean disableDefaultMenu;
    private boolean rendered;
	private boolean disamble;
	private boolean attached;
	private UIContextMenu htmlContextMenu;
    
	public UIContextMenu getHtmlContextMenu() {
		return htmlContextMenu;
	}

	public void setHtmlContextMenu(UIContextMenu htmlContextMenu) {
		this.htmlContextMenu = htmlContextMenu;
	}
	
	public String addHtmlContextMenu(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlContextMenu);
		return null;
	}
	
	public boolean isAttached() {
		return attached;
	}

	public void setAttached(boolean attached) {
		this.attached = attached;
	}

	public String getInfo() {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		Map params = facesContext.getExternalContext().getRequestParameterMap(); 
		String cmdParam = (String) params.get("cmdParam");
		if (cmdParam != null) info = cmdParam;
		System.out.println(info);
		return info;
	}

	public void actionListener(ActionEvent event) {
		setInfo("actionListener");
	}
	
	public void setInfo(String info) {
		System.out.println("Info: " + info);
		this.info = info;
	}

	public ContextMenu() {
		this.selectOneMenu = "item1";
		this.inputText = "inputText";
		this.submitMode = "none";
		this.event = "oncontextmenu";
		this.popupWidth = "300px";
		this.hideDelay = 3;
		this.showDelay = 3;
		this.disableDefaultMenu = true;
		this.rendered = true;
		this.disamble = false;
		this.attached = true;
		this.info = "";
	}

	public boolean isDisamble() {
		return disamble;
	}

	public void setDisamble(boolean disamble) {
		this.disamble = disamble;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getSubmitMode() {
		return submitMode;
	}

	public void setSubmitMode(String submitMode) {
		this.submitMode = submitMode;
	}

	public boolean isDisableDefaultMenu() {
		return disableDefaultMenu;
	}

	public void setDisableDefaultMenu(boolean disableDefaultMenu) {
		this.disableDefaultMenu = disableDefaultMenu;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getPopupWidth() {
		return popupWidth;
	}

	public void setPopupWidth(String popupWidth) {
		this.popupWidth = popupWidth;
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

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public String getSelectOneMenu() {
		return selectOneMenu;
	}

	public void setSelectOneMenu(String selectOneMenu) {
		this.selectOneMenu = selectOneMenu;
	}
}
