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
import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;


/**
 * @author $Autor$
 *
 */
public class Bean {
	private List lists = new ArrayList();

	private List<ConvertableDemoBean> genericLists = new ArrayList<ConvertableDemoBean>();

	private String [] simpleItems = new String [] {
			"First", "Second", "Third", "Fourth"	
	};
	
	private List zebraItems = new ArrayList();
	
	public Bean() {
		for (int i = 0; i < 3; i++) {
			lists.add(new OrderingListDemoBean());
		}
	
		for (int i = 0; i < 10; i++) {
			genericLists.add(new ConvertableDemoBean("#" + i, i));
		}

		for (int i = 0; i < 6; i++) {
			zebraItems.add(String.valueOf(i));
		}
	}

	public List getLists() {
		return lists;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}

	public List<ConvertableDemoBean> getGenericList() {
		return this.genericLists;
	}
	
	public void setGenericList(List<ConvertableDemoBean> list) {
		this.genericLists = list;
	}

	public String[] getSimpleItems() {
		return simpleItems;
	}

	public void setSimpleItems(String[] simpleItems) {
		this.simpleItems = simpleItems;
	}

	private UIComponent eventsBouncer;
	
	public UIComponent getEventsBouncer() {
		if (eventsBouncer == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIComponent output = facesContext.getApplication().createComponent(UIOutput.COMPONENT_TYPE);
			UIOutput o = new UIEventsBouncer();
			
			o.getAttributes().put("escape", Boolean.FALSE);
			output.getChildren().add(o);

			eventsBouncer = output;
		}
		
		return eventsBouncer;
	}
	
	public void setEventsBouncer(UIComponent component) {
		this.eventsBouncer = component;
	}
	
	private Collection selection;
	
	private Object activeItem;
	
	public Collection getSelection() {
		return selection;
	}
	
	public void setSelection(Collection selection) {
		System.out.println("Bean.setSelection() " + selection);
		this.selection = selection;
	}

	public Object getActiveItem() {
		return activeItem;
	}
	
	public void setActiveItem(Object activeItem) {
		System.out.println("Bean.setActiveItem() " + activeItem);
		this.activeItem = activeItem;
	}
	
	public List getZebraItems() {
		return zebraItems;
	}
	
	public void setZebraItems(List zebraItems) {
		this.zebraItems = zebraItems;
	}
}