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

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.richfaces.event.SwitchablePanelSwitchEvent;

/**
 * JSF component class
 */
public abstract class UITabPanel extends UISwitchablePanel {
  
	public static final String COMPONENT_TYPE = "org.richfaces.TabPanel";
    
    private transient boolean processedTabImmediate;
    

	public boolean getRendersChildren() {
        return true;
    }

    protected Iterator getSwitchedFacetsAndChildren() {
    	
    	final Object renderedValue = this.restoredRenderedValue;

    	return new IteratorChain(new FilterIterator(getRenderedTabs(), new Predicate() {

    		public boolean evaluate(Object object) {
    			UITab tab = (UITab) object;
    			
    			if (tab.isDisabled()) {
    			    return false;
    			}
    			
    			if (CLIENT_METHOD.equals(tab.getSwitchTypeOrDefault())) {
    			    return true;
    			}
    			
    			return renderedValue != null && renderedValue.equals(tab.getName());
    		}
        	
        }), getFacets().values().iterator());    	
    }

    public boolean isImmediate() {
	//TODO reorganize method
    	if(!super.isImmediate()){
    		if(processedTabImmediate){
    			return true;
    		}
    		return false;
      	}else{
    		return super.isImmediate();
    	}
	
    }
       
    //TODO JavaDoc
    public Object convertSwitchValue(UIComponent component, Object object) {
        if (object != null) {
            return object;
        }
        
        return ((UITab) component).getName();
    }

    public Object getValue() {
    	if (renderedValue != null) {
    		return renderedValue;
    	}
    	
    	return super.getValue();
    }
    
    public void setValue(Object value) {
    	super.setValue(value);
    }
    
    /**
     * Get value for current selected tab. Possible classes - prefered {@link Integer} for tab number, or other Object for tab model value.
     *
     * @return selectedTab value from local variable or value bindings
     */
    public Object getSelectedTab() {
    	return getValue();
    }

    /**
     * Set currently selected tab.
     *
     * @param tab
     */
    public void setSelectedTab(Object tab) {
    	setValue(tab);
    }

    public Object getRenderedValue() {
    	return getValue();
    }
    
    public void setRenderedValue(Object renderedValue) {
		this.renderedValue = renderedValue;
	}
    
    private Object renderedValue;
    
    private transient Object restoredRenderedValue;
    
    public void processDecodes(FacesContext context) {
	if (context == null) {
	    throw new NullPointerException("FacesContext is null!");
	}

	if (!isRendered()) {
	    return ;
	}

	//RF-1047 - retry to obtain restoredRenderedValue. Seam conversations are absent on 1st phase thus 
	//value is absent also
	if (restoredRenderedValue == null) {
	    this.restoredRenderedValue = getRenderedValue();
	}

	super.processDecodes(context);
    }
    
    public Object saveState(FacesContext context) {
    	Object[] state = new Object[2];
    	state[0] = super.saveState(context);
    	state[1] = renderedValue;
      	
    	return state;
    }
    
    public void restoreState(FacesContext context, Object state) {
    	Object[] states = (Object[]) state;
    	super.restoreState(context, states[0]);
    	this.renderedValue = states[1];
    	//TODO remove that
    	this.restoredRenderedValue = getRenderedValue();
    }
    
    private static final Predicate RENDERED_TAB_PREDICATE = new Predicate() {

	public boolean evaluate(Object object) {
	    if (object instanceof UITab) {
		UITab tab = (UITab) object;

		return tab.isRendered();
	    }

	    return false;
	}

    };
    
    /**
     * Create iterator for all rendered tabs in this component
     * {@link Iterator#next()} method will return tab model - {@link UITab}
     *
     * @return Iterator
     */
    public Iterator getRenderedTabs() {
	if (getChildCount() > 0) {
	    return new FilterIterator(getChildren().iterator(), RENDERED_TAB_PREDICATE);
	} else {
	    return CollectionUtils.EMPTY_COLLECTION.iterator();
	}
    }

    
    //TODO remove switchType  attribute
    /**
     * Get Tab selection behavior for panel - one of "client", "server", "ajax", "page".
     *
     * @return switchType value from local variable or value bindings
     */
    public abstract String getSwitchType();

    /**
     * Set Tab selection behavior for panel - one of "client", "server", "ajax", "page".
     *
     * @param newvalue
     */
    public abstract void setSwitchType(String newvalue);

    /**
     * Get headers alignment - one of "left", "center" and "right".
     *
     * @return headerAlignment value from local variable or value bindings
     */
    public abstract String getHeaderAlignment();

    /**
     * Set headers alignment - one of "left", "center" and "right".
     *
     * @param newAlignment
     */
    public abstract void setHeaderAlignment(String newAlignment);

    public abstract String getHeaderSpacing();

    public abstract void setHeaderSpacing(String value);
    
    public abstract String getHeaderClass();

    public abstract void setHeaderClass(String value);
    
    private UITab processedTab(UIComponent component, Object object){
    	if (object != null) {
             return getTabWithName(object);
        }
        return (UITab)component;
    }
    
    //TODO use getTabs() iterator
    private UITab getTabWithName(Object tabName){
    
    	List children = getChildren();
    	for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			UIComponent childComponent = (UIComponent) iterator.next();
			if(childComponent instanceof UITab && ((UITab)childComponent).getName().equals(tabName)){
				return (UITab)childComponent; 
			}
		}
    	return null;
    }
        
    public void queueEvent(FacesEvent event) {
    	
    	if(event instanceof SwitchablePanelSwitchEvent && this.equals(event.getComponent())){
    		SwitchablePanelSwitchEvent switchEvent = (SwitchablePanelSwitchEvent)event; 
    		UITab tab = processedTab(switchEvent.getEventSource(),switchEvent.getValue());     		
    		//Check if target Tab is immediate
    		processedTabImmediate = tab.isImmediate();
    	}
    	
    	if(event instanceof ActionEvent && event.getComponent()instanceof UITab){
    		
    		if(isImmediate()){
    			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
    		}
    		
    	}
    	
    	super.queueEvent(event);
    }
	
}
