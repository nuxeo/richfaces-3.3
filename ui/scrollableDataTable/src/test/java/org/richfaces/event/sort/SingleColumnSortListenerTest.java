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

package org.richfaces.event.sort;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.component.html.MockColumns;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;

/**
 * @author Maksim Kaszynski
 *
 */
public class SingleColumnSortListenerTest extends AbstractAjax4JsfTestCase {

	
	private SortListener listener;
	private UIScrollableDataTable table;
	
	public SingleColumnSortListenerTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		listener = SingleColumnSortListener.INSTANCE;
		
		table = (UIScrollableDataTable) 
			application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		
		facesContext.getViewRoot().getChildren().add(table);
		
			
		table.getChildren().add(MockColumns.newColumn("col1"));
		table.getChildren().add(MockColumns.newColumn("col2"));
		
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		table = null;
		listener = null;
	}

	/**
	 * Test method for {@link org.richfaces.event.sort.SingleColumnSortListener#processSort(org.richfaces.event.sort.SortEvent)}.
	 */
	public final void testProcessSort() {
		SortEvent event = new SortEvent(table, "col1", 0, 0);
		
		listener.processSort(event);
		
		SortOrder sortOrder = table.getSortOrder();
		
		assertNotNull(sortOrder);
		
		SortField[] fields = sortOrder.getFields();
		
		assertNotNull(fields);
		assertEquals(1, fields.length);
		
		SortField field = fields[0];
		
		assertEquals("col1", field.getName());
		assertEquals(Boolean.TRUE, field.getAscending());
		
		listener.processSort(event);
		
		sortOrder = table.getSortOrder();
		
		assertNotNull(sortOrder);
		
		fields = sortOrder.getFields();
		
		assertNotNull(fields);
		assertEquals(1, fields.length);
		
		field = fields[0];
		
		assertEquals("col1", field.getName());
		assertEquals(Boolean.FALSE, field.getAscending());
		
		event = new SortEvent(table, "col2", 0, 0);
		
		listener.processSort(event);
		
		sortOrder = table.getSortOrder();
		
		assertNotNull(sortOrder);
		
		fields = sortOrder.getFields();
		
		assertNotNull(fields);
		assertEquals(1, fields.length);
		
		field = fields[0];
		
		assertEquals("col2", field.getName());
		assertEquals(Boolean.TRUE, field.getAscending());
		
	}

}
