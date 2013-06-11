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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.PropertyNotFoundException;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlForm;
import javax.faces.el.MethodNotFoundException;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for SuggestionBox component.
 */
public class SuggestionBoxComponentTest extends AbstractAjax4JsfTestCase {
    private static Set javaScripts = new HashSet();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;
    private static String[] SUGGESTION = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    static {
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org.ajax4jsf.javascript.SmartPositionScript");
        javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/scriptaculous/effects.js");
        javaScripts.add("scripts/suggestionbox.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/available.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/jquery/jquery.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
    }

    private UISuggestionBox sb;
    private UIInput input;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SuggestionBoxComponentTest(String testName) {
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

        input = new UIInput();
        input.setId("text");
        form.getChildren().add(input);

        sb = (UISuggestionBox)application.createComponent("org.richfaces.SuggestionBox");
        sb.setId("suggestionBox");
        sb.setFor(input.getId());
        sb.setUsingSuggestObjects(true);
        sb.setSuggestionAction(action);

        form.getChildren().add(sb);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        sb = null;
        form = null;
        input = null;
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

        HtmlElement div = page.getHtmlElementById(sb.getClientId(facesContext));
        assertNotNull(div);
        assertEquals("div", div.getNodeName());

        String classAttr = div.getAttributeValue("class");
        assertTrue(classAttr.contains("dr-sb-common-container"));
        assertTrue(classAttr.contains("rich-sb-common-container"));

        HtmlElement table = page.getHtmlElementById(sb.getClientId(facesContext) + ":suggest");
        assertNotNull(table);
        assertEquals("table", table.getNodeName());
        HtmlElement iframe = page.getHtmlElementById(sb.getClientId(facesContext) + "_iframe");
        assertNotNull(iframe);
        assertEquals("iframe", iframe.getNodeName());
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
        assertTrue(link.getAttributeValue("href").contains("org/richfaces/renderkit/html/css/suggestionbox.xcss"));
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }
    
    public void testBroadcast() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        sb.setVar("item");
        sb.setValueExpression("fetchValue", application.getExpressionFactory().createValueExpression(elContext, "#{item}", Object.class));
        sb.setSubmitedValue(null, new String[]{"1", "2", "11"});
        
        sb.broadcast(new AjaxEvent(sb));
        AjaxContext ajaxContext = AjaxContext.getCurrentInstance(facesContext); 
        assertFalse(ajaxContext.getAjaxAreasToRender().isEmpty());
        assertTrue(ajaxContext.getAjaxAreasToRender().iterator().next().contains(sb.getClientId(facesContext)));
        Map<String, Object> ajaxResponseData = ajaxContext.getResponseDataMap();
        Map<String, Object>  data = (Map<String, Object>) ajaxResponseData.get("_ajax:data");
        Object suggestinObjects = data.get("suggestionObjects");
        assertNotNull(suggestinObjects);
        assertEquals(Arrays.asList(SUGGESTION), suggestinObjects);
        Map requestedObjectsObjects = (Map)data.get("requestedObjects");
        assertNotNull(requestedObjectsObjects);
        assertEquals(2, requestedObjectsObjects.size());
        assertEquals(requestedObjectsObjects.get("1"), "1");
        assertEquals(requestedObjectsObjects.get("2"), "2");
        assertNull(requestedObjectsObjects.get("11"));
    }
    
    public void testDecode() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		sb.setFetchValue("1");
		sb.setSubmitedValue(Arrays.asList(SUGGESTION), new String[] { "1" });
		facesContext.getExternalContext().getRequestParameterMap().put(
				sb.getClientId(facesContext) + "_selection", "1");

		sb.processDecodes(facesContext);
		assertEquals(1, sb.getRowNumber());
	}
    
    MethodExpression action = new MethodExpression(){
		
		public Object invoke(ELContext context, Object[] params) throws PropertyNotFoundException, MethodNotFoundException,
        ELException  {
			return Arrays.asList(SUGGESTION); 
		}
		
		public MethodInfo getMethodInfo(ELContext context) {
		
			return null;
		}

		public boolean equals(Object obj) {
			 return (obj instanceof MethodExpression && obj.hashCode() == this.hashCode());
		}

		
		public String getExpressionString() {
			return null;
		}

		public int hashCode() {
			 return 0;
		}

		public boolean isLiteralText() {
			return false;
		}
		
	};

    
}
