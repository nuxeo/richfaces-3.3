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

package org.richfaces.model.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;


/**
 * @author Maksim Kaszynski
 *
 */
public class ComponentSortableDataModelTest extends AbstractAjax4JsfTestCase {

	private ComponentSortableDataModel model;
	private UIScrollableDataTable table;
	private List l;
	private Object [] a;
	private Object o;
	private SortOrder sortOrder;
	/**
	 * @param name
	 */
	public ComponentSortableDataModelTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		table = (UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		table.setVar("item");
		model = new ComponentSortableDataModel("item", null, null);

		facesContext.getViewRoot().getChildren().add(table);
		
		l = createList();
		a = createArray();
		o = o(20);
		
		
		sortOrder = new SortOrder(new SortField[] {new SortField("name", Boolean.TRUE)});
		
		//model = new ComponentSortableDataModel()
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		model = null;
		table = null;
		
		l = null; 
		a = null;
		o = null;
	}

	List createList() {
		List l = new ArrayList(10);
		for (int i = 0; i < 10; i++) {
			l.add(o(i));
		}
		return l;
	}

	Object [] createArray() {
		Object [] a = new Object[100];
		for(int i = 0; i < a.length; i++) {
			a[i] = o(i);
		}
		
		return a;
	}
	
	TestObj o(int i) {
		return new TestObj(Integer.toString(i));
	}
	
	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#getRowCount()}.
	 */
	public final void testGetRowCount() {
		
		model.setWrappedData(l);
		assertEquals(l.size(), model.getRowCount());
		
		model.setWrappedData(a);
		assertEquals(a.length, model.getRowCount());
		
		model.setWrappedData(o);
		assertEquals(1, model.getRowCount());
		
		model.setWrappedData(null);
		assertEquals(0, model.getRowCount());
		
	}

	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#loadData(int, int, org.richfaces.model.SortOrder)}.
	 */
	public final void testLoadData() {
		model.setWrappedData(l);
		List loaded = model.loadData(0, 5, null);
		assertEquals(5, loaded.size());
		loaded = model.loadData(0, 10, null);
		assertEquals(10, loaded.size());
		loaded = model.loadData(0, 30, null);
		assertEquals(10, loaded.size());
		loaded = model.loadData(5, 30, null);
		assertEquals(5, loaded.size());
		
		model.setWrappedData(l);
		loaded = model.loadData(0, 5, sortOrder);
		assertEquals(5, loaded.size());
		loaded = model.loadData(0, 10, sortOrder);
		assertEquals(10, loaded.size());
		loaded = model.loadData(0, 30, sortOrder);
		assertEquals(10, loaded.size());
		loaded = model.loadData(5, 30, sortOrder);
		assertEquals(5, loaded.size());
		
	}

	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#prepareCollection(javax.faces.context.FacesContext, java.util.List, org.richfaces.model.SortOrder)}.
	 */
	public final void testPrepareCollection() {
		model.setWrappedData(l);
		
		int size = l.size();
		
		List prepared = model.prepareCollection(facesContext, l, sortOrder);
		assertSame(l, prepared);
		assertEquals(size, prepared.size());
		
		Iterator iter = prepared.iterator();
		
		//must be 10 elements in collection
		assertTrue(iter.hasNext());
		
		TestObj prev = (TestObj) iter.next();
		
		while(iter.hasNext()) {
			
			TestObj next = (TestObj) iter.next();
			
			assertTrue(next.getName().compareTo(prev.getName()) > 0);
			
			
			prev = next;
			
		}
		
		
	}

	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#ComponentSortableDataModel(javax.faces.component.UIData, java.lang.Object)}.
	 */
	public final void testComponentSortableDataModel() {
		model = new ComponentSortableDataModel("item", l, null);
		assertEquals(l.size(), model.getRowCount());
	}

	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#getWrappedData()}.
	 */
	public final void testGetWrappedData() {
		model.setWrappedData(l);
		assertEquals(l.size(), ((List) model.getWrappedData()).size());
		
		model.setWrappedData(a);
		assertEquals(a.length, ((List) model.getWrappedData()).size());
		
		model.setWrappedData(o);
		assertEquals(1, ((List) model.getWrappedData()).size());
		
		model.setWrappedData(null);
		assertEquals(0, ((List) model.getWrappedData()).size());
		
	}

	/**
	 * Test method for {@link org.richfaces.model.internal.ComponentSortableDataModel#setWrappedData(java.lang.Object)}.
	 */
	public final void testSetWrappedDataObject() {
		model.setWrappedData(l);
		assertEquals(l.size(), ((List) model.getWrappedData()).size());
		
		model.setWrappedData(a);
		assertEquals(a.length, ((List) model.getWrappedData()).size());
		
		model.setWrappedData(o);
		assertEquals(1, ((List) model.getWrappedData()).size());
		
		model.setWrappedData(null);
		assertEquals(0, ((List) model.getWrappedData()).size());
	}

}
