/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.Column;
import org.richfaces.component.Row;
import org.richfaces.component.UIColumn;
import org.richfaces.component.UIDataTable;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.component.nsutils.NSUtils;
import org.richfaces.component.util.ComponentMessageUtil;
import org.richfaces.event.extdt.ChangeColumnVisibilityEvent;
import org.richfaces.event.extdt.ColumnResizeEvent;
import org.richfaces.event.extdt.DragDropEvent;
import org.richfaces.event.extdt.ExtTableFilterEvent;
import org.richfaces.event.extdt.ExtTableSortEvent;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;
import org.richfaces.model.impl.expressive.JavaBeanWrapper;
import org.richfaces.model.impl.expressive.ObjectWrapperFactory;
import org.richfaces.model.impl.expressive.WrappedBeanComparator2;
import org.richfaces.renderkit.html.HTMLEncodingContributor;
import org.richfaces.renderkit.html.RichTableMenuRenderer;
import org.richfaces.renderkit.html.TableDragDropRenderer;
import org.richfaces.renderkit.html.TableMenuRenderer;
import org.richfaces.renderkit.html.iconimages.DataTableIconSortNone;
import org.richfaces.renderkit.html.images.TriangleIconDown;
import org.richfaces.renderkit.html.images.TriangleIconUp;

/**
 * @author shura
 * 
 */
