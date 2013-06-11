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
import org.richfaces.renderkit.html.images.SliderArrowImage;
import org.richfaces.renderkit.html.images.SliderArrowImageLeft;
import org.richfaces.renderkit.html.images.SliderArrowImageRight;
import org.richfaces.renderkit.html.images.SliderArrowImageTop;
import org.richfaces.renderkit.html.images.SliderArrowSelectedImage;
import org.richfaces.renderkit.html.images.SliderArrowSelectedImageLeft;
import org.richfaces.renderkit.html.images.SliderArrowSelectedImageRight;
import org.richfaces.renderkit.html.images.SliderArrowSelectedImageTop;
import org.richfaces.renderkit.html.images.SliderFieldGradient;
import org.richfaces.renderkit.html.images.SliderTrackGradient;
import org.richfaces.renderkit.html.images.SliderTrackGradientVertical;
import org.w3c.dom.html.HTMLDivElement;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/** Unit test for simple Component. */
public class InputNumberSliderComponentTest extends AbstractAjax4JsfTestCase {

    private UIForm form = null;
    private UIComponent slider = null;
    private UICommand command = null;
    private static Set<String> javaScripts = new HashSet<String>();

    /**
     * Create the test case
     *
     * @param testName
     *                name of the test case
     */

    static {
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("script/SliderScript.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
        javaScripts.add("org/richfaces/renderkit/html/scripts/events.js");
    }

    public InputNumberSliderComponentTest(String testName) {
        super(testName);
    }

    public void setUp() throws Exception {
        super.setUp();

        form = new HtmlForm();
        facesContext.getViewRoot().getChildren().add(form);

        command = new HtmlCommandLink();
        command.setId("command");
        form.getChildren().add(command);

        slider = application.createComponent(
                UIInputNumberSlider.COMPONENT_TYPE);
        slider.setId("slider");
        form.getChildren().add(slider);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        this.form = null;
        this.slider = null;
        this.command = null;
    }

    /**
     * Test component renders correctly
     *
     * @throws Exception
     */
    public void testComponent() throws Exception {
        HtmlPage renderedView = renderView();

        HtmlElement htmlSlider = renderedView.getHtmlElementById(
                slider.getClientId(facesContext));

        assertNotNull(htmlSlider);

        assertTrue(htmlSlider.getAttributeValue("class").contains(
                "dr-insldr rich-slider"));

        HtmlInput htmlSliderInput = (HtmlInput) renderedView.getHtmlElementById(
                slider.getClientId(facesContext) + "Input");

        assertNotNull(htmlSliderInput);

        String a = htmlSliderInput.getClassAttribute();
        assertTrue(htmlSliderInput.getClassAttribute().contains(
                "dr-insldr-field dr-insldr-field-right rich-inslider-field"));
        assertTrue(htmlSliderInput.getTypeAttribute().equals("text"));

        DomNode numContainer = htmlSliderInput.getParentNode()
                .getPreviousSibling();
        assertTrue(((HtmlElement) numContainer).getAttributeValue(
                "class").contains(
                "dr-insldr-right-num rich-inslider-right-num"));
        assertTrue(((HtmlElement) (numContainer.getPreviousSibling()))
                .getAttributeValue("class").contains(
                "dr-insldr-left-num rich-inslider-left-num"));

        HtmlElement tip = renderedView.getHtmlElementById(slider.getClientId(
                facesContext) + "Tip");
        assertTrue(tip.getAttributeValue("class").contains(
                "dr-insldr-tip rich-inslider-tip"));

        HtmlElement handle = renderedView.getHtmlElementById(slider.getClientId(
                facesContext) + "Handle");
        assertTrue(handle.getAttributeValue("class").contains(
                "dr-insldr-handler rich-inslider-handler"));

        HtmlElement track = (HtmlElement) tip.getParentNode().getNextSibling();
        assertTrue(track.getAttributeValue("class").contains(
                "dr-insldr-track rich-inslider-track"));
    }

    public void testHiddenInput() throws Exception {
        slider.getAttributes().put("showInput", Boolean.FALSE);
        HtmlPage renderedView = renderView();
        HtmlInput htmlSliderInput = (HtmlInput) renderedView.getHtmlElementById(
                slider.getClientId(facesContext) + "Input");
        assertTrue(htmlSliderInput.getTypeAttribute().equals("hidden"));
    }

    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/slider.xcss"));
    }

    
    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        //System.out.println(page.asXml());
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

