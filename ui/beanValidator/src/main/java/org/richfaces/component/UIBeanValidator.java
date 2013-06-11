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

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.UIMessage;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.component.AjaxContainer;
import org.ajax4jsf.component.AjaxSupport;
import org.ajax4jsf.component.EventValueExpression;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.event.AjaxListener;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.webapp.taglib.MethodExpressionAjaxListener;
import org.richfaces.event.ValidationEvent;

/**
 * JSF component class
 * 
 */
public abstract class UIBeanValidator extends UIComponentBase implements
		AjaxComponent, AjaxSupport, AjaxContainer {

	public static final String COMPONENT_TYPE = "org.richfaces.BeanValidator";

	public static final String COMPONENT_FAMILY = "org.richfaces.BeanValidator";

	public static final String BEAN_VALIDATOR_FACET = "org.richfaces.validator.";

	@Override
	public void setParent(UIComponent parent) {
		super.setParent(parent);

		if (null != parent && parent instanceof EditableValueHolder) {
			setParentProperties(parent);
		}
	}

	/**
	 * @param parent
	 * @throws FacesException
	 */
	public void setParentProperties(UIComponent parent) throws FacesException {
		if (!(parent instanceof EditableValueHolder)) {
			throw new FacesException(
					"Parent component must be an EditableValueHolder");
		}

		if (null != getEvent()) {
			ValueExpression binding = new EventValueExpression(this);
			parent.setValueExpression(getEvent(), binding);
		}
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		if (event.getComponent() == this) {
			FacesContext context = getFacesContext();
			if (event instanceof ValidationEvent) {
				// ByPass UpdateModelValue
				context.renderResponse();
			} else if (event instanceof AjaxEvent) {
				// TODO - find all UIMessages components for a parent UIInput.
				// for an ajaxSingle component, re-render only target message.
				AjaxContext ajaxContext = AjaxContext
						.getCurrentInstance(context);
				ajaxContext.setSubmittedRegionClientId(this
						.getClientId(context));
			}
		}
	}

	@Override
	public void queueEvent(FacesEvent event) {
		if (event instanceof ValidationEvent && event.getComponent() == this) {
			UIComponent parent = getParent();
			if (parent instanceof UIInput) {
				UIInput input = (UIInput) parent;
				if (input.isImmediate()) {
					event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
				} else {
					event.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
				}
			}
		}
		super.queueEvent(event);
	}

	public String getEventString() {
		StringBuffer buildOnEvent = new StringBuffer();
		String onsubmit = getOnsubmit();
		// Insert script to call before submit ajax request.
		if (null != onsubmit) {
			buildOnEvent.append(onsubmit).append(";");
		}
		buildOnEvent.append(AjaxRendererUtils.buildOnEvent(this,
				getFacesContext(), getEvent()));
		String script = buildOnEvent.toString();
		return script;
	}

	public UIComponent getSingleComponent() {
		return getParent();
	}

	public void encodeAjax(FacesContext context) throws IOException {
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		Set<String> renderedAreas = ajaxContext.getAjaxRenderedAreas();
		for (UIComponent message : getMessages(context)) {
			if (message.isRendered()) {
				message.encodeAll(context);
				renderedAreas.add(message.getClientId(context));
			}
		}
		// Write information about encoded areas after submission.
		AjaxRendererUtils.encodeAreas(context, this);
	}

	public Set<UIComponent> getMessages(FacesContext context) {
		Set<UIComponent> messages = new HashSet<UIComponent>();
		findMessages(this.getParent(), this, messages,false);
		findRichMessages(context, context.getViewRoot(), messages);
		return messages;
	}

	
	/**
	 * Find all instances of the {@link UIRichMessages} and update list of the rendered messages.
	 * @param context
	 * @param component
	 * @param messages
	 */
	protected void findRichMessages(FacesContext context,UIComponent component, Set<UIComponent> messages) {
		Iterator<UIComponent> facetsAndChildren = component.getFacetsAndChildren();
		while (facetsAndChildren.hasNext()) {
			UIComponent child = (UIComponent) facetsAndChildren.next();
			if (child instanceof UIRichMessages) {
				UIRichMessages richMessage = (UIRichMessages) child;
				if(null == richMessage.getFor()){
					richMessage.updateMessages(context, this.getParent().getClientId(context));
					messages.add(richMessage);
				}
			} else {
				findRichMessages(context, child, messages);
			}
		}
	}

	/**
	 * Recursive search messages for the parent component.
	 * @param parent 
	 * @param component
	 * @param messages
	 * @return
	 */
	protected boolean findMessages(UIComponent parent, UIComponent component,
			Set<UIComponent> messages,boolean found) {
		Iterator<UIComponent> facetsAndChildren = parent.getFacetsAndChildren();
		while (facetsAndChildren.hasNext()) {
			UIComponent child = (UIComponent) facetsAndChildren.next();
			if (child != component) {
				if (child instanceof UIMessage || child instanceof UIMessages) {
					UIComponent message = (UIComponent) child;
					Object targetId = message.getAttributes().get("for");
					if (null != targetId
							&& targetId.equals(getParent().getId())) {
						messages.add(message);
						found = true;
					}
				} else {
					found |= findMessages(child, null, messages,found);
				}
			}
		}
		if(!(found && parent instanceof NamingContainer) && component != null){
			UIComponent newParent = parent.getParent();
			if(null != newParent){
				found = findMessages(newParent, parent, messages,found);
			}
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ajax4jsf.event.AjaxSource#addAjaxListener(org.ajax4jsf.event.AjaxListener
	 * )
	 */
	public void addAjaxListener(AjaxListener listener) {
		addFacesListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.event.AjaxSource#getAjaxListeners()
	 */
	public AjaxListener[] getAjaxListeners() {
		return (AjaxListener[]) getFacesListeners(AjaxListener.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.ajax4jsf.event.AjaxSource#removeAjaxListener(org.ajax4jsf.event.
	 * AjaxListener)
	 */
	public void removeAjaxListener(AjaxListener listener) {
		removeFacesListener(listener);
	}

	public void setAjaxListener(MethodExpression listener) {
		AjaxListener[] ajaxListeners = getAjaxListeners();
		for (int i = 0; i < ajaxListeners.length; i++) {
			AjaxListener ajaxListener = ajaxListeners[i];
			if (ajaxListener.getClass().equals(
					MethodExpressionAjaxListener.class)) {
				MethodExpressionAjaxListener expressionListener = (MethodExpressionAjaxListener) ajaxListener;
				if (expressionListener.getExpression() != listener) {
					removeAjaxListener(ajaxListener);

					if (listener == null) {

						return;
					} else {

						break;
					}
				}
			}
		}

		addAjaxListener(new MethodExpressionAjaxListener(listener));
	}

	public MethodExpression getAjaxListener() {
		AjaxListener[] ajaxListeners = getAjaxListeners();
		for (int i = 0; i < ajaxListeners.length; i++) {
			AjaxListener ajaxListener = ajaxListeners[i];
			if (ajaxListener.getClass().equals(
					MethodExpressionAjaxListener.class)) {
				MethodExpressionAjaxListener expressionListener = (MethodExpressionAjaxListener) ajaxListener;
				return expressionListener.getExpression();
			}
		}

		return null;
	}
}
