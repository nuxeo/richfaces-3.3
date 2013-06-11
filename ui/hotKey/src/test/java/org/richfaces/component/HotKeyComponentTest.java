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

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringEscapeUtils;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitXPath;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 20.06.2008
 *
 */
public class HotKeyComponentTest extends AbstractAjax4JsfTestCase {

	public HotKeyComponentTest(String name) {
		super(name);
	}

	private UIHotKey hotKey;

	private HtmlUnitXPath hotKeyPath;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();

		UIComponent form = application.createComponent(UIForm.COMPONENT_TYPE);
		form.setId("myForm");
		facesContext.getViewRoot().getChildren().add(form);

		this.hotKey = (UIHotKey) application.createComponent(UIHotKey.COMPONENT_TYPE);
		this.hotKey.setId("hKey");
		form.getChildren().add(this.hotKey);
		
		this.hotKeyPath = new HtmlUnitXPath("//*[@id = 'myForm:hKey']");
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		this.hotKey = null;
		this.hotKeyPath = null;
	}

	private boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}
	
	private String getScriptBody(HtmlPage view) throws Exception {
		StringBuilder builder = new StringBuilder();
		
		HtmlElement rootElement = (HtmlElement) this.hotKeyPath.selectSingleNode(view);
		List<DomText> scriptNodes = rootElement.getByXPath("//script/text()");
		for (DomText text : scriptNodes) {
			builder.append(text.getData());
		}
		
		return StringEscapeUtils.unescapeXml(builder.toString()).trim();
	}
	
	public void testElements() throws Exception {
		this.hotKey.getChildren().add(application.createComponent(UIOutput.COMPONENT_TYPE));
		HtmlPage view = renderView();
		HtmlElement rootElement = (HtmlElement) this.hotKeyPath.selectSingleNode(view);
		assertEquals("span", rootElement.getTagName());
		assertEquals(this.hotKey.getClientId(facesContext), rootElement.getAttribute("id"));
		assertEquals("display:none", 
				rootElement.getAttribute("style").replaceAll("\\s", ""));
		
		List scriptNodes = rootElement.getByXPath("//script");
		assertFalse(scriptNodes.isEmpty());
	}

	public void testHasScript() throws Exception {
		assertFalse(isEmpty(getScriptBody(renderView())));
	}

	private static final Pattern START_CODE = Pattern.compile("^\\s*new\\s*Richfaces\\.hotKey\\s*\\(\\s*");
	private static final Pattern END_CODE = Pattern.compile("\\s*\\)\\s*;");

	private String processScriptBody() throws Exception {
		String scriptBody = getScriptBody(renderView());
		
		assertTrue(START_CODE.matcher(scriptBody).find());
		assertTrue(END_CODE.matcher(scriptBody).find());

		return END_CODE.matcher(START_CODE.matcher(scriptBody).replaceAll("")).replaceAll("").
			replaceAll("\\s+", "");
	}
	
	public void testDefault() throws Exception {
		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','','',{timing:'immediate'},function(event){}", scriptBody);
	}
	
	public void testKey1() throws Exception {
		this.hotKey.getAttributes().put("key", "alt+l");
		
		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','alt+l','',{timing:'immediate'},function(event){}", scriptBody);
	}

	public void testKey2() throws Exception {
		this.hotKey.getAttributes().put("key", "\"");
	
		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','\\\"','',{timing:'immediate'},function(event){}", scriptBody);
	}

	public void testKey3() throws Exception {
		this.hotKey.getAttributes().put("key", "'");

		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','\\'','',{timing:'immediate'},function(event){}", scriptBody);
	}
	
	public void testFindComponent() throws Exception {
		this.hotKey.getAttributes().put("selector", "#i1,#i2,#i3,*[type='button']");

		UIComponent input = application.createComponent(UIInput.COMPONENT_TYPE);
		input.setId("i1");
		
		facesContext.getViewRoot().getChildren().add(input);

		input = application.createComponent(UIInput.COMPONENT_TYPE);
		input.setId("i2");
		
		this.hotKey.getParent().getChildren().add(input);
		
		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','','#i1,#myForm\\\\:i2,#i3,*[type=\\'button\\'\\x5D',{timing:'immediate'},function(event){}", scriptBody);
	}
	
	public void testOptions1() throws Exception {
		Map<String, Object> attributes = this.hotKey.getAttributes();
		attributes.put("timing", "onregistercall");
		attributes.put("type", "keypress");
		attributes.put("propagate", Boolean.TRUE);
		attributes.put("disableInInput", Boolean.FALSE);
		attributes.put("disableInInputTypes", "all");
		attributes.put("checkParent", Boolean.TRUE);

		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','','',{timing:'onregistercall',type:'keypress',propagate:true,disableInInput:false,checkParent:true},function(event){}", scriptBody);
	}
	
	public void testOptions2() throws Exception {
		Map<String, Object> attributes = this.hotKey.getAttributes();
		attributes.put("timing", "onload");
		attributes.put("type", "onkeyup");
		attributes.put("propagate", Boolean.FALSE);
		attributes.put("disableInInput", Boolean.TRUE);
		attributes.put("disableInInputTypes", "texts");
		attributes.put("checkParent", Boolean.FALSE);

		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','','',{timing:'onload',type:'keyup',propagate:false,disableInInput:true,disableInInputTypes:'texts',checkParent:false},function(event){}", scriptBody);
	}
	
	public void testHandler() throws Exception {
		this.hotKey.getAttributes().put("handler", "alert('it\\'s_clicked!')");

		String scriptBody = processScriptBody();
		assertEquals("'myForm:hKey','','',{timing:'immediate'},function(event){alert('it\\'s_clicked!')}", scriptBody);
	}
	
	public void testTimingOptions() throws Exception {
		Map<String, Object> attributes = this.hotKey.getAttributes();
		
		attributes.put("timing", "onload");
		renderView();

		attributes.put("timing", "immediate");
		renderView();

		attributes.put("timing", "onregistercall");
		renderView();

		try {
			attributes.put("timing", "onxaction");
			renderView();

			fail();
		} catch (IllegalArgumentException e) {
		}
	}

}
