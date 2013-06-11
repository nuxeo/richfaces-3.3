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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.model.DataModel;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.component.UIDataAdaptor;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.model.DataComponentState;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.events.TreeEvents;
import org.richfaces.component.state.TreeState;
import org.richfaces.component.state.events.CollapseAllCommandEvent;
import org.richfaces.component.state.events.CollapseNodeCommandEvent;
import org.richfaces.component.state.events.ExpandAllCommandEvent;
import org.richfaces.component.state.events.ExpandNodeCommandEvent;
import org.richfaces.component.state.events.TreeStateCommandEvent;
import org.richfaces.event.DragListener;
import org.richfaces.event.DropListener;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.event.NodeSelectedListener;
import org.richfaces.event.TreeAjaxEvent;
import org.richfaces.event.TreeAjaxEventType;
import org.richfaces.event.TreeListenerEventsProducer;
import org.richfaces.model.AbstractTreeDataModel;
import org.richfaces.model.CacheableTreeDataModel;
import org.richfaces.model.ClassicCacheableTreeDataModel;
import org.richfaces.model.ClassicTreeDataModel;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.StackingTreeModel;
import org.richfaces.model.StackingTreeModelProvider;
import org.richfaces.model.SwingCacheableTreeDataModel;
import org.richfaces.model.SwingTreeDataModel;
import org.richfaces.model.TreeDataModel;
import org.richfaces.model.TreeModelVisualComponentProvider;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeRange;
import org.richfaces.model.TreeRowKey;
import org.richfaces.model.VisualStackingTreeModel;

/**
 * @author igels Component class providing tree ADT behaviuor
 */

