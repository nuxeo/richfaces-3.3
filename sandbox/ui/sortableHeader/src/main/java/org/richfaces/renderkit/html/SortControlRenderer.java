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

import static org.ajax4jsf.renderkit.RendererUtils.HTML.SCRIPT_ELEM;
import static org.ajax4jsf.renderkit.RendererUtils.HTML.INPUT_ELEM;
import static org.ajax4jsf.renderkit.RendererUtils.HTML.NAME_ATTRIBUTE;
import static org.ajax4jsf.renderkit.RendererUtils.HTML.id_ATTRIBUTE;
import static org.richfaces.component.Mode.AJAX;
import static org.richfaces.component.Mode.CLIENT;
import static org.richfaces.component.Mode.SERVER;

import java.io.IOException;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIDataAdaptor;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.AjaxScript;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.PrototypeScript;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.Mode;
import org.richfaces.component.UIDataTable;
import org.richfaces.component.UISortableControl;
import org.richfaces.event.sort.SortEvent2;

/**
 * @author Maksim Kaszynski
 *
 */
public class SortControlRenderer extends HeaderResourcesRendererBase{

	interface RendererCommand {
		void encodeMarkup(ResponseWriter writer, FacesContext context, UISortableControl control) throws IOException;
	}
	
	private final static InternetResource[] scripts = {
		new AjaxScript(),
		new PrototypeScript()
	};

	private RendererCommand[] commands = new RendererCommand[Mode.values().length];
	
	public SortControlRenderer() {
		commands[AJAX.ordinal()] = new RendererCommand() {
			public void encodeMarkup(ResponseWriter writer, FacesContext context,
					UISortableControl control) throws IOException {
				
				JSFunction constructor = new JSFunction("new RichFaces.SortControl.Ajax");
				JSFunction ajaxFunction = AjaxRendererUtils.buildAjaxFunction(control, context);

				constructor.addParameter(control.getClientId(context));
				constructor.addParameter(ajaxFunction);
				writeScript(context, control, constructor);
			}
		};
		commands[CLIENT.ordinal()] = new RendererCommand() {
			public void encodeMarkup(ResponseWriter writer, FacesContext context,
					UISortableControl control) throws IOException {
				
				UIDataAdaptor table = control.getTable();
				String sortBy = null;
				ValueExpression sortExpression = control.getSortExpression();
				
				if (sortExpression != null) {
					sortBy = sortExpression.getExpressionString();
				}
				
				JSFunction function = new JSFunction("new RichFaces.SortControl");
				
				function.addParameter(control.getClientId(context));
				function.addParameter(table.getClientId(context));
				function.addParameter(sortBy == null ? JSReference.NULL: new JSReference(sortBy));
				function.addParameter(control.getParent().getClientId(context));
				writeScript(context, control, function);
			}
		};
		commands[SERVER.ordinal()] = new RendererCommand() {
			public void encodeMarkup(ResponseWriter writer, FacesContext context,
					UISortableControl control) throws IOException {
				
				JSFunction constructor = new JSFunction("new RichFaces.SortControl.Server");
				
				constructor.addParameter(control.getClientId(context));
				writeScript(context, control, constructor);
				writer.startElement(INPUT_ELEM, control);
				writer.writeAttribute(NAME_ATTRIBUTE, control.getClientId(context), "name");
				writer.writeAttribute(id_ATTRIBUTE, control.getClientId(context) + "s", "name");
				writer.endElement(INPUT_ELEM);
			}
		};
	}
	
	private void writeScript(FacesContext context, UIComponent component, Object script) 
		throws IOException {
		ResponseWriter writer = context.getResponseWriter(); 
		writer.startElement(SCRIPT_ELEM, component);
		writer.writeAttribute(id_ATTRIBUTE, component.getClientId(context), "id");
		writer.writeText(script, null);
		writer.endElement(SCRIPT_ELEM);
	}
	
	
	@Override
	protected Class<? extends UIComponent> getComponentClass() {
		return UISortableControl.class;
	}
	
	@Override
	protected InternetResource[] getScripts() {
		return scripts;
	}
	
	@Override
	protected void doDecode(FacesContext context, UIComponent component) {
		
		UISortableControl control = (UISortableControl) component;
		String clientId = control.getClientId(context);
		ExternalContext externalContext = context.getExternalContext();
		Map<String, String> parameterMap = externalContext.getRequestParameterMap();
		String string = parameterMap.get(clientId);
		if (string != null) {
			UIDataTable table = control.getTable();
			
			new SortEvent2(table, control.getSortExpression());
			
			AjaxContext ajaxContext = AjaxContext.getCurrentInstance();
			
			//TODO: AjaxEvent?
			if (ajaxContext.isAjaxRequest()) {
				ajaxContext.addComponentToAjaxRender(table);
			}
		}
		
		
	}
	
	@Override
	public boolean getRendersChildren() {
		return false;
	}
	
	@Override
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		
		UISortableControl control = (UISortableControl) component;
		Mode mode = control.getMode();
		if (mode == null) {
			mode = SERVER;
		}
		RendererCommand rendererCommand = commands[mode.ordinal()];
		if (rendererCommand != null) {
			rendererCommand.encodeMarkup(writer, context, control);
		}
		
	}
}
