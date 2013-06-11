package org.richfaces.helloworld.domain.util.data;

public class Car {

	private String make;

	private String model;

	private Integer price;

	public Car() {
		this.make = "";
		this.model = "";
		this.price = 0;
	}
	
	public Car(String mak, String mod, Integer pr) {
		this.make = mak;
		this.model = mod;
		this.price = pr;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Car) {
			Car car = (Car) obj;
			if (this.make.equals(car.make) && (this.model.equals(car.model))
					&& (this.price.equals(car.price))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.make + ":" + this.model + ":" + this.price;
	}
}