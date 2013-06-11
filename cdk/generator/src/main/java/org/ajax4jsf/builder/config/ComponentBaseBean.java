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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.naming.ConfigurationException;

import org.apache.commons.beanutils.PropertyUtils;

public class ComponentBaseBean extends JsfBean {

	private static final String[] ignorableComponentProperties = {
								"class",
								"attributes",
								"childCount",
								"children",
								"facets",
								"facetsAndChildren",
								"family",
								"parent",
								"rendererType",
								"rendersChildren",
								"submittedValue",
								"transient"};
	private static final String[] enabledTagProperties = {
			"binding"
			};
	private static final String[] attachedStateProperties = {
			"javax.faces.component.StateHolder",
			"java.util.List",
			"javax.faces.el.MethodBinding",
			"javax.faces.el.ValueBinding",
			"javax.el.MethodExpression",
			"javax.el.ValueExpression",
			"javax.faces.convert.Converter"
		};
	
	
	/**
	 * JSP ( facelets ? ) tag description for component
	 */
	private TagBean _tag;
	private TagHandlerBean _taghandler;
	/**
	 * Descriptions of component JavaBean properties
	 */
	private Map<String,PropertyBean> properties = new TreeMap<String,PropertyBean>();
	/**
	 * Flag for generate component instance class
	 */
	private boolean generate = true;

	/**
	 * @return Returns the tag.
	 */
	public TagBean getTag() {
		return _tag;
	}

	/**
	 * @param tag The tag to set.
	 */
	public void setTag(TagBean tag) {
		_tag = tag;
		tag.setParent(this);
	}

	/**
	 * @return Returns the generate.
	 */
	public boolean isGenerate() {
		return this.generate;
	}

	/**
	 * @param generate The generate to set.
	 */
	public void setGenerate(boolean generate) {
		this.generate = generate;
	}

	/**
	 * @return Returns the tagSupport.
	 */
	public TagHandlerBean getTaghandler() {
		return this._taghandler;
	}

	/**
	 * @param tagSupport The tagSupport to set.
	 */
	public void setTaghandler(TagHandlerBean tagSupport) {
		this._taghandler = tagSupport;
	}

	/**
	 * @return Returns the properties.
	 */
	public Collection<PropertyBean> getProperties() {
		return properties.values();
	}

	/**
	 * @param name
	 * @return true if component have property with given name
	 */
	public boolean containProperty(String name) {
		return properties.containsKey(name);
	}
	
	public PropertyBean getProperty(String name){
	    return properties.get(name);
	}

	/**
	 * Append property to component. If property with name exist, replace it
	 * @param property
	 */
	public void addProperty(PropertyBean property) {
		this.properties.put(property.getName(),property);
		property.setParent(this);
	}

	/**
	 * Subclasses should extend this method to provide specifc checks
	 * 
	 * Check existing and default properties
	 * For properties filled from configuration, attempt to set additional parameters.
	 * If base class have any bean properties, append it to configured
	 * @throws ConfigurationException 
	 */
	public void checkProperties() throws ParsingException {
		try {
			getLog().debug("Parse properties for Component "+getName()+" with superclass "+getSuperclass());
            if (getSuperclass() != null)
            {
    			Class <?> superClass = getLoader().loadClass(getSuperclass());
    
    			new ClassWalkingLogic(superClass)
    				.walk(new ClassVisitor() {
    					public void visit(Class<?> clazz) {
    						checkPropertiesForClass(clazz);
    					}
    				});
            }
		} catch (ClassNotFoundException e) {
			getLog().error("superclass not found for component "+getName(), e);
		}
		if (null != getTag()) {
			try {
				Class superClass = getLoader().loadClass(getTag().getSuperclass());
				PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(superClass);
				// for all properties, add it to component. If property have not abstract getter/setter ,
				// add it with exist = true . If property in list of hidden names, set hidden = true.
				for (int i = 0; i < properties.length; i++) {
					PropertyDescriptor descriptor = properties[i];
					Method writeMethod = descriptor.getWriteMethod();
					if(containProperty(descriptor.getName())){
						if (null != writeMethod && !Modifier.isAbstract(writeMethod.getModifiers()) && Modifier.isPublic(writeMethod.getModifiers()) ) {
							((PropertyBean) this.properties.get(descriptor
									.getName())).setExistintag(true);
						}
					} else if (null != writeMethod && Modifier.isPublic(writeMethod.getModifiers())) {
						if (Arrays.asList(enabledTagProperties).contains(
								descriptor.getName())) {
							Class type = descriptor.getPropertyType();
							getLog().debug("Register tag property  "
									+ descriptor.getName() + " with type name "
									+ type.getCanonicalName() );
							PropertyBean property = new PropertyBean();
							property.setName(descriptor.getName());
							property.setDescription(descriptor
									.getShortDescription());
							property
									.setDisplayname(descriptor.getDisplayName());
							property.setClassname(descriptor.getPropertyType()
									.getCanonicalName());
							property.setExist(true);
							if (!Modifier
									.isAbstract(writeMethod.getModifiers())) {
								property.setExistintag(true);
							}
							addProperty(property);
						}
					}
				}
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				getLog().error("superclass not found for tag "+getTag().getName(), e);
			}
			
		}
	}

	
	
	
	/**
	 * @param superClass
	 */
	private void checkPropertiesForClass(Class<?> superClass) {
		getLog().debug("Check properties for class "+superClass.getName());
		// get all property descriptors
		PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(superClass);
		// for all properties, add it to component. If property have not abstract getter/setter ,
		// add it with exist = true . If property in list of hidden names, set hidden = true.
		PropertyBean property;
		for (int i = 0; i < properties.length; i++) {
			PropertyDescriptor descriptor = properties[i];
			if(!containProperty(descriptor.getName())){
				if(isIgnorableProperty(superClass, descriptor.getName())) {
					continue;
				}
				Class<?> type = descriptor.getPropertyType();
				getLog().debug("Register property  "+descriptor.getName()+" with type name "+type.getCanonicalName());
				property = new PropertyBean();
				property.setName(descriptor.getName());
				property.setDescription(descriptor.getShortDescription());
				property.setDisplayname(descriptor.getDisplayName());
				property.setClassname(descriptor.getPropertyType().getCanonicalName());
				property.setExist(true);
				addProperty(property);
			} else {
				// Load and check property.
				getLog().debug("Check  property  "+descriptor.getName());
				property = (PropertyBean) this.properties.get(descriptor.getName());
				if(property.getClassname() == null) {
					property.setClassname(descriptor.getPropertyType().getCanonicalName());						
				} else {
					if(!property.getClassname().equals(descriptor.getPropertyType().getCanonicalName())){
						String message = "Class "+property.getClassname()+" for property "+property.getName()+" not equals with real bean property type: "+descriptor.getPropertyType().getCanonicalName();
						getLog().error(message);
						//throw new IllegalArgumentException(message);
					}
				}
				if (property.getDescription() == null) {
					property.setDescription(descriptor.getShortDescription());					
				}
				if(property.getDisplayname() == null){
					property.setDisplayname(descriptor.getDisplayName());
				}
				property.setExist(true);
			}
				Method getter = descriptor.getReadMethod();
				Method setter = descriptor.getWriteMethod();
				// Abstract methods
				if(null != setter && null != getter ){
					if( (Modifier.isAbstract(getter.getModifiers()) && Modifier.isAbstract(setter.getModifiers())) || superClass.isInterface()){
						getLog().debug("Detect as abstract property  "+descriptor.getName());
						property.setExist(false);
					} 
				}
				
				if(null == setter || (! Modifier.isPublic(setter.getModifiers())) ){
					getLog().debug("Detect as hidden property  "+descriptor.getName());
					property.setHidden(true);
				}
				if (isAttachedProperty(property)) {
					property.setAttachedstate(true);
				}
				if (property.isInstanceof("javax.faces.el.MethodBinding") || 
						property.isInstanceof("javax.faces.el.ValueBinding")) {
					property.setElonly(true);
				}
	
		}
	}

