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
package org.richfaces.renderkit.html;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIExtendedDataTable;

/**
 * Abstract renderer class for table menu.
 * 
 * @author pawelgo
 * 
 */
public abstract class TableMenuRenderer implements Serializable {

    public final static String CHANGE_COL_VISIBILITY = "change_col_v";

    protected FacesContext context;
    protected UIExtendedDataTable table;
    protected UIColumn column;

    protected JSFunction changeColumnVisibilityFunction;
    protected JSFunction sortFunction;
    protected JSFunction groupFunction;
    protected JSFunction prepareFunction;
    protected JSFunctionDefinition onAjaxCompleteFunction;

    protected static InternetResourceBuilder resourceBuilder;

    /**
     * Performs initial operations and renders table menu.
     * 
     * @param context
     *            faces context
     * @param table
     *            table component
     * @param column
     *            current column
     * @return created menu (DOM element) id
     * @throws IOException
     */
    public String renderMenu(FacesContext context, UIExtendedDataTable table,
            UIColumn column) throws IOException {
        this.context = context;
        this.table = table;
        this.column = column;
        return render();
    }

    /**
     * Renders table menu.
     * 
     * @return created menu (DOM element) id
     * @throws IOException
     */
    protected abstract String render() throws IOException;

    /**
     * Creates JavaScript code to be executed in order to show menu on some
     * event.
     * 
     * @return JavaScript code causes showing menu
     */
    public abstract JSFunctionDefinition createShowMenuEventFunction();

    /**
     * Sets function to be called on complete AJAX request fired by menu action
     * 
     * @param functionDefinition
     *            function to be called on complete AJAX request
     */
    public void setOnAjaxCompleteFunction(
            JSFunctionDefinition functionDefinition) {
        this.onAjaxCompleteFunction = functionDefinition;
    }

    /**
     * Set JavaScript function for change column visibility.
     * 
     * @param changeColumnVisibilityFunction
     *            JavaScript function
     */
    public void setChangeColumnVisibilityFunction(
            JSFunction changeColumnVisibilityFunction) {
        this.changeColumnVisibilityFunction = changeColumnVisibilityFunction;
    }

    /**
     * Set JavaScript function for sort table.
     * 
     * @param sortFunction
     *            JavaScript function
     */
    public void setSortFunction(JSFunction sortFunction) {
        this.sortFunction = sortFunction;
    }

    /**
     * Set JavaScript function for group table.
     * 
     * @param groupFunction
     *            JavaScript function
     */
    public void setGroupFunction(JSFunction groupFunction) {
        this.groupFunction = groupFunction;
    }

    /**
     * Set JavaScript function called before send AJAX request.
     * 
     * @param prepareFunction
     *            JavaScript function
     */
    public void setPrepareFunction(JSFunction prepareFunction) {
        this.prepareFunction = prepareFunction;
    }

}
