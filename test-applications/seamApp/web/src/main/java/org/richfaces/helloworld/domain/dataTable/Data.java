package org.richfaces.helloworld.domain.dataTable;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ValueChangeEvent;


public class Data {
	private List detail = new ArrayList();
	private String mounth;
	private String town;
	private String day;
	private int total;
	private int price;

	private boolean checked=true;
	/**
	 * 
	 */
	public Data() {
	}

	/**
	 * @return the detail
	 */
	public List getDetail() {
		return this.detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(List detail) {
		this.detail = detail;
	}

	/**
	 * @return the mounth
	 */
	public String getMounth() {
		return this.mounth;
	}

	/**
	 * @param mounth the mounth to set
	 */
	public void setMounth(String mounth) {
		this.mounth = mounth;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return this.total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		System.out.println("Invoke get checked for mounth "+getMounth()+", checked:"+checked);
		return this.checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		System.out.println("Invoke set checked for mounth "+getMounth()+", checked:"+checked);
		this.checked = checked;
	}
	
	public String check(){
		checked = !checked;
		System.out.println("Invoke check action for mounth "+getMounth()+", checked:"+checked);
		return null;
	}
	
	public void checkChanged(ValueChangeEvent event) {
		System.out.println("Checked changed for mounth "+getMounth());		
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
	    return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
	    this.price = price;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
