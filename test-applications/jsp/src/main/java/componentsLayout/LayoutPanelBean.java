package componentsLayout;

import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlLayoutPanel;

import util.componentInfo.ComponentInfo;

public class LayoutPanelBean {
	
	private String position;
	private String[] pos = {"left","right","center","top","bottom"};
	private ArrayList<SelectItem> positions;
	private boolean rendered;
	private HtmlLayoutPanel htmlLayoutPanel;
	private String layoutPanelLabel;
	
	public void addLayoutPanel(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlLayoutPanel);
	}
	
	public void checkLayoutPanel(ActionEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		layoutPanelLabel = htmlLayoutPanel.getClientId(context);
	}
	
	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public HtmlLayoutPanel getHtmlLayoutPanel() {
		return htmlLayoutPanel;
	}

	public void setHtmlLayoutPanel(HtmlLayoutPanel htmlLayoutPanel) {
		this.htmlLayoutPanel = htmlLayoutPanel;
	}

	public String getLayoutPanelLabel() {
		return layoutPanelLabel;
	}

	public void setLayoutPanelLabel(String layoutPanelLabel) {
		this.layoutPanelLabel = layoutPanelLabel;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public LayoutPanelBean() {
		positions = new ArrayList<SelectItem>();
		for(int i=0;i<pos.length;i++){
			positions.add(new SelectItem(pos[i],pos[i]));
		}
		rendered = true;
	}

	public LayoutPanelBean(String position) {
		this.position = position;
	}

	public ArrayList<SelectItem> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<SelectItem> positions) {
		this.positions = positions;
	}
}