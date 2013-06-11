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

import javax.faces.component.UIComponent;


/**
 * JSF component class
 *
 */
public abstract class UISubTable extends UIDataTable implements Row {
	
	public static final String COMPONENT_TYPE = "org.richfaces.SubTable";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.SubTable";
	
	
	@Override
	public Iterator<UIComponent> fixedChildren() {
		return new SubtableFixedChildrenIterator(this);
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.component.Column#isBreakBefore()
	 */
	public boolean isBreakBefore() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.component.Column#setBreakBefore(boolean)
	 */
	public void setBreakBefore(boolean newBreakBefore) {
		throw new IllegalStateException("Property 'breakBefore' for subtable is read-only");		
	}

    /* (non-Javadoc)
     * @see org.richfaces.component.UIDataTable#columns()
     */
    public Iterator<UIComponent> columns() {
    	return super.columns();
    }

	/**
	 * @return the sortExpression
	 */
	public String getSortExpression() {
		// SubTable is not sortable element.
		return null;
	}

	/**
	 * @param sortExpression the sortExpression to set
	 */
	public void setSortExpression(String sortExpression) {
		// Do nothing - subtable is not sortable element;
	}
	
}
