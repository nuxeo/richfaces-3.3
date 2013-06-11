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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIForm;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlForm;
import javax.faces.model.SelectItem;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * @author Anton Belevich
 *
 */
public class PickListComponentTest extends AbstractAjax4JsfTestCase{
	
	UIForm form;
	
	UIPickList pickList;
	
	private static Set <String> javaScripts = new HashSet<String>();
		
	static {
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
		javaScripts.add("scripts/ShuttleUtils.js");
		javaScripts.add("scripts/SelectItem.js");
		javaScripts.add("scripts/PickListSI.js");
		javaScripts.add("scripts/LayoutManager.js");
		javaScripts.add("scripts/Control.js");
		javaScripts.add("scripts/ListBase.js");
		javaScripts.add("scripts/OrderingList.js");
		javaScripts.add("scripts/ListShuttle.js");
		javaScripts.add("scripts/PickList.js");
	}

	public PickListComponentTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		
		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
		 
		pickList = (UIPickList)application.createComponent("org.richfaces.PickList");
			
		UISelectItem item1 = new UISelectItem();
		item1.setValue(new SelectItem("Oregon"));
			
		UISelectItem item2 = new UISelectItem();
		item2.setValue(new SelectItem("Pennsylvania"));
			
		UISelectItem item3 = new UISelectItem();
		item3.setValue(new SelectItem("Rhode Island"));
			
		UISelectItem item4 = new UISelectItem();
		item4.setValue(new SelectItem("South Carolina"));
			
	    pickList.getChildren().add(item1);
	    pickList.getChildren().add(item2);
	    pickList.getChildren().add(item3);
	    pickList.getChildren().add(item4);

	    form.getChildren().add(pickList);
	}
	
	public void testRender() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
	}
	
	public void testPickListScripts() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);

		List <HtmlScript>scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName("script");
		for (Iterator <HtmlScript> it = scripts.iterator(); it.hasNext();) {
			HtmlScript item = (HtmlScript) it.next();
			String srcAttr = item.getSrcAttribute();
			if (item.getFirstDomChild() != null) {
				String scriptBodyString = item.getFirstDomChild().getNodeValue();
				assert(scriptBodyString.contains("Richfaces.PickList"));
			}

			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator <String> srcIt = javaScripts.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();
					found = srcAttr.contains(src);
					if (found) {
						break;
					}
				}
				assertTrue(found);
			}
		}
	}
	
	
	public void tearDown() throws Exception {
		form = null;
		pickList = null;
		super.tearDown();
	}	
}
