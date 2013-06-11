package org.richfaces.helloworld.domain.inplaceSelect;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlInplaceSelect;

@Name("inplaceSelect")
@Scope(ScopeType.SESSION)
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
	private int tabindex;
	private Object value;
	private String valueCL;
	private HtmlInplaceSelect myInplaceSelect = null;
	private String bindLabel;
	private String layout;

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
		bindLabel = "Click binding";
		required = false;
		requiredMessage="requiredMessage";
		layout = "inline";
	}	
	
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	public void setmyInplaceSelect(HtmlInplaceSelect myInplaceSelect){
		this.myInplaceSelect = myInplaceSelect;
	}
	
	public HtmlInplaceSelect getmyInplaceSelect(){
		return myInplaceSelect;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myInplaceSelect);
		return null;
	}
	
	public void setbindLabel(String bindLabel){
		this.bindLabel = bindLabel;
	}
	
	public String getbindLabel(){
		return bindLabel;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myInplaceSelect.getClientId(context);
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

	public HtmlInplaceSelect getMyInplaceSelect() {
		return myInplaceSelect;
	}

	public void setMyInplaceSelect(HtmlInplaceSelect myInplaceSelect) {
		this.myInplaceSelect = myInplaceSelect;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

}
