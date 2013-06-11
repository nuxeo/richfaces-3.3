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
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;

import org.ajax4jsf.Messages;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.SequenceDataModel;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.model.OrderingListDataModel;

public abstract class UIOrderingList extends UIOrderingBaseComponent {

	protected void processDecodes(FacesContext faces, Object argument) {
		if (!this.isRendered())
			return;
		this.decode(faces);

		SubmittedValue submittedValue = UIOrderingList.this.submittedValueHolder;
		if (submittedValue != null) {
			Object modelValue = getValue();
			Iterator iterator = submittedValue.dataMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				Object value = entry.getValue();
				
				if (!isSuitableValue(modelValue, value)) {
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

	private ValueHolder valueHolder;

	protected static final class SubmittedValue implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5860506816451180551L;
		
		private Map dataMap;
		private Set selection;
		private Object activeItem;
		
		private boolean _null = false;

		public SubmittedValue(Map dataMap, Set selection, Object activeItem) {
			this.dataMap = dataMap;
			this.selection = selection;
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
				this.dataMap = null;
			}
		}
	}

	private final class ModelItemState implements ItemState {
		private Collection selectedItems;
		private Object activeItem;

		public ModelItemState(Collection selectedItems, Object activeItem) {
			super();
			this.selectedItems = selectedItems;
			this.activeItem = activeItem;
		}

		public boolean isSelected() {
			return selectedItems != null && selectedItems.contains(getRowData());
		}

		public boolean isActive() {
			return activeItem != null && activeItem.equals(getRowData());
		}
	}

	protected ExtendedDataModel createDataModel() {
		Map modelMap = null;

		if (submittedValueHolder != null) {
			modelMap = submittedValueHolder.dataMap;
		}
		
		if (modelMap != null) {
			OrderingListDataModel dataModel = new OrderingListDataModel();
			dataModel.setWrappedData(modelMap);
			return dataModel;
		} else {
			DataModel dataModel = createDataModel(getValue());
			return new SequenceDataModel(dataModel);
		}
	}

	private transient SubmittedValue submittedValueHolder = null;
	
	public final static class ValueHolder implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4216115242421460529L;
		
		private Object value;
		
		private Set selection;
		private boolean selectionSet;

		private Object activeItem;
		private boolean activeItemSet;

		public boolean isTransient() {
			//TODO null collection == [] ?
			return value == null && (selection == null || selection.isEmpty()) && !selectionSet && 
			activeItem == null && !activeItemSet;
		}

		public void restoreState(FacesContext context, UIOrderingList list, Object _state) {
			Object[] state = (Object[]) _state;
			
			value = restoreAttachedState(context, state[0]);
			
			selection = (Set) restoreAttachedState(context, state[1]);
			selectionSet = Boolean.TRUE.equals(state[2]);
			
			activeItem = restoreAttachedState(context, state[3]);
			activeItemSet = Boolean.TRUE.equals(state[4]);
		}

		public Object saveState(FacesContext context, final UIOrderingList list) {
//			Object rowKey = list.getRowKey();
//
//			final HashSet selectionKeySet = new HashSet();
//			final Object[] activeItemSet = new Object[1];
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
//								activeItemSet[0] = rowKey;
//							}
//							
//							if (selection != null && selection.contains(data)) {
//								selectionKeySet.add(rowKey);
//							}
//						}
//					}
//					
//				}, null);
//			} catch (IOException e) {
//				throw new FacesException(e.getLocalizedMessage(), e);
//			}

			Object[] state = new Object[5];
			
			state[0] = saveAttachedState(context, value);
			state[1] = saveAttachedState(context, selection);
			state[2] = this.selectionSet ? Boolean.TRUE : Boolean.FALSE;
			
			state[3] = saveAttachedState(context, activeItem);
			state[4] = this.activeItemSet ? Boolean.TRUE : Boolean.FALSE;

			return state;
		}

		public void setTransient(boolean newTransientValue) {
			if (newTransientValue) {
				throw new IllegalArgumentException();
			}
		}
	}

	public void addValueChangeListener(ValueChangeListener listener) {
		addFacesListener(listener);
	}


	public abstract MethodBinding getValueChangeListener();

	public ValueChangeListener[] getValueChangeListeners() {
		return (ValueChangeListener[]) getFacesListeners(ValueChangeListener.class);
	}

	public abstract boolean isImmediate();

	public abstract boolean isRequired();

	public abstract boolean isValid();


