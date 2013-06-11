/**
 * Licensed under the Common Development and Distribution License,
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.sun.com/cddl/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.richfaces.taglib;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;

/**
 * @author Kin-man Chung
 * @version $Id: IteratedExpression.java,v 1.3 2005/12/08 01:20:43 kchung Exp $
 */
final class IteratedExpression implements Serializable{
	private static final long serialVersionUID = 6133489032098750124L;
	protected final ValueExpression orig;
    protected final String delims;

    private Object base;
    private int index;
    private Iterator iter;

    public IteratedExpression(ValueExpression orig, String delims) {
	this.orig = orig;
	this.delims = delims;
    }

    /**
     * Evaluates the stored ValueExpression and return the indexed item.
     * 
     * @param context
     *                The ELContext used to evaluate the ValueExpression
     * @param i
     *                The index of the item to be retrieved
     */
    public Object getItem(ELContext context, int i) {

	if (base == null) {
	    base = orig.getValue(context);
	    if (base == null) {
		return null;
	    }
	    iter = toIterator(base);
	    index = 0;
	}
	if (index > i) {
	    // Restart from index 0
	    iter = toIterator(base);
	    index = 0;
	}
	while (iter.hasNext()) {
	    Object item = iter.next();
	    if (index++ == i) {
		return item;
	    }
	}
	return null;
    }

    public ValueExpression getValueExpression() {
	return orig;
    }

    private Iterator toIterator(final Object obj) {

	Iterator iter;
	if (obj instanceof String) {
	    iter = toIterator(new StringTokenizer((String) obj, delims));
	} else if (obj instanceof Iterator) {
	    iter = (Iterator) obj;
	} else if (obj instanceof Collection) {
	    iter = toIterator(((Collection) obj).iterator());
	} else if (obj instanceof Enumeration) {
	    iter = toIterator((Enumeration) obj);
	} else if (obj instanceof Map) {
	    iter = ((Map) obj).entrySet().iterator();
	} else {
	    throw new ELException("Bad items");
	}
	return iter;
    }

    private Iterator toIterator(final Enumeration obj) {
	return new Iterator() {
	    public boolean hasNext() {
		return obj.hasMoreElements();
	    }

	    public Object next() {
		return obj.nextElement();
	    }

	    public void remove() {
	    }
	};
    }
}

/**
 * @author Kin-man Chung
 * @version $Id: IteratedValueExpression.java,v 1.2 2005/12/08 01:20:43 kchung
 *          Exp $
 */
final class IteratedValueExpression extends ValueExpression implements
	Serializable {

    private static final long serialVersionUID = 1L;
    protected final int i;
    protected final IteratedExpression iteratedExpression;

    public IteratedValueExpression(IteratedExpression iteratedExpr, int i) {
	this.i = i;
	this.iteratedExpression = iteratedExpr;
    }

    public Object getValue(ELContext context) {
	return iteratedExpression.getItem(context, i);
    }

    public void setValue(ELContext context, Object value) {
    }

    public boolean isReadOnly(ELContext context) {
	return true;
    }

    public Class getType(ELContext context) {
	return null;
    }

    public Class getExpectedType() {
	return Object.class;
    }

    public String getExpressionString() {
	return iteratedExpression.getValueExpression().getExpressionString();
    }

    public boolean equals(Object obj) {
	return iteratedExpression.getValueExpression().equals(obj);
    }

    public int hashCode() {
	return iteratedExpression.getValueExpression().hashCode();
    }

    public boolean isLiteralText() {
	return false;
    }
}

/**
 * @author Andrey Markavtsov
 * @version $Id: IteratedIndexExpression.java,v 1.2 2007/12/06 01:20:43
 * 
 */
final class IteratedIndexExpression extends ValueExpression implements
	Serializable {

    private static final long serialVersionUID = 1L;
    protected final Integer i;

    public IteratedIndexExpression(int i) {
	this.i = i;
    }

    public Object getValue(ELContext context) {
	return i;
    }

    public void setValue(ELContext context, Object value) {
    }

    public boolean isReadOnly(ELContext context) {
	return true;
    }

    public Class getType(ELContext context) {
	return null;
    }

    public Class getExpectedType() {
	return Object.class;
    }

    public String getExpressionString() {
	return i.toString();
    }

    public boolean equals(Object obj) {
	return i.equals(obj);
    }

    public int hashCode() {
	return i.hashCode();
    }

    public boolean isLiteralText() {
	return false;
    }
}

