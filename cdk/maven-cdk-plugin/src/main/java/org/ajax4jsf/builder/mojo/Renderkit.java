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
public class Renderkit {
	
	private String _name = "HTML_BASIC";
	
	private String _markup = "html";
	
	private String _classname;
	
	private String _package;
	
	private StringBuffer _content;

	/**
	 * @return the classname
	 */
	public String getClassname() {
		return this._classname;
	}

	/**
	 * @param classname the classname to set
	 */
	public void setClassname(String classname) {
		this._classname = classname;
	}

	/**
	 * @return the markup
	 */
	public String getMarkup() {
		return this._markup;
	}

	/**
	 * @param markup the markup to set
	 */
	public void setMarkup(String markup) {
		this._markup = markup;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this._name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * @return the package
	 */
	public String getPackage() {
		return this._package;
	}

	/**
	 * @param package1 the package to set
	 */
	public void setPackage(String package1) {
		this._package = package1;
	}

	/**
	 * @return the content
	 */
	StringBuffer getContent() {
		return this._content;
	}

	/**
	 * @param content the content to set
	 */
	void setContent(StringBuffer content) {
		this._content = content;
	}
	
	public String getFacesConfig(){
		if(null != _content){
			return _content.toString();
		}
		return "";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return "render-kit id ["+_name+"], class ["+_classname+"], content :"+_content;
	}

}
