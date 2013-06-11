/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
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
package org.richfaces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.component.SequenceDataAdaptor;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.model.DataComponentState;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.util.ELUtils;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.convert.rowkey.ScrollableDataTableRowKeyConverter;
import org.richfaces.event.AttributeHolder;
import org.richfaces.event.ScrollableGridViewEvent;
import org.richfaces.event.sort.MultiColumnSortListener;
import org.richfaces.event.sort.SingleColumnSortListener;
import org.richfaces.event.sort.SortEvent;
import org.richfaces.event.sort.SortListener;
import org.richfaces.model.DataModelCache;
import org.richfaces.model.Modifiable;
import org.richfaces.model.ModifiableModel;
import org.richfaces.model.Ordering;
import org.richfaces.model.ScrollableTableDataModel;
import org.richfaces.model.ScrollableTableDataRange;
import org.richfaces.model.SelectionMode;
import org.richfaces.model.SortField;
import org.richfaces.model.SortField2;
import org.richfaces.model.SortOrder;
import org.richfaces.renderkit.html.ScrollableDataTableUtils;


/**
 * @author Anton Belevich
 *
 */

public abstract class UIScrollableDataTable extends SequenceDataAdaptor implements AjaxComponent, Sortable, Selectable, ScriptExportable{
	
	
	public static final String COMPONENT_TYPE = "org.richfaces.component.ScrollableDataTable";
	public static final String SORT_SINGLE = "single";
	public static final String SORT_MULTI = "multi";

	private final static Log log = LogFactory.getLog(UIScrollableDataTable.class);
	
	/**
	 * Flag set on each phase to determine what range of data to walk
	 * true means - locally saved ranges (for data pending update)
	 * false means - use range that comes from component state
	 */
	private boolean useSavedRanges = true;
	
	/**
	 * hold list of ranges previously accessed until updates are fully done for them
	 */
	private List<Range> ranges;
	
	private Collection<String> responseData = new ArrayList<String>();
	
	private int reqRowsCount = -1;
	
	private String scrollPos;
	
	private SortListener sortListener;
	
	private Converter defaultRowKeyConverter = new ScrollableDataTableRowKeyConverter();
	
	
	public abstract SortOrder getSortOrder();
	
	public abstract void setSortOrder(SortOrder sortOrder) ;
	
	public Collection<String> getResponseData() {
		return responseData;
	}

	public void setResponseData(Collection<String> responseData) {
		this.responseData = responseData;
	}
	
	protected DataComponentState createComponentState() {
	
		return new DataComponentState(){
		
			public Range getRange() {
				
				int curentRow = getFirst();

				int rows;
				if(reqRowsCount == -1 ){
					rows = getRows();
				} else {
					rows = reqRowsCount;
				}
								
				int rowsCount = getExtendedDataModel().getRowCount();
				
				if(rows > 0){
				
					rows += curentRow;
					
					if(rows >=0){
						rows = Math.min(rows, rowsCount);
					}
					
				} else if(rowsCount >=0 ){
					rows = rowsCount;
					
				} else {
					rows = -1;
				}
				
				return new ScrollableTableDataRange(curentRow,rows, getSortOrder());
			}
		};
	}
	
	
	public void processDecodes(FacesContext faces) {
		
		useSavedRanges = false;
		
		if (log.isTraceEnabled()) {
			log.trace("UIScrollableDataTable.processDecodes(faces)");
		}
		checkRange();
		super.processDecodes(faces);
	}
	
	public void processValidators(FacesContext faces) {
		useSavedRanges = true;
		if (log.isTraceEnabled()) {
			log.trace("UIScrollableDataTable.processValidators(faces)");
		}
		super.processValidators(faces);
	}
	
	public void processUpdates(FacesContext faces) {
		useSavedRanges = true;
		
		if (log.isTraceEnabled()) {
			log.trace("UIScrollableDataTable.processUpdates(faces)");
		}
		
		super.processUpdates(faces);
		ranges = null;
	}
	
	public void encodeBegin(FacesContext context) throws IOException {
		
		if (log.isTraceEnabled()) {
			log.trace("UIScrollableDataTable.encodeBegin(context)");
		}
		
		useSavedRanges = false;
		checkRange();
		super.encodeBegin(context);
	}
	
