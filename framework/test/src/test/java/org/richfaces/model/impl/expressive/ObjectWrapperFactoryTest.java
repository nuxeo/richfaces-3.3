/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.model.impl.expressive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
import org.richfaces.model.impl.expressive.JavaBeanWrapper;
import org.richfaces.model.impl.expressive.ObjectWrapperFactory;

/**
 * @author Maksim Kaszynski
 *
 */
public class ObjectWrapperFactoryTest extends AbstractAjax4JsfTestCase {

	
	/**
	 * 
	 */
	SortField [] sortFields;
	SortOrder sortOrder;
	private ObjectWrapperFactory factory;
	private String var = "abc";
	
	public ObjectWrapperFactoryTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		sortFields = new SortField[3];
		sortFields[0] = new SortField("_id1", Boolean.TRUE);
		sortFields[1] = new SortField("name", Boolean.FALSE);
		sortFields[2] = new SortField("#{" +var + ".name}", Boolean.TRUE);
		sortOrder = new SortOrder(sortFields);
		
		factory = new ObjectWrapperFactory(facesContext, var, sortOrder);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		
		sortFields = null;
		sortOrder = null;
		factory = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ObjectWrapperFactory#convertList(java.util.List, org.richfaces.model.impl.expressive.ObjectWrapperFactory.ObjectConvertor)}.
	 */
	public final void testConvertList() {
		
		
		List objects = new ArrayList(); 
		objects.add(Boolean.TRUE);
		int size = objects.size();
		factory.convertList(objects, new ObjectWrapperFactory.ObjectConvertor() {
			public Object convert(Object o ) {
				
				return new Boolean(!((Boolean) o).booleanValue());
			} 
		});
		
		assertEquals(size, objects.size());
		assertEquals(Boolean.FALSE, objects.get(0));
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ObjectWrapperFactory#unwrapList(java.util.List)}.
	 */
	public final void testUnwrapList() {
		List objects = new ArrayList(10);
		int [] ints = new int[10];
		Random random = new Random();
		for(int i = 0; i < 10; i++) {
			ints[i] = random.nextInt();
			objects.add(new TestObj(String.valueOf(ints[i])));
		}
		
		List l1 = factory.wrapList(objects);
		List l2 = factory.unwrapList(l1);
		
		assertSame(objects, l1);
		assertSame(objects, l2);
		assertEquals(10, l2.size());
		
		for(int i = 0; i < 10; i++) {
			TestObj t = (TestObj) l2.get(i);
			assertEquals(String.valueOf(ints[i]), t.getName());
		}
		
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ObjectWrapperFactory#unwrapObject(java.lang.Object)}.
	 */
	public final void testUnwrapObject() {
		
		TestObj t = new TestObj("20");
		
		JavaBeanWrapper wrapper = new JavaBeanWrapper(t, new HashMap());
		
		Object wrapped = factory.unwrapObject(wrapper);
		
		assertSame(t, wrapped);
		
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ObjectWrapperFactory#wrapList(java.util.List)}.
	 */
	public final void testWrapList() {
		List objects = new ArrayList(10);
		int [] ints = new int[10];
		Random random = new Random();
		for(int i = 0; i < 10; i++) {
			ints[i] = random.nextInt();
			objects.add(new TestObj(String.valueOf(ints[i])));
		}
		
		List l1 = factory.wrapList(objects);
		
		assertSame(objects, l1);
		assertEquals(10, l1.size());
		
		for(int i = 0; i < 10; i++) {
			JavaBeanWrapper wrapper = (JavaBeanWrapper) l1.get(i);
			TestObj t = (TestObj) wrapper.getWrappedObject();
			
			String string = String.valueOf(ints[i]);
			
			assertEquals(string, t.getName());
			
			Object prop1 = wrapper.getProperty("name"); 
			//Object prop2 = wrapper.getProperty("#{abc.name}");
			
			assertNotNull(prop1);
			//assertNotNull(prop2);
			assertEquals(string, prop1);
			//assertEquals(string, prop2);
		}
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ObjectWrapperFactory#wrapObject(java.lang.Object)}.
	 */
	public final void testWrapObject() {
		TestObj t = new TestObj("20");
		
		
		
		JavaBeanWrapper wrapper = factory.wrapObject(t);
		
		Object wrapped = wrapper.getWrappedObject();
		
		assertSame(t, wrapped);
		
		Object prop1 = wrapper.getProperty("name"); 
		//Object prop2 = wrapper.getProperty("#{abc.name}");
		
		assertNotNull(prop1);
		//assertNotNull(prop2);
		assertEquals("20", prop1);
		//assertEquals("20", prop2);
		
		
	}

}
