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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.UIData;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.AjaxComponent;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.event.AjaxListener;
import org.ajax4jsf.event.AjaxSource;
import org.ajax4jsf.renderkit.AjaxRendererUtils;


/**
 * UI implementation for SuggestionBox component.
 *
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.5 $ $Date: 2007/03/01 22:37:50 $
 */
public abstract class UISuggestionBox extends UIData
        implements AjaxComponent, AjaxSource, ValueHolder {


    private static final class SubmittedValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2032953038329023808L;
	
	private Object suggestionValue;
	private String[] requestedValues;

	public SubmittedValue() {

	}
	
	public SubmittedValue(Object suggestionValue, String[] requestedValues) {
	    super();
	    this.suggestionValue = suggestionValue;
	    this.requestedValues = requestedValues;
	}

	public Object getSuggestionValue() {
	    return suggestionValue;
	}

	public String[] getRequestedValues() {
	    return requestedValues;
	}
    }

    /**
     * Component type.
     */
    public static final String COMPONENT_TYPE = "org.richfaces.SuggestionBox";

    /**
     * Component family.
     */
    public static final String COMPONENT_FAMILY =
            "org.richfaces.SuggestionBox";

    /**
     * TRUE if submitted.
     */
    private transient boolean submitted = false;

    /**
     * Getter for for attribute.
     *
     * id (or full path of id's) of target components, for which this element
     * must provide support.
     * If a target component inside of the same <code>NamingContainer<code>
     * (UIForm, UIData in base implementstions), can be simple value of the
     * "id" attribute.
     * For other cases must include id's of <code>NamingContainer<code>
     * components, separated by ':'. For search from the root of components,
     * must be started with ':'.
     *
     * @return for value from local variable or value bindings
     */
    public abstract Object getSubmitedValue();

    public abstract void setSubmitedValue(Object v);

    public void setSubmitedValue(Object suggestionValue, String[] requestedValues) {
	setSubmitedValue(new SubmittedValue(suggestionValue, requestedValues));
    }

    public abstract String getFor();

    /**
     * Setter for attribute.
     *
     * @param f identifier
     */
    public abstract void setFor(String f);

    /**
     * Getter for suggestionAction.
     *
     * Method calls an expression to get a collection of suggestion data
     * on request. It must have one parameter with a type of Object with
     * content of input component and must return any type allowed for
     * <h:datatable>;
     *
     * @return suggestionAction value from local variable or value bindings
     */
    public abstract MethodExpression getSuggestionAction();

    /**
     * Setter for suggestionAction.
     *
     * @param action {@link javax.el.MethodExpression}
     */
    public abstract void setSuggestionAction(MethodExpression action);

    /**
     * Getter for converter.
     * Id of Converter to be used or reference to a Converter.
     *
     * @return converter value from local variable or value bindings
     */
    public abstract Converter getConverter();

    /**
     * Setter for converter.
     *
     * @param converter {@link javax.faces.convert.Converter}
     */
    public abstract void setConverter(Converter converter);

    /**
     * Getter for rowClasses.
     *
     * @return rowClasses value from local variable or value bindings
     */
    public abstract String getRowClasses();

    /**
     * Getter for rowClasses.
     *
     * @param rowClasses row classes
     */
    public abstract void setRowClasses(String rowClasses);

    /**
     * Getter for selectValueClass.
     *
     * Name of the CSS class for a hidden suggestion entry
     * element (table cell)
     *
     * @return selectValueClass value from local variable or value bindings
     */
    public abstract String getSelectValueClass();

    /**
     * Setter for selectValueClass.
     *
     * @param value class
     */
    public abstract void setSelectValueClass(String value);

    /**
     * Getter for border.
     *
     * This attributes specifies the width (in pixels only) of the frame around a table
     *
     * @return border value from local variable or value bindings
     */
    public abstract String getBorder();

    /**
     * Setter for border.
     *
     * @param value of border
     */
    public abstract void setBorder(String value);

    /**
     * Getter for cellpadding.
     *
     * This attribute specifies the amount of space between the
     * border of the cell and its contents. If the value of
     * this attribute is a pixel length, all four margins
     * should be this distance from the contents. If the value
     * of the attribute is percentage length, the top and
     * bottom margins should be equally separated from the
     * content based on percentage of the available vertical
     * space, and the left and right margins should be equally
     * separated from the content based on percentage of the
     * available horizontal space
     */
    public abstract String getCellpadding();

    /**
     * Setter for cellpadding.
     *
     * @param value of cellpadding
     */
    public abstract void setCellpadding(String value);

    /**
     * Getter for immediate.
     *
     * @return immediate value from local variable or value bindings
     */
    public abstract boolean isImmediate();

    /**
     * Getter for immediate.
     *
     * @param value of immediate
     */
    public abstract void setImmediate(boolean value);

    /**
     * Getter for selfRendered.
     *
     * If true, forces active Ajax region render response
     * directly from stored components tree, bypasses page
     * processing. Can be used for increase performance. Also,
     * must be set to 'true' inside iteration components, such
     * as dataTable
     *
     * @return selfRendered value from local variable or value bindings
     */

    public abstract boolean isSelfRendered();

    /**
     * Getter for selfRendered.
     *
     * @param value for attribute
     */
    public abstract void setSelfRendered(boolean value);

    /**
     * Getter for entryClass.
     *
     * Name of the CSS class for a suggestion entry element (table row)
     *
     * @return entryClass value from local variable or value bindings
     */
    public abstract String getEntryClass();

    /**
     * Setter for entryClass.
     *
     * @param value of entry class
     */
    public abstract void setEntryClass(String value);

    /**
     * Getter for submitted.
     * 
     * @return TRUE if submited
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Setter for submitted.
     *
     * @param s of entry class
     */
    public void setSubmitted(boolean s) {
        this.submitted = s;
    }

    public abstract int getZindex();
    
    public abstract void setZindex(int zindex);
    
    public abstract String getNothingLabel();
    
    public abstract void setNothingLabel(String nothingLabel);

    public abstract Object getFetchValue();

    public abstract void setFetchValue(Object value);
    
    /* (non-Javadoc)
     * @see javax.faces.component.UIData#broadcast(javax.faces.event.FacesEvent)
     */
    
    private int rowNumber = -1;
    
    private Object getModelValuesData() {
	int first = getFirst();
	int rows = getRows();
	int rowIndex = getRowIndex();
	
	setFirst(0);
	setRows(0);
	setRowIndex(-1);

	List<Object> results = new ArrayList<Object>();
	int j = 0;
	boolean stop = false;

	while (!stop) {
	    setRowIndex(j++);
	    if (isRowAvailable()) {
		results.add(getRowData());
	    } else {
		stop = true;
	    }
	}

	setFirst(first);
	setRows(rows);
	setRowIndex(rowIndex);
    
	return results;
    }
    
    private Object getRequestedValuesData() {
	SubmittedValue valueHolder = (SubmittedValue) getSubmitedValue();
	FacesContext context = getFacesContext();
	
	if (valueHolder != null) {
	    String[] requestedValues = valueHolder.getRequestedValues();
	    if (requestedValues != null) {
		MethodExpression suggestingAction = getSuggestionAction();
		if (suggestingAction != null) {
		    int first = getFirst();
		    int rows = getRows();
		    int rowIndex = getRowIndex();
		    Object value = getValue();

		    setFirst(0);
		    setRows(0);

		    Map<String, Object> results = new HashMap<String, Object>();
		    for (int i = 0; i < requestedValues.length; i++) {
			String requestedValue = requestedValues[i];
			if (requestedValue != null) {
			    setValue(suggestingAction.invoke(
				    context.getELContext(), new Object[]{requestedValue}));

			    setRowIndex(-1);

			    int j = 0;
			    boolean stop = false;

			    while (!stop) {
				setRowIndex(j++);
				if (isRowAvailable()) {
				    Object fetchValue = getFetchValue();
				    if (fetchValue != null) {
					if (requestedValue.equalsIgnoreCase(fetchValue.toString())) {
					    results.put(requestedValue, getRowData());
					    stop = true;
					}
				    }
				} else {
				    stop = true;
				}
			    }
			}
		    }

		    setFirst(first);
		    setRows(rows);
		    setRowIndex(rowIndex);
		    setValue(value);

		    return results;
		}
	    }
	}

	return null;
    }

    public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public final void broadcast(final FacesEvent event)
            throws AbortProcessingException {
        super.broadcast(event);
        if (event instanceof AjaxEvent) {
            FacesContext context = getFacesContext();
            setupValue(context);

            AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);

            if (this.isUsingSuggestObjects()) {
                Object modelValues = getModelValuesData();
                Object requestedData = getRequestedValuesData();

                if (null != modelValues || null != requestedData) {
        	    Map<String,Object> map = new HashMap<String, Object>();
        	    map.put("suggestionObjects", modelValues);

        	    if (requestedData != null) {
        		map.put("requestedObjects", requestedData);
        	    }

        	    ajaxContext.setResponseData(map);
        	}
            }
            
            AjaxRendererUtils.addRegionsFromComponent(this, context);
            AjaxRendererUtils.addRegionByName(context, this, this.getId());
//            setSubmitted(true);
            if (isSelfRendered()) {
                ajaxContext.setSelfRender(true);
            }
        } else if (event instanceof SelectSuggestionEvent) {
        	setValue(null);
        }
    }

    public void queueEvent(FacesEvent event) {
    	if (event instanceof SelectSuggestionEvent) {
    		event.setPhaseId(PhaseId.RENDER_RESPONSE);
			super.queueEvent(event);
    	} else if (event.getComponent() != this && rowNumber != -1) {
			int prevIndex = getRowIndex();
			setRowIndex(rowNumber);
			super.queueEvent(event);
			setRowIndex(prevIndex);
		} else {
			super.queueEvent(event);
		}
		
	}

	public void processDecodes(FacesContext context) {
		if (!this.isRendered())
			return;
		Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
        String clientId = getClientId(context);
        String rowValue = (String)requestParameterMap.get(clientId+"_selection");
        if (rowValue != null && rowValue.length()!=0) {
        	setRowNumber(Integer.parseInt(rowValue)+getFirst());
            setupValue(context);
            queueEvent(new SelectSuggestionEvent(this));
        } else {
        	setRowNumber(-1);
        }
		super.processDecodes(context);
	}

	public void setupValue(FacesContext context) {
	    	SubmittedValue valueHolder = (SubmittedValue) getSubmitedValue();
	    	Object submittedValue = valueHolder != null ? valueHolder.getSuggestionValue() : null;
	    	
		MethodExpression suggestingAction = getSuggestionAction();
		if (null != suggestingAction) {
		    setValue(suggestingAction.invoke(
		            context.getELContext(), new Object[]{submittedValue}));
		}
	}

	public void addAjaxListener(final AjaxListener listener) {
        addFacesListener(listener);
    }

    public AjaxListener[] getAjaxListeners() {
        return (AjaxListener[]) getFacesListeners(AjaxListener.class);
    }

    public void removeAjaxListener(final AjaxListener listener) {
        removeFacesListener(listener);
    }
    //To support ValueHolder
    public Object getLocalValue() {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    /**
     * Getter for styleClass.
     *
     * Corresponds to the HTML class attribute
     *
     * @return styleClass value from local variable or value bindings
     */
    public abstract String getStyleClass();

    /**
     * Setter for styleClass.
     *
     * @param value class
     */
    public abstract void setStyleClass(String value);
    
    /**
     * Getter for popupClass.
     *
     * HTML CSS class attribute of element for pop-up suggestion content
     *
     * @return popupClass value from local variable or value bindings
     */
    public abstract String getPopupClass();

    /**
     * Setter for popupClass.
     *
     * @param value class
     */
    public abstract void setPopupClass(String value);
    
    /**
     * Getter for style.
     *
     * CSS style(s) is/are to be applied when this component is rendered
     *
     * @return style value from local variable or value bindings
     */
    public abstract String getStyle();

    /**
     * Setter for style.
     *
     * @param value style
     */
    public abstract void setStyle(String value);
    
    /**
     * Getter for popupStyle.
     *
     * HTML CSS style attribute of element for pop-up suggestion content
     *
     * @return popupStyle value from local variable or value bindings
     */
    public abstract String getPopupStyle();

    /**
     * Setter for popupStyle.
     *
     * @param value style
     */
    public abstract void setPopupStyle(String value);
    
    public abstract boolean isUsingSuggestObjects();
    
    public abstract void setUsingSuggestObjects(boolean usingSuggestObjects); 
}
