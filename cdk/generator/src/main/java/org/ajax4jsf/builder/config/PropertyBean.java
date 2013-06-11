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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Describe JavaBean property of component.
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/20 20:57:59 $
 *
 */
public class PropertyBean extends JsfBean {
	
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\s*,\\s*"); 

	/**
	 * Hold default values for java classes and base types
	 */
	private static Map<String,String> defaults;
	
	/**
	 * Enable expression language values for property
	 */
	private boolean _el= true;
	
	/**
	 * Property can be only as expression(value) binding. For example, actionListener
	 */
	private boolean _elonly = false;
		
	/**
	 * If true, property not include to Jsp tag
	 */
	private boolean _hidden = false;
	
	/**
	 * If true, do not generate getter/setter/state save code for property in component
	 */
	private boolean _exist = false;
	
	/**
	 * If true, property exist in tag superclass - do not generate setter code
	 */
	private boolean _existintag = false;

	/**
	 * If true, Generate access methods to property field ( bypass binding check ).
	 * additional getter will be have name getRawPropertyName , setter - setRawPropertyName  
	 */
	private boolean _raw = false;
	
	/**
	 * Default value for non-initialized property
	 */
	private String _defaultvalue = null;
	
	/**
	 * If true, do not include property field to save/restore state code
	 */
	private boolean _transient = false;
	
	/**
	 * If trie, save state of property by call {@link javax.faces.component.UIComponentBase#saveAttachedState(javax.faces.context.FacesContext, java.lang.Object)}
	 */
	private boolean _attachedstate = false;
	
	/**
	 * Alias for property in jsp tag
	 */
	private String _alias = null;
	/**
	 * Comma-separated list of class names for {@link javax.faces.el.MethodBinding}
	 * or other reflection-based properties.
	 * instanses of calls.
	 */
	private String _methodargs = null;
	
	/**
	 * Return type for {@link javax.faces.el.MethodBinding}
	 * or other reflection-based properties.
	 * instanses of calls.
	 */
	private String _returntype = null;
	
	/**
	 * Indicate what this property Required for component.
	 */
	private boolean _required = false;

	static  {
		defaults = new HashMap<String,String>();
		defaults.put(Boolean.TYPE.getName(),"false");
		defaults.put(Byte.TYPE.getName(), "Byte.MIN_VALUE");
		defaults.put(Character.TYPE.getName(), "Character.MIN_VALUE");
		defaults.put(Integer.TYPE.getName(), "Integer.MIN_VALUE");
		defaults.put(Long.TYPE.getName(), "Long.MIN_VALUE");
		defaults.put(Float.TYPE.getName(), "Float.MIN_VALUE");
		defaults.put(Double.TYPE.getName(), "Double.MIN_VALUE");
		defaults.put(Short.TYPE.getName(), "Short.MIN_VALUE");
	}
	
	/**
	 * @return Returns the el.
	 */
	public boolean isEl() {
		return _el;
	}

	/**
	 * @param el The el to set.
	 */
	public void setEl(boolean el) {
		_el = el;
	}

	/**
	 * @return Returns the elOnly.
	 */
	public boolean isElonly() {
		return _elonly;
	}

	/**
	 * @param elOnly The elOnly to set.
	 */
	public void setElonly(boolean elOnly) {
		_elonly = elOnly;
	}

	/**
	 * @return Returns the exist.
	 */
	public boolean isExist() {
		return this._exist;
	}

	/**
	 * @param exist The exist to set.
	 */
	public void setExist(boolean exist) {
		this._exist = exist;
	}

	/**
	 * @return Returns the existInEl.
	 */
	public boolean isExistintag() {
		return this._existintag;
	}

	/**
	 * @param existInEl The existInEl to set.
	 */
	public void setExistintag(boolean existInEl) {
		this._existintag = existInEl;
	}

	/**
	 * @return Returns the hidden.
	 */
	public boolean isHidden() {
		return this._hidden;
	}

	/**
	 * @param hidden The hidden to set.
	 */
	public void setHidden(boolean hidden) {
		this._hidden = hidden;
		if (hidden) {
			this._existintag = true;
		}
	}

