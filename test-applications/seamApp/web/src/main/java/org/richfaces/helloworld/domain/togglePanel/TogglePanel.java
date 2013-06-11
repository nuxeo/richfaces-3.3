package org.richfaces.helloworld.domain.togglePanel;

import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlTogglePanel;

@Name("togglePanel")
@Scope(ScopeType.SESSION)
public class TogglePanel {
	
	private String initialState;
	private String stateOrder;
	private String switchType;
	private HtmlTogglePanel htmlToglePanel = null;
	
	public HtmlTogglePanel getHtmlToglePanel() {
		return htmlToglePanel;
	}

	public void setHtmlToglePanel(HtmlTogglePanel htmlToglePanel) {
		this.htmlToglePanel = htmlToglePanel;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlToglePanel);
		return null;
	}

	public TogglePanel() {
		initialState="asus";
		switchType="server";
		stateOrder="asus,benq,toshiba";
	}

	public String getInitialState() {
		return initialState;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public String getStateOrder() {
		return stateOrder;
	}

	public void setStateOrder(String stateOrder) {
		this.stateOrder = stateOrder;
	}

	public String getSwitchType() {
		return switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public void bTest1() {
		setInitialState("asus");
		setStateOrder("asus,benq,toshiba");
		setSwitchType("client");
	}
	
	public void bTest2() {
		setInitialState("benq");
		setStateOrder("toshiba,asus,benq");
		setSwitchType("client");
	}
	
	public void bTest3() {
		setInitialState("asus");
		setStateOrder("asus,benq,toshiba");
		setSwitchType("server");
	}
	
	public void bTest4() {
		setInitialState("benq");
		setStateOrder("asus,benq,toshiba");
		setSwitchType("ajax");
	}
	
	public void bTest5() {
		setInitialState("toshiba");
		setStateOrder("toshiba,asus,benq");
		setSwitchType("ajax");
	}
}
