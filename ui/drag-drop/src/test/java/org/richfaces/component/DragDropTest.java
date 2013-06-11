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
import java.util.NoSuchElementException;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlForm;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.event.EventsQueue;
import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockViewRoot;
import org.apache.commons.collections.Buffer;
import org.apache.commons.lang.StringUtils;
import org.richfaces.renderkit.DraggableRendererContributor;
import org.richfaces.renderkit.DropzoneRendererContributor;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for simple Component.
 */
public class DragDropTest extends AbstractAjax4JsfTestCase {

	private UIDndParam dndparam;

	private UIDragIndicator dndindicator;

	private UIDragSupport dragsupport;

	private UIDropSupport dropsupport;

	private UIPanel panel1, panel2;

	private UIComponent form;

	private static final String CSS_FILE_PATH = "org/richfaces/renderkit/html/css/dragIndicator.xcss";

	private static Set javaScripts = new HashSet();
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("scripts/browser_info.js");
		javaScripts.add("org.ajax4jsf.javascript.DnDScript");
		javaScripts.add("scripts/events.js");
		javaScripts.add("scripts/utils.js");
		javaScripts.add("scripts/simple-draggable.js");
		javaScripts.add("scripts/simple-dropzone.js");
		javaScripts.add("scripts/json/json-mini.js");
		javaScripts.add("scripts/json/json-dom.js");
		javaScripts.add("scripts/dnd/dnd-common.js");
		javaScripts.add("scripts/dnd/dnd-dropzone.js");
		javaScripts.add("scripts/dnd/dnd-draggable.js");
		javaScripts.add("scripts/drag-indicator.js");
	}
	private static Set eventsSet = new HashSet();
	static {
		eventsSet.add("org.richfaces.component.html.HtmlDragSupport");
		eventsSet.add("org.richfaces.component.html.HtmlDropSupport");

	}

	public DragDropTest(String testName) {
		super(testName);
	}

	public void setUp() throws Exception {

		super.setUp();

		form = new HtmlForm();
		assertNotNull(form);
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);

		panel1 = new UIPanel();
		panel1.setId("panel1");

		panel2 = new UIPanel();
		panel2.setId("panel2");

		dndparam = (UIDndParam) application
				.createComponent("org.richfaces.DndParam");
		dndparam.setId("dndparam");

		dndindicator = (UIDragIndicator) application
				.createComponent(UIDragIndicator.COMPONENT_TYPE);
		dndindicator.setId("dndindicator");

		dragsupport = (UIDragSupport) application
				.createComponent("org.richfaces.DragSupport");
		dragsupport.setId("dragsupport");
		dragsupport.setDragType("foto");
		// panel1.getChildren().add(dragsupport);

		dropsupport = (UIDropSupport) application
				.createComponent("org.richfaces.DropSupport");
		dropsupport.setId("dropsupport");
		dropsupport.setAcceptedTypes("foto");
		// panel2.getChildren().add(dropsupport);

		form.getChildren().add(panel1);
		form.getChildren().add(dndindicator);
		form.getChildren().add(dndparam);
		form.getChildren().add(dragsupport);
		form.getChildren().add(dropsupport);

		form.getChildren().add(panel2);

	}

	public void tearDown() throws Exception {
		super.tearDown();

		this.form = null;
		this.dndindicator = null;
		this.dragsupport = null;
		this.dropsupport = null;
		this.dndparam = null;
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testComponent() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);

		HtmlElement htmlDnDIndicator = page.getHtmlElementById(dndindicator
				.getClientId(facesContext));
		assertTrue(htmlDnDIndicator.getAttributeValue("class").contains(
				"drag_indicator"));

		// HtmlElement htmlDnDParam =
		// page.getHtmlElementById(dndparam.getClientId(facesContext));
		// assertTrue(htmlDnDParam.getNodeName().equals("script"));

		HtmlElement htmlDragSupport = page.getHtmlElementById(dragsupport
				.getClientId(facesContext));
		assertTrue(htmlDragSupport.getNodeName().equals("script"));
		assertTrue(dragsupport.getDragType().equals("foto"));

		HtmlElement htmlDropSupport = page.getHtmlElementById(dropsupport
				.getClientId(facesContext));
		assertTrue(htmlDropSupport.getNodeName().equals("script"));
		assertTrue(dropsupport.getAcceptedTypes().equals("foto"));
	}

	public void testScript() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List scripts = page.getDocumentElement().getHtmlElementsByTagName(
				"script");
		//System.out.println(page.asXml());
		for (Iterator it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			String srcAttr = item.getSrcAttribute();

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

	public void testDecode() throws Exception {
		externalContext.addRequestParameterMap(
				DraggableRendererContributor.DRAG_SOURCE_ID, dragsupport
						.getClientId(facesContext));
		externalContext.addRequestParameterMap(
				DropzoneRendererContributor.DROP_TARGET_ID, dropsupport
						.getClientId(facesContext));

		dragsupport.decode(facesContext);
		dropsupport.decode(facesContext);
		
		
		

		MockViewRoot root = (MockViewRoot) facesContext.getViewRoot();
		EventsQueue queue = root
				.getEventsQueue(PhaseId.ANY_PHASE);
		assertNotNull(queue);
		while (true) {
			try {
				FacesEvent event = queue.remove();
				boolean found = false;
				for (Iterator srcIt = eventsSet.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();
					found = event.toString().contains(src);
					if (found) {
						break;
					}
				}
				assertTrue(found);
			} catch (NoSuchElementException e) {
				break;
			}
		}

	}

	/**
	 * Test style rendering
	 * 
	 * @throws Exception
	 */
	public void testRenderStyle() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List links = page.getDocumentElement().getHtmlElementsByTagName("link");
		assertEquals(1, links.size());
		HtmlElement link = (HtmlElement) links.get(0);
		assertTrue(link.getAttributeValue("href").contains(CSS_FILE_PATH));

		assertNotNull(getResourceIfPresent(CSS_FILE_PATH));
	}
}
