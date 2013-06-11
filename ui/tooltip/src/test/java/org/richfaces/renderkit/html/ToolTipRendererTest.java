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
package org.richfaces.renderkit.html;

import java.util.Map;

import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIToolTip;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ToolTipRendererTest extends AbstractAjax4JsfTestCase{
	
	private UIToolTip toolTip;
	private UIToolTip blockToolTip;
	private ToolTipRenderer renderer;
	//private ToolTipRendererBlock rendererBlock;
	
	public ToolTipRendererTest(String name){
		super(name);
	}
	
	
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
        renderer = new ToolTipRenderer();
        
        
        toolTip = (UIToolTip)application.createComponent("org.richfaces.component.ToolTip");
        toolTip.setId("tootipId");
        toolTip.setLayout("inline");
        toolTip.setDirection("top-left");
        
        toolTip.setHorizontalOffset(20);
        HtmlOutputText defContent = (HtmlOutputText) application.createComponent("javax.faces.HtmlOutputText");
        defContent.setValue("Wait...");
        toolTip.getFacets().put("defaultContent", defContent);
        HtmlOutputText toolTipContent = (HtmlOutputText) application.createComponent("javax.faces.HtmlOutputText");
        toolTipContent.setValue("ToolTip Conntent");
        toolTip.getChildren().add(toolTipContent);
        
        HtmlOutputText parentComp = (HtmlOutputText) application.createComponent("javax.faces.HtmlOutputText");
        parentComp.setId("parentID");
        parentComp.setValue("Text with tooltip");
        parentComp.getChildren().add(toolTip);
     
        blockToolTip = (UIToolTip)application.createComponent("org.richfaces.component.ToolTip");
        blockToolTip.setId("blocktootipId");
        blockToolTip.setLayout("block");
        blockToolTip.setMode("ajax");
        blockToolTip.setValue("Simple block tooltip");
        
        facesContext.getViewRoot().getChildren().add(parentComp);
        facesContext.getViewRoot().getChildren().add(blockToolTip);
	}


	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		renderer = null;
	}

	public void testRender(){
				
		try {
			HtmlPage page = renderView();
			assertNotNull(page);
			HtmlElement elem = page.getHtmlElementById(toolTip.getClientId(facesContext));
			assertNotNull(elem);
			assertEquals(elem.getTagName(), "span");
			
			HtmlElement blockElem = page.getHtmlElementById(blockToolTip.getClientId(facesContext));
			assertNotNull(blockElem);
			assertEquals(blockElem.getTagName(), "div");
			
			renderer.encodeTooltipText(facesContext, blockToolTip);
			
			renderer.doEncodeBegin(writer, this.facesContext, toolTip);
			renderer.doEncodeChildren(facesContext.getResponseWriter(), facesContext, toolTip);
			renderer.doEncodeEnd(facesContext.getResponseWriter(), facesContext, toolTip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	
	public void testBuildEventOptions(){
		Map eventOptions = renderer.buildEventOptions(facesContext, toolTip, 
				toolTip.getParent().getClientId(facesContext));
		assertNotNull(eventOptions);
		assertNotNull(eventOptions.get("oncomplete"));
	}
	
	public void testconstructJSVariable(){
		
		String var = renderer.constructJSVariable(facesContext, blockToolTip);
		assertTrue(var.indexOf(blockToolTip.getClientId(facesContext)) != -1);
		assertTrue(var.indexOf(blockToolTip.getParent().getClientId(facesContext)) != -1);
	}
	
}
