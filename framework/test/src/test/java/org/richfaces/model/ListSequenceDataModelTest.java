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
package org.richfaces.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.SequenceRange;

import junit.framework.TestCase;

/**
 * @author Konstantin Mishin
 *
 */
public class ListSequenceDataModelTest extends TestCase {

	private List<Integer> list;
	private ListSequenceDataModel model;
	private ListSequenceDataModel nullModel;

	/**
	 * @param name
	 */
	public ListSequenceDataModelTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(new Integer(i));
		}
		model = new ListSequenceDataModel(list);
		nullModel = new ListSequenceDataModel(null);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		nullModel = null;
		model = null;
		list = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#isRowAvailable()}.
	 */
	public final void testIsRowAvailable() {
		assertFalse(nullModel.isRowAvailable());
		assertTrue(model.isRowAvailable());
		model.setRowIndex(-1);
		assertFalse(model.isRowAvailable());
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#getRowCount()}.
	 */
	public final void testGetRowCount() {
		assertEquals(model.getRowCount(), list.size());
		assertEquals(nullModel.getRowCount(), -1);
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#setRowIndex()}
	 * and {@link org.richfaces.model.ListSequenceDataModel#getRowIndex()}.
	 */
	public final void testRowIndex() {
		int i = 3;
		model.setRowIndex(i);
		assertEquals(model.getRowIndex(), i);
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#setRowKey(java.lang.Object)}
	 * and {@link org.richfaces.model.ListSequenceDataModel#getRowKey(java.lang.Object)}.
	 */
	public final void testRowKey() {
		Integer i = 3;
		model.setRowKey(i);
		assertEquals(model.getRowKey(), i);
		model.setRowKey(null);
		assertNull(model.getRowKey());
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#walk(javax.faces.context.FacesContext, org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range, java.lang.Object)}
	 * and {@link org.richfaces.model.ListSequenceDataModel#getRowData()}.
	 */
	public final void testWalk() {
		DataVisitor visitor = new DataVisitor(){
			public void process(FacesContext context, Object rowKey,
					Object argument) throws IOException {
				Object key = model.getRowKey();
				model.setRowKey(rowKey);
				assertEquals(model.getRowData(), list.get(((Integer)rowKey).intValue()));
				model.setRowKey(key);
			}	
		};
		try {
			SequenceRange range = new SequenceRange(0, -1);
			model.walk(null, visitor, range, null);
			range = new SequenceRange(0, 5);
			model.walk(null, visitor, range, null);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.richfaces.model.ListSequenceDataModel#ListSequenceDataModel(java.util.List)},
	 * {@link org.richfaces.model.ListSequenceDataModel#setWrappedData(java.lang.Object)}
	 * and {@link org.richfaces.model.ListSequenceDataModel#getWrappedData()} .
	 */
	public final void testListSequenceDataModel() {
		assertNull(nullModel.getWrappedData());
		ListSequenceDataModel dataModel = new ListSequenceDataModel(list);
		assertSame(dataModel.getWrappedData(), model.getWrappedData());
		
	}

}
