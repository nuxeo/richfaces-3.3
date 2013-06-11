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
import java.util.LinkedList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.SequenceRange;
import org.ajax4jsf.tests.MockDataModel;
import org.ajax4jsf.tests.MockDataModelListener;
import org.ajax4jsf.tests.MockValueExpression;
import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author Konstantin Mishin
 *
 */
public class ModifiableModelTest extends AbstractJsfTestCase {

	private ModifiableModel model;
	private ExtendedDataModel originalModel;
	private String var;
	private List<FilterField> filterFields;
	private List<SortField2> sortFields;

	/**
	 * @param name
	 */
	public ModifiableModelTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		var = "var";
		originalModel = new MockDataModel();
		filterFields = new LinkedList<FilterField>();
		sortFields = new LinkedList<SortField2>();
		model = new ModifiableModel(originalModel, var);
		model.modify(filterFields, sortFields);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		model = null;
		var = null;
		filterFields = null;
		sortFields = null;
		originalModel = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#isRowAvailable()}.
	 */
	public final void testIsRowAvailable() {
		assertEquals(model.isRowAvailable(), originalModel.isRowAvailable());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getRowCount()}.
	 */
	public final void testGetRowCount() {
		assertEquals(model.getRowCount(), originalModel.getRowCount());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getRowIndex()}.
	 */
	public final void testGetRowIndex() {
		assertEquals(model.getRowIndex(), originalModel.getRowIndex());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#setRowIndex(int)}.
	 */
	public final void testSetRowIndex() {
		model.setRowIndex(5);
		assertEquals(model.getRowIndex(), originalModel.getRowIndex());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#setRowKey(java.lang.Object)}.
	 */
	public final void testSetRowKey() {
		model.setRowKey(new Integer(5));
		assertEquals(model.getRowKey(), originalModel.getRowKey());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getRowKey()}.
	 */
	public final void testGetRowKey() {
		assertEquals(model.getRowKey(), originalModel.getRowKey());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getSerializableModel(org.ajax4jsf.model.Range)}.
	 */
	public final void testGetSerializableModel() {
		assertEquals(model.getSerializableModel(null), originalModel.getSerializableModel(null));
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#walk(javax.faces.context.FacesContext, org.ajax4jsf.model.DataVisitor, org.ajax4jsf.model.Range, java.lang.Object)}.
	 */
	public final void testWalk() {
		SequenceRange range = new SequenceRange(0, -1);
		DataVisitor visitor = new DataVisitor(){
			public void process(FacesContext context, Object rowKey,
					Object argument) throws IOException {
				Object key = model.getRowKey();
				model.setRowKey(rowKey);
				Object key2 = originalModel.getRowKey();
				originalModel.setRowKey(rowKey);
				assertEquals(model.getRowData(), originalModel.getRowData());
				model.setRowKey(key);
				originalModel.setRowKey(key2);
			}	
		};
		try {
			model.walk(null, visitor, range, null);
			ValueExpression expression = new MockValueExpression(Boolean.TRUE);
			filterFields.add(new FilterField(expression));
			sortFields.add(new SortField2(expression, Ordering.ASCENDING));
			model.walk(null, visitor, range, null);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#ModifiableModel(org.ajax4jsf.model.ExtendedDataModel, java.lang.String, java.util.List, java.util.List)}.
	 */
	public final void testModifiableModel() {
		ModifiableModel modifiableModel = new ModifiableModel(originalModel, var);;
		assertNotNull(modifiableModel);
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#addDataModelListener(javax.faces.model.DataModelListener)}.
	 */
	public final void testAddDataModelListenerDataModelListener() {
		MockDataModelListener listener = new MockDataModelListener();
		model.addDataModelListener(listener);
		assertEquals(model.getDataModelListeners()[0], listener);		
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getDataModelListeners()}.
	 */
	public final void testGetDataModelListeners() {
		assertEquals(model.getDataModelListeners().length, 0);		
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getRowData()}.
	 */
	public final void testGetRowData() {
		assertEquals(model.getRowData(), originalModel.getRowData());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#getWrappedData()}.
	 */
	public final void testGetWrappedData() {
		assertEquals(model.getWrappedData(), originalModel.getWrappedData());
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#removeDataModelListener(javax.faces.model.DataModelListener)}.
	 */
	public final void testRemoveDataModelListenerDataModelListener() {
		MockDataModelListener listener = new MockDataModelListener();
		model.addDataModelListener(listener);
		assertEquals(model.getDataModelListeners()[0], listener);		
		model.removeDataModelListener(listener);
		assertEquals(model.getDataModelListeners().length, 0);		
	}

	/**
	 * Test method for {@link org.richfaces.model.ModifiableModel#setWrappedData(java.lang.Object)}.
	 */
	public final void testSetWrappedDataObject() {
		model.setWrappedData(var);
		assertEquals(originalModel.getWrappedData(), var);		
	}

}
