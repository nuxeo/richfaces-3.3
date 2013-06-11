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

package org.ajax4jsf.builder.mojo;


/**
 * @author shura
 *
 */
public class Taglib {
	
	private String _taglib;
	
	private String _uri;
	
	private String _shortName;
	
	private String _listenerClass;
	
	private String _displayName;

	private String _tlibVersion = null;
	
	private String _jspVersion ;
		
	private String _validatorClass = null;
	
	private String _includeModules = null;
	
	private String _excludeModules = null;
	
	private String _includeTags = null;
	
	private String _excludeTags = null;
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return this._displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this._displayName = displayName;
	}

	/**
	 * @return the listenerClass
	 */
	public String getListenerClass() {
		return this._listenerClass;
	}

	/**
	 * @param listenerClass the listenerClass to set
	 */
	public void setListenerClass(String listenerClass) {
		this._listenerClass = listenerClass;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return this._shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this._shortName = shortName;
	}

	/**
	 * @return the taglib
	 */
	public String getTaglib() {
		return this._taglib;
	}

	/**
	 * @param taglib the taglib to set
	 */
	public void setTaglib(String taglib) {
		this._taglib = taglib;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return this._uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this._uri = uri;
	}

	/**
	 * @return the jspVersion
	 */
	public String getJspVersion() {
		return this._jspVersion;
	}

	/**
	 * @param jspVersion the jspVersion to set
	 */
	public void setJspVersion(String jspVersion) {
		this._jspVersion = jspVersion;
	}

	/**
	 * @return the tlibVersion
	 */
	public String getTlibVersion() {
		return this._tlibVersion;
	}

	/**
	 * @param tlibVersion the tlibVersion to set
	 */
	public void setTlibVersion(String tlibVersion) {
		this._tlibVersion = tlibVersion;
	}

	/**
	 * @return the validatorClass
	 */
	public String getValidatorClass() {
		return this._validatorClass;
	}

	/**
	 * @param validatorClass the validatorClass to set
	 */
	public void setValidatorClass(String validatorClass) {
		this._validatorClass = validatorClass;
	}

	/**
	 * @return the includeModules
	 */
	public String getIncludeModules() {
		return _includeModules;
	}

	/**
	 * @param includeModules the includeModules to set
	 */
	public void setIncludeModules(String includeModules) {
		_includeModules = includeModules;
	}

	/**
	 * @return the excludeModules
	 */
	public String getExcludeModules() {
		return _excludeModules;
	}

	/**
	 * @param excludeModules the excludeModules to set
	 */
	public void setExcludeModules(String excludeModules) {
		_excludeModules = excludeModules;
	}

	/**
	 * @return the includeTags
	 */
	public String getIncludeTags() {
		return _includeTags;
	}

	/**
	 * @param includeTags the includeTags to set
	 */
	public void setIncludeTags(String includeTags) {
		_includeTags = includeTags;
	}

	/**
	 * @return the excludeTags
	 */
	public String getExcludeTags() {
		return _excludeTags;
	}

	/**
	 * @param excludeTags the excludeTags to set
	 */
	public void setExcludeTags(String excludeTags) {
		_excludeTags = excludeTags;
	}

	@Override
	public String toString() {
		return "Lib: "+getShortName()+", URL: "+getUri();
	}
}
