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

package org.ajax4jsf.builder.config;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.ConfigurationException;

import org.ajax4jsf.builder.generator.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ExtendedBaseRules;
import org.apache.tools.ant.BuildException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

/**
 * Parse builder config file for use with component creation.
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.10 $ $Date: 2007/02/20 20:57:58 $
 * 
 */
public class BuilderConfig implements LoaderHolder {

    /**
         * Resource path to config file schemas and entities
         */
    public static final String COMPONENT_SCHEMA_BASE = "/META-INF/schema/";

    /**
         * Resource path to config file schemas and entities
         */
    public static final String ENTITIES_FOLDER = "entities";

    /**
         * default URI to dtd in classpath
         */
    private static final String COMPONENT_CONFIG_DTD_URI = COMPONENT_SCHEMA_BASE
	    + "component-config.dtd";

    /**
         * root element of configuration file
         */
    private static final String GENERATOR_CONFIG_ROOT_ELEMENT = "components";

    /**
         * PUBLIC Id of configuration schema
         */
    private static final String GENERATOR_CONFIG_PUBLIC_ID = "-//AJAX4JSF//CDK Generator config/EN";

    /**
         * components described in this configuration
         */
    private List<ComponentBean> components = new ArrayList<ComponentBean>();

    /**
         * validators described in this configuration
         */
    private List<ValidatorBean> validators = new ArrayList<ValidatorBean>();

    /**
         * components described in this configuration
         */
    private List<ConverterBean> converters = new ArrayList<ConverterBean>();

    /**
         * renderers described in this configuration
         */
    private List<RendererBean> renderers = new ArrayList<RendererBean>();

    private List<ListenerBean> listeners = new ArrayList<ListenerBean>();

    private List<FunctionBean> functions = new ArrayList<FunctionBean>();
    
    private ClassLoader _loader;

    private Logger _log;

    /**
         * @param project -
         *                current ant project
         */
    public BuilderConfig(ClassLoader loader, Logger log) {
	_loader = loader;
	_log = log;
    }

