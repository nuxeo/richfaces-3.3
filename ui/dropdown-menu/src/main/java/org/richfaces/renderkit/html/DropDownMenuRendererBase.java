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

package org.richfaces.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIDropDownMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.renderkit.ScriptOptions;


public class DropDownMenuRendererBase extends AbstractMenuRenderer {

	protected Class<UIDropDownMenu> getComponentClass() {
		return UIDropDownMenu.class;
	}

	@Override
	protected String getLayerScript(FacesContext context, UIComponent component) {
		StringBuffer buffer = new StringBuffer();
		JSFunction function = new JSFunction("new RichFaces.Menu.Layer");
		function.addParameter(component.getClientId(context)+"_menu");
		function.addParameter(component.getAttributes().get("showDelay"));
        
		if (component instanceof UIDropDownMenu) {
    		function.addParameter(component.getAttributes().get("hideDelay"));
    		Object selectedClass = component.getAttributes().get("selectedLabelClass");
    		if (null != selectedClass && !"".equals(selectedClass)) {
    			function.addParameter(selectedClass);
    		}
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
			  subMenuOptions.addOption("highlightParent", Boolean.TRUE);
			  function.addParameter(subMenuOptions);
			  function.appendScript(buffer);

		} else {
			  buffer.append(".");
			  function = new JSFunction("asDropDown");
			  function.addParameter(component.getClientId(context));
			  function.addParameter(component.getClientId(context) + "_span");
  			  String evt = (String) component.getAttributes().get("event");
			  if(evt == null || evt.trim().length() == 0){
					evt = "onmouseover";
			  }
			  function.addParameter(evt);
			  function.addParameter("onmouseout");
			  ScriptOptions menuOptions = new ScriptOptions(component);

			  menuOptions.addOption("direction");
			  menuOptions.addOption("jointPoint");
			  menuOptions.addOption("verticalOffset");


			  menuOptions.addOption("horizontalOffset");
			  menuOptions.addEventHandler("oncollapse");
			  menuOptions.addEventHandler("onexpand");
			  menuOptions.addEventHandler("onitemselect");
			  menuOptions.addEventHandler("ongroupactivate");
			  menuOptions.addOption("disabled");
			  function.addParameter(menuOptions);
			  function.appendScript(buffer);

		}
		
		return buffer.toString();
	}

	public void encodeChildren(FacesContext context, UIComponent component) 
			throws IOException {
		if (!((org.richfaces.component.UIDropDownMenu)component).isDisabled())
			super.encodeChildren(context, component);
	}

	protected void processLayerStyles(FacesContext context, UIComponent layer, ResponseWriter writer) throws IOException {
		writer.writeAttribute(HTML.class_ATTRIBUTE, "rich-menu-list-border", null);
		writer.writeAttribute(HTML.style_ATTRIBUTE, "display: none; z-index: 2;", null);
	}

}
