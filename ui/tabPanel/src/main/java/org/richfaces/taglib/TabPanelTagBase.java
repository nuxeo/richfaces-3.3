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
import org.richfaces.component.UITabPanel;

/**
 * 
 * <br /><br />
 * 
 * Created 23.08.2007
 * @author Nick Belaevski
 * @since 3.1
 */

public abstract class TabPanelTagBase extends HtmlComponentTagBase {

	private boolean _selectedTabSet = false;
	private ValueExpression _value = null;

	private void logValueDeprecation(ValueExpression value) {
		FacesContext facesContext = getFacesContext();
		facesContext.getExternalContext().log("selectedTab attribute has been already set for component with id: " + this.getId() + 
				"[" + value.getExpressionString() + "]. value attribute is deprecated and thus has been dropped!");
	}
	
	public void setSelectedTab(ValueExpression tab) {
		if (!_selectedTabSet && _value != null) {
			logValueDeprecation(tab);
		}
		
		_value = tab;
		_selectedTabSet = true;
	}
	
	public void setValue(ValueExpression value) {
		if (!_selectedTabSet) {
			_value = value;
		} else {
			logValueDeprecation(_value);
		}
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		if (_value != null) {
			if (_value.isLiteralText()) {
				UITabPanel tabPanel = (UITabPanel) component;
				try {
					tabPanel.setSelectedTab(_value.getExpressionString());
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
		_selectedTabSet = false;
		_value = null;
	}
}