	public void removeValueChangeListener(ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	public void setSubmittedString(Map submittedString, Set selection, Object activeItem) {
		this.submittedValueHolder = new SubmittedValue(submittedString, selection, activeItem);
	}

	public Object getSubmittedValue() {
		return submittedValueHolder;
	}

	public void setSubmittedValue(Object object) {
		this.submittedValueHolder = (SubmittedValue) object;
	}

	protected Object saveIterationState() {
		return valueHolder;
	}

	protected void restoreIterationState(Object object) {
		this.valueHolder = (ValueHolder) object;
	}

	public abstract void setImmediate(boolean immediate);

	public abstract void setRequired(boolean required);

	public abstract void setValid(boolean valid);

	public abstract void setValueChangeListener(MethodBinding valueChangeMethod);

	public abstract Converter getConverter();

	public abstract void setConverter(Converter converter);

	/**
	 * <p>Specialized decode behavior on top of that provided by the
	 * superclass.  In addition to the standard
	 * <code>processDecodes</code> behavior inherited from {@link
	 * UIComponentBase}, calls <code>validate()</code> if the the
	 * <code>immediate</code> property is true; if the component is
	 * invalid afterwards or a <code>RuntimeException</code> is thrown,
	 * calls {@link FacesContext#renderResponse}.  </p>
	 * @exception NullPointerException {@inheritDoc}     
	 */ 
	public void processDecodes(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		// Skip processing if our rendered flag is false
		if (!isRendered()) {
			return;
		}

		super.processDecodes(context);
	}

	/**
	 * <p>In addition to the standard <code>processValidators</code> behavior
	 * inherited from {@link UIComponentBase}, calls <code>validate()</code>
	 * if the <code>immediate</code> property is false (which is the 
	 * default);  if the component is invalid afterwards, calls
	 * {@link FacesContext#renderResponse}.
	 * If a <code>RuntimeException</code> is thrown during
	 * validation processing, calls {@link FacesContext#renderResponse}
	 * and re-throw the exception.
	 * </p>
	 * @exception NullPointerException {@inheritDoc}    
	 */ 
	public void processValidators(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		// Skip processing if our rendered flag is false
		if (!isRendered()) {
			return;
		}

		super.processValidators(context);
	}

	/**
	 * <p>In addition to the standard <code>processUpdates</code> behavior
	 * inherited from {@link UIComponentBase}, calls
	 * <code>updateModel()</code>.
	 * If the component is invalid afterwards, calls
	 * {@link FacesContext#renderResponse}.
	 * If a <code>RuntimeException</code> is thrown during
	 * update processing, calls {@link FacesContext#renderResponse}
	 * and re-throw the exception.
	 * </p>
	 * @exception NullPointerException {@inheritDoc}     
	 */ 
	public void processUpdates(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		// Skip processing if our rendered flag is false
		if (!isRendered()) {
			return;
		}

		super.processUpdates(context);
	}

	/**
	 * <p>In addition to to the default {@link UIComponent#broadcast}
	 * processing, pass the {@link ValueChangeEvent} being broadcast to the
	 * method referenced by <code>valueChangeListener</code> (if any).</p>
	 *
	 * @param event {@link FacesEvent} to be broadcast
	 *
	 * @exception AbortProcessingException Signal the JavaServer Faces
	 *  implementation that no further processing on the current event
	 *  should be performed
	 * @exception IllegalArgumentException if the implementation class
	 *  of this {@link FacesEvent} is not supported by this component
	 * @exception NullPointerException if <code>event</code> is
	 * <code>null</code>
	 */
	public void broadcast(FacesEvent event)
	throws AbortProcessingException {

		// Perform standard superclass processing
		super.broadcast(event);

		if (event instanceof ValueChangeEvent) {
			MethodBinding method = getValueChangeListener();
			if (method != null) {
				FacesContext context = getFacesContext();
				method.invoke(context, new Object[] { event });
			}
		}

	}

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

	protected final UpdateModelCommand updateSelectionCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (valueHolder.selectionSet) {
				ValueBinding vb = getValueBinding("selection");
				if (vb != null) {
					vb.setValue(context, valueHolder.selection);
					valueHolder.selection = null;
					valueHolder.selectionSet = false;
				}
			}
		}
		
	};
	
	protected final UpdateModelCommand updateValueCommand = new UpdateModelCommand() {

		public void execute(FacesContext context) {
			if (isLocalValueSet() && valueHolder != null) {
				ValueBinding vb = getValueBinding("value");
				if (vb != null) {
					vb.setValue(context, valueHolder.value);
					setValue(null);
					setLocalValueSet(false);
				}
			}
		}
		
	};
	
	/**
	 * <p>Perform the following algorithm to update the model dataMap
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
			updateModel(context, updateValueCommand);
			updateModel(context, updateSelectionCommand);
			updateModel(context, updateActiveItemCommand);
		}
	}

	protected void validateValue(FacesContext context, Object newValue) {
		if (isValid()) {
			if (isEmpty(newValue)) {
				if (isRequired()) {
					requiredInvalidate(context);
				}
			} else {
				super.validateValue(context, newValue);
			}
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
	 * appropriate dataMap type by calling {@link #getConvertedValue}.</li>
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

//		http://jira.jboss.com/jira/browse/RF-3852 

		Object previousValue = getValue();
		Object newValue = null;

		try {
			if (previousValue == null) {
				previousValue = Collections.EMPTY_LIST;
			}

			try {
				final ArrayList list = new ArrayList(getRowCount());
				
				Object key = getRowKey();
				captureOrigValue(context);
				
				walk(context, new DataVisitor() {
					public void process(FacesContext context, Object rowKey,
							Object argument) throws IOException {

						setRowKey(context, rowKey);
						list.add(getRowData());
					}

				}, null);

				setRowKey(key);
				restoreOrigValue(context);

				newValue = createContainer(list, previousValue);
			} catch (IOException e) {
				throw new ConverterException(e.getLocalizedMessage(), e);
			}
		}
		catch (ConverterException ce) {
			Object submittedValue = submittedValueHolder;
			addConversionErrorMessage(context, ce, submittedValue);
			setValid(false);
		}	

		validateValue(context, newValue);
		
		if (submittedValueHolder == null) {
			return;
		}

		// If our value is valid, store the new value, erase the
		// "submitted" value, and emit a ValueChangeEvent if appropriate
		if (isValid()) {

			setSelection(submittedValueHolder.selection);

			setActiveItem(submittedValueHolder.activeItem);
			
			setValue(newValue);

			if (compareValues(previousValue, newValue)) {
				queueEvent(new ValueChangeEvent(this, previousValue, newValue));
			}

			this.submittedValueHolder.setNull();
		}
	}

	protected void resetDataModel() {
		super.resetDataModel();

		if (this.submittedValueHolder != null) {
			this.submittedValueHolder.resetDataModel();
		}
	}

	public ItemState getItemState() {
		if (submittedValueHolder != null && !submittedValueHolder.isNull()) {
			return new ModelItemState(submittedValueHolder.selection, 
					submittedValueHolder.activeItem);
		} else {
			return new ModelItemState(getSelection(), getActiveItem());
		}
	}

	public abstract String getControlsType();
	public abstract void setControlsType(String type);

	public Set getSelection() {
		if (valueHolder != null && valueHolder.selection != null) {
			return valueHolder.selection;
		} else {
			ValueBinding vb = getValueBinding("selection");
			if (vb != null) {
				return (Set) vb.getValue(FacesContext.getCurrentInstance());
			}
		}
		
		return null;
	}
	
	public void setSelection(Set selection) {
		createValueHolder();
		valueHolder.selection = selection;
		valueHolder.selectionSet = true;
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
	
	private void createValueHolder() {
		if (valueHolder == null) {
			valueHolder = new ValueHolder();
		}
	}

	public void setValue(Object value) {
		if (value instanceof ValueHolder) {
			this.valueHolder = (ValueHolder) value;
		} else {
			createValueHolder();
			valueHolder.value = value;
			setLocalValueSet(true);
		}
	}

	public Object getLocalValue() {
		return valueHolder;
	}
	
	public Object getValue() {
		if (valueHolder != null && valueHolder.value != null) {
			return valueHolder.value;
		}
		ValueBinding ve = getValueBinding("value");
		if (ve != null) {
			return (ve.getValue(getFacesContext()));
		} else {
			return (null);
		}
	}

	public Object saveState(FacesContext faces) {
		Object[] state = new Object[2];
		state[0] = super.saveState(faces);
		if (this.valueHolder != null) {
			state[1] = this.valueHolder.saveState(faces, this);
		}
		return state;
	}
	
	public void restoreState(FacesContext faces, Object object) {
		Object[] state = (Object[]) object;
		
		super.restoreState(faces, state[0]);
		
		if (state[1] != null) {
			this.valueHolder = new ValueHolder();
			this.valueHolder.restoreState(faces, this, state[1]);
		}
	}

}
