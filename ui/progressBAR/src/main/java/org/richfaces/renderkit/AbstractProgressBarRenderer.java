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
 * AbstractProgressBarRenderer.java		Date created: 20.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.renderkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSLiteral;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.CountingOutputWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.UIProgressBar;

/**
 * Abstract progress bar renderer
 * 
 * @author "Andrey Markavtsov"
 * 
 */
public class AbstractProgressBarRenderer extends TemplateEncoderRendererBase {
	
	private static final Log log = LogFactory.getLog(AbstractProgressBarRenderer.class);

	/** Ajax function performing polling */
	private static final String AJAX_POLL_FUNCTION = "A4J.AJAX.Poll";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.AjaxCommandRendererBase#doDecode(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	@Override
	protected void doDecode(FacesContext facesContext, UIComponent uiComponent) {
		new AjaxEvent(uiComponent).queue();
		//uiComponent.queueEvent(new ActionEvent(uiComponent));
	}

	/**
	 * Render progress state forced from javascript
	 * 
	 * @param state
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void renderForcedState(String state, FacesContext context,
			UIComponent component) throws IOException {
		if ("initialState".equals(state)) {
			encodeInitialState(context, component, true);
		} else if ("progressState".equals(state)) {
			encodeProgressState(context, component, true);
		} else if ("completeState".equals(state)) {
			encodeCompletedState(context, component, true);
		}
		component.getAttributes().remove(UIProgressBar.FORCE_PERCENT_PARAM);
	}

	/**
	 * Gets state forced from javascript
	 * 
	 * @param component
	 * @return
	 */
	public String getForcedState(FacesContext context, UIComponent component) {
		String forcedState = null;
		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();
		if (params.containsKey(UIProgressBar.FORCE_PERCENT_PARAM)) {
			forcedState = params.get(UIProgressBar.FORCE_PERCENT_PARAM);
		}
		return forcedState;
	}

	/**
	 * Renderes label markup
	 * 
	 * @param context
	 * @param component
	 * @return
	 */
	public StringBuffer getMarkup(FacesContext context, UIComponent component) {
		StringBuffer result = new StringBuffer();
		CountingOutputWriter customWriter = new CountingOutputWriter();
		try {
			if (hasChildren(component)) {
				
				ResponseWriter writer = context.getResponseWriter();

				String defaultRenderKitId = context.getApplication()
						.getDefaultRenderKitId();
				if (null == defaultRenderKitId) {
					defaultRenderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
				}
				RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder
						.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
				RenderKit renderKit = renderKitFactory.getRenderKit(context,
						defaultRenderKitId);

				ResponseWriter responseWriter = renderKit.createResponseWriter(
						customWriter, null, "UTF-8");
				context.setResponseWriter(responseWriter);
				writeScriptBody(context, component, true);
				result = customWriter.getContent();
				
				if (writer != null) {
					context.setResponseWriter(writer);
				}else {
					HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
					PrintWriter wr = response.getWriter();
					if (wr != null) {
						context.setResponseWriter(renderKit.createResponseWriter(wr, null, "UTF-8"));
					}
				}
			} else {
				writeScriptBody(customWriter, (String) component
						.getAttributes().get("label"));
				result = customWriter.getContent();
			}
		} catch (Exception e) {
			log.error("Error occurred during rendering of progress bar label. It switched to empty string", e);
		}

		return result;

	}

