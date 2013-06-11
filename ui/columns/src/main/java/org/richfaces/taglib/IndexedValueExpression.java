/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
package org.richfaces.taglib;

import javax.el.ELContext;
import javax.el.ValueExpression;


final class IndexedValueExpression extends ValueExpression {

	private static final long serialVersionUID = 5336307425512629431L;

	private final Integer i;

    private final ValueExpression orig;

    /**
     * 
     */
    public IndexedValueExpression(ValueExpression orig, int i) {
	this.i = new Integer(i);
	this.orig = orig;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.ValueExpression#getValue(javax.el.ELContext)
     */
    public Object getValue(ELContext context) {
	Object base = this.orig.getValue(context);
	if (base != null) {
	    context.setPropertyResolved(false);
	    return context.getELResolver().getValue(context, base, i);
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.ValueExpression#setValue(javax.el.ELContext,
     *      java.lang.Object)
     */
    public void setValue(ELContext context, Object value) {
	Object base = this.orig.getValue(context);
	if (base != null) {
	    context.setPropertyResolved(false);
	    context.getELResolver().setValue(context, base, i, value);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.ValueExpression#isReadOnly(javax.el.ELContext)
     */
    public boolean isReadOnly(ELContext context) {
	Object base = this.orig.getValue(context);
	if (base != null) {
	    context.setPropertyResolved(false);
	    return context.getELResolver().isReadOnly(context, base, i);
	}
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.ValueExpression#getType(javax.el.ELContext)
     */
    public Class getType(ELContext context) {
	Object base = this.orig.getValue(context);
	if (base != null) {
	    context.setPropertyResolved(false);
	    return context.getELResolver().getType(context, base, i);
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.ValueExpression#getExpectedType()
     */
    public Class getExpectedType() {
	return Object.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.Expression#getExpressionString()
     */
    public String getExpressionString() {
	return this.orig.getExpressionString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.Expression#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
	return this.orig.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.Expression#hashCode()
     */
    public int hashCode() {
	return this.orig.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.el.Expression#isLiteralText()
     */
    public boolean isLiteralText() {
	return false;
    }

}
