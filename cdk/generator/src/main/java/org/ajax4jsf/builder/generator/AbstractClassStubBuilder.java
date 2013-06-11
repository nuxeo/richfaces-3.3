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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.CodeEmitter;
import net.sf.cglib.core.EmitUtils;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Type;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 20.04.2007
 * 
 */
public class AbstractClassStubBuilder {

	public static Object buildInterfaceStub(final Class<?> clazz, ClassLoader classLoader) throws InstantiationException, IllegalAccessException {
		Enhancer enhancer = new Enhancer() {
			public void generateClass(ClassVisitor v) throws Exception {
				ClassEmitter ce = new ClassEmitter(v);
				ce.begin_class(Constants.V1_5,
						Constants.ACC_PUBLIC,
						getClassName(),
						null,
						new Type[]{Type.getType(clazz)},
						null);
				EmitUtils.null_constructor(ce);
				List methods = new ArrayList();
				getMethods(Object.class, new Class[]{clazz}, methods);

				for (Iterator iterator = methods.iterator(); iterator
				.hasNext();) {
					Method method = (Method) iterator.next();

					if (Modifier.isAbstract(method.getModifiers())) {
						MethodInfo methodInfo = ReflectUtils.getMethodInfo(method);
						Signature signature = methodInfo.getSignature();
						Type returnType = signature.getReturnType();

						CodeEmitter e = ce.begin_method(method.getModifiers() & ~Modifier.ABSTRACT, 
								signature, methodInfo.getExceptionTypes(), 
								methodInfo.getAttribute());

						e.zero_or_null(returnType);
						e.return_value();

						Type[] argumentTypes = methodInfo.getSignature().getArgumentTypes();
						int size = 0;
						if (argumentTypes != null) {
							for (int i = 0; i < argumentTypes.length; i++) {
								size += argumentTypes[i].getSize();
							}
						}

						// 1 is for this
						e.visitMaxs(returnType.getSize(), size + 1);

						e.end_method();
					}
				}

				ce.end_class();
			}
		};

		enhancer.setCallbackType(NoOp.class);
		enhancer.setInterfaces(new Class[]{clazz});
		enhancer.setClassLoader(classLoader != null ? 
				classLoader : clazz.getClassLoader());

		return enhancer.createClass().newInstance();
	}

	public static <T> T buildStub(final Class<T> clazz, MethodInterceptor interceptor, 
			ClassLoader classLoader) {
		Class<T> instrumentedClass;

		if ((Modifier.ABSTRACT & clazz.getModifiers()) != 0) {
			Enhancer enhancer = new Enhancer() {
				@Override
				public void generateClass(ClassVisitor v) throws Exception {
					ClassEmitter ce = new ClassEmitter(v);
					ce.begin_class(Constants.V1_5,
							Constants.ACC_PUBLIC,
							getClassName(),
							Type.getType(clazz),
							null,
							null);
					EmitUtils.null_constructor(ce);
					List methods = new ArrayList();
					getMethods(clazz, null, methods);

					for (Iterator iterator = methods.iterator(); iterator
					.hasNext();) {
						Method method = (Method) iterator.next();

						if (Modifier.isAbstract(method.getModifiers())) {
							MethodInfo methodInfo = ReflectUtils.getMethodInfo(method);
							Signature signature = methodInfo.getSignature();
							Type returnType = signature.getReturnType();

							CodeEmitter e = ce.begin_method(method.getModifiers() & ~Modifier.ABSTRACT, 
									signature, methodInfo.getExceptionTypes(), 
									methodInfo.getAttribute());

							e.zero_or_null(returnType);
							e.return_value();

							Type[] argumentTypes = methodInfo.getSignature().getArgumentTypes();
							int size = 0;
							if (argumentTypes != null) {
								for (int i = 0; i < argumentTypes.length; i++) {
									size += argumentTypes[i].getSize();
								}
							}

							// 1 is for this
							e.visitMaxs(returnType.getSize(), size + 1);

							e.end_method();
						}
					}

					ce.end_class();
				}
			};
			enhancer.setSuperclass(clazz);
			enhancer.setCallbackType(MethodInterceptor.class);
			enhancer.setClassLoader(classLoader != null ? 
					classLoader : clazz.getClassLoader());
			instrumentedClass = enhancer.createClass(); 
		} else {
			instrumentedClass = clazz;
		}

		Enhancer enhancer2 = new Enhancer();
		enhancer2.setSuperclass(instrumentedClass);
		enhancer2.setCallback(interceptor);
		enhancer2.setClassLoader(instrumentedClass.getClassLoader());

		return (T) enhancer2.create();
	}
}


