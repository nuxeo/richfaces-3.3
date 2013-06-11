/**
 * License Agreement.
 *
 * Ajax4jsf 1.1 - Natural Ajax for Java Server Faces (JSF)
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

package org.richfaces.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author $Autor$
 *
 */
public class Bean {
	
	String suggestions = "Alabama,Alaska,Arizona,Arkansas,California,Colorado,Connecticut,Delaware,Florida,Massachusetts,Michigan,Georgia,Hawaii,Idaho,Indiana,Iowa,Kansas,Kentucky,Louisiana,Maine,Minnesota,Mississippi,Missouri,Montana,Nebraska";
	List selectItems = new ArrayList();
	private boolean disabled = false;
	private boolean enableManualInput = true;
	private boolean selectFirstOnUpdate = true;
	private boolean filterNewValues = true;
	private boolean directInputSuggestions = true;
	private String defaultMessage = "edit ..."; 
	private String width;
	private String listWidth;
	private String listHeight;
	private String inputSize;
	private String onchangeScript;
	private String onlistcallScript;
	private String onlistcloseScript;
	public String getOnlistcloseScript() {
		return onlistcloseScript;
	}

	public void setOnlistcloseScript(String onlistcloseScript) {
		this.onlistcloseScript = onlistcloseScript;
	}

	private String onitemselectedScript;
	
	
	private String state="J&'<uneau";
	
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Bean() {
		selectItems.add(new SelectItem("District of Columbia"));
		selectItems.add(new SelectItem("Illinois"));
		selectItems.add(new SelectItem("Maryland"));
		selectItems.add(new SelectItem("Nevada"));
		selectItems.add(new SelectItem("New Hampshire"));
		selectItems.add(new SelectItem("New Jersey"));
	}
	
	 public void selectionChanged(ValueChangeEvent evt) {
		 String selectedValue = (String) evt.getNewValue();

		 if (selectedValue.equals("")) {
			 state = "No selected state";
		 } else {
			 state = selectedValue;
		 }
	}
	
	public List getSuggestions() {
		List result = Arrays.asList(suggestions.split(","));
		Collections.shuffle(result);
		return result;
	}

	public void setSuggestions(List suggestions) {
		//this.suggestions = suggestions;
	}

	public List getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List selectItems) {
		this.selectItems = selectItems;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isEnableManualInput() {
		return enableManualInput;
	}

	public void setEnableManualInput(boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public boolean isSelectFirstOnUpdate() {
		return selectFirstOnUpdate;
	}

	public void setSelectFirstOnUpdate(boolean selectFirstOnUpdate) {
		this.selectFirstOnUpdate = selectFirstOnUpdate;
	}

	public boolean isFilterNewValues() {
		return filterNewValues;
	}

	public void setFilterNewValues(boolean filterNewValues) {
		this.filterNewValues = filterNewValues;
	}

	public boolean isDirectInputSuggestions() {
		return directInputSuggestions;
	}

	public void setDirectInputSuggestions(boolean directInputSuggestions) {
		this.directInputSuggestions = directInputSuggestions;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public String getListHeight() {
		return listHeight;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public String getInputSize() {
		return inputSize;
	}

	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}

	public String getOnchangeScript() {
		return onchangeScript;
	}

	public void setOnchangeScript(String onchangeScript) {
		this.onchangeScript = onchangeScript;
	}

	public String getOnlistcallScript() {
		return onlistcallScript;
	}

	public void setOnlistcallScript(String onlistcallScript) {
		this.onlistcallScript = onlistcallScript;
	}

	public String getOnitemselectedScript() {
		return onitemselectedScript;
	}

	public void setOnitemselectedScript(String onitemselectedScript) {
		this.onitemselectedScript = onitemselectedScript;
	}
	
}