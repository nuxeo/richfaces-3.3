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
 *
 * Portions Copyright Apache Software Foundation.
 */ 

package org.richfaces.iterator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspTagException;

/**
 * TODO Class description goes here.
 * 
 * @author Shawn Bayern
 * 
 */
public class SimpleForEachIterator implements ForEachIterator, Serializable {

    private Iterator i;

    public static ForEachIterator supportedTypeForEachIterator(Object o)
	    throws JspTagException {

	/*
	 * This is, of necessity, just a big, simple chain, matching in order.
	 * Since we are passed on Object because of all the various types we
	 * support, we cannot rely on the language's mechanism for resolving
	 * overloaded methods. (Method overloading resolves via early binding,
	 * so the type of the 'o' reference, not the type of the eventual value
	 * that 'o' references, is all that's available.)
	 * 
	 * Currently, we 'match' on the object we have through an if/else chain
	 * that picks the first interface (or class match) found for an Object.
	 */

	ForEachIterator items;

	if (o instanceof Object[])
	    items = toForEachIterator((Object[]) o);
	else if (o instanceof boolean[])
	    items = toForEachIterator((boolean[]) o);
	else if (o instanceof byte[])
	    items = toForEachIterator((byte[]) o);
	else if (o instanceof char[])
	    items = toForEachIterator((char[]) o);
	else if (o instanceof short[])
	    items = toForEachIterator((short[]) o);
	else if (o instanceof int[])
	    items = toForEachIterator((int[]) o);
	else if (o instanceof long[])
	    items = toForEachIterator((long[]) o);
	else if (o instanceof float[])
	    items = toForEachIterator((float[]) o);
	else if (o instanceof double[])
	    items = toForEachIterator((double[]) o);
	else if (o instanceof Collection)
	    items = toForEachIterator((Collection) o);
	else if (o instanceof Iterator)
	    items = toForEachIterator((Iterator) o);
	else if (o instanceof Enumeration)
	    items = toForEachIterator((Enumeration) o);
	else if (o instanceof Map)
	    items = toForEachIterator((Map) o);
	/*
	 * else if (o instanceof ResultSet) items =
	 * toForEachIterator((ResultSet) o);
	 */
	else if (o instanceof String)
	    items = toForEachIterator((String) o);
	else
	    items = toForEachIterator(o);

	return (items);
    }
    
    
    /*
     * Creates a ForEachIterator of Integers from 'begin' to 'end'
     * in support of cases where our tag handler isn't passed an
     * explicit collection over which to iterate.
     */
    public static ForEachIterator beginEndForEachIterator(int end) {
        /*
         * To plug into existing support, we need to keep 'begin', 'end',
         * and 'step' as they are.  So we'll simply create an Integer[]
         * from 0 to 'end', inclusive, and let the existing implementation
         * handle the subsetting and stepping operations.  (Other than
         * localizing the cost of creating this Integer[] to the start
         * of the operation instead of spreading it out over the lifetime
         * of the iteration, this implementation isn't worse than one that
         * created new Integers() as needed from next().  Such an adapter
         * to ForEachIterator could easily be written but, like I said,
         * wouldn't provide much benefit.)
         */
        Integer[] ia = new Integer[end+1];
        for (int i = 0; i < end; i++)
            ia[i] = new Integer(i);
        return new SimpleForEachIterator(Arrays.asList(ia).iterator());
    }


    // *********************************************************************
    // Private conversion methods to handle the various types we support

    // catch-all method whose invocation currently signals a 'matching error'
    protected static ForEachIterator toForEachIterator(Object o)
	    throws JspTagException {
	throw new JspTagException("FOREACH_BAD_ITEMS");
    }

    // returns an iterator over an Object array (via List)
    protected static ForEachIterator toForEachIterator(Object[] a) {
	return new SimpleForEachIterator(Arrays.asList(a).iterator());
    }

    // returns an iterator over a boolean[] array, wrapping items in Boolean
    protected static ForEachIterator toForEachIterator(boolean[] a) {
	Boolean[] wrapped = new Boolean[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Boolean(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a byte[] array, wrapping items in Byte
    protected static ForEachIterator toForEachIterator(byte[] a) {
	Byte[] wrapped = new Byte[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Byte(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a char[] array, wrapping items in Character
    protected static ForEachIterator toForEachIterator(char[] a) {
	Character[] wrapped = new Character[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Character(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a short[] array, wrapping items in Short
    protected static ForEachIterator toForEachIterator(short[] a) {
	Short[] wrapped = new Short[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Short(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over an int[] array, wrapping items in Integer
    protected static ForEachIterator toForEachIterator(int[] a) {
	Integer[] wrapped = new Integer[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Integer(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a long[] array, wrapping items in Long
    protected static ForEachIterator toForEachIterator(long[] a) {
	Long[] wrapped = new Long[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Long(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a float[] array, wrapping items in Float
    protected static ForEachIterator toForEachIterator(float[] a) {
	Float[] wrapped = new Float[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Float(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // returns an iterator over a double[] array, wrapping items in Double
    protected static ForEachIterator toForEachIterator(double[] a) {
	Double[] wrapped = new Double[a.length];
	for (int i = 0; i < a.length; i++)
	    wrapped[i] = new Double(a[i]);
	return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
    }

    // retrieves an iterator from a Collection
    protected static ForEachIterator toForEachIterator(Collection c) {
	return new SimpleForEachIterator(c.iterator());
    }

    // simply passes an Iterator through...
    protected static ForEachIterator toForEachIterator(Iterator i) {
	return new SimpleForEachIterator(i);
    }

    // converts an Enumeration to an Iterator via a local adapter
    protected static ForEachIterator toForEachIterator(Enumeration e) {

	// local adapter
	class EnumerationAdapter implements ForEachIterator {
	    private Enumeration e;
	    
	    private Object o;

	    public EnumerationAdapter(Enumeration e) {
		this.e = e;
	    }

	    public boolean hasNext() {
		return e.hasMoreElements();
	    }

	    public Object next() {
	    o = e.nextElement();
		return o;
	    }

		public String getVarReplacement() {
				if (o != null)
					return o.toString();
				return null;
			}
	}

	return new EnumerationAdapter(e);
    }

    // retrieves an iterator over the Map.Entry items in a Map
    protected static ForEachIterator toForEachIterator(Map m) {
	return new SimpleForEachIterator(m.entrySet().iterator());
    }

    protected static ForEachIterator toForEachIterator(String s) {
	StringTokenizer st = new StringTokenizer(s, ",");
	return toForEachIterator(st); // convert from Enumeration
    }

    public SimpleForEachIterator(Iterator i) {
	this.i = i;
    }

    public boolean hasNext() {
	return i.hasNext();
    }

    public Object next() {
	return i.next();
    }


	public String getVarReplacement() {
		return null;
	}

}
