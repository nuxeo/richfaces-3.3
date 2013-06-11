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

package org.ajax4jsf.builder.generator;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import junit.framework.TestCase;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 20.04.2007
 * 
 */
public class AbstractClassStubBuilderTest extends TestCase {
	public void testLoader() throws Exception {
		BaseInterface newInstance = AbstractClassStubBuilder.buildStub(AbstractClass.class, new Proxy(), null);
		assertEquals(0, newInstance.test(), 0);
		assertEquals(0, newInstance.test1(), 0);
		assertEquals(0, newInstance.test2());
		assertEquals(0, newInstance.test3());
		assertEquals(0, newInstance.test4(), 0);
		assertEquals(0, newInstance.test5());
		assertEquals(null, newInstance.test6());
		newInstance.test7();
		assertEquals(false, newInstance.test8());
		assertEquals(0, newInstance.test9());
	}

	public static class Proxy implements MethodInterceptor {

		public Object intercept(Object instance, Method method, Object[] arguments,
				MethodProxy methodProxy) throws Throwable {
			return methodProxy.invokeSuper(instance, arguments);
		}
		
	}
}
