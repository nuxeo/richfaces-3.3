/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.util.InputUtils;
import org.richfaces.component.UIEditor;

/**
 * Editor component renderer base class.
 * 
 * @author Alexandr Levkovsky
 *
 */
public class EditorRendererBase extends InputRendererBase {
	
	/** Specific script resource name which will be used to get server suffix */
	private final static String SPECIFIC_SCRIPT_RESOURCE_NAME = "org/richfaces/renderkit/html/1$1.js";
	/** Specific xcss resource name which will be used to get server suffix */
	private final static String SPECIFIC_XCSS_RESOURCE_NAME = "org/richfaces/renderkit/html/1$1.xcss";

	/** Editor viewMode attribute value which should disable rendering tinyMCE initialization */
	private final static String TINY_MCE_DISABLED_MODE = "source";
		
	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.InputRendererBase#getComponentClass()
	 */
	@Override
	protected Class<? extends UIComponent> getComponentClass() {
		return UIEditor.class;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.InputRendererBase#getConvertedValue(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public Object getConvertedValue(FacesContext context,
			UIComponent component, Object submittedValue)
			throws ConverterException {
		return InputUtils.getConvertedValue(context, component, submittedValue);
	}

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.InputRendererBase#doDecode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doDecode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId(context) + UIEditor.EDITOR_TEXT_AREA_ID_SUFFIX;
        Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
        String newValue = (String) requestParameterMap.get(clientId);
        if (null != newValue) {
    		UIInput input = (UIInput) component;
            input.setSubmittedValue(newValue);
        }
	}
	
	
	/**
	 * Method to get converted to String model value or if validation not passed submitted value for component
	 * 
	 * @param context - faces context instance
	 * @param component - component for which method is applied 
	 * @return converted to String component value
	 */
	protected String getFormattedComponentStringValue(FacesContext context,
			UIEditor component) {
		String fieldValue = (String) component.getSubmittedValue();
		if (fieldValue == null) {
			fieldValue = InputUtils.getConvertedStringValue(context, component,
					component.getValue());
		}
		return fieldValue;
	}
	
	/**
	 * Method to get exact script resource URI suffix
	 * 
	 * @param context - faces context instance
	 * @return string with script resource URI suffix
	 */
	protected String getSriptMappingSuffix(FacesContext context) {
		return getResourceSuffix(context, SPECIFIC_SCRIPT_RESOURCE_NAME);
	}
	
	/**
	 * Method to get exact xcss resource URI suffix
	 * 
	 * @param context - faces context instance
	 * @return string with xcss resource URI suffix
	 */
	protected String getCssMappingSuffix(FacesContext context) {
		return getResourceSuffix(context, SPECIFIC_XCSS_RESOURCE_NAME);
	}
	
	/**
	 * Method to get resource URI suffix which was added due to web.xml mappings
	 * 
	 * @param context - faces context instance
	 * @param resourceName - name of the resource which should be checked  
	 * @return string with resource URI suffix which was added after resource name
	 */
	private String getResourceSuffix(FacesContext context, String resourceName){
		InternetResource resource = getResource(resourceName);
		String resourceUri = resource.getUri(context, null);
		String suffix = resourceUri.substring(resourceUri.indexOf(resourceName) + resourceName.length());
		return suffix;
	}

	/**
	 * Method to write tinyMCE configuration script parameters from property
	 * file if it was determined through Editor configuration attribute.
	 * 
	 * @param context - faces context instance
	 * @param component - Editor component instance
	 * @throws IOException
	 */
	public void writeEditorConfigurationParameters(FacesContext context,
			UIEditor component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.writeText("var tinyMceParams = ", null);

		String parametersConfigName = component.getConfiguration();
		if (parametersConfigName != null && parametersConfigName.length() > 0) {
			loadPropertiesAndWrite(context, parametersConfigName, writer, false);
		} else {
			writer.writeText("{};\n", null);
		}
	}
	
	/**
	 * Method to write custom plugins script parameters from property
	 * file if it was determined through Editor configuration attribute.
	 * 
	 * @param context - faces context instance
	 * @param component - Editor component instance
	 * @throws IOException
	 */
	//XXX nick - merge with writeEditorConfigurationParameters() method
	public void writeEditorCustomPluginsParameters(FacesContext context,
			UIEditor component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		String customPluginsConfigName = component.getCustomPlugins();
		if (customPluginsConfigName != null && customPluginsConfigName.length() > 0) {
			loadPropertiesAndWrite(context, customPluginsConfigName, writer, true);
		}
	}
	
	/**
	 * Method to load configuration parameters from property file and write them
	 * @param context
	 * @param configName
	 * @param writer
	 * @param cutomPlugins
	 */
	private void loadPropertiesAndWrite(FacesContext context,
			String configName, ResponseWriter writer, boolean cutomPlugins) {
		Properties parameters = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();

			// XXX nick - use org.ajax4jsf.resource.util.URLToStreamHelper.urlToStream(URL)
			URL url = loader.getResource(configName + ".properties");
			InputStream is = URLToStreamHelper.urlToStream(url);
			if (is == null) {
				throw new FacesException(
						"Editor configuration properties file with name '"
								+ configName + "' was not found in class path");
			}

			// XXX nick - streams should be closed, in finally block
			try {
				parameters.load(is);
			} catch (Exception e) {
				throw new FacesException(e);
			} finally {
				is.close();
			}
			writer.writeText("\n", null);
			if (cutomPlugins) {
				writer.writeText(
						this.getCustomPluginsCode(context, parameters), null);
			} else {
				writer.writeText(this.convertProperties(parameters), null);
			}
			writer.writeText(";\n", null);
		} catch (IOException e) {
			throw new FacesException(e);
		}

	}

	/**
	 * Method to build string with parameters from property file
	 * 
	 * @param map - map with properties
	 * @return string with parameters in valid form for script
	 */
	@SuppressWarnings("unchecked")
	private String convertProperties(Map map) {
		StringBuilder ret = new StringBuilder("{");
		boolean first = true;
		for (Iterator<Map.Entry<Object, Object>> iter = map.entrySet()
				.iterator(); iter.hasNext();) {
			Map.Entry<Object, Object> entry = iter.next();
			if (!first) {
				ret.append(",\n");
			}

			ret.append(entry.getKey());
			ret.append(":");
			ret.append(entry.getValue());
			first = false;
		}
		return ret.append("} ").toString();
	}
	
	/**
	 * Method to write script for loading custom plugins
	 * 
	 * @param context - faces context instance
	 * @param map - properties map
	 * @return string with script
	 */
	@SuppressWarnings("unchecked")
	private String getCustomPluginsCode(FacesContext context, Map map) {
		StringBuilder ret = new StringBuilder();
		for (Iterator<Map.Entry<Object, Object>> iter = map.entrySet()
				.iterator(); iter.hasNext();) {
			Map.Entry<Object, Object> entry = iter.next();

			ret.append("tinymce.PluginManager.load('");
			ret.append(entry.getKey());
			ret.append("','");
			ret.append(context.getExternalContext().getRequestContextPath()+entry.getValue());
			ret.append("',null,{richfaces:true});");
		}
		return ret.toString();
	}	
	
	/**
	 * Method to write tinyMCE configuration script parameters from Editor component attributes.
	 * 
	 * @param context - faces context instance
	 * @param component - Editor component instance
	 * @throws IOException
	 */
	public void writeEditorConfigurationAttributes(FacesContext context,
			UIEditor component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();

		String theme = component.getTheme();
		String language = component.getLanguage();
		String plugins = component.getPlugins();
		String oninit = component.getOninit();
		String onsave = component.getOnsave();
		String onchange = component.getOnchange();
		String onsetup = component.getOnsetup();
		String dialogType = component.getDialogType();
		String skin = component.getSkin();
		Integer width = component.getWidth();
		Integer height = component.getHeight();

		if (theme != null && theme.length() > 0) {
			writer.writeText("tinyMceParams.theme = "
					+ ScriptUtils.toScript(theme) + ";\n", null);
		}
		if (language != null && language.length() > 0) {
			writer.writeText("tinyMceParams.language = "
					+ ScriptUtils.toScript(language) + ";\n", null);
		}
		writer.writeText("tinyMceParams.auto_resize = "
				+ ScriptUtils.toScript(component.isAutoResize()) + ";\n", null);

		writer.writeText("tinyMceParams.readonly = "
				+ ScriptUtils.toScript(component.isReadonly()) + ";\n", null);

		if (plugins != null && plugins.length() > 0) {
			writer.writeText("tinyMceParams.plugins = "
					+ ScriptUtils.toScript(plugins) + ";\n", null);
		}
		if (width != null) {
			writer.writeText("tinyMceParams.width = "
					+ ScriptUtils.toScript(width) + ";\n", null);
		}
		if (height != null) {
			writer.writeText("tinyMceParams.height = "
					+ ScriptUtils.toScript(height) + ";\n", null);
		}

		// XXX nick - use local variables
		if (oninit != null && oninit.length() > 0) {
			writer.writeText("tinyMceParams.oninit = function (event) {\n"
					+ oninit + "\n" + "};\n", null);
		}
		if (onsave != null && onsave.length() > 0) {
			writer
					.writeText(
							"tinyMceParams.save_callback = function (event, element_id, html, body) {\n		return "
									+ onsave + "\n" + "};\n", null);
		}
		if (onchange != null && onchange.length() > 0) {
			writer.writeText(
					"tinyMceParams.onchange_callback = function (event, inst) {\n"
							+ onchange + "\n" + "};\n", null);
		}
		if (onsetup != null && onsetup.length() > 0) {
			writer.writeText("tinyMceParams.setup = function (event, ed) {\n"
					+ onsetup + "\n" + "};\n", null);

		}
		if (dialogType != null && dialogType.length() > 0) {
			writer.writeText("tinyMceParams.dialog_type = "
					+ ScriptUtils.toScript(dialogType) + ";\n", null);
		}
		if (skin != null && skin.length() > 0) {
			writer.writeText("tinyMceParams.skin = "
					+ ScriptUtils.toScript(skin) + ";\n", null);
		} else {
			writer.writeText("if(!tinyMceParams.skin){\n", null);
			writer.writeText("	tinyMceParams.skin = 'richfaces';\n", null);
			writer.writeText("}\n", null);
		}

		writer.writeText("if(tinyMceParams.strict_loading_mode == null){\n",
				null);
		writer.writeText("	tinyMceParams.strict_loading_mode = true;\n", null);
		writer.writeText("}\n", null);
	}

	/**
	 * Method to write tinyMCE configuration script parameters which was defined
	 * as <f:param> children for Editor
	 * 
	 * @param context -
	 *            faces context instance
	 * @param component -
	 *            Editor component instance
	 * @throws IOException
	 */
	public void writeEditorParameters(FacesContext context,
			UIComponent component) throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		List<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {
			if (child instanceof UIParameter) {
				UIParameter parameter = (UIParameter) child;
				StringBuilder b = new StringBuilder();
				b.append("tinyMceParams.");
				ScriptUtils.addEncoded(b, parameter.getName());
				b.append(" = ");
				if (parameter.getValue() != null
						&& (parameter.getValue().equals("true") || parameter
								.getValue().equals("false"))) {
					ScriptUtils.addEncoded(b, parameter.getValue());
				} else {
					b.append(ScriptUtils.toScript(parameter.getValue()));
				}
				b.append(";\n");
				writer.writeText(b.toString(), null);
			}
		}
	}

	/**
	 * Method to check if rendering of tinyMCE scripts needed. 
	 * 
	 * @param component - Editor component instance
	 * @return true if needed or false if only target textarea should be rendered 
	 */
	public boolean shouldRenderTinyMCE(UIEditor component) {
		//XXX nick - use TINY_MCE_DISABLED_MODE.equalsIgnoreCase(component.getViewMode())
		if (TINY_MCE_DISABLED_MODE.equalsIgnoreCase(component.getViewMode())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method to get target textarea style if width or height attributes was determined
	 * 
	 * @param component - Editor component instance
	 * @return string with style properties
	 */
	public String getTextAreaStyle(UIEditor component) {
		StringBuilder b = new StringBuilder();
		if (component.getWidth() != null) {
			b.append("width: " + component.getWidth() + "px;");
		}
		if (component.getHeight() != null) {
			b.append("height: " + component.getHeight() + "px;");
		}
		return b.toString();
	}
	
	
}
