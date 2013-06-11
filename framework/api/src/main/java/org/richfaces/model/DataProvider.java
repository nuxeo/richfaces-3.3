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
package org.richfaces.model;

import java.io.Serializable;
import java.util.List;

/**
 * DataProvider is an interface that defines methods for manage loading data.<br>
 * Usage:
 * <pre>
 * DataProvider&lt;SomeDataType&gt; dataProvider = new DataProvider&lt;SomeDataType&gt;();
 * </pre>
 * @author pawelgo
 *
 */
public interface DataProvider<T> extends Serializable {
	
	/**
	 * Get number of all rows.
	 * @return number of rows.
	 */
	public int getRowCount();
	
	/**
	 * Loads elements from given range.
	 * Starting from startRow, and up to but excluding endRow. 
	 * @param firstRow first row to load
	 * @param endRow end row to load
	 * @return element list
	 */
	public List<T> getItemsByRange(int firstRow, int endRow);
	
	/**
	 * Load single element by given key.
	 * @param key element key to be loaded.  
	 * @return element or null, if not found 
	 */
	public T getItemByKey(Object key);
	
	/**
	 * Get element key.
	 * If key is not instance of Integer or org.richfaces.model.ScrollableTableDataModel.SimpleRowKey,
	 * it is necessary to implement javax.faces.convert.Converter for key type.
	 * @param item element, which key to be get 
	 * @return element key
	 */
	public Object getKey(T item);
}
