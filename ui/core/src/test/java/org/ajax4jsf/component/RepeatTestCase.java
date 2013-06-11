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

package org.ajax4jsf.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.html.RepeatRenderer;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.tests.MockDataModel;
import org.ajax4jsf.tests.MockUIInputRenderer;

/**
 * @author shura
 *
 */
public class RepeatTestCase extends AbstractAjax4JsfTestCase {

	private UIRepeat repeater;
	
	private UIInput child;
	
	private int childInvoked;
	
	private UIInput facetChild;
	
	private int facetInvoked;

	private UIInput childChild;
	
	private int childChildInvoked;

	private UIInput childChildFacet;
	
	private int childChildFacetInvoked;

	private UIRepeat enclosedRepeater;
	/**
	 * @param name
	 */
	public RepeatTestCase(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		// Create mock DataAdaptor and childs.
		repeater = new UIRepeat();
		child = new UIInput(){
			public void processDecodes(FacesContext context) {
				childInvoked++;
				super.processDecodes(context);
			}
		};
		childInvoked = 0;
		child.setId("child");
		repeater.getChildren().add(child);
		facetChild = new UIInput(){
			public void processDecodes(FacesContext context) {
				facetInvoked++;
				super.processDecodes(context);
			}
		};
		facetInvoked = 0;
		facetChild.setId("facetChild");
		repeater.getFacets().put("facet", facetChild);
		childChild = new UIInput(){
			public void processDecodes(FacesContext context) {
				childChildInvoked++;
				super.processDecodes(context);
			}
		};;
		childChildInvoked = 0;
		childChild.setId("childChild");
		child.getChildren().add(childChild);
		childChildFacet = new UIInput(){
			public void processDecodes(FacesContext context) {
				childChildFacetInvoked++;
				super.processDecodes(context);
			}
		};;
		childChildFacetInvoked = 0;
		childChildFacet.setId("childChildFacet");
		childChild.getFacets().put("facet", childChildFacet);
		enclosedRepeater = new UIRepeat();
		renderKit.addRenderer(child.getFamily(), child.getRendererType(), new MockUIInputRenderer(){
			public void decode(FacesContext context, UIComponent component) {
				super.decode(context, component);
				UIInput input = (UIInput) component;
				String submittedValie = enclosedRepeater.getRowKey()+":"+repeater.getRowKey();
				input.setSubmittedValue(submittedValie);
				System.out.println("decode component "+component.getClientId(facesContext)+" with value "+submittedValie);
			}
		});
		renderKit.addRenderer(repeater.getFamily(), repeater.getRendererType(), new RepeatRenderer());
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		repeater = null;
		child = null;
		childChild = null;
		childChildFacet = null;
		facetChild = null;
		enclosedRepeater = null;
	}


	private void createDataTree(){
		enclosedRepeater.setId("data");
		repeater.setId("adaptor");
		repeater.setVar("row");
		ArrayList value = new ArrayList(2);
		value.add("first");
		value.add("second");
		enclosedRepeater.setValue(value);
		enclosedRepeater.setVar("var");
		UIColumn column = new UIColumn();
		enclosedRepeater.getChildren().add(column);
		column.getChildren().add(repeater);
		facesContext.getViewRoot().getChildren().add(enclosedRepeater);
	}
	
	private void printChildMap(Map childrenState){
		System.out.println("{");
		for (Iterator iter = childrenState.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			System.out.println(" "+key+" : "+childrenState.get(key));
		}
		System.out.println("}");
	}
	/**
	 * Test method for {@link javax.faces.component.UIData#processDecodes(javax.faces.context.FacesContext)}.
	 */
	public void testProcessDecodesFacesContext() {
		createDataTree();
		repeater.setValue(new MockDataModel());
//		enclosedRepeater.setValue(new MockDataModel());
		enclosedRepeater.processDecodes(facesContext);
		enclosedRepeater.setRowIndex(1);
		repeater.setRowIndex(1);
		System.out.println("Saved child state for external repeater ");
		printChildMap(enclosedRepeater.getChildState(facesContext));
		System.out.println("Saved child state" );
		printChildMap(repeater.getChildState(facesContext));
		assertEquals("1:1", child.getSubmittedValue());
	}

	/**
	 * Test method for {@link javax.faces.component.UIData#processUpdates(javax.faces.context.FacesContext)}.
	 */
	public void testProcessUpdatesFacesContext() {
		createDataTree();
		repeater.setValue(new MockDataModel());
//		enclosedRepeater.setValue(new MockDataModel());
		enclosedRepeater.processDecodes(facesContext);
		enclosedRepeater.processValidators(facesContext);
		enclosedRepeater.processUpdates(facesContext);
		enclosedRepeater.setRowIndex(1);
		repeater.setRowIndex(1);		
		assertEquals("1:1", child.getValue());
		enclosedRepeater.setRowIndex(0);
		repeater.setRowIndex(2);		
		assertEquals("0:2", child.getValue());
	}

	/**
	 * Test method for {@link javax.faces.component.UIData#processUpdates(javax.faces.context.FacesContext)}.
	 */
	public void testProcessValidatorsFacesContext() {
		createDataTree();
		repeater.setValue(new MockDataModel());
//		enclosedRepeater.setValue(new MockDataModel());
		enclosedRepeater.processDecodes(facesContext);
		enclosedRepeater.processValidators(facesContext);
		enclosedRepeater.setRowIndex(1);
		repeater.setRowIndex(1);		
		assertEquals("1:1", child.getLocalValue());
		enclosedRepeater.setRowIndex(0);
		repeater.setRowIndex(2);		
		assertEquals("0:2", child.getLocalValue());
	}
	
	public void testSetRowIndex() throws Exception {
		createDataTree();
		repeater.setValue(new MockDataModel());
		enclosedRepeater.setRowIndex(1);
		repeater.setRowIndex(1);
		child.setValue("1:1");
		repeater.setRowIndex(-1);
		enclosedRepeater.setRowIndex(-1);
		// -----------------------------
		enclosedRepeater.setRowIndex(0);
		repeater.setRowIndex(2);
		child.setValue("0:2");
		// -----------------------------
		repeater.setRowIndex(-1);
		enclosedRepeater.setRowIndex(-1);
		System.out.println("Saved child state for external repeater ");
		printChildMap(enclosedRepeater.getChildState(facesContext));
		System.out.println("Saved child state" );
		printChildMap(repeater.getChildState(facesContext));
		// -----------------------------
		enclosedRepeater.setRowIndex(1);
		repeater.setRowIndex(1);		
		assertEquals("1:1", child.getValue());
		repeater.setRowIndex(-1);
		enclosedRepeater.setRowIndex(-1);
		// -----------------------------
		enclosedRepeater.setRowIndex(0);
		repeater.setRowIndex(2);		
		assertEquals("0:2", child.getValue());
		
	}
}
