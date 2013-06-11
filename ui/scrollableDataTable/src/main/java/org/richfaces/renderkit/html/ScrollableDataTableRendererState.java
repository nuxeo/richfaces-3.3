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
package org.richfaces.renderkit.html;

import java.util.HashSet;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIDataAdaptor;
import org.ajax4jsf.context.AjaxContext;
import org.richfaces.component.UIScrollableDataTable;

/**
 * bean to store current {@link UIDataAdaptor } information
 * in request map. For nested grids, it support push/pop state saving.
 * In {@link javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)} method
 * must be created instance of this bean , or, if it already exist in request map - push information.
 * at the end of encodeEnd call, bean must be pop information or removed.
 * @author shura
 *
 */
public class ScrollableDataTableRendererState{
	
	public static final String DATA_GRID_RENDERER_STATE = ScrollableDataTableRendererState.class.getName();
	
	private boolean fake;
	
	private Set<String> ids = new HashSet<String>();
	
	private String rowClasses[];
	
	private int rowClassesSize;
	
	private String columnClasses[];
	
	private int columnClassesSize;
	
	private int _rowIndex = 0;
	
	private int _cellIndex = 0;
	
	private int _columns = 0;
	
	private Integer sepOffset;
	
	private String _cell_id_prefix;
	
	private int sumWidth = 0;
	
	private boolean header = false;
	
	private String _column_type;
	
	private int _frozenColumnCount = -1;
	
	private AjaxContext ajaxContext;
	
	private String part;
	
	private boolean _frozenPart = false;
	
	private ResponseWriter writer;
	
	private UIScrollableDataTable _grid;
	
	private String clientId;
	
	private String _cachedClientId;
	
	private ScrollableDataTableRendererState _previousState = null;
	
	private Object rowKey;

	private int rows;

	private static final long serialVersionUID = 2129605586975025578L;

	
	/**
	 * Get current grid state from JSF context
	 * @param context
	 * @return current data grid state, or null if not saved.
	 */
	public static ScrollableDataTableRendererState getRendererState(FacesContext context) throws FacesException {
		if(null == context){
			throw new NullPointerException("Context for grid state is null");
		}
		ScrollableDataTableRendererState state = (ScrollableDataTableRendererState) context.getExternalContext().getRequestMap().get(DATA_GRID_RENDERER_STATE);
//		if( null == state){
//			throw new FacesException("State for current grid not stored in context");
//		}
		return state;
	}

	/**
	 * Create new state for current grid. If state exist, store previsius in field of created.
	 * @param context
	 * @param grid
	 * @return new state for grid.
	 */
	public static ScrollableDataTableRendererState createState(FacesContext context, UIScrollableDataTable grid){
		if(null == context){
			throw new NullPointerException("Context for grid state is null");
		}
		ScrollableDataTableRendererState oldState = getRendererState(context);
		ScrollableDataTableRendererState state = new ScrollableDataTableRendererState(context,oldState,grid);
		state.setColumnClasses(grid.getAttributes().get("columnClasses"));
		state.setRowClasses(grid.getAttributes().get("rowClasses"));
		context.getExternalContext().getRequestMap().put(DATA_GRID_RENDERER_STATE,state);
		return state;
	}
	
	/**
	 * Restore previsius state for gred, or clear request parameter.
	 * @param context
	 */
	public static void restoreState(FacesContext context) {
		if (null == context) {
			throw new NullPointerException("Context for grid state is null");
		}
		ScrollableDataTableRendererState state = getRendererState(context);
		if (null == state) {
			throw new FacesException(
					"State for current grid not saved in context");
		}
		ScrollableDataTableRendererState previsiosState = state.getPreviousState();
		if (null != previsiosState) {
			context.getExternalContext().getRequestMap().put(DATA_GRID_RENDERER_STATE,
					previsiosState);
		} else {
			context.getExternalContext().getRequestMap().remove(DATA_GRID_RENDERER_STATE);
		}
	}
	/**
	 * Create state for current grid ( and store previsios state in field ).
	 * @param previsiosState
	 */
	public ScrollableDataTableRendererState(FacesContext context, ScrollableDataTableRendererState previsiosState, UIScrollableDataTable grid) {
		super();
		_grid = grid;
		_cachedClientId = grid.getClientId(context);
		clientId = _cachedClientId;
		_previousState = previsiosState;
		rows = grid.getRows();
	}

	public  String getCurrentCellId(FacesContext context){
		return getGrid().getClientId(context)+NamingContainer.SEPARATOR_CHAR+"row"+getRowIndex()+
		NamingContainer.SEPARATOR_CHAR+"col"+getCellIndex();
	}
	
	/**
	 * @return Returns the cellIndex.
	 */
	public int getCellIndex() {
		return _cellIndex;
	}

	/**
	 * @param cellIndex The cellIndex to set.
	 */
	public void setCellIndex(int cellIndex) {
		_cellIndex = cellIndex;
	}
	
