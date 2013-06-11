package dataGrid;
import java.util.ArrayList;
import java.util.List;
import org.richfaces.component.html.HtmlDataGrid;


import util.componentInfo.ComponentInfo;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class DataGrid {
	
	public static final String[] carMake = {"Mersedes-Benz", "Audi", "BMW", "Ford", "Ferrari", "Jaguar", "Bently", "Rolls-Royse", "Bugatti", "Porshe"};
	public static final String[] carModel = {"S65AMG", "RS4", "M6", "GT40", "550 Maranello", "XJ", "Brookland", "Phantom", "Veyron", "Carerra GT"};
	public static final String[] carMileage = {"0 km", "0 km", "0 km", "0 km", "0 km", "123 km", "0 km", "250 km", "0 km", ""};
	public static final String[] carPrice = {"125000 eur", "55000 eur", "75000 eur", "255000 eur", "246000 eur", "85000 usd", "430000 eur", "325000 eur", "1200000 eur", "750000 usd"};	
	
	public DataGrid() {
		border = "2px";
		dir = "LTR";
		bindLabel = "not ready";
		elements = "4";
		first = "0";
		columns = "2";
		cellpadding = "1";
		cellspacing = "1";
		rendered = true;
		width = "";
		
		for(int i = 0; i < carMake.length; i++) {
			Car car = new Car(carMake[i], carModel[i], carMileage[i], carPrice[i]);
			allCars.add(car);
		}
	}
	
	private List<Car> allCars = new ArrayList<Car>();
	private HtmlDataGrid myDataGrid = null;
	private String bindLabel;
	private String elements;
	private HtmlDataGrid htmlDataGrid = null;
	private String border;
	private String dir;
	private String first;
	private String columns;
	private String cellpadding;
	private String cellspacing;
	private boolean rendered;
	private String width;
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getCellpadding() {
		return cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public String getCellspacing() {
		return cellspacing;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void addHtmlDataGrid(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlDataGrid);
	}
	
	public String getElements() {
		return elements;
	}

	public void setElements(String elements) {
		this.elements = elements;
	}

	public String submit() {
		return null;
	}

	public void submitAjax() {
		
	}

	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myDataGrid.getBaseClientId(context);
	}	
	
	public List<Car> getAllCars() {
		return allCars;
	}

	public void setAllCars(List<Car> allCars) {
		this.allCars = allCars;
	}

	public HtmlDataGrid getMyDataGrid() {
		return myDataGrid;
	}

	public void setMyDataGrid(HtmlDataGrid myDataGrid) {
		this.myDataGrid = myDataGrid;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public HtmlDataGrid getHtmlDataGrid() {
		return htmlDataGrid;
	}

	public void setHtmlDataGrid(HtmlDataGrid htmlDataGrid) {
		this.htmlDataGrid = htmlDataGrid;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
