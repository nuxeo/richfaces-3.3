/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.richfaces;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.internal.annotations.IAnnotationTransformer;
import org.testng.internal.annotations.ITest;
import org.testng.internal.annotations.TestAnnotation;

/**
 * This transformer sets necessary data provider for each test method.
 *
 * @author carcasser
 */
public class TestAnnotationTransformer implements IAnnotationTransformer {

    /**
     * @see IAnnotationTransformer#transform(ITest, Class, Constructor, Method)
     */
    public void transform(ITest annotation, Class testClass, Constructor testConstructor, Method testMethod) {

        if ((testClass == null || RichSeleniumTest.class.isAssignableFrom(testClass))
                && (annotation instanceof TestAnnotation)) {
            ((TestAnnotation) annotation).setDataProvider("templates");
        }
    }

}