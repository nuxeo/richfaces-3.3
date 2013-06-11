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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
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
import org.richfaces.event.DataFilterSliderAdapter;
import org.richfaces.event.DataFilterSliderEvent;
import org.richfaces.renderkit.html.images.SliderFieldGradient;
import org.richfaces.renderkit.html.images.SliderTrackGradient;

import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class DataFilterSliderComponentTest extends AbstractAjax4JsfTestCase {

    private UIForm form = null;
    private UIDataFltrSlider dfSlider = null;
    private UIDataFltrSlider dfSlider2 = null;
    private UIData data = null;
    private UICommand command = null;
    private static Set<String> javaScripts = new HashSet<String>();
    private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

    /**
         * Create the test case
         *
         * @param testName
         *                name of the test case
         */

    static {
        javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
        //No Ajax
        javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
        javaScripts.add("scripts/datafilterslider.js");
    }

    public DataFilterSliderComponentTest(String testName) {
	super(testName);
    }

    public void setUp() throws Exception {
	super.setUp();

	form = new HtmlForm();
	facesContext.getViewRoot().getChildren().add(form);
	
	ArrayList dat = new ArrayList();
	for (int i=0;i<20;i++){
		dat.add(new Integer(i));
	}        
	data = (UIData) application.createComponent(HtmlDataTable.COMPONENT_TYPE);
	data.setValue(dat);
	data.setId("data");
	data.setRows(5);        
	form.getChildren().add(data);

	command = new HtmlCommandLink();
	command.setId("command");
	form.getChildren().add(command);

	dfSlider = (UIDataFltrSlider) application.createComponent(UIDataFltrSlider.COMPONENT_TYPE);
	dfSlider.setId("slider");
	dfSlider.setStartRange(Integer.valueOf(0));
	dfSlider.setEndRange(Integer.valueOf(100));
	dfSlider.setIncrement(Integer.valueOf(1));
	form.getChildren().add(dfSlider);
	
	dfSlider2 = (UIDataFltrSlider) application.createComponent(UIDataFltrSlider.COMPONENT_TYPE);
	dfSlider2.setId("slider2");
	dfSlider2.setFor("data");
	dfSlider2.setStartRange(Integer.valueOf(0));
	dfSlider2.setEndRange(Integer.valueOf(100));
	dfSlider2.setIncrement(Integer.valueOf(1));
	form.getChildren().add(dfSlider2);
    }

    public void tearDown() throws Exception {
	super.tearDown();
	this.form = null;
	this.dfSlider = null;
	this.command = null;
    }

    /**
     * Test component renders correctly
     *
     * @throws Exception
     */
    public void testComponent() throws Exception {
	((UIDataFltrSlider)dfSlider).setTrailer(true);
	HtmlPage renderedView = renderView();

	HtmlElement htmlSlider = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider-range");
	assertTrue(htmlSlider.getAttributeValue("class").contains("range"));
	assertTrue(((HtmlElement)htmlSlider.getParentNode()).getAttributeValue("class").contains("slider-container"));

    	HtmlElement htmlSliderTrailer = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider-trailer");
    	assertTrue(htmlSliderTrailer.getAttributeValue("class").contains("trailer"));

    	HtmlElement htmlSliderTrack = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider-track");
    	assertTrue(htmlSliderTrack.getAttributeValue("class").contains("track"));
    	assertTrue(htmlSliderTrack.getAttributeValue("style").equals("width:" + dfSlider.getAttributes().get("width")));

    	HtmlElement htmlSliderHandle = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider-handle");
    	assertTrue(htmlSliderHandle.getAttributeValue("class").contains("handle"));

    	HtmlElement htmlSliderInput = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider_val");
    	assertTrue(((HtmlInput)htmlSliderInput).getTypeAttribute().equals("text"));
    	assertTrue(htmlSliderInput.getAttributeValue("class").contains("slider-input-field"));
    	
    	
    	//FIXME: Its wrong!
    	assertTrue(htmlSliderInput.getAttributeValue("onchange").equals("dataFilterSlider.valueChanged(event,this.value);"));
    }

    public void testImages() throws Exception {
	InternetResource image = InternetResourceBuilder.getInstance().createResource(null, SliderFieldGradient.class.getName());
	Dimension imageDim = ((Java2Dresource)image).getDimensions(facesContext, null);
	assertTrue( imageDim.getWidth() == 31 && imageDim.getHeight() == 55);

	image = InternetResourceBuilder.getInstance().createResource(null, SliderTrackGradient.class.getName());
	imageDim = ((Java2Dresource)image).getDimensions(facesContext, null);
	assertTrue( imageDim.getWidth() == 7 && imageDim.getHeight() == 10);

    }

    public void testRenderImages() throws Exception {
    	renderView();
    	assertNotNull(getResourceIfPresent("css/dataFilterSlider.xcss"));

    	String[] resources = new String[] {
    			SliderFieldGradient.class.getName(),
    			SliderTrackGradient.class.getName(),
    	};

    	for (int i = 0; i < resources.length; i++) {
    		ImageInfo info = getImageResource(resources[i]);
    		assertNotNull(info);
        	assertEquals(ImageInfo.FORMAT_PNG, info.getFormat());
		}
    }

    public void testHiddenInput() throws Exception {
    	((UIDataFltrSlider)dfSlider).setManualInput(false);
    	HtmlPage renderedView = renderView();
	HtmlElement htmlSliderInput = renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider_val");
    	assertTrue(((HtmlInput)htmlSliderInput).getTypeAttribute().equals("hidden"));
    }

    public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List links = page.getDocumentElement().getHtmlElementsByTagName("link");
        assertEquals(1, links.size());
        HtmlElement link = (HtmlElement) links.get(0);
        assertTrue(link.getAttributeValue("href").contains("css/dataFilterSlider.xcss"));
    }

    public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
    }

    public void testUpdate() throws Exception {
	//tests if component handles value bindings correctly
    	HtmlPage renderedView = renderView();

    	String value = "5";

	HtmlInput htmlSliderInput = (HtmlInput) renderedView.getHtmlElementById(dfSlider.getClientId(facesContext)+"slider_val");
	htmlSliderInput.setValueAttribute(value);

    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
    	htmlLink.click();

    	List lastParameters = this.webConnection.getLastParameters();
    	for (Iterator iterator = lastParameters.iterator(); iterator.hasNext();) {
			KeyValuePair keyValue = (KeyValuePair) iterator.next();

			externalContext.addRequestParameterMap((String) keyValue.getKey(), (String) keyValue.getValue());
		}

        externalContext.addRequestParameterMap(dfSlider.getClientId(facesContext)+"slider_val", value);

        dfSlider.processDecodes(facesContext);
        dfSlider.processValidators(facesContext);
        dfSlider.processUpdates(facesContext);

        renderedView = renderView();
        assertTrue( value.equals(String.valueOf(((UIDataFltrSlider)dfSlider).getHandleValue())));
    }

    public void testDecode() throws Exception{
	//Tests if component accepts request parameters and stores them in submittedValue().
	//If component is immediate, validation (possibly with conversion) should occur on that phase.
	final SliderBean bean = new SliderBean();
	((UIDataFltrSlider)dfSlider).setValueBinding("value",
		new ValueBinding() {
	    public Class getType(FacesContext context) throws EvaluationException, PropertyNotFoundException {
	        return String.class;
	    }
	    public Object getValue(FacesContext context) throws EvaluationException, PropertyNotFoundException {
		return bean.getValue();
	    }
	    public boolean isReadOnly(FacesContext context) throws EvaluationException, PropertyNotFoundException {
	        return false;
	    }
	    public void setValue(FacesContext context, Object value) throws EvaluationException, PropertyNotFoundException {
	       bean.setValue((String)value);
	    }
	});
	HtmlPage renderedView = renderView();
    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
    	htmlLink.click();
    	externalContext.addRequestParameterMap(dfSlider.getClientId(facesContext),"66");
    	dfSlider.processDecodes(facesContext);
	assertTrue(bean.getValue().equals("66"));
    }

    public void testListener() throws Exception{
    	HtmlPage renderedView = renderView();

    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
    	htmlLink.click();

    	MethodBinding binding = new MethodBinding(){
	    public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
		facesContext.addMessage(dfSlider.getClientId(facesContext), new FacesMessage("Method invoked!"));
		return "invoked";
	    }
	    public Class getType(FacesContext context) throws MethodNotFoundException {
		return String.class;
	    }
	};

	DataFilterSliderEvent event = new DataFilterSliderEvent( ((UIDataFltrSlider) dfSlider), new Integer(20), new Integer (50 ) );
	new DataFilterSliderAdapter(binding).processSlider(event);

	assertTrue(facesContext.getMessages().hasNext());
    }
    
    public void testGetDataTable() throws Exception{
    	HtmlPage renderedView = renderView();
    	UIData data = dfSlider2.getUIData();
    	assertNotNull(data);
    }

    private final class SliderBean  {

	private String value;

	SliderBean (){
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
