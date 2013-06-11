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

/*
 * UIProgressBar.java		Date created: 19.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.component;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.component.UIPoll;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.AjaxContextImpl;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSLiteral;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.renderkit.AbstractProgressBarRenderer;

/**
 * Class provides base component class for progress bar
 * 
 * @author "Andrey Markavtsov"
 * 
 */
// TODO add @since declaration
public abstract class UIProgressBar extends UIPoll {

	/** Logger */
	private final Log logger = LogFactory.getLog(UIProgressBar.class);

	/** Component type */
	public static final String COMPONENT_TYPE = "org.richfaces.ProgressBar";

	/** Component family */
	public static final String COMPONENT_FAMILY = "org.richfaces.ProgressBar";

	/** Request parameter name containing component state to render */
	public static final String FORCE_PERCENT_PARAM = "forcePercent";

	/** Percent param name */
	private static final String PERCENT_PARAM = "percent";

	/** Max value attribute name */
	private static final String _maxValue = "maxValue";

	/** Min value attribute name */
	private static final String _minValue = "minValue";

	/** Enabled attribute name */
	private static final String _enabled = "enabled";

	/** Enabled attribute name */
	private static final String _interval = "interval";

	/** Complete class attribute name */
	private static final String _completeClass = "completeClass";

	/** Remain class attribute name */
	private static final String _remainClass = "remainClass";

	/** Style class attribute name */
	private static final String _styleClass = "styleClass";

	/** Markup data key */
	private static final String _markup = "markup";

	/** Context key */
	private static final String _context = "context";

	/**
	 * Method performs broadcasting of jsf events to progress bar component
	 * 
	 * @param event -
	 *            Faces Event instance
	 */
	public void broadcast(FacesEvent event) throws AbortProcessingException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		AjaxContext ajaxContext = AjaxContextImpl
				.getCurrentInstance(facesContext);
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		String clientId = this.getClientId(facesContext);

		if (!params.containsKey(clientId)) {
			return;
		}

		if (event instanceof ActionEvent) {
			ActionListener listeners[] = getActionListeners();
			for (ActionListener l : listeners) {
				l.processAction((ActionEvent) event);
			}
		} else if (event instanceof AjaxEvent) {

			if (!params.containsKey(FORCE_PERCENT_PARAM)
					&& params.containsKey(PERCENT_PARAM)) {
				Number value = getNumber(this.getAttributes().get(
						RendererUtils.HTML.value_ATTRIBUTE));
				ajaxContext.removeRenderedArea(clientId);
				ajaxContext
						.setResponseData(getResponseData(value, facesContext));

				AjaxRendererUtils.addRegionsFromComponent(this, facesContext);
			} else if (params.containsKey(FORCE_PERCENT_PARAM)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Force state: "
							+ this.getClientId(facesContext));
				}
				ajaxContext.addComponentToAjaxRender(this);
				String forcedState = params.get(FORCE_PERCENT_PARAM);
				if ("complete".equals(forcedState)) {
					Object reRender = this.getAttributes().get("reRenderAfterComplete");
					Set<String> ajaxRegions = AjaxRendererUtils.asSet(reRender);

					if (ajaxRegions != null) {
						for (Iterator<String> iter = ajaxRegions.iterator(); iter
								.hasNext();) {
							String id = iter.next();
							ajaxContext.addComponentToAjaxRender(this, id);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns ajax response data
	 * 
	 * @param uiComponent
	 * @param percent
	 * @return
	 */
	private Map<Object, Object> getResponseData(Number value,
			FacesContext facesContext) {

		AbstractProgressBarRenderer renderer = (AbstractProgressBarRenderer) this
				.getRenderer(facesContext);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(RendererUtils.HTML.value_ATTRIBUTE, value);
		map.put(_interval, this.getInterval());

		if (this.getAttributes().get(RendererUtils.HTML.style_ATTRIBUTE) != null) {
			map.put(RendererUtils.HTML.style_ATTRIBUTE, this.getAttributes()
					.get(RendererUtils.HTML.style_ATTRIBUTE));
		}

		boolean enabled = (Boolean) this.getAttributes().get(_enabled);
		map.put(_enabled, new Boolean(enabled));

		if (!isSimple(renderer)) {
			map.put(_markup, getMarkup(facesContext, renderer));
			map.put(_context, getContext(renderer, value));
		}

		addStyles2Responce(map, _completeClass, this.getAttributes().get(
				_completeClass));
		addStyles2Responce(map, _remainClass, this.getAttributes().get(
				_remainClass));
		addStyles2Responce(map, _styleClass, this.getAttributes().get(
				_styleClass));
		return map;

	}

	/**
	 * Returns context for macrosubstitution
	 * 
	 * @param renderer
	 * @param percent
	 * @return
	 */
	private JSLiteral getContext(AbstractProgressBarRenderer renderer,
			Number percent) {
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("value:");
		buffer.append(ScriptUtils.toScript(percent.toString())).append(",");
		buffer.append("minValue:");
		buffer.append(
				ScriptUtils.toScript(this.getAttributes().get(_minValue)
						.toString())).append(",");
		buffer.append("maxValue:");
		buffer.append(ScriptUtils.toScript(this.getAttributes().get(_maxValue)
				.toString()));

		String parameters = renderer.getParameters(this);
		if (parameters != null) {
			buffer.append(",");
			buffer.append(parameters);
		}
		buffer.append("}");
		return new JSLiteral(buffer.toString());
	}

	/**
	 * Return true if markup is simple
	 * 
	 * @return
	 */
	private boolean isSimple(AbstractProgressBarRenderer renderer) {
		return renderer.isSimpleMarkup(this);
	}

	/**
	 * Returns label markup
	 * 
	 * @param context
	 * @param renderer
	 * @return
	 */
	private JSLiteral getMarkup(FacesContext context,
			AbstractProgressBarRenderer renderer) {
		JSLiteral literal = null;
		try {
			StringBuffer markup = renderer.getMarkup(context, this);
			if (markup != null) {
				literal = new JSLiteral(markup.toString());
			}
		} catch (Exception e) {

		}
		return literal;
	}

	/**
	 * Add component classes to ajax response
	 * 
	 * @param buffer
	 * @param attr
	 * @param newValue
	 */
	private void addStyles2Responce(Map<Object, Object> map, String key,
			Object className) {
		if (className != null) {
			map.put(key, className);
		}
	}

	/**
	 * Converts value attr to number value
	 * 
	 * @param v -
	 *            value attr
	 * @return result
	 */
	public Number getNumber(Object v) {
		Number result = null;
		if (v != null) {
			try {
				if (v instanceof String) { // String
					result = Double.parseDouble((String) v);
				} else {
					Number n = (Number) v;
					if ((n instanceof BigDecimal) || (n instanceof Double) // Double
																			// or
																			// BigDecimal
							|| (n instanceof Float)) {
						result = n.floatValue();
					} else if (n instanceof Integer || n instanceof Long) { // Integer
						result = n.longValue();
					}
				}
			} catch (Exception e) {
				e.getMessage();
			}
			return result;
		}
		return new Integer(0);
	}

}
