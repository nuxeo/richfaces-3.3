package componentsLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlLayout;

import util.componentInfo.ComponentInfo;

public class LayoutBean {

	private String[] pos = { "left", "right", "center", "top", "bottom" };
	private LayoutPanelBean[] panels;
	private LayoutPanelBean[] tempPanels;
	private final int PANELS_COUNT = pos.length;
	private HtmlLayout htmlLayout;
	private boolean rendered;
	private String layoutLabel;

	public void addLayout(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlLayout);
	}
	
	public void checkLayout(ActionEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		layoutLabel = htmlLayout.getClientId(context);
	}
	
	public String getLayoutLabel() {
		return layoutLabel;
	}

	public void setLayoutLabel(String layoutLabel) {
		this.layoutLabel = layoutLabel;
	}

	public HtmlLayout getHtmlLayout() {
		return htmlLayout;
	}

	public void setHtmlLayout(HtmlLayout htmlLayout) {
		this.htmlLayout = htmlLayout;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public LayoutBean() {
		panels = new LayoutPanelBean[PANELS_COUNT];
		tempPanels = new LayoutPanelBean[PANELS_COUNT];
		for (int i = 0; i < PANELS_COUNT; i++) {
			panels[i] = new LayoutPanelBean(pos[i]);
			tempPanels[i] = new LayoutPanelBean(pos[i]);
		}
		rendered = true;
	}

	public LayoutPanelBean[] getPanels() {
		return panels;
	}

	public void setPanels(LayoutPanelBean[] panels) {
		this.panels = panels;
	}

	public LayoutPanelBean[] getTempPanels() {
		return tempPanels;
	}

	public void setTempPanels(LayoutPanelBean[] tempPanels) {
		this.tempPanels = tempPanels;
	}

	public String validate() {
		List<String> complete = Arrays.asList(pos);
		List<String> specific = new ArrayList<String>();
		for (LayoutPanelBean panelBean : tempPanels) {
			specific.add(panelBean.getPosition());
		}
		if (!specific.containsAll(complete)) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage("position for each panel should be unique!!!"));
		} else
			for (int i = 0; i < PANELS_COUNT; i++) {
				panels[i].setPosition(tempPanels[i].getPosition());
			}
		return null;
	}
}