	/**
	 * Methods encodes AJAX script for polling
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component instance
	 * @throws IOException -
	 *             IOException
	 */
	public void encodePollScript(FacesContext context, UIComponent component)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		UIProgressBar progressBar = (UIProgressBar) component;
		String clientId = component.getClientId(context);
		StringBuffer pollScript = new StringBuffer("\n");
		StringBuffer script = new StringBuffer("\n");
		if (isAjaxMode(component) && progressBar.isEnabled()) {
			JSFunction function = AjaxRendererUtils.buildAjaxFunction(
					component, context, AJAX_POLL_FUNCTION);

			function.addParameter(new JSReference("$('" + clientId
					+ "').component.options"));
			function.appendScript(script);

			pollScript.append(script);
		} else {
			pollScript.append(getStopPollScript(clientId));
		}
		pollScript.append(";\n");
		writer.writeText(pollScript.toString(), null);
	}

	/**
	 * Encodes script for state rendering in client mode
	 * 
	 * @param context
	 * @param component
	 * @param state
	 * @throws IOException
	 */
	public void encodeRenderStateScript(FacesContext context,
			UIComponent component, String state) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		StringBuffer script = new StringBuffer("\n");
		script.append(
				"$('" + component.getClientId(context)
						+ "').component.renderState('").append(state).append(
				"');");
		writer.write(script.toString());
	}

	/**
	 * Encodes script for label rendering in client
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void encodeLabelScript(FacesContext context, UIComponent component)
			throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		StringBuffer script = new StringBuffer("\n");
		script.append(
				"$('" + component.getClientId(context)
						+ "').component.renderLabel(").append(
				getMarkup(context, component)).append(",null);\n");
		writer.append(script.toString());

	}

	/**
	 * Encode initial javascript
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void encodeInitialScript(FacesContext context,
			UIComponent component, String state) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		UIProgressBar progressBar = (UIProgressBar) component;
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, component);
		StringBuffer script = new StringBuffer();
		String clientId = component.getClientId(context);
		String containerId = ((UIComponent) AjaxRendererUtils
				.findAjaxContainer(context, component)).getClientId(context);
		String mode = (String) component.getAttributes().get("mode");
		UIComponent form = AjaxRendererUtils.getNestingForm(component);
		String formId = "";
		if (form != null) {
			formId = form.getClientId(context);
		} else if ("ajax".equals(mode)) {
			// Ignore form absent. It can be rendered by forcing from any
			// component
			// throw new FaceletException("Progress bar component in ajax mode
			// should be placed inside the form");
		}
		Number minValue = getNumber(component.getAttributes().get("minValue"));
		Number maxValue = getNumber(component.getAttributes().get("maxValue"));
		Number value = (Number) variables.getVariable("value");
		StringBuffer markup = getMarkup(context, component);

		script.append("new ProgressBar('").append(clientId).append("','") // id
				.append(containerId).append("','") // containerId
				.append(formId).append("','") // formId
				.append(mode).append("',") // mode
				.append(minValue).append(",") // min value
				.append(maxValue).append(","); // max value
		script.append(getContext(component)); // context
		script.append(",");
		script.append(markup != null ? new JSLiteral(markup.toString())
				: JSReference.NULL); // markup
		script.append(",");
		script.append(ScriptUtils.toScript(buildAjaxOptions(clientId, // options
				progressBar, context)));
		String progressVar = (String) component.getAttributes().get(
				"progressVar");
		if (progressVar != null) {
			script.append(",'");
			script.append(progressVar); // progress var
			script.append("','");
		} else {
			script.append(",null,'");
		}
		script.append(state);
		script.append("',");
		script.append(value.toString());
		script.append(")\n;");
		writer.write(script.toString());
	}

	/**
	 * Creates options map for AJAX requests
	 * 
	 * @param clientId
	 * @param progressBar
	 * @param context
	 * @return
	 */
	private Map buildAjaxOptions(String clientId, UIProgressBar progressBar,
			FacesContext context) {
		Map options = AjaxRendererUtils.buildEventOptions(context, progressBar);
		Integer interval = new Integer(progressBar.getInterval());
		options.put("pollId", clientId);
		options.put("pollinterval", interval);
		JSFunctionDefinition onsubmit = getUtils().getAsEventHandler(context, progressBar, "onsubmit", "");
		if (onsubmit != null) {
			options.put("onsubmit", onsubmit);
		}
		if (progressBar.getAttributes().containsKey("ignoreDupResponses")) {
			options.put("ignoreDupResponses", progressBar.getAttributes().get(
					"ignoreDupResponses"));
		}
		Map parameters = (Map) options.get("parameters");
		parameters.put("percent", "percent");
		parameters.put(clientId, clientId);
		parameters.put("ajaxSingle", clientId);
		// options.put("onbeforedomupdate", getOnComplete(clientId, progressBar,
		// context));
		return options;
	}

	/**
	 * Check if component mode is AJAX
	 * 
	 * @param component
	 * @return
	 */
	private boolean isAjaxMode(UIComponent component) {
		String mode = (String) component.getAttributes().get("mode");
		return "ajax".equalsIgnoreCase(mode);
	}

	/**
	 * Encodes client mode
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @param min
	 * @param max
	 * @throws IOException
	 */
	public void encodeClientMode(FacesContext context, UIComponent component,
			Number value, Number min, Number max) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = component.getClientId(context);
		String state = (value.doubleValue() <= min.doubleValue()) ? "initialState"
				: ((value.doubleValue() >= max.doubleValue()) ? "completeState"
						: "progressState");
		writer.startElement(HTML.DIV_ELEM, component);
		writer.writeAttribute(HTML.id_ATTRIBUTE, clientId, null);
		encodeInitialState(context, component, false);
		encodeProgressState(context, component, false);
		encodeCompletedState(context, component, false);
		startScript(writer, component);
		encodeInitialScript(context, component, state);
		encodeLabelScript(context, component);
		encodeRenderStateScript(context, component, state);
		endScript(writer, component);
		writer.endElement(HTML.DIV_ELEM);
	}

	/**
	 * Methods encodes start facet of progress bar component
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component instance
	 * @throws IOException -
	 *             IOException
	 */
	public void encodeInitialState(FacesContext context, UIComponent component,
			boolean isAjaxMode) throws IOException {
		String clientId = component.getClientId(context);
		String initialClass = (String) component.getAttributes().get(
				"initialClass");
		String style = (String) component.getAttributes().get("style");
		if (null==style) style="";
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(HTML.DIV_ELEM, component);
		getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE, initialClass);
		getUtils().writeAttribute(writer, "style",
				style + (!isAjaxMode ? "display: none" : ""));
		writer.writeAttribute(HTML.id_ATTRIBUTE, (isAjaxMode) ? clientId
				: clientId + ":initialState", null);
		UIComponent initial = component.getFacet("initial");
		if (initial != null) {
			renderChild(context, initial);
		}
		if (isAjaxMode) {
			startScript(writer, component);
			encodeInitialScript(context, component, "initialState");
			encodePollScript(context, component);
			endScript(writer, component);
		}
		writer.endElement(HTML.DIV_ELEM);

	}

	/**
	 * Encodes progress state of the component
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void encodeProgressState(FacesContext context,
			UIComponent component, boolean isAjaxMode) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, component);
		String clientId = component.getClientId(context);
		String styleClass = (String) variables.getVariable("styleClass");
		boolean isSimple = isSimpleMarkup(component);
		String style = (String) component.getAttributes().get("style");
		if (null==style) style="";
		String shellClass = (!isSimple) ? "rich-progress-bar-shell-dig "
				: "rich-progress-bar-shell ";
		writer.startElement("div", component);
		getUtils().writeAttribute(
				writer,
				"class",
				"rich-progress-bar-block rich-progress-bar-width " + shellClass
						+ (styleClass != null ? styleClass : ""));
		getUtils().writeAttribute(writer, HTML.id_ATTRIBUTE,
				(isAjaxMode) ? clientId : clientId + ":progressState");
		getUtils().writeAttribute(
				writer,
				"style",
				style + (!isAjaxMode ? "display: none" : ""));
		getUtils().encodePassThruWithExclusions(context, component, "onsubmit");
		encodeProgressBar(context, component, isSimple);
		if (isAjaxMode) {
			startScript(writer, component);
			encodeInitialScript(context, component, "progressState");
			encodeLabelScript(context, component);
			encodePollScript(context, component);
			endScript(writer, component);
		}
		writer.endElement("div");
	}

	/**
	 * Encodes html of component
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component instance
	 * @throws IOException
	 */
	public void encodeProgressBar(FacesContext context, UIComponent component,
			boolean isSimple) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = component.getClientId(context);
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, component);

		Number value = (Number) variables.getVariable("percent");

		String width = String.valueOf(value.intValue());
		String style = (String) component.getAttributes().get("style");
		if (null==style) style="";

		String completeClass = (String) component.getAttributes().get(
				"completeClass");

		String remainClass = (String) component.getAttributes().get(
				"remainClass");

		if (!isSimple) {
			// <div class='remainClass' id='clientId:remain'
			// style='style'></div>
			writer.startElement("div", component);
			getUtils().writeAttribute(
					writer,
					"class",
					"rich-progress-bar-width rich-progress-bar-remained rich-progress-bar-padding "
							+ remainClass);
			getUtils().writeAttribute(writer, "id", clientId + ":remain");
			getUtils().writeAttribute(writer, "style", style);
			writer.endElement("div");

			// <div class='rich-progress-bar-uploaded-dig' id='clientId:upload'
			// style='style'>
			writer.startElement("div", component);
			getUtils().writeAttribute(writer, "class",
					"rich-progress-bar-uploaded-dig");
			getUtils().writeAttribute(writer, "id", clientId + ":upload");
			getUtils().writeAttribute(
					writer,
					"style",
					(style != null ? style + ";" : "") + " width: " + width
							+ "%;");

			// <div class='completeClass' id='clientId:complete'
			// style='style'></div>
			writer.startElement("div", component);
			getUtils()
					.writeAttribute(
							writer,
							"class",
							"rich-progress-bar-height-dig rich-progress-bar-width rich-progress-bar-completed rich-progress-bar-padding "
									+ completeClass);
			getUtils().writeAttribute(writer, "id", clientId + ":complete");
			getUtils().writeAttribute(writer, "style", style);
			writer.endElement("div");

			// </div>
			writer.endElement("div");
		} else {
			writer.startElement("div", component);
			getUtils().writeAttribute(
					writer,
					"class",
					"rich-progress-bar-height rich-progress-bar-uploaded "
							+ completeClass);
			getUtils().writeAttribute(writer, "id", clientId + ":upload");
			getUtils().writeAttribute(
					writer,
					"style",
					(style != null ? style + ";" : "") + " width: " + width
							+ "%;");
			writer.write(" ");
			writer.endElement("div");
		}

	}

	/**
	 * Returns parameters attr
	 * 
	 * @param component
	 * @param renderer
	 * @param percent
	 * @return
	 */
	public String getParameters(UIComponent component) {
		String parameters = (String) component.getAttributes()
				.get("parameters");
		return parameters;
	}

	/**
	 * Writes start script element
	 * 
	 * @param writer
	 * @param component
	 * @throws IOException
	 */
	private void startScript(ResponseWriter writer, UIComponent component)
			throws IOException {
		writer.startElement(HTML.SPAN_ELEM, component);
		writer.writeAttribute(HTML.style_ATTRIBUTE, "display: none;", null);
		writer.startElement(HTML.SCRIPT_ELEM, component);
		writer.writeAttribute(HTML.TYPE_ATTR, "text/javascript", null);
	}

	/**
	 * Writes end script element
	 * 
	 * @param writer
	 * @param component
	 * @throws IOException
	 */
	private void endScript(ResponseWriter writer, UIComponent component)
			throws IOException {
		writer.endElement(HTML.SCRIPT_ELEM);
		writer.endElement(HTML.SPAN_ELEM);
	}

	/**
	 * Returns context for macrosubstitution
	 * 
	 * @param component
	 * @return
	 */
	private JSLiteral getContext(UIComponent component) {
		StringBuffer buffer = new StringBuffer();
		String parameters = getParameters(component);
		JSLiteral literal = null;
		if (parameters != null) {
			buffer.append("{").append(parameters).append("}");
			literal = new JSLiteral(buffer.toString());
		} else {
			literal = new JSLiteral("null");
		}
		return literal;
	}

	/**
	 * Return true if component has children components
	 * 
	 * @param component
	 * @return
	 */
	private boolean hasChildren(UIComponent component) {
		return (component.getChildCount() != 0);
	}

	/**
	 * Returns true if markup should rendered as simple 2 divs
	 * 
	 * @param component
	 * @return
	 */
	public boolean isSimpleMarkup(UIComponent component) {
		if (hasChildren(component)) {
			return false;
		} else {
			if (component.getAttributes().get("label") != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Methods encodes finish facet of progress bar component
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component instance
	 * @throws IOException -
	 *             IOException
	 */
	public void encodeCompletedState(FacesContext context,
			UIComponent component, boolean isAjaxMode) throws IOException {
		String clientId = component.getClientId(context);
		String finishClass = (String) component.getAttributes().get(
				"finishClass");
		String style = (String) component.getAttributes().get("style");
		if (null==style) style="";
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(HTML.DIV_ELEM, component);
		writer.writeAttribute(HTML.id_ATTRIBUTE, (isAjaxMode) ? clientId
				: clientId + ":completeState", null);
		getUtils().writeAttribute(writer, HTML.class_ATTRIBUTE, finishClass);
		getUtils().writeAttribute(writer, "style",
				style + (!isAjaxMode ? "display: none" : ""));

		UIComponent completed = component.getFacet("complete");
		if (completed != null) {
			renderChild(context, completed);
		}
		if (isAjaxMode) {
			startScript(writer, component);
			encodeInitialScript(context, component, "completeState");
			writer.write(getStopPollScript(clientId).toString());
			endScript(writer, component);
		}
		writer.endElement(HTML.DIV_ELEM);

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
				if (v instanceof String) {
					result = Double.parseDouble((String) v);
				} else {
					Number n = (Number) v;
					if (n instanceof BigDecimal || n instanceof Double
							|| n instanceof Float) {
						result = n.floatValue();
					} else if (n instanceof Integer || n instanceof Long) {
						result = n.longValue();
					}
				}
			} catch (Exception e) {
				// no action
			}
			return result;
		}
		return new Integer(0);
	}

	/**
	 * Calculates percent value according to min & max value
	 * 
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public Number calculatePercent(Number value, Number minValue,
			Number maxValue) {
		if (minValue.doubleValue() < value.doubleValue()
				&& value.doubleValue() < maxValue.doubleValue()) {
			return (Number) ((value.doubleValue() - minValue.doubleValue()) * 100.0 / (maxValue
					.doubleValue() - minValue.doubleValue()));
		} else if (value.doubleValue() <= minValue.doubleValue()) {
			return 0;
		} else if (value.doubleValue() >= maxValue.doubleValue()) {
			return 100;
		} 
		return 0;
	}

	/**
	 * Returns JS script to stop polling
	 * 
	 * @param clientId
	 * @return
	 */
	private StringBuffer getStopPollScript(String clientId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("A4J.AJAX.StopPoll('").append(clientId).append("');\n");
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		// only poll component is allowed.
		return UIProgressBar.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.renderkit.TemplateEncoderRendererBase#encodeChildren(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		; // We should not render children
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeChildren(javax.faces.context.ResponseWriter,
	 *      javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void doEncodeChildren(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		; // We should not render children
	}

}
