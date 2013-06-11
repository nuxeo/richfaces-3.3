/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

/**
 * @author asmirnov
 *
 */
public class DataBean {
	private List detail = new ArrayList();
	
	private String mounth;
	
	private int total;
	
	private int price;

	private boolean checked=true;
	/**
	 * 
	 */
	public DataBean() {
		// TODO Auto-generated constructor stub
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
	
	public String inc(){
		total++;
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

}
