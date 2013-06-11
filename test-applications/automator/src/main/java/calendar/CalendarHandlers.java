package calendar;

import general.DrawGrids;

import java.util.ArrayList;

import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UICalendar;

import parser.Attribute;
import parser.TLDParser;

public class CalendarHandlers {	
	private HtmlPanelGrid panelGrid;
	private final TLDParser tldParser = new TLDParser("calendar");	
	private final ArrayList<Attribute> handlers = tldParser.getAllAttributes().getHandlers();
	
	public CalendarHandlers(){
		ArrayList<String> allHandlers = tldParser.getAllHandlers();
		for(String s:allHandlers){
			System.out.println(s);
		}		
	}

	public void testEventHandlers(ActionEvent e){
		DrawGrids.showEventGrid(panelGrid, handlers);
	}	
	
	public HtmlPanelGrid getPanelGrid() {
		return panelGrid;
	}

	public void setPanelGrid(HtmlPanelGrid panelGrid) {
		this.panelGrid = panelGrid;
	}

	public TLDParser getTldParser() {
		return tldParser;
	}

	public ArrayList<Attribute> getHandlers() {
		return handlers;
	}
}