	private boolean isIgnorableProperty(Class<?> base, String name) {
		return Arrays.asList(ignorableComponentProperties).contains(name);
	}

	private boolean isAttachedProperty(PropertyBean prop) {
		for (int i = 0; i < attachedStateProperties.length; i++) {
			String clazz = attachedStateProperties[i];
			if(prop.isInstanceof(clazz)) {
				return true;
			}
		}
		return false;
	}

    public boolean isStateHolderRequired() {
        getLog().info("isStateHolderRequired");
        for (PropertyBean propertyBean : getProperties())
        {
            getLog().info("Property " + propertyBean.getName() + "/" + propertyBean.isTransient());
            if (!propertyBean.isTransient())
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean isSuperclassImplementsStateHolder()
    {
        try {
            Class superClass = getLoader().loadClass(getSuperclass());
            return (StateHolder.class.isAssignableFrom(superClass));
        } catch (ClassNotFoundException e) {
            getLog().error("superclass not found for tag "+getTag().getName(), e);
            return false;
        }
    }
    
    public boolean isSuperSaveStateMethodExists()
    {
        try {
            Class superClass = getLoader().loadClass(getSuperclass());
            Class[] signature = {FacesContext.class};
            try {
                Method m = superClass.getMethod("saveState", signature);
                return !Modifier.isAbstract(m.getModifiers());
            } catch (NoSuchMethodException e) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            getLog().error("superclass not found for tag "+getTag().getName(), e);
            return false;
        }
    }
    
    public boolean isSuperRestoreStateMethodExists()
    {
        try {
            Class superClass = getLoader().loadClass(getSuperclass());
            Class[] signature = {FacesContext.class, Object.class};
            try {
                Method m = superClass.getMethod("restoreState", signature);
                return !Modifier.isAbstract(m.getModifiers());
            } catch (NoSuchMethodException e) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            getLog().error("superclass not found for tag "+getTag().getName(), e);
            return false;
        }
    }
    
    public boolean isSuperIsTransientMethodExists()
    {
        try {
            Class superClass = getLoader().loadClass(getSuperclass());
            Class[] signature = new Class[0];
            try {
                Method m = superClass.getMethod("isTransient", signature);
                return !Modifier.isAbstract(m.getModifiers());
            } catch (NoSuchMethodException e) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            getLog().error("superclass not found for tag "+getTag().getName(), e);
            return false;
        }
    }
    
    public boolean isSuperSetTransientMethodExists()
    {
        try {
            Class superClass = getLoader().loadClass(getSuperclass());
            Class[] signature = {boolean.class};
            try {
                Method m = superClass.getMethod("setTransient", signature);
                return !Modifier.isAbstract(m.getModifiers());
            } catch (NoSuchMethodException e) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            getLog().error("superclass not found for tag "+getTag().getName(), e);
            return false;
        }
    }
}
