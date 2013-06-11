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

package org.ajax4jsf.builder.config;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.ActionSource;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class ClassWalkingLogicTest extends TestCase {
	
	public void testWalking() {
		
		final List<String> l = new ArrayList<String>();
		
		new ClassWalkingLogic(HtmlCommandButton.class)
			.walk(new ClassVisitor() {
				public void visit(Class<?> clazz) {
					l.add(clazz.getName());
					//System.out.println(clazz);
				}
			});
		assertFalse(l.isEmpty());
		assertTrue(l.contains(ActionSource.class.getName()));
		assertTrue(l.contains(StateHolder.class.getName()));
		assertTrue(l.contains(UIComponent.class.getName()));
		assertTrue(l.contains(HtmlCommandButton.class.getName()));
		assertFalse(l.contains(Object.class.getName()));
	}
}
