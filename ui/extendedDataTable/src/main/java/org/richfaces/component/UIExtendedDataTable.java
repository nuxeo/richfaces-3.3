/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
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

package org.richfaces.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.DataModel;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.ContextInitParameters;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.model.ExtendedDataModel;
import org.apache.commons.collections.iterators.IteratorChain;
import org.richfaces.event.extdt.ChangeColumnVisibilityEvent;
import org.richfaces.event.extdt.ColumnResizeEvent;
import org.richfaces.event.extdt.DragDropEvent;
import org.richfaces.event.extdt.ExtTableFilterEvent;
import org.richfaces.event.extdt.ExtTableSortEvent;
import org.richfaces.model.ExtendedTableDataModel;
import org.richfaces.model.ExtendedTableDataModifiableModel;
import org.richfaces.model.FilterField;
import org.richfaces.model.LocaleAware;
import org.richfaces.model.Modifiable;
import org.richfaces.model.ModifiableModel;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;

/**
 * JSF component class
 * 
 */
public abstract class UIExtendedDataTable extends UIDataTable implements
		Selectable, Filterable, Sortable2 {

	/**
	 * COMPONENT_TYPE
	 */
	public static final String COMPONENT_TYPE = "org.richfaces.ExtendedDataTable";

	/**
	 * COMPONENT_FAMILY
	 */
	public static final String COMPONENT_FAMILY = "org.richfaces.ExtendedDataTable";
	
	protected ExtendedDataTableState state;

	public abstract Object getActiveRowKey();

	public abstract void setActiveRowKey(Object activeRowKey);
	
	public abstract String getGroupingColumn();
	
	public abstract void setGroupingColumn(String groupingColumn);
	
	public abstract void setTableState(String tableState);
	
	public abstract String getTableState();

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		if (event instanceof AjaxEvent) {
			//TODO nick - add regions from component too
			AjaxContext.getCurrentInstance().addComponentToAjaxRender(this);
		} else if (event instanceof DragDropEvent) {
			processDradDrop((DragDropEvent) event);
		} else if (event instanceof ChangeColumnVisibilityEvent) {
			processChangeColumnVisibility((ChangeColumnVisibilityEvent) event);
		} else if (event instanceof ColumnResizeEvent) {
			processColumnResize((ColumnResizeEvent) event);
		} else if (event instanceof ExtTableSortEvent) {
			processSortingChange((ExtTableSortEvent) event);
		} else if (event instanceof ExtTableFilterEvent) {
			processFilteringChange((ExtTableFilterEvent) event);
		}

	}

	public void queueEvent(FacesEvent event) {
	    if(event.getSource() instanceof UIExtendedDataTable) {
    		if (event instanceof AjaxEvent) {
    			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    		} else if (event instanceof DragDropEvent) {
    			new AjaxEvent(this).queue();
    			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    		} else if (event instanceof ChangeColumnVisibilityEvent) {
    			new AjaxEvent(this).queue();
    			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    		} else if (event instanceof ColumnResizeEvent) {
    			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    		} else if (event instanceof ExtTableSortEvent) {
    			new AjaxEvent(this).queue();
    			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
    		} else if (event instanceof ExtTableFilterEvent) {
    			new AjaxEvent(this).queue();
    			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
    		}
	    }
	    super.queueEvent(event);
	}

	public Iterator<UIColumn> getSortedColumns() {
		return new SortedColumnsIterator(this);
	}

	public Iterator<UIColumn> getChildColumns() {
		return new ExtendedTableColumnsIterator(this);
	}

	public void processDradDrop(DragDropEvent event) {
		String dragValue = event.getDragValue().toString();// dnd_drag_script
		String dropValue = event.getDropValue().toString();// dnd_drop_script

		getState().changeColumnsOrder(dragValue, dropValue, event.isDropBefore());
		//getFacesContext().renderResponse();
	}

	public List<UIComponent> getSortedChildren() {
		return getState().sortColumns(getFacesContext(), super.getChildren());
	}

	public void processChangeColumnVisibility(ChangeColumnVisibilityEvent event) {
		getState().toggleColumnVisibility(this, event.getColumnId());
		//getFacesContext().renderResponse();
	}

	public void processSortingChange(ExtTableSortEvent event) {
		DataModel dataModel = getDataModel();
		if (dataModel instanceof ExtendedTableDataModifiableModel<?>) {
			((ExtendedTableDataModifiableModel<?>)dataModel).resetSort();
		}
		else{
			super.resetDataModel();
		}
		getFacesContext().renderResponse();
	}

	public void processFilteringChange(ExtTableFilterEvent event) {
		DataModel dataModel = getDataModel();
		if (dataModel instanceof ExtendedTableDataModifiableModel<?>) {
			((ExtendedTableDataModifiableModel<?>)dataModel).resetFilter();
		}
		else{
			super.resetDataModel();
		}
		resetGroupVisibilityState();
		getFacesContext().renderResponse();
	}

	public boolean isColumnVisible(UIComponent column) {
		return getState().isColumnVisible(column.getId());
	}

	public void processColumnResize(ColumnResizeEvent event) {
		getState().changeColumnSize(this, event.getColumnWidths());
		//getFacesContext().renderResponse();
	}

	public String getColumnSize(UIComponent column) {
		return getState().getColumnSize(column);
	}

	public boolean isGroupingOn() {
		//first get it from state
		String groupingColumnId = getGroupByColumnId();
		return ((groupingColumnId!=null) && (!"".equals(groupingColumnId)) && (!ExtendedDataTableState.NONE_COLUMN_ID.equals(groupingColumnId)));
	}

	/**
	 * Get id of column which the data is grouped by. First tries to get it
	 * from the table state. If the table state doesn't provide such
	 * information, get from component attribute.
	 * 
	 * @return id of column which the data is grouped by
	 */
	public String getGroupByColumnId() {
		String id = getState().getGroupingColumnId();
		if (id == null){//grouping is not saved in state
			//get column id from attribute
			id = getGroupingColumn();
		}
		return id;
	}

	/**
	 * Get column component which the data is grouped by. First tries to get it
	 * from the table state. If the table state doesn't provide such
	 * information, get from component attribute.
	 * 
	 * @return column which the data is grouped by
	 */
	public UIColumn getGroupByColumn() {
		if (!isGroupingOn()){
			return null;
		}
		String groupingColumnId = getGroupByColumnId();
		for (Iterator<UIColumn> columns = getChildColumns(); columns.hasNext();) {
			UIColumn child = columns.next();
			if (groupingColumnId.equalsIgnoreCase(child.getId())) {
				return child;
			}
		}
		return null;
	}

	public void setGroupByColumn(org.richfaces.component.UIColumn column) {
		if (column == null) {
			getState().disableGrouping();
		} else {
			getState().groupBy(column.getId(), column.getSortOrder());
		}
	}

	protected void resetGroupVisibilityState() {
		getState().resetGroupVisibilityState();
	}

	public void disableGrouping() {
		getState().disableGrouping();
	}

	public boolean groupIsExpanded(int index) {
		return getState().groupIsExpanded(index);
	}

	public void toggleGroup(int index) {
		getState().toggleGroup(index);
	}
	
	protected Collection<Object> getGroupPriority(){
		Collection<Object> priority = getSortPriority();
		if (isGroupingOn()) {// grouping is on
			UIColumn column = getGroupByColumn();
			if ((column.getSortOrder() == null) || (column.getSortOrder().equals(Ordering.UNSORTED))){
				column.setSortOrder(Ordering.ASCENDING);
			}
			String groupColId = column.getId();
			// try to add group column id as first
			if (priority.contains(groupColId)) {
				priority.remove(groupColId);
			}
			if (priority instanceof List) {
				((List<Object>) priority).add(0, groupColId);
			} else {
				priority.add(groupColId);
			}
		}
		return priority;
	}

