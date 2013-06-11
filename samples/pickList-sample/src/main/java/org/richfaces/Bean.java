package org.richfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class Bean {

	private List <SelectItem> testList = new ArrayList<SelectItem>();

	private String selectedInfo;
    private String targetListWidth="140px"; 
    private String sourceListWidth="140px";
    private String listsHeight="100px";
    private String copyAllLabel = null;    
    private String copyLabel = null;
    private String removeLabel = null;
    private String removeAllLabel = null;
    private boolean disabled;
    private boolean moveControlsVisible = true;
    private boolean fastMoveControlsVisible = true;
    private boolean copyAllVisible = true;
    private boolean copyVisible = true;
    private boolean removeVisible = true;
    private boolean removeAllVisible = true;
    private boolean switchByClick = false;
    private boolean switchByDblClick = true;


    private List <Animal> listValues = new ArrayList<Animal>();
    
//    private List <String> listValues = new ArrayList<String>();
   
//    private Animal[] arrayValues = new Animal[7];
    
    private Map <String,Animal> store = new HashMap<String,Animal>();
    
    public Bean() {
    	
//    	testList.add(new SelectItem("polecat"));
//    	testList.add(new SelectItem("suricate"));
//    	testList.add(new SelectItem("marshotter"));
//    	testList.add(new SelectItem("dog"));
//    	testList.add(new SelectItem("cat"));
//    	testList.add(new SelectItem("bear"));
//    	testList.add(new SelectItem("wolf"));
//    	
//    	listValues.add("polecat");
    	

    	store.put("polecat", new Animal("polecat"));
    	store.put("suricate", new Animal("suricate"));
    	store.put("marshotter", new Animal("marshotter"));
    	store.put("dog", new Animal("dog"));
    	store.put("cat", new Animal("cat"));
    	store.put("bear", new Animal("bear"));
    	store.put("wolf", new Animal("wolf"));
    	
    	testList.add(new SelectItem(store.get("polecat"),"polecat"));
        testList.add(new SelectItem(store.get("suricate"), "suricate"));
        testList.add(new SelectItem(store.get("marshotter"), "marshotter"));
        testList.add(new SelectItem(store.get("dog"), "dog"));
        testList.add(new SelectItem(store.get("cat"), "cat"));
        
        SelectItem [] items = new SelectItem[2];
        items[0] = new SelectItem(store.get("bear"), "bear");
        items[1] = new SelectItem(store.get("wolf"), "wolf");
        
        SelectItemGroup group = new SelectItemGroup();
        group.setSelectItems(items);
        testList.add(group);
        
        listValues.add(store.get("suricate"));
	    listValues.add(store.get("marshotter"));
//        
//        arrayValues[0] = store.get("suricate");
//        arrayValues[1] = store.get("marshotter");
        
    }

    public void selectionChanged(ValueChangeEvent evt) {
        
    	Object newValue =  evt.getNewValue();
    	Object[] selectedValues = null;
    	if (newValue instanceof List) {
    		List list = (List)newValue;
    		selectedValues = list.toArray();
    	} else if (newValue instanceof Object[] ) {
        	selectedValues = (Object[]) evt.getNewValue();
    	} 
    	
    	if (selectedValues.length == 0) {
            selectedInfo = "No selected values";
        } else {
            StringBuffer sb = new StringBuffer("Selected values: ");
            for (int i = 0; i < selectedValues.length; i++) {
                if (i > 0) { 
                    sb.append(", ");
                }    
                
                sb.append(selectedValues[i]);
            }
            selectedInfo = sb.toString();
        }
    }

    public List <SelectItem> getTestList() {
        return testList;
    }

    public String getSelectedInfo() {
        return selectedInfo;
    }

	public String getTargetListWidth() {
		return targetListWidth;
	}

	public void setTargetListWidth(String targetListWidth) {
		this.targetListWidth = targetListWidth;
	}

	public String getSourceListWidth() {
		return sourceListWidth;
	}

	public void setSourceListWidth(String sourceListWidth) {
		this.sourceListWidth = sourceListWidth;
	}

	public String getListsHeight() {
		return listsHeight;
	}

	public void setListsHeight(String listsHeight) {
		this.listsHeight = listsHeight;
	}

	public void setTestList(List <SelectItem> testList) {
		this.testList = testList;
	}
//
//	public String[] getValues() {
//		return values;
//	}
//
//	public void setValues(String[] values) {
//		this.values = values;
//	}

	public String getCopyAllLabel() {
		return copyAllLabel;
	}

	public void setCopyAllLabel(String copyAllLabel) {
		this.copyAllLabel = copyAllLabel;
	}

	public String getCopyLabel() {
		return copyLabel;
	}

	public void setCopyLabel(String copyLabel) {
		this.copyLabel = copyLabel;
	}

	public String getRemoveLabel() {
		return removeLabel;
	}

	public void setRemoveLabel(String removeLabel) {
		this.removeLabel = removeLabel;
	}

	public String getRemoveAllLabel() {
		return removeAllLabel;
	}

	public void setRemoveAllLabel(String removeAllLabel) {
		this.removeAllLabel = removeAllLabel;
	}

	public List <Animal> getListValues() {
		return listValues;
	}

	public void setListValues(List<Animal> listValues) {
		this.listValues = listValues;
	}

	public void setSelectedInfo(String selectedInfo) {
		this.selectedInfo = selectedInfo;
	}

	public Map<String, Animal> getStore() {
		return store;
	}

	public void setStore(Map<String, Animal> store) {
		this.store = store;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isMoveControlsVisible() {
		return moveControlsVisible;
	}

	public void setMoveControlsVisible(boolean moveControlsVisible) {
		this.moveControlsVisible = moveControlsVisible;
	}

	public boolean isFastMoveControlsVisible() {
		return fastMoveControlsVisible;
	}

	public void setFastMoveControlsVisible(boolean fastMoveControlsVisible) {
		this.fastMoveControlsVisible = fastMoveControlsVisible;
	}

	public boolean isCopyAllVisible() {
		return copyAllVisible;
	}

	public void setCopyAllVisible(boolean copyAllVisible) {
		this.copyAllVisible = copyAllVisible;
	}

	public boolean isCopyVisible() {
		return copyVisible;
	}

	public void setCopyVisible(boolean copyVisible) {
		this.copyVisible = copyVisible;
	}

	public boolean isRemoveVisible() {
		return removeVisible;
	}

	public void setRemoveVisible(boolean removeVisible) {
		this.removeVisible = removeVisible;
	}

	public boolean isRemoveAllVisible() {
		return removeAllVisible;
	}

	public void setRemoveAllVisible(boolean removeAllVisible) {
		this.removeAllVisible = removeAllVisible;
	}
	
    public boolean isSwitchByClick() {
		return switchByClick;
	}

	public void setSwitchByClick(boolean switchByClick) {
		this.switchByClick = switchByClick;
	}

	public boolean isSwitchByDblClick() {
		return switchByDblClick;
	}

	public void setSwitchByDblClick(boolean switchByDblClick) {
		this.switchByDblClick = switchByDblClick;
	}


//	public List<String> getListValues() {
//		return listValues;
//	}
//
//	public void setListValues(List<String> listValues) {
//		this.listValues = listValues;
//	}
	
	

//	public Animal[] getArrayValues() {
//		return arrayValues;
//	}
//
//	public void setArrayValues(Animal[] arrayValues) {
//		this.arrayValues = arrayValues;
//	}

	
}