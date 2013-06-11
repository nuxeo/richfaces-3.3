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
package org.richfaces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.events.TreeEvents;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DragListener;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;
import org.richfaces.event.NodeExpandedListener;
import org.richfaces.event.NodeSelectedListener;
import org.richfaces.event.TreeListenerEventsProducer;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com created 22.11.2006 Component
 *         class providing concrete representation for tree node
 */

public abstract class UITreeNode extends UIComponentBase implements
TreeListenerEventsProducer, Draggable, Dropzone, AjaxComponent {

	private String dragType;
	private Object acceptedTypes;
	private String dragIndicator;
	
	private boolean  ajaxSingle = false;
	private boolean  bypassUpdates = false;
	private boolean  limitToList = false;
	private Object  reRender = null;
	private String  status = null;
	private String  eventsQueue = null;
	private String similarityGroupingId = null;
	private int  requestDelay = Integer.MIN_VALUE;
	private String  oncomplete = null;
	private String  focus = null;
	private Object  data = null;
	private boolean  ignoreDupResponses = false;
	private int  timeout = Integer.MIN_VALUE;	
	
	private boolean ajaxSingleSet = false;
	private boolean bypassUpdatesSet = false;
	private boolean limitToListSet = false;
	private boolean requestDelaySet = false;
	private boolean ignoreDupResponsesSet = false;
	private boolean timeoutSet = false;
	
	public static final String COMPONENT_TYPE = "org.richfaces.TreeNode";

	public static final String COMPONENT_FAMILY = "org.richfaces.TreeNode";

	/**
	 * Attribute name to indicate if this tree node is a virtual
	 * {@link UITreeNode} providing default representation for node. Default
	 * tree node representation should use attributes of its parent
	 * {@link UITree} component
	 */
	protected final static String DEFAULT_NODE_FACE_ATTRIBUTE_NAME = "#defaultNodeFace";

	private final static String ICON_LEAF_FACET_NAME = "iconLeaf";
	private final static String ICON_FACET_NAME = "icon";
	private final static String ICON_EXPANDED_FACET_NAME = "iconExpanded";
	private final static String ICON_COLLAPSED_FACET_NAME = "iconCollapsed";
	
	public abstract String getType();

	public abstract void setType(String type);

	public abstract String getIcon();

	public abstract void setIcon(String icon);

	public abstract String getIconExpanded();

	public abstract void setIconExpanded(String icon);

	public abstract String getIconCollapsed();

	public abstract void setIconCollapsed(String icon);

	public abstract String getIconLeaf();

	public abstract void setIconLeaf(String icon);

	public abstract void setAjaxSubmitSelection(String ajaxSubmitSelection);

	public abstract String getAjaxSubmitSelection();

	public abstract void setHighlightedClass(String selectedClass);

	public abstract String getHighlightedClass();

	public abstract void setSelectedClass(String selectedClass);

	public abstract String getSelectedClass();

	public boolean hasAjaxSubmitSelection() {
		String ajaxSubmitSelection = getAjaxSubmitSelection();
		if ("inherit".equals(ajaxSubmitSelection) || 
				ajaxSubmitSelection == null || ajaxSubmitSelection.length() == 0) {
			return getUITree().isAjaxSubmitSelection();
		} else if ("true".equals(ajaxSubmitSelection)) {
			return true;
		} else if ("false".equals(ajaxSubmitSelection)) {
			return false;
		} else {
			throw new IllegalArgumentException(
			"Property \"ajaxSubmitSelection\" should be \"inherit\", \"true\" or \"false\".");
		}

	}


	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);

		FacesContext context = getFacesContext();
		UITree tree = getUITree();

		TreeEvents.invokeListenerBindings(this, event, context);

		//TODO quick fix for UITree to invoke listeners
		
		if (tree != null && (event instanceof DragEvent || event instanceof DropEvent)) {
			tree.broadcast(event);
		} else if (event instanceof DnDEventWrapper) {
			event.queue();
		}

		if (event instanceof AjaxEvent) {
			AjaxRendererUtils.addRegionsFromComponent(this, getFacesContext());
		}
	}

	/**
	 * <p>
	 * Queue an event for broadcast at the end of the current request
	 * processing lifecycle phase. The default implementation in
	 * {@link UIComponentBase} must delegate this call to the
	 * <code>queueEvent()</code> method of the parent {@link UIComponent}.
	 * </p>
	 * 
	 * @param event {@link FacesEvent} to be queued
	 * 
	 * @throws IllegalStateException if this component is not a descendant of a {@link UIViewRoot}
	 * @throws NullPointerException if <code>event</code> is <code>null</code>
	 */
	@Override
	public void queueEvent(FacesEvent event) {
		FacesEvent resultEvent = event;
		
		UITree tree = getUITree();
		if (tree != null && (event instanceof DragEvent || event instanceof DropEvent)) {
			if (tree.isImmediate()) {
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			} else {
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}
			
			resultEvent = new DnDEventWrapper(this, event, tree.getRowKey());
		} else if (event instanceof DnDEventWrapper) {
			resultEvent = ((DnDEventWrapper) event).getTarget();
		}
		
		super.queueEvent(resultEvent);
	}

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


	/**
	 * Finds direct parent {@link UITree} component or throws
	 * 
	 * @return {@link UITree} instance
	 */
	public UITree getUITree() {
		UIComponent parent = getParent();
		while (parent != null) {
			if (parent instanceof UITree) {
				return (UITree) parent;
			} else {
				parent = parent.getParent();
			}
		}

		return null;
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

	public void setAcceptedTypes(Object types) {
		this.acceptedTypes = types;
	}

	/*
	 * List of drag types to be processd by the current drop zone.
	 * Getter for acceptedTypes
	 * @return acceptedTypes value from local variable or value bindings
	 */
	public Object getAcceptedTypes(  ){
		if (null != this.acceptedTypes)
		{
			return this.acceptedTypes;
		}
		ValueBinding vb = getValueBinding("acceptedTypes");
		if (null != vb){
			return (Object)vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getAcceptedTypes();
			}

			return null;
		}
	}

	public void setDragType(String dragType) {
		this.dragType = dragType;
	}

	/*
	 * Key of a drag object. It's used to define a necessity of processing the current dragged element on the drop zone side
	 * Getter for dragType
	 * @return dragType value from local variable or value bindings
	 */
	public String getDragType(  ){
		if (null != this.dragType)
		{
			return this.dragType;
		}
		ValueBinding vb = getValueBinding("dragType");
		if (null != vb){
			return (String)vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getDragType();
			}
			
			return null;
		}
	}

	private UIComponent getTreeFacet(String facetName) {
		UIComponent facet = getFacet(facetName);
		if (facet == null || !facet.isRendered()) {
			UIComponent parentFacet = getUITree().getFacet(facetName);
			if (facet == null || parentFacet != null && parentFacet.isRendered()) {
				facet = parentFacet;
			}
		}
		
		return facet;
	}
	
	public UIComponent getIconFacet() {
		return getTreeFacet(ICON_FACET_NAME);
	}

	public UIComponent getIconLeafFacet() {
		return getTreeFacet(ICON_LEAF_FACET_NAME);
	}
	
	public UIComponent getIconExpandedFacet() {
		return getTreeFacet(ICON_EXPANDED_FACET_NAME);
	}
	
	public UIComponent getIconCollapsedFacet() {
		return getTreeFacet(ICON_COLLAPSED_FACET_NAME);
	}

	public Object saveState(FacesContext context) {
		Object[] state = new Object[17];
		state[0] = super.saveState(context);
		state[1] = this.dragType;
		state[2] = this.acceptedTypes;
		state[3] = this.dragIndicator;
		state[4] = this.reRender;
		state[5] = this.oncomplete;
		state[6] = new Boolean(this.ajaxSingle);
		state[7] = new Boolean(this.bypassUpdates);
		state[8] = new Boolean(this.limitToList);
		state[9] = this.status;
		state[10] = this.eventsQueue;
		state[11] = new Integer(this.requestDelay);
		state[12] = this.focus;
		state[13] = this.data;
		state[14] = new Boolean(this.ignoreDupResponses);
		state[15] = new Integer(this.timeout);
		state[16] = this.similarityGroupingId;
		
		return state;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] _state = (Object[]) state;
		super.restoreState(context, _state[0]);
		this.dragType = (String) _state[1];
		this.acceptedTypes = _state[2];
		this.dragIndicator = (String) _state[3];
		this.reRender = (Object) _state[4];
		this.oncomplete = (String) _state[5];
		this.ajaxSingle = ((Boolean)_state[6]).booleanValue();
		this.bypassUpdates = ((Boolean)_state[7]).booleanValue();
		this.limitToList = ((Boolean)_state[8]).booleanValue();
		this.status = (String) _state[9];
		this.eventsQueue = (String) _state[10];
		this.requestDelay = ((Integer)_state[11]).intValue();
		this.focus = (String) _state[12];
		this.data = _state[13];
		this.ignoreDupResponses = ((Boolean)_state[14]).booleanValue();
		this.timeout = ((Integer)_state[15]).intValue();
		this.similarityGroupingId = (String) _state[16];
	}
	
	public void setDragIndicator(String dragIndicator) {
		this.dragIndicator = dragIndicator;
	}
	
	protected String getLocalDragIndicator() {
		if (dragIndicator != null) {
			return dragIndicator;
		}
		
		ValueBinding vb = getValueBinding("dragIndicator");
		if (vb != null) {
			return (String) vb.getValue(getFacesContext());
		}
		
		return null;
	}
	
	public String getDragIndicator() {
		String localDragIndicator = getLocalDragIndicator();
		if (localDragIndicator == null) {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getDragIndicator();
			} else {
				return null;
			}
		} else {
			return localDragIndicator;
		}
	}
	
	public String getResolvedDragIndicator(FacesContext facesContext) {
		String indicatorId = getLocalDragIndicator();
		UITree tree = getUITree();
		if (tree != null) {
			if (indicatorId != null) {
				UIComponent indicator = RendererUtils.getInstance().
					findComponentFor(this, indicatorId);
				if (indicator != null) {
					return indicator.getClientId(facesContext);
				}
			} else {
				return tree.getResolvedDragIndicator(facesContext);
			}
		}
		
		return null;
	}
	
	protected String getDefaultOndragend() {
		//tag invokes read method on component creation
		//we shouldn't fail with NPE
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndragend();
	}
	
	protected String getDefaultOndragenter() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndragenter();
	}
	
	protected String getDefaultOndragexit() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndragexit();
	}
	
	protected String getDefaultOndragstart() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndragstart();
	}
	
	protected String getDefaultOndrop() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndrop();
	}
	
	protected String getDefaultOndropend() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getOndropend();
	}
	
	protected int getDefaultTimeout() {
		UITree tree = getUITree();
		if (tree == null) {
			return 0;
		}
		return tree.getTimeout();
	}
	
	protected Object getDefaultReRender() {
		UITree tree = getUITree();
		if (tree == null) {
			return null;
		}
		return tree.getReRender();
	}
	
	 /**
	 * if "true", submits ONLY one field/link, instead of all form controls
	 * Setter for ajaxSingle
	 * @param ajaxSingle - new value
	 */
	 public void setAjaxSingle( boolean  __ajaxSingle ){
		this.ajaxSingle = __ajaxSingle;
	 	this.ajaxSingleSet = true;
	 }
	
	/**
	 * if "true", submits ONLY one field/link, instead of all form controls
	 * Getter for ajaxSingle
	 * @return ajaxSingle value from local variable or value bindings
	 */
	 public boolean isAjaxSingle(  ){
		 if(this.ajaxSingleSet){
			return this.ajaxSingle;
		 }
    	ValueBinding vb = getValueBinding("ajaxSingle");
    	if (vb != null) {
    	    Boolean value = (Boolean) vb.getValue(getFacesContext());
    	    if (null == value) {
    	    	UITree tree = getUITree();
    			if (tree != null) {
    				return tree.isAjaxSingle();
    			}
    			return this.ajaxSingle;
    	    }
    	    return (value.booleanValue());
    	} else {
    		UITree tree = getUITree();
			if (tree != null) {
				return tree.isAjaxSingle();
			}
			return this.ajaxSingle;
    	}
	 }
	 
	 /**
	 * If "true", after process validations phase it skips updates of model beans on a force render response. It can be used for validating components input
	 * Setter for bypassUpdates
	 * @param bypassUpdates - new value
	 */
	 public void setBypassUpdates( boolean  __bypassUpdates ){
		this.bypassUpdates = __bypassUpdates;
	 	this.bypassUpdatesSet = true;
	 }
	 
	 /**
	 * If "true", after process validations phase it skips updates of model beans on a force render response. It can be used for validating components input
	 * Getter for bypassUpdates
	 * @return bypassUpdates value from local variable or value bindings
	 */
	public boolean isBypassUpdates() {
		if (this.bypassUpdatesSet) {
			return this.bypassUpdates;
		}
		ValueBinding vb = getValueBinding("bypassUpdates");
		if (vb != null) {
			Boolean value = (Boolean) vb.getValue(getFacesContext());
			if (null == value) {
				UITree tree = getUITree();
				if (tree != null) {
					return tree.isBypassUpdates();
				}
				return this.bypassUpdates;
			}
			return (value.booleanValue());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.isBypassUpdates();
			}
			return this.bypassUpdates;
		}
	}
	
	 /**
	 * If "true", updates on client side ONLY elements from this 'reRender' property. If "false" (default) updates all rendered by ajax region components
	 * Setter for limitToList
	 * @param limitToList - new value
	 */
	public void setLimitToList(boolean __limitToList) {
		this.limitToList = __limitToList;
		this.limitToListSet = true;
	}

	/**
	 * If "true", updates on client side ONLY elements from this 'reRender' property. If "false" (default) updates all rendered by ajax region components
	 * Getter for limitToList
	 * @return limitToList value from local variable or value bindings
	 */
	public boolean isLimitToList() {
		if (this.limitToListSet) {
			return this.limitToList;
		}
		ValueBinding vb = getValueBinding("limitToList");
		if (vb != null) {
			Boolean value = (Boolean) vb.getValue(getFacesContext());
			if (null == value) {
				UITree tree = getUITree();
				if (tree != null) {
					return tree.isLimitToList();
				}
				return this.limitToList;
			}
			return (value.booleanValue());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.isLimitToList();
			}
			return (this.limitToList);
		}
	}
	
	/**
	 * Id['s] (in format of call  UIComponent.findComponent()) of components, rendered in case of AjaxRequest  caused by this component. Can be single id, comma-separated list of Id's, or EL Expression  with array or Collection
	 * Setter for reRender
	 * @param reRender - new value
	 */
	public void setReRender(Object __reRender) {
		this.reRender = __reRender;
	}

	/**
	 * Id['s] (in format of call  UIComponent.findComponent()) of components, rendered in case of AjaxRequest  caused by this component. Can be single id, comma-separated list of Id's, or EL Expression  with array or Collection
	 * Getter for reRender
	 * @return reRender value from local variable or value bindings
	 */
	public Object getReRender() {
		if (null != this.reRender) {
			return this.reRender;
		}
		ValueBinding vb = getValueBinding("reRender");
		if (null != vb) {
			return (Object) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getReRender();
			}
			return null;
		}
	}
	
	/**
	 * ID (in format of call UIComponent.findComponent()) of Request status component
	 * Setter for status
	 * @param status - new value
	 */
	public void setStatus(String __status) {
		this.status = __status;
	}

	/**
	 * ID (in format of call UIComponent.findComponent()) of Request status component
	 * Getter for status
	 * @return status value from local variable or value bindings
	 */
	public String getStatus() {
		if (null != this.status) {
			return this.status;
		}
		ValueBinding vb = getValueBinding("status");
		if (null != vb) {
			return (String) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getStatus();
			}
			return null;
		}
	}
	
	/**
	 * Name of requests queue to avoid send next request before complete other from same event. Can be used to reduce number of requests of frequently events (key press, mouse move etc.)
	 * Setter for eventsQueue
	 * @param eventsQueue - new value
	 */
	public void setEventsQueue(String __eventsQueue) {
		this.eventsQueue = __eventsQueue;
	}

	/**
	 * Name of requests queue to avoid send next request before complete other from same event. Can be used to reduce number of requests of frequently events (key press, mouse move etc.)
	 * Getter for eventsQueue
	 * @return eventsQueue value from local variable or value bindings
	 */
	public String getEventsQueue() {
		if (null != this.eventsQueue) {
			return this.eventsQueue;
		}
		ValueBinding vb = getValueBinding("eventsQueue");
		if (null != vb) {
			return (String) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getEventsQueue();
			}
			return null;
		}
	}
	
	/**
	 * @param similarityGroupingId - new value
	 */
	public void setSimilarityGroupingId(String __similarityGroupingId) {
		this.similarityGroupingId = __similarityGroupingId;
	}

	/**
	 * @return similarityGroupingId value from local variable or value bindings
	 */
	public String getSimilarityGroupingId() {
		if (null != this.similarityGroupingId) {
			return this.similarityGroupingId;
		}
		ValueBinding vb = getValueBinding("similarityGroupingId");
		if (null != vb) {
			return (String) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getSimilarityGroupingId();
			}
			return null;
		}
	}

	/**
	 * Attribute defines the time (in ms.) that the request will be wait in the queue before it is ready to send.
	 When the delay time is over, the request will be sent to the server or removed if the newest 'similar' request is in a queue already
	 * Setter for requestDelay
	 * @param requestDelay - new value
	 */
	public void setRequestDelay(int __requestDelay) {
		this.requestDelay = __requestDelay;
		this.requestDelaySet = true;
	}

	/**
	 * Attribute defines the time (in ms.) that the request will be wait in the queue before it is ready to send.
	 When the delay time is over, the request will be sent to the server or removed if the newest 'similar' request is in a queue already
	 * Getter for requestDelay
	 * @return requestDelay value from local variable or value bindings
	 */
	public int getRequestDelay() {
		if (this.requestDelaySet) {
			return this.requestDelay;
		}
		ValueBinding vb = getValueBinding("requestDelay");
		if (vb != null) {
			Integer value = (Integer) vb.getValue(getFacesContext());
			if (null == value) {
				UITree tree = getUITree();
				if (tree != null) {
					return tree.getRequestDelay();
				}
				return this.requestDelay;
			}
			return (value.intValue());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getRequestDelay();
			}
			return (this.requestDelay);
		}
	}
	
	/**
	 * JavaScript code for call after request completed on client side
	 * Setter for oncomplete
	 * @param oncomplete - new value
	 */
	public void setOncomplete(String __oncomplete) {
		this.oncomplete = __oncomplete;
	}

	/**
	 * JavaScript code for call after request completed on client side
	 * Getter for oncomplete
	 * @return oncomplete value from local variable or value bindings
	 */
	public String getOncomplete() {
		if (null != this.oncomplete) {
			return this.oncomplete;
		}
		ValueBinding vb = getValueBinding("oncomplete");
		if (null != vb) {
			return (String) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getOncomplete();
			}
			return null;
		}
	}
	
	/**
	 * id of element to set focus after request completed on client side
	 * Setter for focus
	 * @param focus - new value
	 */
	public void setFocus(String __focus) {
		this.focus = __focus;
	}

	/**
	 * id of element to set focus after request completed on client side
	 * Getter for focus
	 * @return focus value from local variable or value bindings
	 */
	public String getFocus() {
		if (null != this.focus) {
			return this.focus;
		}
		ValueBinding vb = getValueBinding("focus");
		if (null != vb) {
			return (String) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getFocus();
			}
			return null;
		}
	}
	
	 /**
	 * Serialized (on default with JSON) data passed on the client by a developer on AJAX request. It's accessible via "data.foo" syntax
	 * Setter for data
	 * @param data - new value
	 */
	public void setData(Object __data) {
		this.data = __data;
	}

	/**
	 * Serialized (on default with JSON) data passed on the client by a developer on AJAX request. It's accessible via "data.foo" syntax
	 * Getter for data
	 * @return data value from local variable or value bindings
	 */
	public Object getData() {
		if (null != this.data) {
			return this.data;
		}
		ValueBinding vb = getValueBinding("data");
		if (null != vb) {
			return (Object) vb.getValue(getFacesContext());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getData();
			}
			return null;
		}
	}
	
	/**
	 * Attribute allows to ignore an Ajax Response produced by a request if the newest 'similar' request is
	 in a queue already. ignoreDupResponses="true" does not cancel the request while it is processed on the server,
	 but just allows to avoid unnecessary updates on the client side if the response isn't actual now
	 * Setter for ignoreDupResponses
	 * @param ignoreDupResponses - new value
	 */
	public void setIgnoreDupResponses(boolean __ignoreDupResponses) {
		this.ignoreDupResponses = __ignoreDupResponses;
		this.ignoreDupResponsesSet = true;
	}

	/**
	 * Attribute allows to ignore an Ajax Response produced by a request if the newest 'similar' request is
	 in a queue already. ignoreDupResponses="true" does not cancel the request while it is processed on the server,
	 but just allows to avoid unnecessary updates on the client side if the response isn't actual now
	 * Getter for ignoreDupResponses
	 * @return ignoreDupResponses value from local variable or value bindings
	 */
	public boolean isIgnoreDupResponses() {
		if (this.ignoreDupResponsesSet) {
			return this.ignoreDupResponses;
		}
		ValueBinding vb = getValueBinding("ignoreDupResponses");
		if (vb != null) {
			Boolean value = (Boolean) vb.getValue(getFacesContext());
			if (null == value) {
				UITree tree = getUITree();
				if (tree != null) {
					return tree.isIgnoreDupResponses();
				}
				return this.ignoreDupResponses;
			}
			return (value.booleanValue());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.isIgnoreDupResponses();
			}
			return (this.ignoreDupResponses);
		}
	}
	
	 /**
	 * Response waiting time on a particular request. If a response is not received during this time, the request is aborted
	 * Setter for timeout
	 * @param timeout - new value
	 */
	public void setTimeout(int __timeout) {
		this.timeout = __timeout;
		this.timeoutSet = true;
	}

	/**
	 * Response waiting time on a particular request. If a response is not received during this time, the request is aborted
	 * Getter for timeout
	 * @return timeout value from local variable or value bindings
	 */
	public int getTimeout() {
		if (this.timeoutSet) {
			return this.timeout;
		}
		ValueBinding vb = getValueBinding("timeout");
		if (vb != null) {
			Integer value = (Integer) vb.getValue(getFacesContext());
			if (null == value) {
				UITree tree = getUITree();
				if (tree != null) {
					return tree.getTimeout();
				}
				return this.timeout;
			}
			return (value.intValue());
		} else {
			UITree tree = getUITree();
			if (tree != null) {
				return tree.getTimeout();
			}
			return (this.timeout);
		}
	}
	
	/**
	 * Helper inner class used to wrap drag and drop events to suspend them to ensure
	 * that events will processed last.
	 * 
	 * Note: We should ensure that all drag & drop events will be processed after all other events
	 * to avoid problems with removed while drop operation tree nodes and their event handlers.   
	 *  
	 * @author dmorozov
	 */
	protected static final class DnDEventWrapper extends FacesEvent {

		private static final long serialVersionUID = -5479811879939203868L;
		
		private final FacesEvent target;
		
		private Object key;

		/**
		 * Construct a new event object that wrap original event and store target node row key
		 * to be processed in future.
		 * 
		 * @param owner event owner component
		 * @param target wrapped event
		 * @param key target tree node row key
		 */
		public DnDEventWrapper(UIComponent owner, FacesEvent target, Object key) {
			super(owner);
			this.target = target;
			this.key = key;
			if (this.target == null) {
				throw new NullPointerException();
			}
		}

		/**
        	 * <p>
        	 * Return the identifier of the request processing phase during which
        	 * this event should be delivered.
        	 * </p>
        	 */		
		public PhaseId getPhaseId() {
			return target.getPhaseId();
		}
		
		/**
        	 * <p> Set the {@link PhaseId} during which this event will be delivered.</p>
        	 * @throws IllegalArgumentException phaseId is null.
        	 */ 
		public void setPhaseId(PhaseId phaseId) {
			throw new UnsupportedOperationException();
		}

		/**
		 * Get wrapped event
		 * @return wrapped event
		 */
		public FacesEvent getTarget() {
			return target;
		}

		/* (non-Javadoc)
		 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
		 */
		@Override
		public boolean isAppropriateListener(FacesListener listener) {
			return false;
		}

		/* (non-Javadoc)
		 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
		 */
		@Override
		public void processListener(FacesListener listener) {
			throw new UnsupportedOperationException();
		}

		/**
		 * Get target component row key
		 * @return the row key
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Set target component row key
		 * @param key the row key to set
		 */
		public void setKey(Object key) {
			this.key = key;
		}
	}	
}
