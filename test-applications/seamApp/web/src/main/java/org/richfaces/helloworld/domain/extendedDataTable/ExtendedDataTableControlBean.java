package org.richfaces.helloworld.domain.extendedDataTable;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.richfaces.helloworld.domain.util.componentInfo.ComponentInfo;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.html.HtmlExtendedDataTable;

@Name("extendedDataTableControlBean")
@Scope(ScopeType.SESSION)
public class ExtendedDataTableControlBean {

    private String width = "100%";
    private String height = "500px";
    private List<SelectItem> sortModeSelectItems = new ArrayList<SelectItem>();
    private List<SelectItem> selectionModeSelectItems = new ArrayList<SelectItem>();
    private String sortMode;
    private String selectionMode;
    private Integer rowsNumber;
    private boolean paginated = false;
    private HtmlExtendedDataTable extDTable = null;
    private String bindLabel;   
    private String border;
    private String cellpadding;
    private String cellspacing;
    private String dir;
    private int first;
    private String frame;
    private String groupingColumn;
    private boolean rendered;
    private String sortPriority;
    
    public String getSortPriority() {
		return sortPriority;
	}

	public void setSortPriority(String sortPriority) {
		this.sortPriority = sortPriority;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getGroupingColumn() {
		return groupingColumn;
	}

	public void setGroupingColumn(String groupingColumn) {
		this.groupingColumn = groupingColumn;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getCellpadding() {
		return cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public String getCellspacing() {
		return cellspacing;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public HtmlExtendedDataTable getExtDTable() {
		return extDTable;
	}

	public void setExtDTable(HtmlExtendedDataTable extDTable) {
		this.extDTable = extDTable;
	}
	
	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = extDTable.getBaseClientId(context);
	}
	public void addExtDTable(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(extDTable);
	}

	public ExtendedDataTableControlBean() {
        sortModeSelectItems.add(new SelectItem("single", "single"));
        sortModeSelectItems.add(new SelectItem("multi", "multi"));
        selectionModeSelectItems.add(new SelectItem("single", "single"));
        selectionModeSelectItems.add(new SelectItem("multi", "multi"));
        selectionModeSelectItems.add(new SelectItem("none", "none"));
        bindLabel = "click binding";
        border = "1px";
        cellpadding = "1px";
        cellspacing = "1px";
        dir = "LTR";
        first = 0;
        frame = "void";
        rendered = true;
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

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

}
