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

import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIOutput;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIScrollableDataTable;

/**
 * @author Maksim Kaszynski
 *
 */
public class ScrollableDataTableOptionsTest extends AbstractAjax4JsfTestCase {

	
	private ScrollableDataTableOptions options;
	private UIScrollableDataTable table;
	private ScrollableDataTableRendererState state;
	
	
	public ScrollableDataTableOptionsTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		table = (UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		table.setId("table");
		state = ScrollableDataTableRendererState.createState(facesContext, table);
		
		
		
		facesContext.getViewRoot().getChildren().add(table);

		UIOutput output = new UIOutput();
		output.setId("splash");
		table.getFacets().put("splash", output);
		
		for(int i = 0; i < 10; i++) {
			table.getChildren().add(new UIColumn());
		}

		options = new ScrollableDataTableOptions(table);

		
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		ScrollableDataTableRendererState.restoreState(facesContext);
		
		super.tearDown();
		options = null;
		state = null;
		table = null;
		
	}

	
	
	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableOptions#ScrollableDataTableOptions(org.richfaces.component.UIScrollableDataTable)}.
	 */
	public final void testScrollableDataTableOptions() {
		
		Map map = options.getMap();
		assertNotNull(map);
		assertEquals(new Integer(10), map.get("columnsCount"));
		assertEquals(new Integer(table.getRows()), map.get("rowsCount"));
		assertEquals(table.getBaseClientId(facesContext), map.get("client_id"));
		assertEquals("table:splash", map.get("splash_id"));
		assertTrue(map.containsKey("onSortAjaxUpdate"));
		
		
		//options.getMap().containsKey(key);
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableOptions#onSortAjaxUpdate(javax.faces.context.FacesContext, org.richfaces.component.UIScrollableDataTable)}.
	 */
	public final void testOnSortAjaxUpdate() {
		String ajaxUpdate = options.onSortAjaxUpdate(facesContext, table);
		assertNotNull(ajaxUpdate);
		assertTrue(ajaxUpdate.contains("table:sortColumn"));
		assertTrue(ajaxUpdate.contains("table:sortOrder"));
		assertTrue(ajaxUpdate.contains("table:sortIndex"));
		assertTrue(ajaxUpdate.endsWith("return false;"));
		
	}

}