    public void testImages() throws Exception {
        InternetResource image = InternetResourceBuilder.getInstance()
                .createResource(null, SliderFieldGradient.class.getName());
        Dimension imageDim = ((Java2Dresource) image).getDimensions(
                facesContext, null);
        assertTrue(imageDim.getWidth() == 31 && imageDim.getHeight() == 55);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SliderTrackGradient.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 10);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SliderArrowImage.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

        image = InternetResourceBuilder.getInstance().createResource(null,
                SliderArrowSelectedImage.class.getName());
        imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
        assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);
    }

    public void testRenderImages() throws Exception {
        renderView();
        assertNotNull(getResourceIfPresent("css/slider.xcss"));
        String[] resources = new String[]{
                SliderArrowImage.class.getName(),
                SliderArrowSelectedImage.class.getName(),
        };

        for (int i = 0; i < resources.length; i++) {
        ImageInfo info = getImageResource(resources[i]);
        assertNotNull(info);
            assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
        }
        
        String[] pngResources = new String[]{
                SliderFieldGradient.class.getName(),
                SliderTrackGradient.class.getName()
        };
        
        for (int i = 0; i < pngResources.length; i++) {
            ImageInfo info = getImageResource(pngResources[i]);
            assertNotNull(info);
                    assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
           }
    }
      
    public void testUpdate() throws Exception {
        //tests if component handles value bindings correctly
        HtmlPage renderedView = renderView();

        String value = "66";

        HtmlInput htmlSliderInput = (HtmlInput) renderedView.getHtmlElementById(
                slider.getClientId(facesContext) + "Input");
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
        externalContext.addRequestParameterMap(slider.getClientId(facesContext),
                value);

        slider.processDecodes(facesContext);
        slider.processValidators(facesContext);
        slider.processUpdates(facesContext);

        renderedView = renderView();
        assertTrue(value.equals(((UIInputNumberSlider) slider).getValue()));
    }

    public void testDecode() throws Exception {
        //Tests if component accepts request parameters and stores them in submittedValue().
        //If component is immediate, validation (possibly with conversion) should occur on that phase.
        final SliderBean bean = new SliderBean();
        ((UIInputNumberSlider) slider).setValueBinding("value",
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
        externalContext.addRequestParameterMap(slider.getClientId(facesContext),
                "66");
        slider.processDecodes(facesContext);
        assertTrue(bean.getValue().equals("66"));

        ((UIInputNumberSlider) slider).setImmediate(true);
        renderedView = renderView();
        htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();
        externalContext.addRequestParameterMap(slider.getClientId(facesContext),
                "wrong value");
        slider.processDecodes(facesContext);
        assertTrue(facesContext.getMessages().hasNext());
    }

    public void testValidate() throws Exception {
        HtmlPage renderedView = renderView();

        HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(
                command.getClientId(facesContext));
        htmlLink.click();
        externalContext.addRequestParameterMap(slider.getClientId(facesContext),
                "wrong value");
        slider.processDecodes(facesContext);
        slider.processValidators(facesContext);

        assertTrue(facesContext.getMessages().hasNext());

    }
    /**
     * Test component with orientation renders correctly
     *
     * @throws Exception
     */
    public void testComponentWithOrientation() throws Exception {
		slider.getAttributes().put("orientation", "vertical");
		HtmlPage renderedView = renderView();

		HtmlElement htmlSlider = renderedView.getHtmlElementById(slider
				.getClientId(facesContext));

		assertNotNull(htmlSlider);

		assertTrue(htmlSlider.getAttributeValue("class").contains(
				"dr-insldr-vertical rich-slider "));

		HtmlInput htmlSliderInput = (HtmlInput) renderedView
				.getHtmlElementById(slider.getClientId(facesContext) + "Input");

		assertNotNull(htmlSliderInput);

		assertTrue(htmlSliderInput.getClassAttribute().contains(
				"dr-insldr-field dr-insldr-field-top rich-inslider-field "));
		assertTrue(htmlSliderInput.getTypeAttribute().equals("text"));

		HtmlDivision track = (HtmlDivision) ((HtmlElement) renderedView
				.getHtmlElementById(slider.getClientId(facesContext) + "Track"))
				.getFirstDomChild();

		assertTrue(track.getAttributeValue("class").contains(
				"dr-insldr-track-vertical rich-inslider-track"));

		DomNode numContainer = track.getParentDomNode().getParentDomNode()
				.getNextDomSibling().getFirstDomChild();
		assertTrue(((HtmlElement) numContainer).getAttributeValue("class")
				.contains("dr-insldr-bottom-num "));
		assertTrue(((HtmlElement) track.getParentDomNode().getNextDomSibling())
				.getAttributeValue("class").contains("dr-insldr-top-num "));

		HtmlElement tip = renderedView.getHtmlElementById(slider
				.getClientId(facesContext)
				+ "Tip");
		assertTrue(tip.getAttributeValue("class").contains(
				"dr-insldr-tip rich-inslider-tip"));

		HtmlElement handle = renderedView.getHtmlElementById(slider
				.getClientId(facesContext)
				+ "Handle");
		assertTrue(handle.getAttributeValue("class").contains(
				"dr-insldr-handler-vertical "));
	}

	public void testShowArrows() throws Exception {
		slider.getAttributes().put("showArrows", Boolean.TRUE);
		HtmlPage renderedView = renderView();
		HtmlDivision htmlSliderArrowInc = (HtmlDivision) renderedView
				.getHtmlElementById(slider.getClientId(facesContext)
						+ "ArrowInc");
		assertTrue(htmlSliderArrowInc.getClassAttribute().equals(
				"dr-insldr-inc-horizontal dr-insldr-arrow "));

		HtmlDivision htmlSliderArrowDec = (HtmlDivision) renderedView
				.getHtmlElementById(slider.getClientId(facesContext)
						+ "ArrowDec");
		assertTrue(htmlSliderArrowDec.getClassAttribute().equals(
				"dr-insldr-dec-horizontal dr-insldr-arrow "));

		HtmlDivision htmlSliderTipArrowInc = (HtmlDivision) renderedView
				.getHtmlElementById(slider.getClientId(facesContext)
						+ "TipArrowInc");
		assertTrue(htmlSliderTipArrowInc.getClassAttribute().equals(
				"dr-insldr-tip rich-inslider-tip "));
		assertTrue(htmlSliderTipArrowInc.getStyleAttribute().equals(
				"display: none; "));

		HtmlDivision htmlSliderTipArrowDec = (HtmlDivision) renderedView
				.getHtmlElementById(slider.getClientId(facesContext)
						+ "TipArrowDec");
		assertTrue(htmlSliderTipArrowDec.getClassAttribute().equals(
				"dr-insldr-tip rich-inslider-tip "));
		assertTrue(htmlSliderTipArrowDec.getStyleAttribute().equals(
				"display: none; "));
	}

	public void testHideArrows() throws Exception {
		slider.getAttributes().put("showArrows", Boolean.FALSE);
		HtmlPage renderedView = renderView();
		try {
			renderedView.getHtmlElementById(slider.getClientId(facesContext)
					+ "ArrowInc");
			fail();
		} catch (ElementNotFoundException e) {
			assertTrue(Boolean.TRUE);
		}
		try {
			assertNull(renderedView.getHtmlElementById(slider
					.getClientId(facesContext)
					+ "ArrowDec"));
			fail();
		} catch (ElementNotFoundException e) {
			assertTrue(Boolean.TRUE);
		}
	}

	public void testArrowsImages() throws Exception {
		InternetResource image;
		Dimension imageDim;
		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderTrackGradientVertical.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 10);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowImageLeft.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowImageRight.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowImageTop.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowSelectedImageLeft.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowSelectedImageRight.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);

		image = InternetResourceBuilder.getInstance().createResource(null,
				SliderArrowSelectedImageTop.class.getName());
		imageDim = ((Java2Dresource) image).getDimensions(facesContext, null);
		assertTrue(imageDim.getWidth() == 7 && imageDim.getHeight() == 8);
	}

	public void testRenderArrowImages() throws Exception {
		renderView();
		assertNotNull(getResourceIfPresent("css/slider.xcss"));
		String[] resources = new String[] {
				SliderArrowImageLeft.class.getName(),
				SliderArrowImageRight.class.getName(),
				SliderArrowImageTop.class.getName(),
				SliderArrowSelectedImageLeft.class.getName(),
				SliderArrowSelectedImageRight.class.getName(),
				SliderArrowSelectedImageTop.class.getName()

		};

		for (int i = 0; i < resources.length; i++) {
			ImageInfo info = getImageResource(resources[i]);
			assertNotNull(info);
			assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
		}

		String[] pngResources = new String[] { SliderTrackGradientVertical.class
				.getName() };

		for (int i = 0; i < pngResources.length; i++) {
			ImageInfo info = getImageResource(pngResources[i]);
			assertNotNull(info);
			assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
		}
	}

    private class SliderBean {

        private String value;

        SliderBean() {
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
