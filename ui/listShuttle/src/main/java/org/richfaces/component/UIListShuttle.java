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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;

import org.ajax4jsf.Messages;
import org.ajax4jsf.model.DataVisitor;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.model.ListShuttleDataModel;
import org.richfaces.model.ListShuttleRowKey;

/**
 * JSF component class
 * 
 */
public abstract class UIListShuttle extends UIOrderingBaseComponent {

	public static final String COMPONENT_TYPE = "org.richfaces.ListShuttle";

	public static final String COMPONENT_FAMILY = "org.richfaces.ListShuttle";

	protected static final class SubmittedValue implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5655312942714191981L;
		//ListShuttleRowKey -> ListShuttleRowKey
		private Map map = null;

		private Set sourceSelection;
		private Set targetSelection;
		
		private Object activeItem;
		
		private boolean _null = false;
		
		public SubmittedValue(Map map, Set sourceSelection, Set targetSelection, Object activeItem) {
			this.map = map;
		
			this.sourceSelection = sourceSelection;
			this.targetSelection = targetSelection;

			this.activeItem = activeItem;
		}

		public void setNull() {
			_null = true;
		}
		
		public boolean isNull() {
			return _null;
		}
		
		public void resetDataModel() {
			if (_null) {
				this.map = null;
			}
		}
		
