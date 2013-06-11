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

/**
 * Columns iterator that returns column in sort order.
 * 
 * @see UIExtendedDataTable#getSortedChildren()
 * @author pawelgo
 * 
 */
public class SortedColumnsIterator extends ExtendedTableColumnsIterator {

    /**
     * Creates iterator for table.
     * 
     * @param dataTable
     *            table for which iterator is created
     */
    public SortedColumnsIterator(UIExtendedDataTable dataTable) {
        super(dataTable);
        childrenIterator = dataTable.getSortedChildren().iterator();
    }

}