	/**
	 * Disable all generations for this property. Used for exclude properties defined
	 * in standart sets as entities in config file or detected from superclass. 
	 * @param disabled if true , set hidden , exist and existintag properties to true.
	 */
	public void setDisabled(boolean disabled) {
		if (disabled) {
			setExist(true);
			setHidden(true);
		}
	}
	/**
	 * @return Returns the raw.
	 */
	public boolean isRaw() {
		return _raw;
	}

	/**
	 * @param raw The raw to set.
	 */
	public void setRaw(boolean raw) {
		_raw = raw;
	}

	/**
	 * @return Returns the attachedState.
	 */
	public boolean isAttachedstate() {
		return this._attachedstate;
	}

	/**
	 * @param attachedState The attachedState to set.
	 */
	public void setAttachedstate(boolean attachedState) {
		this._attachedstate = attachedState;
	}

	/**
	 * @return Returns the methodargs.
	 */
	public String getMethodargs() {
		return _methodargs;
	}

	/**
	 * @param methodargs The methodargs to set.
	 */
	public void setMethodargs(String methodargs) {
		_methodargs = methodargs;
	}
	
	/**
	 * @return comma-separated list of classes for method args ( need to build method signature )
	 */
	public String getMethodArgsClasses(){
		if(null != this._methodargs){
			StringBuffer result = new StringBuffer();
			String[] classes = SEPARATOR_PATTERN.split(this._methodargs.trim());
			for (int i = 0; i < classes.length; i++) {
				String argumentClass = classes[i];
				
				if (argumentClass.length() != 0) {
					if(i!=0){
						result.append(',');
					}
					result.append(argumentClass).append(".class");
				} else {
					if (classes.length == 1) {
						//no arguments
						break;
					} else {
						throw new IllegalArgumentException("Error parsing methodargs: " + this._methodargs);
					}
				}
			}
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * @return Returns the transient.
	 */
	public boolean isTransient() {
		return this._transient;
	}

	/**
	 * @param transient1 The transient to set.
	 */
	public void setTransient(boolean transient1) {
		this._transient = transient1;
	}

	/**
	 * @return Returns the required.
	 */
	public boolean isRequired() {
		return this._required;
	}

	/**
	 * @param required The required to set.
	 */
	public void setRequired(boolean required) {
		this._required = required;
	}

	/**
	 * @return Returns the alias.
	 */
	public String getAlias() {
		return _alias;
	}

	/**
	 * @param alias The alias to set.
	 */
	public void setAlias(String alias) {
		_alias = alias;
	}

	public String getGetterName() {
		return getGetterPrefix()+upperFirstChar(getName());
	}

	public String getSetterName() {
		return "set"+upperFirstChar(getName());
	}
	/**
	 * @return
	 */
	public String getGetterPrefix() {
		// TODO Auto-generated method stub
		return "boolean".equals(getClassname())?"is":"get";
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.config.JsfBean#getShortClassName()
	 */
	public String getSimpleClassName() {
		if (null != getClassname()) {
			return super.getSimpleClassName();
		}
		return Void.TYPE.getName();
	}	
	
	/**
	 * @param defaultValue The defaultValue to set.
	 */
	public void setDefaultvalue(String defaultValue) {
		this._defaultvalue = defaultValue;
	}

	public String getDefaultvalue() {
		if(null != _defaultvalue){
			return _defaultvalue;
		}
		if (defaults.containsKey(getClassname())) {
			return (String) defaults.get(getClassname());
		}
		return "null";
		
	}
	
	public String getReturntype() {
		if (null != _returntype) {
			return _returntype;
		}
		return Void.TYPE.getName();
	}
	
	public void setReturntype(String _returntype) {
		this._returntype = _returntype;
	}
	
	public boolean isSimpleType() {
		return defaults.containsKey(getClassname());
	}
	
	public String getBoxingClass(){
		if(isSimpleType()){
			String name = upperFirstChar(getClassname());
			if("Int".equals(name)){
				name="Integer";
			} else if("Char".equals(name)){
				name="Character";
			}
			return name;
		}
		return null;
	}
	
}
