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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Editor component Junit Test
 * @author Alexandr Levkovsky
 *
 */
public class EditorComponentTest extends AbstractAjax4JsfTestCase {
	
	/** UIForm instance */
	UIForm form;
	/** UIEditor instance */
	UIEditor editor;
	
	/** Set with required javascripts for Editor */
	private static Set<String> javaScripts = new HashSet<String>();

	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("scripts/tiny_mce/tiny_mce_src.js");
		javaScripts.add("scripts/editor.js");
	}

	/**
	 * Constructor with test name parameter
	 * @param name
	 */
	public EditorComponentTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);

		editor = (UIEditor) application.createComponent("org.richfaces.Editor");
		editor.setId("editor");
		editor.setValue("Some value");
		form.getChildren().add(editor);
	}
	
	/**
	 * Method to test if required style is present on page 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testEditorStyles() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List links = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"link");
		if (links.size() == 0) {
			fail();
		}
		for (int i = 0; i < links.size(); i++) {
			HtmlElement link = (HtmlElement) links.get(i);
			assertTrue(link.getAttributeValue("href").contains(
					"css/editor.xcss"));
		}
	}
	
	/**
	 * Method to test if required scripts is present on page 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void testEditorScripts() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(
				"script");

		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			String srcAttr = item.getSrcAttribute();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().toString();
				assertTrue(scriptBodyString.contains("RichEditor"));
			}
			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator srcIt = javaScripts.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();
					found = srcAttr.contains(src);
					if (found) {
						break;
					}
				}
				assertTrue(found);
			}
		}
	}
	
	/**
	 * Method to test Editor rendering
	 * @throws Exception
	 */
	public void testEditorRendering() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		HtmlElement htmlDiv = page.getHtmlElementById(editor
				.getClientId(facesContext));
		assertNotNull(htmlDiv);
		assertEquals(htmlDiv.getTagName(), "div");
		HtmlElement htmlTextArea = page.getHtmlElementById(editor
				.getClientId(facesContext)
				+ UIEditor.EDITOR_TEXT_AREA_ID_SUFFIX);
		assertNotNull(htmlTextArea);
		assertEquals(htmlTextArea.getTagName(), "textarea");
		String style = htmlTextArea.getAttribute("style");
		assertNotNull(style);
		assertTrue(style.contains("visibility: hidden"));
		String value = htmlTextArea.getFirstDomChild().asText();
		assertEquals("Some value", value);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
