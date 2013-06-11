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

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class CompositeRendererTest extends AbstractAjax4JsfTestCase {

	public CompositeRendererTest(String name) {
		super(name);
	}

	private CompositeRenderer compositeRenderer;
	
	public void setUp() throws Exception {
		super.setUp();

		this.compositeRenderer = new CompositeRenderer() {

			protected Class getComponentClass() {
				return UIComponent.class;
			}
		};
	}

	public void tearDown() throws Exception {
		super.tearDown();

		this.compositeRenderer = null;
	}

	public final void testDoDecodeFacesContextUIComponent() {
		MockDecodeContributor[] contributors = new MockDecodeContributor[5];
		for (int i = 0; i < contributors.length; i++) {
			contributors[i] = new MockDecodeContributor();
			compositeRenderer.addContributor(contributors[i]);
		}
		
		UIInput component = new UIInput();
		compositeRenderer.doDecode(facesContext, component);

		for (int i = 0; i < contributors.length; i++) {
			assertSame(compositeRenderer, contributors[i].getRenderer());
			assertSame(facesContext, contributors[i].getContext());
			assertSame(component, contributors[i].getComponent());
		}
	}

	public final void testMergeScriptOptionsScriptOptionsFacesContextUIComponent() {
		UIInput input = new UIInput();

		ScriptOptions inputOptions = new ScriptOptions(input);
		inputOptions.addOption("input", "1");
		
		ScriptOptions formOptions = new ScriptOptions(input);
		formOptions.addOption("form", "2");
		
		ScriptOptions options = new ScriptOptions(input);
		options.addOption("generic", "3");
		
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;
		MockDecodeContributor nullContributor;

		inputContributor = new MockDecodeContributor(UIComponent.class, inputOptions);
		formContributor = new MockDecodeContributor(UIComponent.class, formOptions);
		contributor = new MockDecodeContributor(UIComponent.class, options);
		nullContributor = new MockDecodeContributor(UIComponent.class, (ScriptOptions) null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(nullContributor);
		
		ScriptOptions scriptOptions = new ScriptOptions(input);
		compositeRenderer.mergeScriptOptions(scriptOptions, facesContext, input);
		Map map = scriptOptions.getMap();
		
		assertEquals(3, map.size());
		assertEquals("1", map.get("input"));
		assertEquals("2", map.get("form"));
		assertEquals("3", map.get("generic"));
	}

	public final void testMergeScriptOptionsScriptOptionsFacesContextUIComponentClass() {
		UIInput input = new UIInput();

		ScriptOptions inputOptions = new ScriptOptions(input);
		inputOptions.addOption("input", "1");
		
		ScriptOptions formOptions = new ScriptOptions(input);
		formOptions.addOption("form", "2");
		
		ScriptOptions options = new ScriptOptions(input);
		options.addOption("generic", "3");

		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;
		MockDecodeContributor nullContributor;

		inputContributor = new MockDecodeContributor(UIInput.class, inputOptions);
		formContributor = new MockDecodeContributor(NamingContainer.class, formOptions);
		contributor = new MockDecodeContributor(UIComponent.class, options);
		nullContributor = new MockDecodeContributor(UIForm.class, (ScriptOptions) null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(nullContributor);
	
		ScriptOptions scriptOptions = new ScriptOptions(input);
		compositeRenderer.mergeScriptOptions(scriptOptions, facesContext, input, UIForm.class);
		Map map = scriptOptions.getMap();

		assertEquals(2, map.size());
		assertEquals("2", map.get("form"));
		assertEquals("3", map.get("generic"));
	}

	public final void testGetScripts() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;

		inputContributor = new MockDecodeContributor(UIComponent.class, new String[] { "input_script" }, null);
		formContributor = new MockDecodeContributor(UIComponent.class, new String[] { "form_script" }, null);
		contributor = new MockDecodeContributor(UIComponent.class, null, null);

		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
	
		InternetResource[] scripts = compositeRenderer.getScripts();
		
		assertEquals(2, scripts.length);
		
		assertTrue(scripts[0].getKey().contains("org/richfaces/renderkit/form_script"));
		assertTrue(scripts[1].getKey().contains("org/richfaces/renderkit/input_script"));
	}

	public final void testGetStyles() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;

		inputContributor = new MockDecodeContributor(UIComponent.class, null, new String[] { "input_style" });
		formContributor = new MockDecodeContributor(UIComponent.class, null, new String[] { "form_style" });
		contributor = new MockDecodeContributor(UIComponent.class, null, null);

		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(formContributor);
	
		InternetResource[] scripts = compositeRenderer.getStyles();
		
		assertEquals(2, scripts.length);
		
		assertTrue(scripts[0].getKey().contains("org/richfaces/renderkit/input_style"));
		assertTrue(scripts[1].getKey().contains("org/richfaces/renderkit/form_style"));
	}

	public final void testGetScriptsClass() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;

		inputContributor = new MockDecodeContributor(UIInput.class, new String[] { "input_script" }, null);
		formContributor = new MockDecodeContributor(NamingContainer.class, new String[] { "form_script" }, null);
		contributor = new MockDecodeContributor(UIComponent.class, null, null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
	
		InternetResource[] scripts = compositeRenderer.getScripts(UIInput.class);
		
		assertEquals(1, scripts.length);
		
		assertTrue(scripts[0].getKey().contains("org/richfaces/renderkit/input_script"));

		scripts = compositeRenderer.getScripts(NamingContainer.class);
		
		assertEquals(1, scripts.length);
		
		assertTrue(scripts[0].getKey().contains("org/richfaces/renderkit/form_script"));

		scripts = compositeRenderer.getStyles(String.class);
		assertNull(scripts);
	}

	public final void testGetStylesClass() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;

		inputContributor = new MockDecodeContributor(UIInput.class, null, new String[] { "input_style" });
		formContributor = new MockDecodeContributor(NamingContainer.class, null, new String[] { "form_style" });
		contributor = new MockDecodeContributor(UIComponent.class, null, null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
	
		InternetResource[] styles = compositeRenderer.getStyles(UIInput.class);
		
		assertEquals(1, styles.length);
		
		assertTrue(styles[0].getKey().contains("org/richfaces/renderkit/input_style"));

		styles = compositeRenderer.getStyles(NamingContainer.class);
		
		assertEquals(1, styles.length);
		assertTrue(styles[0].getKey().contains("org/richfaces/renderkit/form_style"));

		styles = compositeRenderer.getStyles(String.class);
		assertNull(styles);
	}

	public final void testGetScriptContributionsStringFacesContextUIComponent() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;
		MockDecodeContributor nullContributor;

		inputContributor = new MockDecodeContributor(UIComponent.class, ".1;");
		formContributor = new MockDecodeContributor(UIComponent.class, ".2;");
		contributor = new MockDecodeContributor(UIComponent.class, ".3;");
		nullContributor = new MockDecodeContributor(UIForm.class, (String) null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(nullContributor);
	
		UIInput input = new UIInput();
		input.getAttributes().put("test", ".testValue");

		String contributions = compositeRenderer.getScriptContributions("theVar", facesContext, input);
	
		assertEquals("theVar.testValue.3;theVar.testValue.2;theVar.testValue.1;", contributions);
	}

	public final void testGetScriptContributionsStringFacesContextUIComponentClass() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;
		MockDecodeContributor contributor;
		MockDecodeContributor nullContributor;

		inputContributor = new MockDecodeContributor(UIInput.class, ".input;");
		formContributor = new MockDecodeContributor(NamingContainer.class, ".namingContainer;");
		contributor = new MockDecodeContributor(UIComponent.class, ".generic;");
		nullContributor = new MockDecodeContributor(UIForm.class, (String) null);
		
		compositeRenderer.addContributor(contributor);
		compositeRenderer.addContributor(formContributor);
		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(nullContributor);
	
		UIInput input = new UIInput();
		input.getAttributes().put("test", ".testValue");
		
		String contributions = compositeRenderer.getScriptContributions("theVar", facesContext, input, 
				UIForm.class);
	
		assertEquals("theVar.testValue.generic;theVar.testValue.namingContainer;", contributions);
	}

	public final void testAddContributor() {
		MockDecodeContributor[] contributors = new MockDecodeContributor[5];
		for (int i = 0; i < contributors.length; i++) {
			contributors[i] = new MockDecodeContributor();
			compositeRenderer.addContributor(contributors[i]);
		}

		assertTrue(Arrays.deepEquals(contributors, compositeRenderer.getContributors()));
	}

	public final void testContributorDecodeCallback() {
		MockDecodeContributor inputContributor;
		MockDecodeContributor formContributor;

		formContributor = new MockDecodeContributor(NamingContainer.class);
		inputContributor = new MockDecodeContributor(UIInput.class);

		compositeRenderer.addContributor(inputContributor);
		compositeRenderer.addContributor(formContributor);
		
		UIComponent component = new UIInput();
		compositeRenderer.doDecode(facesContext, component);

		assertSame(compositeRenderer, inputContributor.getRenderer());
		assertSame(facesContext, inputContributor.getContext());
		assertSame(component, inputContributor.getComponent());
		
		assertNull(formContributor.getRenderer());
		assertNull(formContributor.getContext());
		assertNull(formContributor.getComponent());

		inputContributor.reset();
		formContributor.reset();

		assertNull(inputContributor.getRenderer());
		assertNull(inputContributor.getContext());
		assertNull(inputContributor.getComponent());

		assertNull(formContributor.getRenderer());
		assertNull(formContributor.getContext());
		assertNull(formContributor.getComponent());

		component = new UIForm();
		compositeRenderer.doDecode(facesContext, component);

		assertSame(compositeRenderer, formContributor.getRenderer());
		assertSame(facesContext, formContributor.getContext());
		assertSame(component, formContributor.getComponent());
		
		assertNull(inputContributor.getRenderer());
		assertNull(inputContributor.getContext());
		assertNull(inputContributor.getComponent());
	}

	public final void testAddParameterEncoder() {
		MockAttributeParameterEncoder[] encoders = new MockAttributeParameterEncoder[5];
		for (int i = 0; i < encoders.length; i++) {
			encoders[i] = new MockAttributeParameterEncoder("aaa");
			compositeRenderer.addParameterEncoder(encoders[i]);
		}

		assertTrue(Arrays.deepEquals(encoders, compositeRenderer.getParameterEncoders()));
	}

	public final void testEncodeAttributeParameters() throws IOException {

		MockAttributeParameterEncoder encoder1 = new MockAttributeParameterEncoder("Attribute");
		MockAttributeParameterEncoder encoder2 = new MockAttributeParameterEncoder("MoreAttribute");
		
		compositeRenderer.addParameterEncoder(encoder1);
		compositeRenderer.addParameterEncoder(encoder2);

		UIInput input = new UIInput();
		input.getAttributes().put("Attribute", "testValue1");
		input.getAttributes().put("MoreAttribute", "testValue2");
		
		//ResponseWriter responseWriter = facesContext.getResponseWriter();
		StringWriter stringWriter = new StringWriter();
		MockResponseWriter responseWriter = new MockResponseWriter(stringWriter, "text/html", "UTF8");
		facesContext.setResponseWriter(responseWriter);

		responseWriter.startDocument();
		responseWriter.startElement("span", input);
		compositeRenderer.encodeAttributeParameters(facesContext, input);
		responseWriter.endElement("span");
		responseWriter.endDocument();
		
		responseWriter.flush();
		
		String result = stringWriter.getBuffer().toString();
		assertTrue(result.contains("testAttribute=\"testValue1\""));
		assertTrue(result.contains("testMoreAttribute=\"testValue2\""));
	}
}

