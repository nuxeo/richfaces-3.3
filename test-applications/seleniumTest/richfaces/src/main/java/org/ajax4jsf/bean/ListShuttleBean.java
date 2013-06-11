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
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.model.ListShuttleItem;

public class ListShuttleBean {
	private List<ListShuttleItem> items = null;
	private List<ListShuttleItem> freeItems = null;
	
	private Set<ListShuttleItem> sourceSelection;
	private Set<ListShuttleItem> targetSelection;
	
	private boolean controlsVisible = true;
	
	private boolean sourceRequired;
	private boolean targetRequired;
	
	private boolean showButtonLabels;
	private boolean switchByClick;
	private boolean rendered;
	private boolean immediate;
	private String string;
	private boolean valueChangeListener;
	private boolean attrValidator;
	private boolean tagValidator;
	private Boolean renderControlFacet;
	
	public ListShuttleBean() {
		init();
	}
	
	public void init() {
		sourceRequired = false;
		targetRequired = false;
		showButtonLabels = true;
		switchByClick = false;
		rendered = true;
		immediate = false;
		valueChangeListener = false;
		string = "something";
		valueChangeListener = false;
		sourceSelection = null;
		targetSelection = null;
		attrValidator = false;
		tagValidator = false;
		items = new ArrayList<ListShuttleItem>();
		freeItems = new ArrayList<ListShuttleItem>();
		for (int i = 0; i < 5; i++) {
			ListShuttleItem item = new ListShuttleItem(i + 1, "Item" + (i + 1));
			freeItems.add(item);
		}
		controlsVisible = true;
		renderControlFacet = false;
	}
	
	public void hide(ActionEvent event) {
		controlsVisible = false;
	}
	
	public void reset(ActionEvent event) {
		init();
	}
	
	public List<ListShuttleItem> getItems() {
		return items;
	}
	public void setItems(List<ListShuttleItem> items) {
		this.items = items;
	}

	public List<ListShuttleItem> getFreeItems() {
		return freeItems;
	}

	public void setFreeItems(List<ListShuttleItem> freeItems) {
		this.freeItems = freeItems;
	}

	/**
	 * @return the controlsVisible
	 */
	public boolean isControlsVisible() {
		return controlsVisible;
	}

	/**
	 * @param controlsVisible the controlsVisible to set
	 */
	public void setControlsVisible(boolean controlsVisible) {
		this.controlsVisible = controlsVisible;
	}

	public boolean getSourceRequired() {
		return sourceRequired;
	}

	public void setSourceRequired(boolean sourceRequired) {
		this.sourceRequired = sourceRequired;
	}

	public boolean getTargetRequired() {
		return targetRequired;
	}

	public void setTargetRequired(boolean targetRequired) {
		this.targetRequired = targetRequired;
	}

	public boolean isShowButtonLabels() {
		return showButtonLabels;
	}

	public void setShowButtonLabels(boolean showButtonLabels) {
		this.showButtonLabels = showButtonLabels;
	}

	public boolean isSwitchByClick() {
		return switchByClick;
	}

	public void setSwitchByClick(boolean switchByClick) {
		this.switchByClick = switchByClick;
	}

	public boolean getRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public void valueChangeListener(ValueChangeEvent event) {
		valueChangeListener = true;
	}
	
	public void submit() {
		if (valueChangeListener) {
			string = "value was changed";
		}
	}

	public void setTargetSelection(Set<ListShuttleItem> targetSelection) {
		this.targetSelection = targetSelection;
	}

	public Set<ListShuttleItem> getTargetSelection() {
		return targetSelection;
	}

	public Object[] getTargetSelection2() {
		if (targetSelection != null) {
			return targetSelection.toArray();			
		} else {
			return null;
		}
	}

	public void setSourceSelection(Set<ListShuttleItem> sourceSelection) {
		this.sourceSelection = sourceSelection;
	}

	public Set<ListShuttleItem> getSourceSelection() {
		return sourceSelection;
	}
	
	public Object[] getSourceSelection2() {
		if (sourceSelection != null) {
			return sourceSelection.toArray();			
		} else {
			return null;
		}
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (attrValidator) {
			throw new ValidatorException(new FacesMessage("attrValidator"));
		}
	}

	public void setAttrValidator(boolean attrValidator) {
		this.attrValidator = attrValidator;
	}

	public boolean isAttrValidator() {
		return attrValidator;
	}

	public void setTagValidator(boolean tagValidator) {
		this.tagValidator = tagValidator;
	}

	public boolean isTagValidator() {
		return tagValidator;
	}

	public void setRenderControlFacet(Boolean renderControlFacet) {
		this.renderControlFacet = renderControlFacet;
	}

	public Boolean getRenderControlFacet() {
		return renderControlFacet;
	}
}
