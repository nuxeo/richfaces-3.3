/**
 * 
 */
package org.richfaces.sandbox;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.ListDataModel;

import org.richfaces.demo.datagrid.model.Issue;
import org.richfaces.demo.datagrid.service.JiraService;
import org.richfaces.model.Ordering;

/**
 * @author Konstantin Mishin
 *
 */
public class Bean {

	private String sortMode;
	private Ordering[] sortOrder;
	private Collection<String> sortPriority;
	private Issue selectedIssue;
	private ListDataModel model = new ListDataModel(new JiraService().getChannel().getIssues());
	
	public Bean() {
		sortOrder = new Ordering[4];
		clearSortOrder();
		sortPriority = new ArrayList<String>();
	}

	private void clearSortOrder() {
		for (int i = 0; i < sortOrder.length; i++) {
			sortOrder[i] = Ordering.UNSORTED;
		}
	}

	public String getSortMode() {
		return sortMode;
	}

	public void setSortMode(String sortMode) {
		this.sortMode = sortMode;
	}

	public Collection<String> getSortPriority() {
		return sortPriority;
	}

	public void setSortPriority(Collection<String> sortPriority) {
		this.sortPriority = sortPriority;
	}
	
	public void clearSortPriority() {
		sortPriority.clear();
		clearSortOrder();
	}

	public Ordering[] getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Ordering[] sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Issue getSelectedIssue() {
		return selectedIssue;
	}

	public void setSelectedIssue(Issue selectedIssue) {
		this.selectedIssue = selectedIssue;
	}
	
	public void select() {
		selectedIssue = (Issue) model.getRowData();
	}

	public ListDataModel getModel() {
		return model;
	}

	public void setModel(ListDataModel model) {
		this.model = model;
	}
}
