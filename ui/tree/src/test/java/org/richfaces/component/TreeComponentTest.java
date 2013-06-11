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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseId;

import org.ajax4jsf.resource.image.ImageInfo;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.apache.commons.lang.StringUtils;
import org.richfaces.component.state.TreeState;
import org.richfaces.component.state.events.ExpandAllCommandEvent;
import org.richfaces.component.xml.XmlTreeDataBuilder;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DragListener;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.event.NodeSelectedListener;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import org.richfaces.model.TreeRowKey;
import org.xml.sax.InputSource;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class TreeComponentTest 
extends AbstractAjax4JsfTestCase
{
	private static Set<String> javaScripts = new HashSet<String>();

	static {
		javaScripts.add("org/richfaces/renderkit/html/scripts/json/json-dom.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/json/json-mini.js");
		
		javaScripts.add("org.ajax4jsf.javascript.PrototypeScript");
		javaScripts.add("org.ajax4jsf.javascript.AjaxScript");
		javaScripts.add("org.ajax4jsf.javascript.DnDScript");

		javaScripts.add("org/richfaces/renderkit/html/scripts/utils.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/dnd/dnd-common.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/dnd/dnd-draggable.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/dnd/dnd-dropzone.js");


		javaScripts.add("org/ajax4jsf/javascript/scripts/form.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/form.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/events.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/tree.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/tree-selection.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/tree-item.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/tree-item-dnd.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/drag-indicator.js");
		javaScripts.add("org/richfaces/renderkit/html/scripts/browser_info.js");
	}

	private static Set cssStyles = new HashSet();
	private static final boolean IS_PAGE_AVAILABILITY_CHECK = true;

	static {
		cssStyles.add("css/dragIndicator.xcss");
		cssStyles.add("css/tree.xcss");
	}

	private UICommand command = null;
	private UIInput input = null;
	private UIForm form = null;
	private UITree tree = null;
	private UITreeNode treeNode = null;

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public TreeComponentTest( String testName )
	{
		super( testName );
	}

	public void setUp() throws Exception {
		super.setUp();

		form = new HtmlForm();

		facesContext.getViewRoot().getChildren().add(form);

		input = new UIInput() {
			public void decode(FacesContext context) {
				// TODO Auto-generated method stub
				super.decode(context);
			}
		};
		input.setId("input");

		form.getChildren().add(input);

		command = new HtmlCommandLink();
		command.setId("command");

		form.getChildren().add(command);

		tree = (UITree) application.createComponent(UITree.COMPONENT_TYPE);

		tree.setValue(
				XmlTreeDataBuilder.build(
						new InputSource(
								this.getClass().
								getResourceAsStream("/org/richfaces/component/xml/XmlTreeDataBuilderTest.xml")
						)));

		form.getChildren().add(tree);
		tree.queueExpandAll();

		treeNode = (UITreeNode) application.createComponent(UITreeNode.COMPONENT_TYPE);
		treeNode.setType("node");

		tree.getChildren().add(treeNode);
	}

	public void tearDown() throws Exception {
		super.tearDown();

		this.input = null;
		this.form = null;
		this.command = null;
		this.tree = null;
		this.treeNode = null;
	}

	/**
	 * Rigourous Test :-)
	 * @throws Exception 
	 */
	public void testComponent() throws Exception
	{
		HtmlPage renderedView = renderView();
		//System.out.println(renderedView.getWebResponse().getContentAsString());
		/*    	
    	HtmlInput htmlInput = (HtmlInput) renderedView.getHtmlElementById(input.getClientId(facesContext));
    	htmlInput.setValueAttribute("testInput");

    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
    	htmlLink.click();

    	List lastParameters = this.webConnection.getLastParameters();
    	for (Iterator iterator = lastParameters.iterator(); iterator.hasNext();) {
			KeyValuePair keyValue = (KeyValuePair) iterator.next();

			externalContext.addRequestParameterMap((String) keyValue.getKey(), (String) keyValue.getValue());
		}

    	UIViewRoot root = facesContext.getViewRoot();
    	root.processDecodes(facesContext);
    	root.processValidators(facesContext);
    	root.processUpdates(facesContext);
    	root.processApplication(facesContext);

    	renderedView = renderView();
		 */
	}

	public void testRenderStyle() throws Exception {
		HtmlPage page = renderView();
		assertNotNull(page);
		List styles = page.getDocumentElement().getHtmlElementsByTagName("link");
		for (Iterator it = styles.iterator(); it.hasNext();) {
			HtmlLink item = (HtmlLink) it.next();
			String srcAttr = item.getHrefAttribute();

			if (StringUtils.isNotBlank(srcAttr)) {
				boolean found = false;
				for (Iterator srcIt = cssStyles.iterator(); srcIt.hasNext();) {
					String src = (String) srcIt.next();

					found = srcAttr.contains(src);
					if (found) {
						break;
					}
				}

				assertTrue(found);
			}
		}
	}

	public void testRenderScript() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        assertEquals(getCountValidScripts(page, javaScripts, IS_PAGE_AVAILABILITY_CHECK).intValue(), javaScripts.size());
	}

	public void testRenderImages() throws Exception {
		renderView();
		assertNotNull(getResourceIfPresent("css/tree.xcss"));
		String[] resources = new String[] {
				"org.richfaces.renderkit.html.images.TreeLineCollapsedImage",
				"org.richfaces.renderkit.html.images.TreeLineExpandedImage",
				"org.richfaces.renderkit.html.images.TreeLineImage",
				"org.richfaces.renderkit.html.images.TreeLineLastImage",
				"org.richfaces.renderkit.html.images.TreeLineNodeImage",
				"org.richfaces.renderkit.html.images.TreeMinusImage",	
				"org.richfaces.renderkit.html.images.TreePlusImage"	
		};

		for (int i = 0; i < resources.length; i++) {
			ImageInfo info = getImageResource(resources[i]);
    		assertNotNull(info);
			assertEquals(ImageInfo.FORMAT_GIF, info.getFormat());
		}
	}

	private static Set styleClasses = new HashSet();

	static {
		styleClasses.add("dr-tree-h-ic dr-tree-h-ic-line-last");
		styleClasses.add("dr-tree-h-ic dr-tree-h-ic-line-clp");
		styleClasses.add("dr-tree-h-text rich-tree-node");
		styleClasses.add("dr-tree-pointer-cursor");
		styleClasses.add("");
	}

	public void testProcess() throws Exception {

		HtmlPage renderedView = renderView();

		HtmlElement htmlTree = renderedView.getHtmlElementById(tree
				.getClientId(facesContext));
		htmlTree.getAttributeValue("class").equals("dr-tree rich-tree ");

		// System.out.println(htmlTree.getHtmlElementsByAttribute("class",
		// "highlightedclass", "selectedclass"));

		List tagNames = new ArrayList();
		tagNames.add("td");
		tagNames.add("img");
		tagNames.add("div");
		List chldList = htmlTree.getHtmlElementsByTagNames(tagNames);
		//System.out.println(chldList);

		for (Iterator it = chldList.iterator(); it.hasNext();) {
			HtmlElement element = (HtmlElement) it.next();

			if (!element.getAttributeValue("class").equals("")) {
				boolean found = false;
				for (Iterator sCIt = styleClasses.iterator(); sCIt.hasNext();) {
					String src = (String) sCIt.next();
					found = element.getAttributeValue("class").contains(src);
					if (found) {
						break;
					}
				}

				assertTrue(found);
			}
			;

		}
	}


	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#addChangeExpandListener(org.richfaces.component.events.NodeExpandedListener)}.
	 */
	public final void testAddChangeExpandListener() {
		NodeExpandedListener listener = new NodeExpandedListener() {

			public void processExpansion(NodeExpandedEvent nodeExpandedEvent)
			throws AbortProcessingException {
			}

		};
		assertTrue(tree.getChangeExpandListeners().length == 0);
		assertTrue(treeNode.getChangeExpandListeners().length == 0);

		tree.addChangeExpandListener(listener);
		treeNode.addChangeExpandListener(listener);

		assertTrue(tree.getChangeExpandListeners().length == 1);
		assertTrue(treeNode.getChangeExpandListeners().length == 1);

		assertSame(listener, tree.getChangeExpandListeners()[0]);
		assertSame(listener, treeNode.getChangeExpandListeners()[0]);

		tree.removeChangeExpandListener(listener);
		treeNode.removeChangeExpandListener(listener);

		assertTrue(tree.getChangeExpandListeners().length == 0);
		assertTrue(treeNode.getChangeExpandListeners().length == 0);
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#addNodeSelectListener(org.richfaces.component.events.NodeSelectedListener)}.
	 */
	public final void testAddNodeSelectListener() {
		NodeSelectedListener listener = new NodeSelectedListener() {

			public void processSelection(NodeSelectedEvent nodeSelectedEvent)
			throws AbortProcessingException {
			}

		};
		assertTrue(tree.getNodeSelectListeners().length == 0);
		assertTrue(treeNode.getNodeSelectListeners().length == 0);

		tree.addNodeSelectListener(listener);
		treeNode.addNodeSelectListener(listener);

		assertTrue(tree.getNodeSelectListeners().length == 1);
		assertTrue(treeNode.getNodeSelectListeners().length == 1);

		assertSame(listener, tree.getNodeSelectListeners()[0]);
		assertSame(listener, treeNode.getNodeSelectListeners()[0]);

		tree.removeNodeSelectListener(listener);
		treeNode.removeNodeSelectListener(listener);

		assertTrue(tree.getNodeSelectListeners().length == 0);
		assertTrue(treeNode.getNodeSelectListeners().length == 0);
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#getUITree()}.
	 */
	public final void testGetUITree() {
		assertSame(tree, treeNode.getUITree());
		UITreeNode treeNode2 = (UITreeNode) application.createComponent(UITreeNode.COMPONENT_TYPE);
		UIOutput output = new UIOutput();
		tree.getChildren().add(output);
		output.getChildren().add(treeNode2);
		assertSame(tree, treeNode2.getUITree());
		UITreeNode treeNode3 = (UITreeNode) application.createComponent(UITreeNode.COMPONENT_TYPE);
		assertNull(treeNode3.getUITree());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#addDropListener(org.ajax4jsf.dnd.event.DropListener)}.
	 */
	public final void testAddDropListener() {
		DropListener listener = new DropListener() {

			public void processDrop(DropEvent event) {
			}

		};
		assertTrue(tree.getDropListeners().length == 0);
		assertTrue(treeNode.getDropListeners().length == 0);

		tree.addDropListener(listener);
		treeNode.addDropListener(listener);

		assertTrue(tree.getDropListeners().length == 1);
		assertTrue(treeNode.getDropListeners().length == 1);

		assertSame(listener, tree.getDropListeners()[0]);
		assertSame(listener, treeNode.getDropListeners()[0]);

		tree.removeDropListener(listener);
		treeNode.removeDropListener(listener);

		assertTrue(tree.getDropListeners().length == 0);
		assertTrue(treeNode.getDropListeners().length == 0);
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#addDragListener(org.ajax4jsf.dnd.event.DragListener)}.
	 */
	public final void testAddDragListener() {
		DragListener listener = new DragListener() {

			public void processDrag(DragEvent event) {
			}

		};
		assertTrue(tree.getDragListeners().length == 0);
		assertTrue(treeNode.getDragListeners().length == 0);

		tree.addDragListener(listener);
		treeNode.addDragListener(listener);

		assertTrue(tree.getDragListeners().length == 1);
		assertTrue(treeNode.getDragListeners().length == 1);

		assertSame(listener, tree.getDragListeners()[0]);
		assertSame(listener, treeNode.getDragListeners()[0]);

		tree.removeDragListener(listener);
		treeNode.removeDragListener(listener);

		assertTrue(tree.getDragListeners().length == 0);
		assertTrue(treeNode.getDragListeners().length == 0);
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#getDragValue()}.
	 */
	public final void testGetDragValue() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITreeNode#getDropValue()}.
	 */
	public final void testGetDropValue() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#resetDataModel()}.
	 */
	public final void testResetDataModel() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#getTreeNode()}.
	 */
	public final void testGetTreeNode() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueEvent(javax.faces.event.FacesEvent)}.
	 */
	public final void testQueueEventFacesEvent() {
		NodeExpandedEvent nodeExpandedEvent = new NodeExpandedEvent(tree);
		NodeSelectedEvent nodeSelectedEvent = new NodeSelectedEvent(tree, null);
		ExpandAllCommandEvent expandAllCommandEvent = new ExpandAllCommandEvent(tree);
		DragEvent dragEvent = new DragEvent(treeNode);
		DropEvent dropEvent = new DropEvent(treeNode);

		tree.queueEvent(nodeExpandedEvent);
		tree.queueEvent(nodeSelectedEvent);
		tree.queueEvent(expandAllCommandEvent);
		treeNode.queueEvent(dragEvent);
		treeNode.queueEvent(dropEvent);

		assertEquals(PhaseId.INVOKE_APPLICATION, nodeExpandedEvent.getPhaseId());
		assertEquals(PhaseId.UPDATE_MODEL_VALUES, nodeSelectedEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, expandAllCommandEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, dragEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, dropEvent.getPhaseId());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueEvent(javax.faces.event.FacesEvent)}.
	 */
	public final void testQueueEventFacesEventImmediate() {
		tree.setImmediate(true);

		NodeExpandedEvent nodeExpandedEvent = new NodeExpandedEvent(tree);
		NodeSelectedEvent nodeSelectedEvent = new NodeSelectedEvent(tree, null);
		ExpandAllCommandEvent expandAllCommandEvent = new ExpandAllCommandEvent(tree);
		DragEvent dragEvent = new DragEvent(treeNode);
		DropEvent dropEvent = new DropEvent(treeNode);

		tree.queueEvent(nodeExpandedEvent);
		tree.queueEvent(nodeSelectedEvent);
		tree.queueEvent(expandAllCommandEvent);
		treeNode.queueEvent(dragEvent);
		treeNode.queueEvent(dropEvent);

		assertEquals(PhaseId.APPLY_REQUEST_VALUES, nodeExpandedEvent.getPhaseId());
		assertEquals(PhaseId.APPLY_REQUEST_VALUES, nodeSelectedEvent.getPhaseId());
		assertEquals(PhaseId.APPLY_REQUEST_VALUES, expandAllCommandEvent.getPhaseId());
		assertEquals(PhaseId.APPLY_REQUEST_VALUES, dragEvent.getPhaseId());
		assertEquals(PhaseId.APPLY_REQUEST_VALUES, dropEvent.getPhaseId());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#getNodeFacet()}.
	 */
	public final void testGetNodeFacet() {
		tree.setNodeFace("node");
		assertSame(treeNode, tree.getNodeFacet());
	}

	public final void testGetOrCreateNodeFacet1() {
		tree.setNodeFace(null);
		assertNotNull(tree.getOrCreateDefaultFacet());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#getNodeFacet()}.
	 */
	public final void testGetOrCreateNodeFacet2() {
		tree.setNodeFace("node1");
		assertNotNull(tree.getOrCreateDefaultFacet());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#walk(javax.faces.context.FacesContext, org.ajax4jsf.ajax.repeat.DataVisitor)}.
	 */
	public final void testWalkFacesContextDataVisitor() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#walk(javax.faces.context.FacesContext, org.ajax4jsf.ajax.repeat.DataVisitor, org.richfaces.component.TreeRowKey, java.lang.Object)}.
	 */
	public final void testWalkFacesContextDataVisitorTreeRowKeyObject() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#walkModel(javax.faces.context.FacesContext, org.ajax4jsf.ajax.repeat.DataVisitor, org.richfaces.component.TreeRowKey, java.lang.Object)}.
	 */
	public final void testWalkModel() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#processDecodes(javax.faces.context.FacesContext)}.
	 */
	public final void testProcessDecodesFacesContext() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#dataChildren()}.
	 */
	public final void testDataChildren() {
		UIOutput output = new UIOutput();
		UIInput input = new UIInput();
		UIInput input1 = new UIInput();

		treeNode.getChildren().add(output);
		treeNode.getChildren().add(input);

		UITreeNode defaultFacet = tree.getOrCreateDefaultFacet();
		defaultFacet.getChildren().add(input1);

		tree.setNodeFace("node");
		tree.setRowKey(new ListRowKey(null, "testId"));
		Iterator dataChildren = tree.dataChildren();
		assertTrue(dataChildren.hasNext());
		assertSame(treeNode, dataChildren.next());
		assertFalse(dataChildren.hasNext());

		tree.setNodeFace("");
		dataChildren = tree.dataChildren();
		assertTrue(dataChildren.hasNext());
		assertSame(defaultFacet, dataChildren.next());
		assertFalse(dataChildren.hasNext());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#isLeaf()}.
	 */
	public final void testIsLeaf() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#isExpanded()}.
	 */
	public final void testIsExpanded() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#isSelected()}.
	 */
	public final void testIsSelected() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#fixedChildren()}.
	 */
	public final void testFixedChildren() {
		Iterator fixedChildren = tree.fixedChildren();
		assertFalse(fixedChildren.hasNext());

		UIInput input = new UIInput();
		UIOutput output = new UIOutput();
		tree.getFacets().put("facet1", input);
		tree.getFacets().put("facet2", output);

		fixedChildren = tree.fixedChildren();
		List children = new ArrayList();

		assertTrue(fixedChildren.hasNext());
		children.add(fixedChildren.next());
		assertTrue(fixedChildren.hasNext());
		children.add(fixedChildren.next());
		assertFalse(fixedChildren.hasNext());

		assertTrue(children.contains(input));
		assertTrue(children.contains(output));
		assertEquals(2, children.size());
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#createComponentState()}.
	 */
	public final void testCreateComponentState() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#isStopInCollapsed()}.
	 */
	public final void testIsStopInCollapsed() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#broadcast(javax.faces.event.FacesEvent)}.
	 */
	public final void testBroadcastFacesEvent() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#hasAjaxSubmitSelection()}.
	 */
	public final void testHasAjaxSubmitSelection() {
		assertTrue(tree.isAjaxSubmitSelection() == tree.hasAjaxSubmitSelection());
		assertFalse(tree.isAjaxSubmitSelection());
		assertFalse(treeNode.hasAjaxSubmitSelection());

		treeNode.setAjaxSubmitSelection("true");
		assertTrue(treeNode.hasAjaxSubmitSelection());

		treeNode.setAjaxSubmitSelection("inherit");
		assertFalse(treeNode.hasAjaxSubmitSelection());

		tree.setAjaxSubmitSelection(true);
		assertTrue(tree.isAjaxSubmitSelection() == tree.hasAjaxSubmitSelection());
		assertTrue(tree.isAjaxSubmitSelection());

		assertTrue(treeNode.hasAjaxSubmitSelection());

		treeNode.setAjaxSubmitSelection("false");
		assertTrue(tree.isAjaxSubmitSelection());
		assertFalse(treeNode.hasAjaxSubmitSelection());

		treeNode.setAjaxSubmitSelection(null);
		treeNode.hasAjaxSubmitSelection();
		assertTrue(treeNode.hasAjaxSubmitSelection());

		treeNode.setAjaxSubmitSelection("");
		treeNode.hasAjaxSubmitSelection();
		assertTrue(treeNode.hasAjaxSubmitSelection());

		try {
			treeNode.setAjaxSubmitSelection("untrue");
			treeNode.hasAjaxSubmitSelection();

			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#createDataModel()}.
	 */
	public final void testCreateDataModel() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueNodeExpand(org.richfaces.component.TreeRowKey)}.
	 */
	public final void testQueueNodeExpand() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueNodeCollapse(org.richfaces.component.TreeRowKey)}.
	 */
	public final void testQueueNodeCollapse() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueExpandAll()}.
	 */
	public final void testQueueExpandAll() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#queueCollapseAll()}.
	 */
	public final void testQueueCollapseAll() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#getRowData(java.lang.Object)}.
	 */
	public final void testGetRowDataObject() {
	}

	/**
	 * Test method for {@link org.richfaces.component.UITree#getTreeNode(java.lang.Object)}.
	 */
	public final void testGetTreeNodeObject() {
	}
	
	public void testResolveDragIndicator() throws Exception {
		UIComponent indicator1 = application.createComponent(UIOutput.COMPONENT_TYPE);
		indicator1.setId("indicator1");
		form.getChildren().add(indicator1);
		
		form.setId("form");
		tree.setId("tree");
		treeNode.setId("treeNode");
		
		String indicatorId = indicator1.getClientId(facesContext);
		
		tree.setDragIndicator("indicator1");
		assertEquals(indicatorId, treeNode.getResolvedDragIndicator(facesContext));
		
		tree.setDragIndicator(null);
		assertNull(treeNode.getResolvedDragIndicator(facesContext));

		treeNode.setDragIndicator("indicator1");
		assertEquals(indicatorId, treeNode.getResolvedDragIndicator(facesContext));

		treeNode.setDragIndicator(null);
		assertNull(treeNode.getResolvedDragIndicator(facesContext));

		tree.setDragIndicator("indicator1");
		treeNode.setDragIndicator("indicator2");
		assertNull(treeNode.getResolvedDragIndicator(facesContext));
	
		tree.setDragIndicator("indicator2");
		treeNode.setDragIndicator("indicator1");
		assertEquals(indicatorId, treeNode.getResolvedDragIndicator(facesContext));
	}
	
	public void testGetDragIndicator() throws Exception {
		treeNode.setDragIndicator("indicator1");
		assertEquals("indicator1", treeNode.getDragIndicator());
		tree.setDragIndicator("indicator2");
		assertEquals("indicator1", treeNode.getDragIndicator());
		treeNode.setDragIndicator(null);
		assertEquals("indicator2", treeNode.getDragIndicator());
		assertNull(treeNode.getLocalDragIndicator());
	}
	
	public void testDnDEventGetters() throws Exception {
		assertNull(treeNode.getOndragend());
		assertNull(treeNode.getOndragenter());
		assertNull(treeNode.getOndragexit());
		assertNull(treeNode.getOndragstart());
		assertNull(treeNode.getOndrop());
		assertNull(treeNode.getOndropend());

		tree.setOndragend("dragEnd1");
		tree.setOndragenter("dragEnter1");
		tree.setOndragexit("dragExit1");
		tree.setOndragstart("dragStart1");
		tree.setOndrop("drop1");
		tree.setOndropend("dropEnd1");
		
		assertEquals("dragEnd1", treeNode.getOndragend());
		assertEquals("dragEnter1", treeNode.getOndragenter());
		assertEquals("dragExit1", treeNode.getOndragexit());
		assertEquals("dragStart1", treeNode.getOndragstart());
		assertEquals("drop1", treeNode.getOndrop());
		assertEquals("dropEnd1", treeNode.getOndropend());

		treeNode.setOndragend("dragEnd2");
		treeNode.setOndragenter("dragEnter2");
		treeNode.setOndragexit("dragExit2");
		treeNode.setOndragstart("dragStart2");
		treeNode.setOndrop("drop2");
		treeNode.setOndropend("dropEnd2");
	
		assertEquals("dragEnd2", treeNode.getOndragend());
		assertEquals("dragEnter2", treeNode.getOndragenter());
		assertEquals("dragExit2", treeNode.getOndragexit());
		assertEquals("dragStart2", treeNode.getOndragstart());
		assertEquals("drop2", treeNode.getOndrop());
		assertEquals("dropEnd2", treeNode.getOndropend());

		tree.setOndragend(null);
		tree.setOndragenter(null);
		tree.setOndragexit(null);
		tree.setOndragstart(null);
		tree.setOndrop(null);
		tree.setOndropend(null);

		assertEquals("dragEnd2", treeNode.getOndragend());
		assertEquals("dragEnter2", treeNode.getOndragenter());
		assertEquals("dragExit2", treeNode.getOndragexit());
		assertEquals("dragStart2", treeNode.getOndragstart());
		assertEquals("drop2", treeNode.getOndrop());
		assertEquals("dropEnd2", treeNode.getOndropend());

	}
	
	/**
	 * Test drag & drop events processing order.
	 * Drag & drop events ALWAYS should go after all other events
	 * moreover drag should go always before it's pair drop event
	 */
	public final void testDnDListenersOrder() {
		NodeExpandedListener expandingListener = new NodeExpandedListener() {

			public void processExpansion(NodeExpandedEvent nodeExpandedEvent)
			throws AbortProcessingException {
				System.out.println("node expanded");
			}

		};
		NodeSelectedListener selectionListener = new NodeSelectedListener() {

			public void processSelection(NodeSelectedEvent nodeSelectedEvent)
			throws AbortProcessingException {
				System.out.println("node selected");
			}

		};
		DropListener dropListener = new DropListener() {

			public void processDrop(DropEvent event) {
				System.out.println("node dropped");
			}

		};
		DragListener dragListener = new DragListener() {

			public void processDrag(DragEvent event) {
				System.out.println("node dragged");
			}

		};		

		// ------------- Install listeners -----------------
		// add expansion listener
		tree.addChangeExpandListener(expandingListener);
		treeNode.addChangeExpandListener(expandingListener);
		assertTrue(tree.getChangeExpandListeners().length == 1);
		assertTrue(treeNode.getChangeExpandListeners().length == 1);
		
		// add selection listener
		tree.addNodeSelectListener(selectionListener);
		treeNode.addNodeSelectListener(selectionListener);
		assertTrue(tree.getNodeSelectListeners().length == 1);
		assertTrue(treeNode.getNodeSelectListeners().length == 1);
		
		tree.addDropListener(dropListener);
		treeNode.addDropListener(dropListener);
		assertTrue(tree.getDropListeners().length == 1);
		assertTrue(treeNode.getDropListeners().length == 1);

		tree.addDragListener(dragListener);
		treeNode.addDragListener(dragListener);
		assertTrue(tree.getDropListeners().length == 1);
		assertTrue(treeNode.getDropListeners().length == 1);

		// ------------ Invoke listeners --------------------
		NodeExpandedEvent nodeExpandedEvent = new NodeExpandedEvent(tree);
		NodeSelectedEvent nodeSelectedEvent = new NodeSelectedEvent(tree, null);
		ExpandAllCommandEvent expandAllCommandEvent = new ExpandAllCommandEvent(tree);
		DragEvent dragEvent = new DragEvent(tree);
		DropEvent dropEvent = new DropEvent(tree);

		tree.queueEvent(nodeExpandedEvent);
		tree.queueEvent(dropEvent);
		tree.queueEvent(nodeSelectedEvent);
		tree.queueEvent(dragEvent);
		tree.queueEvent(expandAllCommandEvent);
		
		facesContext.getViewRoot().processApplication(facesContext);

		/*
		assertEquals(PhaseId.INVOKE_APPLICATION, nodeExpandedEvent.getPhaseId());
		assertEquals(PhaseId.UPDATE_MODEL_VALUES, nodeSelectedEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, expandAllCommandEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, dragEvent.getPhaseId());
		assertEquals(PhaseId.INVOKE_APPLICATION, dropEvent.getPhaseId());
		 */
		
		// ------------ Cleanup --------------------
		// cleanup listeners
		tree.removeChangeExpandListener(expandingListener);
		treeNode.removeChangeExpandListener(expandingListener);
		tree.removeNodeSelectListener(selectionListener);
		treeNode.removeNodeSelectListener(selectionListener);
		tree.removeDropListener(dropListener);
		treeNode.removeDropListener(dropListener);
		tree.removeDragListener(dragListener);
		treeNode.removeDragListener(dragListener);
	}
	
	public final void testAddNode() {
	    TreeNode rootNode = new TreeNodeImpl<String>();
	    rootNode.setData("root");
	    tree.setValue(rootNode);
	    tree.setPreserveModel("");
	    
	    TreeNode<String> newNode = new TreeNodeImpl<String>();
	    newNode.setData("Sample node");
	    	    
	    tree.addNode(facesContext, null, newNode, new Integer(111), null);
	    Object rowKey = tree.getTreeNodeRowKey(newNode);
	    assertNotNull(rowKey);
	    TreeNode<String> node = tree.getTreeNode(rowKey);
	    assertSame(node, newNode);
	}
	
	public final void testRemoveNode() {
	    TreeNode rootNode = new TreeNodeImpl<String>();
	    rootNode.setData("root");
	    tree.setValue(rootNode);
	    tree.setPreserveModel("");
	    
	    TreeNode<String> newNode = new TreeNodeImpl<String>();
	    newNode.setData("Sample node");
	    	    
	    tree.addNode(facesContext, null, newNode, new Integer(111), null);
	    Object rowKey = tree.getTreeNodeRowKey(newNode);
	    assertNotNull(rowKey);
	    TreeNode<String> node = tree.getTreeNode(rowKey);
	    assertSame(node, newNode);
	    
	    tree.removeNode(rowKey);
	    
	    tree.setRowKey(rowKey);
	    assertFalse(tree.isRowAvailable());
	}
	
	public final void testManipulateState() {
	    TreeNode rootNode = new TreeNodeImpl<String>();
	    rootNode.setData("root");
	    tree.setValue(rootNode);
	    tree.setPreserveModel("");
	    
	    TreeNode<String> newNode = new TreeNodeImpl<String>();
	    newNode.setData("Sample node");	    
	    tree.addNode(facesContext, null, newNode, new Integer(111), null);
	    TreeRowKey rowKey = (TreeRowKey)tree.getTreeNodeRowKey(newNode);
	    
	    TreeNode<String> newSubNode = new TreeNodeImpl<String>();
	    newSubNode.setData("Sample sub node");
	    tree.addNode(facesContext, rowKey, newSubNode, new Integer(1), null);
	    
	    tree.setRowKey(null);
	    
            TreeState state = new TreeState();
            try {
        	state.expandAll(tree);
            } catch (Exception e) {
            	System.out.println("testManipulateState expandAll failed");
            	fail();
            }
            
            tree.setRowKey(rowKey);
            assertTrue(state.isExpanded(rowKey));
            
            TreeState oldState = (TreeState)state.getSubState(rowKey);
            state.clearSubState(rowKey);
            assertTrue(!state.isExpanded(rowKey));
            state.mergeSubState(rowKey, oldState);
            assertTrue(state.isExpanded(rowKey));
	}
}


