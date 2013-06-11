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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.model.ListShuttleItem;

public class OrderingListTestBean {
	private List<ListShuttleItem> items;
	private Collection<ListShuttleItem> selection;
	private Object activeItem;
	
	private Boolean orderControlsVisible;
	private Boolean showButtonLabels;
	private Boolean rendered;
	private String string;
	private Boolean immediate;
	private Boolean valueChanged;
	private Boolean attrValidator;
	private Boolean tagValidator;
	
	public OrderingListTestBean() {
		init();
	}

	public void init() {
		items = new ArrayList<ListShuttleItem>();
		for (int i = 0; i < 4; i++) {
			items.add(new ListShuttleItem(i, "item" + i));
		}
		selection = new LinkedHashSet<ListShuttleItem>();
		orderControlsVisible = true;
		showButtonLabels = true;
		setRendered(true);
		setString("something");
		immediate = false;
		valueChanged = false;
		attrValidator = false;
		tagValidator = false;
	}
	
	public Object getActionResult() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actionResult");
	}
	
	public Collection<ListShuttleItem> getSelection() {
		return selection;
	}
	
	public void setSelection(Collection<ListShuttleItem> selection) {
		this.selection = selection;
	}

	public Object getActiveItem() {
		return activeItem;
	}
	
	public void setActiveItem(Object activeItem) {
		this.activeItem = activeItem;
	}
	
	public List<ListShuttleItem> getItems() {
		return items;
	}

	public void setItems(List<ListShuttleItem> items) {
		this.items = items;
	}
	
	public String getSelectionString() {
		StringBuffer buff = new StringBuffer();
		for (Iterator<ListShuttleItem> it = selection.iterator(); it.hasNext();) {
			ListShuttleItem item = it.next();
			buff.append(item.getName());
			if (it.hasNext()) {
				buff.append(',');
			}
		}
		return buff.toString();
	}


	public void setOrderControlsVisible(Boolean orderControlsVisible) {
		this.orderControlsVisible = orderControlsVisible;
	}

	public Boolean getOrderControlsVisible() {
		return orderControlsVisible;
	}


	public void setShowButtonLabels(Boolean showButtonsLabel) {
		this.showButtonLabels = showButtonsLabel;
	}

	public Boolean getShowButtonLabels() {
		return showButtonLabels;
	}


	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}

	public Boolean getRendered() {
		return rendered;
	}


	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}


	public void setImmediate(Boolean immediate) {
		this.immediate = immediate;
	}

	public Boolean getImmediate() {
		return immediate;
	}
	
	public void valueChangeListener(ValueChangeEvent event) {
		valueChanged = true;
	}

	public void setValueChanged(Boolean valueChanged) {
		this.valueChanged = valueChanged;
	}

	public Boolean getValueChanged() {
		return valueChanged;
	}
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (attrValidator) {
			throw new ValidatorException(new FacesMessage("attrValidator"));
		}
	}

	public void setTagValidator(Boolean tagValidator) {
		this.tagValidator = tagValidator;
	}

	public Boolean getTagValidator() {
		return tagValidator;
	}

	public Boolean getAttrValidator() {
		return attrValidator;
	}

	public void setAttrValidator(Boolean attrValidator) {
		this.attrValidator = attrValidator;
	}
}