		protected Map getMap() {
			return this.map;
		}
	}

	public static final class ValueHolder implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2124352131407581704L;

		private Object sourceValue;
		private boolean sourceValueSet;

		private Object targetValue;
		private boolean targetValueSet;

		private Set sourceSelection;
		private boolean sourceSelectionSet;
		
		private Set targetSelection;
		private boolean targetSelectionSet;

		private Object activeItem;
		private boolean activeItemSet;

		private boolean collectionIsEmpty(Collection collection) {
			return collection == null || collection.isEmpty();
		}
		
		private Object saveBoolean(boolean b) {
			return b ? Boolean.TRUE : Boolean.FALSE;
		}
		
		public boolean isTransient() {
			//TODO null collection == [] ?
			return sourceValue == null && !sourceValueSet && targetValue == null && !targetValueSet && 
			collectionIsEmpty(sourceSelection) && !sourceSelectionSet && 
			collectionIsEmpty(targetSelection) && !targetSelectionSet &&
			activeItem == null && !activeItemSet;
		}

		public void restoreState(FacesContext context, UIListShuttle list, Object _state) {
			Object[] state = (Object[]) _state;

			sourceValue = restoreAttachedState(context, state[0]);
			sourceValueSet = Boolean.TRUE.equals(state[1]);

			targetValue = restoreAttachedState(context, state[2]);
			targetValueSet = Boolean.TRUE.equals(state[3]);

			sourceSelection = (Set) restoreAttachedState(context, state[4]);
			sourceSelectionSet = Boolean.TRUE.equals(state[5]);

			targetSelection = (Set) restoreAttachedState(context, state[6]);
			targetSelectionSet = Boolean.TRUE.equals(state[7]);

			activeItem = restoreAttachedState(context, state[8]);
			activeItemSet = Boolean.TRUE.equals(state[9]);
		}

		public Object saveState(FacesContext context, final UIListShuttle list) {
//			final HashSet sourceSelectionKeySet = new HashSet();
//			final HashSet targetSelectionKeySet = new HashSet();
//			final Object[] activeItemKeys = new Object[1];
//			
//			Object rowKey = list.getRowKey();
//			try {
//				list.walk(context, new DataVisitor() {
//
//					public void process(FacesContext context, Object rowKey,
//							Object argument) throws IOException {
//
//						list.setRowKey(context, rowKey);
//						Object data = list.getRowData();
//						
//						if (data != null) {
//							if (data.equals(activeItem)) {
//								activeItemKeys[0] = rowKey;
//							}
//							
//							if (sourceSelection != null && sourceSelection.contains(data)) {
//								sourceSelectionKeySet.add(rowKey);
//							} else if (targetSelection != null && targetSelection.contains(data)){
//								targetSelectionKeySet.add(rowKey);
//							}
//						}
//					}
//					
//				}, null);
//			} catch (IOException e) {
//				throw new FacesException(e.getLocalizedMessage(), e);
//			}

			Object[] state = new Object[10];

			state[0] = saveAttachedState(context, sourceValue);
			state[1] = sourceValueSet ? Boolean.TRUE : Boolean.FALSE;

			state[2] = saveAttachedState(context, targetValue);
			state[3] = targetValueSet ? Boolean.TRUE : Boolean.FALSE;

			state[4] = saveAttachedState(context, sourceSelection);
			state[5] = sourceSelectionSet ? Boolean.TRUE : Boolean.FALSE;

			state[6] = saveAttachedState(context, targetSelection);
			state[7] = targetSelectionSet ? Boolean.TRUE : Boolean.FALSE;

			state[8] = saveAttachedState(context, activeItem);
			state[9] = activeItemSet ? Boolean.TRUE : Boolean.FALSE;

			return state;
		}

		public void setTransient(boolean newTransientValue) {
			if (newTransientValue) {
				throw new IllegalArgumentException();
			}
		}
	}

	private transient SubmittedValue submittedValueHolder = null;

	private ValueHolder valueHolder;
	
	protected void processDecodes(FacesContext faces, Object argument) {
		if (!this.isRendered())
			return;
		this.decode(faces);

		SubmittedValue submittedValue = UIListShuttle.this.submittedValueHolder;
		if (submittedValue != null) {
			Object modelSourceValue = getSourceValue();
			Object modelTargetValue = getTargetValue();
			
			Iterator iterator = submittedValue.map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				Object value = entry.getValue();
				
				if (!isSuitableValue(modelSourceValue, value) && !isSuitableValue(modelTargetValue, value)) {
					String messageText = Messages.getMessage(
							Messages.INVALID_VALUE, MessageUtil.getLabel(faces, this), value);
					
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, null);
					faces.addMessage(this.getClientId(faces), message);

					setValid(false);
					break;
				}
			}
		}

        this.iterate(faces, decodeVisitor, argument);

		if (isImmediate()) {
			executeValidate(faces);
		}
		
        if (!isValid()) {
            faces.renderResponse();
        }
	}

	private final class ModelItemState implements ItemState {
		private Collection sourceSelectedItems;
		private Collection targetSelectedItems;
		private Object activeItem;

		public ModelItemState(Collection sourceSelectedItems, Collection targetSelectedItems, Object activeItem) {
			super();
			this.sourceSelectedItems = sourceSelectedItems;
			this.targetSelectedItems = targetSelectedItems;
			this.activeItem = activeItem;
		}

		public boolean isSelected() {
			Object rowData = getRowData();
			return ((sourceSelectedItems != null && sourceSelectedItems.contains(rowData)) || 
			(targetSelectedItems != null && targetSelectedItems.contains(rowData)));
		}

		public boolean isActive() {
			return activeItem != null && activeItem.equals(getRowData());
		}
	}

	public Object saveState(FacesContext context) {
		Object[] state = new Object[2];

		state[0] = super.saveState(context);

		if (this.valueHolder != null) {
			state[1] = this.valueHolder.saveState(context, this);
		}
		return state;
	}

	public void restoreState(FacesContext context, Object object) {
		Object[] state = (Object[]) object;

		super.restoreState(context, state[0]);
		if (state[1] != null) {
			this.valueHolder = new ValueHolder();
			this.valueHolder.restoreState(context, this, state[1]);
		}
	}

	public Object getSourceValue() {
		if (valueHolder != null && valueHolder.sourceValue != null) {
			return valueHolder.sourceValue;
		}

		ValueBinding vb = getValueBinding("sourceValue");
		if (vb != null) {
			return vb.getValue(FacesContext.getCurrentInstance());
		}

		return null;
	}

	public void setSourceValue(Object sourceValue) {
		setExtendedDataModel(null);
		createValueHolder();
		valueHolder.sourceValue = sourceValue;
		valueHolder.sourceValueSet = true;
	}

	public Object getTargetValue() {
		if (valueHolder != null && valueHolder.targetValue != null) {
			return valueHolder.targetValue;
		}

		ValueBinding vb = getValueBinding("targetValue");
		if (vb != null) {
			return vb.getValue(FacesContext.getCurrentInstance());
		}

		return null;
	}

	public void setTargetValue(Object targetValue) {
		setExtendedDataModel(null);
		createValueHolder();
		valueHolder.targetValue = targetValue;
		valueHolder.targetValueSet = true;
	}

	public void setSubmittedStrings(Map map, Set sourceSelection, Set targetSelection, Object activeItem) {
		this.submittedValueHolder = new SubmittedValue(map, sourceSelection, targetSelection, activeItem);
	}

	public Object getSubmittedValue() {
		return submittedValueHolder;
	}

	public void setSubmittedValue(Object object) {
		this.submittedValueHolder = (SubmittedValue) object;
	}
	
	protected void restoreIterationSubmittedState(Object object) {
		this.submittedValueHolder = (SubmittedValue) object;
	}

	public org.ajax4jsf.model.ExtendedDataModel createDataModel() {
		Map source = null;
		
		if (submittedValueHolder != null) {
			source = submittedValueHolder.map;
		}
		
		if (source != null) {
			ListShuttleDataModel dataModel = new ListShuttleDataModel();
			dataModel.setWrappedData(source);
			
			return dataModel;
		}
		
		DataModel sourceDataModel = createDataModel(getSourceValue());
		DataModel targetDataModel = createDataModel(getTargetValue());

		ListShuttleDataModel dataModel = new ListShuttleDataModel();
		dataModel.setWrappedData(new DataModel[]{sourceDataModel, targetDataModel});
		return dataModel;
	}

	public void addValueChangeListener(ValueChangeListener listener) {
		addFacesListener(listener);
	}

	public abstract MethodBinding getValueChangeListener();
	public abstract void setValueChangeListener(MethodBinding valueChangeMethod);

	public ValueChangeListener[] getValueChangeListeners() {
		return (ValueChangeListener[]) getFacesListeners(ValueChangeListener.class);
	}

	public void removeValueChangeListener(ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	protected final UpdateModelCommand updateTargetSelectionCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.targetSelectionSet) {
				ValueBinding vb = getValueBinding("targetSelection");
				if (vb != null) {
					vb.setValue(context, valueHolder.targetSelection);
					valueHolder.targetSelection = null;
					valueHolder.targetSelectionSet = false;
				}
			}
		}
		
	};
	
	protected final UpdateModelCommand updateActiveItemCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.activeItemSet) {
				ValueBinding vb = getValueBinding("activeItem");
				if (vb != null) {
					vb.setValue(context, valueHolder.activeItem);
					valueHolder.activeItem = null;
					valueHolder.activeItemSet = false;
				}
			}
		}
		
	};

	protected final UpdateModelCommand updateSourceSelectionCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.sourceSelectionSet) {
				ValueBinding vb = getValueBinding("sourceSelection");
				if (vb != null) {
					vb.setValue(context, valueHolder.sourceSelection);
					valueHolder.sourceSelection = null;
					valueHolder.sourceSelectionSet = false;
				}
			}
		}
		
	};

	private final UpdateModelCommand updateSourceCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.sourceValueSet) {
				ValueBinding vb = getValueBinding("sourceValue");
				if (vb != null) {
					vb.setValue(context, valueHolder.sourceValue);
					valueHolder.sourceValue = null;
					valueHolder.sourceValueSet = false;
				}
			}
		}

	};

	private final UpdateModelCommand updateTargetCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.targetValueSet) {
				ValueBinding vb = getValueBinding("targetValue");
				if (vb != null) {
					vb.setValue(context, valueHolder.targetValue);
					valueHolder.targetValue = null;
					valueHolder.targetValueSet = false;
				}
			}
		}

	};

	/**
	 * <p>Perform the following algorithm to update the model data
	 * associated with this {@link UIInput}, if any, as appropriate.</p>
	 * <ul>
	 * <li>If the <code>valid</code> property of this component is
	 *     <code>false</code>, take no further action.</li>
	 * <li>If the <code>localValueSet</code> property of this component is
	 *     <code>false</code>, take no further action.</li>
	 * <li>If no {@link ValueBinding} for <code>value</code> exists,
	 *     take no further action.</li>
	 * <li>Call <code>setValue()</code> method of the {@link ValueBinding}
	 *      to update the value that the {@link ValueBinding} points at.</li>
	 * <li>If the <code>setValue()</code> method returns successfully:
	 *     <ul>
	 *     <li>Clear the local value of this {@link UIInput}.</li>
	 *     <li>Set the <code>localValueSet</code> property of this
	 *         {@link UIInput} to false.</li>
	 *     </ul></li>
	 * <li>If the <code>setValue()</code> method call fails:
	 *     <ul>
	 *     <li>Enqueue an error message by calling <code>addMessage()</code>
	 *         on the specified {@link FacesContext} instance.</li>
	 *     <li>Set the <code>valid</code> property of this {@link UIInput}
	 *         to <code>false</code>.</li>
	 *     </ul></li>
	 * </ul>
	 *
	 * @param context {@link FacesContext} for the request we are processing
	 *
	 * @exception NullPointerException if <code>context</code>
	 *  is <code>null</code>
	 */
	public void updateModel(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		if (!isValid()) {
			return;
		}

		if (valueHolder != null) {
			updateModel(context, updateActiveItemCommand);
			updateModel(context, updateSourceSelectionCommand);
			updateModel(context, updateTargetSelectionCommand);
			updateModel(context, updateSourceCommand);
			updateModel(context, updateTargetCommand);
		}
	}


	// ------------------------------------------------------ Validation Methods


	/**
	 * <p>Perform the following algorithm to validate the local value of
	 * this {@link UIInput}.</p>
	 * <ul>
	 * <li>Retrieve the submitted value with <code>getSubmittedValue()</code>.
	 *   If this returns null, exit without further processing.  (This
	 *   indicates that no value was submitted for this component.)</li>
	 *
	 * <li> Convert the submitted value into a "local value" of the
	 * appropriate data type by calling {@link #getConvertedValue}.</li>
	 *
	 * <li>Validate the property by calling {@link #validateValue}.</li>
	 *
	 * <li>If the <code>valid</code> property of this component is still
	 *     <code>true</code>, retrieve the previous value of the component
	 *     (with <code>getValue()</code>), store the new local value using
	 *     <code>setValue()</code>, and reset the submitted value to 
	 *     null.  If the local value is different from
	 *     the previous value of this component, fire a
	 *     {@link ValueChangeEvent} to be broadcast to all interested
	 *     listeners.</li>
	 * </ul>
	 *
	 * <p>Application components implementing {@link UIInput} that wish to
	 * perform validation with logic embedded in the component should perform
	 * their own correctness checks, and then call the
	 * <code>super.validate()</code> method to perform the standard
	 * processing described above.</p>
	 *
	 * @param context The {@link FacesContext} for the current request
	 *
	 * @exception NullPointerException if <code>context</code>
	 *  is null
	 */
	public void validate(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		// Submitted value == null means "the component was not submitted
		// at all";  validation should not continue
		if (submittedValueHolder == null) {
			return;
		}

		Object oldSourceValue = getSourceValue();
		Object newSourceValue = oldSourceValue;

		Object oldTargetValue = getTargetValue();
		Object newTargetValue = oldTargetValue;

		try {
			final ArrayList sourceList = new ArrayList();
			final ArrayList targetList = new ArrayList();

			try {
				
				Object key = getRowKey();
				captureOrigValue(context);
				
				walk(context, new DataVisitor() {

					public void process(FacesContext context, Object rowKey,
							Object argument) throws IOException {

						setRowKey(context, rowKey);
						
						ListShuttleRowKey listShuttleRowKey = (ListShuttleRowKey) rowKey;
						if (listShuttleRowKey.isFacadeSource()) {
							sourceList.add(getRowData());
						} else {
							targetList.add(getRowData());
						}
					}
				}, null);

				setRowKey(key);
				restoreOrigValue(context);

			} catch (IOException e) {
				throw new ConverterException(e.getLocalizedMessage(), e);
			}
			
			newSourceValue = createContainer(sourceList, oldSourceValue);
			newTargetValue = createContainer(targetList, oldTargetValue);
		}
		catch (ConverterException ce) {
			Object submittedValue = submittedValueHolder;
			//addConversionErrorMessage(context, ce, submittedValue);
			setValid(false);
		}	

		validateValue(context, new Object[] {newSourceValue, newTargetValue});

		// If our value is valid, store the new value, erase the
		// "submitted" value, and emit a ValueChangeEvent if appropriate
		if (isValid()) {
			setSourceSelection(submittedValueHolder.sourceSelection);
			setTargetSelection(submittedValueHolder.targetSelection);
			setActiveItem(submittedValueHolder.activeItem);

			Object previousSource = getSourceValue();
			Object previousTarget = getTargetValue();

			setSourceValue(newSourceValue);
			setTargetValue(newTargetValue);

			if (compareValues(previousSource, newSourceValue) || compareValues(previousTarget, newTargetValue)) {
				queueEvent(new ValueChangeEvent(this, 
						new Object[]{previousSource,previousTarget}, 
						new Object[]{newSourceValue, newTargetValue}));
			}

			this.submittedValueHolder.setNull();
		}
	}

	protected void validateValue(FacesContext context, Object value) {
	    Object[] values = (Object[]) value;

	    Object sourceValue = values[0];
	    Object targetValue = values[1];
	    
	    boolean sourceValueEmpty = isEmpty(sourceValue);
	    boolean targetValueEmpty = isEmpty(targetValue);
	    
	    if (isValid() && sourceValueEmpty && isSourceRequired()) {
		    requiredInvalidate(context);
	    }

	    if (isValid() && targetValueEmpty && isTargetRequired()) {
		    requiredInvalidate(context);
	    }
	    
	    if (isValid() && (!sourceValueEmpty || !targetValueEmpty)) {
		    super.validateValue(context, value);
	    }
	}
	
	protected void resetDataModel() {
		super.resetDataModel();

		if (submittedValueHolder != null) {
			submittedValueHolder.resetDataModel();
		}
	}

	public ItemState getItemState() {
		if (submittedValueHolder != null && !submittedValueHolder.isNull()) {
			return new ModelItemState(submittedValueHolder.sourceSelection, submittedValueHolder.targetSelection, 
					submittedValueHolder.activeItem);
		} else {
			return new ModelItemState(getSourceSelection(), getTargetSelection(), getActiveItem());
		}
	}

	public abstract String getControlsVerticalAlign();
	public abstract void setControlsVerticalAlign(String controlsVerticalAlign);
	
	public abstract boolean isOrderControlsVisible();
	public abstract void setOrderControlsVisible(boolean visible);
	
	public abstract boolean isFastOrderControlsVisible();
	public abstract void setFastOrderControlsVisible(boolean visible);

	public abstract boolean isMoveControlsVisible();
	public abstract void setMoveControlsVisible(boolean visible);
	
	public abstract boolean isFastMoveControlsVisible();
	public abstract void setFastMoveControlsVisible(boolean visible);
	
	public abstract String getListClass();
	public abstract void setListClass(String listClass);
	
	public abstract boolean isSourceRequired();
	public abstract void setSourceRequired(boolean sourceRequired); 
	
	public abstract boolean isTargetRequired();
	public abstract void setTargetRequired(boolean targetRequired);

	private void createValueHolder() {
		if (valueHolder == null) {
			valueHolder = new ValueHolder();
		}
	}
	
	public Set getSourceSelection() {
		if (valueHolder != null && valueHolder.sourceSelection != null) {
			return valueHolder.sourceSelection;
		} else {
			ValueBinding vb = getValueBinding("sourceSelection");
			if (vb != null) {
				return (Set) vb.getValue(FacesContext.getCurrentInstance());
			}
		}
		
		return null;
	}
	
	public void setSourceSelection(Set collection) {
		createValueHolder();
		valueHolder.sourceSelection = collection;
		valueHolder.sourceSelectionSet = true;
	}

	public Set getTargetSelection() {
		if (valueHolder != null && valueHolder.targetSelection != null) {
			return valueHolder.targetSelection;
		} else {
			ValueBinding vb = getValueBinding("targetSelection");
			if (vb != null) {
				return (Set) vb.getValue(FacesContext.getCurrentInstance());
			}
		}
		
		return null;
	}
	
	public void setTargetSelection(Set collection) {
		createValueHolder();
		valueHolder.targetSelection = collection;
		valueHolder.targetSelectionSet = true;
	}
	
	public Object getActiveItem() {
		if (valueHolder != null && valueHolder.activeItem != null) {
			return valueHolder.activeItem;
		} else {
			ValueBinding vb = getValueBinding("activeItem");
			if (vb != null) {
				return vb.getValue(FacesContext.getCurrentInstance());
			}
		}
		
		return null;
	}
	
	public void setActiveItem(Object activeItem) {
		createValueHolder();
		valueHolder.activeItem = activeItem;
		valueHolder.activeItemSet = true;
	}
	
	public abstract String getSourceCaptionLabel();
	public abstract void setSourceCaptionLabel(String label);
	
	public abstract String getTargetCaptionLabel();
	public abstract void setTargetCaptionLabel(String label);

	public ValueBinding getValueBinding(String name) {
		if ("value".equals(name)) {
			return super.getValueBinding("sourceValue");
		}
		
		return super.getValueBinding(name);
	}
	
	@Override
	public ValueExpression getValueExpression(String name) {
		if ("value".equals(name)) {
			return super.getValueExpression("sourceValue");
		}

		return super.getValueExpression(name);
	}
	
	public void setValue(Object value) {
		this.valueHolder = (ValueHolder) value;
	}
	
	public Object getLocalValue() {
		return this.valueHolder;
	}
}
