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

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.render.Renderer;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlPanel;
import org.richfaces.renderkit.html.PanelRenderer;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class PanelComponentTest 
    extends AbstractAjax4JsfTestCase
{

	
	HtmlPanel htmlPanel;
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PanelComponentTest( String testName )
    {
        super( testName );
    }

    public void setUp() throws Exception {
    	super.setUp();
		htmlPanel = new HtmlPanel();
    	htmlPanel.setId("panel");
    	htmlPanel.setRendererType("panelRenderer");
		facesContext.getViewRoot().getChildren().add(htmlPanel);
		renderKit.addRenderer(HtmlPanel.COMPONENT_FAMILY, "panelRenderer", new PanelRenderer());
    }

    public void tearDown() throws Exception {
    	super.tearDown();
    	htmlPanel = null;
    }
    /**
     * Rigourous Test :-)
     * @throws Exception 
     */
    public void testRender() throws Exception
    {
    	HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById("panel");
        assertNotNull(div);
        assertEquals("div", div.getNodeName());
    }
    
    /**
     * Rigourous Test :-)
     * @throws Exception 
     */
    public void testRenderStyle() throws Exception
    {
    	HtmlPage page = renderView();
        assertNotNull(page);
        List elementsByTagName = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, elementsByTagName.size());
        HtmlElement link = (HtmlElement) elementsByTagName.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/panel.xcss"));
    }

    /**
     * Rigourous Test :-)
     * @throws Exception 
     */
    public void testRenderHeader() throws Exception
    {
    	UIComponent text = createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(), null, null , null);
    	htmlPanel.getFacets().put("header", text);
    	HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById("panel_header");
        assertNotNull(div);
    }

    /**
     * Rigourous Test :-)
     * @throws Exception 
     */
    public void testRenderAttributes() throws Exception
    {
    	Object[][] attributes ={{"style","xxx","style","xxx"},{"onclick","alert()","onclick","alert()"},{"styleClass","yyy","class","dr-pnl rich-panel yyy"}};
    	UIComponent text = createComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class.getName(), null, null , null);
    	htmlPanel.getFacets().put("header", text);
    	for (int i = 0; i < attributes.length; i++) {
			Object[] attr = attributes[i];
			htmlPanel.getAttributes().put((String)attr[0], attr[1]);
		}
    	HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        HtmlElement div = page.getHtmlElementById("panel");
        assertNotNull(div);
    	for (int i = 0; i < attributes.length; i++) {
			Object[] attr = attributes[i];
			assertEquals(attr[3],div.getAttributeValue((String) attr[2]));
		}
        
    }
}
