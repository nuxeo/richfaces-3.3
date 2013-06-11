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

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.StateHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.render.Renderer;

import org.ajax4jsf.model.DataComponentState;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.SerializableDataModel;
import org.ajax4jsf.renderkit.AjaxChildrenRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for iterable components, like dataTable, Tomahawk dataList,
 * Facelets repeat, tree etc., with support for partial rendering on AJAX
 * responces for one or more selected iterations.
 * 
 * @author shura
 * 
 */
public abstract class UIDataAdaptor extends UIData implements AjaxDataEncoder {

	/**
	 * 
	 */
	public static final String COMPONENT_STATE_ATTRIBUTE = "componentState";

	public final static DataModel EMPTY_MODEL = new ListDataModel(
			Collections.EMPTY_LIST);
	
	private static final Log _log = LogFactory.getLog(UIDataAdaptor.class);

	/**
	 * Base class for visit data model at phases decode, validation and update
	 * model
	 * 
	 * @author shura
	 * 
	 */
	protected abstract class ComponentVisitor implements DataVisitor {

		public void process(FacesContext context, Object rowKey, Object argument)
				throws IOException {
			setRowKey(context, rowKey);
			if (isRowAvailable()) {
				Iterator<UIComponent> childIterator = dataChildren();
				while (childIterator.hasNext()) {
					UIComponent component = childIterator.next();
					if (UIColumn.class.equals(component.getClass())) {
						for (UIComponent children : component.getChildren()) {
							processComponent(context, children, argument);
						}
					} else {
						processComponent(context, component, argument);
					}
				}

			}
		}

		public abstract void processComponent(FacesContext context,
				UIComponent c, Object argument) throws IOException;

	}

	/**
	 * Visitor for process decode on children components.
	 */
	protected ComponentVisitor decodeVisitor = new ComponentVisitor() {

		public void processComponent(FacesContext context, UIComponent c,
				Object argument) {
				c.processDecodes(context);
		}

	};

	/**
	 * Visitor for process validation phase
	 */
	protected ComponentVisitor validateVisitor = new ComponentVisitor() {

		public void processComponent(FacesContext context, UIComponent c,
				Object argument) {
				c.processValidators(context);
		}

	};

	/**
	 * Visitor for process update model phase.
	 */
	protected ComponentVisitor updateVisitor = new ComponentVisitor() {

		public void processComponent(FacesContext context, UIComponent c,
				Object argument) {
				c.processUpdates(context);
		}

	};

	/**
	 * Base client id's of this component, for wich invoked encode... methods.
	 * Component will save state and serialisable models for this keys only.
	 */
	private Set<String> _encoded;

	/**
	 * Storage for data model instances with different client id's of this
	 * component. In case of child for UIData component, this map will keep data
	 * models for different iterations between phases.
	 */
	private Map<String, ExtendedDataModel> _modelsMap = new HashMap<String, ExtendedDataModel>();

	/**
	 * Reference for curent data model
	 */
	private ExtendedDataModel _currentModel = null;

	/**
	 * States of this component for diferent iterations, same as for models.
	 */
	private Map<String, DataComponentState> _statesMap = new HashMap<String, DataComponentState>();

	/**
	 * Reference for current component state.
	 */
	private DataComponentState _currentState = null;

	/**
	 * Name of EL variable for current component state.
	 */
	private String _stateVar;

	private String _rowKeyVar;

	/**
	 * Key for current value in model.
	 */
	private Object _rowKey = null;
	
	
	private Converter _rowKeyConverter = null;

	/**
	 * Values of row keys, encoded on ajax response rendering.
	 */
	private Set<Object> _ajaxKeys = null;

	/**
	 * Internal set of row keys, encoded on ajax response rendering and cleared after response complete
	 */
	private Set<Object> _ajaxRequestKeys = null;

	private Object _ajaxRowKey = null;

	private Map<String, Object> _ajaxRowKeysMap = new HashMap<String, Object>();

	/**
	 * Get name of EL variable for component state.
	 * 
	 * @return the varState
	 */
	public String getStateVar() {
		return _stateVar;
	}

	/**
	 * @param varStatus
	 *            the varStatus to set
	 */
	public void setStateVar(String varStatus) {
		this._stateVar = varStatus;
	}

	/**
	 * @return the rowKeyVar
	 */
	public String getRowKeyVar() {
		return this._rowKeyVar;
	}

