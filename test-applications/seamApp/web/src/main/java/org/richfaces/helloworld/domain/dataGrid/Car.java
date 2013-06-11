package org.richfaces.helloworld.domain.dataGrid;

public class Car {

	private String make;
	private String model;
	private String mileage;
	private String price;	
	
	public Car(String make, String model, String mileage, String price) {
		this.make = make;
		this.model = model;
		this.mileage = mileage;
		this.price = price;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
