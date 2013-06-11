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
package org.ajax4jsf.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

import org.ajax4jsf.application.TreeStructureNode.FacetEntry;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.shale.test.mock.MockRenderKit;


/**
 * @author asmirnov
 *
 */
public class AjaxStateManagerTest extends AbstractAjax4JsfTestCase {

	private static final String FACET_B = "facetB";
	private static final String FACET_A = "facetA";
	private static final String AJAX_RENDER_KIT = "AJAX";
	private AjaxStateManager ajaxStateManager;
	
	private Object treeStructure;

	private Object state;
	/**
	 * @param name
	 */
	public AjaxStateManagerTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		RenderKitFactory renderKitFactory = (RenderKitFactory)
        FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
		renderKit = new MockRenderKit(){
			public ResponseStateManager getResponseStateManager() {
				return new ResponseStateManager(){

					public Object getComponentStateToRestore(FacesContext arg0) {
						return state;
					}

					public Object getTreeStructureToRestore(FacesContext arg0,
							String arg1) {
						return treeStructure;
					}

					public void writeState(FacesContext context,
							SerializedView serializedView) throws IOException {
						 treeStructure = serializedView.getStructure();
						 state = serializedView.getState();
					}
					
				};
			}
		};
        renderKitFactory.addRenderKit(AJAX_RENDER_KIT, renderKit);
        facesContext.getViewRoot().setRenderKitId(AJAX_RENDER_KIT);
		ajaxStateManager = new AjaxStateManager(application.getStateManager());
		application.setStateManager(ajaxStateManager);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		ajaxStateManager = null;
		treeStructure = null;
		state = null;
	}

	/**
	 * Test method for {@link org.ajax4jsf.application.AjaxStateManager#restoreStateFromSession(javax.faces.context.FacesContext, java.lang.String, java.lang.String)}.
	 */
	public void testRestoreStateFromSession() {        
		Object structure = new Object();
		Object treeState = new Object();
		ajaxStateManager.saveStateInSession(facesContext, structure, treeState);
		treeStructure = UIViewRoot.UNIQUE_ID_PREFIX+"1";
		Object[] stateFromSession = ajaxStateManager.restoreStateFromSession(facesContext, facesContext.getViewRoot().getViewId(), AJAX_RENDER_KIT);
		assertSame(structure, stateFromSession[0]);
		assertSame(treeState, stateFromSession[1]);
	}

	/**
	 * Test method for {@link org.ajax4jsf.application.AjaxStateManager#getLogicalViewId(javax.faces.context.FacesContext)}.
	 */
	public void testGetNextViewId() {
		Object nextViewId = ajaxStateManager.getLogicalViewId(facesContext);
		Object expected = ajaxStateManager.getLogicalViewId(facesContext);
		assertTrue(expected.equals(nextViewId));
		ajaxContext.setAjaxRequest(true);
		request.setAttribute(AjaxStateManager.AJAX_VIEW_SEQUENCE, expected);
		nextViewId = ajaxStateManager.getLogicalViewId(facesContext);
		assertEquals(expected, nextViewId);
	}


	/**
	 * Test method for {@link org.ajax4jsf.application.AjaxStateManager#getRenderKit(javax.faces.context.FacesContext, java.lang.String)}.
	 */
	public void testGetRenderKit() {
		AjaxStateManager stateManager = getAjaxStateManager();
		RenderKit kit = stateManager.getRenderKit(facesContext, AJAX_RENDER_KIT);
		assertSame(renderKit, kit);
	}
	
	public void testTreeNodeApply() throws Exception {
			buildTestTree();
			TreeStructureNode node = new TreeStructureNode();
			node.apply(facesContext, facesContext.getViewRoot(), new HashSet<String>());
			assertEquals(2, node.getChildren().size());
			assertEquals(1, node.getFacets().size());
			FacetEntry nodeA = node.getFacets().get(0);
			assertNotNull(nodeA);
			assertEquals(FACET_A, nodeA.getName());
			assertEquals(1, nodeA.getNode().getChildren().size());
			assertEquals(1, nodeA.getNode().getFacets().size());
		
	}

	public void testTreeNodeCheckUniqueId() throws Exception {
		buildTestTree();
		TreeStructureNode node = new TreeStructureNode();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		((UIComponent)viewRoot.getChildren().get(0)).setId(FACET_A);
		((UIComponent)viewRoot.getChildren().get(1)).setId(FACET_A);
		try {
			node.apply(facesContext, facesContext.getViewRoot(), new HashSet());
		} catch (IllegalStateException e) {
			assertTrue(e.getMessage().startsWith("duplicate"));
			return;
		}
		assertTrue("Duplicate components Id's not detected ",false);
	}
	
	
	public void testTreeNodeSerialisation() throws Exception {
		buildTestTree();
		TreeStructureNode node = new TreeStructureNode();
		node.apply(facesContext, facesContext.getViewRoot(), new HashSet());
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(node);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		TreeStructureNode nodeIn = (TreeStructureNode) in.readObject();
		assertEquals(2, nodeIn.getChildren().size());
		assertEquals(1, nodeIn.getFacets().size());
		FacetEntry nodeA = nodeIn.getFacets().get(0);
		assertNotNull(nodeA);
		assertEquals(FACET_A, nodeA.getName());
		assertEquals(1, nodeA.getNode().getChildren().size());
		assertEquals(1, nodeA.getNode().getFacets().size());
	}
	/**
	 * 
	 */
	private void buildTestTree() {
		facesContext.getViewRoot().getChildren().add(new UIInput());
		facesContext.getViewRoot().getChildren().add(new UIOutput());
		UIData data = new UIData();
		facesContext.getViewRoot().getFacets().put(FACET_A, data);
		data.getChildren().add(new UIColumn());
		data.getFacets().put(FACET_B, new UIOutput());
	}

	/**
	 * @return
	 */
	private AjaxStateManager getAjaxStateManager() {		
		return ajaxStateManager;
	}

}
