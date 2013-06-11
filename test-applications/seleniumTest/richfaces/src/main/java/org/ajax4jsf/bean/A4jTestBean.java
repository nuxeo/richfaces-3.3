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

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class A4jTestBean {

	private String param = "Not set yet";
	private boolean checked;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	private void checkMap() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		for (String key : params.keySet()) {
			System.out.println(key + "=" + params.get(key));
		}
	}
	
	public void perform(ActionEvent actionEvent) {
		System.out.println("A4jTestBean.perform(ActionEvent) = " + param);
		checkMap();
	}

	public void perform() {
		System.out.println("A4jTestBean.perform() = " + param);
		checkMap();
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