class MockDecodeContributor implements RendererContributor {

	private String[] scriptDependencies;
	private String[] styleDependencies;
	
	private String scriptContribution;
	
	private Class componentClass;
	private FacesContext context;
	private UIComponent component;
	private CompositeRenderer renderer;
	private ScriptOptions options;
	
	public MockDecodeContributor(Class componentClass, ScriptOptions options) {
		super();

		this.componentClass = componentClass;
		this.options = options;
	}

	public MockDecodeContributor() {
		this(UIComponent.class);
	}
	
	public MockDecodeContributor(Class componentClass) {
		super();
		this.componentClass = componentClass;
	}

	public MockDecodeContributor(Class componentClass, String scriptContribution) {
		super();

		this.componentClass = componentClass;
		this.scriptContribution = scriptContribution;
	}

	public MockDecodeContributor(Class componentClass, String[] scriptDependencies,
			String[] styleDependencies) {
		super();
		
		this.componentClass = componentClass;
		this.scriptDependencies = scriptDependencies;
		this.styleDependencies = styleDependencies;
	}

	public void decode(FacesContext context, UIComponent component,
			CompositeRenderer compositeRenderer) {

		this.component = component;
		this.context = context;
		this.renderer = compositeRenderer;
	}

	public Class getAcceptableClass() {
		return componentClass;
	}

	public String getScriptContribution(FacesContext context,
			UIComponent component) {
		return scriptContribution != null ? component.getAttributes().get("test") + scriptContribution :
			null;
	}

	public String[] getScriptDependencies() {
		return scriptDependencies;
	}

	public String[] getStyleDependencies() {
		return styleDependencies;
	}

	public ScriptOptions buildOptions(FacesContext context,
			UIComponent component) {
		return options;
	}
	
	public UIComponent getComponent() {
		return component;
	}
	
	public FacesContext getContext() {
		return context;
	}
	
	public CompositeRenderer getRenderer() {
		return renderer;
	}
	
	public void reset() {
		this.component = null;
		this.context = null;
		this.renderer = null;
	}
}

class MockAttributeParameterEncoder implements AttributeParametersEncoder {

	private String attributeName;
	
	public MockAttributeParameterEncoder(String attributeName) {
		super();
		this.attributeName = attributeName;
	}

	public void doEncode(FacesContext context, UIComponent component)
			throws IOException {

		context.getResponseWriter().writeAttribute("test" + attributeName, component.getAttributes().get(attributeName)
				, null);
	}
}
