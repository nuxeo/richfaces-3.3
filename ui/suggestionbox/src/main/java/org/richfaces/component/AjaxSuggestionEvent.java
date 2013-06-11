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

import org.ajax4jsf.event.AjaxEvent;

import javax.faces.component.UIComponent;

/**
 * Ajax event for SuggestionBox component.
 */
public class AjaxSuggestionEvent extends AjaxEvent {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 9212067213575185754L;

    /**
     * Submitted value.
     */
    private Object submittedValue;

    /**
     * Constructor.
     *
     * @param component {@link javax.faces.component.UIComponent}
     * @param value     The submitted value to set
     */
    public AjaxSuggestionEvent(final UIComponent component,
                               final Object value) {
        super(component);
        this.submittedValue = value;
    }

    /**
     * Constructor.
     *
     * @param component {@link javax.faces.component.UIComponent}
     */
    public AjaxSuggestionEvent(final UIComponent component) {
        super(component);
    }

    /**
     * Gets submitted value.
     *
     * @return Returns the submittedValie.
     */
    public final Object getSubmittedValue() {
        return this.submittedValue;
    }

    /**
     * Sets submitted value.
     *
     * @param value The submitted value to set.
     */
    public final void setSubmittedValue(final Object value) {
        this.submittedValue = value;
    }
}
