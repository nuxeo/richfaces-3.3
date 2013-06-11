package pickList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.richfaces.component.html.HtmlPickList;

import util.componentInfo.ComponentInfo;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class PickList {
	public String copyAllControlLabel;
	public String copyControlLabel;
	public boolean disabled;
	public boolean fastOrderControlsVisible;
	public boolean immediate;
	public String listsHeight;
	public String localValueSet;
	public String moveControlsVerticalAlign;
	public String removeAllControlLabel;
	public String removeControlLabel;
	public boolean rendered;
	public boolean showButtonLabels;
	public String sourceListWidth;
	public boolean switchByClick;
	public String targetListWidth;
	public String title;
	private ArrayList<SelectItem> data;
	private boolean required;
	private String requiredMessage;
	private Object[] value;
	private String valueCL;
	private HtmlPickList myPickList = null;
	private String bindLabel;
	
	public PickList() {
		this.copyAllControlLabel = "copyAllControlLabel";
		this.copyControlLabel = "copyControlLabel";
		this.disabled = false;
		this.fastOrderControlsVisible = true;
		this.immediate = false;
		this.listsHeight = "400";
		this.valueCL = "---";
		// this.localValueSet = ;
		this.moveControlsVerticalAlign = "center";
		this.removeAllControlLabel = "removeAllControlLabel";
		this.removeControlLabel = "removeControlLabel";
		this.rendered = true;
		this.showButtonLabels = true;
		this.sourceListWidth = "300";
		this.switchByClick = false;
		this.targetListWidth = "400";
		this.title = "title";
		this.required = false;
		this.requiredMessage = "requiredMessage";
		bindLabel = "Click Binding";
		data = new ArrayList<SelectItem>();
		for (int i = 0; i < 10; i++)
			data.add(new SelectItem("selectItems " + i));

	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myPickList.getClientId(context);
	}

	public HtmlPickList getMyPickList() {
		return myPickList;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myPickList);
		return null;
	}

	public void setMyPickList(HtmlPickList myPickList) {
		this.myPickList = myPickList;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public String getValueCL() {
		return valueCL;
	}

	public void setValueCL(String valueCL) {
		this.valueCL = valueCL;
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

	public void valueChangeListener(ValueChangeEvent event) {
		valueCL = "valueChangeListener work!";
	}

	public String getCopyAllControlLabel() {
		return copyAllControlLabel;
	}

	public void setCopyAllControlLabel(String copyAllControlLabel) {
		this.copyAllControlLabel = copyAllControlLabel;
	}

	public String getCopyControlLabel() {
		return copyControlLabel;
	}

	public void setCopyControlLabel(String copyControlLabel) {
		this.copyControlLabel = copyControlLabel;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isFastOrderControlsVisible() {
		return fastOrderControlsVisible;
	}

	public void setFastOrderControlsVisible(boolean fastOrderControlsVisible) {
		this.fastOrderControlsVisible = fastOrderControlsVisible;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getListsHeight() {
		return listsHeight;
	}

	public void setListsHeight(String listsHeight) {
		this.listsHeight = listsHeight;
	}

	public String getLocalValueSet() {
		return localValueSet;
	}

	public void setLocalValueSet(String localValueSet) {
		this.localValueSet = localValueSet;
	}

	public String getMoveControlsVerticalAlign() {
		return moveControlsVerticalAlign;
	}

	public void setMoveControlsVerticalAlign(String moveControlsVerticalAlign) {
		this.moveControlsVerticalAlign = moveControlsVerticalAlign;
	}

	public String getRemoveAllControlLabel() {
		return removeAllControlLabel;
	}

	public void setRemoveAllControlLabel(String removeAllControlLabel) {
		this.removeAllControlLabel = removeAllControlLabel;
	}

	public String getRemoveControlLabel() {
		return removeControlLabel;
	}

	public void setRemoveControlLabel(String removeControlLabel) {
		this.removeControlLabel = removeControlLabel;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getSourceListWidth() {
		return sourceListWidth;
	}

	public void setSourceListWidth(String sourceListWidth) {
		this.sourceListWidth = sourceListWidth;
	}

	public boolean isSwitchByClick() {
		return switchByClick;
	}

	public void setSwitchByClick(boolean switchByClick) {
		this.switchByClick = switchByClick;
	}

	public String getTargetListWidth() {
		return targetListWidth;
	}

	public void setTargetListWidth(String targetListWidth) {
		this.targetListWidth = targetListWidth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShowButtonLabels() {
		return showButtonLabels;
	}

	public void setShowButtonLabels(boolean showButtonLabels) {
		this.showButtonLabels = showButtonLabels;
	}

	public ArrayList<SelectItem> getData() {
		return data;
	}

	public void setData(ArrayList<SelectItem> data) {
		this.data = data;
	}

	public Object[] getValue() {
		return value;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}
}
