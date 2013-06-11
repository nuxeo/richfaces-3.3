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

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.AjaxContextImpl;
import org.ajax4jsf.request.MultipartRequest;
import org.richfaces.event.FileUploadListener;
import org.richfaces.event.UploadAjaxActionEvent;
import org.richfaces.event.UploadEvent;
import org.richfaces.renderkit.FileUploadRendererBase;



/**
 * JSF component class
 * 
 */
public abstract class UIFileUpload extends UIInput {

	/**
	 * <p>
	 * The standard component type for this component.
	 * </p>
	 */
	public static final String COMPONENT_TYPE = "org.richfaces.component.FileUpload";

	/**
	 * <p>
	 * The standard component family for this component.
	 * </p>
	 */
	public static final String COMPONENT_FAMILY = "org.richfaces.component.FileUpload";

	private String localContentType;

	private String localFileName;

	private Integer localFileSize;

	private InputStream localInputStream;

	private void setupProgressBarValueExpression(FacesContext context, String uid) {
		FileUploadRendererBase renderer = (FileUploadRendererBase)this.getRenderer(context);
		UIComponent progressBar = renderer.getProgressBar(context, this);
		String percentExpression = FileUploadConstants.PERCENT_BEAN_NAME + "['"+uid+"']";
		ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
		ELContext elContext = context.getELContext();

		ValueExpression value = expressionFactory
		.createValueExpression(elContext, "#{" + percentExpression + "}",
				Integer.class);
		progressBar.setValueExpression("value", value);

		ValueExpression enabled = expressionFactory
		.createValueExpression(elContext, 
				"#{" + percentExpression + " < 100}", //100 - disable progress when upload reaches 100%
				Boolean.class);

		progressBar.setValueExpression("enabled", enabled);
	}



	public String getLocalContentType() {
		return localContentType;
	}

	public void setLocalContentType(String localContentType) {
		this.localContentType = localContentType;
	}

	public String getLocalFileName() {
		return localFileName;
	}

	public void setLocalFileName(String localFileName) {
		this.localFileName = localFileName;
	}

	public Integer getLocalFileSize() {
		return localFileSize;
	}

	public void setLocalFileSize(Integer localFileSize) {
		this.localFileSize = localFileSize;
	}

	public InputStream getLocalInputStream() {
		return localInputStream;
	}

	public void setLocalInputStream(InputStream localInputStream) {
		this.localInputStream = localInputStream;
	}

	public abstract void setAcceptedTypes(String acceptedTypes);

	public abstract String getAcceptedTypes();

	public abstract Integer getMaxFilesQuantity();

	public abstract void setMaxFilesQuantity(Integer maxFilesQuantity);

	public abstract String getListHeight();

	public abstract void setListHeight(String listHeight);

	public abstract String getListWidth();

	public abstract void setListWidth(String listWidth);

	public abstract String getStyleClass();

	public abstract String getStyle();

	public abstract void setStyleClass(String styleClass);

	public abstract void setStyle(String style);

	public abstract Object getLocale();

	public abstract void setLocale(Object locale);

	public abstract MethodBinding getFileUploadListener();

	public abstract void setFileUploadListener(MethodBinding scrollerListener);

	public void addFileUploadListener(FileUploadListener listener) {
		addFacesListener(listener);
	}

	public FileUploadListener[] getFileUploadListeners() {
		return (FileUploadListener[]) getFacesListeners(FileUploadListener.class);
	}

	public void removeFileUploadListener(FileUploadListener listener) {
		removeFacesListener(listener);
	}

	public void reset () {
		this.localContentType = null;
		this.localContentType = null;
		this.localFileName = null;
		this.localFileSize = null;
		this.localInputStream = null;
	}

	public void broadcast(FacesEvent e) {

		if (e instanceof UploadEvent) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			MethodBinding binding = getFileUploadListener();
			if (binding != null) {
				binding.invoke(facesContext, new Object[] { e });
			}

		} else if (e instanceof UploadAjaxActionEvent) {
			UploadAjaxActionEvent uploadActionEvent = (UploadAjaxActionEvent) e;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			
			AjaxContext ajaxContext = AjaxContextImpl.getCurrentInstance(facesContext);
			String uid = uploadActionEvent.getUploadId();
			String actionString = uploadActionEvent.getAction();
			
			if ("progress".equals(actionString)) {
				Map<String, Object> sessionMap = externalContext.getSessionMap();
				Map<String, Integer> requestsSizeMap = (Map<String, Integer>) sessionMap.get(FileUploadConstants.REQUEST_SIZE_BEAN_NAME);

				if (requestsSizeMap != null) {
					setupProgressBarValueExpression(facesContext, uid);
					ajaxContext.setResponseData(requestsSizeMap.get(uid));
				}
			} else if (FileUploadConstants.FILE_UPLOAD_ACTION_STOP.equals(actionString)) {
				MultipartRequest multipartRequest = MultipartRequest.lookupRequest(facesContext, uid);
				if (multipartRequest != null) {
					multipartRequest.stop();
					if (multipartRequest.isStopped()) {
						ajaxContext.setResponseData(uid);
					}
				}
			}
		}
		
		super.broadcast(e);
	}

/**
 *Parse Locale from String.
 *String must be represented as Locale.toString(); xx_XX_XXXX
 */

public Locale parseLocale(String localeStr){

	int length = localeStr.length();
	if(null==localeStr||length<2){
		return Locale.getDefault();
	}

	//Lookup index of first '_' in string locale representation.
	int index1 = localeStr.indexOf("_");
	//Get first charters (if exist) from string
	String language = null; 
	if(index1!=-1){
		language = localeStr.substring(0, index1);
	}else{
		return new Locale(localeStr);
	}
	//Lookup index of second '_' in string locale representation.
	int index2 = localeStr.indexOf("_", index1+1);
	String country = null;
	if(index2!=-1){
		country = localeStr.substring(index1+1, index2);
		String variant = localeStr.substring(index2+1);
		return new Locale(language, country, variant);
	}else{
		country = localeStr.substring(index1+1);
		return new Locale(language, country);
	}		
}

public Locale getAsLocale(Object locale) {

	if (locale instanceof Locale) {

		return (Locale) locale;

	} else if (locale instanceof String) {

		return parseLocale((String) locale);

	} else {

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		Converter converter = application
		.createConverter(locale.getClass());
		if (null != converter) {
			return parseLocale(converter.getAsString(context, this, locale));
		} else {
			throw new FacesException(
					"Wrong locale attibute type or there is no converter for custom attibute type");
		}
	}
}
}
