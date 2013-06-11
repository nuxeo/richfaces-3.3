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

import java.util.ArrayList;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.shale.test.mock.MockExternalContext;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.model.selection.ClientSelection;
import org.richfaces.model.selection.SelectionRange;
import org.richfaces.renderkit.CompositeRenderer;

/**
 * @author Maksim Kaszynski
 *
 */
public class SelectionRendererContributorTest extends AbstractAjax4JsfTestCase {

	private UIScrollableDataTable component;
	private SelectionRendererContributor contributor;
	private CompositeRenderer renderer;

	public SelectionRendererContributorTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		component = (UIScrollableDataTable) application.createComponent(UIScrollableDataTable.COMPONENT_TYPE);
		component.setValue(new ArrayList());
		facesContext.getViewRoot().getChildren().add(component);
		contributor = new SelectionRendererContributor();
		renderer = new CompositeRenderer(){
			protected Class<? extends UIScrollableDataTable> getComponentClass() {
			return UIScrollableDataTable.class;
		}};
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		this.component = null;
		this.contributor = null;
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.SelectionRendererContributor#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent, org.richfaces.renderkit.CompositeRenderer)}.
	 */
	public void testDecode() {
		MockExternalContext mockExternalContext = 
			(MockExternalContext) facesContext.getExternalContext();
		String name = 
			SelectionRendererContributor.getSelectionInputName(facesContext, (UIScrollableDataTable) component);
		
		String value = "";
		
		mockExternalContext.addRequestParameterMap(name, value);
		
		contributor.decode(facesContext, component, renderer);
		
		Object selection = component.getSelection();
		
	}
	
	public void testShouldAddToSelection() {
		
		ClientSelection oldSelection = new ClientSelection();
		oldSelection.addRange(new SelectionRange(10, 15));
		oldSelection.addRange(new SelectionRange(16, 20));
		
		ClientSelection newSelection = new ClientSelection();
		newSelection.addRange(new SelectionRange(20, 40));
		
		newSelection.setSelectionFlag(ClientSelection.FLAG_ALL);
		
		for(int i = 0; i < 100; i++) {
			assertTrue(
					"Contributor was supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		
		newSelection.setSelectionFlag(null);
		
		for (int i = 0; i < 21; i++) {
			assertFalse(
					"Contributor wasn't supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		for (int i = 21; i < 41; i++) {
			assertTrue(
					"Contributor was supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		for (int i = 41; i < 100; i++) {
			assertFalse(
					"Contributor wasn't supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		
		newSelection.setSelectionFlag(ClientSelection.FLAG_RESET);
		
		
		for (int i = 0; i < 20; i++) {
			assertFalse(
					"Contributor wasn't supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		for (int i = 21; i < 41; i++) {
			assertTrue(
					"Contributor was supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		for (int i = 41; i < 100; i++) {
			assertFalse(
					"Contributor wasn't supposed to add " + i,
					contributor.shouldAddToSelection(i, oldSelection, newSelection)
			);
		}
		
		
		
	}
	
	
	public void testShouldRemoveFromSelection() {
		ClientSelection oldSelection = new ClientSelection();
		oldSelection.addRange(new SelectionRange(10, 15));
		oldSelection.addRange(new SelectionRange(16, 20));
		
		ClientSelection newSelection = new ClientSelection();
		newSelection.addRange(new SelectionRange(20, 40));
		
		for (int i = 0; i < 10; i++) {
			assertFalse(
					"Contributor wasn't supposed to remove " + i,
					contributor.shouldRemoveFromSelection(i, oldSelection, newSelection)
			);
		}

		for (int i = 10; i < 20; i++) {
			assertTrue(
					"Contributor was supposed to remove " + i,
					contributor.shouldRemoveFromSelection(i, oldSelection, newSelection)
			);
		}
		
		for (int i = 21; i < 100; i++) {
			assertFalse(
					"Contributor wasn't supposed to remove " + i,
					contributor.shouldRemoveFromSelection(i, oldSelection, newSelection)
			);
		}

		
		newSelection.setSelectionFlag(ClientSelection.FLAG_ALL);
		
		for (int i = 0; i < 100; i++) {
			assertFalse(
					"Contributor wasn't supposed to remove " + i,
					contributor.shouldRemoveFromSelection(i, oldSelection, newSelection)
			);
		}
		
		newSelection.setSelectionFlag(ClientSelection.FLAG_RESET);

		for (int i = 0; i < 100; i++) {
			assertFalse(
					"Contributor wasn't supposed to remove " + i,
					contributor.shouldRemoveFromSelection(i, oldSelection, newSelection)
			);
		}
	}

}
