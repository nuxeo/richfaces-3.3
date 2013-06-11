/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.component.html.MockColumns;

/**
 * @author Maksim Kaszynski
 *
 */
public class ColumnWalkerTest extends AbstractAjax4JsfTestCase {

	private	UIScrollableDataTable table;
	private ScrollableDataTableRendererState state;
	/**
	 * @param name
	 */
	public ColumnWalkerTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		table = 
			(UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		state = ScrollableDataTableRendererState.createState(facesContext, table);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		ScrollableDataTableRendererState.restoreState(facesContext);
		state = null;
		table = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ColumnWalker#iterateOverColumns(javax.faces.context.FacesContext, javax.faces.component.UIComponent, org.richfaces.renderkit.html.ColumnVisitor, javax.faces.context.ResponseWriter, org.richfaces.renderkit.html.ScrollableDataTableRendererState)}.
	 */
	public final void testIterateOverColumns() {
		
		for(int i = 0; i < 10; i++ ) {
			table.getChildren().add(MockColumns.newColumn("col" + i));
		}
		
		ColumnVisitor visitor = new ColumnVisitor() {
			
			public int visit(FacesContext context, UIComponent column,
					ResponseWriter writer,
					ScrollableDataTableRendererState state) throws IOException {
				
				assertEquals("col" + state.getCellIndex(), column.getId());
				
				return 0;
			}
		};
		
		
		try {
			ColumnWalker.iterateOverColumns(facesContext, table, visitor, writer, state);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
	}

}
