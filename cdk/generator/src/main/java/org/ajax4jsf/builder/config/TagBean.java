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

/**
 * Java Bean for hold JSP tag configuration
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/20 20:57:59 $
 *
 */
public class TagBean extends JsfBean {

	/**
	 * Taglib short name for this tag -for generate different taglibs in task. 
	 */
	private String _taglib = null;
	
	private String _bodyContent = "JSP";
	
	private boolean _generate = true;
	/**
	 * Setup default tag superclass
	 */
	public TagBean() {
		super();
		setSuperclass("org.ajax4jsf.webapp.taglib.UIComponentTagBase");
	}

	/**
	 * @return Returns the taglib.
	 */
	public String getTaglib() {
		return _taglib;
	}

	/**
	 * @param taglib The taglib to set.
	 */
	public void setTaglib(String taglib) {
		_taglib = taglib;
	}

	public String getBodyContent() {
		return _bodyContent;
	}

	public void setBodyContent(String bodyContent) {
		this._bodyContent = bodyContent;
	}

	/**
	 * @return Returns the generate.
	 */
	public boolean isGenerate() {
		return this._generate;
	}

	/**
	 * @param generate The generate to set.
	 */
	public void setGenerate(boolean generate) {
		this._generate = generate;
	}
	
	@Override
	public void setTest(TestClassHolder test) {
		super.setTest(test);
		test.setClassname(getClassname() + "TagTest");
	}
	
	@Override
	public TestClassHolder getTest() {
		return super.getTest();
	}
}
