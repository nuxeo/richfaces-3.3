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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.faces.component.UIParameter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIEditor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

public class EditorRendererTest extends AbstractAjax4JsfTestCase {

	/** Name of test property file with configuration parameters */
	private final static String PARAMETERS_PROPERTY_FILE_NAME = "editorFull";
	/** Name of test property file with custom plugins parameters */
	private final static String CUSTOM_PLUGINS_PROPERTY_FILE_NAME = "myplugins";
	
	/** Test f:param name */
	private final static String FPARAM_NAME = "fparam_name";
	/** Test f:param value */
	private final static String FPARAM_VALUE = "fparam_value";
	
	/** UIEditor instance */
	private UIEditor editor;

	/**
	 * Constructor with test name parameter
	 * @param name
	 */
	public EditorRendererTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		editor = (UIEditor) application.createComponent("org.richfaces.Editor");
		editor.setValue("Some value");

		UIParameter param1 = (UIParameter) application
				.createComponent("javax.faces.Parameter");
		param1.setName(FPARAM_NAME);
		param1.setValue(FPARAM_VALUE);
		editor.getChildren().add(param1);

		facesContext.getViewRoot().getChildren().add(editor);
	}


	/**
	 * Method to test EditorRenderer writing configuration script parameters
	 * from properties file.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testWritingScriptParametersFromProperties() throws Exception {

		editor.setConfiguration(PARAMETERS_PROPERTY_FILE_NAME);
		Properties parameters = loadProperties(PARAMETERS_PROPERTY_FILE_NAME);

		HtmlPage page = renderView();
		assertNotNull(page);

		List scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"script");

		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().toString();
				for (Object key : parameters.keySet()) {
					String keyString = key.toString();
					String valueString = (parameters.get(key)).toString();
					if (valueString.indexOf("\"") != -1) {
						valueString = valueString.replaceAll("\"", "");
					}
					assertTrue("writed script parameters not contains '"
							+ keyString + "'", scriptBodyString
							.contains(keyString));
					assertTrue("writed script parameters not contains '"
							+ valueString + "'", scriptBodyString
							.contains(valueString));
				}
			}

		}
	}

	/**
	 * Method to test EditorRenderer writing configuration script parameters
	 * from f:params children.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testWritingScriptParametersFromFParams() throws Exception {

		HtmlPage page = renderView();
		assertNotNull(page);

		List scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"script");

		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().toString();
				assertTrue(
						"writed script parameters not contains defined in Editor f:param",
						scriptBodyString.contains(FPARAM_NAME));
				assertTrue(
						"writed script parameters not contains defined in Editor f:param",
						scriptBodyString.contains(FPARAM_VALUE));
			}

		}

	}

	/**
	 * Method to test EditorRenderer writing configuration script parameters
	 * from UIEditor attributes.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testWritingScriptParametersFromAttributes() throws Exception {
		editor.setAutoResize(true);
		editor.setDialogType("window");
		editor.setHeight(300);
		editor.setWidth(300);
		editor.setLanguage("ru");
		editor.setPlugins("safari,spellchecker,pagebreak");
		editor.setReadonly(true);
		editor.setSkin("o2k7");
		editor.setTheme("advanced");

		HtmlPage page = renderView();
		assertNotNull(page);

		List scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"script");
		
		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().toString();
				assertTrue(scriptBodyString
						.contains("tinyMceParams.auto_resize = true"));
				assertTrue(scriptBodyString
						.contains("tinyMceParams.dialog_type ="));
				assertTrue(scriptBodyString.contains(editor.getDialogType()));
				assertTrue(scriptBodyString
						.contains("tinyMceParams.width = 300"));
				assertTrue(scriptBodyString
						.contains("tinyMceParams.height = 300"));
				assertTrue(scriptBodyString
						.contains("tinyMceParams.language ="));
				assertTrue(scriptBodyString.contains(editor.getLanguage()));
				assertTrue(scriptBodyString.contains("tinyMceParams.plugins ="));
				assertTrue(scriptBodyString.contains(editor.getPlugins()));
				assertTrue(scriptBodyString.contains("tinyMceParams.theme ="));
				assertTrue(scriptBodyString.contains(editor.getTheme()));
				assertTrue(scriptBodyString.contains("tinyMceParams.skin ="));
				assertTrue(scriptBodyString.contains(editor.getSkin()));
				assertTrue(scriptBodyString
						.contains("tinyMceParams.readonly = true"));
			}

		}

	}
	
	/**
	 * Method to test EditorRenderer writing custom plugins loading calls from
	 * properties file.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testWritingScriptParametersForCustomPlugins() throws Exception {

		editor.setCustomPlugins(CUSTOM_PLUGINS_PROPERTY_FILE_NAME);
		Properties parameters = loadProperties(CUSTOM_PLUGINS_PROPERTY_FILE_NAME);

		HtmlPage page = renderView();
		assertNotNull(page);

		List scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"script");

		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().toString();
				for (Object key : parameters.keySet()) {
					String keyString = key.toString();
					String valueString = (parameters.get(key)).toString();
					if (valueString.indexOf("\"") != -1) {
						valueString = valueString.replaceAll("\"", "");
					}
					assertTrue(scriptBodyString.contains("tinymce.PluginManager.load("));
					assertTrue(scriptBodyString.contains(keyString));
					assertTrue(scriptBodyString.contains(valueString));
				}
			}

		}
	}

	/**
	 * Method to load properties from property file
	 * 
	 * @param name - properties file name
	 * @return Properties
	 * @throws IOException
	 */
	private Properties loadProperties(String name) throws IOException {
		Properties properties = new Properties();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(name + ".properties");
		if (is == null) {
			fail("Can't load propertioes file for test");
		}
		properties.load(is);
		return properties;
	}

}
