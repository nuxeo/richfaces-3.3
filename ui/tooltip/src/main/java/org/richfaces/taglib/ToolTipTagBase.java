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
import org.richfaces.component.UIToolTip;

/**
 * Created 23.08.2007
 * @author Alexej Kushunin
 * @since 3.2
 */
public abstract class ToolTipTagBase extends HtmlComponentTagBase {

	private boolean _showEventSet = false;
	private ValueExpression _event = null;

	private void logValueDeprecation(ValueExpression event) {
		FacesContext facesContext = getFacesContext();
		facesContext.getExternalContext().log("showEvent attribute has been already set for component with id: " + this.getId() + 
				"[" + event.getExpressionString() + "]. event attribute is deprecated and thus has been dropped!");
	}
	
	public void setShowEvent(ValueExpression event) {
		if (!_showEventSet && _event != null) {
			logValueDeprecation(event);
		}
		
		_event = event;
		_showEventSet = true;
	}
	
	public void setEvent(ValueExpression event) {
		if (!_showEventSet) {
			_event = event;
		} else {
			logValueDeprecation(_event);
		}
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		if (_event != null) {
			if (_event.isLiteralText()) {
				UIToolTip toolTip = (UIToolTip) component;
				try {
					toolTip.setShowEvent(_event.getExpressionString());
				} catch (ELException e) {
					throw new FacesException(e);
				}
			} else {
				component.setValueExpression("showEvent", _event);
			}
		}
	}
	
	public void release() {
		super.release();
		_showEventSet = false;
		_event = null;
	}

}