	/**
	 * Increment cells counter 
	 * @return next cell number.
	 */
	
	public int nextCell(){
		return ++_cellIndex;
	}


	/**
	 * @return Returns the columns.
	 */
	public int getColumns() {
		return _columns;
	}

	/**
	 * @param columns The columns to set.
	 */
	public void setColumns(int columns) {
		_columns = columns;
	}

	/**
	 * @return Returns the grid.
	 */
	public UIScrollableDataTable getGrid() {
		return _grid;
	}

	/**
	 * @param grid The grid to set.
	 */
	public void setGrid(UIScrollableDataTable grid) {
		_grid = grid;
	}

	/**
	 * @return Returns the previsiosState.
	 */
	public ScrollableDataTableRendererState getPreviousState() {
		return _previousState;
	}

	/**
	 * @param previsiosState The previsiosState to set.
	 */
	public void setPreviousState(ScrollableDataTableRendererState previsiosState) {
		_previousState = previsiosState;
	}

	/**
	 * @return Returns the rowIndex.
	 */
	public int getRowIndex() {
		if(rows != 0 && _rowIndex >= rows){
			_rowIndex = 0;
		}	
		return _rowIndex;
	}
	
	/**
	 * Increment current row counter.
	 * @return new row number.
	 */
	public int nextRow(){
		_rowIndex = _rowIndex + 1; 	
		return _rowIndex;
	}

	/**
	 * @param rowIndex The rowIndex to set.
	 */
	public void setRowIndex(int rowIndex) {
		_rowIndex = rowIndex;
	}


	/**
	 * @return the _cachedClientId
	 */
	public String getCachedClientId() {
		return _cachedClientId;
	}

	
	private StringBuffer buffer = new StringBuffer();
	
	/**
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		buffer.setLength(0);
		return buffer;
	}
	
	/**
	 * @return the rowKey
	 */
	public Object getRowKey() {
		return rowKey;
	}

	/**
	 * @param rowKey the rowKey to set
	 */
	public void setRowKey(Object rowKey) {
		this.rowKey = rowKey;
	}

	public boolean isFrozenColumn() {
		return !(_frozenColumnCount <= 0);
	}

	public void setFrozenColumnCount(int columnCount) {
		_frozenColumnCount = columnCount;
	}
	
	public int getFrozenColumnCount() {
		return _frozenColumnCount;
	}

	public boolean isFrozenPart() {
		return _frozenPart;
	}

	public void setFrozenPart(boolean part) {
		_frozenPart = part;
	}

	public String getCellIdPrefix() {
		return _cell_id_prefix;
	}

	public void setCellIdPrefix(String _id_prefix) {
		this._cell_id_prefix = _id_prefix;
	}
	
	public String getColumnType() {
		return _column_type;
	}

	public void setColumType(String _column_type) {
		this._column_type = _column_type;
	}

	public AjaxContext getAjaxContext() {
		return ajaxContext;
	}

	public void setAjaxContext(AjaxContext ajaxContext) {
		this.ajaxContext = ajaxContext;
	}

	public ResponseWriter getWriter() {
		return writer;
	}

	public void setWriter(ResponseWriter writer) {
		this.writer = writer;
	}
	
	public String getClientId(){
		return clientId;
	}
	
	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public int getSumWidth() {
		return sumWidth;
	}

	public void setSumWidth(int sumWidth) {
		this.sumWidth = sumWidth;
	}

	public Integer getSepOffset() {
		return sepOffset;
	}

	public void setSepOffset(Integer sepOffset) {
		this.sepOffset = sepOffset;
	}

	
	public boolean isFake() {
		return fake;
	}

	public void setFake(boolean fake) {
		this.fake = fake;
	}

	public String  getColumnHeaderClass() {
		return "";
	}
	
	
	public String getColumnClass() {
		return getColumnClass(getCellIndex());
	}
	
	public String getColumnClass(int index) {
		if(columnClasses != null) {
			return columnClasses[index % columnClassesSize];
		} else {
			return "";
		}
	}

	public void setColumnClasses(Object columnClasses) {
		if(columnClasses != null) {
			this.columnClasses = ((String)columnClasses).split(",");
			columnClassesSize = this.columnClasses.length;
		}
	}	
	
	public String getRowClass() {
		return getRowClass(getRowIndex()) + (isFake() ? " dr-sdt-fake-r rich-sdt-fake-r " : "");
	}
	
	private String getRowClass(int index) {
		if(rowClasses != null) {
			return rowClasses[index % rowClassesSize];
		} else {
			return "";
		}
	}

	public void setRowClasses(Object rowClasses) {
		if(rowClasses != null) {
			this.rowClasses = ((String)rowClasses).split(",");
			rowClassesSize = this.rowClasses.length;
		}
	}

	public Set<String> getIds() {
		return ids;
	}

	public void addId(String id) {
		ids.add(id);
	}	
}
