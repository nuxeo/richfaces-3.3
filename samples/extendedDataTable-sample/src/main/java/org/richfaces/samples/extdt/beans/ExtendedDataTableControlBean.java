package org.richfaces.samples.extdt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ExtendedDataTableControlBean {

    String width = "100%";
    String height = "500px";
    List<SelectItem> sortModeSelectItems = new ArrayList<SelectItem>();
    List<SelectItem> selectionModeSelectItems = new ArrayList<SelectItem>();
    String sortMode;
    String selectionMode;
    Integer rowsNumber;
    boolean contextMenuEnabled = true;
    boolean paginated = false;
    
    public ExtendedDataTableControlBean() {
        sortModeSelectItems.add(new SelectItem("single", "single"));
        sortModeSelectItems.add(new SelectItem("multi", "multi"));
        selectionModeSelectItems.add(new SelectItem("single", "single"));
        selectionModeSelectItems.add(new SelectItem("multi", "multi"));
        selectionModeSelectItems.add(new SelectItem("none", "none"));
    }
    
    public boolean isPaginated() {
        return paginated;
    }

    public void setPaginated(boolean paginated) {
        this.paginated = paginated;
    }

    public Integer getRowsNumber() {
        return rowsNumber;
    }

    public void setRowsNumber(Integer rowsNumber) {
        this.rowsNumber = rowsNumber;
    }

    public List<SelectItem> getSelectionModeSelectItems() {
        return selectionModeSelectItems;
    }

    public void setSelectionModeSelectItems(
            List<SelectItem> selectionModeSelectItems) {
        this.selectionModeSelectItems = selectionModeSelectItems;
    }



    public List<SelectItem> getSortModeSelectItems() {
        return sortModeSelectItems;
    }

    public void setSortModeSelectItems(List<SelectItem> sortModeSelectItems) {
        this.sortModeSelectItems = sortModeSelectItems;
    }
    
    public String getSortMode() {
        return sortMode;
    }

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    public String getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

	public boolean isContextMenuEnabled() {
		return contextMenuEnabled;
	}

	public void setContextMenuEnabled(boolean contextMenuEnabled) {
		this.contextMenuEnabled = contextMenuEnabled;
	}

}
