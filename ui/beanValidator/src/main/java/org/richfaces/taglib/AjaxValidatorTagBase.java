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

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.servlet.jsp.JspException;

import org.ajax4jsf.webapp.taglib.UIComponentTagBase;
import org.richfaces.component.UIBeanValidator;
import org.richfaces.renderkit.html.BeanValidatorRenderer;
import org.richfaces.validator.FacesBeanValidator;

/**
 * @author asmirnov
 * 
 */
public class AjaxValidatorTagBase extends UIComponentTagBase {

	/**
	 * Generate script for given event ( onclick, onenter ... )
	 */
	private String event = null;

	private ValueExpression summary = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentTagBase#getComponentType()
	 */
	@Override
	public String getComponentType() {
		return UIBeanValidator.COMPONENT_TYPE;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(ValueExpression summary) {
		this.summary = summary;
	}

	@Override
	public int doStartTag() throws JspException {
		// Locate our parent UIComponentTag
		UIComponentClassicTagBase tag = UIComponentClassicTagBase
				.getParentUIComponentClassicTagBase(pageContext);
		if (tag == null) {
			// PENDING i18n
			throw new JspException(
					"Not nested in a UIComponentTag Error for tag with handler class:"
							+ this.getClass().getName());
		}
		UIComponent component = tag.getComponentInstance();
		if (!(component instanceof EditableValueHolder)) {
			// PENDING i18n
			throw new JspException(
					"Not nested in a UIInput  component. Error for tag with handler class:"
							+ this.getClass().getName());

		}
		// Nothing to do unless this tag created a component
		if (tag.getCreated()) {
			// New created component, add validator.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesBeanValidator validator = (FacesBeanValidator) facesContext
					.getApplication().createValidator(
							FacesBeanValidator.BEAN_VALIDATOR_TYPE);
			if (null != summary) {
					validator.setSummary(summary);
			}
			((EditableValueHolder) component).addValidator(validator);

		}
		return super.doStartTag();
	}

	@Override
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		setStringProperty(component, "event", event);
	}

	@Override
	public void release() {
		super.release();
		event = null;
		summary = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentTagBase#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return BeanValidatorRenderer.RENDERER_TYPE;
	}

	@Override
	protected String getFacetName() {
		return UIBeanValidator.BEAN_VALIDATOR_FACET
				+ (null == event ? "" : event);
	}
}
