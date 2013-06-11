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

import java.awt.Dimension;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletResponse;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.ResourceBuilderImpl;
import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.HtmlTestUtils;
import org.apache.commons.lang.StringUtils;
import org.richfaces.renderkit.html.images.SpinnerButtonDown;
import org.richfaces.renderkit.html.images.SpinnerButtonGradient;
import org.richfaces.renderkit.html.images.SpinnerButtonUp;
import org.richfaces.renderkit.html.images.SpinnerFieldGradient;

import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/** Unit test for simple Component. */
public class InputNumberSpinnerComponentTest extends AbstractAjax4JsfTestCase {

    private UIForm form = null;
    private UIComponent spinner = null;
    private UICommand command = null;
    private static Set<String> javaScripts = new HashSet<String>();

    static {
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("script/SpinnerScript.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */

    public InputNumberSpinnerComponentTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        facesContext.getViewRoot().getChildren().add(form);

        command = new HtmlCommandLink();
        command.setId("command");
        form.getChildren().add(command);

        spinner = application.createComponent(
                UIInputNumberSpinner.COMPONENT_TYPE);
        spinner.setId("spinner");
        form.getChildren().add(spinner);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        this.form = null;
        this.spinner = null;
        this.command = null;
    }

    /**
     * Test component renders correctly
     *
     * @throws Exception
     */
    public void testComponent() throws Exception {
        HtmlPage renderedView = renderView();

        HtmlElement htmlSpinner = renderedView.getHtmlElementById(
                spinner.getClientId(facesContext));

        assertNotNull(htmlSpinner);

        assertTrue(htmlSpinner.getAttributeValue("class").contains(
                "dr-spnr-c rich-spinner-c"));

        HtmlElement htmlSpinnerEdit = (HtmlElement) renderedView
                .getHtmlElementById(spinner.getClientId(facesContext) + "Edit");
        assertNotNull(htmlSpinnerEdit);
        assertTrue(htmlSpinnerEdit.getAttributeValue("class").contains(
                "dr-spnr-e  rich-spinner-input-container"));

        HtmlInput htmlSpinnerInput = (HtmlInput) htmlSpinnerEdit.getLastChild();
        assertNotNull(htmlSpinnerInput);
        assertTrue(htmlSpinnerInput.getAttributeValue("class").contains(
                "dr-spnr-i rich-spinner-input"));

        HtmlElement buttonsContainer = (HtmlElement) renderedView
                .getHtmlElementById(spinner.getClientId(facesContext)
                                    + "Buttons");
        assertNotNull(buttonsContainer);
        Collection inputs = buttonsContainer.getHtmlElementsByTagName("input");
        for (Iterator iter = inputs.iterator(); iter.hasNext();) {
            HtmlInput child = (HtmlInput) iter.next();
            assertTrue(child.getTypeAttribute().equals("image"));
            assertTrue(child.getAttributeValue("class").contains(
                    "dr-spnr-bn rich-spinner-button"));
        }
    }

    public void testDisabledComponent() throws Exception {
        spinner.getAttributes().put("disabled", Boolean.TRUE);
        HtmlPage renderedView = renderView();

        HtmlElement buttonsContainer = (HtmlElement) renderedView
                .getHtmlElementById(spinner.getClientId(facesContext)
                                    + "Buttons");
        assertNotNull(buttonsContainer);
        Collection inputs = buttonsContainer.getHtmlElementsByTagName("input");
        for (Iterator iter = inputs.iterator(); iter.hasNext();) {
            HtmlInput child = (HtmlInput) iter.next();
            assertTrue(child.getAttributeValue("onmouseup").equals(""));
        }
    }

    public void testImages() throws Exception {
        InternetResource image = InternetResourceBuilder.getInstance()
                .createResource(null, SpinnerFieldGradient.class.getName());
        Dimension imageDim = ((Java2Dresource) image).getDimensions(
                facesContext, null);
        assertTrue(imageDim.getWidth() == 30 && imageDim.getHeight() == 50);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SpinnerButtonGradient.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 30 && imageDim.getHeight() == 50);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SpinnerButtonDown.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 14 && imageDim.getHeight() == 7);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SpinnerButtonUp.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 14 && imageDim.getHeight() == 7);
    }

    public void testRenderImages() throws Exception {
        renderView();
        assertNotNull(getResourceIfPresent("css/spinner.xcss"));
        String[] resources = new String[]{
                SpinnerButtonDown.class.getName(),
                SpinnerButtonUp.class.getName()
        };

        for (int i = 0; i < resources.length; i++) {
        ImageInfo info = getImageResource(resources[i]);
        assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        }
        
        String[] pngResources = new String[]{
                SpinnerButtonGradient.class.getName(),
                SpinnerFieldGradient.class.getName(),
        };
        
        for (int i = 0; i < pngResources.length; i++) {
            ImageInfo info = getImageResource(pngResources[i]);
            assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
        }
    }

    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains(
                "css/spinner.xcss"));
    }

    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        
        List<String> scriptSources = HtmlTestUtils.extractScriptSources(page);
		for (String javascript : javaScripts) {
			boolean found = false;
	        for (String script : scriptSources) {
	        	if (script.indexOf(javascript) >= 0) {
	        		found = true;
	        		break;
	        	}
			}
	        assertTrue("Component script " + javascript + " is not found in the response", found);
		}
    }

    public void testUpdate() throws Exception {
        //tests if component handles value bindings correctly
        HtmlPage renderedView = renderView();

        String value = "66";

        HtmlElement inputContainer = (HtmlElement) renderedView
                .getHtmlElementById(spinner.getClientId(facesContext) + "Edit");
        HtmlInput htmlSliderInput = (HtmlInput) inputContainer.getLastChild();
        htmlSliderInput.setValueAttribute(value);

        HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();

        List lastParameters = this.webConnection.getLastParameters();
        for (Iterator iterator = lastParameters.iterator(); iterator.hasNext();)
        {
            KeyValuePair keyValue = (KeyValuePair) iterator.next();

            externalContext.addRequestParameterMap((String) keyValue.getKey(),
                    (String) keyValue.getValue());
        }

        externalContext.addRequestParameterMap(spinner.getClientId(
                facesContext), value);

        spinner.processDecodes(facesContext);
        spinner.processValidators(facesContext);
        spinner.processUpdates(facesContext);

        renderedView = renderView();
        assertTrue(value.equals(((UIInputNumberSpinner) spinner).getValue()));
    }

    public void testDecode() throws Exception {
        //Tests if component accepts request parameters and stores them in submittedValue().
        //If component is immediate, validation (possibly with conversion) should occur on that phase.
        final SpinnerBean bean = new SpinnerBean();
        ((UIInputNumberSpinner) spinner).setValueBinding("value",
                new ValueBinding() {
                    public Class getType(FacesContext context) throws
                                                               EvaluationException,
                                                               PropertyNotFoundException {
                        return String.class;
                    }

                    public Object getValue(FacesContext context) throws
                                                                 EvaluationException,
                                                                 PropertyNotFoundException {
                        return bean.getValue();
                    }

                    public boolean isReadOnly(FacesContext context) throws
                                                                    EvaluationException,
                                                                    PropertyNotFoundException {
                        return false;
                    }

                    public void setValue(FacesContext context, Object value)
                            throws EvaluationException,
                                   PropertyNotFoundException {
                        bean.setValue((String) value);
                    }
                });
        HtmlPage renderedView = renderView();
        HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();
        externalContext.addRequestParameterMap(spinner.getClientId(
                facesContext), "66");
        spinner.processDecodes(facesContext);
        assertTrue(bean.getValue().equals("66"));

        ((UIInputNumberSpinner) spinner).setImmediate(true);
        renderedView = renderView();
        htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();
        externalContext.addRequestParameterMap(spinner.getClientId(
                facesContext), "wrong value");
        spinner.processDecodes(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
    }

    public void testValidate() throws Exception {
        HtmlPage renderedView = renderView();

        HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();
        externalContext.addRequestParameterMap(spinner.getClientId(
                facesContext), "wrong value");
        spinner.processDecodes(facesContext);
        spinner.processValidators(facesContext);

        assertTrue(facesContext.getMessages().hasNext());

    }

    private class SpinnerBean {

        private String value;

        SpinnerBean() {
            this.value = "66";
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
