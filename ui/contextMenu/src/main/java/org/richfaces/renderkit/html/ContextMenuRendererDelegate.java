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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.InternetResource;
import org.richfaces.component.UIContextMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.renderkit.ScriptOptions;

/**
 * @author Maksim Kaszynski
 *
 */
public class ContextMenuRendererDelegate extends AbstractMenuRenderer {

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.html.AbstractMenuRenderer#getLayerScript(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected String getLayerScript(FacesContext context, UIComponent component) {
		StringBuffer buffer = new StringBuffer();
		JSFunction function = new JSFunction("new RichFaces.Menu.Layer");
		function.addParameter(component.getClientId(context)+"_menu");
		function.addParameter(component.getAttributes().get("showDelay"));
        
		if (component instanceof UIContextMenu) {
    		function.addParameter(component.getAttributes().get("hideDelay"));
        } else {
        	function.addParameter(new Integer(300));
        }
        
		function.appendScript(buffer);
		
		if (component instanceof UIMenuGroup) {
			  buffer.append(".");
			  function = new JSFunction("asSubMenu");
			  function.addParameter(component.getParent().getClientId(context)+"_menu");
			  function.addParameter(component.getClientId(context));
	 		  String evt = (String) component.getAttributes().get("event");
			  if(evt == null || evt.trim().length() == 0){
				  evt = "onmouseover";
			  }
			  function.addParameter(evt);
			  ScriptOptions subMenuOptions = new ScriptOptions(component);
			  subMenuOptions.addEventHandler("onopen");
			  subMenuOptions.addEventHandler("onclose");
			  subMenuOptions.addOption("direction");
			  subMenuOptions.addOption("dummy", "dummy");
			  function.addParameter(subMenuOptions);
			  function.appendScript(buffer);

		} else {
			  buffer.append(".");
			  function = new JSFunction("asContextMenu");
/*			  function.addParameter(component.getParent().getClientId(context));
  			  String evt = (String) component.getAttributes().get("event");
			  if(evt == null || evt.trim().length() == 0){
					evt = "oncontextmenu";
			  }
			  function.addParameter(evt);
			  function.addParameter("onmouseout");
*/			  ScriptOptions menuOptions = new ScriptOptions(component);

			  menuOptions.addOption("direction");
			  menuOptions.addOption("jointPoint");
			  menuOptions.addOption("verticalOffset");


			  menuOptions.addOption("horizontalOffset");
			  menuOptions.addEventHandler("oncollapse");
			  menuOptions.addEventHandler("onexpand");
			  menuOptions.addEventHandler("onitemselect");
			  menuOptions.addEventHandler("ongroupactivate");
			  
			  menuOptions.addOption("dummy", "dummy");
			  function.addParameter(menuOptions);
			  function.appendScript(buffer);

		}
		
		return buffer.toString();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		return UIContextMenu.class;
	}


	protected InternetResource[] getStyles() {
		return super.getStyles();
	}

	protected void processLayerStyles(FacesContext context, UIComponent layer, ResponseWriter writer) throws IOException {
		Object style = layer.getAttributes().get(HTML.style_ATTRIBUTE);
		Object styleClass = layer.getAttributes().get(HTML.STYLE_CLASS_ATTR);
		
		if (null == style) {
			style = "";
		}
		if (null == styleClass) {
			styleClass = "";
		}

		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-menu-list-border " + styleClass, null);
		writer.writeAttribute(HTML.style_ATTRIBUTE, "display: none; z-index: 2; " + style, null);
	}
}
