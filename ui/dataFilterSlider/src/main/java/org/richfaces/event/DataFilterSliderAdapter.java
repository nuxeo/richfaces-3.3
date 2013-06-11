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

package org.richfaces.event;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

/**
 * @author Wesley Hales
 */
public class DataFilterSliderAdapter implements DataFilterSliderListener, StateHolder {

    public static Class[] SIGNATURE = new Class[] { DataFilterSliderEvent.class };

    private MethodBinding sliderMethod;
    private boolean sliderTransient;

    /**
    * empty constructor needed.
    *
    */
    public DataFilterSliderAdapter() {
    }

    /**
    * Creates a new DataFilterSliderAdapter.
    *
    * @param mySliderMethod  the MethodBinding to adapt
    */
    public DataFilterSliderAdapter(MethodBinding mySliderMethod) {
        sliderMethod = mySliderMethod;
    }

    /**
    * Processes a SliderEvent.
    *
    * @param event  the slider event
    */
    public void processSlider(DataFilterSliderEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (sliderMethod != null) {
            sliderMethod.invoke(context, new Object[]{event});
        }
    }

    /**
    * Saves the internal state of this DataFilterSliderAdapter.
    *
    * @param context  the Faces context
    *
    * @return  the saved state
    */
    public Object saveState(FacesContext context) {
        return UIComponentBase.saveAttachedState(context, sliderMethod);
    }

    /**
    * Restores the internal state of this DataFilterSliderAdapter.
    *
    * @param context  the Faces context
    * @param object   the state to restore
    */
    public void restoreState(FacesContext context, Object object){
        sliderMethod = (MethodBinding) UIComponentBase.restoreAttachedState(context, object);
    }

    /**
    * Returns true if this DataFilterSliderAdapter is transient and should
    * not be state saved, otherwise false.
    *
    * @return  the value of transient
    */
    public boolean isTransient() {
        return sliderTransient;
    }

    /**
    * Indicates whether or not this DataFilterSliderAdapter is transient and should
    * not be state saved.
    *
    * @param isTransient  the new value for transient
    */
    public void setTransient(

    boolean isTransient) {
        sliderTransient = isTransient;
    }

}
