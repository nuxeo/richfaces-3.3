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

package org.richfaces.taglib;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.webapp.taglib.HtmlComponentTagBase;
import org.richfaces.component.UISimpleTogglePanel;

public abstract class SimpleTogglePanelTagBase extends HtmlComponentTagBase {

	private boolean _openedSet = false;
	private ValueExpression _value = null;

	private void logValueDeprecation(ValueExpression value) {
		FacesContext facesContext = getFacesContext();
		facesContext.getExternalContext().log("opened attribute has been already set for component with id: " + this.getId() + 
				"[" + value.getExpressionString() + "]. value attribute is deprecated and thus has been dropped!");
	}
	
	public void setOpened(ValueExpression opened) {
		if (!_openedSet && _value != null) {
			logValueDeprecation(_value);
		}
		
		_value = opened;
		_openedSet = true;
	}
	
	public void setValue(ValueExpression value) {
		if (!_openedSet) {
			_value = value;
		} else {
			logValueDeprecation(_value);
		}
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		if (_value != null) {
			if (_value.isLiteralText()) {
				try {
					UISimpleTogglePanel panel = (UISimpleTogglePanel) component;
					panel.setOpened(Boolean.valueOf(_value.getExpressionString()));
				} catch (ELException e) {
					throw new FacesException(e);
				}
			} else {
				component.setValueExpression("value", _value);
			}
		}
	}

	public void release() {
		super.release();
		_openedSet = false;
		_value = null;
	}
	
}
