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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIColumn;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlOutputText;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;
import org.richfaces.component.UIOrderingList;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * @author Siarhej Chalipau
 *
 */
public class OrderingListRenderingTest extends AbstractAjax4JsfTestCase {
	private static final String IMAGE_ALT = "test_alt";

	private static Set<String> javaScripts = new HashSet<String>();
	private static Set<String> imageClasses = new HashSet<String>();
	private static Set<String> imagePNGClasses = new HashSet<String>();
	
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("scripts/utils.js");
		javaScripts.add("scripts/ShuttleUtils.js");
		javaScripts.add("scripts/SelectItem.js");
		javaScripts.add("scripts/LayoutManager.js");
		javaScripts.add("scripts/Control.js");
		javaScripts.add("scripts/OrderingList.js");
		javaScripts.add("scripts/ListBase.js");
		
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconUp");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconTop");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconBottom");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconUpDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconDownDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconTopDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconBottomDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconDown");
		
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListHeaderGradient");
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListClickedGradient");
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListButtonGradient");

	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OrderingListRenderingTest( String testName )
    {
        super( testName );
    }
    
    private final static int ROWS_COUNT = 3;
    
    private UIForm form = null;
    private UIOrderingList orderingList = null;
    private List<SimpleItem> items = null;
    private HtmlOutputText output1 = null;
    private HtmlOutputText output2 = null;
    private UIColumn column1 = null;
    private UIColumn column2 = null;
    private HtmlOutputText caption = null;

    public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent("org.richfaces.OrderingList", "org.richfaces.component.html.HtmlOrderingList");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
    	
    	orderingList = (UIOrderingList)application.createComponent("org.richfaces.OrderingList");
    	orderingList.setId("orderingList");
    	orderingList.setControlsType("link");
    	orderingList.setVar("item");
    	orderingList.getAttributes().put("topTitle", IMAGE_ALT);

    	caption =  new HtmlOutputText();
    	caption.setValue("caption");
    	orderingList.getFacets().put("caption", caption);
    	
    	items = new ArrayList<SimpleItem>();
    	items.add(new SimpleItem("Michael", 2000));
    	items.add(new SimpleItem("John", 500));
    	items.add(new SimpleItem("George", 4000));
    	
    	orderingList.setValue(items);
    	
    	output1 = new HtmlOutputText();
    	output1.setValueBinding("value", application.createValueBinding("#{item.name}"));
    	column1 = new UIColumn();
    	column1.getChildren().add(output1);
    	orderingList.getChildren().add(column1);
    	
    	output2 = new HtmlOutputText();
    	output2.setValueBinding("value", application.createValueBinding("#{item.salary}"));
    	column2 = new UIColumn();
    	column2.getChildren().add(output2);
    	orderingList.getChildren().add(column2);
    	
    	form.getChildren().add(orderingList);
    }
    
    public void tearDown() throws Exception {
    	items = null;
    	output1 = null;
    	output2 = null;
    	column1 = null;
    	column2 = null;
    	orderingList = null;
    	form = null;
    	
    	super.tearDown();
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
    	HtmlPage page = renderView();
        assertNotNull(page);
        List<HtmlScript> scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.SCRIPT_ELEM);
        int foundCount = 0;
        for (Iterator<HtmlScript> it = scripts.iterator(); it.hasNext();) {
            HtmlScript item = it.next();
            String srcAttr = item.getSrcAttribute();

            if (StringUtils.isNotBlank(srcAttr)) {
                boolean found = false;
                for (Iterator<String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
                    String src = srcIt.next();

                    found = srcAttr.contains(src);
                    if (found) {
                    	foundCount++;
                        break;
                    }
                }

                assertTrue(found);
            }
        }
        assertEquals(foundCount, javaScripts.size());
    }
    
    /**
     * Test default images rendering
     *
     * @throws Exception
     */
    public void testRenderImages() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
       
        for (Iterator<String> it = imageClasses.iterator(); it.hasNext(); ) {
        	String clazz = it.next();
        	ImageInfo info = getImageResource(clazz);
    		assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
            assertTrue(info.getHeight() > 0);
            assertTrue(info.getWidth() > 0);
        }
        
        for (Iterator<String> it = imagePNGClasses.iterator(); it.hasNext(); ) {
        	String clazz = it.next();
        	ImageInfo info = getImageResource(clazz);
    		assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
            assertTrue(info.getHeight() > 0);
            assertTrue(info.getWidth() > 0);
        }
    }
    
    /**
     * Test style rendering
     *
     * @throws Exception
     */
    public void testRenderStyle() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        List<HtmlElement> links = view.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.LINK_ELEMENT);
        assertNotNull(links);
        assertEquals(1, links.size());
        HtmlElement link = links.get(0);
        assertTrue(link.getAttributeValue(HTML.HREF_ATTR).contains("css/orderingList.xcss"));
        
        String uri = "http:" + link.getAttributeValue(HTML.HREF_ATTR);
        Page page = webClient.getPage(uri);
        assertNotNull(page);
        assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
    }
    
    /**
     * Test controls rendering
     * 
     * @throws Exception
     */
    public void testRenderControls() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        List<HtmlElement> images = view.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.IMG_ELEMENT);
        assertNotNull(images);
        
        int generatedCount = 0;
        for (Iterator<HtmlElement> it = images.iterator(); it.hasNext(); ) {
        	HtmlElement img = it.next();
        	assertNotNull(img);
        	
        	String uri = "http:" + img.getAttributeValue(HTML.src_ATTRIBUTE);
        	Page page = webClient.getPage(uri);
        	assertNotNull(page);
        	assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
        	
        	if (uri.contains("spacer.gif")) {
        		continue;
        	}
        	
        	String alt = img.getAttributeValue(HTML.alt_ATTRIBUTE);
        	assertNotNull(alt);
        	if (alt.length() > 0) {
        	    assertEquals(IMAGE_ALT, alt);
        	} 

        	generatedCount++;
        	
        	HtmlElement element = (HtmlElement) img.getParentDomNode();
        	assertNotNull(element);
        	assertEquals(HTML.DIV_ELEM, element.getNodeName());
        	String clazz = element.getAttributeValue(HTML.class_ATTRIBUTE);
        	assertNotNull(clazz);
        	assertTrue("rich-ordering-list-button-content".equals(clazz));
        	
        	element = (HtmlElement) element.getParentDomNode();
        	assertNotNull(element);
        	if (HTML.a_ELEMENT.equalsIgnoreCase(element.getNodeName())) {
        		element = (HtmlElement) element.getParentDomNode();
        		assertNotNull(element);
        	}
        	
        	assertEquals(HTML.DIV_ELEM, element.getNodeName());
        	
        	element = (HtmlElement) element.getParentDomNode();
        	assertNotNull(element);
        	
        	assertEquals(HTML.DIV_ELEM, element.getNodeName());
        	clazz = element.getAttributeValue(HTML.class_ATTRIBUTE);
        	assertNotNull(clazz);
        	assertTrue(clazz.contains("rich-ordering-control-"));
        	
        	element = (HtmlElement) element.getParentDomNode();
        	assertNotNull(element);
        	assertEquals(HTML.DIV_ELEM, element.getNodeName());
        	clazz = element.getAttributeValue(HTML.class_ATTRIBUTE);
        	assertNotNull(clazz);
        	assertTrue(clazz.contains("rich-ordering-list-button-layout"));
        }
        assertEquals(8, generatedCount);
    }
    
    /**
     * Test items rendering
     * 
     * @throws Exception
     */
    public void testRenderItems() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        HtmlElement div = view.getHtmlElementById(orderingList.getClientId(facesContext) + "contentBox");
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        
        HtmlElement table = (HtmlElement)div.getFirstDomChild();
        assertNotNull(table);
        assertEquals(HTML.TABLE_ELEMENT, table.getNodeName());
        
        HtmlElement tbody = (HtmlElement)table.getFirstDomChild();
        assertNotNull(tbody);
        assertEquals(HTML.TBOBY_ELEMENT, tbody.getNodeName());
        
        int rowsCount = 0;
        for (Iterator<HtmlElement> it = tbody.getChildIterator(); it.hasNext(); ) {
        	HtmlElement tr = it.next();
        	assertNotNull(tr);
            assertEquals(HTML.TR_ELEMENT, tr.getNodeName());
            
            String clazz = tr.getAttributeValue(HTML.class_ATTRIBUTE);
            assertNotNull(clazz);
            assertTrue(clazz.contains("rich-ordering-list-row"));
            
            int cellsCount = 0;
            for (Iterator<HtmlElement> it2 = tr.getChildIterator(); it2.hasNext(); ) {
            	HtmlElement td = it2.next();
            	assertNotNull(td);
                assertEquals(HTML.td_ELEM, td.getNodeName());
                
                String clazz2 = td.getAttributeValue(HTML.class_ATTRIBUTE);
                assertNotNull(clazz2);
                assertTrue(clazz2.contains("rich-ordering-list-cell"));
                
                cellsCount++;
            }
            assertEquals(2, cellsCount);
            
            rowsCount++;
        }
        assertEquals(items.size(), rowsCount);
    }
    
    /**
     * Test common rendering
     * 
     * @throws Exception
     */
    public void testRender() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        HtmlElement div = view.getHtmlElementById(orderingList.getClientId(facesContext));
        assertNotNull(div);
        assertEquals(HTML.DIV_ELEM, div.getNodeName());
        
        HtmlElement hidden1 = view.getHtmlElementById(orderingList.getClientId(facesContext) + "focusKeeper");
        assertNotNull(hidden1);
        assertEquals(HTML.INPUT_ELEM, hidden1.getNodeName());
        assertEquals(HTML.BUTTON, hidden1.getAttributeValue(HTML.TYPE_ATTR));
        hidden1.getAttributeValue(HTML.style_ATTRIBUTE).contains("left: -32767px");
        
        List<HtmlElement> hiddens = view.getDocumentHtmlElement()
        	.getHtmlElementsByAttribute(HTML.INPUT_ELEM, HTML.NAME_ATTRIBUTE, orderingList.getClientId(facesContext));
        assertNotNull(hiddens);
        assertEquals(ROWS_COUNT, hiddens.size());
        for (Iterator<HtmlElement> it = hiddens.iterator(); it.hasNext(); ) {
        	HtmlElement hidden2 = it.next();
        	assertEquals(HTML.INPUT_ELEM, hidden2.getNodeName());
        	assertEquals(HTML.INPUT_TYPE_HIDDEN, hidden2.getAttributeValue(HTML.TYPE_ATTR));
        }
    }
    
    protected class SimpleItem {
    	String name;
    	int salary;
    	
    	public SimpleItem(String name, int salary) {
    		this.name = name;
    		this.salary = salary;
    	}
    	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getSalary() {
			return salary;
		}
		public void setSalary(int salary) {
			this.salary = salary;
		}
    }
}
