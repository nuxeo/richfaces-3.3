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
package org.richfaces.taglib;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.webapp.taglib.HtmlComponentTagBase;
import org.richfaces.component.UIPanelMenuGroup;

/**
 * @author Anton Belevich
 *
 */
public abstract class PanelMenuGroupTagBase extends HtmlComponentTagBase{
	
	private boolean _setExpandedSet = false;
	private ValueExpression _value = null;
	
	private void logValueDeprecation(ValueExpression value) {
		FacesContext facesContext = getFacesContext();
		facesContext.getExternalContext().log("expanded attribute has been already set for component with id: " + this.getId() + 
				"[" + value.getExpressionString() + "]. value attribute is deprecated and thus has been dropped!");
	}
	
	
	public void setExpanded(ValueExpression value){
		
		if (!_setExpandedSet && _value != null) {
			logValueDeprecation(value);
		}
		_value = value;
		_setExpandedSet = true;
	}
	
	public void setValue(ValueExpression value) {
		if (!_setExpandedSet) {
			_value = value;
		} else {
			logValueDeprecation(value);	
		}
	}
	
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		
		UIPanelMenuGroup panelMenu = (UIPanelMenuGroup) component;
		
		if (_value != null) {
			if (_value.isLiteralText()) {
				try {
					panelMenu.setExpanded(Boolean.parseBoolean(_value.getExpressionString()));
				} catch (ELException e) {
					throw new FacesException(e);
				}
			} else {
				component.setValueExpression("value", _value);
			}
		}else{
			panelMenu.setExpanded(false);	
		}	
	}
	
	
	public void release() {
		super.release();
		_setExpandedSet = false;
		_value = null;
	}
}
