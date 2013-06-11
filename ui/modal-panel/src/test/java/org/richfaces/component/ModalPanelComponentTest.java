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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class ModalPanelComponentTest extends AbstractAjax4JsfTestCase {
	private static Set javaScripts = new HashSet();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
	
	protected String[] RESIZERS = new String[] {
			"NWU", "N", "NEU", "NEL", "E",
			"SEU", "SEL", "S", "SWL",
			"SWU",	"W", "NWL"
		};

    static {
    	javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
    	javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("scripts/utils.js");
        javaScripts.add("scripts/modalPanel.js");
        javaScripts.add("scripts/modalPanelBorders.js");
        javaScripts.add("scripts/browser_info.js");
    }
	
    private UIModalPanel modalPanel;
    private UIComponent form;
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ModalPanelComponentTest( String testName )
    {
        super( testName );
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        
        form = new HtmlForm();
    	facesContext.getViewRoot().getChildren().add(form);
    	
    	modalPanel = (UIModalPanel) application.createComponent(UIModalPanel.COMPONENT_TYPE);
    	modalPanel.setId("modalPanel");
    	
    	HtmlOutputText outputText = new HtmlOutputText();
    	outputText.setValue("test");
    	modalPanel.getFacets().put("header", outputText);
    	modalPanel.setKeepVisualState(true);
    	modalPanel.setVisualOptions("border: 1px");
    	form.getChildren().add(modalPanel);
    }
    
    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        form = null;
        modalPanel = null;
    }
    
    /**
     * Test component renders correctly
     *  
     * @throws Exception
     */
    public void testRender() throws Exception {
    	HtmlPage renderedView = renderView();
    	
    	assertNotNull(renderedView);
    	
    	HtmlElement htmlModalPanel = renderedView.getHtmlElementById(modalPanel.getClientId(facesContext));
    	assertNotNull(htmlModalPanel);
    	assertEquals("div", htmlModalPanel.getNodeName());
    	
    	HtmlElement htmlModalPanelCDiv = renderedView.getHtmlElementById(modalPanel.getClientId(facesContext) + "CDiv");
    	assertNotNull(htmlModalPanelCDiv);
    	
    	HtmlElement htmlContentDiv = renderedView.getHtmlElementById(modalPanel.getClientId(facesContext) + "ContentDiv");
    	assertNotNull(htmlContentDiv);
    	assertTrue(htmlContentDiv.getAttributeValue("class").contains("rich-mp-content"));
    	
    	HtmlElement header = renderedView.getHtmlElementById(modalPanel.getClientId(facesContext) + "Header");
    	assertNotNull(header);
    	
    	assertTrue(header.getAttributeValue("class").contains("dr-mpnl-pnl-text dr-mpnl-pnl-h"));
    	assertTrue(header.getAttributeValue("class").contains("rich-mpnl-text"));
    	assertTrue(header.getAttributeValue("class").contains("rich-mpnl-header"));
    	
    	// Test resizers
    	String[] resizers = RESIZERS;
    	for (int i = 0; i < resizers.length; i++) {
    		HtmlElement resizer = renderedView.getHtmlElementById(modalPanel.getClientId(facesContext) + "Resizer" + resizers[i]);
    		assertNotNull(resizer);
    		assertTrue(resizer.getAttributeValue("class").contains("dr-mpnl-resizer rich-mpnl-resizer"));
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
        assertTrue(link.getAttributeValue("href").contains("org/richfaces/renderkit/html/css/modalPanel.xcss"));
    }
    
    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
    	modalPanel.setShowWhenRendered(true);
        HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }
    
    public void testDecode() throws Exception {
    	HtmlPage renderedView = renderView();
    	
    	assertNotNull(renderedView);
    	modalPanel.decode(facesContext);
    	
    }
    
}