public abstract class AbstractExtendedTableRenderer extends
        AbstractExtendedRowsRenderer {

    private static final String SORT_FILTER_PARAMETER = "fsp";

    private static final String SORT_DIR_PARAMETER = "sortDir";

    private static final String SORT_DIR_PARAMETER_ASC = "asc";

    private static final String SORT_DIR_PARAMETER_DESC = "desc";

    private static final String FILTER_INPUT_FACET_NAME = "filterValueInput";

    private static final String COL_RESIZE_ACTION_NAME = "columnResizeAction";

    private final static String CHANGE_COL_VISIBILITY = "change_col_v";

    private static final String SORT_FUNCTION = "sortFunction";
    
    private static final String GROUP_FUNCTION = "groupFunction";

    private static final String SHOW_MENU_FUNCTION = "showMenuFunction";
    
    private static final String ENABLE_CONTEXT_MENU = "enableContextMenu";

    private static final String ON_RESIZE_FUNCTION = "onColumnResize";

    private static final String GROUP_FILTER_PARAMETER = "groupParam";

    private static final String ON_GROUP_TOGGLE_FUNCTION = "onGroupToggleFunction";

    private static final String GROUP_TOGGLE_ACTION_NAME = "groupToggleAction";
    
    private static final String MIN_COLUMN_WIDTH = "20";
    
    //private final Log log = LogFactory.getLog(UIExtendedDataTable.class);
    
    /*
     * Message key constants
     */
    private static final String MSG_NODATA = "org.richfaces.component.UIExtendedDataTable.NoData";

    /**
     * Encode all table structure - colgroups definitions, caption, header,
     * footer
     * 
     * @param context
     * @param table
     * @throws IOException
     */

    public void encodeColumns(FacesContext context,
            UIExtendedDataTable table) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        Iterator<UIColumn> cols = table.getSortedColumns();
        while (cols.hasNext()) {
            UIColumn col = cols.next();
            if (col.isRendered()) {
                writer.startElement("col", table);
                String colWidth = table.getColumnSize(col);
                writer.writeAttribute("width", colWidth, null);
                writer.endElement("col");
            }
        }
        writer.startElement("col", table);
        writer.endElement("col");
    }

    public void encodeHeader(FacesContext context, UIExtendedDataTable table)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        UIComponent header = table.getHeader();
        boolean columnFacetHeaderPresent = isColumnFacetPresent(table, "header");
        Iterator<UIColumn> colums = table.getSortedColumns();
        // int numberOfColumns = getColumnsCount(table);
        int numberOfColumns = table.getVisibleColumnsCount() + 1;
        String headerClass = (String) table.getAttributes().get("headerClass");
        writer.startElement("thead", table);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table.getBaseClientId(context) + ":header", null);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-thead", null);
        //add special fake IE row
    	writer.startElement("tr", header);
        encodeStyleClass(writer, null,
                "extdt-header rich-extdt-header", headerClass, null);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table
                .getBaseClientId(context)
                + ":fakeIeRow", null);
        for (int i = 0; i < numberOfColumns; i++) {
            writer.startElement("th", header);
            encodeStyleClass(writer, null,
                    "extdt-headercell extdt-fakeierow rich-extdt-headercell",
                    headerClass, null);
            writer.endElement("th");
        }
        writer.endElement("tr");
        if (header != null) {
        	
            encodeTableHeaderFacet(context, numberOfColumns, writer,
                    header, "extdt-header rich-extdt-header",
                    "extdt-header-continue rich-extdt-header-continue",
                    "extdt-headercell rich-extdt-headercell",
                    headerClass, "th", "header");
        }
        
        writer.startElement("tr", table);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table
                .getBaseClientId(context)
                + ":headerRow", null);
        encodeStyleClass(writer, null,
                "extdt-subheader rich-extdt-subheader", null,
                headerClass);
        if (columnFacetHeaderPresent) {
        	encodeHeaderFacets(
                context,
                writer,
                table,
                colums,
                "extdt-menucell extdt-subheadercell rich-extdt-subheadercell",
                headerClass, "header", "th", numberOfColumns);
            /* encoding additional empty column used in resizing columns */
            writer.startElement("th", table);
            encodeStyleClass(writer, null,
                    "extdt-empty-cell rich-extdt-subheadercell", null,
                    null);
            encodeNBSP(writer);
            writer.endElement("th");
        }
        else{
        	writer.writeAttribute(HTML.style_ATTRIBUTE, "height: 0px", null);
        	for (int i = 0; i < numberOfColumns; i++) {
                writer.startElement("th", header);
                encodeNBSP(writer);
                writer.endElement("th");
            }
        }
        writer.endElement("tr");
    	encodeFilterRow(context, writer, table, table
                .getSortedColumns(),
                "extdt-subheadercell rich-extdt-subheadercell",
                headerClass, "filter", HTML.th_ELEM);
        writer.endElement("thead");
    }
    
    private void encodeNBSP(ResponseWriter writer) throws IOException {
    	writer.write("&#160;");
    }

    private void encodeFilterRow(FacesContext context, ResponseWriter writer,
            UIExtendedDataTable table, Iterator<UIColumn> headers,
            String skinCellClass, String headerClass, String facetName,
            String element) throws IOException {
        
        if (filteringEnabled(table)) {
            writer.startElement(HTML.TR_ELEMENT, table);
            writer.writeAttribute(HTML.id_ATTRIBUTE, table
                    .getBaseClientId(context)
                    + ":filterRow", null);
            encodeStyleClass(writer, null,
                    "extdt-table-filterrow rich-extdt-subheader", null,
                    headerClass);
            encodeFilterInputs(context, writer, table, headers, skinCellClass,
                    headerClass, facetName, element);
            writer.startElement(HTML.th_ELEM, table);
            encodeStyleClass(writer, null,
                    "extdt-empty-cell rich-extdt-subheadercell", null, null);
            encodeNBSP(writer);
            writer.endElement(HTML.th_ELEM);
            writer.endElement(HTML.TR_ELEMENT);
        }
    }

    /**
     * Checks if column has filtering enabled
     * 
     * @param dataColumn column to inspect
     * @return true if filtering is enabled, false otherwise
     * @author pbuda
     */
    private boolean internalFilterEnabledColumn(UIColumn dataColumn) {
        return dataColumn.getFilterMethod() == null
                && dataColumn.getValueExpression("filterExpression") == null
                && dataColumn.getValueExpression("filterBy") != null;
    }

    /**
     * Checks whether filtering is enabled in this table
     * 
     * @param table table to inspect
     * @return true if filtering is enabled, false otherwise
     * @author pbuda
     */
    private boolean filteringEnabled(UIExtendedDataTable table) {
        Iterator<UIColumn> columns = table.getSortedColumns();
        boolean enabled = false;
        while (columns.hasNext()) {
            UIColumn col = columns.next();
            if ((internalFilterEnabledColumn(col) || (col.getFacet("filter") != null)) && 
            		col.isRendered()
				) {
				enabled = true;
				break;
			}
        }
        return enabled;
    }

    /**
     * Encodes a new row of the table and places filter inputs for corresponding
     * columns in that new row
     * 
     * @param context
     *            current FacesContext instance
     * @param writer
     *            ResponseWriter for this context
     * @param table
     *            table instance
     * @param headers
     *            iterator over headers in table
     * @param skinCellClass
     *            css class of skin
     * @param headerClass
     *            css class of header
     * @param facetName
     *            facet to encode
     * @param element
     *            element to encode
     * @throws IOException
     *             if ResponseWriter fails it's operation
     * @author pbuda
     */
    private void encodeFilterInputs(FacesContext context,
            ResponseWriter writer, UIDataTable table,
            Iterator<UIColumn> headers, String skinCellClass,
            String headerClass, String facetName, String element)
            throws IOException {
        while (headers.hasNext()) {
            UIColumn column = headers.next();
            if (column instanceof UIColumn) {
                if (column.isRendered()) {
                    writer.startElement(element, table);
                    String classAttribute = facetName + "Class";
                    String columnHeaderClass = (String) column.getAttributes()
                            .get(classAttribute);
                    encodeStyleClass(writer, null, skinCellClass, headerClass,
                            columnHeaderClass);
                    UIColumn dataColumn = (UIColumn) column;
                    if (internalFilterEnabledColumn(dataColumn)) {
                        writer.startElement(HTML.DIV_ELEM, column);
                        addInplaceInput(context, column);
                        writer.endElement(HTML.DIV_ELEM);
                    }
                    else if (dataColumn.getFacet("filter") != null) {
                    	writer.startElement(HTML.DIV_ELEM, column);
                    	dataColumn.getFacet("filter").encodeAll(context);
                    	writer.endElement(HTML.DIV_ELEM);
                    }
                    else {
                    	encodeNBSP(writer);
                    }
                    writer.endElement(element);
                }
            }
        }
    }
    
    private JSFunctionDefinition buildSetFocusFunctionDef(String elementId){
    	JSFunctionDefinition function = new JSFunctionDefinition("request","event","data");
		function.addToBody( 
				"var element = request.form.elements['" + elementId + "'];" +
				"if (!element) {element = document.getElementById('" + elementId + "')}" +
				"if (element) {" +
					"element.focus();" +
					//"if (element.createTextRange) { var r = (element.createTextRange());  r.collapse(false); r.select();}" +
					"element.value = element.value;" +
				"}");
		return function;
    }

    public boolean isColumnFacetPresent(UIDataTable table, String facetName) {
        Iterator<UIComponent> columns = table.columns();
        boolean result = false;
        while (columns.hasNext() && !result) {
            UIComponent component = columns.next();
            if (isColumnRendered(component)) {
                if (null != component.getFacet(facetName)) {
                    result = true;
                } /*
                 * else if(component instanceof Column) { Column column =
                 * (Column)component; result = column.isSelfSorted(); }
                 */
            }
        }
        return result;
    }

    /**
     * @param component
     * @return
     */
    protected boolean isColumnRendered(UIComponent component) {
        boolean rendered = true;
        try {
            rendered = component.isRendered();
        } catch (Exception e) {
            // DO nothing, rendered binded to row variable;
        }
        return rendered;
    }

    protected void encodeHeaderFacets(FacesContext context,
            ResponseWriter writer, UIDataTable table,
            Iterator<UIColumn> headers, String skinCellClass,
            String headerClass, String facetName, String element, int colCount)
            throws IOException {
        int t_colCount = 0;

        // HeaderEncodeStrategy richEncodeStrategy = new
        // RichHeaderEncodeStrategy();
        // HeaderEncodeStrategy simpleEncodeStrategy = new
        // SimpleHeaderEncodeStrategy();

        while (headers.hasNext()) {
            UIColumn column = headers.next();
            if (isColumnRendered(column)) {
                if ((Integer) column.getAttributes().get("colspan") != null) {
                    t_colCount = t_colCount
                            + ((Integer) column.getAttributes().get("colspan"))
                                    .intValue();
                } else {
                    t_colCount++;
                }
                if (t_colCount > colCount) {
                    break;
                }

                String classAttribute = facetName + "Class";
                String columnHeaderClass = (String) column.getAttributes().get(
                        classAttribute);

                writer.startElement(element, column);
                encodeStyleClass(writer, null, skinCellClass, headerClass,
                        columnHeaderClass);
                writer.writeAttribute("scope", "col", null);
                getUtils().encodeAttribute(context, column, "colspan");

                boolean sortableColumn = column
                        .getValueExpression("comparator") != null
                        || column.getValueExpression("sortBy") != null;
                column.getAttributes().put("sortable",
                        Boolean.valueOf(sortableColumn));

                HeaderEncodeStrategy strategy = (column instanceof UIColumn && "header"
                        .equals(facetName)) ? new G3HeaderEncodeStrategy()
                        : new SimpleHeaderEncodeStrategy();

                strategy.encodeBegin(context, writer, column, facetName,
                        sortableColumn);

                UIComponent facet = column.getFacet(facetName);
                if (facet != null && isColumnRendered(facet)) {
                    renderChild(context, facet);
                }

                strategy.encodeEnd(context, writer, column, facetName,
                        sortableColumn);

                writer.endElement(element);

            }

        }
    }

    public void encodeFooter(FacesContext context, UIExtendedDataTable table)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        UIComponent footer = table.getFooter();
        boolean columnFacetPresent = isColumnFacetPresent(table, "footer");
        Iterator<UIColumn> tableColumns = table.getSortedColumns();
        // int columns = getColumnsCount(table);
        int columns = table.getVisibleColumnsCount() + 1;
        if (footer != null || columnFacetPresent) {
            writer.startElement("tfoot", table);
            writer.writeAttribute(HTML.id_ATTRIBUTE, table.getBaseClientId(context)
                    + ":footer", null);
            String footerClass = (String) table.getAttributes().get(
                    "footerClass");

            if (columnFacetPresent) {
                writer.startElement("tr", table);
                encodeStyleClass(writer, null,
                        "extdt-subfooter rich-extdt-subfooter", null,
                        footerClass);

                encodeHeaderFacets(context, writer, table, tableColumns,
                        "extdt-subfootercell rich-extdt-subfootercell",
                        footerClass, "footer", "td", columns);
                writer.startElement("td", table);
                encodeStyleClass(writer, null,
                        "extdt-subfootercell rich-extdt-subfootercell", null,
                        null);
                writer.endElement("td");
                writer.endElement("tr");
            }
            if (footer != null) {
                encodeTableHeaderFacet(context, columns, writer, footer,
                        "extdt-footer rich-extdt-footer",
                        "extdt-footer-continue rich-extdt-footer-continue",
                        "extdt-footercell rich-extdt-footercell",
                        footerClass, "td", "footer");
            }
            writer.endElement("tfoot");
        }

    }

    /**
     * Encodes the false row to enable proper rendering in IE when rows grouping
     * is on (IE won't render the table properly if it has first row with
     * colspan).
     * 
     * @param table
     *            to render false row to,
     * @param context
     *            of the table.
     * @throws IOException
     */

    private void encodeFakeIeRow(FacesContext context,
            UIExtendedDataTable table, ExtendedTableHolder holder)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        int numberOfColumns = getColumnsCount(table) + 1;

        writer.startElement("tr", table);
        encodeStyleClass(writer, null, "extdt-fakeierow ", null, null);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table.getBaseClientId(context)
                + ":body:fakeIeRow", null);
        for (int i = 0; i < numberOfColumns; i++) {
            writer.startElement("td", table);
            encodeStyleClass(writer, null, "extdt-fakeierow " + getCellSkinClass(), null, null);
            writer.endElement("td");
        }
        writer.endElement("tr");

    }

    /**
     * Encodes the grouping row.
     * 
     * @param table
     *            to render group row to,
     * @param context
     *            of the table.
     * @throws IOException
     */
    private void encodeGroupRow(FacesContext context,
            UIExtendedDataTable table, ExtendedTableHolder holder)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        int numberOfColumns = getColumnsCount(table) + 1;
        int actGroupRow = holder.getGroupRowCounter();
        writer.startElement(HTML.TR_ELEMENT, table);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-group-row", null);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table.getBaseClientId(context)
                + ":group-row:" + actGroupRow, null);

        writer.writeAttribute("expanded",
                table.groupIsExpanded(actGroupRow) ? "true" : "false", null);
        writer.writeAttribute("groupindex", String.valueOf(actGroupRow), null);

        writer.startElement(HTML.td_ELEM, table);
        writer.writeAttribute(HTML.class_ATTRIBUTE,
                "extdt-group-cell rich-extdt-group-cell", null);
        writer.writeAttribute("colspan", numberOfColumns, null);
        writer.startElement(HTML.SPAN_ELEM, table);
        writer.startElement(HTML.IMG_ELEMENT, table);

        String imagePlusUri = getResource(
                "/org/richfaces/renderkit/html/images/plusIcon.gif").getUri(
                context, null);
        String imageMinusUri = getResource(
                "/org/richfaces/renderkit/html/images/minusIcon.gif").getUri(
                context, null);
        if (table.groupIsExpanded(actGroupRow)) {
            writer.writeAttribute(HTML.src_ATTRIBUTE, imageMinusUri, null);
            writer.writeAttribute("alternatesrc", imagePlusUri, null);
        } else {
            writer.writeAttribute(HTML.src_ATTRIBUTE, imagePlusUri, null);
            writer.writeAttribute("alternatesrc", imageMinusUri, null);
        }
        writer.endElement(HTML.IMG_ELEMENT);
        writer.endElement(HTML.SPAN_ELEM);
        writer.startElement(HTML.SPAN_ELEM, table);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-group-text", null);
        writer.writeAttribute(HTML.style_ATTRIBUTE, "font-weight: bold;", null);
        String label = holder.getGroupingColumnLabel();
        writer.writeText((label == null) ? "" : label + ": ", null);
        writer.endElement(HTML.SPAN_ELEM);
        renderChildren(context, holder.getGroupingColumn());
        writer.startElement(HTML.SPAN_ELEM, table);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-group-text", null);
        writer.endElement(HTML.SPAN_ELEM);
        writer.endElement(HTML.td_ELEM);
        writer.endElement(HTML.TR_ELEMENT);
        holder.nextGroupRow();
    }

    protected boolean rowGroupChanged(FacesContext context,
            ExtendedTableHolder holder) {
        UIExtendedDataTable table = holder.getTable();
        if (holder.getLastData() == null)
            return true;

        // get sort fields
        List<SortField2> sortFields = table.getSortFields();
        // get group field which is actually the first from sort fields
        List<SortField2> groupFields = new ArrayList<SortField2>();
        if (!sortFields.isEmpty())
            groupFields.add(sortFields.get(0));
        // create wrapper factory
        ObjectWrapperFactory wrapperFactory = new ObjectWrapperFactory(context,
                table.getVar(), groupFields);
        // create wrapper for last data
        JavaBeanWrapper wrappedLstD = wrapperFactory.wrapObject(holder
                .getLastData());
        // create wrapper for current data
        JavaBeanWrapper wrappedActD = wrapperFactory.wrapObject(table
                .getRowData());
        // create comparator
        WrappedBeanComparator2 wrappedBeanComparator = new WrappedBeanComparator2(
                (groupFields));
        // compare last and current data
        return (wrappedBeanComparator.compare(wrappedLstD, wrappedActD) != 0);
    }// rowGroupChanged

    public void encodeOneRow(FacesContext context, ExtendedTableHolder holder)
            throws IOException {
        UIExtendedDataTable table = holder.getTable();
        ResponseWriter writer = context.getResponseWriter();
        Iterator<UIColumn> iter = table.getSortedColumns();
        boolean first = true;
        int currentColumn = 0;
        UIColumn column = null;
        if (holder.isFirstRow()) {
            encodeFakeIeRow(context, table, holder);
        }
        if (holder.isGroupingOn() && (rowGroupChanged(context, holder))) {
            encodeGroupRow(context, table, holder);
        }
        holder.setFirstRow(false);
        
        while (iter.hasNext()) {
            column = iter.next();
            // Start new row for first column - expect a case of the detail
            // table, wich will be insert own row.
            boolean isRow = (column instanceof Row);
            if (first && !isRow) {
                encodeRowStart(context, getFirstRowSkinClass(), holder
                        .getRowClass(), table, holder, writer);
            }

            // TODO PKA CHANGE COLUMN RENDERER TO GET RID OF && false
//            if (false && (column instanceof Column)) {
//                boolean breakBefore = ((Column) column).isBreakBefore()
//                        || isRow;
//                if (breakBefore && !first) {
//                    // close current row
//                    writer.endElement(HTML.TR_ELEMENT);
//                    // reset columns counter.
//                    currentColumn = 0;
//                    // Start new row, expect a case of the detail table, wich
//                    // will be insert own row.
//                    if (!isRow) {
//                        holder.nextRow();
//                        encodeRowStart(context, holder.getRowClass(), table,
//                                holder, writer);
//                    }
//                }
//                encodeCellChildren(context, column,
//                        first ? getFirstRowSkinClass() : null,
//                        getRowSkinClass(), holder.getRowClass(),
//                        getCellSkinClass(), holder
//                                .getColumnClass(currentColumn));
//                // renderChild(context, column);
//                if (isRow && iter.hasNext()) {
//                    // Start new row for remained columns.
//                    holder.nextRow();
//                    encodeRowStart(context, holder.getRowClass(), table,
//                            holder, writer);
//                    // reset columns counter.
//                    currentColumn = -1;
//                }
//            } else 
            if (column.isRendered()) {
                // UIColumn don't have own renderer
                writer.startElement(HTML.td_ELEM, table);
                getUtils().encodeId(context, column);
                encodeStyleClass(writer, null, getCellSkinClass(), holder.getColumnClass(currentColumn),
                        column.getAttributes().get("styleClass"));
                encodeStyle(writer, null, null, null, column.getAttributes().get("style"));
                // TODO - encode column attributes.
                writer.startElement(HTML.DIV_ELEM, table);
                writer.writeAttribute("class", "extdt-cell-div", null);
                // IE
                renderChildren(context, column);
                writer.endElement(HTML.DIV_ELEM);
                writer.endElement(HTML.td_ELEM);
            }
            currentColumn++;
            first = false;
        }
        // encode additional empty row for resizing
        writer.startElement(HTML.td_ELEM, table);
        String columnClass = holder.getColumnClass(currentColumn);
        encodeStyleClass(writer, null, "extdt-empty-cell rich-extdt-cell", null, columnClass);
        writer.endElement(HTML.td_ELEM);
        // Close row if then is open.
        if (!first && !(column instanceof Row)) {
            writer.endElement(HTML.TR_ELEMENT);
        }
    }

    protected void encodeRowStart(FacesContext context, String rowClass,
            UIDataTable table, TableHolder holder, ResponseWriter writer)
            throws IOException {
        encodeRowStart(context, getRowSkinClass(), rowClass, table, holder,
                writer);
    }
    
    public void encodeNoDataRow(FacesContext context, 
    		UIComponent component) throws IOException {
    	UIDataTable table = (UIDataTable) component;
    	ResponseWriter writer = context.getResponseWriter();
        int numberOfColumns = getColumnsCount(table) + 1;
        writer.startElement(HTML.TR_ELEMENT, table);
        writer.writeAttribute(HTML.id_ATTRIBUTE, table.getBaseClientId(context)
                + ":noDataRow", null);
        writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-noData-row " + getRowSkinClass(), null);
        
        writer.startElement(HTML.td_ELEM, table);
        writer.writeAttribute(HTML.class_ATTRIBUTE,
                "extdt-noData-cell " + getCellSkinClass(), null);
        writer.writeAttribute("colspan", numberOfColumns, null);
        String label = (String)table.getAttributes().get("noDataLabel");
        if ((label == null) || (label.length() == 0)) {
        	label = ComponentMessageUtil.getMessage(context, MSG_NODATA, new Object[] {}).getSummary();
        }
        writer.writeText(label, null);
        writer.endElement(HTML.td_ELEM);
        writer.endElement(HTML.TR_ELEMENT);
    	
    }

    /**
     * @return
     */
    protected String getRowSkinClass() {
        return "extdt-row rich-extdt-row";
    }

    /**
     * @return
     */
    protected String getFirstRowSkinClass() {
        return "extdt-firstrow rich-extdt-firstrow";
    }

    /**
     * @return
     */
    protected String getCellSkinClass() {
        return "extdt-cell rich-extdt-cell";

    }

    protected void encodeRowStart(FacesContext context, String skinClass,
            String rowClass, UIDataTable table, TableHolder holder,
            ResponseWriter writer) throws IOException {
        writer.startElement(HTML.TR_ELEMENT, table);
        encodeRowId(context, writer, table, holder.getRowCounter());
        encodeStyleClass(writer, null, skinClass, null, rowClass);
        encodeRowEvents(context, table);
    }

    /**
     * Row ID generator
     * 
     * @param context
     *            current FacesContext
     * @param writer
     *            ResponseWriter for FacesContext
     * @param table
     *            table for which this row is being encoded
     * @param rowId
     *            new rowId to encode
     * @throws IOException
     *             if ResponseWriter fails
     */
    protected void encodeRowId(FacesContext context, ResponseWriter writer,
            UIDataTable table, int rowId) throws IOException {
        String ownerId = table.getBaseClientId(context);
        getUtils().writeAttribute(writer, "id", ownerId + ":n:" + rowId);
    }

    /**
     * Calculate total number of columns in table.
     * 
     * @param context
     * @param table
     * @return
     */
    protected int getColumnsCount(UIDataTable table) {
        int count = 0;
        // check for exact value in component
        Integer span = (Integer) table.getAttributes().get("columns");
        if (null != span && span.intValue() != Integer.MIN_VALUE) {
            count = span.intValue();
        } else {
            // calculate max html columns count for all columns/rows children.
            Iterator<UIComponent> col = table.columns();
            count = calculateRowColumns(col);
        }
        return count;
    }

    /**
     * Calculate max number of columns per row. For rows, recursive calculate
     * max length.
     * 
     * @param col -
     *            Iterator other all columns in table.
     * @return
     */
    protected int calculateRowColumns(Iterator<UIComponent> col) {
        int count = 0;
        int currentLength = 0;
        while (col.hasNext()) {
            UIComponent column = (UIComponent) col.next();
            if (column.isRendered()) {
                if (column instanceof Row) {
                    // Store max calculated value of previsous rows.
                    if (currentLength > count) {
                        count = currentLength;
                    }
                    // Calculate number of columns in row.
                    currentLength = calculateRowColumns(((Row) column)
                            .columns());
                    // Store max calculated value
                    if (currentLength > count) {
                        count = currentLength;
                    }
                    currentLength = 0;
                } else if (column instanceof Column) {
                    Column tableColumn = (Column) column;
                    // For new row, save length of previsous.
                    if (tableColumn.isBreakBefore()) {
                        if (currentLength > count) {
                            count = currentLength;
                        }
                        currentLength = 0;
                    }
                    Integer colspan = (Integer) column.getAttributes().get(
                            "colspan");
                    // Append colspan of this column
                    if (null != colspan
                            && colspan.intValue() != Integer.MIN_VALUE) {
                        currentLength += colspan.intValue();
                    } else {
                        currentLength++;
                    }
                } else if (column instanceof javax.faces.component.UIColumn) {
                    // UIColumn always have colspan == 1.
                    currentLength++;
                }

            }
        }
        if (currentLength > count) {
            count = currentLength;
        }
        return count;
    }

    public void encodeScriptIfNecessary(FacesContext context,
            UIExtendedDataTable table) throws IOException {
        boolean shouldRender = false;
        Iterator<UIColumn> columns = table.getSortedColumns();
        while (columns.hasNext() && !shouldRender) {
            UIColumn next = columns.next();
            shouldRender = next.isSortable();
            shouldRender = true;// shouldRender || (next instanceof
            // HtmlDataColumn);
        }
        shouldRender = true;
        if (shouldRender) {
            getUtils().writeScript(context, table,
                    createClientDataTable(context, table));
        }
    }

    public String createClientDataTable(FacesContext context,
            UIExtendedDataTable table) {
        JSFunction function = new JSFunction("new ExtendedDataTable.DataTable");
        function.addParameter(table.getBaseClientId(context));
        ScriptOptions scriptOptions = new ScriptOptions(table);
        // add on resize column AJAX function
        scriptOptions.addOption(ON_RESIZE_FUNCTION, getOnResizeFunctionDef(
                context, table));
        scriptOptions.addOption(SORT_FUNCTION, getSortFunctionDef(context,
                table));
        scriptOptions.addOption(GROUP_FUNCTION, getGroupFunctionDef(context,
                table));
        scriptOptions.addOption(SHOW_MENU_FUNCTION, getShowMenuFunction(
                context, table));
        scriptOptions.addOption(ENABLE_CONTEXT_MENU,table.getAttributes().get(ENABLE_CONTEXT_MENU));
        /* Not needed if we do not save open/close state */
        scriptOptions.addOption(ON_GROUP_TOGGLE_FUNCTION,
                getOnGroupToggleFunctionDef(context, table));
        scriptOptions.addOption("minColumnWidth", MIN_COLUMN_WIDTH);
        composite.mergeScriptOptions(scriptOptions, context, table);
        function.addParameter(scriptOptions);
        return function.toScript();
    }

    protected JSFunctionDefinition getShowMenuFunction(FacesContext context,
            UIDataTable table) {
        return new RichTableMenuRenderer().createShowMenuEventFunction();
    }

    protected JSFunctionDefinition getSortFunctionDef(FacesContext context,
            UIDataTable table) {
        return getSortFunctionDef(context, table, null);
    }

    protected JSFunctionDefinition getSortFunctionDef(FacesContext context,
            UIDataTable table, Boolean asc) {
        JSFunctionDefinition definition = new JSFunctionDefinition();
        definition.addParameter("event");
        definition.addParameter("columnId");
        definition.addParameter("ascending");
        String id = table.getClientId(context);
        Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) eventOptions
                .get("parameters");
        parameters.put(id, SORT_FILTER_PARAMETER);
