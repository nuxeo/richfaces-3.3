/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
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

package org.richfaces.component;

import java.util.Comparator;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockValueExpression;
import org.richfaces.model.Ordering;

/**
 * Created 01.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public class AbstractColumnComponentTest extends AbstractAjax4JsfTestCase {

	private UIColumn column;
	/**
	 * @param name
	 */
	public AbstractColumnComponentTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		
		column = (UIColumn) application.createComponent(UIColumn.COMPONENT_TYPE);
	}
	
	public void tearDown() throws Exception {
		column = null;
		
		super.tearDown();
	}

	private Comparator<Object> comparator0 = new Comparator<Object>() {

		public int compare(Object o1, Object o2) {
			throw new UnsupportedOperationException();
		}
		
	};
	
	private Comparator<Object> comparator1 = new Comparator<Object>() {

		public int compare(Object o1, Object o2) {
			throw new UnsupportedOperationException();
		}
		
	};

	protected Comparator<Object> createTestData_0_comparator() {
		return comparator0;
	}

	protected Comparator<Object> createTestData_1_comparator() {
		return comparator1;
	}
	
	public void testSettersAndGetters()  throws Exception {
		column.setValueExpression("sortOrder", new MockValueExpression(Ordering.ASCENDING));
		assertEquals(Ordering.ASCENDING, column.getSortOrder());
		
		// reset value expression
		column.setValueExpression("sortOrder", null);
		
		column.setSortOrder(Ordering.DESCENDING);
		assertEquals(Ordering.DESCENDING, column.getSortOrder());

		column.setValueExpression("filterValue", new MockValueExpression(null));
		assertNull(column.getFilterValue());

		// reset value expression
		column.setValueExpression("filterValue", null);
		
		column.setFilterValue("filterValue");
		assertEquals("filterValue", column.getFilterValue());
	}
	
	public void testToggleSortOrder() {
		column.setSortOrder(null);
		column.toggleSortOrder();
		assertEquals(Ordering.ASCENDING, column.getSortOrder());
		
		column.toggleSortOrder();
		assertEquals(Ordering.DESCENDING, column.getSortOrder());
	}
}
