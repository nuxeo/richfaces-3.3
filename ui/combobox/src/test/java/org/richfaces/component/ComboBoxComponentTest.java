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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIForm;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlForm;
import javax.faces.model.SelectItem;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;
import org.richfaces.component.UIComboBox;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlScript;

/**
 * @author Anton Belevich
 *
 */
public class ComboBoxComponentTest  extends AbstractAjax4JsfTestCase {
	
    UIComboBox comboBox;
    UIForm form;
    String suggestions = "Alabama,Alaska,Arizona,Arkansas,California,Colorado,Connecticut,Delaware,Florida,Massachusetts,Michigan,Georgia,Hawaii,Idaho,Indiana,Iowa,Kansas,Kentucky,Louisiana,Maine,Minnesota,Mississippi,Missouri,Montana,Nebraska";
    List <SelectItem >selectItems = new ArrayList<SelectItem>();

    private static Set <String> javaScripts = new HashSet <String>();

    static {
	javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
	javaScripts.add("org/richfaces/renderkit/html/scripts/jquery/jquery.js");
	javaScripts.add("scripts/comboboxUtils.js");
	javaScripts.add("scripts/combobox.js");
	javaScripts.add("scripts/comboboxstyles.js");
	javaScripts.add("scripts/combolist.js");
	javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");

    }

    public ComboBoxComponentTest(String testName) {
	super(testName);
    }

    public void setUp() throws Exception {
	super.setUp();

	form = new HtmlForm();
	form.setId("form");
	facesContext.getViewRoot().getChildren().add(form);

	comboBox = (UIComboBox) application.createComponent("org.richfaces.ComboBox");
	comboBox.setSuggestionValues(Arrays.asList(suggestions.split(",")));

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

	comboBox.getChildren().add(item1);
	comboBox.getChildren().add(item2);
	comboBox.getChildren().add(item3);
	comboBox.getChildren().add(item4);

	UISelectItems items = new UISelectItems();
	items.setValue(selectItems);
	comboBox.getChildren().add(items);
	form.getChildren().add(comboBox);
    }

    public void testRender() throws Exception {
	HtmlPage page = renderView();
	assertNotNull(page);
    }

    public void testComboBoxStyles() throws Exception {
	HtmlPage page = renderView();
	assertNotNull(page);
	List <HtmlLink> links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");
	if (links.size() == 0) {
	    fail();
	}
	for (int i = 0; i < links.size(); i++) {
	    HtmlElement link = (HtmlElement) links.get(i);
	    assertTrue(link.getAttributeValue("href").contains("css/combobox.xcss"));
	}
    }

    public void testComboBoxScripts() throws Exception {
	HtmlPage page = renderView();
	assertNotNull(page);

	List <HtmlScript> scripts = page.getDocumentHtmlElement().getHtmlElementsByTagName("script");
	for (Iterator <HtmlScript> it = scripts.iterator(); it.hasNext();) {
	    HtmlScript item = it.next();
	    String srcAttr = item.getSrcAttribute();
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
	comboBox = null;
	super.tearDown();
    }

    public void testComponent() {
	assertTrue(true);
    }
}
