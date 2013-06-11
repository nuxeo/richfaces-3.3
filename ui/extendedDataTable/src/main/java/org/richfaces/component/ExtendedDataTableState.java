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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.json.JSONArray;
import org.richfaces.json.JSONCollection;
import org.richfaces.json.JSONException;
import org.richfaces.json.JSONMap;
import org.richfaces.json.JSONObject;
import org.richfaces.json.JSONStringer;
import org.richfaces.json.JSONWriter;
import org.richfaces.model.Ordering;

/**
 * @author pgolawski
 *
 */
public class ExtendedDataTableState implements Serializable {

	private static final long serialVersionUID = -3103664821855261335L;

	public static final String NONE_COLUMN_ID = "none";
	
	protected ColumnsOrder columnsOrder;
	protected ColumnsVisibility columnsVisibility;
	protected ColumnsSizeState columnsSizeState;
	protected ColumnGroupingState columnGroupingState;
	
	public static ExtendedDataTableState getExtendedDataTableState(UIExtendedDataTable extendedDataTable){
		ExtendedDataTableState state = new ExtendedDataTableState();
		state.init(extendedDataTable);
		return state;
	}//init
	
	/**
	 * Converts its state based on table attribute value or create default state if it is not set.
	 */
	protected void init(UIExtendedDataTable extendedDataTable){
		//get state value from components attribute
		String value = extendedDataTable.getTableState();
		JSONMap stateMap = null;
		if ((value != null) && (value.length() > 0)){
			try {
				stateMap = new JSONMap(value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//initialize columns order part
		try{
			columnsOrder = ColumnsOrder.getColumnsOrder(extendedDataTable, (stateMap == null ? null : (JSONCollection)stateMap.get("columnsOrder")));
		}
		catch(Exception e){
			columnsOrder = ColumnsOrder.getColumnsOrder(extendedDataTable, (JSONCollection)null);
		}
		//initialize columns visibility part
		try{
			columnsVisibility = ColumnsVisibility.getColumnsVisibility(extendedDataTable, (stateMap == null ? null : (JSONMap)stateMap.get("columnsVisibility")));
		}
		catch(Exception e){
			columnsVisibility = ColumnsVisibility.getColumnsVisibility(extendedDataTable, (JSONMap)null);
		}
		//initialize columns size part
		try{
			columnsSizeState = ColumnsSizeState.getColumnsSize(extendedDataTable, (stateMap == null ? null : (JSONMap)stateMap.get("columnsSizeState")));
		}
		catch(Exception e){
			columnsSizeState = ColumnsSizeState.getColumnsSize(extendedDataTable, (JSONMap)null);
		}
		//initialize column grouping part
		try{
			columnGroupingState = ColumnGroupingState.getColumnGropingState(extendedDataTable, (stateMap == null ? null : (JSONMap)stateMap.get("columnGroupingState")));
		}
		catch(Exception e){
			columnGroupingState = ColumnGroupingState.getColumnGropingState(extendedDataTable, (JSONMap)null);
		}
	}//init	
	
	/**
	 * Converts its state to String representation in JSON format.
	 */
	public String toString(){
		return toJSON().toString();
	}//toString
	
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		try {
			result.put("columnsOrder", columnsOrder.toJSON());
			result.put("columnsVisibility", columnsVisibility.toJSON());
			result.put("columnsSizeState", columnsSizeState.toJSON());
			result.put("columnGroupingState", columnGroupingState.toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsOrder#changeOrder(String, String)
	 */
	public void changeColumnsOrder(String sourceColumnId, String targetColumnId, boolean dropBefore) {
		columnsOrder.changeOrder(sourceColumnId, targetColumnId, dropBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsOrder#sortColumns(FacesContext, List)
	 */
	public List<UIComponent> sortColumns(FacesContext context,
			List<UIComponent> children) {
		return columnsOrder.sortColumns(context, children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsVisibility#isVisible(String)
	 */
	public boolean isColumnVisible(String columnId) {
		return columnsVisibility.isVisible(columnId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsVisibility#toggleVisibility(UIExtendedDataTable, String)
	 */
	public void toggleColumnVisibility(UIExtendedDataTable extendedDataTable,
			String columnId) {
		columnsVisibility.toggleVisibility(extendedDataTable, columnId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsSizeState#changeColumnSize(UIExtendedDataTable, String)
	 */
	public void changeColumnSize(UIExtendedDataTable extendedDataTable,
			String newValue) {
		columnsSizeState.changeColumnSize(extendedDataTable, newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnsSizeState#getColumnSize(UIComponent)
	 */
	public String getColumnSize(UIComponent column) {
		return columnsSizeState.getColumnSize(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#getGroupingColumnId()
	 */
	public String getGroupingColumnId(){
		return columnGroupingState.getGroupingColumnId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#groupBy(String, Ordering)
	 */
	public void groupBy(String colId, Ordering ordering) {
		columnGroupingState.groupBy(colId, ordering);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#resetGroupVisibilityState()
	 */
	public void resetGroupVisibilityState(){
		columnGroupingState.resetGroupVisibilityState();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#disableGrouping()
	 */
	public void disableGrouping(){
		columnGroupingState.disableGrouping();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#groupIsExpanded(int)
	 */
	public boolean groupIsExpanded(int index) {
		return columnGroupingState.groupIsExpanded(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ColumnGroupingState#toggleGroup(int)
	 */
	public void toggleGroup(int index) {
		columnGroupingState.toggleGroup(index);
	}
	
	
}

class ColumnsSizeState implements Serializable{

	private static final long serialVersionUID = 8724163192351491340L;

	private static final String DEFAULT_WIDTH = "100";
	
	private JSONObject value;

	private ColumnsSizeState() {
		super();
	}
	
	static ColumnsSizeState getColumnsSize(UIExtendedDataTable extendedDataTable, JSONMap state){
		ColumnsSizeState columnsSize = new ColumnsSizeState();
		columnsSize.init(extendedDataTable, state);
		return columnsSize;
	}
	
	/**
	 * Converts its state from String representation or create default state if it is not set.
	 */
	private void init(UIExtendedDataTable extendedDataTable, JSONMap state){
		value = null;
		if ((state != null) && (state.size()>0)){
			value = new JSONObject(state);
		}
		
		if (value == null){
			createDefaultColumnsSizeState(extendedDataTable);
		}
	}
	
	/**
	 * Converts its state to String representation in JSON format.
	 */
	public String toString(){
		if (value == null){
			return "";
		}
		return value.toString();
	}
	
	/**
	 * Get state in JSON format.
	 * @return JSON object contains state
	 */
	public JSONMap toJSON(){
		return new JSONMap(value);
	}
	
	/**
	 * Create default column order based on component children.
	 */
	private void createDefaultColumnsSizeState(UIExtendedDataTable extendedDataTable){
		try {
			JSONWriter writer = new JSONStringer().object();
			for (Iterator<UIColumn> iter = extendedDataTable.getChildColumns(); iter.hasNext();) {
				UIColumn col = iter.next();
				writer.key(col.getId()).value(getDefaultColumnSize(col));
			}
			value = new JSONObject(writer.endObject().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private String getDefaultColumnSize(UIComponent column){
		String widthStr = (String) column.getAttributes().get("width");
		return (widthStr == null) ? DEFAULT_WIDTH : widthStr;
	}
	
	public String getColumnSize(UIComponent column){
		if (value == null)
			return getDefaultColumnSize(column);
		String res = (String)value.opt(column.getId());
		if (res == null){
			res = getDefaultColumnSize(column);
		}
		return res;
	}
	
//	private String formatWidth(String value){
//		return String.valueOf(HtmlDimensions.decode(value).intValue()); 
//	}
	
	public void changeColumnSize(UIExtendedDataTable extendedDataTable, String newValue){
		if (value == null)
			return;
		try {
			String[] newWidths = newValue.split(";");
			int index = 0;
			for (Iterator<UIColumn> iter = extendedDataTable.getSortedColumns(); iter.hasNext();) {
				UIComponent col = (UIComponent) iter.next();
				if (col.isRendered()){
					String colId = col.getId();
					value.put(colId, newWidths[index++]);
				}//if
			}//for
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}//changeColumnSize
	
}//ColumnsSizeState

class ColumnsOrder implements Serializable{

	private static final long serialVersionUID = 907700564445889954L;

	private JSONArray value;

	private ColumnsOrder() {
		super();
	}
	
	static ColumnsOrder getColumnsOrder(UIExtendedDataTable extendedDataTable, JSONCollection collection){
		ColumnsOrder columnsOrder = new ColumnsOrder();
		columnsOrder.init(extendedDataTable, collection);
		return columnsOrder;
	}
	
	static ColumnsOrder getColumnsOrder(UIExtendedDataTable extendedDataTable, String val) throws JSONException{
		ColumnsOrder columnsOrder = new ColumnsOrder();
		columnsOrder.init(extendedDataTable, new JSONCollection(val));
		return columnsOrder;
	}
	
	/**
	 * Converts its state from String representation or create default state if it is not set.
	 */
	private void init(UIExtendedDataTable extendedDataTable, JSONCollection collection){
		value = null;
		if ((collection != null) && (collection.size()>0)){
			//try to restore state from collection 
			value = new JSONArray(collection);
		}
		
		if (value == null){
			createDefaultColumnsOrder(extendedDataTable);
		}
	}
	
	
	
	/**
	 * Converts its state to String representation in JSON format.
	 */
	public String toString(){
		if (value == null){
			return "";
		}
		return value.toString();
	}
	
	/**
	 * Get state in JSON format.
	 * @return JSON object contains state
	 */
	public JSONArray toJSON(){
		return value;
	}
	
	/**
	 * Create default column order based on component children.
	 */
	private void createDefaultColumnsOrder(UIExtendedDataTable extendedDataTable){
		value = new JSONArray();
		for (Iterator<UIColumn> iter = extendedDataTable.getChildColumns(); iter.hasNext();) {
			UIColumn col = iter.next();
			value.put(col.getId());
		}
	}
	
	/**
	 * Get column index in order. 
	 * @param columnId column id to be found
	 * @return column index or null if not found 
	 */
	private int getColumnIndex(String columnId){
		if (value == null)
			return -1;
		for (int i=0;i<value.length(); i++){
			Object val = value.opt(i);
			if (columnId.equals(val)){
				return i;
			}
		}
		return -1;
	}//getColumnIndex
	
	/**
	 * Changes column order. Moves source column to be next to target column.
	 * @param sourceColumnId source column id to be moved
	 * @param targetColumnId target column id
	 * @param dropBefore
	 */
	void changeOrder(String sourceColumnId, String targetColumnId, boolean dropBefore){
		if (value == null)
			return;
		if (sourceColumnId.equals(targetColumnId))
			return;
		List<String> list = new ArrayList<String>(value.length());
		for (int i=0;i<value.length(); i++){
			list.add(value.optString(i));
		}
		//get index of source column
		int sourceIndex = list.indexOf(sourceColumnId);
		//remove from order if exist
		if (sourceIndex != -1)
			list.remove(sourceIndex);
		//get index of target column
		int targetIndex = list.indexOf(targetColumnId);
		//add source column after or before target column
		if (targetIndex == -1)//add to end
			list.add(sourceColumnId);
		else{
			//add at proper position
			list.add((targetIndex + (dropBefore ? 0 : 1)), sourceColumnId);
		}
		//convert from list to JSON
		value = new JSONArray(list);
	}
	
	/**
	 * Sort column by given order.
	 * @param context faces context
	 * @param children list of unsorted columns
	 * @return list of sorted columns
	 */
	List<UIComponent> sortColumns(final FacesContext context, List<UIComponent> children){
		List<UIComponent> childs = new ArrayList<UIComponent>(children);
		Collections.sort(childs, new Comparator<UIComponent>() {
			public int compare(UIComponent o1, UIComponent o2) {
				Integer index1 = getColumnIndex(o1.getId());
				Integer index2 = getColumnIndex(o2.getId());
				if (index1 == -1) {
					return ((index2 == -1) ? 0 : 1);
				}
				return ((index2 == -1) ? -1 : index1.compareTo(index2));
			}
		});
		return childs;
	}

}//ColumnsOrder

class ColumnsVisibility implements Serializable{
	
	private static final long serialVersionUID = -3923409650272094713L;

	private static final String TRUE = "1";
	private static final String FALSE = "0";

	private JSONObject value;

	private ColumnsVisibility() {
		super();
	}
	
	static ColumnsVisibility getColumnsVisibility(UIExtendedDataTable extendedDataTable, String val) throws JSONException{
		ColumnsVisibility columnsVisibility = new ColumnsVisibility();
		columnsVisibility.init(extendedDataTable, new JSONMap(val));
		return columnsVisibility;
	}
	
	static ColumnsVisibility getColumnsVisibility(UIExtendedDataTable extendedDataTable, JSONMap state){
		ColumnsVisibility columnsVisibility = new ColumnsVisibility();
		columnsVisibility.init(extendedDataTable, state);
		return columnsVisibility;
	}
	
	/**
	 * Converts its state from String representation or create default state if it is not set.
	 */
	private void init(UIExtendedDataTable extendedDataTable, JSONMap state){
		value = null;
		if ((state != null) && (state.size()>0)){
			value = new JSONObject(state);
		}
		
		if (value == null){
			createDefaultColumnsVisibility(extendedDataTable);
		}
	}//init
	
	/**
	 * Converts its state to String representation in JSON format.
	 */
	public String toString(){
		if (value == null){
			return "";
		}
		return value.toString();
	}
	
	/**
	 * Get state in JSON format.
	 * @return JSON object contains state
	 */
	public JSONMap toJSON(){
		return new JSONMap(value);
	}
	
	/**
	 * Create default column visibility based on component children.
	 */
	private void createDefaultColumnsVisibility(UIExtendedDataTable extendedDataTable){
		try {
			JSONWriter writer = new JSONStringer().object();
			for (Iterator<UIColumn> iter = extendedDataTable.getChildColumns(); iter.hasNext();) {
				UIColumn col = iter.next();
				writer.key(col.getId()).value(col.isVisible() ? TRUE : FALSE);
			}
			value = new JSONObject(writer.endObject().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}//createDefaultColumnsVisibility
	
	/**
	 * Get column visibility. 
	 * @param columnId column id to be found
	 * @return true if column is visible, otherwise false
	 */
	boolean isVisible(String columnId){
		if (value == null)
			return true;
		return !FALSE.equals(value.opt(columnId));
	}//isVisible
	
	/**
	 * Toggle column visibility.
	 * @param extendedDataTable table component
	 * @param columnId column id
	 */
	void toggleVisibility(UIExtendedDataTable extendedDataTable, String columnId){
		if (value == null)
			return;
		UIColumn column = null;
		//find column by id
		for (Iterator<UIColumn> iter = extendedDataTable.getChildColumns(); iter.hasNext();) {
			UIColumn col = iter.next();
			if (col.getId().equalsIgnoreCase(columnId)){
				column = (UIColumn) col;
				break;
			}//if
		}//for
		if (column == null)
			return;
		boolean visible = column.isVisible();
		//toggle visibility
		visible = !visible;
		try {
			value.put(columnId, (visible ? TRUE : FALSE));
			//set visibility flag for column
			column.setVisible(visible);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}//changeVisibility

}//ColumnsVisibility

class ColumnGroupingState implements Serializable{
	
	private static final long serialVersionUID = -3923409650272094713L;

	//private static final String TRUE = "1";
	//private static final String FALSE = "0";
	private static final Boolean DEF = Boolean.TRUE;//expanded

	private String columnId;
	private List<Boolean> groupExpanded;
	private Ordering ordering; 

	private ColumnGroupingState() {
		super();
	}
	
	static ColumnGroupingState getColumnGropingState(UIExtendedDataTable extendedDataTable, JSONMap map){
		ColumnGroupingState groupingState = new ColumnGroupingState();
		groupingState.init(extendedDataTable, map);
		return groupingState;
	}
	
	/**
	 * Converts its state from String representation or create default state if it is not set.
	 */
	private void init(UIExtendedDataTable extendedDataTable, JSONMap map){
		columnId = null;
		ordering = Ordering.UNSORTED;
		groupExpanded = new ArrayList<Boolean>();
		if ((map != null) && (map.size() > 0)){
			//try to restore state from string 
//			try {
				columnId = (String)map.get("columnId");
				ordering = Ordering.valueOf((String)map.get("order"));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
		}
		
		if (columnId != null){
			//get column by id and set sort order
			for (Iterator<UIColumn> columns = extendedDataTable.getChildColumns(); columns.hasNext(); ){
				UIColumn child = columns.next();
				if (columnId.equalsIgnoreCase(child.getId())) {
					child.setSortOrder(ordering);
					break;
				}
			}
		}
	}//init
	
	/**
	 * Converts its state to String representation in JSON format.
	 */
	public String toString(){
		return toJSON().toString();
	}
	
	/**
	 * Get state in JSON format.
	 * @return JSON object contains state
	 */
	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		if (columnId != null){
			try {
				result.put("columnId", columnId);
				result.put("order", ordering);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Gets grouped column id.
	 * @return grouped column id if grouping is on, otherwise false
	 */
	String getGroupingColumnId(){
		return columnId;
	}
	
	/**
	 * Turn on grouping for column. 
	 * @param colId id of column to be grouped
	 * @param ordering sort order
	 */
	void groupBy(String colId, Ordering ordering){
		columnId = colId;
		this.ordering = ordering;
		resetGroupVisibilityState();
	}
	
	/**
	 * Resets information about group visibility state.
	 * All group will be mark as expanded.
	 */
	void resetGroupVisibilityState(){
		groupExpanded.clear();
	}
	
	/**
	 * Turn off grouping. 
	 */
	void disableGrouping(){
		columnId = ExtendedDataTableState.NONE_COLUMN_ID;
		ordering = Ordering.UNSORTED;
		resetGroupVisibilityState();
	}
	
	/**
	 * Toggle group. It means that group will be expanded if is collapsed
	 * and group will be collapsed if is expanded.
	 * @param index index of group to be toggled 
	 */
	void toggleGroup(int index){
		if (index < 0)
			throw new IllegalArgumentException("Illegal index value :"+index);
		if (index >= groupExpanded.size()){
			//add default values for lower indexes
			int count = index - groupExpanded.size() + 1;
			for (int i = 0; i < count; i++){
				groupExpanded.add(DEF);
			}///for
		}
		groupExpanded.add(index,!groupExpanded.remove(index));
	}
	
	/**
	 * Checks if group is expanded. 
	 * @param index index of group to be tested 
	 * @return true if group is expanded, otherwise false
	 */
	boolean groupIsExpanded(int index){
		if (index < 0)
			throw new IllegalArgumentException("Illegal index value :"+index);
		if (index >= groupExpanded.size()){
			return DEF;
		}
		return groupExpanded.get(index).booleanValue();
	}

}//ColumnGroupingState
