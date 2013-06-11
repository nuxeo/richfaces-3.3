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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.ajax4jsf.component.UIDataAdaptor;
import org.ajax4jsf.model.DataComponentState;
import org.ajax4jsf.model.RepeatState;
import org.ajax4jsf.util.ELUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.EmptyIterator;
import org.apache.commons.collections.iterators.FilterIterator;
import org.richfaces.component.util.MessageUtil;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 16.11.2007
 * @since 3.2
 */
public abstract class UIOrderingBaseComponent extends UIDataAdaptor implements EditableValueHolder {

	private boolean localValueSet;

	private List validators = null;
	private MethodBinding validator;

	public static final Predicate isColumn = new Predicate() {
		public boolean evaluate(Object input) {
			return (input instanceof javax.faces.component.UIColumn || input instanceof Column) &&
					((UIComponent) input).isRendered();
		}
	};

	protected boolean isSuitableValue(Object value, Object restoredObject) {
		if (value instanceof Object[]) {
			Object[] objects = (Object[]) value;
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
			
				if (object != null && object.equals(restoredObject)) {
					return true;
				}
			}

			return false;
		} else {
			if (value != null) {
				return ((Collection) value).contains(restoredObject);
			} else {
				return false;
			}
		}
	}
	
	public abstract boolean isOrderControlsVisible();
	public abstract void setOrderControlsVisible(boolean visible);
	
	public abstract boolean isFastOrderControlsVisible();
	public abstract void setFastOrderControlsVisible(boolean visible);

	public abstract String getRequiredMessage();
	public abstract void setRequiredMessage(String requiredMessage);
	
	public Object saveState(FacesContext faces) {
		Object[] state = new Object[4];

		state[0] = super.saveState(faces);
	
		state[1] = saveAttachedState(faces, validators);
		state[2] = saveAttachedState(faces, validator);

		state[3] = localValueSet ? Boolean.TRUE : Boolean.FALSE;

		return state;
	}

	public void restoreState(FacesContext faces, Object object) {
		Object[] state = (Object[]) object;

		super.restoreState(faces, state[0]);

		validators = (List) restoreAttachedState(faces, state[1]);
		validator = (MethodBinding) restoreAttachedState(faces, state[2]);

		localValueSet = ((Boolean) state[3]).booleanValue();
	}

	protected DataComponentState createComponentState() {
		return new RepeatState();
	}

	public Iterator columns() {
		return new FilterIterator(getChildren().iterator(), isColumn);
	}

	protected Iterator dataChildren() {
		if (getChildCount() != 0) {
			return columns();
		} else {
			return EmptyIterator.INSTANCE;
		}
	}

	protected Iterator fixedChildren() {
		Map facets = getFacets();
		if (facets != null) {
			return facets.values().iterator();
		} else {
			return EmptyIterator.INSTANCE;
		}
	}

	//validators
	public MethodBinding getValidator() {
		return validator;
	}

	public void setValidator(MethodBinding validatorBinding) {
		this.validator = validatorBinding;
	}

	public Validator[] getValidators() {

		if (validators == null) {
			return new Validator[0];
		} else {
			return (Validator[]) validators.toArray(new Validator[validators.size()]);
		}
	}

	public void addValidator(Validator validator) {
		if (validator == null) {
			throw new NullPointerException();
		}

		if (validators == null) {
			validators = new ArrayList();
		}

		validators.add(validator);
	}

	public void removeValidator(Validator validator) {
		if (validators != null) {
			validators.remove(validator);
		}
	}
	//validators end
	
	public boolean isLocalValueSet() {
		return localValueSet;
	}

	public void setLocalValueSet(boolean localValueSet) {
		this.localValueSet = localValueSet;
	}

	protected DataModel createDataModel(Object value) {
		DataModel dataModel;
        
        if (value == null) {
            dataModel = new ListDataModel(Collections.EMPTY_LIST);
        } else if (value instanceof List) {
        	dataModel = new ListDataModel((List) value);
        } else if (Object[].class.isAssignableFrom(value.getClass())) {
        	dataModel = new ArrayDataModel((Object[]) value);
        } else {
        	throw new IllegalArgumentException();
        }
        
        return dataModel;
	}


	/**
	 * @exception NullPointerException {@inheritDoc}     
	 */ 
	public void decode(FacesContext context) {

		if (context == null) {
			throw new NullPointerException();
		}

		// Force validity back to "true"
		setValid(true);
		super.decode(context);
	}

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

		if (isImmediate()) {
			executeValidate(context);
		}
	}

	/**
	 * Executes validation logic.
	 */
	protected void executeValidate(FacesContext context) {
		try {
			validate(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}

		if (!isValid()) {
			context.renderResponse();
		}
	}


	public abstract void validate(FacesContext context);
	
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
		if (!isImmediate()) {
			executeValidate(context);
		}
	}

	protected void requiredInvalidate(FacesContext context) {
    	String requiredMessage = getRequiredMessage();
    	FacesMessage message = null;
    	
    	if (requiredMessage != null) {
    		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage);
    	} else {
    		message = MessageUtil.getMessage(context, UIInput.REQUIRED_MESSAGE_ID, 
        			new Object[] {MessageUtil.getLabel(context, this)});
        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
    	}

    	context.addMessage(getClientId(context), message);
    	setValid(false);
	}
	
	protected void validateValue(FacesContext context, Object newValue) {
		Validator[] validators = getValidators();
    	for (int i = 0; i < validators.length; i++) {
    		Validator validator = (Validator) validators[i];
    		try { 
    			validator.validate(context, this, newValue);
    		}
    		catch (ValidatorException ve) {
    			// If the validator throws an exception, we're
    			// invalid, and we need to add a message
    			setValid(false);
    			FacesMessage message = ve.getFacesMessage();
    			if (message != null) {
    				message.setSeverity(FacesMessage.SEVERITY_ERROR);
    				context.addMessage(getClientId(context), message);
    			}
    		}
    	}

    	MethodBinding validator = getValidator();
    	if (validator != null) {
    		try {
    			validator.invoke(context,
    					new Object[] { context, this, newValue});
    		}
    		catch (EvaluationException ee) {
    			if (ee.getCause() instanceof ValidatorException) {
    				ValidatorException ve =
    					(ValidatorException) ee.getCause();

    				// If the validator throws an exception, we're
    				// invalid, and we need to add a message
    				setValid(false);
    				FacesMessage message = ve.getFacesMessage();
    				if (message != null) {
    					message.setSeverity(FacesMessage.SEVERITY_ERROR);
    					context.addMessage(getClientId(context), message);
    				}
    			} else {
    				// Otherwise, rethrow the EvaluationException
    				throw ee;
    			}
    		}
    	}
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

		try {
			updateModel(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}

		if (!isValid()) {
			context.renderResponse();
		}
	}

	public abstract void updateModel(FacesContext context);

	protected interface UpdateModelCommand {
		public void execute(FacesContext context);
	}

	protected interface DataAdder {
		Object getContainer();
		void add(Object object);
	}

	protected void updateModel(FacesContext context, UpdateModelCommand command) {
		try {
			command.execute(context);
		} catch (EvaluationException e) {
			String messageStr = e.getMessage();
			FacesMessage message = null;
			if (null == messageStr) {
				message =
					MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID, 
							new Object[] {MessageUtil.getLabel(context, this)});
			}
			else {
				message = new FacesMessage(messageStr);
			}
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(getClientId(context), message);
			setValid(false);
		}
		catch (FacesException e) {
			FacesMessage message = 
				MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID, 
					new Object[] {MessageUtil.getLabel(context, this)});
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(getClientId(context), message);
			setValid(false);
		} catch (IllegalArgumentException e) {
			FacesMessage message = 
				MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID, 
					new Object[] {MessageUtil.getLabel(context, this)});
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(getClientId(context), message);
			setValid(false);
		} catch (Exception e) {
			FacesMessage message =
				MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID, 
					new Object[] {MessageUtil.getLabel(context, this)});
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(getClientId(context), message);
			setValid(false);
		}
	}
	
	/**
	 * <p>Return <code>true</code> if the new value is different from the
	 * previous value.</p>
	 *
	 * @param previous old value of this component (if any)
	 * @param value new value of this component (if any)
	 */
	protected boolean compareValues(Object previous, Object value) {

		if (previous == null) {
			return (value != null);
		} else if (value == null) {
			return (true);
		} else {
			if (previous instanceof Object[]) {
				return !Arrays.equals((Object[]) previous, (Object[]) value);
			} else {
				return (!(previous.equals(value)));
			}
		}

	}

	protected void addConversionErrorMessage(FacesContext context, 
			ConverterException ce, Object value) {
		FacesMessage message = ce.getFacesMessage();
		if (message == null) {
			message = MessageUtil.getMessage(context, UIInput.CONVERSION_MESSAGE_ID, 
					new Object[] {MessageUtil.getLabel(context, this)});
			if (message.getDetail() == null) {
				message.setDetail(ce.getMessage());
			}
		}

		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(getClientId(context), message);
	}
	
	protected boolean isEmpty(Object value) {

		if (value == null) {
			return (true);
		} else if ((value instanceof String) &&
				(((String) value).length() < 1)) {
			return (true);
		} else if (value.getClass().isArray()) {
			if (0 == java.lang.reflect.Array.getLength(value)) {
				return (true);
			}
		}
		else if (value instanceof List) {
			if (0 == ((List) value).size()) {
				return (true);
			}
		}
		return (false);
	}

	protected Object createContainer(ArrayList data, Object object) {
		if (object != null) {
			Class objectClass = object.getClass();
			Class componentType = objectClass.getComponentType();
			if (componentType != null) {
				return data.toArray((Object[]) Array.newInstance(componentType, data.size()));
			}
		}
		
		data.trimToSize();
		return data;
	}

	public abstract ItemState getItemState();
	
	public interface ItemState {
		public boolean isSelected();
		public boolean isActive();
	}

	private Converter getConverterForType(FacesContext context, Class type) {
		if (!Object.class.equals(type) && type != null) {
			Application application = context.getApplication();
			return application.createConverter(type);
		}

		return null;
	}
	
	private static final Converter noOpConverter = new Converter() {
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			return value;
		}
		
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			return (String) value;
		}
	};
	
	public Converter getConverterForValue(FacesContext context) {
		Converter converter = null;
		ValueExpression expression = this.getValueExpression("value");

		if (expression != null) {
			Class<?> containerClass = ELUtils.getContainerClass(context, expression);

			converter = getConverterForType(context, containerClass);
			if (converter == null && String.class.equals(containerClass)) {
				converter = noOpConverter;
			}
		}
		
		
		return converter;
	}
}
