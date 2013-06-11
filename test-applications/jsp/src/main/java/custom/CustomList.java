package custom;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

public class CustomList {
	private ArrayList<Custom> customList;
	private ArrayList<Custom> carsList;
	private int listSize;
	private int carsSize;

	public CustomList() {
		listSize = 12;
		customList = new ArrayList<Custom>();
		
		for(int i = 0; i < listSize; i++) {
			customList.add(new Custom(i));
		}
	}	
	
	public void cars(ActionEvent e) {
		carsList = new ArrayList<Custom>();
		carsSize = 5;		
		
		carsList.add(new Custom("Ford Fiesta",0));
		carsList.add(new Custom("Ford Mustang",0));
		carsList.add(new Custom("Ford Ka",0));
		carsList.add(new Custom("Ford Mondeo",0));
		carsList.add(new Custom("Ford Focus",0));
		
	}
	
	public void resizeList(int elements){
		customList.clear();
		for(int i = 0; i < elements; i++){
			customList.add(new Custom(i));
		}
	}
	
	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public ArrayList<Custom> getCustomList() {
		return customList;
	}

	public void setCustomList(ArrayList<Custom> testList) {
		this.customList = testList;
	}

	public ArrayList<Custom> getCarsList() {
		return carsList;
	}

	public void setCarsList(ArrayList<Custom> carsList) {
		this.carsList = carsList;
	}

	public int getCarsSize() {
		return carsSize;
	}

	public void setCarsSize(int carsSize) {
		this.carsSize = carsSize;
	}
	
}
