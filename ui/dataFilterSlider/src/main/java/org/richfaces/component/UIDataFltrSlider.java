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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.model.ListDataModel;

import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.DataFilterSliderAdapter;
import org.richfaces.event.DataFilterSliderEvent;
import org.richfaces.event.DataFilterSliderListener;
import org.richfaces.event.DataFilterSliderSource;

/**
 * JSF component class
 */
public abstract class UIDataFltrSlider extends AjaxActionComponent implements DataFilterSliderSource {

    /**
     * The component type for this component.
     */
    public static final String COMPONENT_TYPE = "org.richfaces.DataFilterSlider";

    public static final String COMPONENT_FAMILY = "org.richfaces.DataFilterSlider";

    private transient boolean _active = false;

    public boolean getRendersChildren() {
        return true;
    }

    public void addSliderListener(DataFilterSliderListener listener) {
        addFacesListener(listener);
    }

    public DataFilterSliderListener[] getSliderListeners() {
        return (DataFilterSliderListener[]) getFacesListeners(DataFilterSliderListener.class);
    }

    public void removeSliderListener(DataFilterSliderListener listener) {
        removeFacesListener(listener);
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof DataFilterSliderEvent) {

            if (getSliderListeners().length < 1) {
                addSliderListener(new DataFilterSliderAdapter(getSliderListener()));
            }
        }