    /**
         * Parsing builder configuration file
         * 
         * @param configFile
         * @throws ParsingException
         */
    public void parseConfig(final File configFile) throws ParsingException {
	getLog().info(" Parse config file " + configFile.toString());
	Digester digester = new Digester();
	digester.setRules(new ExtendedBaseRules());
	digester.setValidating(false);
	digester.setNamespaceAware(false);
	// try {
	// URL dtd =
        // this.getClass().getClassLoader().getResource(COMPONENT_CONFIG_DTD_URI);
	// new
        // URL("resource:/com/exadel/vcp/builder/config/component-config.dtd");
	getLog()
		.debug("Register config DTD as URI " + COMPONENT_CONFIG_DTD_URI);
	digester.register(GENERATOR_CONFIG_PUBLIC_ID, COMPONENT_CONFIG_DTD_URI);
	// } catch (MalformedURLException e) {
	// throw new ParsingException("Malformed URL for internal DTD
        // reference",e);
	// }

	// setup custom entity resolver for handle file-resource path's
	// resolve DTD even it not pointed in file, and, for entities - resolve
        // it in
	// classpath if entity registered in DTD witn path /META-INF/schema
	digester.setEntityResolver(new EntityResolver2() {

	    /*
                 * (non-Javadoc)
                 * 
                 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String,
                 *      java.lang.String)
                 */
	    public InputSource resolveEntity(String publicId, String systemId)
		    throws SAXException, IOException {
		getLog().debug(
			"Request for entity with systemId " + systemId
				+ " and publicId " + publicId);
		if (GENERATOR_CONFIG_PUBLIC_ID.equals(publicId)) {
		    return getDTDSource();
		} else if (null != publicId && null != systemId
			&& systemId.contains(ENTITIES_FOLDER)) {
		    int base = systemId.indexOf(ENTITIES_FOLDER);
		    String entity = COMPONENT_SCHEMA_BASE
			    + systemId.substring(base);
		    getLog().debug(
			    "attempt to load entity from classpath " + entity);
		    InputStream entityStream = BuilderConfig.class
			    .getResourceAsStream(entity);
		    if (null != entityStream) {
			return new InputSource(entityStream);
		    }
		}
		return null;
	    }

	    public InputSource getExternalSubset(String name, String baseURI)
		    throws SAXException, IOException {
		getLog().debug(
			"Request for ExternalSubset with name " + name
				+ " and baseURI " + baseURI);
		if (GENERATOR_CONFIG_ROOT_ELEMENT.equals(name)) {
		    return getDTDSource();
		}
		return null;
	    }

	    public InputSource resolveEntity(String name, String publicId,
		    String baseURI, String systemId) throws SAXException,
		    IOException {
		getLog().debug(
			"Request for extended entity with systemId " + systemId
				+ " and publicId " + publicId);
		getLog().debug(
			"additional parameters with name " + name
				+ " and baseURI " + baseURI);
		if ("[dtd]".equals(name)
			&& GENERATOR_CONFIG_PUBLIC_ID.equals(publicId)) {
		    return getDTDSource();
		} else if (null == name
			|| (!name.startsWith("[") && !name.startsWith("&"))) {
		    return resolveEntity(publicId, systemId);
		}
		return null;
	    }

	    /**
                 * Resolve config DTD from classpath
                 * 
                 * @return source of config file DTD
                 */
	    private InputSource getDTDSource() {
		return new InputSource(BuilderConfig.class
			.getResourceAsStream(COMPONENT_CONFIG_DTD_URI));
	    }

	});

	// Parsing rules.

	// Components
	String path = "components/component";
	digester.addObjectCreate(path, ComponentBean.class);
	digester.addBeanPropertySetter(path + "/name");
	digester.addBeanPropertySetter(path + "/family");
	digester.addBeanPropertySetter(path + "/classname");
	digester.addBeanPropertySetter(path + "/superclass");
	digester.addBeanPropertySetter(path + "/description");
	digester.addBeanPropertySetter(path + "/displayname");
	digester.addBeanPropertySetter(path + "/icon");
	// TODO - for superclass, populate from description in config file, if
        // exist
	digester.addSetProperties(path);
	digester.addSetNext(path, "addComponent");
	path = "components/renderer";
	digester.addObjectCreate(path, RendererBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addRenderer");
	path = "components/component/renderer";
	digester.addObjectCreate(path, RendererBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setRenderer");

	path = "components/component/facet";
	digester.addObjectCreate(path, JsfBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addFacet");
	path = "components/component/event";
	digester.addObjectCreate(path, EventBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addEvent");

	// Validators

	path = "components/validator";
	digester.addObjectCreate(path, ValidatorBean.class);
	digester.addBeanPropertySetter(path + "/id");
	digester.addBeanPropertySetter(path + "/classname");
	digester.addBeanPropertySetter(path + "/superclass");
	digester.addBeanPropertySetter(path + "/description");
	digester.addBeanPropertySetter(path + "/displayname");
	digester.addBeanPropertySetter(path + "/icon");
	// TODO - for superclass, populate from description in config file, if
        // exist
	digester.addSetProperties(path);
	digester.addSetNext(path, "addValidator");

	path = "components/validator/property";
	digester.addObjectCreate(path, PropertyBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addProperty");

	// Converters

	path = "components/converter";
	digester.addObjectCreate(path, ConverterBean.class);
	digester.addBeanPropertySetter(path + "/id");
	digester.addBeanPropertySetter(path + "/classname");
	digester.addBeanPropertySetter(path + "/superclass");
	digester.addBeanPropertySetter(path + "/description");
	digester.addBeanPropertySetter(path + "/displayname");
	digester.addBeanPropertySetter(path + "/icon");
	digester.addBeanPropertySetter(path + "/forclass");
	// TODO - for superclass, populate from description in config file, if
        // exist
	digester.addSetProperties(path);
	digester.addSetNext(path, "addConverter");

	// Functions

	path = "components/function";
	digester.addObjectCreate(path, FunctionBean.class);
	digester.addBeanPropertySetter(path + "/name");
	digester.addBeanPropertySetter(path + "/description");
	digester.addBeanPropertySetter(path + "/method");
	digester.addSetNext(path, "addFunction");
	
	// - Tags & Tag handlers

	path = "components/component/tag";
	digester.addObjectCreate(path, TagBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTag");

	path = "components/validator/tag";
	digester.addObjectCreate(path, TagBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTag");

	path = "components/converter/tag";
	digester.addObjectCreate(path, TagBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTag");

	path = "components/component/tag/test";
	digester.addObjectCreate(path, TagTestClassHolder.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTest");

	path = "components/component/test";
	digester.addObjectCreate(path, TestClassHolder.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTest");

	path = "components/component/taghandler";
	digester.addObjectCreate(path, TagHandlerBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTaghandler");
	/*
         * path = "components/component/taghandler/test";
         * digester.addObjectCreate(path, TestClassHolder.class);
         * digester.addBeanPropertySetter(path+"/?");
         * digester.addSetProperties(path); digester.addSetNext(path,
         * "setTest");
         */

	// Properties
	path = "components/component/property";
	digester.addObjectCreate(path, PropertyBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addProperty");

	// path = "components/validator/property";
	// digester.addObjectCreate(path, PropertyBean.class);
	// digester.addBeanPropertySetter(path+"/?");
	// digester.addSetProperties(path);
	// digester.addSetNext(path, "addProperty");

	path = "components/converter/property";
	digester.addObjectCreate(path, PropertyBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addProperty");

	path = "*/properties/property";
	digester.addObjectCreate(path, PropertyBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addProperty");

	// Listeners

	path = "components/listener";
	digester.addObjectCreate(path, ListenerBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addListener");
	path = "components/listener/tag";
	digester.addObjectCreate(path, TagBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTag");
	path = "components/listener/taghandler";
	digester.addObjectCreate(path, TagHandlerBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "setTaghandler");
	path = "components/listener/property";
	digester.addObjectCreate(path, PropertyBean.class);
	digester.addBeanPropertySetter(path + "/?");
	digester.addSetProperties(path);
	digester.addSetNext(path, "addProperty");

	// Set this config as root.
	digester.push(this);
	try {
	    digester.parse(configFile.toString());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    throw new ParsingException("I/O error on parsing config file ", e);
	} catch (SAXException e) {
	    // TODO Auto-generated catch block
	    throw new ParsingException("SAX Parsing error in config file ", e);
	}
	// checkComopnentProperties();
	// return this;
    }

    /**
         * Check all components for existing and default properties.
         * 
         * @param classpath -
         *                classpath to find user components, renderers, tags
         * @throws ConfigurationException
         */
    public void checkComponentProperties() throws ParsingException {
	// ClassLoader loader = getProject().createClassLoader(classpath);
	// if(null == loader) {
	// loader = this.getClass().getClassLoader();
	// }
	// setLoader(loader);

	for (ListenerBean listener : getListeners()) {
	    try {
		Class<?> listenerClass = Class.forName(listener
			.getComponentclass(), false, getLoader());

		for (ComponentBean bean : getComponents()) {
		    if (bean.getSuperclass() != null) {
			Class<?> componentSClass = Class.forName(bean
				.getSuperclass(), false, getLoader());
			
			if (listenerClass.isAssignableFrom(componentSClass)) {

			    PropertyBean listenerProperty = bean
				    .getProperty(listener.getName());
			    
			    if (null == listenerProperty) {
					listenerProperty = new PropertyBean();
					listenerProperty.setName(listener.getName());
					bean.addProperty(listenerProperty);
				    listenerProperty.setClassname("javax.el.MethodExpression");
			    }
			    
			    Map<String, PropertyDescriptor> map = 
			    	getPropertyDescriptors(componentSClass);
				
			    PropertyDescriptor propertyDescriptor = 
			    	map.get(listener.getName());
			    
			    if (propertyDescriptor != null) {
			    	String componentPropertyName = propertyDescriptor.getPropertyType().getName();
			    	
			    	if (!componentPropertyName.equals(listenerProperty.getClassname())) {
			    		_log.error(
			    				String.format("Overriding property type %s with %s for %s.%s",
			    				listenerProperty.getClassname(),
			    				componentPropertyName,
			    				bean.getClassname(),
			    				listener.getName()
			    				));
			    	}
			    	
					listenerProperty.setClassname(componentPropertyName);
			    }
			    
			    // TODO - check existing property for compability with this listener. 
			    listenerProperty.setEl(true);
			    listenerProperty.setElonly(true);
			    listenerProperty.setAttachedstate(true);
			    listenerProperty.setMethodargs(listener
				    .getEventclass());
			    listener.addSuitableComponent(bean);
			}
		    }
		}
	    } catch (ClassNotFoundException e) {
		throw new BuildException(e);
	    }
	    
	    listener.checkProperties();
	}

	for (Iterator iter = this.getComponents().iterator(); iter.hasNext();) {
	    ComponentBaseBean component = (ComponentBaseBean) iter.next();
	    component.checkProperties();
	}

	for (Iterator iter = this.getValidators().iterator(); iter.hasNext();) {
	    ComponentBaseBean component = (ComponentBaseBean) iter.next();
	    component.checkProperties();
	}

	for (Iterator iter = this.getConverters().iterator(); iter.hasNext();) {
	    ComponentBaseBean component = (ComponentBaseBean) iter.next();
	    component.checkProperties();
	}

    }

    
    private Map<String, PropertyDescriptor> getPropertyDescriptors(Class<?> clazz) {
    	
    	if (clazz.equals(Object.class)) {
    		return Collections.emptyMap();
    	}
    	
    	Map<String, PropertyDescriptor> m = 
    		new TreeMap<String, PropertyDescriptor>();

    	Class<?> superclass = clazz.getSuperclass();
    	if (superclass != null) {
    		m.putAll(getPropertyDescriptors(superclass));
    	}
    	
    	Class<?>[] interfaces = clazz.getInterfaces();
    	if (interfaces != null) {
        	for (Class<?> intrfc : interfaces) {
    			m.putAll(getPropertyDescriptors(intrfc));
    		}
    	}
    	
    	PropertyDescriptor[] descriptors = 
    		PropertyUtils.getPropertyDescriptors(clazz);
    	
    	for (PropertyDescriptor propertyDescriptor : descriptors) {
			m.put(propertyDescriptor.getName(), propertyDescriptor);
		}
    	
    	return m;
    }
    
    /*
         * (non-Javadoc)
         * 
         * @see com.exadel.vcp.builder.config.LoaderHolder#getLoader()
         */
    public ClassLoader getLoader() {
	return _loader;
    }

    /*
         * (non-Javadoc)
         * 
         * @see com.exadel.vcp.builder.config.LoaderHolder#setLoader(java.lang.ClassLoader)
         */
    public void setLoader(ClassLoader loader) {
	_loader = loader;
    }

    public Logger getLog() {
	return _log;
    }

    public void addComponent(ComponentBean component) {
	this.components.add(component);
	component.setParent(this);
    }

    public void addValidator(ValidatorBean validator) {
	this.validators.add(validator);
	validator.setParent(this);
    }

    public void addConverter(ConverterBean converter) {
	this.converters.add(converter);
	converter.setParent(this);
    }

    public void addRenderer(RendererBean renderer) {
	this.renderers.add(renderer);
	renderer.setParent(this);
    }

    public void addListener(ListenerBean listener) {
	this.listeners.add(listener);
	listener.setParent(this);
    }

    public void addFunction(FunctionBean function) {
	this.functions.add(function);
	function.setParent(this);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#clear()
         */
    public void clear() {
	components.clear();
	renderers.clear();
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#contains(java.lang.Object)
         */
    public boolean contains(Object o) {
	return components.contains(o);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#get(int)
         */
    public ComponentBaseBean get(int index) {
	return components.get(index);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#indexOf(java.lang.Object)
         */
    public int indexOf(ComponentBaseBean o) {
	return components.indexOf(o);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#isEmpty()
         */
    public boolean isEmpty() {
	return components.isEmpty();
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#listIterator()
         */
    public List<ComponentBean> getComponents() {
	return components;
    }

    public List<ConverterBean> getConverters() {
	return converters;
    }

    public List<ValidatorBean> getValidators() {
	return validators;
    }

    public List<ListenerBean> getListeners() {
	return listeners;
    }

    /**
         * @return the renderers
         */
    public List<RendererBean> getRenderers() {
	return this.renderers;
    }

    public List<FunctionBean> getFunctions() {
	return functions;
    }
    
    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#remove(int)
         */
    public ComponentBaseBean remove(int index) {
	return components.remove(index);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#remove(java.lang.Object)
         */
    public boolean remove(ComponentBaseBean o) {
	return components.remove(o);
    }

    /*
         * (non-Javadoc)
         * 
         * @see java.util.List#size()
         */
    public int size() {
	return components.size();
    }
}
