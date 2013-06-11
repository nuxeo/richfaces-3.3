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

/*
 * ColumnsFaceletTagTest.java		Date created: 14.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */
package org.richfaces.facelet.tag;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColumn;
import org.richfaces.component.html.HtmlColumn;

import com.sun.facelets.Facelet;
import com.sun.facelets.FaceletFactory;
import com.sun.facelets.compiler.Compiler;
import com.sun.facelets.compiler.SAXCompiler;
import com.sun.facelets.impl.DefaultFaceletFactory;
import com.sun.facelets.impl.ResourceResolver;

/**
 * Unit test for simple Component.
 */
/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov
 *
 */
public class ColumnsFaceletTagTest extends AbstractAjax4JsfTestCase implements ResourceResolver {

	public ColumnsFaceletTagTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		Compiler c = new SAXCompiler();
		//c.setTrimmingWhitespace(true);
		FaceletFactory factory = new DefaultFaceletFactory(c, this);
		FaceletFactory.setInstance(factory);

		facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, "/test"));

		ResponseWriter rw = facesContext.getRenderKit().createResponseWriter(new StringWriter(), null, null);
		facesContext.setResponseWriter(rw);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		this.servletContext = null;
	}



	public void testFacelet() throws Exception {
		Map session = facesContext.getExternalContext().getSessionMap();
		Collection c = new ArrayList();
		for (int i = 0; i < 10; i++) {
			c.add(i);
		}
		session.put("list", c);


		FaceletFactory f = FaceletFactory.getInstance();
		Facelet at = f.getFacelet("/");

		UIViewRoot root = facesContext.getViewRoot();
		at.apply(facesContext, root);

		int count = root.getChildCount();
		assertTrue(count == 1);

		UIComponent dataTable = root.getChildren().get(0);
		assertNotNull(dataTable);

		count = dataTable.getChildCount();
		assertTrue(count == 5);

		UIComponent component = dataTable.getChildren().get(0);
		assertTrue(component instanceof UIColumn);

		HtmlColumn column = (HtmlColumn) component;
		assertTrue("color: Red;".equals(column.getStyle()));
		assertTrue("100px;".equals(column.getWidth()));

		column = (HtmlColumn)dataTable.getChildren().get(4);
		UIComponent facet = column.getFacet("header");
		assertNotNull(facet);
		assertTrue(facet instanceof UIOutput);
		UIOutput output = (UIOutput)facet;
		
		assertTrue(component.getChildCount() == 1 );
	
	}


	public URL resolveUrl(String string) {
		URL url = getClass().getResource("columns.xml");
		return url;
	}

}
