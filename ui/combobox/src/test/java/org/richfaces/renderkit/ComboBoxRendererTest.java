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
import javax.faces.model.SelectItem;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIComboBox;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Anton  Belevich
 *
 */
public class ComboBoxRendererTest extends AbstractAjax4JsfTestCase {
	
    private UIComboBox comboBox1;
    private UIComboBox comboBox2;
    String suggestions = "Alabama,Alaska,Arizona,Arkansas,California,Colorado,Connecticut,Delaware,Florida,Massachusetts,Michigan,Georgia,Hawaii,Idaho,Indiana,Iowa,Kansas,Kentucky,Louisiana,Maine,Minnesota,Mississippi,Missouri,Montana,Nebraska";
    List <SelectItem> selectItems  = new ArrayList <SelectItem>();

    public void setUp() throws Exception {
		super.setUp();
		comboBox1 = (UIComboBox) application.createComponent("org.richfaces.ComboBox");
		comboBox1.setSuggestionValues(Arrays.asList(suggestions.split(",")));
		selectItems.add(new SelectItem("District of Columbia"));
		selectItems.add(new SelectItem("Illinois"));
		selectItems.add(new SelectItem("Maryland"));
		selectItems.add(new SelectItem("Nevada"));
		selectItems.add(new SelectItem("New Hampshire"));
		selectItems.add(new SelectItem("New Jersey"));
	
		UISelectItem item1 = new UISelectItem();
		item1.setValue(new SelectItem("Oregon"));
	
		UISelectItem item2 = new UISelectItem();
		item2.setValue(new SelectItem("Pennsylvania"));
	
		UISelectItem item3 = new UISelectItem();
		item3.setValue(new SelectItem("Rhode Island"));
	
		UISelectItem item4 = new UISelectItem();
		item4.setValue(new SelectItem("South Carolina"));
	
		comboBox1.getChildren().add(item1);
		comboBox1.getChildren().add(item2);
		comboBox1.getChildren().add(item3);
		comboBox1.getChildren().add(item4);
	
		// SuggestionValues is array
		comboBox2 = (UIComboBox) application.createComponent("org.richfaces.ComboBox");
		comboBox2.setSuggestionValues(suggestions.split(","));
		comboBox2.getChildren().add(item1);
		comboBox2.getChildren().add(item2);
		comboBox2.getChildren().add(item3);
		comboBox2.getChildren().add(item4);
		
		facesContext.getViewRoot().getChildren().add(comboBox1);
		facesContext.getViewRoot().getChildren().add(comboBox2);
	}

    public void testRender() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		HtmlElement elem1 = page.getHtmlElementById(comboBox1.getClientId(facesContext));
		assertNotNull(elem1);
		assertEquals("div",elem1.getTagName());
		checkList(page, comboBox1);
		HtmlElement elem2 = page.getHtmlElementById(comboBox2.getClientId(facesContext));
		assertNotNull(elem2);
		assertEquals("div",elem2.getTagName());
		checkList(page, comboBox2);
	}
    
    protected void checkList(HtmlPage page, UIComboBox combobox) throws Exception {
    	String listId = combobox.getClientId(facesContext) + "list";
    	HtmlElement list = page.getHtmlElementById(listId);
    	Iterator<HtmlElement> htmlElements = list.getAllHtmlChildElements();
    	String allItems = suggestions + "Oregon, Pennsylvania, Rhode Island, South Carolina";  
    	for (; htmlElements.hasNext();) {
    		HtmlElement span =  htmlElements.next();
    		assertEquals("span", span.getTagName());
    		DomNode node = span.getFirstDomChild();
    		assertEquals(Node.TEXT_NODE, node.getNodeType());
    		assertTrue(allItems.contains(node.getNodeValue()));
    	}
    }

    public void tearDown() throws Exception {
		super.tearDown();
    }

    public ComboBoxRendererTest(String name) {
    	super(name);
    }
}
