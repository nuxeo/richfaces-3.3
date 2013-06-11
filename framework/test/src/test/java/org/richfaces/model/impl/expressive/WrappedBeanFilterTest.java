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
package org.richfaces.model.impl.expressive;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.tests.MockValueExpression;
import org.richfaces.model.FilterField;

import junit.framework.TestCase;

/**
 * @author Konstantin Mishin
 *
 */
public class WrappedBeanFilterTest extends TestCase {
	
	private List<FilterField> filterFields;
	private  WrappedBeanFilter filter;
	/**
	 * @param name
	 */
	public WrappedBeanFilterTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		filterFields = new LinkedList<FilterField>();
		filterFields.add(new FilterField(new MockValueExpression("keyTrue")));
		filter = new WrappedBeanFilter(filterFields);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		filter = null;
		filterFields = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.WrappedBeanFilter#WrappedBeanFilter(java.util.List)}.
	 */
	public final void testWrappedBeanFilter() {
		WrappedBeanFilter beanFilter = new WrappedBeanFilter(filterFields);
		assertNotNull(beanFilter);
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.WrappedBeanFilter#accept(org.richfaces.model.impl.expressive.JavaBeanWrapper)}.
	 */
	public final void testAccept() {
		TestObj obj = new TestObj("TestObj");
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("keyTrue", Boolean.TRUE);
		props.put("keyFalse", Boolean.FALSE);
		JavaBeanWrapper wrapper = new JavaBeanWrapper(obj, props);
		assertTrue(filter.accept(wrapper));
		filterFields.add(new FilterField(new MockValueExpression("keyFalse")));
		assertFalse(filter.accept(wrapper));
	}

}
