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
package org.richfaces.renderkit.html;

import javax.faces.context.ResponseWriter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIScrollableDataTable;

public class ScrollableDataTableRendererStateTest extends
		AbstractAjax4JsfTestCase {

	private UIScrollableDataTable component;
	private ScrollableDataTableRendererState state;

	public ScrollableDataTableRendererStateTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		component = (UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		state = new ScrollableDataTableRendererState(facesContext, null, component);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		state = null;
		component = null;
	}

	public void testGetRendererState() {
		facesContext.getExternalContext().getRequestMap().put(ScrollableDataTableRendererState.DATA_GRID_RENDERER_STATE,state);
		assertEquals(state, ScrollableDataTableRendererState.getRendererState(facesContext));
	}

	public void testCreateState() {
		assertNotNull(ScrollableDataTableRendererState.createState(facesContext, component));		
	}

	public void testRestoreState() {
		facesContext.getExternalContext().getRequestMap().put(ScrollableDataTableRendererState.DATA_GRID_RENDERER_STATE,state);
		ScrollableDataTableRendererState.restoreState(facesContext);
		assertNull(facesContext.getExternalContext().getRequestMap().get(ScrollableDataTableRendererState.DATA_GRID_RENDERER_STATE));
	}

	public void testGetCurrentCellId() {
		assertNotNull(state.getCurrentCellId(facesContext));
	}

	public void testCellIndex() {
		int i = 1;
		state.setCellIndex(i);
		assertEquals(i, state.getCellIndex());
	}

	public void testNextCell() {
		int i = state.getCellIndex() + 1;
		assertEquals(i, state.nextCell());
	}

	public void testColumns() {
		int i = 1;
		state.setColumns(i);
		assertEquals(i, state.getColumns());
	}

	public void testGrid() {
		state.setGrid(component);
		assertEquals(component, state.getGrid());
	}

	public void testPreviousState() {
		ScrollableDataTableRendererState rendererState = new ScrollableDataTableRendererState(facesContext, null, component);
		state.setPreviousState(rendererState);
		assertEquals(rendererState, state.getPreviousState());
	}

	public void testRowIndex() {
		int i = 1;
		state.setRowIndex(i);
		assertEquals(i, state.getRowIndex());
	}

	public void testNextRow() {
		int i = state.getRowIndex() + 1;
		assertEquals(i, state.nextRow());
	}

	public void testGetCachedClientId() {
		assertNotNull(state.getCachedClientId());
	}

	public void testGetBuffer() {
		assertNotNull(state.getBuffer());
	}

	public void testRowKey() {
		Object rowKey = new Object();
		state.setRowKey(rowKey);
		assertEquals(rowKey, state.getRowKey());
	}

	public void testFrozenColumnCount() {
		int i = 1;
		state.setFrozenColumnCount(i);
		assertTrue(state.isFrozenColumn());
		assertEquals(i, state.getFrozenColumnCount());
	}

	public void testFrozenPart() {
		boolean frozenPart = true;
		state.setFrozenPart(frozenPart);
		assertEquals(frozenPart, state.isFrozenPart());
	}

	public void testCellIdPrefix() {
		String prefix = "str";
		state.setCellIdPrefix(prefix);
		assertEquals(prefix, state.getCellIdPrefix());
	}

	public void testColumnType() {
		String prefix = "str";
		state.setColumType(prefix);
		assertEquals(prefix, state.getColumnType());
	}

	public void testAjaxContext() {
		state.setAjaxContext(ajaxContext);
		assertEquals(ajaxContext, state.getAjaxContext());
	}

	public void testWriter() {
		ResponseWriter writer = facesContext.getResponseWriter();
		state.setWriter(writer);
		assertEquals(writer, state.getWriter());
	}

	public void testClientId() {
		String id = "id";
		state.setClientId(id);
		assertEquals(id, state.getClientId());
	}

	public void testHeader() {
		boolean header = true;
		state.setHeader(header);
		assertEquals(header, state.isHeader());
	}

	public void testPart() {
		String part = "part";
		state.setPart(part);
		assertEquals(part, state.getPart());
	}

	public void testGetSumWidth() {
		int i = 1;
		state.setSumWidth(i);
		assertEquals(i, state.getSumWidth());
	}

	public void testSepOffset() {
		Integer i = new Integer(1);
		state.setSepOffset(i);
		assertEquals(i, state.getSepOffset());
	}

	public void testIsFake() {
		boolean fake = true;
		state.setFake(fake);
		assertEquals(fake, state.isFake());
	}

	public void testGetColumnHeaderClass() {
		assertEquals("", state.getColumnHeaderClass());
	}

	public void testColumnClass() {
		String columnClasses = "1,2,3";
		state.setColumnClasses(columnClasses);
		state.setCellIndex(1);
		assertEquals("2", state.getColumnClass());
	}

	public void testRowClass() {
		String rowClasses = "1,2,3";
		state.setRowClasses(rowClasses);
		state.setRowIndex(1);
		assertEquals("2", state.getRowClass());
	}

	public void testIds() {
		state.addId("1");
		assertEquals(1, state.getIds().size());		
	}
}
