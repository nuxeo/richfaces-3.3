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

package org.ajax4jsf.component;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.Messages;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

/**
 * Inner class for build event string for parent component.
 * 
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:38 $ Disadvantages -
 *          not rebuild event string setted as EL expression. TODO - save
 *          expressions for build event string at render phase.
 */
public class EventValueBinding extends ValueBinding implements StateHolder {

    /**
         * 
         */
    private static final long serialVersionUID = -6583167387542332290L;

    private String _componentId;

    /**
         * current update component. transient since saved state as component.
         */
    transient private AjaxSupport _component = null;

    /**
         * Default constructor for restoreState.
         */
    public EventValueBinding() {

    }

    /**
         * Constructor for build from AjaxComponent.
         * 
         * @param update
         */
    public EventValueBinding(AjaxSupport update) {
	_component = update;
	// _componentId = string;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.el.ValueBinding#getType(javax.faces.context.FacesContext)
         */
    public Class getType(FacesContext facesContext) throws EvaluationException,
	    PropertyNotFoundException {

	return String.class;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.el.ValueBinding#getValue(javax.faces.context.FacesContext)
         */
    public Object getValue(FacesContext facesContext)
	    throws EvaluationException, PropertyNotFoundException {
	if (((UIComponent) getComponent(facesContext)).isRendered()) {
	    return getComponent(facesContext).getEventString();

	} else {
	    return null;
	}
    }

    private AjaxSupport getComponent(FacesContext facesContext)
	    throws EvaluationException {

	if (_component == null) {
	    UIComponent uiComponent = facesContext.getViewRoot().findComponent(
		    _componentId);
	    if (null != uiComponent && uiComponent instanceof AjaxSupport) {
		_component = (AjaxSupport) uiComponent;
	    } else {
		throw new EvaluationException(Messages.getMessage(
			Messages.COMPONENT_NOT_FOUND, _componentId));
	    }

	}

	return _component;
    }

    /**
         * @param component
         *                the component to set
         */
    public void setComponent(AjaxSupport component) {
	_component = component;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.el.ValueBinding#isReadOnly(javax.faces.context.FacesContext)
         */
    public boolean isReadOnly(FacesContext facesContext)
	    throws EvaluationException, PropertyNotFoundException {
	// TODO Auto-generated method stub
	return true;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.el.ValueBinding#setValue(javax.faces.context.FacesContext,
         *      java.lang.Object)
         */
    public void setValue(FacesContext facesContext, Object value)
	    throws EvaluationException, PropertyNotFoundException {
	throw new EvaluationException(Messages
		.getMessage(Messages.EVENT_IS_READ_ONLY));

    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
         */
    public Object saveState(FacesContext context) {
	if (null == _component) {
	    return _componentId;
	} else {
	    return AjaxRendererUtils
		    .getAbsoluteId((UIComponent) getComponent(context));
	}
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
         *      java.lang.Object)
         */
    public void restoreState(FacesContext context, Object state) {
	// TODO Auto-generated method stub
	_componentId = (String) state;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.component.StateHolder#isTransient()
         */
    public boolean isTransient() {
	// TODO Auto-generated method stub
	return false;
    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.component.StateHolder#setTransient(boolean)
         */
    public void setTransient(boolean newTransientValue) {
	// TODO Auto-generated method stub

    }

    /*
         * (non-Javadoc)
         * 
         * @see javax.faces.el.ValueBinding#getExpressionString()
         */
    public String getExpressionString() {
	// FacesContext context = FacesContext.getCurrentInstance();
	// UIComponent component = (UIComponent) getComponent(context);
	// return "#{ajaxSupport["+component.getClientId(context)+"]}";
	return null;
    }

}