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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.MockColumns;
import org.richfaces.event.ScrollableGridViewEvent;
import org.richfaces.event.scroll.ScrollEvent;
import org.richfaces.event.sort.MultiColumnSortListener;
import org.richfaces.event.sort.SingleColumnSortListener;
import org.richfaces.event.sort.SortEvent;
import org.richfaces.event.sort.SortListener;
import org.richfaces.model.DataModelCache;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
import org.richfaces.model.impl.ListDataModel;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;

/**
 * @author Maksim Kaszynski
 *
 */
public class AbstractScrollableDataTableTestCase extends AbstractAjax4JsfTestCase {


	private UIScrollableDataTable table;
	/**
	 * @param name
	 */
	public AbstractScrollableDataTableTestCase(String name) {
		super(name);
		
	}

	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		table = (UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		facesContext.getViewRoot().getChildren().add(table);
		table.setId("zzz");
		
		for(int i = 0; i < 10; i++) {
			UIComponent column = MockColumns.newColumn("zzz" + i);
			UIOutput output = new UIOutput();
			output.setId("h" + i);
			column.getFacets().put("header", output);
			table.getChildren().add(column);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		table = null;
		super.tearDown();
	}
	
	protected SortOrder createTestData_0_sortOrder() {
		SortField[] fields = new SortField[] {
			new SortField("aaa", Boolean.TRUE),
			new SortField("bb", Boolean.FALSE)
		};
		return new SortOrder(fields);
	}

	protected Selection createTestData_0_selection() {
		SimpleSelection selection = new SimpleSelection();
		selection.addKey(Integer.valueOf(5));		
		return selection;
	}
	
	protected SortOrder createTestData_1_sortOrder() {
		SortField[] fields = new SortField[] {
			new SortField("column3", null),
			new SortField("column1", Boolean.TRUE),
			new SortField("column2", Boolean.FALSE)
		};
		return new SortOrder(fields);
	}

	protected Selection createTestData_1_selection() {
		SimpleSelection selection = new SimpleSelection();
		selection.addKey(Integer.valueOf(9));		
		selection.addKey(Integer.valueOf(8));		
		selection.addKey(Integer.valueOf(10));		
		return selection;
	}

	private class SortListener1 implements SortListener {
		private boolean triggered = false;
		public void processSort(SortEvent e) {
			assertNotNull(e);
			triggered = true;
		}
	}
	
	public void testBroadCast() {
		ScrollableGridViewEvent event = new ScrollEvent(table, 20, 30);
		event.setAttribute("attr", "value0");
		table.broadcast(event);
		assertEquals("value0", table.getAttributes().get("attr"));
		
		AjaxEvent ajaxEvent = new AjaxEvent(table);
		String id = AjaxRendererUtils.getAbsoluteId(table);
		table.broadcast(ajaxEvent);
		assertTrue(ajaxContext.getAjaxAreasToRender().contains(id));
		
		SortListener1 sortListener = new SortListener1();
		table.setSortListener(sortListener);
		table.broadcast(new SortEvent(table, "0", 20, 30));
		assertTrue(sortListener.triggered);
		
	}
	
	
	public void testFixedChildren() {
		Iterator iterator = table.fixedChildren();
		assertTrue(iterator.hasNext());
		int i = 0;
		while(iterator.hasNext()) {
			UIComponent kid = (UIComponent) iterator.next();
			assertEquals("h" + i, kid.getId());
			i++;
		}
	}
	
	public void testSortListener() {
		table.setSortMode(UIScrollableDataTable.SORT_MULTI);
		assertSame(MultiColumnSortListener.INSTANCE, table.getSortListener());
		
		table.setSortMode(UIScrollableDataTable.SORT_SINGLE);
		assertSame(SingleColumnSortListener.INSTANCE, table.getSortListener());
		
		SortListener1 sortListener1 = new SortListener1();
		
		table.setSortListener(sortListener1);
		
		assertSame(sortListener1, table.getSortListener());
		
	}
	
	public void testProcessSortingChange() {
		SortListener1 sortListener1 = new SortListener1();
		
		table.setSortListener(sortListener1);
		
		SortEvent sortEvent = new SortEvent(table, "0", 20, 30);
		
		table.processSortingChange(sortEvent);
		
		assertTrue(sortListener1.triggered);
		
		assertTrue(facesContext.getRenderResponse());
		
		assertEquals(30, table.getFirst());
		
	}
	
	public void testProcessScrolling() {
		ScrollableGridViewEvent event = new ScrollEvent(table, 20, 440);
		table.processScrolling(event);

		assertTrue(facesContext.getRenderResponse());
		assertEquals(440, table.getFirst());
		
	}
	
	public void testCreateDataModel() {
		
		
		
		
		ListDataModel l = new ListDataModel(Collections.singletonList("aaaa"));
		table.setValue(l);
		ExtendedDataModel model = table.createDataModel();
		
		assertTrue(model instanceof DataModelCache);
		
		assertEquals(1, model.getRowCount());
		//assertTrue(model instanceof ComponentSortableDataModel);
		
		//table.isCacheable()
		
		table.setValue(null);
		model = table.createDataModel();
		
		assertTrue(model instanceof ExtendedDataModel);
		
		assertEquals(0, model.getRowCount());
		
		
	}
	
	
	public void testGetResponseData() {
		List data = new ArrayList();
		table.setResponseData(data);
		assertSame(data, table.getResponseData());
	}
	
}
