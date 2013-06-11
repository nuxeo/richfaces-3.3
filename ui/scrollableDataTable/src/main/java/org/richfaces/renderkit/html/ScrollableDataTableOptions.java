/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.renderkit.html;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.renderkit.ScriptOptions;

public class ScrollableDataTableOptions extends ScriptOptions {

	public ScrollableDataTableOptions(UIScrollableDataTable grid) {
		super(grid);
		FacesContext context = FacesContext.getCurrentInstance();
		
		String id = grid.getBaseClientId(context);
		
		addOption("client_id", id);
		
		if(grid.getFacets().containsKey("splash")){
			UIComponent splash = grid.getFacet("splash");
			String splash_id = splash.getClientId(context);
			addOption("splash_id", splash_id);
		}
		
		int columnCount = grid.getChildCount();
		
		addOption("columnsCount", new Integer(columnCount));
		addOption("rowsCount", new Integer(grid.getRows()));
		addEventHandler("onselectionchange");
		addOption("ids", ScrollableDataTableRendererState.getRendererState(context).getIds());
		addOption("hideWhenScrolling");
		
		
		JSFunctionDefinition functionDefinition = new JSFunctionDefinition();
		
		JSReference sortEvent = new JSReference("event");
		functionDefinition.addParameter(sortEvent);
		functionDefinition.addToBody(onSortAjaxUpdate(context, grid));
		
		addOption("onSortAjaxUpdate", functionDefinition);	
		
	}
	
	public String onSortAjaxUpdate(FacesContext context, UIScrollableDataTable grid){
		
		JSReference sortColumn = new JSReference("event.column");
		JSReference sortOrder = new JSReference("event.order");
		JSReference sortStartRow = new JSReference("event.startRow");
		JSReference sortIndex = new JSReference("event.index");
		
		Map<String, Object> options = AjaxRendererUtils.buildEventOptions(context, grid);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> parametersMap = (Map)options.get("parameters");
		String id = grid.getClientId(context);
		parametersMap.put(id + ":sortColumn", sortColumn);
		parametersMap.put(id + ":sortOrder", sortOrder);
		parametersMap.put(id + ":sortStartRow", sortStartRow);
		parametersMap.put(id + ":sortIndex", sortIndex);
		options.put("parameters", parametersMap);
		
		JSFunction function = AjaxRendererUtils.buildAjaxFunction(grid, context);
		options.put("oncomplete", AjaxFunctionBuilder.getOnComplete(context, grid, AjaxFunctionBuilder.SORT));
		function.addParameter(options);
		String completeFunction = function.toScript() + "; return false;";
		
		return completeFunction;
	}

	
}
