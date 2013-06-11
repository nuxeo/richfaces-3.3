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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.renderkit.RendererBase;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RulesBase;
import org.apache.commons.digester.SetNextRule;
import org.apache.commons.digester.SetPropertiesRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;


/**
 * Compiler for XML string or stream for creation of {@link javax.faces.render.Renderer} internal
 * interpretator. Use Apache Digitester for parse .
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:48 $
 *
 */
public class HtmlCompiler {
	
	public static final String NS_PREFIX = "f:";
	public static final String NS_UTIL_PREFIX = "u:";
	public static final String ROOT_TAG = "template";
	public static final String CALL_TAG = "call";
	public static final String VALUE_CALL_TAG = "valueCall";
	public static final String CALL_PARAM_TAG = "parameter";
	public static final String VERBATUM_TAG = "verbatim";
	public static final String BREAK_TAG = "break";
	public static final String FACET_TAG = "insertFacet";
	public static final String CHILDREN_TAG = "insertChildren";
	public static final String IF_TAG = "if";
	public static final String CHILD_TAG = "insertChild";
	public static final String STYLE_TAG = "style";
	public static final String CHILD_METHOD = "addChild";
	public static final String RESOURCE_TAG = "resource";
	public static final String ATTRIBUTE_TAG = "attribute";
	public static final String CHILD_PARAM_METHOD = "addParameter";
	public static final String ANY_PARENT = "*/";
	public static final String SELECTOR_TAG = "selector";
	public static final String IMPORT_RESOURCE_TAG = "importResource";
	

	private static final Log log = LogFactory.getLog(HtmlCompiler.class);
	
	private Digester digestr = new Digester();
	
	
	/**
	 * Constructor with initialisation.
	 */
	public HtmlCompiler(){
		WithDefaultsRulesWrapper rules = new WithDefaultsRulesWrapper(new RulesBase());
		// Add default rules to process plain tags.
	    rules.addDefault(new PlainElementCreateRule());
	    rules.addDefault(new SetNextRule(CHILD_METHOD));
	    digestr.setDocumentLocator(new LocatorImpl());
	    digestr.setRules(rules);
	    digestr.setValidating(false);
	    digestr.setNamespaceAware(false);
	    digestr.setUseContextClassLoader(true);
	    // Concrete renderer method call rules.
	    setCustomRules(digestr);
	}

