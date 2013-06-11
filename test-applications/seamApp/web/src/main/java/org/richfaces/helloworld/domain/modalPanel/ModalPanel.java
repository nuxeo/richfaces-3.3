package org.richfaces.helloworld.domain.modalPanel;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlModalPanel;

@Name("modalPanel")
@Scope(ScopeType.SESSION)
public class ModalPanel {
	private boolean autosized;
	private boolean keepVisualState;
	private String left;
	private String top;
	private boolean rendered;
	private String shadowDepth;
	private String shadowOpacity;
	private boolean showWhenRendered;
	private int zindex;
	private int minHeight;
	private int minWidth;
	private int height;
	private int width;
	private boolean moveable;
	private boolean resizeable;
	private String inputTextTest;
	private String selectOneListboxTest;
	private String visualOptions;
	private String bindLabel;
	private HtmlModalPanel htmlModalPanel;

	public String getVisualOptions() {
		return visualOptions;
	}

	public void setVisualOptions(String visualOptions) {
		this.visualOptions = visualOptions;
	}

	public String getInputTextTest() {
		return inputTextTest;
	}

	public void setInputTextTest(String inputTextTest) {
		this.inputTextTest = inputTextTest;
	}

	public String getSelectOneListboxTest() {
		return selectOneListboxTest;
	}

	public void setSelectOneListboxTest(String selectOneListboxTest) {
		this.selectOneListboxTest = selectOneListboxTest;
	}

	public ModalPanel() {
		this.visualOptions = "";
		this.inputTextTest = "text";
		this.selectOneListboxTest = "1";
		this.minHeight = 100;
		this.minWidth = 100;
		this.height = 300;
		this.width = 300;
		this.moveable = true;
		this.resizeable = true;
		this.autosized = false;
		this.keepVisualState = false;
		this.left = "auto";
		this.top = "auto";
		this.rendered = true;
		this.shadowDepth = "3";
		this.shadowOpacity = "3";
		this.zindex = 3;
		this.showWhenRendered = false;
		this.bindLabel = "not ready";
		this.htmlModalPanel = null;
	}

	public void checkBinding(ActionEvent actionEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = htmlModalPanel.getClientId(context);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}

	public void setAutosized(boolean autosized) {
		this.autosized = autosized;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void bTest1() {
		setHeight(300);
		setWidth(450);
		setMinHeight(250);
		setMinWidth(400);
		setMoveable(false);
		setResizeable(false);
	}

	public void bTest2() {
		setHeight(350);
		setWidth(400);
		setMinHeight(400);
		setMinWidth(450);
		setMoveable(true);
		setResizeable(false);
	}

	public void bTest3() {
		setHeight(400);
		setWidth(300);
		setMinHeight(400);
		setMinWidth(300);
		setMoveable(true);
		setResizeable(true);
	}

	public void bTest4() {
		setHeight(450);
		setWidth(450);
		setMinHeight(450);
		setMinWidth(450);
		setMoveable(false);
		setResizeable(true);
	}

	public void bTest5() {
		setHeight(900);
		setWidth(800);
		setMinHeight(700);
		setMinWidth(600);
		setMoveable(true);
		setResizeable(true);
	}

	public boolean isAutosized() {
		return autosized;
	}

	public boolean isKeepVisualState() {
		return keepVisualState;
	}

	public void setKeepVisualState(boolean keepVisualState) {
		this.keepVisualState = keepVisualState;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public boolean getRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getShadowDepth() {
		return shadowDepth;
	}

	public void setShadowDepth(String shadowDepth) {
		this.shadowDepth = shadowDepth;
	}

	public String getShadowOpacity() {
		return shadowOpacity;
	}

	public void setShadowOpacity(String shadowOpacity) {
		this.shadowOpacity = shadowOpacity;
	}

	public boolean isShowWhenRendered() {
		return showWhenRendered;
	}

	public void setShowWhenRendered(boolean showWhenRendered) {
		this.showWhenRendered = showWhenRendered;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public HtmlModalPanel getHtmlModalPanel() {
		return htmlModalPanel;
	}

	public void setHtmlModalPanel(HtmlModalPanel myModalPanel) {
		this.htmlModalPanel = myModalPanel;
	}

	public void addHtmlModalPanel() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlModalPanel);
	}
}
