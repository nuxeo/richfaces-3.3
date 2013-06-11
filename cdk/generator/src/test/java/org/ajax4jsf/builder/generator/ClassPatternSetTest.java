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

import junit.framework.TestCase;

/**
 * @author shura
 *
 */
public class ClassPatternSetTest extends TestCase {
	
	
	protected ClassPatternSet patterns;

	/**
	 * @param name
	 */
	public ClassPatternSetTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		patterns = new ClassPatternSet();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		patterns = null;
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#setExcludes(java.lang.String)}.
	 */
	public void testSetExcludes() {
		patterns.setExcludes("a");
		String[] excludePatterns = patterns.getExcludePatterns();
		assertEquals(1, excludePatterns.length);
		assertEquals("a", excludePatterns[0]);
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#setExcludes(java.lang.String)}.
	 */
	public void testSetExcludes1() {
		patterns.setExcludes("a,b");
		String[] excludePatterns = patterns.getExcludePatterns();
		assertEquals(2, excludePatterns.length);
		assertEquals("a", excludePatterns[0]);
		assertEquals("b", excludePatterns[1]);
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#setIncludes(java.lang.String)}.
	 */
	public void testSetIncludes() {
		patterns.setIncludes("a");
		String[] includePatterns = patterns.getIncludePatterns();
		assertEquals(1, includePatterns.length);
		assertEquals("a", includePatterns[0]);
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#matchClass(java.lang.String)}.
	 */
	public void testMatchClassExclude() {
		patterns.setExcludes("**.Abst*,b.**");
		assertTrue(patterns.matchClass("a.b.UIComp"));
		assertFalse(patterns.matchClass("b.a.UIComp"));
		assertFalse(patterns.matchClass("a.b.AbstractUIComp"));
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#matchClass(java.lang.String)}.
	 */
	public void testMatchClassInclude() {
		patterns.setIncludes("**.UI*,a.**");
		assertTrue(patterns.matchClass("a.b.UIComp"));
		assertTrue(patterns.matchClass("b.a.UIComp"));
		assertFalse(patterns.matchClass("b.a.AbstractUIComp"));
	}
	
	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#matchClass(java.lang.String)}.
	 */
	public void testMatchClassIncExclude() {
		patterns.setExcludes("**.Abst*,b.**");
		patterns.setIncludes("**.UI*,a.**");
		assertTrue(patterns.matchClass("a.b.UIComp"));
		assertFalse(patterns.matchClass("b.a.UIComp"));
		assertFalse(patterns.matchClass("a.b.AbstractUIComp"));
	}
	
	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#matchPath(java.lang.String, java.lang.String)}.
	 */
	public void testMatchPath() {
		assertTrue(patterns.matchPath("**.D", "a.b.D"));
		assertTrue(patterns.matchPath("**.D?", "a.b.Da"));
		assertTrue(patterns.matchPath("**.D*", "a.b.Def"));
		assertTrue(patterns.matchPath("a.**.UI*", "a.b.UIComp"));
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#match(java.lang.String, java.lang.String)}.
	 */
	public void testMatch() {
		assertTrue(patterns.match("a", "a"));
		assertTrue(patterns.match("*", "a"));
		assertTrue(patterns.match("?", "a"));
		assertTrue(patterns.match("*a", "a"));
		assertTrue(patterns.match("*a", "ba"));
		assertTrue(patterns.match("*a", "cba"));
		assertTrue(patterns.match("a*a", "aba"));
		assertTrue(patterns.match("a*", "abc"));
		assertTrue(patterns.match("?a", "ba"));
		// Not match
		assertFalse(patterns.match("?", "ab"));
		assertFalse(patterns.match("b?", "ab"));
		assertFalse(patterns.match("b*", "ab"));
	}

	/**
	 * Test method for {@link org.ajax4jsf.builder.generator.ClassPatternSet#tokenizePathAsArray(java.lang.String)}.
	 */
	public void testTokenizePathAsArray() {
		String[] strings = patterns.tokenizePathAsArray("a.b.c");
		assertEquals(3, strings.length);
	}

}
