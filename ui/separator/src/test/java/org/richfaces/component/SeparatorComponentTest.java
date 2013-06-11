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

package org.richfaces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.renderkit.html.SeparatorRendererBase;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for Separator component.
 */
public class SeparatorComponentTest extends AbstractAjax4JsfTestCase {

    private UISeparator ui;
    private UIComponent form;
    private UIOutput out1;
    private UIOutput out2;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SeparatorComponentTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);


        out1 = (UIOutput) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        out1.setValue("output1");
        form.getChildren().add(out1);

        ui = (UISeparator) application.createComponent("org.richfaces.separator");
        ui.setId("separator");
        form.getChildren().add(ui);

        out2 = (UIOutput) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        out2.setValue("output2");
        form.getChildren().add(out2);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        ui = null;
        form = null;
        out1 = null;
        out2 = null;
    }

    /**
     * Test component rendering
     *
     * @throws Exception
     */
    public void testRender() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());

        HtmlElement div1 = page.getHtmlElementById(ui.getClientId(facesContext));
        assertNotNull(div1);
        assertEquals("div", div1.getNodeName());

        HtmlElement div2 = (HtmlElement) div1.getHtmlElementsByTagName("div").get(0);
        String classAttr = div2.getAttributeValue("class");
        assertTrue(classAttr.contains("rich-separator"));
        String styleAttr = div2.getAttributeValue("style");
        assertTrue(styleAttr.contains("org.richfaces.renderkit.html.images.BevelSeparatorImage"));
    }

    public void testRenderImage() throws Exception {
        InternetResourceBuilder builder = ResourceBuilderImpl.getInstance();

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_BEVEL);
        renderView();
        InternetResource resource = builder.getResource("org.richfaces.renderkit.html.images.BevelSeparatorImage");
        assertNotNull(resource);
        String uri = "http:" + resource.getUri(facesContext, ui);
        Page page = webClient.getPage(uri);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        ImageInfo info = new ImageInfo();
        info.setInput(page.getWebResponse().getContentAsStream());
        //image recognizable?
        assertTrue(info.check());
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_SOLID);
        renderView();
        resource = builder.getResource("org.richfaces.renderkit.html.images.SimpleSeparatorImage");
        assertNotNull(resource);
        uri = "http:" + resource.getUri(facesContext, ui);
        page = webClient.getPage(uri);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        info = new ImageInfo();
        info.setInput(page.getWebResponse().getContentAsStream());
        //image recognizable?
        assertTrue(info.check());
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_DOTTED);
        renderView();
        resource = builder.getResource("org.richfaces.renderkit.html.images.SimpleSeparatorImage");
        assertNotNull(resource);
        uri = "http:" + resource.getUri(facesContext, ui);
        page = webClient.getPage(uri);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        info = new ImageInfo();
        info.setInput(page.getWebResponse().getContentAsStream());
        //image recognizable?
        assertTrue(info.check());
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        
        ui.setLineType(SeparatorRendererBase.LINE_TYPE_DASHED);
        renderView();
        resource = builder.getResource("org.richfaces.renderkit.html.images.SimpleSeparatorImage");
        assertNotNull(resource);
        uri = "http:" + resource.getUri(facesContext, ui);
        page = webClient.getPage(uri);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        info = new ImageInfo();
        info.setInput(page.getWebResponse().getContentAsStream());
        //image recognizable?
        assertTrue(info.check());
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        
        ui.setLineType(SeparatorRendererBase.LINE_TYPE_DOUBLE);
        renderView();
        resource = builder.getResource("org.richfaces.renderkit.html.images.SimpleSeparatorImage");
        assertNotNull(resource);
        uri = "http:" + resource.getUri(facesContext, ui);
        page = webClient.getPage(uri);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        info = new ImageInfo();
        info.setInput(page.getWebResponse().getContentAsStream());
        //image recognizable?
        assertTrue(info.check());
        assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
    }
}
