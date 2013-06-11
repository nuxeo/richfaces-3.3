/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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

package org.richfaces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * @author k
 * 
 */
public class Bean {
	public class Pair {

		private String first;
		private String second;

		public Pair(int i) {
			if(Package.getPackages().length > i) {
				first = Package.getPackages()[i].getName();
				second = Package.getPackages()[i].getImplementationTitle();
			}
		}

		public void setFirst(String first) {
			this.first = first;
		}

		public String getFirst() {
			return first;
		}

		public void setSecond(String second) {
			this.second = second;
		}

		public String getSecond() {
			return second;
		}
	}

	private List<Pair> model = new ArrayList<Pair>();

	private String param;
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Bean() {
		for (int i = 0; i < 100; i++) {
			model.add(new Pair(i));
		}
	}

	public List<Pair> getModel() {
		return model;
	}

	public void setModel(List<Pair> model) {
		this.model = model;
	}
	
	public void actionListener(ActionEvent event) {
		//System.out.println("Bean.actionListener()" + event.getComponent());
	}
	
	public void delete() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Package deleted: " + param));
		//System.out.println("Bean.action()" + param);
	}
	
	public void edit() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Package edited: " + param));
		//System.out.println("Bean.action()" + param);
	}
	
	

	
}