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
package org.richfaces.component;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ajax4jsf.util.ELUtils;

public abstract class UIPickList extends UISelectMany {
	
	/**
	 * Get base clientId of this component ( without iteration part )
	 * 
	 * @param faces
	 * @return
	 */
	public String getBaseClientId(FacesContext faces) {
		// Return any previously cached client identifier
		if (_baseClientId == null) {

			// Search for an ancestor that is a naming container
			UIComponent ancestorContainer = this;
			StringBuffer parentIds = new StringBuffer();
			while (null != (ancestorContainer = ancestorContainer.getParent())) {
				if (ancestorContainer instanceof NamingContainer) {
					parentIds.append(ancestorContainer.getClientId(faces))
							.append(NamingContainer.SEPARATOR_CHAR);
					break;
				}
			}
			String id = getId();
			if (null != id) {
				_baseClientId = parentIds.append(id).toString();
			} else {
				_baseClientId = parentIds.append(
						faces.getViewRoot().createUniqueId()).toString();
			}
		}
		return (_baseClientId);

	}
	
	@Override
	public Converter getConverter() {
		
		Converter converter = super.getConverter();
		if(converter == null) {
			converter = getConverterForValue(FacesContext.getCurrentInstance());
		}
		
		return converter;
	}
	
	private Converter getConverterForType(FacesContext context, Class <?> type) {
		
		if (!Object.class.equals(type) && type != null) {
			Application application = context.getApplication();
			return application.createConverter(type);
		}

		return null;
	}
	
	public Converter getConverterForValue(FacesContext context) {
		Converter converter = null;
		ValueExpression expression = this.getValueExpression("value");

		if (expression != null) {
			Class<?> containerClass = ELUtils.getContainerClass(context, expression);

			converter = getConverterForType(context, containerClass);
		}
		
		return converter;
	}
	
	private String _baseClientId = null;
	
	public abstract String getControlClass();
	public abstract void setControlClass(String controlClass);
	
	public abstract String getListClass();
	public abstract void setListClass(String listClass);
	
	public abstract String getMoveControlsVerticalAlign();
	public abstract void setMoveControlsVerticalAlign(String moveControlsVerticalAlign);
	
	public abstract int getSize();
	public abstract void setSize(int size);
	
	public abstract boolean isDisabled();
	public abstract void setDisabled(boolean disabled);
	
	public abstract boolean isMoveControlsVisible();
	public abstract void setMoveControlsVisible(boolean visible);
	
	public abstract boolean isFastMoveControlsVisible();
	public abstract void setFastMoveControlsVisible(boolean visible);

	public abstract boolean isCopyAllVisible();
	public abstract void setCopyAllVisible(boolean visible);

	public abstract boolean isCopyVisible();
	public abstract void setCopyVisible(boolean visible);

	public abstract boolean isRemoveVisible();
	public abstract void setRemoveVisible(boolean visible);

	public abstract boolean isRemoveAllVisible();
	public abstract void setRemoveAllVisible(boolean visible);

}