	/**
	 * Build custom rules for parse special tags and attributes.
	 * For most tags, value can be in next forms ( for resource, preffixed with f: ):
	 * <ul>
	 * <li><b>value</b> as string or EL-expression</li>
	 * <li><b>property</b> name for property of current component</li>
	 * <li><b>baseSkin</b> name for property of base skin</li>
	 * <li><b>skin</b> name for property of current skin</li>
	 * <li><b>context</b> name for property of {@link TemplateContext} for trmplate encoding</li>
	 * <li><b>default</b> use as value if main property <code>null</code></li>
	 * </ul>
	 * Supported :
	 * <ul>
	 * <li>Call renderer or utils method &lt;f:call name='xxx'  /&gt;</li>
	 * <li>Call  method parameter &lt;f:parameter name='xxx'  (value='....' | property='style' | baseSkin='paramName' | skin='paramName' | context='propertyName ) /&gt;</li>
	 * <li>Encode plain text &lt;f:verbatim [value='....' | property='style' | baseSkin='paramName' | skin='paramName' | context='propertyName ] &gt;Some Text&lt;/f:text&gt;</li>
	 * <li>Encode as text in HTML CSS parameter format &lt;u:style name='background' (value='....' | property='style' | baseSkin='paramName' | skin='paramName' | context='propertyName )/&gt;</li>
	 * <li>Get and encode resource &lt;f:resource f:key='images/spacer.gif' [ ... any attributes ... ] /&gt;</li>
	 * <li>Encode custom attribute &lt;f:attribute name='style' (value='....' | property='style' | baseSkin='paramName' | skin='paramName' | context='propertyName )  [endorsed='true'] /&gt;</li>
	 * <li>Template breakpoint &lt;f:break name='xxxxx'  /&gt;</li>
	 * <li>Encode named facet &lt;u:facet ( name='head  | property='style' | baseSkin='paramName' | skin='paramName' | context='propertyName )' /&gt;</li>
	 * <li>Encode children components for current &lt;u:children /&gt;</li>
	 * <li>Encode one child component for enclosed u:facet or u:children  &lt;u:child /'&gt;</li>
	 * </ul>
	 * @param digestr
	 */
	protected void setCustomRules( Digester digestr){
		// Root element. not instantiate - already set by compile method.
		String pattern = NS_PREFIX+ROOT_TAG;
		digestr.addSetProperties(pattern);
		// call renderer method <f:call methodName='xxx' [methodParam='yyy'] />
		pattern = ANY_PARENT+NS_PREFIX+CALL_TAG;
		digestr.addObjectCreate(pattern,MethodCallElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
		digestr.addSetProperties(pattern);
		// for value of parent call renderer method <f:valueCall methodName='xxx' [methodParam='yyy'] />
		pattern = ANY_PARENT+NS_PREFIX+VALUE_CALL_TAG;
		digestr.addObjectCreate(pattern,ValueMethodCallElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
		digestr.addSetProperties(pattern);
		// call parameter <f:parapeter methodName='xxx' [methodParam='yyy'] />		
//		pattern = ANY_PARENT+NS_PREFIX+CALL_TAG+"/"+NS_PREFIX+CALL_PARAM_TAG;
		pattern = ANY_PARENT+NS_PREFIX+CALL_PARAM_TAG;
		digestr.addObjectCreate(pattern,MethodParameterElement.class);
		digestr.addSetNext(pattern,CHILD_PARAM_METHOD);
		digestr.addSetProperties(pattern);
		// Encode plain text <f:text>Some Text</f:text>
		pattern = ANY_PARENT+NS_PREFIX+VERBATUM_TAG;
		digestr.addObjectCreate(pattern,TextElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
		digestr.addSetProperties(pattern);
		digestr.addBeanPropertySetter(pattern,"text");
		// resource <f:resource f:key='images/spacer.gif' [f:data='...'] [ ... any attributes ... ] />
		pattern =ANY_PARENT+NS_PREFIX+RESOURCE_TAG;
		digestr.addObjectCreate(pattern,ResourceElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
		{
		Rule rule = new PutAttributesRule(digestr,new String[]{NS_PREFIX+"key",NS_PREFIX+"property",NS_PREFIX+"baseSkin",NS_PREFIX+"skin",NS_PREFIX+"context"});
		digestr.addRule(pattern,rule);
		}
		{
		SetPropertiesRule rule = new SetPropertiesRule(new String[]{NS_PREFIX+"key",NS_PREFIX+"property",NS_PREFIX+"baseSkin",NS_PREFIX+"skin",NS_PREFIX+"context"},new String[]{"value","property","baseSkin","skin","context"});		
		digestr.addRule(pattern,rule);
		}
		// custom attribute <f:attribute name='style' (value='....' | property='style' | skin='paramName' | call='methodName' | utilCall='methodName') [notNull='true'] />
		pattern = ANY_PARENT+NS_PREFIX+ATTRIBUTE_TAG;
		digestr.addObjectCreate(pattern,AttributeElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
		digestr.addSetProperties(pattern);
		// component clientId <f:id [f:required='true'] />
		// breakpoint <f:break name="name">
		pattern = ANY_PARENT+NS_PREFIX+BREAK_TAG;
		digestr.addObjectCreate(pattern,BreakPoint.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
		// conditional rendering  <f:if then="true">
		pattern = ANY_PARENT+NS_PREFIX+IF_TAG;
		digestr.addObjectCreate(pattern,IfElement.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
		// resources import <f:importResource src="...">
		pattern = ANY_PARENT+NS_PREFIX+IMPORT_RESOURCE_TAG;
		digestr.addFactoryCreate(pattern, new AbstractObjectCreationFactory() {

			public Object createObject(Attributes attributes) throws Exception {

				if (attributes == null) {
					throw new IllegalArgumentException("Attributes set is null for " + 
							IMPORT_RESOURCE_TAG +
							" element. Can not obtain required 'src' attribute!");
				}
				
				String value = attributes.getValue("src");
				if (value == null || value.length() == 0) {
					throw new IllegalArgumentException("Missing required 'src' attribute for " +
							IMPORT_RESOURCE_TAG +
							" element!");
				}
				
				return new ImportResourceElement(value);
			}
			
		});
		digestr.addSetNext(pattern,CHILD_METHOD);
		// facet <u:insertFacet name="name">
		pattern = ANY_PARENT+NS_UTIL_PREFIX+FACET_TAG;
		digestr.addObjectCreate(pattern,FacetElement.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
		
		// childrens <u:insertChildren>
		pattern = ANY_PARENT+NS_UTIL_PREFIX+CHILDREN_TAG;
		digestr.addObjectCreate(pattern,ChildrensElement.class);
		digestr.addSetNext(pattern,CHILD_METHOD);
//		{
//			Rule rule = new PutAttributesRule(digestr,new String[]{NS_PREFIX+"key",NS_PREFIX+"property",NS_PREFIX+"skin",NS_PREFIX+"context"});
//			digestr.addRule(pattern,rule);
//			}
			{
			SetPropertiesRule rule = new SetPropertiesRule(new String[]{NS_PREFIX+"key",NS_PREFIX+"property",NS_PREFIX+"skin",NS_PREFIX+"context"},new String[]{"value","property","skin","context"});		
			digestr.addRule(pattern,rule);
			}


		
		// style(sheet) property <u:style>
		pattern = ANY_PARENT+NS_UTIL_PREFIX+STYLE_TAG;
		digestr.addObjectCreate(pattern,ClassElement.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
		// breakpoint <f:break name="name">
		pattern = ANY_PARENT+NS_UTIL_PREFIX+CHILD_TAG;
		digestr.addObjectCreate(pattern,ChildElement.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
		
		//Selector element <u:selector></u:selector>
		pattern = ANY_PARENT+NS_UTIL_PREFIX+SELECTOR_TAG;
		digestr.addObjectCreate(pattern,SelectorElement.class);
		digestr.addSetProperties(pattern);
		digestr.addSetNext(pattern,CHILD_METHOD);
	}
	
	
	/**
	 * Compile template for XML from simple string.
	 * @param xml - String with template code
	 * @return compiled template, ready for rendering.
	 */
	public PreparedTemplate compile(String xml){
		Reader input = new StringReader(xml);
		return compile(input);
	}
	
	/**
	 * Compile set of templates at time. Templates gets from resources in
	 * classpath relative to base class. Used for initialisation set of templates in {@link RendererBase} subclasses constructor
	 * @param base - base class for resources paths
	 * @param templates - array with templates names.
	 * @return {@link Map } , with keys - templates name, value - compiled template.
	 */
	public Map compileResources(Class base,String[] templates) {
		Map result = new HashMap(templates.length);
		for (int i = 0; i < templates.length; i++) {
			String template = templates[i];
			result.put(template,compileResource(base,template));
		}
		return result;
	}
	/**
	 * Compile XML from resource in classpath. Resource always readed
	 * as UTF-8 text , to avoid xml declarations.
	 * @param base - class of base object.
	 * @param resource - path to resource ( if not start with / , relative to base object )
	 * @return compiled XML, or Exception-generated stub, if parsing error occurs.
	 */
	public static PreparedTemplate compileResource(Class base, String resource)  {
		String path;
		if (resource.startsWith("/")) {
			path = base.getPackage().getName().replace('.','/')+resource;
		} else {
			path = base.getPackage().getName().replace('.','/')+"/"+resource;
		}
		return compileResource(path);
	}
	
	
	/**
	 * Compile XML from resource in classpath. Resource always readed
	 * as UTF-8 text , to avoid xml declarations.
	 * @param resource - absolute path to resource
	 * @return compiled XML, or Exception-generated stub, if parsing error occurs.
	 */
	public static PreparedTemplate compileResource(String resource)  {
		HtmlCompiler compiler = new HtmlCompiler();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = URLToStreamHelper.urlToStreamSafe(loader.getResource(resource));
		// Since parsing exceptions handled by compiler, we can not check parameter.
		PreparedTemplate compile = compiler.compile(input, resource);
		if(null != input){
			try {
				input.close();
			} catch (IOException e) {
				// can be ignored
			}
		}
		return compile;
	}
	
	public PreparedTemplate compile(Reader input){
		// By default - empty result. we not throw any exceptions in compile phase -
		// since it can be in static class initialization.
		PreparedTemplate result = null;
		try {
			digestr.clear();
			digestr.push(new RootElement());
			result = (PreparedTemplate) digestr.parse(input);
		} catch (SAXParseException e) {
//			TODO - locate compilation error. How get name ?
			String errorstr = Messages.getMessage(Messages.PARSING_TEMPLATE_ERROR, new Object[]{"" + e.getLineNumber(), "" + e.getColumnNumber(), e.toString()});
//			errorstr.append(digestr.getDocumentLocator().getLineNumber()).append(" and position ").append(digestr.getDocumentLocator().getColumnNumber());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} catch (IOException e) {
			String errorstr = Messages.getMessage(Messages.TEMPLATE_IO_ERROR, e.toString());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} catch (SAXException e) {
			String errorstr = Messages.getMessage(Messages.PARSING_TEMPLATE_ERROR_2, e.toString());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} 
		return result;
	}
	
	/**
	 * Compile input {@link InputStream} to {@link PreparedTemplate} - set of Java classes to encode as JSF output.
	 * @param input stream with template XML text.
	 * @param sourcename ( optional ) name of resource, for debug purposes.
	 * @return compiled template.
	 */
	public PreparedTemplate compile(InputStream input, String sourcename){
		// By default - empty result. we not throw any exceptions in compile phase -
		// since it can be in static class initialization.
		PreparedTemplate result = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug(Messages.getMessage(Messages.START_COMPILE_TEMPLATE_INFO, sourcename));
			}
			digestr.clear();
			RootElement root = new RootElement();
			root.setTemplateName(sourcename);
			digestr.push(root);
			result = (PreparedTemplate) digestr.parse(input);
		} catch (SAXParseException e) {
//			TODO - locate compilation error. How get name ?
			String errorstr = Messages.getMessage(Messages.PARSING_TEMPLATE_ERROR_a, new Object[]{sourcename, "" + e.getLineNumber(), "" + e.getColumnNumber(), e.toString()});
//			errorstr.append(digestr.getDocumentLocator().getLineNumber()).append(" and position ").append(digestr.getDocumentLocator().getColumnNumber());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} catch (IOException e) {
			String errorstr = Messages.getMessage(Messages.TEMPLATE_IO_ERROR_a, sourcename, e.toString());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} catch (SAXException e) {
			String errorstr = Messages.getMessage(Messages.PARSING_TEMPLATE_ERROR_2a, sourcename, e.toString());
			log.error(errorstr, e);
			result = new CompiledError(errorstr.toString(),e);
		} 
		if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.FINISH_COMPILE_TEMPLATE_INFO, sourcename));
		}
		return result;
	}

	/**
	 * Incapsulate parsing - compilation errors to throw at encoding.
	 * Allow static / constructor initialisation, all errors will be
	 * see in page at request time. For debugging purposes. 
	 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
	 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:48 $
	 *
	 */
	private static class CompiledError implements PreparedTemplate {
		private Exception cause = null;
		private String message = Messages.getMessage(Messages.TEMPLATE_NOT_COMPILED_ERROR);

		/**
		 * @param cause
		 * @param message
		 */
		public CompiledError(String message,Exception cause) {
			super();
			this.cause = cause;
			this.message = message;
		}

		/**
		 * @param cause
		 */
		CompiledError(Exception cause) {
			this.cause = cause;
		}

		public void encode(RendererBase renderer, FacesContext context, UIComponent component) throws IOException {
			// TODO - concrete compilation error.
			throw new FacesException(message,cause);
		}

		public List getChildren() {
			return Collections.EMPTY_LIST;
		}

		public void addChild(PreparedTemplate child) throws SAXException {
			// DO Nothing.
			throw new SAXException(Messages.getMessage(Messages.NO_CHILD_ALLOWED));
		}

		public void encode(TemplateContext context) throws IOException {
			throw new FacesException(message,cause);
			
		}

		public void encode(TemplateContext context, String breakPoint) throws IOException {
			throw new FacesException(message,cause);
			
		}

		public void setParent(PreparedTemplate parent) {
			// DO Nothing.
		}

		public String getTag() {
			// TODO Auto-generated method stub
			return "f:error";
		}

		public Object getValue(TemplateContext context) {
			// error don't have value
			return null;
		}
		
	};

}
