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
package org.richfaces.taglib;

import java.io.IOException;
import java.util.Enumeration;

import javax.faces.webapp.UIComponentTag;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.Tag;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlEditor;

/**
 * @author asmirnov
 *
 */
public class EditorTagTest extends AbstractAjax4JsfTestCase {

	EditorTag editorTag;

	
	/**
	 * @param name
	 */
	public EditorTagTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		editorTag = new EditorTag();
		editorTag.setParent(new UIComponentTag(){

			public String getComponentType() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getRendererType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int doStartTag() throws JspException {
				// TODO Auto-generated method stub
				return Tag.EVAL_BODY_INCLUDE;
			}
			
			public int doEndTag() throws JspException {
				// TODO Auto-generated method stub
				return Tag.EVAL_BODY_INCLUDE;
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		editorTag = null;
	}

	/**
	 * Test method for {@link org.richfaces.taglib.PanelTag#setProperties(javax.faces.component.UIComponent)}.
	 * @throws JspException 
	 */
	public void testSetPropertiesUIComponent() throws JspException {
		HtmlEditor editor = new HtmlEditor();
		// other test here
	}

}
