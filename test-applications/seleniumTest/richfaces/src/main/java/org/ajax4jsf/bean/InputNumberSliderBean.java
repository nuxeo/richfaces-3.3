/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.ajax4jsf.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class InputNumberSliderBean {

    private Integer value = 40;

    private boolean enabledManualInput = true;  

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (((Integer) value).intValue() > 90) {
            throw new ValidatorException(new FacesMessage("Fake validation. Value is more than 90."));
        }
    }

    public String action() {
        this.value += 10;
        return null;
    }

    /**
     * Gets value of value field.
     * @return value of value field
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Set a new value for value field.
     * @param value a new value for value field
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    public void reset() {
        this.value = 40;
    }

    public void initEnableManualInputAttributeTest() {
        reset();
        this.enabledManualInput = false;
    }

    /**
     * Gets value of enabledManualInput field.
     * @return value of enabledManualInput field
     */
    public boolean isEnabledManualInput() {
        return enabledManualInput;
    }

    /**
     * Set a new value for enabledManualInput field.
     * @param enabledManualInput a new value for enabledManualInput field
     */
    public void setEnabledManualInput(boolean enabledManualInput) {
        this.enabledManualInput = enabledManualInput;
    }

}