	/**
	 * @param rowKeyVar
	 *            the rowKeyVar to set
	 */
	public void setRowKeyVar(String rowKeyVar) {
		this._rowKeyVar = rowKeyVar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#getRowCount()
	 */
	public int getRowCount() {
		return getExtendedDataModel().getRowCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#getRowData()
	 */
	public Object getRowData() {
		return getExtendedDataModel().getRowData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#isRowAvailable()
	 */
	public boolean isRowAvailable() {
		return this.getExtendedDataModel().isRowAvailable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#setRowIndex(int)
	 */
	public void setRowIndex(int index) {
		FacesContext faces = FacesContext.getCurrentInstance();
		ExtendedDataModel localModel = getExtendedDataModel();
		
		boolean rowAvailable = isRowAvailable();
		
		
//		if (rowAvailable) {
			// save child state
			this.saveChildState(faces);
//		}

		// Set current model row by int, but immediately get value from model.
		// for compability, complex models must provide values map between
		// integer and key value.
		localModel.setRowIndex(index);
		
		rowAvailable = isRowAvailable();
		this._rowKey = localModel.getRowKey();
		this._clientId = null;
		
		boolean rowSelected = this._rowKey != null && rowAvailable;

		setupVariable(faces, localModel, rowSelected);
		
//		if (rowAvailable ) {
			// restore child state
			this.restoreChildState(faces);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#getRowIndex()
	 */
	public int getRowIndex() {
		return getExtendedDataModel().getRowIndex();
	}

	/**
	 * Same as for int index, but for complex model key.
	 * 
	 * @return
	 */
	public Object getRowKey() {
		return this._rowKey;
	}

	public void setRowKey(Object key) {
		setRowKey(FacesContext.getCurrentInstance(), key);
	}

	/**
	 * Setup current row by key. Perform same functionality as
	 * {@link UIData#setRowIndex(int)}, but for key object - it may be not only
	 * row number in sequence data, but, for example - path to current node in
	 * tree.
	 * 
	 * @param faces -
	 *            current FacesContext
	 * @param key
	 *            new key value.
	 */
	public void setRowKey(FacesContext faces, Object key) {
		ExtendedDataModel localModel = getExtendedDataModel();
		
		boolean rowAvailable = isRowAvailable();
		
//		if (rowAvailable) {
			// save child state
			this.saveChildState(faces);
//		}

		this._rowKey = key;
		this._clientId = null;
		
		localModel.setRowKey(key);

		rowAvailable = isRowAvailable();
		boolean rowSelected = key != null && rowAvailable;

		//XXX check for row availability
		setupVariable(faces, localModel, rowSelected);
		
//		if (rowAvailable ) {
			// restore child state
			this.restoreChildState(faces);
//		}
	}

	/**
	 * @return the rowKeyConverter
	 */
	public Converter getRowKeyConverter() {
		Converter converter = _rowKeyConverter;
		if (null == converter) {
			ValueExpression ve = getValueExpression("rowKeyConverter");
			if (null != ve) {
				converter = (Converter) ve.getValue(getFacesContext().getELContext());
			}
		}
		return converter;
	}

	/**
	 * @param rowKeyConverter the rowKeyConverter to set
	 */
	public void setRowKeyConverter(Converter rowKeyConverter) {
		_rowKeyConverter = rowKeyConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.AjaxDataEncoder#getAjaxKeys()
	 */
	@SuppressWarnings("unchecked")
	public Set<Object> getAjaxKeys() {
		Set<Object> keys = null;
		if (this._ajaxKeys != null) {
			keys = (this._ajaxKeys);
		} else {
			ValueExpression vb = getValueExpression("ajaxKeys");
			if (vb != null) {
				keys = (Set<Object>) (vb.getValue(getFacesContext().getELContext()));
			} else if (null != _ajaxRowKey) {
				// If none of above exist , use row with submitted AjaxComponent
				keys = new HashSet<Object>(1);
				keys.add(_ajaxRowKey);
			}
		}
		return keys;
	}
	
	public Set<Object> getAllAjaxKeys() {
		Set<Object> ajaxKeys = getAjaxKeys();
		
		Set<Object> allAjaxKeys = null;
		if (ajaxKeys != null) {
			allAjaxKeys = new HashSet<Object>();
			allAjaxKeys.addAll(ajaxKeys);
		}
		
		if (_ajaxRequestKeys != null) {
			if (allAjaxKeys == null) {
				allAjaxKeys = new HashSet<Object>();
			}
			
			allAjaxKeys.addAll(_ajaxRequestKeys);
		}

		return allAjaxKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.ajax.repeat.AjaxDataEncoder#setAjaxKeys(java.util.Set)
	 */
	public void setAjaxKeys(Set<Object> ajaxKeys) {
		this._ajaxKeys = ajaxKeys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.framework.ajax.AjaxChildrenEncoder#encodeAjaxChild(javax.faces.context.FacesContext,
	 *      java.lang.String, java.util.Set, java.util.Set)
	 */
	public void encodeAjaxChild(FacesContext context, String path,
			final Set<String> ids, final Set<String> renderedAreas) throws IOException {

		Renderer renderer = getRenderer(context);
		if (null != renderer && renderer instanceof AjaxChildrenRenderer) {
			// If renderer support partial encoding - call them.
			if(_log.isDebugEnabled()){
				_log.debug("Component "+getClientId(context)+" has delegated Encode children components by AjaxChildrenRenderer for path "+path);
			}
			AjaxChildrenRenderer childrenRenderer = (AjaxChildrenRenderer) renderer;
			childrenRenderer.encodeAjaxChildren(context, this, path, ids,
					renderedAreas);
		} else {
			if(_log.isDebugEnabled()){
				_log.debug("Component "+getClientId(context)+"  do Encode children components  for path "+path);
			}
			// Use simple ajax children encoding for iterate other keys.
			final AjaxChildrenRenderer childrenRenderer = getChildrenRenderer();
			final String childrenPath = path + getId()
					+ NamingContainer.SEPARATOR_CHAR;
			ComponentVisitor ajaxVisitor = new ComponentVisitor() {

				public void processComponent(FacesContext context,
						UIComponent c, Object argument) throws IOException {
					childrenRenderer.encodeAjaxComponent(context, c,
							childrenPath, ids, renderedAreas);
				}

			};
			Set<Object> ajaxKeys = getAllAjaxKeys();
			if (null != ajaxKeys) {
				if(_log.isDebugEnabled()){
					_log.debug("Component "+getClientId(context)+"  Encode children components for a keys "+ajaxKeys);
				}
				captureOrigValue();
				Object savedKey = getRowKey();
				setRowKey(context, null);
				Iterator<UIComponent> fixedChildren = fixedChildren();
				while (fixedChildren.hasNext()) {
					UIComponent component = fixedChildren.next();
					ajaxVisitor.processComponent(context, component, null);
				}
				for (Iterator<Object> iter = ajaxKeys.iterator(); iter.hasNext();) {
					Object key = iter.next();
					ajaxVisitor.process(context, key, null);
				}
				setRowKey(context,savedKey);
				restoreOrigValue(context);
			} else {
				if(_log.isDebugEnabled()){
					_log.debug("Component "+getClientId(context)+" children components  for all rows");
				}
				iterate(context, ajaxVisitor, null);
			}
		}
	}

	/**
	 * Instance of default renderer in ajax responses.
	 */
	private static final AjaxChildrenRenderer _childrenRenderer = new AjaxChildrenRenderer() {

		protected Class<? extends UIComponent> getComponentClass() {
			return UIDataAdaptor.class;
		}

	};

	/**
	 * getter for simple {@link AjaxChildrenRenderer} instance in case of ajax
	 * responses. If default renderer not support search of children for encode
	 * in ajax response, component will use this instance by default.
	 * 
	 * @return
	 */
	protected AjaxChildrenRenderer getChildrenRenderer() {
		return _childrenRenderer;
	}

	/**
	 * @return Set of values for clientId's of this component, for wich was
	 *         invoked "encode" methods.
	 */
	protected Set<String> getEncodedIds() {
		if (_encoded == null) {
			_encoded = new HashSet<String>();
		}

		return _encoded;
	}

	/**
	 * Setup EL variable for different iteration. Value of row data and
	 * component state will be put into request scope attributes with names
	 * given by "var" and "varState" bean properties.
	 * 
	 * Changed: does not check for row availability now
	 * 
	 * @param faces
	 *            current faces context
	 * @param localModel
	 * @param rowSelected
	 */
	protected void setupVariable(FacesContext faces, DataModel localModel,
			boolean rowSelected) {
		Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
		if (rowSelected/*&& isRowAvailable()*/) {
			// Current row data.
			setupVariable(getVar(), attrs, localModel.getRowData());
			// Component state variable.
			setupVariable(getStateVar(), attrs, getComponentState());
			// Row key Data variable.
			setupVariable(getRowKeyVar(), attrs, getRowKey());

		} else {
			removeVariable(getVar(), attrs);
			removeVariable(getStateVar(), attrs);
			removeVariable(getRowKeyVar(), attrs);
		}
	}

	/**
	 * @param var
	 * @param attrs
	 * @param rowData
	 */
	private void setupVariable(String var, Map<String, Object> attrs, Object rowData) {
		if (var != null) {
			attrs.put(var, rowData);
		}
	}

	/**
	 * @param var
	 * @param attrs
	 * @param rowData
	 */
	private void removeVariable(String var, Map<String, Object> attrs) {
		if (var != null) {
			attrs.remove(var);
		}
	}

	/**
	 * Reset data model. this method must be called twice per request - before
	 * decode phase and before component encoding.
	 */
	protected void resetDataModel() {
		this._currentModel = null;
		_modelsMap.clear();
	}

	/**
	 * Set data model. Model value will be stored in Map with key as current
	 * clientId for this component, to keep models between phases for same
	 * iteration in case if this component child for other UIData
	 * 
	 * @param model
	 */
	protected void setExtendedDataModel(ExtendedDataModel model) {
		this._currentModel = model;
		this._modelsMap.put(getBaseClientId(getFacesContext()), model);
	}

	/**
	 * Get current data model, or create it by {@link #createDataModel()}
	 * method. For different iterations in ancestor UIData ( if present ) will
	 * be returned different models.
	 * 
	 * @return current data model.
	 */
	protected ExtendedDataModel getExtendedDataModel() {
		if (this._currentModel == null) {
			String baseClientId = getBaseClientId(getFacesContext());
			ExtendedDataModel model;
			model = (ExtendedDataModel) this._modelsMap.get(baseClientId);
			if (null == model) {
				model = createDataModel();
				this._modelsMap.put(baseClientId, model);
			}
			this._currentModel = model;
		}
		return this._currentModel;
	}

	/**
	 * Hook mathod for create data model in concrete implementations.
	 * 
	 * @return
	 */
	protected abstract ExtendedDataModel createDataModel();

	/**
	 * Set current state ( at most cases, visual representation ) of this
	 * component. Same as for DataModel, component will keep states for
	 * different iterations.
	 * 
	 * @param state
	 */
	public void setComponentState(DataComponentState state) {
		this._currentState = state;
		this._statesMap.put(getBaseClientId(getFacesContext()),
				this._currentState);
	}

	/**
	 * @return current state of this component.
	 */
	public DataComponentState getComponentState() {
		DataComponentState state = null;
		if (this._currentState == null) {
			// Check for binding state to user bean.
			ValueExpression valueBinding = getValueExpression(UIDataAdaptor.COMPONENT_STATE_ATTRIBUTE);
			FacesContext facesContext = getFacesContext();
			ELContext elContext = facesContext.getELContext();
			if (null != valueBinding) {
				state = (DataComponentState) valueBinding
						.getValue(elContext);
				if (null == state) {
					// Create default state
					state = createComponentState();
					if (!valueBinding.isReadOnly(elContext)) {
						// Store created state in user bean.
						valueBinding.setValue(elContext, state);
					}
				}
			} else {
				// Check for stored state in map for parent iterations
				String baseClientId = getBaseClientId(facesContext);
				state = (DataComponentState) this._statesMap.get(baseClientId);
				if (null == state) {
					// Create default component state
					state = createComponentState();
					this._statesMap.put(baseClientId, state);
				}
				this._currentState = state;
			}
		} else {
			state = this._currentState;
		}
		return state;
	}

	/**
	 * Hook method for create default state in concrete implementations.
	 * 
	 * @return
	 */
	protected abstract DataComponentState createComponentState();

	private String _clientId = null;

	public String getClientId(FacesContext faces) {
		if (null == _clientId) {
			StringBuilder id = new StringBuilder(getBaseClientId(faces));
			Object rowKey = getRowKey();
			if (rowKey != null) {
				// Use converter to get String representation ot the row key.
				Converter rowKeyConverter = getRowKeyConverter();
				if(null == rowKeyConverter){
					// Create default converter for a row key.
					rowKeyConverter = faces.getApplication().createConverter(rowKey.getClass());
					// Store converter for a invokeOnComponents call.
					if(null != rowKeyConverter){
						setRowKeyConverter(rowKeyConverter);
					}
				}
				String rowKeyString;
				if (null !=rowKeyConverter) {
					// Temporary set clientId, to avoid infinite calls from converter.
					_clientId = id.toString();
					rowKeyString = rowKeyConverter.getAsString(faces, this, rowKey);
				} else {
					rowKeyString = rowKey.toString();
				}
				id.append(NamingContainer.SEPARATOR_CHAR).append(
						rowKeyString);
			}
			Renderer renderer;
			if (null != (renderer = getRenderer(faces))) {
				_clientId = renderer.convertClientId(faces, id.toString());
			} else {
				_clientId = id.toString();
			}

		}
		return _clientId;
	}

	private String _baseClientId = null;

	/**
	 * Get base clietntId of this component ( withowt iteration part )
	 * 
	 * @param faces
	 * @return
	 */
	public String getBaseClientId(FacesContext faces) {
		// Return any previously cached client identifier
		if (_baseClientId == null) {

			// Search for an ancestor that is a naming container
			UIComponent ancestorContainer = this;
			StringBuilder parentIds = new StringBuilder();
			while (null != (ancestorContainer = ancestorContainer.getParent())) {
				if (ancestorContainer instanceof NamingContainer) {
				    	String containerClientId = ancestorContainer.getContainerClientId(faces);
				    	// skip case when clientId of ancestor container is null
				    	if(containerClientId != null) {
				    	    parentIds.append(containerClientId).append(NamingContainer.SEPARATOR_CHAR);
				    	}
					break;
				}
			}
			String id = getId();
			if (null != id) {
				_baseClientId = parentIds.append(id).toString();
			} else {
				id = faces.getViewRoot().createUniqueId();
				super.setId(id);
				_baseClientId = parentIds.append(
						getId()).toString();
			}
		}
		return (_baseClientId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		// If component created by restoring tree or JSP, initial Id is null.
		boolean haveId = null != super.getId();
		String baseClientId;
//		baseClientId = haveId ? getBaseClientId(getFacesContext())
//				: null;
		super.setId(id);
		_baseClientId = null;
		_clientId = null;
		if (haveId) {
			// parent UIData ( if present ) will be set same Id at iteration
			// -
			// we use it for
			// switch to different model and state.
			// Step one - save old values.
//			this._statesMap.put(baseClientId, this._currentState);
//			this._modelsMap.put(baseClientId, this._currentModel);
//			this._ajaxRowKeysMap.put(baseClientId, this._ajaxRowKey);
			// Step two - restore values for a new clientId.
			baseClientId = getBaseClientId(getFacesContext());
			this._currentState = (DataComponentState) this._statesMap
					.get(baseClientId);
			this._currentModel = (ExtendedDataModel) this._modelsMap
					.get(baseClientId);
			if (null != this._currentModel) {
				this._rowKey = this._currentModel.getRowKey();
				// restoreChildState();
			}
			// Restore value for row with submitted AjaxComponent.
			this._ajaxRowKey = _ajaxRowKeysMap.get(baseClientId);
		}
	}

	private Object origValue;

	/**
	 * Save current state of data variable.
	 */
	public void captureOrigValue() {
		captureOrigValue(FacesContext.getCurrentInstance());
	}

	/**
	 * Save current state of data variable.
	 * 
	 * @param faces
	 *            current faces context
	 */
	public void captureOrigValue(FacesContext faces) {
		String var = getVar();
		if (var != null) {
			Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
			this.origValue = attrs.get(var);
		}
	}

	/**
	 * Restore value of data variable after processing phase.
	 */
	public void restoreOrigValue() {
		restoreOrigValue(FacesContext.getCurrentInstance());
	}

	/**
	 * Restore value of data variable after processing phase.
	 * 
	 * @param faces
	 *            current faces context
	 */
	public void restoreOrigValue(FacesContext faces) {
		String var = getVar();
		if (var != null) {
			Map<String, Object> attrs = faces.getExternalContext().getRequestMap();
			if (this.origValue != null) {
				attrs.put(var, this.origValue);
			} else {
				attrs.remove(var);
			}
		}
	}

	/**
	 * Saved values of {@link EditableValueHolder} fields per iterations.
	 */
	private Map<String, Map<String, SavedState>> childState;

	/**
	 * @param faces
	 * @return Saved values of {@link EditableValueHolder} fields per
	 *         iterations.
	 */
	protected Map<String, SavedState> getChildState(FacesContext faces) {
		if (this.childState == null) {
			this.childState = new HashMap<String, Map<String,SavedState>>();
		}
		String baseClientId = getBaseClientId(faces);
		Map<String, SavedState> currentChildState = childState.get(baseClientId);
		if (null == currentChildState) {
			currentChildState = new HashMap<String, SavedState>();
			childState.put(baseClientId, currentChildState);
		}
		return currentChildState;
	}

	/**
	 * Save values of {@link EditableValueHolder} fields before change current
	 * row.
	 * 
	 * @param faces
	 */
	protected void saveChildState(FacesContext faces) {

		Iterator<UIComponent> itr = dataChildren();
		Map<String, SavedState> childState = this.getChildState(faces);
		while (itr.hasNext()) {
			this.saveChildState(faces, (UIComponent) itr.next(), childState);
		}
	}

	/**
	 * Recursive method for Iterate on children for save
	 * {@link EditableValueHolder} fields states.
	 * 
	 * @param faces
	 * @param c
	 * @param childState
	 */
	private void saveChildState(FacesContext faces, UIComponent c,
			Map<String, SavedState> childState) {

		if (!c.isTransient() && (c instanceof EditableValueHolder||c instanceof IterationStateHolder)) {
			String clientId = c.getClientId(faces);
			SavedState ss = childState.get(clientId);
			if (ss == null) {
				ss = new SavedState();
				childState.put(clientId, ss);
			}
			if (c instanceof EditableValueHolder) {
				ss.populate((EditableValueHolder) c);
			}
			if(c instanceof IterationStateHolder){
				ss.populate((IterationStateHolder) c);
			}
		}
		// continue hack
		Iterator<UIComponent> itr = c.getChildren().iterator();
		while (itr.hasNext()) {
			saveChildState(faces, (UIComponent) itr.next(), childState);
		}
			itr = c.getFacets().values().iterator();
			while (itr.hasNext()) {
				saveChildState(faces, (UIComponent) itr.next(), childState);
			}
	}

	/**
	 * Restore values of {@link EditableValueHolder} fields after change current
	 * row.
	 * 
	 * @param faces
	 */
	protected void restoreChildState(FacesContext faces) {

		Iterator<UIComponent> itr = dataChildren();
		Map<String, SavedState> childState = this.getChildState(faces);
		while (itr.hasNext()) {
			this.restoreChildState(faces, (UIComponent) itr.next(), childState);
		}
	}

	/**
	 * Recursive part of
	 * {@link #restoreChildState(FacesContext, UIComponent, Map)}
	 * 
	 * @param faces
	 * @param c
	 * @param childState
	 * 
	 */
	private void restoreChildState(FacesContext faces, UIComponent c,
			Map<String, SavedState> childState) {
		// reset id
		String id = c.getId();
		c.setId(id);

		// hack
		if (c instanceof EditableValueHolder || c instanceof IterationStateHolder) {
			String clientId = c.getClientId(faces);
			SavedState ss = childState.get(clientId);
			if (ss == null) {
				ss=NullState;
			}
			if (c instanceof EditableValueHolder) {
				EditableValueHolder evh = (EditableValueHolder) c;
				ss.apply(evh);
			}
			if (c instanceof IterationStateHolder) {
				IterationStateHolder ish = (IterationStateHolder) c;
				ss.apply(ish);
			}
		}

		// continue hack
        for (UIComponent child : c.getChildren()) {
            restoreChildState(faces, child, childState);
        }
        for (UIComponent facet : c.getFacets().values()) {
            restoreChildState(faces, facet, childState);
        }
	}

	/**
	 * Check for validation errors on children components. If true, saved values
	 * must be keep on render phase
	 * 
	 * @param context
	 * @return
	 */
	protected boolean keepSaved(FacesContext context) {
		// For an any validation errors, children components state should be preserved
        FacesMessage.Severity sev = context.getMaximumSeverity();
		return (sev != null && (FacesMessage.SEVERITY_ERROR.compareTo(sev) >= 0));
	}

	/**
	 * Perform iteration on all children components and all data rows with given
	 * visitor.
	 * 
	 * @param faces
	 * @param visitor
	 */
	protected void iterate(FacesContext faces, ComponentVisitor visitor,
			Object argument) {

		// stop if not rendered
		if (!this.isRendered()) {
			return;
		}
		// reset rowIndex
		this.captureOrigValue(faces);
		this.setRowKey(faces, null);
		try {
			Iterator<UIComponent> fixedChildren = fixedChildren();
			while (fixedChildren.hasNext()) {
				UIComponent component = fixedChildren.next();
				visitor.processComponent(faces, component, argument);
			}

			walk(faces, visitor, argument);
		} catch (Exception e) {
			throw new FacesException(e);
		} finally {
			this.setRowKey(faces, null);
			this.restoreOrigValue(faces);
		}
	}
	
	/**
	 * Extracts segment of component client identifier containing row key
	 * 
	 * @param context current faces context
	 * @param tailId substring of component client identifier with base client identifier removed
	 * @return segment containing row key or <code>null</code>
	 */
	protected String extractKeySegment(FacesContext context, String tailId) {
		int indexOfSecondColon = tailId.indexOf(NamingContainer.SEPARATOR_CHAR);
		
		return (indexOfSecondColon > 0 ? tailId.substring(0, indexOfSecondColon) : null);
	}
	
	/**
	 * Returns iterator of components to search through 
	 * in {@link #invokeOnComponent(FacesContext, String, ContextCallback)}. 
	 * 
	 * @return
	 */
	protected Iterator<UIComponent> invocableChildren() {
		return getFacetsAndChildren();
	}
	
	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId,
			ContextCallback callback) throws FacesException {
		if( null == context || null == clientId || null == callback){
			throw new NullPointerException();
		}
		boolean found = false;
    	Object oldRowKey = getRowKey();
		String baseClientId = getBaseClientId(context);
        if (clientId.equals(baseClientId)) {
        	// This is call for a same data component.
			try {
				if (null != oldRowKey) {
					captureOrigValue(context);
					setRowKey(context,null);
				}
				callback.invokeContextCallback(context, this);
				found = true;
			} catch (Exception e) {
				throw new FacesException(e);
			} finally {
				if (null != oldRowKey) {
					try {
						setRowKey(context,oldRowKey);
						restoreOrigValue(context);
					} catch (Exception e) {
						context.getExternalContext().log(e.getMessage(), e);
					}
				}				
			}
        } else {
			String baseId = baseClientId + NamingContainer.SEPARATOR_CHAR;
			if (clientId.startsWith(baseId)) {
				Object newRowKey = null;
				// Call for a child component - try to detect row key
				String rowKeyString = extractKeySegment(context, 
						clientId.substring(baseId.length()));
				if (rowKeyString != null) {
					Converter keyConverter = getRowKeyConverter();
					if (null != keyConverter) {
						try {
							newRowKey = keyConverter.getAsObject(context, this,
									rowKeyString);
						} catch (ConverterException e) {
							// TODO: log error
						}
					}
				}
				if( null != oldRowKey || null != newRowKey){
					captureOrigValue(context);
					setRowKey(newRowKey);
				}
	            Iterator<UIComponent> itr = invocableChildren();	            
	            while (itr.hasNext() && !found) {
	                found = itr.next().invokeOnComponent(context, clientId,
	                        callback);
	            }
				if( null != oldRowKey || null != newRowKey){
					setRowKey(oldRowKey);
					restoreOrigValue(context);
				}
			}
		}

        return found;
	}

	/**
	 * Walk ( visit ) this component on all data-avare children for each row.
	 * 
	 * @param faces
	 * @param visitor
	 * @throws IOException
	 */
	public void walk(FacesContext faces, DataVisitor visitor, Object argument)
			throws IOException {
		getExtendedDataModel().walk(faces, visitor,
				getComponentState().getRange(), argument);
	}

	protected void processDecodes(FacesContext faces, Object argument) {
		if (!this.isRendered())
			return;
		this.iterate(faces, decodeVisitor, argument);
		this.decode(faces);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#processDecodes(javax.faces.context.FacesContext)
	 */
	public void processDecodes(FacesContext faces) {
		processDecodes(faces, null);
	}

	/**
	 * Reset per-request fields in component.
	 * 
	 * @param faces
	 * 
	 */
	protected void resetComponent(FacesContext faces) {
		// resetDataModel();
		if (null != this.childState) {
			childState.remove(getBaseClientId(faces));
		}
		this._encoded = null;
	}

	protected void processUpdates(FacesContext faces, Object argument) {
		if (!this.isRendered())
			return;
		this.iterate(faces, updateVisitor, argument);
		ExtendedDataModel dataModel = getExtendedDataModel();
		// If no validation errors, update values for serializable model,
		// restored from view.
		if (dataModel instanceof SerializableDataModel && (!keepSaved(faces))) {
			SerializableDataModel serializableModel = (SerializableDataModel) dataModel;
			serializableModel.update();
		}

	}

	public void processUpdates(FacesContext faces) {
		processUpdates(faces, null);
		// resetComponent(faces);
	}

	protected void processValidators(FacesContext faces, Object argument) {
		if (!this.isRendered())
			return;
		this.iterate(faces, validateVisitor, argument);
	}

	public void processValidators(FacesContext faces) {
		processValidators(faces, null);
	}

	public void encodeBegin(FacesContext context) throws IOException {
		// Mark component as used, if parent UIData change own range states not
		// accessed at
		// encode phase must be unsaved.
		getEncodedIds().add(getBaseClientId(context));
		// getComponentState().setUsed(true);
		super.encodeBegin(context);
	}

	/**
	 * This method must create iterator for all non-data avare children of this
	 * component ( header/footer facets for components and columns in dataTable,
	 * facets for tree etc.
	 * 
	 * @return iterator for all components not sensitive for row data.
	 */
	protected abstract Iterator<UIComponent> fixedChildren();

	/**
	 * This method must create iterator for all children components, processed
	 * "per row" It can be children of UIColumn in dataTable, nodes in tree
	 * 
	 * @return iterator for all components processed per row.
	 */
	protected abstract Iterator<UIComponent> dataChildren();

	private final static SavedState NullState = new SavedState();

	// from RI
	/**
	 * This class keep values of {@link EditableValueHolder} row-sensitive
	 * fields.
	 * 
	 * @author shura
	 * 
	 */
	private final static class SavedState implements Serializable {

		private Object submittedValue;
		
		private Object iterationState;

		private static final long serialVersionUID = 2920252657338389849L;

		Object getSubmittedValue() {
			return (this.submittedValue);
		}

		void setSubmittedValue(Object submittedValue) {
			this.submittedValue = submittedValue;
		}

		private boolean valid = true;

		boolean isValid() {
			return (this.valid);
		}

		void setValid(boolean valid) {
			this.valid = valid;
		}

		private Object value;

		Object getValue() {
			return (this.value);
		}

		public void setValue(Object value) {
			this.value = value;
		}

		private boolean localValueSet;

		boolean isLocalValueSet() {
			return (this.localValueSet);
		}

		public void setLocalValueSet(boolean localValueSet) {
			this.localValueSet = localValueSet;
		}

		public Object getIterationState() {
			return iterationState;
		}

		public void setIterationState(Object iterationState) {
			this.iterationState = iterationState;
		}

		public String toString() {
			return ("submittedValue: " + submittedValue + " value: " + value
					+ " localValueSet: " + localValueSet);
		}

		public void populate(EditableValueHolder evh) {
			this.value = evh.getLocalValue();
			this.valid = evh.isValid();
			this.submittedValue = evh.getSubmittedValue();
			this.localValueSet = evh.isLocalValueSet();
		}

		
		public void populate(IterationStateHolder ish) {
			this.iterationState = ish.getIterationState();
		}
		
		public void apply(EditableValueHolder evh) {
			evh.setValue(this.value);
			evh.setValid(this.valid);
			evh.setSubmittedValue(this.submittedValue);
			evh.setLocalValueSet(this.localValueSet);
		}
		
		public void apply(IterationStateHolder ish) {
			ish.setIterationState(this.iterationState);
		}

	}

	protected void addAjaxKeyEvent(FacesEvent event) {
		Object eventRowKey = getRowKey();
		if (null != eventRowKey) {
			this._ajaxRowKey = eventRowKey;
			this._ajaxRowKeysMap.put(getBaseClientId(getFacesContext()),
			eventRowKey);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIData#queueEvent(javax.faces.event.FacesEvent)
	 */
	public void queueEvent(FacesEvent event) {
		if (event.getComponent() != this) {
			// For Ajax events, keep row value.
			if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
				addAjaxKeyEvent(event);
			}
			event = new IndexedEvent(this, event, getRowKey());
		}
		// Send event directly to parent, to avoid wrapping in superclass.
		UIComponent parent = getParent();
		if (parent == null) {
			throw new IllegalStateException("No parent component for queue event");
		} else {
			parent.queueEvent(event);
		}
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		if (!(event instanceof IndexedEvent)) {
			if (!broadcastLocal(event)) {
				super.broadcast(event);
			}
			return;
		}

		// Set up the correct context and fire our wrapped event
		IndexedEvent revent = (IndexedEvent) event;
		Object oldRowKey = getRowKey();
		FacesContext faces = FacesContext.getCurrentInstance();
		captureOrigValue(faces);
		Object eventRowKey = revent.getKey();
		setRowKey(faces, eventRowKey);
		FacesEvent rowEvent = revent.getTarget();
		
		rowEvent.getComponent().broadcast(rowEvent);
		
		setRowKey(faces, oldRowKey);
		restoreOrigValue(faces);
	}

	/**
	 * Process events targetted for concrete implementation. Hook method called
	 * from {@link #broadcast(FacesEvent)}
	 * 
	 * @param event -
	 *            processed event.
	 * @return true if event processed, false if component must continue
	 *         processing.
	 */
	protected boolean broadcastLocal(FacesEvent event) {
		return false;
	}

	/**
	 * Wrapper for event from child component, with value of current row key.
	 * 
	 * @author shura
	 * 
	 */
	protected static final class IndexedEvent extends FacesEvent {

		private static final long serialVersionUID = -8318895390232552385L;

		private final FacesEvent target;

		private final Object key;

		public IndexedEvent(UIDataAdaptor owner, FacesEvent target, Object key) {
			super(owner);
			this.target = target;
			this.key = key;
		}

		public PhaseId getPhaseId() {
			return (this.target.getPhaseId());
		}

		public void setPhaseId(PhaseId phaseId) {
			this.target.setPhaseId(phaseId);
		}

		public boolean isAppropriateListener(FacesListener listener) {
			return this.target.isAppropriateListener(listener);
		}

		public void processListener(FacesListener listener) {
			UIDataAdaptor owner = (UIDataAdaptor) this.getComponent();
			Object prevIndex = owner._rowKey;
			try {
				owner.setRowKey(this.key);
				this.target.processListener(listener);
			} finally {
				owner.setRowKey(prevIndex);
			}
		}

		public Object getKey() {
			return key;
		}

		public FacesEvent getTarget() {
			return target;
		}

	}

	/**
	 * "memento" pattern class for state of component.
	 * 
	 * @author shura
	 * 
	 */
	private static class DataState implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 17070532L;

		private Object superState;

		private Map<String, PerIdState> componentStates = new HashMap<String, PerIdState>();

		private Set<Object> ajaxKeys;

		public String rowKeyVar;

		public String stateVar;

		private Map<String, Map<String, SavedState>> childStates;

		public Object rowKeyConverter;

	}

	/**
	 * Serialisable model and component state per iteration of parent UIData.
	 * 
	 * @author shura
	 * 
	 */
	private static class PerIdState implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9037454770537726418L;

		/**
		 * Flag setted to true if componentState implements StateHolder
		 */
		private boolean stateInHolder = false;

		/**
		 * Serializable componentState or
		 */
		private Object componentState;

		private SerializableDataModel model;
	}

	public void restoreState(FacesContext faces, Object object) {
		DataState state = (DataState) object;
		super.restoreState(faces, state.superState);
		this._ajaxKeys = state.ajaxKeys;
		this._statesMap = new HashMap<String, DataComponentState>();
		this._rowKeyVar = state.rowKeyVar;
		this._stateVar = state.stateVar;
		this.childState = state.childStates;
		if (null != state.rowKeyConverter) {
			this._rowKeyConverter = (Converter) restoreAttachedState(faces,
					state.rowKeyConverter);
		} 
		// Restore serializable models and component states for all rows of
		// parent UIData ( single if this
		// component not child of iterable )
		for (Iterator<Entry<String, PerIdState>> iter = state.componentStates.entrySet().iterator(); iter
				.hasNext();) {
			Entry<String, PerIdState> stateEntry = iter.next();
			PerIdState idState = stateEntry.getValue();
			DataComponentState compState;
			if (idState.stateInHolder) {
				// TODO - change RichFaces Tree component, for remove reference
				// to component from state.
				compState = createComponentState();
				((StateHolder) compState).restoreState(faces,
						idState.componentState);
			} else {
				compState = (DataComponentState) idState.componentState;
			}
			String key = stateEntry.getKey();
			this._statesMap.put(key, compState);
			this._modelsMap.put(key, idState.model);
		}
	}

	public Object saveState(FacesContext faces) {
		DataState state = new DataState();
		state.superState = super.saveState(faces);
		state.ajaxKeys = this._ajaxKeys;
		state.rowKeyVar = this._rowKeyVar;
		state.stateVar = this._stateVar;
		state.childStates = this.childState;
		if (null != this._rowKeyConverter) {
			state.rowKeyConverter = saveAttachedState(faces,this._rowKeyConverter);
		}
		Set<String> encodedIds = getEncodedIds();
		// Save all states of component and data model for all valies of
		// clientId, encoded in this request.
//		this._statesMap.put(getBaseClientId(faces), this._currentState);
//		this._modelsMap.put(getBaseClientId(faces), this._currentModel);
		for (Iterator<Entry<String, DataComponentState>> iter = this._statesMap.entrySet().iterator(); iter
				.hasNext();) {
			Entry<String, DataComponentState> stateEntry = iter.next();
			DataComponentState dataComponentState = stateEntry.getValue();
			String stateKey = stateEntry.getKey();
			if (encodedIds.isEmpty() || encodedIds.contains(stateKey)) {
				PerIdState idState = new PerIdState();
				// Save component state , depended if implemented interfaces.
				if (null == dataComponentState) {
					idState.componentState = null;
				} else {
					if (dataComponentState instanceof Serializable) {
						idState.componentState = dataComponentState;
					} else if (dataComponentState instanceof StateHolder) {
						idState.componentState = ((StateHolder) dataComponentState)
								.saveState(faces);
						idState.stateInHolder = true;
					}
					ExtendedDataModel extendedDataModel = (ExtendedDataModel) this._modelsMap
							.get(stateKey);
					if (null != extendedDataModel) {
						idState.model = extendedDataModel
								.getSerializableModel(dataComponentState
										.getRange());

					}
				}
				if (null != idState.model || null != idState.componentState) {
					state.componentStates.put(stateKey, idState);
				}
			}
		}
		return state;
	}

	public void setParent(UIComponent parent) {
		super.setParent(parent);
		this._clientId = null;
		this._baseClientId = null;
	}

	/**
	 * Adds argument key to AJAX internal request keys set
	 * @param key key to add
	 */
	public void addRequestKey(Object key) {
		if (_ajaxRequestKeys == null) {
			_ajaxRequestKeys = new HashSet<Object>();
		}

		_ajaxRequestKeys.add(key);
	}

	/**
	 * Removes argument key from AJAX internal request keys set
	 * @param key key to remove
	 */
	public void removeRequestKey(Object key) {
		if (_ajaxRequestKeys != null && key != null) {
			_ajaxRequestKeys.remove(key);
		}
	}
	
	/**
	 * Checks whether AJAX internal request keys set contains argument key
	 * @param key key to check
	 * @return <code>true</code> if set contains key, <code>false</code> - otherwise
	 */
	public boolean containsRequestKey(Object key) {
		if (_ajaxRequestKeys != null && key != null) {
			return _ajaxRequestKeys.contains(key);
		}
		
		return false;
	}
	
	/**
	 * Clears AJAX internal request keys set
	 */
	public void clearRequestKeysSet() {
		_ajaxRequestKeys = null;
	}
	
	public Object getValue() {
		return super.getValue();
	}
	
	public void setValue(Object value) {
		setExtendedDataModel(null);
		super.setValue(value);
	}
	
	public void beforeRenderResponse(FacesContext context) {
		resetDataModel();
		this._encoded = null;
		if (null != childState && !keepSaved(context)) {
			childState.clear();
		}
	}
}