        super.broadcast(event);

    }

    protected void setupReRender(FacesContext context) {
        super.setupReRender(context);
        //add data slider itself to rendered list of components
        AjaxRendererUtils.addRegionByName(context, this, this.getId());

        String forAttr = this.getFor();
        RendererUtils rendUtil = RendererUtils.getInstance();

        if (forAttr != null) {
            AjaxRendererUtils.addRegionByName(context, this, rendUtil.correctForIdReference(forAttr, this));
        }
    }

    public void processDecodes(FacesContext context) {
        if (context == null)
            throw new NullPointerException();

        if (!isRendered())
            return;

        // decode the Slider component
        decode(context);
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
     *
     * @throws NullPointerException {@inheritDoc}
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
    }

    protected void updateModel(FacesContext context) {
        Integer handleValue = getHandleValue();
        if (null != handleValue) {
            ValueExpression ve = getValueExpression("handleValue");
            if (ve != null) {
                try {
                    ve.setValue(context.getELContext(), handleValue);
                    setHandleValue(null);
                } catch (ELException e) {
                    String messageStr = e.getMessage();
                    Throwable result = e.getCause();
                    while (null != result && result.getClass().isAssignableFrom(ELException.class)) {
                        messageStr = result.getMessage();
                        result = result.getCause();
                    }
                    FacesMessage message;
                    if (null == messageStr) {
                        message =
                        // not an UIInput, but constant is fine
                        MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID, new Object[] { MessageUtil.getLabel(
                                context, this) });
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageStr, messageStr);
                    }

                    context.getExternalContext().log(message.getSummary(), result);
                    context.addMessage(getClientId(context), message);
                    context.renderResponse();
                } catch (IllegalArgumentException e) {
                    FacesMessage message = MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID,
                            new Object[] { MessageUtil.getLabel(context, this) });

                    context.getExternalContext().log(message.getSummary(), e);
                    context.addMessage(getClientId(context), message);
                    context.renderResponse();
                } catch (Exception e) {
                    FacesMessage message = MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID,
                            new Object[] { MessageUtil.getLabel(context, this) });

                    context.getExternalContext().log(message.getSummary(), e);
                    context.addMessage(getClientId(context), message);
                    context.renderResponse();
                }
            }
        }
    }

    /**
     * @return Returns the active.
     */
    public boolean isActive() {
        return _active;
    }

    /**
     * @param active
     *                The active to set. This method should never be called from
     *                user code.
     */
    public void setActive(boolean active) {
        _active = active;
    }

    private Map<String, Object> getSession() {
        return getFacesContext().getExternalContext().getSessionMap();
    }

    private transient UIData _UIData;

    public UIData getUIData() {
        List ajaxSliderList = (ArrayList) getSession().get("UISliderList");
        _UIData = getDataTable();
        if (ajaxSliderList == null) {
            ListDataModel ajaxSliderLDM = new ListDataModel();
            ajaxSliderLDM.setWrappedData(_UIData.getValue());
            getSession().put("UISliderList", ajaxSliderLDM.getWrappedData());
        } else {
            _UIData.setValue(ajaxSliderList);
        }

        return _UIData;

    }

    /**
     * Finds the dataTable which id is mapped to the "for" property
     *
     * @return the dataTable component
     */
    protected UIData getDataTable() {
        String forAttribute = getFor();
        UIComponent forComp;
        if (forAttribute == null) {
            // DataScroller may be a child of uiData
            forComp = getParent();
        } else {
            forComp = RendererUtils.getInstance().findComponentFor(this, forAttribute);
        }
        if (forComp == null) {
            throw new IllegalArgumentException("could not find dataTable with id '"
                    + forAttribute + "'. If you are outside the naming container, try prepending the form name like for=\"form1:tableName\"");
        } else if (!(forComp instanceof UIData)) {
            throw new IllegalArgumentException("component with id '" + forAttribute
                    + "' must be of type " + UIData.class.getName() + ", not type "
                    + forComp.getClass().getName());
        }

        return (UIData) forComp;
    }

    public void resetDataTable() {
        getSession().remove("UISliderList");
        getDataTable().setValue(getDataTable().getValueBinding("#{" + getForValRef() + "}"));
    }

    public void filterDataTable(int sliderVal) {

        UIData dataTable = getUIData();
        List filteredDataTable = new ArrayList();
        List dataTableList = (ArrayList) dataTable.getValue();
        String filterByString;
        int objectVal;

        for (int i = 0; i < dataTableList.size(); i++) {
            Object o = dataTableList.get(i);

            try {

                filterByString = o.getClass().getMethod(getFilterBy(), null).invoke(o, null).toString();

                if (filterByString.indexOf(".") > 0) {
                    filterByString = filterByString.substring(0, filterByString.indexOf("."));
                }

                objectVal = Integer.parseInt(filterByString);

                if (objectVal < sliderVal) {
                    filteredDataTable.add(o);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getDataTable().setValue(filteredDataTable);

    }

    public String getSliderRange() {
        String sliderValues = "";

        int segmentTotal;
        int segmentTotalStart;
        
        Integer startRange = getStartRange();
        Integer endRange = getEndRange();
        Integer increment = getIncrement();

        if((startRange != null) && (endRange != null) && (increment != null)) {
	        if (getStartRange().intValue() == 0) {
	            segmentTotal = (getEndRange().intValue() / getIncrement().intValue());
	            //set slider start to 0
	            segmentTotalStart = 0;
	        } else {
	            //subtract 1 for 0 based array
	            segmentTotal = (getEndRange().intValue() / getIncrement().intValue()) - 1;
	            //set the slider start for the loop
	            segmentTotalStart = getStartRange().intValue();
	        }
	        int j = segmentTotalStart;
	
	        for (int i = segmentTotalStart; i <= segmentTotal; i++) {
	            if (i != 0) {
	                //Check to see if we are at start of slider
	                if (i != j){
	                    //increment by number given
	                    j = j + getIncrement().intValue();
	                }
	            } else {
	                //start at specified range
	                j = getStartRange().intValue();
	            }
	
	            if (i == segmentTotal) {
	                //this is the end so no comma and exact specified
	                sliderValues = sliderValues + getEndRange();
	            } else {
	                //add to string
	                sliderValues = sliderValues + j + ",";
	            }
	        }
        }
        return sliderValues;
    }

    public abstract void setSliderListener(MethodBinding binding);

    public abstract MethodBinding getSliderListener();

    public abstract String getTrackStyleClass();

    public abstract void setTrackStyleClass(String trackStyleClass);

    public abstract boolean isSubmitOnSlide();
    
    public abstract void setSubmitOnSlide(boolean value);
    
    public abstract boolean isStoreResults();

    public abstract void setStoreResults(boolean storeResults);

    public abstract String getForValRef();

    public abstract void setForValRef(String forValRef);

    public abstract String getFilterBy();

    public abstract void setFilterBy(String filterBy);

    public abstract String getFor();

    public abstract void setFor(String _for);

    public abstract void setStyleClass(String styleClass);

    public abstract String getStyleClass();

    public abstract void setFieldStyleClass(String fieldStyleClass);

    public abstract String getFieldStyleClass();

    public abstract Integer getStartRange();

    public abstract void setStartRange(Integer startRange);

    public abstract Integer getEndRange();

    public abstract void setEndRange(Integer endRange);

    public abstract Integer getIncrement();

    public abstract void setIncrement(Integer increment);

    public abstract String getRangeStyleClass();

    public abstract void setRangeStyleClass(String rangeStyleClass);

    public abstract boolean isTrailer();

    public abstract void setTrailer(boolean trailer);

    public abstract String getTrailerStyleClass();

    public abstract void setTrailerStyleClass(String trailerStyleClass);

    public abstract String getHandleStyleClass();

    public abstract void setHandleStyleClass(String handleStyleClass);

    public abstract Integer getHandleValue();

    public abstract void setHandleValue(Integer handleValue);

    public abstract boolean isManualInput();

    public abstract void setManualInput(boolean manualInput);


    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void decode(FacesContext context) {
        super.decode(context);
    }
    
}   
 
