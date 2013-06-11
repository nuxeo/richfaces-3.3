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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import org.ajax4jsf.builder.generator.Logger;


/**
 * Base class for most configuration elements. Hold common properties, applicable to
 * most elements - component, tag, renderer, property
 * @author shura (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/20 20:57:58 $
 *
 */
public class JsfBean  implements LoaderHolder {

	/**
	 * Name for element 
	 */
	private String _name;
	/**
	 * Display name for configuration file
	 */
	private String _displayname;
	
	private String _xmlEncodedDisplayname;
	/**
	 * Description of element
	 */
	private String _description;
	
	private String _xmlEncodedDescription;
	/**
	 * Icon image file name, for config files
	 */
	private String _icon;
	/**
	 * Full Java class name for element.
	 */
	private String _classname;
	/**
	 * Full Java name for base class of element
	 */
	private String _superclass;

	private LoaderHolder _parent = null;
	
	/**
	 * appropriate test class 
	 */
	private TestClassHolder _test;
	
	
	
	public TestClassHolder getTest() {
		return _test;
	}

	public void setTest(TestClassHolder test) {
		this._test = test;
	}

	/**
	 * @return Returns the classname.
	 */
	public String getClassname() {
		return _classname;
	}

	/**
	 * Extract package name for class.
	 * TODO - handle generalization <package.Class>
	 * @return
	 */
	public String getPackageName() {
		int lastPoint = getClassname().lastIndexOf('.');
		if (lastPoint>0) {
			return getClassname().substring(0,lastPoint);
		}
		return "";
	}

	/**
	 * Convert full class name to simple.
	 * @return class name without package name. 
	 */
	public String getSimpleClassName() {
		int lastPoint = getClassname().lastIndexOf('.');
		if (lastPoint>0) {
			return getClassname().substring(lastPoint+1);
		}
		return getClassname();
	}

	public List<JsfBean> getGenericTypes() {
		return Collections.emptyList();
	}
	
	
	/**
	 * @param classname The classname to set.
	 */
	public void setClassname(String classname) {
		_classname = classname;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * @return Returns the xmlEncodedDescription.
	 */
	public String getXmlEncodedDescription() {
		return this._xmlEncodedDescription;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		_description = description;
		_xmlEncodedDescription = escapeXML(description);
	}

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayname() {
		return _displayname;
	}

	/**
	 * @return Returns the xmlEncodedDisplayname.
	 */
	public String getXmlEncodedDisplayname() {
		return this._xmlEncodedDisplayname;
	}

	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayname(String displayName) {
		_displayname = displayName;
		_xmlEncodedDisplayname = this.escapeXML(displayName);
	}

	/**
	 * @return Returns the icon.
	 */
	public String getIcon() {
		return _icon;
	}

	/**
	 * @param icon The icon to set.
	 */
	public void setIcon(String icon) {
		_icon = icon;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @return Returns the superclass.
	 */
	public String getSuperclass() {
		return _superclass;
	}

	/**
	 * @param superclass The superclass to set.
	 */
	public void setSuperclass(String superclass) {
		_superclass = superclass;
	}

	/**
	 * Check for instance for class or interface name. 
	 * @param name
	 * @return
	 */
	public boolean isInstanceof(String name) {
		String classname = getClassname();
		if(null == classname){
			String msg = "classname not set in "+getClass().getName()+" for element "+getName();
			getLog().error(msg);
			throw new NullPointerException(msg);
		}
		if (classname.equals(name) ) {
			return true;
		}
		try {
			Class<?> beanClass;
			try {
				beanClass = getLoader().loadClass(classname);
			} catch (ClassNotFoundException e) {
				if (null == getSuperclass()) {
					return false;
				}
				beanClass = getLoader().loadClass(getSuperclass());
			}
			Class<?> superClass = getLoader().loadClass(name);
			return superClass.isAssignableFrom(beanClass);
		} catch (ClassNotFoundException e) {
			getLog().warn("Could't instantiate for testing class "+classname);
		} catch (Exception e) {
			getLog().warn("Error in testing class "+classname);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Check for not-abstract method with given name for class.
	 * @param name
	 * @return
	 */
	public boolean haveMethod(String name) {
		try {
			Class<?> beanClass;
			try {
				beanClass = getLoader().loadClass(getClassname());
			} catch (ClassNotFoundException e) {
				beanClass = getLoader().loadClass(getSuperclass());
			}
			Method[] methods = beanClass.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if(methods[i].getName().equals(name)) {
					if (! Modifier.isAbstract(methods[i].getModifiers())) {
						return true;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			getLog().warn("Could't instantiate for testing class "+getClassname());
		} catch (Exception e) {
			getLog().warn("Error in testing class "+getClassname());
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	public String escapeXML(String raw) {
		if (null != raw) {
			return raw.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("&", "&amp;");
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.config.LoaderHolder#getLoader()
	 */
	public ClassLoader getLoader() {
		// TODO Auto-generated method stub
		return getParent().getLoader();
	}
	
	public Logger getLog(){
		return getParent().getLog();
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.config.LoaderHolder#setLoader(java.lang.ClassLoader)
	 */
	public void setLoader(ClassLoader loader) {
	}

	
	/**
	 * @return Returns the parent.
	 */
	public LoaderHolder getParent() {
		return _parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(LoaderHolder parent) {
		_parent = parent;
	}

	public String upperFirstChar(String name) {
		char[] charName = name.toCharArray();
		charName[0] = Character.toUpperCase(charName[0]);
		return new String(charName);
	}

}