	protected ExtendedDataModel createDataModel() {
		ExtendedDataModel model = super.createDataModel();
		if (model instanceof ScrollableTableDataModel) {
			if (isCacheable()) {
				if (log.isDebugEnabled()) {
					log.debug("Initializing cache of type "
							+ DataModelCache.class);
				}
				model = new DataModelCache((ScrollableTableDataModel<?>) model);
			}
		} else {
			List<SortField2> sortFields = getSortFields();
			if (sortFields != null && !sortFields.isEmpty()) {
				Modifiable modifiable = null;
				if (model instanceof Modifiable) {
					modifiable = (Modifiable) model;
				} else {
					ModifiableModel modifiableModel = new ModifiableModel(
							model, getVar());
					model = modifiableModel;
					modifiable = modifiableModel;
				}
				modifiable.modify(null, sortFields);
			}
		}
		/*
		 * TODO: Verify - do we really need it
			model.setSortOrder(getSortOrder());
		*/
		return model;		
	}
	
	private List<SortField2> getSortFields() {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext eLContext= context.getELContext();
		ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
		String var = getVar();
		SortOrder sortOrder = getSortOrder();
		List<SortField2> sortFields2 = null;
		if (sortOrder != null) {
			SortField[] sortFields = sortOrder.getFields();
			sortFields2 = new LinkedList<SortField2>();
			for (SortField sortField : sortFields) {
				ValueExpression valueExpression = null;
				String name = sortField.getName();
				if (ELUtils.isValueReference(name)) {
					valueExpression = expressionFactory.createValueExpression(
							eLContext, name, Object.class);
				} else if (!name.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
					valueExpression = expressionFactory.createValueExpression(
							eLContext, "#{" + var + "." + name + "}",
							Object.class);
				}
				Ordering ordering = Ordering.UNSORTED;
				Boolean ascending = sortField.getAscending();
				if (ascending != null) {
					if (ascending) {
						ordering = Ordering.ASCENDING;
					} else {
						ordering = Ordering.DESCENDING;
					}
				}
				if (valueExpression != null
						&& !Ordering.UNSORTED.equals(ordering)) {
					sortFields2.add(new SortField2(valueExpression, ordering));
				}
			}
		}
		return sortFields2;
	}	
	
