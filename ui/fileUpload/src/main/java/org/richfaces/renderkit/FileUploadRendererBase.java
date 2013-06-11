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

package org.richfaces.renderkit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpSession;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.javascript.JSFunction;
import org.ajax4jsf.javascript.JSFunctionDefinition;
import org.ajax4jsf.javascript.JSLiteral;
import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.ComponentVariables;
import org.ajax4jsf.renderkit.ComponentsVariableResolver;
import org.ajax4jsf.renderkit.RendererUtils;
import org.ajax4jsf.request.MultipartRequest;
import org.ajax4jsf.resource.CountingOutputWriter;
import org.richfaces.component.FileUploadConstants;
import org.richfaces.component.UIFileUpload;
import org.richfaces.component.UIProgressBar;
import org.richfaces.component.util.HtmlUtil;
import org.richfaces.event.UploadAjaxActionEvent;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

/**
 * Class provides base renderer for upload file component
 * 
 * @author "Andrey Markavtsov"
 * 
 */
public abstract class FileUploadRendererBase extends
		TemplateEncoderRendererBase {

	/** Attribute name where collection of files uploaded will be stored */
	private static final String _FILES_UPLOADED_ATTRIBUTE_NAME = "uploadData";

	/** File upload bundle name */
	private static final String FILE_UPLOAD_BUNDLE = "org.richfaces.renderkit.fileUpload";

	/** Bundle prefix */
	private static final String bundlePrefix = "RICH_FILE_UPLOAD_";

	/** Bundle postfix */
	private static final String bundlePostfix = "_LABEL";

	/** Set of bundles that can be defined */
	private static final String[] bundlesLables = { "add", "upload", "stop",
			"clear_all", "entry_cancel", "entry_clear", "entry_stop", "done",
			"size_error", "transfer_error","progress" };

	/** Default labels values */
	private static final String[] defaultLables = { "Add...", "<b>Upload</b>",
			"<b>Stop</b>", "Clear All", "Cancel", "Clear", "Stop",
			"<b>Done</b>", "File size restricted", "Transfer error occurred","uploading"};

	/** Set of attributes that can define label's value */
	private static final String[] labelAttribues = { "addControlLabel",
			"uploadControlLabel", "stopControlLabel", "clearAllControlLabel",
			"cancelEntryControlLabel", "clearControlLabel",
			"stopEntryControlLabel", "doneLabel", "sizeErrorLabel", "transferErrorLabel", "progressLabel" };

	/**
	 * Overrides standard JSF component method.
	 * 
	 * @param context
	 *            faces context
	 * @param component
	 *            file upload component
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void doDecode(FacesContext context, UIComponent component) {
		UIFileUpload fileUpload = (UIFileUpload) component;
		String clientId = component.getClientId(context);
		ExternalContext externalContext = context.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		if (requestParameterMap.get(clientId) != null) {
            String actionString = requestParameterMap.get(FileUploadConstants.FILE_UPLOAD_ACTION);

            String uid = requestParameterMap.get(FileUploadConstants.UPLOAD_FILES_ID);
            
            if (actionString != null) {
            	new UploadAjaxActionEvent(component, actionString, uid).queue();
            }
			
			String fileUploadIndicator = requestParameterMap.get(FileUploadConstants.FILE_UPLOAD_INDICATOR);
			if (fileUploadIndicator != null && Boolean.TRUE.toString().equals(fileUploadIndicator)) {
				decreaseFileCounter(context, clientId);
				
				MultipartRequest multipartRequest = MultipartRequest.lookupRequest(context, uid);
				
				boolean isFlash = (requestParameterMap.get("_richfaces_send_http_error") != null);
				
				List<UploadItem> fileList = multipartRequest.getUploadItems();
				
				if (fileList == null || fileList.size() == 0) { 
					return;
				}
				
				boolean formUpload = multipartRequest.isFormUpload();
				
				if (isFlash && !formUpload && fileList.size() > 0) {
					try {
						UploadItem item = fileList.get(0);
						int actualSize = item.getFileSize();
						int clientSize = Integer.parseInt(requestParameterMap.get("_richfaces_size"));
						
						if (actualSize != clientSize) {
							return; // File uploading has been stopped on the client side
						}
					}catch (Exception e) {
						return;
					}
				}
				onUploadComplete(context, fileList, fileUpload, formUpload);
			}
		}
		

	}
	
	/**
	 * Put max file count into session scope
	 * 
	 * @param context
	 * @param component
	 * @param clientId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer initMaxFilesCount(FacesContext context,
			UIComponent component, String clientId) {
		Integer max = (Integer) component.getAttributes().get(
				"maxFilesQuantity");
		Object session = context.getExternalContext()
				.getSession(false);
		synchronized (session) {
			Map<String, Integer> map = (Map<String, Integer>) context.getExternalContext().getSessionMap().get(FileUploadConstants.UPLOADED_COUNTER);
			if (map == null) {
				map = Collections
						.synchronizedMap(new HashMap<String, Integer>());
			}
			map.put(clientId, max);
		}
		return max;
	}

	/**
	 * Gets form id
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component
	 * @return String form id
	 */
	public String getFormId(FacesContext context, UIComponent component) {
		UIComponent form = AjaxRendererUtils.getNestingForm(component);
		if (form != null) {
			return form.getClientId(context);
		}
		return "";
	}

	/**
	 * Gets container id
	 * 
	 * @param context -
	 *            faces context
	 * @param component -
	 *            component
	 * @return String container id
	 */
	public String getContainerId(FacesContext context, UIComponent component) {
		UIComponent container = (UIComponent) AjaxRendererUtils
				.findAjaxContainer(context, component);
		if (container != null) {
			return container.getClientId(context);
		}
		return null;
	}

	/**
	 * Generates map with internalized labels to be put into JS
	 * 
	 * @param o
	 *            map of labels
	 * @return javascript hash map
	 */
	public Object _getLabels(Object o) {
		return ScriptUtils.toScript(o);
	}

	/**
	 * Gets internalized labels. At the first system is looking for them in
	 * appropriate attributes if they are defined. Next search place is
	 * application and file upload bundles. If no result - default label value
	 * will be set up.
	 * 
	 * @param context
	 *            facesContext instance
	 * @param component
	 *            UIComponent
	 * @return map of labels
	 */
	public Map<String, String> getLabels(FacesContext context,
			UIComponent component) {
		Map<String, String> labelsMap = new HashMap<String, String>();
		ResourceBundle bundle1 = null;
		ResourceBundle bundle2 = null;
		UIFileUpload fileUpload = (UIFileUpload) component;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String messageBundle = context.getApplication().getMessageBundle();
		Object locale = fileUpload.getLocale();
		if (locale == null) {
			locale = context.getExternalContext().getRequestLocale();
		}
		if (null != messageBundle) {
			bundle1 = ResourceBundle.getBundle(messageBundle, fileUpload
					.getAsLocale(locale), loader);
		}
		try {
			bundle2 = ResourceBundle.getBundle(FILE_UPLOAD_BUNDLE, fileUpload
					.getAsLocale(locale), loader);

		} catch (MissingResourceException e) {
			// No external bundle was found, ignore this exception.
		}
		initLabels(labelsMap, bundle1, bundle2, fileUpload);
		return labelsMap;
	}

	/**
	 * Sets values for labels.
	 * 
	 * @param map
	 *            map of labels
	 * @param bundle1
	 *            application bundle
	 * @param bundle2
	 *            file upload bundle
	 * @param fileUpload
	 */
	private void initLabels(Map<String, String> map, ResourceBundle bundle1,
			ResourceBundle bundle2, UIFileUpload fileUpload) {
		int i = 0;
		for (String name : bundlesLables) {
			boolean found = false;
			if (labelAttribues[i] != null) {
				String attributeName = labelAttribues[i];
				if (fileUpload.getAttributes().get(attributeName) != null) {
					map.put(name, (String) fileUpload.getAttributes().get(
							attributeName));
					found = true;
				}
			}
			if (!found && (bundle1 != null || bundle2 != null)) {
				String label = getFromBundle(name, bundle1, bundle2);
				if (label != null) {
					map.put(name, getFromBundle(name, bundle1, bundle2));
					found = true;
				}
			}
			if (!found) {
				map.put(name, defaultLables[i]);
			}
			i++;
		}
	}

	/**
	 * Looking for labels name in bundles provided.
	 * 
	 * @param bundle1
	 *            application bundle
	 * @param bundle2
	 *            file upload bundle
	 * @return String label value
	 */
	private String getFromBundle(String name, ResourceBundle bundle1,
			ResourceBundle bundle2) {
		name = bundlePrefix + name.toUpperCase() + bundlePostfix;
		String v = null;
		if (bundle1 != null) {
			try {
				v = bundle1.getString(name);
			} catch (Exception e) {
				if (bundle2 != null) {
					try {
						v = bundle2.getString(name);
					} catch (Exception ex) {
						// no value has been found
					}
				}

			}

		} else if (bundle2 != null) {
			try {
				v = bundle2.getString(name);
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				b.write(v.getBytes());
			} catch (Exception e) {
				// no value has been found
			}
		}
		return v;
	}

	/**
	 * Method decrease file counter
	 * 
	 * @param context
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	private void decreaseFileCounter(FacesContext context, String id) {
		Map<String, Integer> map = (Map<String, Integer>) context.getExternalContext().getSessionMap()
				.get(FileUploadConstants.UPLOADED_COUNTER);
		if (map != null) {
			Integer i = map.get(id);
			if (i == null) {
				i = 0;
			} else {
				i--;
			}
			map.put(id, i);
		}
	}

	/**
	 * Put uploaded file into data attribute defined
	 * 
	 * @param context
	 * @param fileUpload
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	private void storeData(FacesContext context, UIFileUpload fileUpload,
			List<UploadItem> fileList) {
		ValueExpression data = fileUpload
				.getValueExpression(_FILES_UPLOADED_ATTRIBUTE_NAME);
		if (data != null) {
			if (data.getValue(context.getELContext()) instanceof Collection) {
				Collection collection = (Collection) data.getValue(context
						.getELContext());
				collection.addAll(fileList);
			}
		}
		new UploadEvent(fileUpload, fileList).queue();
	}

	/**
	 * Call with method after uploading completed
	 * 
	 * @param context
	 * @param file
	 * @param fileUpload
	 */
	private void onUploadComplete(FacesContext context, List<UploadItem> fileList,
			UIFileUpload fileUpload, boolean formUpload) {
		storeData(context, fileUpload, fileList);
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		if ((Boolean)fileUpload.getAttributes().get(AjaxRendererUtils.AJAX_SINGLE_ATTR)) {
			ajaxContext.setAjaxSingleClientId(fileUpload.getClientId(context));
		}

		ajaxContext.setAjaxRequest(!formUpload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	@Override
	protected Class<? extends UIComponent> getComponentClass() {
		return UIFileUpload.class;
	}

	/**
	 * Generates common JS script by action value
	 * 
	 * @param context
	 * @param component
	 * @param action
	 * @param oncomplete
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private String getActionScript(FacesContext context, UIComponent component,
			String action, Object oncomplete) throws IOException {
		ComponentVariables variables = ComponentsVariableResolver.getVariables(
				this, component);
		String clientId = component.getClientId(context);
		String containerId = (String) variables.getVariable("containerId");
		JSFunction ajaxFunction = new JSFunction(
				AjaxRendererUtils.AJAX_FUNCTION_NAME);
		ajaxFunction.addParameter(containerId);
		ajaxFunction.addParameter(new JSReference("formId"));
		ajaxFunction.addParameter(new JSReference("event"));
		// AjaxRendererUtils.buildAjaxFunction(
		// component, context);

		Map options = AjaxRendererUtils.buildEventOptions(context, component);
		Map parameters = (Map) options.get("parameters");
		parameters.put(FileUploadConstants.FILE_UPLOAD_ACTION, action);
		parameters.put(FileUploadConstants.UPLOAD_FILES_ID, new JSReference("uid"));
		parameters.put(clientId, clientId);
		parameters.put(AjaxRendererUtils.AJAX_SINGLE_ATTR, clientId);
		if (oncomplete != null) {
			options.put("onbeforedomupdate", oncomplete);
		}
		ajaxFunction.addParameter(options);

		JSFunctionDefinition function = new JSFunctionDefinition("uid");
		function.addParameter("formId");
		function.addParameter("event");
		function.addToBody(ajaxFunction.toScript());

		return function.toScript();
	}

	/**
	 * Return accepted types map
	 * 
	 * @param context
	 * @param component
	 * @return
	 */
	public Object getAcceptedTypes(FacesContext context, UIComponent component) {
		String acceptedTypes = (String) component.getAttributes().get(
				"acceptedTypes");
		Map<String, Boolean> accepted = new HashMap<String, Boolean>();
		if (acceptedTypes != null && acceptedTypes.length() > 0) {
			acceptedTypes = acceptedTypes.replaceAll("[\\s]", "");
			String[] types = acceptedTypes.split("[,;|]");
			if (types != null) {
				for (String type : types) {
					accepted.put(type.toLowerCase(), true);
				}
				return ScriptUtils.toScript(accepted);
			}
		}else {
			accepted.put("*", true);
		}
		return ScriptUtils.toScript(accepted);
	}

	/**
	 * Generates JS script for stopping uploading process
	 * 
	 * @param context
	 * @param component
	 * @return
	 * @throws IOException
	 */
	public String getStopScript(FacesContext context, UIComponent component)
			throws IOException {
		JSFunctionDefinition oncomplete = new JSFunctionDefinition();
		oncomplete.addParameter("request");
		oncomplete.addParameter("event");
		oncomplete.addParameter("data");
		StringBuffer body = new StringBuffer("$('");
		body.append(component.getClientId(context));
		body.append("').component.cancelUpload(data);");
		oncomplete.addToBody(body);
		return getActionScript(context, component, FileUploadConstants.FILE_UPLOAD_ACTION_STOP, oncomplete);
	}

	/**
	 * Generates JS script for getting file size from server
	 * 
	 * @param context
	 * @param component
	 * @return
	 * @throws IOException
	 */
	public String getFileSizeScript(FacesContext context, UIComponent component)
			throws IOException {
		JSFunctionDefinition oncomplete = new JSFunctionDefinition();
		oncomplete.addParameter("request");
		oncomplete.addParameter("event");
		oncomplete.addParameter("data");
		StringBuffer body = new StringBuffer("$('");
		body.append(component.getClientId(context));
		body.append("').component.getFileSize(data);");
		oncomplete.addToBody(body);
		return getActionScript(context, component, FileUploadConstants.FILE_UPLOAD_ACTION_PROGRESS, oncomplete);

	}

	/**
	 * Generates progress label markup
	 * 
	 * @param context
	 * @param component
	 * @return
	 * @throws IOException
	 */
	public Object getLabelMarkup(FacesContext context, UIComponent component)
			throws IOException {
		CountingOutputWriter customWriter = new CountingOutputWriter();
		StringBuffer result = null;
		UIComponent label = component.getFacet("label");
		if (label != null) {

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
			writeScriptBody(context, label, false);
			if (writer != null) {
				context.setResponseWriter(writer);
			}
			result = customWriter.getContent();
		}

		return (result != null) ? new JSLiteral(result.toString())
				: JSReference.NULL;
	}

	/**
	 * Gets a string representing css specific height of downloaded file list
	 * panel.
	 * 
	 * @param component
	 *            file upload component
	 * @return a string representing css specific height of downloaded file list
	 *         panel
	 */
	public String getFileListHeight(UIFileUpload component) {
		return HtmlUtil.qualifySize(component.getListHeight());
	}

	/**
	 * Gets a string representing css specific width of downloaded file list
	 * panel.
	 * 
	 * @param component
	 *            file upload component
	 * @return a string representing css specific width of downloaded file list
	 *         panel
	 */
	public String getFileListWidth(UIFileUpload component) {
		return HtmlUtil.qualifySize(component.getListWidth());
	}

	/**
	 * Generate component custom events functions
	 * 
	 * @param context
	 * @param component
	 * @param attributeName
	 * @return
	 */
	public String getAsEventHandler(FacesContext context,
			UIComponent component, String attributeName) {
		Object eventHandler = RendererUtils.getInstance().getAsEventHandler(
				context, component, attributeName, "");
		if (eventHandler != null) {
			return eventHandler.toString();
		}
		return JSReference.NULL.toScript();
	}

	/**
	 * Gets progress bar Id
	 * 
	 * @param context
	 * @param component
	 * @return
	 * @throws IOException
	 */
	public String getProgressBarId(FacesContext context, UIComponent component)
			throws IOException {
		return getProgressBar(context, component).getClientId(context);
	}

	/**
	 * Renders progress bar
	 * 
	 * @param context
	 * @param component
	 * @throws IOException
	 */
	public void renderProgress(FacesContext context, UIComponent component)
			throws IOException {
		UIComponent progressBar = getProgressBar(context, component);
		renderChild(context, progressBar);
	}

	/**
	 * Creates progress bar component
	 * 
	 * @param context
	 * @param fileUpload
	 * @return
	 */
	private UIComponent createProgressBar(FacesContext context,
			UIComponent fileUpload) {
		UIComponent progressBar = fileUpload.getFacet("progress");
		if (null == progressBar) {
			progressBar = context.getApplication().createComponent(
					UIProgressBar.COMPONENT_TYPE);
		}
		fileUpload.getFacets().put("progress", progressBar);
		return progressBar;
	}

	/**
	 * Returns progress bar
	 * 
	 * @param context
	 * @param component
	 * @return
	 */
	public UIComponent getProgressBar(FacesContext context,
			UIComponent component) {
		UIComponent progressBar = component.getFacet("progress");
		if (null == progressBar) {
			progressBar = createProgressBar(context, component);
		}
		progressBar.getAttributes().put("minValue", -1);
		
		ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
		ValueExpression falseExpression = expressionFactory.createValueExpression(
				context.getELContext(), 
				"#{" + Boolean.FALSE + "}", 
				Boolean.class);
		
		progressBar.setValueExpression("enabled", falseExpression);
		progressBar.setTransient(false);
		return progressBar;
	}
	
	
	/**
	 * Returns set of children UIParameters
	 * @param context
	 * @param component
	 * @return
	 */
	public Object getChildrenParams(FacesContext context, UIComponent component) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Iterator<UIComponent> iterator = component.getChildren().iterator(); iterator.hasNext();) {
			UIComponent child =  iterator.next();
			if (child instanceof UIParameter) {
				UIParameter p = (UIParameter)child;
				parameters.put(p.getName(), p.getValue());
			}
		}
		
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		Map<String, Object> commonAjaxParameters = ajaxContext.getCommonAjaxParameters();
		if (commonAjaxParameters != null) {
			parameters.putAll(commonAjaxParameters);
		}
		
		return ((parameters.size() > 0) ? ScriptUtils.toScript(parameters) : JSReference.NULL);
	}
	
	public String getSessionId(FacesContext context, UIComponent component) {
		String id = null;
		Object session = context.getExternalContext().getSession(false);
		if (session != null) {
			if (session instanceof HttpSession) {
				id = ((HttpSession) session).getId();
			} else {
				Class<? extends Object> sesssionClass = session.getClass();
				try {
					Method getIdMethod = sesssionClass.getMethod("getId");
					id = (String) getIdMethod.invoke(session);
				} catch (SecurityException e) {
					throw new FacesException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new FacesException(e.getMessage(), e);
				} catch (IllegalArgumentException e) {
					throw new FacesException(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					throw new FacesException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					Throwable cause = e.getCause();
					throw new FacesException(cause.getMessage(), cause);
				}
			}
		}
		
		return id;
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
