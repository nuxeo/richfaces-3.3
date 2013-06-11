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

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlForm;
import javax.faces.model.SelectItem;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockValueExpression;
import org.richfaces.component.UIPickList;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Anton Belevich 
 *
 */
public class PickListRendererTest extends AbstractAjax4JsfTestCase {
	
	private UIForm form;
	private UIPickList pickList;
	private UIPickList pickList2;
	private String [] selected = new String [] {"District of Columbia, Illinois, Maryland, New Hampshire, New Jersey"};
	private List <String> selectedList = Arrays.asList(selected);
	private List <SelectItem> selectItems = Arrays.asList(new SelectItem("New Mexico"), new SelectItem("Texas"), new SelectItem("Florida")); 
		
		    
	public PickListRendererTest(String name) {
		super(name);
		
	}
	
	public void setUp() throws Exception {
		super.setUp();
		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
		
		UISelectItem item1 = new UISelectItem();
		item1.setValue(new SelectItem("Oregon"));
			
		UISelectItem item2 = new UISelectItem();
		item2.setValue(new SelectItem("Pennsylvania"));
			
		UISelectItem item3 = new UISelectItem();
		item3.setValue(new SelectItem("Rhode Island"));
			
		UISelectItem item4 = new UISelectItem();
		item4.setValue(new SelectItem("South Carolina"));
		
		UISelectItems items = new UISelectItems();
	    items.setValue(selectItems);
		
		pickList = (UIPickList)application.createComponent("org.richfaces.PickList");
		pickList.setValueExpression("value", new MockValueExpression(selected) );
		
	    pickList.getChildren().add(item1);
	    pickList.getChildren().add(item2);
	    pickList.getChildren().add(item3);
	    pickList.getChildren().add(item4);
	    pickList.getChildren().add(items);
	    form.getChildren().add(pickList);
	    
	    pickList2 = (UIPickList)application.createComponent("org.richfaces.PickList");
		pickList2.setValueExpression("value", new MockValueExpression(selectedList) );
		pickList2.getChildren().add(item1);
	    pickList2.getChildren().add(item2);
	    pickList2.getChildren().add(item3);
	    pickList2.getChildren().add(item4);
	    pickList2.getChildren().add(items);

	    form.getChildren().add(pickList2);
	}
	
	public void testRender() throws Exception{
		HtmlPage page = renderView();
		assertNotNull(page);
		HtmlElement elem = page.getHtmlElementById(pickList.getClientId(facesContext));
		assertNotNull(elem);
		assertEquals(elem.getTagName(), "table");
		// TODO:  extend mockup check test 	
		HtmlElement elem2 = page.getHtmlElementById(pickList2.getClientId(facesContext));
		assertNotNull(elem2);
		assertEquals(elem.getTagName(), "table");
	}
	

}