	public Object saveState(FacesContext context) {
		
		Object values[] = new Object[4];
		values[0] = super.saveState(context);
		values[1] = ranges;
		values[2] = scrollPos;
		values[3] = saveAttachedState(context, sortListener);
		
		return (Object)values;
	
	}
	
	
	@SuppressWarnings("unchecked")
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(context, values[0]);
		ranges = ((List<Range>)values[1]);
		scrollPos = (String)values[2]; 
		sortListener = (SortListener) restoreAttachedState(context, values[3]);
	}

	@SuppressWarnings("unchecked")
	protected Iterator<UIComponent> dataChildren() {
		IteratorChain chain = new IteratorChain();
		//RF-1248 Adding facets to both dataChildren and fixed children
		//To make both supports and header/footer work
		chain.addIterator(getFacets().values().iterator());
		for (Iterator<UIComponent> i = getChildren().iterator(); i.hasNext(); ) {
			UIComponent kid = (UIComponent)i.next();
			if (kid instanceof Column || kid instanceof UIColumn) {
				chain.addIterator(kid.getChildren().iterator());
			}
		}
		
		return chain;
	}
	
	@SuppressWarnings("unchecked")
	protected Iterator<UIComponent> fixedChildren() {
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
	
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		
		super.broadcast(event);
		
		if (event instanceof AttributeHolder) {
			((AttributeHolder) event).applyAttributes(this);
		}

		if(event instanceof AjaxEvent){
			AjaxContext.getCurrentInstance().addComponentToAjaxRender(this);
		}else if(event instanceof SortEvent){
			processSortingChange(event);
		//	new AjaxEvent(this).queue();
		}else if(event instanceof ScrollableGridViewEvent){
		//	new AjaxEvent(this).queue();
			processScrolling(event);
		}
		
	}
	
	protected boolean broadcastLocal(FacesEvent event) {
		return super.broadcastLocal(event) || event instanceof SortEvent || event instanceof ScrollableGridViewEvent;
	}
	
	public void queueEvent(FacesEvent event) {
		
		if(event instanceof AjaxEvent){
			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
		}else if(event instanceof SortEvent){
			new AjaxEvent(this).queue();
			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
		}else if(event instanceof ScrollableGridViewEvent){
			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			new AjaxEvent(this).queue();
		}
		super.queueEvent(event);
	}
	
	public void processScrolling(FacesEvent event){
		ScrollableGridViewEvent e = (ScrollableGridViewEvent)event;
		
		setFirst(e.getFirst());
		reqRowsCount = e.getRows();
		
		getFacesContext().renderResponse();
	}
	
	public void processSortingChange(FacesEvent event){
		
		SortEvent e = (SortEvent)event;
		
		getSortListener().processSort(e);
	
		setFirst(e.getFirst());
		reqRowsCount = e.getRows();
		
		
		resetDataModel();
		
		getFacesContext().renderResponse();
	}
	
	public void walk(FacesContext faces, DataVisitor visitor, Object argument) throws IOException {
		
		Range visitedRange = getComponentState().getRange();
	
		if(ranges == null){
			ranges = new ArrayList<Range>();
		}
		
		if(!ranges.contains(visitedRange)){
			ranges.add(visitedRange);
		}
				
		if(useSavedRanges){
			
			for (Iterator<Range> iter = ranges.iterator(); iter.hasNext();) {
				ScrollableTableDataRange range = (ScrollableTableDataRange) iter.next();
				
				if (log.isDebugEnabled()) {
					log.debug("Range is: " + range.getFirst() + " - " + range.getLast() + " sortOrder: " + range.getSortOrder() );
				}
				
				
				getExtendedDataModel().walk(faces, visitor,range, argument);
			}
			
		}else{
			super.walk(faces, visitor, argument);
		}
	}
	
	
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	public boolean isCacheable() {
		return true;
	}

	public String getScrollPos() {
		return scrollPos;
	}

	public void setScrollPos(String scrollPos) {
		this.scrollPos = scrollPos;
	}

	public SortListener getSortListener() {
		if (sortListener != null) {
			return sortListener;
		}
		
		if (SORT_MULTI.equals(getSortMode())) {
		
			return MultiColumnSortListener.INSTANCE;
	
		} else {
		
			return SingleColumnSortListener.INSTANCE;
		
		}
	}

	public void setSortListener(SortListener sortListener) {
		this.sortListener = sortListener;
	}
	
	public abstract String getSortMode();
	public abstract void setSortMode(String mode);
	
	public abstract SelectionMode getSelectionMode();
	public abstract void setSelectionMode(SelectionMode mode);
	
	public boolean isSelectionEnabled() {
		return getSelectionMode().isSelectionEnabled();
	}
	
	public abstract Object getActiveRowKey();
	public abstract void setActiveRowKey(Object activeRowKey);
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.component.UIDataAdaptor#setRowIndex(int)
	 */
	public void setRowIndex(int index) {
		if (index < 0) {
			setRowKey(null);
		}
		//super.setRowIndex(index);
	}

	public void resetReqRowsCount() {
		this.reqRowsCount = -1;
	}
	
	private void checkRange() {
		int rows;
		if(reqRowsCount == -1 ){
			rows = getRows();
		} else {
			rows = reqRowsCount;
		}
		if (getRowCount() < getFirst() + rows) {
			setFirst(0);
			setScrollPos("0,0," + rows + "," + ScrollableDataTableUtils.getClientRowIndex(this));
		}
	}
	
	
	//RF-2771
	public boolean isLimitToList() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setLimitToList(boolean submitForm) {
		// TODO Auto-generated method stub
		
	}

	public boolean isAjaxSingle() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAjaxSingle(boolean single) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Converter getRowKeyConverter() {
		Converter converter = super.getRowKeyConverter();
		if (null == converter) {
			return defaultRowKeyConverter;
		}
		return converter;
	}
	
	@Override
	public void setRowKeyConverter(Converter rowKeyConverter) {
		super.setRowKeyConverter(rowKeyConverter);
	}
}