public abstract class UITree extends UIDataAdaptor implements
TreeListenerEventsProducer, Draggable, Dropzone, AjaxComponent {

	public static final String ENCODE_BEHAVIOR_NONE = "none";

	public static final String ENCODE_BEHAVIOR_SUBTREE = "subtree";

	public static final String ENCODE_BEHAVIOR_NODE = "node";

	private transient boolean allowCachedModel = false;
	
	public static final String COMPONENT_TYPE = "org.richfaces.Tree";

	public static final String COMPONENT_FAMILY = "org.richfaces.Tree";

	public final static String PRESERVE_MODEL_STATE = "state";

	public final static String PRESERVE_MODEL_REQUEST = "request";

	public final static String PRESERVE_MODEL_NONE = ENCODE_BEHAVIOR_NONE;

	public final static String SWITCH_SERVER = "server";

	public final static String SWITCH_CLIENT = "client";

	public final static String SWITCH_AJAX = "ajax";

	public final static String SELECTED_NODE_PARAMETER_NAME = NamingContainer.SEPARATOR_CHAR
	+ "selectedNode";

	public static final String SELECTION_INPUT_ATTRIBUTE = "_selectionInput";

	public final static String DEFAULT_SELECTED_CSS_CLASS = "dr-tree-i-sel";

	public final static String DEFAULT_HIGHLIGHTED_CSS_CLASS = "dr-tree-i-hl";

	private UITreeNode defaultFacet;
	
	private Set<Object> ajaxNodeKeys = null;
	
	private Set<Object> nodeRequestKeys = null;
	
	/**
	 * Name of EL variable for the tree node.
	 * This reference is needed to let this parent class
	 * know about the attributes defined in the subclass 
	 */
	private String _treeNodeVar;

	/**
	 * Stored state of "treeNodeVar" attribute
	 */
	private Object treeNodeVarOrigValue = null;

	public UITree() {
		super();

		DecodesPhaseNotifiyingListener
		.registerListenerInstance(getFacesContext());
	}

	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * Get name of EL variable for the tree node.
	 * 
	 * @return the varState
	 */
	public String getTreeNodeVar() {
		return _treeNodeVar;
	}

	/**
	 * Set the name of EL variable
	 * 
	 * @param treeNodeVar the varStatus to set
	 */
	public void setTreeNodeVar(String treeNodeVar) {
		this._treeNodeVar = treeNodeVar;
	}
	
	/**
	 * Lazily creates default node representation that is used if there is no
	 * {@link UITreeNode} child for that nodeFace
	 * 
	 * @return created or existing node representation component
	 */
	protected UITreeNode getOrCreateDefaultFacet() {
		if (defaultFacet == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();
			defaultFacet = (UITreeNode) application
			.createComponent(UITreeNode.COMPONENT_TYPE);
			defaultFacet.setId("_defaultNodeFace");
			defaultFacet.setParent(this);
			defaultFacet.getAttributes().put(
					UITreeNode.DEFAULT_NODE_FACE_ATTRIBUTE_NAME, Boolean.TRUE);
			defaultFacet.getChildren().add(
					createDefaultNodeFaceOutput(facesContext));
		}

		return defaultFacet;
	}

	/**
	 * Return the data object representing the node for the currently
	 * selected row key, if any. 
	 * 
	 * <p>
	 * 	Adaptor-based trees do not use {@link TreeNode}, so the method always return 
	 * <code>null</code> for such trees. Please use {@link #getRowData()} instead.
	 * </p>
	 *
	 * @return {@link TreeNode} instance corresponding to the current row key or 
	 * <code>null</code> if none exist
	 */
	public TreeNode getTreeNode() {
		return ((AbstractTreeDataModel) getExtendedDataModel()).getTreeNode();
	}

	public TreeNode getModelTreeNode() {
		return ((AbstractTreeDataModel) getExtendedDataModel()).getModelTreeNode();
	}
	
	public TreeNode getModelTreeNode(Object rowKey) {
		FacesContext context = this.getFacesContext();
		Object storedKey = getRowKey();
		try {
			setRowKey(context, rowKey);
			return getModelTreeNode();
		} finally {
			try {
				setRowKey(context, storedKey);
			} catch (Exception e) {
				context.getExternalContext().log(e.getMessage(), e);
			}
		}
	}	
	
	public abstract boolean isImmediate();

	public abstract void setImmediate(boolean immediate);

	private FacesEvent wrapTreeEvent(FacesEvent facesEvent) {
		if (facesEvent.getSource() == this) {
			return new IndexedEvent(this, facesEvent, getRowKey());
		}

		return facesEvent;
	}

	public void queueEvent(FacesEvent event) {
		FacesEvent resultEvent = event;
		if (event instanceof NodeExpandedEvent) {
			if (isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}

			resultEvent = wrapTreeEvent(event);
		} else if (event instanceof NodeSelectedEvent) {
			if (isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
			}

			resultEvent = wrapTreeEvent(event);
		} else if (event instanceof TreeStateCommandEvent) {
			FacesContext context = FacesContext.getCurrentInstance();
			boolean afterDecodesPhase = null != context
			.getExternalContext()
			.getRequestMap()
			.get(
					DecodesPhaseNotifiyingListener.DECODE_PHASE_NOTICE_NAME);

			if (isImmediate() && !afterDecodesPhase) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}
		}

		super.queueEvent(resultEvent);
	}

	/**
	 * Locates {@link UITreeNode} instance that has type equal to current
	 * nodeFace. Default nodeFace is declared as #{var.type} by default where
	 * var is "var" attribute value Calls <code>getOrCreateDefaultFacet()</code>
	 * if none found.
	 * 
	 * @see UITree#getOrCreateDefaultFacet()
	 * @return {@link UITreeNode} representing current nodeFace
	 */
	public UITreeNode getNodeFacet() {
		String facetName = this.getNodeFace();
		if (facetName != null) {
			if (getChildCount() > 0) {
				Iterator iterator = getChildren().iterator();
				while (iterator.hasNext()) {
					Object object = iterator.next();
					if (object instanceof UITreeNode) {
						UITreeNode treeNode = (UITreeNode) object;

						if (!treeNode.isRendered()) {
							continue;
						}
						
						if (facetName.equals(treeNode.getType())) {
							return treeNode;
						}
					}
				}
			}
		} else {
			UIComponent component = null;
			
			ExtendedDataModel dataModel = getExtendedDataModel();
			if (dataModel instanceof TreeModelVisualComponentProvider) {
				TreeModelVisualComponentProvider componentProvider = (TreeModelVisualComponentProvider) dataModel;
				component = componentProvider.getComponent();
			}

			if (component == null) {
				component = this;
			}
			
			if (component.isRendered() && component.getChildCount() > 0) {
				Iterator iterator = component.getChildren().iterator();
				while (iterator.hasNext()) {
					UIComponent child = (UIComponent) iterator.next();
					
					if (child instanceof UITreeNode) {
						UITreeNode treeNode = (UITreeNode) child;
						
						if (!treeNode.isRendered()) {
							continue;
						}
						
						if (treeNode.getType() != null) {
							continue;
						}

						return treeNode;
					}
				}
			}
		}

		return getOrCreateDefaultFacet();
	}
	
	/**
	 * Setup EL variable for different iteration. Value of row tree node
	 * is put into request scope attributes with name "treeNodeVar" bean
	 * property. All other attributes are processed in parent 
	 * <code>UIDataAdaptor</code> class
	 *
	 * @param faces - current faces context
	 * @param localModel - reference to data model
	 * @param rowSelected - boolean flag indicating whether the row is selected
	 */
	protected void setupVariable(FacesContext faces, DataModel localModel, boolean rowSelected) {
	    super.setupVariable(faces, localModel, rowSelected);
	    // get the map storing the request scope attributes
	    Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
	    
	    String treeNodeVar = getTreeNodeVar();
	    if (rowSelected) {
		// Current row tree node.
		if (treeNodeVar != null) {
		    attrs.put(treeNodeVar, getTreeNode());
		}
	    } else {
		if (treeNodeVar != null) {
		    attrs.remove(treeNodeVar);
		}
	    }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#resetDataModel()
	 */

	public void resetDataModel() {
		this.allowCachedModel = false;
		
		super.resetDataModel();

		TreeState state = (TreeState) getComponentState();
		// re-set stopInCollapsed to handle AJAX switch type change
		state.setStopInCollapsed(isStopInCollapsed());
	}
	
	public void walk(FacesContext faces, DataVisitor visitor)
	throws IOException {

		walk(faces, visitor, null);
	}

	/**
	 * Walks through model or some subset of it if rowKey is not null
	 * 
	 * @param faces
	 *            {@link FacesContext} instance
	 * @param visitor
	 *            {@link UIDataAdaptor.ComponentVisitor} instance
	 * @param range 
	 * 			  {@link Range} range instance
	 * @param rowKey
	 *            row key to start from or null to start
	 *            from root
	 * @param argument
	 *            implementation-specific visitor argument
	 * @throws IOException
	 */
	public void walk(FacesContext faces, DataVisitor visitor, Range range,
			Object rowKey, Object argument) throws IOException {

		Object savedRowKey = getRowKey();

		try {
			AbstractTreeDataModel dataModel = (AbstractTreeDataModel) getExtendedDataModel();
			dataModel.walk(faces, visitor, range, rowKey,
					argument,
					false);
		} finally {
			try {
				setRowKey(faces, savedRowKey);
			} catch (Exception e) {
				faces.getExternalContext().log(e.getMessage(), e);
			}
		}
	}

	/**
	 * Walks through backing data model if current data model has one. Cached
	 * data models usually have one. This method is typically used for acquiring
	 * new data when some nodes has changed their state
	 * 
	 * @param faces
	 *            {@link FacesContext} instance
	 * @param visitor
	 *            {@link UIDataAdaptor.ComponentVisitor} instance
	 * @param range 
	 * 			  {@link Range} range instance
	 * @param rowKey
	 *            row key to start from or null to start
	 *            from root
	 * @param argument
	 *            implementation-specific visitor argument
	 * @throws IOException
	 */
	public void walkModel(FacesContext faces, DataVisitor visitor, Range range,
			Object key, Object argument) throws IOException {
		AbstractTreeDataModel extendedDataModel = (AbstractTreeDataModel) getExtendedDataModel();
		extendedDataModel.walkModel(faces, visitor,
				(TreeRange) range, key, argument,
				false);
	}

	public String getSelectionStateInputName(FacesContext context) {
		return getBaseClientId(context) + NamingContainer.SEPARATOR_CHAR
		+ "input";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#processDecodes(javax.faces.context.FacesContext)
	 */

	public void processDecodes(FacesContext faces) {
		if (!this.isRendered())
			return;

		Map requestParameterMap = faces.getExternalContext().getRequestParameterMap();
		if (requestParameterMap.get(getBaseClientId(faces)
				+ SELECTED_NODE_PARAMETER_NAME) != null) {
			getAttributes().put(
					SELECTED_NODE_PARAMETER_NAME,
					requestParameterMap.get(getBaseClientId(faces)
							+ SELECTED_NODE_PARAMETER_NAME));
		}
		Object selectionInputValue = requestParameterMap
		.get(getSelectionStateInputName(faces));
		if (selectionInputValue instanceof String) {
			getAttributes().put(SELECTION_INPUT_ATTRIBUTE, selectionInputValue);
		}

		try {
			super.processDecodes(faces, null);

			if (null != getAttributes().get(SELECTION_INPUT_ATTRIBUTE)) {
				TreeState state = (TreeState) getComponentState();

				if (!state.isSelected(null)) {
					new NodeSelectedEvent(this, state.getSelectedNode()).queue();
				}
			}

		} finally {
			getAttributes().remove(SELECTION_INPUT_ATTRIBUTE);
			getAttributes().remove(SELECTED_NODE_PARAMETER_NAME);
		}

		// if (treePersister != null) {
		// RequestUtils.setStoredPersister(this
		// .getBaseClientId(getFacesContext()),
		// this.getFacesContext(), treePersister);
		// setExtendedDataModel(new PersistedDataModel(treePersister,
		// (AbstractTreeDataModel) getExtendedDataModel()));
		// }
	}


	protected Iterator<UIComponent> dataChildren() {
		Collection<UIComponent> children;
		if (!isRowAvailable()) {
			children = Collections.emptyList();
		} else {
			children = Collections.singletonList((UIComponent) getNodeFacet());
		}
		
		return children.iterator();
	}

	/**
	 * Returns whether current node is leaf ie. has no children
	 * 
	 * @return <code>true</code> if current node is leaf else
	 *         <code>false</code>
	 */
	public boolean isLeaf() {
		AbstractTreeDataModel dataModel = (AbstractTreeDataModel) getExtendedDataModel();
		return dataModel.isLeaf();
	}

	/**
	 * Returns whether current node is expanded
	 * 
	 * @return <code>true</code> if node is expanded else <code>false</code>
	 */
	public boolean isExpanded() {
		TreeState treeState = (TreeState) getComponentState();
		return treeState.isExpanded((TreeRowKey) this.getRowKey());
	}

	/**
	 * Returns whether current node is selected
	 * 
	 * @return <code>true</code> if node is selected else <code>false</code>
	 */
	public boolean isSelected() {
		TreeState treeState = (TreeState) getComponentState();
		return treeState.isSelected((TreeRowKey) this.getRowKey());
	}

	/**
	 * Sets current node as selected
	 */
	public void setSelected() {
		TreeState treeState = (TreeState) getComponentState();
		treeState.setSelected((TreeRowKey) this.getRowKey());
	}

	protected Iterator<UIComponent> fixedChildren() {
		return getFacets().values().iterator();
	}


	protected DataComponentState createComponentState() {
		return new TreeState(isStopInCollapsed());
	}

	/**
	 * Returns whether switch type is set to "server"
	 * 
	 * @return <code>true</code> if switch type is "server" else
	 *         <code>false</code>
	 */
	protected boolean isStopInCollapsed() {
		return !SWITCH_CLIENT.equals(getSwitchType());
	}

	
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		
		DataComponentState componentState = getComponentState();
		if (componentState instanceof FacesListener) {
			FacesListener facesListener = (FacesListener) componentState;

			if (event.isAppropriateListener(facesListener)) {
				// we can safely open/close nodes here
				event.processListener(facesListener);
			}
		}

		if (event instanceof NodeSelectedEvent) {
			NodeSelectedEvent selectedEvent = (NodeSelectedEvent) event;
			setSelected();
		}

		// fire node events
		TreeEvents.invokeListenerBindings(this, event, getFacesContext());
		
		if (event instanceof AjaxEvent) {
			FacesContext facesContext = getFacesContext();
			AjaxRendererUtils.addRegionsFromComponent(this, facesContext);
		}
	}

	public boolean hasAjaxSubmitSelection() {
		return isAjaxSubmitSelection();
	}
	
	/**
	 * Save current state of data variable.
	 * Overrides the implementation in the base class for the case
	 * the base implementation will be changed.
	 */
	@Override
	public void captureOrigValue() {
	    this.captureOrigValue(FacesContext.getCurrentInstance());
	}
	
	/**
	 * Save current state of data variable.
	 * 
	 * @param faces current faces context
	 */
	@Override
	public void captureOrigValue(FacesContext faces) {
	    super.captureOrigValue(faces);
	    String treeNodeVar = getTreeNodeVar();
	    if (treeNodeVar != null) {
		Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
		this.treeNodeVarOrigValue = attrs.get(treeNodeVar);
	    }
	}
	
	/**
	 * Restore value of data variable after processing phase.
	 * Overrides the implementation in the base class for the case
	 * the base implementation will be changed.
	 */
	@Override
	public void restoreOrigValue() {
	    this.restoreOrigValue(FacesContext.getCurrentInstance());
	}
	
	/**
	 * Restore value of data variable after processing phase.
	 * 
	 * @param faces current faces context
	 */
	@Override
	public void restoreOrigValue(FacesContext faces) {
	    super.restoreOrigValue(faces);
	    String treeNodeVar = getTreeNodeVar();
	    if (treeNodeVar != null) {
		Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
		if (this.treeNodeVarOrigValue != null) {
		    attrs.put(treeNodeVar, this.treeNodeVarOrigValue);
		} else {
		    attrs.remove(treeNodeVar);
		}
	    }
	}

	public void processUpdates(FacesContext faces) {
		super.processUpdates(faces);
		if (getExtendedDataModel() instanceof CacheableTreeDataModel) {
			this.allowCachedModel = false;
			setExtendedDataModel(createDataModel());
		}
	}

	protected ExtendedDataModel createDataModel() {
		Object value = this.getValue();
		if (value != null) {
			if (value instanceof TreeNode) {
				TreeDataModel<TreeNode> treeDataModel = new ClassicTreeDataModel();
				treeDataModel.setWrappedData(value);
				
				if (this.allowCachedModel && PRESERVE_MODEL_REQUEST.equals(getPreserveModel())) {
					treeDataModel = new ClassicCacheableTreeDataModel(treeDataModel);
				}

				return treeDataModel;
			} else {
				TreeDataModel<javax.swing.tree.TreeNode> swingTreeDataModel = new SwingTreeDataModel();
				swingTreeDataModel.setWrappedData(value);
				
				if (this.allowCachedModel && PRESERVE_MODEL_REQUEST.equals(getPreserveModel())) {
					swingTreeDataModel = new SwingCacheableTreeDataModel(swingTreeDataModel);
				}

				return swingTreeDataModel;
			}
		} else {
			//TODO implement request caching
			StackingTreeModel stackingTreeModel = new VisualStackingTreeModel(null);
			if (getChildCount() > 0) {
				Iterator children = getChildren().iterator();
				while (children.hasNext()) {
					UIComponent component = (UIComponent) children.next();
					if (component instanceof StackingTreeModelProvider) {
						StackingTreeModelProvider provider = (StackingTreeModelProvider) component;
						stackingTreeModel.addStackingModel(provider.getStackingModel());
					}
				}
			}
			
			return stackingTreeModel;
		}
	}

	/**
	 * Queues expansion command for node whose row key is equal to rowKey
	 * parameter
	 * 
	 * @param rowKey
	 *            of the node to expand
	 * @throws IOException
	 */
	public void queueNodeExpand(TreeRowKey rowKey) throws IOException {
		new ExpandNodeCommandEvent(this, rowKey).queue();
	}

	/**
	 * Queues collapsion command for node whose row key is equal to rowKey
	 * parameter
	 * 
	 * @param rowKey
	 *            of the node to expand
	 * @throws IOException
	 */
	public void queueNodeCollapse(TreeRowKey rowKey) throws IOException {
		new CollapseNodeCommandEvent(this, rowKey).queue();
	}

	/**
	 * Queues all node expansion command
	 * 
	 * @throws IOException
	 */
	public void queueExpandAll() throws IOException {
		new ExpandAllCommandEvent(this).queue();
	}

	/**
	 * Queues all node collapsion command
	 * 
	 * @throws IOException
	 */
	public void queueCollapseAll() throws IOException {
		new CollapseAllCommandEvent(this).queue();
	}

	public abstract String getSwitchType();

	public abstract void setSwitchType(String switchType);

	public abstract String getIcon();

	public abstract void setIcon(String icon);

	public abstract String getIconExpanded();

	public abstract void setIconExpanded(String icon);

	public abstract String getIconCollapsed();

	public abstract void setIconCollapsed(String icon);

	public abstract String getIconLeaf();

	public abstract void setIconLeaf(String icon);

	public abstract void setShowConnectingLines(boolean showConnectingLines);

	public abstract boolean isShowConnectingLines();

	public abstract void setAjaxSubmitSelection(boolean ajaxSubmitSelection);

	public abstract boolean isAjaxSubmitSelection();

	public abstract String getPreserveModel();

	public abstract void setPreserveModel(String preserveModel);

	public abstract void setHighlightedClass(String selectedClass);

	public abstract String getHighlightedClass();

	public abstract void setSelectedClass(String selectedClass);

	public abstract String getSelectedClass();

	public abstract void setNodeFace(String nodeFace);

	public abstract String getNodeFace();	

	public abstract void setToggleOnClick(boolean toggleOnClick);

	public abstract boolean isToggleOnClick();

	public abstract void setStateAdvisor(Object nodeFace);

	public abstract Object getStateAdvisor();

	public abstract MethodBinding getAdviseNodeOpened();

	public abstract void setAdviseNodeOpened(MethodBinding adviseNodeOpened);

	public abstract MethodBinding getAdviseNodeSelected();

	public abstract void setAdviseNodeSelected(MethodBinding adviseNodeSelected);

	public abstract String getSimilarityGroupingId();

	public abstract void setSimilarityGroupingId(String similarityGroupingId);

	public abstract String getAjaxChildActivationEncodeBehavior();
	public abstract void setAjaxChildActivationEncodeBehavior(String behavior);
	
	public abstract String getAjaxNodeSelectionEncodeBehavior();
	public abstract void setAjaxNodeSelectionEncodeBehavior(String behavior);

	public void addChangeExpandListener(NodeExpandedListener listener) {
		addFacesListener(listener);
	}

	public void addNodeSelectListener(NodeSelectedListener listener) {
		addFacesListener(listener);
	}

	public void removeChangeExpandListener(NodeExpandedListener listener) {
		removeFacesListener(listener);
	}

	public void removeNodeSelectListener(NodeSelectedListener listener) {
		removeFacesListener(listener);
	}

	public NodeExpandedListener[] getChangeExpandListeners() {
		return (NodeExpandedListener[]) getFacesListeners(NodeExpandedListener.class);
	}

	public NodeSelectedListener[] getNodeSelectListeners() {
		return (NodeSelectedListener[]) getFacesListeners(NodeSelectedListener.class);
	}

	public void addDropListener(DropListener listener) {
		addFacesListener(listener);
	}

	public DropListener[] getDropListeners() {
		return (DropListener[]) getFacesListeners(DropListener.class);
	}

	public void removeDropListener(DropListener listener) {
		removeFacesListener(listener);
	}

	public void addDragListener(DragListener listener) {
		addFacesListener(listener);
	}

	public DragListener[] getDragListeners() {
		return (DragListener[]) getFacesListeners(DragListener.class);
	}

	public void removeDragListener(DragListener listener) {
		removeFacesListener(listener);
	}

	/**
	 * Return the data object representing the node data for the contextual row key
	 *
	 * @param rowKey contextual row key
	 * @return data corresponding to the current row key
	 */
	public Object getRowData(Object rowKey) {
		Object storedKey = getRowKey();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			setRowKey(context, rowKey);

			return getRowData();
		} finally {
			try {
				setRowKey(context, storedKey);
			} catch (Exception e) {
				context.getExternalContext().log(e.getMessage(), e);
			}
		}
	}

	/**
	 * Return the data object representing the node for the contextual row key
	 *
	 * @param rowKey contextual row key
	 * @return {@link TreeNode} instance corresponding to the current row key
	 */
	public TreeNode getTreeNode(Object rowKey) {
		FacesContext context = this.getFacesContext();
		Object storedKey = getRowKey();
		try {
			setRowKey(context, rowKey);
			return getTreeNode();
		} finally {
			try {
				setRowKey(context, storedKey);
			} catch (Exception e) {
				context.getExternalContext().log(e.getMessage(), e);
			}
		}
	}

	private UIOutput createDefaultNodeFaceOutput(FacesContext facesContext) {
		UIOutput component = new UIOutput() {

			public Object getValue() {
				return getRowData();
			}


			public String getId() {
				String id = super.getId();

				if (id == null) {
					//set fixed id to prevent duplicate id exception
					id = "_defaultNodeFaceOutput";//FacesContext.getCurrentInstance().getViewRoot()
					//.createUniqueId();

					setId(id);
				}

				return id;
			}


			public void setTransient(boolean transientFlag) {
				if (!transientFlag) {
					throw new IllegalArgumentException(
					"Default representation for node face cannot be set non-persistent!");
				}
			}


			public boolean isTransient() {
				return true;
			}
		};

		component.getAttributes().put("escape", Boolean.TRUE);
		
		return component;
	}


	public Object saveState(FacesContext faces) {
		Object[] state = new Object[4];
		state[0] = super.saveState(faces);
		state[1] = saveAttachedState(faces, defaultFacet);
		state[2] = getTreeNodeVar();
		state[3] = ajaxNodeKeys;

		return state;
	}


	public void restoreState(FacesContext faces, Object object) {
		this.allowCachedModel = true;
		
		Object[] state = (Object[]) object;
		super.restoreState(faces, state[0]);

		defaultFacet = (UITreeNode) restoreAttachedState(faces, state[1]);
		if (defaultFacet != null) {
			defaultFacet.getChildren().add(createDefaultNodeFaceOutput(faces));
			defaultFacet.setParent(this);
		}
		setTreeNodeVar((String) state[2]);
		ajaxNodeKeys = (Set<Object>) state[3];
	}
	
	public String getResolvedDragIndicator(FacesContext facesContext) {
		String dragIndicator = getDragIndicator();
		if (dragIndicator != null) {
			UIComponent indicator = RendererUtils.getInstance().
				findComponentFor(this, dragIndicator);
			if (indicator != null) {
				return indicator.getClientId(facesContext);
			}
		}
		
		return null;
	}
	
	private static final Pattern SEPARATOR = Pattern.compile("(?<!" + ListRowKey.SEPARATOR_ESCAPE_CHAR + ")\\" 
			+ NamingContainer.SEPARATOR_CHAR + "\\" + SEPARATOR_CHAR);

	@Override
	protected String extractKeySegment(FacesContext context, String tailId) {
		Matcher matcher = SEPARATOR.matcher(tailId);
		if (matcher.find()) {
			return tailId.substring(0, matcher.start());
		} else {
			return null;
		}
	}
	
	@Override
	protected Iterator<UIComponent> invocableChildren() {
		return dataChildren();
	}
	
	private static final Converter KEY_CONVERTER = new Converter() {

		public Object getAsObject(FacesContext context, UIComponent component, String value) {
			if (component == null || context == null) {
				throw new NullPointerException();
			}

			if (value == null || value.length() == 0) {
				return null;
			}

			UITree tree = (UITree) component;
			AbstractTreeDataModel dataModel = (AbstractTreeDataModel) tree.getExtendedDataModel();
			Object rowKey = dataModel.convertToKey(context, value, tree, null);

			return rowKey;
		}

		public String getAsString(FacesContext context, UIComponent component, Object value) {
			if (component == null || context == null) {
				throw new NullPointerException();
			}

			if (value == null) {
				return null;
			}

			return value.toString() + NamingContainer.SEPARATOR_CHAR;
		}
	};
	
	@Override
	public Converter getRowKeyConverter() {
	    Converter converter = super.getRowKeyConverter();
	    if (converter == null) {
	    	converter = KEY_CONVERTER;
	    }
		return converter;
	}
	
	@Override
	public void setRowKeyConverter(Converter rowKeyConverter) {
		super.setRowKeyConverter(rowKeyConverter);
	}
	
	/**
	 * Return row key for certain model's tree node
	 *
	 * @return row key
	 */
	public Object getTreeNodeRowKey(TreeNode node) {
		return getTreeNodeRowKey((Object) node);
	}
	
	/**
	 * Return row key for generic model's tree node
	 *
	 * @return row key
	 */
	public Object getTreeNodeRowKey(Object node) {
		return ((AbstractTreeDataModel) getExtendedDataModel()).getTreeNodeRowKey(node);
	}

	/**
	 * Collect current tree node state including node selection and expanded nodes list
	 * 
	 * @param transferQueuedNodes whether to collect queued expanded nodes states or not
	 * @return tree node state
	 */
	public Object getTreeNodeState() {
		// Check for node were choosed
		TreeRowKey rowKey = (TreeRowKey) getRowKey(); 
		if (rowKey == null) {
			return null;
		}
		TreeState state = (TreeState) getComponentState();
		return state.getSubState(rowKey);
	}
	
	/**
	 * Set current tree node state.
	 * 
	 * @param state node state to apply.
	 */
	public void setTreeNodeState(Object state) {
		// Check for node were choosed
	    	TreeRowKey rowKey = (TreeRowKey) getRowKey(); 
		if (rowKey == null) {
			return;
		}
		
		if (state instanceof TreeState) {
		    	TreeState currentState = (TreeState) getComponentState();
		    	currentState.mergeSubState(rowKey, (TreeState) state);
		}
	}
	
	/**
	 * Cleanup current tree node state information.
	 */
	public void clearTreeNodeState() {
		TreeRowKey rowKey = (TreeRowKey) getRowKey(); 
		if (rowKey == null) {
			return;
		}
		
		TreeState state = (TreeState) getComponentState();
		state.clearSubState(rowKey);
	}

	/**
	 * Remove node from tree
	 * 
	 * @param context JSF context
	 * @param node Node to remove
	 * @return removed node state description
	 */
	public Object removeNode(FacesContext context, Object rowKey) {
		Object nodeState = null;
		if (rowKey != null) {
			Object storedKey = getRowKey();
			try {
				setRowKey(context, rowKey);
				
				// remove node from data model
				((AbstractTreeDataModel) getExtendedDataModel()).removeNode(rowKey);
				
				// clean up node state
				nodeState = getTreeNodeState();
				clearTreeNodeState();
				setRowKey(null);
			} finally {
				try {
					setRowKey(context, storedKey);
				} catch (Exception e) {
					context.getExternalContext().log(e.getMessage(), e);
					nodeState = null;
				}
			}
		}
		
		return nodeState;
	}
	
	/**
	 * Remove node from tree
	 * 
	 * @param node Node to remove
	 * @return removed node state
	 */
	public Object removeNode(Object rowKey) {
		return removeNode(getFacesContext(), rowKey);
	}

	
	/**
	 * Add node to tree
	 * 
	 * @param parentRowKey parent node row key
	 * @param newNode inserted node
	 * @param id inserted node parent's local identifier
	 * @param state inserted tree node state. Optional
	 * 
	 * @since 3.3.1
	 */
	public void addNode(Object parentRowKey, Object newNode, Object id, Object state) {
		addNode(getFacesContext(), parentRowKey, newNode, id, state);
	}
	
	/**
	 * Add node to tree
	 * 
	 * @param context JSF context
	 * @param parentRowKey parent node row key
	 * @param newNode inserted node
	 * @param id inserted node parent's local identifier
	 * @param state inserted tree node state. Optional
	 * 
	 * @since 3.3.1
	 */
	public void addNode(FacesContext context, Object parentRowKey, Object newNode, Object id, Object state) {
		if (newNode != null) {
			Object storedKey = getRowKey();
			try {
				setRowKey(context, parentRowKey);
				
				// add node in data model
				((AbstractTreeDataModel) getExtendedDataModel()).addNode(parentRowKey, newNode, id);

				// select new node as current
				setRowKey(getTreeNodeRowKey(newNode));				
				
				// set node state if specified
				if (state != null) {
					setTreeNodeState(state);
				}				
			} finally {
				try {
					setRowKey(context, storedKey);
				} catch (Exception e) {
					context.getExternalContext().log(e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * Add node to tree
	 * 
	 * @param context JSF context
	 * @param parentRowKey parent node row key
	 * @param newNode inserted node
	 * @param id inserted node parent's local identifier
	 * @param state inserted tree node state. Optional
	 */
	public void addNode(FacesContext context, Object parentRowKey, TreeNode newNode, Object id, Object state) {
		addNode(context, parentRowKey, (Object) newNode, id, state);
	}
	
	/**
	 * Add node to tree
	 * 
	 * @param parentRowKey parent node row key
	 * @param newNode inserted node
	 * @param id inserted node parent's local identifier
	 * @param state inserted tree node state. Optional
	 */
	public void addNode(Object parentRowKey, TreeNode draggedNode, Object id, Object state) {
		addNode(getFacesContext(), parentRowKey, draggedNode, id, state);
	}
	
	public Object getParentRowKey(Object rowKey) {
		return ((AbstractTreeDataModel) getExtendedDataModel()).getParentRowKey(rowKey);
	}

	public void transferQueuedNode() {
		TreeState treeState = (TreeState) getComponentState();
		treeState.transferQueuedNodes((TreeRowKey) getRowKey());
	}

	/**
	 * @param o
	 * @since 3.3.0
	 */
	public void addNodeRequestKey(Object o) {
		if (o == null) {
			throw new IllegalArgumentException();
		}
		
		if (nodeRequestKeys == null) {
			nodeRequestKeys = new HashSet<Object>();
		}
		
		nodeRequestKeys.add(o);
	}
	
	/**
	 * @since 3.3.0
	 */
	public void clearNodeRequestKeysSet() {
		if (nodeRequestKeys != null) {
			nodeRequestKeys.clear();
		}
	}
	
	/**
	 * @param o
	 * @return
	 * @since 3.3.0
	 */
	public boolean containsNodeRequestKey(Object o) {
		return nodeRequestKeys != null && nodeRequestKeys.contains(o);
	}
	
	/**
	 * @param o
	 * @since 3.3.0
	 */
	public void removeNodeRequestKey(Object o) {
		if (nodeRequestKeys != null) {
			nodeRequestKeys.remove(o);
		}
	}
	
	/**
	 * @return
	 * @since 3.3.0
	 */
	public Set<Object> getNodeRequestKeys() {
		return nodeRequestKeys;
	}
	
	/**
	 * @return
	 * @since 3.3.0
	 */
	public Set<Object> getAjaxNodeKeys() {
		Set<Object> keys = null;
		if (this.ajaxNodeKeys != null) {
			keys = this.ajaxNodeKeys;
		} else {
			ValueExpression ve = this.getValueExpression("ajaxNodeKeys");
			if (ve != null) {
				keys = (Set<Object>) ve.getValue(getFacesContext().getELContext());
			}
		}
		
		return keys;
	}

	/**
	 * @param keys
	 * @since 3.3.0
	 */
	public void setAjaxNodeKeys(Set<Object> keys) {
		this.ajaxNodeKeys = keys;
	}
	
	/**
	 * @return
	 * @since 3.3.0
	 */
	public Set<Object> getAllAjaxNodeKeys() {
		HashSet<Object> result = null;
		
		Set<Object> ajaxNodeKeys = getAjaxNodeKeys();
		if (ajaxNodeKeys != null) {
			result = new HashSet<Object>(ajaxNodeKeys);
		}
		
		Set<Object> nodeRequestKeys = getNodeRequestKeys();
		if (nodeRequestKeys != null) {
			if (result != null) {
				result.addAll(nodeRequestKeys);
			} else {
				result = new HashSet<Object>(nodeRequestKeys);
			}
		}
		
		return result;
	}
	
	private void handleNodeEncodeBehavior(String behavior) {
		if (ENCODE_BEHAVIOR_NODE.equals(behavior)) {
			addNodeRequestKey(getRowKey());
		} else if (ENCODE_BEHAVIOR_SUBTREE.equals(behavior)) {
			addRequestKey(getRowKey());
		} else if (!ENCODE_BEHAVIOR_NONE.equals(behavior)) {
			throw new IllegalArgumentException("Unsupported behavior value: " + behavior + "!");
		}
	}
	
	@Override
	protected void addAjaxKeyEvent(FacesEvent event) {
		if (event instanceof TreeAjaxEvent) {
			TreeAjaxEvent treeAjaxEvent = (TreeAjaxEvent) event;
			if (TreeAjaxEventType.EXPANSION.equals(treeAjaxEvent.getEventType())) {
				addRequestKey(getRowKey());
			} else if (TreeAjaxEventType.SELECTION.equals(treeAjaxEvent.getEventType())) {
				String behavior = getAjaxNodeSelectionEncodeBehavior();
				if (behavior == null || behavior.length() == 0) {
					super.addAjaxKeyEvent(event);
				} else {
					handleNodeEncodeBehavior(behavior);
				}
			} else {
				super.addAjaxKeyEvent(event);
			}
		} else {
			String behavior = getAjaxChildActivationEncodeBehavior();
			if (behavior == null || behavior.length() == 0) {
				super.addAjaxKeyEvent(event);
			} else {
				handleNodeEncodeBehavior(behavior);
			}
		}
	}
}
