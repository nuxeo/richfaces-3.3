package general;

import java.util.ArrayList;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import parser.Attribute;

public class DrawGrids {

	public static void showResultGrid(HtmlPanelGrid panelGrid, ArrayList<Attribute> attrs) {
		panelGrid.getChildren().clear();

		HtmlOutputText attrNameHeader = new HtmlOutputText();
		attrNameHeader.setValue("Attribute");
		attrNameHeader.setStyle("font-weight: bold; font-size: large");

		HtmlOutputText attrStatusHeader = new HtmlOutputText();
		attrStatusHeader.setValue("Status");
		attrStatusHeader.setStyle("font-weight: bold; font-size: large");

		panelGrid.getChildren().add(attrNameHeader);
		panelGrid.getChildren().add(attrStatusHeader);

		for (Attribute a : attrs) {
			HtmlOutputText attrName = new HtmlOutputText();
			attrName.setValue(a.getName());
			HtmlOutputText attrStatus = new HtmlOutputText();
			attrStatus.setValue(a.getStatus());
			attrStatus.setId(a.getName()+ "ID");

			switch (a.getStatus()) {
			case FAILED:
				attrStatus.setStyle("color: red");
				break;
			case PASSED:
				attrStatus.setStyle("color: green");
				break;
			case NOT_READY:
				attrStatus.setStyle("color: grey");
				break;
			case IMPLEMENTED:
				attrStatus.setStyle("color: blue");
				break;
			}

			panelGrid.getChildren().add(attrName);
			panelGrid.getChildren().add(attrStatus);
		}
	}
	
	public static void showEventGrid(final HtmlPanelGrid panelGrid, final ArrayList<Attribute> events) {
		panelGrid.getChildren().clear();

		HtmlOutputText attrNameHeader = new HtmlOutputText();
		attrNameHeader.setValue("Handlers");
		attrNameHeader.setStyle("font-weight: bold; font-size: large");

		HtmlOutputText attrStatusHeader = new HtmlOutputText();
		attrStatusHeader.setValue("Status");
		attrStatusHeader.setStyle("font-weight: bold; font-size: large");

		panelGrid.getChildren().add(attrNameHeader);
		panelGrid.getChildren().add(attrStatusHeader);

		for (Attribute a : events) {
			HtmlOutputText attrName = new HtmlOutputText();
			attrName.setValue(a.getName());
			HtmlOutputText attrStatus = new HtmlOutputText();
			attrStatus.setValue(a.getStatus());
			attrStatus.setId(a.getName() + "ID");
			
			switch (a.getStatus()) {
			case FAILED:
				attrStatus.setStyle("color: red");
				break;
			case PASSED:
				attrStatus.setStyle("color: green");
				break;
			case NOT_READY:
				attrStatus.setStyle("color: grey");
				break;
			}
			
			panelGrid.getChildren().add(attrName);
			panelGrid.getChildren().add(attrStatus);
		}		
		
		HtmlCommandButton button = new HtmlCommandButton();
		button.setValue("confirm");
		button.setId("confirmButton");		
		//button.addActionListener(new HandlersAListener());
		panelGrid.getChildren().add(button);		
	}	

	/*
	public static void showEventGrid(HtmlPanelGrid panelGrid, ArrayList<Attribute> events) {
		panelGrid.getChildren().clear();

		HtmlOutputText attrNameHeader = new HtmlOutputText();
		attrNameHeader.setValue("Handler");
		attrNameHeader.setStyle("font-weight: bold; font-size: large");

		HtmlOutputText attrStatusHeader = new HtmlOutputText();
		attrStatusHeader.setValue("Status");
		attrStatusHeader.setStyle("font-weight: bold; font-size: large");

		panelGrid.getChildren().add(attrNameHeader);
		panelGrid.getChildren().add(attrStatusHeader);

		for (Attribute a : events) {
			HtmlOutputText attrName = new HtmlOutputText();
			attrName.setValue(a.getName());
			HtmlOutputText attrStatus = new HtmlOutputText();
			attrStatus.setValue(a.getStatus());
			attrStatus.setId(a.getName() + "ID");

			panelGrid.getChildren().add(attrName);
			panelGrid.getChildren().add(attrStatus);
		}
	}
	*/
	
	public static void showStylesGrid(HtmlPanelGrid panelGrid, ArrayList<Attribute> styles) {
		panelGrid.getChildren().clear();

		HtmlOutputText attrNameHeader = new HtmlOutputText();
		attrNameHeader.setValue("Handler");
		attrNameHeader.setStyle("font-weight: bold; font-size: large");

		HtmlOutputText attrStatusHeader = new HtmlOutputText();
		attrStatusHeader.setValue("Status");
		attrStatusHeader.setStyle("font-weight: bold; font-size: large");

		panelGrid.getChildren().add(attrNameHeader);
		panelGrid.getChildren().add(attrStatusHeader);

		for (Attribute a : styles) {
			HtmlOutputText attrName = new HtmlOutputText();
			attrName.setValue(a.getName());
			HtmlOutputText attrStatus = new HtmlOutputText();
			attrStatus.setValue(a.getStatus());
			attrStatus.setId(a.getName() + "ID");

			panelGrid.getChildren().add(attrName);
			panelGrid.getChildren().add(attrStatus);
		}
	}
}
