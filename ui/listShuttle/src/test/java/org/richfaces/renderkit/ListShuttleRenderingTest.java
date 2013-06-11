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
import org.richfaces.component.UIListShuttle;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

public class ListShuttleRenderingTest extends AbstractAjax4JsfTestCase {
	private static final int IMAGE_COUNT = 16;
	private static final String IMAGE_ALT = "test_alt";
	
	private UIListShuttle listShuttle = null;
	private UIForm form = null;
	private List<SimpleItem> items = null;
    private HtmlOutputText output1 = null;
    private HtmlOutputText output2 = null;
    private UIColumn column1 = null;
    private UIColumn column2 = null;
	
	private static final Set<String> javaScripts = new HashSet<String>();
	private static final Set<String> imageClasses = new HashSet<String>();
	private static final Set<String> imagePNGClasses = new HashSet<String>();
	
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("scripts/utils.js");
		javaScripts.add("scripts/ShuttleUtils.js");
		javaScripts.add("scripts/SelectItem.js");
		javaScripts.add("scripts/LayoutManager.js");
		javaScripts.add("scripts/Control.js");
		javaScripts.add("scripts/OrderingList.js");
		javaScripts.add("scripts/ListShuttle.js");
		javaScripts.add("scripts/ListBase.js");
		
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconUp");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconTop");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconBottom");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconUpDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconDownDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconTopDisabled");
		
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconCopy");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconCopyDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconCopyAll");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconCopyAllDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconRemove");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconRemoveAll");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconRemoveDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.ListShuttleIconRemoveAllDisabled");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconDown");
		imageClasses.add("org.richfaces.renderkit.html.images.OrderingListIconBottomDisabled");
		
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListButtonGradient");
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListClickedGradient");
		imagePNGClasses.add("org.richfaces.renderkit.html.gradientimages.OrderingListHeaderGradient");


	}

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public ListShuttleRenderingTest(String testName) {
		super(testName);
	}
	
	public void setUp() throws Exception {
    	super.setUp();
    	
    	application.addComponent(UIListShuttle.COMPONENT_TYPE, "org.richfaces.component.html.HtmlListShuttle");
    	
    	form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        
        listShuttle = (UIListShuttle)application.createComponent(UIListShuttle.COMPONENT_TYPE);
        listShuttle.setId("listShuttle");
        listShuttle.setVar("item");
        listShuttle.getAttributes().put("copyTitle", IMAGE_ALT);
        
        
        items = new ArrayList<SimpleItem>();
    	items.add(new SimpleItem("Michael", 2000));
    	items.add(new SimpleItem("John", 500));
    	items.add(new SimpleItem("George", 4000));
    	
    	listShuttle.setSourceValue(items);
    	
    	output1 = new HtmlOutputText();
    	output1.setValueBinding("value", application.createValueBinding("#{item.name}"));
    	column1 = new UIColumn();
    	column1.getChildren().add(output1);
    	listShuttle.getChildren().add(column1);
    	
    	output2 = new HtmlOutputText();
    	output2.setValueBinding("value", application.createValueBinding("#{item.salary}"));
    	column2 = new UIColumn();
    	column2.getChildren().add(output2);
    	listShuttle.getChildren().add(column2);
    	
    	form.getChildren().add(listShuttle);
    }
    
    public void tearDown() throws Exception {
    	super.tearDown();
    	
    	items = null;
    	output1 = null;
    	output2 = null;
    	column1 = null;
    	column2 = null;
    	listShuttle = null;
    	form = null;
    }
    
    /**
     * Test common rendering
     * 
     * @throws Exception
     */
    public void testCommonRendering() throws Exception{
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        HtmlElement table = view.getHtmlElementById(listShuttle.getClientId(facesContext));
        assertNotNull(table);
        assertEquals(HTML.TABLE_ELEMENT, table.getNodeName());
        
        HtmlElement tbody = (HtmlElement) table.getFirstDomChild();
        assertNotNull(tbody);
        assertEquals(HTML.TBOBY_ELEMENT, tbody.getTagName());
        
        HtmlElement tr = (HtmlElement) tbody.getFirstDomChild();
        assertNotNull(tr);
        String style = tr.getAttributeValue(HTML.style_ATTRIBUTE);
        assertNotNull(style);
        assertTrue(style.contains("display: none;"));
        assertEquals(HTML.TR_ELEMENT, tr.getTagName());
        
        tr = (HtmlElement) tr.getNextDomSibling();
        assertNotNull(tr);
        assertNull(tr.getNextDomSibling());
        
        int childCount = 0;
        for (Iterator<HtmlElement> it = tr.getChildIterator(); it.hasNext();) {
        	HtmlElement td = it.next();
        	assertNotNull(td);
        	assertEquals(HTML.td_ELEM, td.getTagName());
        	childCount++;
        }
        assertEquals(4, childCount);
        
        HtmlElement hidden1 = view.getHtmlElementById(listShuttle.getClientId(facesContext) + "focusKeeper");
        assertNotNull(hidden1);
        assertEquals(HTML.INPUT_ELEM, hidden1.getNodeName());
        assertEquals(HTML.BUTTON, hidden1.getAttributeValue(HTML.TYPE_ATTR));
        hidden1.getAttributeValue(HTML.style_ATTRIBUTE).contains("left: -32767px");
        
        List<HtmlElement> inputs = view.getDocumentHtmlElement().
        	getHtmlElementsByAttribute(HTML.INPUT_ELEM, HTML.id_ATTRIBUTE, listShuttle.getClientId(facesContext) + "focusKeeper");
        assertNotNull(inputs);
        assertEquals(1, inputs.size());
        inputs = view.getDocumentHtmlElement().
        	getHtmlElementsByAttribute(HTML.INPUT_ELEM, HTML.id_ATTRIBUTE, listShuttle.getClientId(facesContext) + "tlFocusKeeper");
        assertNotNull(inputs);
        assertEquals(1, inputs.size());
        
        inputs = view.getDocumentHtmlElement().
        	getHtmlElementsByAttribute(HTML.INPUT_ELEM, HTML.NAME_ATTRIBUTE, listShuttle.getClientId(facesContext));
        assertNotNull(inputs);
        assertEquals(4, inputs.size());
    }

    /**
     * Test script rendering
     *
     * @throws Exception
     */
    public void testRenderScript() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        List<HtmlScript> scripts = view.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.SCRIPT_ELEM);
        int foundCount = 0;
        for (Iterator<HtmlScript> it = scripts.iterator(); it.hasNext();) {
            HtmlScript item = it.next();
            String srcAttr = item.getSrcAttribute();

            if (StringUtils.isNotBlank(srcAttr)) {
                boolean found = false;
                for (Iterator<String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
                    String src = (String) srcIt.next();

                    found = srcAttr.contains(src);
                    if (found) {
                    	foundCount++;
                    	
                    	 String uri = "http:" + srcAttr;
                         Page page = webClient.getPage(uri);
                         assertNotNull(page);
                         assertTrue(page.getWebResponse().getStatusCode() == HttpServletResponse.SC_OK);
                    	
                        break;
                    }
                }

                assertTrue(found);
            }
        }
        assertEquals(javaScripts.size(), foundCount);
    }
    
    /**
     * Test style rendering
     *
     * @throws Exception
     */
    public void testRenderStyle() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        List<HtmlElement> links = view.getDocumentElement().getHtmlElementsByTagName(HTML.LINK_ELEMENT);
        assertNotNull(links);
        assertEquals(1, links.size());
        HtmlElement link = links.get(0);
        assertTrue(link.getAttributeValue(HTML.HREF_ATTR).contains("css/listShuttle.xcss"));
        
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
        
        int foundImages = 0;
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
        	
        	System.out.println("uri = " + uri + ", alt" + alt); 
        	foundImages++;
        	
        	HtmlElement element = (HtmlElement) img.getParentDomNode();
        	assertNotNull(element);
        	assertEquals(HTML.DIV_ELEM, element.getNodeName());
        	
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
        	String clazz = element.getAttributeValue(HTML.class_ATTRIBUTE);
        	assertNotNull(clazz);
        	assertTrue(clazz.contains("rich-shuttle-control"));
        }
        assertEquals(IMAGE_COUNT, foundImages);
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
     * Test items rendering
     * 
     * @throws Exception
     */
    public void testRenderItems() throws Exception {
    	HtmlPage view = renderView();
        assertNotNull(view);
        
        HtmlElement div = view.getHtmlElementById(listShuttle.getClientId(facesContext) + "contentBox");
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
            assertTrue(clazz.contains("rich-shuttle-source-row"));
            
            int cellsCount = 0;
            for (Iterator<HtmlElement> it2 = tr.getChildIterator(); it2.hasNext(); ) {
            	HtmlElement td = it2.next();
            	assertNotNull(td);
                assertEquals(HTML.td_ELEM, td.getNodeName());
                
                String clazz2 = td.getAttributeValue(HTML.class_ATTRIBUTE);
                assertNotNull(clazz2);
                assertTrue(clazz2.contains("rich-shuttle-source-cell"));
                
                cellsCount++;
            }
            assertEquals(2, cellsCount);
            
            rowsCount++;
        }
        assertEquals(items.size(), rowsCount);
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
