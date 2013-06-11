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

/*
 * ValueExpressionMock.java		Date created: 14.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.ajax4jsf.tests;

import javax.el.ELContext;
import javax.el.ValueExpression;

/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov
 *
 */
public class MockValueExpression extends ValueExpression {
    
	private static final long serialVersionUID = -285020144243143376L;

	/** Object to returned by getValue method */
    private Object value;
   
    /**
     * TODO Description goes here.
     */
    public MockValueExpression(Object value) {
    	super();
    	this.value = value;
    }

    /* (non-Javadoc)
     * @see javax.el.ValueExpression#getExpectedType()
     */
    @Override
    public Class<?> getExpectedType() {
    	return Object.class;
    }

    /* (non-Javadoc)
     * @see javax.el.ValueExpression#getType(javax.el.ELContext)
     */
    @Override
    public Class<?> getType(ELContext context) {
	return  value.getClass();
    }

    /* (non-Javadoc)
     * @see javax.el.ValueExpression#getValue(javax.el.ELContext)
     */
    @Override
    public Object getValue(ELContext context) {
	// TODO Auto-generated method stub
	return value;
    }

    /* (non-Javadoc)
     * @see javax.el.ValueExpression#isReadOnly(javax.el.ELContext)
     */
    @Override
    public boolean isReadOnly(ELContext context) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see javax.el.ValueExpression#setValue(javax.el.ELContext, java.lang.Object)
     */
    @Override
    public void setValue(ELContext context, Object value) {
		value = value;

    }

    /* (non-Javadoc)
     * @see javax.el.Expression#getExpressionString()
     */
    @Override
    public String getExpressionString() {
	// TODO Auto-generated method stub
	return value.toString();
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MockValueExpression other = (MockValueExpression) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/* (non-Javadoc)
     * @see javax.el.Expression#isLiteralText()
     */
    @Override
    public boolean isLiteralText() {
    	return false;
    }
}
