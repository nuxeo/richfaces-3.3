package org.richfaces.helloworld.domain.combobox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.richfaces.helloworld.domain.util.data.Data;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.UIComboBox;

/**
 * @author Mikhail Vitenkov
 *
 */
@Name("combobox")
@Scope(ScopeType.SESSION)
public class Combobox {
	public boolean disabled;
	public String defaultLabel;
	public boolean filterNewValues;
	public String hideDelay;
	public boolean directInputSuggestions;
	public boolean immediate;
	public String width;
	public Data value;
	public int tabindex;
	public List<Data> suggestionValues;
	public int size;
	public String showDelay;
	public boolean required;
	public String requiredMessage;
	public boolean rendered;
	public boolean selectFirstOnUpdate;
	public boolean enableManualInput;
	public String listHeight;
	public String listWidth;
	public ArrayList<SelectItem> selectItem;
	private UIComboBox myComboBox = null;
	private String bindLabel;
	private String align;

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
	
	public void add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myComboBox);
	}

	public Combobox() {
		this.disabled = false;
		this.defaultLabel = "defaultLabel";
		this.filterNewValues = false;
		this.hideDelay = "100";
		this.directInputSuggestions = true;
		this.immediate = false;
		this.width = "300";
		this.value = new Data("default",0);
		this.tabindex = 2;
		this.showDelay = "200";
		this.required = false;
		this.requiredMessage = "requiredMessage";
		this.rendered = true;
		this.selectFirstOnUpdate = true;
		this.enableManualInput = true;
		this.listHeight = "400";
		this.listWidth = "350";
		this.suggestionValues = new ArrayList<Data>();
		this.selectItem = new ArrayList<SelectItem>();
		this.bindLabel = "Click Binding";
		this.align = "left";
		Random r = new Random(); 
		for(int i = 0; i < 10; i++){
			suggestionValues.add(new Data("selectItem", i));
			selectItem.add(new SelectItem("selectItem"));
			System.out.println(selectItem.get(i).getValue().toString());
		}
	}

	public ArrayList<SelectItem> getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(ArrayList<SelectItem> selectItem) {
		this.selectItem = selectItem;
	}

	public void valueChangeListener(ValueChangeEvent event) {
		System.out.println(event.getNewValue());
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public String getHideDelay() {
		return hideDelay;
	}

	public void setHideDelay(String hideDelay) {
		this.hideDelay = hideDelay;
	}

	public boolean isDirectInputSuggestions() {
		return directInputSuggestions;
	}

	public void setDirectInputSuggestions(boolean directInputSuggestions) {
		this.directInputSuggestions = directInputSuggestions;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Data getValue() {
		return value;
	}

	public void setValue(Data value) {
		this.value = value;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public List<Data> getSuggestionValues() {
		return suggestionValues;
	}

	public void setSuggestionValues(List<Data> suggestionValues) {
		this.suggestionValues = suggestionValues;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isFilterNewValues() {
		return filterNewValues;
	}

	public void setFilterNewValues(boolean filterNewValues) {
		this.filterNewValues = filterNewValues;
	}

	public String getShowDelay() {
		return showDelay;
	}

	public void setShowDelay(String showDelay) {
		this.showDelay = showDelay;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isSelectFirstOnUpdate() {
		return selectFirstOnUpdate;
	}

	public void setSelectFirstOnUpdate(boolean selectFirstOnUpdate) {
		this.selectFirstOnUpdate = selectFirstOnUpdate;
	}

	public boolean isEnableManualInput() {
		return enableManualInput;
	}

	public void setEnableManualInput(boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public String getListHeight() {
		return listHeight;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}


	public UIComboBox getMyComboBox() {
		return myComboBox;
	}

	public void setMyComboBox(UIComboBox myComboBox) {
		this.myComboBox = myComboBox;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myComboBox.getClientId(context);
	}

}
