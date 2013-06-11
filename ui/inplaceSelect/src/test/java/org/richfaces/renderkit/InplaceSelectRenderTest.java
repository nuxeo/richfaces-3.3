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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockValueExpression;
import org.richfaces.component.UIInplaceSelect;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Anton Belevich
 *
 */
public class InplaceSelectRenderTest extends AbstractAjax4JsfTestCase {
	
	private UIInplaceSelect iselect;
	
	List <String> states = Arrays.asList("Kansas City", "Las Vegas", "Oklahoma City", "New Jersey", "Detroit", "Toronto", "Cleveland", "Indianapolis", "Indianapolis", "New York");
	List <SelectItem> selectItems = new ArrayList <SelectItem>();

	public InplaceSelectRenderTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
	    iselect = (UIInplaceSelect)application.createComponent("org.richfaces.InplaceSelect");
	       
	    selectItems.add(new SelectItem("Kansas City"));
		selectItems.add(new SelectItem("Las Vegas"));
		selectItems.add(new SelectItem("Oklahoma City"));
		selectItems.add(new SelectItem("New Jersey"));
		selectItems.add(new SelectItem("Detroit"));
			    
		UISelectItem item1 = new UISelectItem();
		item1.setValue(new SelectItem("Toronto"));
			
		UISelectItem item2 = new UISelectItem();
		item2.setValue(new SelectItem("Cleveland"));
			
		UISelectItem item3 = new UISelectItem();
		item3.setValue(new SelectItem("Indianapolis"));
			
		UISelectItem item4 = new UISelectItem();
		item4.setValue(new SelectItem("New York"));
		   
	    UISelectItems items = new UISelectItems();
	    items.setValueExpression("value",new MockValueExpression(selectItems));
	    
	    iselect.getChildren().add(items);
	    iselect.getChildren().add(item1);
	    iselect.getChildren().add(item2);
	    iselect.getChildren().add(item3);
	    iselect.getChildren().add(item4);
	    
	    facesContext.getViewRoot().getChildren().add(iselect);
	}
	
	public void testRender() throws Exception{
		HtmlPage page = renderView();
		assertNotNull(page);
		String clientId = iselect.getClientId(facesContext);
		HtmlElement elem = page.getHtmlElementById(clientId);
		assertNotNull(elem);
		assertEquals(elem.getTagName(), "span");
		HtmlElement list = page.getHtmlElementById(clientId + "list");
//		test is list rendered correctly...		
		for (Iterator<HtmlElement> items = list.getChildIterator(); items.hasNext();) {
			HtmlElement span = items.next();
			assertEquals("span", span.getNodeName().toLowerCase());
			DomNode node = span.getFirstDomChild();
    		assertEquals(Node.TEXT_NODE, node.getNodeType());
    		assertTrue(states.contains(node.getNodeValue()));
    	}
	}
}
