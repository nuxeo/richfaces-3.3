package inplaceSelect;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlInplaceSelect;

import util.componentInfo.ComponentInfo;

public class InplaceSelect {
	private boolean applyFromControlsOnly;
	private String controlsHorizontalPosition;
	private String controlsVerticalPosition;
	private String defaultLabel;
	private String editEvent;
	private boolean immediate;
	private String listHeight;
	private String listWidth;
	private String maxSelectWidth;
	private String minSelectWidth;
	private boolean openOnEdit;
	private boolean rendered;
	private boolean required;
	private String requiredMessage;
	private String selectWidth;
	private boolean showControls;
	private boolean changedClass;
	private String changedLabel;
	private int tabindex;
	private Object value;
	private String valueCL;
	private HtmlInplaceSelect myInplaceSelect = null;
	private String bindLabel;
	private String layout;
	private List<SelectItem> itemsList;

	public void addHtmlInplaceSelect(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myInplaceSelect);
	}
	
	public InplaceSelect() {
		valueCL = "---";
		editEvent = "onclick";
		maxSelectWidth = "150";
		minSelectWidth = "85";
		selectWidth = "170";
		defaultLabel = "defaultLabel";
		controlsVerticalPosition = "top";
		controlsHorizontalPosition = "center";
		value = "errors";
		listWidth = "200";
		listHeight = "150";
		showControls = false;
		applyFromControlsOnly = false;
		openOnEdit = true;
		rendered = true;
		immediate = false;
		bindLabel = "Click Binding";
		changedLabel = "default";
		changedClass = false;
		required = false;
		requiredMessage="requiredMessage";
		layout = "inline";
		
		itemsList = new ArrayList<SelectItem>();
		itemsList.add(new SelectItem("1", "ItemOne"));
		itemsList.add(new SelectItem("2", "ItemTwo"));
		itemsList.add(new SelectItem("3", "ItemThree"));
		itemsList.add(new SelectItem("4", "ItemFour"));
		itemsList.add(new SelectItem("5", "ItemFive"));
	}	
	
	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	public HtmlInplaceSelect getMyInplaceSelect(){
		return myInplaceSelect;
	}
	
	public void setMyInplaceSelect(HtmlInplaceSelect myInplaceSelect){
		this.myInplaceSelect = myInplaceSelect;
	}
	
	public String getbindLabel(){
		return bindLabel;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myInplaceSelect.getClientId(context);
	}
	
	public void setbindLabel(String bindLabel){
		this.bindLabel = bindLabel;
	}
	
	public void valueChangeListener(ValueChangeEvent event){
		valueCL = "valueChangeListener work!";
	}

	public String getControlsHorizontalPosition() {
		return controlsHorizontalPosition;
	}

	public String getControlsVerticalPosition() {
		return controlsVerticalPosition;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public String getValueCL() {
		return valueCL;
	}

	public void setValueCL(String valueCL) {
		this.valueCL = valueCL;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public String getEditEvent() {
		return editEvent;
	}

	public String getListHeight() {
		return listHeight;
	}

	public String getListWidth() {
		return listWidth;
	}

	public String getMaxSelectWidth() {
		return maxSelectWidth;
	}

	public String getMinSelectWidth() {
		return minSelectWidth;
	}

	public String getSelectWidth() {
		return selectWidth;
	}

	public Object getValue() {
		return value;
	}

	public boolean isApplyFromControlsOnly() {
		return applyFromControlsOnly;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public boolean isOpenOnEdit() {
		return openOnEdit;
	}

	public boolean isRendered() {
		return rendered;
	}

	public boolean isShowControls() {
		return showControls;
	}

	public void setApplyFromControlsOnly(boolean applyFromControlsOnly) {
		this.applyFromControlsOnly = applyFromControlsOnly;
	}

	public void setControlsHorizontalPosition(String controlsHorizontalPosition) {
		this.controlsHorizontalPosition = controlsHorizontalPosition;
	}

	public void setControlsVerticalPosition(String controlsVerticalPosition) {
		this.controlsVerticalPosition = controlsVerticalPosition;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public void setMaxSelectWidth(String maxSelectWidth) {
		this.maxSelectWidth = maxSelectWidth;
	}

	public void setMinSelectWidth(String minSelectWidth) {
		this.minSelectWidth = minSelectWidth;
	}

	public void setOpenOnEdit(boolean openOnEdit) {
		this.openOnEdit = openOnEdit;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public void setSelectWidth(String selectWidth) {
		this.selectWidth = selectWidth;
	}

	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getChangedLabel() {
		return changedLabel;
	}

	public void setChangedLabel(String changedLabel) {
		this.changedLabel = changedLabel;
	}

	public void checkChangedClass(){
		if (changedClass){
     	setChangedLabel("activeTabStyle");	
		}
	}

	public boolean isChangedClass() {
		return changedClass;
	}

	public void setChangedClass(boolean changedClass) {
		this.changedClass = changedClass;
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

	public List<SelectItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<SelectItem> itemsList) {
		this.itemsList = itemsList;
	}


}
