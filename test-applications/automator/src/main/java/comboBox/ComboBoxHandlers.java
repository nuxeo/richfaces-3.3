package comboBox;

import general.DrawGrids;

import java.util.ArrayList;

import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;

import parser.Attribute;
import parser.AttributesList;
import parser.TLDParser;

public class ComboBoxHandlers {

	private HtmlPanelGrid panelGrid;

	private TLDParser tldParser = new TLDParser("comboBox");
	private AttributesList attrs = tldParser.getAllAttributes();
	private ArrayList<Attribute> handlers = attrs.getHandlers();

	public ComboBoxHandlers() {

	}

	public void testHandlers(ActionEvent e) {
		DrawGrids.showEventGrid(panelGrid, handlers);
	}

	public HtmlPanelGrid getPanelGrid() {
		return panelGrid;
	}

	public void setPanelGrid(HtmlPanelGrid panelGrid) {
		this.panelGrid = panelGrid;
	}
}
