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
package org.ajax4jsf.tests;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;

/**
 * Value expression failing when trying 
 * to coerce its expression string to any type
 * Needed to test tags throw FacesException in that case
 * @author Maksim Kaszynski
 *
 */
@SuppressWarnings("serial")
public class ConstantlyFailingLiteralValueExpression extends ValueExpression {

	/* (non-Javadoc)
	 * @see javax.el.ValueExpression#getExpectedType()
	 */
	@Override
	public Class<?> getExpectedType() {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.ValueExpression#getType(javax.el.ELContext)
	 */
	@Override
	public Class<?> getType(ELContext context) {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.ValueExpression#getValue(javax.el.ELContext)
	 */
	@Override
	public Object getValue(ELContext context) {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.ValueExpression#isReadOnly(javax.el.ELContext)
	 */
	@Override
	public boolean isReadOnly(ELContext context) {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.ValueExpression#setValue(javax.el.ELContext, java.lang.Object)
	 */
	@Override
	public void setValue(ELContext context, Object value) {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.Expression#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.Expression#getExpressionString()
	 */
	@Override
	public String getExpressionString() {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.Expression#hashCode()
	 */
	@Override
	public int hashCode() {
		throw new ELException("Everything is a stub here");
	}

	/* (non-Javadoc)
	 * @see javax.el.Expression#isLiteralText()
	 */
	@Override
	public boolean isLiteralText() {
		return true;
	}

}
