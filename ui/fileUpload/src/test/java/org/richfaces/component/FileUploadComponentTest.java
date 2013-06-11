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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * Unit test for file upload component.
 */
public class FileUploadComponentTest extends AbstractAjax4JsfTestCase {

    private final static String FILE_UPLOAD_LABEL_TEMPLATE = "{_KB}KB from {KB}KB uploaded --- {mm}:{ss}";
    
    private HtmlForm form;
    private UIFileUpload fileUpload;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileUploadComponentTest(String testName) {
        super(testName);
    }

    /**
     * @see AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        form = new HtmlForm();
        form.setId("form");
        facesContext.getViewRoot().getChildren().add(form);
        fileUpload = (UIFileUpload) application.createComponent(UIFileUpload.COMPONENT_TYPE);
        fileUpload.setAcceptedTypes("application/zip,image/jpeg,video/mpeg");
        fileUpload.setMaxFilesQuantity(5);
        fileUpload.setLocale("en");
        Map<String, UIComponent> facets = fileUpload.getFacets();
        UIProgressBar progressBar = (UIProgressBar) application.createComponent(UIProgressBar.COMPONENT_TYPE);
        facets.put("progress", progressBar);
        UIOutput label = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        label.setValue(FILE_UPLOAD_LABEL_TEMPLATE);
        facets.put("label", label);
        form.getChildren().add(fileUpload);
    }

    public void testRenderer() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
    }

    public void testFileUploadStyles() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(2, links.size());
        String hrefs = "";
        for (Object link : links) {
            hrefs += ((HtmlElement) link).getAttributeValue("href");
        }
        assertTrue(hrefs.contains("css/fileUpload.xcss"));
        assertTrue(hrefs.contains("css/progressBar.xcss"));
    }

    public void testFileUploadScrits() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);

        List scripts = page.getDocumentElement().getHtmlElementsByTagName("script");

        for (Iterator it = scripts.iterator(); it.hasNext();) {
            HtmlScript item = (HtmlScript) it.next();
            String srcAttr = item.getSrcAttribute();
            if (item.getFirstChild() != null) {
                    String scriptBodyString = item.getFirstChild().toString();
                    if (scriptBodyString.contains("new FileUpload")) {
                        assertTrue(scriptBodyString.contains("application/zip"));
                        assertTrue(scriptBodyString.contains("image/jpeg"));
                        assertTrue(scriptBodyString.contains("video/mpeg"));
                        assertFalse(scriptBodyString.contains("image/png"));
                        assertTrue(scriptBodyString.contains("FileUpload.CLASSES"));
                    }
            }
        }
    }

    public void testButtonState() throws Exception {
        String clientId = fileUpload.getClientId(facesContext);
        String testScript = "<script type='text/javascript'> var fileUpload = $(" + clientId + ").component; var input = $(" + clientId + ":file);input.value = 'D:/TEST.txt';" + 
        "for( var i = 0; i <= 5; i++ ) { fileUpload.add(input); } </script>";
        UIOutput testScriptOutput = (UIOutput) application.createComponent(UIOutput.COMPONENT_TYPE);
        testScriptOutput.setValue(testScript);
        form.getChildren().add(testScriptOutput);
        HtmlPage page = renderView();
        assertNotNull(page);
        HtmlElement addButtonDiv = page.getHtmlElementById(clientId + ":add1");
        assertNotNull(addButtonDiv);
        //assertEquals("display: none", addButtonDiv.getAttributeValue("style"));
        form.getChildren().remove(testScriptOutput);
    }

    /**
     * @see AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        form = null;
        fileUpload = null;
    }
}
