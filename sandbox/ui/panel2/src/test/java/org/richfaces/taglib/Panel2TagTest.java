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

import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.ajax4jsf.tests.AbstractJspTestCase;
import org.apache.shale.test.el.MockValueExpression;
import org.richfaces.component.html.HtmlPanel2;

/**
 * @author asmirnov
 *
 */
public class Panel2TagTest extends AbstractJspTestCase {

	Panel2Tag panelTag;

	
	/**
	 * @param name
	 */
	public Panel2TagTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		panelTag = new Panel2Tag();
		panelTag.setPageContext(pageContext);
		panelTag.setParent(new UIComponentTag(){

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
		panelTag = null;
	}

	/**
	 * Test method for {@link org.richfaces.taglib.PanelTag#setProperties(javax.faces.component.UIComponent)}.
	 * @throws JspException 
	 */
	public void testSetPropertiesUIComponent() throws JspException {
		HtmlPanel2 panel = new HtmlPanel2();
		panelTag.setBodyClass(new MockValueExpression("panel", String.class));
		panelTag.setHeaderClass(new MockValueExpression("headClass", String.class));
		panelTag.setProperties(panel);
		assertEquals("panel", panel.getBodyClass());
		assertEquals("headClass", panel.getHeaderClass());
	}

}
