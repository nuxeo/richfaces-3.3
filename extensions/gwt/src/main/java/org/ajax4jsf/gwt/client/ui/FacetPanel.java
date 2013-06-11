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

package org.ajax4jsf.gwt.client.ui;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Extension of {@link HTMLPanel} for wrap html element with rendered content
 * of jsf component ( in most cases, will be used with {@link org.ajax4jsf.gwt.jsf.WidgetWithFacetsRenderer} for
 * wrap rendered facet or children components.
 * @author shura
 *
 */
public class FacetPanel extends HTMLPanel {

	public FacetPanel(String id) throws ElementNotFoundException {
		super("");
        Element e = DOM.getElementById(id);
        if (e == null) {
            throw new ElementNotFoundException(id);
        }
        setElement(e);
	}

}