//        if (asc != null) {
//            parameters.put(SORT_DIR_PARAMETER, asc ? SORT_DIR_PARAMETER_ASC
//                    : SORT_DIR_PARAMETER_DESC);
//        }
        // parameters.put(SORT_FILTER_PARAMETER, column.getClientId(context));
        JSFunctionDefinition onAjaxCompleteFunction = getOnAjaxCompleteFunction(
                context, table);
        if (onAjaxCompleteFunction != null) {
            eventOptions.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);
        }
        definition.addToBody("var options = ").addToBody(
                ScriptUtils.toScript(eventOptions)).addToBody(";\n");
        definition.addToBody("options.parameters['" + SORT_FILTER_PARAMETER
                + "'] = columnId;\n");
        definition.addToBody("" +
        		"if (ascending != null){"+
        		"	options.parameters['" + SORT_DIR_PARAMETER + "'] = (ascending ? '"+SORT_DIR_PARAMETER_ASC+"':'"+SORT_DIR_PARAMETER_DESC+"');"+
        		"}"
        );
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(new JSReference("options"));
        definition.addToBody(ajaxFunction.toScript()).addToBody(";\n");
        return definition;
    }// getSortFunctionDef

    protected JSFunction getSortFunction(FacesContext context, UIDataTable table) {
        String id = table.getClientId(context);
        Map<String, Object> requestOpts = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) requestOpts
                .get("parameters");
        parameters.put(id, SORT_FILTER_PARAMETER);
        parameters.put(SORT_DIR_PARAMETER, "{sortDirection}");
        parameters.put(SORT_FILTER_PARAMETER, "{columnId}");
        JSFunctionDefinition onAjaxCompleteFunction = getOnAjaxCompleteFunction(
                context, table);
        if (onAjaxCompleteFunction != null) {
            requestOpts.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);
        }
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(requestOpts);
        return ajaxFunction;
    }// getSortFunction  

    protected JSFunction getGroupFunction(FacesContext context,
            UIDataTable table) {
        String id = table.getClientId(context);
        Map<String, Object> requestOpts = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) requestOpts
                .get("parameters");
        parameters.put(id, GROUP_FILTER_PARAMETER);
        parameters.put(GROUP_FILTER_PARAMETER, "{columnId}");
        JSFunctionDefinition onAjaxCompleteFunction = getOnAjaxCompleteFunction(
                context, table);
        if (onAjaxCompleteFunction != null) {
            requestOpts.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);
        }
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(requestOpts);
        return ajaxFunction;
    }// getGroupFunction
    
    protected JSFunctionDefinition getGroupFunctionDef(FacesContext context,
            UIDataTable table) {
    	JSFunctionDefinition definition = new JSFunctionDefinition();
        definition.addParameter("event");
        definition.addParameter("columnId");
        String id = table.getClientId(context);
        Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) eventOptions
                .get("parameters");
        parameters.put(id, GROUP_FILTER_PARAMETER);
        
        JSFunctionDefinition onAjaxCompleteFunction = getOnAjaxCompleteFunction(
                context, table);
        if (onAjaxCompleteFunction != null) {
            eventOptions.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);
        }
        definition.addToBody("var options = ").addToBody(
                ScriptUtils.toScript(eventOptions)).addToBody(";\n");
        definition.addToBody("options.parameters['" + GROUP_FILTER_PARAMETER
                + "'] = columnId;\n");
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(new JSReference("options"));
        definition.addToBody(ajaxFunction.toScript()).addToBody(";\n");
        return definition;
    }// getGroupFunctionDef
    
    protected JSFunctionDefinition getOnGroupToggleFunctionDef(
            FacesContext context, UIDataTable table) {
        JSFunctionDefinition definition = new JSFunctionDefinition();
        definition.addParameter("event");
        definition.addParameter("groupIndex");
        Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) eventOptions
                .get("parameters");
        parameters.put(GROUP_TOGGLE_ACTION_NAME, GROUP_TOGGLE_ACTION_NAME);
        definition.addToBody("var options = ").addToBody(
                ScriptUtils.toScript(eventOptions)).addToBody(";\n");
        definition
                .addToBody("options.parameters['groupIndex'] = groupIndex;\n");
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(new JSReference("options"));
        definition.addToBody(ajaxFunction.toScript()).addToBody(";\n");
        return definition;
    }// getOnGroupToggleFunctionDef

    protected JSFunctionDefinition getOnResizeFunctionDef(FacesContext context,
            UIDataTable table) {
        JSFunctionDefinition definition = new JSFunctionDefinition();
        definition.addParameter("event");
        definition.addParameter("columnWidths");

        Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) eventOptions
                .get("parameters");
        parameters.put(COL_RESIZE_ACTION_NAME, COL_RESIZE_ACTION_NAME);
        definition.addToBody("var options = ").addToBody(
                ScriptUtils.toScript(eventOptions)).addToBody(";\n");
        definition
                .addToBody("options.parameters['columnWidths'] = columnWidths;\n");

        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(new JSReference("options"));
        definition.addToBody(ajaxFunction.toScript()).addToBody(";\n");
        return definition;
    }

    protected JSFunction getChangeColumnVisibilityFunction(
            FacesContext context, UIDataTable table) {
        boolean ajaxSingle = true;
        Map<String, Object> requestOpts = AjaxRendererUtils.buildEventOptions(
                context, table);

        JSFunctionDefinition onAjaxCompleteFunction = getOnAjaxCompleteFunction(
                context, table);
        if (onAjaxCompleteFunction != null)
            requestOpts.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);

        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) requestOpts
                .get("parameters");
        if (parameters == null) {
            parameters = new HashMap<String, Object>();
            requestOpts.put("parameters", parameters);
        }
        if (ajaxSingle) {
            if (!parameters
                    .containsKey(AjaxRendererUtils.AJAX_SINGLE_PARAMETER_NAME))
                parameters.put(AjaxRendererUtils.AJAX_SINGLE_PARAMETER_NAME,
                        table.getClientId(context));
            if (!requestOpts.containsKey("control"))
                requestOpts.put("control", JSReference.THIS);
        }
        parameters.put(
                table.getClientId(context) + ":" + CHANGE_COL_VISIBILITY,
                "{columnId}");

        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        ajaxFunction.addParameter(requestOpts);
        return ajaxFunction;
    }

    protected JSFunction getPreSendAjaxRequestFunction(FacesContext context,
            UIDataTable table) {
        return new JSFunction(getJavaScriptVarName(context, table)
                + ".preSendAjaxRequest");
    }

    protected void doDecode(FacesContext context, UIComponent component) {
        super.doDecode(context, component);
        composite.decode(context, component);

        if (component instanceof UIExtendedDataTable) {
            UIExtendedDataTable table = (UIExtendedDataTable) component;
            Map<String, String> map = context.getExternalContext()
                    .getRequestParameterMap();
            String clientId = component.getClientId(context);

            if (SORT_FILTER_PARAMETER.equals(map.get(clientId))) {
                String sortColumnId = map.get(SORT_FILTER_PARAMETER);
                boolean isSingleSortMode = !"multi".equals(table.getSortMode());
                
                boolean sorting = false;
                boolean filtering = false;

                boolean isGroupingOn = table.isGroupingOn();
                boolean sortByGroupingColumn = false;
                // String groupingColumnId = null;
                UIColumn groupingColumn = null;
                if (isGroupingOn) {
                    groupingColumn = table.getGroupByColumn();
                    sortByGroupingColumn = (groupingColumn == null ? false
                            : groupingColumn.getClientId(context).equals(
                                    sortColumnId));
                    // groupingColumnId = table.getGroupingColumnId();
                }
                for (Iterator<UIColumn> columns = table.getChildColumns(); columns
                        .hasNext();) {
                    UIColumn column = columns.next();
                    String id = column.getId();
                    column.setId(id);

                    if (sortColumnId != null) {
                    	sorting = true;
                        boolean isGroupingColumn = (isGroupingOn && column
                                .equals(groupingColumn));
                        if (sortColumnId.equals(column.getClientId(context))) {
                            // set sort order
                            if (map.containsKey(SORT_DIR_PARAMETER)) {
                                String sortDir = (String) map
                                        .get(SORT_DIR_PARAMETER);
                                column
                                        .setSortOrder((sortDir
                                                .equals(SORT_DIR_PARAMETER_ASC) ? Ordering.ASCENDING
                                                : (sortDir
                                                        .equals(SORT_DIR_PARAMETER_DESC) ? Ordering.DESCENDING
                                                        : Ordering.UNSORTED)));
                            } else {
                                column.toggleSortOrder();
                            }

                            Collection<Object> sortPriority = table
                                    .getSortPriority();
                            // clear sort priority in case of single sort mode
                            if (isSingleSortMode) {
                                sortPriority.clear();
                            }
                            // add column to sort priority if is not added yet
                            if (!sortPriority.contains(id)) {
                                sortPriority.add(id);
                            }
                            if (isGroupingColumn) {
                                // set as grouping column to mark that grouping
                                // order has changed
                                table.setGroupByColumn(column);
                            }
                        } else if (isSingleSortMode) { // in case of single
                            // sort mode
                            if (!isGroupingColumn) { // grouping is not by
                                // this column
                                if (!sortByGroupingColumn) {// sort not by
                                    // grouping column
                                    // disable sort by this column
                                    column.setSortOrder(Ordering.UNSORTED);
                                }
                            }
                        }
                    }

                    UIInput filterValueInput = (UIInput) column
                            .getFacet(FILTER_INPUT_FACET_NAME);
                    if (null != filterValueInput) {
                        filterValueInput.decode(context);
                        String oldFilterValue = column.getFilterValue();
                        Object submittedValue = filterValueInput
                                .getSubmittedValue();
                        String newFilterValue = null;
                        if (null != submittedValue) {
                            newFilterValue = filterValueInput
                                    .getSubmittedValue().toString();
                            if ((newFilterValue != null)
                                    && (newFilterValue.length() == 0)) {
                                newFilterValue = null;
                            }
                            column.setFilterValue(newFilterValue);
                        }
                        boolean filterChanged = (newFilterValue == null ? (oldFilterValue != null)
                                : !newFilterValue.equals(oldFilterValue));
//                        if (filterChanged) {
//                        	//set focus on changed field
//                        	AjaxContext.getCurrentInstance().getResponseDataMap().put(AjaxActionComponent.FOCUS_DATA_ID, filterValueInput.getClientId(context));
//                        }
                        filtering = (filtering || filterChanged);
                    }
                }
    			if (AjaxRendererUtils.isAjaxRequest(context)) {
    				AjaxRendererUtils.addRegionsFromComponent(table, context);
    			}
                // AjaxContext.getCurrentInstance().addComponentToAjaxRender(component);
                if (sorting){
                	new ExtTableSortEvent(component).queue();
                }
                if (filtering){
                	new ExtTableFilterEvent(component).queue();
                }
                
            }

            // GROUP COLUMNS
            if (GROUP_FILTER_PARAMETER.equals(map.get(clientId))) {
                String groupColumnId = map.get(GROUP_FILTER_PARAMETER);
                // turn off grouping
                table.disableGrouping();
                if (groupColumnId != null) {
                    // turn off sorting by all columns
                    table.getSortPriority().clear();
                    for (Iterator<UIColumn> columns = table.getChildColumns(); columns
                            .hasNext();) {
                        UIColumn column = columns.next();
                        // child.setId(child.getId());
                        if (groupColumnId.equals(column.getClientId(context))) { // group
                            // by
                            // this
                            // column
                            // set sort order if is not set
                            if (column.getSortOrder().equals(Ordering.UNSORTED)) {
                                column.setSortOrder(Ordering.ASCENDING);
                            }
                            // set as grouping column
                            table.setGroupByColumn(column);
                        } else { // grouping is not by this column
                            // turn off sorting by this column
                            column.setSortOrder(Ordering.UNSORTED);
                        }
                    }// for columns
                }// if

                // AjaxContext.getCurrentInstance().addComponentToAjaxRender(component);
                // AjaxContext.getCurrentInstance().addRenderedArea(clientId +
                // ":tu");
                new ExtTableSortEvent(component).queue();
            }// group columns

            // CHANGE COLUMN ORDER - DRAG AND DROP
            String dragSourceId = (String) map
                    .get(org.richfaces.renderkit.DraggableRendererContributor.DRAG_SOURCE_ID);
            String dropTargetId = (String) map
                    .get(org.richfaces.renderkit.DropzoneRendererContributor.DROP_TARGET_ID);
            if ((dragSourceId != null) && (dropTargetId != null)) {
                Pattern sourcePattern = Pattern.compile(clientId + ":(\\w*):"
                        + TableDragDropRenderer.DRAG_SOURCE_SCRIPT_ID);
                Pattern targetPattern = Pattern.compile(clientId + ":(\\w*):"
                        + TableDragDropRenderer.DROP_TARGET_SCRIPT_ID + "("
                        + TableDragDropRenderer.DROP_TARGET_BEFORE + "|"
                        + TableDragDropRenderer.DROP_TARGET_AFTER + ")");
                Matcher sourceMatcher = sourcePattern.matcher(dragSourceId);
                Matcher targetMatcher = targetPattern.matcher(dropTargetId);
                if (sourceMatcher.find() && targetMatcher.find()) {
                    String sourceColumnId = sourceMatcher.group(1);
                    String targetColumnId = targetMatcher.group(1);
                    String kind = targetMatcher.group(2);

                    DragDropEvent dragDropEvent = new DragDropEvent(component);
                    dragDropEvent.setDragValue(sourceColumnId);
                    dragDropEvent.setDropValue(targetColumnId);
                    dragDropEvent.setDropBefore(kind
                            .equals(TableDragDropRenderer.DROP_TARGET_BEFORE));

                    dragDropEvent.queue();

                    // AjaxContext.getCurrentInstance().addComponentToAjaxRender(component);
                    // AjaxContext ajaxContext =
                    // AjaxContext.getCurrentInstance();
                    // ajaxContext.addComponentToAjaxRender(component);
                    // ajaxContext.addRenderedArea(clientId + ":tb");// body
                    // ajaxContext.addRenderedArea(clientId + ":tu");
                    // ajaxContext.addRenderedArea(clientId + ":tm");// menu
                    // AjaxContext.getCurrentInstance().addRenderedArea(clientId);
                }
            }// change column order

            // CHANGE COLUMN VISIBILITY
            String columnToChange = (String) map.get(clientId + ":"
                    + TableMenuRenderer.CHANGE_COL_VISIBILITY);
            if (columnToChange != null) {
                ChangeColumnVisibilityEvent event = new ChangeColumnVisibilityEvent(
                        component, columnToChange);

                event.queue();

                // AjaxContext.getCurrentInstance().addComponentToAjaxRender(component);
                // AjaxContext ajaxContext = AjaxContext.getCurrentInstance();
                // ajaxContext.addComponentToAjaxRender(component);
                // ajaxContext.addRenderedArea(clientId + ":tb");// body
                // ajaxContext.addRenderedArea(clientId + ":tu");
                // ajaxContext.addRenderedArea(clientId + ":tm");// menu
            }// change column visibility

            // COLUMN RESIZE
            if (COL_RESIZE_ACTION_NAME.equals(map.get(COL_RESIZE_ACTION_NAME))) {
                String colWidths = (String) map.get("columnWidths");
                ColumnResizeEvent event = new ColumnResizeEvent(component,
                        colWidths);
                event.queue();
            }

            // TOGGLE ROW GROUP
            if (GROUP_TOGGLE_ACTION_NAME.equals(map
                    .get(GROUP_TOGGLE_ACTION_NAME))) {
                String group = (String) map.get("groupIndex");
                if (group != null) {
                    try {
                        table.toggleGroup(Integer.valueOf(group));
                    } catch (NumberFormatException _) {
                    }
                }// if
                context.renderResponse();
            }

        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)throws IOException {
    //FIXME need to check if EDT is enclosed in a form!
//        UIComponent c = component;
//        boolean inForm = false;
//        while((c = c.getParent()) != null) {
//            if(c instanceof UIForm) {
//                inForm = true;
//                break;
//            }
//        }
//        if(inForm && log.isWarnEnabled()) {
//            log.warn("Extended Data Table must be enclosed in a Form component");
//        }
    	super.encodeBegin(context, component);
    	//component.getAttributes().put(AjaxRendererUtils.AJAX_SINGLE_ATTR, Boolean.TRUE);
    }
    
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        String clientId = component.getClientId(context);
        Set<String> ajaxRenderedAreas = AjaxContext.getCurrentInstance()
                .getAjaxRenderedAreas();
        // if (ajaxRenderedAreas.contains(clientId + ":tb")) {
        // ajaxRenderedAreas.remove(clientId);
        // }
        if (ajaxRenderedAreas.contains(clientId)) {
            // remove all child elements
            for (Iterator<String> iter = ajaxRenderedAreas.iterator(); iter
                    .hasNext();) {
                String area = iter.next();
                if (area.startsWith(clientId) && (!area.equals(clientId))) {
                    iter.remove();
                }
            }
        }
    }

    protected void addInplaceInput(FacesContext context, UIComponent column) throws IOException {
        UIInput filterValueInput = (UIInput) column
                .getFacet(FILTER_INPUT_FACET_NAME);
        if (null == filterValueInput) {
            filterValueInput = (UIInput) context.getApplication()
                    .createComponent(UIInput.COMPONENT_TYPE);
            filterValueInput.setId(column.getId() + SORT_FILTER_PARAMETER);
            filterValueInput.setImmediate(true);
            filterValueInput.getAttributes().put(HTML.STYLE_CLASS_ATTR, "rich-filter-input");
            column.getFacets().put(FILTER_INPUT_FACET_NAME, filterValueInput);
            filterValueInput.getAttributes().put(HTML.onclick_ATTRIBUTE,
                    "Event.stop(event);");
        }
        String filterEvent = (String) column.getAttributes().get("filterEvent");
        if (null == filterEvent || "".equals(filterEvent)) {
            filterEvent = "onchange";
        }

        String buffer = buildAjaxFunction(
        		context, 
        		column, 
        		false, 
        		buildSetFocusFunctionDef(filterValueInput.getClientId(context)));
        filterValueInput.getAttributes().put(filterEvent, buffer);
        filterValueInput.setValue(column.getAttributes().get("filterValue"));

        getUtils().encodeBeginFormIfNessesary(context, column);
        renderChild(context, filterValueInput);
        getUtils().encodeEndFormIfNessesary(context, column);
    }

    protected String buildAjaxFunction(FacesContext context,
            UIComponent column, boolean sortable,
            JSFunctionDefinition onAjaxCompleteFunction) {
        UIComponent table = column.getParent();
        String id = table.getClientId(context);
        JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(table,
                context);
        Map<String, Object> eventOptions = AjaxRendererUtils.buildEventOptions(
                context, table);
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters = (Map<String, Object>) eventOptions
                .get("parameters");

        parameters.put(id, SORT_FILTER_PARAMETER);
        if (sortable) {
            parameters.put(SORT_FILTER_PARAMETER, column.getClientId(context));
        }
        if (onAjaxCompleteFunction != null)
            eventOptions.put(AjaxRendererUtils.ONCOMPLETE_ATTR_NAME,
                    onAjaxCompleteFunction);
        ajaxFunction.addParameter(eventOptions);
        StringBuffer buffer = new StringBuffer();
        ajaxFunction.appendScript(buffer);

        return buffer.toString();
    }

    protected class SimpleHeaderEncodeStrategy implements HeaderEncodeStrategy {

        public void encodeBegin(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {

        }

        public void encodeEnd(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {

        }

    }

    protected class RichHeaderEncodeStrategy implements HeaderEncodeStrategy {

        public void encodeBegin(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {
            UIColumn col = (UIColumn) column;
            String clientId = col.getClientId(context) + facetName;
            writer.writeAttribute("id", clientId, null);

            if (sortableColumn && col.isSelfSorted()) {
                writer.writeAttribute(HTML.onclick_ATTRIBUTE,
                        buildAjaxFunction(context, column, true, null)
                                .toString(), null);
                writer.writeAttribute(HTML.style_ATTRIBUTE, "cursor: pointer;",
                        null);
            }

            writer.startElement(HTML.DIV_ELEM, column);
            writer.writeAttribute(HTML.id_ATTRIBUTE, clientId + ":sortDiv",
                    null);
            AjaxContext.getCurrentInstance().addRenderedArea(
                    clientId + ":sortDiv");

            if (sortableColumn) {
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                        "extdt-sortable-header", null);
            }
        }

        public void encodeEnd(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {
            UIColumn col = (UIColumn) column;
            if (sortableColumn) {
                String imageUrl = null;
                if (Ordering.ASCENDING.equals(col.getSortOrder())) {
                    if (null != col.getSortIconAscending()) {
                        imageUrl = col.getSortIconAscending();
                    } else {
                        imageUrl = getResource(TriangleIconUp.class.getName())
                                .getUri(context, null);
                    }
                } else if (Ordering.DESCENDING.equals(col.getSortOrder())) {
                    if (null != col.getSortIconDescending()) {
                        imageUrl = col.getSortIconDescending();
                    } else {
                        imageUrl = getResource(TriangleIconDown.class.getName())
                                .getUri(context, null);
                    }
                } else if (col.isSelfSorted()) {
                    if (null != col.getSortIcon()) {
                        imageUrl = col.getSortIcon();
                    } else {
                        imageUrl = getResource(
                                DataTableIconSortNone.class.getName()).getUri(
                                context, null);
                    }
                }

                if (imageUrl != null) {
                    writer.startElement(HTML.IMG_ELEMENT, column);
                    writer.writeAttribute(HTML.src_ATTRIBUTE, imageUrl, null);
                    writer.writeAttribute(HTML.width_ATTRIBUTE, "15", null);
                    writer.writeAttribute(HTML.height_ATTRIBUTE, "15", null);
                    writer.writeAttribute(HTML.class_ATTRIBUTE,
                            "extdt-header-sort-img", null);
                    writer.endElement(HTML.IMG_ELEMENT);
                }
                writer.endElement(HTML.SPAN_ELEM);
            }

            writer.endElement(HTML.DIV_ELEM);

            if (col.getFilterMethod() == null
                    && col.getValueExpression("filterExpression") == null
                    && col.getValueExpression("filterBy") != null) {

                writer.startElement(HTML.DIV_ELEM, column);
                addInplaceInput(context, column);
                writer.endElement(HTML.DIV_ELEM);
            }
        }
    }

    protected class G3HeaderEncodeStrategy implements HeaderEncodeStrategy {

        public void encodeBegin(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {
            if (column instanceof UIColumn) {
                UIColumn dataColumn = (UIColumn) column;
                String clientId = dataColumn.getClientId(context);// +
                // facetName;
                writer.writeAttribute("id", clientId, null);
                column.getAttributes().put("columnClientId", clientId);
                boolean sortable = sortableColumn && dataColumn.isSelfSorted();
                if (sortable) {
                    /*
                     * writer.writeAttribute(HTML.onclick_ATTRIBUTE,
                     * buildAjaxFunction( context, column, true,
                     * getOnAjaxCompleteFunction(context, (UIDataTable)
                     * column.getParent())) .toString(), null);
                     */
                    writer.writeAttribute(HTML.style_ATTRIBUTE,
                            "cursor: pointer;", null);
                }
                writer.writeAttribute("sortable", String.valueOf(sortable),
                        null);
                // column.getAttributes().put("sortable",Boolean.valueOf(sortable));
                // drag source area
                writer.startElement(HTML.DIV_ELEM, dataColumn);
                writer.writeAttribute(HTML.id_ATTRIBUTE, dataColumn.getParent()
                        .getClientId(context)
                        + "_hdrag_" + dataColumn.getId(), null);

                writer.startElement(HTML.DIV_ELEM, dataColumn);
                writer.writeAttribute(HTML.id_ATTRIBUTE, clientId + ":sortDiv",
                        null);
                AjaxContext.getCurrentInstance().addRenderedArea(
                        clientId + ":sortDiv");

                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                            "extdt-sortable-header", null);
            }
        }

        public void encodeEnd(FacesContext context, ResponseWriter writer,
                UIComponent column, String facetName, boolean sortableColumn)
                throws IOException {
            if (column instanceof UIColumn) {
                UIColumn dataColumn = (UIColumn) column;
                String clientId = dataColumn.getClientId(context) + facetName;
                String tableId = dataColumn.getParent().getClientId(context);

                String imageUrl = null;
                if (sortableColumn) {
                    if (Ordering.ASCENDING.equals(dataColumn.getSortOrder())) {
                        if (null != dataColumn.getSortIconAscending()) {
                            imageUrl = dataColumn.getSortIconAscending();
                        } else {
                            imageUrl = getResource(
                                    TriangleIconUp.class.getName()).getUri(
                                    context, null);
                        }
                    } else if (Ordering.DESCENDING.equals(dataColumn
                            .getSortOrder())) {
                        if (null != dataColumn.getSortIconDescending()) {
                            imageUrl = dataColumn.getSortIconDescending();
                        } else {
                            imageUrl = getResource(
                                    TriangleIconDown.class.getName()).getUri(
                                    context, null);
                        }
                    } else if (dataColumn.isSelfSorted()) {
                        if (null != dataColumn.getSortIcon()) {
                            imageUrl = dataColumn.getSortIcon();
                        } else {
                            imageUrl = getResource(
                                    DataTableIconSortNone.class.getName())
                                    .getUri(context, null);
                        }
                    }
                }
                else{
                	//set empty image
                	imageUrl = getResource("/org/richfaces/renderkit/html/images/s.gif").getUri(context, null);
                }
                if (imageUrl != null) {
                    writer.startElement(HTML.IMG_ELEMENT, column);
                    writer.writeAttribute(HTML.src_ATTRIBUTE, imageUrl,
                            null);
                    writer.writeAttribute(HTML.class_ATTRIBUTE,
                            "extdt-header-sort-img", null);
                    writer.endElement(HTML.IMG_ELEMENT);
                }
                writer.endElement(HTML.SPAN_ELEM);

                writer.endElement(HTML.DIV_ELEM);

                // drag source area
                writer.endElement(HTML.DIV_ELEM);
                String dragSourceId = tableId + "_hdrag_" + dataColumn.getId();
                String indicatorId = tableId + ":dataTable_indicator";
                renderDragSupport(context, dataColumn, dragSourceId,
                        indicatorId, (String)dataColumn.getAttributes().get("label"));

                // separator area
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.id_ATTRIBUTE, clientId + ":sepSpan",
                        null);
                writer
                        .writeAttribute(HTML.class_ATTRIBUTE, "extdt-hsep",
                                null);
                writer.endElement(HTML.SPAN_ELEM);

                // drop target area LEFT
                String spanId = tableId + "_hdrop_" + dataColumn.getId()
                        + "left";
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.id_ATTRIBUTE, spanId, null);
                writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-hdrop",
                        null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                        "extdt-hdrop-top extdt-hdrop-top-left", null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.endElement(HTML.SPAN_ELEM);
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                        "extdt-hdrop-bottom extdt-hdrop-bottom-left", null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.endElement(HTML.SPAN_ELEM);
                writer.endElement(HTML.SPAN_ELEM);
                renderDropSupport(context, dataColumn, spanId, true);

                // drop target area RIGHT
                spanId = tableId + "_hdrop_" + dataColumn.getId() + "right";
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.id_ATTRIBUTE, spanId, null);
                writer.writeAttribute(HTML.class_ATTRIBUTE, "extdt-hdrop",
                        null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                        "extdt-hdrop-top extdt-hdrop-top-right", null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.endElement(HTML.SPAN_ELEM);
                writer.startElement(HTML.SPAN_ELEM, column);
                writer.writeAttribute(HTML.class_ATTRIBUTE,
                        "extdt-hdrop-bottom extdt-hdrop-bottom-right", null);
                writer.writeAttribute(HTML.style_ATTRIBUTE,
                        "visibility: hidden;", null);
                writer.endElement(HTML.SPAN_ELEM);
                writer.endElement(HTML.SPAN_ELEM);
                renderDropSupport(context, dataColumn, spanId, false);

                // menu
                if ("header".equals(facetName)) {
                    writer.startElement(HTML.DIV_ELEM, column);
                    String menuDivId = clientId + ":menuDiv";
                    writer.writeAttribute(HTML.id_ATTRIBUTE, menuDivId, null);
                    writer.writeAttribute(HTML.class_ATTRIBUTE,
                            "extdt-menu-div-out", null);
                    writer.endElement(HTML.DIV_ELEM);
                }

                // if (dataColumn.getFilterMethod() == null
                // && dataColumn.getValueExpression("filterExpression") == null
                // && dataColumn.getValueExpression("filterBy") != null) {
                //
                // writer.startElement(HTML.DIV_ELEM, column);
                // addInplaceInput(context, column, buildAjaxFunction(context,
                // column, false, getOnAjaxCompleteFunction(context,
                // (UIDataTable) column.getParent())));
                // writer.endElement(HTML.DIV_ELEM);
                // }
            }
        }
    }

    public String encodeDragDropChildScripts(FacesContext context,
            UIDataTable component) throws IOException {
        TableDragDropRenderer.getInstance(context).encodeChildScripts(context,
                component);
        return "";
    }

    public void encodeNamespace(FacesContext context, UIComponent component)
            throws IOException {
        NSUtils.writeNameSpace(context, component);
    }

    public void renderDragSupport(FacesContext context, UIColumn column,
            String dragSourceId, String indicatorId, String dragLabel)
            throws IOException {
        TableDragDropRenderer.getInstance(context).renderDragSupport(column,
                dragSourceId, indicatorId, dragLabel);
    }// renderDragSupport

    public void renderDropSupport(FacesContext context, UIColumn column,
            String dropTargetId, boolean before) throws IOException {
        TableDragDropRenderer renderer = TableDragDropRenderer
                .getInstance(context);
        renderer.setOnAjaxCompleteFunctionDef(getOnAjaxCompleteFunction(
                context, (UIDataTable) column.getParent()));
        renderer.setPreSendAjaxRequestFunction(getPreSendAjaxRequestFunction(
                context, (UIDataTable) column.getParent()));
        renderer.renderDropSupport(column, dropTargetId, before);
    }// renderDropSupport

    public static String getJavaScriptVarName(FacesContext context,
            UIDataTable grid) {
        String id = grid.getBaseClientId(context);
        String name = "ExtendedDataTable.DataTable_"
                + id.replaceAll("[^A-Za-z0-9_]", "_");
        // String name = "Richfaces_ScrollableGrid";
        return "window." + name;
    }

    protected String getScriptContributions(FacesContext context,
            UIDataTable grid) {
        return composite.getScriptContributions(getJavaScriptVarName(context,
                grid), context, grid);
    }

    protected JSFunctionDefinition getOnAjaxCompleteFunction(
            FacesContext context, UIDataTable table) {

        return null;
        // JSFunctionDefinition function = new JSFunctionDefinition("request",
        // "event", "data");
        // String varName = getJavaScriptVarName(context, table);
        // function.addToBody(varName + ".update();");
        // return function;
    }

    public void encodeTableMenu(FacesContext context, UIExtendedDataTable table)
            throws IOException {
        AjaxContext ajaxContext = AjaxContext.getCurrentInstance();
        Object key = table.getRowKey();
        table.setRowKey(null);
        TableMenuRenderer menuRenderer = new RichTableMenuRenderer();
        menuRenderer.setSortFunction(getSortFunction(context, table));
        menuRenderer.setGroupFunction(getGroupFunction(context, table));
        menuRenderer
                .setChangeColumnVisibilityFunction(getChangeColumnVisibilityFunction(
                        context, table));
        menuRenderer.setPrepareFunction(getPreSendAjaxRequestFunction(context,
                table));
        for (Iterator<UIColumn> colums = table.getSortedColumns(); colums
                .hasNext();) {
            UIColumn col = colums.next();
            if (col.isRendered()) {
				String menuId = menuRenderer.renderMenu(context, table, col);
				ajaxContext.addRenderedArea(menuId);
			}
        }// for
        table.setRowKey(key);
    }

    public void contributorsEncodeHere(FacesContext context, UIDataTable table)
            throws IOException {
        RendererContributor[] contribs = composite.getContributors();

        if (contribs != null) {
            for (int i = 0; i < contribs.length; i++) {
                RendererContributor rendererContributor = contribs[i];

                if (rendererContributor instanceof HTMLEncodingContributor) {
                    ((HTMLEncodingContributor) rendererContributor).encode(
                            context, table);
                }
            }
        }
    }
}