//	public Object saveState(FacesContext context) {
//		Object values[] = new Object[2];
//		values[0] = super.saveState(context);
//		values[1] = state;
//		return values;
//	}
//
//	public void restoreState(FacesContext context, Object state) {
//		Object values[] = (Object[]) state;
//		super.restoreState(context, values[0]);
//		this.state = (ExtendedDataTableState) values[1];
//	}

	public int getVisibleColumnsCount() {
		int count = 0;
		for (Iterator<UIColumn> iter = getChildColumns(); iter.hasNext();) {
			UIColumn column = iter.next();
			if (column.isRendered())
				count++;
		}// for
		return count;
	}// getVisibleColumnnCount

	@Override
	@SuppressWarnings("unchecked")
	protected ExtendedDataModel createDataModel() {
		List<FilterField> filterFields = new LinkedList<FilterField>();
		Map<String, SortField2> sortFieldsMap = new LinkedHashMap<String, SortField2>();
		Collection<Object> sortPriority = getGroupPriority();
		List<UIComponent> list = getChildren();
		for (Iterator<UIComponent> iterator = list.iterator(); iterator
				.hasNext();) {
			UIComponent component = iterator.next();
			if (component instanceof UIColumn) {
				UIColumn column = (UIColumn) component;
				FilterField filterField = column.getFilterField();
				if (filterField != null) {
					filterFields.add(filterField);
				}
				SortField2 sortField = column.getSortField();
				if (sortField != null) {
					sortFieldsMap.put(component.getId(), sortField);
				}
			}

		}
		List<SortField2> sortFields = new LinkedList<SortField2>();
		
		if (sortPriority != null) {
			for (Object object : sortPriority) {
				if (object instanceof String) {
					String id = (String) object;
					SortField2 sortField = sortFieldsMap.get(id);
					if (sortField != null) {
						sortFields.add(sortField);
						sortFieldsMap.remove(id);
					}
				}
			}
		}
		sortFields.addAll(sortFieldsMap.values());
		setFilterFields(filterFields);
		setSortFields(sortFields);
		
		ExtendedDataModel dataModel = (ExtendedDataModel)getDataModel();
		if (dataModel instanceof ExtendedTableDataModifiableModel<?>) {
			((ExtendedTableDataModifiableModel<?>) dataModel).setVar(getVar());
		}
		if ((filterFields != null && !filterFields.isEmpty())
				|| (sortFields != null && !sortFields.isEmpty())) {
			Modifiable modifiable = null;
			if (dataModel instanceof Modifiable) {
				modifiable = (Modifiable) dataModel;
			} else if (dataModel instanceof ExtendedTableDataModel<?>) {
				ExtendedTableDataModel<?> tableDataModel = (ExtendedTableDataModel<?>) dataModel;
				//ExtendedTableDataModifiableModelOld<?> modifiableModel = new ExtendedTableDataModifiableModelOld(tableDataModel, getVar());
				ExtendedTableDataModifiableModel<?> modifiableModel = new ExtendedTableDataModifiableModel(tableDataModel, getVar());
				dataModel = modifiableModel;
				modifiable = modifiableModel;
			}
			else {
				ModifiableModel modifiableModel = new ModifiableModel(dataModel, getVar());
				dataModel = modifiableModel;
				modifiable = modifiableModel;	
			}
			
			if (dataModel instanceof LocaleAware) {
				FacesContext facesContext = getFacesContext();
				if (ContextInitParameters.isDatatableUsesViewLocale(facesContext)) {
					UIViewRoot viewRoot = facesContext.getViewRoot();
					((LocaleAware) dataModel).setLocale(viewRoot.getLocale());
				}
			}
			
			modifiable.modify(filterFields, sortFields);
		}
		return dataModel;
	}

	/**
	 * Original version of this method is defined in
	 * {@link org.ajax4jsf.component.UIDataAdaptor} and is called before
	 * RENDER_RESPONSE phase. In that version data model is reseted which causes
	 * need to sort and filter every time component is rendered.
	 */
	// @Override
	protected void resetDataModel() {
		// Do not reset only for ExtendedTableDataModel model
		if (!(getDataModel() instanceof ExtendedTableDataModel<?>)) {
			super.resetDataModel();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<UIComponent> fixedChildren() {
		IteratorChain chain = new IteratorChain(getFacets().values().iterator());
		//RF-1248 Adding facets to both dataChildren and fixed children
		//To make both supports and header/footer work
		for (Iterator<UIComponent> i = getChildren().iterator(); i.hasNext(); ) {
			UIComponent kid = (UIComponent)i.next();
			if (kid instanceof Column || kid instanceof UIColumn) {
				chain.addIterator(kid.getFacets().values().iterator());
			}
		}
		
		return chain;
	}

	@Override
	public void beforeRenderResponse(FacesContext context) {
		super.beforeRenderResponse(context);
		
		for (Iterator<UIColumn> columns = getChildColumns(); columns
				.hasNext();) {
			UIColumn column = columns.next();
			column.setId(column.getId());
			column.setVisible(isColumnVisible(column));
		}
		
		UIColumn groupingColumn = getGroupByColumn();
		if (groupingColumn != null) {// grouping is on
			if (groupingColumn.getSortOrder().equals(Ordering.UNSORTED)) {
				groupingColumn.setSortOrder(Ordering.ASCENDING);
			}
		}
	}
	
	@Override
	public void processUpdates(FacesContext context){
		super.processUpdates(context);
		updateTableState(context);
	}
	
	/**
	 * Puts own state into component state. 
	 */
	protected void updateTableState(FacesContext context){
		ValueExpression ve = getValueExpression("tableState");
		if ((null != ve) && (!ve.isReadOnly(context.getELContext()))) {
			ve.setValue(context.getELContext(), getState().toString());
		}
	}//publishChanges
	
	protected ExtendedDataTableState getState() {
		if (state == null){
			state = ExtendedDataTableState.getExtendedDataTableState(this);
		}
		return state;
	}

}
