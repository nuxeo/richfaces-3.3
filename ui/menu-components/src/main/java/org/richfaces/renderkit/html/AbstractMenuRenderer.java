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

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.UIMenuSeparator;
import org.richfaces.component.util.HtmlUtil;
import org.richfaces.renderkit.ScriptOptions;

/**
 * @author Maksim Kaszynski
 *
 */
public abstract class AbstractMenuRenderer extends HeaderResourcesRendererBase {

	private final InternetResource[] scripts = {
			new org.ajax4jsf.javascript.PrototypeScript(),
			new org.ajax4jsf.javascript.AjaxScript(),
			getResource("scripts/menu.js") };

	@Override
	protected InternetResource[] getScripts() {
		return scripts;
	}
	
	@Override
	protected InternetResource[] getStyles() {
		return super.getStyles();
	}

	public void encodeScript(FacesContext context, UIComponent component) throws IOException {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(getLayerScript(context, component));
		
		List children = component.getChildren();
		for(Iterator it = children.iterator();it.hasNext();) {
			buffer.append(getItemScript(context, (UIComponent) it.next()));
		}

		ResponseWriter out = context.getResponseWriter();
		String script = buffer.append(";").toString();
		out.write(script);
	}
	
	protected abstract String getLayerScript(FacesContext context, UIComponent layer);
	
	protected String getItemScript(FacesContext context, UIComponent kid) {
		String itemId = null;
		boolean closeOnClick = true;
		Integer flagGroup = null;
		boolean disabled = false;
		if (kid instanceof UIMenuItem) {
			UIMenuItem menuItem = (UIMenuItem) kid;
			itemId = kid.getClientId(context);
			disabled = menuItem.isDisabled();
			if (disabled) {
				closeOnClick = false;
			}
		} else if (kid instanceof UIMenuGroup) {
			UIMenuGroup menuGroup = (UIMenuGroup) kid;
			itemId = kid.getClientId(context);
			closeOnClick = false;
			if ((disabled = menuGroup.isDisabled())) {
				flagGroup = Integer.valueOf(2);
			} else {
				flagGroup = Integer.valueOf(1);
			}
		}
		if (itemId != null) {
			JSFunction function = new JSFunction(".addItem");
			function.addParameter(itemId);
			ScriptOptions options = new ScriptOptions(kid);

			options.addEventHandler("onmouseout");
			options.addEventHandler("onmouseover");
			
			if (closeOnClick) {
				options.addOption("closeOnClick", Boolean.TRUE);
			}
			options.addOption("flagGroup", flagGroup);
			
			options.addOption("styleClass");
			options.addOption("style");			
			options.addOption("itemClass");
			options.addOption("itemStyle");
			options.addOption("disabledItemClass");
			options.addOption("disabledItemStyle");
			options.addOption("selectItemClass");
			options.addOption("labelClass");
			options.addOption("selectedLabelClass");
			options.addOption("disabledLabelClass");
			
			options.addOption("selectClass");
			options.addOption("selectStyle");
			options.addOption("iconClass");
			
			if (disabled) {
				options.addOption("disabled", Boolean.TRUE);
			}
			
			options.addEventHandler("onselect");
			
			function.addParameter(options);
			return function.toScript();
		}
		return "";
	}
	
	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		List flatListOfNodes = new LinkedList();
		String width = (String) component.getAttributes().get("popupWidth");

		flatten(component.getChildren(), flatListOfNodes);
		processLayer(context, component, width);

		for (Iterator iter = flatListOfNodes.iterator(); iter.hasNext();) {
			UIMenuGroup node = (UIMenuGroup) iter.next();
			if (node.isRendered() && !node.isDisabled())
				processLayer(context, node, width);
		}
	}

	public void processLayer(FacesContext context, UIComponent layer, String width) throws IOException {
		String clientId = layer.getClientId(context);
		
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(HTML.DIV_ELEM, layer);
		writer.writeAttribute(HTML.id_ATTRIBUTE, clientId+"_menu", null);
		processLayerStyles(context, layer, writer);
		writer.startElement(HTML.DIV_ELEM, layer);
		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-menu-list-bg", null);
		encodeItems(context, layer);

		writer.startElement(HTML.DIV_ELEM, layer);
		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-menu-list-strut", null);
		writer.startElement(HTML.DIV_ELEM, layer);
		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-menu-list-strut", null);
		writer.writeAttribute(HTML.style_ATTRIBUTE, width!=null && width.length() > 0 ? "width: " + HtmlUtil.qualifySize(width) : "", null);
		writer.write("&#160;");
		writer.endElement(HTML.DIV_ELEM);				
		writer.endElement(HTML.DIV_ELEM);

		writer.endElement(HTML.DIV_ELEM);
		writer.endElement(HTML.DIV_ELEM);

		writer.startElement(HTML.SCRIPT_ELEM, layer);
		writer.writeAttribute(HTML.id_ATTRIBUTE, clientId+"_menu_script", null);
		writer.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
		encodeScript(context, layer);
		writer.endElement(HTML.SCRIPT_ELEM);

		AjaxContext ajaxContext = AjaxContext.getCurrentInstance();
		Set renderedAreas = ajaxContext.getAjaxRenderedAreas();
		renderedAreas.add(clientId + "_menu_script");
	}

	public void encodeItems(FacesContext context, UIComponent component) throws IOException {
		List kids = component.getChildren();
		Iterator it = kids.iterator();
		while (it.hasNext()) {
			UIComponent kid = (UIComponent)it.next();
			if (kid instanceof UIMenuGroup || kid instanceof UIMenuItem || kid instanceof UIMenuSeparator) {
				renderChild(context, kid);
			}
		}
	}

	private void flatten(List kids, List flatList){
		if(kids != null){
			for (Iterator iter = kids.iterator(); iter.hasNext();) {
				UIComponent kid = (UIComponent) iter.next();
				if (kid instanceof UIMenuGroup) {
					UIMenuGroup node = (UIMenuGroup) kid;
					flatList.add(node);
					flatten(node.getChildren(), flatList);
				}
			}
		}
	}
	
	protected abstract void processLayerStyles(FacesContext context, UIComponent layer, ResponseWriter writer) throws IOException;

}
