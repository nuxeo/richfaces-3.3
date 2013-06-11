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

import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSReference;
import org.richfaces.component.UIScrollableDataTable;


public class AjaxFunctionBuilder {

	private static final JSReference request = new JSReference("request");
	private static final JSReference event = new JSReference("event");
	private static final JSReference data = new JSReference("data");
	public static final int SORT = 0;
	public static final int SCROLL = 1;
	
	public static JSFunction createFunction(String name) {
		JSFunction function = new JSFunction(name);
		function.addParameter(request);
		function.addParameter(event);
		function.addParameter(data);
		
		return function;
	}	
		
	public static JSFunctionDefinition getOnComplete(FacesContext context, UIScrollableDataTable grid, int type) {
		
		JSFunction function = null;
		
		switch (type) {
			case SCROLL:
				function = createFunction(ScrollableDataTableBaseRenderer.getJavaScriptVarName(context, grid) + ".onScrollComplete");
				break;
			case SORT: 				
				function = createFunction(ScrollableDataTableBaseRenderer.getJavaScriptVarName(context, grid) + ".onSortComplete");
				break;
		}	

		JSFunctionDefinition functionDefinition = new JSFunctionDefinition();
		functionDefinition.addToBody(function);
		functionDefinition.addParameter(request);
		functionDefinition.addParameter(event);
		functionDefinition.addParameter(data);
		
		return functionDefinition;
	}
}
