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

package org.ajax4jsf.gwt.jsf;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource2;
import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;

/**
 * Universal component for Google Web toolkit widgets. Can be used as input
 * component, action component and both.
 * 
 * @author shura
 * 
 */
public class UIWidget extends UIInput implements GwtComponent, GwtSource,
		ActionSource2 {

	public static final String COMPONENT_TYPE = "org.ajax4jsf.gwt.Widget";

	private String _module;

	private String _widget;

	private String _bundleBase;

	private String _bundleName;

	// Html attributes
	private String _style;

	private String _styleClass;

	private String _layout;

	private String _actionParam = NamingContainer.SEPARATOR_CHAR + "action";

	private String _inputParam = NamingContainer.SEPARATOR_CHAR + "value";

	private UICommand _commandBrige;

	public UIWidget() {
		_commandBrige = new UICommand();
		_commandBrige.setParent(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return "org.ajax4jsf.gwt.Widget";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtComponent#getModuleName()
	 */
	public String getModuleName() {
		// TODO Auto-generated method stub
		return _module;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtComponent#getWidgetName()
	 */
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return _widget;
	}

	/**
	 * @return the actionParam
	 */
	public String getActionParam() {
		return _actionParam;
	}

	/**
	 * @param actionParam
	 *            the actionParam to set
	 */
	public void setActionParam(String actionParam) {
		_actionParam = actionParam;
	}

	/**
	 * @return the bundleBasename
	 */
	public String getBundleBase() {
		if (this._bundleBase != null) {
			return this._bundleBase;
		}

		ValueExpression ve = getValueExpression("bundleBase");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (this._bundleBase);
		}
	}

	/**
	 * @param bundleBasename
	 *            the bundleBasename to set
	 */
	public void setBundleBase(String bundleBasename) {
		_bundleBase = bundleBasename;
	}

	/**
	 * @return the bundleName
	 */
	public String getBundleName() {
		if (this._bundleName != null) {
			return this._bundleName;
		}

		ValueExpression ve = getValueExpression("bundleName");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (this._bundleName);
		}
	}

	/**
	 * @param bundleName
	 *            the bundleName to set
	 */
	public void setBundleName(String bundleName) {
		_bundleName = bundleName;
	}

	/**
	 * @return the inputParam
	 */
	public String getInputParam() {
		return _inputParam;
	}

	/**
	 * @param inputParam
	 *            the inputParam to set
	 */
	public void setInputParam(String inputParam) {
		_inputParam = inputParam;
	}

	/**
	 * @return the layout
	 */
	public String getLayout() {
		if (this._layout != null) {
			return this._layout;
		}

		ValueExpression ve = getValueExpression("layout");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (this._layout);
		}
	}

	/**
	 * @param layout
	 *            the layout to set
	 */
	public void setLayout(String layout) {
		_layout = layout;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		if (this._style != null) {
			return this._style;
		}

		ValueExpression ve = getValueExpression("style");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (this._style);
		}
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		_style = style;
	}

	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		if (this._styleClass != null) {
			return this._styleClass;
		}

		ValueExpression ve = getValueExpression("styleClass");
		if (ve != null) {
			try {
				return ((String) ve.getValue(getFacesContext().getELContext()));
			} catch (ELException e) {
				throw new FacesException(e);
			}
		} else {
			return (this._styleClass);
		}
	}

	/**
	 * @param styleClass
	 *            the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		_styleClass = styleClass;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return _module;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModule(String moduleName) {
		_module = moduleName;
	}

	/**
	 * @return the widget
	 */
	public String getWidget() {
		return _widget;
	}

	/**
	 * @param widgetName
	 *            the widgetName to set
	 */
	public void setWidget(String widgetName) {
		_widget = widgetName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtComponent#getWidgetParameters()
	 */
	public Map getWidgetParameters() {
		Map params = new HashMap();
		String basename = getBundleBase();
		String bundleName = getBundleName();
		if (null != basename && null != bundleName) {
			try {
				ResourceBundle bundle = ResourceBundle
						.getBundle(
								basename,
								FacesContext.getCurrentInstance().getViewRoot()
										.getLocale());
				HashMap messages = new HashMap();
				for (Enumeration keys = bundle.getKeys(); keys.hasMoreElements();) {
					String key = (String) keys.nextElement();
					messages.put(key, bundle.getString(key));
				}
				params.put(bundleName, messages);
			} catch (Exception e) {
				// message bundle not found
			}

		}
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof UIParameter) {
				UIParameter param = (UIParameter) element;
				params.put(param.getName(), param.getValue());
			}
			
		}
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		WidgetState componentState = (WidgetState) state;
		super.restoreState(context, componentState.parentState);
		_module = componentState.moduleName;
		_widget = componentState.widgetName;
		_bundleBase = componentState.bundleBasename;
		_bundleName = componentState.bundleName;
		_style = componentState.style;
		_styleClass = componentState.styleClass;
		_layout = componentState.layout;
		_commandBrige.restoreState(context, componentState.commandState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		WidgetState state = new WidgetState();
		state.parentState = super.saveState(context);
		state.commandState = _commandBrige.saveState(context);
		state.moduleName = _module;
		state.widgetName = _widget;
		state.bundleBasename = _bundleBase;
		state.bundleName = _bundleName;
		state.style = _style;
		state.styleClass = _styleClass;
		state.layout = _layout;
		return state;
	}

	/**
	 * Class for keep component state , realised by "snapshot" pattern.
	 * 
	 * @author shura
	 * 
	 */
	private static class WidgetState implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8689558840635652079L;

		private String moduleName;

		private String widgetName;

		private String bundleBasename;

		private String bundleName;

		// Html attributes
		private String style;

		private String styleClass;

		private String layout;

		private Object commandState;

		private Object parentState;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#broadcast(javax.faces.event.FacesEvent)
	 */
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		if (event instanceof ActionEvent) {
			_commandBrige.broadcast(event);
		} else {
			super.broadcast(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#queueEvent(javax.faces.event.FacesEvent)
	 */
	public void queueEvent(FacesEvent event) {
		if (event instanceof ActionEvent) {
			_commandBrige.queueEvent(event);
		} else {
			super.queueEvent(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtSource#addGwtListener(org.ajax4jsf.gwt.jsf.GwtListener)
	 */
	public void addGwtListener(GwtListener listener) {
		addFacesListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtSource#getGwtListeners()
	 */
	public GwtListener[] getGwtListeners() {
		return (GwtListener[]) getFacesListeners(GwtListener.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.gwt.jsf.GwtSource#removeGwtListener(org.ajax4jsf.gwt.jsf.GwtListener)
	 */
	public void removeGwtListener(GwtListener listener) {
		removeFacesListener(listener);
	}

	/**
	 * @return
	 */
	public MethodExpression getActionExpression() {
		return _commandBrige.getActionExpression();
	}

	/**
	 * @param action
	 */
	public void setActionExpression(MethodExpression action) {
		_commandBrige.setActionExpression(action);

	}

	public void addActionListener(ActionListener listener) {
		_commandBrige.addActionListener(listener);
	}

	public MethodBinding getAction() {
		return _commandBrige.getAction();
	}

	public MethodBinding getActionListener() {
		return _commandBrige.getActionListener();
	}

	public ActionListener[] getActionListeners() {
		// TODO Auto-generated method stub
		return _commandBrige.getActionListeners();
	}

	public void removeActionListener(ActionListener listener) {
		_commandBrige.removeActionListener(listener);

	}

	public void setAction(MethodBinding action) {
		_commandBrige.setAction(action);
	}

	public void setActionListener(MethodBinding actionListener) {
		_commandBrige.setActionListener(actionListener);
	}

	/**
	 * @return
	 * @see javax.faces.component.UICommand#isImmediate()
	 */
	public boolean isImmediate() {
		return _commandBrige.isImmediate();
	}

	/**
	 * @param immediate
	 * @see javax.faces.component.UICommand#setImmediate(boolean)
	 */
	public void setImmediate(boolean immediate) {
		_commandBrige.setImmediate(immediate);
	}

}
