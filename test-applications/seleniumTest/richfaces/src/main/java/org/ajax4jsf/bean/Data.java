/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.bean;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class Data {
	
	private static final String[] mnames={"Jan","Feb","Mar","Apr","May"};
	
	private List mounths = new ArrayList(); 
	
	private List numbers = new ArrayList();
	
	private boolean c3rendered=true;

	private boolean c2rendered=true;

	/**
	 * @return the c3rendered
	 */
	public boolean isC3rendered() {
		return this.c3rendered;
	}

	/**
	 * @param c3rendered the c3rendered to set
	 */
	public void setC3rendered(boolean c3rendered) {
		this.c3rendered = c3rendered;
	}

	public String toggleColumn() {
		this.c3rendered = !this.c3rendered;
		return null;
	}
	/**
	 * @return the numbers
	 */
	public List getNumbers() {
		return this.numbers;
	}

	/**
	 * @param numbers the numbers to set
	 */
	public void setNumbers(List numbers) {
		this.numbers = numbers;
	}

	public Data() {
		Properties properties = System.getProperties();
		Enumeration keys = properties.keys();
		for(int i=0;i<5;i++){
			DataBean bean = new DataBean();
			int l = (int)(Math.random()*5)+1;
			bean.setTotal(0);
			bean.setMounth(mnames[i]);
			mounths.add(bean);
			for(int j=0;j<l;j++){
				ChildBean child = new ChildBean();
				child.setName((String) keys.nextElement());
				int qty = (int)(Math.random()*10);
				bean.setTotal(bean.getTotal()+qty);
				child.setQty(qty);
				bean.getDetail().add(child);
			}
		}
		for(int i=0;i<16;i++){
			numbers.add(new Integer(i));
		}
	}

	/**
	 * @return the mounths
	 */
	public List getMounths() {
		return this.mounths;
	}

	/**
	 * @param mounths the mounths to set
	 */
	public void setMounths(List mounths) {
		this.mounths = mounths;
	}

	/**
	 * @return the c2rendered
	 */
	public boolean isC2rendered() {
		return this.c2rendered;
	}

	/**
	 * @param c2rendered the c2rendered to set
	 */
	public void setC2rendered(boolean c2rendered) {
		this.c2rendered = c2rendered;
	}

	public int getC2span(){
		return c3rendered?1:2;
	}

	public int getC1span(){
		int i = c2rendered?1:c3rendered?2:3;
		return i;
	}
}
