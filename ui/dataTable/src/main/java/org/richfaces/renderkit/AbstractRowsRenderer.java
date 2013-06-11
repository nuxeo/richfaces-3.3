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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.SequenceDataAdaptor;
import org.ajax4jsf.component.UIDataAdaptor;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.Row;

/**
 * @author shura
 * 
 */
public abstract class AbstractRowsRenderer extends HeaderResourcesRendererBase 
        implements DataVisitor {
    
	public static final String[][] TABLE_EVENT_ATTRS = 	{ 
		{"onclick","onRowClick"},
		{"ondblclick","onRowDblClick"},
		{"onmousemove","onRowMouseMove"},
		{"onmouseup","onRowMouseUp"},
		{"onmousedown","onRowMouseDown"},
		{"onmouseover","onRowMouseOver"},
		{"onmouseout","onRowMouseOut"}
	};


	public static final String ROW_CLASS_KEY = 
	    AbstractRowsRenderer.class.getName() + ".rowClass";

	public static final String SKIN_ROW_CLASS_KEY = 
	    AbstractRowsRenderer.class.getName() + ".skinRowClass";

	public static final String CELL_CLASS_KEY = 
	    AbstractRowsRenderer.class.getName() + ".cellClass";

	public static final String SKIN_CELL_CLASS_KEY = 
	    AbstractRowsRenderer.class.getName() + ".skinCellClass";

	public static final String SKIN_FIRST_ROW_CLASS_KEY = 
	    AbstractRowsRenderer.class.getName() + ".firstRowSkinClass";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.DataVisitor#process(javax.faces.context.FacesContext,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void process(FacesContext context, Object rowKey, Object argument)
			throws IOException {
		TableHolder holder = (TableHolder) argument;
		UIDataAdaptor table = holder.getTable();
		table.setRowKey(context, rowKey);
		encodeOneRow(context, holder);
		holder.nextRow();
	}

	public void encodeRows(FacesContext context, UIComponent component) throws IOException {
		encodeRows(context, component, new TableHolder((UIDataAdaptor) component));
	}
	
	/**
	 * Iterate over all rows for this table.
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	protected void encodeRows(FacesContext context, UIComponent component, TableHolder tableHolder)
			throws IOException {
		UIDataAdaptor table = (UIDataAdaptor) component;
		Object key = table.getRowKey();
		table.captureOrigValue(context);
		
		table.walk(context, this, tableHolder);
		
		doCleanup(context, tableHolder);
		table.setRowKey(key);
		table.restoreOrigValue(context);
	}

	/**
	 * @param context
	 *            TODO
	 * @param tableHolder
	 * @throws IOException
	 */
	protected void doCleanup(FacesContext context, TableHolder tableHolder)
			throws IOException {
		// Hoock method for perform encoding after all rows is rendered

	}

	public abstract void encodeOneRow(FacesContext context, TableHolder holder)
			throws IOException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		encodeRows(context, component);
	}

	public void encodeCaption(FacesContext context, SequenceDataAdaptor table)
			throws IOException {
		UIComponent caption = table.getFacet("caption");
		if (caption == null) {
		    return;
		}

        ResponseWriter writer = context.getResponseWriter();
		writer.startElement("caption", table);

	    String captionClass = (String) table.getAttributes().get("captionClass");
		if (captionClass != null) {
			captionClass = "dr-table-caption rich-table-caption " + captionClass;
		} else {
			captionClass = "dr-table-caption rich-table-caption";
		}
		writer.writeAttribute("class", captionClass, "captionClass");
		
        String captionStyle = (String) table.getAttributes().get("captionStyle");
		if (captionStyle != null) {
			writer.writeAttribute("style", captionStyle, "captionStyle");
		}
		
		renderChild(context, caption);
		
		writer.endElement("caption");
	}

	/**
	 * @param context
	 * @param table
	 * @throws IOException
	 */
	protected void encodeRowEvents(FacesContext context, UIDataAdaptor table)
			throws IOException {
		RendererUtils utils2 = getUtils();
		for (int i = 0; i < TABLE_EVENT_ATTRS.length; i++) {
		    String[] attrs = TABLE_EVENT_ATTRS[i];
			utils2.encodeAttribute(context, table, attrs[1], attrs[0]);		    
		}
	}

	/**
	 * Encode HTML "class" attribute, if is not empty. Classes combined from
	 * pre-defined skin classes, class from parent component, and custom
	 * attribute.
	 * 
	 * @param writer
	 * @param parentPredefined TODO
	 * @param predefined
	 *            predefined skin classes
	 * @param parent
	 *            class from parent component
	 * @param custom
	 *            custom classes.
	 * @throws IOException
	 */
	protected void encodeStyleClass(ResponseWriter writer, Object parentPredefined,
			Object predefined, Object parent, Object custom) throws IOException {
		StringBuffer styleClass = new StringBuffer();
		
		// Construct predefined classes
		if (null != parentPredefined) {
			styleClass.append(parentPredefined).append(" ");			
		} else if (null != predefined) {
			styleClass.append(predefined).append(" ");
		}
		
		// Append class from parent component.
		if (null != parent) {
			styleClass.append(parent).append(" ");
		}
		if (null != custom) {
			styleClass.append(custom);
		}
		if (styleClass.length() > 0) {
			writer.writeAttribute(HTML.class_ATTRIBUTE, styleClass, "styleClass");
		}
	}
	protected void encodeStyle(ResponseWriter writer, Object parentPredefined,
			Object predefined, Object parent, Object custom) throws IOException {
		StringBuffer style = new StringBuffer();
		// Construct predefined styles
		if (null != parentPredefined) {
			style.append(parentPredefined).append(" ");			
		} else if (null != predefined) {
			style.append(predefined).append(" ");
		}
		// Append style from parent component.
		if (null != parent) {
			style.append(parent).append(" ");
		}
		if (null != custom) {
			style.append(custom);
		}
		if (style.length() > 0) {
			writer.writeAttribute("style", style, "style");
		}
	}


	/**
	 * Render component and all its children with current row/cell style
	 * classes.
	 * 
	 * @param context
	 * @param cell
	 * @param skinFirstRowClass TODO
	 * @param skinRowClass
	 *            TODO
	 * @param rowClass
	 * @param skinCellClass
	 *            TODO
	 * @param cellClass
	 * @throws IOException
	 */
	protected void encodeCellChildren(FacesContext context, UIComponent cell,
			String skinFirstRowClass, String skinRowClass, String rowClass,
			String skinCellClass, String cellClass) throws IOException {
		
	    Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
		// Save top level class parameters ( if any ), and put new for this
		// component
		Object savedRowClass = requestMap.get(ROW_CLASS_KEY);
		if (null != rowClass) {
			requestMap.put(ROW_CLASS_KEY, rowClass);

		}
		Object savedSkinFirstRowClass = requestMap.get(SKIN_FIRST_ROW_CLASS_KEY);
		if (null != skinRowClass) {
			requestMap.put(SKIN_FIRST_ROW_CLASS_KEY, skinFirstRowClass);

		}
		Object savedSkinRowClass = requestMap.get(SKIN_ROW_CLASS_KEY);
		if (null != skinRowClass) {
			requestMap.put(SKIN_ROW_CLASS_KEY, skinRowClass);

		}
		Object savedCellClass = requestMap.get(CELL_CLASS_KEY);
		if (null != cellClass) {
			requestMap.put(CELL_CLASS_KEY, cellClass);
		}
		Object savedSkinCellClass = requestMap.get(SKIN_CELL_CLASS_KEY);
		if (null != skinCellClass) {
			requestMap.put(SKIN_CELL_CLASS_KEY, skinCellClass);

		}
		
		renderChild(context, cell);
		
		// Restore original values.
		requestMap.put(ROW_CLASS_KEY, savedRowClass);
		requestMap.put(CELL_CLASS_KEY, savedCellClass);
		requestMap.put(SKIN_FIRST_ROW_CLASS_KEY, savedSkinFirstRowClass);
		requestMap.put(SKIN_ROW_CLASS_KEY, savedSkinRowClass);
		requestMap.put(SKIN_CELL_CLASS_KEY, savedSkinCellClass);

	}

	protected void encodeTableHeaderFacet(FacesContext context, int columns, ResponseWriter writer, UIComponent footer, String skinFirstRowClass, String skinRowClass, String skinCellClass, String footerClass, String element) throws IOException {
		boolean isColgroup = footer instanceof Row;
		if (!isColgroup) {
			writer.startElement("tr", footer);
			encodeStyleClass(writer, null, skinFirstRowClass, footerClass, null);
			writer.startElement(element, footer);
			encodeStyleClass(writer, null, skinCellClass, footerClass, null);
			if (columns > 0) {
				writer.writeAttribute("colspan", String.valueOf(columns), null);
			}
			writer.writeAttribute("scope", "colgroup", null);
		}
		
		encodeCellChildren(context, footer, skinFirstRowClass, skinRowClass, 
		        footerClass, skinCellClass, null);
		
		if (!isColgroup) {
			writer.endElement(element);
			writer.endElement("tr");
		}
	}

}
